package launcher.Utility.MapleHolders;

import client.Skills.SkillStatEffect;

public class MapleBuffValueHolder {

    public long startTime;
    public SkillStatEffect effect;

    public MapleBuffValueHolder(final long startTime, final SkillStatEffect effect) {
        this.startTime = startTime;
        this.effect = effect;
    }
}
