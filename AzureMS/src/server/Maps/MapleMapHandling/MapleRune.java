package server.Maps.MapleMapHandling;

import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapObject.AbstractHinaMapObject;
import client.MapleClient;
import connections.Packets.RunePacket;

public class MapleRune extends AbstractHinaMapObject {

    private int type, posX, posY;
    private MapleMap map;

    public MapleRune(int type, int posX, int posY, MapleMap map) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.map = map;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.RUNE;
    }

    public void setMap(MapleMap map) {
        this.map = map;
    }

    public MapleMap getMap() {
        return map;
    }

    public int getRuneType() {
        return type;
    }

    public int getPositionX() {
        return posX;
    }

    public int getPositionY() {
        return posY;
    }

    @Override
    public void sendSpawnData(MapleClient client) {
        client.getSession().writeAndFlush(RunePacket.spawnRune(this, false));
    }

    @Override
    public void sendDestroyData(MapleClient client) {

    }
}
