package client.Community.MapleExpedition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import client.Community.MapleParty.MapleParty;
import client.Character.MapleCharacter;
import client.Community.MapleParty.MaplePartyCharacter;
import client.MapleClient;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldCommunity;

public class MapleExpedition {

    Map<Integer, MapleParty> parties = new LinkedHashMap<Integer, MapleParty>();
    ReentrantLock lock = new ReentrantLock();
    private int leader;
    private MapleExpeditionType type;
    private int saveBossRaidMap = -1;
    private int saveBossRaidChannel = -1;
    private boolean clearedBoss = false;
    private List<Integer> deadchars = new LinkedList<Integer>();
    private int id;

    public MapleExpedition(int lead, MapleExpeditionType typ) {
        this.leader = lead;
        this.type = typ;
    }

    public boolean isBossKilled() {
        return clearedBoss;
    }

    public void setBossKilled(boolean kill) {
        this.clearedBoss = kill;
    }

    public int getPositionByPartyId(int id) {
        lock.lock();
        try {
            for (Entry<Integer, MapleParty> e : parties.entrySet()) {
                if (e.getValue().getId() == id) {
                    return e.getKey();
                }
            }
        } finally {
            lock.unlock();
        }

        return -1;
    }

    public final ArrayList<MapleCharacter> getExpeditionMembers(final MapleClient c) {
        ArrayList<MapleCharacter> chars = new ArrayList<>();
        for (int i = 0; i < parties.size(); i++) {
            MapleParty pp = WorldCommunity.getParty(parties.get(i).getId());
            if (pp == null) {
                parties.remove(i);
            } else {
                for (MaplePartyCharacter chr : pp.getMembers()) {
                    chars.add(c.getChannelServer().getPlayerStorage().getCharacterById(chr.getId()));
                }
            }
        }
        return chars;
    }

    public void addDeadChar(int cid) {
        deadchars.add(cid);
    }

    public boolean containDeadChar(int cid) {
        return deadchars.contains(cid);
    }

    public int getLastBossMap() {
        return saveBossRaidMap;
    }

    public void setLastBossMap(int map) {
        this.saveBossRaidMap = map;
    }

    public int getLastBossChannel() {
        return saveBossRaidChannel;
    }

    public void setLastBossChannel(int cha) {
        this.saveBossRaidChannel = cha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAllMemberSize() {
        lock.lock();
        try {
            int ret = 0;
            for (MapleParty party : getPartys()) {
                ret += party.getMembers().size();
            }
            return ret;
        } finally {
            lock.unlock();
        }
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int le) {
        this.leader = le;
    }

    public MapleExpeditionType getType() {
        return type;
    }

    public Collection<MapleParty> getPartys() {
        return parties.values();
    }

    public void addParty(int num, MapleParty party) {
        lock.lock();
        try {
            if (parties.containsKey(num)) {
                parties.remove(num);
            }
            parties.put(num, party);
        } finally {
            lock.unlock();
        }
    }

    public void removeParty(int num) {
        lock.lock();
        try {
            if (parties.containsKey(num)) {
                parties.remove(num);
            }
        } finally {
            lock.unlock();
        }
    }

    public MapleParty getParty(int num) {
        lock.lock();
        try {
            return parties.get(num);
        } finally {
            lock.unlock();
        }
    }

    public boolean isContained(int num) {
        lock.lock();
        try {
            return parties.keySet().contains(num);
        } finally {
            lock.unlock();
        }
    }

    public int getNextFreeSlot() {
        if (parties.size() >= 6) {
            return -1;
        }
        lock.lock();
        try {
            for (int z = 0; z <= 4; z++) {
                if (!parties.keySet().contains(z)) {
                    return z;
                }
            }
            return -1;
        } finally {
            lock.unlock();
        }
    }

    public void broadcastMessage(byte[] packet) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            lock.lock();
            try {
                for (MapleParty party : getPartys()) {
                    for (MaplePartyCharacter hpc : party.getMembers()) {
                        MapleCharacter hp = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                        if (hp != null) {
                            hp.send(packet);
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void broadcastMessage(MapleCharacter chr, byte[] packet) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            lock.lock();
            try {
                for (MapleParty party : getPartys()) {
                    for (MaplePartyCharacter hpc : party.getMembers()) {
                        MapleCharacter hp = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                        if (hp != null && hp.getId() != chr.getId()) {
                            hp.send(packet);
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
