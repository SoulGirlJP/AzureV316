package server.Maps.MapReactor;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import tools.Pair;

public class MapleReactorStats {

    private byte facingDirection;
    private Point tl;
    private Point br;
    private Map<Byte, Map<Byte, StateData>> stateInfo = new HashMap<Byte, Map<Byte, StateData>>();

    public final void setFacingDirection(final byte facingDirection) {
        this.facingDirection = facingDirection;
    }

    public final byte getFacingDirection() {
        return facingDirection;
    }

    public void setTL(Point tl) {
        this.tl = tl;
    }

    public void setBR(Point br) {
        this.br = br;
    }

    public Point getTL() {
        return tl;
    }

    public Point getBR() {
        return br;
    }

    public int getStateEventSize(byte state) {
        if (stateInfo.get(state) == null) {
            return 0;
        }
        return stateInfo.get(state).size();
    }

    public Map<Byte, StateData> getState(byte stat) {
        return stateInfo.get(stat);
    }

    public void addState(byte state, byte index, int type, Pair<Integer, Integer> reactItem, byte nextState) {
        StateData newState = new StateData(type, reactItem, nextState);
        if (!stateInfo.containsKey(state)) {
            stateInfo.put(state, new HashMap<Byte, StateData>());
        }
        stateInfo.get(state).put(index, newState);
    }

    public byte getNextState(byte state, byte index) {
        if (stateInfo.get(state) == null) {
            return -1;
        }
        StateData nextState = stateInfo.get(state).get(index);
        if (nextState != null) {
            return nextState.getNextState();
        } else {
            return -1;
        }
    }

    public int getType(byte state, byte index) {
        if (stateInfo.get(state) == null) {
            return -1;
        }
        StateData nextState = stateInfo.get(state).get(index);
        if (nextState != null) {
            return nextState.getType();
        } else {
            return -1;
        }
    }

    public Pair<Integer, Integer> getReactItem(byte state, byte index) {
        if (stateInfo.get(state) == null) {
            return null;
        }
        StateData nextState = stateInfo.get(state).get(index);
        if (nextState != null) {
            return nextState.getReactItem();
        } else {
            return null;
        }
    }

    private class StateData {

        private int type;
        private Pair<Integer, Integer> reactItem;
        private byte nextState;

        private StateData(int type, Pair<Integer, Integer> reactItem, byte nextState) {
            this.type = type;
            this.reactItem = reactItem;
            this.nextState = nextState;
        }

        private int getType() {
            return type;
        }

        private byte getNextState() {
            return nextState;
        }

        private Pair<Integer, Integer> getReactItem() {
            return reactItem;
        }
    }
}
