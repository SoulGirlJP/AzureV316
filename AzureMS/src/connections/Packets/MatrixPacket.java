package connections.Packets;

import client.Character.MapleCharacter;
import java.awt.Point;
import java.util.List;

import client.MapleClient;
import client.Skills.MatrixSkill;
import client.Skills.VCore;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import handlers.GlobalHandler.MatrixHandler;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import tools.HexTool;
import tools.RandomStream.Randomizer;

public class MatrixPacket {

    public static byte[] DotPunisuer(int cid, List<Point> pos) {
        WritingPacket p = new WritingPacket();
        p.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        p.write(0);
        p.writeInt(cid);
        p.writeInt(28);
        p.write(1);
        p.writeInt(pos.size());
        for (int i = 0; i < pos.size(); i++) {
            p.writeInt(0);
        }

        p.writeInt(400021001);
        for (int i = 1; i <= pos.size() + 1; i++) {
            if (i == pos.size() + 1) {
                p.write(0);
            } else {
                p.write(1);
                p.writeInt(i + 1);
                p.writeInt(0);
                p.writeInt(44);
                p.writeInt(4);
                p.writeInt(203);
                p.writeInt(720);
                p.writeInt(pos.get(i - 1).x);
                p.writeInt(pos.get(i - 1).y);
                p.writeInt(231443911);
                p.writeInt(0);
                p.writeInt(0);
                p.writeInt(0);
            }
        }

        p.writeInt(-700);
        p.writeInt(-600);

        p.writeInt(700);
        p.writeInt(600);

        p.writeInt(20);
        return p.getPacket();
    }

    public static byte[] IdleWorm(int cid, List<Point> pos) {
        WritingPacket p = new WritingPacket();
        p.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        p.write(0);
        p.writeInt(cid);
        p.writeInt(34);
        p.write(1);
        p.writeInt(pos.size());
        for (int i = 0; i < pos.size(); i++) {
            p.writeInt(0);
        }

        p.writeInt(400031022);
        for (int i = 1; i <= pos.size() + 1; i++) {
            if (i == pos.size() + 1) {
                p.write(0);
            } else {
                p.write(1);
                p.writeInt(i + 1);
                p.writeInt(0);
                p.writeInt(44);
                p.writeInt(4);
                p.writeInt(203);
                p.writeInt(720);
                p.writeInt(pos.get(i - 1).x);
                p.writeInt(pos.get(i - 1).y);
                p.writeInt(231443911);
                p.writeInt(0);
                p.writeInt(0);
                p.writeInt(0);
            }
        }

        p.writeInt(-700);
        p.writeInt(-600);

        p.writeInt(700);
        p.writeInt(600);

        p.writeInt(20);
        return p.getPacket();
    }

    public static byte[] MatrixSkill(int skillid, int level, List<MatrixSkill> arMatrixSkill) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.DarkSpear.getValue());
        mplew.write(1);
        mplew.writeInt(skillid);
        mplew.writeInt(level);
        mplew.writeInt(arMatrixSkill.size());
        for (MatrixSkill ms : arMatrixSkill) {
            mplew.writeInt(ms.getnAngle());
        }
        return mplew.getPacket();
    }

    public static byte[] MatrixSkillMulti(int nCID, int nSkillID, int nSLV, Point pPos, int nBulletID, List<MatrixSkill> arMatrixSkill) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.MATRIX_SKILL_MULTI.getValue());

        mplew.writeInt(nCID);
        mplew.writeInt(nSkillID);
        mplew.writeInt(nSLV);
        mplew.writeInt(pPos.x);
        mplew.writeInt(pPos.y);
        mplew.writeInt(nBulletID);
        mplew.write(false);

        mplew.writeInt(arMatrixSkill.size());
        for (MatrixSkill ms : arMatrixSkill) {
            mplew.writeInt(ms.getnSkillID());
            mplew.writeInt(ms.getnSLV());
            mplew.writeInt(ms.getnAngle());
            mplew.writeShort(ms.getnUnk1());
            mplew.writePos(ms.getPt());
            mplew.writeInt(ms.getnUnk2());
            mplew.write(ms.isbLeft());
            mplew.write(ms.getPt2() != null);
            if (ms.getPt2() != null) {
                mplew.writeInt(ms.getPt2().x);
                mplew.writeInt(ms.getPt2().y);
            }
        }
        return mplew.getPacket();
    }

    public static void addMatrixInfo(WritingPacket p, List<VCore> cores, MapleClient c) {
        p.writeInt(cores.size());
        int i = 0;
        for (VCore m : cores) {
            p.writeLong(m.getCrcid());
            p.writeInt(m.getCoreId());
            p.writeInt(m.getLevel());
            p.writeInt(m.getExp());
            p.writeInt(m.getState());
            p.writeInt(m.getSkill1());
            p.writeInt(m.getSkill2());
            p.writeInt(m.getSkill3());
            boolean a = false;
            if (m.getState() == 2) {
                for (int k = 0; k < 28; k++) {
                    if (m.getCrcid() == MatrixHandler.getValue(c, "core" + k)) {
                        p.writeInt(k);
                        a = true;
                        break;
                    }
                }
            }
            if (!a) {
                p.writeInt(-1);
            }
            p.writeLong(PacketProvider.getTime(-2));
        }
        p.writeInt(28);
        for (int k = 0; k < 28; k++) {
            i = 0;
            boolean a = false;
            if (MatrixHandler.getValue(c, "core" + k) != -1) {
                for (VCore m : c.getPlayer().cores) {
                    i++;
                    if (m.getCrcid() == MatrixHandler.getValue(c, "core" + k)) {
                        p.writeInt(i - 1);
                        p.writeInt(k);
                        p.writeInt(MatrixHandler.getValue2(c, "upcore" + k));
                        p.write(0);
                        a = true;
                        break;
                    }
                }
            }
            if (!a) {
                p.writeInt(-1);
                p.writeInt(k);
                p.writeInt(MatrixHandler.getValue2(c, "upcore" + k));
                p.write(0);
            }
        }

    }

    public static byte[] CoreEffect() {
        WritingPacket p = new WritingPacket();
        p.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        p.write(8);
        p.write(1);
        p.writeInt(2435719);
        p.writeInt(-1);
        return p.getPacket();
    }

    public static byte[] getCoreq(int q) {
        WritingPacket p = new WritingPacket();
        p.writeShort(395); // 368 -> 395
        p.writeInt(q);
        return p.getPacket();
    }

    public static byte[] getCoreMake(VCore core) {
        WritingPacket p = new WritingPacket();
        p.writeShort(396); // 369 -> 396
        p.writeInt(core.getCoreId());
        p.writeInt(core.getLevel());
        p.writeInt(core.getSkill1());
        p.writeInt(core.getSkill2());
        p.writeInt(core.getSkill3());
        return p.getPacket();
    }

    public static byte[] AddCore(VCore core) {
        WritingPacket p = new WritingPacket();
        p.writeShort(393); // 366 -> 393
        p.writeInt(core.getCoreId());
        p.writeInt(core.getLevel());
        p.writeInt(core.getSkill1());
        p.writeInt(core.getSkill2());
        p.writeInt(core.getSkill3());
        p.writeInt(0);
        return p.getPacket();
    }

    public static byte[] CoreList(final MapleCharacter chr) {
        WritingPacket p = new WritingPacket();
        p.writeShort(392); // 365 -> 392
        addMatrixInfo(p, chr.cores, chr.getClient());
        p.write(1);
        p.writeInt(4);
        p.writeInt(0);
        return p.getPacket();
    }

    public static byte[] OnCoreEnforcementResult(int nSlot, int maxLevel, int currentlevel, int afterlevel) {
        WritingPacket p = new WritingPacket();
        p.writeShort(394); // 367 -> 394
        p.writeInt(nSlot);
        p.writeInt(maxLevel);
        p.writeInt(currentlevel);
        p.writeInt(afterlevel);
        return p.getPacket();
    }

    public static byte[] 플레임디스차지여우(MapleCharacter chr) {

        WritingPacket mplew = new WritingPacket();

        mplew.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        mplew.write(1);
        mplew.writeInt(chr.getId());
        mplew.writePos(chr.getPosition());
        mplew.writeInt(4);
        mplew.write(1);
        mplew.writePos(chr.getPosition());
        mplew.writeInt(400021045);
        mplew.write(1);
        mplew.writeInt(Randomizer.rand(0x26, 0x9F));
        mplew.writeInt(6);
        mplew.writeInt(Randomizer.rand(39, 44));
        mplew.writeInt(Randomizer.rand(3, 4));
        mplew.writeInt(Randomizer.rand(13, 332));
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.write(HexTool.getByteArrayFromHexString("33 7C F0 0B"));
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.write(0);
        mplew.writeInt(chr.getPosition().x);
        mplew.writeInt(chr.getPosition().y - 10);

        return mplew.getPacket();
    }

    public static byte[] 플레임디스차지여우재생성(MapleCharacter chr, MapleMonster monster) {

        WritingPacket mplew = new WritingPacket();

        mplew.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        mplew.write(1);
        mplew.writeInt(chr.getId());
        mplew.writePos(monster.getPosition());
        mplew.writeInt(4);
        mplew.write(1);
        mplew.writePos(monster.getPosition());
        mplew.writeInt(400021045);
        mplew.write(1);
        mplew.writeInt(Randomizer.rand(0x26, 0x9F));
        mplew.writeInt(6);
        mplew.writeInt(Randomizer.rand(39, 44));
        mplew.writeInt(Randomizer.rand(3, 4));
        mplew.writeInt(Randomizer.rand(13, 332));
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.write(HexTool.getByteArrayFromHexString("33 7C F0 0B"));
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.write(0);
        mplew.writeInt(monster.getPosition().x);
        mplew.writeInt(monster.getPosition().y - 10);

        return mplew.getPacket();
    }

    public static byte[] GUIDEDARROW(MapleCharacter chr, Point pos) {
        WritingPacket mplew = new WritingPacket();

        mplew.writeShort(SendPacketOpcode.ABSORB_DF.getValue());
        mplew.write(0);
        mplew.writeInt(chr.getId());
        mplew.writeInt(27);
        mplew.write(1);
        mplew.writeInt(1);
        mplew.writeInt(0);
        mplew.writeInt(400031000);
        mplew.write(1);
        mplew.writeInt(2);
        mplew.writeInt(1);
        mplew.writeInt(Randomizer.rand(0x2B, 0x2C));
        mplew.writeInt(Randomizer.rand(3, 4));
        mplew.writeInt(90);  //각도
        mplew.writeInt(840);
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.writeInt(System.currentTimeMillis());
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.writeInt(0);

        mplew.write(0);

        mplew.writeInt((int) pos.getX());
        mplew.writeInt((int) pos.getY());
        mplew.writeInt((int) pos.getX());
        mplew.writeInt((int) pos.getY());

        mplew.writeInt(0);
        return mplew.getPacket();
    }

    public static byte[] GuidedArrow(MapleCharacter chr, int oid) {
        WritingPacket mplew = new WritingPacket();

        mplew.writeShort(SendPacketOpcode.ABSORB_DF.getValue() + 3);
        mplew.writeInt(2);
        mplew.writeInt(chr.getId());
        mplew.writeInt(1);
        mplew.writeInt(1);
        mplew.writeInt(1);
        mplew.writeInt(oid);
        return mplew.getPacket();
    }

}
