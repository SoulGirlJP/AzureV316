package server.Maps.MapReactor;

import java.awt.Rectangle;

import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapObject.AbstractHinaMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import client.MapleClient;
import connections.Packets.MainPacketCreator;
import scripting.Reactor.ReactorScriptManager;
import tools.Pair;
import tools.Timer.MapTimer;

public class MapleReactor extends AbstractHinaMapObject {

    private int rid, gid;
    private MapleReactorStats stats;
    private byte state;
    private int delay;
    private MapleMap map;
    private String name;
    private int rank = 0;
    private boolean timerActive, alive;

    public MapleReactor(MapleReactorStats stats, int rid) {
        this.stats = stats;
        this.rid = rid;
        alive = true;
    }

    public MapleReactor(MapleReactorStats stats, int rid, int gid) {
        this.stats = stats;
        this.rid = rid;
        alive = true;
    }

    public final byte getFacingDirection() {
        return stats.getFacingDirection();
    }

    public final MapleReactorStats getStats() {
        return stats;
    }

    public void setTimerActive(boolean active) {
        this.timerActive = active;
    }

    public boolean isTimerActive() {
        return timerActive;
    }

    public int getReactorId() {
        return rid;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public byte getState() {
        return state;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.REACTOR;
    }

    public int getReactorType(byte index) {
        return stats.getType(state, index);
    }

    public void setMap(MapleMap map) {
        this.map = map;
    }

    public MapleMap getMap() {
        return map;
    }

    public Pair<Integer, Integer> getReactItem(byte index) {
        return stats.getReactItem(state, index);
    }

    @Override
    public void sendDestroyData(MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.destroyReactor(this));
    }

    @Override
    public void sendSpawnData(MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.spawnReactor(this));
    }

    public void forceStartReactor(MapleClient c) {
        ReactorScriptManager.getInstance().act(c, this);
    }

    // hitReactor command for item-triggered reactors
    public void hitReactor(MapleClient c) {
        if (stats.getState(state) == null) {
            hitReactor(0, (short) 0, c, (byte) 0);
            return;
        } else if (stats.getStateEventSize(state) == 0) {
            hitReactor(0, (short) 0, c, (byte) 0);
            return;
        } else {
            for (int i = 0; i < stats.getStateEventSize(state); i++) {
                hitReactor(0, (short) 0, c, (byte) i);
            }
        }
    }

    public void delayedDestroyReactor(long delay) {
        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                map.destroyReactor(getObjectId());
            }
        }, delay);
    }

    public void forceHitReactor(final byte newState) {
        setState((byte) newState);
        setTimerActive(false);
        map.broadcastMessage(MainPacketCreator.triggerReactor(this, (short) 0, 0));
    }

    public void hitReactor(int charPos, short stance, MapleClient c, byte index) {
        if (stats.getType(state, index) < 999 && stats.getType(state, index) != -1) {
            // type 2 = only hit from right (kerning swamp plants), 00 is air left 02 is
            // ground left

            if (!(stats.getType(state, index) == 2 && (charPos == 0 || charPos == 2))) { // next state
                state = stats.getNextState(state, index);

                if (stats.getNextState(state, index) == -1) { // end of reactor
                    if (stats.getType(state, index) < 100) { // reactor broken
                        if (delay > 0) {
                            map.destroyReactor(getObjectId());
                        } else {// trigger as normal
                            map.broadcastMessage(MainPacketCreator.triggerReactor(this, stance, c.getPlayer().getId()));
                        }
                    } else { // item-triggered on final step
                        map.broadcastMessage(MainPacketCreator.triggerReactor(this, stance, c.getPlayer().getId()));
                    }
                    ReactorScriptManager.getInstance().act(c, this);
                } else { // reactor not broken yet
                    map.broadcastMessage(MainPacketCreator.triggerReactor(this, stance, c.getPlayer().getId()));
                    if (state == stats.getNextState(state, index)) { // current state = next state, looping reactor
                        ReactorScriptManager.getInstance().act(c, this);
                    }
                }
            }
        }
    }

    public Rectangle getArea() {
        int height = stats.getBR().y - stats.getTL().y;
        int width = stats.getBR().x - stats.getTL().x;
        int origX = getPosition().x + stats.getTL().x;
        int origY = getPosition().y + stats.getTL().y;

        return new Rectangle(origX, origY, width, height);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int r) {
        this.rank = r;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Reactor " + getObjectId() + " of id " + rid + " at position " + getPosition().toString() + " state"
                + state + " type " + stats.getType(state, (byte) 0);
    }

    public void setGuildid(int gid) {
        this.gid = gid;
    }

    public int getGuildid() {
        return gid;
    }
}
