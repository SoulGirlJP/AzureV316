package tools;

import java.util.List;

public class AttackPair {

    public int objectid;
    public List<Pair<Long, Boolean>> attack;
    public byte hitAction;

    public AttackPair(int objectid, List<Pair<Long, Boolean>> attack, byte hitAction) {
        this.objectid = objectid;
        this.attack = attack;
        this.hitAction = hitAction;
    }
}
