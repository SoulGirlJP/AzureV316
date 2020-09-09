package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import client.MapleClient;
import connections.Packets.MainPacketCreator;
import connections.Packets.MobPacket;
import connections.Packets.PacketUtility.ReadingMaple;
import server.LifeEntity.MobEntity.MobAttack;
import server.LifeEntity.MobEntity.MobAttackInfo;
import server.LifeEntity.MobEntity.MobAttackInfoFactory;
import server.LifeEntity.MobEntity.MobSkill;
import server.LifeEntity.MobEntity.MobSkillFactory;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleNodes;
import server.Movement.LifeMovementFragment;
import tools.Pair;
import tools.Timer.MapTimer;
import tools.RandomStream.Randomizer;

public class MobHandler {

    public static final void MoveMonster(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        int oid = rh.readInt();
        final MapleMonster monster = chr.getMap().getMonsterByOid(oid);
        if (monster == null) {
            return;
        }
        if (chr.getMapId() == 120000102) { 
            return;
        }

        final short moveSN = rh.readShort();
        final byte bOption = rh.readByte();
        final byte actionAndDir = rh.readByte();
        final long targetInfo = rh.readLong();
        final int skillId = (int) (targetInfo & 0xFF);
        final int skillLevel = (int) ((targetInfo >> 16) & 0xFF);
        final int option = (int) ((targetInfo >> 16) & 0xFFFF);
        final boolean changeController = (bOption & 0x10) == 0x10;
        boolean movingAttack = (bOption & 0x01) == 0x01;
        int forceAttackIdx = 0;
        boolean usedSkill = false;
        int realSkillID = 0;
        int realSkillLevel = 0;

        int action = actionAndDir;
        if (action < 0) {
            action = -1;
        } else {
            action = action >> 1;
        }
        boolean nextAttackPossible = false;

        if (movingAttack && monster.getLastAttackTime() + 10000 < System.currentTimeMillis()) {
            nextAttackPossible = true;
        }
        if (!monster.delaying) {
            if (action >= 13 && action <= 29 && movingAttack) { // Mob attack
                int attackIdx = action - 13;
                
                if (attackIdx < monster.getStats().getAttacks().size()) {
                    MobAttack attack = monster.getStats().getAttacks().get(attackIdx);
                    MobAttackInfo attackInfo = MobAttackInfoFactory.getInstance().getMobAttackInfo(monster, attackIdx);

                    if (attackInfo.getMpCon() <= 0 || monster.getMp() >= attackInfo.getMpCon()) {
                        if (attack.getAfterAttack() != -1) {
                            monster.getMap().broadcastMessage(MobPacket.OnMobSetAfterAttack(oid, attack.getAfterAttack(), action, (actionAndDir & 0x01) != 0));
                        }

                        movingAttack = true;
                        monster.setLastAttackTime();
                    }
                }
            } else if (action >= 30 && action <= 46 && movingAttack) {
                usedSkill = true;

                if (monster.getNextSkill() == skillId && monster.getNextSkillLvl() == skillLevel && monster.hasSkill(skillId, skillLevel)) {
                    MobSkill msi = MobSkillFactory.getMobSkill(skillId, skillLevel);

                    if (msi != null) {
                        if (monster.getStats().getSkillAfter(skillId, skillLevel) != null) {
                            msi.setMobSkillDelay(c.getPlayer(), monster,
                                    monster.getStats().getSkillAfter(skillId, skillLevel), (short) 0);
                        } else if (msi.getSkillAfter() > 0) {
                            msi.setMobSkillDelay(c.getPlayer(), monster, msi.getSkillAfter(), (short) 0);
                        } else {
                            MapTimer.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    if (monster.isAlive()) {
                                        msi.applyEffect(chr, monster, true);
                                    }
                                }
                            }, skillId == 170 ? option : option * 100 + 450);
                            monster.setLastSkillUsed(skillId, skillLevel, System.currentTimeMillis(),
                                    msi.getCoolTime());
                            monster.setNextSkill(0);
                            monster.setNextSkillLvl(0);

                            usedSkill = false;
                        }
                    }
                }
            }

            if (movingAttack && !usedSkill && monster.getSkills().size() > 0 && System.currentTimeMillis() - monster.lastSkillUsed > 3000) {
                if (monster.getNextSkill() == 0) {
                    final int reqHp = (int) (((double) monster.getHp() / monster.getMobMaxHp()) * 100);
                    for (Pair<Integer, Integer> ss : monster.getSkills()) {
                        final long now = System.currentTimeMillis();
                        final long ls = monster.getLastSkillUsed(ss.left, ss.right);
                        final MobSkill msk = MobSkillFactory.getMobSkill(ss.left, ss.right);
                        if (ls == 0 || now >= ls) {
                            if (msk.getHP() == 0 || reqHp <= msk.getHP()) {
                                monster.setNextSkill(ss.left);
                                monster.setNextSkillLvl(ss.right);
                                msk.SkillMessage(monster);
                                realSkillID = ss.left;
                                realSkillLevel = ss.right;

                                break;
                            }
                        }
                    }
                } else {
                    realSkillID = monster.getNextSkill();
                    realSkillLevel = monster.getNextSkillLvl();
                }
            }
        }

        if (monster.getController() != null && monster.getController().getId() != c.getPlayer().getId()) {
            if (!changeController) { // Control at the same time
                c.sendPacket(MobPacket.stopControllingMonster(oid, false));
                return;
            } else {
                monster.switchController(chr, true);
            }
        }

        int count = rh.readByte();
        final List<Pair<Short, Short>> multiTargetForBall = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            multiTargetForBall.add(new Pair<>(rh.readShort(), rh.readShort()));
        }

        count = rh.readByte();
        final List<Short> randTimeForAreaAttack = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            randTimeForAreaAttack.add(rh.readShort());
        }

        rh.readByte();

        int skillAct = rh.readInt();

        rh.readInt();
        rh.readInt();
        rh.readInt();
        rh.readByte();

        int tEncodedGatherDuration = rh.readInt();

        final Point startPos = new Point(rh.readShort(), rh.readShort()
        );
        final Point velPos = new Point(rh.readShort(), rh.readShort()
        );
        
        final List<LifeMovementFragment> res = MovementParse.parseMovement(rh);

        count = rh.readByte();

        for (int i = 0; i < count; i += 2) {
            rh.readByte();
        }

        rh.readByte();
        rh.readInt();
        rh.readInt();
        rh.readInt();
        rh.readInt();
        rh.readByte();
        rh.readInt();
        rh.readByte();
        rh.readByte();
        rh.readByte();
        if (monster != null && c != null) {
            c.getSession().writeAndFlush(MobPacket.moveMonsterResponse(oid, moveSN, monster.getMp(), nextAttackPossible, realSkillID, realSkillLevel, forceAttackIdx));
        }
        if (res != null && res.size() > 0) {
            final MapleMap map = c.getPlayer().getMap();

            MovementParse.updatePosition(res, monster, -1);
            map.moveMonster(monster, monster.getPosition());
            map.broadcastMessage(chr, MobPacket.moveMonster(bOption, actionAndDir, targetInfo, oid, tEncodedGatherDuration, startPos, velPos, res, multiTargetForBall, randTimeForAreaAttack), monster.getPosition());

            monster.lastMoves = res;
        }
    }

    public static final void MobSkillDelayEnd(ReadingMaple slea, MapleCharacter chr) {
        System.out.println("MobSkillDelayEnd : " + slea.toString());
        MapleMonster monster = chr.getMap().getMonsterByOid(slea.readInt());
        if (monster != null) {
            int skillID = slea.readInt();
            int skillLv = slea.readInt();
            short remainCount = 0;
            if (slea.readByte() == 1) {
                remainCount = (short) slea.readInt();
            }

            monster.delaying = false;

            if (skillID == monster.getNextSkill() && skillLv == monster.getNextSkillLvl()) {
                monster.setLastSkillUsed(skillID, skillLv, System.currentTimeMillis(), MobSkillFactory.getMobSkill(skillID, skillLv).getCoolTime());
                MobSkillFactory.getMobSkill(skillID, skillLv).applyEffect(chr, monster, true);
                monster.setNextSkill(0);
                monster.setNextSkillLvl(0);
            }
        }
    }

    public static final void ObtacleAtomCollision(ReadingMaple rm, MapleCharacter chr) {
        int len = rm.readInt();
        for (int i = 0; i < len; i++) {
            int idx = rm.readInt();
            int SN = rm.readInt();
            rm.readInt();
            rm.readInt();
            rm.readInt();
        }
    }

    public static final void FriendlyDamage(final ReadingMaple rh, final MapleCharacter chr) {
        final MapleMap map = chr.getMap();
        final MapleMonster mobfrom = map.getMonsterByOid(rh.readInt());
        rh.skip(4);
        final MapleMonster mobto = map.getMonsterByOid(rh.readInt());

        if (mobfrom != null && mobto != null && mobto.getStats().isFriendly()) {
            final int damage = (mobto.getStats().getLevel() * Randomizer.nextInt(99)) / 2;																			// effective
            mobto.damage(chr, damage, true);
        }
    }

    public static final void MonsterBomb(final int oid, final MapleCharacter chr) {
        final MapleMonster monster = chr.getMap().getMonsterByOid(oid);

        if (monster == null || !chr.isAlive() || chr.isHidden()) {
            return;
        }
        final byte selfd = monster.getStats().getSelfD();
        if (selfd != -1) {
            chr.getMap().killMonster(monster, chr, false, false, selfd);
        }
    }

    public static final void AutoAggro(final int monsteroid, final MapleCharacter chr) {
        final MapleMonster monster = chr.getMap().getMonsterByOid(monsteroid);

        if (monster != null && chr.getPosition().distance(monster.getPosition()) < 200000) {
            if (monster.getController() != null) {
                if (chr.getMap().getCharacterById_InMap(monster.getController().getId()) == null) {
                    monster.switchController(chr, true);
                } else {
                    monster.switchController(monster.getController(), true);
                }
            } else {
                monster.switchController(chr, true);
            }
        }
    }

    public static final void HypnotizeDmg(final ReadingMaple slea, final MapleCharacter chr) {
        final MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        slea.skip(4);
        final int to = slea.readInt();
        slea.skip(1);
        final int damage = slea.readInt();

        final MapleMonster mob_to = chr.getMap().getMonsterByOid(to);

        if (mob_from != null && mob_to != null) {
            if (damage > 30000) {
                return;
            }
            mob_to.damage(chr, damage, true);
            chr.getMap().broadcastMessage(chr, MobPacket.damageMonster(to, damage), false);
        }
    }

    public static final void MobNode(ReadingMaple slea, MapleCharacter chr) {
        MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        int newNode = slea.readInt();
        int nodeSize = chr.getMap().getNodes().size();
        if ((mob_from != null) && (nodeSize > 0)) {
            MapleNodes.MapleNodeInfo mni = chr.getMap().getNode(newNode);
            if (mni == null) {
                return;
            }
            if (mni.attr == 2) {
                switch (chr.getMapId() / 100) {
                    case 9211200:
                    case 9211201:
                    case 9211202:
                    case 9211203:
                    case 9211204:
                        chr.getMap().talkMonster("Please escort me carefully.", 5120035, mob_from.getObjectId());
                        break;
                    case 9320001:
                    case 9320002:
                    case 9320003:
                        chr.getMap().talkMonster("Please escort me carefully.", 5120051, mob_from.getObjectId());
                }
            }

            if (chr.getMap().isLastNode(newNode)) {
                switch (chr.getMapId() / 100) {
                    case 9211200:
                    case 9211201:
                    case 9211202:
                    case 9211203:
                    case 9211204:
                    case 9320001:
                    case 9320002:
                    case 9320003:
                        chr.getMap().broadcastMessage(MainPacketCreator.serverNotice(5, "Proceed to the next stage."));
                        chr.getMap().removeMonster(mob_from);
                }
            }
        }
    }

    public static final void DisplayNode(ReadingMaple slea, MapleCharacter chr) {
        MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        if (mob_from != null) {
            chr.getClient().getSession().writeAndFlush(MobPacket.getNodeProperties(mob_from, chr.getMap()));
        }
    }
}
