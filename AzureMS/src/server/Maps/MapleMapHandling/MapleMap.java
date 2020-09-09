package server.Maps.MapleMapHandling;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.Item;
import client.ItemInventory.MapleInventoryType;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import client.Stats.BuffStats;
import client.Stats.MonsterStatus;
import client.Stats.MonsterStatusEffect;
import client.Community.MapleParty.MaplePartyOperation;
import constants.GameConstants;
import constants.ServerConstants;
import constants.Data.QuickMove;
import connections.Database.MYSQL;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;
import handlers.GlobalHandler.MapleMechDoor;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.MapleCoolDownValueHolder;
import connections.Packets.AndroidPacket;
import connections.Packets.DemianPacket;
import connections.Packets.MainPacketCreator;
import connections.Packets.MobPacket;
import connections.Packets.PetPacket;
import connections.Packets.RunePacket;
import connections.Packets.SoulWeaponPacket;
import connections.Packets.UIPacket;
import connections.Packets.SkillPackets.MechanicSkill;
import scripting.MapScriptMethods;
import server.MonsterCollection;
import server.MonsterCollectionRegion.CollectionField.CollectionGroup.CollectionMob;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonsterProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonsterStats;
import server.LifeEntity.NpcEntity.MapleNPC;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterDropEntry;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterGlobalDropEntry;
import server.LifeEntity.MobEntity.MonsterEntity.OverrideMonsterStats;
import server.LifeEntity.MobEntity.SpawnPoint;
import server.LifeEntity.MobEntity.BossEntity.SpawnPointAreaBoss;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterSpawnEntry;
import server.Quests.MapleQuest;
import tools.Pair;
import tools.Timer.MapTimer;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapleFootHold.MapleFootholdTree;
import tools.RandomStream.Randomizer;
import client.Community.MapleParty.MaplePartyCharacter;
import client.Stats.PlayerStatList;
import handlers.GlobalHandler.BossEventHandler.Damien.DemianSword;
import handlers.GlobalHandler.DeathCount;
import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import launcher.Utility.WorldBroadcasting;
import server.Maps.ArrowFlatter;
import server.Maps.MapField.FieldLimitType;
import server.Maps.MapField.FieldLucid;
import server.Maps.MapReactor.MapleReactor;
import server.Maps.MapReactor.MapleReactorFactory;
import server.Maps.MapReactor.MapleReactorStats;
import server.Maps.MapSummon.MapleSummon;
import server.Maps.MapleDoor;
import server.Maps.MapleDragon;
import server.Maps.MapleExtractor;
import server.Maps.MapleFootHold.MapleFoothold;
import server.Maps.MapleMist;
import server.Maps.MapleNodes;
import server.Maps.MapleWorldMap.MapleWorldMapItem;
import server.Maps.MapleWorldMap.MapleWorldMapProvider;
import server.Maps.ObtacleAtom;
import tools.FileoutputUtil;

public class MapleMap {

    private EnumMap<MapleMapObjectType, ConcurrentHashMap<Integer, MapleMapObject>> mapobjects = new EnumMap<>(
            MapleMapObjectType.class);
    private int runningOid = 500000;
    private final Lock runningOidLock = new ReentrantLock();
    private long lastSpawnTime = 0, lastHurtTime = 0;
    private final Collection<MonsterSpawnEntry> monsterSpawn = new LinkedList<MonsterSpawnEntry>();
    private final AtomicInteger spawnedMonstersOnMap = new AtomicInteger(0);
    private final Map<Integer, MaplePortal> portals = new HashMap<Integer, MaplePortal>();
    private final List<Rectangle> areas = new ArrayList<Rectangle>();
    private MapleFootholdTree footholds = null;
    private float monsterRate, recoveryRate;
    private MapleMapEffect mapEffect;
    private byte channel;
    private short decHP = 0, createMobInterval = 9000;
    private String fieldType = "";
    private int instanceId;
        private final ReentrantReadWriteLock charactersLock = new ReentrantReadWriteLock();
        private final List<MapleCharacter> characters = new ArrayList<MapleCharacter>();
    private int protectItem = 0, barrier = 0, mapid, returnMapId, timeLimit, fieldLimit, maxRegularSpawn = 0,
            partyBonusRate = 0, fixedMob;
    private int forcedReturnMap = 999999999;
    private boolean town, clock, personalShop, everlast = false, dropsDisabled = false;
    private String mapName, streetName, onUserEnter, onFirstUserEnter, fieldScript;
    private Map<Integer, MapleNPC> tempnpcs3 = new HashMap<Integer, MapleNPC>();
    private Map<Integer, MapleMonster> tempmonsters3 = new HashMap<Integer, MapleMonster>();
    private WeakReference<MapleCharacter> changeMobOrigin = null;
    private List<Integer> droppedItems = new LinkedList<Integer>();
    private long maptimer = 0;
    private MapleRune rune;
    public short soulamount;
    private ScheduledFuture<?> catchstart = null;
    private long lastPlayerLeft = System.currentTimeMillis();
    private int EliteMobCount;
    private int EliteMobCommonCount;
    private int EliteBossCount;
    private boolean EliteMobCommonCountHalf = false;
    private boolean EliteBossDuplicate = false;
    private boolean elitebossmap;
    private boolean elitebossrewardmap;
    private List<ObtacleAtom> obtacleAtoms = new LinkedList<>();
    private boolean isSpawns = true;
    private MapleNodes nodes;
    private FieldLucid.LucidState lucidStat = null;

    public MapleMap(final int mapid, final int channel, final int returnMapId, final float monsterRate) {
        this.mapid = mapid;
        this.channel = (byte) channel;
        this.returnMapId = returnMapId;
        this.monsterRate = monsterRate;
        rune = null;
        
        if (isSwooBoss()) {
            resetObtacleAtom();
        }

        for (MapleMapObjectType type : MapleMapObjectType.values()) {
            mapobjects.put(type, new ConcurrentHashMap<Integer, MapleMapObject>());
        }
    }

    public final void toggleDrops() {
        this.dropsDisabled = !dropsDisabled;
    }

    public int EliteMobCount() {
        return this.EliteMobCount;
    }

    public int EliteMobCommonCount() {
        return this.EliteMobCommonCount;
    }

    public void SetEliteMobCount(int a) {
        this.EliteMobCount = a;
    }

    public void SetEliteMobCommonCount(int a) {
        this.EliteMobCommonCount = a;
    }

    public boolean isEliteBossMap() {
        return this.elitebossmap;
    }
    
    public int EliteBossCount() {
        return this.EliteBossCount;
    }
    
    public void SetEliteBossCount(int a) {
        this.EliteBossCount = a;
    }

    public void setEliteBossMap(boolean bool) {
        this.elitebossmap = bool;
    }

    public boolean isEliteBossRewardMap() {
        return this.elitebossrewardmap;
    }

    public void setEliteBossRewardMap(boolean bool) {
        this.elitebossrewardmap = bool;
    }

    public final int getId() {
        return mapid;
    }

    public boolean canDelete() {
        return (System.currentTimeMillis() - lastPlayerLeft > (30 * 60 * 1000L)) && (getCharactersSize() == 0);
    }

    public final MapleMap getReturnMap() {
        return ChannelServer.getInstance(channel).getMapFactory().getMap(returnMapId);
    }

    public final int getReturnMapId() {
        return returnMapId;
    }

    public final int getForcedReturnId() {
        return forcedReturnMap;
    }

    public final MapleMap getForcedReturnMap() {
        return ChannelServer.getInstance(channel).getMapFactory().getMap(forcedReturnMap);
    }

    public final void setForcedReturnMap(final int map) {
        this.forcedReturnMap = map;
    }

    public final float getRecoveryRate() {
        return recoveryRate;
    }

    public final void setRecoveryRate(final float recoveryRate) {
        this.recoveryRate = recoveryRate;
    }

    public final int getBarrier() {
        return barrier;
    }

    public final void setBarrier(final int barrier) {
        this.barrier = barrier;
    }

    public final int getFieldLimit() {
        return fieldLimit;
    }

    public final void setFieldLimit(final int fieldLimit) {
        this.fieldLimit = fieldLimit;
    }

    public final String getFieldType() {
        return fieldType;
    }

    public final void setFieldType(final String fieldType) {
        this.fieldType = fieldType;
    }

    public final void setInstanceId(final int iid) {
        this.instanceId = iid;
    }

    public final void setCreateMobInterval(final short createMobInterval) {
        this.createMobInterval = createMobInterval;
    }

    public final void setTimeLimit(final int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public final void setMapName(final String mapName) {
        this.mapName = mapName;
    }

    public final String getMapName() {
        return mapName;
    }

    public final String getStreetName() {
        return streetName;
    }

    public final void setChangeableMobOrigin(MapleCharacter d) {
        this.changeMobOrigin = new WeakReference<MapleCharacter>(d);
    }

    public final MapleCharacter getChangeableMobOrigin() {
        if (changeMobOrigin == null) {
            return null;
        }
        return changeMobOrigin.get();
    }

    public final void setFirstUserEnter(final String onFirstUserEnter) {
        this.onFirstUserEnter = onFirstUserEnter;
    }

    public final void setUserEnter(final String onUserEnter) {
        this.onUserEnter = onUserEnter;
    }

    public final void setFieldScript(final String fieldScript) {

    }

    public final boolean hasClock() {
        return clock;
    }

    public final void setClock(final boolean hasClock) {
        this.clock = hasClock;
    }

    public final boolean isTown() {
        return town;
    }

    public final void setTown(final boolean town) {
        this.town = town;
    }

    public final boolean allowPersonalShop() {
        return personalShop;
    }

    public final void setPersonalShop(final boolean personalShop) {
        this.personalShop = personalShop;
    }

    public final void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public final void setEverlast(final boolean everlast) {
        this.everlast = everlast;
    }

    public final boolean getEverlast() {
        return everlast;
    }

    public final int getHPDec() {
        return decHP;
    }

    public final void setHPDec(final int delta) {
        decHP = (short) delta;
    }

    public final int getHPDecProtect() {
        return protectItem;
    }

    public final void setHPDecProtect(final int delta) {
        this.protectItem = delta;
    }

    public final int getCurrentPartyId() {
        for (MapleMapObject obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter chr = (MapleCharacter) obj;
            if (chr.getParty() != null) {
                return chr.getParty().getId();
            }
        }
        return -1;
    }

    public final void addMapObject(final MapleMapObject mapobject) {
        runningOidLock.lock();
        int newOid;
        try {
            newOid = ++runningOid;
        } finally {
            runningOidLock.unlock();
        }

        mapobject.setObjectId(newOid);
        mapobjects.get(mapobject.getType()).put(newOid, mapobject);
    }

    private void spawnAndAddRangedMapObject(final MapleMapObject mapobject, final DelayedPacketCreation packetbakery) {
        addMapObject(mapobject);

        for (MapleMapObject obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter chr = (MapleCharacter) obj;
            if ((mapobject.getType() == MapleMapObjectType.MIST || chr.getTruePosition().distanceSq(mapobject.getPosition()) <= GameConstants.maxViewRangeSq())) {
                packetbakery.sendPackets(chr.getClient());
                chr.addVisibleMapObject(mapobject);
            }
        }
    }

    public final MapleMapObject getMapObject(int oid, MapleMapObjectType type) {
        return mapobjects.get(type).get(oid);
    }

    public final void checkClockContact(final MapleCharacter chr, final MapleMonster monster) {
        final Point m_Pos = monster.getPosition();
        if (this.getFieldType().equals("63")) {
            for (MapleMapObject object : this.getMapObjectsInRange(chr.getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MIST))) {
                final MapleMist clock = (MapleMist) object;
                if (clock.isClock()) {
                    Rectangle rect = new Rectangle(clock.getBox().x, clock.getBox().y, clock.getBox().width, clock.getBox().height);
                    if (rect.intersects(monster.getRectangle())) {
                        clock.setUsed(true);
                        chr.send(UIPacket.showInfo("반반이 시간을 움직임"));
                        return;
                    }
                }
            }
        }
    }

    public final void removeMapObject(final MapleMapObject obj) {
        mapobjects.get(obj.getType()).remove(Integer.valueOf(obj.getObjectId()));
    }

    public final Point calcPointMaple(final Point initial) {
        final MapleFoothold fh = footholds.findMaple(initial);
        if (fh == null) {
            return null;
        }
        int dropY = fh.getY1();
        if (!fh.isWall() && fh.getY1() != fh.getY2()) {
            final double s1 = Math.abs(fh.getY2() - fh.getY1());
            final double s2 = Math.abs(fh.getX2() - fh.getX1());
            if (fh.getY2() < fh.getY1()) {
                dropY = fh.getY1() - (int) (Math.cos(Math.atan(s2 / s1))
                        * (Math.abs(initial.x - fh.getX1()) / Math.cos(Math.atan(s1 / s2))));
            } else {
                dropY = fh.getY1() + (int) (Math.cos(Math.atan(s2 / s1))
                        * (Math.abs(initial.x - fh.getX1()) / Math.cos(Math.atan(s1 / s2))));
            }
        }
        return new Point(initial.x, dropY);
    }

    public final Point calcDropPos(final Point initial, final Point fallback) {
        final Point ret = calcPointMaple(new Point(initial.x, initial.y - 50));
        if (ret == null) {
            return fallback;
        }
        return ret;
    }

    private void dropFromMonster(final MapleCharacter chr, final MapleMonster mob) {
        if (mapid == 992010000 || mapid == 992018000 || mapid == 992030000 || mapid == 992040000) {
            return;
        }
        final ItemInformation ii = ItemInformation.getInstance();
        final byte droptype = (byte) (mob.getStats().isExplosiveReward() ? 3 : mob.getStats().isFfaLoot() ? 2 : chr.getParty() != null ? 1 : 0);
        final int mobpos = mob.getTruePosition().x, cmServerrate = ChannelServer.getInstance(channel).getMesoRate(), chServerrate = ChannelServer.getInstance(channel).getDropRate(), caServerrate = 10;
        Item idrop;
        Item te;
        byte d = 1;
        Point pos = new Point(0, mob.getTruePosition().y);
        double showdown = 100.0;
        final MonsterStatusEffect mse = mob.getBuff(MonsterStatus.SHOWDOWN);
        final MapleMonsterProvider mi = MapleMonsterProvider.getInstance();
        final List<MonsterDropEntry> derp = mi.retrieveDrop(mob.getId());
        final MapleClient c = chr.getClient();
        if (derp == null) {
            return;
        }
        final List<MonsterDropEntry> dropEntry = new ArrayList<MonsterDropEntry>(derp);
        Collections.shuffle(dropEntry);
        boolean mesoDropped = false;
        int ce = 0, maxdrop = -1;
        if ((mob.getStats().isBoss()) && (ServerConstants.useBossMaxDrop)) {
            maxdrop = ServerConstants.bossMaxDrop;
        } else if ((!mob.getStats().isBoss()) && (ServerConstants.useMaxDrop)) {
            maxdrop = ServerConstants.maxDrop;
        }
        if (mob.isEliteMonster()) { //엘리트몹
            dropEntry.add(new MonsterDropEntry(4001832, 300000, 1, 1, 0)); //주문의 흔적
            dropEntry.add(new MonsterDropEntry(2432970, 75000, 1, 1, 0)); //스페셜 명예의 훈장
        }
        
        if (mob.isEliteBoss()) { //엘리트보스
            dropEntry.add(new MonsterDropEntry(4001832, 900000, 1, 10, 0)); //주문의 흔적
            dropEntry.add(new MonsterDropEntry(2432970, 900000, 1, 5, 0)); //스페셜 명예의 훈장
            dropEntry.add(new MonsterDropEntry(2630282, 900000, 1, 1, 0)); //의문의 코어 젬스톤 상자
            dropEntry.add(new MonsterDropEntry(2630282, 900000, 1, 1, 0));//의문의 아케인 심볼 상자
            dropEntry.add(new MonsterDropEntry(2433942, 900000, 1, 1, 0)); //메소 상자
            chr.modifyCSPoints(1, 500, true); // 킬포인트
            chr.send(MainPacketCreator.sendHint("현재 #b#h ##k님의 킬포인트는 #r" + chr.getCSPoints(1) + "#k점 입니다.", 350, 5));
        }
		
        for (final MonsterDropEntry de : dropEntry) {
            if (de.itemId == mob.getStolen()) {
                continue;
            }
            if (GameConstants.getInventoryType(de.itemId) == MapleInventoryType.EQUIP || de.itemId == 4000244) {
                ce = (int) ((de.chance * chServerrate) / 10);
            } else {
                ce = (int) (de.chance * chServerrate);
            }
            int chance = de.chance + (int) (de.chance * (Math.max(chr.getStat().getincRewardProp(), 200) * 0.01D));
            if (de.itemId == 4000245) {
                return;
            }
            if ((Randomizer.nextInt(999999) < chance * chServerrate * (showdown / 100.0D)) && ((maxdrop == -1) || (d < maxdrop + 1))) {
                if (mesoDropped && droptype != 3 && de.itemId == 0) {
                    continue;
                }
                if (de.itemId / 10000 == 238 && !mob.getStats().isBoss()) {
                    continue;
                }
                if (droptype == 3) {
                    pos.x = (mobpos + (d % 2 == 0 ? (40 * (d + 1) / 2) : -(40 * (d / 2))));
                } else {
                    pos.x = (mobpos + ((d % 2 == 0) ? (25 * (d + 1) / 2) : -(25 * (d / 2))));
                }
                if (de.itemId == 0) {
                    int mesos = Randomizer.nextInt(1 + Math.abs(de.Maximum - de.Minimum)) + de.Minimum;
                    if (mesos > 0) {
                        if (GameConstants.isFrozenMap(chr.getMapId())) {
                            chr.gainMeso(mesos * cmServerrate, true);
                        } else {
                            spawnMobMesoDrop((int) (mesos * cmServerrate), calcDropPos(pos, mob.getTruePosition()), mob, chr, false, droptype);
                        }
                        mesoDropped = true;
                    }
                } else {
                    if (GameConstants.getInventoryType(de.itemId) == MapleInventoryType.EQUIP) {
                        idrop = ii.randomizeStats((Equip) ii.getEquipById(de.itemId));
                        if (GameConstants.환생의불꽃아이템(idrop.getItemId())) {
                            idrop = (Item) InventoryHandler.환생의불꽃((Equip) idrop);
                        }
                    } else {
                        final int range = Math.abs(de.Maximum - de.Minimum);
                        idrop = new Item(de.itemId, (byte) 0,
                                (short) (de.Maximum != 1 ? Randomizer.nextInt(range <= 0 ? 1 : range) + de.Minimum : 1),
                                (byte) 0);
                    }
                    if (de.itemId == -1) {
                        continue;
                    }
                    if (!de.checkNull) {
                        de.checkNull = true;
                        if (ItemInformation.getInstance().getItemData(de.itemId) == null) {
                            de.itemId = -1;
                            continue;
                        }
                    }
                    if (!GameConstants.isFrozenMap(chr.getMapId())) {
                        spawnMobDrop(idrop, calcDropPos(pos, mob.getTruePosition()), mob, chr, droptype, de.questid);
                    }
                }
                d++;
            }
        }
        final List<MonsterGlobalDropEntry> globalEntry = new ArrayList<MonsterGlobalDropEntry>(mi.getGlobalDrop());
        Collections.shuffle(globalEntry);
        for (final MonsterGlobalDropEntry de : globalEntry) {
            int chance = de.chance + (int) (de.chance * (chr.getStat().getincRewardProp() * 0.01D));
            if (Randomizer.nextInt(999999) < chance) {
                if (droptype == 3) {
                    pos.x = (mobpos + (d % 2 == 0 ? (40 * (d + 1) / 2) : -(40 * (d / 2))));
                } else {
                    pos.x = (mobpos + ((d % 2 == 0) ? (25 * (d + 1) / 2) : -(25 * (d / 2))));
                }
                if (de.itemId == 0) {
                    int mesos = Randomizer.nextInt(1 + Math.abs(de.Maximum - de.Minimum)) + de.Minimum;
                    if (mesos > 0) {
                    if (GameConstants.isFrozenMap(chr.getMapId())) {
                        chr.gainMeso(mesos * cmServerrate, true);
                    } else {
                        spawnMobMesoDrop((int) (mesos * cmServerrate), calcDropPos(pos, mob.getTruePosition()), mob, chr, false, droptype);
                    }
                    mesoDropped = true;
                    }
                } else {
                    if (GameConstants.getInventoryType(de.itemId) == MapleInventoryType.EQUIP) {
                        idrop = ii.randomizeStats((Equip) ii.getEquipById(de.itemId));
                        if (GameConstants.환생의불꽃아이템(idrop.getItemId())) {
                            idrop = InventoryHandler.환생의불꽃((Equip) idrop);
                        }
                    } else {
                        idrop = new Item(de.itemId, (byte) 0,
                                (short) (de.Maximum != 1 ? Randomizer.nextInt(de.Maximum - de.Minimum) + de.Minimum : 1),
                                (byte) 0);
                    }
                    if (de.itemId == 4000421 && !chr.haveItem(2430492)) {
                        return;
                    }
                    if (de.itemId == 2022165) {
                        return;
                    }
                    if ((de.itemId == 4001513 && !(mob.getStats().getLevel() >= 105 && mob.getStats().getLevel() <= 114)) || (de.itemId == 4001515 && !(mob.getStats().getLevel() >= 115 && mob.getStats().getLevel() <= 159)) || (de.itemId == 4001521 && !(mob.getStats().getLevel() >= 160 && mob.getStats().getLevel() <= 250))) {
                        return;
                    }
                    if (de.itemId >= 4005000 && de.itemId <= 4005004 && !(mob.isEliteMonster() && mob.getStats().getLevel() >= 170)) {
                        return;
                    }
                    if (de.itemId >= 4007000 && de.itemId <= 4007007 && mob.getStats().getLevel() < 170) {
                        return;
                    }
                    if (de.itemId >= 3994000 && de.itemId <= 3994009) {
                        if (c != null && c.getPlayer() != null) {
                            int mLevel = c.getPlayer().getLevel() + 20;
                            int nLevel = c.getPlayer().getLevel() - 20;
                            boolean dropOK = false;
                            if (mob.getStats().getLevel() <= mLevel && mob.getStats().getLevel() >= nLevel) {
                                dropOK = true;
                            }
                            if (!dropOK) {
                                return;
                            }
                        }
                    }

                    if (de.itemId == -1) {
                        continue;
                    }
                    if (!de.checkNull) {
                        de.checkNull = true;
                        if (ItemInformation.getInstance().getItemData(de.itemId) == null) {
                            de.itemId = -1;
                            continue;
                        }
                    }
                    spawnMobDrop(idrop, calcDropPos(pos, mob.getTruePosition()), mob, chr, droptype, de.questid);
                }
                d++;
            }
        }
    }
    public final void killMonster(final MapleMonster monster) {
        spawnedMonstersOnMap.decrementAndGet();
        monster.setHp(0);
        monster.spawnRevives(this);
        broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 1, GameConstants.isAswanMap(mapid)));
        removeMapObject(monster);
    }
    public final void spawnSoul(final MapleMapObject dropper, final MapleCharacter chr, final IItem item, Point pos) {
        final Point droppos = calcDropPos(pos, pos);
        final MapleWorldMapItem drop = new MapleWorldMapItem(item, droppos, dropper, chr, (byte) 0, true);
        broadcastMessage(MainPacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte) 0));
        broadcastMessage(MainPacketCreator.removeItemFromMap(drop.getObjectId(), 2, chr.getId(), false, 0));
        if (chr.getEquippedSoulSkill() != 0) {
            chr.send(SoulWeaponPacket.giveSoulGauge(chr.addgetSoulCount(), chr.getEquippedSoulSkill()));
            chr.checkSoulState(false, chr.getEquippedSoulSkill());
        }
    }

    public final void killAllMonsters2(final boolean animate, MapleCharacter chr) {
        int qwer = 0;
        for (final MapleMapObject m : getAllMonster()) {
            MapleMonster monster = (MapleMonster) m;
            spawnedMonstersOnMap.decrementAndGet();
            monster.setHp(0);
            broadcastMessage(MobPacket.killMonster(monster.getObjectId(), animate ? 1 : 0, GameConstants.isAswanMap(mapid)));
            removeMapObject(monster);
            qwer++;
        }
    }

    public final void killMonster(final MapleMonster monster, final MapleCharacter chr, final boolean withDrops, final boolean second, final byte animation) {
 
        
        if ((monster.getId() == 8810018 || monster.getId() == 8810122) && !second) {
            MapTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    killMonster(monster, chr, true, true, (byte) 1);
                    killAllMonsters(true);
                }
            }, 3000);
            return;
        }

        MapleCharacter dropOwner = monster.killBy(chr);
        if (GameConstants.isZero(chr.getJob())) { // WP 흡수
            int wp = 1;
            chr.gainWP(wp);
            chr.send(MainPacketCreator.ZeroUpdate(chr));
            chr.send(MainPacketCreator.absorbingDF(monster.getObjectId(), chr.addWP(wp), wp, true, chr, chr.getTruePosition()));
            chr.send(MainPacketCreator.ZeroWP(wp));
        }
        
        Date data = new Date();
        String month = data.getMonth() < 10 ? "0" + (data.getMonth() + 1) : String.valueOf((data.getMonth() + 1));
        String date2 = data.getDate() < 10 ? "0" + data.getDate() : String.valueOf(data.getDate());
        String date = (data.getYear() + 1900) + "" + month + "" + date2;
        Calendar now = Calendar.getInstance();
        String week = now.get(now.WEEK_OF_YEAR) + "";
        String weekdate = (data.getYear() + 1900) + "" + week;

        // 일반퀘스트
        if (chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_isclear") == 0 && chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_count") < chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_mobq")) {
            chr.setKeyValue_new(201901, "quest_" + monster.getId() + "_count", String.valueOf(chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_count") + 1));
            chr.dropShowInfo(monster.getStats().getName() + " " + chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_count") + " / " + chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_mobq"));
            if (chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_count") >= chr.getKeyValue_new(201901, "quest_" + monster.getId() + "_mobq")) {
                chr.setKeyValue_new(201901, "quest_" + monster.getId() + "_isclear", String.valueOf(1));
            }
        }

        // Daily Quest
        if (chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_isclear") == 0 && chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_count") < chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_mobq")) {
            chr.setKeyValue_new(201901, date + "_" + monster.getId() + "_count", String.valueOf(chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_count") + 1));
             chr.dropShowInfo(monster.getStats().getName() + " " + chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_count") + " / " + chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_mobq"));
            if (chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_count") >= chr.getKeyValue_new(201901, date + "_" + monster.getId() + "_mobq")) {
                chr.setKeyValue_new(201901, date + "_" + monster.getId() + "_isclear", String.valueOf(1));
            }
        }

        // 주간퀘스트
        if (chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_isclear") == 0 && chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_count") < chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_mobq")) {
            chr.setKeyValue_new(201901, weekdate + "_" + monster.getId() + "_count", String.valueOf(chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_count") + 1));
             chr.dropShowInfo(monster.getStats().getName() + " " + chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_count") + " / " + chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_mobq"));
            if (chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_count") >= chr.getKeyValue_new(201901, weekdate + "_" + monster.getId() + "_mobq")) {
                chr.setKeyValue_new(201901, weekdate + "_" + monster.getId() + "_isclear", String.valueOf(1));
            }
        }

        //심화퀘스트 몹 코드 추가
        int[] questmob = {8641014, 8642012, 8643012, 8644008, 8644410, 8644508};
        for (int temp : questmob) {
            if (monster.getId() == temp) {
                chr.setKeyValue_new(20190509, "mobkill_" + temp, "" + (chr.getKeyValue_new(20190509, "mobkill_" + temp) + 1));
            }
        }
        
        if ((chr.getMapId() - 925070000) > 0 && (chr.getMapId() - 925070000) <= 6300) {
            int floor = (chr.getMapId() - 925070000) / 100;
            if (floor / 10 == 3 && floor != 30 && floor != 33 && floor != 36 && floor != 39 && chr.mCount < 1) {
                chr.mCount++;
                int id = 0;
                long hp = 0;
                switch (floor) {
                    case 31:
                        id = 9305630;
                        hp = 2108240000;
                        break;
                    case 32:
                        id = 9305631;
                        hp = 2526520000L;
                        break;
                    case 34:
                        id = 9305633;
                        hp = 3464920000L;
                        break;
                    case 35:
                        id = 9305634;
                        hp = 3986640000L;
                        break;
                    case 37:
                        id = 9305636;
                        hp = 5149760000L;
                        break;
                    case 38:
                        id = 9305637;
                        hp = 6474960000L;
                        break;
                }
                if (id > 0) {
                    MapleMonster mob = MapleLifeProvider.getMonster(id);
                    if (mob != null) {
                        mob.setHp(hp);
                        mob.getStats().setHp(hp);
                        spawnMonsterOnGroundBelow(mob, new Point(Randomizer.nextBoolean() ? -304 : 185, 7));
                    }
                }
            } else {
                    chr.mCount = 0;
                    chr.setDojoStopTime(System.currentTimeMillis());
                    chr.setKeyValue_new(3, "dojo", String.valueOf(floor));
                    chr.setKeyValue_new(3, "dojo_time", String.valueOf((int) ((System.currentTimeMillis() - chr.getDojoStartTime() - chr.getDojoCoolTime()) / 1000)));
                    chr.dropMessage(6, "상대를 격파하였습니다. 10초간 타이머가 정지됩니다.");
                    System.out.println("테스트트트트");
                    chr.getClient().getSession().writeAndFlush(MainPacketCreator.showMapEffect("Dojang/clear"));
                    chr.getClient().getSession().writeAndFlush(MainPacketCreator.showMapEffect("dojang/end/clear"));
                    chr.getClient().getSession().writeAndFlush(MainPacketCreator.getDojoClockStop(true, (int) (900 - ((System.currentTimeMillis() - chr.getDojoStartTime()) / 1000))));
                }
            }


        int hpoint = Randomizer.rand(1, 1);
        if (hpoint == 1) {
            boolean killp = false;
            int[] blockmob = {8881000, 8880150, 9440025, 8880302, 6500001, 8880503, 9100000, 9100001,8250000,8250001,8250002,2300100,4230102,3230101,2230101,2230131};  //킬포인트 얻지 않게 할 몹
            for (int i = 0; i < blockmob.length; i++) {
                if (monster.getId() == blockmob[i]) {
                    killp = true;
                }
            }
            if (!killp) {
                if (Randomizer.isSuccess(50)) {
                    chr.modifyCSPoints(1, 1, true); // 킬포인트
                    if (chr.getCSPoints(1) % 50 == 0) {
                        chr.send(MainPacketCreator.sendHint("현재 #b#h ##k님의 킬포인트는 #r" + chr.getCSPoints(1) + "#k점 입니다.", 350, 5));
                    }
                }
            }
        }
        
        int id = monster.getId();
        int[] point2 = {30000, 15000, 10000, 7500, 6000, 5000};  //파티당 킬포인트 금액
        int[] point3 = {50000, 25000, 16600, 12500, 10000, 8300}; 
        int[] point4 = {80000, 40000, 26600, 20000, 16000, 13300}; 
        int[] point5 = {120000, 60000, 40000, 3000, 24000, 20000};
        int[] point6 = {200000, 100000, 66600, 50000, 40000, 33300}; 
        if (id == 8880150) {
                if (chr.getParty() != null) {
                for (MaplePartyCharacter pc : chr.getParty().getMembers()) {
                    MapleCharacter ch = ChannelServer.getInstance(pc.getChannel()).getPlayerStorage().getCharacterById(pc.getId());
         if (ch != null) {
                        if (ch.getMapId() == 450004250) {
                        ch.modifyCSPoints(1, point2[ch.getParty().getMembers().size() - 1], true);
                        ch.send(MainPacketCreator.sendHint("축하드립니다!!\r\n#b" + monster.getStats().getName() + "#k을(를) 처치하여 #r" + point2[ch.getParty().getMembers().size() - 1] + " 킬포인트#k를 얻으셨습니다.", 350, 50));                            
                        } else {
                        ch.send(MainPacketCreator.sendHint("보스맵에 없어 킬포인트를 얻지 못하였습니다", 350, 50));                            
                        }
                    }
                }
            }   
        } else if (id == 9440025) {
                if (chr.getParty() != null) {
                for (MaplePartyCharacter pc : chr.getParty().getMembers()) {
                    MapleCharacter ch = ChannelServer.getInstance(pc.getChannel()).getPlayerStorage().getCharacterById(pc.getId());
        if (ch != null) {
                        if (ch.getMapId() == 992040000) {
                        ch.modifyCSPoints(1, point3[ch.getParty().getMembers().size() - 1], true);
                        ch.send(MainPacketCreator.sendHint("축하드립니다!!\r\n#b" + monster.getStats().getName() + "#k을(를) 처치하여 #r" + point3[ch.getParty().getMembers().size() - 1] + " 킬포인트#k를 얻으셨습니다.", 350, 50));                            
                        } else {
                        ch.send(MainPacketCreator.sendHint("보스맵에 없어 킬포인트를 얻지 못하였습니다", 350, 50));                            
                        }
                    }
                }
            }   
        } else if (id == 8880302) {
                if (chr.getParty() != null) {
                for (MaplePartyCharacter pc : chr.getParty().getMembers()) {
                    MapleCharacter ch = ChannelServer.getInstance(pc.getChannel()).getPlayerStorage().getCharacterById(pc.getId());
         if (ch != null) {
                        if (ch.getMapId() == 450008350) {
                        ch.modifyCSPoints(1, point4[ch.getParty().getMembers().size() - 1], true);
                        ch.send(MainPacketCreator.sendHint("축하드립니다!!\r\n#b" + monster.getStats().getName() + "#k을(를) 처치하여 #r" + point4[ch.getParty().getMembers().size() - 1] + " 킬포인트#k를 얻으셨습니다.", 350, 50));                            
                        } else {
                        ch.send(MainPacketCreator.sendHint("보스맵에 없어 킬포인트를 얻지 못하였습니다", 350, 50));                            
                        }
                    }
                }
            }   
        } else if (id == 6500001) {
                if (chr.getParty() != null) {
                for (MaplePartyCharacter pc : chr.getParty().getMembers()) {
                    MapleCharacter ch = ChannelServer.getInstance(pc.getChannel()).getPlayerStorage().getCharacterById(pc.getId());
         if (ch != null) {
                        if (ch.getMapId() == 450010800) {
                        ch.modifyCSPoints(1, point5[ch.getParty().getMembers().size() - 1], true);
                        ch.send(MainPacketCreator.sendHint("축하드립니다!!\r\n#b" + monster.getStats().getName() + "#k을(를) 처치하여 #r" + point5[ch.getParty().getMembers().size() - 1] + " 킬포인트#k를 얻으셨습니다.", 350, 50));                            
                        } else {
                        ch.send(MainPacketCreator.sendHint("보스맵에 없어 킬포인트를 얻지 못하였습니다", 350, 50));                            
                        }
                    }
                }
            }   
        } else if (id == 8880503) {
                if (chr.getParty() != null) {
                for (MaplePartyCharacter pc : chr.getParty().getMembers()) {
                    MapleCharacter ch = ChannelServer.getInstance(pc.getChannel()).getPlayerStorage().getCharacterById(pc.getId());
         if (ch != null) {
                        if (ch.getMapId() == 500000011) {
                        ch.modifyCSPoints(1, point6[ch.getParty().getMembers().size() - 1], true);
                        ch.send(MainPacketCreator.sendHint("축하드립니다!!\r\n#b" + monster.getStats().getName() + "#k을(를) 처치하여 #r" + point6[ch.getParty().getMembers().size() - 1] + " 킬포인트#k를 얻으셨습니다.", 350, 50));                            
                        } else {
                        ch.send(MainPacketCreator.sendHint("보스맵에 없어 킬포인트를 얻지 못하였습니다", 350, 50));                            
                        }
                    }
                }
            }   
        }
        if (id == 8881000 || id == 8880150 || id == 9440025 || id == 8880302 || id == 6500001 || id == 8880503) {
            FileoutputUtil.log(FileoutputUtil.보스로그, "[ " + chr.getName() + " ]님이 서버보스 [ " + monster.getStats().getName() + " ]을(를) 클리어하였습니다.");
        }
        if (GameConstants.isFrozenMap(chr.getMapId())) {
            chr.setFrozenMobCount(chr.getFrozenMobCount() - 1);
        }

        if (chr.isEquippedSoulWeapon()) { // 소울웨폰 게이지
            int rand = Randomizer.rand(1, 3);
            for (int i = 0; i < rand; i++) {
                IItem toDrop;
                toDrop = new Item(4001536, (byte) 0, (short) 1, (byte) 0);
                spawnSoul(monster, chr, toDrop, monster.getPosition());
            }
        }
       /* if (chr.getDateKey("questB4", false) != null) {
            if (monster.getId() == Integer.parseInt(chr.getDateKey("questB4", false))) {
                chr.dropMessage(5, "일일퀘스트 보스 토벌 완료! 보상 NPC를 찾아가 보상을 받아가세요!");
                chr.setDateKey("questD4", "1");
            }
        }
        if (chr.getDateKey("questD2", false) != null) {
            chr.setDateKey("questD2", "" + (Integer.parseInt(chr.getDateKey("questD2", false)) + 1));
        } else {
            chr.setDateKey("questD2", "1");
        }
        if (chr.getDateKey("questD2", false) != null) {
            if (Integer.parseInt(chr.getDateKey("questD2", false)) == 1500) {
                chr.dropMessage(5, "일일퀘스트 몬스터 헌팅 완료! 보상 NPC를 찾아가 보상을 받아가세요!");
            }
        }*/

        broadcastMessage(MobPacket.killMonster(monster.getObjectId(), animation, GameConstants.isAswanMap(getId())));
        spawnedMonstersOnMap.decrementAndGet();
        removeMapObject(monster);
        /*if (chr.getEventInstance() != null) {
            if (getAllMonster().isEmpty()) {
                chr.getEventInstance().allMonstersDead();
            }
        }*/
        if (monster.getBuffToGive() > -1) {
            final int buffid = monster.getBuffToGive();
            final SkillStatEffect buff = ItemInformation.getInstance().getItemEffect(buffid);
            for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
                MapleCharacter c = (MapleCharacter) _obj;
                if (c.isAlive()) {
                    buff.applyTo(c);
                    switch (monster.getId()) {
                        case 8810018:
                        case 8810122:
                        case 8820001:
                        case 8820101:
                            c.getClient().getSession().writeAndFlush(MainPacketCreator.showSkillEffect(-1, buffid, 20));																						// spirit
                            broadcastMessage(c, MainPacketCreator.showSkillEffect(c.getId(), buffid, 20), false);
                            // spirit
                            break;
                    }
                }
            }
        }
        final int mobid = monster.getId();

        if (mobid == 8820008 || mobid == 8820108) {
            for (final MapleMapObject mmo : getAllMonster()) {
                MapleMonster mons = (MapleMonster) mmo;
                if (mons.getLinkOid() != monster.getObjectId()) {
                    killMonster(mons, chr, false, false, animation);
                }
            }
        } else if (mobid >= 8820010 && mobid <= 8820014) {
            for (final MapleMapObject mmo : getAllMonster()) {
                MapleMonster mons = (MapleMonster) mmo;
                if (mons.getId() != 8820000 && mons.getId() != 8820001 && mons.getObjectId() != monster.getObjectId() && mons.isAlive() && mons.getLinkOid() == monster.getObjectId()) {
                    killMonster(mons, chr, false, false, animation);
                }
            }
        } else if (mobid >= 8820110 && mobid <= 8820114) {
            for (final MapleMapObject mmo : getAllMonster()) {
                MapleMonster mons = (MapleMonster) mmo;
                if (mons.getId() != 8820100 && mons.getId() != 8820101 && mons.getObjectId() != monster.getObjectId() && mons.isAlive() && mons.getLinkOid() == monster.getObjectId()) {
                    killMonster(mons, chr, false, false, animation);
                }
            }
        } else if (mobid >= 8810102 && mobid <= 8810109) {
            boolean notyetdead = false;
            for (int i = 8810102; i < 8810109; i++) {
                if (getMonsterById(i) != null) {
                    notyetdead = true;
                    break;
                }
            }
            if (!notyetdead) {
                killMonster(getMonsterById(8810122), chr, false, false, (byte) 0);
            }
            
            // Mob stages Transfer
        } else if (mobid == 9801028) {
            for (MapleCharacter mchr : getCharacters()) {
                mchr.changeMap(350060200, 0);
                mchr.dropMessage(5, "Lotus is angry!");
            }
            ChannelServer.getInstance(chr.getClient().getChannel()).getMapFactory().getMap(350060200)
                    .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(9801029), new Point(226, -16));
        } else if (mobid == 9300890) {
            for (MapleCharacter mchr : getCharacters()) {
                mchr.changeMap(350160240, 0);
                mchr.dropMessage(5, "Damiens is angry!");
            }
            ChannelServer.getInstance(chr.getClient().getChannel()).getMapFactory().getMap(350160240)
                    .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880131), new Point(765, 19));
        } else if (mobid == 8880140) {
            for (MapleCharacter mchr : getCharacters()) {
                mchr.changeMap(450004250, 0);
                mchr.dropMessage(5, "Lucid is angry!");
            }
            ChannelServer.getInstance(chr.getClient().getChannel()).getMapFactory().getMap(450004250)
                    .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880150), new Point(798, -192));    
            
        } else if (mobid == 8880301) { // 윌 1페이즈
            for (MapleCharacter mchr : getCharacters()) {
                mchr.changeMap(450008350, 0);
                mchr.dropMessage(5, "Will is angry!");
            }
            ChannelServer.getInstance(chr.getClient().getChannel()).getMapFactory().getMap(450008350)
                    .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880302), new Point(-4, 281));
        } else if (mobid == 8880502) { // 검은 마법사
            for (MapleCharacter mchr : getCharacters()) {
                mchr.changeMap(500000011, 0);
                mchr.dropMessage(5, "The Black Wizard is angry!");
            }
            ChannelServer.getInstance(chr.getClient().getChannel()).getMapFactory().getMap(500000011)
                    .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880503), new Point(949, 19));
        } else if (mobid >= 8850000 && mobid <= 8850003) {
            spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(mobid + 1), new Point(-363, 100));
        } else if (mobid == 8850004) {
            spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8850012), new Point(-363, 100));
        } else if (mobid == 8800002 || mobid == 8800102 || mobid == 8800022) { // When it is Zakum, closing this conditional statement
            killAllMonsters(true);
        } // This should be here
        
        // Raid Boss
        if (mobid == 8880150 || mobid == 9440025 || mobid == 8880302 || mobid == 6500001
        || mobid == 8880503) {
            if (chr.getParty() != null) {
                for (MapleCharacter mchr : chr.getMap().getCharacters()) {
                    if (mchr != null) {
                        mchr.Message("Congratulations on clearing! `@boss` Run that command.");
                        if (mobid == 8880150) {
                            mchr.removeBossClearDB("lucid");
                            mchr.addBossClearDB("lucid");
                        }
                        if (mobid == 9440025) {
                            mchr.removeBossClearDB("cross");
                            mchr.addBossClearDB("cross");
                        }
                        if (mobid == 8880302) {
                            mchr.removeBossClearDB("will");
                            mchr.addBossClearDB("will");
                        }
                        if (mobid == 6500001) {
                            mchr.removeBossClearDB("hilla");
                            mchr.addBossClearDB("hilla");
                        }
                         if (mobid == 8880503) {
                            mchr.removeBossClearDB("blackmagic");
                            mchr.addBossClearDB("blackmagic");
                        }
                    }
                }
                WorldBroadcasting.broadcastMessage(MainPacketCreator.serverNotice(2,
                "[Server Boss] : " + chr.getParty().getLeader().getName() + "'S party killed" + monster.getStats().getName() + "!"));
            }
        }
        
        if (mobid == 8800102) { // Chaos Zakum
            chr.setExpeditionKilledBoss(true);
        }
        if (mobid == 8840000) { // Von Leon
            chr.setExpeditionKilledBoss(true);
        }
        switch (mapid) {
            case 105200130:
            case 105200200:
            case 105200300:
            case 105200400:
            case 105200140:
            case 105200210:
            case 105200313:
            case 105200411:
                if (chr.getClient().getChannelServer().getMapFactory().getMap(mapid).getAllMonster().size() == 0) {
                    broadcastMessage(MainPacketCreator.showEffect("Gstar/ClearS"));
                }
        }
        switch (monster.getId()) {
            case 8920100:
                spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8920101), monster.getPosition());
                break;
            case 8920101:
                spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8920102), monster.getPosition());
                break;
            case 8920102:
                spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8920103), monster.getPosition());
                break;
            default:
                break;
        }
        if (monster.getId() == 8900100) {
            spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8900101), monster.getPosition());
        } else if (monster.getId() == 8900101) {
            spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8900102), monster.getPosition());
        }

        if (monster.getId() == 8910001 || monster.getId() == 8900102 || monster.getId() == 8920103
                || monster.getId() == 8930100) {
            broadcastMessage(MainPacketCreator.showEffect("killing/clear"));
            broadcastMessage(MainPacketCreator.playSound("Party1/Clear"));
            int i = monster.getId() == 8930100 ? 7 + Randomizer.nextInt(13) : monster.getId() == 8920103 ? 5 + Randomizer.nextInt(4) : monster.getId() == 8900102 ? 3 + Randomizer.nextInt(4) : 6 + Randomizer.nextInt(9);
        }

        if (monster.getId() == 9480000) {
            MapleQuestStatus st = chr.getQuestNAdd(MapleQuest.getInstance(70002));
            if (st != null) {
                if (st.getCustomData().equals("1")) {
                    st.setMobKills(mobid, st.getMobKills(mobid) + 1);
                    chr.getClient().sendPacket(UIPacket.showInfo("파이라노 " + st.getMobKills(mobid) + " / 5"));
                    if (st.getMobKills(mobid) >= 5) {
                        chr.getClient().sendPacket(
                                MainPacketCreator.getQuestMessage("Completed Class Class Quest. Please click Hurdle."));
                    }
                }
            }
        } else if (monster.getId() == 9400014) {
            MapleQuestStatus st = chr.getQuestNAdd(MapleQuest.getInstance(70010));
            if (st != null) {
                st.setCustomData("1");
            }
        } else if (monster.getId() == 9601050) {
            MapleQuestStatus st = chr.getQuestNAdd(MapleQuest.getInstance(70011));
            if (st != null) {
                st.setCustomData("1");
            }
        } else if (monster.getId() == 9601068) {
            MapleQuestStatus st = chr.getQuestNAdd(MapleQuest.getInstance(70012));
            if (st != null) {
                st.setCustomData("1");
            }
        }
        
        if (withDrops) {
            if (dropOwner == null) {
                dropOwner = chr;
            }
            boolean drop = false;
            int[] blockmob = {8881000, 8880150, 9440025, 8880302, 6500001, 8880503, 9100000, 9100001,8250000,8250001,8250002,2300100,4230102,3230101,2230101,2230131};  //드랍안하게 할 몹
            for (int i = 0; i < blockmob.length; i++) {
                if (monster.getId() == blockmob[i]) {
                    drop = true;
                }
            }
            if (!drop) {
                dropFromMonster(dropOwner, monster);
            }
        }

       int trace = 0;
       
       // Really good to know for Drops here below
       // Drops
       
        if(monster.getStats().getLevel() >= 100) // Monster level 100 or above
        {
            trace = 300;
        }
        
        if(monster.getStats().getLevel() >= 150) // Monster level 150 or above
        {
            trace = 600;
        }
        if(monster.getStats().getLevel() >= 200) // Monster level 200 or higher
        {
                    
        int rand = Randomizer.rand(1, 10000);
            if(rand <= 100)
            {
                //2435719 Core gemstone
               spawnMobDrop(new Item(2435719, (byte) 0, (short) 1, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
            }
            trace = 800;
        }
         
        if(monster.getStats().getLevel() >= 250) // Monster level 150 or above
        {
            trace = 1000;
        }
         
        if(trace != 0)
        {
            int randtrace = Randomizer.rand(1, 10000);
            if(randtrace <= trace)
            {
               spawnMobDrop(new Item(4001832, (byte) 0, (short) 1, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
            }
            trace = 0;
        }
        
        
        
        if (!this.isEliteBossMap() && !monster.isEliteMonster() && !monster.isEliteBoss()
                && !monster.getStats().isBoss() && monster.getStats().getLevel() - 40 <= chr.getLevel()
                && chr.getLevel() <= monster.getStats().getLevel() + 40) {
            int RandElitemobcount = Randomizer.rand(1, 1500);
            if(monster.getStats().getLevel() >= 100)
            {
             SetEliteMobCommonCount(this.EliteMobCommonCount + RandElitemobcount);
            }
        } else if (monster.isEliteMonster()) {
            broadcastMessage(MainPacketCreator.startMapEffect("어두운 기운이 사라지지 않아 이곳을 음산하게 만들고 있습니다.", 5120124, true));
            monster.setEliteMonster(false);
            timeAllPlayer(this);
            if(EliteBossDuplicate != true)
            {
                SetEliteBossCount(this.EliteBossCount + 1);
            }
        }
        if(monster.isEliteBoss())
        {
            EliteBossDuplicate = false;
            monster.setEliteBoss(false);
        }
	if (this.EliteMobCommonCount >= 50000 && EliteMobCommonCountHalf != true) {
            EliteMobCommonCountHalf = true;
            broadcastMessage(MainPacketCreator.startMapEffect("I can feel the dark energy.", 5120124, true));
        }
        else if (this.EliteMobCommonCount >= 100000) {
            SetEliteMobCommonCount(0);
            MapleMonster elite = makeEliteMonster(monster);
            spawnMonster(elite, -2);
            broadcastMessage(UIPacket.playSpecialMapSound("Field.img/eliteMonster/Regen"), chr.getPosition());
            broadcastMessage(MainPacketCreator.startMapEffect("Powerful monsters appear with dark energy.", 5120124, true));
            timeAllPlayer(this);
            EliteMobCommonCountHalf = false;
        }
        
                
         if (!this.isEliteBossMap() && !monster.isEliteMonster() && !monster.isEliteBoss() && !monster.getStats().isBoss() 
            && monster.getStats().getLevel() >= 100
            && monster.getStats().getLevel() - 40 <= chr.getLevel()
            && chr.getLevel() <= monster.getStats().getLevel() + 40) 
        {
            if(this.EliteBossCount >= 16)
            {
                broadcastMessage(MainPacketCreator.startMapEffect("This place is full of dark energy, and it seems like something will happen soon.", 5120124, true));
            }
            if(this.EliteBossCount == 20)
            {
                int boss_spawn_id = Randomizer.rand(1, 5);
                
                if (boss_spawn_id == 1)
                {
                    boss_spawn_id = 8220022;
                }
                else if(boss_spawn_id == 2)
                {
                    boss_spawn_id = 8220023;
                }
                else if(boss_spawn_id == 3)
                {
                    boss_spawn_id = 8220024;
                }
                else if(boss_spawn_id == 4)
                {
                    boss_spawn_id = 8220025;
                } 
                else if(boss_spawn_id == 5) 
                {
                    int duenkel = Randomizer.rand(1, 1000);
                    if(duenkel <= 3000)
                    {
                        boss_spawn_id = 8645009;
                    }
                    else
                    {
			return;
                    }
                }
                
		final MapleMonster bossmob = MapleLifeProvider.getMonster(boss_spawn_id);
                MapleMonster boss = makeEliteBoss(monster, bossmob);
                spawnMonster(boss, -2);
                SetEliteBossCount(0);
                EliteBossDuplicate = true;
            }
        }
        
         /* Elite Monsters */ 
         if (monster.getId() == 8220022) {// Elite 1
             int rand = Randomizer.rand(5, 20);
                spawnMobDrop(new Item(4031102, (byte) 0, (short) rand, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
         if (monster.getId() == 8220023) { // Elite 2 
             int rand = Randomizer.rand(5, 20);
                spawnMobDrop(new Item(4031102, (byte) 0, (short) rand, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
         if (monster.getId() == 8220024) { // Elite 3 
             int rand = Randomizer.rand(5, 20);
                spawnMobDrop(new Item(4031102, (byte) 0, (short) rand, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
         if (monster.getId() == 8220025) { // Elite 4
             int rand = Randomizer.rand(5, 20);
                spawnMobDrop(new Item(4031102, (byte) 0, (short) rand, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
         if (monster.getId() == 8645009) { // Elite 5 Dunkel
            int rand = Randomizer.rand(50, 100);
                spawnMobDrop(new Item(4031102, (byte) 0, (short) rand, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
         
         
         /* Jcoins Bosses */
        if (monster.getId() == 5220002) {
            int rand = Randomizer.rand(10, 10);
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 10, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 5220000) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 20, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 9300119) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 30, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 8300006) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 50, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 8300007) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 60, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 9309205) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 80, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 9309208) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 110, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
            }
        if (monster.getId() == 8220011) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 130, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 9010009) {
                spawnMobDrop(new Item(4310034, (byte) 0, (short) 150, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        // Box
        if (monster.getId() == 9390710) {
                spawnMobDrop(new Item(4032101, (byte) 0, (short) 10, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
            }
        if (monster.getId() == 9390711) {
                spawnMobDrop(new Item(4032101, (byte) 0, (short) 20, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }  
        if (monster.getId() == 9390712) {
                spawnMobDrop(new Item(4032101, (byte) 0, (short) 30, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }    
        if (monster.getId() == 8220003) {
                spawnMobDrop(new Item(4032101, (byte) 0, (short) 50, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 2600800) {
                spawnMobDrop(new Item(4032101, (byte) 0, (short) 70, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }
        if (monster.getId() == 8644011) {
                spawnMobDrop(new Item(4032101, (byte) 0, (short) 80, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
        }  
        
        if(monster.getId() == 8800102) { // Zakum   
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001899, q);
            
            player.Message("Horntail's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8810122) { // Horntail
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 2, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001897, q);
            
            player.Message("Horntail's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 9101078) { // Flame Wolf
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 4, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001905, q);
            
            player.Message("Flame Wolf's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8870000) { // Hilla
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 6, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001906, q);
            
            player.Message("Hilla's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8840000) { // Von Leon
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 8, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001909, q);
            
            player.Message("Von Leon's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8860000) { // Arkarium
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 10, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001907, q);
            
            player.Message("Arkarium's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8850011) { // Cygnus
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 15, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001908, q);
            
            player.Message("Cygnus's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8880000) { // Magnus 
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 20, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
            
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001901, q);
            
            player.Message("Magnus's Crystal " + q + " Has been paid.");
        }
              
 
        }
        
        
        if(monster.getId() == 8880200) { // Omni-Cln
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 30, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001911, q);
            
            player.Message("Omni-Cln's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8820212) { // Pink Bean
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 40, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);    
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001903, q);
            
            player.Message("Pink Bean's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8900000) { // Chaos Pierre
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 50, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001917, q);
            
            player.Message("Chaos Pierre's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8910000) { // Chaos Von Bon
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 60, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001921, q);
            
            player.Message("Chaos Von Bon's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8920000) { // Chaos Queen
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 75, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001896, q);
            
            player.Message("Chaos Queen's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8930000) { // Chaos Vellum
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 80, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001922, q);
            
            player.Message("Chaos Vellum's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 9801029) { // Lotus
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 85, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001914, q);
            
            player.Message("Lotus's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8880131) { // Damien
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 90, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001912, q);
            
            player.Message("Damien's Crystal " + q + " Has been paid.");
        }

 
        }
        if(monster.getId() == 8880150) { // Lucid
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 95, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        
                
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001915, q);
            
            player.Message("Lucid's Crystal " + q + " Has been paid.");
        }  
               
        
 
        }
        if(monster.getId() == 9309207) { // Dorothy
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 100, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001913, q);
            
            player.Message("Dorothy's Crystal " + q + " Has been paid.");
        }  
 
        }
        if(monster.getId() == 9440025) { // Cross
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 115, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001916, q);
            
            player.Message("Cross's Crystal " + q + " Has been paid.");
        } 
 
        }
        if(monster.getId() == 8500002) { // Papulatus
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 120, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001904, q);
            
            player.Message("Papulatus's Crystal " + q + " Has been paid.");
        }
            
 
        }
        if(monster.getId() == 8880302) { // Will
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 125, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001919, q);
            
            player.Message("Will's Crystal " + q + " Has been paid.");
        }
           
 
        }
        if(monster.getId() == 6500001) { // Jin Hilla
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 150, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001902, q);
            
            player.Message("Will's Crystal " + q + " Has been paid.");
        }
  
 
        }
        if(monster.getId() == 8880503) { // Black Mage
        int rand = Randomizer.rand(1, 100);
                  
        if (rand < 40)
            spawnMobDrop(new Item(4310034, (byte) 0, (short) 200, (byte) 0), calcDropPos(new Point(0, monster.getPosition().x), monster.getPosition()), monster, chr, (byte) 1, (short) 0);
                  
        for (MaplePartyCharacter pchr : chr.getParty().getMembers()) {
            final MapleCharacter player = chr.getClient().getChannelServer().getPlayerStorage().getCharacterById(pchr.getId());
            int q = Randomizer.rand(1, 3);
            player.gainItem(4001892, q);
            
            player.Message("Will's Crystal " + q + " Has been paid.");
        }

 
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        MapleQuest q = MapleQuest.getInstance(10546);
        if (q != null && chr.getQuestNoAdd(q) != null && chr.getQuestStatus(10546) == 1) {
            int killCount = chr.getKeyValue("killCount") == null ? 0 : Integer.parseInt(chr.getKeyValue("killCount"));
            killCount++;
            if (killCount == 100) {
                MapleQuestStatus qs = chr.getQuestNoAdd(q);
                if (qs.getCustomData() == null || qs.getCustomData().isEmpty()) {
                    qs.setCustomData("0");

                }
                int feeds = Integer.parseInt(qs.getCustomData());
                if (feeds < 15) {
                    feeds++;
                    qs.setCustomData(feeds + "");
                    if (feeds == 15) {
                        qs.setStatus((byte) 2);
                    }
                    chr.updateQuest(qs);
                }
                killCount = 0;
            }
            chr.setKeyValue("killCount", killCount + "");
        }

        CollectionMob mobCollectionData = MonsterCollection.getInstance().getMobCollectionInfo(mobid);
        if (mobCollectionData != null) {
            if (Randomizer.nextInt(999999) <= 12000) {// TODO 확률

                if (chr.getKeyValue("mc" + mobCollectionData.getCollectionId()) != null) {
                    BigInteger b = new BigInteger(
                            new StringBuilder(chr.getKeyValue("mc" + mobCollectionData.getCollectionId())).reverse()
                                    .toString(),
                            16);
                    BigInteger mobFlag = BigInteger.ONE.shiftLeft(mobCollectionData.convertMobFlag());

                    if (b.and(mobFlag).compareTo(BigInteger.ZERO) == 0) {
                        b = b.or(mobFlag);

                        String s = "" + (mobCollectionData.getCollectionId() % 100) + "=";

                        StringBuilder sb = new StringBuilder(b.toString(16)).reverse();

                        for (int i = sb.length(); i < 48; i++) {
                            sb.append('0');
                        }

                        chr.setKeyValue("mc" + mobCollectionData.getCollectionId(), sb.toString());

                        chr.send(UIPacket.showWZEffect("Effect/BasicEff.img/monsterCollectionGet", 0));
                        chr.send(UIPacket.OnMonsterCollectionResult(12, 0, 0));
                        chr.send(UIPacket.OnCollectionRecordMessage(mobCollectionData.getCollectionId(),
                                s + sb.toString()));
                    }
                } else {
                    StringBuilder sb = new StringBuilder(
                            BigInteger.ONE.shiftLeft(mobCollectionData.convertMobFlag()).toString(16)).reverse();

                    String s = "" + (mobCollectionData.getCollectionId() % 100) + "=";

                    for (int i = sb.length(); i < 48; i++) {
                        sb.append('0');
                    }

                    chr.setKeyValue("mc" + mobCollectionData.getCollectionId(), sb.toString());

                    chr.send(UIPacket.showWZEffect("Effect/BasicEff.img/monsterCollectionGet", 0));
                    chr.send(UIPacket.OnMonsterCollectionResult(12, 0, 0));
                    chr.send(
                            UIPacket.OnCollectionRecordMessage(mobCollectionData.getCollectionId(), s + sb.toString()));
                }
            }
        }
    }

    public MapleMonster makeEliteMonster(final MapleMonster monster) {
        final MapleMonster elite = MapleLifeProvider.getMonster(monster.getId());
        final OverrideMonsterStats ostats = new OverrideMonsterStats();
        final MapleMonsterStats stats = elite.getStats();
        elite.setEliteMonster(true);
        elite.setEliteType(Randomizer.rand(0x70, 0x88));
        ostats.setOHp(elite.getMobMaxHp() * 30);
        ostats.setOMp(elite.getMobMaxMp());
        ostats.setOPad(stats.getPad() * 8);
        ostats.setOPhysicalDefense(stats.getPhysicalDefense());
        ostats.setOMad(stats.getMad() + 60);
        ostats.setOMagicDefense(stats.getMagicDefense());
        ostats.setOSpeed(stats.getSpeed() + 30);
        ostats.setOAcc(stats.getAcc());
        ostats.setOEva(stats.getEva());
        ostats.setOPushed(stats.getPushed() * 2);
        ostats.setOLevel(stats.getLevel());
        elite.setOverrideStats(ostats);
        elite.setPosition(monster.getTruePosition());
        elite.setFh(monster.getFh());
        return elite;
    }

    public MapleMonster makeEliteMonster(final MapleMonster monster, final Point ps) {
        final MapleMonster elite = MapleLifeProvider.getMonster(monster.getId());
        final OverrideMonsterStats ostats = new OverrideMonsterStats();
        final MapleMonsterStats stats = elite.getStats();
        elite.setEliteMonster(true);
        elite.setEliteType(Randomizer.rand(0x70, 0x88));
        ostats.setOHp(elite.getMobMaxHp() * 30);
        ostats.setOMp(elite.getMobMaxMp());
        ostats.setOPad(stats.getPad() * 8);
        ostats.setOPhysicalDefense(stats.getPhysicalDefense());
        ostats.setOMad(stats.getMad() + 60);
        ostats.setOMagicDefense(stats.getMagicDefense());
        ostats.setOSpeed(stats.getSpeed() + 30);
        ostats.setOAcc(stats.getAcc());
        ostats.setOEva(stats.getEva());
        ostats.setOPushed(stats.getPushed() * 2);
        ostats.setOLevel(stats.getLevel());
        elite.setOverrideStats(ostats);
        elite.setPosition(ps);
        elite.setFh(monster.getFh());
        return elite;
    }

    public MapleMonster makeEliteBoss(final MapleMonster sourcemob, final MapleMonster sourceboss) {
        final MapleMonster eliteboss = MapleLifeProvider.getMonster(sourceboss.getId());
        final OverrideMonsterStats ostats = new OverrideMonsterStats();
        eliteboss.setEliteBoss(true);
        eliteboss.setEliteType(Randomizer.rand(0x64, 0x88));
        ostats.setOHp(sourcemob.getMobMaxHp() * 75); // 필드몹의 75배
        ostats.setOMp(sourcemob.getMobMaxMp());
        ostats.setOPad(0);
        ostats.setOPhysicalDefense(sourcemob.getStats().getPhysicalDefense());
        ostats.setOMad((int) (sourcemob.getStats().getMad() * 2.5));
        ostats.setOMagicDefense(sourcemob.getStats().getMagicDefense());
        ostats.setOSpeed(sourcemob.getStats().getSpeed() + 35);
        ostats.setOAcc(sourcemob.getStats().getAcc());
        ostats.setOEva(sourcemob.getStats().getEva());
        ostats.setOPushed(0);
        ostats.setOLevel(sourcemob.getStats().getLevel());
        ostats.setOExp(0);
        eliteboss.setOverrideStats(ostats);
        eliteboss.setFh(sourcemob.getFh());
        return eliteboss;
    }

    private void time(final MapleCharacter chr) {
        MapTimer.getInstance().schedule(new Runnable() {
            public final void run() {
                chr.send(MainPacketCreator.removeMapEffect());
            }
        }, 5000L);
    }

    private void timeAllPlayer(final MapleMap map) {
        MapTimer.getInstance().schedule(new Runnable() {
            public final void run() {
                broadcastMessage(MainPacketCreator.removeMapEffect());
            }
        }, 5000L);
    }

    private void AlarmEliteBoss(final MapleMap currentmap, final MapleCharacter player, final int mobid) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public final void run() {
                if (currentmap.isEliteBossMap()) {
                    MapleWorldMapProvider mwmp = ChannelServer.getInstance(player.getClient().getChannel())
                            .getMapFactory();
                    Iterator itr = mwmp.getMaps().values().iterator();
                    while (itr.hasNext()) {
                        MapleMap target = ((MapleMap) itr.next());
                        if (target.getReturnMapId() == currentmap.getReturnMapId()) {
                            target.broadcastMessage(UIPacket.eliteBossNotice(2, currentmap.getId(), mobid));
                        }
                    }
                    mwmp = null;
                    itr = null;
                } else {
                    this.cancel();
                }
            }
        }, 0, 30000);
    }

    private void CancelEliteBossAlarm(final MapleMap currentmap, final MapleCharacter player) {
        MapleWorldMapProvider mwmp = ChannelServer.getInstance(player.getClient().getChannel()).getMapFactory();
        Iterator itr = mwmp.getMaps().values().iterator();
        while (itr.hasNext()) {
            MapleMap target = ((MapleMap) itr.next());
            if (target.getReturnMapId() == currentmap.getReturnMapId()) {
                target.broadcastMessage(UIPacket.eliteBossNotice(1, currentmap.getId(), 0));
            }
        }
        mwmp = null;
        itr = null;
    }

    public final void killAllMonsters(final boolean animate) {
        for (final MapleMapObject m : getAllMonster()) {
            MapleMonster monster = (MapleMonster) m;
            spawnedMonstersOnMap.decrementAndGet();
            monster.setHp(0);
            broadcastMessage(
                    MobPacket.killMonster(monster.getObjectId(), animate ? 1 : 0, GameConstants.isAswanMap(mapid)));
            removeMapObject(monster);
        }
    }

    public final void killMonster(final int monsId) {
        for (final MapleMapObject mmo : getAllMonster()) {
            if (((MapleMonster) mmo).getId() == monsId) {
                broadcastMessage(MobPacket.killMonster(mmo.getObjectId(), 1, GameConstants.isAswanMap(mapid)));
                spawnedMonstersOnMap.decrementAndGet();
                removeMapObject(mmo);
                break;
            }
        }
    }

    public final void killMonsterOid(final int oid) {
        for (final MapleMapObject mmo : getAllMonster()) {
            if (mmo.getObjectId() == oid) {
                broadcastMessage(MobPacket.killMonster(mmo.getObjectId(), 1, GameConstants.isAswanMap(mapid)));
                spawnedMonstersOnMap.decrementAndGet();
                removeMapObject(mmo);
                break;
            }
        }
    }

    public MapleReactor getReactorById(int id) {
        MapleReactor ret = null;
        Iterator<MapleMapObject> itr = mapobjects.get(MapleMapObjectType.REACTOR).values().iterator();
        while (itr.hasNext()) {
            MapleReactor n = (MapleReactor) itr.next();
            if (n.getReactorId() == id) {
                ret = n;
                break;
            }
        }
        return ret;
    }

    public final void destroyReactor(final int oid) {
        final MapleReactor reactor = getReactorByOid(oid);
        if (reactor == null) {
            return;
        }
        broadcastMessage(MainPacketCreator.destroyReactor(reactor));
        reactor.setAlive(false);
        removeMapObject(reactor);
        reactor.setTimerActive(false);

        if (reactor.getDelay() > 0) {
            MapTimer.getInstance().schedule(new Runnable() {
                @Override
                public final void run() {
                    respawnReactor(reactor);
                }
            }, reactor.getDelay());
        }
    }

    /*
	 * command to reset all item-reactors in a map to state 0 for GM/NPC use - not
	 * tested (broken reactors get removed from mapobjects when destroyed) Should
	 * create instances for multiple copies of non-respawning reactors...
     */
    public final void resetReactors(MapleClient c) {
        setReactorState(c, (byte) 0);
    }

    public final void setReactorState(MapleClient c) {
        setReactorState(c, (byte) 1);
    }

    public final void setReactorState(MapleClient c, byte state) {
        for (final MapleMapObject o : getAllReactor()) {
            ((MapleReactor) o).setState(state);
            ((MapleReactor) o).setTimerActive(false);
            broadcastMessage(MainPacketCreator.triggerReactor((MapleReactor) o, 1, c.getPlayer().getId()));
        }
    }

    public final void shuffleReactors() {
        List<Point> points = new ArrayList<Point>();
        for (final MapleMapObject o : getAllReactor()) {
            points.add(((MapleReactor) o).getPosition());
        }
        Collections.shuffle(points);
        for (final MapleMapObject o : getAllReactor()) {
            ((MapleReactor) o).setPosition(points.remove(points.size() - 1));
        }
    }

    /**
     * Automagically finds a new controller for the given monster from the chars
     * on the map...
     *
     * @param monster
     */
    public final void updateMonsterController(final MapleMonster monster) {
        if (!monster.isAlive()) {
            return;
        }
        if (monster.getController() != null) {
            if (monster.getController().getMap() != this) {
                monster.getController().stopControllingMonster(monster);
            } else {
                return;
            }
        }
        int mincontrolled = -1;
        MapleCharacter newController = null;
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter chr = (MapleCharacter) _obj;
            if ((!chr.isHidden()) && ((chr.getControlledMonsters().size() < mincontrolled) || (mincontrolled == -1))) {
                mincontrolled = chr.getControlledMonsters().size();
                newController = chr;
            }
        }
        if (newController != null) {
            if (monster.isFirstAttack() && monster.getId() != 8220028) {
                newController.controlMonster(monster, true);
                if (monster.getId() != 8220028) {
                    monster.setControllerHasAggro(true);
                    monster.setControllerKnowsAboutAggro(true);
                }
            } else {
                newController.controlMonster(monster, false);
            }
        }
    }

    public final boolean containsNPC(final int npcid) {
        for (MapleMapObject obj : getAllNPC()) {
            if (((MapleNPC) obj).getId() == npcid) {
                return true;
            }
        }
        return false;
    }

    public MapleMonster getMonsterById(int id) {
        MapleMonster ret = null;
        Iterator<MapleMapObject> itr = mapobjects.get(MapleMapObjectType.MONSTER).values().iterator();
        while (itr.hasNext()) {
            MapleMonster n = (MapleMonster) itr.next();
            if (n.getId() == id) {
                ret = n;
                break;
            }
        }
        return ret;
    }

    public int countMonsterById(int id) {
        int ret = 0;
        Iterator<MapleMapObject> itr = mapobjects.get(MapleMapObjectType.MONSTER).values().iterator();
        while (itr.hasNext()) {
            MapleMonster n = (MapleMonster) itr.next();
            if (n.getId() == id) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * returns a monster with the given oid, if no such monster exists returns
     * null
     *
     * @param oid
     * @return
     */
    public final MapleMonster getMonsterByOid(final int oid) {
        MapleMapObject mmo = getMapObject(oid, MapleMapObjectType.MONSTER);
        if (mmo == null) {
            return null;
        }
        return (MapleMonster) mmo;
    }

    public final MapleNPC getNPCByOid(final int oid) {
        MapleMapObject mmo = getMapObject(oid, MapleMapObjectType.NPC);
        if (mmo == null) {
            return null;
        }
        return (MapleNPC) mmo;
    }

    public final MapleNPC getNPCById(final int id) {
        for (MapleMapObject hmo : getAllNPC()) {
            MapleNPC d = (MapleNPC) hmo;
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public final MapleReactor getReactorByOid(final int oid) {
        MapleMapObject mmo = getMapObject(oid, MapleMapObjectType.REACTOR);
        if (mmo == null) {
            return null;
        }
        return (MapleReactor) mmo;
    }

    public final MapleReactor getReactorByName(final String name) {
        for (final MapleMapObject obj : getAllReactor()) {
            if (((MapleReactor) obj).getName().equals(name)) {
                return (MapleReactor) obj;
            }
        }
        return null;
    }

    public final MapleReactor getReactor(final int rid) {
        for (final MapleMapObject obj : getAllReactor()) {
            if (((MapleReactor) obj).getReactorId() == rid) {
                return (MapleReactor) obj;
            }
        }
        return null;
    }

    public final void spawnTempNpc(final int id, final int x, final int y, final int owner) {
        final MapleNPC npc = MapleLifeProvider.getNPC(id);
        final Point pos = new Point(x, y);
        npc.setPosition(pos);
        npc.setCy(y);
        npc.setRx0(x + 50);
        npc.setRx1(x - 50);
        npc.setFh(getFootholds().findMaple(pos).getId());
        npc.setTemp(true);
        addMapObject(npc);
        tempnpcs3.put(owner, npc);
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter chr = (MapleCharacter) _obj;
            if (chr.getId() == owner) {
                chr.send(MainPacketCreator.spawnNPC(npc, true));
            }
        }
    }

    public final void removeTempNpc(final int id, final int owner) {

        for (final MapleMapObject npcmo : getAllNPC()) {
            final MapleNPC npc = (MapleNPC) npcmo;
            if (npc.isTemp() && npc.getId() == id && tempnpcs3.get(owner).getId() == id) {
                broadcastMessage(MainPacketCreator.removeNPC(npc.getObjectId()));
                removeMapObject(npc);
            }
        }
    }

    public final void spawnNpcForPlayer(MapleClient c, final int id, final Point pos) {
        final MapleNPC npc = MapleLifeProvider.getNPC(id);
        npc.setPosition(pos);
        npc.setCy(pos.y);
        npc.setRx0(pos.x + 50);
        npc.setRx1(pos.x - 50);
        npc.setFh(getFootholds().findMaple(pos).getId());
        npc.setCustom(true);
        addMapObject(npc);
        c.getSession().writeAndFlush(MainPacketCreator.spawnNPC(npc, true));
    }

    public final void spawnNpc(final int id, final Point pos) {
        final MapleNPC npc = MapleLifeProvider.getNPC(id);
        npc.setPosition(pos);
        npc.setCy(pos.y);
        npc.setRx0(pos.x + 50);
        npc.setRx1(pos.x - 50);
        npc.setFh(getFootholds().findMaple(pos).getId());
        npc.setCustom(true);
        addMapObject(npc);
        broadcastMessage(MainPacketCreator.spawnNPC(npc, true));
    }

    public final void removeNpc(final int id) {
        Iterator<MapleMapObject> itr = getAllNPC().iterator();
        while (itr.hasNext()) {
            MapleNPC npc = (MapleNPC) itr.next();
            if (npc.isCustom() && (id == -1 || npc.getId() == id)) {
                broadcastMessage(MainPacketCreator.removeNPCController(npc.getObjectId()));
                broadcastMessage(MainPacketCreator.removeNPC(npc.getObjectId()));
                removeMapObject(npc);
                break;
            }
        }
    }

    public final void spawnMonster_sSack(final MapleMonster mob, final Point pos, final int spawnType) {
        spawnMonster_sSack(mob, pos, spawnType, 0);
    }

    public final void spawnMonster_sSack(final MapleMonster mob, final Point pos, final int spawnType, int effect) {
        final Point spos = calcPointMaple(new Point(pos.x, pos.y - 100));
        mob.setPosition(spos);
        spawnMonster(mob, spawnType, effect);
    }

    public final int getMapId() {
        return mapid;
    }

    public final void spawnMonsterWithEffectBelow(final MapleMonster mob, final Point pos, final int effect) {
        final Point spos = calcPointMaple(new Point(pos.x, pos.y - 1));
        spawnMonsterWithEffect(mob, effect, spos);
    }

    public final void spawnMonsterOnGroundBelow(final MapleMonster mob, final Point pos) {
        spawnMonster_sSack(mob, pos, -2);
    }

    public final void spawnTempMonster(final int key, final int id, final Point pos) {
        if (tempmonsters3.containsKey(key) && getAllPlayer().size() == 1) {
            killMonster(tempmonsters3.get(key));
        }
        final MapleMonster mob = MapleLifeProvider.getMonster(id);
        tempmonsters3.put(key, mob);
        spawnMonsterOnGroundBelow(mob, pos);
    }

    public final void spawnFakeMonsterOnGroundBelow(final MapleMonster mob, final Point pos) {
        Point spos = calcPointMaple(new Point(pos.x, pos.y - 1));
        spos.y -= 1;
        mob.setPosition(spos);
        spawnFakeMonster(mob);
    }

    public final void spawnZakum(final Point pos) {
        final Point spos = calcPointMaple(new Point(pos.x, pos.y - 100));
        final int[] zakpart = {8800002, 8800003, 8800004, 8800005, 8800006, 8800007, 8800008, 8800009, 8800010};

        for (final int i : zakpart) {
            final MapleMonster part = MapleLifeProvider.getMonster(i);
            part.setFake(true);
            part.setPosition(spos);
            spawnFakeMonster(part);
        }
    }

    public final void spawnChaosZakum(final int x, final int y) {
        final Point pos = new Point(x, y);
        final MapleMonster mainb = MapleLifeProvider.getMonster(8800100);
        final Point spos = calcPointMaple(new Point(pos.x, pos.y - 100));
        mainb.setPosition(spos);
        mainb.setFake(true);

        // Might be possible to use the map object for reference in future.
        spawnFakeMonster(mainb);

        final int[] zakpart = {8800103, 8800104, 8800105, 8800106, 8800107, 8800108, 8800109, 8800110};

        for (final int i : zakpart) {
            final MapleMonster part = MapleLifeProvider.getMonster(i);
            part.setPosition(spos);

            spawnMonster(part, -2);
        }
    }

    public final void spawnFakeMonsterOnGroundMaple(final MapleMonster mob, final Point pos) {
        Point spos = new Point(pos.x, pos.y - 100);
        spos = calcPointMaple(spos);
        spos.y -= 1;
        mob.setPosition(spos);
        spawnFakeMonster(mob);
    }

    private final void checkRemoveAfter(final MapleMonster monster) {
        final int ra = monster.getStats().getRemoveAfter();

        if (ra > 0) {
            MapTimer.getInstance().schedule(new Runnable() {
                @Override
                public final void run() {
                    if (monster != null) {
                        killMonster(monster);
                    }
                }
            }, ra * 1000);
        }
    }

    public final void spawnRevives(final MapleMonster monster, final int oid) {
        monster.setMap(this);
        checkRemoveAfter(monster);
        monster.setLinkOid(oid);
        spawnAndAddRangedMapObject(monster, new DelayedPacketCreation() {
            @Override
            public final void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(MobPacket.spawnMonster(monster, -2, 0, oid,
                        GameConstants.isAswanMap(c.getPlayer().getMapId()))); // TODO effect
            }
        });
        updateMonsterController(monster);
        spawnedMonstersOnMap.incrementAndGet();
    }

    public final void spawnMonster(final MapleMonster monster, final int spawnType) {
        spawnMonster(monster, spawnType, 0);
    }

    public final void spawnMonster(final MapleMonster monster, final int spawnType, final int effect) {
        monster.setMap(this);
        spawnAndAddRangedMapObject(monster, new DelayedPacketCreation() {
            public final void sendPackets(MapleClient c) {
                if (c != null) {
                    c.getSession().writeAndFlush(MobPacket.spawnMonster(monster, spawnType, effect, 0,
                            GameConstants.isAswanMap(c.getPlayer().getMapId())));
                    // c.send(MobPacket.MobAttackBlock(monster.getObjectId(),
                    // Collections.EMPTY_LIST));
                    // c.send(MobPacket.MobAttackBlock(monster.getObjectId() + 1,
                    // Collections.EMPTY_LIST));
                    // c.send(MobPacket.MobChangePhase(monster.getObjectId(), 1, false));
                }
            }
        });
        updateMonsterController(monster);
        checkRemoveAfter(monster);
        spawnedMonstersOnMap.incrementAndGet();
    }

    public final void spawnMonsterWithEffect(final MapleMonster monster, final int effect, Point pos) {
        try {
            monster.setMap(this);
            monster.setPosition(pos);

            spawnAndAddRangedMapObject(monster, new DelayedPacketCreation() {
                @Override
                public final void sendPackets(MapleClient c) {
                    c.getSession().writeAndFlush(MobPacket.spawnMonster(monster, -2, effect, 0,
                            GameConstants.isAswanMap(c.getPlayer().getMapId())));
                }
            });
            updateMonsterController(monster);

            spawnedMonstersOnMap.incrementAndGet();
        } catch (Exception e) {
        }
    }

    public final void spawnFakeMonster(final MapleMonster monster) {
        monster.setMap(this);
        monster.setFake(true);

        spawnAndAddRangedMapObject(monster, new DelayedPacketCreation() {
            @Override
            public final void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(MobPacket.spawnMonster(monster, -2, 0, 0, false));
            }
        });
        updateMonsterController(monster);

        spawnedMonstersOnMap.incrementAndGet();
    }

    public final void spawnRune(final MapleRune rune) {
        rune.setMap(this);
        spawnAndAddRangedMapObject(rune, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                /* Respawn Effect 발동 */
                c.getSession().writeAndFlush(RunePacket.spawnRune(rune, true));
                c.getSession().writeAndFlush(RunePacket.spawnRune(rune, false));
            }
        });
    }

    public final void spawnReactor(final MapleReactor reactor) {
        reactor.setMap(this);
        spawnAndAddRangedMapObject(reactor, new DelayedPacketCreation() {
            @Override
            public final void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(MainPacketCreator.spawnReactor(reactor));
            }
        });
    }

    private final void respawnReactor(final MapleReactor reactor) {
        if (reactor.getReactorId() >= 100000 && reactor.getReactorId() <= 200011 && reactor.getRank() > 0) {
            int reactid = GameConstants.getRandomProfessionReactorByRank(reactor.getRank());
            final MapleReactorStats stats = MapleReactorFactory.getReactor(reactid);
            final MapleReactor myReactor = new MapleReactor(stats, reactid);
            myReactor.setPosition(reactor.getPosition());
            myReactor.setDelay(900000);
            myReactor.setState((byte) 0);
            myReactor.setName("광맥");
            myReactor.setRank(reactor.getRank());
            spawnReactor(myReactor);
        } else {
            reactor.setState((byte) 0);
            reactor.setAlive(true);
            spawnReactor(reactor);
        }
    }

    public final void spawnDoor(final MapleDoor door) {
        spawnAndAddRangedMapObject(door, new DelayedPacketCreation() {
            public final void sendPackets(MapleClient c) {
                door.sendSpawnData(c, true);
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            }
        });
    }

    public final void spawnMechDoor(final MapleMechDoor door) {
        spawnAndAddRangedMapObject(door, new DelayedPacketCreation() {
            public final void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(MechanicSkill.mechDoorSpawn(door, true));
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            }
        });
    }

    public final void spawnDragon(final MapleDragon summon) {
        spawnAndAddRangedMapObject(summon, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(MainPacketCreator.spawnDragon(summon));
            }
        });
    }

    public final void spawnSummon(final MapleSummon summon, final boolean animated, final int duration) {
        try {
            spawnAndAddRangedMapObject(summon, new DelayedPacketCreation() {
                @Override
                public void sendPackets(MapleClient c) {
                    c.getSession().writeAndFlush(MainPacketCreator.spawnSummon(summon, summon.getSkillLevel(), animated));
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public final void spawnExtractor(final MapleExtractor ex) {
        spawnAndAddRangedMapObject(ex, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                ex.sendSpawnData(c);
            }
        });
    }

    public final void spawnClockMist(final MapleMist clock) {
        spawnAndAddRangedMapObject(clock, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                broadcastMessage(MainPacketCreator.spawnClockMist(clock));
            }
        });
        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                broadcastMessage(MainPacketCreator.removeMist(clock.getObjectId(), false));
                removeMapObject(clock);
            }
        }, 22000);
    }

    public final void spawnMist(final MapleMist mist, final int duration, boolean poison, boolean fake, boolean rv,
            boolean burningregion, boolean timecapsule) {
        if (this.mapid == 100000000) {
            return;
        }
        if (mist.getSourceSkill().getId() == 33111013 || mist.getSourceSkill().getId() == 51120057 || mist.getSourceSkill().getId() == 4121015 
                || mist.getSourceSkill().getId() == 35121052 || mist.getSourceSkill().getId() == 400041041) {
            for (MapleMapObject mis : mist.getOwner().getMap().getAllMist()) {
                MapleMist mm = (MapleMist) mis;
                if (mm.getSourceSkill().getId() == mist.getSourceSkill().getId()) {
                    int oid = mm.getObjectId();
                    removeMapObject(mm);
                    broadcastMessage(MainPacketCreator.removeMist(oid, false));
                }
            }  
        }

        mist.setEndTime(duration);
        mist.setMap(this);
        spawnAndAddRangedMapObject(mist, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.sendPacket(MainPacketCreator.spawnMist(mist));
            }
        });

        final MapTimer tMan = MapTimer.getInstance();
        final ScheduledFuture<?> poisonSchedule;

        if (poison) {
            poisonSchedule = tMan.register(new Runnable() {
                @Override
                public void run() {
                    for (final MapleMapObject mo : getMapObjectsInRect(mist.getBox(),
                            Collections.singletonList(MapleMapObjectType.MONSTER))) {
                        if (mist.makeChanceResult()) {
                            ((MapleMonster) mo).applyStatus(mist.getOwner(),
                                    new MonsterStatusEffect(Collections.singletonMap(MonsterStatus.POISON, 1),
                                            mist.getSourceSkill(), 0, null, false),
                                    duration);
                        }
                    }
                }
            }, 2000, 2500);
        } else if (rv) {
            poisonSchedule = tMan.register(new Runnable() {
                @Override
                public void run() {
                    for (final MapleMapObject mo : getMapObjectsInRect(mist.getBox(),
                            Collections.singletonList(MapleMapObjectType.PLAYER))) {
                        if (mist.makeChanceResult()) {
                            final MapleCharacter chr = ((MapleCharacter) mo);
                            chr.addMP((int) (mist.getSource().getX() * (chr.getStat().getMaxMp() / 100.0)));
                        }
                    }
                }
            }, 2000, 2500);
        } else if (burningregion) {
            poisonSchedule = tMan.register(new Runnable() {
                @Override
                public void run() {
                    for (final MapleMapObject mo : getMapObjectsInRect(mist.getBox(),
                            Collections.singletonList(MapleMapObjectType.PLAYER))) {
                        final MapleCharacter chr = ((MapleCharacter) mo);
                        final ISkill skill = SkillFactory.getSkill(GameConstants.getLinkedAttackSkill(12121005));
                        final SkillStatEffect effect = skill
                                .getEffect(chr.getSkillLevel(mist.getOwner().getSkillLevel(skill)));
                        if (!chr.isActiveBuffedValue(12121005)) {
                            effect.applyTo(chr);
                        }
                    }
                }
            }, 2000, 2500);
        } else if (timecapsule) {
            poisonSchedule = tMan.register(new Runnable() {
                @Override
                public void run() {
                    for (MapleMapObject mmo : getMapObjectsInRect(mist.getBox(),
                            Collections.singletonList(MapleMapObjectType.PLAYER))) {
                        MapleCharacter chr = (MapleCharacter) mmo;
                        for (final MapleMapObject mistoo : chr.getMap().getMapObjectsInRange(chr.getPosition(),
                                Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MIST))) {
                            final MapleMist check = (MapleMist) mistoo;
                            if (mist.getOwner() == check.getOwner() && mist.isTimeCapsule()) {
                                for (MapleCoolDownValueHolder mcdvh : chr.getAllCooldowns()) {
                                    if (mcdvh.skillId != 36121007) {
                                        chr.changeCooldown(mcdvh.skillId, -15000);
                                    }
                                }
                            } else {
                                return;
                            }
                        }
                    }
                }
            }, 5000, 5000);
        } else if (mist.getSource().isMonsterBuff()) {
            poisonSchedule = tMan.register(new Runnable() {
                @Override
                public void run() {
                    mist.getSource().applyMonsterBuff(mist.getOwner());
                }
            }, 2000, 2500);
        } else if (mist.getSource().getSourceId() == 400001017) {
            poisonSchedule = tMan.register(new Runnable() {
                @Override
                public void run() {
                    for (MapleMapObject mmo : getMapObjectsInRect(mist.getBox(),
                            Collections.singletonList(MapleMapObjectType.PLAYER))) {
                        MapleCharacter chr = (MapleCharacter) mmo;

                        for (final MapleMapObject mistoo : chr.getMap().getMapObjectsInRange(chr.getPosition(),
                                Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MIST))) {
                            final MapleMist check = (MapleMist) mistoo;
                            if (mist.getOwner() == check.getOwner() && check.getSource().getSourceId() == 400001017) {
                                if (chr.getPartyId() != mist.getOwner().getPartyId()) {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }, 5000, 5000);
        } else {
            poisonSchedule = null;
        }
        tMan.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    int oid = mist.getObjectId();
                    if (mist.getOwner().isActiveBuffedValue(12121005)) {
                        mist.getOwner().cancelEffectFromBuffStat(BuffStats.IndieBooster, 12121005);
                        mist.getOwner().cancelEffectFromBuffStat(BuffStats.IndieDamR, 12121005);
                    }
                    removeMapObject(mist);
                    broadcastMessage(MainPacketCreator.removeMist(oid, false));
                    if (poisonSchedule != null) {
                        poisonSchedule.cancel(false);
                    }
                    if (mist.getSource().getSourceId() == 35121052) {
                        mist.getOwner().acaneAim = 0;
                    }
                } catch (Exception ex) {

                }
            }
        }, duration);
    }

    public final void checkMaxItemInMap() {
        if (droppedItems.size() + 1 > 400) {
            MapleWorldMapItem mapitem = (MapleWorldMapItem) getMapObject(droppedItems.get(0), MapleMapObjectType.ITEM);
            if (mapitem == null) {
                return;
            }
            if (mapitem.isPickedUp()) {
                return;
            }
            mapitem.setPickedUp(true);
            broadcastMessage(MainPacketCreator.removeItemFromMap(mapitem.getObjectId(), 0, 0));
            removeMapObject(mapitem);
        }
    }

    public final void disappearingItemDrop(final MapleMapObject dropper, final MapleCharacter owner, final IItem item,
            final Point pos) {
        final Point droppos = calcDropPos(pos, pos);
        final MapleWorldMapItem drop = new MapleWorldMapItem(item, droppos, dropper, owner, (byte) 1, false);
        broadcastMessage(MainPacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte) 3),
                drop.getPosition());
    }

    public final void spawnMesoDrop(final int meso, final Point position, final MapleMapObject dropper,
            final MapleCharacter owner, final boolean playerDrop, final byte droptype) {

        final Point droppos = calcDropPos(position, position);
        final MapleWorldMapItem mdrop = new MapleWorldMapItem(meso, droppos, dropper, owner, droptype, playerDrop);
        checkMaxItemInMap();
        spawnAndAddRangedMapObject(mdrop, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(
                        MainPacketCreator.dropItemFromMapObject(mdrop, dropper.getPosition(), droppos, (byte) 1));
            }
        });
        if (!everlast) {
            MapTimer.getInstance().schedule(new ExpireMapItemJob(mdrop, owner), 60000L);
        }
    }

    public final void spawnMobMesoDrop(final int meso, final Point position, final MapleMapObject dropper,
            final MapleCharacter owner, final boolean playerDrop, final byte droptype) {
        final MapleWorldMapItem mdrop = new MapleWorldMapItem(meso, position, dropper, owner, droptype, playerDrop);

        checkMaxItemInMap();
        spawnAndAddRangedMapObject(mdrop, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(
                        MainPacketCreator.dropItemFromMapObject(mdrop, dropper.getPosition(), position, (byte) 1));
            }
        });
        MapTimer.getInstance().schedule(new ExpireMapItemJob(mdrop, owner), 60000L);
    }

    public final void spawnMobDrop(final IItem idrop, final Point dropPos, final MapleMonster mob,
            final MapleCharacter chr, final byte droptype, final int questid) {
        if (idrop.getItemId() == -1) {
            return;
        }
        final MapleWorldMapItem mdrop = new MapleWorldMapItem(idrop, dropPos, mob, chr, droptype, false, questid);
        checkMaxItemInMap();
        // Start nx block from fm
        int[] nxItems = {5150030, 5151025, 5152033, 5152035, 1002186, 1082102, 1002999, 1052211, 1072175, 1003000,
            1052212, 1003001, 1052213, 1072406, 1002998, 1052210, 1072404};
        if (mapid != 104040000) { // HHG1
            for (int i : nxItems) {
                if (mdrop.getItemId() == i) {
                    return;
                }
            }
        }
        spawnAndAddRangedMapObject(mdrop, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                if (questid <= 0 || c.getPlayer().getQuestStatus(questid) == 1) {
                    c.getSession().writeAndFlush(
                            MainPacketCreator.dropItemFromMapObject(mdrop, mob.getPosition(), dropPos, (byte) 1));
                }
            }
        });
        MapTimer.getInstance().schedule(new ExpireMapItemJob(mdrop, chr), 60000L);
        activateItemReactors(mdrop, chr.getClient());
    }

    public final void spawnItemDrop(MapleMapObject dropper, MapleCharacter owner, IItem item, Point pos,
            boolean ffaDrop, boolean playerDrop) {
        spawnItemDrop(dropper, owner, item, pos, ffaDrop, playerDrop, false, false, 0, 0);
    }

    public final void spawnItemDrop(final MapleMapObject dropper, final MapleCharacter owner, final IItem item,
            Point pos, final boolean ffaDrop, final boolean playerDrop, boolean fly, boolean touch, int gradiant,
            int speed) {

        checkMaxItemInMap();
        IEquip equip = null;
        if (item.getType() == 1) {
            equip = (IEquip) item;
        }
        final Point droppos = calcDropPos(pos, pos);
        final MapleWorldMapItem drop = new MapleWorldMapItem(item, droppos, dropper, owner, (byte) 0, playerDrop, equip,
                fly, touch, gradiant, speed);

        spawnAndAddRangedMapObject(drop, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(
                        MainPacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte) 1));
            }
        });
        broadcastMessage(MainPacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte) 0));

        if (!everlast) {
            MapTimer.getInstance().schedule(new ExpireMapItemJob(drop, owner), 60000L);
            activateItemReactors(drop, owner.getClient());
        }
    }

    private void activateItemReactors(final MapleWorldMapItem drop, final MapleClient c) {
        final IItem item = drop.getItem();

        for (final MapleMapObject o : getAllReactor()) {
            final MapleReactor react = (MapleReactor) o;
            for (int i = 0; i < react.getStats().getStateEventSize(react.getState()); i++) {
                if (react.getReactorType((byte) i) == 100) {
                    if (react.getReactItem((byte) i).getLeft() == item.getItemId()
                            && react.getReactItem((byte) i).getRight() == item.getQuantity()) {
                        if (react.getArea().contains(drop.getPosition())) {
                            if (!react.isTimerActive()) {
                                MapTimer.getInstance().schedule(new ActivateItemReactor(drop, react, c), 5000);
                                react.setTimerActive(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public final void returnEverLastItem(final MapleCharacter chr) {
        for (final MapleMapObject o : getAllItems()) {
            final MapleWorldMapItem item = ((MapleWorldMapItem) o);
            item.setPickedUp(true);
            broadcastMessage(MainPacketCreator.removeItemFromMap(item.getObjectId(), 2, chr.getId()),
                    item.getPosition());
            if (item.getMeso() > 0) {
                chr.gainMeso(item.getMeso(), false);
            } else {
                InventoryManipulator.addFromDrop(chr.getClient(), item.getItem(), false);
            }
            removeMapObject(item);
        }
    }

    public final void startSimpleMapEffect(final String msg, final int itemId) {
        broadcastMessage(MainPacketCreator.startMapEffect(msg, itemId, true));
    }

    public final void startMapEffect(final String msg, final int itemId) {
        startMapEffect(msg, itemId, 30000);
    }

    public final void startMapEffect(final String msg, final int itemId, final long time) {
        if (mapEffect != null) {
            return;
        }
        mapEffect = new MapleMapEffect(msg, itemId);
        broadcastMessage(mapEffect.makeStartData());
        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                broadcastMessage(mapEffect.makeDestroyData());
                mapEffect = null;
            }
        }, time);
    }

    public boolean isSwooBoss() {
        return mapid == 350060160 || mapid == 350060180 || mapid == 350060200;
    }

    public void resetObtacleAtom() {
        if (!isSwooBoss()) {
            System.out.println("@MapleMap::resetObtacleAtom | not swoo boss map");

            return;
        }
        /*
		 * int count = Randomizer.nextInt(10) + 5;
		 * 
		 * for (int i = 0; i < count; i++) {
		 * 
		 * /* final int type, final int sx, final int sy, final int ex, final int ey,
		 * final int range, final int pdam, final int mdam, final int delay, final int
		 * high, final int speed, final int len, final int float_
         */
        // -603 703
        /*
		 * int x = Randomizer.rand(-603, 703); ObtacleAtom oa = new
		 * ObtacleAtom(Randomizer.rand(48, 52), x, -10, // sx, sy 0, 0, // ex, ey 50000,
		 * // range 25, -5, 1000, // delay 10000800, // high (duration?) 10, 700000,
		 * 250);
		 * 
		 * obtacleAtoms.add(oa); }
         */
    }
    
    public void removeRune() {
        this.rune = null;
    }

    public final void addPlayer(final MapleCharacter chr) {
        if (chr.getKeyValue("rc_shot") != null) {
            chr.BuckShot();
        }
        if (chr.getKeyValue("scrue2") != null) {
            chr.cancelEffectFromBuffStat(BuffStats.SerpentScrewOnOff, 400051015);
        }
        if (chr.getKeyValue("fireaura2") != null) {
            SkillFactory.getSkill(2121054).getEffect(1).applyTo(chr, chr.getPosition());
        }
        if (chr.getKeyValue("scrue3") != null) {
            SkillFactory.getSkill(65111007).getEffect(1).applyTo(chr, chr.getPosition());
        }
        if (chr.getKeyValue("Donate_Trifle") != null) {
            SkillFactory.getSkill(13120003).getEffect(1).applyTo(chr, chr.getPosition());
        }
        if (chr.getKeyValue("magicg2") != null) {
            SkillFactory.getSkill(2001002).getEffect(1).applyTo(chr, chr.getPosition());
        }

        mapobjects.get(MapleMapObjectType.PLAYER).put(chr.getObjectId(), chr);

        RespawnNPC();

        if (!chr.isHidden()) {
            broadcastMessage(chr, MainPacketCreator.spawnPlayerMapobject(chr), false);
        }
        sendObjectPlacement(chr);

        if (!onFirstUserEnter.equals("")) {
            if (getCharactersSize() == 1) {
                if (ServerConstants.showPackets) {
                    // System.out.println("[" + this.getMapName() + "] [" + this.mapid + "] " +
                    // onFirstUserEnter);
                }
                MapScriptMethods.startScript_FirstUser(chr.getClient(), onFirstUserEnter);
            }
        }

        if (!onUserEnter.equals("")) {
            if (ServerConstants.showPackets) {
                // System.out.println("[" + this.getMapName() + "] [" + this.mapid + "] " +
                // onUserEnter);
            }
            MapScriptMethods.startScript_User(chr.getClient(), onUserEnter);
        }

        if (isSwooBoss()) {
            // chr.getClient().send(MobPacket.spawnObtacleAtomBomb(obtacleAtoms));
        }

        if (mapid == ServerConstants.startMap) {
            mapEffect = new MapleMapEffect(ServerConstants.serverWelcome, 5121035);
            chr.send(mapEffect.makeStartData());
        }

        if (mapid == ServerConstants.startMap || mapid == 100000000) {
            // chr.send(MainPacketCreator.musicChange("BgmEvent2/risingStar2"));
            // chr.send(MainPacketCreator.musicChange("Bgm69/Town"));
        }

        if (QuickMove.getQuickMoves(mapid) != null) {
            chr.send(MainPacketCreator.getQuickMove(QuickMove.getQuickMoves(mapid)));
            chr.setQuickMoved(true);
        }

        if (chr.getJob() >= 1400 && chr.getJob() <= 1412) {
            chr.acaneAim = 0;
        }

        for (int i = 0; i < 3; ++i) {
            if (chr.getPet(i) != null) {
                chr.getPet(i).setPos(chr.getPosition()); // 펫 좌표 업데이트
                chr.getClient().send(PetPacket.updatePet(chr, chr.getPet(i), false, chr.getPetLoot()));
                chr.send(PetPacket.showPet(chr, chr.getPet(i), false, false, false));
                broadcastMessage(chr, PetPacket.showPet(chr, chr.getPet(i), false, false, true), false);
            }
        }

        if (chr.getPetAutoHP() > 0) {
            chr.getClient().send(MainPacketCreator.getPetAutoHP(chr.getPetAutoHP()));
        }

        if (chr.getPetAutoMP() > 0) {
            chr.getClient().send(MainPacketCreator.getPetAutoMP(chr.getPetAutoMP()));
        }

        if (chr.getAndroid() != null) { // Set
            chr.getAndroid().setPosition(chr.getPosition()); // 안드로이드 좌표 업데이트
            broadcastMessage(chr, AndroidPacket.spawnAndroid(chr, chr.getAndroid()), true);
        }

        if (getHPDec() > 0) {
            chr.startHurtHp();
        }

        if (chr.getParty() != null) {
            chr.silentPartyUpdate();
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.updateParty(chr.getClient().getChannel(),
                    chr.getParty(), MaplePartyOperation.SILENT_UPDATE, null));
            chr.updatePartyMemberHP();
            chr.receivePartyMemberHP();
        }

        if (!chr.getSummons().isEmpty()) {
            for (Pair<Integer, MapleSummon> summon : chr.getSummons().values()) {
                if (!summon.right.isStaticSummon()) {
                    summon.right.setPosition(chr.getPosition());
                    chr.addVisibleMapObject(summon.right);
                    spawnSummon(summon.right, false, SkillFactory.getSkill(summon.right.getSkill())
                            .getEffect(summon.right.getSkillLevel()).getDuration());
                }
            }
        }

        if (mapEffect != null) {
            mapEffect.sendStartData(chr.getClient());
        }

        if (timeLimit > 0 && getForcedReturnMap() != null) {
            chr.startMapTimeLimitTask(timeLimit, getForcedReturnMap());
        }

        if (chr.getBuffedValue(BuffStats.MonsterRiding) != null) {
            if (FieldLimitType.Mount.check(fieldLimit)) {
                chr.cancelBuffStats(-1, BuffStats.MonsterRiding);
            }
        }

        if (hasClock()) {
            final Calendar cal = Calendar.getInstance();
            chr.getClient().getSession().writeAndFlush((MainPacketCreator.getClockTime(cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND))));
        }

        if (chr.getEventInstance() != null) {
            chr.getEventInstance().onMapLoad(chr);
            if (chr.getEventInstance().isTimerStarted()) {
                chr.getClient()
                        .sendPacket(MainPacketCreator.getClock((int) (chr.getEventInstance().getTimeLeft() / 1000)));
            }
        }

        if (GameConstants.isEvan(chr.getJob()) && chr.getJob() >= 2200
                && chr.getBuffedValue(BuffStats.MonsterRiding) == null) {
            if (chr.getDragon() == null) {
                chr.makeDragon();
            }
            spawnDragon(chr.getDragon());
            updateMapObjectVisibility(chr, chr.getDragon());
        }

        // for (MapleDiseaseValueHolder hdvh : chr.getAllDiseases()) {
        // for (Pair<DiseaseStats, Integer> p : GameConstants.getBroadcastDebuffs()) {
        // if (hdvh.disease == p.getLeft()) {
        // broadcastMessage(MainPacketCreator.giveForeignDebuff(chr.getId(),
        // p.getLeft(), new MobSkill(p.getRight(), 1)));
        // }
        // }
        // }
        if (!isExpiredMapTimer()) {
            long lefttime = maptimer - System.currentTimeMillis();
            int sec = (int) (lefttime / 1000);
            chr.send(MainPacketCreator.getClock(sec));
        }

        if (isEliteBossMap()) {
            chr.send(UIPacket.showSpecialMapEffect(2, 1, "Bgm36.img/RoyalGuard", null));
        } else if (isEliteBossRewardMap()) {
            chr.send(
                    UIPacket.showSpecialMapEffect(3, 1, "Bgm36.img/HappyTimeShort", "Map/Map/Map9/924050000.img/back"));
        }

        final List<MapleMapObject> monsters = this.getAllMonster();
        if (!this.isTown() && monsters.size() > 0 && rune == null && Randomizer.nextInt(380) < 48) {
            MapleMonster mob = (MapleMonster) monsters.get(Randomizer.rand(0, monsters.size() - 1));
            rune = new MapleRune(Randomizer.rand(0, 7), mob.getPosition().x, mob.getPosition().y, this);
            this.spawnRune(rune);
        }
        
        /* 룬 종료 */
        if (chr.getSkillEffect() != null) {
            chr.setKeyDownSkill_Time(0);
            broadcastMessage(MainPacketCreator.skillCancel(chr, chr.getSkillEffect().getSkillId()));
            chr.setSkillEffect(null);
        }

        if (getIndex(mapid) != -1) {
            for (MapleCharacter mc : getCharacters()) {
                if (mc.getDeathCount() <= 0) {
                    if (mc.getSaveTime() <= 0) {
                        mc.setDeathCount(DeathCount.deathcount.get(getIndex(mapid)).getThird());
                        mc.startMapSaveTimeLimitTask(DeathCount.deathcount.get(getIndex(mapid)).getSecond() * 60, mc.getClient().getChannelServer().getMapFactory().getMap(100000000));
                        mc.getClient().getSession().write(MainPacketCreator.getDeathCount(mc.getDeathCount()));
                    } else {
                        MapleMap map = mc.getClient().getChannelServer().getMapFactory().getMap(100000000);
                        mc.changeMap(map, map.getPortal(0));
                    }
                } else if (mc.getSaveTime() > 0) {
                    mc.getClient().send(MainPacketCreator.getClock(mc.getSaveTime()));
                    mc.getClient().getSession().write(MainPacketCreator.getDeathCount(mc.getDeathCount()));
                } else { // 버그 방지
                    if (mc.getSaveTime() <= 0) {
                        mc.setDeathCount(DeathCount.deathcount.get(getIndex(mapid)).getThird());
                        mc.startMapSaveTimeLimitTask(DeathCount.deathcount.get(getIndex(mapid)).getSecond() * 60, mc.getClient().getChannelServer().getMapFactory().getMap(100000000));
                        mc.getClient().getSession().write(MainPacketCreator.getDeathCount(mc.getDeathCount()));
                    } else {
                        MapleMap map = mc.getClient().getChannelServer().getMapFactory().getMap(100000000);
                        mc.changeMap(map, map.getPortal(0));
                    }
                }
            }
        } else if (chr.getSaveTime() > 0) {
            chr.setSaveTime(0);
            chr.setDeathCount(0);
            chr.cancelSaveTimeLimitTask();
        }
    }

    public int getIndex(int mapid) {
        int index = -1;
        for (int i = 0; i < DeathCount.deathcount.size(); i++) {
            if (DeathCount.deathcount.get(i).getFirst().equals(this.mapid)) {
                index = i;
            }
        }
        return index;
    }

    public final void removePlayer(final MapleCharacter chr) {
        lastPlayerLeft = System.currentTimeMillis();
        if (everlast) {
            returnEverLastItem(chr);
        }
        removeMapObject(chr);
        broadcastMessage(MainPacketCreator.removePlayerFromMap(chr.getId()));
        chr.checkFollow();
        for (final MapleMonster monster : chr.getControlledMonsters()) {
            monster.setController(null);
            monster.setControllerHasAggro(false);
            monster.setControllerKnowsAboutAggro(false);
            updateMonsterController(monster);
        }
        for (final MapleMechDoor mmd : chr.getMechDoors()) {
            broadcastMessage(MechanicSkill.mechDoorRemove(mmd, true));
            removeMapObject(mmd);
        }
        chr.leaveMap();
        chr.cancelMapTimeLimitTask();
        for (Pair<Integer, MapleSummon> summon : chr.getSummons().values()) {
            broadcastMessage(MainPacketCreator.removeSummon(summon.right, true));
            removeMapObject(summon.right);
            chr.removeVisibleMapObject(summon.right);
        }
        
        List<MapleMapObject> removes = new ArrayList<>();
        
        for (MapleMapObject mist : this.getAllMist()) {
            removes.add(mist);
        }
        for (MapleMapObject mist : removes) {
            broadcastMessage(MainPacketCreator.removeMist(mist.getObjectId(), false));
            removeMapObject(mist);
        }

        if (getArrowFlatter(chr.getId()) != null) {
            chr.getMap().broadcastMessage(MainPacketCreator.cancelArrowFlatter(
                    getArrowFlatter(chr.getId()).getObjectId(), getArrowFlatter(chr.getId()).getArrow()));
            removeMapObject(getArrowFlatter(chr.getId()));
        }

        if (chr.getExtractor() != null) {
            removeMapObject(chr.getExtractor());
            chr.setExtractor(null);
            chr.message(5, "맵을 이동하여 분해기가 해체되었습니다.");
        }

        if (chr.getDragon() != null) {
            removeMapObject(chr.getDragon());
        }
        if (tempnpcs3.containsKey(chr.getId())) {
            removeTempNpc(tempnpcs3.get(chr.getId()).getId(), chr.getId());
            tempnpcs3.remove(chr.getId());
        }

        if (getId() == 103050510) { // 단련실 1
            spawnTempMonster(1, 9300522, new Point(-578, 152));
            spawnTempMonster(2, 9300521, new Point(-358, 152));
            spawnTempMonster(3, 9300522, new Point(-138, 152));
            spawnTempMonster(4, 9300522, new Point(82, 152));
            spawnTempMonster(5, 9300522, new Point(302, 152));
            spawnTempMonster(6, 9300522, new Point(522, 152));
        }

        if (getId() == 103050530 && getAllMonster().isEmpty()) { // 단련실 3
            spawnTempMonster(1, 9300523, new Point(-192, 152));
        }
    }

    public final void broadcastMessage(final byte[] packet) {
        broadcastMessage(null, packet, Double.POSITIVE_INFINITY, null);
    }

    public final void broadcastMessage(final MapleCharacter source, final byte[] packet, final boolean repeatToSource) {
        broadcastMessage(repeatToSource ? null : source, packet, Double.POSITIVE_INFINITY, source.getPosition());
    }

    public final void broadcastMessage(final byte[] packet, final Point rangedFrom) {
        broadcastMessage(null, packet, GameConstants.maxViewRangeSq(), rangedFrom);
    }

    public final void broadcastMessage(final MapleCharacter source, final byte[] packet, final Point rangedFrom) {
        broadcastMessage(source, packet, GameConstants.maxViewRangeSq(), rangedFrom);
    }

    private void broadcastMessage(final MapleCharacter source, final byte[] packet, final double rangeSq,
            final Point rangedFrom) {
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter chr = (MapleCharacter) _obj;
            if (chr != source) {
                if (rangeSq < Double.POSITIVE_INFINITY) {
                    if (rangedFrom.distanceSq(chr.getTruePosition()) <= rangeSq) {
                        chr.getClient().getSession().writeAndFlush(packet);
                    }
                } else {
                    chr.getClient().getSession().writeAndFlush(packet);
                }
            }
        }
    }

    private final void sendObjectPlacement(final MapleCharacter c) {
        if (c == null) {
            return;
        }
        for (final MapleMapObject o : getMapObjectsInRange(c.getPosition(), GameConstants.maxViewRangeSq(),
                GameConstants.rangedMapobjectTypes)) {
            if (o.getType() == MapleMapObjectType.REACTOR) {
                if (!((MapleReactor) o).isAlive()) {
                    continue;
                }
            }
            o.sendSpawnData(c.getClient());
            c.addVisibleMapObject(o);
        }
        for (final MapleMapObject o : getAllMonster()) {
            updateMonsterController((MapleMonster) o);
        }
    }

    public final List<MapleMapObject> getMapObjectsInRect(final Rectangle box,
            final List<MapleMapObjectType> MapObject_types) {
        final List<MapleMapObject> ret = new ArrayList<MapleMapObject>();
        for (MapleMapObjectType type : MapObject_types) {
            Iterator<MapleMapObject> itr = mapobjects.get(type).values().iterator();
            while (itr.hasNext()) {
                MapleMapObject mmo = itr.next();
                if (box.contains(mmo.getPosition())) {
                    ret.add(mmo);
                }
            }
        }
        return ret;
    }

    public final List<MapleMapObject> getMapObjectsInRange(final Point from, final double rangeSq) {
        final List<MapleMapObject> ret = new ArrayList<MapleMapObject>();
        for (MapleMapObjectType type : MapleMapObjectType.values()) {
            Iterator<MapleMapObject> itr = mapobjects.get(type).values().iterator();
            while (itr.hasNext()) {
                MapleMapObject mmo = itr.next();
                if (from.distanceSq(mmo.getPosition()) <= rangeSq) {
                    ret.add(mmo);
                }
            }
        }
        return ret;
    }

    public final List<MapleMapObject> getMapObjectsInRange(final Point from, final double rangeSq,
            final List<MapleMapObjectType> MapObject_types) {
        final List<MapleMapObject> ret = new ArrayList<MapleMapObject>();
        for (MapleMapObjectType type : MapObject_types) {
            Iterator<MapleMapObject> itr = mapobjects.get(type).values().iterator();
            while (itr.hasNext()) {
                MapleMapObject mmo = itr.next();
                if (from.distanceSq(mmo.getPosition()) <= rangeSq) {
                    ret.add(mmo);
                }
            }
        }
        return ret;
    }

    public final List<MapleCharacter> getPlayersInRect(final Rectangle box, final List<MapleCharacter> chrList) {
        final List<MapleCharacter> character = new LinkedList<MapleCharacter>();
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter a = (MapleCharacter) _obj;
            if (chrList.contains(a) && a.getBounds().intersects(box)) {
                character.add(a);
            }
        }
        return character;
    }

    public final List<MapleMapObject> getAllItems() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.ITEM));
    }

    public List<MapleWorldMapItem> getAllItemsThreadsafe() {
        ArrayList<MapleWorldMapItem> ret = new ArrayList<MapleWorldMapItem>();
        for (MapleMapObject mmo : getAllItems()) {
            ret.add((MapleWorldMapItem) mmo);
        }
        return ret;
    }

    public final List<MapleMapObject> getAllNPC() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.NPC, MapleMapObjectType.PLAYERNPC));
    }

    public final List<MapleMapObject> getAllPlayerNPC() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.PLAYERNPC));
    }

    public final List<MapleMapObject> getAllReactor() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.REACTOR));
    }

    public final List<MapleMapObject> getAllPlayer() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.PLAYER));
    }

    public final List<MapleMapObject> getAllMonster() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.MONSTER));
    }

    public final List<MapleMapObject> getAllDemianSword() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.DemianSword));
    }

    public final List<MapleMapObject> getAllMonstersThreadsafe() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.MONSTER));
    }

    public final List<MapleMapObject> getAllMist() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MIST));
    }

    public final List<MapleMapObject> getAllDoor() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.DOOR));
    }

    public final List<MapleMapObject> getAllMechDoor() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.DOOR));
    }

    public List<MapleMapObject> getAllHiredMerchant() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.HIRED_MERCHANT));
    }

    public List<MapleMapObject> getAllMistsThreadsafe() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MIST));
    }

    public List<MapleMapObject> getAllSummon() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.SUMMON));
    }

    public List<MapleMapObject> getAllRune() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.RUNE));
    }

    public final void addPortal(final MaplePortal myPortal) {
        portals.put(myPortal.getId(), myPortal);
    }

    public final MaplePortal getPortal(final String portalname) {
        for (final MaplePortal port : portals.values()) {
            if (port.getName().equals(portalname)) {
                return port;
            }
        }
        return null;
    }

    public final MaplePortal getPortal(final int portalid) {
        return portals.get(portalid);
    }

    public final List<MaplePortal> getPortalSP() {
        List<MaplePortal> res = new LinkedList<MaplePortal>();
        for (final MaplePortal port : portals.values()) {
            if (port.getName().equals("sp")) {
                res.add(port);
            }
        }
        return res;
    }

    public final void addMapleArea(final Rectangle rec) {
        areas.add(rec);
    }

    public final List<Rectangle> getAreas() {
        return new ArrayList<Rectangle>(areas);
    }

    public final Rectangle getArea(final int index) {
        return areas.get(index);
    }

    public final void setFootholds(final MapleFootholdTree footholds) {
        this.footholds = footholds;
    }

    public final MapleFootholdTree getFootholds() {
        return footholds;
    }

    public final void setFixedMob(int fm) {
        this.fixedMob = fm;
    }

    public final void loadMonsterRate(final boolean first) { // Spawn Rate
        final int spawnSize = monsterSpawn.size();
        if (spawnSize >= 20 || partyBonusRate > 0) {
            maxRegularSpawn = Math.round(spawnSize / monsterRate);
        } else {
            maxRegularSpawn = (int) Math.ceil(spawnSize * monsterRate);
        }
        if (fixedMob > 0) {
            maxRegularSpawn = fixedMob;
        } else if (maxRegularSpawn <= 2) {
            maxRegularSpawn = 2;
        } else if (maxRegularSpawn > spawnSize) {
            maxRegularSpawn = Math.max(10, spawnSize * 3); // changed 
        }

        Collection<MonsterSpawnEntry> newSpawn = new LinkedList<>();
        Collection<MonsterSpawnEntry> newBossSpawn = new LinkedList<>();
        for (final MonsterSpawnEntry s : monsterSpawn) {
            if (s.getMonster().getStats().isBoss()) {
                newBossSpawn.add(s);
            } else {
                newSpawn.add(s);
            }
        }
        monsterSpawn.clear();
        monsterSpawn.addAll(newBossSpawn);
        monsterSpawn.addAll(newSpawn);

        if (first && spawnSize > 0) {
            lastSpawnTime = System.currentTimeMillis();
            respawn(false); // this should do the trick, we don't need to wait upon entering map
        }
    }

    public void setSpawns(boolean bo) {
        this.isSpawns = bo;
    }

    public final boolean canSpawn(long now) {
        return lastSpawnTime > 0 && lastSpawnTime + createMobInterval < now;
    }

    public final void addMonsterSpawn(final MapleMonster monster, final int mobTime, final String msg) {
        final Point newpos = calcPointMaple(monster.getPosition());
        newpos.y -= 1;

        monsterSpawn.add(new SpawnPoint(monster, newpos, mobTime, msg));
    }

    public final void addAreaMonsterSpawn(final MapleMonster monster, Point pos1, Point pos2, Point pos3,
            final int mobTime, final String msg) {
        pos1 = calcPointMaple(pos1);
        pos2 = calcPointMaple(pos2);
        pos3 = calcPointMaple(pos3);
        pos1.y -= 1;
        pos2.y -= 1;
        pos3.y -= 1;

        monsterSpawn.add(new SpawnPointAreaBoss(monster, pos1, pos2, pos3, mobTime, msg));
    }

    public final List<MapleCharacter> getCharacters() {
        return getCharactersThreadsafe();
    }

    public final List<MapleCharacter> getCharactersThreadsafe() {
        final List<MapleCharacter> chars = new ArrayList<MapleCharacter>();

        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter mc = (MapleCharacter) _obj;
            chars.add(mc);
        }
        return chars;
    }

    public final MapleCharacter getCharacterById_InMap(final int id) {
        return getCharacterById(id);
    }

    public final MapleCharacter getCharacterById(final int id) {
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter mc = (MapleCharacter) _obj;
            if (mc.getId() == id) {
                return mc;
            }
        }
        return null;
    }

    private final void updateMapObjectVisibility(final MapleCharacter chr, final MapleMapObject mo) {
        if (chr == null) {
            return;
        }
        if (!chr.isMapObjectVisible(mo)) { // monster entered view range
            if (mo.getType() == MapleMapObjectType.MIST || mo.getType() == MapleMapObjectType.EXTRACTOR
                    || mo.getType() == MapleMapObjectType.ANDROID || mo.getType() == MapleMapObjectType.SUMMON
                    || mo.getPosition().distanceSq(chr.getPosition()) <= GameConstants.maxViewRangeSq()) {
                chr.addVisibleMapObject(mo);
                mo.sendSpawnData(chr.getClient());
            }
        } else // monster left view range
        {
            if (mo.getType() != MapleMapObjectType.MIST && mo.getType() != MapleMapObjectType.EXTRACTOR
                    && mo.getType() != MapleMapObjectType.ANDROID && mo.getType() != MapleMapObjectType.SUMMON
                    && mo.getPosition().distanceSq(chr.getPosition()) > GameConstants.maxViewRangeSq()) {
                chr.removeVisibleMapObject(mo);
                mo.sendDestroyData(chr.getClient());
            } else if (mo.getType() == MapleMapObjectType.MONSTER) { // monster didn't leave view range, and is visible
                if (chr.getPosition().distanceSq(mo.getPosition()) <= GameConstants.maxViewRangeSq()) {
                    updateMonsterController((MapleMonster) mo);
                }
            }
        }
    }

    public void moveMonster(MapleMonster monster, Point reportedPos) {
        monster.setPosition(reportedPos);
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter mc = (MapleCharacter) _obj;
            updateMapObjectVisibility(mc, monster);
        }
    }

    public void movePlayer(final MapleCharacter player, final Point newPosition) {
        player.setPosition(newPosition);
        try {
            Collection<MapleMapObject> visibleObjects = player.getAndWriteLockVisibleMapObjects();
            ArrayList<MapleMapObject> copy = new ArrayList<>(visibleObjects);
            Iterator<MapleMapObject> itr = copy.iterator();
            while (itr.hasNext()) {
                MapleMapObject mo = itr.next();
                if (mo != null && getMapObject(mo.getObjectId(), mo.getType()) == mo) {
                    updateMapObjectVisibility(player, mo);
                } else if (mo != null) {
                    visibleObjects.remove(mo);
                }
            }
            for (MapleMapObject mo : getMapObjectsInRange(player.getTruePosition(), GameConstants.maxViewRangeSq())) {
                if (mo != null && !visibleObjects.contains(mo)) {
                    mo.sendSpawnData(player.getClient());
                    visibleObjects.add(mo);
                }
            }
        } finally {
            player.unlockWriteVisibleMapObjects();
        }
    }

    public MaplePortal findClosestSpawnpoint(Point from) {
        MaplePortal closest = null;
        double distance, shortestDistance = Double.POSITIVE_INFINITY;
        for (MaplePortal portal : portals.values()) {
            distance = portal.getPosition().distanceSq(from);
            if (portal.getType() >= 0 && portal.getType() <= 2 && distance < shortestDistance
                    && portal.getTargetMapId() == 999999999) {
                closest = portal;
                shortestDistance = distance;
            }
        }
        return closest;
    }

    public void setMapTimer(long time) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM bosscooltime WHERE map = ? AND channel = ?");
            ps.setInt(1, mapid);
            ps.setInt(2, channel);
            ps.executeUpdate();
            ps = con.prepareStatement("INSERT INTO bosscooltime VALUES (?, ?, ?)");
            ps.setInt(1, channel);
            ps.setInt(2, mapid);
            ps.setLong(3, time);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("[오류] DB로 보스 쿨타임을 저장하는데 실패했습니다.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        this.maptimer = time;
    }

    public void setMapTimerNotDB(long time) {
        this.maptimer = time;
    }

    public boolean isExpiredMapTimer() {
        return maptimer < System.currentTimeMillis();
    }

    public String spawnDebug() {
        StringBuilder sb = new StringBuilder("Mapobjects in map : ");
        sb.append(this.getMapObjectSize());
        sb.append(" spawnedMonstersOnMap: ");
        sb.append(spawnedMonstersOnMap);
        sb.append(" spawnpoints: ");
        sb.append(monsterSpawn.size());
        sb.append(" maxRegularSpawn: ");
        sb.append(maxRegularSpawn);
        sb.append(" actual monsters: ");
        sb.append(getAllMonster().size());

        return sb.toString();
    }

    public final int getMapObjectSize() {
        return mapobjects.size();
    }

    public final int getCharactersSize() {
        return mapobjects.get(MapleMapObjectType.PLAYER).size();
    }

    public Collection<MaplePortal> getPortals() {
        return Collections.unmodifiableCollection(portals.values());
    }

    public int getSpawnedMonstersOnMap() {
        return spawnedMonstersOnMap.get();

    }

    private class ExpireMapItemJob implements Runnable {

        private MapleWorldMapItem mapitem;
        private MapleCharacter chr;

        public ExpireMapItemJob(MapleWorldMapItem mapitem, MapleCharacter chr) {
            this.mapitem = mapitem;
            this.chr = chr;
        }

        @Override
        public void run() {
            if (mapitem != null && mapitem == getMapObject(mapitem.getObjectId(), MapleMapObjectType.ITEM)) {
                if (droppedItems.contains(Integer.valueOf(mapitem.getObjectId()))) {
                    droppedItems.remove(Integer.valueOf(mapitem.getObjectId()));
                }
                if (chr != null && mapitem.getMeso() == 1 && mapitem.getOwner() == chr.getId()) {
                    if (chr.mesoCount > 0)
                        chr.mesoCount--;
                    chr.addpocket();
                }
                if (mapitem.isPickedUp()) {
                    return;
                }
                mapitem.setPickedUp(true);

                broadcastMessage(MainPacketCreator.removeItemFromMap(mapitem.getObjectId(), 0, 0));
                removeMapObject(mapitem);
            }
        }
    }

    private class ActivateItemReactor implements Runnable {

        private MapleWorldMapItem mapitem;
        private MapleReactor reactor;
        private MapleClient c;

        public ActivateItemReactor(MapleWorldMapItem mapitem, MapleReactor reactor, MapleClient c) {
            this.mapitem = mapitem;
            this.reactor = reactor;
            this.c = c;
        }

        @Override
        public void run() {
            if (mapitem != null && mapitem == getMapObject(mapitem.getObjectId(), MapleMapObjectType.ITEM)) {
                if (mapitem.isPickedUp()) {
                    reactor.setTimerActive(false);
                    return;
                }
                mapitem.setPickedUp(true);
                broadcastMessage(MainPacketCreator.removeItemFromMap(mapitem.getObjectId(), 0, 0));
                removeMapObject(mapitem);
                try {
                    reactor.hitReactor(c);
                } catch (Exception e) {
                    if (!ServerConstants.realese) {
                        e.printStackTrace();
                    }
                }
                reactor.setTimerActive(false);

                if (reactor.getDelay() > 0) {
                    MapTimer.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            reactor.setState((byte) 0);
                            broadcastMessage(MainPacketCreator.triggerReactor(reactor, 0, c.getPlayer().getId()));
                        }
                    }, reactor.getDelay());
                }
            }
        }
    }

    public int countSummonSkill(MapleCharacter chr, int skill) {
        int count = 0;
        if (GameConstants.isEvan(chr.getJob())) {
            return 0;
        }
        List<MapleMapObject> mapobjs = chr.getMap().getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.SUMMON));
        for (MapleMapObject o : mapobjs) {
            if (o.getType() == MapleMapObjectType.SUMMON) {
                if (((MapleSummon) o).getOwnerChr() == chr) {
                    if (((MapleSummon) o).getSkill() == skill) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public List<MapleMapObject> getSummonObjects(MapleCharacter chr, int skill) {
        List<MapleMapObject> ret = new ArrayList<MapleMapObject>();
        List<MapleMapObject> mapobjs = chr.getMap().getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.SUMMON));
        for (MapleMapObject o : mapobjs) {
            if (o.getType() == MapleMapObjectType.SUMMON) {
                if (((MapleSummon) o).getOwnerChr() == chr) {
                    if (((MapleSummon) o).getSkill() == skill) {
                        ret.add(o);
                    }
                }
            }
        }
        return ret;
    }

        public final MapleCharacter getCharacterByName_InMap(final String name) {
        charactersLock.readLock().lock();
        try {
            final Iterator<MapleCharacter> ltr = characters.iterator();
            MapleCharacter c;
            while (ltr.hasNext()) {
                c = ltr.next();
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        } finally {
            charactersLock.readLock().unlock();
        }
        return null;
    }
    public void respawn(final boolean force) {
        respawn(force, System.currentTimeMillis());
    }

    public void respawn(final boolean force, final long now) {
        lastSpawnTime = now;
        if (force) {
            final int numShouldSpawn = monsterSpawn.size() - spawnedMonstersOnMap.get();
            if (numShouldSpawn > 0) {
                int spawned = 0;
                for (MonsterSpawnEntry spawnPoint : monsterSpawn) {
                    spawnPoint.spawnMonster(this);
                    spawned++;
                    if (spawned >= numShouldSpawn) {
                        break;
                    }
                }
            }
        } else {
            final int numShouldSpawn = maxRegularSpawn - spawnedMonstersOnMap.get();
            if (numShouldSpawn > 0) {
                int spawned = 0;
                final List<MonsterSpawnEntry> randomSpawn = new ArrayList<MonsterSpawnEntry>(monsterSpawn);
                Collections.shuffle(randomSpawn);
                for (MonsterSpawnEntry spawnPoint : randomSpawn) {
                    if (spawnPoint.shouldSpawn()) {
                        spawnPoint.spawnMonster(this);
                        spawned++;
                    }
                    if (spawned >= numShouldSpawn) {
                        break;

                    }
                }
            }
        }
    }

    public static interface DelayedPacketCreation {

        void sendPackets(MapleClient c);
    }

    private static interface SpawnCondition {

        boolean canSpawn(MapleCharacter chr);
    }

    public Collection<MapleCharacter> getNearestPvpChar(Point attacker, double maxRange, double maxHeight,
            boolean isLeft, Collection<MapleCharacter> chr) {
        Collection<MapleCharacter> character = new LinkedList<MapleCharacter>();
        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter a = (MapleCharacter) _obj;
            if (chr.contains(a.getClient().getPlayer())) {
                Point attackedPlayer = a.getPosition();
                MaplePortal Port = a.getMap().findClosestSpawnpoint(a.getPosition());
                Point nearestPort = Port.getPosition();
                double safeDis = attackedPlayer.distance(nearestPort);
                double distanceX = attacker.distance(attackedPlayer.getX(), attackedPlayer.getY());

                if (isLeft) {
                    if (attacker.x < attackedPlayer.x && distanceX < maxRange && distanceX > 1
                            && attackedPlayer.y >= attacker.y - maxHeight && attackedPlayer.y <= attacker.y + maxHeight) {
                        character.add(a);
                    }
                } else if (attacker.x > attackedPlayer.x && distanceX < maxRange && distanceX > 1
                        && attackedPlayer.y >= attacker.y - maxHeight && attackedPlayer.y <= attacker.y + maxHeight) {
                    character.add(a);
                }
            }
        }
        return character;
    }

    public void startCatch() {
        if (catchstart == null) {
            broadcastMessage(MainPacketCreator.getClock(180));
            catchstart = MapTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    broadcastMessage(
                            MainPacketCreator.serverNotice(1, "제한시간 2분이 지나 양이 승리하였습니다!\r\n모든 분들은 게임 보상맵으로 이동됩니다."));
                    for (MapleCharacter chr : getCharacters()) {
                        chr.getStat().setHp(chr.getStat().getMaxHp(), chr);
                        chr.updateSingleStat(PlayerStatList.HP, chr.getStat().getMaxHp());
                        if (chr.isCatching) {
                            chr.changeMap(chr.getClient().getChannelServer().getMapFactory().getMap(109090201),
                                    chr.getClient().getChannelServer().getMapFactory().getMap(109090201).getPortalSP()
                                            .get(0));
                        } else {
                            chr.changeMap(chr.getClient().getChannelServer().getMapFactory().getMap(109090100),
                                    chr.getClient().getChannelServer().getMapFactory().getMap(109090100).getPortalSP()
                                            .get(0));
                        }
                    }
                    stopCatch();
                }
            }, 180000);
        }
    }

    public void stopCatch() {
        if (catchstart != null) {
            catchstart.cancel(true);
            catchstart = null;
        }
    }

    public void RespawnNPC() {
        try {
            Connection con = MYSQL.getConnection();
            String sql = "SELECT * FROM `spawn` WHERE mapid = " + mapid + " AND type = 'n'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!CheckNPC(rs.getInt("lifeid"))) {
                    // {
                    final MapleNPC npc = MapleLifeProvider.getNPC(rs.getInt("lifeid"));
                    npc.setRx0(rs.getInt("rx0"));
                    npc.setRx1(rs.getInt("rx1"));
                    npc.setCy(rs.getInt("cy"));
                    npc.setF(rs.getInt("dir"));
                    npc.setFh(rs.getInt("fh"));
                    npc.setPosition(new Point(npc.getRx0() - 50, npc.getCy()));
                    if (npc != null) {
                        addMapObject(npc);
                    } else {
                        System.err.println("[오류] 엔피시 데이터를 만드는중 널 포인터 오류가 발생했습니다.");
                    }
                    // }
                }
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("[오류] 엔피시를 DB로부터 불러오는데 오류가 발생했습니다.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public boolean CheckNPC(int i) {
        for (MapleMapObject ob : getAllNPC()) {
            MapleNPC imsi = (MapleNPC) ob;
            if (imsi.getId() == i) {
                return true;
            }
        }
        return false;
    }

    public final MapleCharacter getCharacterById(MapleCharacter player, final int id) {
        for (MapleCharacter chr : player.getMap().getCharacters()) {
            if (chr.getId() == id) {
                return chr;
            }
        }
        return null;
    }

    public final List<ArrowFlatter> getArrowFlatterRange(final Point from, final double rangeSq,
            final List<MapleMapObjectType> MapObject_types) {
        final List<MapleMapObject> mapobjects = this.getMapObjectsInRange(from, rangeSq);
        final List<ArrowFlatter> arrows = new ArrayList<ArrowFlatter>();
        for (int i = 0; i < mapobjects.size(); i++) {
            if (mapobjects.get(i).getType() == MapleMapObjectType.ARROWFLATTER) {
                arrows.add((ArrowFlatter) mapobjects.get(i));
            }
        }
        return arrows;
    }

    public final void spawnArrowFlatter(final ArrowFlatter arrow) {
        spawnAndAddRangedMapObject(arrow, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(MainPacketCreator.spawnArrowFlatter(arrow.getCid(), arrow.getArrow(),
                        arrow.getPosition(), arrow.getObjectId()));
                c.getSession()
                        .writeAndFlush(MainPacketCreator.spawnArrowFlatter(arrow.getArrow(), arrow.getObjectId()));
            }
        });
    }

    public final void spawnDemianSword(final DemianSword sword) {
        spawnAndAddRangedMapObject(sword, new DelayedPacketCreation() {
            @Override
            public void sendPackets(MapleClient c) {
                c.getSession().writeAndFlush(DemianPacket.Demian_OnFlyingSwordCreat(true, sword));
                c.getSession().writeAndFlush(DemianPacket.Demian_OnFlyingSwordNode(sword, new ArrayList<>()));
                c.getSession().writeAndFlush(DemianPacket.Demian_OnFlyingSwordTarget(sword));
            }
        });
    }

    public final ArrowFlatter getArrowFlatter(final int cid) {
        for (ArrowFlatter arrows : getArrowFlatter()) {
            if (arrows.getCid() == cid) {
                return arrows;
            }
        }
        return null;
    }

    public final List<ArrowFlatter> getArrowFlatter() {
        return getArrowFlatterRange(new Point(0, 0), Double.POSITIVE_INFINITY,
                Arrays.asList(MapleMapObjectType.ARROWFLATTER));
    }

    public List<MapleMapObject> getItemsInRange(Point from, double rangeSq) {
        return getMapObjectsInRange(from, rangeSq, Arrays.asList(MapleMapObjectType.ITEM));
    }

    public String getSnowballPortal() {
        int[] teamss = new int[2];

        for (MapleMapObject _obj : mapobjects.get(MapleMapObjectType.PLAYER).values()) {
            MapleCharacter chr = (MapleCharacter) _obj;
            if (chr.getTruePosition().y > -80) {
                teamss[0]++;
            } else {
                teamss[1]++;
            }
        }

        if (teamss[0] > teamss[1]) {
            return "st01";
        } else {
            return "st00";
        }
    }

    public final void resetFully() {
        resetFully(true);
    }

    public final void reloadReactors() {
        List<MapleReactor> toSpawn = new ArrayList<>();
        for (MapleMapObject obj : mapobjects.get(MapleMapObjectType.REACTOR).values()) {
            final MapleReactor reactor = (MapleReactor) obj;
            broadcastMessage(MainPacketCreator.destroyReactor(reactor));
            reactor.setAlive(false);
            reactor.setTimerActive(false);
            toSpawn.add(reactor);
        }

        for (MapleReactor r : toSpawn) {
            removeMapObject(r);
            // if (!r.isCustom()) { //guardians cpq
            respawnReactor(r);
            // }
        }
    }

    public final void removeDrops() {
        List<MapleMapObject> items = this.getAllItems();
        for (MapleMapObject i : items) {
            ((MapleWorldMapItem) i).expire(this);
        }
    }

    public final void resetFully(final boolean respawn) {
        killAllMonsters(false);
        reloadReactors();
        removeDrops();
        if (respawn) {
            respawn(true);
        }
    }

    public final void talkMonster(final String msg, final int itemId, final int objectid) {
        if (itemId > 0) {
            startMapEffect(msg, -1, itemId);
        }
        broadcastMessage(MobPacket.talkMonster(objectid, itemId, msg)); // 5120035
        broadcastMessage(MobPacket.removeTalkMonster(objectid));
    }

    public void setNodes(final MapleNodes mn) {
        this.nodes = mn;
    }

    public final List<MapleNodes.MaplePlatform> getPlatforms() {
        return nodes.getPlatforms();
    }

    public Collection<MapleNodes.MapleNodeInfo> getNodes() {
        return nodes.getNodes();
    }

    public MapleNodes.MapleNodeInfo getNode(final int index) {
        return nodes.getNode(index);
    }

    public boolean isLastNode(final int index) {
        return nodes.isLastNode(index);
    }

    public List<Pair<Point, Integer>> getGuardians() {
        return nodes.getGuardians();
    }

    public MapleNodes.DirectionInfo getDirectionInfo(int i) {
        return nodes.getDirection(i);
    }

    public void removeMonster(final MapleMonster monster) {
        if (monster == null) {
            return;
        }
        spawnedMonstersOnMap.decrementAndGet();
        if (GameConstants.isAswanMap(mapid)) {
            broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 0, true));
        } else {
            broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 0, false));
        }
        removeMapObject(monster);
    }

    public final void resetSpawnLevel(int level) {
        for (MonsterSpawnEntry spawn : monsterSpawn) {
            if (spawn instanceof SpawnPoint) {
                ((SpawnPoint) spawn).setLevel(level);
            }
        }
    }

    public final void resetPQ(int level) {
        resetFully();
        for (MapleMapObject mons : getAllMonster()) {
            ((MapleMonster) mons).changeLevel(level, true);
        }
        resetSpawnLevel(level);
    }

    public void setLucidState(FieldLucid.LucidState stat) {
        lucidStat = stat;
    }

    public void removeAllMobsExcept(List<MapleMonster> except) {
        for (MapleMapObject ob : this.getAllMonster()) {
            if (except.contains((MapleMonster) ob)) {
                continue;
            }
            MapleMonster monster = (MapleMonster) ob;
            spawnedMonstersOnMap.decrementAndGet();
            monster.setHp(0);
            broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 1, GameConstants.isAswanMap(mapid)));
            removeMapObject(monster);
        }
    }

    public DemianSword getDemianSword(int oid) {
        for (MapleMapObject obj : getAllDemianSword()) {
            if (obj.getObjectId() == oid) {
                return (DemianSword) obj;
            }
        }
        return null;
    }
}
