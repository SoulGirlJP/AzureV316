package connections.Packets;

import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;

public class SLFCGPacket {

    public static byte[] FrozenLinkMobCount(int count) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(405);
        w.write(1);
        w.writeInt(count);
        w.writeInt(0);
        return w.getPacket();
    }

    public static byte[] WeatherAddPacket(int type) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(464);
        w.writeInt(type);
        return w.getPacket();
    }

    public static byte[] WeatherRemovePacket(int type) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(465);
        w.writeInt(type);
        return w.getPacket();
    }

    public static byte[] CharReLocationPacket(int x, int y) { 
        final WritingPacket w = new WritingPacket();
        w.writeShort(858); // 316
        w.writeInt(x); 
        w.writeInt(y);
        return w.getPacket();
    }

    public static byte[] BlockGameCommandPacket(int command) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(1450);
        w.writeInt(command);
        return w.getPacket();
    }

    public static byte[] BlockGameControlPacket(int velocity, int misplaceallowance) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(1449);
        w.write0(12);
        w.writeInt(1);
        w.writeInt(0);
        w.writeInt(velocity); // Speed, but I don't know what speed
        w.writeInt(misplaceallowance); // I think it means an allowance, but I don't know this either
        w.write0(25);
        return w.getPacket();
    }

    public static byte[] MesoChairPacket(int charid, int meso, int chairid) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(538);
        w.writeInt(charid);
        w.writeInt(chairid);
        w.writeLong((long) meso);
        w.writeLong((long) meso);
        return w.getPacket();
    }

    public static byte[] TowerChairMessage(String chairs) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        packet.write(0x0E);
        packet.writeInt(7266);
        packet.writeMapleAsciiString(chairs);
        return packet.getPacket();
    }

    public static byte[] TowerChairSaveDone() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(354);
        return packet.getPacket();
    }
}
