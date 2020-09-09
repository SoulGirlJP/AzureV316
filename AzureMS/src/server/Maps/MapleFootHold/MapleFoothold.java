package server.Maps.MapleFootHold;

import java.awt.Point;

public class MapleFoothold implements Comparable<MapleFoothold> {

    private Point p1;
    private Point p2;
    private int id;
    private short next, prev;

    public MapleFoothold(Point p1, Point p2, int id) {
        this.p1 = p1;
        this.p2 = p2;
        this.id = id;
    }

    public boolean isWall() {
        return p1.x == p2.x;
    }

    public int getX1() {
        return p1.x;
    }

    public int getX2() {
        return p2.x;
    }

    public int getY1() {
        return p1.y;
    }

    public int getY2() {
        return p2.y;
    }

    public int compareTo(MapleFoothold o) {
        MapleFoothold other = (MapleFoothold) o;
        if (p2.y < other.getY1()) {
            return -1;
        } else if (p1.y > other.getY2()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getId() {
        return id;
    }

    public short getNext() {
        return next;
    }

    public void setNext(short next) {
        this.next = next;
    }

    public short getPrev() {
        return prev;
    }

    public void setPrev(short prev) {
        this.prev = prev;
    }
}
