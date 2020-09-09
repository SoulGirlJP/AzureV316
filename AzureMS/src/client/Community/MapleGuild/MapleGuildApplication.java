package client.Community.MapleGuild;

import client.Character.MapleCharacter;

public class MapleGuildApplication {

    private int id, job, level;
    private String name;

    public MapleGuildApplication(final MapleCharacter c) {
        id = c.getId();
        job = c.getJob();
        level = c.getLevel();
        name = c.getName();
    }

    public MapleGuildApplication(final int id, final short level, final String name, final int job) {
        this.level = level;
        this.id = id;
        this.name = name;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public int getJob() {
        return job;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
