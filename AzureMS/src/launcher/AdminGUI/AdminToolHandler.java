/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher.AdminGUI;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import constants.ServerConstants;
import connections.Packets.PacketUtility.ByteStream;
import connections.Packets.PacketUtility.ReadingMaple;


/**
 *
 * @author SoulGirlJP#7859
 * 
 **/

public class AdminToolHandler extends IoHandlerAdapter {
    @Override
    public void sessionOpened(IoSession session) {
        AdminTool.session.add(session);
        session.write(AdminToolPacket.Info());
    }

    @Override
    public void sessionClosed(IoSession session) {
        AdminTool.session.remove(session);
    }

    @Override

    public void exceptionCaught(IoSession session, Throwable cause) {

    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        final ByteBuffer packet = (ByteBuffer) message;
        final int length = packet.limit();
        final ReadingMaple read = new ReadingMaple(new ByteStream(packet.array()));
        final String data = Decrypt(read.readAsciiString(length));
        final short header = Short.parseShort(decode("Header", data));
        handlePacket(session, header, data);
    }

    public void handlePacket(final IoSession session, final short header, final String data) {
        final String address = session.getRemoteAddress().toString().split(":")[0];

        switch (header) {
            case 0: {
                int exp = Integer.parseInt(decode("Exp", data));
                int drop = Integer.parseInt(decode("Drop", data));
                int meso = Integer.parseInt(decode("Meso", data));
                String serverMessage = decode("ServerMessage", data);
                AdminToolClientHandler.updateServerInfo(session, exp, drop, meso, serverMessage);
                break;
            }
            case 1: {
                String charName = decode("CharName", data);
                int itemid = Integer.parseInt(decode("ItemId", data));
                short quantity = Short.parseShort(decode("Quantity", data));
                AdminToolClientHandler.giveItem(session, charName, itemid, quantity);
                break;
            }
            case 2: {
                int itemid = Integer.parseInt(decode("ItemId", data));
                short quantity = Short.parseShort(decode("Quantity", data));
                AdminToolClientHandler.giveHotTime(session, itemid, quantity);
                break;
            }
            case 3: {
                String charName = decode("CharName", data);
                AdminToolClientHandler.banned(session, charName);
                break;
            }
            case 4: {
                String charName = decode("CharName", data);
                long meso = Long.parseLong(decode("Meso", data));
                AdminToolClientHandler.giveMeso(session, charName, meso);
                break;
            }
            case 5: {
                String charName = decode("CharName", data);
                int rc = Integer.parseInt(decode("RC", data));
                AdminToolClientHandler.giveRC(session, charName, rc);
                break;
            }
            case 6: {
                String charName = decode("CharName", data);
                boolean banned = Boolean.parseBoolean(decode("Banned", data));
                AdminToolClientHandler.userChatBlock(session, charName, banned);
                break;
            }
            case 7: {
                boolean Block = Boolean.parseBoolean(decode("Block", data));
                AdminToolClientHandler.chatBlock(session, Block);
                break;
            }
            case 8: {
                int type = Integer.parseInt(decode("Type", data));
                String message = decode("Message", data);
                AdminToolClientHandler.sendNotice(session, type, message);
                break;
            }
            case 9: {
                AdminToolClientHandler.WarpLoginServer(session);
                break;
            }
            case 10: {
                String charName = decode("CharName", data);
                AdminToolClientHandler.setGM(session, charName);
                break;
            }
            case 11: {
                String charName = decode("CharName", data);
                AdminToolClientHandler.discon(session, charName);
                break;
            }
        }
    }

    public static String Reverse(String s) {
        return new StringBuffer(s).reverse().toString();
    }

    public static String Decrypt(String strInput) {
        StringBuilder sb = new StringBuilder();
        String input = Reverse(strInput);

        for (char ch : input.toCharArray()) {
            sb.append((char) (ch - 4));
        }

        return sb.toString();
    }

    public String decode(final String header, final String data) {
        return data.split(header + "=")[1].split(";")[0];
    }
}
