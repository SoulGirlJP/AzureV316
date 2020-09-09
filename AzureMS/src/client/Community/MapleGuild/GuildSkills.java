package client.Community.MapleGuild;

import java.io.Serializable;

public class GuildSkills implements Serializable {

    public static final long serialVersionUID = 3565477792055301248L;
    public String purchaser, activator;
    public long timestamp;
    public int skillID, level;

    public GuildSkills(final int skillID, final int level, final long timestamp, final String purchaser,
            final String activator) {
        this.timestamp = timestamp;
        this.skillID = skillID;
        this.level = level;
        this.purchaser = purchaser;
        this.activator = activator;
    }

}
