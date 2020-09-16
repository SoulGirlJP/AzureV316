package server.LifeEntity.MobEntity.MonsterEntity;

import java.awt.Rectangle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;

import client.Character.MapleCharacter;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.IItem;
import client.ItemInventory.Item;
import client.ItemInventory.MapleInventoryType;
import client.MapleClient;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import client.Stats.BuffStats;
import client.Stats.DiseaseStats;
import client.Stats.MonsterStatus;
import client.Stats.MonsterStatusEffect;
import constants.GameConstants;
import connections.Packets.MainPacketCreator;
import connections.Packets.MobPacket;
import connections.Packets.UIPacket;
import launcher.ServerPortInitialize.ChannelServer;
import scripting.EventManager.EventInstanceManager;
import server.Items.ItemInformation;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Movement.LifeMovementFragment;
import server.LifeEntity.MobEntity.AbstractLoadedMapleLife;
import server.LifeEntity.MobEntity.ChangeableStats;
import server.LifeEntity.MobEntity.Element;
import server.LifeEntity.MobEntity.ElementalEffectiveness;
import static server.LifeEntity.MobEntity.ElementalEffectiveness.IMMUNE;
import static server.LifeEntity.MobEntity.ElementalEffectiveness.STRONG;
import static server.LifeEntity.MobEntity.ElementalEffectiveness.WEAK;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MobSkill;
import tools.Pair;
import tools.RandomStream.Randomizer;

public class MapleMonster extends AbstractLoadedMapleLife {

    private MapleMonsterStats stats;
    private OverrideMonsterStats ostats = null;
    private int mp;
    private long hp, finalmaxhp;
    private boolean bossreal;
    private byte venom_counter;
    private MapleMap map;
    private MapleMonster sponge;
    private int stolen = -1;
    private int linkoid = 0, lastNode = -1, linkCID = 0;
    private MapleCharacter highestDamageChar = null;
    private WeakReference<MapleCharacter> controller = new WeakReference<MapleCharacter>(null);
    private boolean fake, dropsDisabled, controllerHasAggro, controllerKnowsAboutAggro, statChanged;
    private final Collection<AttackerEntry> attackers = new LinkedList<AttackerEntry>();
    private EventInstanceManager eventInstance;
    private MonsterListener listener = null;
    private final Map<MonsterStatus, MonsterStatusEffect> stati = new LinkedHashMap<MonsterStatus, MonsterStatusEffect>();
    private Map<Pair<Integer, Integer>, Long> usedSkills;
    private List<Integer> reflections = new LinkedList<Integer>();
    private int elitetype = 0x70;
    private boolean elitemonster;
    private boolean eliteboss;
    private int EliteHP;
    private int EliteMHP;
    private int EBID;
    private int bossnew;
    private transient ScheduledFuture<?> PoisonTime = null;
    private long KinesisUltimateDeep = -1;
    private int scale = 100;
    private int freezeStack = 0;
    private long freezeStackTime = -1;
    private int nextSkill = 0;
    private int nextSkillLevel = 0;
    private long lastAttackTime = 0;

    private byte[] reflectpack = null, nodepack = null;

    public MapleMonster(final int id, final MapleMonsterStats stats) {
        super(id);
        initWithStats(stats);
    }

    public MapleMonster(final MapleMonster monster) {
        super(monster);
        initWithStats(monster.stats);
    }

    private final void initWithStats(final MapleMonsterStats stats) {
        setStance(5);
        this.stats = stats;
        hp = stats.getHp();
        finalmaxhp = stats.getFinalMaxHP();
        mp = stats.getMp();
        venom_counter = 0;
        fake = false;
        dropsDisabled = false;

        if (stats.getNoSkills() > 0) {
            usedSkills = new HashMap<Pair<Integer, Integer>, Long>();
        }
    }

    public final MapleMonsterStats getStats() {
        return stats;
    }

    public final void disableDrops() {
        this.dropsDisabled = true;
    }

    public final boolean dropsDisabled() {
        return dropsDisabled;

    }

    public final void setStatChanged(boolean d) {
        this.statChanged = d;
    }

    public final boolean isStatChanged() {
        return statChanged;
    }

    public final void setSponge(final MapleMonster mob) {
        sponge = mob;
    }

    public byte[] getNodePacket() {
        return nodepack;
    }

    public void setNodePacket(final byte[] np) {
        this.nodepack = np;
    }

    public final void setMap(final MapleMap map) {
        this.map = map;
    }

    public final long getHp() {
        return hp;
    }

    public final void setHp(long hp) {
        this.hp = hp;
    }

    public final long getMobMaxHp() {
        if (ostats != null) {
            return ostats.getHp();
        }
        return stats.getHp();
    }

    public final boolean isFinalBoss() {
        return finalmaxhp > 0;
    }

    public final long getFinalMaxHP() {
        if (ostats != null) {
            return ostats.getHp();
        }
        return finalmaxhp;
    }

    public final void setFinalMaxHP(long fmhp) {
        this.finalmaxhp = fmhp;
    }

    public final long getMobFinalMaxHP() {
        if (ostats != null) {
            return ostats.getHp();
        }
        return stats.getFinalMaxHP();
    }

    public final int getMp() {
        return mp;
    }

    public final void setMp(int mp) {
        if (mp < 0) {
            mp = 0;
        }
        this.mp = mp;
    }

    public final int getMobMaxMp() {
        if (ostats != null) {
            return ostats.getMp();
        }
        return stats.getMp();
    }

    public final int getMobExp() {
        if (ostats != null) {
            return ostats.getExp();
        }
        return stats.getExp();
    }

    public final void setOverrideStats(final OverrideMonsterStats ostats) {
        this.ostats = ostats;
        this.hp = ostats.getHp();
        this.mp = ostats.getMp();
    }

    public final MapleMonster getSponge() {
        return sponge;
    }

    public final boolean isCancelFreezeStack(long time) {
        if (freezeStackTime == -1) {
            return false;
        }
        return time >= freezeStackTime;
    }

    public final void setCacnelFreezeStack(int time) {
        this.freezeStackTime = System.currentTimeMillis() + time;
    }

    public final void setFreezeStack(int stack) {
        if (this.freezeStack == 5 && stack != 0) {
            return;
        }
        this.freezeStack = stack;
    }

    public final int getFreezeStack() {
        return this.freezeStack;
    }

    public final byte getVenomMulti() {
        return venom_counter;
    }

    public final void setVenomMulti(final byte venom_counter) {
        this.venom_counter = venom_counter;
    }

    public final void damage(final MapleCharacter from, final long damage, final boolean updateAttackTime) {
        AttackerEntry attacker = null;

        if (from.getParty() != null) {
            attacker = new PartyAttackerEntry(from.getParty().getId(), from.getClient().getChannelServer());
        } else {
            attacker = new SingleAttackerEntry(from, from.getClient().getChannelServer());
        }

        boolean replaced = false;
        for (final AttackerEntry aentry : attackers) {
            if (aentry.equals(attacker)) {
                attacker = aentry;
                replaced = true;
                break;
            }
        }
        if (!replaced) {
            attackers.add(attacker);
        }
        final long rDamage = Math.max(0, Math.min(damage, hp));
        attacker.addDamage(from, rDamage, updateAttackTime);

        if (from.getMapId() == 120000102) {
            from.addDamageMeter(rDamage);
            from.send(MainPacketCreator.RemovePopupSay());
            from.send(MainPacketCreator.OnAddPopupSay(9000036, 3000, "누적 데미지 #e[#n#r" + Randomizer.Comma(from.getDamageMeter()) + "#k#e]", ""));
        }

        if (finalmaxhp > 0) {
            if (hp > 0) {
                hp -= rDamage;
                switch (stats.getHPDisplayType()) {
                    case 0:
                        map.broadcastMessage(MobPacket.showBossHP(this), this.getPosition());
                        break;
                    case 1:
                        map.broadcastMessage(MobPacket.damageFriendlyMob(this, damage), this.getPosition());
                        break;
                    case 2:
                        map.broadcastMessage(MobPacket.showMonsterHP(getObjectId(), (int) Math.ceil((hp * 100.0) / getMobMaxHp())));
                        break;
                    case 3:
                        for (final AttackerEntry mattacker : attackers) {
                            for (final AttackingMapleCharacter cattacker : mattacker.getAttackers()) {
                                if (cattacker.getAttacker().getMap() == from.getMap()) { // current attacker is on the map of the monster
                                    if (cattacker.getLastAttackTime() >= System.currentTimeMillis() - 4000) {
                                        cattacker.getAttacker().getClient().getSession().write(MobPacket.showMonsterHP(getObjectId(), (int) Math.ceil((hp * 100.0) / getMobMaxHp())));
                                    }
                                }
                            }
                        }
                        break;
                }
            } else if (finalmaxhp > 0) {
                finalmaxhp -= damage;
                if (finalmaxhp > 0) {
                    map.broadcastMessage(UIPacket.clearMidMsg());
                    map.broadcastMessage(UIPacket.greenShowInfo("<" + stats.getName() + ">  final HP : " + finalmaxhp));
                } else {
                    map.broadcastMessage(UIPacket.clearMidMsg());
                    map.broadcastMessage(UIPacket.greenShowInfo("<" + stats.getName() + "> final HP : 0"));
                    map.killMonster(this, from, true, false, stats.getSelfD());
                }
            }
            return;
        } else if (stats.getSelfD() != -1) {
            hp -= rDamage;
            if (hp > 0) {
                if (hp < stats.getSelfDHp()) { // HP is Maple the selfd level
                    map.killMonster(this, from, false, false, stats.getSelfD());
                } else // Show HP
                {
                    if (stats.isInvincible()) {
                        for (final AttackerEntry mattacker : attackers) {
                            for (final AttackingMapleCharacter cattacker : mattacker.getAttackers()) {
                                if (cattacker.getAttacker().getMap() == from.getMap()) { // current attacker is on the map of the monster
                                    if (cattacker.getLastAttackTime() >= System.currentTimeMillis() - 4000) {
                                        cattacker.getAttacker().getClient().getSession().write(MobPacket.showMonsterHP(getObjectId(), (int) Math.ceil((hp * 100.0) / getMobMaxHp())));
                                    }
                                }
                            }
                        }
                    }
                }
            } else { // Character killed it without explosing :(
                map.killMonster(this, from, true, false, (byte) 1);

            }
        } else {
            if (sponge != null) {
                if (sponge.hp > 0) { // If it's still alive, dont want double/triple rewards
//                     Sponge are always in the same map, so we can use this.map
//                     The only mob that uses sponge are PB/HT
                    sponge.hp -= rDamage;

                    if (sponge.hp <= 0) {
                        map.killMonster(sponge, from, true, false, (byte) 1);

                    } else {
                        map.broadcastMessage(MobPacket.showBossHP(sponge));
                    }
                }
            }
            if (hp > 0) {
                hp -= rDamage;
                switch (stats.getHPDisplayType()) {
                    case 0:
                        map.broadcastMessage(MobPacket.showBossHP(this), this.getPosition());
                        break;
                    case 1:
                        map.broadcastMessage(MobPacket.damageFriendlyMob(this, damage), this.getPosition());
                        break;
                    case 2:
                        map.broadcastMessage(MobPacket.showMonsterHP(getObjectId(), (int) Math.ceil((hp * 100.0) / getMobMaxHp())));
                        break;
                    case 3:
                        for (final AttackerEntry mattacker : attackers) {
                            for (final AttackingMapleCharacter cattacker : mattacker.getAttackers()) {
                                if (cattacker.getAttacker().getMap() == from.getMap()) { // current attacker is on the map of the monster
                                    if (cattacker.getLastAttackTime() >= System.currentTimeMillis() - 4000) {
                                        cattacker.getAttacker().getClient().getSession().write(MobPacket.showMonsterHP(getObjectId(), (int) Math.ceil((hp * 100.0) / getMobMaxHp())));
                                    }
                                }
                            }
                        }
                        break;
                }
                if (hp <= 0 && !isFinalBoss()) {
                    if (from.lastSkill == 400011027) {
                        from.getClient().getSession().writeAndFlush(MainPacketCreator.deathEffect(getObjectId(), 400011027, from.getId())); 
                    }
                    map.killMonster(this, from, true, false, (byte) 1);
                }
            }
        }
    }


    public final void changeableMob(MapleCharacter chr) {
        if (getStats().isChangeableMob()) {
            double rate = chr.getLevel() / (double) getStats().getLevel();
            if (chr.getLevel() >= 150) {
                rate = rate * rate;
            }
            getStats().setHp((int) (getStats().getHp() * rate));
            setHp((int) (getHp() * rate));
            getStats().setMp((int) (getStats().getMp() * rate));
            setMp((int) (getMp() * rate));
            getStats().setPad((int) (getStats().getPad() * rate));
            getStats().setMad((int) (getStats().getMad() * rate));
            getStats().setPhysicalDefense((short) (getStats().getPhysicalDefense() * rate));
            getStats().setMagicDefense((short) (getStats().getMagicDefense() * rate));
            getStats().setAcc((int) (getStats().getAcc() * rate));
            getStats().setEva((short) (getStats().getEva() + chr.getLevel() / 2));
            getStats().setPushed((int) (getStats().getPushed() * rate));
            getStats().setLevel(chr.getLevel());
            setStatChanged(true);
        }
    }

    public final void heal(final long hp, final int mp, final boolean broadcast) {
        final long TotalHP = getHp() + hp;
        final int TotalMP = getMp() + mp;

        if (TotalHP >= getMobMaxHp()) {
            setHp(getMobMaxHp());
        } else {
            setHp(TotalHP);
        }
        if (TotalMP >= getMp()) {
            setMp(getMp());
        } else {
            setMp(TotalMP);
        }
        if (broadcast) {
            map.broadcastMessage(MobPacket.healMonster(getObjectId(), (int) hp));
        } else if (sponge != null) {
            sponge.hp += hp;
        }
    }

    private final void giveExpToCharacter(final MapleCharacter attacker, int exp, final boolean highestDamage, final int numExpSharers, final byte pty, final byte Class_Bonus_EXP_PERCENT) {
        if (highestDamage) {
            if (eventInstance != null) {
                eventInstance.monsterKilled(attacker, this);
            } else {
                final EventInstanceManager em = attacker.getEventInstance();
                if (em != null) {
                    em.monsterKilled(attacker, this);
                }
            }
            highestDamageChar = attacker;

            if (isEliteMonster()) {
                exp = ((((attacker.getLevel()) - (attacker.getLevel() % 10)) / 10) * 3) + 3;
            }
        }
         if (exp > 0) { // Exp Rate?
            // Experience level || exp rate || exp
             //EXP formulae Azure316 pre-adjustment
            if (attacker.getLevel() < 30) {

                 exp *= 2 * (1+ (attacker.getLevel()/10));
             }
             else if (attacker.getLevel() < 900) {
                 exp *= 5 * ((attacker.getLevel()/10) - 1);
             }
             else {
                 exp *= 455;
             }
            
            int originExp = exp;
            final MonsterStatusEffect ms = stati.get(MonsterStatus.SHOWDOWN);
            if (ms != null) {
                exp += (int) (originExp * (ms.getStati().get(MonsterStatus.SHOWDOWN) / 100.0D));
            }
            final Integer holySymbol = attacker.getBuffedValue(BuffStats.HolySymbol);
            if (holySymbol != null) {
                exp += (int) (originExp * (holySymbol.doubleValue() / 100.0D));
            }
            int Class_Bonus_EXP = 0;
            if (Class_Bonus_EXP_PERCENT > 0) {
                Class_Bonus_EXP = (int) ((float) (originExp / 100) * Class_Bonus_EXP_PERCENT);
            }
            if (attacker.hasDisease(DiseaseStats.CURSE)) {
                exp /= 2;
            }
            attacker.gainExpMonster(exp, true, highestDamage, pty, Class_Bonus_EXP);

            if (attacker.getBuffedSkillEffect(BuffStats.IndieEXP) != null) {
                attacker.gainExpMonster(exp, true, highestDamage, pty, 0);
            }

        }
        attacker.mobKilled(getId());
    }

    public final MapleCharacter killBy(final MapleCharacter killer) {
        int cash = 0;
        int totalBaseExp = (int) (Math.min(Integer.MAX_VALUE, (getMobExp())));
        AttackerEntry highest = null;
        long highdamage = 0;

        if (Randomizer.nextInt(100) <= 2) {
            if (killer.getLevel() - getStats().getLevel() <= 5) { // 몬스터레벨 보다 5이상 높으면드롭안됨
                cash = Randomizer.rand(50, 150);
            } else if (killer.getLevel() - getStats().getLevel() <= -20) { // 몬스터레벨 보다 20이상 낮으면 더높게
                cash = Randomizer.rand(60, 200);
            }
        }

        for (final AttackerEntry attackEntry : attackers) {
            if (attackEntry.getDamage() > highdamage) {
                highest = attackEntry;
                highdamage = attackEntry.getDamage();
            }
        }
        int baseExp;
        for (final AttackerEntry attackEntry : attackers) {
            baseExp = (int) Math.ceil(totalBaseExp * ((double) attackEntry.getDamage() / getMobMaxHp()));
            attackEntry.killedMob(killer.getMap(), baseExp, attackEntry == highest);
        }
        final MapleCharacter controll = controller.get();
        if (controll != null) {
            controll.getClient().getSession().writeAndFlush(MobPacket.stopControllingMonster(getObjectId(), GameConstants.isAswanMap(killer.getMapId())));
            controll.stopControllingMonster(this);
        }

        spawnRevives(killer.getMap());
        if (eventInstance != null) {
            eventInstance.unregisterMonster(this);
            eventInstance = null;
        }
        sponge = null;

        if (listener != null) {
            listener.monsterKilled();
        }

        final MapleCharacter ret = highestDamageChar;
        highestDamageChar = null;
        return ret;
    }

    public int getLinkOid() {
        return linkoid;
    }

    public void setLinkOid(int lo) {
        this.linkoid = lo;
    }

    public final void spawnRevives(final MapleMap map) {
        final List<Integer> toSpawn = stats.getRevives();

        if (toSpawn == null) {
            return;
        }
        MapleMonster spongy = null;
        switch (getId()) {
            case 8820002:
            case 8820003:
            case 8820004:
            case 8820005:
            case 8820006:
            case 8820102:
            case 8820103:
            case 8820104:
            case 8820105:
            case 8820106:
            case 8840000:
            case 6160003:
            case 8850011:
                break;
            case 8810118:
            case 8810119:
            case 8810120:
            case 8810121:
                for (final int i : toSpawn) {
                    final MapleMonster mob = MapleLifeProvider.getMonster(i);

                    mob.setPosition(getPosition());
                    if (eventInstance != null) {
                        eventInstance.registerMonster(mob);
                    }
                    if (dropsDisabled()) {
                        mob.disableDrops();
                    }
                    switch (mob.getId()) {
                        case 8810119:
                        case 8810120:
                        case 8810121:
                        case 8810122:
                            spongy = mob;
                            break;
                    }
                }
                if (spongy != null && map.getMonsterById(spongy.getId()) == null) {
                    map.spawnMonster(spongy, -2);
                    for (MapleMapObject mon : map.getAllMonster()) {
                        MapleMonster mons = (MapleMonster) mon;
                        if (mons.getObjectId() != spongy.getObjectId()
                                && (mons.getSponge() == this || mons.getLinkOid() == this.getObjectId())) {
                            mons.setSponge(spongy);
                        }
                    }
                }
                break;
            case 8810026:
            case 8810130:
            case 8820008:
            case 8820009:
            case 8820010:
            case 8820011:
            case 8820012:
            case 8820013:
            case 8820108:
            case 8820109:
            case 8820110:
            case 8820111:
            case 8820112:
            case 8820113: {
                final List<MapleMonster> mobs = new ArrayList<MapleMonster>();

                for (final int i : toSpawn) {
                    final MapleMonster mob = MapleLifeProvider.getMonster(i);

                    mob.setPosition(getPosition());
                    if (eventInstance != null) {
                        eventInstance.registerMonster(mob);
                    }
                    if (dropsDisabled()) {
                        mob.disableDrops();
                    }
                    switch (mob.getId()) {
                        case 8810018: // Horntail Sponge
                        case 8810118:
                        case 8820009: // PinkBeanSponge0
                        case 8820010: // PinkBeanSponge1
                        case 8820011: // PinkBeanSponge2
                        case 8820012: // PinkBeanSponge3
                        case 8820013: // PinkBeanSponge4
                        case 8820014: // PinkBeanSponge5
                        case 8820109: // PinkBeanSponge0
                        case 8820110: // PinkBeanSponge1
                        case 8820111: // PinkBeanSponge2
                        case 8820112: // PinkBeanSponge3
                        case 8820113: // PinkBeanSponge4
                        case 8820114: // PinkBeanSponge5
                            spongy = mob;
                            break;
                        default:
                            mobs.add(mob);
                            break;
                    }
                }
                if (spongy != null && map.getMonsterById(spongy.getId()) == null) {
                    map.spawnMonster(spongy, -2);

                    for (final MapleMonster i : mobs) {
                        map.spawnMonster(i, -2);
                        i.setSponge(spongy);
                    }
                }
                break;
            }
            case 8820014:
            case 8820114: {
                for (final int i : toSpawn) {
                    final MapleMonster mob = MapleLifeProvider.getMonster(i);

                    if (eventInstance != null) {
                        eventInstance.registerMonster(mob);
                    }
                    mob.setPosition(getPosition());
                    if (dropsDisabled()) {
                        mob.disableDrops();
                    }
                    map.spawnMonster(mob, -2);
                }
                break;
            }
            default: {
                for (final int i : toSpawn) {
                    final MapleMonster mob = MapleLifeProvider.getMonster(i);

                    if (eventInstance != null) {
                        eventInstance.registerMonster(mob);
                    }
                    mob.setPosition(getPosition());
                    if (dropsDisabled()) {
                        mob.disableDrops();
                    }
                    map.spawnRevives(mob, this.getObjectId());

                    if (mob.getId() == 9300216) {
                        map.broadcastMessage(MainPacketCreator.environmentChange("Dojang/clear", 5));
                        map.broadcastMessage(MainPacketCreator.environmentChange("dojang/end/clear", 12));
                    }
                }
                break;
            }
        }
    }

    public final boolean isAlive() {
        return hp > 0 || finalmaxhp > 0;
    }

    public final MapleCharacter getController() {
        return controller.get();
    }

    public final void setController(final MapleCharacter controller) {
        this.controller = new WeakReference<MapleCharacter>(controller);
    }

    public final void switchController(final MapleCharacter newController, final boolean immediateAggro) {
        final MapleCharacter controllers = getController();
        if ((controllers == newController) && (!this.elitemonster)) {
            return;
        } else if ((controllers != null) && (!this.elitemonster)) {
            controllers.stopControllingMonster(this);
            controllers.getClient().getSession().writeAndFlush(MobPacket.stopControllingMonster(getObjectId(),
                    GameConstants.isAswanMap(newController.getMapId())));
        }
        if (this.isAlive()) {
            newController.controlMonster(this, getId() != 8220028 ? immediateAggro : false);
        }
        setController(newController);
        if (immediateAggro && getId() != 8220028) {
            setControllerHasAggro(true);
        }
        setControllerKnowsAboutAggro(false);
    }

    public final void addListener(final MonsterListener listener) {
        this.listener = listener;
    }

    public final boolean isControllerHasAggro() {
        return controllerHasAggro;
    }

    public final void setControllerHasAggro(final boolean controllerHasAggro) {
        this.controllerHasAggro = controllerHasAggro;
    }

    public final boolean isControllerKnowsAboutAggro() {
        return controllerKnowsAboutAggro;
    }

    public final void setControllerKnowsAboutAggro(final boolean controllerKnowsAboutAggro) {
        this.controllerKnowsAboutAggro = controllerKnowsAboutAggro;
    }

    @Override
    public final void sendSpawnData(final MapleClient client) {
        if (!isAlive()) {
            return;
        }
        if (client == null) {
            return;
        }
        if (client.getPlayer() == null) {
            return;
        }
        if (this == null) {
            return;
        }
        client.getSession().writeAndFlush(MobPacket.spawnMonster(this, fake ? -4 : -2, 0, 0,
                GameConstants.isAswanMap(client.getPlayer().getMapId())));

    }

    @Override
    public final void sendDestroyData(final MapleClient client) {
        client.getSession().writeAndFlush(
                MobPacket.killMonster(getObjectId(), 0, GameConstants.isAswanMap(client.getPlayer().getMapId())));
    }

    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(stats.getName());
        sb.append("(");
        sb.append(getId());
        sb.append(") HP: ");
        sb.append(stats.getFinalMaxHP() > 0 ? finalmaxhp : getHp());
        sb.append("/ ");
        sb.append(stats.getFinalMaxHP() > 0 ? stats.getFinalMaxHP() : getMobMaxHp());
        sb.append(",MP : ");
        sb.append(getMp());
        sb.append("/ ");
        sb.append(getMobMaxMp());
        sb.append(" (Alive: ");
        sb.append(isAlive());
        sb.append(" Object ID: ");
        sb.append(getObjectId());
        sb.append(") || Controller Name : ");
        final MapleCharacter chr = controller.get();
        sb.append(chr != null ? chr.getName() : "None");
        sb.append(" FinalMaxHP Final Boss: ");
        sb.append(isFinalBoss() ? "True" : "False");

        return sb.toString();
    }

    public final void changeLevel(final int newLevel) {
        changeLevel(newLevel, true);
    }

    public final void changeLevel(final int newLevel, boolean pqMob) {
        if (!stats.isChangeableMob()) {
            return;
        }
        this.ostats = new ChangeableStats(stats, newLevel, pqMob);
        this.hp = ostats.getHp();
        this.mp = ostats.getMp();
    }

    @Override
    public final MapleMapObjectType getType() {
        return MapleMapObjectType.MONSTER;
    }

    public final EventInstanceManager getEventInstance() {
        return eventInstance;
    }

    public final void setEventInstance(final EventInstanceManager eventInstance) {
        this.eventInstance = eventInstance;
    }

    public final int getStatusSourceID(final MonsterStatus status) {
        final MonsterStatusEffect effect = stati.get(status);
        if (effect != null) {
            return effect.getSkill().getId();
        }
        return -1;
    }

    public final ElementalEffectiveness getEffectiveness(final Element e) {
        if (stati.size() > 0) {
            return ElementalEffectiveness.NORMAL;
        }
        return stats.getEffectiveness(e);
    }

    public final void applyStatus(final MapleCharacter from, final MonsterStatusEffect status, final long duration) {
        applyStatus(from, status, duration, -1);
    }

    public final void applyStatus(final MapleCharacter from, final MonsterStatusEffect status, final long duration,
            final int oid) {
        long time = System.currentTimeMillis();
        long endTime = time + duration;
        if (!isAlive()) {
            return;
        }
        switch (stats.getEffectiveness(status.getSkill().getElement())) {
            case IMMUNE:
            case STRONG:
                return;
            case NORMAL:
            case WEAK:
                break;
            default:
                return;
        }
        final int statusSkill = status.getSkill().getId();
        switch (statusSkill) {
            case 2111006: {
                switch (stats.getEffectiveness(Element.POISON)) {
                    case IMMUNE:
                    case STRONG:
                        return;
                }
                break;
            }
            case 2211006: { // IL compo
                switch (stats.getEffectiveness(Element.ICE)) {
                    case IMMUNE:
                    case STRONG:
                        return;
                }
                break;
            }
            case 4110011:
            case 4120005:
            case 4220005:
            case 14110004: {
                switch (stats.getEffectiveness(Element.POISON)) {
                    case WEAK:
                        return;
                }
                break;
            }
        }
        final Map<MonsterStatus, Integer> statis = status.getStati();
        if (stats.isBoss()) {
            if (!(statis.containsKey(MonsterStatus.SPEED) && statis.containsKey(MonsterStatus.WATK))) {
                if (!statis.containsKey(MonsterStatus.POISON)) {
                    return;
                }
            }
        }
        for (MonsterStatus stat : statis.keySet()) {
            final MonsterStatusEffect oldEffect = stati.get(stat);
            if (oldEffect != null) {
                oldEffect.removeActiveStatus(stat);
                if (oldEffect.getStati().size() == 0) {
                    oldEffect.CancelEffect();
                }
            }
        }
        final Runnable cancelTask = new Runnable() {
            @Override
            public final void run() {
                if (isAlive()) {
                    try {
                        map.broadcastMessage(MobPacket.cancelMonsterStatus(
                                getController() == null ? 0 : getController().getId(), getObjectId(), status, statis));
                    } catch (Exception e) {

                    }
                    for (final MonsterStatus stat : statis.keySet()) {
                        stati.remove(stat);
                    }
                    setVenomMulti((byte) 0);
                }
            }
        };
        int poisonDamage = 0;
        if (statis.containsKey(MonsterStatus.POISON) || statis.containsKey(MonsterStatus.Burned)) {
            poisonDamage = status.getSkill().getEffect(status.getSkillLevel()).dotDamage(from.getStat().getMinAttack());
        }
        if (statis.containsKey(MonsterStatus.POISON)) {
            statis.replace(MonsterStatus.POISON, statis.get(MonsterStatus.POISON), poisonDamage);
        } else if (statis.containsKey(MonsterStatus.Burned)) {
            statis.replace(MonsterStatus.Burned, statis.get(MonsterStatus.Burned), poisonDamage);
        }
        if (poisonDamage > 0) {
            status.setV(1, Randomizer.nextInt());
            status.setV(2, Randomizer.nextInt());
            status.setPoison(new PoisonTask(poisonDamage, from, status.getSkill().getId(),
                    status.getSkill().getEffect(status.getSkillLevel()), time));
        }
        status.setEndTime(endTime);
        status.setCancelEffect(cancelTask);
        for (final MonsterStatus stat : statis.keySet()) {
            stati.put(stat, status);
        }
        for (Entry<MonsterStatus, Integer> e : status.getStati().entrySet()) {
            Map<MonsterStatus, Integer> p = Collections.singletonMap(e.getKey(), e.getValue());
            MonsterStatusEffect eff = new MonsterStatusEffect(p, status.getSkill(), status.getSkillLevel(),
                    status.getMobSkill(), status.isMonsterSkill());
            eff.setOwnerId(from.getId());
            map.broadcastMessage(MobPacket.applyMonsterStatus(getObjectId(), eff), getPosition());
        }
        if (getController() != null && !getController().isMapObjectVisible(this)) {
            for (MonsterStatusEffect mse : stati.values()) {
                for (Entry<MonsterStatus, Integer> e : mse.getStati().entrySet()) {
                    Map<MonsterStatus, Integer> p = Collections.singletonMap(e.getKey(), e.getValue());
                    MonsterStatusEffect eff = new MonsterStatusEffect(p, status.getSkill(), status.getSkillLevel(),
                            status.getMobSkill(), status.isMonsterSkill());
                    getController().getClient().getSession().writeAndFlush(MobPacket.applyMonsterStatus(getObjectId(), eff));
                }
            }
        }
    }

    public Map<MonsterStatus, MonsterStatusEffect> getStati() {
        return this.stati;
    }

    public final void cancelStatus(final MonsterStatus stat) {
        final MonsterStatusEffect mse = stati.get(stat);
        if (mse == null || !isAlive()) {
            return;
        }
        final MapleCharacter con = getController();
        if (con != null) {
            map.broadcastMessage(con,
                    MobPacket.cancelMonsterStatus(getController().getId(), getObjectId(), mse, mse.getStati()),
                    getPosition());
            con.getClient().getSession().writeAndFlush(
                    MobPacket.cancelMonsterStatus(getController().getId(), getObjectId(), mse, mse.getStati()));
        } else {
            map.broadcastMessage(
                    MobPacket.cancelMonsterStatus(getController().getId(), getObjectId(), mse, mse.getStati()));
        }
        stati.remove(stat);
    }

    public final void cancelSingleStatus(final MonsterStatusEffect stat) {
        if (stat == null || !isAlive()) {
            return;
        }
        for (Object ms : stat.getStati().keySet()) {
            cancelStatus((MonsterStatus) ms);
        }
        return;
    }

    public final void applyMonsterBuff(final Map<MonsterStatus, Integer> stats, final int x, final int skillId,
            final long duration, final MobSkill skill, final List<Integer> reflection) {
        final MonsterStatusEffect effect = new MonsterStatusEffect(stats, null, 0, skill, true);
        final long endTime = System.currentTimeMillis() + duration;
        final Runnable cancelTask = new Runnable() {
            @Override
            public final void run() {
                if (isAlive()) {
                    try {
                        map.broadcastMessage(
                                MobPacket.cancelMonsterStatus(getController() == null ? 0 : getController().getId(),
                                        getObjectId(), effect, stats),
                                getPosition());
                    } catch (Exception ex) {

                    }
                    if (getController() != null && !getController().isMapObjectVisible(MapleMonster.this)) {
                        getController().getClient().getSession().writeAndFlush(
                                MobPacket.cancelMonsterStatus(getController().getId(), getObjectId(), effect, stats));
                    }
                    for (final MonsterStatus stat : stats.keySet()) {
                        stati.remove(stat);
                    }
                    reflections.clear();
                }
            }
        };
        effect.setCancelEffect(cancelTask);
        effect.setEndTime(endTime);
        for (final MonsterStatus stat : stats.keySet()) {
            stati.put(stat, effect);
        }
        if (reflection.size() > 0) {
            if (getController() != null && !getController().isMapObjectVisible(this)) {
                for (Entry<MonsterStatus, Integer> e : effect.getStati().entrySet()) {
                    Map<MonsterStatus, Integer> p = Collections.singletonMap(e.getKey(), e.getValue());
                    MonsterStatusEffect eff = new MonsterStatusEffect(p, effect.getSkill(), 0, effect.getMobSkill(),
                            effect.isMonsterSkill());
                    getController().getClient().getSession()
                            .writeAndFlush(MobPacket.applyMonsterStatus(getObjectId(), eff, reflection));
                }
            }
            reflections = reflection;
        } else {
            for (Entry<MonsterStatus, Integer> e : effect.getStati().entrySet()) {
                Map<MonsterStatus, Integer> p = Collections.singletonMap(e.getKey(), e.getValue());
                MonsterStatusEffect eff = new MonsterStatusEffect(p, effect.getSkill(), 0, effect.getMobSkill(),
                        effect.isMonsterSkill());
                map.broadcastMessage(MobPacket.applyMonsterStatus(getObjectId(), eff), getPosition());
            }
            if (getController() != null && !getController().isMapObjectVisible(this)) {
                for (Entry<MonsterStatus, Integer> e : effect.getStati().entrySet()) {
                    Map<MonsterStatus, Integer> p = Collections.singletonMap(e.getKey(), e.getValue());
                    MonsterStatusEffect eff = new MonsterStatusEffect(p, effect.getSkill(), 0, effect.getMobSkill(),
                            effect.isMonsterSkill());
                    getController().getClient().getSession()
                            .writeAndFlush(MobPacket.applyMonsterStatus(getObjectId(), eff));
                }
            }
        }
    }

    public final boolean isBuffed(final MonsterStatus status) {
        return stati.containsKey(status);
    }

    public final void setFake(final boolean fake) {
        this.fake = fake;
    }

    public final boolean isFake() {
        return fake;
    }

    public final MapleMap getMap() {
        return map;
    }

    public final Rectangle getRectangle() {
        return new Rectangle(getPosition().x, getPosition().y, getStats().getWidth(), getStats().getHeight());
    }

    public final List<Pair<Integer, Integer>> getSkills() {
        return stats.getSkills();
    }

    public final boolean hasSkill(final int skillId, final int level) {
        return stats.hasSkill(skillId, level);
    }

    public final long getLastSkillUsed(final int skillId, final int skillLevel) {
        for (Entry<Pair<Integer, Integer>, Long> kvp : usedSkills.entrySet()) {
            if (kvp.getKey().left == skillId && kvp.getKey().right == skillLevel) {
                return kvp.getValue();
            }
        }

        return 0;
    }

    public final void setLastSkillUsed(final int skillId, final int skillLevel, final long now, final long cooltime) {
        switch (skillId) {
            case 140:
                usedSkills.put(new Pair<>(skillId, skillLevel), now + (cooltime * 2));
                usedSkills.put(new Pair<>(141, skillLevel), now);
                break;
            case 141:
                usedSkills.put(new Pair<>(skillId, skillLevel), now + (cooltime * 2));
                usedSkills.put(new Pair<>(140, skillLevel), now + cooltime);
                break;
            case 176:
                if (skillLevel >= 33 && skillLevel <= 36) {
                    usedSkills.put(new Pair<>(skillId, skillLevel), now + 5000);
                    usedSkills.put(new Pair<>(126, 23), now + 5000);
                } else {
                    usedSkills.put(new Pair<>(skillId, skillLevel), now + cooltime);
                }
                break;
            case 238:
                switch (skillLevel) {
                    case 1:
                    case 2:
                    case 3:
                        usedSkills.put(new Pair<>(skillId, 1), now + 8000);
                        usedSkills.put(new Pair<>(skillId, 2), now + 8000);
                        usedSkills.put(new Pair<>(skillId, 3), now + 8000);
                        break;
                    default:
                        usedSkills.put(new Pair<>(skillId, skillLevel), now + cooltime);
                        break;
                }
                break;
            default:
                usedSkills.put(new Pair<>(skillId, skillLevel), now + cooltime);
                break;
        }
    }

    public final byte getNoSkills() {
        return stats.getNoSkills();
    }

    public final boolean isFirstAttack() {
        return stats.isFirstAttack();
    }

    public final int getBuffToGive() {
        return stats.getBuffToGive();
    }

    public final MonsterStatusEffect getBuff(final MonsterStatus status) {
        if (!stati.containsKey(status)) {
            return null;
        }
        return stati.get(status);
    }

    public final int getStolen() {
        return stolen;
    }

    public final void setStolen(final int s) {
        this.stolen = s;
    }

    public final void handleSteal(MapleCharacter chr) {
        double showdown = 100.0;
        final MonsterStatusEffect mse = getBuff(MonsterStatus.SHOWDOWN);
        if (mse != null) {
            showdown += 1;
        }

        ISkill steal = SkillFactory.getSkill(4201004);
        final int level = chr.getSkillLevel(steal),
                chServerrate = ChannelServer.getInstance(chr.getClient().getChannel()).getDropRate();
        if (level > 0 && !getStats().isBoss() && stolen == -1 && steal.getEffect(level).makeChanceResult()) {
            final MapleMonsterProvider mi = MapleMonsterProvider.getInstance();
            final List<MonsterDropEntry> de = mi.retrieveDrop(getId());
            if (de == null) {
                stolen = 0;
                return;
            }
            final List<MonsterDropEntry> dropEntry = new ArrayList<MonsterDropEntry>(de);
            Collections.shuffle(dropEntry);
            IItem idrop;
            for (MonsterDropEntry d : dropEntry) { // set to 4x rate atm, 40% chance + 10x
                if (d.itemId > 0 && d.questid == 0 && d.itemId / 10000 != 238
                        && Randomizer.nextInt(999999) < (int) (10 * d.chance * chServerrate)) { // kinda op
                    if (GameConstants.getInventoryType(d.itemId) == MapleInventoryType.EQUIP) {
                        Equip eq = (Equip) ItemInformation.getInstance().getEquipById(d.itemId);
                        idrop = ItemInformation.getInstance().randomizeStats(eq, false);
                    } else {
                        idrop = new Item(d.itemId, (byte) 0,
                                (short) (d.Maximum != 1 ? Randomizer.nextInt(d.Maximum - d.Minimum) + d.Minimum : 1),
                                (byte) 0);
                        idrop.setGMLog(chr.getName() + "obtained items using Steal.");
                    }
                    stolen = d.itemId;
                    map.spawnMobDrop(idrop, map.calcDropPos(getPosition(), getPosition()), this, chr, (byte) 0,
                            (short) 0);
                    break;
                }
            }
        } else {
            stolen = 0; // failed once, may not go again
        }
    }

    public final int getEliteType() {
        return elitetype;
    }

    public final void setEliteType(int type) {
        this.elitetype = type;
    }

    public final boolean isEliteMonster() {
        return this.elitemonster;
    }

    public final void setEliteMonster(boolean a) {
        this.elitemonster = a;
    }

    public final void setEliteBoss(boolean a) {
        this.eliteboss = a;
    }

    public final boolean isEliteBoss() {
        return this.eliteboss;
    }

    public final int EliteHP() {
        return this.EliteHP;
    }

    public final void setEliteHP(int a) {
        this.EliteHP = a;
    }

    public final int EliteMHP() {
        return this.EliteMHP;
    }

    public final void setEliteMHP(int a) {
        this.EliteMHP = a;
    }

    public int EBID() {
        return this.EBID;
    }

    public void SetEBID(int a) {
        this.EBID = a;
    }

    public long getKinesisUltimateDeep() {
        return KinesisUltimateDeep;
    }

    public void setKinesisUltimateDeep(long i) {
        this.KinesisUltimateDeep = i;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return this.scale;
    }

    public final class PoisonTask {

        private final int poisonDamage;
        private final MapleCharacter chr;
        private final int skillid;
        private long checkTime;
        private final SkillStatEffect effect;

        public PoisonTask(final int poisonDamage, final MapleCharacter chr, final int skillid,
                final SkillStatEffect effect, final long checkTime) {
            this.poisonDamage = poisonDamage;
            this.chr = chr;
            this.skillid = skillid;
            this.checkTime = checkTime;
            this.effect = effect;
        }

        public void pdamage(final long time) {
            damage(chr, poisonDamage, false);
            getMap().broadcastMessage(MobPacket.damageMonster(getObjectId(), poisonDamage));
            if (skillid == 2111010) {
                if (!isAlive()) {
                    if (chr.getSlimeVirusCount() > 0) {
                        effect.applyTo(chr, getTruePosition());
                        effect.applyTo(chr, getTruePosition());
                        chr.addSlimeVirusCount(-2);
                    }
                }
            }
            this.checkTime = time + effect.getDotInterval();
        }

        public long getCheckTime() {
            return this.checkTime;
        }
    }

    private class AttackingMapleCharacter {

        private MapleCharacter attacker;
        private long lastAttackTime;

        public AttackingMapleCharacter(final MapleCharacter attacker, final long lastAttackTime) {
            super();
            this.attacker = attacker;
            this.lastAttackTime = lastAttackTime;
        }

        public final long getLastAttackTime() {
            return lastAttackTime;
        }

        public final void setLastAttackTime(final long lastAttackTime) {
            this.lastAttackTime = lastAttackTime;
        }

        public final MapleCharacter getAttacker() {
            return attacker;
        }
    }

    private interface AttackerEntry {

        List<AttackingMapleCharacter> getAttackers();

        public void addDamage(MapleCharacter from, long damage, boolean updateAttackTime);

        public long getDamage();

        public boolean contains(MapleCharacter chr);

        public void killedMob(MapleMap map, int baseExp, boolean mostDamage);
    }

    private final class SingleAttackerEntry implements AttackerEntry {

        private int damage;
        private int chrid;
        private long lastAttackTime;
        private ChannelServer cserv;

        public SingleAttackerEntry(final MapleCharacter from, final ChannelServer cserv) {
            this.chrid = from.getId();
            this.cserv = cserv;
        }

        @Override
        public void addDamage(final MapleCharacter from, final long damage, final boolean updateAttackTime) {
            if (chrid == from.getId()) {
                this.damage += damage;
                if (updateAttackTime) {
                    lastAttackTime = System.currentTimeMillis();
                }
            }
        }

        @Override
        public final List<AttackingMapleCharacter> getAttackers() {
            final MapleCharacter chr = cserv.getPlayerStorage().getCharacterById(chrid);
            if (chr != null) {
                return Collections.singletonList(new AttackingMapleCharacter(chr, lastAttackTime));
            } else {
                return Collections.emptyList();
            }
        }

        @Override
        public boolean contains(final MapleCharacter chr) {
            return chrid == chr.getId();
        }

        @Override
        public long getDamage() {
            return damage;
        }

        @Override
        public void killedMob(final MapleMap map, final int baseExp, final boolean mostDamage) {
            final MapleCharacter chr = cserv.getPlayerStorage().getCharacterById(chrid);
            if (chr != null && chr.getMap() == map && chr.isAlive()) {
                if (getStats().getCharismaEXP() > 0) {
                    chr.addCharisma(getStats().getCharismaEXP());
                }
                if (getStats().getWP() > 0 && GameConstants.isZero(chr.getJob())) {
                    chr.addWP(chr.getWP());
                }
                giveExpToCharacter(chr, baseExp, mostDamage, 1, (byte) 0, (byte) 0);
            }
        }

        @Override
        public int hashCode() {
            return chrid;
        }

        @Override
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final SingleAttackerEntry other = (SingleAttackerEntry) obj;
            return chrid == other.chrid;
        }
    }

    private static final class ExpMap {

        public final int exp;
        public final byte ptysize;
        public final byte Class_Bonus_EXP;

        public ExpMap(final int exp, final byte ptysize, final byte Class_Bonus_EXP) {
            super();
            this.exp = exp;
            this.ptysize = ptysize;
            this.Class_Bonus_EXP = Class_Bonus_EXP;
        }
    }

    private static final class OnePartyAttacker {

        public MapleParty lastKnownParty;
        public long damage;
        public long lastAttackTime;

        public OnePartyAttacker(final MapleParty lastKnownParty, final long damage) {
            super();
            this.lastKnownParty = lastKnownParty;
            this.damage = damage;
            this.lastAttackTime = System.currentTimeMillis();
        }
    }

    private class PartyAttackerEntry implements AttackerEntry {

        private long totDamage;
        private final Map<Integer, OnePartyAttacker> attackers = new HashMap<Integer, OnePartyAttacker>(6);
        private int partyid;
        private ChannelServer cserv;

        public PartyAttackerEntry(final int partyid, final ChannelServer cserv) {
            this.partyid = partyid;
            this.cserv = cserv;
        }

        public List<AttackingMapleCharacter> getAttackers() {
            final List<AttackingMapleCharacter> ret = new ArrayList<AttackingMapleCharacter>(attackers.size());
            for (final Entry<Integer, OnePartyAttacker> entry : attackers.entrySet()) {
                final MapleCharacter chr = cserv.getPlayerStorage().getCharacterById(entry.getKey());
                if (chr != null) {
                    ret.add(new AttackingMapleCharacter(chr, entry.getValue().lastAttackTime));
                }
            }
            return ret;
        }

        private final Map<MapleCharacter, OnePartyAttacker> resolveAttackers() {
            final Map<MapleCharacter, OnePartyAttacker> ret = new HashMap<MapleCharacter, OnePartyAttacker>(
                    attackers.size());
            for (final Entry<Integer, OnePartyAttacker> aentry : attackers.entrySet()) {
                final MapleCharacter chr = cserv.getPlayerStorage().getCharacterById(aentry.getKey());
                if (chr != null) {
                    ret.put(chr, aentry.getValue());
                }
            }
            return ret;
        }

        @Override
        public final boolean contains(final MapleCharacter chr) {
            return attackers.containsKey(chr.getId());
        }

        @Override
        public final long getDamage() {
            return totDamage;
        }

        public void addDamage(final MapleCharacter from, final long damage, final boolean updateAttackTime) {
            final OnePartyAttacker oldPartyAttacker = attackers.get(from.getId());
            if (oldPartyAttacker != null) {
                oldPartyAttacker.damage += damage;
                oldPartyAttacker.lastKnownParty = from.getParty();
                if (updateAttackTime) {
                    oldPartyAttacker.lastAttackTime = System.currentTimeMillis();
                }
            } else {
                // TODO actually this causes wrong behaviour when the party changes between
                // attacks
                // only the last setup will get exp - but otherwise we'd have to store the full
                // party
                // constellation for every attack/everytime it changes, might be wanted/needed
                // in the
                // future but not now
                final OnePartyAttacker onePartyAttacker = new OnePartyAttacker(from.getParty(), damage);
                attackers.put(from.getId(), onePartyAttacker);
                if (!updateAttackTime) {
                    onePartyAttacker.lastAttackTime = 0;
                }
            }
            totDamage += damage;
        }

        @Override
        public final void killedMob(final MapleMap map, final int baseExp, final boolean mostDamage) {
            MapleCharacter pchr, highest = null;
            long iDamage, highestDamage = 0;
            int iexp;
            MapleParty party;
            double averagePartyLevel, expWeight, levelMod, innerBaseExp, expFraction;
            List<MapleCharacter> expApplicable;
            final Map<MapleCharacter, ExpMap> expMap = new HashMap<MapleCharacter, ExpMap>(6);
            byte Class_Bonus_EXP;

            for (final Entry<MapleCharacter, OnePartyAttacker> attacker : resolveAttackers().entrySet()) {
                party = attacker.getValue().lastKnownParty;
                averagePartyLevel = 0;

                Class_Bonus_EXP = 0;
                expApplicable = new ArrayList<MapleCharacter>();
                for (final MaplePartyCharacter partychar : party.getMembers()) {
                    if (attacker.getKey().getLevel() - partychar.getLevel() <= 5
                            || stats.getLevel() - partychar.getLevel() <= 5) {
                        pchr = cserv.getPlayerStorage().getCharacterByName(partychar.getName());
                        if (pchr != null) {
                            if (pchr.isAlive() && pchr.getMap() == map) {
                                expApplicable.add(pchr);
                                averagePartyLevel += pchr.getLevel();
                            }
                        }
                    }
                }
                if (expApplicable.size() > 1) {
                    averagePartyLevel /= expApplicable.size();
                }
                iDamage = attacker.getValue().damage;
                if (iDamage > highestDamage) {
                    highest = attacker.getKey();
                    highestDamage = iDamage;
                }
                innerBaseExp = baseExp * ((double) iDamage / totDamage);
                expFraction = innerBaseExp / (expApplicable.size() + 1);

                for (final MapleCharacter expReceiver : expApplicable) {
                    iexp = expMap.get(expReceiver) == null ? 0 : expMap.get(expReceiver).exp;
                    expWeight = (expReceiver == attacker.getKey() ? 2.0 : 0.7);
                    levelMod = expReceiver.getLevel() / averagePartyLevel;
                    if (levelMod > 1.0 || attackers.containsKey(expReceiver.getId())) {
                        levelMod = 1.0;
                    }
                    iexp += (int) Math.round(expFraction * expWeight * levelMod);
                    expMap.put(expReceiver, new ExpMap(iexp, (byte) expApplicable.size(), Class_Bonus_EXP));
                }
            }
            ExpMap expmap;
            for (final Entry<MapleCharacter, ExpMap> expReceiver : expMap.entrySet()) {
                expmap = expReceiver.getValue();
                if (getStats().getCharismaEXP() > 0) {
                    expReceiver.getKey().addCharisma(getStats().getCharismaEXP());
                }
                giveExpToCharacter(expReceiver.getKey(), expmap.exp,
                        mostDamage ? expReceiver.getKey() == highest : false, expMap.size(), expmap.ptysize,
                        expmap.Class_Bonus_EXP);
            }
        }

        @Override
        public final int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + partyid;
            return result;
        }

        @Override
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PartyAttackerEntry other = (PartyAttackerEntry) obj;
            if (partyid != other.partyid) {
                return false;
            }
            return true;
        }
    }

    public void setNextSkill(int i) {
        this.nextSkill = i;
        if (i == 0) {
            this.nextSkillLevel = i;
        }
    }

    public void setNextSkillLvl(int i) {
        this.nextSkillLevel = i;
    }

    public int getNextSkill() {
        return this.nextSkill;
    }

    public int getNextSkillLvl() {
        return this.nextSkillLevel;
    }

    public boolean delaying;
    public long lastSkillUsed;

    public void setLastAttackTime() {
        lastAttackTime = System.currentTimeMillis();
    }

    public long getLastAttackTime() {
        return lastAttackTime;

    }

    public static boolean burn;
    public static boolean BurningServer() 
    {
        return burn;
    }
    public static void BurningServer(boolean burning) 
    {
        burn = burning;
    }
    
    
    public List<LifeMovementFragment> lastMoves;

}
