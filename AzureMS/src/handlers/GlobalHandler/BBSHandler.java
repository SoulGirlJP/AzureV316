package handlers.GlobalHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.MapleClient;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;

public class BBSHandler {

    private static String correctLength(final String in, final int maxSize) {
        if (in.length() > maxSize) {
            return in.substring(0, maxSize);
        }
        return in;
    }

    public static void BBSOperatopn(final ReadingMaple rh, final MapleClient c) {
        if (c.getPlayer().getGuildId() <= 0) {
            return; // expelled while viewing bbs or hax
        }
        int localthreadid = 0;
        switch (rh.readByte()) {
            case 0: // start a new post
                final boolean bEdit = rh.readByte() == 1 ? true : false;
                if (bEdit) {
                    localthreadid = rh.readInt();
                }
                final boolean bNotice = rh.readByte() == 1 ? true : false;
                final String title = correctLength(rh.readMapleAsciiString(), 25);
                String text = correctLength(rh.readMapleAsciiString(), 600);
                final int icon = rh.readInt();
                if (icon >= 0x64 && icon <= 0x6a) {
                    if (!c.getPlayer().haveItem(5290000 + icon - 0x64, 1, false, true)) {
                        return; // hax, using an nx icon that s/he doesn't have
                    }
                } else if (!(icon >= 0 && icon <= 2)) {
                    return; // hax, using an invalid icon
                }
                if (!bEdit) {
                    newBBSThread(c, title, text, icon, bNotice);
                } else {
                    editBBSThread(c, title, text, icon, localthreadid);
                }
                break;
            case 1: // delete a thread
                localthreadid = rh.readInt();
                deleteBBSThread(c, localthreadid);
                break;
            case 2: // list threads
                int start = rh.readInt();
                listBBSThreads(c, start * 10);
                break;
            case 3: // list thread + reply, followed by id (int)
                localthreadid = rh.readInt();
                displayThread(c, localthreadid, true);
                break;
            case 4: // reply
                localthreadid = rh.readInt();
                text = correctLength(rh.readMapleAsciiString(), 25);
                newBBSReply(c, localthreadid, text);
                break;
            case 5: // delete reply
                localthreadid = rh.readInt(); // we don't use this
                int replyid = rh.readInt();
                deleteBBSReply(c, replyid);
                break;
        }
    }

    private static void listBBSThreads(MapleClient c, int start) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("SELECT * FROM bbs_threads WHERE guildid = ? ORDER BY localthreadid DESC");
            ps.setInt(1, c.getPlayer().getGuildId());
            ResultSet rs = ps.executeQuery();

            c.getSession().writeAndFlush(MainPacketCreator.BBSThreadList(rs, start));

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        }
    }

    private static void newBBSReply(final MapleClient c, final int localthreadid, final String text) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("SELECT threadid FROM bbs_threads WHERE guildid = ? AND localthreadid = ?");
            ps.setInt(1, c.getPlayer().getGuildId());
            ps.setInt(2, localthreadid);
            ResultSet threadRS = ps.executeQuery();

            if (!threadRS.next()) {
                threadRS.close();
                ps.close();
                con.close();
                return; // thread no longer exists, deleted?
            }
            int threadid = threadRS.getInt("threadid");
            threadRS.close();
            ps.close();

            ps = con.prepareStatement(
                    "INSERT INTO bbs_replies (`threadid`, `postercid`, `timestamp`, `content`) VALUES "
                    + "(?, ?, ?, ?)");
            ps.setInt(1, threadid);
            ps.setInt(2, c.getPlayer().getId());
            ps.setLong(3, System.currentTimeMillis());
            ps.setString(4, text);
            ps.execute();
            ps.close();

            ps = con.prepareStatement("UPDATE bbs_threads SET replycount = replycount + 1 WHERE threadid = ?");
            ps.setInt(1, threadid);
            ps.execute();
            ps.close();
            con.close();
            displayThread(c, localthreadid, true);
        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        }
    }

    private static void editBBSThread(final MapleClient c, final String title, final String text, final int icon,
            final int localthreadid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return; // expelled while viewing?
        }
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("UPDATE bbs_threads SET " + "`name` = ?, `timestamp` = ?, " + "`icon` = ?, "
                            + "`startpost` = ? WHERE guildid = ? AND localthreadid = ? AND (postercid = ? OR ?)");
            ps.setString(1, title);
            ps.setLong(2, System.currentTimeMillis());
            ps.setInt(3, icon);
            ps.setString(4, text);
            ps.setInt(5, c.getPlayer().getGuildId());
            ps.setInt(6, localthreadid);
            ps.setInt(7, c.getPlayer().getId());
            ps.setBoolean(8, c.getPlayer().getGuildRank() <= 2);
            ps.execute();
            ps.close();
            con.close();
            displayThread(c, localthreadid, true);
        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        }
    }

    private static void newBBSThread(final MapleClient c, final String title, final String text, final int icon,
            final boolean bNotice) {
        if (c.getPlayer().getGuildId() <= 0) {
            return; // expelled while viewing?
        }
        int nextId = 0;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps;

            if (!bNotice) { // notice's local id is always 0, so we don't need to fetch it
                ps = con.prepareStatement(
                        "SELECT MAX(localthreadid) AS lastLocalId FROM bbs_threads WHERE guildid = ?");
                ps.setInt(1, c.getPlayer().getGuildId());
                ResultSet rs = ps.executeQuery();

                rs.next();
                nextId = rs.getInt("lastLocalId") + 1;
                rs.close();
                ps.close();
            }

            ps = con.prepareStatement("INSERT INTO bbs_threads (`postercid`, `name`, `timestamp`, `icon`, `startpost`, "
                    + "`guildid`, `localthreadid`) VALUES(?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, c.getPlayer().getId());
            ps.setString(2, title);
            ps.setLong(3, System.currentTimeMillis());
            ps.setInt(4, icon);
            ps.setString(5, text);
            ps.setInt(6, c.getPlayer().getGuildId());
            ps.setInt(7, nextId);
            ps.execute();

            ps.close();
            con.close();
            displayThread(c, nextId, true);
        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        }
    }

    private static void deleteBBSThread(final MapleClient c, final int localthreadid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT threadid, postercid FROM bbs_threads WHERE guildid = ? AND localthreadid = ?");
            ps.setInt(1, c.getPlayer().getGuildId());
            ps.setInt(2, localthreadid);
            ResultSet threadRS = ps.executeQuery();

            if (!threadRS.next()) {
                threadRS.close();
                ps.close();
                con.close();
                return; // thread no longer exists, deleted?
            }
            if (c.getPlayer().getId() != threadRS.getInt("postercid") && c.getPlayer().getGuildRank() > 2) {
                threadRS.close();
                ps.close();
                con.close();
                return; // [hax] deleting a thread that he didn't make
            }
            int threadid = threadRS.getInt("threadid");
            threadRS.close();
            ps.close();

            ps = con.prepareStatement("DELETE FROM bbs_replies WHERE threadid = ?");
            ps.setInt(1, threadid);
            ps.execute();
            ps.close();

            ps = con.prepareStatement("DELETE FROM bbs_threads WHERE threadid = ?");
            ps.setInt(1, threadid);
            ps.execute();
            ps.close();
            con.close();
        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        }
    }

    private static void deleteBBSReply(final MapleClient c, final int replyid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }

        int threadid;
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("SELECT postercid, threadid FROM bbs_replies WHERE replyid = ?");
            ps.setInt(1, replyid);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rs.close();
                ps.close();
                con.close();
                return; // thread no longer exists, deleted?
            }
            if (c.getPlayer().getId() != rs.getInt("postercid") && c.getPlayer().getGuildRank() > 2) {
                rs.close();
                ps.close();
                con.close();
                return; // [hax] deleting a reply that he didn't make
            }
            threadid = rs.getInt("threadid");
            rs.close();
            ps.close();

            ps = con.prepareStatement("DELETE FROM bbs_replies WHERE replyid = ?");
            ps.setInt(1, replyid);
            ps.execute();
            ps.close();

            ps = con.prepareStatement("UPDATE bbs_threads SET replycount = replycount - 1 WHERE threadid = ?");
            ps.setInt(1, threadid);
            ps.execute();
            ps.close();
            con.close();
            displayThread(c, threadid, false);
        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        }
    }

    private static void displayThread(final MapleClient c, final int threadid, final boolean bIsThreadIdLocal) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet repliesRS = null;
        ResultSet threadRS = null;

        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM bbs_threads WHERE guildid = ? AND "
                    + (bIsThreadIdLocal ? "local" : "") + "threadid = ?");
            ps.setInt(1, c.getPlayer().getGuildId());
            ps.setInt(2, threadid);
            threadRS = ps.executeQuery();

            if (!threadRS.next()) {
                threadRS.close();
                ps.close();
                con.close();
                return; // thread no longer exists, deleted?
            }

            if (threadRS.getInt("replycount") > 0) {
                ps2 = con.prepareStatement("SELECT * FROM bbs_replies WHERE threadid = ?");
                ps2.setInt(1, !bIsThreadIdLocal ? threadid : threadRS.getInt("threadid"));
                repliesRS = ps2.executeQuery();
                // the lack of repliesRS.next() is intentional
            }
            c.getSession().writeAndFlush(MainPacketCreator
                    .showThread(bIsThreadIdLocal ? threadid : threadRS.getInt("localthreadid"), threadRS, repliesRS));

        } catch (SQLException se) {
            System.err.println("SQLException: " + se.getLocalizedMessage() + se);
        } catch (RuntimeException re) {
            System.err.println("The number of reply rows does not match the replycount in thread.  ThreadId = "
                    + re.getMessage() + re);
            try {
                ps = con.prepareStatement("DELETE FROM bbs_threads WHERE threadid = ?");
                ps.setInt(1, Integer.parseInt(re.getMessage()));
                ps.execute();
                ps.close();

                ps = con.prepareStatement("DELETE FROM bbs_replies WHERE threadid = ?");
                ps.setInt(1, Integer.parseInt(re.getMessage()));
                ps.execute();
                ps.close();

                if (ps != null) {
                    ps.close();
                }
                if (repliesRS != null) {
                    repliesRS.close();
                }
                if (threadRS != null) {
                    threadRS.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (SQLException e) {
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (repliesRS != null) {
                    repliesRS.close();
                }
                if (threadRS != null) {
                    threadRS.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }
}
