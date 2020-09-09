package connections.Packets.SkillPackets;

import client.Character.MapleCharacter;
import connections.Packets.PacketProvider;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import tools.HexTool;

public class ZeroSkill {

    public static byte[] ScrollStart() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_SCROLL_START.getValue());

        return packet.getPacket();
    }

    public static byte[] Scroll(int scroll) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_SCROLL.getValue());
        packet.writeShort(1);
        packet.write(0);
        packet.writeInt(scroll);

        return packet.getPacket();
    }

    public static byte[] Open(int type) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_WEAPON.getValue());
        packet.writeInt(type);
        packet.writeInt((type == 1) ? 100000 : 50000);
        packet.writeInt((type == 1) ? 600 : 500);
        packet.writeShort(0);

        return packet.getPacket();
    }

    public static byte[] WeaponInfo(int type, int level, int action, int weapon) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_WEAPONINFO.getValue());
        packet.write(0);
        packet.write(action);
        packet.writeInt(type);
        packet.writeInt(level);
        packet.writeInt(weapon + 10001);
        packet.writeInt(weapon + 1);

        return packet.getPacket();
    }

    public static byte[] WeaponUpgradeSuccess() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_UPGRADE_SU.getValue());
        packet.writeShort(1);

        return packet.getPacket();
    }

    public static byte[] WeaponLevelUp() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_UPGRADE.getValue());
        packet.write(0);

        return packet.getPacket();
    }

    public static byte[] NPCTalk(String txt) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.NPC_TALK.getValue());
        packet.write(3);
        packet.writeInt(0);
        packet.writeShort(0);
        packet.writeShort(0x24);
        packet.write(0);
        packet.writeInt(2400010); // 2400009 man, 2400010 woman
        packet.writeMapleAsciiString(txt);
        packet.write(HexTool.getByteArrayFromHexString("00 01"));
        packet.writeInt(0); // 1.2.239+

        return packet.getPacket();
    }

    public static byte[] ZeroTag(MapleCharacter chr, byte Gender) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_TAG.getValue());
        packet.writeShort(0xC7);
        packet.write(Gender);
        packet.writeInt(chr.getStat().getHp());
        packet.writeInt(chr.getStat().getMp() > 100 ? 100 : chr.getStat().getMp());
        packet.writeInt(chr.getStat().getMaxHp());
        packet.writeInt(chr.getStat().getMaxMp() > 100 ? 100 : chr.getStat().getMaxMp());

        return packet.getPacket();
    }

    public static byte[] TagTip(MapleCharacter chr, int type) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.UPDATE_STATS.getValue());
        packet.write(0);
        packet.write(0);
        packet.writeInt(0x4000);
        packet.writeInt(chr.getStat().getMaxHp());
        packet.write(1);
        packet.writeShort(type);

        return packet.getPacket();
    }

    public static byte[] MultiTag(MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_MUITTAG.getValue());
        packet.writeInt(chr.getId());
        PacketProvider.addPlayerLooks(packet, chr, false, chr.getGender() == 1);
        return packet.getPacket();
    }

    public static byte[] OnZeroLastAssistState(MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(714);
        packet.writeInt(chr.getId());
        return packet.getPacket();
    }

    public static byte[] Clothes(int value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_TAG.getValue());
        packet.write(0);
        packet.write(1);
        packet.writeInt(value);

        return packet.getPacket();
    }

    public static byte[] Reaction() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MODIFY_INVENTORY_ITEM.getValue());
        packet.write(0);
        packet.writeShort(0);

        packet.write(0);
        return packet.getPacket();
    }

    public static byte[] shockWave(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ZERO_SHOCKWAVE.getValue());
        packet.writeInt(paramInt1);
        packet.writeInt(paramInt2);
        packet.writeInt(paramInt3);
        packet.writeInt(paramInt4);

        return packet.getPacket();
    }
}
