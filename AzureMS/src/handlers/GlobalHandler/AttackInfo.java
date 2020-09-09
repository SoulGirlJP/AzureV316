package handlers.GlobalHandler;

import java.awt.Point;
import java.util.List;

import client.Character.MapleCharacter;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import constants.GameConstants;
import tools.AttackPair;

public class AttackInfo {

    public byte skillLevel, tbyte, animation, speed, AOE, csstar, hits, targets, slot, assist, flag1, flag2;
    public int display, item, skill, charge, lastAttackTickCount;
    public Point position;
    public Point plusPosition;
    public List<AttackPair> allDamage;
    public byte nMoveAction = -1;
    public byte bShowFixedDamage = 0;

    public final SkillStatEffect getAttackEffect(final MapleCharacter chr, int skillLevel, final ISkill skill_) {
        if (skillLevel == 0) {
            return null;
        }
        if (GameConstants.isLinkedAttackSkill(skill)) {
            final ISkill skillLink = SkillFactory.getSkill(skill);
            return skillLink.getEffect(skillLevel);
        }
        return skill_.getEffect(skillLevel);
    }
}
