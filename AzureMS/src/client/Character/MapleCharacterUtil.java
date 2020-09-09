package client.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connections.Database.MYSQL;

public class MapleCharacterUtil {

    public static final boolean canCreateChar(final String name) {
        if (name.getBytes().length < 4 || name.getBytes().length > 14 || getIdByName(name) != -1) {
            return false;
        }
        return true;
    }

    public static final String makeMapleReadable(final String in) {
        String wui = in.replace('I', 'i');
        wui = wui.replace('l', 'L');
        wui = wui.replace("rn", "Rn");
        wui = wui.replace("vv", "Vv");
        wui = wui.replace("VV", "Vv");
        return wui;
    }

    public static final int getIdByName(final String name) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT id FROM characters WHERE name = ?");
            ps.setString(1, name);
            final ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rs.close();
                ps.close();
                con.close();
                return -1;
            }
            final int id = rs.getInt("id");
            rs.close();
            ps.close();
            con.close();
            return id;
        } catch (SQLException e) {
            System.err.println("error 'getIdByName' " + e);
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
                System.out.println("[ERROR] There was a problem closing the connection´Ù.  " + ex);
            }
        }
        return -1;
    }

    public final boolean PromptPoll(final int accountid) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        boolean prompt = false;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * from game_poll_reply where AccountId = ?");
            ps.setInt(1, accountid);

            rs = ps.executeQuery();
            prompt = rs.next() ? false : true;
        } catch (SQLException e) {
        } finally {
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
            } catch (SQLException e) {
            }
        }
        return prompt;
    }

    public final boolean SetPoll(final int accountid, final int selection) {
        if (!PromptPoll(accountid)) { // Hacking OR spamming the db.
            return false;
        }

        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("INSERT INTO game_poll_reply (AccountId, SelectAns) VALUES (?, ?)");
            ps.setInt(1, accountid);
            ps.setInt(2, selection);

            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return true;
    }
}
