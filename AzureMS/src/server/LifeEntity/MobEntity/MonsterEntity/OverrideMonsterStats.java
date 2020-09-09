package server.LifeEntity.MobEntity.MonsterEntity;

public class OverrideMonsterStats {

    public long hp, finalmaxhp;
    public int mp, exp;
    public int pad, mad, speed, acc, pushed;
    public short PhysicalDefense, MagicDefense, eva, level;
    public boolean firstAttack;

    public OverrideMonsterStats() {
        hp = 1;
        exp = 0;
        mp = 0;
    }

    public OverrideMonsterStats(long hp, int mp, int exp, boolean change) {
        this.hp = hp;
        this.mp = mp;
        this.exp = exp;
    }

    public OverrideMonsterStats(long hp, int mp, int exp) {
        this(hp, mp, exp, true);
    }

    public int getExp() {
        return exp;
    }

    public void setOExp(int exp) {
        this.exp = exp;
    }

    public long getHp() {
        return hp;
    }

    public void setOHp(long hp) {
        this.hp = hp;
    }

    public long getfinalMaxHp() {
        return finalmaxhp;
    }

    public int getMp() {
        return mp;
    }

    public void setOMp(int mp) {
        this.mp = mp;
    }

    public int getPad() {
        return pad;
    }

    public void setOPad(int pad) {
        this.pad = pad;
    }

    public int getMad() {
        return mad;
    }

    public void setOMad(int mad) {
        this.mad = mad;
    }

    public short getPhysicalDefense() {
        return this.PhysicalDefense;
    }

    public void setOPhysicalDefense(short PhysicalDefense) {
        this.PhysicalDefense = PhysicalDefense;
    }

    public short getMagicDefense() {
        return this.MagicDefense;
    }

    public void setOMagicDefense(short MagicDefense) {
        this.MagicDefense = MagicDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setOSpeed(int speed) {
        this.speed = speed;
    }

    public int getAcc() {
        return this.acc;
    }

    public void setOAcc(int acc) {
        this.acc = acc;
    }

    public short getEva() {
        return this.eva;
    }

    public void setOEva(short eva) {
        this.eva = eva;
    }

    public int getPushed() {
        return this.pushed;
    }

    public void setOPushed(int pushed) {
        this.pushed = pushed;
    }

    public short getLevel() {
        return this.level;
    }

    public void setOLevel(short level) {
        this.level = level;
    }

    public boolean isFirstAttack() {
        return firstAttack;
    }

    public void setOFirstAttack(boolean firstAttack) {
        this.firstAttack = firstAttack;
    }
}
