package server.Quests;

import server.Items.RandomRewards;
import tools.RandomStream.Randomizer;

class QuestItem {

    private int itemid;
    private int count;
    private int period;
    private int gender;
    private int job;
    private int jobEx;
    private int prop;

    public QuestItem(int itemid, int count, int period, int gender, int job, int jobEx, int prop) {
        if (RandomRewards.getTenPercent().contains(itemid)) {
            count += Randomizer.nextInt(3);
        }
        this.itemid = itemid;
        this.count = count;
        this.period = period;
        this.gender = gender;
        this.job = job;
        this.jobEx = jobEx;
        this.prop = prop;
    }

    public int getItemId() {
        return itemid;
    }

    public int getCount() {
        return count;
    }

    public int getPeriod() {
        return period;
    }

    public int getGender() {
        return gender;
    }

    public int getJob() {
        return job;
    }

    public int getJobEx() {
        return jobEx;
    }

    public int getProp() {
        return prop;
    }
}
