/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher.AdminGUI;

import org.apache.mina.common.ByteBuffer;
import connections.Packets.PacketUtility.WritingPacket;
import client.Character.MapleCharacter;
import launcher.ServerPortInitialize.ChannelServer;

/**
 *
 * @author SoulGirlJP#7859
 * 
 **/
public class AdminToolPacket {
     public static ByteBuffer Info() {
        String data = "";
        data += enp("Header", 0);
        data += enp("Exp", ChannelServer.getInstance(0).getExpRate());
        data += enp("Drop", ChannelServer.getInstance(0).getDropRate());
        data += enp("Meso", ChannelServer.getInstance(0).getMesoRate());
        data += enp("ServerTopMessage", ChannelServer.getInstance(0).getServerMessage());
        String OnlineCharacter = "";
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter name : cserv.getPlayerStorage().getAllCharacters().values()) {
                OnlineCharacter += name.getName() + ", ";
            }
        }
        data += enp("OnlineCharacter", OnlineCharacter);
        WritingPacket packet = new WritingPacket();
        packet.writeAsciiString(Encrypt(data));
        return getPacket(packet.getPacket());
    }

    public static ByteBuffer Message(int type) {
        String data = "";
        data += enp("Header", 1);
        data += enp("Message", type);
        WritingPacket packet = new WritingPacket();
        packet.writeAsciiString(Encrypt(data));
        return getPacket(packet.getPacket());
    }

    public static ByteBuffer sendChatText(String txt) {
        String data = "";
        data += enp("Header", 2);
        data += enp("Message", txt);
        WritingPacket packet = new WritingPacket();
        packet.writeAsciiString(Encrypt(data));
        return getPacket(packet.getPacket());
    }

    private static String enp(final String data, final Object value) {
        return data + "=" + value + ";";
    }

    private static ByteBuffer getPacket(final byte[] packet) {
        return ByteBuffer.wrap(packet);
    }

    private static String Reverse(String s) {
        return new StringBuffer(s).reverse().toString();
    }

    private static String Encrypt(String strInput) {
        StringBuilder sb = new StringBuilder();
        String input = Reverse(strInput);

        for (char ch : input.toCharArray()) {
            sb.append((char) (ch + 4));
        }

        return sb.toString();
    }
    
}
