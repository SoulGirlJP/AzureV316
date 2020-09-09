package server.Movement;

import java.awt.Point;

public abstract class AbstractLifeMovement implements LifeMovement {

    private Point position;
    private int duration;
    private int newstate;
    private int type;

    public AbstractLifeMovement(int type, Point position, int duration, int newstate) {
        super();
        this.type = type;
        this.position = position;
        this.duration = duration;
        this.newstate = newstate;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getNewstate() {
        return newstate;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
