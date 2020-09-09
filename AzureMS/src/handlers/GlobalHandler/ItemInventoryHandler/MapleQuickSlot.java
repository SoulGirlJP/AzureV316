package handlers.GlobalHandler.ItemInventoryHandler;

import connections.Database.MYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import constants.ServerConstants;
import java.sql.SQLException;
import tools.Pair;

public class MapleQuickSlot {

    private List<Pair<Integer, Integer>> quickslot = new ArrayList<Pair<Integer, Integer>>();
    private int cid;

    public MapleQuickSlot(int cid) {
        this.cid = cid;
    }

    public List<Pair<Integer, Integer>> getQuickSlot() {
        return quickslot;
    }

    public void loadFromDB() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM quickslot WHERE cid = ?");
            ps.setInt(1, cid);
            rs = ps.executeQuery();
            while (rs.next()) {
                quickslot.add(new Pair<Integer, Integer>(rs.getInt("index"), rs.getInt("key")));
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
            System.err.println("[Error] Failed to load quickslot.");
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void saveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("DELETE FROM quickslot WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();
            for (Pair<Integer, Integer> p : quickslot) {
                ps = con.prepareStatement("INSERT INTO quickslot VALUES (?, ?, ?)");
                ps.setInt(1, cid);
                ps.setInt(2, p.getLeft());
                ps.setInt(3, p.getRight());
                ps.executeUpdate();
                ps.close();
            }
            con.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
            System.err.println("[ERROR] Failed to save quickslot.");
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void resetQuickSlot() {
        quickslot.clear();
    }

    public void addQuickSlot(int index, int key) {
        quickslot.add(new Pair<Integer, Integer>(index, key));
    }

    public int getKeyByIndex(int index) {
        for (Pair<Integer, Integer> p : quickslot) {
            if (p.getLeft() == index) {
                return p.getRight();
            }
        }
        return -1;
    }

}
