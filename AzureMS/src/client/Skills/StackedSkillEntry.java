package client.Skills;

public class StackedSkillEntry {

    private int skillid, value, bufflength;
    private long time;

    public StackedSkillEntry(int a, int b, long c, int d) {
        this.skillid = a;
        this.value = b;
        this.time = c;
        this.bufflength = d;
    }

    public int getSkillId() {
        return skillid;
    }

    public int getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }

    public int getBuffLength() {
        return bufflength;
    }
}
