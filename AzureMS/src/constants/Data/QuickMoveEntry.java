package constants.Data;

public class QuickMoveEntry {

    private String name;
    private String desc;
    private int npcId;
    private int levelLimit;
    private int icon;

    public QuickMoveEntry(String nn, int np, int i, int l, String dd) {
        this.name = nn;
        this.desc = dd;
        this.npcId = np;
        this.levelLimit = l;
        this.icon = i;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getLevelLimit() {
        return levelLimit;
    }

    public int getIcon() {
        return icon;
    }

}
