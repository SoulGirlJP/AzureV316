package client.Community.MapleGuild;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import connections.Database.MYSQL;

public class MapleGuildRanking {

    private static MapleGuildRanking instance = new MapleGuildRanking();
    private List<GuildRankingInfo> ranks = new LinkedList<GuildRankingInfo>();
    private long lastUpdate = System.currentTimeMillis();
    private boolean hasLoaded = false;

    public static MapleGuildRanking getInstance() {
        return instance;
    }

    public List<GuildRankingInfo> getRank() {
        if ((ranks.isEmpty() && !hasLoaded) || (System.currentTimeMillis() - lastUpdate) > 3600000) {
            hasLoaded = true; // TO prevent loading when there's no guild for the server
            reload();
        }
        return ranks;
    }

    private void reload() {
        ranks.clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM guilds ORDER BY `GP` DESC LIMIT 50");
            rs = ps.executeQuery();

            while (rs.next()) {
                final GuildRankingInfo rank = new GuildRankingInfo(rs.getString("name"), rs.getInt("GP"),
                        rs.getInt("logo"), rs.getInt("logoColor"), rs.getInt("logoBG"), rs.getInt("logoBGColor"));

                ranks.add(rank);
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error handling guildRanking" + e);
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
        lastUpdate = System.currentTimeMillis();
    }
}
