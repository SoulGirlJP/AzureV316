/**
 * KOREA Project
 * ==================================
 * KOREA < dookank@nate.com >
 * wild¸Å´ÏÀú < up_set@nate.com >
 * ==================================
 */
package client;

import java.awt.Point;

public class ForceAtom {

    private short delay;
    private int oid;
    private int key;
    private int inc;
    private int count;
    private int firstimpact;
    private int secondimpact;
    private int angle;
    private Point pos;
    private int specialtime;

    public ForceAtom(int oid, int key, int inc, int firstimpact, int secondimpact, int angle, short delay) {
        this.oid = oid;
        this.key = key;
        this.inc = inc;
        this.firstimpact = firstimpact;
        this.secondimpact = secondimpact;
        this.angle = angle;
        this.delay = delay;
        this.pos = new Point(0, 0);
        this.count = 0;
    }

    public ForceAtom(int oid, int key, int inc, int firstimpact, int secondimpact, int angle, short delay, Point pos) {
        this.oid = oid;
        this.key = key;
        this.inc = inc;
        this.firstimpact = firstimpact;
        this.secondimpact = secondimpact;
        this.angle = angle;
        this.delay = delay;
        this.pos = pos;
        this.count = 0;
    }

    public ForceAtom(int oid, int key, int inc, int firstimpact, int secondimpact, int angle, short delay, int time) {
        this.oid = oid;
        this.key = key;
        this.inc = inc;
        this.firstimpact = firstimpact;
        this.secondimpact = secondimpact;
        this.angle = angle;
        this.delay = delay;
        this.pos = new Point(0, 0);
        this.specialtime = time;
        this.count = 0;
    }

    public short getDelay() {
        return delay;
    }

    public void setDelay(short delay) {
        this.delay = delay;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getInc() {
        return inc;
    }

    public void setInc(int inc) {
        this.inc = inc;
    }

    public int getFirstimpact() {
        return firstimpact;
    }

    public void setFirstimpact(int firstimpact) {
        this.firstimpact = firstimpact;
    }

    public int getSecondimpact() {
        return secondimpact;
    }

    public void setSecondimpact(int secondimpact) {
        this.secondimpact = secondimpact;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSpecialTime() {
        return this.specialtime;
    }
}
