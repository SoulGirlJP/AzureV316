package server.Items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import connections.Database.MYSQL;

public class MapleItemIdenfier {

    private AtomicInteger runningId = new AtomicInteger(0);
    private static MapleItemIdenfier instance = null;

    public static MapleItemIdenfier getInstance() {
        if (instance == null) {
            instance = new MapleItemIdenfier();
            try {
                Connection con = MYSQL.getConnection();
                PreparedStatement ps = con
                        .prepareStatement("SELECT uniqueid FROM inventoryitems ORDER BY uniqueid DESC LIMIT 1");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    instance.runningId.set(rs.getInt(1));
                }
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException sql) {
            }
        }
        return instance;
    }

    public synchronized int getNewUniqueId() {
        return runningId.incrementAndGet();
    }
}
