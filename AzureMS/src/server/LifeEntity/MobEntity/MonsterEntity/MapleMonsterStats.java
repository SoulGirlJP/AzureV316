package server.LifeEntity.MobEntity.MonsterEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.LifeEntity.MobEntity.BanishInfo;
import server.LifeEntity.MobEntity.Element;
import server.LifeEntity.MobEntity.ElementalEffectiveness;
import server.LifeEntity.MobEntity.MobAttack;

import tools.Pair;

public class MapleMonsterStats {

    private byte cp, point, selfDestruction_action, tagColor, tagBgColor, rareItemDropLevel, HPDisplayType;
    private short level, PhysicalDefense, MagicDefense, eva;
    private int exp, mp, removeAfter, buffToGive, fixedDamage, selfDestruction_hp, charismaEXP, link, pad, mad, acc,
            pushed, speed;
    private int smartPhase = -1;
    private long hp, finalmaxhp;
    private boolean boss, undead, ffaLoot, firstAttack, isExplosiveReward, mobile, fly, onlyNormalAttack, friendly,
            changeable, invincible;
    private boolean isRealBoss = false;
    private String name;
    private Map<Element, ElementalEffectiveness> resistance = new HashMap<Element, ElementalEffectiveness>();
    private List<Integer> revives = Collections.emptyList();
    private final List<Pair<Integer, Integer>> skills = new ArrayList<>();
    private final Map<Pair<Integer, Integer>, Integer> afterAttack = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Integer> skillAfter = new HashMap<>();
    private final List<MobAttack> attacks = new ArrayList<>();
    private BanishInfo banish;
    private int width, height;
    private byte PDRate, MDRate;
    private int wp;
    private int PhysicalAttack;
    private int MagicAttack;
    private boolean skeleton;
    private List<String> hitParts = new ArrayList<>();

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isRealBoss() {
        return isRealBoss;
    }

    public void setRealBoss(boolean a) {
        this.isRealBoss = a;
    }

    public long getFinalMaxHP() {
        return finalmaxhp;
    }

    public long getHp() {
        return hp;
    }

    public final void setMagicAttack(final int MagicAttack) {
        this.MagicAttack = MagicAttack;
    }

    public final int getMagicAttack() {
        return MagicAttack;
    }

    public void setFinalMaxHP(long fmhp) {
        this.finalmaxhp = fmhp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public boolean isChangeableMob() {
        return changeable;
    }

    public void setChangeableMob(boolean a) {
        this.changeable = a;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setPhysicalAttack(final int PhysicalAttack) {
        this.PhysicalAttack = PhysicalAttack;
    }

    public int getPhysicalAttack() {
        return PhysicalAttack;
    }

    public void setInvincible(boolean a) {
        this.invincible = a;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public void setPad(int pad) {
        this.pad = pad;
    }

    public void setMad(int mad) {
        this.mad = mad;
    }

    public int getPad() {
        return pad;
    }

    public int getMad() {
        return mad;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public int getAcc() {
        return acc;
    }

    public void setPushed(int pushed) {
        this.pushed = pushed;
    }

    public int getPushed() {
        return pushed;
    }

    public int getLink() {
        return link;
    }

    public int getSmartPhase() {
        return smartPhase;
    }

    /*
	 * public void setSmartPhase(int smartPhase) { this.smartPhase = smartPhase; }
     */
    public void setSelfD(byte selfDestruction_action) {
        this.selfDestruction_action = selfDestruction_action;
    }

    public byte getSelfD() {
        return selfDestruction_action;
    }

    public void setSelfDHP(int selfDestruction_hp) {
        this.selfDestruction_hp = selfDestruction_hp;
    }

    public int getSelfDHp() {
        return selfDestruction_hp;
    }

    public void setFixedDamage(int damage) {
        this.fixedDamage = damage;
    }

    public int getFixedDamage() {
        return fixedDamage;
    }

    public void setPhysicalDefense(final short PhysicalDefense) {
        this.PhysicalDefense = PhysicalDefense;
    }

    public short getPhysicalDefense() {
        return PhysicalDefense;
    }

    public final void setMagicDefense(final short MagicDefense) {
        this.MagicDefense = MagicDefense;
    }

    public final short getMagicDefense() {
        return MagicDefense;
    }

    public final void setEva(final short eva) {
        this.eva = eva;
    }

    public final short getEva() {
        return eva;
    }

    public void setOnlyNormalAttack(boolean onlyNormalAttack) {
        this.onlyNormalAttack = onlyNormalAttack;
    }

    public boolean getOnlyNoramlAttack() {
        return onlyNormalAttack;
    }

    public BanishInfo getBanishInfo() {
        return banish;
    }

    public void setBanishInfo(BanishInfo banish) {
        this.banish = banish;
    }

    public int getRemoveAfter() {
        return removeAfter;
    }

    public void setRemoveAfter(int removeAfter) {
        this.removeAfter = removeAfter;
    }

    public byte getrareItemDropLevel() {
        return rareItemDropLevel;
    }

    public void setrareItemDropLevel(byte rareItemDropLevel) {
        this.rareItemDropLevel = rareItemDropLevel;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setSkeleton(boolean skeleton) {
        this.skeleton = skeleton;
    }

    public boolean isSkeleton() {
        return boss;
    }

    public void setFfaLoot(boolean ffaLoot) {
        this.ffaLoot = ffaLoot;
    }

    public boolean isFfaLoot() {
        return ffaLoot;
    }

    public void setExplosiveReward(boolean isExplosiveReward) {
        this.isExplosiveReward = isExplosiveReward;
    }

    public boolean isExplosiveReward() {
        return isExplosiveReward;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean getMobile() {
        return mobile;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public boolean getFly() {
        return fly;
    }

    public List<Integer> getRevives() {
        return revives;
    }

    public void setRevives(List<Integer> revives) {
        this.revives = revives;
    }

    public void setUndead(boolean undead) {
        this.undead = undead;
    }

    public boolean getUndead() {
        return undead;
    }

    public void setEffectiveness(Element e, ElementalEffectiveness ee) {
        resistance.put(e, ee);
    }

    public void removeEffectiveness(Element e) {
        resistance.remove(e);
    }

    public ElementalEffectiveness getEffectiveness(Element e) {
        ElementalEffectiveness elementalEffectiveness = resistance.get(e);
        if (elementalEffectiveness == null) {
            return ElementalEffectiveness.NORMAL;
        } else {
            return elementalEffectiveness;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharismaEXP(int ee) {
        this.charismaEXP = ee;
    }

    public int getCharismaEXP() {
        return charismaEXP;
    }

    public void setWP(int wp) {
        this.wp = wp;
    }

    public int getWP() {
        return wp;
    }

    public byte getTagColor() {
        return tagColor;
    }

    public void setTagColor(int tagColor) {
        this.tagColor = (byte) tagColor;
    }

    public byte getTagBgColor() {
        return tagBgColor;
    }

    public void setTagBgColor(int tagBgColor) {
        this.tagBgColor = (byte) tagBgColor;
    }

    public void setSkills(List<Pair<Integer, Integer>> skill_) {
        for (Pair<Integer, Integer> skill : skill_) {
            skills.add(skill);
        }
    }

    public List<Pair<Integer, Integer>> getSkills() {
        return Collections.unmodifiableList(this.skills);
    }

    public void setAttacks(List<MobAttack> attack_) {
        for (MobAttack attack : attack_) {
            attacks.add(attack);
        }
    }

    public List<MobAttack> getAttacks() {
        return Collections.unmodifiableList(this.attacks);
    }

    public byte getNoSkills() {
        return (byte) skills.size();
    }

    public boolean hasSkill(int skillId, int level) {
        for (Pair<Integer, Integer> skill : skills) {
            if (skill.getLeft() == skillId && skill.getRight() == level) {
                return true;
            }
        }
        return false;
    }

    public void setFirstAttack(boolean firstAttack) {
        this.firstAttack = firstAttack;
    }

    public boolean isFirstAttack() {
        return firstAttack;
    }

    public void setCP(byte cp) {
        this.cp = cp;
    }

    public byte getCP() {
        return cp;
    }

    public int getPoint() {
        return point;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setBuffToGive(int buff) {
        this.buffToGive = buff;
    }

    public int getBuffToGive() {
        return buffToGive;
    }

    public byte getHPDisplayType() {
        return HPDisplayType;
    }

    public void setHPDisplayType(byte HPDisplayType) {
        this.HPDisplayType = HPDisplayType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPDRate(byte selfDestruction) {
        this.PDRate = selfDestruction;
    }

    public byte getPDRate() {// MDRate
        return this.PDRate;
    }

    public void setMDRate(byte selfDestruction) {
        this.MDRate = selfDestruction;
    }

    public byte getMDRate() {// MDRate
        return this.MDRate;
    }

    public Integer getAfterAttack(int skillid, int skilllevel) {
        return afterAttack.get(new Pair<>(skillid, skilllevel));
    }

    public void addAfterAttack(int skillid, int skilllevel, int afterAttack) {
        this.afterAttack.put(new Pair<>(skillid, skilllevel), afterAttack);
    }

    public Integer getSkillAfter(int skillid, int skilllevel) {
        return skillAfter.get(new Pair<>(skillid, skilllevel));
    }

    public void addSkillAfter(int skillid, int skilllevel, int afterAttack) {
        this.skillAfter.put(new Pair<>(skillid, skilllevel), afterAttack);
    }

    public List<String> getHitParts() {
        return Collections.unmodifiableList(hitParts);
    }

    public void addHitPart(String hitPart) {
        hitParts.add(hitPart);
    }
}
