package client.ItemInventory;

import java.util.HashMap;
import java.util.Map;

public class MapleSaveItemPot { // All of the conditions below will be saved as a query

    protected Map<Integer, MapleItempot> imphold = new HashMap<Integer, MapleItempot>();

    public void putImp(MapleItempot imp) { // Add item pot to the slot
        imphold.put(imp.getSlot(), imp);
    }

    public MapleItempot getImpInSlot(int slot) { // Load item pot in specific slot
        return imphold.get(slot);
    }

    public void removeImpInSlot(int slot) { // Delete item pot from the slot
        imphold.remove(slot);
    }
}
