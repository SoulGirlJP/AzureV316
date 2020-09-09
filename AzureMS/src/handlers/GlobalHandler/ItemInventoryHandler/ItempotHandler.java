package handlers.GlobalHandler.ItemInventoryHandler;

import client.MapleClient;
import client.ItemInventory.MapleItempot;
import client.ItemInventory.MapleItempotMain;
import client.ItemInventory.MapleInventoryType;
import constants.GameConstants;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import tools.RandomStream.Randomizer;

public class ItempotHandler {

    public static void putItempot(ReadingMaple rm, MapleClient c) {
        int itemid = rm.readInt();
        int slot = rm.readInt();
        int targetslot = rm.readInt();

        if (targetslot == 0) { // Message after using ItemPod
            c.getPlayer().dropMessage(1, "After pressing the [Y] key, move the item pot to be raised to any slot..");
            MapleItempotMain.getInstance().getImp(c.getPlayer().getId(), slot);
            c.send(MainPacketCreator.resetActions(c.getPlayer())); // Make the player move
            return;
        }

        ItemInformation ii = ItemInformation.getInstance(); // Retrieve item information
        MapleItempot imp = new MapleItempot(ii.getImpLifeid(itemid), targetslot, c.getPlayer().getId()); // Retrieve query information
        MapleItempotMain.getInstance().putImp(c.getPlayer().getId(), imp); // Added item pod to server
        c.send(MainPacketCreator.showItempotAction(imp, true)); // Send itempot packet
        InventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, (byte) slot, (short) 1, false); // The itempot from the inventory
        // delete
        c.send(MainPacketCreator.resetActions(c.getPlayer())); // To make the player moveÇÔ
    }

    public static void removeItempot(ReadingMaple rm, MapleClient c) {
        int slot = rm.readInt();
        MapleItempotMain.getInstance().removeImp(c.getPlayer().getId(), slot);
        c.send(MainPacketCreator.showItempotAction(new MapleItempot(0, slot, 0, 0), false));
        c.send(MainPacketCreator.resetActions(c.getPlayer()));
    }

    public static void feedItempot(ReadingMaple rm, MapleClient c) {
        int itemid = rm.readInt();
        int slot = rm.readInt();
        int targetslot = rm.readInt();
        int increaseFullness = (GameConstants.getInventoryType(itemid) == MapleInventoryType.ETC ? 1 : 5);
        int random = Randomizer.nextInt(20);
        MapleItempotMain mib = MapleItempotMain.getInstance();
        MapleItempot imp = mib.getImp(c.getPlayer().getId(), targetslot);
        if (imp.getStatus() == 1 || imp.getStatus() == 2) {
            if (random == 1) {
                increaseFullness *= 2 + Randomizer.nextInt(1);
                imp.gainFullness(increaseFullness);
                imp.gainCloseness(1 + Randomizer.nextInt(imp.getIncClose()));
                MainPacketCreator.serverNotice(6, "Item pot eats good items, greatly increases satiety and intimacy.");
            }
            if (imp.getStatus() == 2) {
                imp.setStatus(1);
                imp.gainFullness(increaseFullness);
                imp.setLastFeedTime(System.currentTimeMillis());
            } else if (imp.getStatus() == 1) {
                if (imp.getIncCloseLeft() > 0) {
                    imp.gainCloseness(1 + Randomizer.nextInt(imp.getIncClose()));
                    imp.setIncCloseLeft(imp.getIncCloseLeft() - 1);
                    imp.setLastFeedTime(System.currentTimeMillis());
                }
                imp.gainFullness(increaseFullness);
            }

            if (imp.getFullness() >= imp.getMaxFull()) {
                imp.setStatus(3);
                imp.setSleepTime(System.currentTimeMillis() + (21 * 60 * 60 * 1000));
            }
        }
        c.send(MainPacketCreator.showItempotAction(imp, true));
        InventoryManipulator.removeFromSlot(c, GameConstants.getInventoryType(itemid), (byte) slot, (short) 1, false);
        c.send(MainPacketCreator.resetActions(c.getPlayer()));
    }

    public static void cureItempot(ReadingMaple rm, MapleClient c) {
        int itemid = rm.readInt();
        int slot = rm.readInt();
        int targetslot = rm.readInt();
        MapleItempotMain mib = MapleItempotMain.getInstance();
        MapleItempot imp = mib.getImp(c.getPlayer().getId(), targetslot);
        if (imp.getStatus() == 4) {
            imp.gainCloseness(1 + Randomizer.nextInt(imp.getIncClose()));
            imp.setLastFeedTime(System.currentTimeMillis());
            if (imp.getFullness() <= 0) {
                imp.setFullness(0);
                imp.setStatus(2);
            } else {
                imp.setStatus(1);
            }
        }
        c.send(MainPacketCreator.showItempotAction(imp, true));
        InventoryManipulator.removeFromSlot(c, GameConstants.getInventoryType(itemid), (byte) slot, (short) 1, false);
        c.send(MainPacketCreator.resetActions(c.getPlayer()));
    }
}
