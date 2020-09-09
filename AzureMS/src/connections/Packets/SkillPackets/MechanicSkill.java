package connections.Packets.SkillPackets;

import java.awt.Point;
import java.util.List;

import client.Stats.BuffStats;
import handlers.GlobalHandler.MapleMechDoor;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import connections.Packets.PacketProvider;
import tools.HexTool;
import tools.Triple;

public class MechanicSkill {

    /**
     * 메탈아머 : 휴먼
     *
     * @param statups
     * @param skillid
     * @param bufflength
     * @param mountid
     * @param smountid
     * @return - 패킷값을 보냄.
     */
    public static byte[] giveHuman(List<Triple<BuffStats, Integer, Boolean>> statups, int skillid, int bufflength,
            int mountid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        PacketProvider.writeBuffMask(packet, statups);
        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            if (!statup.first.equals(BuffStats.MonsterRiding) && !statup.third) {
                packet.writeInt(statup.getSecond().shortValue());
                packet.writeInt(skillid);
                packet.writeInt(bufflength);
            }
        }

        packet.writeLong(0);
        packet.writeInt(0);
        packet.write(0);

        packet.writeInt(mountid);
        packet.writeInt(skillid);
        packet.writeInt(0);

        packet.write(0);

        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            if (statup.third) {
                packet.writeInt(1);
                packet.writeInt(skillid);
                packet.writeInt(statup.second);
                packet.writeInt(236440026);
                packet.writeInt(236440026);
                packet.writeInt(0);
                packet.writeInt(0);
                packet.writeInt(0);
            }
        }
        packet.writeInt(0);
        packet.write(1);
        packet.write(5);
        packet.writeInt(0);
        packet.writeLong(0);
        packet.writeLong(0);
        return packet.getPacket();
    }

    public static byte[] giveTank(int skillid, int bufflength, int mountid, int smountid) {
        WritingPacket packet = new WritingPacket();
        int statup = 0;
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0x4000000);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0x1D8010);
        packet.writeInt(0);
        packet.writeInt(0x400000);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0x4000000);
        for (int i = 0; i < 7; i++) {
            if ((i == 0) || (i == 1)) {
                statup = 2100;
            } else if ((i == 2)) {
                statup = 0x37;
            } else if (i == 5) {
                statup = 0x01;
            } else if (i == 6) {
                statup = 0x32;
            } else {
                statup = 600;
            }
            packet.writeInt(statup);
            packet.writeInt(skillid);
            packet.writeInt((i == 5) ? 0 : bufflength);
        }
        packet.write0(9);
        packet.writeInt(mountid);
        packet.writeInt(skillid);
        packet.writeInt(smountid);
        packet.write(0);
        packet.writeInt(1);
        packet.writeInt(skillid);
        packet.writeInt(30);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.write(1);
        packet.write(0x9);
        packet.writeInt(0);

        return packet.getPacket();
    }

    /**
     * 메탈아머 : 휴먼 캔슬모션
     *
     * @param skillId - 패킷값 구분.
     * @return - 패킷값을 보냄.
     */
    public static byte[] cancelHuman() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_BUFF.getValue());
        packet.writeInt(0);
        packet.write(PacketProvider.convertFromBigInteger(BuffStats.MonsterRiding.getBigValue(), BuffStats.BIT_COUNT));
        packet.write0(20);
        packet.write(0x7);
        packet.write(0x1);

        return packet.getPacket();
    }

    /**
     * 메탈아머 : 탱크 캔슬모션
     *
     * @param skillId - 패킷값 구분.
     * @return - 패킷값을 보냄.
     */
    public static byte[] cancelTank() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_BUFF.getValue());
        packet.writeInt(0);
        packet.write(PacketProvider.convertFromBigInteger(BuffStats.MonsterRiding.getBigValue(), BuffStats.BIT_COUNT));
        packet.write0(12);
        packet.write(0x25);
        packet.write(0x01);

        return packet.getPacket();
    }

    /**
     * 메탈아머 : 휴먼, 탱크 공통 스탯
     *
     * @param skillId - 패킷값 구분.
     * @return - 패킷값을 보냄.
     */
    public static byte[] giveMetalStats(int skillid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0x5000020);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0x4);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeShort(0); // TODO : Color
        packet.writeInt(30000227);
        packet.write0(13);
        for (int i = 0; i < 4; i++) {
            packet.writeInt((i == 0) ? 2 : (i == 1) ? 0 : 1);
            packet.writeInt((i == 1) ? skillid : 30000227);
            packet.writeInt((i == 1) ? 30 : 10);
            packet.write(HexTool.getByteArrayFromHexString("38 3B D1 37 38 3B D1 37"));
            packet.writeInt((i == 1) && (skillid == 35111003) ? 1 : 0);
            if ((i != 0)) {
                packet.writeInt(0);
            }
        }
        packet.writeInt(0);
        packet.write(0x1);
        packet.writeInt(0);

        return packet.getPacket();
    }

    /**
     * 메카닉 오픈게이트 소환 패킷
     *
     * @param door - 메카닉의 오픈게이트 클래스를 불러옴.
     * @param active - 작동하는지 안하는지 여부를 물음.
     * @return - 패킷값을 보냄.
     */
    public static byte[] mechDoorSpawn(MapleMechDoor door, boolean active) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MECH_DOOR_SPAWN.getValue());
        packet.write(active ? 0 : 1);
        packet.writeInt(door.getOwnerId());
        packet.writePos(door.getTruePosition());
        packet.write(door.getId());
        if (door.getPartyId() > 0) {
            packet.writeInt(door.getPartyId());
        }
        return packet.getPacket();
    }

    public static byte[] OnOpenGateClose(int cid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MECH_DOOR_SPAWN.getValue() + 1);
        packet.writeInt(cid);
        return packet.getPacket();
    }

    /**
     * 메카닉 오픈게이트 취소 패킷
     *
     * @param door - 메카닉의 오픈게이트 클래스를 불러옴.
     * @param active - 작동하는지 안하는지 여부를 물음.
     * @return - 패킷값을 보냄.
     */
    public static byte[] mechDoorRemove(MapleMechDoor door, boolean active) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MECH_DOOR_REMOVE.getValue());
        packet.write(active ? 0 : 1);
        packet.writeInt(door.getOwnerId());
        packet.write(door.getId());

        return packet.getPacket();
    }

    /**
     * 메카닉 포탈구현패킷
     *
     * @param pos - 현재 플레이어의 위치.
     * @return - 패킷값을 보냄.
     */
    public static byte[] mechPortal(Point pos) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SPAWN_PORTAL.getValue());
        packet.writePos(pos);

        return packet.getPacket();
    }

    /**
     * 메카닉 위장색
     *
     */
    public static byte[] MechanicMetalArmorCamouflage(int id, int time) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        // PacketProvider.writeSingleMask(packet, BuffStats.MECHANIC_CAMOUFLAGE);
        packet.writeShort(id);
        packet.writeInt(30000227);
        packet.writeInt(time);
        packet.write0(18);

        return packet.getPacket();
    }
}
