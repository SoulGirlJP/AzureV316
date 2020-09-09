/*
 * 테스피아 Project
 * ==================================
 * 팬더 spirit_m@nate.com
 * 백호 softwarewithcreative@nate.com
 * ==================================
 * 
 */
package handlers.GlobalHandler.PlayerHandler;

import client.Character.MapleCharacter;
import client.Community.MapleBuddy.BuddyList;
import client.Community.MapleBuddy.BuddyList.BuddyAddResult;
import client.Community.MapleBuddy.BuddyList.BuddyOperation;
import static client.Community.MapleBuddy.BuddyList.BuddyOperation.ADDED;
import static client.Community.MapleBuddy.BuddyList.BuddyOperation.DELETED;
import client.Community.MapleBuddy.BuddylistEntry;
import client.MapleClient;
import client.MaplePlayerIdNamePair;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldCommunity;
import launcher.Utility.WorldConnected;

public class BuddyListHandler {

    private static final class CharacterIdNameBuddyCapacity extends MaplePlayerIdNamePair {

        private int buddyCapacity;

        public CharacterIdNameBuddyCapacity(int id, String name, int level, int job, int buddyCapacity) {
            super(id, name, level, job);
            this.buddyCapacity = buddyCapacity;
        }

        public int getBuddyCapacity() {
            return buddyCapacity;
        }
    }

    private static final void nextPendingRequest(final MapleClient c) {
        MaplePlayerIdNamePair pendingBuddyRequest = c.getPlayer().getBuddylist().pollPendingRequest();
        if (pendingBuddyRequest != null) {
            c.getSession().writeAndFlush(MainPacketCreator.requestBuddylistAdd(c, pendingBuddyRequest.getId(), pendingBuddyRequest.getName(), pendingBuddyRequest.getLevel(), pendingBuddyRequest.getJob(), false));
        }
    }

    private static final CharacterIdNameBuddyCapacity getCharacterIdAndNameFromDatabase(final String name) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CharacterIdNameBuddyCapacity ret = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM characters WHERE name LIKE ?");
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                ret = new CharacterIdNameBuddyCapacity(rs.getInt("id"), rs.getString("name"), rs.getInt("level"), rs.getInt("job"), rs.getInt("buddyCapacity"));
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public static final String getCharacterNameFromId(final int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ret = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                ret = rs.getString("name");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    public static final void BuddyOperation(final ReadingMaple rh, final MapleClient c) {
        final int mode = rh.readByte();
        final BuddyList buddylist = c.getPlayer().getBuddylist();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        switch (mode) {
            case 1:
                //친구 추가.
                final String addName = rh.readMapleAsciiString();
                final String groupName = rh.readMapleAsciiString();
                final BuddylistEntry ble = buddylist.get(addName);
                if (addName.length() > 13 || groupName.length() > 16) {
                    return;
                }
                if (ble != null && !ble.isVisible()) { //You are already registered as a friend
                    c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "You are already registered as a friend."));
                } else if (buddylist.isFull()) { //Friends list is full.
                    c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Friends list is full."));
                } else {
                    try {
                        CharacterIdNameBuddyCapacity charWithId = null;
                        int channel;
                        final MapleCharacter otherChar = c.getChannelServer().getPlayerStorage().getCharacterByName(addName);
                        if (otherChar != null) {
                            channel = c.getChannel();
                            if (!otherChar.isGM() || c.getPlayer().isGM()) {
                                charWithId = new CharacterIdNameBuddyCapacity(otherChar.getId(), otherChar.getName(), otherChar.getLevel(), otherChar.getJob(), otherChar.getBuddylist().getCapacity());
                            }
                        } else {
                            channel = WorldConnected.find(addName);
                            charWithId = getCharacterIdAndNameFromDatabase(addName);
                        }
                        if (charWithId != null) {
                            BuddyAddResult buddyAddResult = null;
                            if (channel != -1) {
                                buddyAddResult = WorldCommunity.requestBuddyAdd(addName, c.getChannel(), c.getPlayer().getId(), c.getPlayer().getName(), c.getPlayer().getLevel(), c.getPlayer().getJob(), groupName);
                            } else {
                                con = MYSQL.getConnection();
                                ps = con.prepareStatement("SELECT COUNT(*) as buddyCount FROM buddies WHERE characterid = ? AND pending = 0");
                                ps.setInt(1, charWithId.getId());
                                rs = ps.executeQuery();
                                if (!rs.next()) {
                                    ps.close();
                                    rs.close();
                                    con.close();
                                    throw new RuntimeException("Result set expected");
                                } else {
                                    int count = rs.getInt("buddyCount");
                                    if (count >= charWithId.getBuddyCapacity()) {
                                        buddyAddResult = BuddyAddResult.BUDDYLIST_FULL;
                                    }
                                }
                                rs.close();
                                ps.close();

                                ps = con.prepareStatement("SELECT pending FROM buddies WHERE characterid = ? AND buddyid = ?");
                                ps.setInt(1, charWithId.getId());
                                ps.setInt(2, c.getPlayer().getId());
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    buddyAddResult = BuddyAddResult.ALREADY_ON_LIST;
                                }
                                rs.close();
                                ps.close();
                                con.close();
                            }
                            if (buddyAddResult == BuddyAddResult.BUDDYLIST_FULL) { //Add friend window is full.
                                c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Friends list is full."));
                            } else {
                                int displayChannel = -1;
                                int otherCid = charWithId.getId();
                                if (buddyAddResult == BuddyAddResult.ALREADY_ON_LIST && channel != -1) {
                                    c.send(MainPacketCreator.serverNotice(1, "Character is already on target's friend list."));
                                    return;
                                } else if (buddyAddResult != BuddyAddResult.ALREADY_ON_LIST && channel == -1) {
                                    con = MYSQL.getConnection();
                                    ps = con.prepareStatement("INSERT INTO buddies (`characterid`, `buddyid`, `groupname`, `pending`) VALUES (?, ?, ?, 1)");
                                    ps.setInt(1, charWithId.getId());
                                    ps.setInt(2, c.getPlayer().getId());
                                    ps.setString(3, groupName);
                                    ps.executeUpdate();
                                    ps.close();
                                    con.close();
                                }
                                buddylist.put(new BuddylistEntry(charWithId.getName(), otherCid, groupName, displayChannel, true, charWithId.getLevel(), charWithId.getJob()));
                                c.getSession().writeAndFlush(MainPacketCreator.updateBuddylist(buddylist.getBuddies(), 23, 0));
                            }
                        } else { //No character found.
                            c.send(MainPacketCreator.serverNotice(1, "No character found."));
                        }
                    } catch (SQLException e) {
                        System.err.println("SQL THROW" + e);
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                            if (ps != null) {
                                ps.close();
                            }
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                break;
            case 2: {
                //Accept friend
                int otherCid = rh.readInt();
                if (!buddylist.isFull()) {
                    try {
                        final int channel = WorldConnected.find(otherCid);
                        String otherName = null;
                        int otherLevel = 0, otherJob = 0;
                        MapleCharacter otherChar = null;
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            otherChar = cserv.getPlayerStorage().getCharacterById(otherCid);
                            if (otherChar != null) {
                                break;
                            }
                        }
                        if (otherChar == null) {
                            con = MYSQL.getConnection();
                            ps = con.prepareStatement("SELECT name, level, job FROM characters WHERE id = ?");
                            ps.setInt(1, otherCid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                otherName = rs.getString("name");
                                otherLevel = rs.getInt("level");
                                otherJob = rs.getInt("job");
                            }
                            rs.close();
                            ps.close();
                            con.close();
                        } else {
                            otherName = otherChar.getName();
                        }
                        if (otherName != null) {
                            buddylist.put(new BuddylistEntry(otherName, otherCid, "그룹 미지정", channel, true, otherLevel, otherJob));
                            c.getSession().writeAndFlush(MainPacketCreator.requestBuddylistAdd(c, otherCid, otherName, 0, 0, true));
                            c.getSession().writeAndFlush(MainPacketCreator.updateBuddylist(buddylist.getBuddies(), 10, 0));

                            notifyRemoteChannel(c, channel, otherCid, ADDED);
                        }
                        otherChar.BuddiesSaveToDB();
                        c.getPlayer().BuddiesSaveToDB();
                    } catch (SQLException e) { 
                        System.err.println("SQL THROW" + e);
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                            if (ps != null) {
                                ps.close();
                            }
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Friends list is full."));
                }
                break;
            }
            case 5: {
                //Remove friend
                final int otherCid = rh.readInt();
                if (buddylist.containsVisible(otherCid)) {
                    notifyRemoteChannel(c, WorldConnected.find(otherCid), otherCid, DELETED);
                }
                buddylist.remove(otherCid);
                c.getSession().writeAndFlush(MainPacketCreator.updateBuddylist(c.getPlayer().getBuddylist().getBuddies(), 18, otherCid));
                break;
            }
            //Reject a friend
            case 6:
                break;
            case 10:
                //Increase friends
                if (c.getPlayer().getMeso() >= 50000) {
                    c.getPlayer().setBuddyCapacity(c.getPlayer().getBuddyCapacity() + 5);
                    c.getPlayer().gainMeso(-50000, false);
                }
                break;
            default:
                break;
        }
    }

    private static void notifyRemoteChannel(final MapleClient c, final int remoteChannel, final int otherCid, final BuddyOperation operation) {
        final MapleCharacter player = c.getPlayer();
        if (remoteChannel != -1) {
            ChannelServer.getInstance(remoteChannel).buddyChanged(otherCid, player.getId(), player.getName(), c.getChannel(), operation, player.getLevel(), player.getJob());
        }
    }
}
