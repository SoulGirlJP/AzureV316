package client.Skills;

public class SkillEntry {

    public final byte skillevel;
    public final byte masterlevel;
    public final long expiration;

   
    public SkillEntry(final byte skillevel, final byte masterlevel, final long expiration) {
        this.skillevel = skillevel;
        this.masterlevel = masterlevel;
        this.expiration = expiration;
    }
}
