package client.Stats;

import java.util.concurrent.ScheduledFuture;

import client.Skills.SkillStatEffect;

public class BuffStatsValueHolder {

    public SkillStatEffect effect;
    public long startTime;
    public int value;
    public ScheduledFuture<?> schedule;

    public BuffStatsValueHolder(SkillStatEffect effect, long startTime, ScheduledFuture<?> schedule, int value) {
        super();
        this.effect = effect;
        this.startTime = startTime;
        this.value = value;
        this.schedule = schedule;
    }
}
