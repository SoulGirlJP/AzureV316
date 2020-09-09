package connections.Packets.SkillPackets;

import client.MapleClient;
import client.Skills.SkillFactory;
import java.awt.Rectangle;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PacketUtility.WritingPacket;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;

public class KinesisSkill {

    public static void PsychicUnknown(ReadingMaple rh, final MapleClient c) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PSYCHIC_UNKNOWN.getValue());
        packet.writeInt(c.getPlayer().getId());
        packet.writeInt(rh.readInt());

        c.getSession().writeAndFlush(packet.getPacket());
    }


    public static byte[] OnReleasePsychicLock(int cid, int oid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_PSYCHIC_GREP.getValue());
        packet.writeInt(cid);
        packet.writeInt(oid);
        return packet.getPacket();
    }

    public static void CancelPsychicGrep(ReadingMaple rh, final MapleClient c) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_PSYCHIC_GREP.getValue());
        int skillid = rh.readInt();
        rh.skip(4);
        packet.writeInt(c.getPlayer().getId());
        packet.writeInt(rh.readInt());
        c.getPlayer().givePPoint(SkillFactory.getSkill(skillid).getEffect(1), false);
        c.getPlayer().getMap().broadcastMessage(packet.getPacket());
    }

    public static void PsychicGrep(ReadingMaple rh, final MapleClient c) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PSYCHIC_GREP.getValue());
        /* First AttackInfo Start */
        packet.writeInt(c.getPlayer().getId());
        packet.write(1);
        final int skillid = rh.readInt();
        packet.writeInt(skillid);
        packet.writeShort(rh.readShort());
        packet.writeInt(rh.readInt());
        packet.writeInt(rh.readInt());
        /* First AttackInfo End */
        int i = 0;
        boolean end = false;
        MapleMonster target = null;
        while (true) {
            end = (rh.readByte() <= 0);
            packet.write(!end ? 1 : 0);
            if (!end) {
                packet.write(!end ? 1 : 0);
                packet.writeInt(rh.readInt());
            } else {
                break; 
            }
            rh.skip(4);
            packet.writeInt(i + 1);
            final int monsterid = rh.readInt();
            packet.writeInt(monsterid); // Monster ID.
            packet.writeShort(rh.readShort());
            if (monsterid != 0) {
                target = c.getPlayer().getMap().getMonsterByOid(monsterid);
            }
            int unk = rh.readInt();
            packet.writeInt(unk);
            rh.skip(2);
            packet.writeLong(monsterid != 0 ? (int) target.getHp() : 100);
            packet.writeLong(monsterid != 0 ? (int) target.getHp() : 100);
            packet.write(rh.readByte());
            packet.writeNRect(new Rectangle(rh.readInt(), rh.readInt(), rh.readInt(), rh.readInt()));
            i++;
        }

        c.getPlayer().getMap().broadcastMessage(packet.getPacket());
        c.getPlayer().givePPoint(SkillFactory.getSkill(skillid).getEffect(1), false);
    }
}
