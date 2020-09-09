package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import constants.GameConstants;
import java.util.List;
import java.util.ArrayList;
import client.MapleClient;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Stats.PlayerStatList;
import client.Stats.PlayerStats;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import tools.RandomStream.Randomizer;
import tools.Pair;

public class StatsHandling {

    public static final void DistributeAP(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        final List<Pair<PlayerStatList, Long>> statupdate = new ArrayList<Pair<PlayerStatList, Long>>(2);
        c.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statupdate, true, chr.getJob(), c.getPlayer()));
        rh.skip(4);

        final PlayerStats stat = chr.getStat();

        if (chr.getRemainingAp() > 0) {
            switch (rh.readInt()) {
                case 64: // Str
                    if (stat.getStr() >= c.getPlayer().getMaxStats()) {
                        return;
                    }
                    stat.setStr(stat.getStr() + 1);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.STR, (long) stat.getStr()));
                    break;
                case 128: // Dex
                    if (stat.getDex() >= c.getPlayer().getMaxStats()) {
                        return;
                    }
                    stat.setDex(stat.getDex() + 1);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.DEX, (long) stat.getDex()));
                    break;
                case 256: // Int
                    if (stat.getInt() >= c.getPlayer().getMaxStats()) {
                        return;
                    }
                    stat.setInt(stat.getInt() + 1);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.INT, (long) stat.getInt()));
                    break;
                case 512: // Luk
                    if (stat.getLuk() >= c.getPlayer().getMaxStats()) {
                        return;
                    }
                    stat.setLuk(stat.getLuk() + 1);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.LUK, (long) stat.getLuk()));
                    break;
                case 2048: // HP
                    long MaxHP = stat.getMaxHp();
                    if (chr.getHpApUsed() >= 10000 || MaxHP >= 500000) {
                        return;
                    }
                    ISkill improvingMaxHP = null;
                    int improvingMaxHPLevel = 0;
                    if (chr.getJob() == 0) {
                        MaxHP += Randomizer.rand(8, 12);
                    } else if (chr.getJob() >= 100 && chr.getJob() <= 132) {
                        improvingMaxHP = SkillFactory.getSkill(1000001);
                        improvingMaxHPLevel = chr.getSkillLevel(improvingMaxHP);
                        MaxHP += Randomizer.rand(20, 24);
                        if (improvingMaxHPLevel >= 1) {
                            MaxHP += improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
                        }
                    } else if (chr.getJob() >= 200 && chr.getJob() <= 232) {
                        MaxHP += Randomizer.rand(6, 10);
                    } else if (chr.getJob() >= 300 && chr.getJob() <= 322) {
                        MaxHP += Randomizer.rand(16, 20);
                    } else if (chr.getJob() >= 400 && chr.getJob() <= 422) {
                        MaxHP += Randomizer.rand(20, 24);
                    } else if (chr.getJob() >= 500 && chr.getJob() <= 522) {
                        improvingMaxHP = SkillFactory.getSkill(5100000);
                        improvingMaxHPLevel = chr.getSkillLevel(improvingMaxHP);
                        MaxHP += Randomizer.rand(16, 20);
                        if (improvingMaxHPLevel >= 1) {
                            MaxHP += improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
                        }
                    } else if (chr.getJob() >= 1100 && chr.getJob() <= 1111) {
                        improvingMaxHP = SkillFactory.getSkill(11000000);
                        improvingMaxHPLevel = chr.getSkillLevel(improvingMaxHP);
                        MaxHP += Randomizer.rand(36, 42);
                        if (improvingMaxHPLevel >= 1) {
                            MaxHP += improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
                        }
                    } else if (chr.getJob() >= 1200 && chr.getJob() <= 1211) {
                        MaxHP += Randomizer.rand(15, 21);
                    } else if ((chr.getJob() >= 1300 && chr.getJob() <= 1311) || (chr.getJob() >= 1400 && chr.getJob() <= 1411)) {
                        MaxHP += Randomizer.rand(30, 36);
                    } else if (chr.getJob() == 3101 || (chr.getJob() >= 3120 && chr.getJob() <= 3122)) {
                        MaxHP += Randomizer.rand(30, 36);
                    } else {
                        MaxHP += Randomizer.rand(50, 100);
                    }
                    MaxHP = Math.min(500000, MaxHP);
                    chr.setHpApUsed(chr.getHpApUsed() + 1);
                    stat.setMaxHp((int) MaxHP);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXHP, (long) MaxHP));
                    break;
                case 8192: // MP
                    long MaxMP = stat.getMaxMp();
                    if (chr.getMpApUsed() >= 10000 && stat.getMaxMp() >= 500000) {
                        return;
                    }
                    if (chr.getJob() == 0) {
                        MaxMP += Randomizer.rand(6, 8);
                    } else if (chr.getJob() >= 100 && chr.getJob() <= 132) {
                        MaxMP += Randomizer.rand(2, 4);
                    } else if (chr.getJob() >= 200 && chr.getJob() <= 232) {
                        ISkill improvingMaxMP = SkillFactory.getSkill(2000001);
                        int improvingMaxMPLevel = chr.getSkillLevel(improvingMaxMP);
                        if (improvingMaxMPLevel >= 1) {
                            MaxMP += Randomizer.rand(18, 20) + improvingMaxMP.getEffect(improvingMaxMPLevel).getY();
                        } else {
                            MaxMP += Randomizer.rand(18, 20);
                        }
                    } else if (chr.getJob() >= 300 && chr.getJob() <= 322) {
                        MaxMP += Randomizer.rand(10, 12);
                    } else if (chr.getJob() >= 400 && chr.getJob() <= 422) {
                        MaxMP += Randomizer.rand(10, 12);
                    } else if (chr.getJob() >= 500 && chr.getJob() <= 522) {
                        MaxMP += Randomizer.rand(10, 12);
                    } else if (chr.getJob() >= 1100 && chr.getJob() <= 1111) {
                        MaxMP += Randomizer.rand(6, 9);
                    } else if (chr.getJob() >= 1200 && chr.getJob() <= 1211) {
                        ISkill improvingMaxMP = SkillFactory.getSkill(12000000);
                        int improvingMaxMPLevel = chr.getSkillLevel(improvingMaxMP);
                        MaxMP += Randomizer.rand(33, 36);
                        if (improvingMaxMPLevel >= 1) {
                            MaxMP += improvingMaxMP.getEffect(improvingMaxMPLevel).getY();
                        }
                    } else if ((chr.getJob() >= 1300 && chr.getJob() <= 1311) || (chr.getJob() >= 1400 && chr.getJob() <= 1411)) { // Wind Breaker and Night Walker
                        MaxMP += Randomizer.rand(21, 24);
                    } else {
                        MaxMP += Randomizer.rand(50, 100);
                    }
                    MaxMP = Math.min(500000, MaxMP);
                    chr.setMpApUsed(chr.getMpApUsed() + 1);
                    stat.setMaxMp((int) MaxMP);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXMP, (long) MaxMP));
                    break;
                default:
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                    return;
            }
            chr.setRemainingAp(chr.getRemainingAp() - 1);
            statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.AVAILABLEAP, (long) chr.getRemainingAp()));
            c.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statupdate, true, chr.getJob(), c.getPlayer()));
            chr.getStat().recalcLocalStats(chr);
            if (GameConstants.isDemonAvenger(chr.getJob())) {
                chr.getStat().giveDemonWatk(chr);
            }
        }
    }

    public static final void DistributeSP(ReadingMaple rh, final int skillid, final MapleClient c, final MapleCharacter chr) {
        int sid = skillid;
        if (sid == 80001044 || sid == 80001137 || sid == 80001144 || (sid >= 80001000 && sid <= 80001033)
                || (sid >= 80001037 && sid <= 80001039) || (sid >= 80001082 && sid <= 80001090)) {
            chr.dropMessage(1, "You cannot upload this skill.");
            chr.ea();
            return;
        }
        final ISkill skill = SkillFactory.getSkill(skillid);
        if (skill.hasRequiredSkill()) {
            if (chr.getOriginSkillLevel(SkillFactory.getSkill(skill.getRequiredSkillId())) < skill.getRequiredSkillLevel()) {
                return;
            }
        }
        final int curLevel = chr.getOriginSkillLevel(skill);
        final int addskilllevel = rh.readInt();
        chr.gainSP(-addskilllevel, GameConstants.getSkillBook(skillid / 10000));
        chr.updateSingleStat(PlayerStatList.AVAILABLESP, chr.getRemainingSp());
        chr.send(MainPacketCreator.updateSp(chr, false));
        chr.changeSkillLevel(skill, (byte) (curLevel + addskilllevel), chr.getMasterLevel(skill));
        chr.checkInduerense();
        chr.checkMercedesRecovery();
        chr.setNullSelfRecovery();
        chr.checkSelfRecovery();
    }

    public static final void AutoAssignAP(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        rh.skip(4);
        final int count = rh.readInt();
        final int PrimaryStat = rh.readInt();
        final int amount = rh.readInt();
        final int SecondaryStat = count == 2 ? rh.readInt() : 0;
        final int amount2 = count == 2 ? rh.readInt() : 0;
        if (amount < 0 || amount2 < 0) {
            return;
        }
        final PlayerStats playerst = chr.getStat();

        List<Pair<PlayerStatList, Long>> statupdate = new ArrayList<Pair<PlayerStatList, Long>>(2);
        c.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statupdate, true, chr.getJob(), c.getPlayer()));
        if (chr.getRemainingAp() == amount + amount2 || GameConstants.isXenon(chr.getJob())) {
            switch (PrimaryStat) {
                case 64: // Str
                    if (playerst.getStr() + amount > 999) {
                        return;
                    }
                    playerst.setStr(playerst.getStr() + amount);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.STR, (long) playerst.getStr()));
                    break;
                case 128: // Dex
                    if (playerst.getDex() + amount > 999) {
                        return;
                    }
                    playerst.setDex(playerst.getDex() + amount);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.DEX, (long) playerst.getDex()));
                    break;
                case 256: // Int
                    if (playerst.getInt() + amount > 999) {
                        return;
                    }
                    playerst.setInt(playerst.getInt() + amount);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.INT, (long) playerst.getInt()));
                    break;
                case 512: // Luk
                    if (playerst.getLuk() + amount > 999) {
                        return;
                    }
                    playerst.setLuk(playerst.getLuk() + amount);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.LUK, (long) playerst.getLuk()));
                    break;
                case 2048: //Max Hp
                    if (playerst.getMaxHp() + (amount * 30) > 500000) {
                        return;
                    }
                    playerst.setMaxHp(playerst.getMaxHp() + (amount * 30));
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXHP, (long) playerst.getMaxHp()));
                    break;
                default:
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                    return;
            }
            switch (SecondaryStat) {
                case 64: // Str
                    if (playerst.getStr() + amount2 > 999) {
                        return;
                    }
                    playerst.setStr(playerst.getStr() + amount2);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.STR, (long) playerst.getStr()));
                    break;
                case 128: // Dex
                    if (playerst.getDex() + amount2 > 999) {
                        return;
                    }
                    playerst.setDex(playerst.getDex() + amount2);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.DEX, (long) playerst.getDex()));
                    break;
                case 256: // Int
                    if (playerst.getInt() + amount2 > 999) {
                        return;
                    }
                    playerst.setInt(playerst.getInt() + amount2);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.INT, (long) playerst.getInt()));
                    break;
                case 512: // Luk
                    if (playerst.getLuk() + amount2 > 999) {
                        return;
                    }
                    playerst.setLuk(playerst.getLuk() + amount2);
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.LUK, (long) playerst.getLuk()));
                    break;
                case 2048: //Max Hp
                    if (playerst.getMaxHp() + (amount * 30) > 500000) {
                        return;
                    }
                    playerst.setMaxHp(playerst.getMaxHp() + (amount * 30));
                    statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXHP, (long) playerst.getMaxHp()));
                    break;
            }
            chr.setRemainingAp(chr.getRemainingAp() - (amount + amount2));
            statupdate.add(new Pair<PlayerStatList, Long>(PlayerStatList.AVAILABLEAP, (long) chr.getRemainingAp()));
            c.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statupdate, true, chr.getJob(), c.getPlayer()));
            chr.getStat().recalcLocalStats(chr);
            if (GameConstants.isDemonAvenger(chr.getJob())) {
                chr.getStat().giveDemonWatk(chr);
            }
        }
    }
}
