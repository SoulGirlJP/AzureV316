package client.Skills;

public class HyperSkillEntry {

    public final int skillid;
    public final byte value;

    public HyperSkillEntry(final int skillid, final byte value) {
        this.skillid = skillid;
        this.value = value;
    }

    public int getSkillId() {
        return skillid;
    }

    public byte getHyperValue() {
        return value;
    }
}
