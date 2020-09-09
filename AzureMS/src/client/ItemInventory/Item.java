package client.ItemInventory;

import java.io.Serializable;

import client.MapleAndroid;
import client.ItemInventory.PetsMounts.MaplePet;
import server.Items.ItemInformation;
import server.Items.MapleItemIdenfier;

public class Item implements IItem, Serializable {

    private int id;
    private short position;
    private short quantity;
    private short flag;
    private long expiration = -1;
    private MaplePet pet = null;
    private MapleAndroid android = null;
    private int uniqueid = -1;
    private String owner = "";
    private String GameMaster_log = null;
    private String giftFrom = "";
    private boolean isCash = false;
    private int inventoryitemid;

    public Item(final int id, final short position, final short quantity, final short flag) {
        super();
        this.id = id;
        this.position = position;
        this.quantity = quantity;
        this.pet = null;
        this.flag = flag;
        this.uniqueid = MapleItemIdenfier.getInstance().getNewUniqueId();
    }

    @Override
    public IItem copy() {
        final Item ret = new Item(id, position, quantity, flag);
        ret.pet = pet;
        ret.android = android;
        ret.owner = owner;
        ret.GameMaster_log = GameMaster_log;
        ret.expiration = expiration;
        ret.isCash = isCash;
        ret.uniqueid = uniqueid;
        ret.flag = flag;
        return ret;
    }

    @Override
    public final void setPosition(final short position) {
        this.position = position;

        if (pet != null) {
            pet.setInventoryPosition(position);
        }
    }

    @Override
    public void setQuantity(final short quantity) {
        this.quantity = quantity;
    }

    @Override
    public final int getItemId() {
        return id;
    }

    public final int setItemId(int id) {
        this.id = id;
        return id;
    }

    @Override
    public final int getInventoryId() {
        return inventoryitemid;
    }

    @Override
    public final void setInventoryId(int id) {
        this.inventoryitemid = id;
    }

    @Override
    public final short getPosition() {
        return position;
    }

    @Override
    public final short getFlag() {
        if (isCash()) {
            if (!ItemFlag.KARMA_EQ.check(flag)) {
                flag |= ItemFlag.KARMA_EQ.getValue();
            }
        }
        return flag;
    }

    @Override
    public final short getQuantity() {
        return quantity;
    }

    @Override
    public byte getType() {
        return 2; // An Item
    }

    @Override
    public final String getOwner() {
        return owner;
    }

    @Override
    public final void setOwner(final String owner) {
        this.owner = owner;
    }

    @Override
    public final void setFlag(final short flag) {
        this.flag = flag;
    }

    @Override
    public final long getExpiration() {
        return expiration;
    }

    @Override
    public final void setExpiration(final long expire) {
        this.expiration = expire;
    }

    @Override
    public final String getGMLog() {
        return GameMaster_log;
    }

    @Override
    public void setGMLog(final String GameMaster_log) {
        this.GameMaster_log = GameMaster_log;
    }

    @Override
    public final int getUniqueId() {
        if (uniqueid == -1) {
            this.uniqueid = MapleItemIdenfier.getInstance().getNewUniqueId();
        }
        return uniqueid;
    }

    @Override
    public final void setUniqueId(final int id) {
        this.uniqueid = id;
    }

    @Override
    public final MaplePet getPet() {
        return pet;
    }

    public final void setPet(final MaplePet pet) {
        this.pet = pet;
    }

    @Override
    public final MapleAndroid getAndroid() {
        return android;
    }

    public final void setAndroid(final MapleAndroid and) {
        this.android = and;
    }

    @Override
    public int compareTo(IItem other) {
        if (Math.abs(position) < Math.abs(other.getPosition())) {
            return -1;
        } else if (Math.abs(position) == Math.abs(other.getPosition())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Item: " + id + " quantity: " + quantity;
    }

    @Override
    public void setGiftFrom(String gift) {
        giftFrom = gift;
    }

    @Override
    public String getGiftFrom() {
        return giftFrom;
    }

    @Override
    public boolean isCash() {
        if (!isCash) {
            isCash = ItemInformation.getInstance().isCash(id);
        }
        return isCash;
    }

    @Override
    public void setCash(boolean cash) {
        this.isCash = cash;
    }
}
