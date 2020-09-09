package constants.Programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connections.Database.MYSQL;

public class SpiritDel {

    public static void main(String args[]) {
        Connection con = null;
        ResultSet sql = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            sql = con.prepareStatement("SELECT * FROM accounts").executeQuery();
            ps = con.prepareStatement("DELETE FROM accounts WHERE id = ?");
            while (sql.next()) {
                if (!CheckChar(con, sql.getInt("id"))) {
                    ps.setInt(1, sql.getInt("id"));
                    ps.executeUpdate();
                    System.out.println(sql.getString("name") + "Delete account");
                }
            }
            ps.close();
            sql.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (sql != null) {
                    sql.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public static boolean CheckChar(Connection con, int i) {
        ResultSet sql = null;
        try {
            sql = con.prepareStatement("SELECT * FROM characters WHERE accountid =" + i).executeQuery();
            if (sql.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (sql != null) {
                    sql.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
        return false;
    }
}
