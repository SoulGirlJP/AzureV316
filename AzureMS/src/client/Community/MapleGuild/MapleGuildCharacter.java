package client.Community.MapleGuild;

import client.Character.MapleCharacter;

public class MapleGuildCharacter { // alias for a character

    private byte channel;
    private short level;
    private int id, jobid, guildrank, guildid, allianceRank;
    private boolean online;
    private String name;
    MapleCharacter chr;

    public MapleGuildCharacter(final MapleCharacter c) {
	name = c.getName();
	level = (short) c.getLevel();
	id = c.getId();
	channel = (byte) c.getClient().getChannel();
	jobid = c.getJob();
	guildrank = c.getGuildRank();
	guildid = c.getGuildId();
        allianceRank = c.getAllianceRank();
	online = true;
        chr = c;
    }

    // or we could just read from the database
    public MapleGuildCharacter(final int id, final short lv, final String name, final byte channel, final int job, final int rank, final int gid, final boolean on, final int allianceRank) {
	this.level = lv;
	this.id = id;
	this.name = name;
	if (on) {
	    this.channel = channel;
	}
	this.jobid = job;
	this.online = on;
	this.guildrank = rank;
	this.guildid = gid;
        this.allianceRank = allianceRank;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(short l) {
	level = l;
    }

    public int getId() {
	return id;
    }

    public void warp(int mapId) {
        chr.warp(mapId);
    }
    public void setChannel(byte ch) {
	channel = ch;
    }

    public int getChannel() {
	return channel;
    }

    public int getJobId() {
	return jobid;
    }

    public void setJobId(int job) {
	jobid = job;
    }

    public int getGuildId() {
	return guildid;
    }

    public void setGuildId(int gid) {
	guildid = gid;
    }

    public void setGuildRank(int rank) {
	guildrank = rank;
    }

    public int getGuildRank() {
	return guildrank;
    }

    public boolean isOnline() {
	return online;
    }

    public String getName() {
	return name;
    }

    public void setOnline(boolean f) {
	online = f;
    }
    
    public void setAllianceRank(byte rank) {
        allianceRank = rank;
    }

    public int getAllianceRank() {
        return allianceRank;
    }
}
