package client.Skills; //Äð

public class CoolDownValueHolder {

    public int skillId;
    public long startTime;
    public long length;

    public CoolDownValueHolder(int skillId, long startTime, long length) {
        super();
        this.skillId = skillId;
        this.startTime = startTime;
        this.length = length;
    }
}
