package launcher.LauncherHandlers;

import client.ItemInventory.MapleInventory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class MapleItemHolder {

    private static final ReentrantReadWriteLock mutex = new ReentrantReadWriteLock();
    private static final Lock rL = mutex.readLock(), wL = mutex.writeLock();
    private static Map<Integer, MapleInventory[]> ivs = new HashMap<>();

    public static final void registerInv(final int cid, final MapleInventory[] inventorys) {
        wL.lock();
        try {
            ivs.put(cid, inventorys);
        } finally {
            wL.unlock();
        }
    }

    public static final void deregisterPlayer(final int cid) {
        wL.lock();
        try {
            ivs.remove(cid);
        } finally {
            wL.unlock();
        }
    }

    public static final MapleInventory[] getInv(final int cid) {
        rL.lock();
        try {
            return ivs.get(cid);
        } finally {
            rL.unlock();
        }
    }

}
