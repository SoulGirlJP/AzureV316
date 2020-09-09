package server.LifeEntity.MobEntity.MonsterEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import connections.Database.MYSQL;

public class MapleMonsterProvider {

    private static final MapleMonsterProvider instance = new MapleMonsterProvider();
    private final Map<Integer, List<MonsterDropEntry>> drops = new HashMap<Integer, List<MonsterDropEntry>>();
    private final List<MonsterGlobalDropEntry> globaldrops = new ArrayList<MonsterGlobalDropEntry>();

    protected MapleMonsterProvider() {
        retrieveGlobal();
    }

    public static final MapleMonsterProvider getInstance() {
        return instance;
    }

    public final List<MonsterGlobalDropEntry> getGlobalDrop() {
        return globaldrops;
    }

    public final void retrieveGlobal() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            final Connection con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM drop_data_global WHERE chance > 0");
            rs = ps.executeQuery();

            while (rs.next()) {
                globaldrops.add(new MonsterGlobalDropEntry(rs.getInt("itemid"), rs.getInt("chance"),
                        rs.getInt("continent"), rs.getByte("dropType"), rs.getInt("minimum_quantity"),
                        rs.getInt("maximum_quantity"), rs.getInt("questid")));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving drop" + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public final List<MonsterDropEntry> retrieveDrop(final int monsterId) {
        if (drops.containsKey(monsterId)) {
            return drops.get(monsterId);
        }
        final List<MonsterDropEntry> ret = new LinkedList<MonsterDropEntry>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid = ?");
            ps.setInt(1, monsterId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ret.add(new MonsterDropEntry(rs.getInt("itemid"), rs.getInt("chance"), rs.getInt("minimum_quantity"),
                        rs.getInt("maximum_quantity"), rs.getInt("questid")));
            }
            con.close();
            if (monsterId == 8880150) {
                int minimum = 1;
                int maximum = 10;
                ret.add(new MonsterDropEntry(4000000, 100 * 10000, minimum, maximum, 0));
            }
        } catch (SQLException e) {
            return ret;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignore) {
                return ret;
            }
        }
        drops.put(monsterId, ret);
        return ret;
    }

    public final void clearDrops() {
        drops.clear();
        globaldrops.clear();
        retrieveGlobal();
    }
}
