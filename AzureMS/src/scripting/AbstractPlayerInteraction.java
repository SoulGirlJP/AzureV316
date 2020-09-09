package scripting;

import java.awt.Point;
import java.util.List;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.ItemInventory.PetsMounts.MaplePet;
import client.MapleProfession;
import client.MapleProfessionType;
import client.MapleQuestStatus;
import client.ItemInventory.Equip;
import client.ItemInventory.IItem;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Community.MapleExpedition.MapleExpedition;
import client.Community.MapleGuild.MapleGuild;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import constants.GameConstants;
import connections.Database.MYSQL;
import java.util.ArrayList;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.WideObjectHolder;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;
import connections.Packets.EffectPacket;
import connections.Packets.MainPacketCreator;
import connections.Packets.PetPacket;
import connections.Packets.UIPacket;
import server.Events.MapleDojoAgent;
import server.Events.MapleEvent;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapReactor.MapleReactor;
import server.Maps.SavedLocationType;
import server.Quests.MapleQuest;
import tools.CurrentTime;
import tools.FileoutputUtil;
import tools.Pair;
import tools.Timer.EventTimer;
import tools.RandomStream.Randomizer;
import tools.Triple;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import scripting.EventManager.EventInstanceManager;
import scripting.EventManager.EventManager;
import scripting.NPC.NPCScriptManager;
import scripting.NPC.NPCTalk;

public abstract class AbstractPlayerInteraction {

    protected MapleClient c;
    public int id;
    protected int id2;
    protected String script;

    public AbstractPlayerInteraction(final MapleClient c, final int id, final int id2, final String script) {
        this.c = c;
        this.id = id;
        this.id2 = id2;
        this.script = script;
    }

    public final MapleClient getClient() {
        return c;
    }

    public final MapleClient getC() {
        return c;
    }

    public MapleCharacter getChar() {
        return c.getPlayer();
    }

    public final ChannelServer getChannelServer() {
        return c.getChannelServer();
    }

    public final MapleCharacter getPlayer() {
        return c.getPlayer();
    }

    public final EventManager getEventManager(final String event) {
        return c.getChannelServer().getEventSM().getEventManager(event);
    }

    public final EventInstanceManager getEventInstance() {
        return c.getPlayer().getEventInstance();
    }

    public final boolean allPartyItem(int itemid, int number) {
        for (final MapleCharacter partymem : c.getChannelServer().getPartyMembers(getPlayer().getParty())) {
            if (!partymem.haveItem(itemid)) {
                return true;
            }
        }
        for (final MapleCharacter partymem : c.getChannelServer().getPartyMembers(getPlayer().getParty())) {
            partymem.gainItem(itemid, (short) -1, false, -1, null);
        }
        return false;
    }

    public final void dispose() {
        NPCScriptManager.getInstance().dispose(c);
    }

    public final void openNpc(int npc, String filename) {
        NPCScriptManager.getInstance().start(c, npc, filename);
    }

    public final void openNpc(MapleClient client, int npc, String filename) {
        NPCScriptManager.getInstance().start(client, npc, filename);
    }

    public final void warp(final int map) {
        final MapleMap mapz = getWarpMap(map);
        try {
            c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        } catch (Exception e) {
            c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }

    public final void warp_Instanced(final int map) {
        final MapleMap mapz = getMap_Instanced(map);
        try {
            c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        } catch (Exception e) {
            c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }

    public final void warp(final int map, final int portal) {
        final MapleMap mapz = getWarpMap(map);
        if (portal != 0 && map == c.getPlayer().getMapId()) { // test
            final Point portalPos = new Point(c.getPlayer().getMap().getPortal(portal).getPosition());
            if (portalPos.distanceSq(getPlayer().getTruePosition()) < 90000.0) { // estimation
                c.getSession().writeAndFlush(MainPacketCreator.instantMapWarp((byte) portal)); // until we get packet
                // for far movement,
                // this will do
                c.getPlayer().checkFollow();
                c.getPlayer().getMap().movePlayer(c.getPlayer(), portalPos);
            } else {
                c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
            }
        } else {
            c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }

    public final void warpS(final int map, final int portal) {
        final MapleMap mapz = getWarpMap(map);
        c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }

    public final void warp(final int map, String portal) {
        final MapleMap mapz = getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        if (map == c.getPlayer().getMapId()) { // test
            final Point portalPos = new Point(c.getPlayer().getMap().getPortal(portal).getPosition());
            if (portalPos.distanceSq(getPlayer().getTruePosition()) < 90000.0) { // estimation
                c.getPlayer().checkFollow();
                c.getSession().writeAndFlush(
                        MainPacketCreator.instantMapWarp((byte) c.getPlayer().getMap().getPortal(portal).getId()));
                c.getPlayer().getMap().movePlayer(c.getPlayer(),
                        new Point(c.getPlayer().getMap().getPortal(portal).getPosition()));
            } else {
                c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
            }
        } else {
            c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }

    public final void warpS(final int map, String portal) {
        final MapleMap mapz = getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }

    public final void warpMap(final int mapid, final int portal) {
        final MapleMap map = getMap(mapid);
        for (MapleCharacter chr : c.getPlayer().getMap().getCharacters()) {
            chr.changeMap(map, map.getPortal(portal));
        }
    }

    public final void warpByName(final int mapid, final String chrname) {
        MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(chrname);
        if (chr == null) {
            c.getPlayer().dropMessage(1, "Could not find the character.");
            c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
            return;
        }
        final MapleMap mapz = getWarpMap(mapid);
        try {
            chr.changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
            chr.getClient().removeClickedNPC();
            NPCScriptManager.getInstance().dispose(chr.getClient());
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
        } catch (Exception e) {
            chr.changeMap(mapz, mapz.getPortal(0));
            chr.getClient().removeClickedNPC();
            NPCScriptManager.getInstance().dispose(chr.getClient());
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
        }
    }

    public final void mapChangeTimer(final int map, final int nextmap, final int time, final boolean notice) {
        final List<MapleCharacter> current = c.getChannelServer().getMapFactory().getMap(map).getCharacters();
        c.getChannelServer().getMapFactory().getMap(map).broadcastMessage(MainPacketCreator.getClock(time));
        if (notice) {
            c.getChannelServer().getMapFactory().getMap(map)
                    .startMapEffect("You will be moved out of the map when the timer ends.", 5120041);
        }
        EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (current != null) {
                    for (MapleCharacter chrs : current) {
                        chrs.changeMap(nextmap, 0);
                    }
                }
            }
        }, time * 1000); // seconds
    }

    public final void playPortalSE() {
        // c.getSession().writeAndFlush(MainPacketCreator.showSkillEffect(-1, 0, 20));
    }

    public final void showSkillEffect(int skillid, int level) {
        c.sendPacket(
                MainPacketCreator.showSkillEffect(-1, getPlayer().getLevel(), skillid, level, (byte) 0, 1, null, null));
    }

    private MapleMap getWarpMap(final int map) {
        return ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(map);
    }

    public final MapleMap getMap() {
        return c.getPlayer().getMap();
    }

    public final MapleMap getMap(final int map) {
        return getWarpMap(map);
    }

    public final MapleMap getMap_Instanced(final int map) {
        return c.getPlayer().getEventInstance() == null ? getMap(map)
                : c.getPlayer().getEventInstance().getMapInstance(map);
    }

    public void spawnMonster(final int id, final int qty) {
        spawnMob(id, qty, c.getPlayer().getTruePosition());
    }

    public final void spawnMobOnMap(final int id, final int qty, final int x, final int y, final int map) {
        for (int i = 0; i < qty; i++) {
            getMap(map).spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(id), new Point(x, y));
        }
    }

    public final void spawnMob(final int id, final int qty, final int x, final int y) {
        spawnMob(id, qty, new Point(x, y));
    }

    public final void spawnMob(final int id, final int x, final int y) {
        spawnMob(id, 1, new Point(x, y));
    }

    private void spawnMob(final int id, final int qty, final Point pos) {
        for (int i = 0; i < qty; i++) {
            c.getPlayer().getMap().spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(id), pos);
        }
    }

    public final void killMob(int ids) {
        c.getPlayer().getMap().killMonster(ids);
    }

    public final void killAllMob() {
        c.getPlayer().getMap().killAllMonsters(true);
    }

    public final void killAllMob2() {
        c.getPlayer().getMap().killAllMonsters2(true, c.getPlayer());
    }

    public final void addHP(final int delta) {
        c.getPlayer().addHP(delta);
    }

    public final int getPlayerStat(final String type) {
        switch (type) {
            case "LVL":
                return c.getPlayer().getLevel();
            case "STR":
                return c.getPlayer().getStat().getStr();
            case "DEX":
                return c.getPlayer().getStat().getDex();
            case "INT":
                return c.getPlayer().getStat().getInt();
            case "LUK":
                return c.getPlayer().getStat().getLuk();
            case "HP":
                return c.getPlayer().getStat().getHp();
            case "MP":
                return c.getPlayer().getStat().getMp();
            case "MaxHP":
                return c.getPlayer().getStat().getMaxHp();
            case "MaxMP":
                return c.getPlayer().getStat().getMaxMp();
            case "RAP":
                return c.getPlayer().getRemainingAp();
            case "RSP":
                return c.getPlayer().getRemainingSp(0);
            case "GID":
                return c.getPlayer().getGuildId();
            case "GRANK":
                return c.getPlayer().getGuildRank();
            case "ARANK":
                return c.getPlayer().getAllianceRank();
            case "GM":
                return c.getPlayer().isGM() ? 1 : 0;
            case "GENDER":
                return c.getPlayer().getGender();
            case "FACE":
                return c.getPlayer().getFace();
            case "HAIR":
                return c.getPlayer().getHair();
        }
        return -1;
    }

    public final String getName() {
        return c.getPlayer().getName();
    }

    public final boolean haveItem(final int itemid) {
        return haveItem(itemid, 1);
    }

    public final boolean haveItem(final int itemid, final int quantity) {
        return haveItem(itemid, quantity, false, true);
    }

    public final boolean haveItem(final int itemid, final int quantity, final boolean checkEquipped,
            final boolean greaterOrEquals) {
        return c.getPlayer().haveItem(itemid, quantity, checkEquipped, greaterOrEquals);
    }

    public final boolean canHold() {
        for (int i = 1; i <= 5; i++) {
            if (c.getPlayer().getInventory(MapleInventoryType.getByType((byte) i)).getNextFreeSlot() <= -1) {
                return false;
            }
        }
        return true;
    }

    public final boolean canHoldSlots(final int slot) {
        for (int i = 1; i <= 5; i++) {
            if (c.getPlayer().getInventory(MapleInventoryType.getByType((byte) i)).isFull(slot)) {
                return false;
            }
        }
        return true;
    }

    public final boolean canHoldEquip(final int slot) {
        if (c.getPlayer().getInventory(MapleInventoryType.getByType((byte) 1)).isFull(slot)) {
            return false;
        }
        return true;
    }

    public final boolean canHold(final int itemid) {
        return c.getPlayer().getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }

    public final boolean canHold(final int itemid, final int quantity) {
        return InventoryManipulator.checkSpace(c, itemid, quantity, "");
    }

    public final MapleQuestStatus getQuestRecord(final int id) {
        return c.getPlayer().getQuestNAdd(MapleQuest.getInstance(id));
    }

    public final MapleQuestStatus getQuestNoRecord(final int id) {
        return c.getPlayer().getQuestNoAdd(MapleQuest.getInstance(id));
    }

    public final byte getQuestStatus(final int id) {
        return c.getPlayer().getQuestStatus(id);
    }

    public final boolean isQuestActive(final int id) {
        return getQuestStatus(id) == 1;
    }

    public final boolean isQuestFinished(final int id) {
        return getQuestStatus(id) == 2;
    }

    public final void showQuestMsg(final String msg) {
        c.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, msg));
    }

    public final void forceStartQuest(final int id, final String data) {
        MapleQuest.getInstance(id).forceStart(c.getPlayer(), 0, data);
    }

    public final void forceStartQuest(final int id, final int data, final boolean filler) {
        MapleQuest.getInstance(id).forceStart(c.getPlayer(), 0, filler ? String.valueOf(data) : null);
    }

    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(c.getPlayer(), 0, null);
    }

    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(getPlayer(), 0);
    }

    public void spawnNpc(final int npcId) {
        c.getPlayer().getMap().spawnNpc(npcId, c.getPlayer().getPosition());
    }

    public final void spawnNpc(final int npcId, final int x, final int y) {
        c.getPlayer().getMap().spawnNpc(npcId, new Point(x, y));
    }

    public final void spawnNpc(final int npcId, final Point pos) {
        c.getPlayer().getMap().spawnNpc(npcId, pos);
    }

    public final void spawnNpcForPlayer(final int npcId, final int x, final int y) {
        c.getPlayer().getMap().spawnNpcForPlayer(c, npcId, new Point(x, y));
    }

    public final void removeNpc(final int mapid, final int npcId) {
        c.getChannelServer().getMapFactory().getMap(mapid).removeNpc(npcId);
    }

    public final void removeNpc(final int npcId) {
        c.getPlayer().getMap().removeNpc(npcId);
    }

    public final void hideNpc(final int npcId) {

    }

    public final void respawn(final boolean force) {
        c.getPlayer().getMap().respawn(force);
    }

    public final void forceStartReactor(final int mapid, final int id) {
        MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
        MapleReactor react;

        for (final MapleMapObject remo : map.getAllReactor()) {
            react = (MapleReactor) remo;
            if (react.getReactorId() == id) {
                react.forceStartReactor(c);
                break;
            }
        }
    }

    public final void destroyReactor(final int mapid, final int id) {
        MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
        MapleReactor react;

        for (final MapleMapObject remo : map.getAllReactor()) {
            react = (MapleReactor) remo;
            if (react.getReactorId() == id) {
                react.hitReactor(c);
                break;
            }
        }
    }

    public final void hitReactor(final int mapid, final int id) {
        MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
        MapleReactor react;

        for (final MapleMapObject remo : map.getAllReactor()) {
            react = (MapleReactor) remo;
            if (react.getReactorId() == id) {
                react.hitReactor(c);
                break;
            }
        }
    }

    public final int getJob() {
        return c.getPlayer().getJob();
    }

    public final void gainNX(final int amount) {
        c.getPlayer().modifyCSPoints(4, amount, true); // theremk you can change it to prepaid yae since cspoiint is
        // xncredit so make it prepaid
    }

    public final void gainItemPeriod(final int id, final short quantity, final int period) { // period is in days
        gainItem(id, quantity, false, period, false, -1, "");
    }

    public final void gainItemPeriod(final int id, final short quantity, final long period, final String owner) { // period is in days
        gainItem(id, quantity, false, period, false, -1, owner);
    }

    public final void gainItemPeriod(final int id, final short quantity, final int period, boolean hours) { // period is in days
        gainItem(id, quantity, false, period, hours, -1, "");
    }

    public final void gainItemPeriod(final int id, final short quantity, final long period, boolean hours,
            final String owner) { // period is in days
        gainItem(id, quantity, false, period, hours, -1, owner);
    }

    public final void gainItem(final int id, final short quantity) {
        gainItem(id, quantity, false, 0, false, -1, "");
    }

    public final void gainItemSilent(final int id, final short quantity) {
        gainItem(id, quantity, false, 0, false, -1, "", c, false);
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats) {
        gainItem(id, quantity, randomStats, 0, false, -1, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final int slots) {
        gainItem(id, quantity, randomStats, 0, false, slots, "");
    }

    public final void gainItem(final int id, final short quantity, final long period) {
        gainItem(id, quantity, false, period, false, -1, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period,
            final int slots) {
        gainItem(id, quantity, randomStats, period, false, slots, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period,
            boolean hours, final int slots, final String owner) {
        gainItem(id, quantity, randomStats, period, hours, slots, owner, c);
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period,
            boolean hours, final int slots, final String owner, final MapleClient cg) {
        gainItem(id, quantity, randomStats, period, hours, slots, owner, cg, true);
    }

    // public final void gainItem(final int id, final short quantity, final boolean
    // randomStats, final long period, boolean hours, final int slots, boolean
    // potential, final String owner) {
    // gainItem(id, quantity, randomStats, period, hours, slots, potential, owner,
    // c);
    // }
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period,
            boolean hours, final int slots, final String owner, final MapleClient cg, final boolean show) {
        if (quantity >= 0) {
            FileoutputUtil.logToFile_("ScriptItem.txt",
                    getPlayer().getName() + "," + this.id + "," + id + "," + script);
            final ItemInformation ii = ItemInformation.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!InventoryManipulator.checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id)
                    && !GameConstants.isBullet(id)) {
                final Equip item = (Equip) (randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id))
                        : ii.getEquipById(id));
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + (period * (hours ? 1 : 24) * 60 * 60 * 1000));
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte) (item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                item.setGMLog("Received from interaction " + this.id + " (" + id2 + ") on "
                        + FileoutputUtil.CurrentReadable_Time());
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) { // medal
                    final String msg = "< " + name + " > has been rewarded.";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                InventoryManipulator.addbyItem(cg, item.copy());
            } else {
                InventoryManipulator.addById(cg, id, quantity, owner == null ? "" : owner, null, period, hours,
                        "Received from interaction " + this.id + " (" + id2 + ") on "
                        + FileoutputUtil.CurrentReadable_Date());
            }
        } else {
            InventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        if (show) {
            cg.getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, quantity, true));
        }
    }

    public final boolean removeItem(final int id) { // quantity 1
        if (InventoryManipulator.removeById_Lock(c, GameConstants.getInventoryType(id), id)) {
            c.getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, (short) -1, true));
            return true;
        }
        return false;
    }

    public final void changeMusic(final String songName) {
        getPlayer().getMap().broadcastMessage(MainPacketCreator.musicChange(songName));
    }

    public final void worldMessage(final int type, final String message) {
        WorldBroadcasting.broadcastMessage(MainPacketCreator.serverNotice(type, message));
    }

    // default playerMessage and mapMessage to use type 5
    public final void playerMessage(final String message) {
        playerMessage(5, message);
    }

    public final void mapMessage(final String message) {
        mapMessage(5, message);
    }

    public final void guildMessage(final String message) {
        guildMessage(5, message);
    }

    public final void playerMessage(final int type, final String message) {
        c.getPlayer().dropMessage(type, message);
    }

    public final void mapMessage(final int type, final String message) {
        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.serverNotice(type, message));
    }

    public final void guildMessage(final int type, final String message) {
        if (getPlayer().getGuildId() > 0) {
            WideObjectHolder.getInstance().guildPacket(getPlayer().getGuildId(),
                    MainPacketCreator.serverNotice(type, message));
        }
    }

    public final MapleGuild getGuild() {
        return getGuild(getPlayer().getGuildId());
    }

    public final MapleGuild getGuild(int guildid) {
        return WideObjectHolder.getInstance().getGuild(guildid);
    }

    public final MapleParty getParty() {
        return c.getPlayer().getParty();
    }

    public final int getCurrentPartyId(int mapid) {
        return getMap(mapid).getCurrentPartyId();
    }

    public final boolean isLeader() {
        if (getPlayer().getParty() == null) {
            return false;
        }
        return getParty().getLeader().getId() == c.getPlayer().getId();
    }

    public final boolean isAllPartyMembersAllowedJob(final int job) {
        if (c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : c.getPlayer().getParty().getMembers()) {
            if (mem.getJobId() / 100 != job) {
                return false;
            }
        }
        return true;
    }

    public final boolean allMembersHere() {
        if (c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : c.getPlayer().getParty().getMembers()) {
            final MapleCharacter chr = c.getPlayer().getMap().getCharacterById(mem.getId());
            if (chr == null) {
                return false;
            }
        }
        return true;
    }

    public final void warpParty(final int mapId) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            warp(mapId, 0);
            return;
        }
        final MapleMap target = getMap(mapId);
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }
    
    public void fakeRelog() {
        c.getPlayer().send(MainPacketCreator.getPlayerInfo(c.getPlayer()));
    }

    public void updateChar() {
        MapleMap currentMap = c.getPlayer().getMap();
        currentMap.removePlayer(c.getPlayer());
        currentMap.addPlayer(c.getPlayer());
    }

    public final void warpParty(final int mapId, final int portal) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            if (portal < 0) {
                warp(mapId);
            } else {
                warp(mapId, portal);
            }
            return;
        }
        final boolean rand = portal < 0;
        final MapleMap target = getMap(mapId);
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                if (rand) {
                    try {
                        curChar.changeMap(target, target.getPortal(Randomizer.nextInt(target.getPortals().size())));
                    } catch (Exception e) {
                        curChar.changeMap(target, target.getPortal(0));
                    }
                } else {
                    curChar.changeMap(target, target.getPortal(portal));
                }
            }
        }
    }

    public final void gainItemTarget(final MapleCharacter chr, final int id, final short quantity) {
        gainItemTarget(chr, id, quantity, false, 0);
    }

    public final void gainItemTarget(final MapleCharacter chr, final int id, final short quantity,
            final boolean randomStats, final long period) {
        if (quantity >= 0) {
            final ItemInformation ii = ItemInformation.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!InventoryManipulator.checkSpace(chr.getClient(), id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id)
                    && !GameConstants.isBullet(id)) {
                final IItem item = randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id), true)
                        : ii.getEquipById(id);
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + period);
                }
                item.setGMLog(CurrentTime.getAllCurrentTime() + "Items obtained through the gainItem script.");
                InventoryManipulator.addbyItem(chr.getClient(), item);
            } else {
                InventoryManipulator.addById(chr.getClient(), id, quantity, "", null, period,
                        CurrentTime.getAllCurrentTime() + "Items obtained through the gainItem script.");
            }
        } else {
            InventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(id), id, -quantity, true,
                    false);
        }
        chr.send(MainPacketCreator.getShowItemGain(id, quantity, true));
    }

    public final void warpParty_Instanced(final int mapId) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            warp_Instanced(mapId);
            return;
        }
        final MapleMap target = getMap_Instanced(mapId);

        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }

    public void gainMeso(long gain) {
        c.getPlayer().gainMeso(gain, true, true);
    }

    public void gainExp(int gain) {
        c.getPlayer().gainExp(gain, true, true, true);
    }

    public void gainExpR(int gain) {
        c.getPlayer().gainExp(gain * c.getChannelServer().getExpRate(), true, true, true);
    }

    public void gainSp(final int amount) {
        c.getPlayer().gainSP((short) amount, 0);
    }

    public final void givePartyItems(final int id, final short quantity, final List<MapleCharacter> party) {
        for (MapleCharacter chr : party) {
            if (quantity >= 0) {
                InventoryManipulator.addById(chr.getClient(), id, quantity,
                        "Received from party interaction " + id + " (" + id2 + ")");
            } else {
                InventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(id), id, -quantity,
                        true, false);
            }
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, quantity, true));
        }
    }

    public void addPartyTrait(String t, int e, final List<MapleCharacter> party) {

    }

    public void addPartyTrait(String t, int e) {

    }

    public void addTrait(String t, int e) {

    }

    public final void givePartyItems(final int id, final short quantity) {
        givePartyItems(id, quantity, false);
    }

    public final void givePartyItems(final int id, final short quantity, final boolean removeAll) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainItem(id, (short) (removeAll ? -getPlayer().itemQuantity(id) : quantity));
            return;
        }

        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                gainItem(id, (short) (removeAll ? -curChar.itemQuantity(id) : quantity), false, 0, false, 0, "",
                        curChar.getClient());
            }
        }
    }

    public final void givePartyExp_PQ(final int maxLevel, final double mod, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(
                    chr.getLevel() > maxLevel ? (maxLevel + ((maxLevel - chr.getLevel()) / 10)) : chr.getLevel())
                    / (Math.min(chr.getLevel(), maxLevel) / 5.0) / (mod * 2.0));
            chr.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
        }
    }

    public final void gainExp_PQ(final int maxLevel, final double mod) {
        final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(
                getPlayer().getLevel() > maxLevel ? (maxLevel + (getPlayer().getLevel() / 10)) : getPlayer().getLevel())
                / (Math.min(getPlayer().getLevel(), maxLevel) / 10.0) / mod);
        gainExp(amount * c.getChannelServer().getExpRate());
    }

    public final void givePartyExp_PQ(final int maxLevel, final double mod) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            final int amount = (int) Math.round(GameConstants
                    .getExpNeededForLevel(getPlayer().getLevel() > maxLevel ? (maxLevel + (getPlayer().getLevel() / 10))
                            : getPlayer().getLevel())
                    / (Math.min(getPlayer().getLevel(), maxLevel) / 10.0) / mod);
            gainExp(amount * c.getChannelServer().getExpRate());
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(
                        curChar.getLevel() > maxLevel ? (maxLevel + (curChar.getLevel() / 10)) : curChar.getLevel())
                        / (Math.min(curChar.getLevel(), maxLevel) / 10.0) / mod);
                curChar.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
            }
        }
    }

    public final void givePartyExp(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
        }
    }

    public final void givePartyExp(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainExp(amount * c.getChannelServer().getExpRate());
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
            }
        }
    }

    public final void endPartyQuest(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.endPartyQuest(amount);
        }
    }

    public final void endPartyQuest(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            getPlayer().endPartyQuest(amount);
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.endPartyQuest(amount);
            }
        }
    }

    public final void removeFromParty(final int id, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            final int possesed = chr.getInventory(GameConstants.getInventoryType(id)).countById(id);
            if (possesed > 0) {
                InventoryManipulator.removeById(c, GameConstants.getInventoryType(id), id, possesed, true, false);
                chr.getClient().getSession()
                        .writeAndFlush(MainPacketCreator.getShowItemGain(id, (short) -possesed, true));
            }
        }
    }

    public final void removeFromParty(final int id) {
        givePartyItems(id, (short) 0, true);
    }

    public final void useSkill(final int skill, final int level) {
        if (level <= 0) {
            return;
        }
        SkillFactory.getSkill(skill).getEffect(level).applyTo(c.getPlayer());
    }

    public final void useItem(final int id) {
        ItemInformation.getInstance().getItemEffect(id).applyTo(c.getPlayer());
        c.getSession().writeAndFlush(UIPacket.getStatusMsg(id));
    }

    public final void cancelItem(final int id) {
        c.getPlayer().cancelEffect(ItemInformation.getInstance().getItemEffect(id), false, -1);
    }

    public final int getMorphState() {
        return c.getPlayer().getMorphState();
    }

    public final void removeAll(final int id) {
        c.getPlayer().removeAll(id);
    }

    public final void gainCloseness(final int closeness, final int index) {
        final MaplePet pet = getPlayer().getPet(index);
        if (pet != null) {
            pet.setCloseness(pet.getCloseness() + closeness);
            getClient().getSession().writeAndFlush(
                    PetPacket.updatePet(getPlayer(), getPlayer().getPet(index), false, getPlayer().getPetLoot()));
        }
    }

    public final void gainClosenessAll(final int closeness) {
        for (final MaplePet pet : getPlayer().getPets()) {
            if (pet != null) {
                pet.setCloseness(pet.getCloseness() + closeness);
                getClient().getSession()
                        .writeAndFlush(PetPacket.updatePet(getPlayer(), pet, false, getPlayer().getPetLoot()));
            }
        }
    }

    public final void givePartyNX(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.modifyCSPoints(1, amount, true);
        }
    }

    public final void givePartyNX(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainNX(amount);
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null
                    && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.modifyCSPoints(1, amount, true);
            }
        }
    }

    public final void resetMap(final int mapid) {
        getMap(mapid).resetFully();
        // getMap(mapid).resetReactors(c);
    }

    public final void openNpc(final int id) {
        getClient().removeClickedNPC();
        NPCScriptManager.getInstance().start(getClient(), id, null);
    }

    public final void openNpc(final MapleClient cg, final int id) {
        cg.removeClickedNPC();
        NPCScriptManager.getInstance().start(cg, id, null);
    }

    public final int getMapId() {
        return c.getPlayer().getMap().getId();
    }

    public final boolean haveMonster(final int mobid) {
        for (MapleMapObject obj : c.getPlayer().getMap().getAllMonster()) {
            final MapleMonster mob = (MapleMonster) obj;
            if (mob.getId() == mobid) {
                return true;
            }
        }
        return false;
    }
	
	public final boolean haveMonster() {
        return c.getPlayer().getMap().getAllMonstersThreadsafe().size() > 0;
    }
	
    public final int getChannelNumber() {
        return c.getChannel();
    }

    public final int getMonsterCount(final int mapid) {
        return c.getChannelServer().getMapFactory().getMap(mapid).getAllMonster().size();
    }

    public final void teachSkill(final int id, final byte level, final byte masterlevel) {
        getPlayer().changeSkillLevel(SkillFactory.getSkill(id), level, masterlevel);
    }

    public final void teachSkill(final int id, byte level) {
        final ISkill skil = SkillFactory.getSkill(id);
        if (getPlayer().getSkillLevel(skil) > level) {
            level = getPlayer().getSkillLevel(skil);
        }
        getPlayer().changeSkillLevel(skil, level, (byte) skil.getMaxLevel());
    }

    public final int getPlayerCount(final int mapid) {
        return c.getChannelServer().getMapFactory().getMap(mapid).getCharactersSize();
    }

    public final void dojo_getUp() {
        // int sec = 12;//getCurrentTime
        // long curtime = getCurrentTime();
        // System.err.println(curtime);
        c.getPlayer().updateInfoQuest(7215, "stage=6;type=1;token=3");
        c.getPlayer().updateInfoQuest(7218, "1");
        for (int i = 0; i < 3; i++) {
            c.getPlayer().updateInfoQuest(7281, "item=0;chk=0;cNum=0;sec=2;stage=0;lBonus=0"); // last stage
        }
        for (int i = 0; i < 2; i++) {
            c.getPlayer().updateInfoQuest(7281, "item=0;chk=0;cNum=0;sec=2;stage=0;lBonus=0");
        }
        c.getPlayer().updateInfoQuest(7216, "3");
        c.getPlayer().updateInfoQuest(7214, "5");
        c.getPlayer().updateInfoQuest(7215, "0");
        // c.getSession().writeAndFlush(InfoPacket.updateInfoQuest(1207,
        // "min=1;tuto=1")); //old - 1207, "pt=1;min=4;belt=1;tuto=1")); //todo
        // c.getSession().writeAndFlush(InfoPacket.updateInfoQuest(7281,
        // "item=0;chk=0;cNum=0;sec=" + sec + ";stage=0;lBonus=0"));
        c.getSession().writeAndFlush(UIPacket.Mulung_DojoUp2());
        c.getSession().writeAndFlush(MainPacketCreator.instantMapWarp((byte) 6));
    }

    public final boolean dojoAgent_NextMap(final boolean dojo, final boolean fromresting) {
        if (dojo) {
            return MapleDojoAgent.warpNextMap(c.getPlayer(), fromresting, c.getPlayer().getMap());
        }
        return MapleDojoAgent.warpNextMap_Agent(c.getPlayer(), fromresting);
    }

    public final boolean dojoAgent_NextMap(final boolean dojo, final boolean fromresting, final int mapid) {
        if (dojo) {
            return MapleDojoAgent.warpNextMap(c.getPlayer(), fromresting, getMap(mapid));
        }
        return MapleDojoAgent.warpNextMap_Agent(c.getPlayer(), fromresting);
    }

    public final int dojo_getPts() {
        return c.getPlayer().getIntNoRecord(GameConstants.DOJO);
    }

    public final void dojowarp(final int map) {
        final MapleMap mapz = getWarpMap(map);
        mapz.resetFully();
        c.getPlayer().setDojoStopTime(0);
        c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        int floor = (mapz.getId() - 925070000) / 100;
        int id = 0, id2 = 0;
        long hp = 0, hp2 = 0, hp3 = 0;
        switch (floor) {
            case 1:
                id = 9305600;
                hp = 5200000;
                break;
            case 2:
                id = 9305601;
                hp = 5740800;
                break;
            case 3:
                id = 9305602;
                hp = 6307200;
                break;
            case 4:
                id = 9305603;
                hp = 6930000;
                break;
            case 5:
                id = 9305604;
                hp = 7549200;
                break;
            case 6:
                id = 9305605;
                hp = 12342000;
                break;
            case 7:
                id = 9305606;
                hp = 13923000;
                break;
            case 8:
                id = 9305607;
                hp = 15105000;
                break;
            case 9:
                id = 9305608;
                hp = 16846000;
                break;
            case 10:
                id = 9305619;
                hp = 100000000;
                break;
            case 11:
                id = 9305610;
                hp = 40824000;
                break;
            case 12:
                id = 9305611;
                hp = 45404550;
                break;
            case 13:
                id = 9305612;
                hp = 48593250;
                break;
            case 14:
                id = 9305613;
                hp = 55350000;
                break;
            case 15:
                id = 9305614;
                hp = 61600500;
                break;
            case 16:
                id = 9305615;
                hp = 68121000;
                break;
            case 17:
                id = 9305616;
                hp = 12342000;
                break;
            case 18:
                id = 9305617;
                hp = 90011250;
                break;
            case 19:
                id = 9305618;
                hp = 97902000;
                break;
            case 20:
                id = 9305609;
                hp = 1500000000;
                break;
            case 21:
                id = 9305620;
                id2 = 9305641;
                hp = 130536000;
                break;
            case 22:
                id = 9305621;
                id2 = 9305642;
                hp = 159138000;
                hp2 = 159138000;
                break;
            case 23:
                id = 9305622;
                id2 = 9305643;
                hp = 190350000;
                hp2 = 190350000;
                break;
            case 24:
                id = 9305623;
                id2 = 9305644;
                hp = 242424000;
                hp2 = 242424000;
                break;
            case 25:
                id = 9305624;
                id2 = 9305645;
                hp = 405504000;
                hp2 = 405504000;
                break;
            case 26:
                id = 9305625;
                id2 = 9305646;
                hp = 497040000;
                hp2 = 497040000;
                break;
            case 27:
                id = 9305626;
                id2 = 9305647;
                hp = 596496000;
                hp2 = 596496000;
                break;
            case 28:
                id = 9305627;
                id2 = 9305648;
                hp = 706176000;
                hp2 = 706176000;
                break;
            case 29:
                id = 9305628;
                id2 = 9305649;
                hp = 824256000;
                hp2 = 824256000;
                break;
            case 30:
                id = 9305629;
                hp = 3000000000L;
                break;
            case 31:
                id = 9305630;
                hp = 2108240000;
                break;
            case 32:
                id = 9305631;
                hp = 2526520000L;
                break;
            case 33:
                id = 9305632;
                hp = 2976000000L;
                hp2 = 2976000000L;
                hp3 = 2976000000L;
                break;
            case 34:
                id = 9305633;
                hp = 3464920000L;
                break;
            case 35:
                id = 9305634;
                hp = 3986640000L;
                break;
            case 36:
                id = 9305635;
                hp = 4551000000L;
                hp2 = 4551000000L;
                hp3 = 4551000000L;
                break;
            case 37:
                id = 9305636;
                hp = 5149760000L;
                break;
            case 38:
                id = 9305637;
                hp = 6474960000L;
                break;
            case 39:
                id = 9305638;
                hp = 7971840000L;
                hp2 = 4551000000L;
                hp3 = 4551000000L;
                break;
            case 40:
                id = 9305639;
                hp = 8000000000L;
                break;
            case 41:
                id = 9305656;
                hp = 42000000000L;
                break;
            case 42:
                id = 9305657;
                hp = 63000000000L;
                break;
            case 43:
                id = 9305658;
                hp = 84000000000L;
                break;
            case 44:
                id = 9305659;
                hp = 105000000000L;
                break;
            case 45:
                id = 9305660;
                hp = 105000000000L;
                hp2 = 105000000000L;
                hp3 = 105000000000L;
                break;
            case 46:
                id = 9305661;
                hp = 210000000000L;
                break;
            case 47:
                id = 9305662;
                hp = 315000000000L;
                break;
            case 48:
                id = 9305663;
                hp = 420000000000L;
                break;
            case 49:
                id = 9305664;
                hp = 525000000000L;
                break;
            case 50:
                id = 9305665;
                hp = 525000000000L;
                hp2 = 525000000000L;
                hp3 = 525000000000L;
                break;
            case 51:
                id = 9305666;
                hp = 630000000000L;
                break;
            case 52:
                id = 9305667;
                hp = 735000000000L;
                break;
            case 53:
                id = 9305668;
                hp = 840000000000L;
                break;
            case 54:
                id = 9305669;
                hp = 945000000000L;
                break;
            case 55:
                id = 9305670;
                hp = 1050000000000L;
                break;
            case 56:
                id = 9305671;
                hp = 1155000000000L;
                break;
            case 57:
                id = 9305672;
                hp = 1260000000000L;
                break;
            case 58:
                id = 9305673;
                hp = 1365000000000L;
                break;
            case 59:
                id = 9305674;
                hp = 1470000000000L;
                break;
            case 60:
                id = 9305675;
                hp = 1575000000000L;
                break;
            case 61:
                id = 9305676;
                hp = 1680000000000L;
                break;
            case 62:
                id = 9305677;
                hp = 1785000000000L;
                break;
            case 63:
                id = 9305640;
                hp = 2100000000000L;
                break;
        }
        MapleMonster mob = MapleLifeProvider.getMonster(id);
        mob.setHp(hp);
        mob.getStats().setHp(hp);
        mapz.spawnMonsterWithEffectBelow(mob, new Point(Randomizer.nextBoolean() ? -304 : 185, 7), 1);
        //mob.applyStatus(c, MonsterStatus.MS_AddEffect, new MonsterStatusEffect(0, Short.MAX_VALUE), 0, null);
        if (id2 == 0 && hp2 > 0) {
            MapleMonster mob2 = MapleLifeProvider.getMonster(id);
            mob2.setHp(hp2);
            mob2.getStats().setHp(hp2);
            mapz.spawnMonsterWithEffectBelow(mob2, new Point(Randomizer.nextBoolean() ? -304 : 185, 7), 1);
        }
        if (id2 > 0) {
            for (int i = 0; i < 5; i++) {
                MapleMonster mob2 = MapleLifeProvider.getMonster(id2);
                mapz.spawnMonsterWithEffectBelow(mob2, new Point(Randomizer.nextBoolean() ? -304 : 185, 7), 1);
            }
        }
        if (hp3 > 0) {
            MapleMonster mob3 = MapleLifeProvider.getMonster(id);
            mob3.setHp(hp3);
            mob3.getStats().setHp(hp3);
            mapz.spawnMonsterWithEffectBelow(mob3, new Point(Randomizer.nextBoolean() ? -304 : 185, 7), 1);
        }

    }

    public final MapleEvent getEvent(final String loc) {
        return null;
    }

    public final int getSavedLocation(final String loc) {
        final Integer ret = c.getPlayer().getSavedLocation(SavedLocationType.fromString(loc));
        if (ret == null || ret == -1) {
            return 100000000;
        }
        return ret;
    }

    public final void saveLocation(final String loc) {
        c.getPlayer().saveLocation(SavedLocationType.fromString(loc));
    }

    public final void saveReturnLocation(final String loc) {
        c.getPlayer().saveLocation(SavedLocationType.fromString(loc), c.getPlayer().getMap().getReturnMap().getId());
    }

    public final void clearSavedLocation(final String loc) {
        c.getPlayer().clearSavedLocation(SavedLocationType.fromString(loc));
    }

    public final void summonMsg(final String msg) {
        if (!c.getPlayer().hasSummon()) {
            playerSummonHint(true);
        }
        c.getSession().writeAndFlush(UIPacket.summonMessage(msg));
    }

    public final void summonMsg(final int type) {
        if (!c.getPlayer().hasSummon()) {
            playerSummonHint(true);
        }
        c.getSession().writeAndFlush(UIPacket.summonMessage(type));
    }

    public final void showInstruction(final String msg, final int width, final int height) {
        c.getSession().writeAndFlush(MainPacketCreator.sendHint(msg, width, height));
    }

    public final void playerSummonHint(final boolean summon) {
        c.getPlayer().setHasSummon(summon);
        c.getSession().writeAndFlush(UIPacket.summonHelper(summon));
    }

    public final String getInfoQuest(final int id) {
        return c.getPlayer().getInfoQuest(id);
    }

    public final void updateInfoQuest(final int id, final String data) {
        c.getPlayer().updateInfoQuest(id, data);
    }

    public final boolean getEvanIntroState(final String data) {
        return getInfoQuest(22013).equals(data);
    }

    public final void updateEvanIntroState(final String data) {
        updateInfoQuest(22013, data);
    }

    public final void Aran_Start() {
        c.getSession().writeAndFlush(MainPacketCreator.showMapEffect("Aran/balloon"));
    }

    public final void evanTutorial(final String data, final int v1) {
        c.getSession().writeAndFlush(MainPacketCreator.getEvanTutorial(data));
    }

    public final void AranTutInstructionalBubble(final String data) {
        c.getSession().writeAndFlush(UIPacket.showWZEffect(data));
    }

    public final void ShowWZEffect(final String data) {
        c.getSession().writeAndFlush(MainPacketCreator.TutInstructionalBalloon(data));
    }

    public final void showWZEffect(final String data) {
        c.getSession().writeAndFlush(EffectPacket.ShowWZEffect(data));
    }

    public final void ShowWZEffect(final String data, int i) {
        c.getSession().writeAndFlush(MainPacketCreator.TutInstructionalBalloon(data));
    }

    public final void PartyTimeMove(final int map1, final int map2, final int time) {
        for (final MapleCharacter partymem : c.getChannelServer().getPartyMembers(getPlayer().getParty())) {
            partymem.timeMoveMap(map1, map2, time);
        }
    }

    public final void showWZEffect(final String data, int i) {
        c.getSession().writeAndFlush(MainPacketCreator.TutInstructionalBalloon(data));
    }

    public final void broadcastWZEffect(String data, int i) {
        getPlayer().getMap().broadcastMessage(getPlayer(), UIPacket.broadcastWZEffect(getPlayer().getId(), data, 1),
                false);
    }

    public final void EarnTitleMsg(final String data) {
        c.getSession().writeAndFlush(UIPacket.showInfo(data));
    }

    public final void topMsg(final String data) {
        c.getSession().writeAndFlush(UIPacket.showInfo(data));
    }

    public final void EnableUI(final short i) {
        c.getSession().writeAndFlush(UIPacket.IntroEnableUI(i));
    }

    public final void DisableUI(final boolean enabled) {
        c.getSession().writeAndFlush(UIPacket.IntroDisableUI(enabled));
    }

    public final void MovieClipIntroUI(final boolean enabled) {
        c.getSession().writeAndFlush(UIPacket.IntroDisableUI(enabled));
        c.getSession().writeAndFlush(UIPacket.IntroLock(enabled));
    }

    public MapleInventoryType getInvType(int i) {
        return MapleInventoryType.getByType((byte) i);
    }

    public String getItemName(final int id) {
        return ItemInformation.getInstance().getName(id);
    }

    public void gainPet(int id, String name, int level, int closeness, int fullness, long period, short flags) {
        if (id >= 5001000 || id < 5000000) {
            id = 5000000;
        }
        if (level > 30) {
            level = 30;
        }
        if (closeness > 30000) {
            closeness = 30000;
        }
        if (fullness > 100) {
            fullness = 100;
        }
        try {
            InventoryManipulator.addById(c, id, (short) 1, "", MaplePet.createPet(id, id == 5000054 ? (int) period : 0),
                    45, false,
                    "Pet from interaction " + id + " (" + id2 + ")" + " on " + FileoutputUtil.CurrentReadable_Date());
        } catch (NullPointerException ex) {
        }
    }

    public void removeSlot(int invType, byte slot, short quantity) {
        InventoryManipulator.removeFromSlot(c, getInvType(invType), slot, quantity, true);
    }

    public void gainGP(final int gp) {
        if (getPlayer().getGuildId() <= 0) {
            return;
        }
        WideObjectHolder.getInstance().gainGP(getPlayer().getGuildId(), gp); // 1 for
    }

    public int getGP() {
        if (getPlayer().getGuildId() <= 0) {
            return 0;
        }
        return WideObjectHolder.getInstance().getGuild(getPlayer().getGuildId()).getGP();
    }

    public void showMapEffect(String path) {
        getClient().getSession().writeAndFlush(MainPacketCreator.showMapEffect(path));
    }

    public int itemQuantity(int itemid) {
        return getPlayer().itemQuantity(itemid);
    }

    public EventInstanceManager getDisconnected(String event) {
        EventManager em = getEventManager(event);
        if (em == null) {
            return null;
        }
        for (EventInstanceManager eim : em.getInstances()) {
            if (eim.isDisconnected(c.getPlayer()) && eim.getPlayerCount() > 0) {
                return eim;
            }
        }
        return null;
    }

    public boolean isAllReactorState(final int reactorId, final int state) {
        boolean ret = false;
        for (MapleMapObject o : getMap().getAllReactor()) {
            final MapleReactor r = (MapleReactor) o;
            if (r.getReactorId() == reactorId) {
                ret = r.getState() == state;
            }
        }
        return ret;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public void spawnMonster(int id) {
        spawnMonster(id, 1, getPlayer().getTruePosition());
    }

    // summon one monster, remote location
    public void spawnMonster(int id, int x, int y) {
        spawnMonster(id, 1, new Point(x, y));
    }

    // multiple monsters, remote location
    public void spawnMonster(int id, int qty, int x, int y) {
        spawnMonster(id, qty, new Point(x, y));
    }

    // handler for all spawnMonster
    public void spawnMonster(int id, int qty, Point pos) {
        for (int i = 0; i < qty; i++) {
            getMap().spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(id), pos);
        }
    }

    public void sendNPCText(final String text, final int npc) {
        NPCTalk talk = new NPCTalk((byte) 4, npc, (byte) 0);
        talk.setText(text);

        getMap().broadcastMessage(MainPacketCreator.getNPCTalk(talk));
    }

    public void sendUIWindow(final int type, final int npc) {
        c.getSession().writeAndFlush(UIPacket.openUIOption(type, npc));
    }

    public void trembleEffect(int type, int delay) {
        c.getSession().writeAndFlush(MainPacketCreator.trembleEffect(type, delay));
    }

    public int nextInt(int arg0) {
        return Randomizer.nextInt(arg0);
    }

    public MapleQuest getQuest(int arg0) {
        return MapleQuest.getInstance(arg0);
    }

    public void achievement(int a) {
        c.getPlayer().getMap().broadcastMessage(UIPacket.AchievementRatio(a));
    }

    public final MapleInventory getInventory(int type) {
        return c.getPlayer().getInventory(MapleInventoryType.getByType((byte) type));
    }

    public final void prepareAswanMob(int mapid, EventManager eim) {
        MapleMap map = eim.getMapFactory().getMap(mapid);
        if (c.getPlayer().getParty() != null) {
            map.setChangeableMobOrigin(ChannelServer.getInstance(c.getChannel()).getPlayerStorage()
                    .getCharacterById(c.getPlayer().getParty().getLeader().getId()));
        } else {
            map.setChangeableMobOrigin(c.getPlayer());
        }
        // map.setChangeableMobUsing(true);
        map.killAllMonsters(false);
        map.respawn(true);
    }

    public final void startAswanOffSeason(final MapleCharacter leader) {
        final List<MapleCharacter> check1 = c.getChannelServer().getMapFactory().getMap(955000100).getCharacters();
        final List<MapleCharacter> check2 = c.getChannelServer().getMapFactory().getMap(955000200).getCharacters();
        final List<MapleCharacter> check3 = c.getChannelServer().getMapFactory().getMap(955000300).getCharacters();
        c.getChannelServer().getMapFactory().getMap(955000100).broadcastMessage(MainPacketCreator.getClock(20 * 60));
        c.getChannelServer().getMapFactory().getMap(955000200).broadcastMessage(MainPacketCreator.getClock(20 * 60));
        c.getChannelServer().getMapFactory().getMap(955000300).broadcastMessage(MainPacketCreator.getClock(20 * 60));
        EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (check1 != null && check2 != null && check3 != null && (leader.getMapId() == 955000100
                        || leader.getMapId() == 955000200 || leader.getMapId() == 955000300)) {
                    for (MapleCharacter chrs : check1) {
                        chrs.changeMap(262010000, 0);
                    }
                    for (MapleCharacter chrs : check2) {
                        chrs.changeMap(262010000, 0);
                    }
                    for (MapleCharacter chrs : check3) {
                        chrs.changeMap(262010000, 0);
                    }
                } else {
                    EventTimer.getInstance().stop();
                }
            }
        }, 20 * 60 * 1000);
    }

    public int randInt(int arg0) {
        return Randomizer.nextInt(arg0);
    }

    public void sendDirectionStatus(int key, int value) {
        c.getSession().writeAndFlush(UIPacket.getDirectionInfo(key, value));
        c.getSession().writeAndFlush(UIPacket.getDirectionStatus(true));
    }

    public void sendDirectionStatus(int key, int value, boolean direction) {
        c.getSession().writeAndFlush(UIPacket.getDirectionInfo(key, value));
        c.getSession().writeAndFlush(UIPacket.getDirectionStatus(direction));
    }

    public void sendDirectionInfo(String data) {
        c.getSession().writeAndFlush(UIPacket.getDirectionInfo(data, 2000, 0, -100, 0, 0));
        c.getSession().writeAndFlush(UIPacket.getDirectionInfo(1, 2000));
    }

    public void getDirectionInfo(String data, int value, int x, int y, int a, int b) {
        c.getSession().writeAndFlush(UIPacket.getDirectionInfo(data, value, x, y, a, b));
    }

    public void getDirectionInfo(byte type, int value) {
        c.getSession().writeAndFlush(UIPacket.getDirectionInfo(type, value));
    }

    public void introEnableUI(int wtf) {
        c.getSession().writeAndFlush(UIPacket.IntroEnableUI(wtf));
    }

    public void introDisableUI(boolean enable) {
        c.getSession().writeAndFlush(UIPacket.IntroDisableUI(enable));
    }

    public void getDirectionStatus(boolean enable) {
        c.getSession().writeAndFlush(UIPacket.getDirectionStatus(enable));
    }

    public void playMovie(String data, boolean show) {
        c.getSession().writeAndFlush(UIPacket.playMovie(data, show));
    }

    public String getCharacterName(int characterid) {
        return c.getChannelServer().getPlayerStorage().getCharacterById(characterid).getName();
    }

    public final MapleExpedition getExpedition() {
        return WorldCommunity.getParty(c.getPlayer().getPartyId()).getExpedition();
    }

    public int getExpeditionMembers(int id) {
        return WorldCommunity.getParty(c.getPlayer().getPartyId()).getExpedition().getAllMemberSize();
    }

    public void warpExpedition(int mapid, int portal) {
        for (MapleCharacter chr : getExpedition().getExpeditionMembers(c)) {
            chr.changeMap(mapid, portal);
        }
    }

    public String getMasteryBooksByJob(String job) {
        StringBuilder sb = new StringBuilder();
        for (Pair<Integer, String> book : ItemInformation.getInstance().getAllItems()) {
            if (book.getLeft() >= 2280000 && book.getLeft() < 2300000) {
                String skilldesc = ItemInformation.getInstance().getDesc(book.getLeft());
                if (skilldesc.contains(job)) {
                    sb.append("~").append(book.getLeft());
                }
            }
        }
        return sb.toString();
    }

    public void test(String test) {
        System.out.println(test);
    }

    public String format(String format, Object... toFormat) {
        return String.format(format, toFormat);
    }

    public void addReward(int type, int item, int mp, int meso, int exp, String desc) {
        getPlayer().addReward(type, item, mp, meso, exp, desc);
    }

    public void addReward(long start, long end, int type, int item, int mp, int meso, int exp, String desc) {
        getPlayer().addReward(start, end, type, item, mp, meso, exp, desc);
    }

    public int getPQLog(String pqid) {
        return getPlayer().getPQLog(pqid);
    }

    public void setPQLog(String pqid) {
        getPlayer().setPQLog(pqid);
    }

    public int getMapMobCount(int mapid) {
        return getClient().getChannelServer().getMapFactory().getMap(mapid).getAllMonster().size();
    }

    public void setProfession(int index, int skill) {
        MapleProfession pro = c.getPlayer().getProfession();
        if (index == 1) {
            pro.setFirstProfession(MapleProfessionType.getProfessionById(skill));
            pro.setFirstProfessionExp(0);
            pro.setFirstProfessionLevel(1);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(skill), (byte) 1, (byte) 10);
        } else if (index == 2) {
            pro.setSecondProfession(MapleProfessionType.getProfessionById(skill));
            pro.setSecondProfessionExp(0);
            pro.setSecondProfessionLevel(1);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(skill), (byte) 1, (byte) 10);
        }
    }

    public void deleteProfession(int index) {
        MapleProfession pro = c.getPlayer().getProfession();
        if (index == 1) {
            pro.setFirstProfessionExp(0);
            pro.setFirstProfessionLevel(0);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(pro.getFirstProfessionSkill()), (byte) 0, (byte) 0);
            pro.setFirstProfession(MapleProfessionType.NONE);
        } else if (index == 2) {
            pro.setSecondProfessionExp(0);
            pro.setSecondProfessionLevel(0);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(pro.getSecondProfessionSkill()), (byte) 0, (byte) 0);
            pro.setSecondProfession(MapleProfessionType.NONE);
        }
    }

    public void levelUpProfession(int index) {
        MapleProfession pro = c.getPlayer().getProfession();
        if (index == 1) {
            pro.setFirstProfessionExp(0);
            pro.addFirstProfessionLevel(1);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(pro.getFirstProfessionSkill()), (byte) 1, (byte) 10);
        } else if (index == 2) {
            pro.setSecondProfessionExp(0);
            pro.addSecondProfessionLevel(1);
            c.getPlayer().changeSkillLevel(SkillFactory.getSkill(pro.getSecondProfessionSkill()), (byte) 1, (byte) 10);
        }
    }

    public int getProfession(int index) {
        if (index == 1) {
            return c.getPlayer().getProfession().getFirstProfessionSkill();
        } else if (index == 2) {
            return c.getPlayer().getProfession().getSecondProfessionSkill();
        }
        return 0;
    }

    public void showGetItem(int itemid, int quantity) {
        getPlayer().dropMessage(5, ItemInformation.getInstance().getName(itemid) + " To " + quantity + " Earned.");
        getClient().sendPacket(UIPacket.getItemTopMsg(itemid,
                ItemInformation.getInstance().getName(itemid) + " To " + quantity + " Earned."));
    }

    public final void allPartyWarp(int mapid, boolean all) {
        MapleMap map = null;
        if (getPlayer().getEventInstance() != null) {
            map = getPlayer().getEventInstance().getMapFactory().getMap(mapid);
        } else {
            map = c.getChannelServer().getMapFactory().getMap(mapid);
        }

        for (final MapleCharacter partymem : c.getChannelServer().getPartyMembers(getPlayer().getParty())) {
            if (all || partymem.getMapId() == c.getPlayer().getMapId()) {
                partymem.changeMap(map, map.getPortal(0));
            }
        }
    }

    public final boolean BossCheck(String a, int i) {
        for (final MapleCharacter chr : getPlayer().getClient().getChannelServer().getPartyMembers(getPlayer().getParty())) {
            if (chr.getDateKey(a) == null) {
                chr.setDateKey(a, "0");
            }
            if (Integer.parseInt(chr.getDateKey(a)) >= i) {
                return false;
            }
        }
        return true;
    }

    public final void BossAdd(String a) {
        for (final MapleCharacter partymem : getPlayer().getClient().getChannelServer().getPartyMembers(getPlayer().getParty())) {
            partymem.setDateKey(a, (Integer.parseInt(partymem.getDateKey(a)) + 1) + "");
        }
    }

    public void openNpcCustom(final MapleClient c, final int id, final String custom) {
        c.removeClickedNPC();
        NPCScriptManager.getInstance().start(getClient(), id, custom);
    }
    
    public String DojangText() {
        StringBuilder str = new StringBuilder().append("Mureung Dojang ranking. Only the top 50 shows in the ranking.\r\n");
        List<Triple<Integer, Integer, String>> data = DojoRank();
        for (int i = 0; i < data.size(); i++) {
            str.append((i + 1) + "µî : #b" + data.get(i).third + "#k #d" + data.get(i).first + "Ãþ#k " + data.get(i).second + "ÃÊ\r\n");
            if (i >= 99) {
                break;
            }
        }
        return str.toString();
    }
    
    public String CharName(int id) {
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getByte("gm") == 0) {
                    return rs.getString("name");
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public List<Triple<Integer, Integer, String>> DojoRank() {
        List<Triple<Integer, Integer, String>> file = new ArrayList<>(); // floor, time, name
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM queststatus WHERE quest = 3");
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] data = rs.getString("customData").split(";");
                int dataz = 0, timez = 0;
                for (String da : data) {
                    if (da.startsWith("dojo=")) {
                        String valuez = da.replace("dojo=", "");
                        dataz = Integer.valueOf(valuez.replace(";", ""));
                    }
                    if (da.startsWith("dojo_time=")) {
                        String valuez = da.replace("dojo_time=", "");
                        timez = Integer.valueOf(valuez.replace(";", ""));
                    }
                }
                if (dataz > 0 && timez > 0 && (CharName(rs.getInt("characterid")) != null)) {
                    file.add(new Triple<>(dataz, timez, CharName(rs.getInt("characterid"))));
                }
                if (file.size() == 50) {
                    break;
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        Triple<Integer, Integer, String> temp;

        for (int i = 0; i < file.size(); i++) {
            for (int j = i + 1; j < file.size(); j++) {
                if (file.get(i).first < file.get(j).first) {
                    temp = file.get(i);
                    file.set(i, file.get(j));
                    file.set(j, temp);
                } else if (file.get(i).first == file.get(j).first) {
                    if (file.get(i).second > file.get(j).second) {
                        temp = file.get(i);
                        file.set(i, file.get(j));
                        file.set(j, temp);
                    }
                }
            }
        }
        return file;
    }
}
