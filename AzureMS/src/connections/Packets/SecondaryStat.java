package connections.Packets;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.Character.MapleCharacter;
import client.ItemInventory.IItem;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import client.Skills.SkillStatEffect;
import client.Skills.StackedSkillEntry;
import client.Stats.BuffStats;
import constants.ServerConstants;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import tools.Triple;
import tools.RandomStream.Randomizer;

public class SecondaryStat {

    private static boolean check(BigInteger f, BuffStats b) {
        if (f.and(b.getBigValue()).equals(BigInteger.ZERO) == false) {
            return true;
        }

        return false;
    }

    private static boolean check(BigInteger f, BigInteger b) {
        if (f.and(b).equals(BigInteger.ZERO) == false) {
            return true;
        }

        return false;
    }

    private static int get(Map<BuffStats, Integer> m, BuffStats b) {
        Integer ret = m.get(b);

        return ret == null ? 0 : (int) ret;
    }

    private static BigInteger s_uFilter = null;
    private static BigInteger s_uFilter2 = null;
    private static BigInteger s_uFilter3 = null;
    private static BigInteger s_uFilter4 = null;

    private static boolean IsEnDecode4Byte(BigInteger f) {
        if (aTS_StatFlag2(f)) {
            return true;
        }

        if (s_uFilter == null) {
            s_uFilter = BigInteger.ZERO;
            s_uFilter = s_uFilter.or(BuffStats.CarnivalDefence.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.SpiritLink.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.DojangLuckyBonus.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.SoulGazeCriDamR.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.PowerTransferGauge.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.ReturnTeleport.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.ShadowPartner.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.SetBaseDamage.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.QuiverCatridge.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.ImmuneBarrier.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.NaviFlying.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.Dance.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.SetBaseDamageByBuff.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.DotHealHPPerSecond.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.MagnetArea.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.ReflectDamR.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.HolyUnity.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.AranSmashSwing.getBigValue());
            s_uFilter = s_uFilter.or(BuffStats.MegaSmasher.getBigValue());
        }

        return check(s_uFilter, f);
    }

    private static boolean IsMovementAffectingStat(BigInteger f) {
        if (s_uFilter2 == null) {
            s_uFilter2 = BigInteger.ZERO;
            s_uFilter2 = s_uFilter2.or(BuffStats.Stun.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Weakness.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Slow.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Morph.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Ghost.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.BasicStatUp.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Attract.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Dash_Speed.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Dash_Jump.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Flying.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Frozen.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Frozen2.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Lapidification.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.IndieSpeed.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.IndieJump.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.KeyDownMoving.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.EnergyCharged.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Mechanic.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Magnet.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.MagnetArea.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.VampDeath.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.VampDeathSummon.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.GiveMeHeal.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.DarkTornado.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.NewFlying.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.NaviFlying.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.UserControlMob.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.Dance.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.SelfWeakness.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.BattlePvP_Helena_WindSpirit.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.BattlePvP_LeeMalNyun_ScaleUp.getBigValue());
            s_uFilter2 = s_uFilter2.or(BuffStats.TouchMe.getBigValue());
            // s_uFilter2 = s_uFilter2.or(BuffStats.IndieForceSpeed.getBigValue());
            // s_uFilter2 = s_uFilter2.or(BuffStats.IndieForceJump.getBigValue());
        }
        return check(s_uFilter2, f);
    }

    private static boolean aTS_StatFlag(BigInteger f) {
        if (s_uFilter3 == null) {
            s_uFilter3 = BigInteger.ZERO;
            s_uFilter3 = s_uFilter3.or(BuffStats.EnergyCharged.getBigValue());
            s_uFilter3 = s_uFilter3.or(BuffStats.Dash_Speed.getBigValue());
            s_uFilter3 = s_uFilter3.or(BuffStats.Dash_Jump.getBigValue());
            s_uFilter3 = s_uFilter3.or(BuffStats.PartyBooster.getBigValue());
            s_uFilter3 = s_uFilter3.or(BuffStats.GuidedBullet.getBigValue());
            s_uFilter3 = s_uFilter3.or(BuffStats.Undead.getBigValue());
            s_uFilter3 = s_uFilter3.or(BuffStats.RideVehicleExpire.getBigValue());
        }
        return check(s_uFilter3, f);
    }

    private static boolean aTS_StatFlag2(BigInteger f) {
        if (s_uFilter4 == null) {
            s_uFilter4 = BigInteger.ZERO;
            s_uFilter4 = s_uFilter4.or(BuffStats.MonsterRiding.getBigValue());
        }
        return check(s_uFilter4, f);
    }

    public static byte[] encodeForRemote(final MapleCharacter chr, List<Triple<BuffStats, Integer, Boolean>> statups) {
        WritingPacket packet = new WritingPacket();
        boolean infusion = false;

        packet.writeShort(SendPacketOpcode.GIVE_FOREIGN_BUFF.getValue());
        packet.writeInt(chr.getId());

        BigInteger f = PacketProvider.writeBuffStatsSub(packet, statups/* , blockStat */);
        BuffStats b = null;

        if (check(f, BuffStats.PartyBooster)) {
            infusion = true;
        }

        b = BuffStats.Speed;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.ComboCounter;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.WeaponCharge;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ElementalCharge;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
        }
        b = BuffStats.Stun;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Shock;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.Darkness;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Seal;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Weakness;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.WeaknessMdamage;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Curse;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Slow;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PvPRaceEffect;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        b = BuffStats.TimeBomb;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Team;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.DisOrder;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Thread;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Poison;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
        }
        b = BuffStats.Poison;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ShadowPartner;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Morph;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Ghost;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
        }
        b = BuffStats.Attract;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Magnet;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.MagnetArea;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.NoBulletConsume;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BanMap;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Barrier;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DojangShield;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ReverseInput;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.KadenaStack;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.RespectPImmune;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.RespectMImmune;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DefenseAtt;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DefenseState;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DojangBerserk;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.RepeatEffect;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.StopPortion;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.StopMotion;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Fear;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.MagicShield;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Frozen;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Frozen2;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Web;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DrawBack;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.FinalCut;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.OnCapsule;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.Mechanic;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Inflation;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Explosion;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DarkTornado;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.AmplifyDamage;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.HideAttack;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DevilishPower;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SpiritLink;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Event;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Event2;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DeathMark;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PainMark;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Lapidification;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.VampDeath;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.VampDeathSummon;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.VenomSnake;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PyramidEffect;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.KillingPoint;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.PinkbeanRollingGrade;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.IgnoreTargetDEF;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Invisible;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Judgement;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.KeyDownAreaMoving;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.StackBuff;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
        }
        b = BuffStats.BlessOfDarkness;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Larkness;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ReshuffleSwitch;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BulletParty;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SpecialAction;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.StopForceAtomInfo;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SoulGazeCriDamR;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PowerTransferGauge;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.AffinitySlug;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SoulExalt;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.HiddenPieceOn;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SmashStack;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.MobZoneState;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.GiveMeHeal;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.TouchMe;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Contagion;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Contagion;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ComboUnlimited;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IgnorePCounter;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IgnoreAllCounter;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IgnorePImmune;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IgnoreAllImmune;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.FinalJudgement;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.KnightsAura;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IceAura;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.FireAura;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.HeavensDoor;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DamAbsorbShield;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.AntiMagicShell;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.NotDamaged;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BleedingToxin;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.WindBreakerFinal;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IgnoreMobDamR;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Asura;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.UnityOfPower;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Stimulate;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ReturnTeleport;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.CapDebuff;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.OverloadCount;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.FireBomb;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SurplusSupply;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.NewFlying;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.IliumeFly;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.NaviFlying;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.AmaranthGenerator;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.CygnusElementSkill;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.StrikerHyperElectric;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.EventPointAbsorb;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.EventAssemble;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Albatross;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Translucence;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PoseType;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.LightOfSpirit;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ElementSoul;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.GlimmeringTime;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Reincarnation;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Beholder;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.QuiverCatridge;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ArmorPiercing;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ZeroAuraStr;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ZeroAuraSpd;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ImmuneBarrier;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ImmuneBarrier;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.FullSoulMP;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
            packet.writeInt(0);
        }

        b = BuffStats.AntiMagicShell;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.Dance;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SpiritGuard;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ComboTempest;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.HalfstatByDebuff;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ComplusionSlant;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.JaguarSummoned;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BMageAura;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.DarkLighting;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.MASTER_OF_DEATH;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.AuraWeapon;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.MegaSmasher;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Spotlight;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.OverloadMode;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.UnionAuraBlow;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.LightningUnion;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.HolyUnity;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BlessedHammer;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ReadyToDie;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SpreadThrow;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ShadowAssault;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.OverDrive;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Transform;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BlessMark;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SlowAttack;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.SplitArrow;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BlitzShield;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Pray;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PsychicTornado;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
        }
        b = BuffStats.TrueSniping;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.ReflectDamR;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.FireBarrier;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.KeyDownMoving;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.MichaelSoulLink;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.KinesisPsychicEnergeShield;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.BladeStance;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Fever;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.AdrenalinBoost;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.RWBarrier;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.RWMagnumBlow;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.Stigma;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }
        b = BuffStats.PoseType;
        if (check(f, b)) {
            packet.write(chr.getBuffedValue(b));
        }
        b = BuffStats.BlessedHammerBig;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        b = BuffStats.HarmonyLink;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        b = BuffStats.FastCharge;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        b = BuffStats.ArkTransform;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        b = BuffStats.GrandCrossSize;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        packet.write(0);// DefenseAtt
        packet.write(0);// DefenseState
        packet.write(0);// PVPDamage
        packet.writeInt(0);
        packet.writeInt(0);

        b = BuffStats.ZeroAuraStr;
        if (check(f, b)) {
            packet.write(0);
        }
        b = BuffStats.ZeroAuraSpd;
        if (check(f, b)) {
            packet.write(0);
        }

        b = BuffStats.BMageAura;
        if (check(f, b)) {
            packet.write(0);
        }
        b = BuffStats.BattlePvP_Helena_Mark;
        if (check(f, b)) {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        b = BuffStats.BattlePvP_LangE_Protection;
        if (check(f, b)) {
            packet.writeInt(0);
            packet.writeInt(0);
        }
        b = BuffStats.MichaelSoulLink;
        if (check(f, b)) {
            packet.writeInt(0);
            packet.write(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }
        b = BuffStats.HolyUnity;
        if (check(f, b)) {
            packet.writeShort(chr.getBuffedSkillEffect(b).getSkillLevel());
        }
        b = BuffStats.AdrenalinBoost;
        if (check(f, b)) {
            packet.write(chr.royalGuard);
            packet.writeInt(0);
        }
        b = BuffStats.Stigma;
        if (check(f, b)) {
            packet.writeInt(0);
        }

        b = BuffStats.BlessMark;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
        }

        b = BuffStats.StopForceAtomInfo;
        if (check(f, b)) {
            int skillid = chr.getBuffedSkillEffect(b).getSourceId();
            MapleInventory equip = chr.getInventory(MapleInventoryType.EQUIPPED);
            IItem weapon = equip.getItem((byte) -11);
            if (skillid != 61101002 && skillid != 61110211) {
                packet.writeInt(skillid == 61121217 ? 4 : 2); // 스킬구분
                packet.writeInt(5); // 머리위에 뜨는 무기의 갯수
                packet.writeInt(weapon.getItemId()); // 착용중인 두손검
                packet.writeInt(5); // AttackCount
                for (int j = 0; j < 5; j++) {
                    packet.writeInt(0);
                }
            } else {
                packet.writeInt(skillid == 61110211 ? 3 : 1); // 스킬구분
                packet.writeInt(3); // 머리위에 뜨는 무기의 갯수
                packet.writeInt(weapon.getItemId()); // 착용중인 두손검
                packet.writeInt(3); // AttackCount
                for (int j = 0; j < 3; j++) {
                    packet.writeInt(0);
                }
            }
        } else {
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
            packet.writeInt(0);
        }

        for (Triple<BuffStats, Integer, Boolean> stat : statups) {
            if (!stat.getThird() && aTS_StatFlag(stat.first.getBigValue())) {
                packet.writeInt(stat.getSecond());
                packet.writeInt(chr.getBuffedSkillEffect(stat.first).getSourceId());
                packet.write(!infusion ? 1 : 0);
                if (chr.getBuffedSkillEffect(stat.first).getSourceId() == 33001001) {
                    packet.write(1);
                } else {
                    packet.writeInt(!infusion ? 1 : 0);
                }
                if (infusion) {
                    packet.write(0);
                    packet.writeInt(0);
                }
                packet.writeShort(chr.getBuffedSkillEffect(stat.first).getDuration());
            }
        }

        b = BuffStats.MonsterRiding;
        if (check(f, b)) {
            packet.writeInt(chr.getBuffedValue(b));
            packet.writeInt(chr.getBuffedSkillEffect(b).getSourceId());
            packet.write(0);
            packet.writeInt(0);
        }

        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            if (statup.getThird()) {
                packet.writeInt(chr.getStackSkills().get(statup.getFirst()).size());
                for (StackedSkillEntry sse : chr.getStackSkills().get(statup.getFirst())) {
                    packet.writeInt(sse.getSkillId());
                    packet.writeInt(sse.getValue());
                    packet.writeInt(sse.getTime());
                    packet.writeInt(0);
                    packet.writeInt(sse.getBuffLength());
                    packet.writeInt(0);
                    packet.writeInt(0);
                }
            }
        }

        packet.write(0);

        packet.writeShort(0);
        packet.write(0);

        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        return packet.getPacket();
    }

    public static byte[] t(int buffid, BigInteger b) {
        WritingPacket p = new WritingPacket();

        p.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        Map<BuffStats, Integer> m = new HashMap<>();
        p.writeInt(0);
        p.writeInt(0);
        BigInteger f = PacketProvider.writeBuffMask(p, b);
        if (IsEnDecode4Byte(f)) {
            p.writeInt(1);
        } else {
            p.writeShort(1);
        }
        p.writeInt(buffid);
        p.writeInt(Integer.MAX_VALUE);
        p.write0(200);
        return p.getPacket();
    }

    public static byte[] encodeForLocal(int buffid, int bufflength, List<Triple<BuffStats, Integer, Boolean>> statups,
            SkillStatEffect effect, Map<BuffStats, List<StackedSkillEntry>> stacks, int animationTime,
            MapleCharacter chr) {
        WritingPacket p = new WritingPacket();

        p.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());

        Map<BuffStats, Integer> m = new HashMap<>();
        p.writeInt(0);
        p.writeInt(0);
        BigInteger f = PacketProvider.writeBuffMask(p, statups);

        if (check(f, BuffStats.BlessMark)) {
            buffid = 152000009;
        }

        if (aTS_StatFlag(f)) {
            return encodeForPirate(statups, bufflength / 1000, buffid);
        }

        BuffStats b = null;

        for (Triple<BuffStats, Integer, Boolean> t : statups) {
            m.put(t.first, t.second);
        }

        b = BuffStats.STR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.INT;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DEX;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.LUK;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PAD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PDD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MAD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ACC;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Craft;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Speed;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EnergyBurst;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Jump;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EMHP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EMMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EPAD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EMAD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EPDD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MagicGuard;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DarkSight;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Booster;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PowerGuard;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Guard;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.GuidedArrow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessMark;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MaxHP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MaxMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulArrow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Stun;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Shock;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Poison;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Seal;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Darkness;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboCounter;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.WeaponCharge;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ElementalCharge;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HolySymbol;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MesoUp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ShadowPartner;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PickPocket;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MesoGuard;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Thaw;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Weakness;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.WeaknessMdamage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Curse;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Slow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TimeBomb;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BuffLimit;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Team;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DisOrder;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Thread;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Morph;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Ghost;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Regen;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BasicStatUp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Stance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SharpEyes;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ManaReflection;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Attract;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Magnet;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MagnetArea;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NoBulletConsume;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.StackBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Trinity;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Infinity;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AdvancedBless;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IllusionStep;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Blind;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Concentration;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BanMap;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MaxLevelBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Barrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DojangShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReverseInput;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MesoUpByItem;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ItemUpByItem;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RespectPImmune;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RespectMImmune;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DefenseAtt;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KadenaStack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DefenseState;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DojangBerserk;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DojangInvincible;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulMasterFinal;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.WindBreakerFinal;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ElementalReset;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HideAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EventRate;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboAbilityBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboDrain;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboBarrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PartyBarrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BodyPressure;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RepeatEffect;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ExpBuffRate;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.StopPortion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.StopMotion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Fear;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MagicShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MagicResistance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulStone;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Flying;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NewFlying;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NaviFlying;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Frozen;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Frozen2;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Web;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Enrage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NotDamaged;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FinalCut;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HowlingAttackDamage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BeastFormDamageUp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Dance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.OnCapsule;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HowlingCritical;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HowlingMaxMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HowlingDefence;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HowlingEvasion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PinkbeanMinibeenMove;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Sneak;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Mechanic;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DrawBack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BeastFormMaxHP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Dice;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessingArmor;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessingArmorIncPAD;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DamR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TeleportMasteryOn;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CombatOrders;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Beholder;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DispelItemOption;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DispelItemOptionByField;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Inflation;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.OnixDivineProtection;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Bless;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Explosion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DarkTornado;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncMaxHP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncMaxMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PVPDamage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PVPDamageSkill;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PvPScoreBonus;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PvPInvincible;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PvPRaceEffect;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HolyMagicShell;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.InfinityForce;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AmplifyDamage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KeyDownTimeIgnore;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MasterMagicOn;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AsrRByItem;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TerR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DamAbsorbShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Roulette;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Event;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SpiritLink;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CriticalBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DropRate;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PlusExpRate;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ItemInvincible;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ItemCritical;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ItemEvade;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Event2;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.VampiricTouch;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DDR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncTerR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncAsrR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DeathMark;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PainMark;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.UsefulAdvancedBless;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Lapidification;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.VampDeath;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.VampDeathSummon;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.VenomSnake;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CarnivalAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CarnivalDefence;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CarnivalExp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SlowAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PyramidEffect;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HollowPointBullet;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KeyDownMoving;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KeyDownAreaMoving;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CygnusElementSkill;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnoreTargetDEF;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Invisible;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReviveOnce;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AntiMagicShell;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EnrageCr;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EnrageCrDamMin;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessOfDarkness;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.LifeTidal;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Judgement;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DojangLuckyBonus;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HitCriDamR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Larkness;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SmashStack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReshuffleSwitch;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SpecialAction;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArcaneAim;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.StopForceAtomInfo;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulGazeCriDamR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulRageCount;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PowerTransferGauge;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AffinitySlug;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulExalt;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HiddenPieceOn;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BossShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MobZoneState;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.GiveMeHeal;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TouchMe;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Contagion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboUnlimited;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnorePCounter;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnoreAllCounter;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnorePImmune;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnoreAllImmune;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FinalJudgement;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KnightsAura;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IceAura;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FireAura;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.VengeanceOfAngel;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HeavensDoor;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Preparation;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BullsEye;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncEffectHPPotion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncEffectMPPotion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FullSoulMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulSkillDamageUp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BleedingToxin;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnoreMobDamR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Asura;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FlipTheCoin;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.UnityOfPower;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Stimulate;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReturnTeleport;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CapDebuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DropRIncrease;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IgnoreMobpdpR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BdR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Exceed;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DiabolikRecovery;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FinalAttackProp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ExceedOverload;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DevilishPower;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.OverloadCount;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BuckShot;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FireBomb;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HalfstatByDebuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SurplusSupply;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SetBaseDamage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AmaranthGenerator;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.StrikerHyperElectric;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EventPointAbsorb;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.EventAssemble;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.StormBringer;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ACCR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DEXR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Albatross;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Translucence;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PoseType;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.LightOfSpirit;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ElementSoul;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.GlimmeringTime;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Restoration;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboCostInc;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ChargeBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TrueSight;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CrossOverChain;
        if (check(f, b)) {
//            if (IsEnDecode4Byte(f)) {
//                p.writeInt(get(m, b));
//            } else {
//                p.writeShort((short) get(m, b));
//            }
            p.writeShort(10);
            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ChillingStep;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Reincarnation;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DotBasedBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessEnsenble;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ExtremeArchery;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.QuiverCatridge;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AdvancedQuiver;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.UserControlMob;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ImmuneBarrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArmorPiercing;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ZeroAuraStr;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ZeroAuraSpd;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CriticalGrowing;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PASS_PINDER_SHAPE;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.QuickDraw;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BowMasterConcentration;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TimeFastABuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TimeFastBBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.GatherDropR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AimBox2D;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CursorSniping;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncMonsterBattleCaptureRate;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DebuffTolerance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DotHealHPPerSecond;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SpiritGuard;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PreReviveOnce;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SetBaseDamageByBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.LimitMP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BulletParty;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReflectDamR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboTempest;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MHPCutR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MMPCutR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SelfWeakness;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ElementDarkness;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FlareTrick;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FlameDischarge;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Dominion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SiphonVitality;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DarknessAscension;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BossWaitingLinesBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DamageReduce;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ShadowServant;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ShadowIllusion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AddAttackCount;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComplusionSlant;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.JaguarSummoned;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.JaguarCount;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SSFShootingAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DevilCry;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ShieldAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BMageAura;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DarkLighting;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MASTER_OF_DEATH;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BMageDeath;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BombTime;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NoDebuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.XenonAegisSystem;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PinPointRocket;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AngelicBursterSoulSeeker;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HiddenPossession;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NightWalkerBat;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NightLordMark;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.WizardIgnite;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_Helena_Mark;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_Helena_WindSpirit;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_LangE_Protection;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_LeeMalNyun_ScaleUp;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_Revive;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PinkbeanAttackBuff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RandAreaAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_Mike_Shield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BattlePvP_Mike_Bugle;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PinkbeanRelax;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PinkbeanYoYoStack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NextAttackEnhance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AranBeyonderDamAbsorb;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AranCombotempastOption;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.NautilusFinalAttack;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ViperTimeLeap;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RoyalGuardState;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RoyalGuardPrepare;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MichaelSoulLink;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MichaelStanceLink;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TriflingWhimOnOff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SerpentScrewOnOff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AddRangeOnOff;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KinesisPsychicPoint;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KinesisPsychicOver;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KinesisPsychicShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KinesisIncMastery;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KinesisPsychicEnergeShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BladeStance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DebuffActiveSkillHPCon;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.DebuffIncHP;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BowMasterMortalBlow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AngelicBursterSoulResonance;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Fever;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RpSiksin;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TeleportMasteryRange;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FireBarrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ChangeFoxMan;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FixCoolTime;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IncMobRateDummy;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AdrenalinBoost;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AranSmashSwing;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AranDrain;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AranBoostEndHunt;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HiddenHyperLinkMaximization;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWCylinder;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWCombination;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWMagnumBlow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWBarrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWBarrierHeal;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWMaximizeCannon;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWOverHeat;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RWMovingEvar;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Stigma;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.InstallMaha;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.AuraWeapon;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.PsychicTornado;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.MegaSmasher;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.OverloadMode;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Spotlight;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.UnionAuraBlow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HowlingGale;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.LightningUnion;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Ellision;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }
        
        b = BuffStats.LightOrDark;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }
        
        b = BuffStats.ShadowSpear;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BigHugeGiganticCanonBall;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HolyUnity;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessedHammer;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlessedHammerBig;
        if (check(f, b)) {
            p.writeShort(get(m, b));
            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReadyToDie;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SpreadThrow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ShadowAssault;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.KillMonster;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.OverDrive;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Transform;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SplitArrow;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.BlitzShield;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Pray;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.TrueSniping;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ReflectDamR;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }
        b = BuffStats.IliumeFly;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.Slpidia;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CraftOf;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.HarmonyLink;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.FastCharge;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.CrystalChargeMax;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkGage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkTransform;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkChargeMable;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkChargeMableRed;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkChargeMableBlue;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkChargeMableGRAY;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkComeDie;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ArkBattle;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ChargeSpellAmplification;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.ComboInstings;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.WindWall;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.GrandCrossSize;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.QuiverFullCBuster;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SwordOfSoulLIght;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.GrandCross;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.RelicGage;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.IndieObsidiunbarrier;
        if (check(f, b)) {
            if (IsEnDecode4Byte(f)) {
                p.writeInt(get(m, b));
            } else {
                p.writeShort((short) get(m, b));
            }

            p.writeInt(buffid);
            p.writeInt(bufflength);
        }

        b = BuffStats.SoulMP;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.writeInt(0);
        }

        b = BuffStats.FullSoulMP;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        p.writeShort(0); // size
        for (int i = 0; i < 0; i++) {
            p.writeInt(0); // -key
            p.write(0); // bEnable
        }

        p.write(0); // nDefenseAtt
        p.write(0); // nDefenseState
        p.write(0); // nPVPDamage
        p.writeInt(0);

        b = BuffStats.Dice;
        if (check(f, b)) {
            // TODO FIND OUT
            for (int i = 0; i < 22; i++) {
                p.writeInt(0);
            }
        }

        b = BuffStats.KillingPoint;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(chr.KillingPoint);
        }

        b = BuffStats.PinkbeanRollingGrade;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.Judgement;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.StackBuff;
        if (check(f, b)) {
            int stack = 0;
            // TODO FIND OUT
            switch (buffid) {
                case 36111003:
                    stack = chr.dualBrid;
                    break;
                default:
                    stack = chr.acaneAim;
                    break;
            }
            p.write(stack);
        }

        b = BuffStats.Trinity;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.ElementalCharge;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(chr.ELEMENTAL_CHARGE);
            p.writeShort(0);
            p.write(0);
            p.write(0);
        }

        b = BuffStats.LifeTidal;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.AntiMagicShell;
        if (check(f, b)) {
            // TODO FIND OUT
            if (buffid == 27111004) {
                p.write(0);
            } else {
                p.write(1);
            }
        }

        b = BuffStats.Larkness;
        if (check(f, b)) {
            // TODO FIND OUT

            for (int i = 0; i < 2; i++) {
                p.writeInt(chr.luminusskill[i]);
                p.writeInt(Randomizer.nextInt());
            }

            p.writeInt(-1);
            p.writeInt(10000);
        }

        b = BuffStats.IgnoreTargetDEF;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.lightning);
        }

        b = BuffStats.StopForceAtomInfo;
        if (check(f, b)) {
            // TODO FIND OUT
            MapleInventory equip = chr.getInventory(MapleInventoryType.EQUIPPED);
            IItem weapon = equip.getItem((byte) -11);
            if (buffid != 61101002 && buffid != 61110211) {
                p.writeInt(buffid == 61121217 ? 4 : 2); // 스킬구분
                p.writeInt(5); // 머리위에 뜨는 무기의 갯수
                p.writeInt(weapon.getItemId()); // 착용중인 두손검
                p.writeInt(5); // AttackCount
                for (int i = 0; i < 5; i++) {
                    p.writeInt(0);
                }
            } else {
                p.writeInt(buffid == 61110211 ? 3 : 1); // 스킬구분
                p.writeInt(3); // 머리위에 뜨는 무기의 갯수
                p.writeInt(weapon.getItemId()); // 착용중인 두손검
                p.writeInt(3); // AttackCount
                for (int i = 0; i < 3; i++) {
                    p.writeInt(0);
                }
            }
        }

        b = BuffStats.SmashStack;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.MobZoneState;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0); // send , and last is 0
        }

        b = BuffStats.Slow;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.IceAura;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.KnightsAura;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.IgnoreMobpdpR;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.BdR;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.DropRIncrease;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.write(0);
        }

        b = BuffStats.PoseType;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.Beholder;
        if (check(f, b)) {
            p.writeInt(chr.getSkillLevel(1310013) > 0 ? 1310013 : 0);
            p.writeInt(0);
        }

        b = BuffStats.CrossOverChain;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.Reincarnation;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(effect.getZ());
        }

        b = BuffStats.ExtremeArchery;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.writeInt(0);
        }

        b = BuffStats.QuiverCatridge;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.quivermode);
        }

        b = BuffStats.ImmuneBarrier;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.ZeroAuraStr;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.ZeroAuraSpd;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
        }

        b = BuffStats.ArmorPiercing;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.SharpEyes;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.AdvancedBless;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.DotHealHPPerSecond;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.SpiritGuard;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(get(m, b));
        }

        b = BuffStats.KnockBack;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.writeInt(0);
        }

        b = BuffStats.ShieldAttack;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.SSFShootingAttack;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.BMageAura;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.write(1);
        }

        b = BuffStats.BattlePvP_Helena_Mark;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.PinkbeanAttackBuff;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.RoyalGuardState;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.royalGuard);
            p.writeInt(chr.royalGuard);
        }

        b = BuffStats.MichaelSoulLink;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.write(0);
            p.writeInt(0);
            p.writeInt(0);
        }

        b = BuffStats.AdrenalinBoost;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(chr.royalGuard);
        }

        b = BuffStats.RWCylinder;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(0);
            p.writeShort(0);
        }

        b = BuffStats.RWMagnumBlow;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeShort(0);
            p.write(0);
        }

        p.writeInt(0); // nViperEnergyCharge

        b = BuffStats.BladeStance;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(1);
        }

        b = BuffStats.SlowAttack;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.FlameDischarge;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.flameDischarge);
        }

        b = BuffStats.HolyUnity;
        if (check(f, b)) {
            p.writeShort(effect.getSkillLevel());
        }

        b = BuffStats.DarkSight;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.Stigma;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.PickPocket;
        if (check(f, b)) {
            // TODO FIND OUT
            p.write(chr.mesoCount);
        }

        b = BuffStats.HolySymbol;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.getId());
            p.writeInt(20);
            p.writeInt(0);
            p.writeInt(0);
            p.writeShort(1);
        }

        b = BuffStats.RelicTitleGage;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.getRelicCount());
            p.writeInt(0);
            p.write(0);
            p.writeInt(0);
            p.writeInt(0); // 단계?
        }

        b = BuffStats.MonsterRiding;
        if (check(f, b)) {
            p.writeInt(get(m, b));
            p.writeInt(buffid);
            p.write(0);
            p.writeInt(0);
        }

        b = BuffStats.RideVehicleExpire;
        if (check(f, b)) {
            p.writeInt(get(m, b));
            p.writeInt(buffid);
            p.write(1);
            p.writeInt(1);
            p.writeShort(10);
        }

        b = BuffStats.RelicTitleGage;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.getRelicCount());
            p.writeInt(0);
            p.write(0);
            p.writeInt(0);
            p.writeInt(0); // 단계?
        }

        // SecondaryStat::DecodeIndieTempStat
        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            //System.out.println(statup.first + " " + statup.second); 
            if (statup.getThird() || statup.getFirst().isIndie()) {
                //System.out.println("stack size = " + stacks.get(statup.getFirst()).size()); 
                p.writeInt(stacks.get(statup.getFirst()).size());
                for (StackedSkillEntry sse : stacks.get(statup.getFirst())) {
                    p.writeInt(check(f, BuffStats.BlessMark) ? 152000009 : sse.getSkillId());
                    p.writeInt(sse.getValue());
                    p.writeInt(sse.getTime());
                    p.writeInt(0);
                    p.writeInt(sse.getBuffLength());
                    p.writeInt(0);
                    p.writeInt(0);
                    // KVP <int, int>
                }
            }
        }

        b = BuffStats.UsingScouter;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
        }

        b = BuffStats.BlessMark;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.getBlessMarkSkillID());
            p.writeInt(chr.getSkillLevel(chr.getBlessMarkSkillID()));
        }

        b = BuffStats.KillMonster;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.ObjectId);
        }

        b = BuffStats.KadenaStack;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.KADENA_STACK);
        }

        b = BuffStats.ArkGage;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.getArkGage());
        }

        b = BuffStats.ArkChargeMable;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.ArkMable[0]);
            p.writeInt(chr.ArkMable[0]);
        }

        b = BuffStats.ArkChargeMableRed;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.ArkMable[1]);
            p.writeInt(chr.ArkMable[1]);
        }

        b = BuffStats.ArkChargeMableBlue;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.ArkMable[2]);
            p.writeInt(chr.ArkMable[2]);
        }

        b = BuffStats.ArkChargeMableGRAY;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(chr.ArkMable[3]);
            p.writeInt(chr.ArkMable[3]);
        }
        b = BuffStats.RelicGage;
        if (check(f, b)) {
            // TODO FIND OUT
            p.writeInt(0);
            p.writeInt(chr.getRelicCount());
        }

        p.writeShort(0); // tDelay
        p.write(0); /// nOptionForIcon
        p.write(0); // bJustBuffCheck
        p.write(buffid == 400051002); // bFirstSet
        if (IsMovementAffectingStat(f)) {
            p.write(0);
        }
        p.writeInt(0);
        p.writeLong(0);
        p.writeLong(0);

        if (ServerConstants.showPackets) {
            //System.out.println("[" + (effect != null ? effect.getSourceId() : 0) + "] " + p.toString());
        }
        return p.getPacket();
    }

    public static byte[] encodeForPirate(List<Triple<BuffStats, Integer, Boolean>> statups, int duration, int skillid) {
        boolean infusion = false;
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());
        packet.writeInt(0);
        packet.writeInt(0);
        BigInteger b = PacketProvider.writeBuffMask(packet, statups);
        if (check(b, BuffStats.PartyBooster)) {
            infusion = true;
        }
        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            if (!aTS_StatFlag(statup.first.getBigValue())) {
                packet.writeInt(statup.getSecond().shortValue());
                packet.writeInt(skillid);
                packet.writeInt(duration * 1000);
            }
        }
        /*
		 * if (skillid == 22171080) { packet.writeInt(1); packet.writeInt(skillid);
		 * packet.writeInt(duration * 1000); }
         */
        packet.write(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        for (Triple<BuffStats, Integer, Boolean> stat : statups) {
            if (!stat.getThird()) {
                packet.writeInt(stat.getSecond());
                packet.writeInt(skillid);
                packet.write(!infusion ? 1 : 0);
                if (skillid == 33001001) {
                    packet.write(1);
                } else {
                    packet.writeInt(!infusion ? 1 : 0);
                }
                if (infusion) {
                    packet.write(0);
                    packet.writeInt(0);
                }
                packet.writeShort(duration);
            }
        }
        packet.writeInt(infusion ? 600 : 0);
        if (skillid == 33001001) {
            packet.write(0);
        }
        if (!infusion) {
            packet.write(1);
        }
        byte v1 = 0;
        if (skillid == 33001001) {
            v1 = 0x06;
        }
        if (!infusion) {
            v1 = 0x08;
        }
        packet.write(v1);
        packet.writeInt(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        return packet.getPacket();
    }
    
    
    public static byte[] stack(List<Triple<BuffStats, Integer, Boolean>> statups,
            SkillStatEffect effect, MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());

        Map<BuffStats, Integer> m = new HashMap<>();
        packet.writeInt(0);
        packet.writeInt(0);
        BigInteger f = PacketProvider.writeBuffMask(packet, statups);
        
        packet.writeShort(chr.getaa());
        packet.writeInt(400051044);
        packet.writeInt(0);
        
        packet.writeInt(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        return packet.getPacket();
    }
    
    
    public static byte[] stack2(List<Triple<BuffStats, Integer, Boolean>> statups,
            SkillStatEffect effect, MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.GIVE_BUFF.getValue());

        Map<BuffStats, Integer> m = new HashMap<>();
        packet.writeInt(0);
        packet.writeInt(0);
        BigInteger f = PacketProvider.writeBuffMask(packet, statups);
        
        packet.writeShort(chr.getbb());
        packet.writeInt(400021071);
        packet.writeInt(effect.getDuration());
        
        packet.writeInt(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        return packet.getPacket();
    }
}
