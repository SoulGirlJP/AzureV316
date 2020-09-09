package client;

public class MapleReward {

    private int id, type, item, mp, meso, exp;
    private long from, to;
    private String desc;

    public MapleReward(int id, long start, long end, int type, int item, int mp, int meso, int exp, String desc) {
        this.id = id;
        this.from = start;
        this.to = end;
        this.type = type;
        this.item = item;
        this.mp = mp;
        this.meso = meso;
        this.desc = desc;
    }

    public void setFromDate(long from) {
        this.from = from;
    }

    public void setToDate(long to) {
        this.to = to;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public void setMaplePoints(int mp) {
        this.mp = mp;
    }

    public void setMeso(int meso) {
        this.meso = meso;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public long getReceiveDate() {
        return from;
    }

    public long getExpireDate() {
        return to;
    }

    public int getType() {
        return type;
    }

    public int getItem() {
        return item;
    }

    public int getMaplePoints() {
        return mp;
    }

    public int getMeso() {
        return meso;
    }

    public int getExp() {
        return exp;
    }

    public String getDesc() {
        return desc;
    }
}
