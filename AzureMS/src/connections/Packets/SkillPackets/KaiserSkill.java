package connections.Packets.SkillPackets;

import client.Character.MapleCharacter;
import java.util.List;

import client.Stats.BuffStats;
import connections.Packets.PacketProvider;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import tools.HexTool;
import tools.RandomStream.Randomizer;

public class KaiserSkill {

    /**
     * 카이저 모프게이지 버프패킷.
     *
     * @param gauge
     */
    public static byte[] giveMorphGauge(int gauge) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        PacketProvider.writeSingleMask(packet, BuffStats.SmashStack);
        if (gauge == -1000) {
            packet.writeShort(0);
        } else {
            packet.writeShort(Math.min(gauge, 700));
        }
        packet.write0(40); // 1.2.239+

        return packet.getPacket();
    }

    /**
     * 윌 오브 소드/어드밴스드 윌 오브 소드.
     *
     * @param characterid - 캐릭터 아이디
     * @param swordSize - 소드 사이즈, ex) 2th - 3, 4th - 5
     * @param swordCount - 랜덤값
     * @param skillid - 스킬코드
     * @return
     */
    public static byte[] absorbingSwordCount(MapleCharacter chr, List<Integer> oids, int skillid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        packet.write(0);
        packet.writeInt(chr.getId());
        packet.writeInt(2);
        packet.write(1);
        packet.writeInt(oids.size());
        for (Integer oid : oids) {
            packet.writeInt(oid.intValue());
        }
        
        packet.writeInt(skillid);
        boolean advanced = (skillid == 61120007) || (skillid == 61121217) || (skillid == 400011058) || (skillid == 400011059);
        boolean transform = chr.isFinalFiguration;
        for (int i = 0; i < (advanced ? 5 : 3); i++) {
          packet.write(1);
          packet.writeInt(i + 2);
          packet.writeInt(transform ? 4 : 2);
          packet.writeInt(Randomizer.rand(15, 18));
          packet.writeInt(Randomizer.rand(26, 31));
          packet.writeInt(0);
          packet.writeInt(Randomizer.rand(1000, 1500));
          packet.writeInt(0);
          packet.writeInt(0);
          packet.write(HexTool.getByteArrayFromHexString("CF C7 29 D5"));
          packet.writeInt(0);
          packet.writeInt(0);
          packet.writeInt(0);
        }
        packet.write(0);
        return packet.getPacket();
    }

    public static byte[] ItemSkillFromButton() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.REBUTTON_FORM_ITEM_SKILL.getValue());
        packet.writeLong(9);
        packet.writeLong(Randomizer.nextLong());
        packet.writeLong(Randomizer.nextLong());

        return packet.getPacket();
    }
}
