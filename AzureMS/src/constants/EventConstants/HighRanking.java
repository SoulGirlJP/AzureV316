package constants.EventConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.ServerConstants;
import constants.Data.HighRankingType;
import connections.Database.MYSQL;
import launcher.ServerPortInitialize.ChannelServer;
import tools.Timer.WorldTimer;

public class HighRanking {

    private static HighRanking instance = null;
    private Map<Integer, RankingDataHolder> rankingData = new HashMap<Integer, RankingDataHolder>();

    public static HighRanking getInstance() {
        if (instance == null) {
            instance = new HighRanking();
        }
        return instance;
    }

    public void startTasking() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        };
        WorldTimer.getInstance().register(r, 3600000L);
    }

    public final void refreshData() {
        /* [Auto Save] */
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.saveAllMerchant();
        }

        rankingData.clear();
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            // Type 0
            // <editor-fold defaultstate="collapsed" desc="1st job change shortest time">
            String sql = "SELECT k.value as time, c.id, c.name FROM keyvalue2 as k, characters as c WHERE k.`key` = '1stJobTrialCompleteTime' AND k.cid = c.id ORDER BY k.`value` ASC LIMIT 100";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            RankingDataHolder rdh = new RankingDataHolder();
            while (rs.next()) {
                RankingData rank = new RankingData(rs.getInt("id"), rs.getString("name"), rs.getInt("time"), 0, 0);
                rdh.getRankings().add(rank);
            }
            rankingData.put(HighRankingType.FirstAdvance.getType(), rdh);
            // </editor-fold>

            // Type 1
            // <editor-fold defaultstate="collapsed" desc="2nd job shortest time">
            sql = "SELECT k.value as time, c.id, c.name FROM keyvalue2 as k, characters as c WHERE k.`key` = '2ndJobTrialCompleteTime' AND k.cid = c.id ORDER BY k.`value` ASC LIMIT 100";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            rdh = new RankingDataHolder();
            while (rs.next()) {
                RankingData rank = new RankingData(rs.getInt("id"), rs.getString("name"), rs.getInt("time"), 0, 0);
                rdh.getRankings().add(rank);
            }
            rankingData.put(HighRankingType.SecondAdvance.getType(), rdh);
            // </editor-fold>

            // Type 2
            // <editor-fold defaultstate="collapsed" desc="3rd job shortest time">
            sql = "SELECT k.value as time, c.id, c.name FROM keyvalue2 as k, characters as c WHERE k.`key` = '3rdJobTrialCompleteTime' AND k.cid = c.id ORDER BY k.`value` ASC LIMIT 100";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            rdh = new RankingDataHolder();
            while (rs.next()) {
                String lifeCountSql = "SELECT `value` FROM `keyvalue2` WHERE `key` = '3rdTrialLifeCount' AND `cid` = '"
                        + rs.getInt("id") + "'";
                PreparedStatement lifeCountPs = con.prepareStatement(lifeCountSql);
                ResultSet lifeCountRs = lifeCountPs.executeQuery();
                String failedCountSql = "SELECT `value` FROM `keyvalue2` WHERE `key` = '3rdTrialFailedCount' AND `cid` = '"
                        + rs.getInt("id") + "'";
                PreparedStatement failedCountPs = con.prepareStatement(failedCountSql);
                ResultSet failedCountRs = failedCountPs.executeQuery();
                if (lifeCountRs.next() && failedCountRs.next()) {
                    RankingData rank = new RankingData(rs.getInt("id"), rs.getString("name"), rs.getInt("time"),
                            lifeCountRs.getInt("value"), failedCountRs.getInt("value"));
                    rdh.getRankings().add(rank);
                }
                lifeCountPs.close();
                lifeCountRs.close();
                failedCountPs.close();
                failedCountRs.close();
            }
            rankingData.put(HighRankingType.ThirdAdvance.getType(), rdh);
            // </editor-fold>

            // Type 3
            // <editor-fold defaultstate="collapsed" desc="4th job shortest time">
            sql = "SELECT k.value as time, c.id, c.name FROM keyvalue2 as k, characters as c WHERE k.`key` = '4thJobTrialCompleteTime' AND k.cid = c.id ORDER BY k.`value` ASC LIMIT 100";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            rdh = new RankingDataHolder();
            while (rs.next()) {
                RankingData rank = new RankingData(rs.getInt("id"), rs.getString("name"), rs.getInt("time"), 0, 0);
                rdh.getRankings().add(rank);
            }
            rankingData.put(HighRankingType.ForthAdvance.getType(), rdh);
            // </editor-fold>

            con.close();
        } catch (Exception e) {
            System.err.println("[Error] An error occurred while updating the high ranking data.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public RankingDataHolder getData(Integer key) {
        return rankingData.get(key);
    }

    public class RankingDataHolder {

        private List<RankingData> Rankings = new ArrayList<RankingData>();

        public List<RankingData> getRankings() {
            return Rankings;
        }
    }

    public class RankingData {

        private int id, value1, value2, value3;
        private String name;

        public RankingData(int id, String name, int value1, int value2, int value3) {
            this.id = id;
            this.name = name;
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getValue1() {
            return value1;
        }

        public int getValue2() {
            return value2;
        }

        public int getValue3() {
            return value3;
        }

    }

}
