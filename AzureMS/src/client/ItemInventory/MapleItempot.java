package client.ItemInventory;

public class MapleItempot {

    private int lifeid;
    private int slot;
    private int level, status;
    private int fullness, closeness, incCloseLeft;
    private long startTime;
    private long sleepTime;
    private int maxfull;
    private int maxclose;
    private int incClose;
    private int feedInterval;
    private long lastfeedtime;
    private int ownerid;

    public MapleItempot(int lifeid, int slot, int owner, long d) {
        this.lifeid = lifeid;
        this.slot = (byte) slot;
        this.level = 1;
        this.status = 1;
        this.startTime = d;
        this.fullness = 10;
        this.closeness = 10;
        this.maxclose = 100;
        this.maxfull = 100;
        this.feedInterval = 10;
        this.incClose = 2;
        this.incCloseLeft = 2;
        this.sleepTime = System.currentTimeMillis();
        this.lastfeedtime = System.currentTimeMillis();
        this.ownerid = owner;
    }

    public MapleItempot(int lifeid, int slot, int owner) {
        this.lifeid = lifeid;
        this.slot = (byte) slot;
        this.level = 1;
        this.status = 1;
        this.startTime = System.currentTimeMillis();
        this.fullness = 10;
        this.closeness = 10;
        this.maxclose = 100;
        this.maxfull = 100;
        this.feedInterval = 10;
        this.incClose = 2;
        this.incCloseLeft = 2;
        this.sleepTime = System.currentTimeMillis();
        this.lastfeedtime = System.currentTimeMillis();
        this.ownerid = owner;
    }

    public void setSleepTime(long time) {
        this.sleepTime = time;
    }

    public void setLastFeedTime(long time) {
        this.lastfeedtime = time;
    }

    public int getOwnerId() {
        return this.ownerid;
    }

    public int getMaxClose() {
        return this.maxclose;
    }

    public int getMaxFull() {
        return this.maxfull;
    }

    public long getSleepTime() {
        return this.sleepTime;

    }

    public long getLastFeedTime() {
        return this.lastfeedtime;
    }

    public int getSlot() {
        return this.slot;
    }

    public int getLifeId() {
        return this.lifeid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setStatus(int status) {
        this.status = (byte) status;
    }

    public int getStatus() {
        return this.status;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setFullness(int full) {
        this.fullness = full;
    }

    public void gainFullness(int gain) {
        setFullness(getFullness() + gain);
    }

    public int getFullness() {
        return this.fullness;
    }

    public void setCloseness(int close) {
        this.closeness = close;
    }

    public void gainCloseness(int gain) {
        setCloseness(getCloseness() + gain);
    }

    public int getCloseness() {
        return this.closeness;
    }

    public void setFeedInterval(int time) {
        this.feedInterval = time;
    }

    public int getFeedInterval() {
        return this.feedInterval;
    }

    public void setIncClose(int close) {
        this.incClose = close;
    }

    public int getIncClose() {
        return this.incClose;
    }

    public void setIncCloseLeft(int left) {
        this.incCloseLeft = left;
    }

    public int getIncCloseLeft() {
        return this.incCloseLeft;
    }

    public void die() {
        setLevel(0);
    }
}
