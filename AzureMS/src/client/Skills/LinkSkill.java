package client.Skills;

public class LinkSkill {

    private final int bj;
    private final int bk;
    private final int skillid;
    private final int bl;
    private final int bm;
    private final int bn;
    private final long time;

    public LinkSkill(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6,
            long paramLong) {
        this.bj = paramInt1;
        this.bk = paramInt2;
        this.skillid = paramInt3;
        this.bl = paramInt4;
        this.bm = paramInt5;
        this.bn = paramInt6;
        this.time = paramLong;
    }

    public int getAccId() {
        return this.bj;
    }

    public int getRealSkillId() {
        return this.bk;
    }

    public int getSkillId() {
        return this.skillid;
    }

    public int getLinkingCid() {
        return this.bl;
    }

    public int getLinkedCid() {
        return this.bm;
    }

    public int getSkillLevel() {
        return this.bn;
    }

    public long getTime() {
        return this.time;
    }

    public boolean checkInfo(int paramInt1, int paramInt2) {
        return (getSkillId() == paramInt1) && (getLinkingCid() == paramInt2);
    }

    public boolean checkInfo_(int paramInt1, int paramInt2) {
        return (getSkillId() == paramInt1) && (getLinkedCid() == paramInt2);
    }
}
