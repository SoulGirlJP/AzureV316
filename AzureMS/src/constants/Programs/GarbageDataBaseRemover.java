package constants.Programs;

import constants.ServerConstants;
import connections.Database.MYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GarbageDataBaseRemover {

    public static void main(String args[]) {
        System.out.println("[Notice] Start Database Cleanup\r\n");
        int deletedrows = 0;

        ServerConstants.Host = "localhost";
        ServerConstants.dbHost = "jdbc:mysql://localhost:3306/kms_316?autoReconnect=true&characterEncoding=euckr&maxReconnects=100";
        ServerConstants.dbUser = "root";
        ServerConstants.dbPassword = "";

        try {
            MYSQL.init();
            Connection c = MYSQL.getConnection();

            System.out.println("[Notice] Deleting the garbage database from the 'characters' table...");
            PreparedStatement p = c.prepareStatement("SELECT * FROM characters");
            ResultSet r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("accountid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `accounts` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM characters WHERE accountid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'inventoryitems' table...");
            p = c.prepareStatement("SELECT * FROM inventoryitems");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("characterid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM inventoryitems WHERE characterid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'inventoryequipment' table...");
            p = c.prepareStatement("SELECT * FROM inventoryequipment");
            r = p.executeQuery();
            while (r.next()) {
                long id = r.getLong("inventoryitemid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `inventoryitems` WHERE inventoryitemid = ?");
                b.setLong(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM inventoryequipment WHERE inventoryitemid = ?");
                    d.setLong(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'inventoryslot' table...");
            p = c.prepareStatement("SELECT * FROM inventoryslot");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("characterid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM inventoryslot WHERE characterid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'keymap' table...");
            p = c.prepareStatement("SELECT * FROM keymap");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("characterid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM keymap WHERE characterid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'keyvalue' table...");
            p = c.prepareStatement("SELECT * FROM keyvalue");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("cid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM keyvalue WHERE cid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'keyvalue2' table...");
            p = c.prepareStatement("SELECT * FROM keyvalue2");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("cid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM keyvalue2 WHERE cid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'mountdata' table...");
            p = c.prepareStatement("SELECT * FROM mountdata");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("characterid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM mountdata WHERE characterid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'skills' table...");
            p = c.prepareStatement("SELECT * FROM skills");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("characterid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM skills WHERE characterid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'pets' table...");
            p = c.prepareStatement("SELECT * FROM pets");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("uniqueid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `inventoryitems` WHERE uniqueid = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM pets WHERE uniqueid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'android' table...");
            p = c.prepareStatement("SELECT * FROM android");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("uniqueid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `inventoryitems` WHERE uniqueid = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM android WHERE uniqueid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'buddies' table...");
            p = c.prepareStatement("SELECT * FROM buddies");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("characterid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM buddies WHERE characterid = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            System.out.println("[Notice] Deleting the garbage database from the 'guilds' table...");
            p = c.prepareStatement("SELECT * FROM guilds");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("leader");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `characters` WHERE id = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("DELETE FROM guilds WHERE leader = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    deletedrows++;
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();

            p = c.prepareStatement("SELECT * FROM characters");
            r = p.executeQuery();
            while (r.next()) {
                int id = r.getInt("guildid");
                PreparedStatement b = c.prepareStatement("SELECT * FROM `guilds` WHERE guildid = ?");
                b.setInt(1, id);
                ResultSet rs = b.executeQuery();
                if (!rs.next()) {
                    PreparedStatement d = c.prepareStatement("UPDATE characters set guildid = 0, guildrank = 5 WHERE `guildid` = ?");
                    d.setInt(1, id);
                    d.executeUpdate();
                    d.close();
                }
                b.close();
                rs.close();
            }
            p.close();
            r.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("\r\n[Notice] GarbageDataBaseRemover Complete, Remove Size : " + (EquipRemover.rm + deletedrows) + "\r\n");
    }
}
