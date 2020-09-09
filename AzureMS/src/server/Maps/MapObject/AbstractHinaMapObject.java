package server.Maps.MapObject;


import java.awt.Point;

public abstract class AbstractHinaMapObject implements MapleMapObject {

    private Point position = new Point();
    private int objectId;

    
    public abstract MapleMapObjectType getType();

    
    public Point getPosition() {
        return new Point(position);
    }

   
    public void setPosition(Point position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    
    public int getObjectId() {
        return objectId;
    }

   
    public void setObjectId(int id) {
        this.objectId = id;
    }

    public Point getTruePosition() {
        return position;
    }
}
