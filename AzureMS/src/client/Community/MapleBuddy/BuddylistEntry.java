package client.Community.MapleBuddy;

public class BuddylistEntry {

    private String name, group;
    private int cid, channel = -1, level, job;
    private boolean visible;

    public BuddylistEntry(String name, int characterId, String group, int channel, boolean visible, int level, int job) {
        super();
        this.name = name;
        this.cid = characterId;
        this.group = group;
        this.channel = channel;
        this.visible = visible;
        this.level = level;
        this.job = job;
    }

    /**
     * @return the channel the character is on. If the character is offline
     * returns -1.
     */
    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isOnline() {
        return channel >= 0;
    }

    public void setOffline() {
        channel = -1;
    }

    public String getName() {
        return name;
    }

    public int getCharacterId() {
        return cid;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getGroup() {
        return group;
    }

    public int getLevel() {
        return level;
    }

    public int getJob() {
        return job;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cid;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BuddylistEntry other = (BuddylistEntry) obj;
        if (cid != other.cid) {
            return false;
        }
        return true;
    }
}
