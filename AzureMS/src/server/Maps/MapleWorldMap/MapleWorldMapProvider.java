package server.Maps.MapleWorldMap;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import server.Maps.MapleMapHandling.PortalFactory;
import server.Maps.MapleMapHandling.MapleMap;
import constants.GameConstants;
import constants.ServerConstants;
import connections.Database.MYSQL;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.LifeEntity.MobEntity.AbstractLoadedMapleLife;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.NpcEntity.MapleNPC;
import server.LifeEntity.NpcEntity.MaplePlayerNPC;
import server.Maps.MapReactor.MapleReactor;
import server.Maps.MapReactor.MapleReactorFactory;
import server.Maps.MapReactor.MapleReactorStats;
import server.Maps.MapleFootHold.MapleFoothold;
import server.Maps.MapleFootHold.MapleFootholdTree;
import server.Maps.MapleNodes;
import server.Maps.MapleNodes.DirectionInfo;
import server.Maps.MapleNodes.MapleNodeInfo;
import server.Maps.MapleNodes.MaplePlatform;
import server.Systems.PotSystem;
import tools.Pair;
import tools.StringUtil;

public class MapleWorldMapProvider {

    private static final MapleDataProvider source = MapleDataProviderFactory.getDataProvider(new File("wz/Map.wz"));
    private static final MapleData nameData = MapleDataProviderFactory.getDataProvider(new File("wz/String.wz")).getData("Map.img");
    private final Map<Integer, MapleMap> maps = new HashMap<Integer, MapleMap>();
    private final HashMap<Integer, MapleMap> instanceMap = new HashMap<>();
    private int channel;
    private final ReentrantLock lock = new ReentrantLock();

    public final MapleMap getMap(final int mapid) {
        return getMap(mapid, true, true, true);
    }

    public final MapleMap getMap(final int mapid, final boolean respawns, final boolean npcs) {
        return getMap(mapid, respawns, npcs, true);
    }

    public final MapleMap getMap(final int mapid, final boolean respawns, final boolean npcs, final boolean reactors) {
        Integer omapid = Integer.valueOf(mapid);
        MapleMap map = maps.get(omapid);
        if (map == null) {
            lock.lock();
            try {

                map = maps.get(omapid);
                if (map != null) {
                    return map;
                }
                MapleData fieldgenData = source.getData(getFieldGenerator());
                List<MapleData> fieldgenmaps = fieldgenData.getChildren();
                int templatemapid = mapid;
                for (int i = 0; i < fieldgenmaps.size(); i++) {
                    if (fieldgenmaps.get(i).getName().equals(String.valueOf(i))) {
                        MapleData template = fieldgenData.getChildByPath(i + "/template");
                        MapleData fieldgencount = fieldgenData.getChildByPath(i + "/count");
                        int templatecheck = MapleDataTool.getInt(template);
                        int count = MapleDataTool.getInt(fieldgencount);
                        if (templatecheck < omapid && omapid < templatecheck + count) {
                            templatemapid = templatecheck;
                            break;
                        }
                    }
                }
                MapleData mapData = source.getData(getMapName(templatemapid));

                MapleData link = mapData.getChildByPath("info/link");
                if (link != null) {
                    mapData = source.getData(getMapName(MapleDataTool.getIntConvert("info/link", mapData)));
                }
                float monsterRate = 0;
                if (respawns) {
                    MapleData mobRate = mapData.getChildByPath("info/mobRate");
                    if (mobRate != null) {
                        monsterRate = ((Float) mobRate.getData()).floatValue();
                    }
                }
                map = new MapleMap(mapid, channel, MapleDataTool.getInt("info/returnMap", mapData), monsterRate);

                PortalFactory portalFactory = new PortalFactory();
                for (MapleData portal : mapData.getChildByPath("portal")) {
                    map.addPortal(portalFactory.makePortal(MapleDataTool.getInt(portal.getChildByPath("pt")), portal));
                }
                if (mapid == 924000200) {
                    map.getPortal(2).setScriptName("enter_dragon");
                }
                if (mapid == 910000000) {
                    map.getPortal(3).setScriptName("enter_fm01");
                }
                if (mapid >= 910000001 && mapid <= 910000022) {
                    map.getPortal("out00").setScriptName("out_fm01");
                }
                List<MapleFoothold> allFootholds = new LinkedList<MapleFoothold>();
                Point lBound = new Point();
                Point uBound = new Point();
                MapleFoothold fh;

                for (MapleData footRoot : mapData.getChildByPath("foothold")) {
                    for (MapleData footCat : footRoot) {
                        for (MapleData footHold : footCat) {
                            fh = new MapleFoothold(
                                    new Point(MapleDataTool.getInt(footHold.getChildByPath("x1")), MapleDataTool.getInt(footHold.getChildByPath("y1"))),
                                    new Point(MapleDataTool.getInt(footHold.getChildByPath("x2")), MapleDataTool.getInt(footHold.getChildByPath("y2"))), Integer.parseInt(footHold.getName()));
                            fh.setPrev((short) MapleDataTool.getInt(footHold.getChildByPath("prev")));
                            fh.setNext((short) MapleDataTool.getInt(footHold.getChildByPath("next")));

                            if (fh.getX1() < lBound.x) {
                                lBound.x = fh.getX1();
                            }
                            if (fh.getX2() > uBound.x) {
                                uBound.x = fh.getX2();
                            }
                            if (fh.getY1() < lBound.y) {
                                lBound.y = fh.getY1();
                            }
                            if (fh.getY2() > uBound.y) {
                                uBound.y = fh.getY2();
                            }
                            allFootholds.add(fh);
                        }
                    }
                }
                MapleFootholdTree fTree = new MapleFootholdTree(lBound, uBound);
                for (MapleFoothold foothold : allFootholds) {
                    fTree.insert(foothold);
                }
                map.setFootholds(fTree);

                if (mapData.getChildByPath("area") != null) {
                    int x1, y1, x2, y2;
                    Rectangle mapArea;
                    for (MapleData area : mapData.getChildByPath("area")) {
                        x1 = MapleDataTool.getInt(area.getChildByPath("x1"));
                        y1 = MapleDataTool.getInt(area.getChildByPath("y1"));
                        x2 = MapleDataTool.getInt(area.getChildByPath("x2"));
                        y2 = MapleDataTool.getInt(area.getChildByPath("y2"));
                        mapArea = new Rectangle(x1, y1, (x2 - x1), (y2 - y1));
                        map.addMapleArea(mapArea);
                    }
                }

                int bossid = -1;
                String msg = null;
                if (mapData.getChildByPath("info/timeMob") != null) {
                    bossid = MapleDataTool.getInt(mapData.getChildByPath("info/timeMob/id"), 0);
                    msg = MapleDataTool.getString(mapData.getChildByPath("info/timeMob/message"), null);
                }

                String type;
                AbstractLoadedMapleLife myLife;

                for (MapleData life : mapData.getChildByPath("life")) {
                    type = MapleDataTool.getString(life.getChildByPath("type"));
                    if (npcs || !type.equals("n")) {
                        myLife = loadLife(life, MapleDataTool.getString(life.getChildByPath("id")), type);
                        if (myLife instanceof MapleMonster) {
                            final MapleMonster mob = (MapleMonster) myLife;
                            map.addMonsterSpawn(mob, MapleDataTool.getInt("mobTime", life, 0), mob.getId() == bossid ? msg : null);
                            if (!mob.getStats().isBoss()) {
                                map.addMonsterSpawn(mob, MapleDataTool.getInt("mobTime", life, 0), mob.getId() == bossid ? msg : null);
                            }
                        } else {
                            map.addMapObject(myLife);
                        }
                    }
                }

                try {
                    Connection con = MYSQL.getConnection();
                    String sql = "SELECT * FROM `spawn` WHERE mapid = " + mapid + " AND type = 'm'";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        MapleMonster mob = MapleLifeProvider.getMonster(rs.getInt("lifeid"));
                        mob.setRx0(rs.getInt("rx0"));
                        mob.setRx1(rs.getInt("rx1"));
                        mob.setCy(rs.getInt("cy"));
                        mob.setF(rs.getInt("dir"));
                        mob.setFh(rs.getInt("fh"));
                        mob.setPosition(new Point(mob.getRx0() - 50, mob.getCy()));
                        if (mob != null) {
                            map.addMonsterSpawn(mob, rs.getInt("mobTime"), null);
                        } else {
                            System.err.println("[Error] Null Pointer Error Occurred While Creating NPC Data.");
                        }
                    }
                    rs.close();
                    ps.close();
                    con.close();
                } catch (Exception e) {
                    System.err.println("[Error] There was an error loading nfish from DB.");
                    if (!ServerConstants.realese) {
                        e.printStackTrace();
                    }
                }

                if (npcs) {
                    try {
                        Connection con = MYSQL.getConnection();
                        String sql = "SELECT * FROM `playernpcs` WHERE map = " + mapid;
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            MaplePlayerNPC pnpc = MapleLifeProvider.getPlayerNPC(rs.getInt("id"));
                            pnpc.setFh(map.getFootholds().findMaple(pnpc.getPosition()).getId());
                            if (pnpc != null) {
                                map.addMapObject(pnpc);
                            } else {
                                System.err.println("[Error] A null pointer error occurred while creating player copy data.");
                            }
                        }
                        rs.close();
                        ps.close();
                        con.close();

                    } catch (Exception e) {
                        System.err.println("[Error] There was an error loading Player Np from DB.");
                        if (!ServerConstants.realese) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Connection con = MYSQL.getConnection();
                        String sql = "SELECT * FROM `spawn` WHERE mapid = " + mapid + " AND type = 'n'";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            MapleNPC npc = MapleLifeProvider.getNPC(rs.getInt("lifeid"));
                            npc.setRx0(rs.getInt("rx0"));
                            npc.setRx1(rs.getInt("rx1"));
                            npc.setCy(rs.getInt("cy"));
                            npc.setF(rs.getInt("dir"));
                            npc.setFh(rs.getInt("fh"));
                            npc.setPosition(new Point(npc.getRx0() - 50, npc.getCy()));
                            if (npc != null) {
                                map.addMapObject(npc);
                            } else {
                                System.err.println("[Error] Null Pointer Error occurred while creating NPC data.");
                            }
                        }
                        rs.close();
                        ps.close();
                        con.close();
                    } catch (Exception e) {
                        System.err.println("[Error] There was an error loading nfish from DB.");
                        if (!ServerConstants.realese) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    if (omapid == 910001009) {
                        Connection con = MYSQL.getConnection();
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM pots where channel = ?");
                        ps.setInt(1, channel);
                        ResultSet rs = ps.executeQuery();

                        MapleReactorStats stats = null;
                        MapleReactor myReactor = null;
                        while (rs.next()) {
                            stats = MapleReactorFactory.getReactor(rs.getInt("rid"));
                            myReactor = new MapleReactor(stats, rs.getInt("rid"), rs.getInt("gid"));

                            stats.setFacingDirection((byte) 0);
                            myReactor.setPosition(new Point(rs.getInt("x"), rs.getInt("y")));
                            myReactor.setDelay(0);
                            myReactor.setState((byte) 0);
                            myReactor.setName(rs.getString("name"));
                            PotSystem.addPot(rs.getInt("gid"), rs.getInt("rid"), rs.getInt("exp"));
                            map.spawnReactor(myReactor);
                        }
                        rs.close();
                        ps.close();
                        con.close();
                    }
                } catch (Exception e) {
                    System.err.println("[Error] There was an error loading the guild pot from the DB.");
                    if (!ServerConstants.realese) {
                        e.printStackTrace();
                    }
                }

                addAreaBossSpawn(map);

                map.setNodes(loadNodes(mapid, mapData));
                /* load reactor data */
                String id;
                if (reactors && mapData.getChildByPath("reactor") != null) {
                    for (MapleData reactor : mapData.getChildByPath("reactor")) {
                        id = MapleDataTool.getString(reactor.getChildByPath("id"));
                        if (id != null) {
                            try {

                                map.spawnReactor(loadReactor(reactor, id,
                                        (byte) MapleDataTool.getInt(reactor.getChildByPath("f"), 0)));
                            } catch (Exception ex) {

                            }
                        }
                    }
                }
                if (reactors) {
                    try {
                        Connection con = MYSQL.getConnection();
                        String sql = "SELECT * FROM `spawns_profession` WHERE mid = " + mapid + "";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            int reactid = GameConstants
                                    .getRandomProfessionReactorByRank(Integer.parseInt(rs.getString("type")));
                            final MapleReactorStats stats = MapleReactorFactory.getReactor(reactid);
                            final MapleReactor myReactor = new MapleReactor(stats, reactid);
                            myReactor.setPosition(new Point(rs.getInt("x"), rs.getInt("y")));
                            myReactor.setDelay(900000);
                            myReactor.setState((byte) 0);
                            myReactor.setName("±¤¸Æ");
                            myReactor.setRank(Integer.parseInt(rs.getString("type")));
                            map.spawnReactor(myReactor);
                        }
                        rs.close();
                        ps.close();
                        con.close();
                    } catch (Exception e) {
                        System.err.println("[Error] An error occurred while loading the vein from DB.");
                        if (!ServerConstants.realese) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Connection con = MYSQL.getConnection();
                    PreparedStatement ps = con
                            .prepareStatement("SELECT time FROM bosscooltime WHERE map = ? AND channel = ?");
                    ps.setInt(1, mapid);
                    ps.setInt(2, channel);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        map.setMapTimer(rs.getLong("time"));
                    }
                    ps.close();
                    rs.close();
                    con.close();
                } catch (Exception e) {
                    System.err.println("[Error] Failed to apply Boss Map Cooltime.");
                    if (!ServerConstants.realese) {
                        e.printStackTrace();
                    }
                }
                try {
                    map.setMapName(
                            MapleDataTool.getString("mapName", nameData.getChildByPath(getMapStringName(omapid)), ""));
                    map.setStreetName(MapleDataTool.getString("streetName",
                            nameData.getChildByPath(getMapStringName(omapid)), ""));
                } catch (Exception e) {
                    map.setMapName("");
                    map.setStreetName("");
                }
                map.setClock(mapData.getChildByPath("clock") != null);
                map.setEverlast(mapData.getChildByPath("info/everlast") != null);
                map.setPersonalShop(mapData.getChildByPath("info/personalShop") != null);
                map.setHPDec(MapleDataTool.getInt(mapData.getChildByPath("info/decHP"), 0));
                map.setHPDecProtect(MapleDataTool.getInt(mapData.getChildByPath("info/protectItem"), 0));
                map.setBarrier(MapleDataTool.getInt(mapData.getChildByPath("info/barrier"), 0));
                map.setForcedReturnMap(MapleDataTool.getInt(mapData.getChildByPath("info/forcedReturn"), 999999999));
                map.setTimeLimit(MapleDataTool.getInt(mapData.getChildByPath("info/timeLimit"), -1));
                map.setFieldLimit(MapleDataTool.getInt(mapData.getChildByPath("info/fieldLimit"), 0));
                map.setFieldScript(MapleDataTool.getString(mapData.getChildByPath("info/fieldScript"), ""));
                map.setFirstUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onFirstUserEnter"), ""));
                map.setUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onUserEnter"), ""));
                map.setFieldType(MapleDataTool.getString(mapData.getChildByPath("info/fieldType"), ""));
                map.setRecoveryRate(MapleDataTool.getFloat(mapData.getChildByPath("info/recovery"), 1));
                map.setCreateMobInterval(
                        (short) MapleDataTool.getInt(mapData.getChildByPath("info/createMobInterval"), 9000));
                map.setFixedMob(MapleDataTool.getInt(mapData.getChildByPath("info/fixedMobCapacity"), 0));
                map.loadMonsterRate(true);
                map.setTown(MapleDataTool.getInt(mapData.getChildByPath("info/town"), 0) != 0 ? true : false);
                maps.put(omapid, map);
            } finally {
                lock.unlock();
            }
        }
        return map;
    }

    public boolean isInstanceMapLoaded(int instanceid) {
        return instanceMap.containsKey(instanceid);
    }

    public MapleMap getInstanceMap(final int instanceid) {
        return instanceMap.get(instanceid);
    }

    public void removeInstanceMap(final int instanceid) {
        if (isInstanceMapLoaded(instanceid)) {
            instanceMap.remove(instanceid);
        }
    }

    public MapleMap CreateInstanceMap(int mapid, boolean respawns, boolean npcs, boolean reactors, int instanceid) {
        if (isInstanceMapLoaded(instanceid)) {
            return getInstanceMap(instanceid);
        }
        MapleData mapData = source.getData(getMapName(mapid));
        MapleData link = mapData.getChildByPath("info/link");
        if (link != null) {
            mapData = source.getData(getMapName(MapleDataTool.getIntConvert("info/link", mapData)));
        }

        float monsterRate = 0;
        if (respawns) {
            MapleData mobRate = mapData.getChildByPath("info/mobRate");
            if (mobRate != null) {
                monsterRate = ((Float) mobRate.getData()).floatValue();
            }
        }
        MapleMap map = new MapleMap(mapid, channel, MapleDataTool.getInt("info/returnMap", mapData), monsterRate);

        PortalFactory portalFactory = new PortalFactory();
        for (MapleData portal : mapData.getChildByPath("portal")) {
            map.addPortal(portalFactory.makePortal(MapleDataTool.getInt(portal.getChildByPath("pt")), portal));
        }
        List<MapleFoothold> allFootholds = new LinkedList<MapleFoothold>();
        Point lBound = new Point();
        Point uBound = new Point();
        for (MapleData footRoot : mapData.getChildByPath("foothold")) {
            for (MapleData footCat : footRoot) {
                for (MapleData footHold : footCat) {
                    MapleFoothold fh = new MapleFoothold(
                            new Point(MapleDataTool.getInt(footHold.getChildByPath("x1")),
                                    MapleDataTool.getInt(footHold.getChildByPath("y1"))),
                            new Point(MapleDataTool.getInt(footHold.getChildByPath("x2")),
                                    MapleDataTool.getInt(footHold.getChildByPath("y2"))),
                            Integer.parseInt(footHold.getName()));
                    fh.setPrev((short) MapleDataTool.getInt(footHold.getChildByPath("prev")));
                    fh.setNext((short) MapleDataTool.getInt(footHold.getChildByPath("next")));

                    if (fh.getX1() < lBound.x) {
                        lBound.x = fh.getX1();
                    }
                    if (fh.getX2() > uBound.x) {
                        uBound.x = fh.getX2();
                    }
                    if (fh.getY1() < lBound.y) {
                        lBound.y = fh.getY1();
                    }
                    if (fh.getY2() > uBound.y) {
                        uBound.y = fh.getY2();
                    }
                    allFootholds.add(fh);
                }
            }
        }
        MapleFootholdTree fTree = new MapleFootholdTree(lBound, uBound);
        for (MapleFoothold fh : allFootholds) {
            fTree.insert(fh);
        }
        map.setFootholds(fTree);

        // load areas (EG PQ platforms)
        if (mapData.getChildByPath("area") != null) {
            int x1, y1, x2, y2;
            Rectangle mapArea;
            for (MapleData area : mapData.getChildByPath("area")) {
                x1 = MapleDataTool.getInt(area.getChildByPath("x1"));
                y1 = MapleDataTool.getInt(area.getChildByPath("y1"));
                x2 = MapleDataTool.getInt(area.getChildByPath("x2"));
                y2 = MapleDataTool.getInt(area.getChildByPath("y2"));
                mapArea = new Rectangle(x1, y1, (x2 - x1), (y2 - y1));
                map.addMapleArea(mapArea);
            }
        }
        int bossid = -1;
        String msg = null;
        if (mapData.getChildByPath("info/timeMob") != null) {
            bossid = MapleDataTool.getInt(mapData.getChildByPath("info/timeMob/id"), 0);
            msg = MapleDataTool.getString(mapData.getChildByPath("info/timeMob/message"), null);
        }

        // load life data (npc, monsters)
        String type;
        AbstractLoadedMapleLife myLife;

        for (MapleData life : mapData.getChildByPath("life")) {
            type = MapleDataTool.getString(life.getChildByPath("type"));
            if (npcs || !type.equals("n")) {
                myLife = loadLife(life, MapleDataTool.getString(life.getChildByPath("id")), type);
                if (myLife instanceof MapleMonster) {
                    final MapleMonster mob = (MapleMonster) myLife;
                    map.addMonsterSpawn(mob, MapleDataTool.getInt("mobTime", life, 0),
                            mob.getId() == bossid ? msg : null);
                    if (!mob.getStats().isBoss()) {
                        map.addMonsterSpawn(mob, MapleDataTool.getInt("mobTime", life, 0),
                                mob.getId() == bossid ? msg : null);
                    }
                } else {
                    map.addMapObject(myLife);
                }
            }
        }
        addAreaBossSpawn(map);

        map.setNodes(loadNodes(mapid, mapData));
        // load reactor data
        String id;
        if (reactors && mapData.getChildByPath("reactor") != null) {
            for (MapleData reactor : mapData.getChildByPath("reactor")) {
                id = MapleDataTool.getString(reactor.getChildByPath("id"));
                if (id != null) {
                    map.spawnReactor(
                            loadReactor(reactor, id, (byte) MapleDataTool.getInt(reactor.getChildByPath("f"), 0)));
                }
            }
        }
        try {
            map.setMapName(MapleDataTool.getString("mapName", nameData.getChildByPath(getMapStringName(mapid)), ""));
            map.setStreetName(
                    MapleDataTool.getString("streetName", nameData.getChildByPath(getMapStringName(mapid)), ""));
        } catch (Exception e) {
            map.setMapName("");
            map.setStreetName("");
        }
        map.setClock(mapData.getChildByPath("clock") != null);
        map.setEverlast(mapData.getChildByPath("info/everlast") != null);
        map.setTown(mapData.getChildByPath("info/town") != null);
        map.setHPDec(MapleDataTool.getInt(mapData.getChildByPath("info/decHP"), 0));
        map.setHPDecProtect(MapleDataTool.getInt(mapData.getChildByPath("info/protectItem"), 0));
        map.setBarrier(MapleDataTool.getInt(mapData.getChildByPath("info/barrier"), 0));
        map.setForcedReturnMap(MapleDataTool.getInt(mapData.getChildByPath("info/forcedReturn"), 999999999));
        map.setTimeLimit(MapleDataTool.getInt(mapData.getChildByPath("info/timeLimit"), -1));
        map.setFieldLimit(MapleDataTool.getInt(mapData.getChildByPath("info/fieldLimit"), 0));
        map.setFieldScript(MapleDataTool.getString(mapData.getChildByPath("info/fieldScript"), ""));
        map.setFirstUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onFirstUserEnter"), ""));
        map.setUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onUserEnter"), ""));
        map.setFieldType(MapleDataTool.getString(mapData.getChildByPath("info/fieldType"), ""));
        map.setRecoveryRate(MapleDataTool.getFloat(mapData.getChildByPath("info/recovery"), 1));
        map.setCreateMobInterval((short) MapleDataTool.getInt(mapData.getChildByPath("info/createMobInterval"), 9000));
        map.setFixedMob(MapleDataTool.getInt(mapData.getChildByPath("info/fixedMobCapacity"), 0));
        map.loadMonsterRate(true);

        map.setInstanceId(instanceid);
        instanceMap.put(instanceid, map);
        return map;
    }

    public int getLoadedMaps() {
        return maps.size();
    }

    public boolean isMapLoaded(int mapId) {
        return maps.containsKey(mapId);
    }

    public void clearLoadedMap() {
        maps.clear();
    }

    public List<MapleMap> getAllLoadedMaps() {
        List<MapleMap> ret = new ArrayList<>();
        lock.lock();
        try {
            ret.addAll(maps.values());
            ret.addAll(instanceMap.values());
        } finally {
            lock.unlock();
        }
        return ret;
    }

    public Map<Integer, MapleMap> getMaps() {
        return maps;
    }

    private AbstractLoadedMapleLife loadLife(MapleData life, String id, String type) {
        AbstractLoadedMapleLife myLife = MapleLifeProvider.getLife(Integer.parseInt(id), type);
        myLife.setCy(MapleDataTool.getInt(life.getChildByPath("cy")));
        MapleData dF = life.getChildByPath("f");
        if (dF != null) {
            myLife.setF(MapleDataTool.getInt(dF));
        }
        myLife.setFh(MapleDataTool.getInt(life.getChildByPath("fh")));
        myLife.setRx0(MapleDataTool.getInt(life.getChildByPath("rx0")));
        myLife.setRx1(MapleDataTool.getInt(life.getChildByPath("rx1")));
        myLife.setPosition(new Point(MapleDataTool.getInt(life.getChildByPath("x")),
                MapleDataTool.getInt(life.getChildByPath("y"))));

        if (MapleDataTool.getInt("hide", life, 0) == 1) {
            myLife.setHide(true);
        }
        return myLife;
    }

    private final MapleReactor loadReactor(final MapleData reactor, final String id, final byte FacingDirection) {
        final MapleReactorStats stats = MapleReactorFactory.getReactor(Integer.parseInt(id));
        final MapleReactor myReactor = new MapleReactor(stats, Integer.parseInt(id));

        stats.setFacingDirection(FacingDirection);
        myReactor.setPosition(new Point(MapleDataTool.getInt(reactor.getChildByPath("x")),
                MapleDataTool.getInt(reactor.getChildByPath("y"))));
        myReactor.setDelay(MapleDataTool.getInt(reactor.getChildByPath("reactorTime")) * 1000);
        myReactor.setState((byte) 0);
        myReactor.setName(MapleDataTool.getString(reactor.getChildByPath("name"), ""));

        return myReactor;
    }

    private String getFieldGenerator() {
        StringBuilder builder = new StringBuilder("Map/FieldGenerator.img");
        return builder.toString();
    }

    private String getMapName(int mapid) {
        String mapName = StringUtil.getLeftPaddedStr(Integer.toString(mapid), '0', 9);
        StringBuilder builder = new StringBuilder("Map/Map");
        builder.append(mapid / 100000000);
        builder.append("/");
        builder.append(mapName);
        builder.append(".img");

        mapName = builder.toString();
        return mapName;
    }

    private String getMapStringName(int mapid) {
        StringBuilder builder = new StringBuilder();
        if (mapid < 100000000) {
            builder.append("maple");
        } else if (mapid >= 100000000 && mapid < 200000000) {
            builder.append("victoria");
        } else if (mapid >= 200000000 && mapid < 300000000) {
            builder.append("ossyria");
        } else if (mapid >= 300000000 && mapid < 400000000) {
            builder.append("3rd");
        } else if (mapid >= 540000000 && mapid < 541010110) {
            builder.append("singapore");
        } else if (mapid >= 600000000 && mapid < 620000000) {
            builder.append("MasteriaGL");
        } else if (mapid >= 670000000 && mapid < 682000000) {
            builder.append("weddingGL");
        } else if (mapid >= 682000000 && mapid < 683000000) {
            builder.append("HalloweenGL");
        } else if (mapid >= 800000000 && mapid < 900000000) {
            builder.append("jp");
        } else {
            builder.append("etc");
        }
        builder.append("/");
        builder.append(mapid);
        
        return builder.toString();
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    private void addAreaBossSpawn(final MapleMap map) {
        int monsterid = -1;
        int mobtime = -1;
        String msg = null;
        Point pos1 = null, pos2 = null, pos3 = null;

        switch (map.getId()) {
            case 104000400: // Mano
                mobtime = 2700;
                monsterid = 2220000;
                msg = "A cool breeze was felt when Mano appeared.";
                pos1 = new Point(439, 185);
                pos2 = new Point(301, -85);
                pos3 = new Point(107, -355);
                break;
            case 101030404: // Stumpy
                mobtime = 2700;
                monsterid = 3220000;
                msg = "Stumpy has appeared with a stumping sound that rings the Stone Mountain.";
                pos1 = new Point(867, 1282);
                pos2 = new Point(810, 1570);
                pos3 = new Point(838, 2197);
                break;
            case 110040000: // King Clang
                mobtime = 1200;
                monsterid = 5220001;
                msg = "A strange turban shell has appeared on the beach.";
                pos1 = new Point(-355, 179);
                pos2 = new Point(-1283, -113);
                pos3 = new Point(-571, -593);
                break;
            case 250010304: // Tae Roon
                mobtime = 2100;
                monsterid = 7220000;
                msg = "Tae Roon appeared with a loud grow.";
                pos1 = new Point(-210, 33);
                pos2 = new Point(-234, 393);
                pos3 = new Point(-654, 33);
                break;
            case 200010300: // Eliza
                mobtime = 1200;
                monsterid = 8220000;
                msg = "Eliza has appeared with a black whirlwind.";
                pos1 = new Point(665, 83);
                pos2 = new Point(672, -217);
                pos3 = new Point(-123, -217);
                break;
            case 250010503: // Ghost Priest
                mobtime = 1800;
                monsterid = 7220002;
                msg = "The area fills with an unpleasant force of evil.. even the occasional ones of the cats sound disturbing";
                pos1 = new Point(-303, 543);
                pos2 = new Point(227, 543);
                pos3 = new Point(719, 543);
                break;
            case 222010310: // Old Fox
                mobtime = 2700;
                monsterid = 7220001;
                msg = "As the moon light dims,a long fox cry can be heard and the presence of the old fox can be felt.";
                pos1 = new Point(-169, -147);
                pos2 = new Point(-517, 93);
                pos3 = new Point(247, 93);
                break;
            case 107000300: // Dale
                mobtime = 1800;
                monsterid = 6220000;
                msg = "The huge crocodile Dale has come out from the swamp.";
                pos1 = new Point(710, 118);
                pos2 = new Point(95, 119);
                pos3 = new Point(-535, 120);
                break;
            case 100040105: // Faust
                mobtime = 1800;
                monsterid = 5220002;
                msg = "The blue fog became darker when Faust appeared.";
                pos1 = new Point(1000, 278);
                pos2 = new Point(557, 278);
                pos3 = new Point(95, 278);
                break;
            case 100040106: // Faust
                mobtime = 1800;
                monsterid = 5220002;
                msg = "The blue fog became darker when Faust appeared.";
                pos1 = new Point(1000, 278);
                pos2 = new Point(557, 278);
                pos3 = new Point(95, 278);
                break;
            case 220050100: // Timer
                mobtime = 1500;
                monsterid = 5220003;
                msg = "Click clock! Timer has appeared with an irregular clock sound.";
                pos1 = new Point(-467, 1032);
                pos2 = new Point(532, 1032);
                pos3 = new Point(-47, 1032);
                break;
            case 221040301: // Jeno
                mobtime = 2400;
                monsterid = 6220001;
                msg = "Jeno has appeared with a heavy sound of machinery.";
                pos1 = new Point(-4134, 416);
                pos2 = new Point(-4283, 776);
                pos3 = new Point(-3292, 776);
                break;
            case 260010201: // Dewu
                mobtime = 3600;
                monsterid = 3220001;
                msg = "Dewu slowly appeared out of the sand dust.";
                pos1 = new Point(-215, 275);
                pos2 = new Point(298, 275);
                pos3 = new Point(592, 275);
                break;
            case 261030000: // Chimera
                mobtime = 2700;
                monsterid = 8220002;
                msg = "Chimera has appeared out of the darkness of the underground with a glitter in her eyes.";
                pos1 = new Point(-1094, -405);
                pos2 = new Point(-772, -116);
                pos3 = new Point(-108, 181);
                break;
            case 230020100: // Sherp
                mobtime = 2700;
                monsterid = 4220000;
                msg = "A strange shell has appeared from a grove of seaweed.";
                pos1 = new Point(-291, -20);
                pos2 = new Point(-272, -500);
                pos3 = new Point(-462, 640);
                break;
            default:
                return;
        }
        map.addAreaMonsterSpawn(MapleLifeProvider.getMonster(monsterid), pos1, pos2, pos3, mobtime, msg);
    }

    private MapleNodes loadNodes(final int mapid, final MapleData mapData) {
        MapleNodes nodeInfo = new MapleNodes(mapid);
        if (mapData.getChildByPath("nodeInfo") != null) {
            for (MapleData node : mapData.getChildByPath("nodeInfo")) {
                try {
                    if (node.getName().equals("start")) {
                        nodeInfo.setNodeStart(MapleDataTool.getInt(node, 0));
                        continue;
                    }
                    List<Integer> edges = new ArrayList<>();
                    if (node.getChildByPath("edge") != null) {
                        for (MapleData edge : node.getChildByPath("edge")) {
                            edges.add(MapleDataTool.getInt(edge, -1));
                        }
                    }
                    final MapleNodeInfo mni = new MapleNodeInfo(Integer.parseInt(node.getName()),
                            MapleDataTool.getIntConvert("key", node, 0), MapleDataTool.getIntConvert("x", node, 0),
                            MapleDataTool.getIntConvert("y", node, 0), MapleDataTool.getIntConvert("attr", node, 0),
                            edges);
                    nodeInfo.addNode(mni);
                } catch (NumberFormatException e) {
                } // start, end, edgeInfo = we dont need it
            }
            nodeInfo.sortNodes();
        }
        for (int i = 1; i <= 7; i++) {
            if (mapData.getChildByPath(String.valueOf(i)) != null && mapData.getChildByPath(i + "/obj") != null) {
                for (MapleData node : mapData.getChildByPath(i + "/obj")) {
                    if (node.getChildByPath("SN_count") != null && node.getChildByPath("speed") != null) {
                        int sn_count = MapleDataTool.getIntConvert("SN_count", node, 0);
                        String name = MapleDataTool.getString("name", node, "");
                        int speed = MapleDataTool.getIntConvert("speed", node, 0);
                        if (sn_count <= 0 || speed <= 0 || name.equals("")) {
                            continue;
                        }
                        final List<Integer> SN = new ArrayList<>();
                        for (int x = 0; x < sn_count; x++) {
                            SN.add(MapleDataTool.getIntConvert("SN" + x, node, 0));
                        }
                        final MaplePlatform mni = new MaplePlatform(name, MapleDataTool.getIntConvert("start", node, 2),
                                speed, MapleDataTool.getIntConvert("x1", node, 0),
                                MapleDataTool.getIntConvert("y1", node, 0), MapleDataTool.getIntConvert("x2", node, 0),
                                MapleDataTool.getIntConvert("y2", node, 0), MapleDataTool.getIntConvert("r", node, 0),
                                SN);
                        nodeInfo.addPlatform(mni);
                    } else if (node.getChildByPath("tags") != null) {
                        String name = MapleDataTool.getString("tags", node, "");
                        nodeInfo.addFlag(new Pair<>(name, name.endsWith("3") ? 1 : 0)); // idk, no indication in lib
                    }
                }
            }
        }
        // load areas (EG PQ platforms)
        if (mapData.getChildByPath("area") != null) {
            int x1, y1, x2, y2;
            Rectangle mapArea;
            for (MapleData area : mapData.getChildByPath("area")) {
                x1 = MapleDataTool.getInt(area.getChildByPath("x1"));
                y1 = MapleDataTool.getInt(area.getChildByPath("y1"));
                x2 = MapleDataTool.getInt(area.getChildByPath("x2"));
                y2 = MapleDataTool.getInt(area.getChildByPath("y2"));
                mapArea = new Rectangle(x1, y1, (x2 - x1), (y2 - y1));
                nodeInfo.addMapleArea(mapArea);
            }
        }
        if (mapData.getChildByPath("CaptureTheFlag") != null) {
            final MapleData mc = mapData.getChildByPath("CaptureTheFlag");
            for (MapleData area : mc) {
                nodeInfo.addGuardianSpawn(
                        new Point(MapleDataTool.getInt(area.getChildByPath("FlagPositionX")),
                                MapleDataTool.getInt(area.getChildByPath("FlagPositionY"))),
                        area.getName().startsWith("Red") ? 0 : 1);
            }
        }
        if (mapData.getChildByPath("directionInfo") != null) {
            final MapleData mc = mapData.getChildByPath("directionInfo");
            for (MapleData area : mc) {
                DirectionInfo di = new DirectionInfo(Integer.parseInt(area.getName()),
                        MapleDataTool.getInt("x", area, 0), MapleDataTool.getInt("y", area, 0),
                        MapleDataTool.getInt("forcedInput", area, 0) > 0);
                final MapleData mc2 = area.getChildByPath("eventQ");
                if (mc2 != null) {
                    for (MapleData event : mc2) {
                        di.eventQ.add(MapleDataTool.getString(event));
                    }
                }
                nodeInfo.addDirection(Integer.parseInt(area.getName()), di);
            }
        }
        if (mapData.getChildByPath("monsterCarnival") != null) {
            final MapleData mc = mapData.getChildByPath("monsterCarnival");
            if (mc.getChildByPath("mobGenPos") != null) {
                for (MapleData area : mc.getChildByPath("mobGenPos")) {
                    nodeInfo.addMonsterPoint(MapleDataTool.getInt(area.getChildByPath("x")),
                            MapleDataTool.getInt(area.getChildByPath("y")),
                            MapleDataTool.getInt(area.getChildByPath("fh")),
                            MapleDataTool.getInt(area.getChildByPath("cy")), MapleDataTool.getInt("team", area, -1));
                }
            }
            if (mc.getChildByPath("mob") != null) {
                for (MapleData area : mc.getChildByPath("mob")) {
                    nodeInfo.addMobSpawn(MapleDataTool.getInt(area.getChildByPath("id")),
                            MapleDataTool.getInt(area.getChildByPath("spendCP")));
                }
            }
            if (mc.getChildByPath("guardianGenPos") != null) {
                for (MapleData area : mc.getChildByPath("guardianGenPos")) {
                    nodeInfo.addGuardianSpawn(
                            new Point(MapleDataTool.getInt(area.getChildByPath("x")),
                                    MapleDataTool.getInt(area.getChildByPath("y"))),
                            MapleDataTool.getInt("team", area, -1));
                }
            }
            if (mc.getChildByPath("skill") != null) {
                for (MapleData area : mc.getChildByPath("skill")) {
                    nodeInfo.addSkillId(MapleDataTool.getInt(area));
                }
            }
        }
        return nodeInfo;
    }
}
