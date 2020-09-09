package server.LifeEntity.MobEntity;

import java.util.List;

public class SummonAttackEntry {

    private Integer mob;
    private List<Long> damage;

    public SummonAttackEntry(Integer mob, List<Long> damage) {
        super();
        this.mob = mob;
        this.damage = damage;
    }

    public Integer getMonster() {
        return mob;
    }

    public List<Long> getDamage() {
        return damage;
    }

    public long getTotDamage() {
        long v1 = 0;
        for (long i : damage) {
            v1 += i;
        }
        return v1;
    }
}
