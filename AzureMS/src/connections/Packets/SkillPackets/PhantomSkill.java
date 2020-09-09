package connections.Packets.SkillPackets;

import java.util.ArrayList;
import java.util.List;

import client.Character.MapleCharacter;
import client.Skills.ISkill;
import client.Skills.StealSkillEntry;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;

public class PhantomSkill {

    public static byte[] debug() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.STEEL_SKILL.getValue());
        packet.write(0);
        packet.write(2);
        return packet.getPacket();
    }

    public static byte[] getSteelSkillCheck(int oid, boolean success, StealSkillEntry sse, boolean delete) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.STEEL_SKILL.getValue());
        packet.write(1);

        /*
		 * 0 : 등록 1 : 실패 2 : ? 3 : 삭제
         */
        if (!success) {
            packet.write(1);
        } else {
            packet.write(delete ? 3 : 0);
            if (delete) {
                packet.writeInt(StealSkillEntry.getJobIndex(sse.getSkillId()));
                packet.writeInt(sse.getSlot() - 1);
            } else {
                packet.writeInt(StealSkillEntry.getJobIndex(sse.getSkillId()));
                packet.writeInt(sse.getSlot() - 1);
                packet.writeInt(sse.getSkillId());
                packet.writeInt(sse.getSkillLevel());
                packet.writeInt(0); // 마스터레벨?
            }
        }

        return packet.getPacket();
    }

    public static byte[] getSteelAvailableSkills(MapleCharacter hp, boolean found) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.STEEL_SKILL_CHECK.getValue());
        packet.write(1);
        packet.writeInt(hp.getObjectId());
        if (found) {
            packet.writeInt(4);
            packet.writeInt(hp.getJob());
            List<Integer> skills = new ArrayList<Integer>();
            for (ISkill skill : hp.getSkills().keySet()) {
                if (skill.getEffect(1).getSkillStats().getStats("mpCon") != 0) {
                    skills.add(skill.getId());
                }
            }
            packet.writeInt(skills.size());
            for (Integer i : skills) {
                packet.writeInt(i.intValue());
            }
        } else {
            packet.writeInt(1);
        }

        return packet.getPacket();
    }

    public static byte[] getUpdateEquippedSkill(int baseSkillId, int skillId, int index, boolean equipped) {
        return getUpdateEquippedSkill(baseSkillId, skillId, index, equipped, true);
    }

    public static byte[] getUpdateEquippedSkill(int baseSkillId, int skillId, int index, boolean equipped, boolean v1) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.EQUIPPED_SKILL.getValue());
        packet.write(v1 ? 1 : 0);
        packet.write(equipped ? 1 : 0);
        packet.writeInt(baseSkillId);
        packet.writeInt(skillId);
        //System.out.println(packet);
        return packet.getPacket();
    }
}
