package handlers;

import client.MapleClient;
import connections.Packets.PacketUtility.ReadingMaple;

public class SocketHandler {

    public static void handleClientExceptionInfo(ReadingMaple iPacket, MapleClient c) {
        String sFileName = iPacket.readMapleAsciiString();
        int nLine = iPacket.readInt();

        System.out.println("[ERROR] Client Exception : FILE " + sFileName + ", LINE " + nLine);
    }
}
