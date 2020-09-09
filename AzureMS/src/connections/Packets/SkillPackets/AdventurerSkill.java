package connections.Packets.SkillPackets;

import client.Stats.BuffStats;
import connections.Packets.PacketProvider;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;

public class AdventurerSkill {

    public static byte[] giveBeholderDominant(int buffid1, int buffid2, int buffid3) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        PacketProvider.writeSingleMask(packet, BuffStats.Beholder);
        packet.writeShort(1);
        packet.writeInt(buffid1);
        packet.writeInt(344117);
        packet.writeInt(0);
        packet.write(4);
        packet.writeInt(buffid2);
        if (buffid3 == 1311014) {
            packet.writeInt(1311014);
            packet.writeInt(0);
        }
        packet.write0(12);
        packet.writeShort(1);
        packet.write0(3);

        return packet.getPacket();
    }

    public static byte[] CancelHeholderBuff() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_BUFF.getValue());
        packet.writeInt(0);
        PacketProvider.writeSingleMask(packet, BuffStats.Beholder);

        return packet.getPacket();
    }
}
