package server.Items;

import client.ItemInventory.IItem;
import client.ItemInventory.ItemFactory;
import client.ItemInventory.MapleInventoryType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.MapleClient;
import constants.GameConstants;
import connections.Packets.CashPacket;
import tools.Pair;

public class MapleCashInventory {

    private List<IItem> inventory = new ArrayList<IItem>();
    private int accid;

    public MapleCashInventory(int accid) {
        this.accid = accid;
    }

    public List<IItem> getInventory() {
        return inventory;
    }

    public int getAccId() {
        return accid;
    }

    public void addItem(IItem item) {
        inventory.add(item);
    }

    public IItem findByCashId(int id) {
        for (IItem item : inventory) {
            if (item.getUniqueId() == id) {
                return item;
            }
        }
        return null;
    }

    public void removeItemByCashId(int id) {
        int index = -1;
        for (IItem item : inventory) {
            index++;
            if (item.getUniqueId() == id) {
                break;
            }
        }
        if (index != -1) {
            inventory.remove(index);
        } else {
            System.err.println("[Error] Failed to delete because cache item was not found.");
        }
    }

    public void loadFromDB() {
        try {
            for (Pair<IItem, MapleInventoryType> list : ItemFactory.CASHSHOP.loadItems(false, accid).values()) {
                this.addItem(list.left);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveToDB() {
        List<Pair<IItem, MapleInventoryType>> list = new ArrayList<>();
        for (IItem item : inventory) {
            list.add(new Pair<>(item, GameConstants.getInventoryType(item.getItemId())));
        }
        try {
            ItemFactory.CASHSHOP.saveItems(list, accid);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeFromInventory(IItem item) {
        inventory.remove(item);
    }

    public void checkExpire(MapleClient c) {
        List<IItem> toberemove = new ArrayList<IItem>();
        for (IItem item : inventory) {
            if (item != null && !GameConstants.isPet(item.getItemId()) && item.getExpiration() > 0
                    && item.getExpiration() < System.currentTimeMillis()) {
                toberemove.add(item);
            }
        }
        if (toberemove.size() > 0) {
            for (IItem item : toberemove) {
                removeFromInventory(item);
                c.getSession().writeAndFlush(CashPacket.itemExpired(item.getUniqueId()));
            }
            toberemove.clear();
        }
    }
}
