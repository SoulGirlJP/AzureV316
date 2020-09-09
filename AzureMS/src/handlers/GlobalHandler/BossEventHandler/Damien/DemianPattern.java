package handlers.GlobalHandler.BossEventHandler.Damien;

import java.util.ArrayList;
import java.util.List;

public class DemianPattern {

    public byte v1;
    public short v2, v3, v4;
    public int v5, v6, v7;
    public byte v8, v9;
    public int v10, v11;

    public static final List<DemianPattern> patternList = new ArrayList<>();

    public static void initDemianPattern() {
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 0, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 895, -300));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 1, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 410, 100));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 2, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 40, -200));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 3, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, -60, 100));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 4, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 360, -150));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 5, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 810, 50));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 6, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 1440, -200));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 7, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 1690, -50));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 8, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 1540, 100));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 9, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 840, -150));
        patternList.add(new DemianPattern((byte) 2, (short) 4, (short) 10, (short) 35, 500, 0, 0, (byte) 0, (byte) 0, 840, -150));
        patternList.add(new DemianPattern((byte) 2, (short) 4, (short) 11, (short) 35, 500, 0, 0, (byte) 0, (byte) 0, 840, -150));
        patternList.add(new DemianPattern((byte) 2, (short) 4, (short) 12, (short) 35, 500, 0, 0, (byte) 0, (byte) 0, 840, -150));
        patternList.add(new DemianPattern((byte) 1, (short) 4, (short) 13, (short) 35, 0, 0, 0, (byte) 0, (byte) 0, 895, -300));
        patternList.add(new DemianPattern((byte) 2, (short) 4, (short) 14, (short) 60, 500, 0, 0, (byte) 0, (byte) 0, 840, -150));
        patternList.add(new DemianPattern((byte) 2, (short) 4, (short) 15, (short) 35, 0, 11000, 0, (byte) 1, (byte) 0, 0, 0));
    }

    public DemianPattern(byte v1, short v2, short v3, short v4, int v5, int v6, int v7, byte v8, byte v9, int v10, int v11) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v6 = v6;
        this.v7 = v7;
        this.v8 = v8;
        this.v9 = v9;
        this.v10 = v10;
        this.v11 = v11;
    }
}
