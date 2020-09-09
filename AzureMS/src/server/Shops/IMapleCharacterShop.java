package server.Shops;

import java.util.List;

import client.Character.MapleCharacter;
import client.MapleClient;
import server.Shops.AbstractPlayerStore.BoughtItem;
import tools.Pair;

public interface IMapleCharacterShop {

    public final static byte OMOK = 1;
    public final static byte CARD = 2;
    public final static byte ROCK_PAPER_SCISSORS = 3;
    public final static byte PLAYER_SHOP = 5;
    public final static byte HIRED_MERCHANT = 6;

    public String getOwnerName();

    public String getDescription();

    public List<Pair<Byte, MapleCharacter>> getVisitors();

    public List<MapleCharacterShopItem> getItems();

    public boolean isOpen();

    public boolean removeItem(int item);

    public boolean isOwner(MapleCharacter chr);

    public byte getShopType();

    public byte getVisitorSlot(MapleCharacter visitor);

    public byte getFreeSlot();

    public int getGameType();

    public int getItemId();

    public int getMeso();

    public int getOwnerId();

    public int getOwnerAccId();

    public void setOpen(boolean open);

    public void setMeso(int meso);

    public void addItem(MapleCharacterShopItem item);

    public void removeFromSlot(int slot);

    public void broadcastToVisitors(byte[] packet);

    public void addVisitor(MapleCharacter visitor);

    public void removeVisitor(MapleCharacter visitor);

    public void removeAllVisitors(int error, int type);

    public void buy(MapleClient c, int item, short quantity);

    public void closeShop(boolean saveItems, boolean remove);

    public int getMaxSize();

    public int getSize();

    public List<BoughtItem> getBoughtItems();

    public void setPassword(String password);

    public String getPassword();

    public List<Pair<String, Byte>> getMessages();
}
