package constants.Programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connections.Database.MYSQL;

public class DatabaseGarbageCollector {

    public static void main(String[] args) {
        int deletedrows = 0;
        List<Integer> items = new ArrayList<Integer>();

        try {
            Connection con = MYSQL.getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM android");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int uniqueid = rs.getInt("uniqueid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `inventoryitems` WHERE uniqueid = ?");
                check.setInt(1, uniqueid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM android WHERE uniqueid = ?");
                    del.setInt(1, uniqueid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM extendedslots");
            rs = ps.executeQuery();
            while (rs.next()) {
                int uniqueid = rs.getInt("uniqueid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `inventoryitems` WHERE uniqueid = ?");
                check.setInt(1, uniqueid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM extendedslots WHERE uniqueid = ?");
                    del.setInt(1, uniqueid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM extendedslots");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `inventoryitems` WHERE characterid = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM extendedslots WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM hiredmerch");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM hiredmerch WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM hiredmerchantsaveitems");
            rs = ps.executeQuery();
            while (rs.next()) {
                int mid = rs.getInt("merchid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `hiredmerchantsaves` WHERE id = ?");
                check.setInt(1, mid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con
                            .prepareStatement("DELETE FROM hiredmerchantsaveitems WHERE merchid = ?");
                    del.setInt(1, mid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `inner_ability_skills`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("player_id");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con
                            .prepareStatement("DELETE FROM `inner_ability_skills` WHERE player_id = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `inventoryitems` WHERE type = 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con
                            .prepareStatement("DELETE FROM `inventoryitems` WHERE characterid = ? AND type = 1");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `inventoryslot`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `inventoryslot` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `keymap`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `keymap` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `keyvalue`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `keyvalue` WHERE cid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `keyvalue2`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `keyvalue2` WHERE cid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            deletedrows += con.prepareStatement("DELETE FROM `keyvalue` WHERE `key` = '1stJobTrialCompleteTime'")
                    .executeUpdate();
            deletedrows += con.prepareStatement("DELETE FROM `keyvalue` WHERE `key` = '2ndJobTrialCompleteTime'")
                    .executeUpdate();
            deletedrows += con.prepareStatement("DELETE FROM `keyvalue` WHERE `key` = '3rdJobTrialCompleteTime'")
                    .executeUpdate();
            deletedrows += con.prepareStatement("DELETE FROM `keyvalue` WHERE `key` = '4thJobTrialCompleteTime'")
                    .executeUpdate();

            ps = con.prepareStatement("SELECT * FROM pets");
            rs = ps.executeQuery();
            while (rs.next()) {
                int uniqueid = rs.getInt("uniqueid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `inventoryitems` WHERE uniqueid = ?");
                check.setInt(1, uniqueid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM pets WHERE uniqueid = ?");
                    del.setInt(1, uniqueid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `questinfo`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `questinfo` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `queststatus`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `queststatus` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `quickslot`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `quickslot` WHERE cid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `rewardsaves`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `rewardsaves` WHERE cid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `skillmacros`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `skillmacros` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `skills`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `skills` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
            }

            ps = con.prepareStatement("SELECT * FROM `skills_cooldowns`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("charid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `skills_cooldowns` WHERE charid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement("SELECT * FROM `trocklocations`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("characterid");
                PreparedStatement check = con.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                check.setInt(1, cid);
                ResultSet checkrs = check.executeQuery();
                if (!checkrs.next()) {
                    PreparedStatement del = con.prepareStatement("DELETE FROM `trocklocations` WHERE characterid = ?");
                    del.setInt(1, cid);
                    del.executeUpdate();
                    deletedrows++;
                    del.close();
                }
                check.close();
                checkrs.close();
            }
            ps.close();
            rs.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("[Error] In the database cleaner " + deletedrows + "Rows removed.");
    }
}
