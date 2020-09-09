package connections.Packets.SkillPackets;

import client.Character.MapleCharacter;
import connections.Packets.PacketProvider;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import tools.RandomStream.Randomizer;

public class AngelicBusterSkill {

    public static byte[] AngelicBusterChangingWait(final byte type, final boolean called) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        packet.write(type);
        packet.writeInt(!called ? 0x1B1E : 0xAACE);
        packet.write(2);
        packet.writeLong(PacketProvider.getTime(System.currentTimeMillis()));

        return packet.getPacket();
    }

    public static byte[] DressUpTime(byte type, int id) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        packet.write(1);
        packet.writeInt(id);
        packet.write(3);
        packet.writeLong(PacketProvider.getTime(System.currentTimeMillis()));
        return packet.getPacket();
    }

    public static byte[] updateDress(int code, MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.UPDATE_DRESS.getValue());
        packet.writeInt(chr.getId());
        packet.writeInt(code);

        return packet.getPacket();
    }

    public static byte[] showRechargeEffect() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        packet.write(0x31);

        return packet.getPacket();
    }

    public static byte[] lockSkill(int skillid) {
        WritingPacket packet = new WritingPacket(6);
        packet.writeShort(SendPacketOpcode.LOCK_SKILL.getValue());
        packet.writeInt(skillid);

        return packet.getPacket();
    }

    public static byte[] unlockSkill() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.UNLOCK_SKILL.getValue());

        return packet.getPacket();
    }

    public static byte[] keydownStingExplosion(int skillid, int random, int length) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.STING_EXPLOSION.getValue());
        packet.writeInt(skillid);
        packet.writeInt(random);
        packet.writeInt(random);
        packet.writeInt(length);
        packet.writeInt(0);

        return packet.getPacket();
    }

    public static byte[] keydownStingExplosionEffect(int skillid, int random, int skilllvl) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        packet.write(9); // 1.2.251+ (+2)
        packet.writeInt(skillid);
        packet.writeInt(random);
        packet.writeInt(random);
        packet.writeInt(skilllvl);

        return packet.getPacket();
    }

    public static byte[] SoulSeeker(MapleCharacter chr, int skillid, int sn, int sc1, int sc2) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        packet.write(0);
        packet.writeInt(chr.getId());
        packet.writeInt(3);
        packet.write(1);
        packet.writeInt(sn);
        packet.writeInt(sc1);
        if (sn > 1) {
            packet.writeInt(sc2);
        }
        packet.writeInt(skillid); // hide skills
        for (int i = 0; i < 2; i++) {
            packet.write(1);
            packet.writeInt(i + 2);
            packet.writeInt(1);
            packet.writeInt(Randomizer.rand(17, 19));
            packet.writeInt(Randomizer.rand(32, 34));
            packet.writeInt(Randomizer.rand(32, 45));
            packet.writeInt(705);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(Randomizer.nextInt());
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        packet.write(0);
        return packet.getPacket();
    }

    public static byte[] SoulSeekerRegen(MapleCharacter chr, int sn) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        packet.write(1);
        packet.writeInt(chr.getId());
        packet.writeInt(sn);
        packet.writeInt(4);
        packet.write(1);
        packet.writeInt(sn);
        packet.writeInt(65111007);
        packet.write(1);
        packet.writeInt(Randomizer.rand(3, 5));
        packet.writeInt(1);
        packet.writeInt(Randomizer.rand(40, 43));
        packet.writeInt(Randomizer.rand(3, 4));
        packet.writeInt(Randomizer.rand(304, 310));
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(Randomizer.nextInt());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.write(0);
        packet.writeInt(0);
        packet.writeInt(0);

        return packet.getPacket();
    }
}
