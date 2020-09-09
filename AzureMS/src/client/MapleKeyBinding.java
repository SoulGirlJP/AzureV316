package client;

public class MapleKeyBinding {

    private int type, action;

    public MapleKeyBinding(int type, int action) {
        super();
        this.type = type;
        this.action = action;
    }

    public int getType() {
        return type;
    }

    public int getAction() {
        return action;
    }

    public void setType(int i) {
        this.type = i;
    }

    public void setAction(int i) {
        this.action = i;
    }
}
