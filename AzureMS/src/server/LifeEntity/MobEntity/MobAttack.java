package server.LifeEntity.MobEntity;

import java.util.ArrayList;
import java.util.List;

import tools.Pair;

public class MobAttack {

    private final int afterAttack;
    private final int fixAttack;
    private final int action;
    private final int cooltime;
    private final int onlyAfterAttack;
    private final List<Pair<Integer, Integer>> skills = new ArrayList<>();

    public MobAttack(final int action, final int afterAttack, final int fixAttack, final int onlyAfterAttack,
            final int cooltime) {
        this.action = action;
        this.afterAttack = afterAttack;
        this.fixAttack = fixAttack;
        this.onlyAfterAttack = onlyAfterAttack;
        this.cooltime = cooltime;
    }

    public List<Pair<Integer, Integer>> getSkills() {
        return skills;
    }

    public void addSkill(int s, int l) {
        skills.add(new Pair<>(s, l));
    }

    public int getAction() {
        return action;
    }

    public int getAfterAttack() {
        return afterAttack;
    }

    public boolean isOnlyAfterAttack() {
        return onlyAfterAttack == 1;
    }

    public int getFixAttack() {
        return this.fixAttack;
    }

    public int getCoolTime() {
        return cooltime;
    }
}
