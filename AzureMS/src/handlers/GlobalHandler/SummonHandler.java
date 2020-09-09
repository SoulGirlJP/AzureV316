package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import client.MapleClient;
import client.Skills.ISkill;
import client.Skills.MatrixSkill;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import client.Skills.SummonSkillEntry;
import client.Stats.BuffStats;
import client.Stats.MonsterStatusEffect;
import connections.Packets.MainPacketCreator;
import connections.Packets.MatrixPacket;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.UIPacket;
import constants.GameConstants;
import java.util.Arrays;
import server.Items.ItemInformation;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.SummonAttackEntry;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapSummon.MapleSummon;
import server.Maps.MapSummon.SummonMovementType;
import server.Maps.MapleMapHandling.MapleMap;
import server.Movement.LifeMovementFragment;
import tools.Pair;

public class SummonHandler {

    public static final void MoveDragon(final ReadingMaple rh, final MapleCharacter chr) {
        rh.skip(12); // POS v192 +4byte.
        final List<LifeMovementFragment> res = MovementParse.parseMovement(rh);
        if (chr.getDragon() != null) {
            final List<LifeMovementFragment> res2 = new ArrayList<LifeMovementFragment>(res);
            final Point pos = chr.getDragon().getPosition();
            MovementParse.updatePosition(res, chr.getDragon(), 0);
            if (!chr.isHidden()) {
                chr.getMap().broadcastMessage(chr, MainPacketCreator.moveDragon(chr.getDragon(), pos, res),
                        chr.getPosition());
            }
        }
    }

    public static final void MoveSummon(final ReadingMaple rh, final MapleCharacter chr) {
        final int oid = rh.readInt();
        rh.skip(12); // +4byte [v192]
        final List<LifeMovementFragment> res = MovementParse.parseMovement(rh);

        for (Pair<Integer, MapleSummon> sum : chr.getSummons().values()) {
            if (sum.right.getObjectId() == oid && sum.right.getMovementType() != SummonMovementType.STATIONARY) {
                final Point startPos = sum.right.getPosition();
                MovementParse.updatePosition(res, sum.right, 0);
                chr.getMap().broadcastMessage(chr, MainPacketCreator.moveSummon(chr.getId(), oid, startPos, res),
                        sum.right.getPosition());
                break;
            }
        }
    }

    public static final void DamageSummon(final ReadingMaple rh, final MapleCharacter chr) {
        final int unkByte = rh.readByte();
        final int damage = rh.readInt();
        final int monsterIdFrom = rh.readInt();

        final Iterator<Pair<Integer, MapleSummon>> iter = chr.getSummons().values().iterator();
        MapleSummon summon;

        while (iter.hasNext()) {
            summon = iter.next().right;
            if (summon.getOwner().getId() == chr.getId()) {
                summon.addHP((short) -damage);
                chr.getMap().broadcastMessage(chr,
                        MainPacketCreator.damageSummon(chr.getId(), summon.getSkill(), damage, unkByte, monsterIdFrom),
                        summon.getPosition());
                if (summon.getSkill() == 14000027) {
                    chr.getMap().broadcastMessage(MainPacketCreator.removeSummon(summon, true));
                    chr.getMap().removeMapObject(summon);
                    summon.getOwner().removeVisibleMapObject(summon);
                    if (summon.getOwner().getSummons().get(summon.getSkill()) != null) {
                        summon.getOwner().getSummons().remove(summon.getObjectId());
                    }
                }
            }
        }
    }

    public static final void SummonAttack(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        if (!chr.isAlive()) {
            return;
        }
        final MapleMap map = chr.getMap();
        final MapleMapObject obj = map.getMapObject(rh.readInt(), MapleMapObjectType.SUMMON);
        if (obj == null || !obj.getType().equals(MapleMapObjectType.SUMMON)) {
            return;
        }
        rh.readInt();
        int v1 = rh.readInt();
        int skillid = rh.readInt();
        int v2 = rh.readInt();
       
        rh.readByte();
        final byte animation = rh.readByte();
        byte tbyte = (byte) (rh.readByte());
        short targets = (short) ((tbyte >>> 4) & 0xF);
        byte hits = (byte) (tbyte & 0xF);
        byte v3 = rh.readByte();
        if (skillid == 35111002) {
            rh.skip(12);
        }
        int v4 = rh.readInt(); // ?
        int v5 = rh.readInt(); // ?
        int v6 = rh.readByte(); // flag
        int v7 = rh.readInt();
        short v8 = rh.readShort(); 
        int v9 = rh.readInt();
       int v10 = rh.readInt();
        final MapleSummon summon = (MapleSummon) obj;
        final ISkill summonSkill = SkillFactory.getSkill(skillid);
        final SummonSkillEntry sse = SkillFactory.getSummonData(summon.getSkill());
//        if (sse == null) {
//            return;
//        }
        if(skillid == 400021071) {
            chr.setbb(0);
            SkillFactory.getSkill(400021071).getEffect(chr.getSkillLevel(400021071)).applyTo(chr);
        }
        final List<SummonAttackEntry> allDamage = new ArrayList<SummonAttackEntry>();
        for (int i = 0; i < targets; i++) {
            int oid = rh.readInt();
            rh.skip(36);
            List<Long> damage = new ArrayList<Long>();
            for (int j = 0; j < hits; j++) {
                damage.add(rh.readLong());
            }
            allDamage.add(new SummonAttackEntry(oid, damage));
            rh.skip(18);
        }
        map.broadcastMessage(chr, MainPacketCreator.summonAttack(summon.getOwner().getId(), summon.getObjectId(), animation, tbyte, allDamage, chr.getLevel(), false, v5), summon.getPosition());

        
        for (SummonAttackEntry attackEntry : allDamage) {
            final long toDamage = attackEntry.getTotDamage();
            final MapleMonster mob = map.getMonsterByOid(attackEntry.getMonster());
            final SkillStatEffect summonEffect = summonSkill.getEffect(summon.getSkillLevel());
            if (mob != null) {
                if (summonEffect.getMonsterStati().size() > 0) {
                    if (summonEffect.makeChanceResult()) {
                        mob.applyStatus(chr,
                                new MonsterStatusEffect(summonEffect.getMonsterStati(), summonSkill,
                                        summon.getSkillLevel(), null, false),
                                summonEffect.getDuration(), summon.getObjectId());
                    }
                }
                mob.damage(chr, toDamage, true);
                chr.checkMonsterAggro(mob);
                if (mob.isAlive()) {
                    if (chr.getAddDamage() > 0) {
                        long dam = chr.getAddDamage() * (chr.getDamageHit() + chr.getDamageHit2()) / 100;
                chr.send(UIPacket.detailShowInfo("Additional Damage: " + dam + "  ", true));
                        mob.damage(chr, dam, true);
                    }
                }
            }
        }
        switch (summon.getSkill()) {
            case 32001014: // death
            case 32100010: // Death Contract
            case 32110017: // Death Contract 2
            case 32120019: // Death Contract 3
                summon.setEndTime(System.currentTimeMillis() + 1000);
                c.getPlayer().deathCount = 1;
                c.getPlayer().getBuffedSkillEffect(BuffStats.BMageDeath).applyToBMDeath(chr);
                break;
            case 2111010:
            case 33101008:
                summon.removeSummon(map);
                break;
        }

        if ((v2 == 152110002 || v2 == 152100002 ||  v2 == 152100001 || v2 == 152110001) && !chr.skillisCooling(v2)) {
            c.getPlayer().addCooldown(v2, System.currentTimeMillis(),
                    SkillFactory.getSkill(v2).getEffect(c.getPlayer().getSkillLevel(v2)).getCooldown() * 1000);
        }

        if (hits == 2 && skillid == 1301013) { 
            c.getPlayer().addCooldown(1311014, System.currentTimeMillis(),
                    SkillFactory.getSkill(1311014).getEffect(c.getPlayer().getSkillLevel(1311014)).getCooldown() * 1000);
        }
        if (v2 == 152101006) {
            chr.saveCrystalskill = 0;
            chr.getMap().broadcastMessage(chr, MainPacketCreator.EnableCrystal(chr, chr.getCrystalOid(), 2, 1), true);
            chr.getMap().broadcastMessage(chr, MainPacketCreator.CrystalSkill(chr, chr.getCrystalOid(), 3), true);
        } 
        if (skillid == 12120013) {
            chr.FlameDischarge = 0;
            chr.getClient().sendPacket(MainPacketCreator.FlameDischarge((byte) chr.FlameDischarge, 0));
            chr.addCooldown(400021042, System.currentTimeMillis(), 20 * 1000);
            chr.getClient().sendPacket(MainPacketCreator.resetActions());
            
        }
    }

    public static final void SummonSpecialAttack(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        if (!chr.isAlive()) {
            return;
        }
        final MapleMap map = chr.getMap();
        final MapleMapObject obj = map.getMapObject(rh.readInt(), MapleMapObjectType.SUMMON);
        if (obj == null || !obj.getType().equals(MapleMapObjectType.SUMMON)) {
            return;
        }
        rh.readInt();
        final MapleSummon summon = (MapleSummon) obj;
        final ISkill summonSkill = SkillFactory.getSkill(rh.readInt());
        switch (summonSkill.getId()) {
            case 152100001:
            case 152110001:
                c.getPlayer().addCooldown(summonSkill.getId(), System.currentTimeMillis(), SkillFactory.getSkill(summonSkill.getId()).getEffect(c.getPlayer().getSkillLevel(summonSkill.getId())).getCooldown() * 1000);
                summon.removeSummon(map);
                SkillFactory.getSkill(152101000).getEffect(c.getPlayer().getSkillLevel(152101000)).applyTo(c.getPlayer(), summon.getPosition());
                break;
            
        }
        int subId = rh.readInt();
        switch (subId) {
            case 400011054: 
                if (!chr.skillisCooling(subId))
                    c.getPlayer().addCooldown(subId, System.currentTimeMillis(), SkillFactory.getSkill(subId).getEffect(c.getPlayer().getSkillLevel(subId)).getCooldown() * 1000);
                break;
            case 400021066: 
                if (!chr.skillisCooling(subId))
                    c.getPlayer().addCooldown(subId, System.currentTimeMillis(), SkillFactory.getSkill(subId).getEffect(c.getPlayer().getSkillLevel(subId)).getCooldown() * 1000);
                break;
        }
         
    }

    public static final void removeSummon(final ReadingMaple rh, final MapleClient ha) {
        int oid = rh.readInt();
        MapleSummon hs = null;
        if ((MapleSummon) ha.getPlayer().getMap().getMapObject(oid, MapleMapObjectType.SUMMON) != null) {
            hs = (MapleSummon) ha.getPlayer().getMap().getMapObject(oid, MapleMapObjectType.SUMMON);
        }
                
        if (hs.getSkill() == 400041033 || hs.getSkill() == 400021047) {
            rh.skip(1);
            
            int nSkillID = rh.readInt();
            int nSLV = rh.readInt();
            int v2 = rh.readInt();
            byte v3 = rh.readByte();
            if (v3 == 1)
                rh.readInt();
            Point pt = new Point(rh.readInt(), rh.readInt());

            int nBulletID = rh.readInt();

            rh.skip(24);
            
            int size = rh.readInt();
            List<MatrixSkill> arMatrixSkill = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                MatrixSkill ms = new MatrixSkill(rh.readInt(), rh.readInt(), rh.readInt(), rh.readShort(), new Point(rh.readShort(), rh.readShort()), rh.readInt(), rh.readByte() == 1);
                if (rh.readByte() == 1) {
                    ms.setPt2(new Point(rh.readInt(), rh.readInt()));
                }

                arMatrixSkill.add(ms);
            }
            ha.sendPacket(MatrixPacket.MatrixSkill(nSkillID, nSLV, arMatrixSkill));           
            ha.getPlayer().getMap().broadcastMessage(ha.getPlayer(), MatrixPacket.MatrixSkillMulti(ha.getPlayer().getId(), nSkillID, nSLV, pt, nBulletID, arMatrixSkill), false);
            return;
        }
        hs.removeSummon(ha.getPlayer().getMap());
        if (hs == null) {
        //    System.err.println("[Error] Failed to find the corresponding object among the summoned. : " + oid);
            return;
        }
    }

    public static final void summonSkill(final ReadingMaple rh, final MapleClient ha, final MapleCharacter chr) {
        int oid = rh.readInt();
        rh.skip(4);
        int skillid = rh.readInt();
        MapleSummon hs = null;
        if ((MapleSummon) ha.getPlayer().getMap().getMapObject(oid, MapleMapObjectType.SUMMON) != null) {
            hs = (MapleSummon) ha.getPlayer().getMap().getMapObject(oid, MapleMapObjectType.SUMMON);
        } 
        if (hs == null) {
            System.err.println("[Error] Failed to find the corresponding object among the summoned. : " + oid);
            return;
        }
        switch (skillid) {
            case 152111007: {
                chr.getMap().broadcastMessage(chr, MainPacketCreator.EnableCrystal(chr, chr.getCrystalOid(), 1, 3), true);
                SkillStatEffect eff = SkillFactory.getSkill(skillid).getEffect(skillid);
                chr.saveCrystalskill1 = 0;
                chr.getMap().broadcastMessage(chr, MainPacketCreator.EnableCrystal(chr, chr.getCrystalOid(), 2, 3), true);
                chr.getMap().broadcastMessage(chr, MainPacketCreator.CrystalSkill(chr, chr.getCrystalOid(), 3), true);
                if (eff != null) {
                    eff.applyTo(ha.getPlayer());
                }
                ha.getPlayer().setHamonyCount(0);
                break;
            }
            case 35121009: {
                if (!ha.getPlayer().canSummon(2000)) {
                    return;
                }
                if (hs.getSkill() != skillid) {
                    return;
                }
                for (int i = 0; i < 3; i++) {
                    final MapleSummon tosummon = new MapleSummon(ha.getPlayer(),
                            35121011, 3000,
                            new Point(hs.getPosition().x, hs.getPosition().y - 5), SummonMovementType.WALK_STATIONARY,
                            System.currentTimeMillis());
                    ha.getPlayer().getMap().spawnSummon(tosummon, true,
                            (int) (20 + 2 * Math.floor(hs.getSkillLevel() / 3)) * 1000);
                }
                break;
            }
            case 35121003: {
                int v1 = rh.readInt();
                byte state = rh.readByte();
                break;
            }
            case 35120002:
            case 35111011: {
                if (!ha.getPlayer().canSummon(1000)) {
                    return;
                }
                ha.getPlayer().addHP((int) (ha.getPlayer().getStat().getCurrentMaxHp()
                        * SkillFactory.getSkill(hs.getSkill()).getEffect(hs.getSkillLevel()).getHp() / 100.0));
                ha.getPlayer().getClient().getSession().writeAndFlush(MainPacketCreator.showSkillEffect(-1, chr.getLevel(),
                        hs.getSkill(), hs.getSkillLevel(), (byte) 0, 2, null, null));
                ha.getPlayer().getMap().broadcastMessage(ha.getPlayer(),
                        MainPacketCreator.showSkillEffect(ha.getPlayer().getId(), chr.getLevel(), hs.getSkill(),
                                hs.getSkillLevel(), (byte) 0, 2, null, null),
                        false);
                break;
            }
            case 1301013: {
                byte state = rh.readByte();
                int v1 = rh.readInt();
                ISkill bHealing = SkillFactory.getSkill(1301013);
                final int bHealingLvl = 1;
                final SkillStatEffect healEffect = bHealing.getEffect(bHealingLvl);
                chr.addHP(healEffect.getHp());
                chr.getMap().broadcastMessage(MainPacketCreator.summonSkill(chr.getId(), oid, state, v1));
                break;
            }
            case 1310016: {
                // A3 86 01 00 40 FD 13 00 92 1C 6C 69 35 00
                // rh.readByte();
                int v1 = rh.readInt();
                byte state = rh.readByte();
                if (!chr.isActiveBuffedValue(skillid)) {
                    // chr.getMap().broadcastMessage(MainPacketCreator.summonSkill(chr.getId(), oid,
                    // state, 0));
                    chr.getClient().sendPacket(MainPacketCreator.showSkillEffect(-1, 4, skillid, 1));
                    SkillFactory.getSkill(skillid).getEffect(chr.getSkillLevel(skillid)).applyTo(chr);
                }
                break;
            }
            case 400041038: {
                    final List<MapleMapObject> objs = chr.getMap().getMapObjectsInRange(chr.getTruePosition(), 800000, Arrays.asList(MapleMapObjectType.MONSTER));
                    final List<MapleMonster> monsters = new ArrayList<>();
                    for (MapleMapObject o : objs) {
                        monsters.add((MapleMonster) o);
                        if (monsters.size() == 7)
                            break;
                    }
                    int count = 7;
                    count += (monsters.size() * 5);
                    Point pos1 = new Point(hs.getPosition().x, hs.getPosition().y - 250);
                    if (objs.size() > 0)
                        chr.getMap().broadcastMessage(MainPacketCreator.absorbingMarkOfTheif(chr.getId(), monsters.get(0).getObjectId(), 4120019, monsters, pos1, chr.getKeyValue2("ǥâ"), count));
                }
                break;
            default: {
                if (GameConstants.isAngelicBlessBuff(skillid)) {
                    ItemInformation ii = ItemInformation.getInstance();
                    int effectid = 0;
                    switch (skillid % 1000) {
                        case 86: // Angelic bless
                            ii.getItemEffect(2022746).applyTo(ha.getPlayer());
                            effectid = 1085;
                            break;
                        case 88: // Dark Angel Bless
                            ii.getItemEffect(2022747).applyTo(ha.getPlayer());
                            effectid = 1087;
                            break;
                        case 91: // Snowflake Angelic Blessed
                            ii.getItemEffect(2022764).applyTo(ha.getPlayer());
                            effectid = 1090;
                            break;
                        case 180:// White Angel Bless
                            ii.getItemEffect(2022823).applyTo(ha.getPlayer());
                            effectid = 1179;
                            break;
                    }
                    ha.getPlayer().getMap()
                            .broadcastMessage(MainPacketCreator.showSkillEffect(-1, skillid, chr.getSkillLevel(skillid)));
                    ha.getPlayer().getMap().broadcastMessage(
                            MainPacketCreator.showSkillEffect(ha.getPlayer().getId(), skillid, chr.getSkillLevel(skillid)));
                    break;
                }
            }
        }
    }

    public static void AtomAttack(ReadingMaple slea, MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final int oid = slea.readInt();

        MapleSummon summon = (MapleSummon) chr.getMap().getMapObject(oid, MapleMapObjectType.SUMMON);

        if (summon == null) {
            return;
        }

        slea.readByte();

        slea.readInt(); // skillId
        slea.readInt();
        slea.readInt();
        slea.readInt();
        slea.readInt();
        slea.readPos();

        slea.readInt();
        slea.readInt();
        slea.readInt();
        slea.readInt();
        slea.readInt();
        slea.readByte();

        int count = slea.readInt();
        for (int i = 0; i < count; i++) {
            slea.readInt(); // skillId
            slea.readByte();
            slea.readInt();
            slea.readShort();
            slea.readPos();
            slea.readInt();
            slea.readByte();

            if (slea.readByte() == 1) {
                slea.readInt();
                slea.readInt();
            }
        }
    }
}
