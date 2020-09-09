package client.Skills;

public class SkillEffectEntry {

    private int skillId;
    private int level;
    private short display;
    private byte speed;

    public SkillEffectEntry(int skillId, int level, short display, byte speed) {
        this.skillId = skillId;
        this.level = level;
        this.display = display;
        this.speed = speed;
    }

    public int getSkillId() {
        return skillId;
    }

    public int getLevel() {
        return level;
    }

    public short getDisplay() {
        return display;
    }

    public byte getSpeed() {
        return speed;
    }

}
