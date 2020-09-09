package launcher.Utility.MapleHolders;

import client.Stats.DiseaseStats;

public class MapleDiseaseValueHolder {

    public int diseaseid;
    public long startTime;
    public long length;
    public DiseaseStats disease;

    public MapleDiseaseValueHolder(final DiseaseStats disease, final long startTime, final long length) {
        this.disease = disease;
        this.startTime = startTime;
        this.length = length;
    }
}
