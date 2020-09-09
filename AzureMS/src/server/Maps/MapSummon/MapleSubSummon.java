package server.Maps.MapSummon;

import java.awt.Point;
import java.lang.ref.WeakReference;

import client.Character.MapleCharacter;
import client.MapleClient;
import server.Maps.MapObject.AnimatedHinaMapObjectExtend;
import server.Maps.MapObject.MapleMapObjectType;

public class MapleSubSummon extends AnimatedHinaMapObjectExtend {

    private int check, cid, value, skillid, time, skillLevel, strange2, strange3;
    private byte value2;
    private Point strange;
    private short secondValue;
    private final WeakReference<MapleCharacter> ownerchr;

    public MapleSubSummon(MapleCharacter owner, short check, int cid, int value, byte value2, Point strange,
            int strange2, short secondValue, int skillid, int skillLevel, int strange3, short time) {
        this.ownerchr = new WeakReference<>(owner);
        this.check = check;
        this.cid = cid;
        this.value = value;
        this.value2 = value2;
        this.strange = strange;
        this.strange2 = strange2;
        this.secondValue = secondValue;
        this.skillid = skillid;
        this.skillLevel = skillLevel;
        this.strange3 = strange3;
        this.time = time;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.SUBSUMMON;
    }

    @Override
    public void sendSpawnData(final MapleClient client) {

    }

    @Override
    public void sendDestroyData(MapleClient client) {
        //
    }

    public MapleCharacter getOwnerChr() {
        return ownerchr.get();
    }

    public int getCheck() {
        return check;
    }

    public int getCid() {
        return cid;
    }

    public int getValue() {
        return value;
    }

    public byte getValue2() {
        return value2;
    }

    public Point getStrange() {
        return strange;
    }

    public int getStrange2() {
        return strange2;
    }

    public int getStrange3() {
        return strange3;
    }

    public short getSecondValue() {
        return secondValue;
    }

    public int getTime() {
        return time;
    }

    public int getSkillId() {
        return skillid;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

}
