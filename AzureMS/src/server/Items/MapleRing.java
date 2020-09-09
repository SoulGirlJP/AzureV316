package server.Items;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import client.Character.MapleCharacter;
import connections.Database.MYSQL;
import connections.Database.MYSQLException;
import tools.RandomStream.Randomizer;

public class MapleRing implements Serializable {

    private static final long serialVersionUID = 9179541993413738579L;
    private final int ringId;
    private final int ringId2;
    private final int partnerId;
    private final int itemId;
    private String partnerName;
    private boolean equipped = false;

    private MapleRing(int id, int id2, int partnerId, int itemid, String partnerName) {
        this.ringId = id;
        this.ringId2 = id2;
        this.partnerId = partnerId;
        this.itemId = itemid;
        this.partnerName = partnerName;
    }

    public static MapleRing loadFromDb(int ringId) {
        return loadFromDb(ringId, false);
    }

    public static MapleRing loadFromDb(int ringId, boolean equipped) {
        try {
            Connection con = MYSQL.getConnection();
            MapleRing ret;
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM rings WHERE ringId = ?")) {
                ps.setInt(1, ringId);
                try (ResultSet rs = ps.executeQuery()) {
                    ret = null;
                    if (rs.next()) {
                        ret = new MapleRing(ringId, rs.getInt("partnerRingId"), rs.getInt("partnerChrId"),
                                rs.getInt("itemid"), rs.getString("partnerName"));
                        ret.setEquipped(equipped);
                    }
                }
            }
            con.close();
            return ret;
        } catch (SQLException ex) {
        }
        return null;
    }

    public static void addToDB(int itemid, MapleCharacter chr, int id, int id2) throws SQLException {
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO rings (id, ringId, itemid, partnerChrId, partnerName, partnerRingId) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1, id);
        ps.setInt(2, id);
        ps.setInt(3, itemid);
        ps.setInt(4, chr.getId());
        ps.setString(5, chr.getName());
        ps.setInt(6, id2);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public static void addToDB(int itemid, MapleCharacter chr, String player, int id, int[] ringId) throws SQLException {
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO rings (ringId, itemid, partnerChrId, partnerName, partnerRingId) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, ringId[0]);
        ps.setInt(2, itemid);
        ps.setInt(3, chr.getId());
        ps.setString(4, chr.getName());
        ps.setInt(5, ringId[1]);
        ps.executeUpdate();
        ps.close();

        ps = con.prepareStatement("INSERT INTO rings (ringId, itemid, partnerChrId, partnerName, partnerRingId) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, ringId[1]);
        ps.setInt(2, itemid);
        ps.setInt(3, id);
        ps.setString(4, player);
        ps.setInt(5, ringId[0]);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public static int createRing(int itemid, MapleCharacter partner1, String partner2, String msg, int id2, int sn) {
        try {
            if (partner1 == null) {
                return -2;
            }
            if (id2 <= 0) {
                return -1;
            }
            return makeRing(itemid, partner1, partner2, id2, msg, sn);
        } catch (Exception ex) {
        }
        return 0;
    }


    public static int[] makeRing(int itemid, MapleCharacter partner1, MapleCharacter partner2) throws Exception {
        int[] ringID = {Randomizer.nextInt(), Randomizer.nextInt()};
        addToDB(itemid, partner1, partner2.getName(), partner2.getId(), ringID);
        return ringID;
    }

    public static int makeRing(int itemid, MapleCharacter partner1, String partner2, int id2, String msg, int sn) throws Exception {
        //    int[] ringID = {MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance()};
        int[] ringID = {Randomizer.nextInt(), Randomizer.nextInt()};
        addToDB(itemid, partner1, partner2, id2, ringID);
        InventoryManipulator.addRing(partner1, itemid, ringID[1], sn, partner2);
        // partner1.getCashInventory().gift(id2, partner1.getName(), msg, sn, ringID[0]);
        return 1;
    }
        public static int makeRing(int itemid, MapleCharacter partner, int id, int id2) throws Exception {
        addToDB(itemid, partner, id, id2);
        return 1;
    }

    public int getRingId() {
        return this.ringId;
    }

    public int getPartnerRingId() {
        return this.ringId2;
    }

    public int getPartnerChrId() {
        return this.partnerId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public boolean isEquipped() {
        return this.equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public String getPartnerName() {
        return this.partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    @Override
    public boolean equals(Object o) {
        if ((o instanceof MapleRing)) {
            return ((MapleRing) o).getRingId() == getRingId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.ringId;
        return hash;
    }

    public static void removeRingFromDb(MapleCharacter player) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM rings WHERE partnerChrId = ?");
            ps.setInt(1, player.getId());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                con.close();
                return;
            }
            int otherId = rs.getInt("partnerRingId");
            int otherotherId = rs.getInt("ringId");
            rs.close();
            ps.close();
            ps = con.prepareStatement("DELETE FROM rings WHERE ringId = ? OR ringId = ?");
            ps.setInt(1, otherotherId);
            ps.setInt(2, otherId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException sex) {
        }
    }

    public static class RingComparator implements Comparator<MapleRing>, Serializable {

        @Override
        public int compare(MapleRing o1, MapleRing o2) {
            if (o1.ringId < o2.ringId) {
                return -1;
            }
            if (o1.ringId == o2.ringId) {
                return 0;
            }
            return 1;
        }
    }
}
