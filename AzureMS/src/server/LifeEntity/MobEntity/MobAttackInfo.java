package server.LifeEntity.MobEntity;

public class MobAttackInfo {

    private int mobId, attackId;
    private boolean isDeadlyAttack;
    private int mpBurn, mpCon;
    private int diseaseSkill, diseaseLevel;

    public MobAttackInfo(int mobId, int attackId) {
        this.mobId = mobId;
        this.attackId = attackId;
    }

    public void setDeadlyAttack(boolean isDeadlyAttack) {
        this.isDeadlyAttack = isDeadlyAttack;
    }

    public boolean isDeadlyAttack() {
        return isDeadlyAttack;
    }

    public void setMpBurn(int mpBurn) {
        this.mpBurn = mpBurn;
    }

    public int getMpBurn() {
        return mpBurn;
    }

    public void setDiseaseSkill(int diseaseSkill) {
        this.diseaseSkill = diseaseSkill;
    }

    public int getDiseaseSkill() {
        return diseaseSkill;
    }

    public void setDiseaseLevel(int diseaseLevel) {
        this.diseaseLevel = diseaseLevel;
    }

    public int getDiseaseLevel() {
        return diseaseLevel;
    }

    public void setMpCon(int mpCon) {
        this.mpCon = mpCon;
    }

    public int getMpCon() {
        return mpCon;
    }
}
