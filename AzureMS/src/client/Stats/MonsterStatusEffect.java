package client.Stats;

import java.util.Map;
import java.util.Map.Entry;

import client.Skills.ISkill;
import server.LifeEntity.MobEntity.MobSkill;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster.PoisonTask;
import tools.ArrayMap;

public class MonsterStatusEffect {

    private final Map<MonsterStatus, Integer> stati;
    private final MobSkill mobskill;
    private final ISkill skill;
    private final boolean monsterSkill;
    private int ownerid;
    private int skillLevel;
    private int v1;
    private int v2;
    private Runnable cancelEffect;
    private PoisonTask poison;
    private long endTime;

    public MonsterStatusEffect(final Map<MonsterStatus, Integer> stati, final ISkill skillId, final int skillLevel,
            final MobSkill mobskill, final boolean monsterSkill) {
        this.stati = new ArrayMap<MonsterStatus, Integer>(stati);
        this.skill = skillId;
        this.monsterSkill = monsterSkill;
        this.mobskill = mobskill;
        this.skillLevel = skillLevel;
    }

    public final Map<MonsterStatus, Integer> getStati() {
        return stati;
    }

    public Entry<MonsterStatus, Integer> getStati(int stat1, int stat_) {
        for (Entry<MonsterStatus, Integer> stat : stati.entrySet()) {
            if (stat.getKey().getValue(stat_) == stat1) {
                return stat;
            }
        }
        return null;
    }

    public final int getSkillLevel() {
        return skillLevel;
    }

    public final Integer setValue(final MonsterStatus status, final Integer newVal) {
        return stati.put(status, newVal);
    }

    public final ISkill getSkill() {
        return skill;
    }

    public final int getSkillId() {
        switch (skill.getId()) {
            case 2201008:
            case 2211010:
                return 2211002;
            case 2121055:
                return 2121052;
            default:
                return skill.getId();
        }
    }

    public final MobSkill getMobSkill() {
        return mobskill;
    }

    public final boolean isMonsterSkill() {
        return monsterSkill;
    }

    public final void setOwnerId(final int ownerid) {
        this.ownerid = ownerid;
    }

    public final int getOwnerId() {
        return ownerid;
    }

    public final void removeActiveStatus(final MonsterStatus stat) {
        stati.remove(stat);
    }

    public final void setPoison(final PoisonTask poison) {
        this.poison = poison;
    }

    public final PoisonTask getPoison() {
        return this.poison;
    }

    public final void setEndTime(final long endTime) {
        this.endTime = endTime;
    }

    public final long getEndTime() {
        return this.endTime;
    }

    public final void setV(int i, int j) {
        if (i == 1) {
            v1 = j;
        } else if (i == 2) {
            v2 = j;
        }
    }

    public final int getV(int i) {
        return i == 1 ? v1 : v2;
    }

    public final void setCancelEffect(final Runnable cancelEffect) {
        this.cancelEffect = cancelEffect;
    }

    public final void CancelEffect() {
        this.cancelEffect.run();
    }
}
