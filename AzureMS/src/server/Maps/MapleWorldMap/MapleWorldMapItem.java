package server.Maps.MapleWorldMap;

import java.awt.Point;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import connections.Packets.MainPacketCreator;
import server.Maps.MapObject.AbstractHinaMapObject;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapleMapHandling.MapleMap;

public class MapleWorldMapItem extends AbstractHinaMapObject {

    protected IItem item;
    protected MapleMapObject dropper;
    protected int character_ownerid, meso, questid = -1;
    protected long droppedTime;
    protected byte type;
    protected boolean pickedUp = false, playerDrop;
    protected IEquip equip;
    protected boolean fly, touch;
    protected int gradiant, speed;
    protected long nextExpiry = 0, nextFFA = 0;
    private ReentrantLock lock = new ReentrantLock();
    private byte item_type = 0;

    public MapleWorldMapItem(IItem item, Point position, MapleMapObject dropper, MapleCharacter owner, byte type,
            boolean playerDrop) {
        setPosition(position);
        this.item = item;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.meso = 0;
        this.type = type;
        this.playerDrop = playerDrop;
        this.droppedTime = System.currentTimeMillis();
    }

    public MapleWorldMapItem(IItem item, Point position, MapleMapObject dropper, MapleCharacter owner, byte type,
            boolean playerDrop, IEquip equip) {
        setPosition(position);
        this.item = item;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.meso = 0;
        this.type = type;
        this.playerDrop = playerDrop;
        this.droppedTime = System.currentTimeMillis();
        this.equip = equip;
    }

    public MapleWorldMapItem(IItem item, Point position, MapleMapObject dropper, MapleCharacter owner, byte type,
            boolean playerDrop, IEquip equip, boolean fly, boolean touch, int gradiant, int speed) {
        setPosition(position);
        this.item = item;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.meso = 0;
        this.type = type;
        this.playerDrop = playerDrop;
        this.droppedTime = System.currentTimeMillis();
        this.equip = equip;
        this.fly = fly;
        this.touch = touch;
        this.gradiant = gradiant;
        this.speed = speed;
    }

    public MapleWorldMapItem(IItem item, Point position, MapleMapObject dropper, MapleCharacter owner, byte type,
            boolean playerDrop, int questid) {
        setPosition(position);
        this.item = item;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.meso = 0;
        this.type = type;
        this.playerDrop = playerDrop;
        this.questid = questid;
        this.droppedTime = System.currentTimeMillis();
    }

    public MapleWorldMapItem(int meso, Point position, MapleMapObject dropper, MapleCharacter owner, byte type,
            boolean playerDrop) {
        setPosition(position);
        this.item = null;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.meso = meso;
        this.type = type;
        this.playerDrop = playerDrop;
        this.droppedTime = System.currentTimeMillis();
    }

    public final IItem getItem() {
        return item;
    }

    public final IEquip getEquip() {
        return equip;
    }

    public final int getQuest() {
        return questid;
    }

    public Lock getLock() {
        return lock;
    }

    public final long getDroppedTime() {
        return droppedTime;
    }

    public final int getItemId() {
        if (getMeso() > 0) {
            return meso;
        }
        return item.getItemId();
    }

    public final MapleMapObject getDropper() {
        return dropper;
    }

    public final int getOwner() {
        return character_ownerid;
    }

    public final int getMeso() {
        return meso;
    }

    public final boolean isPlayerDrop() {
        return playerDrop;
    }

    public final boolean isFly() {
        return fly;
    }

    public final boolean isTouch() {
        return touch;
    }

    public final int getGradiant() {
        return gradiant;
    }

    public final int getSpeed() {
        return speed;
    }

    public final boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(final boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public byte getDropType() {
        return type;
    }

    public void setDropType(byte a) {
        type = a;
    }

    @Override
    public final MapleMapObjectType getType() {
        return MapleMapObjectType.ITEM;
    }

    @Override
    public void sendSpawnData(final MapleClient client) {
        if (questid <= 0 || client.getPlayer().getQuestStatus(questid) == 1) {
            client.getSession().writeAndFlush(
                    MainPacketCreator.dropItemFromMapObject(this, getPosition(), getPosition(), (byte) 2));
        }
    }

    @Override
    public void sendDestroyData(final MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.removeItemFromMap(getObjectId(), 1, 0));
    }

    public void registerExpire(final long time) {
        nextExpiry = System.currentTimeMillis() + time;
    }

    public void registerFFA(final long time) {
        nextFFA = System.currentTimeMillis() + time;
    }

    public boolean shouldExpire(long now) {
        return !pickedUp && nextExpiry > 0 && nextExpiry < now;
    }

    public boolean shouldFFA(long now) {
        return !pickedUp && type < 2 && nextFFA > 0 && nextFFA < now;
    }

    public boolean hasFFA() {
        return nextFFA > 0;
    }

    public void setItem_Type(byte type) {
        item_type = type;
    }

    public byte getItem_Type() {
        return item_type;
    }

    public void expire(final MapleMap map) {
        pickedUp = true;
        map.broadcastMessage(MainPacketCreator.removeItemFromMap(getObjectId(), 0, 0));
        map.removeMapObject(this);
    }
}
