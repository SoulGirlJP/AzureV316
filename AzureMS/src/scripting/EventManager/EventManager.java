package scripting.EventManager;

import client.Character.MapleCharacter;
import client.Community.MapleExpedition.MapleExpedition;
import client.Community.MapleGuild.MapleSquadLegacy;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ScheduledFuture;

import javax.script.Invocable;
import javax.script.ScriptException;

import client.MapleClient;
import connections.Packets.MainPacketCreator;
import launcher.ServerPortInitialize.ChannelServer;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.OverrideMonsterStats;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapReactor.MapleReactor;
import server.Maps.MapReactor.MapleReactorFactory;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleWorldMap.MapleWorldMapProvider;
import tools.Timer.EventTimer;
import tools.RandomStream.Randomizer;

public class EventManager {

    private static final int[] eventChannel = new int[2];
    private final Invocable iv;
    private final int channel;
    private final Map<String, EventInstanceManager> instances = new WeakHashMap<>();
    private final Properties props = new Properties();
    private final String name;

    public EventManager(ChannelServer cserv, Invocable iv, String name) {
        this.iv = iv;
        this.channel = cserv.getChannel();
        this.name = name;
    }

    public void cancel() {
        try {
            iv.invokeFunction("cancelSchedule", (Object) null);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : cancelSchedule:\n" + ex);
        }
    }

    public ScheduledFuture<?> schedule(final String methodName, long delay) {
        return EventTimer.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                try {
                    iv.invokeFunction(methodName, (Object) null);
                } catch (NoSuchMethodException | ScriptException ex) {
                    System.out.println("Event name : " + name + ", method Name : " + methodName + ":\n" + ex);
                }
            }
        }, delay);
    }

    public ScheduledFuture<?> schedule(final String methodName, long delay, final EventInstanceManager eim) {
        return EventTimer.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                try {
                    iv.invokeFunction(methodName, eim);
                } catch (NoSuchMethodException | ScriptException ex) {
                    System.out.println("Event name : " + name + ", method Name : " + methodName + ":\n" + ex);
                }
            }
        }, delay);
    }

    public ScheduledFuture<?> scheduleAtTimestamp(final String methodName, long timestamp) {
        return EventTimer.getInstance().scheduleAtTimestamp(new Runnable() {

            @Override
            public void run() {
                try {
                    iv.invokeFunction(methodName, (Object) null);
                } catch (ScriptException | NoSuchMethodException ex) {
                    System.out.println("Event name : " + name + ", method Name : " + methodName + ":\n" + ex);
                }
            }
        }, timestamp);
    }

    public int getChannel() {
        return channel;
    }

    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(channel);
    }

    public EventInstanceManager getInstance(String name) {
        return instances.get(name);
    }

    public Collection<EventInstanceManager> getInstances() {
        return Collections.unmodifiableCollection(instances.values());
    }

    public EventInstanceManager newInstance(String name) {
        EventInstanceManager ret = new EventInstanceManager(this, name, channel);
        instances.put(name, ret);
        return ret;
    }

    public void disposeInstance(String name) {
        instances.remove(name);
        if (getProperty("state") != null && instances.isEmpty()) {
            setProperty("state", "0");
        }
        if (getProperty("leader") != null && instances.isEmpty() && getProperty("leader").equals("false")) {
            setProperty("leader", "true");
        }
    }

    public Invocable getIv() {
        return iv;
    }

    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public final Properties getProperties() {
        return props;
    }

    public String getName() {
        return name;
    }

    public void startInstance() {
        try {
            iv.invokeFunction("setup", (Object) null);
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public void startInstance_Solo(String mapid, MapleCharacter chr) {
        try {
            EventInstanceManager eim = (EventInstanceManager) iv.invokeFunction("setup", (Object) mapid);
            eim.registerPlayer(chr);
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public void startInstance(String mapid, MapleCharacter chr) {
        try {
            EventInstanceManager eim = (EventInstanceManager) iv.invokeFunction("setup", (Object) mapid);
            eim.registerCarnivalParty(chr, chr.getMap(), (byte) 0);
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public EventInstanceManager readyInstance() {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", (Object) null));
            return eim;
        } catch (ScriptException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        System.out.println("NULL");
        return null;
    }

    public void startInstance_Party(String mapid, MapleCharacter chr) {
        try {
            EventInstanceManager eim = (EventInstanceManager) iv.invokeFunction("setup", (Object) mapid);
            eim.registerParty(chr.getParty(), chr.getMap());
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    // GPQ
    public void startInstance(MapleCharacter character, String leader) {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", (Object) null));
            eim.registerPlayer(character);
            eim.setProperty("leader", leader);
            eim.setProperty("guildid", String.valueOf(character.getGuildId()));
            setProperty("guildid", String.valueOf(character.getGuildId()));
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-Guild:\n" + ex);
        }
    }

    public void startInstance_CharID(MapleCharacter character) {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", character.getId()));
            eim.registerPlayer(character);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-CharID:\n" + ex);
        }
    }

    public void startInstance_CharMapID(MapleCharacter character) {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", character.getId(),
                    character.getMapId()));
            eim.registerPlayer(character);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-CharID:\n" + ex);
        }
    }

    public void startInstance(MapleCharacter character) {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", (Object) null));
            eim.registerPlayer(character);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-character:\n" + ex);
        }
    }

    // PQ method: starts a PQ
    public void startInstance(MapleParty party, MapleMap map) {
        startInstance(party, map, 255);
    }

    public void startInstance(MapleParty party, MapleMap map, int maxLevel) {
        try {
            int averageLevel = 0, size = 0;
            for (MaplePartyCharacter mpc : party.getMembers()) {
                if (mpc.isOnline() && mpc.getMapid() == map.getId()) {
                    averageLevel += mpc.getLevel();
                    size++;
                }
            }
            if (size <= 0) {
                return;
            }
            averageLevel /= size;
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup",
                    Math.min(maxLevel, averageLevel), party.getId()));
            eim.registerParty(party, map);
        } catch (ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-partyid:\n" + ex);
        } catch (Exception ex) {
            // ignore
            startInstance_NoID(party, map, ex);
        }
    }

    public void startInstance(MapleClient c, MapleExpedition exped, MapleMap map, int maxLevel) {
        try {
            int averageLevel = 0, size = 0;
            for (MapleCharacter mpc : exped.getExpeditionMembers(c)) {
                if (mpc != null && mpc.getMapId() == map.getId()) {
                    averageLevel += mpc.getLevel();
                    size++;
                }
            }
            if (size <= 0) {
                return;
            }
            averageLevel /= size;
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup",
                    Math.min(maxLevel, averageLevel), exped.getId()));
            eim.registerExpedition(c, exped, map);
        } catch (ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-partyid:\n" + ex);
        } catch (Exception ex) {
            // ignore
            startInstance_NoID(c, exped, map, ex);
        }
    }

    public void startInstance_NoID(MapleParty party, MapleMap map) {
        startInstance_NoID(party, map, null);
    }

    public void startInstance_NoID(MapleParty party, MapleMap map, final Exception old) {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", (Object) null));
            eim.registerParty(party, map);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-party:\n" + ex);
        }
    }

    public void startInstance_NoID(MapleClient c, MapleExpedition exped, MapleMap map, final Exception old) {
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", (Object) null));
            eim.registerExpedition(c, exped, map);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-party:\n" + ex);
        }
    }

    // non-PQ method for starting instance
    public void startInstance(EventInstanceManager eim, String leader) {
        try {
            iv.invokeFunction("setup", eim);
            eim.setProperty("leader", leader);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-leader:\n" + ex);
        }
    }

    public void startInstance(MapleSquadLegacy squad, MapleMap map) {
        startInstance(squad, map, -1);
    }

    public void startInstance(MapleSquadLegacy squad, MapleMap map, int questID) {
        if (squad.getStatus() == 0) {
            return; // we dont like cleared squads
        }
        try {
            EventInstanceManager eim = (EventInstanceManager) (iv.invokeFunction("setup", squad.getLeader().getName()));
            eim.registerSquad(squad, map, questID);
        } catch (NoSuchMethodException | ScriptException ex) {
            System.out.println("Event name : " + name + ", method Name : setup-squad:\n" + ex);
        }
    }

    public void warpAllPlayer(int from, int to) {
        final MapleMap tomap = getMapFactory().getMap(to);
        final MapleMap frommap = getMapFactory().getMap(from);
        List<MapleCharacter> list = frommap.getCharacters();
        if (tomap != null && frommap != null && list != null && frommap.getCharactersSize() > 0) {
            for (MapleMapObject mmo : list) {
                ((MapleCharacter) mmo).changeMap(tomap, tomap.getPortal(0));
            }
        }
    }

    public MapleWorldMapProvider getMapFactory() {
        return getChannelServer().getMapFactory();
    }

    public OverrideMonsterStats newMonsterStats() {
        return new OverrideMonsterStats();
    }

    public List<MapleCharacter> newCharList() {
        return new ArrayList<>();
    }

    public MapleMonster getMonster(final int id) {
        return MapleLifeProvider.getMonster(id);
    }

    public MapleReactor getReactor(final int id) {
        return new MapleReactor(MapleReactorFactory.getReactor(id), id);
    }

    public void broadcastYellowMsg(final String msg) {
        getChannelServer().broadcastPacket(MainPacketCreator.getGMText(8, msg));
    }

    public void broadcastServerMsg(final int type, final String msg, final boolean weather) {
        if (!weather) {
            getChannelServer().broadcastPacket(MainPacketCreator.serverNotice(type, msg));
        } else {
            for (MapleMap load : getMapFactory().getMaps().values()) {
                if (load.getCharactersSize() > 0) {
                    load.startMapEffect(msg, type);
                }
            }
        }
    }

    public boolean scheduleRandomEvent() {
        boolean event = false;
        for (int i = 0; i < eventChannel.length; i++) {
            event |= scheduleRandomEventInChannel(eventChannel[i]);
        }
        return event;
    }

    public boolean scheduleRandomEventInChannel(int chz) {
        return true;
    }

    public void setWorldEvent() {
        for (int i = 0; i < eventChannel.length; i++) {
            eventChannel[i] = Randomizer.nextInt(ChannelServer.getAllInstances().size() - 4) + 2 + i; // 2-13
        }
    }

    public void test(String test) {
        System.out.println(test);
    }

    public void broadcastShip(final int mapid, final int effect, final int mode) {
        getMapFactory().getMap(mapid).broadcastMessage(MainPacketCreator.boatPacket(effect, mode));
    }

}
