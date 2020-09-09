package server.Maps.MapObject;

import java.awt.Point;

import client.MapleClient;

public interface MapleMapObject {

    public int getObjectId();

    public void setObjectId(final int id);

    public MapleMapObjectType getType();

    public Point getPosition();

    public void setPosition(final Point position);

    public void sendSpawnData(final MapleClient client);

    public void sendDestroyData(final MapleClient client);
}
