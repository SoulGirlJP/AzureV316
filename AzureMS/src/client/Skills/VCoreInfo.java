package client.Skills;

import java.util.List;
import tools.Triple;

public class VCoreInfo {

    public int type;
    public String desc;
    public Triple<Integer, Integer, Integer> connect;
    public List<String> jobs;

    public VCoreInfo(final int type, final String desc, final Triple<Integer, Integer, Integer> connect, final List<String> jobs) {
        this.type = type;
        this.desc = desc;
        this.connect = connect;
        this.jobs = jobs;
    }
}
