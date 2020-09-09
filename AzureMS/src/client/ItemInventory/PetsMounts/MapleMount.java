package client.ItemInventory.PetsMounts;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;

import client.Character.MapleCharacter;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import tools.RandomStream.Randomizer;

public class MapleMount {

    private int itemid, skillid, fatigue, exp, level;
    private transient boolean changed = false;
    private transient ScheduledFuture<?> tirednessSchedule = null;
    private transient WeakReference<MapleCharacter> owner;

    public MapleMount(MapleCharacter owner, int id, int skillid, int fatigue, int level, int exp) {
        this.itemid = id;
        this.skillid = skillid;
        this.fatigue = fatigue;
        this.level = level;
        this.exp = exp;
        this.owner = new WeakReference<MapleCharacter>(owner);
    }

    public void saveMount(final int charid) throws SQLException {
        if (!changed) {
            return;
        }
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con
                .prepareStatement("UPDATE mountdata set `Level` = ?, `Exp` = ?, `Fatigue` = ? WHERE characterid = ?");
        ps.setInt(1, level);
        ps.setInt(2, exp);
        ps.setInt(3, fatigue);
        ps.setInt(4, charid);
        ps.close();
        con.close();
    }

    public int getId() {
        switch (itemid) {
            case 1902000:
            case 1902001:
            case 1902002:
                return itemid - 1901999;
            case 1902005:
            case 1902006:
            case 1902007:
                return itemid - 1902004;
            case 1902015:
            case 1902016:
            case 1902017:
            case 1902018:
                return itemid - 1902014;
            case 1902040:
            case 1902041:
            case 1902042:
                return itemid - 1902039;
            default:
                return 4;
        }
    }

    public int getItemId() {
        return itemid;
    }

    public int getSkillId() {
        return skillid;
    }

    public int getFatigue() {
        return fatigue;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public void setItemId(int c) {
        changed = true;
        this.itemid = c;
    }

    public void setFatigue(int amount) {
        changed = true;
        fatigue += amount;
        if (fatigue < 0) {
            fatigue = 0;
        }
    }

    public void setExp(int c) {
        changed = true;
        this.exp = c;
    }

    public void setLevel(int c) {
        changed = true;
        this.level = c;
    }

    public void increaseFatigue() {

        changed = true;
        this.fatigue++;
        if (fatigue > 100 && owner.get() != null) {
            owner.get().dispelSkill(1004);
        }
        update();
    }

    /*
	 * public void startSchedule() { tirednessSchedule =
	 * TimerManager.getInstance().register(new Runnable() {
	 * 
	 * public void run() { increaseFatigue(); } }, 30000, 30000); }
     */

 /*
	 * public void cancelSchedule() { if (tirednessSchedule != null) {
	 * tirednessSchedule.cancel(false); } }
     */
    public void increaseExp() {
        int e;
        if (level >= 1 && level <= 7) {
            e = Randomizer.nextInt(10) + 15;
        } else if (level >= 8 && level <= 15) {
            e = Randomizer.nextInt(13) + 15 / 2;
        } else if (level >= 16 && level <= 24) {
            e = Randomizer.nextInt(23) + 18 / 2;
        } else {
            e = Randomizer.nextInt(28) + 25 / 2;
        }
        setExp(exp + e);
    }

    public void update() {
        final MapleCharacter chr = owner.get();
        if (chr != null && chr != null) {
            // cancelSchedule();
            chr.getMap().broadcastMessage(MainPacketCreator.updateMount(chr, false));
        }
    }
}
