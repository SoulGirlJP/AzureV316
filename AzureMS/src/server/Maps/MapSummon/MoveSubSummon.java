package server.Maps.MapSummon;

import java.lang.ref.WeakReference;

import client.Character.MapleCharacter;

public class MoveSubSummon {

    private int check, skillid, point1, point2;
    private final WeakReference<MapleCharacter> ownerchr;

    public MoveSubSummon(MapleCharacter owner, int check, int skillid, int point1, int point2) {
        this.ownerchr = new WeakReference<>(owner);
        this.check = check;
        this.skillid = skillid;
        this.point1 = point1;
        this.point2 = point2;
    }

    public int getSkillid() {
        return skillid;
    }

    public int getCheck() {
        return check;
    }

    public int getPoint1() {
        return point1;
    }

    public int getPoint2() {
        return point2;
    }

    public MapleCharacter getOwnerChr() {
        return ownerchr.get();
    }
}
