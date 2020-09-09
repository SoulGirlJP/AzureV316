package client.Skills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import client.MapleClient;
import connections.Packets.PacketUtility.ReadingMaple;

public final class VCore {

    private int coreid, charid, level, exp, state, skill1, skill2, skill3;
    private long crcid;

    public VCore(long crcid, int coreid, int charid, int level, int exp, int state, int skill1, int skill2, int skill3) {
        setCrcid(crcid);
        setCoreId(coreid);
        setCharid(charid);
        setLevel(level);
        setExp(exp);
        setState(state);
        setSkill1(skill1);
        setSkill2(skill2);
        setSkill3(skill3);
    }

    public void SaveCore(Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO core (crcid, coreid, charid, level, exp, state, skill1, skill2, skill3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, getCrcid());
            ps.setInt(2, getCoreId());
            ps.setInt(3, getCharid());
            ps.setInt(4, getLevel());
            ps.setInt(5, getExp());
            ps.setInt(6, getState());
            ps.setInt(7, getSkill1());
            ps.setInt(8, getSkill2());
            ps.setInt(9, getSkill3());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public long getCrcid() {
        return crcid;
    }

    public void setCrcid(long crcid) {
        this.crcid = crcid;
    }

    public int getCoreId() {
        return coreid;
    }

    public void setCoreId(int id) {
        this.coreid = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSkill1() {
        return skill1;
    }

    public void setSkill1(int skill1) {
        this.skill1 = skill1;
    }

    public int getSkill2() {
        return skill2;
    }

    public void setSkill2(int skill2) {
        this.skill2 = skill2;
    }

    public int getSkill3() {
        return skill3;
    }

    public void setSkill3(int skill3) {
        this.skill3 = skill3;
    }

    public int getCharid() {
        return charid;
    }

    public void setCharid(int charid) {
        this.charid = charid;
    }

    public static void CoreResult(ReadingMaple slea, MapleClient c) {
        Map<Skill, SkillEntry> z = new HashMap<>();
        // c.getSession().write(CWvsContext.updateMatrixSkills(z));
    }
}
