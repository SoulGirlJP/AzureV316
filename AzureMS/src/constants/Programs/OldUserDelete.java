package constants.Programs;

import connections.Database.MYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OldUserDelete {

    public static void main(String[] args) {
        MYSQL.init();
        System.out.println("[Notification] Database Cleanup Program has been working.");
        long startTime = System.currentTimeMillis();
        int deletedrows = 0;

        try {
            Connection con = MYSQL.getConnection();

            System.out.println("[Notification] DB is being deleted.");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int lastconnect = rs.getInt("lastconnect");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
                Date date = Calendar.getInstance().getTime();
                date.setDate(date.getDate() - 30);
                if (lastconnect < Integer.parseInt(sdf.format(date))) {
                    deletedrows++;
                    PreparedStatement ps3 = con.prepareStatement("DELETE FROM accounts WHERE id = ?");
                    ps3.setInt(1, rs.getInt("id"));
                    ps3.executeUpdate();
                    ps3.close();

                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM characters where accountid = ?");
                    ps2.setInt(1, rs.getInt("id"));
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        deleteCharacter(rs.getInt("id"), rs2.getInt("id"));
                    }
                    rs2.close();
                    ps2.close();
                }
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        System.out.println("[Notice] " + deletedrows + "Accounts have been deleted. The time required " + (System.currentTimeMillis() - startTime) + "ms is.");
    }

    public final static boolean deleteCharacter(final int accId, final int cid) {
        try {
            final Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id, guildid, guildrank, name FROM characters WHERE id = ? AND accountid = ?");
            ps.setInt(1, cid);
            ps.setInt(2, accId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rs.close();
                ps.close();
                con.close();
                return false;
            }
            rs.close();
            ps.close();

            ps = con.prepareStatement("DELETE FROM characters WHERE id = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM skills WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM hiredmerch WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM mountdata WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM monsterbook WHERE charid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM keyvalue WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM keyvalue2 WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM inventoryitems WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `inner_ability_skills` WHERE player_id = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `inventoryslot` WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `keymap` WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `questinfo` WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `queststatus` WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `quickslot` WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `skillmacros` WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `skills_cooldowns` WHERE charid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `steelskills` WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("DELETE FROM `trocklocations` WHERE characterid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (final SQLException e) {
            System.err.println("DeleteChar error" + e);
        }
        return false;
    }
}
