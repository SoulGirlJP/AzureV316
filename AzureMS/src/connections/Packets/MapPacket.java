package connections.Packets;

import connections.Packets.PacketUtility.WritingPacket;

public class MapPacket {

    public static byte[] getT1() {
        WritingPacket p = new WritingPacket();
        p.writeShort(0x1C9);
        p.writeInt(0);
        p.writeShort(0);
        return p.getPacket();
    }

    public static byte[] getT2() {
        WritingPacket p = new WritingPacket();
        p.writeShort(0x1CA);
        p.writeInt(0);
        p.writeShort(0);
        p.write(0);
        return p.getPacket();
    }

    public static byte[] getT3() {
        WritingPacket p = new WritingPacket();
        p.writeShort(0x1CB);
        p.write(0);
        return p.getPacket();
    }

    public static byte[] getT4(int cid) {
        WritingPacket p = new WritingPacket();
        p.writeShort(0x211);
        p.writeInt(cid);
        p.writeLong(0);
        return p.getPacket();
    }

    public static byte[] getT5(int cid) {
        WritingPacket p = new WritingPacket();
        p.writeShort(0x1D7);
        p.writeInt(cid);
        p.write(1);
        p.write(1);
        p.writeInt(0);
        return p.getPacket();
    }

    public static byte[] getT6() {
        WritingPacket p = new WritingPacket();
        p.writeShort(0x7B);
        p.writeShort(4);
        return p.getPacket();
    }
}
