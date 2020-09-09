package server.LifeEntity.MobEntity;

public class BanishInfo {

    private int map;
    private String portal, msg;

    public BanishInfo(String msg, int map, String portal) {
        this.msg = msg;
        this.map = map;
        this.portal = portal;
    }

    public int getMap() {
        return map;
    }

    public String getPortal() {
        return portal;
    }

    public String getMsg() {
        return msg;
    }
}
