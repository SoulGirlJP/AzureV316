package launcher.AdminGUI;

import org.apache.mina.common.IoSession;

import client.Character.MapleCharacter;
import connections.Packets.MainPacketCreator;
import connections.Packets.UIPacket;
import constants.ServerConstants;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;

public class AdminToolClientHandler {

    public static final void updateServerInfo(IoSession session, int exp, int drop, int meso, String serverMessage) {
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            cs.setExpRate(exp);
            cs.setMesoRate(meso);
            cs.setDropRate(drop);
            cs.setServerMessage(serverMessage);
        }
        session.write(AdminToolPacket.Message(1));
    }

    public static final void giveItem(IoSession session, String charName, int itemid, short quantity) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.gainItem(itemid, quantity);
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(2));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }

    public static final void giveHotTime(IoSession session, int itemid, short quantity) {
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters().values()) {
                chr.gainItem(itemid, quantity);
                chr.dropMessage(1, "Hot Time Item has been paid.");
            }
        }
        session.write(AdminToolPacket.Message(9));
    }

    public static final void banned(IoSession session, String charName) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.ban("I was beaten by the server.", true, true);
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(6));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }

    public static final void giveMeso(IoSession session, String charName, long meso) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.gainMeso(meso, true);
                chr.dropMessage(1, "From the operator " + meso + "I got a meso.");
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(4));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }

    public static final void giveRC(IoSession session, String charName, int rc) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.gainRC(rc);
                chr.dropMessage(1, "From the operator " + rc + "I received a Donation point.");
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(3));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }

    public static final void chatBlock(IoSession session, boolean block) {
        WorldCommunity.isFreeze = block;
        if (WorldCommunity.isFreeze) {
            session.write(AdminToolPacket.Message(7));
        } else {
            session.write(AdminToolPacket.Message(8));
        }
    }

    public static final void userChatBlock(IoSession session, String charName, boolean block) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.setChatban(String.valueOf(block));
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(Boolean.parseBoolean(chr.getChatban()) ? 13 : 14));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }

    public static final void sendNotice(IoSession session, int type, String message) {
        byte[] packet = null;
        switch (type) {
            case 0:
                packet = MainPacketCreator.serverNotice(1, message);
                break;
            case 1:
                packet = MainPacketCreator.serverNotice(5, message);
                break;
            case 2:
                packet = UIPacket.showInfo(message);
                break;
            case 3:
                packet = MainPacketCreator.getNPCTalk(2007, (byte) 0, message, "00 00", (byte) 0);
                break;
        }
        if (packet != null) {
            WorldBroadcasting.broadcastMessage(packet);
        }
        session.write(AdminToolPacket.Message(10));
    }

    public static final void WarpLoginServer(IoSession session) {
        ServerConstants.isShutdown = true;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            cs.getPlayerStorage().disconnectAll();
        }
        session.write(AdminToolPacket.Message(5));
    }

    public static final void setGM(IoSession session, String charName) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.setGMLevel((byte) 2);
                chr.dropMessage(1, "GM으로 설정 되었습니다.");
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(11));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }

    public static final void discon(IoSession session, String charName) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterByName(charName);
            if (chr != null) {
                chr.getClient().getSession().close();
                chr.getClient().disconnect(true, false);
                break;
            }
        }
        if (chr != null) {
            session.write(AdminToolPacket.Message(15));
        } else {
            session.write(AdminToolPacket.Message(12));
        }
    }
}
