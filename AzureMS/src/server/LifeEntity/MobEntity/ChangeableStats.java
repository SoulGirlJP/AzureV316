package server.LifeEntity.MobEntity;

import constants.GameConstants;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonsterStats;
import server.LifeEntity.MobEntity.MonsterEntity.OverrideMonsterStats;

public class ChangeableStats extends OverrideMonsterStats {

    public int watk, matk, acc, eva, PDRate, MDRate, pushed, speed, level;

    public ChangeableStats(MapleMonsterStats stats, OverrideMonsterStats ostats) {
        hp = ostats.getHp();
        exp = ostats.getExp();
        mp = ostats.getMp();
        watk = stats.getPhysicalAttack();
        matk = stats.getMagicAttack();
        acc = stats.getAcc();
        eva = stats.getEva();
        PDRate = stats.getPDRate();
        MDRate = stats.getMDRate();
        pushed = stats.getPushed();
        speed = stats.getSpeed();
        level = stats.getLevel();
    }

    public ChangeableStats(MapleMonsterStats stats, int newLevel, boolean pqMob) { // here we go i think
        final double mod = (double) newLevel / (double) stats.getLevel();
        final double hpRatio = (double) stats.getHp() / (double) stats.getExp();
        final double pqMod = (pqMob ? 1.5 : 1.0); // god damn
        hp = (long) Math
                .round((!stats.isBoss() ? GameConstants.getMonsterHP(newLevel) : (stats.getHp() * mod)) * pqMod); // right
        // here
        // lol
        exp = (int) Math.round((!stats.isBoss() ? (GameConstants.getMonsterHP(newLevel) / hpRatio) : (stats.getExp())));
        mp = (int) Math.round(stats.getMp() * mod * pqMod);
        watk = (int) Math.round(stats.getPhysicalAttack() * mod);
        matk = (int) Math.round(stats.getMagicAttack() * mod);
        acc = (int) Math.round(stats.getAcc() + Math.max(0, newLevel - stats.getLevel()) * 2);
        eva = (int) Math.round(stats.getEva() + Math.max(0, newLevel - stats.getLevel()));
        PDRate = Math.min(stats.isBoss() ? 30 : 20, (int) Math.round(stats.getPDRate() * mod));
        MDRate = Math.min(stats.isBoss() ? 30 : 20, (int) Math.round(stats.getMDRate() * mod));
        pushed = (int) Math.round(stats.getPushed() * mod);
        speed = (int) Math.round(stats.getSpeed() * mod);
        level = 150;
        // exp = exp * level;
    }
}
