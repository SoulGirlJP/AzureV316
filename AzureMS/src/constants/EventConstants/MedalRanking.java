package constants.EventConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import client.Character.MapleCharacter;
import client.ItemInventory.MapleInventoryType;
import constants.ServerConstants;
import connections.Database.MYSQL;
import launcher.ServerPortInitialize.ChannelServer;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import tools.CurrentTime;

public class MedalRanking {

    private static MedalRanking instance = null;
    private static Map<String, List<MedalRankHolder>> rank = new HashMap<String, List<MedalRankHolder>>();
    private static Map<String, ReentrantReadWriteLock> lock = new HashMap<String, ReentrantReadWriteLock>();

    public static MedalRanking getInstance() {
        if (instance == null) {
            instance = new MedalRanking();
        }
        return instance;
    }

    public void init() {
        try {
            System.out.println("[Notification] Loading Medal Rankings.");
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT type FROM medalranking");
            PreparedStatement ps2 = con
                    .prepareStatement("SELECT cid,name,value FROM medalranking WHERE type = '?' ORDER BY value ASC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<MedalRankHolder> ret = new ArrayList<MedalRankHolder>();
                ps2.setString(1, rs.getString("type"));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    MedalRankHolder mrh = new MedalRankHolder(rs2.getInt("cid"), rs2.getString("name"),
                            rs2.getInt("value"));
                    ret.add(mrh);
                }

                rank.put(rs.getString("type"), ret);
                lock.put(rs.getString("type"), new ReentrantReadWriteLock());
                ps2.close();
            }
            ps.close();
            con.close();
        } catch (Throwable e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        sort();
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownListener()));
    }

    public class ShutdownListener implements Runnable {

        @Override
        public void run() {
            save();
        }
    }

    public void save() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM medalranking");
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("INSERT INTO (type, cid, name, value) VALUES (?, ?, ?, ?)");
            for (Entry<String, List<MedalRankHolder>> e : rank.entrySet()) {
                lock.get(e.getKey()).readLock().lock();
                try {
                    ps.setString(1, e.getKey());
                    for (MedalRankHolder mrh : e.getValue()) {
                        ps.setInt(2, mrh.cid);
                        ps.setString(3, mrh.name);
                        ps.setInt(4, mrh.value);
                        ps.executeUpdate();
                    }
                } finally {
                    lock.get(e.getKey()).readLock().unlock();
                }
            }
            ps.close();
            con.close();
        } catch (Throwable e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public void sort() {
        for (Entry<String, List<MedalRankHolder>> l : rank.entrySet()) {
            lock.get(l.getKey()).writeLock().lock();
            try {
                Collections.sort(l.getValue(), new Comparator<MedalRankHolder>() {

                    @Override
                    public int compare(MedalRankHolder o1, MedalRankHolder o2) {
                        if (o1.value > o2.value) {
                            return 1;
                        } else if (o1.value == o2.value) {
                            return 0;
                        } else if (o1.value < o2.value) {
                            return -1;
                        }
                        return 0;
                    }

                });
            } finally {
                lock.get(l.getKey()).writeLock().unlock();
            }
        }
    }

    public int getCurrentRank(int cid, String type) {
        int i = -1;
        lock.get(type).readLock().lock();
        try {

            for (MedalRankHolder l : rank.get(type)) {
                if (l.cid == cid) {
                    break;
                }
                i++;
            }
        } finally {
            lock.get(type).readLock().unlock();
        }
        return i;
    }

    public List<MedalRankHolder> getRanks(String type) {
        return Collections.unmodifiableList(rank.get(type));
    }

    public void submitRank(MapleCharacter hp, String type, int value) {
        int cur = getCurrentRank(hp.getId(), type);
        int cur1stid = 0;
        if (rank.get(type).size() > 0) {
            cur1stid = rank.get(type).get(0).cid;
        }
        MedalRankHolder mrh = null;
        if (cur != -1) {
            lock.get(type).writeLock().lock();
            try {
                mrh = rank.get(type).remove(cur - 1);
                rank.get(type).add(new MedalRankHolder(hp.getId(), hp.getName(), value));
            } finally {
                lock.get(type).writeLock().unlock();
            }
        }
        sort();
        int cur2 = getCurrentRank(hp.getId(), type);
        if (cur != 1 && cur2 == 1) {
            if (cur1stid != 0) {
                boolean isConnected = false;
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterById(cur1stid);
                    if (chr != null) {
                        if (chr.getInventory(MapleInventoryType.EQUIP).countById(getMedal(type)) > 0) {
                            InventoryManipulator.removeById(chr.getClient(), MapleInventoryType.EQUIP, getMedal(type),
                                    1, false, false);
                        } else if (chr.getInventory(MapleInventoryType.EQUIPPED).countById(getMedal(type)) > 0) {
                            InventoryManipulator.removeById(chr.getClient(), MapleInventoryType.EQUIPPED,
                                    getMedal(type), 1, false, false);
                        }
                        chr.message(5, "Someone sets a new record [" + ItemInformation.getInstance().getName(getMedal(type)) + "] The decoration was confiscated.");
                        isConnected = true;
                    }
                }
                if (!isConnected) {
                    try {
                        Connection con = MYSQL.getConnection();
                        con.prepareStatement("DELETE FROM inventoryitems WHERE itemid = " + getMedal(type)
                                + " WHERE cid = " + cur1stid).executeUpdate();
                        con.prepareStatement(
                                "DELETE FROM rewardsaves WHERE itemid = " + getMedal(type) + " WHERE cid = " + cur1stid)
                                .executeUpdate();
                        con.close();
                    } catch (Exception e) {
                        if (!ServerConstants.realese) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (hp.getInventory(MapleInventoryType.EQUIP).isFull()) {
                hp.addRewardDB(hp.getId(), getMedal(type), 1);
                hp.message(1, "Out of inventory space, no medal awarded. Please get it from Lucian of Henesys.");
            } else {
                hp.gainItem(getMedal(type), (short) 1, false, -1, CurrentTime.getAllCurrentTime() + "Items obtained by No. 1 in the medal ranking.");
            }
            hp.message(5, "Set a new record [" + ItemInformation.getInstance().getName(getMedal(type)) + "] Got the decoration!");

        }

    }

    public class MedalRankHolder {

        public int cid, value;
        public String name;

        public MedalRankHolder(int cid, String name, int value) {
            this.cid = cid;
            this.name = name;
            this.value = value;
        }
    }

    public int getMedal(String type) {
        if (type.equals("¾Ó")) {
            return 1111;
        }
        return 0;
    }
}
