package handlers.GlobalHandler.PlayerHandler;

import client.Character.MapleCharacter;
import client.Character.MapleCharacterUtil;
import client.Community.MapleParty.MaplePartyCharacter;
import client.ForceAtom;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.MapleInventoryType;
import client.ItemInventory.MapleWeaponType;
import java.awt.Point;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.AntiCheat.DamageParse;
import client.MapleAndroid;
import client.MapleClient;
import client.MapleKeyBinding;
import client.MapleQuestStatus;
import client.Skills.ISkill;
import client.Skills.InnerAbillity;
import client.Skills.InnerSkillValueHolder;
import client.Skills.LinkSkill;
import client.Skills.MatrixSkill;
import client.Skills.Skill;
import client.Skills.SkillEffectEntry;
import client.Skills.SkillEntry;
import client.Skills.SkillFactory;
import client.Skills.SkillMacro;
import client.Skills.SkillStatEffect;
import client.Skills.StackedSkillEntry;
import client.Stats.BuffStats;
import client.Stats.BuffStatsValueHolder;
import client.Stats.MonsterStatus;
import client.Stats.MonsterStatusEffect;
import client.Stats.PlayerStats;
import connections.Database.MYSQL;
import connections.Opcodes.RecvPacketOpcode;
import connections.Packets.AndroidPacket;
import connections.Packets.CashPacket;
import connections.Packets.LoginPacket;
import connections.Packets.MainPacketCreator;
import connections.Packets.MatrixPacket;
import connections.Packets.MobPacket;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PacketUtility.WritingPacket;
import connections.Packets.SLFCGPacket;
import connections.Packets.SkillPackets.AngelicBusterSkill;
import connections.Packets.SkillPackets.KaiserSkill;
import connections.Packets.SkillPackets.KinesisSkill;
import connections.Packets.SkillPackets.MechanicSkill;
import connections.Packets.UIPacket;
import constants.GameConstants;
import constants.ServerConstants;
import handlers.GlobalHandler.AttackInfo;
import handlers.GlobalHandler.AttackType;
import handlers.GlobalHandler.BossEventHandler.AswanHandler;
import handlers.GlobalHandler.BossEventHandler.FishingHandler;
import handlers.GlobalHandler.MovementParse;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimerTask;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.AttackPair;
import tools.CaltechEval;
import tools.Pair;
import tools.Timer.BuffTimer;
import tools.Timer;
import tools.Triple;
import tools.RandomStream.Randomizer;
import java.util.concurrent.ScheduledFuture;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.MapleCoolDownValueHolder;
import scripting.NPC.NPCScriptManager;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.Items.MakerItemFactory;
import server.LifeEntity.MobEntity.MobAttackInfo;
import server.LifeEntity.MobEntity.MobAttackInfoFactory;
import server.LifeEntity.MobEntity.MobSkill;
import server.LifeEntity.MobEntity.MobSkillFactory;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.SummonAttackEntry;
import server.Maps.ArrowFlatter;
import server.Maps.MapField.FieldLimitType;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapSummon.MapleSummon;
import server.Maps.MapSummon.SummonMovementType;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleMapHandling.MaplePortal;
import server.Maps.MapleMist;
import server.Maps.MapleWorldMap.MapleWorldMapItem;
import server.Movement.LifeMovementFragment;
import server.Quests.MapleQuest;

public class PlayerHandler {

    private static ItemInformation ii = ItemInformation.getInstance();
    private static int 여우령 = 0, Rank = 0; // spirit Fox
    private static transient ScheduledFuture<?> diabolicRecoveryTask = null;

    private static int isFinisher(int skillid) {
        switch (skillid) {
            case 1111003: //Panic
                return 2;
            case 1121015: //Greeting
                return 1;
            case 1111008: //Shout
                return 1;
            case 1111014: //Shout
                return 1;
            case 1121010: //Inlay
                return 1;
            case 400011027: //Combo Death
                return 6;
        }
        return 0;
    }

    public static void ChangeSkillMacro(ReadingMaple rh, MapleCharacter chr) {
        int num = rh.readByte();
        String name;
        int shout, skill1, skill2, skill3;
        SkillMacro macro;

        for (int i = 0; i < num; i++) {
            name = rh.readMapleAsciiString();
            shout = rh.readByte();
            skill1 = rh.readInt();
            skill2 = rh.readInt();
            skill3 = rh.readInt();

            macro = new SkillMacro(skill1, skill2, skill3, name, shout, i);
            chr.updateMacros(i, macro);
        }
    }

    public static void ChangeKeymap(ReadingMaple rh, MapleCharacter chr) {
        if ((rh.available() != 8) && (chr != null)) {
            rh.skip(4);
            int numChanges = rh.readInt();
            for (int i = 0; i < numChanges; i++) {
                int key = rh.readInt();
                byte type = rh.readByte();
                int action = rh.readInt();
                if ((type == 1) && (action >= 1000)) {
                    ISkill skill = SkillFactory.getSkill(action);
                    if ((skill != null) && (((!skill.isFourthJob()) && (!skill.isBeginnerSkill()) && (skill.isInvisible()) && (chr.getSkillLevel(skill) <= 0)) || (GameConstants.isLinkedAttackSkill(action)) || (action % 10000 < 1000))) {
                        continue;
                    }
                }
                chr.changeKeybinding(key, new MapleKeyBinding(type, action));
            }
        } else {
            int mode = rh.readInt();
            int itemId = rh.readInt();
            if (mode == 1) {
                chr.setPetAutoHP(itemId);
            } else {
                chr.setPetAutoMP(itemId);
            }
        }
    }

    public static void ExitBlockGame(ReadingMaple rh, MapleClient c) { //Buzz
        c.getSession().writeAndFlush(SLFCGPacket.BlockGameCommandPacket(3));
        c.getPlayer().setBlockCount(0);
        long startTime = System.currentTimeMillis();
        for (int count = 0;; count++) {
            long now = System.currentTimeMillis();
            if (now - startTime >= 3500) {
                break;
            }
        }
        c.getSession().writeAndFlush(UIPacket.IntroDisableUI(false));
        c.getSession().writeAndFlush(UIPacket.IntroLock(false));
        ChannelServer cserv = c.getChannelServer();
        MapleMap target = cserv.getMapFactory().getMap(993017000);
        c.getPlayer().changeMap(target, target.getPortal(0));
        c.getPlayer().gainItem(4310085, c.getPlayer().getBlockCoin());
        c.getPlayer().setBlockCoin(0);
    }

    public static void HandleBlockGameRes(ReadingMaple rh, MapleClient c) { //Buzz
        byte type = rh.readByte();
        if (type == 0x03) {
            c.getSession().writeAndFlush(SLFCGPacket.BlockGameCommandPacket(3));
            c.getPlayer().setBlockCount(0);
            long startTime = System.currentTimeMillis();
            for (int count = 0;; count++) {
                long now = System.currentTimeMillis();
                if (now - startTime >= 3500) {
                    break;
                }
            }
            c.getSession().writeAndFlush(UIPacket.IntroDisableUI(false));
            c.getSession().writeAndFlush(UIPacket.IntroLock(false));
            ChannelServer cserv = c.getChannelServer();
            MapleMap target = cserv.getMapFactory().getMap(993017000);
            c.getPlayer().changeMap(target, target.getPortal(0));
            c.getPlayer().gainItem(4310085, c.getPlayer().getBlockCoin());
            c.getPlayer().setBlockCoin(0);
        } else {
            c.getPlayer().addBlockCoin(type == 0x02 ? 1 : 1);
            int block = c.getPlayer().getBlockCount() + 1;
            c.getPlayer().setBlockCount(block);
            if (block % 10 == 0) {
                int velocity = 100 + ((block / 10) * 30);
                int misplaceallowance = 1 + (block / 10);
                switch (block) {
                    case 70:
                        c.getSession().writeAndFlush(SLFCGPacket.WeatherAddPacket(1));
                        c.getSession().writeAndFlush(MainPacketCreator.musicChange("Bgm45/Time Is Gold"));
                        break;
                    case 100:
                        c.getSession().writeAndFlush(MainPacketCreator.musicChange("Bgm45/Demian Spine"));
                        break;
                    case 140:
                        c.getSession().writeAndFlush(SLFCGPacket.WeatherRemovePacket(1));
                        c.getSession().writeAndFlush(SLFCGPacket.WeatherAddPacket(2));
                        break;
                }
                c.getSession().writeAndFlush(SLFCGPacket.BlockGameControlPacket(velocity, misplaceallowance));
            }
        }
    }

    public static void ChangeQuickSlot(ReadingMaple rh, MapleCharacter chr) {
        final StringBuilder ret = new StringBuilder();
        final List<Integer> quickSlot = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            int id = rh.readInt();
            ret.append(id);
            quickSlot.add(id);
            if (i != 31) {
                ret.append(",");
            }
        }
        chr.getQuestNAdd(MapleQuest.getInstance(GameConstants.QUICK_SLOT)).setCustomData(ret.toString());
    }

    public static void UseChair(int itemId, MapleClient c, MapleCharacter chr, ReadingMaple rh) {
        IItem toUse = chr.getInventory(MapleInventoryType.SETUP).findById(itemId);
        rh.skip(9);

        if (itemId == 3014000 || itemId == 3014001 || itemId == 3014011) {
            final String Special = rh.readMapleAsciiString();
            chr.setChairText(Special);
        }

        if (chr.getMapId() == FishingHandler.FishingMap && itemId == FishingHandler.FishingChair && chr.Fishing()) {
            chr.dropMessage(6, "You can't resume fishing for 5 seconds after fishing.");
            chr.send(MainPacketCreator.resetActions());
            return;
        }

        if (itemId == 100000000 && chr.getMapId() == 3000500) {
            NPCScriptManager.getInstance().start(c, 2003, null);
        }
        chr.setChair(itemId);
        chr.getMap().broadcastMessage(chr, MainPacketCreator.showChair(chr.getId(), itemId, chr.getChairText()), false);
        if (chr.getMapId() == FishingHandler.FishingMap && itemId == FishingHandler.FishingChair && !chr.Fishing()) {
            FishingHandler.StartFishing(chr);
        }
        c.getSession().writeAndFlush(MainPacketCreator.resetActions());
    }

    public static void CancelChair(short id, MapleClient c, MapleCharacter chr) {
        if (id == -1) {
            if (chr.getChair() == 3010587) {
                for (final MapleMapObject mmo : chr.getMap().getMapObjectsInRange(chr.getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MIST))) {
                    final MapleMist capsule = (MapleMist) mmo;
                    if (chr.getId() == capsule.getOwner().getId() && capsule.isTimeCapsule()) {
                        chr.getMap().removeMapObject(mmo);
                        chr.getMap().broadcastMessage(MainPacketCreator.removeMist(capsule.getObjectId(), false));
                        break;
                    }
                }
            }
            chr.setChair(0);
            chr.setChairText(null);
            c.getSession().writeAndFlush(MainPacketCreator.cancelChair(chr, -1));
            chr.getMap().broadcastMessage(chr, MainPacketCreator.showChair(chr.getId(), 0, chr.getChairText()), true);
        } else {
            chr.setChair(id);
            c.getSession().writeAndFlush(MainPacketCreator.cancelChair(chr, id));
        }
    }

    public static void TrockAddMap(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        byte addrem = rh.readByte();
        byte vip = rh.readByte();

        if (addrem == 0) {
            chr.deleteFromTrockMaps(vip, rh.readInt());
        } else if (addrem == 1) {
            if (chr.getMap().getForcedReturnId() == 999999999) {
                chr.addTrockMap(vip, chr.getMapId());
            }
        }
        c.getSession().writeAndFlush(CashPacket.getTrockRefresh(chr, vip, addrem == 3));
    }

    public static void CharInfoRequest(int objectid, MapleClient c, MapleCharacter chr) {
        MapleCharacter player = (MapleCharacter) c.getPlayer().getMap().getMapObject(objectid, MapleMapObjectType.PLAYER);
        if (player != null) {
            if (!player.isGM() || (c.getPlayer().isGM() && player.isGM())) {
                c.getSession().writeAndFlush(MainPacketCreator.getCharInfo(player, c.getPlayer().equals(player)));
            } else {
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            }
        }
    }

    public static void absorbingDF(ReadingMaple rh, MapleClient c) {
        int size = rh.readInt();
        int skillid = rh.readInt();
        c.getPlayer().getAtoms().clear();
        int v1 = rh.readInt();
        List<Integer> oid = new ArrayList<>();
        for (int j = 0; j < v1; j++) {
            int s1 = rh.readInt();
            oid.add(rh.readInt());
            rh.skip(4);
            int v3 = rh.readInt();
            int v4 = rh.readInt();
        }
        for (int j = 0; j < v1; j++) {
            int v5 = rh.readInt();
            byte v6 = rh.readByte();
            int m1 = rh.readInt();
            int v7 = rh.readInt();
            int m2 = rh.readInt();
            int v8 = rh.readInt();
            int v9 = rh.readInt();
            int v10 = rh.readInt();
            rh.skip(1);
            rh.skip(4);
            if (skillid == 31221014 || skillid == 400041010 || skillid == 152110004) {
                m2 = rh.readInt();
            }

            switch (skillid) {
                case 65111007:
                    if (SkillFactory.getSkill(65111007).getEffect(c.getPlayer().getSkillLevel(65111007)).makeChanceResult()) {
                        c.getPlayer().getMap().broadcastMessage(AngelicBusterSkill.SoulSeekerRegen(c.getPlayer(), m1));
                    }
                    break;
                case 65120011:
                    if (SkillFactory.getSkill(65120011).getEffect(c.getPlayer().getSkillLevel(65120011)).makeChanceResult()) {
                        c.getPlayer().getMap().broadcastMessage(AngelicBusterSkill.SoulSeekerRegen(c.getPlayer(), m1));
                    }
                    break;
                case 31221014:
                    if (c.getPlayer().getSC(oid.get(j)) <= 0) {
                        c.getPlayer().removeSC(oid.get(j));
                        return;
                    }
                    c.getPlayer().minSC(oid.get(j));
                    c.getPlayer().getMap()
                            .broadcastMessage(MainPacketCreator.ShieldChacingRe(c.getPlayer().getId(), m1, m2, oid.get(j)));
                    break;
                case 400041023:
                    ForceAtom atom = new ForceAtom(m1, 3, 0x20, 0x21, 0x16, 0x54, (short) 0); //  0x20, 0x21, 0x16 It's one of three
                    c.getPlayer().getAtoms().add(atom);
                    MapleMonster mob = c.getPlayer().getMap().getMonsterByOid(m1);
                    c.getPlayer().blackJack++;
                    if (c.getPlayer().blackJack == 20) {
                        c.getPlayer().blackJack = 0;
                        c.getPlayer().send(MainPacketCreator.BlackJackAttack(400041024, mob.getPosition(), (short) 913));
                        break;
                    }
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.blackJackRegen(c.getPlayer(), m1, mob.getTruePosition()));
                    break;
                case 400021045: {
                    if (c.getPlayer().FlameBall > 0) {
                        MapleMonster monster = c.getPlayer().getMap().getMonsterByOid(m1);
                        if (monster == null) {
                            for (final MapleMapObject mmo : c.getPlayer().getMap().getAllMonster()) {
                                monster = (MapleMonster) mmo;
                                break;
                            }
                        }
                        c.getPlayer().FlameBall--;
                        c.getPlayer().getMap().broadcastMessage(MatrixPacket.플레임디스차지여우재생성(c.getPlayer(), monster)); // Flamedischarge Fox Regen
                    }
                }
            }
        }
    }

    public static void ArrowFlatterAction(ReadingMaple rh, final MapleCharacter chr) {
        final ArrowFlatter be = chr.getMap().getArrowFlatter(chr.getId());
        if (be != null) {
            chr.getMap().broadcastMessage(MainPacketCreator.cancelArrowFlatter(be.getObjectId(), be.getArrow()));
            chr.getMap().removeMapObject(be);
        }
        final int arrow = rh.readByte();
        final Point pos = rh.readIntPos();
        final ArrowFlatter arrowflatter = new ArrowFlatter(chr.getId(), System.currentTimeMillis() + 30000, pos, arrow);
        chr.addCooldown(3111013, System.currentTimeMillis(),
                SkillFactory.getSkill(3111013).getEffect(chr.getSkillLevel(3111013)).getCooldown() * 1000);
        chr.getMap().spawnArrowFlatter(arrowflatter);
    }

    public static void absorbingSword(ReadingMaple rh, MapleCharacter chr) {
        if (!chr.isAlive() || chr.getKeyValue2("Atom") == 1) {
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(chr));
            return;
        }
        final int skillid = rh.readInt();
        final int mobcount = rh.readInt();
        final List<Integer> oids = new ArrayList<>();
        for (int i = 0; i < mobcount; i++) {
            oids.add(rh.readInt());
        }
        chr.setKeyValue2("Atom", 1);

        if (skillid == 400011058 || skillid == 400011059 || skillid == 400011060 || skillid == 400011061) {
            chr.addCooldown(400011058, System.currentTimeMillis(), 30 * 1000);
            chr.getMap().broadcastMessage(KaiserSkill.absorbingSwordCount(chr, oids, skillid));
            chr.cancelEffectFromBuffStat(BuffStats.StopForceAtomInfo);
        } else {
            chr.getMap().broadcastMessage(KaiserSkill.absorbingSwordCount(chr, oids, chr.getSkillLevel(61120007) > 0 ? 61120007 : skillid));
            chr.cancelEffectFromBuffStat(BuffStats.StopForceAtomInfo);
        }
    }

    public static void TakeDamage(ReadingMaple rh, MapleClient c, MapleCharacter chr) throws InterruptedException {
        rh.skip(4); // Ticks
        rh.skip(4);
        rh.skip(4);
        byte type = rh.readByte();
        rh.skip(1); // Element - 0x00 = elementless, 0x01 = ice, 0x02 = fire, 0x03 = lightning
        int damage = rh.readInt();
        final int odamage = damage;

        int oid = 0;
        int monsteridfrom = 0;
        int reflect = 0;
        byte direction = 0;
        int pos_x = 0;
        int pos_y = 0;
        int fake = 0;
        int mpattack = 0;
        boolean is_pg = false;
        boolean isDeadlyAttack = false;
        boolean guardianSpiritActivated = false;
        MapleMonster attacker = null;
        MapleCharacter attacker2 = null;
        MapleMapObject attacke = null;
        PlayerStats stats = chr.getStat();
        rh.skip(2);
        if (type != -2 && type != -3 && type != -4 && type != -6) { // Not map damage
            oid = rh.readInt();
            monsteridfrom = rh.readInt();
            rh.readInt();
            attacker = (MapleMonster) chr.getMap().getMonsterByOid(oid);
            direction = rh.readByte();

            if (chr.isActiveBuffedValue(36111004) && System.currentTimeMillis() - chr.megaSmasherChargeStartTime > 1500) {
                chr.megaSmasherChargeStartTime = System.currentTimeMillis();
                chr.getMap().broadcastMessage(chr, MainPacketCreator.EazisSystem(chr.getId(), oid), true);
            }

            if (chr.getBuffedSkillEffect(BuffStats.RoyalGuardPrepare) != null) {
                if (chr.royalGuard < 5) {
                    chr.royalGuard++;
                }
                SkillStatEffect eff = SkillFactory.getSkill(51001005).getEffect(1);
                eff.applyTo(chr);
                chr.cancelEffectFromBuffStat(BuffStats.RoyalGuardPrepare);
                chr.getClient().sendPacket(MainPacketCreator.OnRoyalGuardAttack());
            }

            SkillStatEffect eff = chr.getBuffedSkillEffect(BuffStats.StackBuff, 36111003);
            if (eff != null && damage != -1 && damage != 0) {
                int ckrka = (damage / 100) * 5;
                damage -= ckrka;
                chr.dualBrid--;
                if (chr.dualBrid >= 1) {
                    eff.applyTo(chr);
                } else {
                    chr.cancelEffectFromBuffStat(BuffStats.StackBuff, 36111003);
                }
            }

            if (chr.getJob() >= 433 && chr.getJob() <= 434 && damage != -1) {
                if (chr.getSkillLevel(4330009) > 0) {
                    if (Randomizer.rand(1, 100) <= SkillFactory.getSkill(4330009).getEffect(chr.getSkillLevel(4330009))
                            .getER()) {
                        damage = -1;
                        SkillFactory.getSkill(4330009).getEffect(chr.getSkillLevel(4330009)).applyTo(chr);
                    }
                }
            }
            if (damage != -1) {
                if ((chr.getJob() >= 411 && chr.getJob() <= 412) || (chr.getJob() >= 421 && chr.getJob() <= 422) || (chr.getJob() >= 1411 && chr.getJob() <= 1412)) {
                    int v1 = chr.getJob() >= 411 && chr.getJob() <= 412 ? 4111007 : chr.getJob() >= 421 && chr.getJob() <= 422 ? 4211007 : 14111010;
                    for (Pair<Integer, MapleSummon> s : chr.getSummons().values()) {
                        if (s.getLeft() == v1) {
                            if (attacker != null){
                                final List<SummonAttackEntry> allDamage = new ArrayList<SummonAttackEntry>();
                                List<Long> dmg = new ArrayList<Long>();
                                dmg.add((long) damage * 13);
                                allDamage.add(new SummonAttackEntry(attacker.getObjectId(), dmg));
                                SkillStatEffect e = SkillFactory.getSkill(v1).getEffect(chr.getSkillLevel(v1));
                                int refDmg = (int) (e.getX() * 0.01) * damage;
                                chr.getMap().broadcastMessage(chr, MainPacketCreator.summonAttack(chr.getId(), v1, (byte) 0x06, (byte) 0x11, allDamage, chr.getLevel(), true, 0), s.getRight().getPosition());
                                attacker.damage(chr, refDmg, true); // Damage Limit
                            }
                        }
                    }
                }
            }
            if (type != -1) {
                MobAttackInfo attackInfo = MobAttackInfoFactory.getInstance().getMobAttackInfo(attacker, type);
                if (attackInfo != null) {
                    if (attackInfo.isDeadlyAttack()) {
                        isDeadlyAttack = true;
                        mpattack = stats.getMp() - 1;
                    } else {
                        mpattack += attackInfo.getMpBurn();
                    }
                    if (attackInfo.getDiseaseSkill() != 0) {
                        MobSkill skill = MobSkillFactory.getMobSkill(attackInfo.getDiseaseSkill(),
                                attackInfo.getDiseaseLevel());
                        if (skill != null && (damage == -1 || damage > 0)) {
                            skill.applyEffect(chr, attacker, false);
                        }
                        attacker.setMp(attacker.getMp() - attackInfo.getMpCon());
                    }
                }
            }
        } else if (type == -6) {
            chr.addHP(-chr.getStat().getCurrentMaxHp());
        }

        /* Deduction of Holy Magic Shell Guard Count */
        if (chr.getBuffedValue(BuffStats.HolyMagicShell) != null) {
            if (chr.getKeyValue2("HolyMagicShellLifeCount") != 0 && chr.getKeyValue2("HolyMagicShellLifeCount") != -1) {
                int life = chr.getKeyValue2("HolyMagicShellLifeCount");
                if (life > 0) {
                    life--;
                }
                chr.setKeyValue2("HolyMagicShellLifeCount", life);
                if (life == 0) {
                    chr.cancelEffectFromBuffStat(BuffStats.HolyMagicShell, -1);
                }
            }
        }

        if (GameConstants.isXenon(chr.getJob())) {
            if (Randomizer.isSuccess(60)) {
                chr.giveSurPlus(1);
            }
        }

        //Aesop Barrier Start
        if (GameConstants.isZero(chr.getJob()) && chr.getGender() == 1) {
            SkillStatEffect zero = SkillFactory.getSkill(101120109).getEffect(chr.getSkillLevel(101120109));
            if (zero != null) {
                if (zero.makeChanceResult()) {
                    zero.applyTo(chr);
                }
            }
        }

        if (GameConstants.isZero(chr.getJob())) {
            if (chr.getGender() == 0 && chr.getSecondGender() == 1) {
                if (chr.getSkillLevel(101120109) > 0) {
                    if (!chr.Barrier()) {
                        SkillStatEffect effects = SkillFactory.getSkill(101120109).getEffect(chr.getSkillLevel(101120109));
                        damage = -1;
                        effects.applyTo(chr);
                        chr.setBarrier(true);
                        Timer.MapTimer.getInstance().schedule(new Runnable() {
                            @Override
                            public final void run() {
                                chr.setBarrier(false);
                            }
                        }, 1000 * 30);
                    }
                }
            }
        }

        //Aesop Barrier
        if (chr.getBuffedValue(BuffStats.BlessingArmor, 1210016) != null) {
            int guardCount = chr.getBuffedValue(BuffStats.BlessingArmor, 1210016).intValue();
            if (guardCount == 0) {
                chr.cancelEffectFromBuffStat(BuffStats.BlessingArmor, 1210016);
            } else {
                chr.setBuffedValue(BuffStats.BlessingArmor, 1210016, guardCount - 1);
            }
        }

        if (damage == -1) {
            if (chr.getJob() / 100 == 4) {
                fake = 4020002 + ((chr.getJob() / 10 - 40) * 100000);
            } else if (chr.getJob() == 122) { // Guardian Iris
                fake = 1220006;
                guardianSpiritActivated = true;
            } else if (GameConstants.isMercedes(chr.getJob())) {
                fake = 23000001;
            } else if (chr.getJob() == 512) { // Guard Crush
                fake = 5120014;
            }
        }
        if (damage == 0) { // Guard
            if (chr.getSkillLevel(31110008) > 0) {
                SkillStatEffect effs = SkillFactory.getSkill(31110008).getEffect(chr.getSkillLevel(31110008));
                int recHP = (int) (chr.getStat().getCurrentMaxHp() * (effs.getY() / 100.0D));
                int recMP = effs.getZ();
                chr.addHP(recHP);
                chr.addMP(recMP);
                chr.handleForceGain(oid, 31110008, chr.getStat().addForce(effs.getZ()));
            }
        }

        if (chr.getJob() == 2711 || chr.getJob() == 2712) {
            if (chr.getSkillLevel(27110007) > 0) { // Life Tidal
                ISkill skill = SkillFactory.getSkill(27110007);
                int critical = chr.getSkillLevel(skill);
                if ((chr.getStat().getHp() / chr.getStat().getCurrentMaxHp())
                        * 100 < (chr.getStat().getMp() / chr.getStat().getCurrentMaxMp()) * 100) {
                    c.send(MainPacketCreator.giveLifeTidal(false, skill.getEffect(critical).getX()));
                } else if ((chr.getStat().getHp() / chr.getStat().getCurrentMaxHp())
                        * 100 > (chr.getStat().getMp() / chr.getStat().getCurrentMaxMp()) * 100) {
                    if (critical > 0) {
                        chr.getStat().passive_sharpeye_rate += skill.getEffect(critical).getProb();
                        chr.getStat().passive_sharpeye_min_percent += skill.getEffect(critical).getCriticalMin();
                        c.send(MainPacketCreator.giveLifeTidal(true, skill.getEffect(critical).getProb()));
                    }
                }
            }
        }
        if (damage > 0) {
            if (chr.getBuffedValue(BuffStats.Morph) != null) {
                chr.cancelMorphs();
            }

            if (type == -1) {
                if (chr.getBuffedValue(BuffStats.ACC) != null) {
                    attacker = (MapleMonster) chr.getMap().getMapObject(oid, MapleMapObjectType.MONSTER);
                    if (attacker != null) {
                        long bouncedamage = (int) (damage * (chr.getBuffedValue(BuffStats.ACC).doubleValue() / 100));
                        bouncedamage = (int) Math.min(bouncedamage, attacker.getMobMaxHp() / 2);
                        attacker.damage(chr, bouncedamage, true);
                        chr.checkMonsterAggro(attacker);
                        damage -= bouncedamage;
                        chr.getMap().broadcastMessage(chr, MobPacket.damageMonster(oid, bouncedamage),
                                chr.getPosition());
                        if (GameConstants.isDemonSlayer(chr.getJob())) { // Dark Revenge Effect
                            if (chr.getSkillLevel(31101003) > 0) {
                                SkillStatEffect skills = SkillFactory.getSkill(31101003)
                                        .getEffect(chr.getSkillLevel(31101003));
                                if (skills.makeChanceResult()) {
                                    attacker.applyStatus(chr,
                                            new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.FREEZE, 1),
                                                    SkillFactory.getSkill(31101003), chr.getSkillLevel(31101003), null,
                                                    false),
                                            skills.getValue("subTime"));
                                }
                            }
                        }
                        is_pg = true;
                    }
                } else if (chr.getBuffedValue(BuffStats.BlessOfDarkness) != null) {
                    attacker = (MapleMonster) chr.getMap().getMapObject(oid, MapleMapObjectType.MONSTER);
                    if (attacker != null) {
                        int reducedamage = (int) (damage
                                * (chr.getBuffedValue(BuffStats.BlessOfDarkness).doubleValue() / 100));
                        damage = reducedamage;
                        chr.setBlessOfDark((byte) (chr.getBlessOfDark() - 1));
                        if (chr.getBlessOfDark() == 0) {
                            chr.cancelEffect(SkillFactory.getSkill(27100003).getEffect(1), false, -1);
                        } else {
                            SkillFactory.getSkill(27100003).getEffect(chr.getSkillLevel(27100003)).applyTo(chr);
                        }
                    }
                } else if (chr.getSkillLevel(4221006) > 0) {
                    for (final MapleMapObject mm : chr.getMap().getAllMistsThreadsafe()) {
                        MapleMist mist = (MapleMist) mm;
                        if (mist.getSourceSkill() != null) {
                            if (mist.getSourceSkill().getId() == 4221006) {
                                for (final MapleMapObject mo : chr.getMap().getMapObjectsInRect(mist.getBox(), Collections.singletonList(MapleMapObjectType.PLAYER))) {
                                    if (((MapleCharacter) mo).getId() == chr.getId()) {
                                        damage = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (type != -2 && type != -3 && type != -4) { // -2, -3, -4 : Map Damage
                switch (chr.getJob()) {
                    case 112: {
                        ISkill skill = SkillFactory.getSkill(1120004);
                        if (chr.getSkillLevel(1120004) > 0) {
                            damage = (int) ((skill.getEffect(chr.getSkillLevel(skill)).getX() / 1000.0) * damage);
                        }
                        break;
                    }
                    case 122: {
                        ISkill skill = SkillFactory.getSkill(1220005);
                        if (chr.getSkillLevel(1220005) > 0) {
                            damage = (int) ((skill.getEffect(chr.getSkillLevel(skill)).getX() / 1000.0) * damage);
                        }
                        break;
                    }
                    case 132: {
                        ISkill skill = SkillFactory.getSkill(1320005);
                        if (chr.getSkillLevel(1320005) > 0) {
                            damage = (int) ((skill.getEffect(chr.getSkillLevel(skill)).getX() / 1000.0) * damage);
                        }
                        break;
                    }
                }
            }
            int hploss = 0;
            int mploss = 0;
            if (chr.getBuffedValue(BuffStats.PartyBarrier) != null) {
                damage -= (int) Math.ceil(damage / 10);
                if (chr.getParty() != null) {
                    for (MaplePartyCharacter pPlayer : chr.getParty().getMembers()) {
                        final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage()
                                .getCharacterById(pPlayer.getId());
                        player.addHP((int) Math.ceil(damage / 10));
                    }
                }
            }
            if (chr.getBuffedValue(BuffStats.MagicGuard) != null || chr.getSkillLevel(27000003) > 0) {
                if (isDeadlyAttack) {
                    if (stats.getHp() > 1) {
                        hploss = stats.getHp() - 1;
                    }
                    if (stats.getMp() > 1) {
                        mploss = stats.getMp() - 1;
                    }
                } else {
                    if (chr.getSkillLevel(27000003) > 0) {
                        ISkill skill = SkillFactory.getSkill(27000003);
                        SkillStatEffect eff = skill.getEffect(chr.getSkillLevel(skill));
                        mploss = (int) (damage * (eff.getX() / 100.0));
                    } else {
                        mploss = (int) (damage * (chr.getBuffedValue(BuffStats.MagicGuard).doubleValue() / 100.0));
                    }
                    hploss = damage - mploss;
                    mpattack += mploss;

                    if (mploss > stats.getMp()) {
                        hploss += mploss - stats.getMp();
                        mpattack = stats.getMp();
                    }
                }
            }
            if (chr.getBuffedValue(BuffStats.MesoGuard) != null) {
                damage = (damage % 2 == 0) ? damage / 2 : (damage / 2) + 1;

                int mesoloss = (int) (damage * (chr.getBuffedValue(BuffStats.MesoGuard).doubleValue() / 100.0));
                if (chr.getMeso() < mesoloss) {
                    chr.gainMeso(-chr.getMeso(), false);
                    chr.cancelEffectFromBuffStat(BuffStats.MesoGuard, -1);
                } else {
                    chr.gainMeso(-mesoloss, false);
                }
                if (isDeadlyAttack && stats.getMp() > 1) {
                    mpattack = stats.getMp() - 1;
                }
            }
            if (chr.getBuffedValue(BuffStats.Stance, 22181004) != null) { // Will of Onyx
                int level = chr.getSkillLevel(22181004);
                int lessDaMper = (int) (new CaltechEval("5+d(" + level + "/2)").evaluate());
                if (hploss > 0) {
                    int lessDaM = (int) (hploss * (lessDaMper / 100.0D));
                    hploss -= lessDaM;
                } else {
                    int lessDaM = (int) (damage * (lessDaMper / 100.0D));
                    damage -= lessDaM;
                }
            }

            if (chr.getBuffedValue(BuffStats.KinesisPsychicEnergeShield, 142001007) != null) {
                SkillStatEffect eff = SkillFactory.getSkill(142001007).getEffect(chr.getSkillLevel(142001007));
                int lessDaMper = (int) eff.getX();
                if (chr.PPoint > 0) {
                    chr.PPoint--;
                    chr.givePPoint(eff, false);
                    if (hploss > 0) {
                        int lessDaM = (int) (hploss * (lessDaMper / 100.0D));
                        hploss -= lessDaM;
                    } else {
                        int lessDaM = (int) (damage * (lessDaMper / 100.0D));
                        damage -= lessDaM;
                    }
                }
            }

            // Body of steal
            if (chr.getBuffedValue(BuffStats.IndieDamR, 400011066) != null) {
                int level = chr.getSkillLevel(400011066);
                SkillStatEffect eff = SkillFactory.getSkill(400011066).getEffect(level);
                int maxStack = 10;
                if (chr.getBodyOfSteel() < maxStack) {
                    long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);
                    chr.addBodyOfSteel();

                    int duration = eff.getDuration();
                    duration += (int) (chr.getBuffedStarttime(BuffStats.IndieDamR, 400011066)
                            - System.currentTimeMillis());

                    final List<Triple<BuffStats, Integer, Boolean>> stat = Collections
                            .singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.IndieDamR,
                                    eff.getX() * chr.getBodyOfSteel(), true));

                    List<StackedSkillEntry> stacks = chr.getStackSkills().get(BuffStats.IndieDamR);
                    List<StackedSkillEntry> copyStacks = Collections.unmodifiableList(stacks);

                    int i = 0;
                    for (StackedSkillEntry stack : copyStacks) {
                        if (stack.getSkillId() == 400011066) {
                            stacks.remove(i);
                            stacks.add(new StackedSkillEntry(400011066, eff.getX() * chr.getBodyOfSteel(),
                                    overlap_magic, Integer.MAX_VALUE));
                        }

                        i++;
                    }

                    chr.send(MainPacketCreator.giveBuff(400011066, duration, stat, eff, chr.getStackSkills(), 0, chr));
                }
            }

            List<Integer> attack = attacke instanceof MapleMonster || attacke == null ? null
                    : (new ArrayList<Integer>());
            if ((chr.getJob() == 531 || chr.getJob() == 532) && attacke != null) {
                final ISkill divine = SkillFactory.getSkill(5310009);
                if (chr.getSkillLevel(divine) > 0) {
                    final SkillStatEffect divineShield = divine.getEffect(chr.getSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        if (attacke instanceof MapleMonster) {
                            attacker = (MapleMonster) attacke;
                            final int theDmg = (int) (divineShield.getDamage());
                            attacker.damage(chr, theDmg, true);
                            chr.getMap().broadcastMessage(MobPacket.damageMonster(attacker.getObjectId(), theDmg));
                        } else {
                            attacker2 = (MapleCharacter) attacke;
                            attacker2.addHP(-divineShield.getDamage());
                            attack.add((int) divineShield.getDamage());
                        }
                    }
                }
            }
            if (chr.getSkillLevel(1210016) > 0 && (chr.getJob() == 121 || chr.getJob() == 122)) { // Blessing Armor
                SkillStatEffect effect = SkillFactory.getSkill(1210016).getEffect(chr.getSkillLevel(1210016));
                if (!chr.skillisCooling(1210016)) {
                    if (effect.makeChanceResult()) {
                        effect.applyTo(chr);
                        chr.addCooldown(1210016, System.currentTimeMillis(), effect.getCooldown() * 1000);
                    }
                }
            }
            if ((chr.getJob() == 512 || chr.getJob() == 522) && chr.getBuffedValue(BuffStats.DamR) == null
                    && chr.getSkillLevel(chr.getJob() == 512 ? 5120011 : 5220012) > 0) {
                final ISkill divine = SkillFactory.getSkill(chr.getJob() == 512 ? 5120011 : 5220012);
                SkillStatEffect effect = divine.getEffect(chr.getSkillLevel(chr.getJob() == 512 ? 5120011 : 5220012));
                if (chr.getSkillLevel(divine) > 0 && !chr.skillisCooling(divine.getId())) {
                    final SkillStatEffect divineShield = divine.getEffect(chr.getSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        divineShield.applyTo(chr);
                        chr.addCooldown(chr.getJob() == 512 ? 5120011 : 5220012, System.currentTimeMillis(),
                                effect.getCooldown() * 1000);
                    }
                }
            }

            if (attacker != null && chr.getBuffedValue(BuffStats.Beholder, 1301013) != null
                    && chr.getSkillLevel(1320011) > 0 && chr.getSummons().get(1301013) != null) {
                if (!chr.skillisCooling(1320011)) {
                    for (Pair<Integer, MapleSummon> summon : chr.getSummons().values()) {
                        if (summon.getLeft() == 1301013) {
                            chr.getMap().broadcastMessage(MainPacketCreator.SummonBeholderRevengeAttack(chr.getId(),
                                    summon.getRight().getObjectId(), 1320011, 0));
                        }
                    }
                }
            }
            if (damage > odamage) {
                damage = odamage;
            }
            if (isDeadlyAttack) {
                chr.addMPHP(stats.getHp() > 1 ? -(stats.getHp() - 1) : 0, stats.getMp() > 1 ? -(stats.getMp() - 1) : 0);
            } else {
                chr.addMPHP(hploss > 0 ? -hploss : -damage, -mpattack);
            }

        }

        if (guardianSpiritActivated) {
            rh.skip(11);
            int gsOid = rh.readInt();
            MapleMonster gsMob = (MapleMonster) chr.getMap().getMonsterByOid(gsOid);
            if (!gsMob.getStats().isBoss()) {
                gsMob.applyStatus(chr,
                        new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.STUN, 1),
                                SkillFactory.getSkill(1220006), chr.getSkillLevel(1220006), null, false),
                        SkillFactory.getSkill(1220006).getEffect(chr.getSkillLevel(1220006)).getStatusDuration());
            }
        }
        if (!chr.isHidden()) {
            chr.getMap().broadcastMessage(chr, MainPacketCreator.damagePlayer(type, monsteridfrom, chr.getId(), damage,
                    fake, direction, reflect, is_pg, oid, pos_x, pos_y), false);
        }
    }

    public static void AranGainCombo(MapleClient c, MapleCharacter chr) {
        /*if (GameConstants.isAran(chr.getJob())) {
            short combo = chr.getCombo();
            long curr = System.currentTimeMillis();
            combo++;
            chr.updateCombo(combo, curr);
        }*/
    }

    public static void AranLoseCombo(MapleClient c, MapleCharacter chr) {
        if (GameConstants.isAran(chr.getJob())) {
            final short losecombo = (short) ((chr.getCombo() / 100) + 1);
            chr.updateCombo((short) (chr.getCombo() - losecombo), System.currentTimeMillis());
        }
    }

    public static void BlessOfDarkness(MapleCharacter chr) {
        if (chr.getJob() >= 2710 && chr.getJob() <= 2712) {
            if (chr.getBlessOfDark() < 3) {
                chr.setBlessOfDark((byte) (chr.getBlessOfDark() + 1));
                SkillFactory.getSkill(27100003).getEffect(chr.getSkillLevel(27100003)).applyTo(chr);
            }
        }
    }

    public static void UseItemEffect(int itemId, MapleClient c, MapleCharacter chr) {
        IItem toUse = chr.getInventory(MapleInventoryType.CASH).findById(itemId);
        if (toUse == null || toUse.getItemId() != itemId || toUse.getQuantity() < 1) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        chr.setItemEffect(itemId);
        chr.getMap().broadcastMessage(chr, MainPacketCreator.itemEffect(chr.getId(), itemId), false);
    }

    public static void CancelItemEffect(int id, MapleCharacter chr) {
        if (GameConstants.isAngelicBlessBuffEffectItem(id)) {
            return;
        }
        if (GameConstants.isAngelicBlessBuffEffectItem(-id)) {
            return;
        }
        chr.cancelEffect(ItemInformation.getInstance().getItemEffect(-id), false, -1);
    }

    public static void CancelBuffHandler(int sourceid, MapleCharacter chr) {
        ISkill charge = SkillFactory.getSkill(GameConstants.getLinkedAttackSkill(sourceid));
        if (charge == null) {
            chr.dropMessage(6, "Skill error occurred! SkillID : " + sourceid + " Please write your error on the error board!");
            return;
        }

        if (charge.isChargeSkill()) {
            if (sourceid == 3121004 ? charge.getEffect(20).getCooldown() > 0 : sourceid == 3121009 ? charge.getEffect(20).getCooldown() > 0 : charge.getEffect(chr.getSkillLevel(charge)).getCooldown() > 0) {
                if (!chr.isEquilibrium() && !GameConstants.isDarkSkills(charge.getId())) {
                    SkillStatEffect effect = charge.getEffect(chr.getSkillLevel(charge));
                    chr.addCooldown(sourceid, System.currentTimeMillis(), effect.getCooldown() * 1000);
                    chr.send(MainPacketCreator.skillCooldown(sourceid, effect.getCooldown()));
                }
            }
            if (sourceid == 400021072) {
                SkillStatEffect effect = charge.getEffect(chr.getSkillLevel(sourceid));
                chr.addCooldown(sourceid, System.currentTimeMillis(), effect.getStat("cooltimeMS"));
            }
            chr.setKeyDownSkill_Time(0);
            chr.getMap().broadcastMessage(chr, MainPacketCreator.skillCancel(chr, charge.getId()), false);
            chr.setSkillEffect(null);
        }

        Map<SkillStatEffect, Long> bsvhs = new HashMap<>();
        chr.getEffects().entrySet().stream().forEach((Entry<BuffStats, List<BuffStatsValueHolder>> effect) -> {
            effect.getValue().stream().filter((bsvh) -> (!bsvhs.containsKey(bsvh.effect) && bsvh.effect.getSourceId() == sourceid)).forEach((bsvh) -> {
                bsvhs.put(bsvh.effect, bsvh.startTime);
            });
        });
        if (sourceid == 4341002) {
            return;
        }
        if (sourceid == 2221054) {
            chr.setIceAura(false);
        }
        if (sourceid == 2321001) {
            return;
        }
        /*if (sourceid == 2121004 || sourceid == 2221004 || sourceid == 2321004) {
            SkillStatEffect eff = SkillFactory.getSkill(sourceid).getEffect(chr.getSkillLevel(sourceid));
            chr.cancelEffect(eff, false, sourceid);
            //chr.send(MainPacketCreator.givePMDR(0, 0));
        }*/
        bsvhs.entrySet().stream().forEach((bsvh) -> {
            chr.cancelEffect(bsvh.getKey(), false, bsvh.getValue());
        });
        if (sourceid != 2221011) {
            chr.send(MainPacketCreator.SkillUseResult((byte) 0));
        }
        chr.ea();
        if (sourceid == 35111003) {
            CancelBuffHandler(35001002, chr);
            //SkillFactory.getSkill(35001002).getEffect(chr.getSkillLevel(35001002)).applyTo(chr);
        }

        // 조커 : 카드 뽑기
        if (sourceid == 400041009) {
            int[] skills = new int[]{400041011, 400041012, 400041013, 400041014, 400041015};

            for (int skill : skills) {
                CancelBuffHandler(skill, chr);
            }

            int jokerSkill = skills[Randomizer.nextInt(skills.length)];

            if (jokerSkill == 400041015) {
                SkillFactory.getSkill(400041011).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);
                SkillFactory.getSkill(400041012).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);
                SkillFactory.getSkill(400041013).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);
                SkillFactory.getSkill(400041014).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);
            } else {
                SkillFactory.getSkill(jokerSkill).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);
            }
            chr.addCooldown(sourceid, System.currentTimeMillis(), 150 * 1000);
            chr.send(MainPacketCreator.skillCooldown(sourceid, 150));
            chr.getMap().broadcastMessage(MainPacketCreator.showSkillEffect(chr.getId(), chr.getLevel(), jokerSkill, chr.getSkillLevel(400041009), (byte) 0, 1, chr.getPosition(), chr.getTruePosition()));
        }
    }

    public static void SkillEffect(ReadingMaple rh, MapleCharacter chr) {
        int skillId = rh.readInt();
        final int level = rh.readInt();
        rh.readInt();
        boolean c = rh.readByte() == 1;
        if (c) {
            rh.readInt();
        }
        final short display = rh.readShort();
        final byte speed = rh.readByte();
        rh.skip(1);
        SkillEffectEntry entry = new SkillEffectEntry(skillId, level, display, speed);
        ISkill skill = SkillFactory.getSkill(entry.getSkillId());
        int skilllevel_serv = chr.getSkillLevel(skill);
        if (skilllevel_serv > 0 && skilllevel_serv == entry.getLevel() && skill.isChargeSkill() && entry.getLevel() > 0 || skill.getId() == 35101009 || skill.getId() == 22171083 || skill.getId() == 400051334) {
            chr.setKeyDownSkill_Time(System.currentTimeMillis());
            chr.setSkillEffect(entry);

            if (skill.getId() == 155121341 || skill.getId() == 400051334 || skill.getId() == 155111306) {
                Map<SkillStatEffect, Long> bsvhs = new HashMap<>();
                chr.getEffects().entrySet().stream().forEach((Entry<BuffStats, List<BuffStatsValueHolder>> effect1) -> {
                    effect1.getValue().stream().filter((bsvh) -> (!bsvhs.containsKey(bsvh.effect) && bsvh.effect.getSourceId() == 155101006)).forEach((bsvh) -> {
                        bsvhs.put(bsvh.effect, bsvh.startTime);
                    });
                });
                if (bsvhs.size() == 0) {
                    SkillFactory.getSkill(155101006).getEffect(chr.getSkillLevel(155101006)).applyTo(chr);
                }
            }
            if (skill.getId() == 400011072) {
                SkillFactory.getSkill(400011072).getEffect(chr.getSkillLevel(400011072)).applyTo(chr);
            }
            if (skill.getId() == 22171083) {
                SkillFactory.getSkill(22171083).getEffect(chr.getSkillLevel(22171080)).applyTo(chr);
            }

            if (skill.getId() == 27101202 || skill.getId() == 35001001 || skill.getId() == 35101009 || skill.getId() == 400041009) {
                SkillFactory.getSkill(400041009).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);
                skill.getEffect(entry.getLevel()).applyTo(chr);
                chr.getMap().broadcastMessage(chr, MainPacketCreator.skillEffect(chr, entry, rh.readPos()), false);
            } else {
                chr.getMap().broadcastMessage(chr, MainPacketCreator.skillEffect(chr, entry, rh.readPos()), false);
            }
        }

        if (skill.getId() == 400051040) {
            SkillStatEffect effect = skill.getEffect(skilllevel_serv);
            chr.addCooldown(skill.getId(), System.currentTimeMillis(), effect.getCooldown() * 1000);
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.skillCooldown(skill.getId(), effect.getCooldown()));
        }
    }

    /*
    public static void SkillEffect(ReadingMaple rh, MapleCharacter chr) {
        SkillEffectEntry entry = new SkillEffectEntry(rh.readInt(), (byte) rh.readShort(), rh.readByte(), rh.readByte(),
                rh.readByte());
        rh.skip(1);
        Point pos = chr.getPosition();
        SkillStatEffect effect = SkillFactory.getSkill(entry.getSkillId()).getEffect(entry.getLevel());
        if (effect.getPowerEnergy() > 0) {
            if (!chr.isActiveBuffedValue(36121054)) {
                chr.giveSurPlus(-effect.getPowerEnergy());
            }
        }
        chr.setKeyDownSkill_Time(System.currentTimeMillis());
        chr.setSkillEffect(entry);
        chr.getMap().broadcastMessage(chr, MainPacketCreator.skillEffect(chr, entry, pos), false);

        if (entry.getSkillId() == 400041009) { // 팬텀 : 조커
            SkillFactory.getSkill(400041009).getEffect(chr.getSkillLevel(400041009)).applyTo(chr);

        }
    }
     */
    public static void SpecialSkill(ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        if (c.getPlayer() == null || chr == null || !chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }

        Point oid_pos = rh.readPos();
        int skillid = rh.readInt();
        //Defeat Jump Skill Stats
        if (GameConstants.MoveMentSkillIdParsing(skillid)) {
            //System.out.println("Health / Mana Stat Update Test");
            int movement_mana_spend = 10;
            if (GameConstants.isPhantom(chr.getJob()))
            {
            	movement_mana_spend = 30;
            }
            if (GameConstants.isFlameWizard(chr.getJob())) 
            {
                movement_mana_spend = 12;
            }
            chr.statupdate_bytaiga(movement_mana_spend);
            return;
        }
        if (skillid == 23111008) {
            skillid += Randomizer.nextInt(3);
        }

        if (GameConstants.isZero(chr.getJob())) {
            rh.skip(1);
        }
        int skillLevel = rh.readInt();
        if (GameConstants.isAran(chr.getJob())) {
            chr.useComboSkill(skillid);
        }

        ISkill skill = SkillFactory.getSkill(GameConstants.getLinkedBuffSkill(skillid));
        SkillStatEffect effect = SkillFactory.getSkill(GameConstants.getLinkedBuffSkill(skillid)).getEffect(skillLevel);
        if (skillid != 11121014 && skillid != 400041002 && skillid != 400041003 && skillid != 400041004 && skillid != 400041005) {
            c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.showSkillEffect(c.getPlayer().getId(), chr.getLevel(), skillid, skillLevel, (byte) 0, 1, chr.getPosition(), oid_pos), false);
        }
        // System.out.println(skillid);
        chr.setKeyValue2("Atom", 0);
        if (skillid != 2221011) {
            c.getPlayer().send(MainPacketCreator.SkillUseResult((byte) 0));
        }
        /*  if (skillid == 400031000) {
           c.getPlayer().getMap().broadcastMessage(MatrixPacket.GUIDEDARROW(chr, chr.getTruePosition()));
        }
         */

        if (effect.getCooldown() > 0 && skillid != 400021000 && skillid != 21121058 && skillid != 33121255 && skillid != 35111002 && skillid != 152120001) {
            c.getPlayer().addCooldown(skillid, System.currentTimeMillis(), effect.getCooldown() * 1000);
            c.getSession().writeAndFlush(MainPacketCreator.skillCooldown(skillid, effect.getCooldown()));
        }
        if (skillid == 33121255) {
            c.getPlayer().addCooldown(33121155, System.currentTimeMillis(), 14 * 1000);
            c.getSession().writeAndFlush(MainPacketCreator.skillCooldown(33121155, 14));
        }
        if (skillid == 400001022) {
            c.getPlayer().addCooldown(400001019, System.currentTimeMillis(), 25 * 1000);
            c.getSession().writeAndFlush(MainPacketCreator.skillCooldown(400001019, 25));
        }
        if (skillid == 12121005) {
            MapleMist mist = new MapleMist(effect.calculateBoundingBox(c.getPlayer().getPosition(), c.getPlayer().isFacingLeft()), c.getPlayer(), effect, c.getPlayer().getSkillLevel(1221005), c.getPlayer().getPosition());
            BurningRegion(c, skillid);
            c.getPlayer().getMap().spawnMist(mist, effect.getDuration(), false, false, false, false, false);
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        if (skillid == 400051010) {
            for (MapleCoolDownValueHolder m : chr.getAllCooldowns()) {
                final int skil = m.skillId;
                chr.removeCooldown(skil);
                chr.getClient().send(MainPacketCreator.skillCooldown(skil, 0));
            }
        }
        switch (skillid) {
            case 400031040: {
                List<MapleMapObject> mist = c.getPlayer().getMap().getAllMistsThreadsafe();
                for (MapleMapObject ob : mist) {
                    MapleMist Mmist = (MapleMist) ob;
                    if (Mmist.getSource().getSourceId() == 400031040 || Mmist.getSource().getSourceId() == 400031037) {
                        return;
                    }
                }
                chr.setRelicCount(chr.getRelicCount() - 510);
                SkillStatEffect effects = SkillFactory.getSkill(400031040).getEffect(400031040);
                effects.applyTo(c.getPlayer());
                c.getPlayer().addCooldown(400031037, System.currentTimeMillis(), 200000);
                c.getPlayer().ea();
                break;
            }
            case 400031028: {
                if (chr.getBuffedValue(BuffStats.AdvancedQuiver) != null) {
                    chr.cancelBuffStats(3121016, BuffStats.AdvancedQuiver);
                }
            }
            break;
            case 22170070:
            case 22141017: {
                SkillStatEffect eff = SkillFactory.getSkill(skillid).getEffect(skillid);
                int max = eff.getV();
                final List<MapleMapObject> objs = chr.getMap().getMapObjectsInRange(chr.getTruePosition(), 200000, Arrays.asList(MapleMapObjectType.MONSTER));
                final List<MapleMonster> monsters = new ArrayList<>();
                for (MapleMapObject o : objs) {
                    if (monsters.size() < max) {
                        monsters.add((MapleMonster) o);
                    } else {
                        break;
                    }
                }
                if (monsters.size() > 0) {
                    chr.getMap().broadcastMessage(MainPacketCreator.Evanruinsuse(chr, monsters));
                    if (chr.getSkillLevel(22170070) > 0) {
                        for (final Point pos : chr.Pointxy) {
                            chr.getMap().broadcastMessage(MainPacketCreator.EvanruinsFinal(chr, 24, monsters, 22170070, pos, chr.acaneAim));
                        }
                    } else {
                        for (final Point pos : chr.Pointxy) {
                            chr.getMap().broadcastMessage(MainPacketCreator.EvanruinsFinal(chr, 23, monsters, 22141017, pos, chr.acaneAim));
                        }
                    }
                    chr.Pointxy.clear();
                    chr.acaneAim = 0;
                    chr.마법잔해 = 0;
                }
                break;
            }
            case 400011053: {
                //chr.send(MainPacketCreator.giveBuff(400011053, effect.getDuration(), Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.BlessedHammerBig, chr.GetCount(), false)), SkillFactory.getSkill(400011053).getEffect(chr.getSkillLevel(400011053)), null, SkillFactory.getSkill(400011053).getAnimationTime(), chr));
                break;
            }
            case 400031005: {
                chr.setKeyValue2("Jaguar", 1);
                int[] Jaguars = {33001007, 33001008, 33001009, 33001010, 33001011, 33001012, 33001013, 33001014, 33001015};
                for (int i = 0; i < 6; i++) {
                    int Random = Randomizer.rand(0, 8);
                    MapleSummon summon = new MapleSummon(chr, Jaguars[Random], chr.getPosition(), SummonMovementType.SUMMON_JAGUAR, System.currentTimeMillis());
                    summon.setPosition(chr.getPosition());
                    chr.getMap().spawnSummon(summon, true, 40000);
                    Timer.EventTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            summon.removeSummon(chr.getMap());
                        }
                    }, 40000); // seconds
                }
                Timer.EventTimer.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        chr.setKeyValue2("Jaguar", 0);
                    }
                }, 40000); // seconds
                break;
            }
            case 21121058: {
                SkillFactory.getSkill(21110016).getEffect(chr.getSkillLevel(21110016)).applyTo(chr);
                chr.addCooldown(21121058, System.currentTimeMillis(), effect.getCooldown() * 1000);
                chr.setCombo((short) 500);
                chr.updateCombo(chr.getCombo(), System.currentTimeMillis());
                break;
            }
            case 2221054: {
                c.getPlayer().setIceAura(true);
                IceAura(c, c.getPlayer().getPosition(), skillid);
                effect.applyTo(c.getPlayer());
                break;
            }
            case 142121008: {
                //c.getPlayer().givePPoint(effect, false);
                c.getPlayer().addCooldown(142121008, System.currentTimeMillis(), effect.getCooldown() * 1000);
                break;
            }
            case 152101003: {
                rh.skip(14);
                Point pos = rh.readPos();
                pos.y = c.getPlayer().getPosition().y;
                for (MapleMapObject ob : c.getPlayer().getMap().getAllSummon()) {
                    MapleSummon summon = (MapleSummon) ob;
                    if (summon.getOwner() != null && summon.getOwner().getId() == c.getPlayer().getId() && summon.getSkill() == 152101000) {
                        summon.setPosition(pos);
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.updateCrystal(c.getPlayer().getId(), summon.getObjectId(), pos));
                        break;
                    }
                }
                break;
            }
            case 152101004: {
                rh.skip(14);
                Point pos = rh.readPos();
                for (MapleMapObject ob : c.getPlayer().getMap().getAllSummon()) {
                    MapleSummon summon = (MapleSummon) ob;
                    if (summon.getOwner() != null && summon.getOwner().getId() == c.getPlayer().getId() && summon.getSkill() == 152101000) {
                        summon.setPosition(pos);
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.updateCrystal(c.getPlayer().getId(), summon.getObjectId(), pos));
                        break;
                    }
                }
                break;
            }
            case 152001001: {
                rh.skip(9);
                c.getPlayer().getMap().broadcastMessage(null,
                        MainPacketCreator.CraftJavelin(c.getPlayer().getId(), oid_pos), c.getPlayer().getPosition());
                break;
            }
            case 152120001: {
                rh.skip(9);
                c.getPlayer().getMap().broadcastMessage(null,
                        MainPacketCreator.CraftJavelin2(c.getPlayer().getId(), oid_pos), c.getPlayer().getPosition());
                break;
            }
            case 400031039:
            case 400031038: {
                effect.applyTo(chr);
                chr.setRelicCount(chr.getRelicCount() - 510);
                c.getPlayer().addCooldown(400031037, System.currentTimeMillis(), 200000);
                break;
            }
             
            case 3011004:
            case 3300002:
            case 3321003: {
                List<Integer> moblist = new ArrayList<Integer>();
                List<MapleMapObject> objs = c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getTruePosition(), 200000, Arrays.asList(MapleMapObjectType.MONSTER));
                rh.skip(13);
                Point xy = rh.readPos();
                byte count = rh.readByte();
                for (byte i = 1; i <= count; i++) {
                    moblist.add(rh.readInt());
                }
                if (!objs.isEmpty()) {
                    for (MapleMapObject monss : c.getPlayer().getMap().getAllMonstersThreadsafe()) { // Additional attack when there is a boss monster in range
                        MapleMonster mons = (MapleMonster) monss;
                        if (mons.getStats().isBoss() == true) {
                            if (c.getPlayer().getSkillLevel(3320002) > 0) {
                                moblist.add(mons.getObjectId());
                                moblist.add(mons.getObjectId());
                                break;
                            } else {
                                moblist.add(mons.getObjectId());
                                break;
                            }
                        }
                    }
                }
                if (c.getPlayer().shape == 2 && Randomizer.isSuccess(60)) {
                    final List<MapleMonster> monsters = new ArrayList<>();
                    int i = 0;
                    for (MapleMapObject o : objs) {
                        monsters.add((MapleMonster) o);
                        i++;
                        if (i == 1) {
                            monsters.add((MapleMonster) o);
                        }
                        if (c.getPlayer().getBuffedValue(BuffStats.IndieIliumStack, 3321034) == null) {
                            if (i == 2) {
                                break;
                            }
                        } else {
                            if (i == 3) {
                                break;
                            }
                        }
                    }
                    chr.getMap().broadcastMessage(MainPacketCreator.에디셔널블래스트(c.getPlayer(), monsters, 3310004));
                }
                c.getPlayer().GivePassPinderShape(skillid);
                chr.getMap().broadcastMessage(MainPacketCreator.카디널디스차지(chr, moblist, skillid));
                break;
            }
            case 155001103: {
                chr.send(MainPacketCreator.CreateForceArkAtom(chr, 155001103));
                chr.ResetArkMable();
                chr.AddArkMable(skillid);
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                break;
            }

            case 155111207: {
                List<Integer> moblist = new ArrayList<Integer>();
                for (Point AtomPos : chr.getAtomPos()) {
                    chr.getMap().broadcastMessage(MainPacketCreator.ReturnHatredFinal(chr, moblist, AtomPos));
                }
                chr.AtomPos.clear();
                rh.skip(15);
                int count = rh.readInt();
                for (int i = 0; i < count; i++) {
                    moblist.add(rh.readInt());
                    chr.getMap().broadcastMessage(MainPacketCreator.RemoveArkToken(chr, rh.readInt()));
                }
                chr.arkatom = 0;
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                break;
            }
            case 400021044: {
                c.getPlayer().FlameBall = 56;
                if (c.getPlayer().FlameDischarge == 3) {
                    c.getPlayer().FlameBall += 14;
                } else if (c.getPlayer().FlameDischarge == 4) {
                    c.getPlayer().FlameBall += 28;
                } else if (c.getPlayer().FlameDischarge == 5) {
                    c.getPlayer().FlameBall += 42;
                } else if (c.getPlayer().FlameDischarge == 6) {
                    c.getPlayer().FlameBall += 56;
                }
                for (byte i = 1; i <= 8; i++) {
                    chr.getMap().broadcastMessage(MatrixPacket.플레임디스차지여우(c.getPlayer())); // Flamedischarged Fox
                }
                chr.FlameDischarge = 0;
                chr.cancelEffectFromBuffStat(BuffStats.FlameDischarge);
                chr.getClient().send(MainPacketCreator.FlameDischarge((byte) chr.FlameDischarge, 0));
                chr.addCooldown(400021042, System.currentTimeMillis(), 20 * 1000);
                chr.getClient().getSession().writeAndFlush(MainPacketCreator.skillCooldown(400021042, 20));
                break;
            }
            case 400021030: { // Thunder brake
                final SkillStatEffect subEffect = SkillFactory.getSkill(400021031).getEffect(skillLevel);
                final List<Point> points = new ArrayList<>();
                rh.skip(14);

                int loop = rh.readInt();
                for (int i = 0; i < loop; i++) {
                    points.add(rh.readIntPos());
                }

                tools.Timer.MapTimer.getInstance().schedule(new Runnable() {
                    int m = 0;

                    @Override
                    public void run() {
                        for (Point pos : points) {
                            for (int i = 0; i <= 400; i += 200) {
                                pos.y -= i;
                                chr.getMap().spawnMist(
                                        new MapleMist(effect.calculateBoundingBox(pos, chr.isFacingLeft()), chr, subEffect,
                                                subEffect.getSkillStats().getLevel(), pos),
                                        500, false, false, false, false, false);
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MobSkill.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }, 0);

                break;
            }
            case 400031000: {
                c.getPlayer().getMap().broadcastMessage(MatrixPacket.GUIDEDARROW(chr, chr.getTruePosition()));
                break;
            }
            case 400021001: { // 도트퍼니셔
                List<Point> pList = new ArrayList<>();
                int xMin = c.getPlayer().getPosition().x - 500;
                int xMax = c.getPlayer().getPosition().x + 500;
                int yMin = c.getPlayer().getPosition().y - 450;
                int yMax = c.getPlayer().getPosition().y - 75;
                for (int i = 0; i < 15; i++) {
                    pList.add(new Point(Randomizer.rand(xMin, xMax), Randomizer.rand(yMin, yMax)));
                }
                c.getPlayer().getMap().broadcastMessage(null, MatrixPacket.DotPunisuer(c.getPlayer().getId(), pList), c.getPlayer().getPosition());
                break;
            }
            case 400031022: { // Children worm
                List<Point> pList = new ArrayList<>();
                for (int i = 0; i < effect.getX(); i++) {
                    pList.add(new Point(c.getPlayer().getPosition().x + Randomizer.rand(-150, 150), c.getPlayer().getPosition().y - Randomizer.rand(0, 150)));
                }
                c.getPlayer().getMap().broadcastMessage(null, MatrixPacket.IdleWorm(c.getPlayer().getId(), pList), c.getPlayer().getPosition());
                break;
            }

            case 400041022: { // Blackjack
                c.getPlayer().blackJack = 0;
                rh.skip(10);
                int objectId = rh.readInt();
                short delay = rh.readShort();
                chr.getAtoms().clear();
                ForceAtom atom = new ForceAtom(objectId, 2, 0x20, 0x20, 0x19, 0x45, delay);
                chr.getAtoms().add(atom);
                chr.getMap().broadcastMessage(MainPacketCreator.blackJack(chr, chr.getTruePosition()));
                chr.addCooldown(400041022, System.currentTimeMillis(), effect.getCooldown() * 1000);
                break;
            }

            case 400041000: { // Venom Burst
                List<MapleMapObject> mobs_objects = c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 320000, Arrays.asList(MapleMapObjectType.MONSTER));
                final List<MapleMonster> allmobs = new ArrayList<>();
                for (int i = 0; i < mobs_objects.size(); i++) {
                    MapleMonster mob = (MapleMonster) mobs_objects.get(i);
                    mob.applyStatus(chr, new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.POISON, (int) Randomizer.rand(100, 500)), SkillFactory.getSkill(400041000), 10, null, false), (long) 10 * 1000);
                    if (mob.isBuffed(MonsterStatus.Burned) || mob.isBuffed(MonsterStatus.POISON)) {
                        allmobs.add(mob);
                    }
                }
                final int maxmob_count = Math.min(effect.getMobCount(), allmobs.size());
                final List<MapleMonster> mobs = new ArrayList<>();
                for (int i = 0; i < maxmob_count; i++) {
                    final int randmob_remove = Randomizer.rand(0, allmobs.size() - 1);
                    mobs.add(allmobs.get(randmob_remove));
                    allmobs.remove(randmob_remove);
                }
                if (mobs.isEmpty()) {
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                    return;
                }
                List<Triple<Integer, Integer, Integer>> finalMobList = new ArrayList<>();
                int test = 0;
                for (MapleMonster mob : mobs) {
                    finalMobList.add(new Triple<Integer, Integer, Integer>(mob.getObjectId(), 0, test));
                    test++;
                }
                c.send(MainPacketCreator.bonusAttackRequest(400041030, finalMobList, false, 0));
                break;
            }

            case 400051006:
                chr.bulletPartyStartTime = System.currentTimeMillis();
                break;

            case 400011016: {

                List<MapleMapObject> mistsInMap = chr.getMap().getAllMistsThreadsafe();
                MapleMist maha = null;
                for (MapleMapObject nn : mistsInMap) {
                    MapleMist mist = (MapleMist) nn;
                    if (mist.getSource().getSourceId() == 21121068) {
                        maha = mist;
                        break;
                    }
                }

                if (maha == null) {
                    return;
                }

                maha.removeMist();
                SkillFactory.getSkill(400011016).getEffect(skillLevel).applyTo(chr);
                chr.updateCombo((short) (chr.getCombo() + effect.getZ()), System.currentTimeMillis());
                break;
            }
            case 400021047: {
                if (chr.BHGCCount <= 0) {
                    return;
                }

                if (chr.BHGCCount == effect.getY()) {
                    chr.lastBHGCGiveTime = System.currentTimeMillis();
                }
                chr.BHGCCount--;

                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));

                chr.send(MainPacketCreator.giveBuff(400021047, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
                break;
            }

            case 400011015: { // Limit brake
                // Reducing Cool Time
                for (MapleCoolDownValueHolder cooltime : new LinkedList<>(chr.getAllCooldowns())) {
                    Skill cooltimeSkill = (Skill) SkillFactory.getSkill(cooltime.skillId);

                    int reduceTime = effect.getStat("q") * 10 - 1000;

                    chr.changeCooldown(skillid, -reduceTime);
                }

                return;
            }

            case 400041007: {
                return;
            }

            case 2001009: {
                if (chr.isActiveBuffedValue(2201009)) {
                    if (chr.getBuffedSkillEffect(BuffStats.ChillingStep).makeChanceResult()) {
                        int y = chr.getPosition().y;
                        int x_max = chr.getPosition().x;
                        int x_min = x_max - 200;
                        int x_center = ((x_max - x_min) / 2) + x_min;
                        chr.getBuffedSkillEffect(BuffStats.ChillingStep).applyMist(chr, new Point(x_max, y));
                        chr.getBuffedSkillEffect(BuffStats.ChillingStep).applyMist(chr, new Point(x_min, y));
                        chr.getBuffedSkillEffect(BuffStats.ChillingStep).applyMist(chr, new Point(x_center, y));
                    }
                }
                break;
            }
            case 36121002: // Hologram Graffiti: Penetrating
            case 36121013: // Hologram Graffiti: Force Field
            case 36121014: { // Hologram graffiti: stand by
                Point pos = rh.readPos();
                MapleSummon summon = new MapleSummon(chr, skillid, chr.getPosition(), SummonMovementType.STATIONARY, System.currentTimeMillis());
                chr.getMap().spawnSummon(summon, true, 20000);
                break;
            }
            case 400041034: // A.D governance
            case 400041033: { // A.D governance
                Point pos = rh.readPos();
                break;
            }
            case 101100100:
            case 101100101: { // Throwing weapon
                rh.skip(5);
                Point pos = rh.readPos();
                MapleSummon summon = new MapleSummon(chr, 101100100, pos, SummonMovementType.ZEROWEAPON, System.currentTimeMillis());
                chr.getMap().spawnSummon(summon, true, 5000);
                break;
            }
            case 25100009: { // Spirit Fox
                rh.skip(1);
                int sn = rh.readInt();
                c.getPlayer().send(MainPacketCreator.absorbingFG(c.getPlayer().getId(), 25100010, sn));
                여우령 = 50; // spirit Fox
                break;
            }
            case 25120110: // Disgraced
                rh.skip(1);
                int fsn = rh.readInt();
                c.getPlayer().send(MainPacketCreator.absorbingFG(c.getPlayer().getId(), 25120115, fsn));
                여우령 = 100;
                break;
            case 12001027:
            case 12001028: { // Fireworks
                c.getPlayer().getMap().broadcastMessage(MainPacketCreator.FireWork(chr));
                break;
            }
            case 12101025: { // Fire Blink
                rh.skip(9);
                int y1 = rh.readInt();
                int y2 = rh.readInt();
                c.getSession().writeAndFlush(MainPacketCreator.FireBlink(c.getPlayer().getId(), y1, y2));
               // c.getPlayer().getMap().broadcastMessage(MainPacketCreator.FireBlink(c.getPlayer().getId(), y1, y2));
                //c.getPlayer().getMap().broadcastMessage(MainPacketCreator.FireBlink(chr.getId(), y1, y2));
                break;
            }
            case 400021071: {
                MapleSummon summons = new MapleSummon(c.getPlayer(), 400021071, c.getPlayer().getPosition(), SummonMovementType.STATIONARY, System.currentTimeMillis());
                c.getPlayer().getMap().spawnSummon(summons, true, effect.getDuration());
                effect.applyTo(chr);
                break;
            }
            case 12120013:
            case 12120014: { // Spirit of Flame
                rh.skip(7);
                if (chr.getSkillLevel(skillid) <= 0) {
                    chr.teachSkill(skillid, (byte) 30, (byte) 30);
                }
                for (Pair<Integer, MapleSummon> summon : chr.getSummons().values()) {
                    if (summon.left == 12120013 || summon.left == 12120014) {
                        chr.cancelEffect(chr.getBuffedSkillEffect(BuffStats.IgnoreMobDamR, summon.left), false, -1);
                    }
                }
                break;
            }
            case 1311014: {
                for (Pair<Integer, MapleSummon> summon : chr.getSummons().values()) {
                    if (summon.getLeft() == 1301013) {
                        chr.getMap().broadcastMessage(MainPacketCreator.SummonBeholderRevengeAttack(chr.getId(),
                                summon.getRight().getObjectId(), 1311014, 1));
                    }
                }
                break;
            }
            case 400011054: {
                for (MapleMapObject ob : chr.getMap().getAllSummon()) {
                    MapleSummon summon = (MapleSummon) ob;
                    if (summon.getOwner() != null && summon.getOwner().getId() == chr.getId()
                            && summon.getSkill() == 1301013) {
                        chr.getMap().broadcastMessage(MainPacketCreator.SummonBeholderRevengeAttack(chr.getId(),
                                summon.getObjectId(), 400011054, 1));
                        break;
                    }
                }
                chr.addCooldown(skillid, System.currentTimeMillis(), effect.getCooldown() * 1000);
            }
            case 65111100: { // Soul Seeker
                rh.skip(13);
                int soulnum = rh.readByte();
                int scheck = 0;
                int scheck2 = 0;
                if (soulnum == 1) {
                    scheck = rh.readInt();
                } else if (soulnum == 2) {
                    scheck = rh.readInt();
                    scheck2 = rh.readInt();
                }
                rh.skip(3);
                c.send(AngelicBusterSkill.SoulSeeker(chr, 65111007, soulnum, scheck, scheck2));
                c.send(AngelicBusterSkill.unlockSkill());
                break;
            }
            case 2121052: // Catfish Flame
            case 31221001: // Shield chasing
            case 35101002: // Homing missile
            case 35110017: { // Advanced homing missile
                chr.addCooldown(skillid, System.currentTimeMillis(), effect.getCooldown() * 1000);
                List<Integer> moblist = new ArrayList<Integer>();
                rh.skip(9);
                if (skillid == 31221001) {
                    rh.skip(4);
                }
                int len = rh.readByte();
                for (int i = 0; i < len; i++) {
                    moblist.add(rh.readInt());
                }
                switch (skillid) {
                    case 31221001:
                        if (moblist.size() == 1) {
                            moblist.add(moblist.get(0));
                        }
                        int idx = c.getPlayer().getNextSCIdx();
                        c.getPlayer().addSC(idx);
                        c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.ShieldChacing(chr.getId(), moblist, 31221014, idx), true);
                        idx = c.getPlayer().getNextSCIdx();
                        c.getPlayer().addSC(idx);
                        c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.ShieldChacing(chr.getId(), moblist, 31221014, idx), true);
                        break;
                    case 2121052:
                        c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.MegidoFlameRe(chr.getId(), moblist.get(0), chr.getPosition(), rh.readByte(), rh.readByte(), rh.readByte()), true);
                        break;
                    case 35101002:
                    case 35110017:
                        if (chr.isActiveBuffedValue(35121055)) {
                            c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.HomingMisile(chr.getId(), moblist, skillid, skillLevel), true);
                        }
                        c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.HomingMisile(chr.getId(), moblist, skillid, skillLevel), true);
                        break;
                    case 142110011:
                        for (int i = 0; i < moblist.size(); i++) {
                            c.getPlayer().getMap().broadcastMessage(chr, MainPacketCreator.PsychicGrep(chr.getId(), moblist.get(i), i, skillid, skillLevel), true);
                        }
                        break;
                    default:
                        break;
                }
                break;
            }
            case 12101022: { // Burn and Rest
                int a = SkillFactory.getSkill(12101022).getEffect(chr.getSkillLevel(12101022)).getX();
                chr.addMP((int) (chr.getStat().getCurrentMaxMp() * (a / 100.0D)));
                chr.addCooldown(skillid, System.currentTimeMillis(), effect.getCooldown() * 1000);
                break;
            }
            case 4211006: { // Meso Explosion
                rh.skip(3);
                List<MapleMapObject> drops = c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 320000, Arrays.asList(MapleMapObjectType.ITEM));
                final List<MapleWorldMapItem> allmesos = new ArrayList<>();
                for (int i = 0; i < drops.size(); i++) { // 1 method in scope, ownership of which method
                    MapleWorldMapItem drop = (MapleWorldMapItem) drops.get(i);
                    if (drop.getMeso() == 1 && drop.getOwner() == c.getPlayer().getId()) {
                        allmesos.add(drop);
                    }
                }
                int maxmeso_count = Randomizer.rand(allmesos.isEmpty() ? 0 : 1, allmesos.size() > effect.getBulletCount() ? effect.getBulletCount() : allmesos.size());
                if (maxmeso_count > c.getPlayer().mesoCount) {
                    maxmeso_count = c.getPlayer().mesoCount;
                }

                final List<Pair<Integer, Point>> mesos = new ArrayList<>();
                for (int i = 0; i < maxmeso_count; i++) {
                    final int randmeso_remove = Randomizer.rand(0, allmesos.size() - 1);
                    mesos.add(new Pair(allmesos.get(randmeso_remove).getObjectId(), allmesos.get(randmeso_remove).getPosition()));
                    allmesos.remove(randmeso_remove);
                }
                final List<MapleMapObject> mobjects = c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 320000, Arrays.asList(MapleMapObjectType.MONSTER));
                final List<Integer> moids = new ArrayList<>();
                final int randmob_count = Randomizer.rand(mobjects.isEmpty() ? 0 : 1, mobjects.size() > 10 ? 10 : mobjects.size());
                for (int i = 0; i < randmob_count; i++) {
                    final int randmob_remove = Randomizer.rand(0, mobjects.size() - 1);
                    moids.add(mobjects.get(randmob_remove).getObjectId());
                    mobjects.remove(randmob_remove);
                }
                if (mesos.isEmpty() || moids.isEmpty()) {
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                    return;
                }
                MapleWorldMapItem remove;
                for (int i = 0; i < mesos.size(); i++) {
                    remove = (MapleWorldMapItem) c.getPlayer().getMap().getMapObject(mesos.get(i).left, MapleMapObjectType.ITEM);
                    c.getPlayer().getMap().removeMapObject(remove);
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.removeItemFromMap(remove.getObjectId(), 0, c.getPlayer().getId()));
                }
                c.getPlayer().getMap().broadcastMessage(MainPacketCreator.giveMesoExplosion(c.getPlayer().getId(), moids, mesos));
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                c.getPlayer().mesoCount -= mesos.size();
                c.getPlayer().addpocket();
                break;
            }
            case 33120056: //Lampi As One
            case 33121052: //Lampi As One
            case 33121255: //Lampi As One
            case 33001025:
            case 33101115:
            case 33111015:
            case 33121017:
            case 33001016: {
                c.getPlayer().send(MainPacketCreator.OnJaguarSkill(skillid));
                int cool = effect.getCooldown();
                if (skillid == 33101115) {
                    cool = 7;
                    skillid = 33101215;
                }
                if (skillid == 33001025) {
                    final List<MapleMapObject> objs = chr.getMap().getMapObjectsInRange(chr.getTruePosition(), 200000, Arrays.asList(MapleMapObjectType.MONSTER));
                    final List<MapleMonster> monsters = new ArrayList<>();
                    int i = 0;
                    for (MapleMapObject o : objs) {
                        monsters.add((MapleMonster) o);
                        i++;
                        if (i == 10) {
                            break;
                        }
                    }
                    for (MapleMonster mob2 : monsters) {
                        SkillStatEffect eff = SkillFactory.getSkill(33001025).getEffect(chr.getSkillLevel(33001025));
                        mob2.applyStatus(chr, new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.MS_JaguarProvoke, 1), SkillFactory.getSkill(33001025), chr.getSkillLevel(33001025), null, false), eff.getDuration());
                    }
                }

                c.getPlayer().addCooldown(skillid, System.currentTimeMillis(), cool * 1000);
                break;

            }
        }
        if (chr.lastUsedSkill + 100 > System.currentTimeMillis()) {
            chr.usedSkillFast++;
        } else {
            chr.usedSkillFast = 0;
        }
        chr.lastUsedSkill = System.currentTimeMillis();

        if (skillid == 5211011) { // Assemble Crew
            switch (Randomizer.nextInt(2)) {
                case 0:
                    skillid = 5211011;
                    break;
                case 1:
                    skillid = 5211015;
                    break;
                case 2:
                    skillid = 5211016;
                    break;
            }
        }
        switch (skillid) {
            case 64001012:
                chr.cancelEffectFromBuffStat(BuffStats.DarkSight);
                break;
            case 36111008:
                chr.giveSurPlus(10);
                break;
            case 36111003:
                chr.dualBrid = 10;
                break;
            case 36121054:
                chr.giveSurPlus(20);
                break;
            case 4221054:
                chr.send(MainPacketCreator.OnOffFlipTheCoin(false));
                if (!chr.isActiveBuffedValue(skillid)) {
                    chr.flipTheCoin = 0;
                }
                break;
            case 2111010:
                chr.setSlimeVirusCount(10);
                break;
            case 2111011:
                chr.elementalAdep = 5;
                break;
            case 15001022:
                chr.lightning = 1;
                break;
            case 25121209:
                chr.SpiritGuard = 3;
                break;
            case 27121054:
                chr.luminusskill = new int[2];
                chr.luminusskillLen = new int[2];
                break;
            case 32001014: //death
                chr.deathCount = 1;
                break;
            case 32100010: // Death Contract
                chr.deathCount = 1;
                break;
            case 32110017: // Death Contract 2
            case 32120019: // Death Contract 3
                chr.deathCount = 1;
                break;
        }
        if (GameConstants.isBlaster(chr.getJob())) {
            chr.giveBulletGauge(skillid, false);
        }
        if (skill != null) {
            if (skillid == 31211004) {
                chr.startDiabolicRecovery(effect);
            }

            if (effect.getPowerEnergy() > 0) {
                if (!chr.isActiveBuffedValue(36121054)) {
                    chr.giveSurPlus(-effect.getPowerEnergy());
                }
            }

            if (GameConstants.isKinesis(chr.getJob())) {
                chr.givePPoint(effect, false);
            }
            switch (skillid) {
                case 1121001:
                case 1221001:
                case 1321001:
                case 9001020:
                    byte number_of_mobs = rh.readByte();
                    rh.skip(3);
                    for (int i = 0; i < number_of_mobs; i++) {
                        int mobId = rh.readInt();
                        MapleMonster mob = chr.getMap().getMonsterByOid(mobId);
                        if (mob != null) {
                            mob.switchController(chr, mob.isControllerHasAggro());
                        }
                    }
                    break;
                case 30001061: { //Capture
                    rh.skip(5);
                    int mobid = rh.readInt();
                    MapleMonster mob = chr.getMap().getMonsterByOid(mobid);
                    chr.send(MainPacketCreator.catchMonster(mobid, (byte) 1));
                    chr.getMap().killMonster(mob, chr, false, false, (byte) 1);
                    chr.setKeyValue2("CapturedJaguar", mob.getId());
                    chr.ea();
                    chr.send(MainPacketCreator.captureMob(true));
                    chr.send(MainPacketCreator.updateJaguar(chr));
                    break;
                }
                case 400051017: {
                    MapleSummon summons = new MapleSummon(c.getPlayer(), 400051017, c.getPlayer().getPosition(), SummonMovementType.FOLLOW, System.currentTimeMillis());
                    c.getPlayer().getMap().spawnSummon(summons, true, effect.getDuration());
                    c.send(MainPacketCreator.bonusAttackRequest(400051017, Collections.EMPTY_LIST, true, 0));
                    effect.applyTo(chr);
                    return;
                }
                case 35121009: {
                    MapleSummon summons = new MapleSummon(c.getPlayer(), 35121009, c.getPlayer().getPosition(), SummonMovementType.STATIONARY, System.currentTimeMillis());
                    c.getPlayer().getMap().spawnSummon(summons, true, effect.getDuration());
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (chr.getKeyValue2("Robot") == 11) {
                                chr.setKeyValue2("Robot", 0);
                                timer.cancel();
                            }
                            chr.setKeyValue2("Robot", chr.getKeyValue2("Robot") + 1);
                            for (int i = 0; i < 3; i++) {
                                MapleSummon tosummon = new MapleSummon(c.getPlayer(), 35121011, 3000, summons.getPosition(), SummonMovementType.WALK_STATIONARY, System.currentTimeMillis());
                                chr.getMap().spawnSummon(tosummon, true, -2);
                            }
                        }
                    }, 0, 3000);
                    chr.ea();
                    break;
                }
                case 35111002: { // Magnetic field
                    MapleSummon sumon = new MapleSummon(chr, 35111002, chr.getPosition(), SummonMovementType.STATIONARY, System.currentTimeMillis());
                    chr.getMap().spawnSummon(sumon, true, 20000);
                    chr.ea();
                    if (chr.getMap().countSummonSkill(chr, 35111002) == 3) {
                        c.send(MainPacketCreator.skillCooldown(35111002, effect.getCooldown()));
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showMagneticConnect(c.getPlayer().getId(), chr.getMap().getSummonObjects(chr, 35111002)));
                        c.getPlayer().send(MainPacketCreator.showMagneticConnect(c.getPlayer().getId(), chr.getMap().getSummonObjects(chr, 35111002)));
                    }
                    return;
                }
                case 400051022: {
                    chr.summonxy = new Point(chr.getPosition().x, chr.getPosition().y);
                    chr.귀문진시작(effect);
                    effect.applyTo(c.getPlayer(), chr.getTruePosition());
                    return;
                }
                
		case 400011077: { // Ortross 
                    MapleSummon left = new MapleSummon(c.getPlayer(), 400011077, new Point(chr.getPosition().x+400,chr.getPosition().y), SummonMovementType.FOLLOW, System.currentTimeMillis());
                    chr.getMap().spawnSummon(left, true, 5000);
                    /*
                    MapleSummon right = new MapleSummon(c.getPlayer(), 400011077, new Point(chr.getPosition().x+100,chr.getPosition().y), SummonMovementType.FOLLOW, System.currentTimeMillis());
                    chr.getMap().spawnSummon(right, true, 5000);
                    */
                    effect.applyTo(chr);
                    chr.send(MainPacketCreator.resetActions(c.getPlayer()));
                return;
             }
                case 12111022: { //Maelstroom
                    rh.skip(9);
                    Point mpos = rh.readPos();
                    rh.skip(3);
                    int mobid = rh.readInt();
                    // Deleted Maelstrom summoned before Maelstrom.
                    List<MapleSummon> maelstrom = new ArrayList<>();
                    chr.getSummons().values().stream().filter(sum -> sum.left == 12111022).forEach(s -> maelstrom.add(s.right));
                    maelstrom.forEach(sum -> {
                        chr.removeSummon(sum.getObjectId());
                        chr.getMap().broadcastMessage(MainPacketCreator.removeSummon(sum, true));
                        chr.getMap().removeMapObject(sum);
                        chr.removeVisibleMapObject(sum);
                    });
                    MapleSummon summon = new MapleSummon(chr, skillid, mpos, SummonMovementType.STATIONARY, System.currentTimeMillis());
                    summon.setPosition(mpos);
                    summon.setMaelstromId(chr.getMap().getMonsterByOid(mobid).getId());
                    chr.getMap().spawnSummon(summon, true, effect.getDuration());
                    chr.getSummons().put(summon.getObjectId(), new Pair<>(12111022, summon));
                    chr.ea();
                    break;
                }
                case 155101006: {

                    Map<SkillStatEffect, Long> bsvhs = new HashMap<>();
                    c.getPlayer().getEffects().entrySet().stream().forEach((Entry<BuffStats, List<BuffStatsValueHolder>> effect1) -> {
                        effect1.getValue().stream().filter((bsvh) -> (!bsvhs.containsKey(bsvh.effect) && bsvh.effect.getSourceId() == 155101006)).forEach((bsvh) -> {
                            bsvhs.put(bsvh.effect, bsvh.startTime);
                        });
                    });
                    if (bsvhs.size() > 0) {
                        bsvhs.entrySet().stream().forEach((bsvh) -> {
                            chr.cancelEffect(bsvh.getKey(), false, bsvh.getValue());
                        });
                    } else {
                        effect.applyTo(chr);
                    }

                    c.getPlayer().giveCoolDowns(skillid, System.currentTimeMillis(), 3000);
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                    break;
                }
                case 152121005: {
                    chr.saveCrystalskill2 = 0;
                    chr.cancelEffectFromBuffStat(BuffStats.IndieIliumStack, 152001003);
                    chr.cancelEffectFromBuffStat(BuffStats.IndieIliumStack, 152101008);
                    for (int i = 0; i < 5; ++i) {
                        SkillStatEffect a = SkillFactory.getSkill(152121006).getEffect(c.getPlayer().getSkillLevel(152121005));
                        MapleSummon sumon = new MapleSummon(chr, 152121006, chr.getTruePosition(), SummonMovementType.BIRD_FOLLOW, System.currentTimeMillis());
                        chr.getMap().spawnSummon(sumon, true, a.getDuration());
                        chr.getSummons().put(sumon.getObjectId(), new Pair<>(152121006, sumon));
                    }
                    chr.getMap().broadcastMessage(chr, MainPacketCreator.EnableCrystal(chr, chr.getCrystalOid(), 2, 3), true);
                    effect.applyTo(chr);
                    break;
                }
                case 152111007: {
                    chr.getMap().broadcastMessage(chr, MainPacketCreator.EnableCrystal(chr, chr.getCrystalOid(), 1, 3), true);
                    SkillStatEffect eff = SkillFactory.getSkill(skillid).getEffect(skillid);
                    chr.saveCrystalskill1 = 0;
                    chr.getMap().broadcastMessage(chr, MainPacketCreator.EnableCrystal(chr, chr.getCrystalOid(), 2, 3), true);
                    chr.getMap().broadcastMessage(chr, MainPacketCreator.CrystalSkill(chr, chr.getCrystalOid(), 3), true);
                    if (eff != null) {
                        eff.applyTo(c.getPlayer());
                    }
                    c.getPlayer().setHamonyCount(0);
                    break;
                }
                case 400051025: {
                    rh.skip(9);
                    Point pos = rh.readIntPos();
                    effect.applyTo(chr);
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.ICBM(skillid, effect.calculateBoundingBox(pos, c.getPlayer().isFacingLeft())));
                    SkillStatEffect a = SkillFactory.getSkill(400051025).getEffect(chr.getSkillLevel(400051024));
                    chr.getMap().spawnMist(new MapleMist(a.calculateBoundingBox(pos, chr.isFacingLeft()), chr, a), 1500, false, false, false, false, false);
                    SkillStatEffect b = SkillFactory.getSkill(400051026).getEffect(chr.getSkillLevel(400051024));
                    chr.getMap().spawnMist(new MapleMist(b.calculateBoundingBox(pos, chr.isFacingLeft()), chr, b), 15000, false, false, false, false, false);
                    break;
                }
                case 152111003: {
                    chr.cancelEffectFromBuffStat(BuffStats.IndieIliumStack, 152101000);
                    for (MapleMapObject ob : c.getPlayer().getMap().getAllSummon()) {
                        MapleSummon summon = (MapleSummon) ob;
                        if (summon.getOwner() != null && summon.getOwner().getId() == c.getPlayer().getId() && summon.getSkill() == 152101000) {
                            summon.removeSummon(chr.getMap());
                            break;
                        }
                    }
                    chr.setCristalCharge(0);
                    chr.getMap().broadcastMessage(chr, MainPacketCreator.CrystalSkill(chr, chr.getCrystalOid(), 2), true);
                    chr.resetEnableCristalSkill(chr.getCrystalOid());
                    effect.applyTo(chr);
                    break;
                }
                case 3321034: {
                    chr.setRelicCount(1000);
                    effect.applyTo(chr);
                    break;
                }
                default:
                    Point pos = null;
                    if (rh.available() > 12) {
                        rh.skip(9);
                    }
                    if (effect.getSourceId() == 2121054 && GameConstants.isPhantom(chr.getJob())) {
                        c.getPlayer().addCooldown(2121054, System.currentTimeMillis(), 75000);
                    }
                    if (rh.available() > 3) {
                        pos = rh.readPos();
                    }
                    if (effect.isMagicDoor()) {
                        for (Pair<Integer, MapleSummon> summon2 : chr.getSummons().values()) {
                            if (summon2.right.getSkill() == 35101005) {
                                summon2.right.setEndTime(System.currentTimeMillis() + 30000);
                            }
                            break;
                        }
                        if (!FieldLimitType.MysticDoor.check(chr.getMap().getFieldLimit())) {
                            effect.applyTo(c.getPlayer(), pos);
                        } else {
                            chr.dropMessage(5, "Mystic door cannot be summoned at this location.");
                            chr.send(MainPacketCreator.resetActions(c.getPlayer()));
                        }
                    } else {
                        if (effect.parseMountInfo(c.getPlayer(), skill.getId()) != 0 && c.getPlayer().getBuffedValue(BuffStats.MonsterRiding) == null && c.getPlayer().getDragon() != null) {
                            c.getPlayer().getMap().broadcastMessage(MainPacketCreator.removeDragon(c.getPlayer().getId()));
                            c.getPlayer().getMap().removeMapObject(c.getPlayer().getDragon());
                            c.getPlayer().setDragon(null);
                        }
                        boolean death = false;
                        if (effect.getSourceId() == 32001014) {
                            if (c.getPlayer().getBuffedValue(BuffStats.BMageDeath, 32001014) != null) {
                                death = true;
                            }
                        }
                        boolean death1 = false;
                        if (effect.getSourceId() == 32100010) {
                            if (c.getPlayer().getBuffedValue(BuffStats.BMageDeath, 32100010) != null) {
                                death1 = true;
                            }
                        }
                        boolean death2 = false;
                        if (effect.getSourceId() == 32110017) {
                            if (c.getPlayer().getBuffedValue(BuffStats.BMageDeath, 32110017) != null) {
                                death2 = true;
                            }
                        }
                        boolean death3 = false;
                        if (effect.getSourceId() == 32120019) {
                            if (c.getPlayer().getBuffedValue(BuffStats.BMageDeath, 32120019) != null) {
                                death3 = true;
                            }
                        }
                        boolean v1 = false;
                        if (effect.getSourceId() == 2101010) {
                            if (c.getPlayer().isActiveBuffedValue(2101010)) {
                                v1 = true;
                            }
                        }
                        boolean v2 = false;
                        if (effect.getSourceId() == 35001002) {
                            if (c.getPlayer().isActiveBuffedValue(35001002)) {
                                v2 = true;
                            } else {
                                c.getPlayer().cancelEffectFromBuffStat(BuffStats.Mechanic, 35111003);
                            }
                        }
                        if (effect.getSourceId() != 36121002 && effect.getSourceId() != 36121013 && effect.getSourceId() != 36121014) {
                            if (skillid == 400051038) {
                                MapleSummon sumon = new MapleSummon(chr, 400051052, effect.getDuration(), pos, SummonMovementType.WALK_FOLLOW, System.currentTimeMillis());
                                chr.getMap().spawnSummon(sumon, true, effect.getDuration());
                                chr.getSummons().put(sumon.getObjectId(), new Pair<>(400051052, sumon));
                                MapleSummon sumon1 = new MapleSummon(chr, 400051053, effect.getDuration(), pos, SummonMovementType.WALK_FOLLOW, System.currentTimeMillis());
                                chr.getMap().spawnSummon(sumon1, true, effect.getDuration());
                                chr.getSummons().put(sumon1.getObjectId(), new Pair<>(400051053, sumon1));
                            }
                            if (skillid == 4111009) {
                                rh.skip(2);
                                boolean pet = rh.readByte() == 1;
//                                System.out.println(pet);
                                if (pet) {
                                    if (!chr.isActiveBuffedValue(4111009)) {
                                        effect.applyTo(c.getPlayer(), pos);
                                    } else {
                                        chr.ea();
                                    }
                                } else {
                                    effect.applyTo(c.getPlayer(), pos);
                                }
                            } else {
                                effect.applyTo(c.getPlayer(), pos);
                            }
                        }
                        if (v1) {
                            c.getPlayer().cancelEffectFromBuffStat(BuffStats.WizardIgnite, 2101010);
                        }
                        if (v2) {
                            c.getPlayer().cancelEffectFromBuffStat(BuffStats.MonsterRiding, 35001002);
                        }

                        if (skill.isPairSkill() && chr.getBuffedValue(BuffStats.HolyUnity) != null
                                && skill.getId() != 400011003) {
                            MapleCharacter targetChr = chr.getMap()
                                    .getCharacterById(chr.getBuffedValue(BuffStats.HolyUnity));

                            if (targetChr != null) {
                                effect.applyTo(targetChr, pos);
                            }
                        }

                        if (skill.getId() == 400011012) {
                            SkillFactory.getSkill(400011013).getEffect(chr.getSkillLevel(400011012)).applyTo(c.getPlayer(),
                                    pos);
                            SkillFactory.getSkill(400011014).getEffect(chr.getSkillLevel(400011012)).applyTo(c.getPlayer(),
                                    pos);
                        }
                    }
                    break;
            }

            boolean hasNoCoolTime = false;
            for (InnerSkillValueHolder isvh : chr.getInnerSkills()) {
                if (isvh.getSkillId() == 70000045) {
                    hasNoCoolTime = Randomizer.isSuccess(SkillFactory.getSkill(70000045).getEffect(chr.getSkillLevel(70000045)).getSkillStats().getStats("nocoolProp"));
                    if (hasNoCoolTime) {
                        break;
                    }
                }
            }

            if (!hasNoCoolTime && skillid != 35111002) {
                if (!chr.isEquilibrium() && !GameConstants.isDarkSkills(skillid)) {
                    c.getSession().writeAndFlush(MainPacketCreator.skillCooldown(skillid, (skillid == 142121008) ? 45 * 1000 : effect.getCooldown()));

                    chr.addCooldown(skillid, System.currentTimeMillis(), (skillid == 142121008) ? 45 * 1000 : effect.getCooldown() * 1000);
                }
            }

            if (skillid >= 51001006 && skillid <= 51001010) {
                chr.addCooldown(51001005, System.currentTimeMillis(), effect.getCooldown() * 1000);
            } else if (skillid == 23111009 || skillid == 23111010) {
                if (skillid == 23111009 || skillid == 23111010) { // Elemental Night-Cool Time Share
                    if (chr.skillisCooling(23111008)) {
                        return;
                    }
                    chr.addCooldown(23111008, System.currentTimeMillis(), effect.getCooldown() * 1000);
                }
            } else if (skillid == 5211015 || skillid == 5211016) {
                if (skillid == 5211015 || skillid == 5211016) { // Assemble Crew-Cool Time Share
                    if (chr.skillisCooling(5211011)) {
                        return;
                    }
                    chr.addCooldown(5211011, System.currentTimeMillis(), effect.getCooldown() * 1000);
                }
            } else if (effect.getCooldown() > 0 && !chr.isEquilibrium()) {
                chr.addCooldown(skillid, System.currentTimeMillis(), effect.getCooldown() * 1000);
            }

            if (skillid == 400021032) {
                chr.performAngelOfLibra();
            }
        }
    }

    public static void closeRangeAttack(ReadingMaple rh, final MapleClient c, final MapleCharacter chr,
            RecvPacketOpcode recv) {

        if (c.getPlayer() == null || !chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }

        AttackInfo attack = DamageParse.parseDmg(c.getPlayer(), rh, RecvPacketOpcode.CLOSE_RANGE_ATTACK.getValue(), recv.getValue());
        if(attack.skill == 155121306) {
            Map<SkillStatEffect, Long> bsvhs = new HashMap<>();
            chr.getEffects().entrySet().stream().forEach((Entry<BuffStats, List<BuffStatsValueHolder>> effect1) -> {
                effect1.getValue().stream().filter((bsvh) -> (!bsvhs.containsKey(bsvh.effect) && bsvh.effect.getSourceId() == 155101006)).forEach((bsvh) -> {
                    bsvhs.put(bsvh.effect, bsvh.startTime);
                });
            });
            if (bsvhs.size() == 0) {
                SkillFactory.getSkill(155101006).getEffect(chr.getSkillLevel(155101006)).applyTo(chr);
            }
        }
        if (attack.skill > 0) {
            if (chr.getMapId() == 123456788) {
                chr.changeMap(931000313, 1);
                chr.dropMessage(1, "Use of skills in the village is prohibited.");
                return;
            }
            if(chr.gettimes() >= System.currentTimeMillis()) {
                if(chr.getsaveSkill() == 15001020 && attack.skill == 15001021) {
                   if(chr.getaa() >= 8 && chr.getCooldownLimit(400051044) == 0) {
                       chr.send(MainPacketCreator.specialskill(400051044, 5, attack.position));
                       chr.setaa(0);
                       chr.stackCool(400051044, System.currentTimeMillis(), 7000);
                   }
                   chr.settimes(0);
                   chr.setaa(chr.getaa() + 1);
                   SkillFactory.getSkill(400051044).getEffect(chr.getSkillLevel(400051044)).applyTo(chr);
                } else if(chr.getsaveSkill() == 15101020 && attack.skill == 15101021) {
                   chr.settimes(0);
                   if(chr.getaa() >= 8 && chr.getCooldownLimit(400051044) == 0) {
                       chr.send(MainPacketCreator.specialskill(400051044, 5, attack.position));
                       chr.setaa(0);
                       chr.stackCool(400051044, System.currentTimeMillis(), 7000);
                   }
                   chr.setaa(chr.getaa() + 1);
                   SkillFactory.getSkill(400051044).getEffect(chr.getSkillLevel(400051044)).applyTo(chr);
                } else if(chr.getsaveSkill() == 15111020 && attack.skill == 15111021) {
                   chr.settimes(0);
                   if(chr.getaa() >= 8 && chr.getCooldownLimit(400051044) == 0) {
                       chr.send(MainPacketCreator.specialskill(400051044, 5, attack.position));
                       chr.setaa(0);
                       chr.stackCool(400051044, System.currentTimeMillis(), 7000);
                   }
                   chr.setaa(chr.getaa() + 1);
                   SkillFactory.getSkill(400051044).getEffect(chr.getSkillLevel(400051044)).applyTo(chr);
                } else if(chr.getsaveSkill() == 15121001 && attack.skill == 15121002) {
                   chr.settimes(0);
                   if(chr.getaa() >= 8 && chr.getCooldownLimit(400051044) == 0) {
                       chr.stackCool(400051044, System.currentTimeMillis(), 7000);
                       chr.send(MainPacketCreator.specialskill(400051044, 5, attack.position));
                       chr.setaa(0);
                   }
                   chr.setaa(chr.getaa() + 1);
                   SkillFactory.getSkill(400051044).getEffect(chr.getSkillLevel(400051044)).applyTo(chr);
                } else {
                    chr.settimes(0);
                }
            }
           if(attack.skill == 15001020) {
               chr.settimes(System.currentTimeMillis() + 3000);
               chr.setsaveSkill(attack.skill);
           } else if(attack.skill == 15101020) {
               chr.settimes(System.currentTimeMillis() + 3000);
               chr.setsaveSkill(attack.skill);
           } else if(attack.skill == 15111020) {
               chr.settimes(System.currentTimeMillis() + 3000);
               chr.setsaveSkill(attack.skill);
           } else if(attack.skill == 15121001) {
               chr.settimes(System.currentTimeMillis() + 3000);
               chr.setsaveSkill(attack.skill);
           }
            
            if (chr.getSkillLevel(5100015) > 0) {
                int 최대에너지 = chr.getSkillLevel(5110014) > 0 ? 10000 : 5000;
                int 에너지충전량 = chr.getSkillLevel(5120018) > 0 ? 350 : chr.getSkillLevel(5110014) > 0 ? 300 : 150;
                int 에너지감소량 = 0;
                switch (attack.skill) {
                    case 5101014: // Energy tornado
                        에너지감소량 = 85;
                        break;
                    case 5111013:
                        에너지감소량 = 140;
                        break;
                    case 5111012:
                    case 5111015: // Shock wave
                    case 5121001:
                    case 5121019: // Dragon Strike
                        에너지감소량 = 180;
                        break;
                    case 5121007:
                    case 5121020:
                        에너지감소량 = 150;
                        break;
                    case 5121017: // Double blast
                        에너지감소량 = 260;
                        break;
                    case 5121052:
                    case 5121055: // Unity of power
                        에너지감소량 = 1500;
                        break;
                    case 400051015: //Serpent Screw
                        에너지감소량 = 130;
                        break;
                }

                if (chr.에너지차지구분 == 0 && attack.skill != 400051015) {
                    chr.acaneAim += (에너지충전량 * 2);
                } else if (chr.에너지차지구분 == 1 || attack.skill == 400051015) {
                    chr.acaneAim -= 에너지감소량;
                }
                int 현재에너지 = chr.acaneAim;
                if (현재에너지 <= 0 && chr.isActiveBuffedValue(400051015)) {
                    chr.cancelEffectFromBuffStat(BuffStats.DevilishPower);
                }
                if (chr.에너지차지구분 == 0 && attack.skill != 400051015) {
                    if (현재에너지 >= 10000) {
                        chr.send(MainPacketCreator.giveEnergyCharge(현재에너지, 10000, 5120018, 10000, true, false));
                        chr.acaneAim = 10000;
                        chr.에너지차지구분 = 1;
                    } else {
                        chr.send(MainPacketCreator.giveEnergyCharge(현재에너지, 10000, 5120018, 10000, chr.getBuffedValue(BuffStats.Stimulate) != null ? true : false, false));
                    }
                } else if (chr.에너지차지구분 == 1 || attack.skill == 400051015) {
                    if (현재에너지 <= 0) {
                        chr.send(MainPacketCreator.giveEnergyCharge(0, 0, 5120018, 현재에너지, chr.getBuffedValue(BuffStats.Stimulate) != null ? true : false, false));
                        chr.acaneAim = 0;
                        chr.에너지차지구분 = 0;
                    } else {
                        if (attack.skill != 400051015) {
                            chr.send(MainPacketCreator.giveEnergyCharge(0, 0, 5120018, 현재에너지, chr.getBuffedValue(BuffStats.Stimulate) != null ? true : false, true));
                        } else if (attack.skill == 400051015 && chr.에너지차지구분 == 0) {
                            chr.send(MainPacketCreator.giveEnergyCharge(현재에너지, 10000, 5120018, 10000, chr.getBuffedValue(BuffStats.Stimulate) != null ? true : false, false));
                        } else if (attack.skill == 400051015 && chr.에너지차지구분 == 1) {
                            chr.send(MainPacketCreator.giveEnergyCharge(0, 0, 5120018, 현재에너지, chr.getBuffedValue(BuffStats.Stimulate) != null ? true : false, true));
                        }
                    }
                }
            }
        }
        if (attack.skill == 21120018 || attack.skill == 21120019) {
            if (chr.royalGuard == 1) {
                SkillStatEffect effect = SkillFactory.getSkill(chr.isActiveBuffedValue(21110016) ? 21110016 : 21121058).getEffect(chr.getSkillLevel(chr.isActiveBuffedValue(21110016) ? 21110016 : 21121058));

                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                statups.add(new Triple<>(BuffStats.AdrenalinBoost, 1, false));
                chr.royalGuard = 0;
                chr.send(MainPacketCreator.giveBuff(chr.isActiveBuffedValue(21110016) ? 21110016 : 21121058, Integer.MAX_VALUE, statups, effect, null, 0,
                        chr));
            }
        }
        if (attack.skill == 3311002 || attack.skill == 3311003 || attack.skill == 3321006 || attack.skill == 3321007) {
            if (chr.BHGCCount <= 0) {
                return;
            }

            if (chr.BHGCCount == 5) {
                chr.lastBHGCGiveTime = System.currentTimeMillis();
            }
            chr.BHGCCount--;

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));
            SkillStatEffect effect = SkillFactory.getSkill(3311002).getEffect(chr.getSkillLevel(3311002));
            chr.send(MainPacketCreator.giveBuff(3311002, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
        }

        boolean mirror = chr.getBuffedValue(BuffStats.ShadowPartner) != null;
        int attackCount = ((chr.getJob() >= 430 && chr.getJob() <= 434) ? 2 : (attack.skill == 61101002 || attack.skill == 61110211) ? 3 : (attack.skill == 61120007 || attack.skill == 61121217) ? 5 : 1);
        int skillLevel = attack.skillLevel != 0 ? attack.skillLevel : 1;
        try {
            chr.getCalcDamage().PDamage(chr, attack);
        } catch (Exception ex) {
        }
        ISkill skill = null;
        SkillStatEffect effect = null;
        if (GameConstants.isFrozenMap(chr.getMapId()) && attack.skill == 80001770) {
            return;
        }
        if (GameConstants.SurfaceDamageSkillLink(attack.skill)) {
            for (int i = 0; i < attack.allDamage.size(); ++i) { // Marisu
                for (int x = 0; x < attack.allDamage.get(i).attack.size(); ++x) { // Demb Index
                    MapleMonster Target = chr.getMap().getMonsterByOid(attack.allDamage.get(i).objectid);
                    Target.damage(chr, attack.allDamage.get(i).attack.get(x).left, false);
                    if (Target.getHp() <= 0) {
                        break;
                    }
                }
            }
            return;
        }

        if (attack.skill != 0) {
            skill = SkillFactory.getSkill(attack.skill);
            effect = attack.getAttackEffect(chr, skillLevel, skill);

            if (GameConstants.isDemonAvenger(c.getPlayer().getJob())) {
                if (skill.getName().contains("익시드 :")) {
                    if (c.getPlayer().exeedCount < 20) {
                        c.getPlayer().exeedCount++;
                    }
                    SkillFactory.getSkill(30010230).getEffect(1).applyTo(chr);
                }
            }
            if (attack.skill != 400011019 && GameConstants.isBlaster(chr.getJob()) && chr.getBuffedValue(BuffStats.SlowAttack) != null) {
                chr.send(MainPacketCreator.bonusAttackRequest(400011019, Collections.EMPTY_LIST, true, 0));
            }
            if ((skill.getId() == 31011000 || skill.getId() == 31201000 || skill.getId() == 31211000 || skill.getId() == 31221000)) {
                c.getPlayer().exeedAttackCount++;
            } else if (attack.skill == 61101002 || attack.skill == 61110211) { // Will of Sword
                DamageParse.applyAttack(attack, skill, c.getPlayer(), attackCount, effect, mirror ? AttackType.NON_RANGED_WITH_MIRROR : AttackType.NON_RANGED);
                SkillStatEffect realEffect = SkillFactory.getSkill(61101002).getEffect(c.getPlayer().getSkillLevel(61101002));
                c.getSession().writeAndFlush(MainPacketCreator.skillCooldown(61101002, realEffect.getCooldown()));
                chr.addCooldown(61101002, System.currentTimeMillis(), realEffect.getCooldown() * 1000);
                chr.cancelEffectFromBuffStat(BuffStats.StopForceAtomInfo, -1);
                return;
            } else if (attack.skill == 61120007 || attack.skill == 61121217) { // Advanced Will of Sword (Translation)
                DamageParse.applyAttack(attack, skill, c.getPlayer(), attackCount, effect, mirror ? AttackType.NON_RANGED_WITH_MIRROR : AttackType.NON_RANGED);
                SkillStatEffect realEffect = SkillFactory.getSkill(61101002).getEffect(c.getPlayer().getSkillLevel(61101002));
                c.getSession().writeAndFlush(MainPacketCreator.skillCooldown(61101002, realEffect.getCooldown()));
                chr.addCooldown(61101002, System.currentTimeMillis(), realEffect.getCooldown() * 1000);
                chr.cancelEffectFromBuffStat(BuffStats.StopForceAtomInfo, -1);
                return;
            } else if (attack.skill == 64111012) {
                chr.addCooldown(64111004, System.currentTimeMillis(),
                        SkillFactory.getSkill(64111004).getEffect(skillLevel).getCooldown() * 1000);
            } else if (attack.skill == 64121024) {
                chr.addCooldown(64121021, System.currentTimeMillis(),
                        SkillFactory.getSkill(64121021).getEffect(skillLevel).getCooldown() * 1000);
            }
            attackCount = effect.getAttackCount();
            if (SkillFactory.getSkill(attack.skill).getEffect(skillLevel).getCooldown() > 0) {
                switch (attack.skill) {
                    case 1321013:
                        if (!chr.isActiveBuffedValue(1321015) && !chr.isActiveBuffedValue(1320019)) {
                            chr.addCooldown(attack.skill, System.currentTimeMillis(), effect.getCooldown() * 1000);
                        }
                        break;
                    case 15111022:
                    case 15120003:
                        if (!chr.isActiveBuffedValue(15121054)) {
                            chr.addCooldown(attack.skill, System.currentTimeMillis(), effect.getCooldown() * 1000);
                        }
                        break;
                    case 11121055: {
                        chr.addCooldown(11121052, System.currentTimeMillis(),
                                SkillFactory.getSkill(11121052).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    case 33111013:
                    case 142121005: {
                        if (chr.getCooldownLimit(attack.skill) == 0) {
                            chr.addCooldown(attack.skill, System.currentTimeMillis(),
                                    SkillFactory.getSkill(attack.skill).getEffect(skillLevel).getCooldown() * 1000);
                        }
                        break;
                    }
                    case 400011032:
                    case 400011033:
                    case 400011034:
                    case 400011035:
                    case 400011036:
                    case 400011037: {
                        chr.addCooldown(400011032, System.currentTimeMillis(),
                                SkillFactory.getSkill(400011032).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    case 3321014:  //Combo Assault
                    case 3321015:
                    case 3321016:
                    case 3321017:
                    case 3321018:
                    case 3321019:
                    case 3321020:
                    case 3321021: {
                        chr.addCooldown(3321014, System.currentTimeMillis(),
                                SkillFactory.getSkill(3321014).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    case 3321035:  //Ancient Astra
                    case 3321036:
                    case 3321037:
                    case 3321038:
                    case 3321039:
                    case 3321040: {
                        chr.addCooldown(3321035, System.currentTimeMillis(),
                                SkillFactory.getSkill(3321035).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    case 155001102:  //Revive Nightmare, Endless Nightmare Cool Time
                    case 155110000:
                    case 155120000: {
                        chr.addCooldown(155001102, System.currentTimeMillis(),
                                SkillFactory.getSkill(155001102).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }

                    case 155101104:  //Unstoppable instinct
                    case 155101204:  //
                    case 155101214: {
                        chr.addCooldown(155101104, System.currentTimeMillis(),
                                SkillFactory.getSkill(155101104).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    case 155101100:  //Scarlet Charge Drive
                    case 155101101: {
                        chr.addCooldown(155101100, System.currentTimeMillis(),
                                SkillFactory.getSkill(155101100).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    case 400010030: {
                        chr.addCooldown(400011031, System.currentTimeMillis(),
                                SkillFactory.getSkill(400011031).getEffect(skillLevel).getCooldown() * 1000);
                        break;
                    }
                    default:
                        if (attack.skill != 400011052 && attack.skill != 2221012 && attack.skill != 400051008 && attack.skill != 400031000 && attack.skill != 400031037) {
                            chr.addCooldown(attack.skill, System.currentTimeMillis(),
                                    SkillFactory.getSkill(attack.skill).getEffect(skillLevel).getCooldown() * 1000);
                        }
                        break;
                }
            }

            if (attack.skill == 31111003) { // Bloody Raven Recovery
                int recover = (int) (chr.getStat().getCurrentMaxHp() * (effect.getX() / 100.0D));
                chr.addHP(recover);
            }

            if (GameConstants.isAngelicBuster(chr.getJob())) {
                int Recharge = effect.getOnActive();
                if (chr.getJob() == 6512) {
                    Recharge = 100;
                }
                if (Randomizer.isSuccess(Recharge)) {
                    c.send(AngelicBusterSkill.unlockSkill());
                }
            }
            if (chr.getBuffedValue(BuffStats.GlimmeringTime) != null && attack.skill != 11121052
                    && attack.skill != 11121055 && attack.skill != 400011056) {
                BuffTimer.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        int stateid = 0;
                        if (chr.getBuffedValue(BuffStats.PoseType, 11101022) != null) {
                            chr.cancelEffect(chr.getBuffedSkillEffect(BuffStats.PoseType, 11101022), false, -1);
                            stateid = 11111022;
                        } else if (chr.getBuffedValue(BuffStats.PoseType, 11111022) != null) {
                            chr.cancelEffect(chr.getBuffedSkillEffect(BuffStats.PoseType, 11111022), false, -1);
                            stateid = 11101022;
                        }
                        SkillStatEffect stateeffect = SkillFactory.getSkill(stateid)
                                .getEffect(chr.getSkillLevel(stateid));
                        stateeffect.applyTo(chr);
                    }
                }, 180);
            }
            if (attack.skill == 21121057) {
                SkillFactory.getSkill(21121068).getEffect(1).applyTo(chr, chr.getPosition());
            }
        }

        attackCount *= mirror ? 2 : 1;

        int numFinisherOrbs = 0;
        Integer comboBuff = chr.getBuffedValue(BuffStats.ComboCounter);

        if (isFinisher(attack.skill) > 0) {
            if (comboBuff != null) {
                numFinisherOrbs = comboBuff.intValue() - 1;
            }
            chr.handleOrbconsume(isFinisher(attack.skill));
        } else if ((attack.targets > 0) && (comboBuff != null)) {
            switch (chr.getJob()) {
                case 110:
                case 111:
                case 112:
                case 2411:
                case 2412:
                    if (attack.skill == 1111008 || attack.skill == 400011073 || attack.skill == 400011074 || attack.skill == 400011075 || attack.skill == 400011076) {
                        break;
                    }
                    chr.handleOrbgain();
            }
        }
        if ((isFinisher(attack.skill) > 0) && (numFinisherOrbs == 0)) {
            return;
        }

        if (attack.skill == 27101101) {
            for (AttackPair atk : attack.allDamage) {
                chr.getMap().getMonsterByOid(atk.objectid).applyStatus(chr, new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.STUN, 1), SkillFactory.getSkill(27101101), attack.skillLevel, null, false), (long) 3 * 1000);
            }
        }
        chr.checkFollow();
        if (attack.skill == 21101003) {
            for (AttackPair ap : attack.allDamage) {
                chr.getMap().broadcastMessage(chr, MobPacket.damageMonster(ap.objectid, ap.attack.get(0).getLeft()),
                        false);
            }
        } else {
            if (attack.skill != 400051015) {
                chr.getMap().broadcastMessage(chr, MainPacketCreator.attack(RecvPacketOpcode.CLOSE_RANGE_ATTACK, chr, attack), chr.getPosition());
            }
        }
        DamageParse.applyAttack(attack, skill, c.getPlayer(), attackCount, effect, mirror ? AttackType.NON_RANGED_WITH_MIRROR : AttackType.NON_RANGED);

        if (GameConstants.isBlaster(chr.getJob())) {
            if (attack.skill == 37001000 || attack.skill == 37101000 || attack.skill == 37121004) {
                //Revolving Cannon Skills Available: Magnum Punch, Double Fang, Revolving Bunker
                chr.giveBulletGauge(attack.skill, true);
            }
            if (attack.skill == 37001000 || attack.skill == 37101000) {
                int id = chr.getSkillLevel(37100007) > 0 ? 37100007 : 37000007;
                List<Integer> subskillid = new ArrayList<>();
                subskillid.add(id);
                c.getSession().writeAndFlush(MainPacketCreator.ConnectionSkill(attack.position, attack.skill, subskillid, chr.isFacingLeft() ? -1 : 1));
            }
        }

    }

    public static void rangedAttack(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        if (c.getPlayer() == null || chr == null || !chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }

        AttackInfo attack = DamageParse.parseDmg(c.getPlayer(), rh, RecvPacketOpcode.RANGED_ATTACK.getValue(), RecvPacketOpcode.RANGED_ATTACK.getValue());
        int bulletCount = 1;
        int skillLevel = attack.skillLevel;

        ISkill skill = SkillFactory.getSkill(attack.skill);
        SkillStatEffect effect = null;
        if (attack.skill > 0) {
            if (chr.getMapId() == 123456788) {
                chr.changeMap(931000313, 1);
                chr.dropMessage(1, "Use of skills in the village is prohibited.");
                return;
            }
            if (chr.getSkillLevel(5100015) > 0) {
                int 최대에너지 = chr.getSkillLevel(5110014) > 0 ? 10000 : 5000;
                int 에너지충전량 = chr.getSkillLevel(5120018) > 0 ? 350 : chr.getSkillLevel(5110014) > 0 ? 300 : 150;
                int 에너지감소량 = 0;
                switch (attack.skill) {
                    case 5101014: // Energy tornado
                        에너지감소량 = 85;
                        break;
                    case 5111013:
                        에너지감소량 = 140;
                        break;
                    case 5111012:
                    case 5111015: // Shock wave
                    case 5121001:
                    case 5121019: // Dragon Strike
                        에너지감소량 = 180;
                        break;
                    case 5121007:
                    case 5121020:
                        에너지감소량 = 150;
                        break;
                    case 5121017: // Double blast
                        에너지감소량 = 260;
                        break;
                    case 5121052:
                    case 5121055: // Unity of power
                        에너지감소량 = 1500;
                        break;
                    case 400051015: //Serpent Screw
                        에너지감소량 = 130;
                        break;
                }

                if (chr.에너지차지구분 == 0 && attack.skill != 400051015) {
                    chr.acaneAim += (에너지충전량 * 2);
                } else if (chr.에너지차지구분 == 1 || attack.skill == 400051015) {
                    chr.acaneAim -= 에너지감소량;
                }

                int 현재에너지 = chr.acaneAim;

                if (chr.에너지차지구분 == 0 && attack.skill != 400051015) {
                    if (현재에너지 >= 10000) {
                        chr.send(MainPacketCreator.giveEnergyCharge(현재에너지, 10000, 5120018, 10000, true, false));
                        chr.acaneAim = 10000;
                        chr.에너지차지구분 = 1;
                    } else {
                        chr.send(MainPacketCreator.giveEnergyCharge(현재에너지, 10000, 5120018, 10000, false, false));
                    }
                } else if (chr.에너지차지구분 == 1 || attack.skill == 400051015) {
                    if (현재에너지 <= 0) {
                        chr.send(MainPacketCreator.giveEnergyCharge(0, 0, 5120018, 현재에너지, false, false));
                        chr.acaneAim = 0;
                        chr.에너지차지구분 = 0;
                        if (chr.isActiveBuffedValue(400051015)) {
                            chr.cancelEffectFromBuffStat(BuffStats.DevilishPower);
                        }
                    } else {
                        if (attack.skill != 400051015) {
                            chr.send(MainPacketCreator.giveEnergyCharge(0, 0, 5120018, 현재에너지, false, true));
                        } else if (attack.skill == 400051015 && chr.에너지차지구분 == 0) {
                            chr.send(MainPacketCreator.giveEnergyCharge(현재에너지, 10000, 5120018, 10000, false, false));
                        } else if (attack.skill == 400051015 && chr.에너지차지구분 == 1) {
                            chr.send(MainPacketCreator.giveEnergyCharge(0, 0, 5120018, 현재에너지, false, false));
                        }
                    }
                }
            }
            effect = attack.getAttackEffect(chr, skillLevel, skill);
        }
        if (GameConstants.SurfaceDamageSkillLink(attack.skill)) {
            for (int i = 0; i < attack.allDamage.size(); ++i) { // Marisu
                for (int x = 0; x < attack.allDamage.get(i).attack.size(); ++x) { // Demb Index
                    MapleMonster Target = chr.getMap().getMonsterByOid(attack.allDamage.get(i).objectid);
                    Target.damage(chr, attack.allDamage.get(i).attack.get(x).left, false);
                    if (Target.getHp() <= 0) {
                        break;
                    }
                }
            }
        }

        if (GameConstants.isAngelicBuster(chr.getJob())) {
            int Recharge = effect.getOnActive();
            if (chr.getJob() == 6512) {
                Recharge = 100;
            }
            if (Randomizer.isSuccess(Recharge)) {
                c.send(AngelicBusterSkill.unlockSkill());
            }
        }

        try {
            chr.getCalcDamage().PDamage(chr, attack);
        } catch (Exception ex) {

        }
        Integer ShadowPartner = chr.getBuffedValue(BuffStats.ShadowPartner);
        if (ShadowPartner != null) {
            bulletCount *= 2;
        }
        int projectile = 0, visProjectile = 0;
        if (attack.slot == 0) {
            for (IItem item : chr.getInventory(MapleInventoryType.USE).list()) {
                if (GameConstants.isUsingBulletJob(chr.getJob()) && GameConstants.isBullet(item.getItemId())) {
                    projectile = item.getItemId();
                    break;
                } else if (GameConstants.isUsingStarJob(chr.getJob()) && GameConstants.isThrowingStar(item.getItemId())) {
                    projectile = item.getItemId();
                    break;
                } else if (GameConstants.isUsingArrowForBowJob(chr.getJob())
                        && GameConstants.isArrowForBow(item.getItemId())) {
                    projectile = item.getItemId();
                    break;
                } else if (GameConstants.isUsingArrowForCrossBowJob(chr.getJob())
                        && GameConstants.isArrowForCrossBow(item.getItemId())) {
                    projectile = item.getItemId();
                    break;
                }
            }
        } else {
            projectile = chr.getInventory(MapleInventoryType.USE).getItem(attack.slot).getItemId();
            if (attack.item == 0) {
                attack.item = projectile;
            }
            if (chr.getBuffedValue(BuffStats.NoBulletConsume) == null) {
                InventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, attack.slot, (short) 1, false);
            }
        }
        if (projectile == 0) {
            if (chr.getJob() >= 3500 && chr.getJob() <= 3512) {
                projectile = 2330000;
            }
            if (chr.getJob() == 501 || (chr.getJob() >= 530 && chr.getJob() <= 533)) {
                projectile = 2330000;
            }
            if (projectile == 0) {
                projectile = 0;
            }
        }

        if (attack.csstar > 0) {
            visProjectile = chr.getInventory(MapleInventoryType.CASH).getItem((short) attack.csstar).getItemId();
        } else {
            visProjectile = projectile;
        }
        if (effect != null) {
            boolean nocool = false;
            if (attack.skill == 5221022) {
                if (chr.getCooldownLimit(5221022) > 0) {
                    nocool = true;
                }
            }
            int cooldown = effect.getCooldown();
            if (cooldown > 0) {
                if (attack.skill == 3221007) {
                    if (chr.getSkillLevel(3220051) > 0) {
                        cooldown = 0;
                    }
                }
                if (!nocool) {
                    chr.addCooldown(attack.skill, System.currentTimeMillis(), cooldown * 1000);
                }
            }
        }

        //chr.getMap().broadcastMessage(chr, MainPacketCreator.attack(RecvPacketOpcode.RANGED_ATTACK, chr, chr.getId(), attack.tbyte, attack.skill, skillLevel, attack.display, attack.animation, attack.speed, attack.allDamage, attack.position, (byte) 0, 0, chr.getLevel(), visProjectile, attack.assist == 1), chr.getPosition());
        chr.getMap().broadcastMessage(MainPacketCreator.attack(RecvPacketOpcode.RANGED_ATTACK, chr, attack));

        DamageParse.applyAttack(attack, skill, chr, bulletCount, effect, ShadowPartner != null ? AttackType.RANGED_WITH_SHADOWPARTNER : AttackType.RANGED);
    }

    public static void MagicDamage(ReadingMaple rh, MapleClient c, MapleCharacter chr, RecvPacketOpcode recv) {
        if (c.getPlayer() == null || chr == null || !chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        AttackInfo attack = DamageParse.parseDmg(c.getPlayer(), rh, recv.getValue(), recv.getValue());
        int bulletCount = 1;
        int skillLevel = attack.skillLevel;
        ISkill skill = SkillFactory.getSkill(attack.skill);
        SkillStatEffect effect = attack.getAttackEffect(chr, skillLevel, skill);
        if (attack.skill > 0) {
            if (chr.getMapId() == 123456788) {
                chr.changeMap(931000313, 1);
                chr.dropMessage(1, "Use of skills in the village is prohibited.");
                return;
            }
        }
        MapleMap map = chr.getMap();
        if (GameConstants.SurfaceDamageSkillLink(attack.skill)) {
            for (int i = 0; i < attack.allDamage.size(); ++i) { // Marisu
                for (int x = 0; x < attack.allDamage.get(i).attack.size(); ++x) { // Demb Index
                    MapleMonster Target = chr.getMap().getMonsterByOid(attack.allDamage.get(i).objectid);
                    if (Target != null) {
                        Target.damage(chr, attack.allDamage.get(i).attack.get(x).left, false);
                        if (Target.getHp() <= 0) {
                            break;
                        }
                    }
                }
            }
        }
        chr.checkFollow();
        try {
            chr.getCalcDamage().PDamage(chr, attack);
        } catch (Exception ex) {
        }

        if (effect.getCooldown() > 0) {
            switch (attack.skill) {
                case 142121005: {
                    if (chr.getCooldownLimit(attack.skill) == 0) {
                        chr.addCooldown(attack.skill, System.currentTimeMillis(),
                                SkillFactory.getSkill(attack.skill).getEffect(skillLevel).getCooldown() * 1000);
                    }
                    break;
                }
                case 142110000: {
                    chr.addCooldown(attack.skill, System.currentTimeMillis(),
                            SkillFactory.getSkill(142110001).getEffect(skillLevel).getCooldown() * 1000);
                    break;
                }
                default: {
                    if (!chr.isEquilibrium() && !GameConstants.isDarkSkills(attack.skill)) {
                        chr.addCooldown(attack.skill, System.currentTimeMillis(), effect.getCooldown() * 1000);
                    }
                }
            }
        }
        if (attack.skill == 27101101) {
            for (AttackPair atk : attack.allDamage) {
                chr.getMap().getMonsterByOid(atk.objectid).applyStatus(chr, new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.STUN, 1), SkillFactory.getSkill(27101101), attack.skillLevel, null, false), (long) 3 * 1000);
            }
        }
        if (attack.bShowFixedDamage == 0) {
            chr.getMap().broadcastMessage(chr, MainPacketCreator.attack(RecvPacketOpcode.MAGIC_ATTACK, chr, attack), chr.getPosition());
        }
        switch (attack.skill) {
            case 27101100: // Silpid Lancer
            case 27101202: // Body Pressure
            case 27111100: // Spectral Light
            case 27111202: // Knox Spear
            case 27121100: // Light reflection
            case 27121202: // Apocalypse
            case 2121006:
            case 2221003:
            case 2221006:
            case 2221007:
            case 2221012:
            case 2321007: // Angel ray
            case 2121003: // Mist Eruption
            case 22181002: // Dark fog
                bulletCount = effect.getAttackCount();
                DamageParse.applyAttack(attack, skill, chr, bulletCount, effect, AttackType.RANGED);
                break;
            default:
                DamageParse.applyAttackMagic(attack, skill, c.getPlayer(), effect);
                break;
        }
    }

    public static void WheelOfFortuneEffect(int itemId, MapleCharacter chr) {
        switch (itemId) {
            case 5510000: {
                if (!chr.isAlive()) {
                    chr.getMap().broadcastMessage(chr, MainPacketCreator.showSpecialEffect(chr.getId(), itemId), false);
                }
                break;
            }
            default:
                break;
        }
    }

    public static void DropMeso(int meso, MapleCharacter chr) {
        if (!chr.isAlive() || (meso < 10 || meso > 50000) || (meso > chr.getMeso())) {
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(chr));
            return;
        }
        chr.gainMeso(-meso, false, true);
        chr.getMap().spawnMesoDrop(meso, chr.getPosition(), chr, chr, true, (byte) 0);
    }

    public static void ChangeEmotion(int emote, MapleCharacter chr) {
        if (emote > 7) {
            int emoteid = 5159992 + emote;
            MapleInventoryType type = GameConstants.getInventoryType(emoteid);
        }
        if (emote > 0) {
            chr.getMap().broadcastMessage(chr, MainPacketCreator.facialExpression(chr, emote), false);
        }
    }

    public static void ChangeEmotionAndroid(int emote, MapleCharacter chr) {
        if (emote > 0) {
            chr.getMap().broadcastMessage(chr, AndroidPacket.showAndroidEmotion(chr.getId(), emote), false);
        }
    }

    public static void Heal(ReadingMaple rh, MapleCharacter chr) {
        rh.skip(8);
        int healHP = rh.readShort();
        int healMP = rh.readShort();
        PlayerStats stats = chr.getStat();
        if (stats.getHp() <= 0) {
            return;
        }
        if (healHP != 0) {
            chr.addHP(healHP);
        }
        if (healMP != 0) {
            chr.addMP(healMP);
        }
    }

    public static final void MoveAndroid(final ReadingMaple rh, final MapleClient ha, final MapleCharacter hp) {
        rh.skip(12);
        final List<LifeMovementFragment> res = MovementParse.parseMovement(rh);
        if (res != null && hp != null && res.size() != 0 && hp.getMap() != null && hp.getAndroid() != null) {
            final Point pos = new Point(hp.getAndroid().getPosition());
            hp.getAndroid().updatePosition(res);
            hp.getMap().broadcastMessage(hp, AndroidPacket.moveAndroid(hp.getId(), pos, res), false);
        }
    }

    public static void MovePlayer(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        Point Original_Pos = chr.getPosition();
        rh.skip(22);
        List<LifeMovementFragment> res = null;
        if (chr.isEA()) {
            chr.updateEA();
        }
        try {
            res = MovementParse.parseMovement(rh);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            if (!ServerConstants.realese) {
                aioobe.printStackTrace();
            }
            System.err.println("Movement Parse Error : " + rh.toString());
        }
        if (res != null) {
            MapleMap map = c.getPlayer().getMap();
            if (chr.isHidden()) {
                chr.setLastRes(res);
            } else {
                map.broadcastMessage(chr, MainPacketCreator.movePlayer(chr.getId(), res, Original_Pos), false);
            }
            MovementParse.updatePosition(res, chr, 0);
            Point pos = chr.getTruePosition();
            map.movePlayer(chr, chr.getPosition());

            if ((chr.getFollowId() > 0) && (chr.isFollowOn()) && (chr.isFollowInitiator())) {
                MapleCharacter fol = map.getCharacterById_InMap(chr.getFollowId());
                if (fol != null) {
                    Point original_pos = fol.getPosition();
                    fol.getClient().getSession()
                            .writeAndFlush(MainPacketCreator.moveFollow(Original_Pos, original_pos, pos, res));
                    MovementParse.updatePosition(res, fol, 0);
                    map.movePlayer(fol, pos);
                    map.broadcastMessage(fol, MainPacketCreator.movePlayer(fol.getId(), res, Original_Pos), false);
                } else {
                    chr.checkFollow();
                }
            }
        }
    }

    public static void ChangeMapSpecial(String portal_name, MapleClient c, MapleCharacter chr) {
        MaplePortal portal = chr.getMap().getPortal(portal_name);
        if (portal != null) {
            portal.enterPortal(c);
        }
    }

    public static void ChangeMap(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        if (rh.available() != 0) {
            rh.skip(11);
            int targetid = rh.readInt();
            MaplePortal portal = chr.getMap().getPortal(rh.readMapleAsciiString());
            if (chr.getMapId() == 109090300) {
                chr.dropMessage(1, "Wait for the tag to finish! There is a reward.");
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                return;
            }
            boolean wheel = rh.readShort() > 0;
            if (targetid != -1 && !chr.isAlive()) {
                if (chr.getEventInstance() != null) {
                    chr.getEventInstance().revivePlayer(chr);
                }
                chr.setStance(0);
                if (wheel && chr.getEventInstance() == null) {
                    if (chr.haveItem(5510000, 1, false, true)) {
                        chr.getStat().setHp((chr.getStat().getMaxHp() / 100) * 40, chr);
                        InventoryManipulator.removeById(c, MapleInventoryType.CASH, 5510000, 1, true, false);
                        MapleMap to = chr.getMap();
                        chr.changeMap(to, to.getPortal(0));
                    } else {
                        chr.getStat().setHp(50, chr);
                        MapleMap to = chr.getMap().getReturnMap();
                        chr.changeMap(to, to.getPortal(0));
                        if (chr.getParty() != null) {
                            if (chr.getParty().getExpedition() != null
                                    && chr.getParty().getExpedition().getLastBossMap() != -1) {
                                chr.getParty().getExpedition().addDeadChar(chr.getId());
                            }
                        }
                    }
                } else {
                    chr.getStat().setHp(50, chr);
                    MapleMap to = chr.getMap().getReturnMap();
                    chr.changeMap(to, to.getPortal(0));
                    if (chr.getParty() != null) {
                        if (chr.getParty().getExpedition() != null
                                && chr.getParty().getExpedition().getLastBossMap() != -1) {
                            chr.getParty().getExpedition().addDeadChar(chr.getId());
                        }
                    }
                }
            } else if (targetid != -1 && chr.isGM()) {
                if (chr.getEventInstance() == null) {
                    MapleMap to = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(targetid);
                    chr.changeMap(to, to.getPortal(0));
                } else {
                    MapleMap to = chr.getEventInstance().getMapFactory().getMap(targetid);
                    chr.changeMap(to, to.getPortal(0));
                }
            } else if (portal != null) {
                portal.enterPortal(c);
            } else {
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            }
        }
    }

    public static void InnerPortal(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        MaplePortal portal = c.getPlayer().getMap().getPortal(rh.readMapleAsciiString());
        int toX = rh.readShort();
        int toY = rh.readShort();

        if (portal == null) {
            c.disconnect(true, false);
            return;
        }
        chr.getMap().movePlayer(chr, new Point(toX, toY));
        chr.checkFollow();
    }

    public static void Agi_Buff(ReadingMaple rh, MapleClient c) {
        int skill = rh.readInt();
        if (c.getPlayer().getSkillLevel(skill) > 0) {
            SkillStatEffect eff = SkillFactory.getSkill(skill).getEffect(c.getPlayer().getSkillLevel(skill));
            if (eff.makeChanceResult()) {
                eff.applyTo(c.getPlayer());
            }
        }
    }

    public static void RoomChange(ReadingMaple rh, MapleClient c, MapleCharacter player) {
        if (player.getMapId() < 910000000 || player.getMapId() > 910000022) {
            c.getPlayer().ea();
            return;
        }
        byte channel = rh.readByte();
        int targetMap = rh.readInt();
        MapleMap to = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(targetMap);

        if (c.getChannel() != channel) {
            if (c.getPlayer().getLastCC() + 10000 > System.currentTimeMillis()) {
                c.getPlayer().message(5, "Channel movement is possible every 10 seconds.");
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                return;
            }
            c.getPlayer().crossChannelWarp(c, targetMap, channel);
        } else {
            player.changeMap(to, to.getPortal("sp"));
        }
    }

    public static void makerSkill(ReadingMaple rh, MapleClient c) {
        ItemInformation ii = ItemInformation.getInstance();
        int type = rh.readInt();
        int toCreate = rh.readInt();
        switch (type) {
            case 1:
                MakerItemFactory.MakerItemCreateEntry recipe = MakerItemFactory.getItemCreateEntry(toCreate);
                if (!canCreate(c, recipe) || c.getPlayer().getInventory(ii.getInventoryType(toCreate)).isFull()) {
                    c.getPlayer().dropMessage(1, "No such item or inventory is full.");
                    return;
                }
                c.getPlayer().gainMeso(-recipe.getCost(), false);
                for (Pair<Integer, Integer> p : recipe.getReqItems()) {
                    int toRemove = p.getLeft();
                    InventoryManipulator.removeById(c, ii.getInventoryType(toRemove), toRemove, p.getRight(), false, false);
                }
                if (ii.getInventoryType(toCreate) == MapleInventoryType.EQUIP) {
                    boolean prodStim = rh.readByte() == 1;
                    int gemz = rh.readShort();
                    rh.readShort();
                    Equip item = (Equip) ii.getEquipById(toCreate);
                    if (prodStim) {
                        int prodId = recipe.getcatalyst();
                        if (prodId == -1) {
                            c.getPlayer().dropMessage(1, "Something went wrong, please notify a GM about this issue.");
                            return;
                        }
                        if (!c.getPlayer().haveItem(prodId)) {
                            return;
                        }
                        if (new Random().nextInt(9) < 1) {
                            item = null;
                        } else {
                            item = ii.randomizeStats(item);
                        }
                        InventoryManipulator.removeById(c, MapleInventoryType.ETC, prodId, 1, false, false);
                    }
                    for (int i = 0; i < gemz; i++) {
                        int gem = rh.readInt();
                        if (c.getPlayer().haveItem(gem)) {
                            InventoryManipulator.removeById(c, MapleInventoryType.ETC, gem, 1, false, false);
                            ii.addCrystalEffect(item, gem);
                        } else {
                            return;
                        }
                    }
                    if (item != null) {
                        InventoryManipulator.addFromDrop(c, item, true);
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.getScrollEffect(c.getPlayer().getId(), IEquip.ScrollResult.SUCCESS));
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showSpecialEffect(0x12));
                        c.getPlayer().dropMessage(1, "Successful production.");
                    } else {
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.getScrollEffect(c.getPlayer().getId(), IEquip.ScrollResult.FAIL));
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showSpecialEffect(0x12));
                        c.getPlayer().dropMessage(1, "Failed attempt to create your item.");
                    }
                } else {
                    Pair<Integer, Short> reward = recipe.getRandomReward();
                    InventoryManipulator.addById(c, reward.getLeft(), reward.getRight());
                    c.getSession().writeAndFlush(MainPacketCreator.getShowItemGain(reward.getLeft(), reward.getRight(), true));
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.getScrollEffect(c.getPlayer().getId(), reward.getLeft() >= toCreate ? IEquip.ScrollResult.SUCCESS : IEquip.ScrollResult.FAIL));
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showSpecialEffect(0x12));
                    c.getPlayer().dropMessage(1, reward.getLeft() >= toCreate ? "Congratulations! You've succeeded in the making of the item! You've made " : "Failed attempt to create your item.");
                }
                break;
            case 3:
                if (c.getPlayer().getItemQuantity(toCreate, false) >= 100) {
                    int lvl = ii.getETCMonsLvl(toCreate);
                    if (lvl != -1) {
                        InventoryManipulator.removeById(c, MapleInventoryType.ETC, toCreate, 100, false, false);
                        InventoryManipulator.addById(c, Math.min(Math.max(5, (int) Math.ceil(lvl / 10.0)) - 5 + 4260000, 4260008), (short) 1);
                        c.getPlayer().dropMessage(1, "Congratulations. You've made 1 Monster Crystals!");
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showSpecialEffect(0x12));
                        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.getScrollEffect(c.getPlayer().getId(), IEquip.ScrollResult.SUCCESS));
                    } else {
                        c.getPlayer().dropMessage(1, "You cannot use these items to make a monster crystal!");
                    }
                }
                break;
            case 4:
                rh.readInt();
                short slot = rh.readShort();
                if (c.getPlayer().haveItem(toCreate)) {
                    InventoryManipulator.removeFromSlot(c, slot >= 0 ? MapleInventoryType.EQUIP : MapleInventoryType.EQUIPPED, (byte) slot, (short) 1, false);
                    int itemToGain = ((ii.getReqLevel(toCreate) - 50) / 10) + 4260000;
                    int amount = ii.getWeaponType(toCreate) == MapleWeaponType.NOT_A_WEAPON ? new Random().nextInt(15) + 6 : new Random().nextInt(15) + 20;
                    InventoryManipulator.addById(c, itemToGain, (short) amount);
                    c.getPlayer().dropMessage(1, "Congratulations. You've made " + amount + " Monster Crystals!");
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.getScrollEffect(c.getPlayer().getId(), IEquip.ScrollResult.SUCCESS));
                    c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showSpecialEffect(0x12));
                    c.getSession().writeAndFlush(MainPacketCreator.getShowItemGain(itemToGain, (short) amount, true));
                }
                break;
            default:
                break;
        }
    }

    public static boolean canCreate(MapleClient c, MakerItemFactory.MakerItemCreateEntry recipe) {
        return hasItems(c, recipe) && c.getPlayer().getMeso() >= recipe.getCost()
                && c.getPlayer().getLevel() >= recipe.getReqLevel() && c.getPlayer()
                .getSkillLevel(c.getPlayer().getJob() / 1000 * 1000 + 1007) >= recipe.getReqSkillLevel();
    }

    public static boolean hasItems(MapleClient c, MakerItemFactory.MakerItemCreateEntry recipe) {
        for (Pair<Integer, Integer> p : recipe.getReqItems()) {
            int itemId = p.getLeft();
            if (c.getPlayer().getInventory(ii.getInventoryType(itemId)).countById(itemId) < p.getRight()) {
                return false;
            }
        }
        return true;
    }

    public static void VoydPressure(ReadingMaple rh, MapleCharacter chr) {
        chr.getMap().broadcastMessage(chr, MainPacketCreator.showVoydPressure(chr.getId()), true);
    }

    public static void znfxkdladmfwnwkdyd(ReadingMaple rh, MapleClient c) {
        MapleCharacter chr = c.getPlayer();
        rh.skip(16);
        int id = rh.readInt();
        chr.addCooldown(id, System.currentTimeMillis(),
                SkillFactory.getSkill(id).getEffect(chr.getSkillLevel(id)).getCooldown() * 1000);
    }

    public static void subSummonAction(ReadingMaple slea, MapleClient c) {
        MapleCharacter chr = c.getPlayer();
        int type = slea.readShort();
        int cid = slea.readInt();
        int key = slea.readInt();
        if (type == 0) {

            c.getSession().writeAndFlush(MainPacketCreator.ObjectAction_single(type, key));
        } else if (type == 3) {
            int skillid = slea.readInt();
            int skilllevel = slea.readInt();
            Point pos = slea.readIntPos();
            c.getSession().writeAndFlush(MainPacketCreator.ObjectAction_single(type, key));
            chr.getMap().broadcastMessage(chr, MainPacketCreator.ObjectAction(type, cid, chr.getMapId(), pos, skillid, false, 0, 0, 0, (byte) 0, null, 0, null), false);
        } else if (type == 4) {
            slea.readPos();
            Point pos = slea.readPos();
            int skillid = slea.readInt();
            boolean bLeft = slea.readByte() == 1;
            slea.skip(10);
            int unk1 = slea.readShort();
            int unk2 = slea.readShort();
            int unk3 = slea.readShort();
            byte unk4 = slea.readByte();
            String unk5 = null;
            if (unk4 > 0) {
                unk5 = slea.readMapleAsciiString();
            }
            int unk6 = slea.readInt();
            Point pPos = slea.readIntPos();
            c.getSession().writeAndFlush(MainPacketCreator.ObjectAction_single(type, key));
            if (skillid != 5221022 && skillid != 5221023 && skillid != 5221024 && skillid != 5221025) {
                chr.getMap().broadcastMessage(chr, MainPacketCreator.ObjectAction(type, cid, chr.getMapId(), pos, skillid, bLeft, unk1, unk2, unk3, unk4, unk5, unk6, pPos), false);
            }
        }
    }

    public static void getHyperSkill(ReadingMaple rh, MapleClient c) {
        final String name = rh.readMapleAsciiString();
        final int lvl = rh.readInt();
        final int sp = rh.readInt();
        if (name.equals("hyper")) {
            int j = 0;
            switch (lvl) {
                case 28:
                case 32:
                case 36:
                case 38:
                case 40:
                    if (sp == 0) {
                        j = 1;
                    } else if (lvl == 40) {
                        j = 1;
                    }
                    break;
                case 30:
                case 34:
                    if (sp == 1) {
                        j = 1;
                    }
                    break;
                case 29:
                case 31:
                case 33:
                case 35:
                case 37:
                case 39:
                default:
                    if ((lvl >= 41) && (lvl <= 50)) {
                    }
                    break;
            }
            c.send(MainPacketCreator.ResultInstanceTable(name, lvl, sp, (byte) 1, j));
            return;
        }
        if (name.equals("incHyperStat")) {
            int n2 = 0;
            if (140 <= lvl && lvl <= 149) {
                n2 = 3;
            } else if (150 <= lvl && lvl <= 159) {
                n2 = 4;
            } else if (160 <= lvl && lvl <= 169) {
                n2 = 5;
            } else if (170 <= lvl && lvl <= 179) {
                n2 = 6;
            } else if (180 <= lvl && lvl <= 189) {
                n2 = 7;
            } else if (190 <= lvl && lvl <= 199) {
                n2 = 8;
            } else if (200 <= lvl && lvl <= 209) {
                n2 = 9;
            } else if (210 <= lvl && lvl <= 219) {
                n2 = 10;
            } else if (220 <= lvl && lvl <= 229) {
                n2 = 11;
            } else if (230 <= lvl && lvl <= 239) {
                n2 = 12;
            } else if (240 <= lvl && lvl <= 249) {
                n2 = 13;
            } else if (250 >= lvl) {
                n2 = 14;
            }
            c.send(MainPacketCreator.ResultInstanceTable(name, lvl, sp, (byte) 1, n2));
            return;
        }
        if (name.equals("needHyperStatLv")) {
            int loseHsp[] = {0, 1, 2, 4, 8, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80};
            c.send(MainPacketCreator.ResultInstanceTable(name, lvl, sp, (byte) 1, loseHsp[lvl]));
        }
    }

    public static void SetFreeJob(final ReadingMaple rh, final MapleCharacter chr) {
        int jobid = rh.readInt();
        int jobcoin = 0;
        long meso = 0;
        rh.skip(1);
        if (chr.getLevel() >= 206) {
            jobcoin = 50;
            meso = 1096700000;
        } else if (chr.getLevel() >= 205) {
            jobcoin = 45;
            meso = 967600000;
        } else if (chr.getLevel() >= 203) {
            jobcoin = 40;
            meso = 886500000;
        } else if (chr.getLevel() >= 200) {
            jobcoin = 35;
            meso = 795700000;
        } else if (chr.getLevel() >= 188) {
            jobcoin = 30;
            meso = 687500000;
        } else if (chr.getLevel() >= 167) {
            jobcoin = 25;
            meso = 567300000;
        } else if (chr.getLevel() >= 160) {
            jobcoin = 20;
            meso = 437500000;
        } else if (chr.getLevel() >= 142) {
            jobcoin = 15;
            meso = 326800000;
        } else if (chr.getLevel() >= 138) {
            jobcoin = 10;
            meso = 215400000;
        } else if (chr.getLevel() >= 131) {
            jobcoin = 8;
            meso = 10108000;
        } else if (chr.getLevel() >= 130) {
            jobcoin = 6;
            meso = 8750000;
        } else if (chr.getLevel() >= 126) {
            jobcoin = 4;
            meso = 5270000;
        } else if (chr.getLevel() >= 112) {
            jobcoin = 2;
            meso = 3580000;
        } else {
            jobcoin = 1;
            meso = 1830000;
        }
        if (chr.haveItem(4310086, jobcoin, false, false)) {
            chr.gainItem(4310086, (short) -jobcoin, false, 0, null);
            chr.zerooskill(chr.getJob());
            chr.zerooskill(chr.getJob() - 1);
            chr.zerooskill(chr.getJob() - 2);
            chr.changeJob(jobid);
            chr.maxskill(chr.getJob());
            chr.maxskill(chr.getJob() - 1);
            chr.maxskill(chr.getJob() - 2);
            //    chr.getClient().getSession().writeAndFlush(MainPacketCreator.getNPCTalk(9010000, (byte) 0, "You now have a new job.", "00 00", (byte) 0));
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(chr));
        } else if (chr.getMeso() >= meso) {
            chr.gainMeso(-meso, false);
            chr.zerooskill(chr.getJob());
            chr.zerooskill(chr.getJob() - 1);
            chr.zerooskill(chr.getJob() - 2);
            chr.changeJob(jobid);
            chr.maxskill(chr.getJob());
            chr.maxskill(chr.getJob() - 1);
            chr.maxskill(chr.getJob() - 2);
            //    chr.getClient().getSession().writeAndFlush(MainPacketCreator.getNPCTalk(9010000, (byte) 0, "You now have a new job.", "00 00", (byte) 0));
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(chr));
        } else {
            //    chr.getClient().getSession().writeAndFlush(MainPacketCreator.getNPCTalk(9010000, (byte) 0, "Not enough mesos to choose a new job.", "00 00", (byte) 0));
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(chr));
        }
    }

    public static void getStarPlanetRank(final ReadingMaple rh, final MapleCharacter chr) {
        List name = new LinkedList();
        List level = new LinkedList();
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("SELECT * FROM characters WHERE gm = 0 ORDER BY level DESC LIMIT 100");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name.add(rs.getString("name"));
                level.add(Integer.valueOf(rs.getInt("level")));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        chr.send(MainPacketCreator.getStarPlanetRank(name, level));
    }

    public static void warpToStarplanet(final byte action, final ReadingMaple rh, final MapleCharacter chr) {
        if (action == 2) {
            rh.skip(1);
            int mapcode = rh.readInt(); // The code of the previous map.
            int direction = rh.readByte(); // 1 if the current Star Planet map, 0 otherwise.

            if (direction == 1) {
                chr.dropMessage(5, "[Notice] " + ServerConstants.serverName + " Go to Star Planet Map, the square of notifications.");
                MapleMap map = chr.getClient().getChannelServer().getMapFactory().getMap(340000100);
                chr.changeMap(map, map.getPortal(0));
            } else {
                chr.dropMessage(5, "[Notice] Go back to the map before moving to the square.");
                MapleMap map = chr.getClient().getChannelServer().getMapFactory()
                        .getMap(chr.getKeyValue2("Return_to_Starplanet"));
                chr.changeMap(map, map.getPortal(0));
            }
            chr.setKeyValue2("Return_to_Starplanet", mapcode);
        }
    }

    public static void MapleGuide(final ReadingMaple rh, final short action, final MapleCharacter chr) {
        if (action == 0) {
            final int mapid = rh.readInt();
            MapleMap map = chr.getClient().getChannelServer().getMapFactory().getMap(mapid);
            chr.changeMap(map, map.getPortal(0));
        }
    }

    public static void MapleChat(ReadingMaple rh, MapleCharacter chr) {
        if (!ServerConstants.mChat_char.isEmpty()) {
            chr.send(UIPacket.MapleChat());
        }
    }

    public static void OrbitalFlame(final ReadingMaple rh, final MapleClient c) {
        MapleCharacter chr = c.getPlayer();

        int tempskill = rh.readInt();
        byte unk = rh.readByte();
        int direction = rh.readShort();
        int skillid = 0;
        int elementid = 0;
        int effect = 0;
        switch (tempskill) {
            case 12001020:
                skillid = 12000026;
                elementid = 12000022;
                effect = 1;
                break;
            case 12100020:
                skillid = 12100028;
                elementid = 12100026;
                effect = 2;
                break;
            case 12110020:
                skillid = 12110028;
                elementid = 12110024;
                effect = 3;
                break;
            case 12120006:
                skillid = 12120010;
                elementid = 12120007;
                effect = 4;
                break;
        }
        SkillStatEffect flame = SkillFactory.getSkill(tempskill).getEffect(chr.getSkillLevel(tempskill));
        if (flame != null && chr.getSkillLevel(elementid) > 0) {
            if (!chr.isActiveBuffedValue(elementid)) {
                SkillStatEffect element = SkillFactory.getSkill(elementid).getEffect(chr.getSkillLevel(elementid));
                MapleSummon summon = new MapleSummon(chr, element, chr.getPosition(), SummonMovementType.FOLLOW,
                        System.currentTimeMillis());
                chr.getSummons().put(summon.getObjectId(), new Pair<>(elementid, summon));
                chr.getMap().spawnSummon(summon, true, element.getDuration());
                element.applyTo(chr);
            }
        }
        chr.getMap().broadcastMessage(
                MainPacketCreator.OrbitalFlame(chr.getId(), skillid, effect, direction, flame.getRange()));
    }

    public static final void ChangeInner(ReadingMaple rh, MapleClient c) {
        int rank = rh.readInt(); //Fixed rating
        int count = rh.readInt(); //Fixed Ability Count
        int consume = 100 + (rank == 1 ? 400 : rank == 2 ? 5000 : rank == 3 ? 10000 : 0) + (count == 1 ? 3000 : count == 2 ? 8000 : 0);
        c.getPlayer().setInnerExp(c.getPlayer().getInnerExp() - consume);
        c.getPlayer().getClient().getSession().writeAndFlush(MainPacketCreator.updateInnerExp(c.getPlayer().getInnerExp()));
        List<InnerSkillValueHolder> newValues = new LinkedList<InnerSkillValueHolder>();
        int i = 1;
        int line = count >= 1 ? rh.readInt() : 0;
        int line2 = count >= 2 ? rh.readInt() : 0;
        boolean check_rock = false;
        InnerSkillValueHolder ivholder = null;
        InnerSkillValueHolder ivholder2 = null;
        for (InnerSkillValueHolder isvh : c.getPlayer().getInnerSkills()) {
            switch (count) {
                case 1:
                    check_rock = line == i;
                    break;
                case 2:
                    check_rock = line == i || line2 == i;
                    break;
                default:
                    check_rock = false;
                    break;
            }
            if (check_rock) {
                newValues.add(isvh);
                if (ivholder == null) {
                    ivholder = isvh;
                } else if (ivholder2 == null) {
                    ivholder2 = isvh;
                }
            } else if (ivholder == null) { //1st
                int nowrank = -1;
                int rand = Randomizer.nextInt(100);
                if (rank != 0) {
                    nowrank = rank;
                } else if (isvh.getRank() == 3) {
                    if (rand < 30) {
                        nowrank = 2; // Falling
                    } else {
                        nowrank = 3; // Maintain
                    }
                } else if (isvh.getRank() == 2) {
                    if (rand < 5) {
                        nowrank = 3; // Increase
                    } else if (rand >= 6 && rand < 30) {
                        nowrank = 1; // Falling
                    } else {
                        nowrank = 2; // Maintain
                    }
                } else if (isvh.getRank() == 1) {
                    if (rand < 10) {
                        nowrank = 2; // Increase
                    } else if (rand >= 10 && rand < 30) {
                        nowrank = 0; // Falling
                    } else {
                        nowrank = 1; // Maintain
                    }
                } else if (rand < 40) {
                    nowrank = 1; // Increase
                } else {
                    nowrank = 0; // maintain
                }
                ivholder = InnerAbillity.getInstance().renewSkill(nowrank, 0,false);
                boolean breakout = false;
                while (!breakout) {
                    if (count != 0) {
                        if (count == 1) {
                            if (ivholder.getSkillId() == c.getPlayer().getInnerSkills().get(line - 1).getSkillId()) {
                                ivholder = InnerAbillity.getInstance().renewSkill(nowrank, 0,false);
                            } else {
                                breakout = true;
                            }
                        } else if (ivholder.getSkillId() == c.getPlayer().getInnerSkills().get(line - 1).getSkillId() || ivholder.getSkillId() == c.getPlayer().getInnerSkills().get(line2 - 1).getSkillId()) {
                            ivholder = InnerAbillity.getInstance().renewSkill(nowrank, 0,false);
                        } else {
                            breakout = true;
                        }
                    } else {
                        ivholder = InnerAbillity.getInstance().renewSkill(nowrank, 0,false);
                        breakout = true;
                    }
                }
                newValues.add(ivholder);
            } else if (ivholder2 == null) {
                ivholder2 = InnerAbillity.getInstance().renewSkill(ivholder.getRank() == 0 ? 0 : (ivholder.getRank() - 1), 0,false);
                boolean breakout = false;
                while (!breakout) {
                    breakout = true;
                    if (count != 0) {
                        if (count == 1) {
                            if (ivholder2.getSkillId() == c.getPlayer().getInnerSkills().get(line - 1).getSkillId()) {
                                ivholder2 = InnerAbillity.getInstance().renewSkill(ivholder.getRank() == 0 ? 0 : (ivholder.getRank() - 1), 0, false);
                                breakout = false;
                            }
                        } else if (ivholder2.getSkillId() == c.getPlayer().getInnerSkills().get(line - 1).getSkillId() || ivholder2.getSkillId() == c.getPlayer().getInnerSkills().get(line2 - 1).getSkillId()) {
                            ivholder2 = InnerAbillity.getInstance().renewSkill(ivholder.getRank() == 0 ? 0 : (ivholder.getRank() - 1), 0, false);
                            breakout = false;
                        }
                    }
                    if (ivholder.getSkillId() == ivholder2.getSkillId()) {
                        ivholder2 = InnerAbillity.getInstance().renewSkill(ivholder.getRank() == 0 ? 0 : (ivholder.getRank() - 1), 0, false);
                        breakout = false;
                    }
                }
                newValues.add(ivholder2);
            } else {
                InnerSkillValueHolder ivholder3 = InnerAbillity.getInstance().renewSkill(ivholder.getRank() == 0 ? 0 : (ivholder.getRank() - 1), 0, false);
                while (ivholder.getSkillId() == ivholder3.getSkillId() || ivholder2.getSkillId() == ivholder3.getSkillId()) {
                    ivholder3 = InnerAbillity.getInstance().renewSkill(ivholder.getRank() == 0 ? 0 : (ivholder.getRank() - 1), 0, false);
                }
                newValues.add(ivholder3);
            }
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), (byte) 0, (byte) 0);
            i++;
        }
        c.getPlayer().getInnerSkills().clear();
        for (InnerSkillValueHolder isvh : newValues) {
            c.getPlayer().getInnerSkills().add(isvh);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getSkillLevel());
            c.getPlayer().getClient().getSession().writeAndFlush(MainPacketCreator.updateInnerAbility(isvh, c.getPlayer().getInnerSkills().size(), c.getPlayer().getInnerSkills().size() == 3));
        }
        c.getPlayer().dropMessage(5, "Ability reset succeeded.");

    }

    public static int getRank() {
        return Rank;
    }

    public static void Holly(ReadingMaple rh, MapleCharacter chr) {
        if (chr == null || !chr.isAlive()) {
            return;
        }
        List<MapleMapObject> mistsInMap = chr.getMap().getAllMistsThreadsafe();
        MapleMist fountain = null;
        for (MapleMapObject nn : mistsInMap) {
            MapleMist mist = (MapleMist) nn;
            if (mist.getSource().getSourceId() == 2311011) {
                fountain = mist;
                break;
            }
        }
        rh.readByte();
        int timesUsed = rh.readInt();
        int sourceid = rh.readInt();
        Point pos = rh.readPos();

        chr.addHP(chr.getStat().getMaxHp() / 100 * fountain.getSource().getX());
        chr.getClient().getSession().writeAndFlush(MainPacketCreator.showSkillEffect(-1, chr.getLevel(), sourceid,
                chr.getSkillLevel(sourceid), (byte) 0, 2, null, null));
    }

    public static void OnThrowGrenade(ReadingMaple rh, MapleClient c) {
        MapleCharacter chr = c.getPlayer();

        Point pt = new Point(rh.readInt(), rh.readInt());
        Point pt2 = new Point(rh.readInt(), rh.readInt());
        int tKeyDown = rh.readInt();
        int nSkillID = rh.readInt();
        int nBySummonedID = rh.readInt(); // Probably not
        boolean bLeft = rh.readByte() == 1;
        int nLayer = rh.readInt();
        int nGrenadeID = rh.readInt();
        rh.skip(1);

        if (nSkillID == 12121001) {
            c.getPlayer().addCooldown(nSkillID, System.currentTimeMillis(), 5 * 1000);
        } else if (nSkillID == 2221012) {
            SkillStatEffect effect = SkillFactory.getSkill(nSkillID).getEffect(chr.getSkillLevel(nSkillID));
            c.getPlayer().addCooldown(2221012, System.currentTimeMillis(), effect.getCooldown() * 1000);
        } else if (nSkillID == 400031003 || nSkillID == 400031004) {
            if (chr.HowlingGaleCount <= 0) {
                return;
            }

            SkillStatEffect effect = SkillFactory.getSkill(400031003).getEffect(chr.getSkillLevel(400031003));

            if (chr.HowlingGaleCount == 2) {
                chr.lastHowlingGaleTime = System.currentTimeMillis();
            }
            chr.HowlingGaleCount = 0;

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.HowlingGale, chr.HowlingGaleCount, false));
            chr.send(MainPacketCreator.giveBuff(400031003, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0, chr));
        }

        c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MainPacketCreator.OnThrowGrenade(c.getPlayer().getId(), nGrenadeID, pt, tKeyDown, nSkillID, nBySummonedID, c.getPlayer().getSkillLevel(nSkillID), bLeft, nLayer), false);
        c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MainPacketCreator.showSkillEffect(c.getPlayer().getId(), c.getPlayer().getLevel(), nSkillID, c.getPlayer().getSkillLevel(nSkillID), (byte) 0, 1, null, null), false);
    }

    public static void mistSkill(ReadingMaple rh, MapleCharacter chr) {
        int duration = 0;
        rh.readInt();
        final int skillId = rh.readInt(); // Chilling Step, Ignite
        if (skillId == 2100010) {
            duration = rh.readInt();
        }
        //rh.readInt();
        final int size = rh.readShort();
        SkillStatEffect effect = SkillFactory.getSkill(skillId).getEffect(chr.getSkillLevel(skillId));
        for (int i = 0; i < (size * 2); i++) {
            Point pos = rh.readIntPos();
            chr.getMap().spawnMist(
                    new MapleMist(effect.calculateBoundingBox(pos, chr.isFacingLeft()), chr, effect,
                            effect.getSkillStats().getLevel(), chr.getPosition()),
                    (skillId == 2100010) ? (duration * 20) : (size * 2000), false, false, false, false, false);
        }
    }

    public static void CharacterCard(ReadingMaple rh, MapleClient c) {
        Map<Integer, Integer> card = new HashMap<Integer, Integer>();
        for (int i = 0; i < 9; i++) {
            card.put(i, rh.readInt());
        }
        c.setCharacterCard(card);
    }

    public static void OnOpenGateClose(ReadingMaple rm, MapleClient c) {
        int cid = rm.readInt();
        c.getPlayer().getMap().broadcastMessage(MechanicSkill.OnOpenGateClose(cid));
        c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
    }

    public static void CreateKinesisPsychicArea(ReadingMaple rm, MapleClient c) {
        int nAction = rm.readInt();
        int ActionSpeed = rm.readInt();
        int PsychicAreaKey = rm.readInt();
        int LocalKey = rm.readInt();
        int SkillID = rm.readInt();
        short SLV = rm.readShort();
        int DurationTime = rm.readInt();
        byte second = rm.readByte();
        short SkeletonFieldPathIdx = rm.readShort();
        short SkeletonAniIdx = rm.readShort();
        short SkeletonLoop = rm.readShort();
        long mask8 = rm.readLong();
        SkillStatEffect eff = SkillFactory.getSkill(SkillID).getEffect(SLV);
        c.getPlayer().getMap()
                .broadcastMessage(MainPacketCreator.OnCreatePsychicArea(c.getPlayer().getId(), nAction, ActionSpeed,
                        LocalKey, SkillID, SLV, PsychicAreaKey, DurationTime, second, SkeletonFieldPathIdx,
                        SkeletonAniIdx, SkeletonLoop, mask8));
        c.getPlayer().givePPoint(eff, false);
        if (eff.getCooldown() > 0) {
            c.getPlayer().addCooldown(SkillID, System.currentTimeMillis(), eff.getCooldown() * 1000);
        }
    }

    public static final void ReleasePsychicLock(ReadingMaple rm, MapleClient c) {
        int skillid = rm.readInt();
        int skillLevel = rm.readInt();
        int Count = rm.readInt();
        int oid = rm.readInt();
        int v1 = rm.readInt();
        long mask = rm.readLong();
        c.getSession().writeAndFlush(KinesisSkill.OnReleasePsychicLock(c.getPlayer().getId(), skillid));
    }

    public static final void DoActivePsychicArea(ReadingMaple rm, MapleClient c) {
        int nPsychicAreaKey = rm.readInt();
        int v16 = rm.readShort();
        long ptCurrent = rm.readLong();
        c.getSession().writeAndFlush(MainPacketCreator.OnDoActivePsychicArea(nPsychicAreaKey, v16));
    }

    public static final void DebuffPsychicArea(ReadingMaple rm, MapleClient c) {
        int skillid = rm.readInt();
        short slv = rm.readShort();
        int v1 = rm.readInt();
        byte v2 = rm.readByte();
        int v3 = rm.readInt();
        int v4 = rm.readInt();
        short size = rm.readShort();
        ISkill theSkill = SkillFactory.getSkill(skillid);
        SkillStatEffect effect = theSkill.getEffect(slv);
        for (int i = 0; i < size; i++) {
            final MapleMonster monster = c.getPlayer().getMap().getMonsterByOid(rm.readInt());
            if (monster != null) {
                monster.applyStatus(c.getPlayer(),
                        new MonsterStatusEffect(effect.getMonsterStati(), theSkill, slv, null, false),
                        effect.getStatusDuration());
            }
        }
        short v5 = rm.readShort();
    }

    public static final void UserDamageSkinSaveRequest(final ReadingMaple rm, final MapleClient c) {
        byte v1 = rm.readByte();
        byte ds = rm.readByte();
        MapleQuest quest = MapleQuest.getInstance(7291);
        MapleQuestStatus queststatus = new MapleQuestStatus(quest, (byte) 1);
        String skinString = String.valueOf(ds);
        queststatus.setCustomData(skinString == null ? "0" : skinString);
        c.getPlayer().updateQuest(queststatus);
        if (c.getPlayer().getKeyValue("SaveDamageSkin") != null
                && c.getPlayer().getKeyValue("SaveDamageSkin").split(",").length >= 2) {
            c.getPlayer().setKeyValue("SaveDamageSkin",
                    c.getPlayer().getKeyValue("SaveDamageSkin").replaceAll(
                            String.valueOf(GameConstants.getDamageSkinItemByNumber((int) ds)),
                            String.valueOf(GameConstants.getDamageSkinItemByNumber(c.getPlayer().getDamageSkin()))));
        }
        c.send(MainPacketCreator.showQuestMessage("Damage skin changed."));
        c.getSession().writeAndFlush(MainPacketCreator.DamageSkinSaveResult(c.getPlayer()));
    }

    public static void link_skill(ReadingMaple rp, MapleClient c) {
        try {
            int sid = rp.readInt();
            final String sname = SkillFactory.getSkillName(sid);
            final int cid = rp.readInt();
            MapleData data = null;
            MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File("wz/String.wz"));
            data = dataProvider.getData("Skill.img");
            List<Pair<Integer, String>> skillPairList = new LinkedList<Pair<Integer, String>>();
            for (MapleData skillIdData : data.getChildren()) {
                skillPairList.add(new Pair<Integer, String>(Integer.parseInt(skillIdData.getName()),
                        MapleDataTool.getString(skillIdData.getChildByPath("name"), "NO-NAME")));
            }
            for (Pair<Integer, String> skillPair : skillPairList) {
                if (skillPair.getRight().toLowerCase().contains(sname) && sid != skillPair.left) {
                    sid = skillPair.left;
                    break;
                }
            }
            data = null;
            dataProvider = null;
            Connection con = MYSQL.getConnection();
            if (con.prepareStatement("SELECT * FROM link_skill WHERE skillid = " + sid + " AND link_cid = " + c.getPlayer().getId()).executeQuery().next()) {
                con.prepareStatement("DELETE FROM link_skill WHERE skillid = " + sid + " AND link_cid = " + c.getPlayer().getId()).execute();
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO link_skill (skillid, skillLevel, link_cid,cid) VALUES (?,?,?,?)");
            ps.setInt(1, sid);
            ps.setInt(2, c.getPlayer().getSkillLevel(sid));
            ps.setInt(3, c.getPlayer().getId());
            ps.setInt(4, cid);
            if (ps.executeUpdate() == 1) {
                c.getPlayer().dropMessage(1, "You have transferred the skill to that player.");
                c.getPlayer().ea();
            } else {
                c.getPlayer().dropMessage(1, "Failed to register to DB. Please contact the administrator");
            }
            ps.close();
            con.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void OnMemoInGameRequest(final ReadingMaple r, final MapleClient c) {
        if (c.getPlayer().getMeso() < 10000) {
            c.getPlayer().dropMessage(1, "Meso lacks.");
            return;
        }
        int v1 = r.readByte();
        String name = null;
        if (v1 == 0) {
            name = r.readMapleAsciiString();
        } else {
            int cid = r.readInt();
            try {
                name = BuddyListHandler.getCharacterNameFromId(cid);

            } catch (Exception ex) {
                Logger.getLogger(PlayerHandler.class
                        .getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        String t = r.readMapleAsciiString();
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            for (MapleCharacter ch : cs.getPlayerStorage().getAllCharacters().values()) {
                chr = ch;
                break;
            }
        }
        c.getPlayer().sendNote(name, t);
        if (chr != null) {
            chr.showNote();
        }
        c.getPlayer().dropMessage(1, name + "You sent a message to.");
    }

    public static void NameChange(ReadingMaple rp, MapleClient c) {
        final short type = rp.readShort();
        c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Nickname change application is completed. You can change your nickname in the character selection window."));
        c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
        c.getPlayer().gainItem(rp.readInt(), (short) -1, false, -1, "");
        c.setNameChangeValue(1, c.getPlayer().getAccountID());
    }

    public static void NameChange_From_Login(ReadingMaple rp, MapleClient c) {
        final int cid = rp.readInt();
        final String beforeName = rp.readMapleAsciiString();
        final String afterName = rp.readMapleAsciiString();
        if (c.getNameChangeValue() == 0) {
            c.send(MainPacketCreator.serverNotice(1, "You have already changed your nickname."));
        } else if (beforeName.equals(afterName)) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "We cannot change with nickname same as before."));
        } else if (MapleCharacterUtil.canCreateChar(afterName)) {
            c.setCharName(afterName, cid);
            c.setNameChangeValue(0, c.getAccID());
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "You have changed your nickname. Please log in again."));
        } else {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "[" + afterName + "] Can not be changed."));
        }
        c.send(LoginPacket.getLoginFailed(20));
    }

    public static void MannequinResult(ReadingMaple rp, MapleClient c) {
        rp.skip(4);
        boolean isSaveHair = rp.readByte() == 1;
        int slot = rp.readInt();
        c.getPlayer().selMannequineSlot = slot;
        String qd = c.getPlayer().getInfoQuest(26544);
        String data = "h" + slot + "=" + c.getPlayer().getHair();
        int hair = Integer.parseInt(qd.split("h" + slot + "=")[1].split(";")[0]);
        if (hair == 0) {
            qd = qd.replaceAll("h" + slot + "=" + hair, data);
            final MapleQuestStatus oldStatus = c.getPlayer().getQuest(MapleQuest.getInstance(26544));
            final MapleQuestStatus newStatus = new MapleQuestStatus(MapleQuest.getInstance(26544), (byte) 1, 0);
            newStatus.setCompletionTime(oldStatus.getCompletionTime());
            newStatus.setForfeited(oldStatus.getForfeited());
            c.getPlayer().updateQuest(newStatus);
            c.getPlayer().updateInfoQuest(26544, qd);
            c.sendPacket(MainPacketCreator.OnMannequinResult(slot, true));
            c.sendPacket(MainPacketCreator.SkillUseResult((byte) 0));
            c.sendPacket(MainPacketCreator.resetActions(c.getPlayer()));
        } else {
            NPCScriptManager.getInstance().start(c, 9000216, "ManneChange");
        }
    }

    public static void HyperSkillUp(ReadingMaple rm, MapleClient c) {
        rm.skip(4);
        int skilid = rm.readInt();
        ISkill skill = SkillFactory.getSkill(skilid);
        byte skillLevel = c.getPlayer().getSkillLevel(skill);
        byte maxLevel = skill.getMaxLevel();
        c.getPlayer().changeSkillLevel(skill, (byte) (skillLevel + 1), maxLevel);
    }

    public static void HyperStatUp(ReadingMaple rm, MapleClient c) {
        rm.skip(4);
        int skillid = rm.readInt();
        ISkill skill = SkillFactory.getSkill(skillid);
        byte skillLevel = c.getPlayer().getSkillLevel(skill);
        byte maxLevel = skill.getMaxLevel();
        c.getPlayer().changeSkillLevel(skill, (byte) (skillLevel + 1), maxLevel);
    }

    public static void HyperSkillRemove(final ReadingMaple rh, final MapleClient c) {
        final MapleQuestStatus status;
        final int count = ((status = new MapleQuestStatus(MapleQuest.getInstance(7965), (byte) 1)).getCustomData() != null) ? (Integer.parseInt(status.getCustomData()) + 1) : 1;
        final long meso = 10000L * (long) Math.pow(10.0, count);
        if (c.getPlayer().getMeso() < meso) {
            c.getPlayer().dropMessage(1, "Insufficient Meso to initialize Hyperskill.");
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final HashMap<ISkill, SkillEntry> hashMap = new HashMap<ISkill, SkillEntry>();
        final Iterator<ISkill> iterator = c.getPlayer().getSkills().keySet().iterator();
        while (iterator.hasNext()) {
            final ISkill skill;
            if ((skill = iterator.next()).getHyper() > 0) {
                hashMap.put(skill, new SkillEntry((byte) 0, (byte) 1, -1L));
            }
        }
        c.getPlayer().gainMeso(-meso, false);
        c.getPlayer().changeSkillLevel_NoSkip(hashMap, true);
        status.setCustomData(String.valueOf(count));
        c.getPlayer().updateQuest(status);
    }

    public static void HyperStatRemove(final ReadingMaple rh, final MapleClient c) {
        if (c.getPlayer().getMeso() < 10000000) {
            c.getPlayer().dropMessage(1, "Insufficient Meso to initialize Hyperstat.");
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final HashMap<ISkill, SkillEntry> hashMap = new HashMap<ISkill, SkillEntry>();
        final Iterator<ISkill> iterator = c.getPlayer().getSkills().keySet().iterator();
        while (iterator.hasNext()) {
            final ISkill skill;
            if ((skill = iterator.next()).getHyperStat() > 0) {
                hashMap.put(skill, new SkillEntry((byte) 0, (byte) skill.getMaxLevel(), -1L));
            }
        }
        c.getPlayer().gainMeso(-10000000, false);
        c.getPlayer().changeSkillLevel_NoSkip(hashMap, true);
    }

    public static void EnterDungen(ReadingMaple rm, MapleClient c) {
        final String d = rm.readMapleAsciiString();
        NPCScriptManager.getInstance().action(c, d, ServerConstants.cshopNpc, null);
    }

    public static void EventList(ReadingMaple rm, MapleClient c) {
    }

    public static void CashRemover(ReadingMaple rm, MapleClient c) {
        int invType = rm.readInt();
        int slot = rm.readInt();
        int npcid = rm.readInt();
        IItem item = c.getPlayer().getInventory(MapleInventoryType.getByType((byte) invType)).getItem((short) slot);
        InventoryManipulator.removeFromSlot(c, MapleInventoryType.getByType((byte) invType), (short) slot,
                item.getQuantity(), false);
        WritingPacket p = new WritingPacket();
        p.writeShort(0x16B);
        p.write(0x5C);
        c.sendPacket(p.getPacket());
    }

    public static void TowerChair(ReadingMaple rm, MapleClient c) {
        List<Integer> chairs = new ArrayList<Integer>();
        for (int a = 0; a < 6; a++) {
            int val = rm.readInt();
            if (val == 0) {
                break;
            }
            chairs.add(val);
        }
        StringBuilder sb = new StringBuilder();
        for (int a = 0; a < chairs.size(); a++) {
            sb.append(a);
            sb.append('=');
            sb.append(chairs.get(a));
            if (a != chairs.size() - 1) {
                sb.append(';');
            }
        }
        int[] temp = new int[chairs.size()];
        int i = 0;
        for (Integer e : chairs) {
            temp[i++] = e.intValue();
        }
        String set = "#" + temp.length + sb.toString();
        c.getPlayer().setTowerChairSetting(set);
        c.getPlayer().SaveSLFCG();
        c.getSession().writeAndFlush(SLFCGPacket.TowerChairSaveDone());
        c.getSession().writeAndFlush(SLFCGPacket.TowerChairMessage(c.getPlayer().getTowerChairSetting().substring(2)));
        Point temppoint = c.getPlayer().getPosition();
        c.getPlayer().changeMap(c.getPlayer().getMapId(), 2);
        c.getSession().writeAndFlush(SLFCGPacket.CharReLocationPacket(temppoint.x, temppoint.y));
    }

    public static void DarkSpear(ReadingMaple rm, MapleClient c) {
        MapleCharacter chr = c.getPlayer();

        int nSkillID = rm.readInt();
        int nSLV = rm.readInt();
        int v2 = rm.readInt();
        byte v3 = rm.readByte();
        if (v3 == 1) {
            rm.readInt();
        }
        Point pt = new Point(rm.readInt(), rm.readInt());

        int nBulletID = rm.readInt();
        int v7 = rm.readInt();

        byte v8 = rm.readByte();
        byte v9 = rm.readByte();
        int v10 = rm.readInt();
        byte v11 = rm.readByte();

        if (nSkillID == 400051003) {
            if (chr.transformEnergyOrb <= 0) {
                return;
            }

            SkillStatEffect effect = SkillFactory.getSkill(400051002).getEffect(chr.getSkillLevel(400051002));

            chr.transformEnergyOrb--;

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.IndiePMdR, effect.getStat("indiePMdR"), true));
            statups.add(new Triple<>(BuffStats.Transform, chr.transformEnergyOrb, false));

            long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);
            int duration = effect.getDuration();
            duration += (int) (chr.getBuffedStarttime(BuffStats.Transform, 400051002) - System.currentTimeMillis());
            Map<BuffStats, List<StackedSkillEntry>> stacked = chr.getStackSkills();
            for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                if (statup.getThird()) {
                    if (!stacked.containsKey(statup.getFirst())) {
                        stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                    }
                    stacked.get(statup.getFirst())
                            .add(new StackedSkillEntry(400051002, statup.getSecond(), overlap_magic, duration));
                }
            }

            c.send(MainPacketCreator.giveBuff(400051002, duration, statups, effect, stacked,
                    SkillFactory.getSkill(400051002).getAnimationTime(), chr));
            final long starttime = System.currentTimeMillis();
            chr.registerEffect(effect, starttime,
                    tools.Timer.BuffTimer.getInstance().schedule(
                            new SkillStatEffect.CancelEffectAction(chr, effect, starttime),
                            ((starttime + duration) - System.currentTimeMillis())));
            chr.getMap().broadcastMessage(chr, MainPacketCreator.giveForeignBuff(chr, statups), false);
        } else if (nSkillID == 400051008) {
            if (chr.BHGCCount <= 0) {
                return;
            }

            SkillStatEffect effect = SkillFactory.getSkill(400051008).getEffect(chr.getSkillLevel(400051008));

            if (chr.BHGCCount == effect.getY()) {
                chr.lastBHGCGiveTime = System.currentTimeMillis();
            }
            chr.BHGCCount--;

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.BigHugeGiganticCanonBall, chr.BHGCCount, false));

            chr.send(MainPacketCreator.giveBuff(400051008, Integer.MAX_VALUE, statups, effect, chr.getStackSkills(), 0,
                    chr));
        }
        if (nSkillID == 152001002 || nSkillID == 152120003) {
            int passiveid = 152000007;
            SkillFactory.getSkill(passiveid).getEffect(chr.getSkillLevel(passiveid)).applyTo(chr);
            SkillStatEffect craft = SkillFactory.getSkill(nSkillID).getEffect(chr.getTotalSkillLevel(nSkillID));
            craft.applyTo(chr);
        }
        if (nSkillID == 400021048) {
            SkillStatEffect effect = SkillFactory.getSkill(nSkillID).getEffect(chr.getSkillLevel(nSkillID));
            chr.givePPoint(effect, false);
        }

        switch (nSkillID) {
            case 3301008: {
                c.getPlayer().setRelicCount(c.getPlayer().getRelicCount() - 80);
                break;
            }
            case 3311010:
            case 3311011: {
                c.getPlayer().setRelicCount(c.getPlayer().getRelicCount() - 80);
                break;
            }
        }

        int mainskillid = nSkillID; //Ancient Astra Cool Time
        if (nSkillID >= 3321036 && nSkillID <= 3321040) {
            mainskillid = 3321035;
        }
        int cool = SkillFactory.getSkill(mainskillid).getEffect(chr.getSkillLevel(mainskillid)).getCooldown();
        if (cool > 0) {
            chr.addCooldown(mainskillid, System.currentTimeMillis(), cool * 1000);
        }
        int size = rm.readInt();
        List<MatrixSkill> arMatrixSkill = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MatrixSkill ms = new MatrixSkill(rm.readInt(), rm.readInt(), rm.readInt(), rm.readShort(), new Point(rm.readShort(), rm.readShort()), rm.readInt(), rm.readByte() == 1);
            if (rm.readByte() == 1) {
                ms.setPt2(new Point(rm.readInt(), rm.readInt()));
            }

            arMatrixSkill.add(ms);
        }
        c.sendPacket(MatrixPacket.MatrixSkill(nSkillID, nSLV, arMatrixSkill));
        c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MatrixPacket.MatrixSkillMulti(c.getPlayer().getId(), nSkillID, nSLV, pt, nBulletID, arMatrixSkill), false);
    }

    public static void DaethCount(ReadingMaple rm, MapleClient c) {
        rm.readByte();
		System.out.println("Descount Estimation");

        if (c.getPlayer().bossDeathCount != -1) {
            c.getPlayer().bossDeathCount--;
            c.getPlayer().addHP(c.getPlayer().getStat().getCurrentMaxHp());
            AswanHandler.AswanWarp(c, c.getPlayer().getMapId());
            c.getSession().writeAndFlush(MainPacketCreator.getDeathCount(c.getPlayer().bossDeathCount));
        } else {
            if (c.getPlayer().getEventInstance() != null) {
                c.getPlayer().getEventInstance().unregisterPlayer(c.getPlayer());
            }
            c.getPlayer().changeMap(100000000, 2);
        }
    }

    public static void LoadedDice(ReadingMaple slea, MapleClient c) {
        c.getPlayer().loadedDice = slea.readInt();
    }

    public static void IncSkillTime(ReadingMaple slea, MapleClient c) {
        if (System.currentTimeMillis() - c.getPlayer().lastIncSkillTime < 1000) {
            return;
        }

        c.getPlayer().lastIncSkillTime = System.currentTimeMillis();

        int skillId = slea.readInt();

        if (skillId == 400051006) {
            SkillStatEffect effect = SkillFactory.getSkill(400051006).getEffect(c.getPlayer().getSkillLevel(400051006));

            if (System.currentTimeMillis() - c.getPlayer().bulletPartyStartTime >= effect.getY() * 1000) {
                return;
            }

            effect.applyTo(c.getPlayer(), c.getPlayer().getPosition(), true);
        }
    }

    public static void SymbolExp(ReadingMaple slea, MapleClient c) { //Arcane Symbol
        try {
            int type = slea.readInt();
            if (type == 0) {
                int src = slea.readInt();//Slot number

                int pos = slea.readInt();

                Equip source = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) src);

                Equip symbol = null;

                for (IItem items : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).list()) {

                    if (source.getItemId() == items.getItemId()) {

                        symbol = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) items.getPosition());

                    }

                }
                ArrayList removes = new ArrayList();

                for (IItem items : c.getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {

                    if (symbol.getItemId() == items.getItemId()) {

                        Equip _source = (Equip) items;

                        if (source.getArcLevel() == _source.getArcLevel()) {

                            symbol.setArcEXP(symbol.getArcEXP() + (_source.getArcEXP() / 2 + 1) * Math.max(1, _source.getArcLevel()));

                            removes.add(_source.getPosition());

                            if (symbol.getArcEXP() >= Math.pow(symbol.getArcLevel(), 2) + 11) {

                                c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "The symbol experience has reached its maximum.\nRaise the level of the symbol."));

                                break;

                            }

                        }

                    }

                }

                Collections.sort(removes);

                for (Object ri : removes) {
                    c.getPlayer().getInventory(MapleInventoryType.EQUIP).removeSlot((Short) ri);
                    c.getSession().writeAndFlush(MainPacketCreator.clearInventoryItem(MapleInventoryType.EQUIP, (short) ri, false));
                }

                c.getSession().writeAndFlush(MainPacketCreator.updateEquipSlot(symbol, true));

            } else if (type == 1) {
                int pos = slea.readInt() * -1;
                Equip item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) pos);
                long meso = 12440000 + (6600000 * item.getArcLevel());
                if (c.getPlayer().getMeso() < meso) {
                    c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "[Notice] There is not enough Meso to symbolize up."));
                }
                item.setArcLevel(item.getArcLevel() + 1);
                item.setArcEXP(0);
                item.setArc((short) (10 * (item.getArcLevel() + 2)));
                if (GameConstants.isStrJob(c.getPlayer().getJob())) {
                    item.setStr((short) (item.getStr() + 100));
                } else if (GameConstants.isDexJob(c.getPlayer().getJob())) {
                    item.setDex((short) (item.getDex() + 100));
                } else if (GameConstants.isIntJob(c.getPlayer().getJob())) {
                    item.setInt((short) (item.getInt() + 100));
                } else if (GameConstants.isLukJob(c.getPlayer().getJob())) {
                    item.setLuk((short) (item.getLuk() + 100));
                } else if (GameConstants.isXenon(c.getPlayer().getJob())) {
                    item.setStr((short) (item.getStr() + 39));
                    item.setDex((short) (item.getDex() + 39));
                    item.setLuk((short) (item.getLuk() + 39));
                }
                c.getPlayer().gainMeso(-meso, false);
                c.getSession().writeAndFlush(MainPacketCreator.updateEquipSlot(item, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InhumanSpeed(ReadingMaple slea, MapleClient c) {
        int objectId = slea.readInt();
        slea.readInt();

        MapleCharacter chr = c.getPlayer();
        MapleMonster mob = chr.getMap().getMonsterByOid(objectId);
        if (mob != null) {
            chr.getMap().broadcastMessage(MainPacketCreator.giveInhumanSpeedArrow(chr, mob));
        }
    }

    public static void BossRate(ReadingMaple rm, MapleClient c) {
        rm.skip(8);
        int idx = rm.readInt();
        int[] mapid = {105100100, 211042300, 970072200, 401060000, 262030000, 221030900, 220080000, 105200000,
            105200000, 105200000, 105200000, 211070000, 240050400, 272020110, 270050000, 271040000, 350060000,
            105300303, 450004000};
        c.getPlayer().changeMap(mapid[idx], 0);
    }

    public static void ICBMData(ReadingMaple slea, MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        int angle = slea.readInt();
        int y = slea.readInt();
    }

    public static void HowlingGaleEnd(ReadingMaple slea, MapleClient c) {
    }

    public static void Joker(ReadingMaple slea, MapleClient c) {
        MapleCharacter player = c.getPlayer();

        if (player.isActiveBuffedValue(400041009)) {
            SkillFactory.getSkill(400041010).getEffect(player.getSkillLevel(400041009)).applyAtom(player, 33);
        }
    }

    public static void SpotlightBuff(ReadingMaple slea, MapleClient c) {
        final MapleCharacter chr = c.getPlayer();

        if (slea.readByte() == 0) {
            chr.cancelEffectFromBuffStat(BuffStats.Spotlight);
        } else {
            int buffCount = slea.readInt();

            SkillStatEffect eff = chr.getBuffedSkillEffect(BuffStats.Spotlight);

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.IndieAsrR, eff.getW() * buffCount, true));
            statups.add(new Triple<>(BuffStats.IndieCr, eff.getV() * buffCount, true));
            statups.add(new Triple<>(BuffStats.IndieStance, eff.getStat("q") * buffCount, true));
            statups.add(new Triple<>(BuffStats.IndiePMdR, eff.getStat("s") * buffCount, true));

            long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);

            int duration = eff.getDuration(), i = 0;
            List<StackedSkillEntry> stacks;
            List<StackedSkillEntry> copyStacks;
            duration += (int) (chr.getBuffedStarttime(BuffStats.Spotlight, 400051018) - System.currentTimeMillis());

            for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                if (statup.third) {
                    i = 0;
                    stacks = chr.getStackSkills().get(statup.first);
                    copyStacks = new LinkedList<>(stacks);

                    for (StackedSkillEntry stack : copyStacks) {
                        if (stack.getSkillId() == 400051018) {
                            stacks.remove(i);
                        }

                        i++;
                    }

                    stacks.add(new StackedSkillEntry(400051018, statup.second, overlap_magic, Integer.MAX_VALUE));
                }
            }

            chr.send(MainPacketCreator.giveBuff(400051018, duration, statups, eff, chr.getStackSkills(), 0, chr));
        }
    }

    public static void XenonMegaSmahser(ReadingMaple slea, MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        boolean start = slea.readByte() == 1;

        if (start) {
            SkillStatEffect effect = SkillFactory.getSkill(400041007).getEffect(chr.getSkillLevel(400041007));

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.MegaSmasher, -1, false));

            long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);
            int localDuration = Integer.MAX_VALUE;
            Map<BuffStats, List<StackedSkillEntry>> stacked = chr.getStackSkills();
            for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                if (statup.getThird()) {
                    if (!stacked.containsKey(statup.getFirst())) {
                        stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                    }
                    stacked.get(statup.getFirst())
                            .add(new StackedSkillEntry(400041007, statup.getSecond(), overlap_magic, localDuration));
                }
            }

            chr.send(MainPacketCreator.giveBuff(400041007, localDuration, statups, effect, stacked,
                    SkillFactory.getSkill(400041007).getAnimationTime(), chr));
            final long starttime = System.currentTimeMillis();
            chr.forceRegisterEffect(effect, statups, starttime, null);
            chr.getMap().broadcastMessage(chr, MainPacketCreator.giveForeignBuff(chr, statups), false);

            chr.isMegaSmasherCharging = true;
            chr.megaSmasherChargeStartTime = System.currentTimeMillis();
        } else {
            if (!chr.isMegaSmasherCharging) {
                return;
            }

            SkillStatEffect effect = SkillFactory.getSkill(400041007).getEffect(chr.getSkillLevel(400041007));

            int maxChargeTime = effect.getTime() + effect.getZ() * 1000;
            int chargeTime = Math.min(maxChargeTime,
                    effect.getTime() + (int) ((System.currentTimeMillis() - chr.megaSmasherChargeStartTime)
                    / (effect.getY() * 1000) * 1000));

            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            statups.add(new Triple<>(BuffStats.MegaSmasher, 1, false));
            chr.setBuffedValue(BuffStats.MegaSmasher, 400041007, 1);

            long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);
            int localDuration = chargeTime;
            Map<BuffStats, List<StackedSkillEntry>> stacked = chr.getStackSkills();
            for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                if (statup.getThird()) {
                    if (!stacked.containsKey(statup.getFirst())) {
                        stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                    }
                    stacked.get(statup.getFirst())
                            .add(new StackedSkillEntry(400041007, statup.getSecond(), overlap_magic, localDuration));
                }
            }

            chr.send(MainPacketCreator.giveBuff(400041007, localDuration, statups, effect, stacked,
                    SkillFactory.getSkill(400041007).getAnimationTime(), chr));
            final long starttime = System.currentTimeMillis();
            chr.registerEffect(effect, starttime,
                    tools.Timer.BuffTimer.getInstance().schedule(
                            new SkillStatEffect.CancelEffectAction(chr, effect, starttime),
                            ((starttime + localDuration) - System.currentTimeMillis())));
            chr.getMap().broadcastMessage(chr, MainPacketCreator.giveForeignBuff(chr, statups), false);
        }
    }

    public static void DailyGift(ReadingMaple rm, MapleClient c) {
        Calendar cal = Calendar.getInstance();
        int Month = cal.get(Calendar.MONTH) + 1;
        if (c.getPlayer().getDailyGift() == null) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Unable to receive payment due to unknown error."));
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        if (!c.getPlayer().getDailyGift().getDailyData().equals(cal.get(Calendar.YEAR) + (Month == 10 || Month == 11 || Month == 12 ? "" : "0") + Month + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE))) {
            c.getPlayer().gainItem(GameConstants.dailyGifts[c.getPlayer().getDailyGift().getDailyDay() + 1], GameConstants.dailyCounts[c.getPlayer().getDailyGift().getDailyDay() + 1]);
            c.getPlayer().getDailyGift().setDailyDay(c.getPlayer().getDailyGift().getDailyDay() + 1);
            c.getPlayer().getDailyGift().setDailyCount(1);
            c.getPlayer().getDailyGift().setDailyData(cal.get(Calendar.YEAR) + (Month == 10 || Month == 11 || Month == 12 ? "" : "0") + Month + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE));
            c.getPlayer().getDailyGift().saveDailyGift(c.getPlayer().getClient().getAccID(c.getAccountName()), c.getPlayer().getDailyGift().getDailyDay(), c.getPlayer().getDailyGift().getDailyDay(), c.getPlayer().getDailyGift().getDailyData());
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Item payment completed."));
        }
        c.getSession().writeAndFlush(MainPacketCreator.getDailyGiftRecord("count=" + c.getPlayer().getDailyGift().getDailyCount() + ";day=" + c.getPlayer().getDailyGift().getDailyDay() + ";date=" + cal.get(Calendar.YEAR) + (Month == 10 || Month == 11 || Month == 12 ? "" : "0") + Month + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE) + ";"));
        c.getPlayer().ea();
    }

    public static void LinkSkillAction(MapleClient paramMapleClient, ReadingMaple paramLittleEndianAccessor) {
        List<LinkSkill> localObject = paramMapleClient.getPlayer().getLinkSkill(true, false, false);
        int i = paramLittleEndianAccessor.readInt();
        int j = paramLittleEndianAccessor.readInt();
        paramLittleEndianAccessor.readInt();
        for (LinkSkill skill : localObject) {
            if (skill.checkInfo(i, j)) {
                paramMapleClient.getPlayer().LinkSkill(skill);
                return;
            }
        }
    }

    public static void UnLinkSkillAction(MapleClient paramMapleClient, ReadingMaple paramLittleEndianAccessor) {
        List<LinkSkill> localObject = paramMapleClient.getPlayer().getLinkSkill(false, false, false);
        int v1 = paramLittleEndianAccessor.readInt();
        for (LinkSkill skill : localObject) {
            if (skill.checkInfo_(v1, paramMapleClient.getPlayer().getId())) {
                paramMapleClient.getPlayer().UnLinkSkill(skill);
                return;
            }
        }
    }

    public static void AndroidEar(MapleClient c, ReadingMaple rm) {
        MapleAndroid android = c.getPlayer().getAndroid();
        if (android == null) {
            c.getPlayer().dropMessage(1, "An unknown error has occurred.");
            c.getPlayer().ea();
            return;
        }
        android.setEear(!android.isEear());
        c.getPlayer().updateAndroid();
        server.Items.InventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, rm.readShort(), (short) 1, true);
        c.getPlayer().ea();
    }

    public static void BurningRegion(final MapleClient c, final int skillid) {
        BuffTimer tMan = BuffTimer.getInstance();
        if (diabolicRecoveryTask != null) {
            diabolicRecoveryTask.cancel(true);
            diabolicRecoveryTask = null;
        }
        Runnable r = new Runnable() {

            @Override
            public void run() {
                if (c.getPlayer() != null) {
                    for (final MapleMapObject mm : c.getPlayer().getMap().getAllMistsThreadsafe()) {
                        MapleMist mist = (MapleMist) mm;
                        if (mist.getSourceSkill() != null) {
                            if (mist.getSourceSkill().getId() == 12121005) {
                                c.getPlayer().cancelEffectFromBuffStat(BuffStats.IndieBooster, 12121005);
                                c.getPlayer().cancelEffectFromBuffStat(BuffStats.IndieDamR, 12121005);
                                for (final MapleMapObject mo : c.getPlayer().getMap().getMapObjectsInRect(mist.getBox(), Collections.singletonList(MapleMapObjectType.PLAYER))) {
                                    MapleCharacter chr = (MapleCharacter) mo;
                                    SkillStatEffect effect = SkillFactory.getSkill(12121005).getEffect(c.getPlayer().getSkillLevel(12121005));
                                    if (!chr.isActiveBuffedValue(12121005)) {
                                        effect.applyTo(chr);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    diabolicRecoveryTask.cancel(true);
                    diabolicRecoveryTask = null;
                }
            }
        };
        diabolicRecoveryTask = tMan.register(r, 1000);
        tMan.schedule(new Runnable() {
            @Override
            public void run() {
                if (diabolicRecoveryTask != null) {
                    diabolicRecoveryTask.cancel(true);
                    diabolicRecoveryTask = null;
                }
            }
        }, 30000);
    }

    public static void IceAura(final MapleClient c, final Point pos, final int skillid) {
        SkillStatEffect effect = SkillFactory.getSkill(skillid).getEffect(c.getPlayer().getSkillLevel(skillid));
        BuffTimer tMan = BuffTimer.getInstance();
        int i = 0;
        if (diabolicRecoveryTask != null) {
            diabolicRecoveryTask.cancel(true);
            diabolicRecoveryTask = null;
        }
        Runnable r = new Runnable() {

            @Override
            public void run() {
                if (c.getPlayer() != null) {
                    if (c.getPlayer().isIceAura()) {
                        for (final MapleMapObject mo : c.getPlayer().getMap().getMapObjectsInRect(effect.calculateBoundingBox(c.getPlayer().getPosition(), c.getPlayer().isFacingLeft()), Collections.singletonList(MapleMapObjectType.MONSTER))) {
                            if (((MapleMonster) mo).getFreezeStack() != 5) {
                                ((MapleMonster) mo).setFreezeStack(((MapleMonster) mo).getFreezeStack() + 1);
                            } else {
                                ((MapleMonster) mo).setFreezeStack(1);
                            }
                            ((MapleMonster) mo).applyStatus(c.getPlayer(), new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.SPEED, ((MapleMonster) mo).getFreezeStack()), SkillFactory.getSkill(skillid), c.getPlayer().getSkillLevel(skillid), null, false), 999999999);
                        }
                    }
                } else {
                    diabolicRecoveryTask.cancel(true);
                    diabolicRecoveryTask = null;
                }
            }
        };
        diabolicRecoveryTask = tMan.register(r, 5000);
        tMan.schedule(new Runnable() {
            @Override
            public void run() {
                if (diabolicRecoveryTask != null) {
                    diabolicRecoveryTask.cancel(true);
                    diabolicRecoveryTask = null;
                }
            }
        }, 2100000000);
    }

    public static void HandleCellClick(final int number, MapleClient c) {
        if (c.getPlayer().getBingoGame().getRanking().contains(c.getPlayer())) {
            return;
        }
        int[][] table = c.getPlayer().getBingoGame().getTable(c.getPlayer());
        c.getSession().writeAndFlush(MainPacketCreator.BingoCheckNumber(number));
        int jj = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (table[x][y] == number) {
                    table[x][y] = 0xFF;
                }
            }
        }
        int temp = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (table[x][y] == 0xFF || table[x][y] == 0x00) {
                    temp++;
                }
            }
            if (temp == 5) {
                c.getSession().writeAndFlush(MainPacketCreator.BingoDrawLine(y * 5, 0, number));
            }
            temp = 0;
        }
        temp = 0;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (table[x][y] == 0xFF || table[x][y] == 0x00) {
                    temp++;
                }
            }
            if (temp == 5) {
                c.getSession().writeAndFlush(MainPacketCreator.BingoDrawLine(x, 1, number));
            }
            temp = 0;
        }
        int crossCnt = 0;
        int rcrossCnt = 0;
        for (int i = 0; i < 5; i++) {
            if (table[i][i] == 0xFF || table[i][i] == 0x00) {
                crossCnt++;
            }
            if (table[i][4 - i] == 0xFF || table[i][4 - i] == 0x00) {
                rcrossCnt++;
            }
            if (crossCnt == 5) {
                c.getSession().writeAndFlush(MainPacketCreator.BingoDrawLine(1, 2, number));
            }
            if (rcrossCnt == 5) {
                c.getSession().writeAndFlush(MainPacketCreator.BingoDrawLine(1, 3, number));
            }
        }
    }

    public static void HandleBingoClick(MapleClient c) {
        c.getPlayer().getBingoGame().addRank(c.getPlayer());
    }

    public static void KadenaSkill(ReadingMaple slea, MapleClient c) {
        c.getPlayer().send(MainPacketCreator.SpecialSkill(c.getPlayer(), 400041034, 1));
        c.getPlayer().setAtomsa(c.getPlayer().getAtomsa() + 1);
    }

    public static void AranCommandLock(final ReadingMaple slea, final MapleClient c) {

        String id = "";
        List<Pair<Byte, Byte>> a = new ArrayList<>();
        String info = c.getPlayer().getInfoQuest(21770);
        String[] info_ = info == "" ? null : info.split(";");
        info = "";

        for (int i = 0; i < 10; i++) {
            a.add(i, new Pair<>((byte) -1, (byte) -1));
        }

        if (info_ != null) {
            for (int i = 0; i < info_.length; i++) {
                a.remove(Byte.parseByte(info_[i].split("=")[0]) - 1);
                a.add(Byte.parseByte(info_[i].split("=")[0]) - 1, new Pair<>(Byte.parseByte(info_[i].split("=")[0]), Byte.parseByte(info_[i].split("=")[1])));
            }
        }

        switch (slea.readInt()) {
            case 21001009:
                id = "1";
                break;
            case 21101011:
                id = "2";
                break;
            case 21101016:
                id = "3";
                break;
            case 21101017:
                id = "4";
                break;
            case 21111017:
                id = "5";
                break;
            case 21111019:
                id = "6";
                break;
            case 21111021:
                id = "7";
                break;
            case 21120023:
                id = "8";
                break;
            case 21120019:
                id = "9";
                break;
            case 400011031:
                id = "10";
                break;
        }
        if (id == "") {
            c.getPlayer().send(MainPacketCreator.resetActions());
            return;
        }

        boolean changed = false;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getLeft().equals(Byte.parseByte(id))) {
                byte value = a.get(i).getRight() == 0 ? (byte) 1 : (byte) 0;
                a.remove(i);
                a.add(i, new Pair<>(Byte.parseByte(id), value));
                changed = true;
                break;
            }
        }

        if (!changed) {
            a.remove(Byte.parseByte(id) - 1);
            a.add(Byte.parseByte(id) - 1, new Pair<>(Byte.parseByte(id), (byte) 1));
        }

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getLeft() != (byte) -1) {
                if (info == "") {
                    info = a.get(i).getLeft() + "=" + a.get(i).getRight();
                } else {
                    info = info + ";" + a.get(i).getLeft() + "=" + a.get(i).getRight();
                }
            }
        }

        c.getPlayer().updateInfoQuest(21770, info);
    }

    public static void CrystalSkill(final ReadingMaple slea, final MapleClient c) {
        final int skillid;
        int n = skillid = slea.readInt();
        int oid = -1;
        for (MapleMapObject ob : c.getPlayer().getMap().getAllSummon()) {
            MapleSummon summon = (MapleSummon) ob;
            if (summon.getOwner() != null && summon.getOwner().getId() == c.getPlayer().getId() && summon.getSkill() == 152101000) {
                oid = summon.getObjectId();
                break;
            }
        }

        if (n == 152001001 || n == 152120001 || n == 152121004) {
            n = 152110001;
        } else if (n == 152001002 || n == 152120003) {
            n = 152100002;
            if (c.getPlayer().getSkillLevel(152110002) > 0) {
                n = 152110002;
            }
        }

        if (skillid == 152001002 || skillid == 152120003 && !c.getPlayer().skillisCooling(skillid)) {
            c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MainPacketCreator.CrystalSkill(c.getPlayer(), oid, 3), true);
            int passiveid = 152000007;
            SkillFactory.getSkill(passiveid).getEffect(c.getPlayer().getSkillLevel(passiveid)).applyTo(c.getPlayer());
            SkillStatEffect craft = SkillFactory.getSkill(skillid).getEffect(c.getPlayer().getTotalSkillLevel(skillid));
            craft.applyTo(c.getPlayer());
        }
        if ((n == 152100001 || n == 152110001) && !c.getPlayer().skillisCooling(152110001)) {
            c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MainPacketCreator.CrystalSkill2(c.getPlayer(), oid, n), true);
        }

        c.getPlayer().handleCristalCharge(skillid, oid);
    }

    public static void BlackAlter(ReadingMaple rm, MapleClient c) {

        int oid = rm.readInt();
        MapleSummon hs = null;
        if ((MapleSummon) c.getPlayer().getMap().getMapObject(oid, MapleMapObjectType.SUMMON) != null) {
            hs = (MapleSummon) c.getPlayer().getMap().getMapObject(oid, MapleMapObjectType.SUMMON);
        }

        if (hs.getSkill() == 400041033) {
            rm.skip(1);
            int nSkillID = rm.readInt();
            int nSLV = rm.readInt();
            int v2 = rm.readInt();
            byte v3 = rm.readByte();
            if (v3 == 1) {
                rm.readInt();
            }
            Point pt = new Point(rm.readInt(), rm.readInt());

            int nBulletID = rm.readInt();
            int v7 = rm.readInt();

            byte v8 = rm.readByte();
            byte v9 = rm.readByte();
            int v10 = rm.readInt();
            byte v11 = rm.readByte();

            int size = rm.readInt();
            List<MatrixSkill> arMatrixSkill = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                MatrixSkill ms = new MatrixSkill(rm.readInt(), rm.readInt(), rm.readInt(), rm.readShort(), new Point(rm.readShort(), rm.readShort()), rm.readInt(), rm.readByte() == 1);
                if (rm.readByte() == 1) {
                    ms.setPt2(new Point(rm.readInt(), rm.readInt()));
                }

                arMatrixSkill.add(ms);
            }
            c.sendPacket(MatrixPacket.MatrixSkill(nSkillID, nSLV, arMatrixSkill));
            c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MatrixPacket.MatrixSkillMulti(c.getPlayer().getId(), nSkillID, nSLV, pt, nBulletID, arMatrixSkill), false);
        }
    }

    public static void ArkAutoSkill(ReadingMaple slea, MapleClient c) {
        int skillid = slea.readInt();
        if (c.getPlayer().getKeyValue3("ArkAutoSkill_" + skillid) == -1) {
            c.getPlayer().setKeyValue3("ArkAutoSkill_" + skillid, 1);
        } else {
            c.getPlayer().setKeyValue3("ArkAutoSkill_" + skillid, -1);
        }
        int value = c.getPlayer().getKeyValue3("ArkAutoSkill_155001103") == -1 ? 0 : 1;
        int NewValue = c.getPlayer().getKeyValue3("ArkAutoSkill_155111207") == -1 ? 0 : 1;
        c.getPlayer().updateInfoQuest(1544, "155001103=" + value + ";155111207=" + NewValue);
        c.getPlayer().send(MainPacketCreator.resetActions());
    }

    public static void GiveGrandCross(ReadingMaple slea, MapleClient c) {
        int skillid = slea.readInt();
        if (skillid == 400011072) {
            if (c.getPlayer().getKeyDownSkill_Time() + 3000 <= System.currentTimeMillis()) {
                c.getPlayer().setBuffedValue(BuffStats.GrandCrossSize, 400011072, 2);
                SkillStatEffect effect = SkillFactory.getSkill(400011072).getEffect(c.getPlayer().getSkillLevel(400011072));
                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                statups.add(new Triple<>(BuffStats.GrandCrossSize, 2, false));
                c.getPlayer().send(MainPacketCreator.giveBuff(400011072, 0, statups, effect, null,
                        0, c.getPlayer()));
                c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MainPacketCreator.giveForeignBuff(c.getPlayer(), statups), false);

            }
        }
    }

    public static void Obsidiunbarrier(final ReadingMaple slea, MapleClient c) {
        final int skill = slea.readInt();
        List<MapleMapObject> mist = c.getPlayer().getMap().getAllMistsThreadsafe();
        MapleMist maha = null;
        for (MapleMapObject ob : mist) {
            MapleMist Mmist = (MapleMist) ob;
            System.out.println(Mmist.getSource().getSourceId());
            if (Mmist.getSource().getSourceId() == 400031040 || Mmist.getSource().getSourceId() == 400031037) {
                System.out.println(Mmist.getObjectId());
                c.getPlayer().getMap().broadcastMessage(MainPacketCreator.removeMist(Mmist.getObjectId(), false));
            }
        }
        SkillStatEffect effect = SkillFactory.getSkill(skill).getEffect(skill);
        effect.applyTo(c.getPlayer());
        c.getPlayer().ea();
    }
    
    public static void openUnion(MapleClient c) {
        System.out.println("openUnion");
        /*
        c.getSession().writeAndFlush(MainPacketCreator.openUnionUI(c)); // 패킷 출력
        c.getSession().writeAndFlush(MainPacketCreator.UnionPreset(0));
        c.getSession().writeAndFlush(MainPacketCreator.UnionPreset(1));
        c.getSession().writeAndFlush(MainPacketCreator.UnionPreset(2));
        c.getSession().writeAndFlush(MainPacketCreator.UnionPreset(3));
        c.getSession().writeAndFlush(MainPacketCreator.UnionPreset(4));
    */
    }
}
