package server.Shops;

import java.util.ArrayList;
import java.util.List;

import client.Character.MapleCharacter;
import client.MapleClient;
import connections.Packets.PlayerShopPacket;
import server.Maps.MapObject.MapleMapObjectType;

public class MapleCharacterShop extends AbstractPlayerStore {

    private boolean open;
    private MapleCharacter owner;
    private List<String> bannedList = new ArrayList<String>();

    public MapleCharacterShop(MapleCharacter owner, int itemId, String desc) {
        super(owner, owner.getId(), owner.getAccountID(), owner.getPosition(), itemId, desc, "", 3);
        this.owner = owner;
        open = false;
    }

    @Override
    public void buy(MapleClient c, int item, short quantity) {
        MapleCharacterShopItem pItem = items.get(item);
        if (pItem.bundles > 0) {
            owner.getClient().getSession().writeAndFlush(PlayerShopPacket.shopItemUpdate(this));
        }
    }

    @Override
    public byte getShopType() {
        return IMapleCharacterShop.PLAYER_SHOP;
    }

    @Override
    public void closeShop(boolean saveItems, boolean remove) {
        MapleCharacter owner = getMCOwner();
        getMCOwner().getClient().getSession().writeAndFlush(PlayerShopPacket.shopErrorMessage(10, 3, 0)); // The store is closed.
        removeAllVisitors(3, -2);

        getMap().removeMapObject(this);
        for (MapleCharacterShopItem items : getItems()) {
            saveItems();
            break;
        }
        owner.setPlayerShop(null);
        update();
    }

    public void banPlayer(String name) {
        if (!bannedList.contains(name)) {
            bannedList.add(name);
        }
        for (int i = 0; i < 3; i++) {
            MapleCharacter chr = getVisitor(i);
            if (chr.getName().equals(name)) {
                chr.getClient().getSession().writeAndFlush(PlayerShopPacket.shopErrorMessage(5, 1, 0x11));
                chr.setPlayerShop(null);
                removeVisitor(chr);
            }
        }
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    public boolean isBanned(String name) {
        if (bannedList.contains(name)) {
            return true;
        }
        return false;
    }

    public MapleCharacter getMCOwner() {
        return owner;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.SHOP;
    }

    @Override
    public void sendSpawnData(MapleClient client) {
    }

    @Override
    public void sendDestroyData(MapleClient client) {
    }
}
