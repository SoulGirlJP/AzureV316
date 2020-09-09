package connections.Packets;

import client.Character.MapleCharacter;
import client.Stats.BuffStats;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;

public class SoulWeaponPacket {

    public static byte[] giveSoulGauge(int count, int skillid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        PacketProvider.writeSingleMask(packet, BuffStats.SoulMP);
        packet.writeShort(count);
        packet.writeInt(skillid);
        packet.writeInt(0);
        packet.writeInt(1000);
        packet.writeInt(skillid);
        packet.write(0); // nDefenseAtt
        packet.write(0); // nDefenseState
        packet.write(0); // nPVPDamage
        packet.writeInt(0);
        packet.write0(18);

        return packet.getPacket();
    }

    public static byte[] cancelSoulGauge() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_BUFF.getValue());
        packet.writeInt(0);
        PacketProvider.writeSingleMask(packet, BuffStats.SoulMP);
        packet.writeInt(0);

        return packet.getPacket();
    }

    public static byte[] showEnchanterEffect(int cid, byte result) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_ENCHANTER_EFFECT.getValue());
        packet.writeInt(cid);
        packet.write(result);

        return packet.getPacket();
    }

    public static byte[] showSoulScrollEffect(int cid, byte result, boolean destroyed) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_SOULSCROLL_EFFECT.getValue());
        packet.writeInt(cid);
        packet.write(result);
        packet.write(destroyed ? 1 : 0);

        return packet.getPacket();
    }

    public static byte[] showSoulEffect(MapleCharacter chr, byte use) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_SOULEFFECT_RESPONESE.getValue());
        packet.writeInt(chr.getId());
        packet.write(use);

        return packet.getPacket();
    }
}
