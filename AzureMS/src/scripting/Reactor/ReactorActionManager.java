package scripting.Reactor;

import client.ItemInventory.Equip;
import client.ItemInventory.Item;
import client.ItemInventory.MapleInventoryType;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import client.MapleClient;
import constants.GameConstants;
import launcher.ServerPortInitialize.ChannelServer;
import scripting.AbstractPlayerInteraction;
import server.Items.ItemInformation;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapReactor.MapleReactor;
import server.Maps.MapReactor.ReactorDropEntry;
import tools.RandomStream.Randomizer;

public class ReactorActionManager extends AbstractPlayerInteraction {

    private final MapleReactor reactor;

    public ReactorActionManager(MapleClient c, MapleReactor reactor) {
        super(c, reactor.getReactorId(), c.getPlayer().getMapId(), null);
        this.reactor = reactor;
    }

    public void dropItems() {
        dropItems(false, 0, 0, 0, 0);
    }

    public void dropItems(boolean meso, int mesoChance, int minMeso, int maxMeso) {
        dropItems(meso, mesoChance, minMeso, maxMeso, 0);
    }

    public void dropItems(boolean meso, int mesoChance, int minMeso, int maxMeso, int minItems) {
        final List<ReactorDropEntry> chances = ReactorScriptManager.getInstance().getDrops(reactor.getReactorId());
        final List<ReactorDropEntry> items = new LinkedList<>();

        if (meso) {
            if (Math.random() < (1 / (double) mesoChance)) {
                items.add(new ReactorDropEntry(0, mesoChance, -1));
            }
        }

        int numItems = 0;
        // narrow list down by chances
        final Iterator<ReactorDropEntry> iter = chances.iterator();
        // for (DropEntry d : chances){
        while (iter.hasNext()) {
            ReactorDropEntry d = (ReactorDropEntry) iter.next();
            if (Math.random() < (1 / (double) d.chance)
                    && (d.questid <= 0 || getPlayer().getQuestStatus(d.questid) == 1)) {
                numItems++;
                items.add(d);
            }
        }

        // if a minimum number of drops is required, add meso
        while (items.size() < minItems) {
            items.add(new ReactorDropEntry(0, mesoChance, -1));
            numItems++;
        }
        final Point dropPos = reactor.getPosition();

        dropPos.x -= (12 * numItems);

        int range, mesoDrop;
        final ItemInformation ii = ItemInformation.getInstance();
        for (final ReactorDropEntry d : items) {
            if (d.itemId == 0) {
                range = maxMeso - minMeso;
                mesoDrop = Randomizer.nextInt(range)
                        + minMeso * ChannelServer.getInstance(getClient().getChannel()).getMesoRate();
                reactor.getMap().spawnMesoDrop(mesoDrop, dropPos, reactor, getPlayer(), false, (byte) 0);
            } else {
                Item drop;
                if (GameConstants.getInventoryType(d.itemId) != MapleInventoryType.EQUIP) {
                    drop = new Item(d.itemId, (byte) 0, (short) 1, (byte) 0);
                } else {
                    drop = ii.randomizeStats((Equip) ii.getEquipById(d.itemId));
                }
                drop.setGMLog("Dropped from reactor " + reactor.getReactorId() + " on map " + getPlayer().getMapId());
                reactor.getMap().spawnItemDrop(reactor, getPlayer(), drop, dropPos, false, false);
            }
            dropPos.x += 25;
        }
    }

    public void dropSingleItem(int itemId) {
        Item drop;
        if (GameConstants.getInventoryType(itemId) != MapleInventoryType.EQUIP) {
            drop = new Item(itemId, (byte) 0, (short) 1, (byte) 0);
        } else {
            drop = ItemInformation.getInstance()
                    .randomizeStats((Equip) ItemInformation.getInstance().getEquipById(itemId));
        }
        drop.setGMLog("Dropped from reactor " + reactor.getReactorId() + " on map " + getPlayer().getMapId());
        reactor.getMap().spawnItemDrop(reactor, getPlayer(), drop, reactor.getPosition(), false, false);
    }

    @Override
    public void spawnNpc(int npcId) {
        spawnNpc(npcId, getPosition());
    }

    // returns slightly above the reactor's position for monster spawns
    public Point getPosition() {
        Point pos = reactor.getPosition();
        pos.y -= 10;
        return pos;
    }

    public MapleReactor getReactor() {
        return reactor;
    }

    public int hitBigby() {
        MapleReactor bigby = null;
        for (MapleMapObject o : getMap().getAllReactor()) {
            final MapleReactor r = (MapleReactor) o;
            if (r.getReactorId() == 1301000) {
                bigby = r;
            }
        }
        if (bigby != null) {
            bigby.forceHitReactor((byte) (bigby.getState() + 1));
            if (bigby.getState() == 10) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public void spawnZakum() {
        reactor.getMap().spawnZakum(new Point(getPosition().x, getPosition().y));
    }

    public void spawnFakeMonster(int id) {
        spawnFakeMonster(id, 1, getPosition());
    }

    // summon one monster, remote location
    public void spawnFakeMonster(int id, int x, int y) {
        spawnFakeMonster(id, 1, new Point(x, y));
    }

    // multiple monsters, reactor location
    public void spawnFakeMonster(int id, int qty) {
        spawnFakeMonster(id, qty, getPosition());
    }

    // multiple monsters, remote location
    public void spawnFakeMonster(int id, int qty, int x, int y) {
        spawnFakeMonster(id, qty, new Point(x, y));
    }

    // handler for all spawnFakeMonster
    private void spawnFakeMonster(int id, int qty, Point pos) {
        for (int i = 0; i < qty; i++) {
            reactor.getMap().spawnFakeMonsterOnGroundBelow(MapleLifeProvider.getMonster(id), pos);
        }
    }

    public void killAll() {
        reactor.getMap().killAllMonsters(true);
    }

    public void killMonster(int monsId) {
        reactor.getMap().killMonster(monsId);
    }

    public void killReactor(int reactId) {
        reactor.getMap().getReactorById(reactId).delayedDestroyReactor(1);
    }

    // summon one monster on reactor location
    @Override
    public void spawnMonster(int id) {
        spawnMonster(id, 1, getPosition());
    }

    // summon monsters on reactor location
    @Override
    public void spawnMonster(int id, int qty) {
        spawnMonster(id, qty, getPosition());
    }

    public void dispelAllMonsters(final int num) { // dispels all mobs, cpq
        /*
		 * final MCSkill skil = MapleCarnivalFactory.getInstance().getGuardian(num); if
		 * (skil != null) { for (MapleMonster mons : getMap().getAllMonster()) {
		 * mons.dispelSkill(skil.getSkill()); } }
         */
    }

    public void cancelHarvest(boolean succ) {
        /*
		 * getPlayer().setFatigue((byte) (getPlayer().getFatigue() + 1));
		 * getPlayer().getMap().broadcastMessage(getPlayer(),
		 * CField.showHarvesting(getPlayer().getId(), 0), false);
		 * getPlayer().getMap().broadcastMessage(CField.harvestResult(getPlayer().getId(
		 * ), succ));
         */
    }

    public void doHarvest() { // TODO LEGEND
        /*
		 * if (getPlayer().getFatigue() >= 200 || getPlayer().getStat().harvestingTool
		 * <= 0 ||
		 * getReactor().getTruePosition().distanceSq(getPlayer().getTruePosition()) >
		 * 10000) { return; } final int pID = getReactor().getReactorId() < 200000 ?
		 * 92000000 : 92010000; final String pName = (getReactor().getReactorId() <
		 * 200000 ? "Herbalism" : "Mining"); final int he =
		 * getPlayer().getProfessionLevel(pID); if (he <= 0) { return; } final Item item
		 * = getInventory(1).getItem((short) getPlayer().getStat().harvestingTool); if
		 * (item == null || ((item.getItemId() / 10000)) != (getReactor().getReactorId()
		 * < 200000 ? 150 : 151)) { return; } int hm = getReactor().getReactorId() %
		 * 100; int successChance = 90 + ((he - hm) * 10); if
		 * (getReactor().getReactorId() % 100 == 10) { hm = 1; successChance = 100; }
		 * else if (getReactor().getReactorId() % 100 == 11) { hm = 10; successChance -=
		 * 40; } getPlayer().getStat().checkEquipDurabilitys(getPlayer(), -1, true); int
		 * masteryIncrease = (hm - he) * 2 + 20; final boolean succ = randInt(100) <
		 * successChance; if (!succ) { masteryIncrease /= 10;
		 * dropSingleItem(getReactor().getReactorId() < 200000 ? 4022023 : 4010010); }
		 * else { dropItems(); if (getReactor().getReactorId() < 200000) {
		 * addTrait("sense", 5); if (Randomizer.nextInt(10) == 0) {
		 * dropSingleItem(2440000); } if (Randomizer.nextInt(100) == 0) {
		 * dropSingleItem(4032933); } } else { addTrait("insight", 5); if
		 * (Randomizer.nextInt(10) == 0) { dropSingleItem(2440001); //IMP } } }
		 * cancelHarvest(succ); playerMessage(-5, pName + "'s Mastery increased. (+" +
		 * masteryIncrease + ")"); if (getPlayer().addProfessionExp(pID,
		 * masteryIncrease)) { playerMessage(-5, pName + " has gained a level."); }
         */
    }
}
