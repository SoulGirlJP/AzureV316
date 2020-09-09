package client;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import constants.ServerConstants;
import connections.Database.MYSQL;
import server.Items.ItemInformation;
import server.Items.MapleItemIdenfier;
import server.Movement.AbsoluteLifeMovement;
import server.Movement.LifeMovement;
import server.Movement.LifeMovementFragment;

public class MapleAndroid {

    private byte skincolor;
    private int hair, face;
    private int itemid;
    private String name;
    private Point pos;
    private int stance;
    private int uniqueid;
    private boolean isEear;

    public MapleAndroid(int itemid, int uniqueid) {
        this.itemid = itemid;
        this.uniqueid = uniqueid;
    }

    public int getUniqueId() {
        return uniqueid;
    }

    public void setPosition(Point pos) {
        this.pos = pos;
    }

    public Point getPosition() {
        return pos;
    }

    public void setStance(int stan) {
        this.stance = stan;
    }

    public int getStance() {
        return stance;
    }

    public void setSkinColor(int num) {
        this.skincolor = (byte) num;
    }

    public void setHair(int h) {
        this.hair = h;
    }

    public void setFace(int f) {
        this.face = f;
    }

    public void setItemId(int item) {
        this.itemid = item;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEear(boolean Eear) {
        this.isEear = Eear;
    }

    public boolean isEear() {
        return this.isEear;
    }

    public int getHair() {
        return hair;
    }

    public int getFace() {
        return face;
    }

    public byte getSkinColor() {
        return skincolor;
    }

    public int getItemId() {
        return itemid;
    }

    public String getName() {
        return name;
    }

    public static final MapleAndroid loadFromDb(final int itemid, final int uniqueid) {
        Connection con = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            MapleAndroid ret = new MapleAndroid(itemid, uniqueid);
            con = MYSQL.getConnection(); // Get a connection to the database
            ps = con.prepareStatement("SELECT * FROM android WHERE uniqueid = ?");
            ps.setInt(1, uniqueid);
            rs = ps.executeQuery();
            rs.next();
            ret.setName(rs.getString("name"));
            ret.setFace(rs.getInt("face"));
            ret.setHair(rs.getInt("hair"));
            ret.setItemId(rs.getInt("itemid"));
            ret.setSkinColor(rs.getInt("skincolor"));
            ret.setEear(rs.getInt("ear") == 1);
            rs.close();
            ps.close();
            con.close();
            return ret;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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

    public final void saveToDb() {
        Connection con = null; 
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("UPDATE android SET name = ?, face = ?, hair = ?, itemid = ?, skincolor = ?, ear = ? WHERE uniqueid = ?");
            ps.setString(1, name);
            ps.setInt(2, face);
            ps.setInt(3, hair);
            ps.setInt(4, itemid);
            ps.setInt(5, skincolor);
            ps.setInt(6, this.isEear ? 1 : 0);
            ps.setInt(7, uniqueid);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (final SQLException ex) {
            if (!ServerConstants.realese) {
                ex.printStackTrace();
            }
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

    public static final MapleAndroid createAndroid(final int itemid) {
        
        int ret = MapleItemIdenfier.getInstance().getNewUniqueId();
        int defHair, defFace;
        int android = ItemInformation.getInstance().getAndroid(itemid);
        if (ItemInformation.getInstance().getAndroidBasicSettings(android) == null) {
            System.err.println("[ERROR] Failed to load the basic appearance of Android while creating Android.");
        }
        defHair = ItemInformation.getInstance().getAndroidBasicSettings(android).getRandomHair();
        defFace = ItemInformation.getInstance().getAndroidBasicSettings(android).getRandomFace();
        Connection con = null; 
        PreparedStatement ps = null;
        try { // Commit to db first
            con = MYSQL.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO android (uniqueid, name, face, hair, itemid, skincolor) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, ret);
            ps.setString(2, "안드로이드");
            ps.setInt(3, defFace);
            ps.setInt(4, defHair);
            ps.setInt(5, itemid);
            ps.setInt(6, 0);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (final SQLException ex) {
            if (!ServerConstants.realese) {
                ex.printStackTrace();
            }
            return null;
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
        
        MapleAndroid newandroid = new MapleAndroid(itemid, ret);
        newandroid.setName("안드로이드");
        newandroid.setFace(defFace);
        newandroid.setHair(defHair);
        newandroid.setSkinColor(0);
        newandroid.setItemId(itemid);
        newandroid.setEear(false);
        return newandroid;
    }

    public final void updatePosition(final List<LifeMovementFragment> movement) {
        for (final LifeMovementFragment move : movement) {
            if (move instanceof LifeMovement) {
                if (move instanceof AbsoluteLifeMovement) {
                    setPosition(((LifeMovement) move).getPosition());
                }
                setStance(((LifeMovement) move).getNewstate());
            }
        }
    }
}
