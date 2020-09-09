package client.Skills;

import client.Character.MapleCharacter;
import client.Stats.BuffStats;
import client.Stats.BuffStatsValueHolder;
import client.Stats.MonsterStatus;
import client.Stats.MonsterStatusEffect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.Maps.ArrowFlatter;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapSummon.MapleSummon;
import tools.Triple;
import java.awt.Point;

public class SkillStatEffectCancelHandler implements Runnable {

    private Skill _bigHugeGiganticCanonBall;
    private Skill _howlingGale;
    private Skill _demonicFrenzy;
    private Skill _blackMagicAlter;
    private Skill _overloadMode;
    private Skill _Transition;
    private Skill _퓨리어스차지;
    
    public SkillStatEffectCancelHandler() {
        System.out.println("[Notice] SkillStatEffectCancelHandler Thread is up.");
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            for (Iterator<MapleMap> iterator = cs.getMapFactory().getAllLoadedMaps().iterator(); iterator.hasNext(); ) {
                MapleMap map = iterator.next();
                if (map != null) {
                    if (map.canSpawn(time)) {
                        map.respawn(false, time);
                    }
                    for (final MapleCharacter chr : map.getCharacters()) {

                        if (map.getArrowFlatter().size() > 0) {
                            for (final ArrowFlatter arrow : map.getArrowFlatter()) {
                                if (time >= arrow.getTime()) {
                                    map.broadcastMessage(MainPacketCreator.cancelArrowFlatter(arrow.getObjectId(), arrow.getArrow()));
                                    map.removeMapObject(arrow);
                                }
                            }
                        }
                        if (map.getAllSummon().size() > 0) {
                            for (final MapleMapObject ss : map.getAllSummon()) {
                                MapleSummon summon = (MapleSummon) ss;
                                if (summon.getEndTime() <= time) {
                                    if (summon.getSkill() == 14000027 || summon.getSkill() == 14100027 || summon.getSkill() == 14110029 || summon.getSkill() == 14120008) {
                                    } else {
                                        summon.removeSummon(summon.getOwner().getMap());
                                    }
                                }
                            }
                        }
                        if (chr.getBuffedValue(BuffStats.FireAura) != null) {
                            chr.addMP(-100);
                        }
                        if (chr.getBuffedValue(BuffStats.IceAura) != null) {
                            chr.addMP(-60);
                        }
                        if (chr.getBuffedValue(BuffStats.MichaelSoulLink) != null) {
                            chr.addMP(-chr.getBuffedSkillEffect(BuffStats.MichaelSoulLink).getStat("mpCon"));
                        }
                        if (chr.getBuffedValue(BuffStats.UnionAuraBlow) != null) {
                            chr.addMP(-chr.getBuffedSkillEffect(BuffStats.UnionAuraBlow).getStat("mpCon"));
                        }
                        if (chr.isActiveBuffedValue(400011010)) {
                            chr.addHP(-SkillFactory.getSkill(400011010).getEffect(chr.getSkillLevel(400011010)).getY());
                        }
                        if (chr.isActiveBuffedValue(142121032)) {
                            chr.givePP(1);
                        }
                        if (chr.getBuffedValue(BuffStats.DevilishPower, 400051015) != null && !(chr.getJob() >= 510 && chr.getJob() <= 512)) {
                            SkillFactory.getSkill(400051015).getEffect(1).cerpentScrew(chr);
                        }
                        if (chr.getCooldowns().size() > 0) {
                            List<CoolDownValueHolder> coolList = new ArrayList<>();

                            for (Entry<Integer, CoolDownValueHolder> cool : chr.getCooldowns().entrySet()) {
                                if ((cool.getValue().startTime + cool.getValue().length) <= time) {
                                    coolList.add(cool.getValue());
                                }
                            }
                            if (!coolList.isEmpty()) {
                                for (int i = 0; i < coolList.size(); i++) {
                                    chr.getClient().sendPacket(MainPacketCreator.skillCooldown(coolList.get(i).skillId, 0));
                                    chr.cancelCooldown(coolList.get(i).skillId);
                                }
                            }
                        }
                        if (_bigHugeGiganticCanonBall == null) {
                            _bigHugeGiganticCanonBall = (Skill) SkillFactory.getSkill(400051008);
                        }
                        if (_howlingGale == null) {
                            _howlingGale = (Skill) SkillFactory.getSkill(400031003);
                        }
                        if (_demonicFrenzy == null) {
                            _demonicFrenzy = (Skill) SkillFactory.getSkill(400011010);
                        }
                        if (_blackMagicAlter == null) {
                            _blackMagicAlter = (Skill) SkillFactory.getSkill(400021047);
                        }
                        if (_overloadMode == null) {
                            _overloadMode = (Skill) SkillFactory.getSkill(400041029);
                        }
                        if (_Transition == null) {
                            _Transition = (Skill) SkillFactory.getSkill(3311002);
                        }
                        if (_퓨리어스차지 == null) {
                            _퓨리어스차지 = (Skill) SkillFactory.getSkill(400051042);
                        }
                        if (chr.getSkillLevel(5121054) > 0) { // Big Hughes Periodic Cannonball or Black Magic Alter
                            if (chr.getBuffedValue(BuffStats.Stimulate) != null) {
                                    chr.acaneAim += 800;
                                    if (chr.acaneAim >= 10000) {
                                        chr.acaneAim = 10000;
                                    }
                                    chr.send(MainPacketCreator.giveEnergyCharge(chr.acaneAim, 10000, 5120018, 10000, true, false));
                                }
                        }
                        if (chr.getSkillLevel(400051008) > 0) {
                            SkillStatEffect effect = _bigHugeGiganticCanonBall.getEffect(chr.getSkillLevel(400051008));

                            if (chr.lastBHGCGiveTime == 0) {
                                chr.lastBHGCGiveTime = System.currentTimeMillis();
                                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
                                chr.send(MainPacketCreator.giveBuff(400051008, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                            }
                            if (time - chr.lastBHGCGiveTime >= 25000) {
                                if (chr.BHGCCount < effect.getY()) {
                                    chr.BHGCCount++;
                                    List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                    statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
                                    chr.send(MainPacketCreator.giveBuff(400051008, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                                }
                                chr.lastBHGCGiveTime = time;
                            }
                        } else if (chr.getSkillLevel(400021047) > 0) {
                            SkillStatEffect effect = _blackMagicAlter.getEffect(chr.getSkillLevel(400021047));

                            if (chr.lastBHGCGiveTime == 0) {
                                chr.lastBHGCGiveTime = System.currentTimeMillis();
                                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
                                chr.send(MainPacketCreator.giveBuff(400021047, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                            }
                            if (time - chr.lastBHGCGiveTime >= effect.getStat("v") * 1000) {
                                if (chr.BHGCCount < effect.getY()) {
                                    chr.BHGCCount++;
                                    List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                    statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
                                    chr.send(MainPacketCreator.giveBuff(400021047, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                                }
                                chr.lastBHGCGiveTime = time;
                            }
                        }
                        if (chr.getSkillLevel(400031003) > 0) { // Howling Gayle
                            SkillStatEffect effect = _howlingGale.getEffect(chr.getSkillLevel(400031003));

                            if (chr.lastHowlingGaleTime == 0) {
                                chr.lastHowlingGaleTime = System.currentTimeMillis();
                                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                statups.add(new Triple<>(BuffStats.HowlingGale, chr.HowlingGaleCount, false));
                                chr.send(MainPacketCreator.giveBuff(400031003, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                            }
                            if (time - chr.lastHowlingGaleTime >= effect.getX() * 1000) {
                                if (chr.HowlingGaleCount < 2) {
                                    chr.HowlingGaleCount++;
                                    List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                    statups.add(new Triple<>(BuffStats.HowlingGale, chr.HowlingGaleCount, false));
                                    chr.send(MainPacketCreator.giveBuff(400031003, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                                }
                                chr.lastHowlingGaleTime = time;
                            }
                        }
                        if (chr.isActiveBuffedValue(400011010)) {
                            if (chr.lastDemonicFrenzyTime == 0) {
                                chr.lastDemonicFrenzyTime = System.currentTimeMillis();
                            }
                            SkillStatEffect effect = _demonicFrenzy.getEffect(chr.getSkillLevel(400011010));

                            if (time - chr.lastDemonicFrenzyTime >= effect.getU() * 1000) {
                                if (chr.getStat().getHp() > chr.getMaxHp() * (effect.getStat("q2") / 100.0D)) {
                                    
                                int x = chr.getTruePosition().x;
                                int y = chr.getTruePosition().y + 25;
                                   // int xMax = xMin + 500;
                                    SkillFactory.getSkill(400010010).getEffect(chr.getSkillLevel(400011010)).applyTo(chr, new Point(x, y));
                                }
                                chr.lastDemonicFrenzyTime = time;
                            }
                        }
                        if (chr.isActiveBuffedValue(400041029)) {
                            SkillStatEffect effect = _overloadMode.getEffect(chr.getSkillLevel(400041029));
                            int consumeMP = (int) (chr.getStat().getMaxMp() * (effect.getStat("q") / 100.0D)) + effect.getY();

                            if (chr.getStat().getMp() < consumeMP) {
                                chr.cancelBuffStats(400041029, BuffStats.OverloadMode);
                            } else {
                                chr.addMP(-consumeMP);
                            }
                        }
                        
                        if (chr.getJob() / 100 == 155 && !chr.isActiveBuffedValue(155101006) && chr.getArkGage() < 1000) {
                            chr.setArkGage(chr.getArkGage() + 13);
                            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                            statups.add(new Triple<>(BuffStats.ArkGage, 1, false));
                            chr.send(MainPacketCreator.giveBuff(0, 0, statups, null, chr.getStackSkills(),
                            0, chr));                            
                            statups.clear();
                            statups.add(new Triple<>(BuffStats.ArkChargeMable, 1, false));
                            chr.send(MainPacketCreator.giveBuff(0, 0, statups, null, chr.getStackSkills(),
                            0, chr));
                        } else if (chr.getJob() / 100 == 155 && chr.isActiveBuffedValue(155101006) && chr.getArkGage() > 0) {
                            chr.setArkGage(chr.getArkGage() - 23);
                            if (chr.getArkGage() <= 0) {
                                chr.setArkGage(0);
                                Map<SkillStatEffect, Long> bsvhs = new HashMap<>();
                                chr.getEffects().entrySet().stream().forEach((Entry<BuffStats, List<BuffStatsValueHolder>> effect1) -> {
                                    effect1.getValue().stream().filter((bsvh) -> (!bsvhs.containsKey(bsvh.effect) && bsvh.effect.getSourceId() == 155101006)).forEach((bsvh) -> {
                                        bsvhs.put(bsvh.effect, bsvh.startTime);
                                    });
                                });
                                if (bsvhs.size() > 0) {
                                    bsvhs.entrySet().stream().forEach((bsvh) -> {
                                        chr.cancelEffect(bsvh.getKey(), false, bsvh.getValue());
                                    });
                                }
                            } else {
                                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                statups.add(new Triple<>(BuffStats.ArkGage, 1, false));
                                chr.send(MainPacketCreator.giveBuff(0, 0, statups, null, chr.getStackSkills(),
                                0, chr));                            
                                statups.clear();
                                statups.add(new Triple<>(BuffStats.ArkChargeMable, 1, false));
                                chr.send(MainPacketCreator.giveBuff(0, 0, statups, null, chr.getStackSkills(),
                                0, chr));     
                            }
                        }
                    
                        if (chr.getSkillLevel(3311002) > 0) {
                            SkillStatEffect effect = _Transition.getEffect(chr.getSkillLevel(3311002));

                            if (chr.lastBHGCGiveTime == 0) {
                                chr.lastBHGCGiveTime = System.currentTimeMillis();
                                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
                                chr.send(MainPacketCreator.giveBuff(3311002, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                            }
                            if (time - chr.lastBHGCGiveTime >= (chr.getBuffedValue(BuffStats.IndieIliumStack, 3321034) == null ? 7000 : 3000)) {
                                if (chr.BHGCCount < 5) {
                                    chr.BHGCCount++;
                                    List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                    statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
                                    chr.send(MainPacketCreator.giveBuff(3311002, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                                }
                                chr.lastBHGCGiveTime = time;
                            }
                        }
                        if (chr.getSkillLevel(400051042) > 0) {
                            SkillStatEffect effect = _퓨리어스차지.getEffect(chr.getSkillLevel(400051042));

                            if (chr.lastHowlingGaleTime == 0) {
                                chr.lastHowlingGaleTime = System.currentTimeMillis();
                                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.HowlingGaleCount, false));
                                chr.send(MainPacketCreator.giveBuff(400051042, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                            }
                            if (time - chr.lastHowlingGaleTime >= 10000) {
                                if (chr.HowlingGaleCount < 5) {
                                    chr.HowlingGaleCount++;
                                    List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                                    statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.HowlingGaleCount, false));
                                    chr.send(MainPacketCreator.giveBuff(400051042, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                                }
                                chr.lastHowlingGaleTime = time;
                            }
                        }
                    }
                    if (map.getAllSummon().size() > 0) {
                        for (final MapleMapObject ss : map.getAllSummon()) {
                            MapleSummon summon = (MapleSummon) ss;
                            if (summon.getEndTime() <= time) {
                                if (summon.getSkill() == 14000027 || summon.getSkill() == 14100027 || summon.getSkill() == 14110029 || summon.getSkill() == 14120008) {
                                } else {
                                    summon.removeSummon(summon.getOwner().getMap());
                                }
                            }
                        }
                    }
                    if (map.getAllMonster().size() > 0) {
                        for (final MapleMapObject obj : map.getAllMonster()) {
                            final MapleMonster mob = (MapleMonster) obj;
                            if (mob.isAlive()) {
                                List<MonsterStatusEffect> cancelStatus = new ArrayList<>();
                                for (final Entry<MonsterStatus, MonsterStatusEffect> stat : mob.getStati().entrySet()) {
                                    if (stat.getValue().getPoison() != null) {
                                        if (time >= stat.getValue().getPoison().getCheckTime()) {
                                            stat.getValue().getPoison().pdamage(time);
                                        }
                                    }
                                    if (mob.isAlive()) {
                                        if (time >= stat.getValue().getEndTime()) {
                                            cancelStatus.add(stat.getValue());
                                        }
                                    }
                                }
                                for (int i = 0; i < cancelStatus.size(); i++) {
                                    if (mob.getFreezeStack() > 0) {
                                        mob.setFreezeStack(0);
                                    }
                                    try {
                                        cancelStatus.get(i).CancelEffect();
                                    } catch (Exception e) {

                                    }
                                }
                                if (cancelStatus.size() > 0) {
                                    cancelStatus.clear();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
