package client.ItemInventory;

import client.Character.MapleCharacter;
import client.MapleClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;

import constants.GameConstants;
import constants.ServerConstants;
import connections.Database.MYSQL;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import tools.Timer.MapTimer;
import tools.RandomStream.Randomizer;

public class MapleItempotMain {

    protected Map<Integer, MapleSaveItemPot> impHolder = new HashMap<Integer, MapleSaveItemPot>();
    private static MapleItempotMain instance = null;
    private ScheduledFuture<?> runner = null;
    private long RunInterval = 5 * 60 * 1000;
    private long diseaseTime = 10 * 60 * 1000;
    private long starveTime = 20 * 60 * 1000;

    public MapleItempotMain() {
        if (runner == null) {
            loadFromDB();
            doMain();
        }
    }

    public boolean hasImp(int cid) {
        if (impHolder.containsKey(cid)) {
            if (impHolder.get(cid).imphold.size() > 0) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    private void loadFromDB() {
        Connection con = null;
        PreparedStatement ps;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT id FROM `characters`");
            ResultSet rs = ps.executeQuery();
            List<Integer> temp = new ArrayList<Integer>();
            while (rs.next()) {
                temp.add(rs.getInt("id"));
                impHolder.put(rs.getInt("id"), new MapleSaveItemPot());
            }
            ps.close();
            rs.close();
            for (Integer cid : temp) {
                ps = con.prepareStatement("SELECT * FROM `itempot` WHERE cid = ?");
                ps.setInt(1, cid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    MapleItempot imp = new MapleItempot(rs.getInt("lifeid"), rs.getInt("slot"), cid,
                            rs.getLong("starttime"));
                    imp.setCloseness(rs.getInt("closeness"));
                    imp.setFullness(rs.getInt("fullness"));
                    imp.setLevel(rs.getInt("level"));
                    imp.setStatus(rs.getInt("status"));
                    imp.setSleepTime(rs.getLong("sleeptime"));
                    imp.setIncCloseLeft(rs.getInt("incCloseLeft"));
                    impHolder.get(cid).putImp(imp);
                }
                rs.close();
                ps.close();
            }
            con.close();
        } catch (Exception e) {
            System.err.println("[Error] Failed to load itempot data.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public static MapleItempotMain getInstance() {
        if (instance == null) {
            instance = new MapleItempotMain();
        }
        return instance;
    }

    public void putImp(int cid, MapleItempot imp) {
        if (!impHolder.containsKey(cid)) {
            impHolder.put(cid, new MapleSaveItemPot());
        }
        impHolder.get(cid).putImp(imp);
    }

    public void newCharDB(int cid) {
        impHolder.put(cid, new MapleSaveItemPot());
    }

    public MapleItempot getImp(int cid, int slot) {
        return impHolder.get(cid).getImpInSlot(slot);
    }

    public void removeImp(int cid, int slot) {
        impHolder.get(cid).removeImpInSlot(slot);
    }

    public void PlayerLoggedInMapleImp(MapleClient c) {
        int cid = c.getPlayer().getId();
        for (Entry<Integer, MapleItempot> imp : impHolder.get(cid).imphold.entrySet()) {
            if (imp.getValue().getLifeId() < 1000) {
                impHolder.get(cid).removeImpInSlot(imp.getValue().getSlot());
                continue;
            }
            c.send(MainPacketCreator.showItempotAction(imp.getValue(), true));
        }
    }

    public void saveToDBall() {
        for (Entry<Integer, MapleSaveItemPot> ea : impHolder.entrySet()) {
            int cid = ea.getKey();
            try {
                Connection con = MYSQL.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM `itempot` WHERE cid = ?");
                ps.setInt(1, cid);
                ps.executeUpdate();
                ps.close();
                for (Entry<Integer, MapleItempot> impa : impHolder.get(cid).imphold.entrySet()) {
                    MapleItempot imp = impa.getValue();
                    try {
                        ps = con.prepareStatement("INSERT INTO `itempot` VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        ps.setInt(1, cid);
                        ps.setInt(2, imp.getLifeId());
                        ps.setInt(3, imp.getSlot());
                        ps.setInt(4, imp.getLevel());
                        ps.setInt(5, imp.getStatus());
                        ps.setInt(6, imp.getFullness());
                        ps.setInt(7, imp.getCloseness());
                        ps.setLong(8, imp.getStartTime());
                        ps.setLong(9, imp.getSleepTime());
                        ps.setInt(10, imp.getIncCloseLeft());
                        ps.executeUpdate();
                        ps.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                con.close();
            } catch (Exception e) {
                System.err.println("[Error] Itempot failed to save all data.");
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveToDB(int cid) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM `itempot` WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();
            for (Entry<Integer, MapleItempot> impa : impHolder.get(cid).imphold.entrySet()) {
                MapleItempot imp = impa.getValue();
                try {
                    ps = con.prepareStatement("INSERT INTO `itempot` VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setInt(1, cid);
                    ps.setInt(2, imp.getLifeId());
                    ps.setInt(3, imp.getSlot());
                    ps.setInt(4, imp.getLevel());
                    ps.setInt(5, imp.getStatus());
                    ps.setInt(6, imp.getFullness());
                    ps.setInt(7, imp.getCloseness());
                    ps.setLong(8, imp.getStartTime());
                    ps.setLong(9, imp.getSleepTime());
                    ps.setInt(10, imp.getIncCloseLeft());
                    ps.executeUpdate();
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            con.close();
        } catch (Exception e) {
            System.err.println("[Error] Failed to save item pot data.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    private void doMain() {
        MapTimer tMan = MapTimer.getInstance();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (Entry<Integer, MapleSaveItemPot> e : impHolder.entrySet()) {
                    for (Entry<Integer, MapleItempot> imp : e.getValue().imphold.entrySet()) {
                        MapleItempot real = imp.getValue();
                        if (real.getLifeId() < 1000) {
                            e.getValue().removeImpInSlot(real.getSlot());
                            continue;
                        }
                        if (real.getLevel() > 0) {
                            if (real.getStatus() != 3 && real.getStatus() != 5) { // Sleep or growth check
                                runMapleImp(real, false);
                            } else if (real.getStatus() == 5) {
                            } else { // ±ú¾î³²
                                if (real.getSleepTime() <= System.currentTimeMillis()) {
                                    real.setStatus(1);
                                    real.setLevel(real.getLevel() + 1);
                                    real.setFullness(10);
                                    real.gainCloseness(Randomizer.rand(10, 15));
                                    if (real.getLevel() == GameConstants.getMaxLevelImp(real.getLifeId())) {
                                        real.setStatus(5);
                                    } else {
                                        runMapleImp(real, true);
                                    }
                                }
                            }
                        }
                        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                            MapleCharacter chr = cserv.getPlayerStorage().getCharacterById(real.getOwnerId());
                            if (chr != null) {
                                chr.send(MainPacketCreator.showItempotAction(real, true));
                            }
                        }
                    }
                }
            }
        };
        runner = tMan.register(r, RunInterval);
    }

    private void runMapleImp(MapleItempot real, boolean awake) {
        int random = Randomizer.nextInt(10);
        if (random == 0 && !awake) {
            real.setStatus(4); // Rhythm
            real.setLastFeedTime(System.currentTimeMillis());
        }
        if (real.getFullness() == 0) { // hungry
            if ((real.getLastFeedTime() + starveTime) < System.currentTimeMillis()) {
                real.setLevel(real.getLevel() - 1);
                if (real.getLevel() == 0) {
                    real.setStatus(0);
                }
            }
            if (real.getCloseness() != 0) {
                real.setCloseness(real.getCloseness() - 2);
            }
        } else {
            if (real.getStatus() == 4) { // If the item pot hurts
                real.setFullness(real.getFullness() - 2);
                real.setCloseness(real.getCloseness() - 2);
                if ((real.getLastFeedTime() + diseaseTime) < System.currentTimeMillis()) {
                    real.setLevel(real.getLevel() - 1);
                    if (real.getLevel() == 0) {
                        real.setStatus(0);
                    }
                }
            } else { // usually
                if (System.currentTimeMillis() > real.getLastFeedTime() + (real.getFeedInterval() * 60 * 1000)) {
                    if (real.getIncCloseLeft() <= 1) {
                        real.setIncCloseLeft(real.getIncCloseLeft() + 1);
                    }
                }
                real.setFullness(real.getFullness() - 1);
            }
            if (real.getFullness() <= 0) {
                real.setFullness(0);
                real.setStatus(2);
                real.setLastFeedTime(System.currentTimeMillis());
            }
        }
    }
}
