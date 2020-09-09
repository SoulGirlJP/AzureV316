package connections.Packets;

import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;

public class AntiHackPacket {

    public static byte[] sendProcessRequest() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PROCESS_CHECK.getValue());
        packet.write(1);

        return packet.getPacket();
    }
}
