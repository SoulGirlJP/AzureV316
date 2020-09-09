package server.Maps.MapObject;

public abstract class AnimatedHinaMapObjectExtend extends AbstractHinaMapObject implements AnimatedHinaMapObject {
    
    private int stance;

    @Override
    public int getStance() {
        return stance;
    }

    @Override
    public void setStance(int stance) {
        this.stance = stance;
    }

    @Override
    public boolean isFacingLeft() {
        return getStance() % 2 == 1;
    }
    
    public int getFacingDirection() {
        return getStance() % 2;
    }
}
