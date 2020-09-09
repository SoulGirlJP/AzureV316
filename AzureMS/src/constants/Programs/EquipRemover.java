package constants.Programs;

import constants.ServerConstants;
import connections.Database.MYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipRemover {
    
    public static int rm = 0;
    
    public static void main(String[] args) {
            ServerConstants.Host = "localhost";
            ServerConstants.dbHost = "jdbc:mysql://localhost:3306/kms_316?autoReconnect=true&characterEncoding=euckr&maxReconnects=100";
            ServerConstants.dbUser = "root";
            ServerConstants.dbPassword = "";
            run();
    }

    public static void run() {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;

        List<Integer> inventoryitemids = new ArrayList<Integer>();
        try {
            MYSQL.init();
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM inventoryitems");
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                inventoryitemids.add(i, rs.getInt("inventoryitemid"));
                i++;
            }
            
            ps = con.prepareStatement("SELECT * FROM inventoryequipment");
            rs = ps.executeQuery();
            while (rs.next()) {
                int iid = rs.getInt("inventoryitemid");
                if (!inventoryitemids.contains(iid)) {
                    System.out.println(iid);
                    rm++;
                    pse = con.prepareStatement("DELETE FROM inventoryequipment WHERE inventoryitemid = ?");
                    pse.setInt(1, iid);
                    pse.executeUpdate();
                    pse.close();
                }
            }

            inventoryitemids = null;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                con = null;
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (pse != null) {
                try {
                    pse.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

