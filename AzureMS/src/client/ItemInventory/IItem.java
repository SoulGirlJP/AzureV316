package client.ItemInventory;

import client.MapleAndroid;
import client.ItemInventory.PetsMounts.MaplePet;

public interface IItem extends Comparable<IItem> {

    byte getType();

    short getPosition();

    short getFlag();

    short getQuantity();

    String getOwner();

    String getGMLog();

    int getItemId();

    MaplePet getPet();

    MapleAndroid getAndroid();

    int getUniqueId();

    IItem copy();

    long getExpiration();

    void setFlag(short flag);

    void setUniqueId(int id);

    void setPosition(short position);

    void setExpiration(long expire);

    void setOwner(String owner);

    void setGMLog(String GameMaster_log);

    void setQuantity(short quantity);

    void setGiftFrom(String gift);

    int getInventoryId();

    void setInventoryId(int id);

    String getGiftFrom();

    boolean isCash();

    void setCash(boolean cash);
}
