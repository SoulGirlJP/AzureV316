package connections.Packets;

import java.awt.Point;
import java.awt.Rectangle;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.Stats.MonsterStatus;
import client.Stats.MonsterStatusEffect;
import constants.GameConstants;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleNodes;
import server.Maps.ObtacleAtom;
import server.Movement.LifeMovementFragment;
import tools.Pair;

public class MobPacket {

    public static byte[] damageMonster(final int oid, final long damage) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.DAMAGE_MONSTER.getValue());
        packet.writeInt(oid);
        packet.write(0);
        packet.writeLong(damage);

        return packet.getPacket();
    }

    public static byte[] damageFriendlyMob(final MapleMonster mob, final long damage) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.DAMAGE_MONSTER.getValue());
        packet.writeInt(mob.getObjectId());
        packet.write(1);
        packet.writeLong(damage);
        packet.writeLong(mob.getHp());
        packet.writeLong(mob.getMobMaxHp());

        return packet.getPacket();
    }

    public static byte[] killMonster(final int oid, final int animation, boolean isAswan) {
        WritingPacket packet = new WritingPacket();

        if (isAswan) {
            packet.writeShort(SendPacketOpcode.ASWAN_KILL_MONSTER.getValue());
        } else {
            packet.writeShort(SendPacketOpcode.KILL_MONSTER.getValue());
        }
        packet.writeInt(oid);
        packet.write(animation); // 0 = dissapear, 1 = fade out, 2+ = special

        return packet.getPacket();
    }

    public static byte[] healMonster(final int oid, final int heal) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.DAMAGE_MONSTER.getValue());
        packet.writeInt(oid);
        packet.write(0);
        packet.writeLong(-heal);

        return packet.getPacket();
    }

    public static byte[] showMonsterHP(int oid, int remhppercentage) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_MONSTER_HP.getValue());
        packet.writeInt(oid);
        packet.writeInt(remhppercentage); 
        packet.write(remhppercentage);


        return packet.getPacket();
    }

    public static byte[] showBossHP(final MapleMonster mob) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.BOSS_ENV.getValue());
        packet.write(6);
        packet.writeInt(mob.getId());
        packet.writeLong(mob.getHp());
        packet.writeLong(mob.getMobMaxHp());
        packet.write(mob.getStats().getTagColor());
        packet.write(mob.getStats().getTagBgColor());

        return packet.getPacket();
    }

    public static byte[] showFinalBossHP(final MapleMonster mob) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.BOSS_ENV.getValue());
        packet.write(6);
        packet.writeInt(mob.getId());
        packet.writeLong(mob.getHp());
        packet.writeLong(mob.getMobMaxHp());
        packet.write(mob.getStats().getTagColor());
        packet.write(mob.getStats().getTagBgColor());
        return packet.getPacket();
    }

    public static byte[] OnNextAttack(int oid, int attackIdx) {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(876);
        wp.writeInt(oid);
        wp.writeInt(attackIdx);
        return wp.getPacket();
    }

    public static byte[] moveMonster(byte useskill, int skill, long targetInfo, int oid, int tEncodedGatherDuration,
            Point startPos, Point velPos, List<LifeMovementFragment> moves, List<Pair<Short, Short>> multiTargetForBall,
            List<Short> randTimeForAreaAttack) {
        WritingPacket packet = new WritingPacket();
       
        
        packet.writeShort(SendPacketOpcode.MOVE_MONSTER.getValue());
        packet.writeInt(oid);
        packet.write(useskill);
        packet.write(skill);
        
        packet.writeInt(0);

        packet.writeInt(targetInfo);
        packet.write(multiTargetForBall.size());
        for (int i = 0; i < multiTargetForBall.size(); i++) {
            packet.writeShort(multiTargetForBall.get(i).left);
            packet.writeShort(multiTargetForBall.get(i).right);
        }

        packet.write(randTimeForAreaAttack.size());
        for (int i = 0; i < randTimeForAreaAttack.size(); i++) {
            packet.writeShort(randTimeForAreaAttack.get(i));
        }

        packet.writeInt(tEncodedGatherDuration); // 1.2.192+
        packet.writePos(startPos);
        packet.writePos(velPos);
        serializeMovementList(packet, moves);
        packet.write(0); // boolean

        return packet.getPacket();
    }

    private static void serializeMovementList(WritingPacket packet, List<LifeMovementFragment> moves) {
        packet.write(moves.size());
        for (LifeMovementFragment move : moves) {
            move.serialize(packet);
        }
    }

    public static void addMobSkillInfo(WritingPacket packet, MapleMonster life) {
        if (life.isStatChanged()) {
            packet.write(1);
            packet.writeInt(life.getHp());
            packet.writeInt(life.getMp());
            packet.writeInt(life.getStats().getExp());
            packet.writeInt(life.getStats().getPad());
            packet.writeInt(life.getStats().getMad());
            packet.writeInt(0);// PDR
            packet.writeInt(0);// MDR
            packet.writeInt(life.getStats().getAcc());
            packet.writeInt(life.getStats().getEva());
            packet.writeInt(life.getStats().getPushed());
            packet.writeInt(life.getStats().getSpeed());
            packet.writeInt(life.getStats().getLevel());
            packet.writeInt(0);// UserCount
        } else {
            packet.write(0);
        }
        Map<MonsterStatus, Integer> stats = new HashMap<MonsterStatus, Integer>();
        for (Entry<MonsterStatus, Integer> stat : stats.entrySet()) {
            stats.put(stat.getKey(), stat.getValue());
        }
        byte[] by = writeMonsterIntMask(stats);
        packet.write(by);
        int mask1 = GameConstants.bint(by, 0);
        int mask2 = GameConstants.bint(by, 4);
        int mask3 = GameConstants.bint(by, 8);
        int mask4 = GameConstants.bint(by, 12);
        for (int i = 31; i >= 0; i--) {
            if (((mask1 >>> i) & 1) == 1) {
                MonsterStatus key = MonsterStatus.getStati(0, i);
                if (key != null) {
                    MonsterStatusEffect mse = life.getStati().get(key);
                    if (mse != null) {
                        Entry<MonsterStatus, Integer> stat = mse.getStati(i, 0);
                        if (stat != null) {
                            int value = stat.getValue();
                            packet.writeInt(value == 0 ? 1 : value);
                            packet.writeInt(mse.getSkillId());
                            packet.writeShort(0);
                        }
                    }
                }
            }
        }
        for (int i = 31; i >= 0; i--) {
            if (((mask2 >>> i) & 1) == 1) {
                MonsterStatus key = MonsterStatus.getStati(4, i);
                if (key != null) {
                    MonsterStatusEffect mse = life.getStati().get(key);
                    if (mse != null) {
                        Entry<MonsterStatus, Integer> stat = mse.getStati(i, 4);
                        if (stat != null) {
                            int value = stat.getValue();
                            packet.writeInt(value == 0 ? 1 : value);
                            packet.writeInt(mse.getSkillId());
                            packet.writeShort(0);
                        }
                    }
                }
            }
        }
        for (int i = 31; i >= 21; i--) {
            if (((mask3 >>> i) & 1) == 1) {
                MonsterStatus key = MonsterStatus.getStati(8, i);
                if (key != null) {
                    MonsterStatusEffect mse = life.getStati().get(key);
                    if (mse != null) {
                        Entry<MonsterStatus, Integer> stat = mse.getStati(i, 8);
                        if (stat != null) {
                            int value = stat.getValue();
                            packet.writeInt(value == 0 ? 1 : value);
                            packet.writeInt(mse.getSkillId());
                            packet.writeShort(0);
                        }
                    }
                }
            }
        }
        if (((mask1 >>> 30) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 28) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 6) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 5) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 6) & 1) == 1 || ((mask1 >>> 5) & 1) == 1) {
            packet.writeInt(0);
            packet.write(0);
            packet.writeInt(0);
        }
        if (((mask2 >>> 26) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask3 >>> 22) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask3 >>> 17) & 1) == 1) {
            packet.write(1);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask2 >>> 24) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask2 >>> 21) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask1 >>> 25) & 1) == 1) {
            packet.write(0);
        }
        if (((mask2 >>> 12) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask2 >>> 9) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask2 >>> 7) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask2 >>> 1) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 23) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask3 >>> 20) & 1) == 1) {
            MonsterStatusEffect mse = life.getStati().get(MonsterStatus.Burned);
            if (mse != null) {
                packet.write(1);
                packet.writeInt(mse.getOwnerId());
                packet.writeInt(mse.getSkill().getId());
                packet.writeInt(mse.getStati().get(MonsterStatus.Burned));
                packet.writeInt(mse.getSkill().getEffect(mse.getSkillLevel()).getDotInterval()); // dot Interval
                packet.writeInt(mse.getV(1));
                packet.writeInt(mse.getV(2));
                packet.writeInt(mse.getSkill().getEffect(mse.getSkillLevel()).getDotTime()); // dotTime
                packet.writeInt(0);
                packet.writeInt(0);
                packet.writeInt(0);
                packet.writeInt(0);
            }
        }
        if (((mask3 >>> 19) & 1) == 1) {
            packet.write(0);
            packet.write(0);
        }
        if (((mask3 >>> 18) & 1) == 1) {
            packet.write(0);
        }
        if (((mask2 >>> 28) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask3 >>> 16) & 1) == 1) {
            packet.writeMapleAsciiString("");
        }
        if ((mask3 & 0x8000) == 0x8000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x4000) == 0x4000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeShort(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x2000) == 0x2000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeShort(0);
            packet.writeInt(0);
        }

        // ÀÌ±×´Ï¼Ç
        if ((mask3 & 0x1000) == 0x1000) {
            packet.writeInt(0); // value
            packet.writeInt(0); // skillid
            packet.writeInt(0); // time?
            packet.writeInt(0);
            packet.writeInt(0);
        }

        if ((mask3 & 0x800) == 0x800) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x20000000) == 0x20000000) {
            packet.writeInt(0);
        }
        if ((mask3 & 0x400) == 0x400) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask2 & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask2 & 0x80000000) == 0x80000000) {
            packet.writeInt(0);
            packet.writeInt(0);
        }
    }

    public static byte[] spawnMonster(MapleMonster life, int spawnType, int effect, int link, boolean isAswan) {
        WritingPacket packet = new WritingPacket();
        if (isAswan) {
            packet.writeShort(SendPacketOpcode.ASWAN_SPAWN_MONSTER.getValue());
        } else {
            packet.writeShort(SendPacketOpcode.SPAWN_MONSTER.getValue());
        }
        packet.write(0);
        packet.writeInt(life.getObjectId());
        packet.write(1);
        packet.writeInt(life.getId());
        addMobSkillInfo(packet, life);
        packet.writeShort(life.getPosition().x);
        packet.writeShort(life.getPosition().y);
        packet.write(life.getStance());
        if ((life.getId() == 8910000) || (life.getId() == 8910100)) {
            packet.write(0);
        }
        packet.writeShort(life.getFh());
        packet.writeShort(life.getFh());
        int v24 = spawnType;
        packet.write(v24);
        if (v24 == -3 || v24 >= 0) {
            packet.writeInt(link);
        }
        packet.write(0xFF); // Monster Carnival.
        packet.writeLong(life.getHp() > Long.MAX_VALUE ? Long.MAX_VALUE : life.getHp());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.write(0);
        if (false) {
            packet.writeInt(0);
            packet.writeMapleAsciiString("");
            packet.writeMapleAsciiString("");
        }
        packet.writeInt(-1); // afterAttack
        packet.writeInt(0); // unk
        packet.writeInt(-1); // currentAction
        packet.write(life.isFacingLeft()); // bIsLeft
        packet.writeInt(0);
        packet.writeInt(life.getScale());
        packet.writeInt(life.isEliteMonster() ? 1 : -1);
        if (life.isEliteMonster()) {
            packet.writeInt(1);
            packet.writeInt(life.getEliteType());
            packet.writeInt(life.getEliteType());
            packet.writeInt(1);
        }
        packet.write(0);
        packet.write(0);
        packet.writeInt(0);
        packet.write(0);
        packet.write(0);
        packet.writeInt(0);
        packet.writeInt(0);
        if (spawnType == -2) {
            packet.write(0); // true => 5 ints
        }
        if (life.getStats().isSkeleton()) {
            List<String> hitParts = life.getStats().getHitParts();

            packet.write(hitParts.size());

            for (String hitPart : hitParts) {
                packet.writeMapleAsciiString(hitPart);
                packet.write(0);
                packet.writeInt(0);
            }
        }
        return packet.getPacket();
    }

    public static byte[] controlMonster(MapleMonster life, boolean newSpawn, boolean aggro, boolean isAswan) {
        WritingPacket packet = new WritingPacket();
        if (isAswan) {
            packet.writeShort(SendPacketOpcode.ASWAN_SPAWN_MONSTER_CONTROL.getValue());
        } else {
            packet.writeShort(SendPacketOpcode.SPAWN_MONSTER_CONTROL.getValue());
        }
        packet.write(aggro ? 2 : 1);
        if (isAswan) {
            packet.write(1);
        }
        packet.writeInt(life.getObjectId());
        packet.write(1);
        packet.writeInt(life.getId());
        addMobSkillInfo(packet, life);
        packet.writeShort(life.getPosition().x);
        packet.writeShort(life.getPosition().y);
        packet.write(life.getStance());
        if ((life.getId() == 8910000) || (life.getId() == 8910100)) {
            packet.write(0);
        }
        packet.writeShort(life.getFh());
        packet.writeShort(life.getFh());
        byte AppearType = (byte) (newSpawn ? (life.getStats().isBoss() ? -1 : 0xFE) : life.isFake() ? -4 : 0xFF);
        packet.write(AppearType);
        if (AppearType == -3 || AppearType >= 0) {
            packet.writeInt(0);
        }
        packet.write(0xFF); // Monster Carnival.
        packet.writeLong(life.getHp() > Long.MAX_VALUE ? Long.MAX_VALUE : life.getHp());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.write(0);
        if (false) {
            packet.writeInt(0);
            packet.writeMapleAsciiString("");
            packet.writeMapleAsciiString("");
        }
        packet.writeInt(-1); // afterAttack
        packet.writeInt(0); // unk
        packet.writeInt(-1); // currentAction
        packet.write(life.isFacingLeft()); // bIsLeft
        packet.writeInt(0);
        packet.writeInt(life.getScale());
        packet.writeInt(life.isEliteMonster() ? 1 : -1);
        if (life.isEliteMonster()) {
            packet.writeInt(1);
            packet.writeInt(life.getEliteType());
            packet.writeInt(life.getEliteType());
            packet.writeInt(1);
        }
        packet.write(0);
        packet.write(0);
        packet.writeInt(0);
        packet.write(0);
        packet.write(0);
        packet.writeInt(0);
        packet.writeInt(0);

        if (AppearType == -2) {
            packet.write(0); // true => 5 ints
        }

        if (life.getStats().isSkeleton()) {
            List<String> hitParts = life.getStats().getHitParts();

            packet.write(hitParts.size());

            for (String hitPart : hitParts) {
                packet.writeMapleAsciiString(hitPart);
                packet.write(0);
                packet.writeInt(0);
            }
        }
        return packet.getPacket();
    }

    public static byte[] stopControllingMonster(int oid, boolean isAswan) {
        WritingPacket packet = new WritingPacket();

        if (isAswan) {
            packet.writeShort(SendPacketOpcode.ASWAN_SPAWN_MONSTER_CONTROL.getValue());
            packet.write(0);
        } else {
            packet.writeShort(SendPacketOpcode.SPAWN_MONSTER_CONTROL.getValue());
        }
        packet.write(0);
        packet.writeInt(oid);

        return packet.getPacket();
    }

    public static byte[] makeMonsterInvisible(MapleMonster life, boolean isAswan) {
        WritingPacket packet = new WritingPacket();

        if (isAswan) {
            packet.writeShort(SendPacketOpcode.ASWAN_SPAWN_MONSTER_CONTROL.getValue());
        } else {
            packet.writeShort(SendPacketOpcode.SPAWN_MONSTER_CONTROL.getValue());
        }
        packet.write(0);
        packet.writeInt(life.getObjectId());

        return packet.getPacket();
    }

    public static byte[] moveMonsterResponse(int objectid, short moveid, int currentMp, boolean useSkills, int skillId,
            int skillLevel, int attackIdx) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MOVE_MONSTER_RESPONSE.getValue());
        packet.writeInt(objectid);
        packet.writeShort(moveid); // nMobCtrlSN
        packet.write(useSkills ? 1 : 0); // bNextAttackPossible
        packet.writeInt(currentMp); // v5
        packet.writeInt(skillId);
        packet.writeShort(skillLevel);
        packet.writeInt(attackIdx);
        packet.writeInt(skillId);
        return packet.getPacket();
    }

    public static byte[] MobSkillDelay(int objectId, int skillID, int skillLv, int skillAfter, short sequenceDelay,
            List<Rectangle> skillRectInfo) {
        WritingPacket mplew = new WritingPacket();

        mplew.writeShort(1001);
        mplew.writeInt(objectId);
        mplew.writeInt(skillAfter);
        mplew.writeInt(skillID);
        mplew.writeInt(skillLv);
        if (skillRectInfo == null || skillRectInfo.isEmpty()) {
            mplew.writeInt(0);
            mplew.writeInt(0);
        } else {
            mplew.writeInt(sequenceDelay);
            mplew.writeInt(skillRectInfo.size());
            for (Rectangle rect : skillRectInfo) {
                mplew.writeInt(rect.x);
                mplew.writeInt(rect.y);
                mplew.writeInt(rect.x + rect.width);
                mplew.writeInt(rect.y + rect.height);
            }
        }
        mplew.writeInt(0);
        return mplew.getPacket();
    }

    public static byte[] MobForceAttack(int objectId, int attackIdx) {
        WritingPacket mplew = new WritingPacket();

        mplew.writeShort(876);
        mplew.writeInt(objectId);
        mplew.writeInt(attackIdx);
        return mplew.getPacket();
    }

    public static byte[] writeMonsterIntMask(Map<MonsterStatus, Integer> stats) {
        BigInteger bi = BigInteger.valueOf(0);

        for (MonsterStatus stat : stats.keySet()) {
            bi = bi.or(stat.getBigValue());
        }

        return PacketProvider.convertFromBigInteger(bi, MonsterStatus.BIT_COUNT);
    }

    public static byte[] applyMonsterStatus(final int oid, final MonsterStatusEffect mse) {
        return applyMonsterStatus(oid, mse, null);
    }

    public static byte[] applySwooLaser(final int oid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        packet.writeInt(oid);
        packet.write(writeMonsterIntMask(Collections.singletonMap(MonsterStatus.Laser, 1)));
        packet.writeInt(1);
        packet.writeShort(223); // mobskillid
        packet.writeShort(5); // mobskilllv
        packet.writeShort(24033); // position?
        packet.writeShort(23589); // position?
        packet.writeInt(1);
        packet.writeInt(233);
        packet.writeShort(0);
        packet.write(1);
        return packet.getPacket();
    }

    public static byte[] applyMonsterStatus(final int oid, final MonsterStatusEffect mse,
            final List<Integer> reflection) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        packet.writeInt(oid);
        byte[] by = writeMonsterIntMask(mse.getStati());
        packet.write(by);
        int mask1 = GameConstants.bint(by, 0);
        int mask2 = GameConstants.bint(by, 4);
        int mask3 = GameConstants.bint(by, 8);
        int mask4 = GameConstants.bint(by, 12);
        for (int i = 31; i >= 0; i--) {
            if (((mask1 >>> i) & 1) == 1) {
                Entry<MonsterStatus, Integer> stat = mse.getStati(i, 0);
                if (stat != null) {
                    int value = stat.getValue();
                    if (((mask1 >>> 22) & 1) == 1) { //
                        packet.writeInt(0xF1FFFFFF);
                    } else {
                        packet.writeInt(value == 0 ? 1 : value);
                    }
                    packet.writeInt(mse.getSkillId());
                    packet.writeShort(((mask1 >>> 22) & 1) == 1 ? 0x1E : 0); //
                    if (mse.getSkillId() == 2321001) {
                        packet.writeInt(0);// ºò¹ð
                    }
                    if (((mask1 >>> 22) & 1) == 1) { //
                        packet.writeInt(value);
                    }
                }
            }
        }
        for (int i = 31; i >= 0; i--) {
            if (((mask2 >>> i) & 1) == 1) {
                Entry<MonsterStatus, Integer> stat = mse.getStati(i, 4);
                if (stat != null) {
                    int value = stat.getValue();
                    packet.writeInt(value == 0 ? 1 : value);
                    packet.writeInt(mse.getSkillId());
                    packet.writeShort(0);
                }
            }
        }
        for (int i = 31; i >= 21; i--) {
            if (((mask3 >>> i) & 1) == 1) {
                Entry<MonsterStatus, Integer> stat = mse.getStati(i, 8);
                if (stat != null) {
                    int value = stat.getValue();
                    packet.writeInt(value == 0 ? 1 : value);
                    packet.writeInt(mse.getSkillId());
                    packet.writeShort(0);
                }
            }
        }
        if (((mask1 >>> 30) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 28) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 6) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 5) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 6) & 1) == 1 || ((mask1 >>> 5) & 1) == 1) {
            packet.writeInt(0);
            packet.write(0);
            packet.writeInt(0);
        }
        if (((mask2 >>> 26) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask3 >>> 22) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask3 >>> 17) & 1) == 1) {
            packet.write(1);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask2 >>> 24) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask2 >>> 21) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask1 >>> 25) & 1) == 1) {
            packet.write(1);
        }
        if (((mask2 >>> 12) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask2 >>> 9) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask2 >>> 7) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask2 >>> 1) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask1 >>> 23) & 1) == 1) {
            packet.writeInt(0);
        }
        if (((mask3 >>> 26) & 1) == 1) { // 1.2.316
            packet.write(1);
            packet.writeInt(0);
            packet.writeInt(mse.getOwnerId());
            packet.writeInt(mse.getSkill().getId());
            packet.writeLong(mse.getStati().get(MonsterStatus.Burned));
            packet.writeInt(mse.getSkill().getEffect(mse.getSkillLevel()).getDotInterval()); // dot Interval
            packet.writeInt(mse.getV(1));
            packet.writeInt(mse.getV(2));
            packet.writeInt(mse.getSkill().getEffect(mse.getSkillLevel()).getDotTime()); // dotTime
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        
        if (((mask3 >>> 19) & 1) == 1) {
            packet.write(0);
            packet.write(0);
        }
        if (((mask3 >>> 18) & 1) == 1) {
            packet.write(0);
        }
        if (((mask2 >>> 28) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if (((mask3 >>> 16) & 1) == 1) {
            packet.writeMapleAsciiString("");
        }
        if ((mask3 & 0x8000) == 0x8000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x4000) == 0x4000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeShort(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x2000) == 0x2000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeShort(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x1000) == 0x1000) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x800) == 0x800) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask3 & 0x20000000) == 0x20000000) {
            packet.writeInt(0);
        }
        if ((mask3 & 0x400) == 0x400) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask2 & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        if ((mask2 & 0x80000000) == 0x80000000) {
            packet.writeInt(0);
            packet.writeInt(0);
        }
        packet.writeShort(0);
        packet.write(0);
        packet.writeLong(0);
        return packet.getPacket();
    }

    public static byte[] cancelMonsterStatus(int cid, int oid, final MonsterStatusEffect mse,
            Map<MonsterStatus, Integer> stats) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.CANCEL_MONSTER_STATUS.getValue());
        packet.writeInt(oid);
        byte[] by = writeMonsterIntMask(stats);
        packet.write(by);
        int mask[] = {GameConstants.bint(by, 0), GameConstants.bint(by, 4), GameConstants.bint(by, 8)};
        if (((mask[2] >>> 26) & 1) == 1) {
            packet.writeInt(0);
            packet.writeInt(1);
            packet.writeInt(cid);
            packet.writeInt(mse == null ? -1 : mse.getV(1));
        }
        packet.write0(50);
        packet.write(3);
        packet.write(2);

        return packet.getPacket();
    }

    public static byte[] swallowMonster(final int oid, final int cid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.KILL_MONSTER.getValue());
        packet.writeInt(oid);
        packet.write(4);
        packet.writeInt(cid);

        return packet.getPacket();
    }

    public static byte[] removeObtacleAtomBomb(int objectid) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(436);
        mplew.writeInt(objectid);
        mplew.write0(100);
        return mplew.getPacket();
    }

    public static byte[] spawnObtacleAtomBomb(int mobid, int type, List<ObtacleAtom> olist, int rand) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(435);
        mplew.writeInt(1597783778);
        mplew.writeInt(olist.size());
        mplew.write(type);
        mplew.writeInt(mobid);
        mplew.writeInt(890);
        mplew.writeInt(-498);
        mplew.writeInt(790);
        mplew.writeInt(0);
        int i = 0;
        for (ObtacleAtom bomb : olist) {
            mplew.write(1);
            mplew.writeInt(bomb.type);
            mplew.writeInt(1597763778 + i);
            mplew.writeInt(bomb.sx);
            mplew.writeInt(bomb.sy);
            mplew.writeInt(bomb.ex);
            mplew.writeInt(bomb.ey);
            mplew.writeInt(bomb.range);
            mplew.writeInt(bomb.pdam);
            mplew.writeInt(bomb.mdam);
            mplew.writeInt(bomb.delay);
            mplew.writeInt(bomb.high);
            mplew.writeInt(bomb.speed);
            mplew.writeInt(bomb.len);
            mplew.writeInt(bomb.float_);
            mplew.writeInt(bomb.v1);
            i++;
        }
        return mplew.getPacket();
    }

    public static final byte[] getNodeProperties(MapleMonster objectid, MapleMap map) {
        if (objectid.getNodePacket() != null) {
            return objectid.getNodePacket();
        }
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.MONSTER_PROPERTIES.getValue());
        pw.writeInt(objectid.getObjectId());
        pw.writeInt(map.getNodes().size());
        pw.writeInt(objectid.getPosition().x);
        pw.writeInt(objectid.getPosition().y);
        for (MapleNodes.MapleNodeInfo mni : map.getNodes()) {
            pw.writeInt(mni.x);
            pw.writeInt(mni.y);
            pw.writeInt(mni.attr);
            if (mni.attr == 2) {
                pw.writeInt(500);
            }
        }
        pw.writeInt(0);
        pw.write(0);
        pw.write(0);

        objectid.setNodePacket(pw.getPacket());
        return objectid.getNodePacket();
    }

    public static byte[] talkMonster(int oid, int itemId, String msg) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.TALK_MONSTER.getValue());
        pw.writeInt(oid);
        pw.writeInt(500);
        pw.writeInt(itemId);
        pw.write(itemId <= 0 ? 0 : 1);
        pw.write((msg == null) || (msg.length() <= 0) ? 0 : 1);
        if ((msg != null) && (msg.length() > 0)) {
            pw.writeMapleAsciiString(msg);
        }
        pw.writeInt(1);

        return pw.getPacket();
    }

    public static byte[] removeTalkMonster(int oid) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.REMOVE_TALK_MONSTER.getValue());
        pw.writeInt(oid);

        return pw.getPacket();
    }

    public static byte[] OnMobSetAfterAttack(int oid, int AfterAttack, int nAction, boolean IsLeft) {
        WritingPacket p = new WritingPacket();
        p.writeShort(977);
        p.writeInt(oid);
        p.writeShort(AfterAttack);
        p.writeInt(nAction);
        p.writeInt(nAction);
        p.write(IsLeft);
        return p.getPacket();

    }

    public static byte[] MobAttackBlock(int oid, List<Integer> blockedAttacks) {
        WritingPacket p = new WritingPacket();
        p.writeShort(884);
        p.writeInt(oid);
        p.writeInt(blockedAttacks.size());

        for (Integer attackIdx : blockedAttacks) {
            p.writeInt(attackIdx);
        }
        return p.getPacket();

    }

    public static byte[] MobChangePhase(int oid, int phase, boolean phaseStart) {
        WritingPacket p = new WritingPacket();
        p.writeShort(425);
        p.writeInt(oid);
        p.writeInt(phase);
        p.write(phaseStart);
        return p.getPacket();

    }

    public static byte[] MobAttackPriority(int oid) {
        WritingPacket p = new WritingPacket();
        p.writeShort(885);
        p.writeInt(oid);
        p.writeInt(1);
        p.writeInt(0);
        p.writeInt(0);
        return p.getPacket();

    }

    public static byte[] MobAttackTime(int oid, int time, int attackIdx, int count) {
        WritingPacket p = new WritingPacket();
        p.writeShort(886);
        p.writeInt(oid);
        p.writeInt(1);
        p.writeInt(time);
        p.writeInt(attackIdx);
        p.writeInt(count);
        return p.getPacket();

    }

    public static byte[] MobTeleport(int oid, Point pos) {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(878);
        wp.writeInt(oid);
        wp.write(0);
        wp.writeInt(3);
        wp.writeintPos(pos);
        return wp.getPacket();
    }

}
