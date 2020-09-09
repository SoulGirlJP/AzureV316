package connections.Packets;

import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;

public class EffectPacket {

    public static byte[] ShowWZEffect(String data) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        pw.write(26); // updated.
        pw.write(0); // bool
        pw.writeInt(0); // bUpgrade
        pw.writeInt(4); // nRet
        pw.writeMapleAsciiString(data);
        return pw.getPacket();
    }

    public static byte[] ShowWZEffect(String data, byte b) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        pw.write(b); // updated.
        pw.writeMapleAsciiString(data);
        pw.write(0); // bool
        pw.writeInt(0); // bUpgrade
        pw.writeInt(4); // nRet

        return pw.getPacket();
    }
}
