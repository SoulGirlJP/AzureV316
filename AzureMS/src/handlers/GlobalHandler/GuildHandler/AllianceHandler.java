package handlers.GlobalHandler.GuildHandler;

import client.MapleClient;
import client.Character.MapleCharacter;
import client.Community.MapleGuild.MapleGuild;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;


public class AllianceHandler {

    public static final void AllianceOperatopn(final ReadingMaple rh, final MapleClient c, boolean denied) {
        System.out.println(rh);
        if (c.getPlayer().getGuildId() <= 0) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final MapleGuild gs = ChannelServer.getGuild(c.getPlayer().getGuildId());
        if (gs == null) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        byte op = rh.readByte();
        if (c.getPlayer().getGuildRank() != 1 && op != 1) {
            return;
        }
        if (op == 22) {
            denied = true;
        }
        int leaderid = 0;
        if (gs.getAllianceId() > 0) {
            leaderid = ChannelServer.getAllianceLeader(gs.getAllianceId());
        }
        if (op != 4 && !denied) {
            if (gs.getAllianceId() <= 0 || leaderid <= 0) {
                return;
            }
        } else if (leaderid > 0 || gs.getAllianceId() > 0) {
            return;
        }
        if (denied) {
            DenyInvite(c, gs);
            return;
        }
        MapleCharacter chr;
        int inviteid;
        switch (op) {
            case 1:

                for (byte[] pack : ChannelServer.getAllianceInfo(gs.getAllianceId(), false)) {
                    if (pack != null) {
                        c.getSession().writeAndFlush(pack);
                    }
                }
                break;
            case 3:
                String name = rh.readMapleAsciiString();
                final int newGuild = ChannelServer.getGuildLeader(name);
                if (newGuild > 0 && c.getPlayer().getAllianceRank() == 0 && leaderid == c.getPlayer().getId()) {
                    chr = c.getChannelServer().getPlayerStorage().getCharacterById(newGuild);
                    if (chr != null && chr.getGuildId() > 0 && ChannelServer.canInvite(gs.getAllianceId())) {
                        chr.getClient().getSession().writeAndFlush(MainPacketCreator.sendAllianceInvite(ChannelServer.getAlliance(gs.getAllianceId()).getName(), c.getPlayer()));
                        ChannelServer.setInvitedId(chr.getGuildId(), gs.getAllianceId());
                    } else {
                        c.getPlayer().dropMessage(1, "Guild ground of target guild is not connected.");
                    }
                } else {
                    c.getPlayer().dropMessage(1, "" + leaderid + "  / " + name + " / " + c.getPlayer().getAllianceRank() + " / Target Guild Not Found.");
                }
                break;
            case 4:
                inviteid = ChannelServer.getInvitedId(c.getPlayer().getGuildId());
                if (inviteid > 0) {
                    if (!ChannelServer.addGuildToAlliance(inviteid, c.getPlayer().getGuildId())) {
                        c.getPlayer().dropMessage(5, "Guild does not exist.");
                    }
                    ChannelServer.setInvitedId(c.getPlayer().getGuildId(), 0);
                }
                break;
            case 2:
            case 6:
                final int gid;
                if (op == 6 && rh.available() >= 4) {
                    gid = rh.readInt();
                    if (rh.available() >= 4 && gs.getAllianceId() != rh.readInt()) {
                        break;
                    }
                } else {
                    gid = c.getPlayer().getGuildId();
                }
                if (c.getPlayer().getAllianceRank() <= 2 && (c.getPlayer().getAllianceRank() == 1 || c.getPlayer().getGuildId() == gid)) {
                    if (!ChannelServer.removeGuildFromAlliance(gs.getAllianceId(), gid, c.getPlayer().getGuildId() != gid)) {
                        c.getPlayer().dropMessage(5, "Guild does not exist.");
                    }
                }
                break;
            case 7:
                if (leaderid == c.getPlayer().getId()) {
                    if (!ChannelServer.changeAllianceLeader(gs.getAllianceId(), rh.readInt())) {
                        c.getPlayer().dropMessage(5, "I'm not a leader.");
                    }
                }
                break;
            case 8:
                String[] ranks = new String[5];
                for (int i = 0; i < 5; i++) {
                    ranks[i] = rh.readMapleAsciiString();
                }
                ChannelServer.updateAllianceRanks(gs.getAllianceId(), ranks);
                break;
            case 9:
                if (c.getPlayer().getAllianceRank() <= 2) {
                    if (!ChannelServer.changeAllianceRank(gs.getAllianceId(), rh.readInt(), rh.readByte())) {
                        c.getPlayer().dropMessage(5, "Guild does not exist.");
                    }
                }
                break;
            case 10:
                if (c.getPlayer().getAllianceRank() <= 2) {
                    final String notice = rh.readMapleAsciiString();
                    if (notice.length() > 100) {
                        break;
                    }
                    ChannelServer.updateAllianceNotice(gs.getAllianceId(), notice);
                }
                break;
            default:
                System.out.println("Unhandled GuildAlliance op: " + op + ", \n" + rh.toString());
                break;
        }
    }

    public static final void DenyInvite(MapleClient c, final MapleGuild gs) {
        final int inviteid = ChannelServer.getInvitedId(c.getPlayer().getGuildId());
        if (inviteid > 0) {
            final int newAlliance = ChannelServer.getAllianceLeader(inviteid);
            if (newAlliance > 0) {
                final MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterById(newAlliance);
                if (chr != null) {
                    chr.dropMessage(5, gs.getName() + "Guild Union Error.");
                }
                ChannelServer.setInvitedId(c.getPlayer().getGuildId(), 0);
            }
        }
    }
}