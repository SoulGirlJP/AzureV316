package client.ItemInventory.PetsMounts;

import constants.ServerConstants;
import connections.Database.MYSQL;


import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import server.Items.ItemInformation;
import server.Items.MapleItemIdenfier;
import server.Movement.AbsoluteLifeMovement;
import server.Movement.LifeMovement;
import server.Movement.LifeMovementFragment;

public class MaplePet {

    private String name;
    private byte summoned = 0;
    private int Fh = 0, stance = 0, fullness = 100, level = 1, closeness = 0, skillValue = 0, buffSkillId = 0, uniqueid, petitemid, flags;
    private Point pos;
    private short inventorypos = 0;
    private int[] skillItemId = new int[8];
    private long expireDate = 0;
    private int[] baseSkillId = {5190000, 5190002, 5190003, 5190005, 5190004, 5190001, 5190006, 5190010, 5190011, 5190012};
    private int[] baseSkillId1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
    private int[] baseKey = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512};
    public boolean isStartFullness = false;

    private MaplePet(final int petitemid) {
        this.petitemid = petitemid;
    }

    private MaplePet(final int petitemid, final int uniqueid, final short inventorypos) {
        this.petitemid = petitemid;
        this.uniqueid = uniqueid;
        this.inventorypos = inventorypos;
    }

    public static final MaplePet loadFromDb(final int itemid, final int uniqueid, final short inventorypos) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            final MaplePet ret = new MaplePet(itemid, uniqueid, inventorypos);

            con = MYSQL.getConnection(); // Get a connection to the database
            ps = con.prepareStatement("SELECT * FROM pets WHERE uniqueid = ?"); // Get pet details..
            ps.setInt(1, uniqueid);

            rs = ps.executeQuery();
            rs.next();

            ret.setName(rs.getString("name"));
            ret.setCloseness(rs.getInt("closeness"));
            ret.setLevel(rs.getInt("level"));
            ret.setFullness(rs.getInt("fullness"));
            ret.setExpireDate(rs.getLong("expireDate"));
            ret.setBuffSkillId(rs.getInt("pet_buff"));
            String[] skillid = rs.getString("pet_skill").split(",");
            for (int next = 0; next < skillid.length; ++next) { //Call up the pet skill.
                ret.addSkillId(Integer.parseInt(skillid[next]));
                ret.addSkillValue(Integer.parseInt(skillid[next]));
            }
            rs.close();
            ps.close();

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
            ps = con.prepareStatement("UPDATE pets SET name = ?, level = ?, closeness = ?, fullness = ?, pet_skill = ?, pet_buff = ?, expiredate = ? WHERE uniqueid = ?");
            ps.setString(1, name); // Set name
            ps.setInt(2, level); // Set Level
            ps.setInt(3, closeness); // Set Closeness
            ps.setInt(4, fullness); // Set Fullness
            StringBuilder builder = new StringBuilder();
            int count = 0;
            for (int next = 0; next < skillItemId.length; ++next) {
                if (skillItemId[next] > 0) {
                    builder.append(skillItemId[next]);
                    builder.append(',');
                    ++count;
                }
            }
            if (count == 0) {
                builder.append("-1,");
            }
            String temp = builder.toString();
            ps.setString(5, temp.substring(0, temp.length() - 1));
            ps.setInt(6, buffSkillId);
            ps.setLong(7, expireDate);
            ps.setInt(8, uniqueid); // Set ID
            ps.executeUpdate(); // Execute statement
            ps.close();
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

    public static final MaplePet createPet(final int itemid, long expireDate) {
        int ret = MapleItemIdenfier.getInstance().getNewUniqueId();
        Connection con = null;
        PreparedStatement ps = null;
        try { // Commit to db first
            con = MYSQL.getConnection();
            ps = con.prepareStatement("INSERT INTO pets (uniqueid, name, level, closeness, fullness, expiredate) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, ret);
            ps.setString(2, ItemInformation.getInstance().getName(itemid));
            ps.setInt(3, 1);
            ps.setInt(4, 0);
            ps.setInt(5, 100);
            ps.setLong(6, expireDate);
            ps.executeUpdate();
            ps.close();
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
        final MaplePet pet = new MaplePet(itemid);
        pet.setName(ItemInformation.getInstance().getName(itemid));
        pet.setLevel(15);
        pet.setCloseness(0);
        pet.setFullness(100);
        pet.setUniqueId(ret);
        pet.setExpireDate(expireDate);
 /*       if (ItemInformation.getInstance().pickupItem(itemid)) {
        pet.addSkillId(5190010);
        pet.addSkillId(5190000);
        pet.addSkillId(5190001);
        pet.addSkillId(5190006);
        }*/
        return pet;
    }

    public final boolean getSummoned() {
        return summoned > 0;
    }

    public final byte getSummonedValue() {
        return summoned;
    }

    public final void setSummoned(final int summoned) {
        this.summoned = (byte) summoned;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final short getInventoryPosition() {
        return inventorypos;
    }

    public final void setInventoryPosition(final short inventorypos) {
        this.inventorypos = inventorypos;
    }

    public int getUniqueId() {
        return uniqueid;
    }

    public void setUniqueId(int id) {
        this.uniqueid = id;
    }

    public final int getCloseness() {
        return closeness;
    }

    public final void setCloseness(final int closeness) {
        this.closeness = closeness;
    }

    public final int getLevel() {
        return level;
    }

    public final void setLevel(final int level) {
        this.level = level;
    }

    public final int getFullness() {
        return fullness;
    }

    public final void setFullness(final int fullness) {
        this.fullness = fullness;
    }

    public final long getExpireDate() {
        return expireDate;
    }

    public final void setExpireDate(final long expireDate) {
        this.expireDate = expireDate;
    }

    public final int getFh() {
        return Fh;
    }

    public final void setFh(final int Fh) {
        this.Fh = Fh;
    }

    public final int getFlags() {
        return flags;
    }

    public final void setFlags(final int flags) {
        this.flags = flags;
    }

    public final Point getPos() {
        return pos;
    }

    public final void setPos(final Point pos) {
        this.pos = pos;
    }

    public final int getStance() {
        return stance;
    }

    public final void setStance(final int stance) {
        this.stance = stance;
    }

    public final int getPetItemId() {
        return petitemid;
    }

    public final int getBuffSkillId() {
        return buffSkillId;
    }

    public final void setBuffSkillId(int skillId) {
        this.buffSkillId = skillId;
    }

    public final int getFlag(int itemId) {
        for (int axiz = 0; axiz < skillItemId.length; ++axiz) {
            if (baseSkillId[axiz] == itemId) {
                return baseKey[axiz];
            }
        }
        return 0;
    }

    public final void addSkillId(int itemId) {
        for (int axiz = 0; axiz < skillItemId.length; ++axiz) {
            if (skillItemId[axiz] == 0) {
                skillItemId[axiz] = itemId;
                return;
            }
        }
    }

    public final void addSkillValue(int itemId) {
        for (int axiz = 0; axiz < baseSkillId.length; ++axiz) {
            if (baseSkillId[axiz] == itemId) {
                skillValue += baseKey[axiz];
                return;
            }
        }
    }

    public final void removeSkillId(int itemId) {
        for (int axiz = 0; axiz < skillItemId.length; ++axiz) {
            if (skillItemId[axiz] == itemId) {
                skillItemId[axiz] = 0;
                return;
            }
        }
    }

    public final void removeSkillValue(int itemId) {
        for (int axiz = 0; axiz < baseSkillId.length; ++axiz) {
            if (baseSkillId[axiz] == itemId) {
                skillValue -= baseKey[axiz];
                return;
            }
        }
    }

   /*  public final int getSkillValue() {
        return skillValue;
    }*/
    
       public final int getSkillValue() {
        int ret = 0;
        for (int i = 0; i < baseSkillId1.length; i++) {
            ret += baseSkillId1[i];
        }
        return ret;
    }

    public final boolean canConsume(final int itemId) {
        final ItemInformation mii = ItemInformation.getInstance();
        for (final int petId : mii.petsCanConsume(itemId)) {
            //////System.out.println(petId);
            if (petId == petitemid) {
                return true;
            }
        }
        return false;
    }

    public final int getTameness(final int itemId) {
        final ItemInformation mii = ItemInformation.getInstance();
        return mii.getTameness(itemId);
    }

    public final int getRepleteness(final int itemId) {
        final ItemInformation mii = ItemInformation.getInstance();
        return mii.getRepleteness(itemId);
    }

    public final void updatePosition(final List<LifeMovementFragment> movement) {
        for (final LifeMovementFragment move : movement) {
            if (move instanceof LifeMovement) {
                if (move instanceof AbsoluteLifeMovement) {
                    setPos(((LifeMovement) move).getPosition());
                }
                setStance(((LifeMovement) move).getNewstate());
            }
        }
    }
}
