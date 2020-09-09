package server.Maps;

import java.awt.Point;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapObject.AbstractHinaMapObject;
import client.MapleClient;
import connections.Packets.MainPacketCreator;

public class ArrowFlatter extends AbstractHinaMapObject {

    private int cid, arrow;
    private Point pos;
    private long time;

    
    public ArrowFlatter(int cid, long time, Point pos, int arrow) {
        this.cid = cid;
        this.time = time;
        this.pos = pos;
        this.arrow = arrow;
    }
    
    public int getCid() {
        return cid;
    }
    
    public int getArrow() {
        return arrow;
    }
    @Override
    public Point getPosition() {
        return pos;
    }
   
    public long getTime() {
        return time;
    }

    
    public MapleMapObjectType getType() {
        return MapleMapObjectType.ARROWFLATTER;
    }

    
    public void sendSpawnData(MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.spawnArrowFlatter(cid, arrow, pos, getObjectId()));
        client.getSession().writeAndFlush(MainPacketCreator.spawnArrowFlatter(arrow, getObjectId()));
    }

   
    public void sendDestroyData(MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.cancelArrowFlatter(getObjectId(), arrow));
    }
}
