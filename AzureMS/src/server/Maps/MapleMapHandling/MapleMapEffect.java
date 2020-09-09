package server.Maps.MapleMapHandling;

import client.MapleClient;
import connections.Packets.MainPacketCreator;

public class MapleMapEffect {

    private String msg;
    private int itemId;
    private boolean active = true;

    public MapleMapEffect(String msg, int itemId) {
        this.msg = msg;
        this.itemId = itemId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public byte[] makeDestroyData() {
        return MainPacketCreator.removeMapEffect();
    }

    public byte[] makeStartData() {
        return MainPacketCreator.startMapEffect(msg, itemId, active);
    }

    public void sendStartData(MapleClient c) {
        c.getSession().writeAndFlush(MainPacketCreator.startMapEffect(msg, itemId, active));
    }
}
