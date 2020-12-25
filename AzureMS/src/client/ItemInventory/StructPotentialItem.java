/*
 * 테스피아 Project
 * ==================================
 * 팬더 spirit_m@nate.com
 * 백호 softwarewithcreative@nate.com
 * ==================================
 *
 */

package client.ItemInventory;

import client.Character.MapleCharacter;

public class StructPotentialItem {

    public byte incSTR, incDEX, incINT, incLUK, incACC, incEVA, incSpeed, incJump,
            incPAD, incMAD, incPDD, incMDD, prop, time, incSTRr, incDEXr, incINTr,
            incLUKr, incMHPr, incMMPr, incACCr, incEVAr, incPADr, incMADr, incPDDr,
            incMDDr, incCr, incDAMr, RecoveryHP, RecoveryMP, HP, MP, level,
            ignoreTargetDEF, ignoreDAM, DAMreflect, mpconReduce, mpRestore,
            incMesoProp, incRewardProp, incAllskill, ignoreDAMr, RecoveryUP,
            incAsrR, incTerR, incCriticaldamageMax, incCriticaldamageMin,
            reduceCooltime, incMaxDamage, incSTRlv, incDEXlv, incINTlv, incLUKlv,
            incEXPr, incPADlv, incMADlv;
    public boolean boss;
    public short incMHP, incMMP, attackType, skillID;
    public int optionType, reqLevel, weight, potentialID; //probably the slot
    public String face, string; //angry, cheers, love, blaze, glitter
    public boolean isAllStat;

    @Override
    public final String toString() {
        final StringBuilder ret = new StringBuilder();
        if(incAsrR > 0)
        {
            ret.append("Status Resistance ");
            ret.append(incAsrR);
            ret.append("%");
            ret.append(" ");
        }
        if(incTerR > 0)
        {
            ret.append("Status Resistance ");
            ret.append(incTerR);
            ret.append("%");
            ret.append(" ");
        }
        if (incMesoProp > 0) {
            ret.append("Meso Drop Rate ");
            ret.append(incMesoProp);
            ret.append("%");
            ret.append(" ");
        }
        if (incRewardProp > 0) {
            ret.append("Item Drop Rate ");
            ret.append(incRewardProp);
            ret.append("%");
            ret.append(" ");
        }
        if (prop > 0) {
            ret.append("Probability(not coded): ");
            ret.append(prop);
            ret.append(" ");
        }
        if (time > 0) {
            ret.append("Duration(not coded): ");
            ret.append(time);
            ret.append(" ");
        }
        if (attackType > 0) {
            ret.append("Attack Type(not coded): ");
            ret.append(attackType);
            ret.append(" ");
        }
        if (incAllskill > 0) {
            ret.append("Gives ALL SKILLS: ");
            ret.append(incAllskill);
            ret.append(" ");
        }
        if (skillID > 0) {
            ret.append("Gives SKILL: ");
            ret.append(skillID);
            ret.append(" ");
        }
        if (boss) {
            ret.append("Boss Damage: ");
        }
        if (face.length() > 0) {
            ret.append("Face Expression: ");
            ret.append(face);
            ret.append(" ");
        }
        if (RecoveryUP > 0) {
            ret.append("Gives Recovery % on potions: ");
            ret.append(RecoveryUP);
            ret.append(" ");
        }
        if (DAMreflect > 0) {
            ret.append("Reflects Damage when Hit: ");
            ret.append(DAMreflect);
            ret.append(" ");
        }
        if (mpconReduce > 0) {
            ret.append("Reduces MP Needed for skills: ");
            ret.append(mpconReduce);
            ret.append(" ");
        }
        if (ignoreTargetDEF > 0) {
            ret.append("Ignore Monster DEF ");
            ret.append(ignoreTargetDEF);
            ret.append("%");
            ret.append(" ");
        }
        if (RecoveryHP > 0) {
            ret.append("Recovers HP: ");
            ret.append(RecoveryHP);
            ret.append(" ");
        }
        if (RecoveryMP > 0) {
            ret.append("Recovers MP: ");
            ret.append(RecoveryMP);
            ret.append(" ");
        }
        if (HP > 0) { //no idea
            ret.append("Recovers HP: ");
            ret.append(HP);
            ret.append(" ");
        }
        if (MP > 0) { //no idea
            ret.append("Recovers MP: ");
            ret.append(MP);
            ret.append(" ");
        }
        if (mpRestore > 0) { //no idea
            ret.append("Recovers MP: ");
            ret.append(mpRestore);
            ret.append(" ");
        }
        if (ignoreDAM > 0) {
            ret.append("Ignores Monster Damage +");
            ret.append(ignoreDAM);
            ret.append(" ");
        }
        if (ignoreDAMr > 0) {
            ret.append("Ignores Monster Damage");
            ret.append(ignoreDAMr);
            ret.append("%");
            ret.append(" ");
        }
        if (incMHP > 0) {
            ret.append("HP +");
            ret.append(incMHP);
            ret.append(" ");
        }
        if (incMMP > 0) {
            ret.append("MP +");
            ret.append(incMMP);
            ret.append(" ");
        }
        if (incMHPr > 0) {
            ret.append("HP ");
            ret.append(incMHPr);
            ret.append("%");
            ret.append(" ");
        }
        if (incMMPr > 0) {
            ret.append("MP ");
            ret.append(incMMPr);
            ret.append("%");
            ret.append(" ");
        }
        if (incSTR > 0 && incLUK <= 0 && incDEX <= 0 && incINT <= 0) {
            ret.append("STR +");
            ret.append(incSTR);
            ret.append(" ");
        }
        if (incDEX > 0 && incLUK <= 0 && incSTR <= 0 && incINT <= 0) {
            ret.append("DEX +");
            ret.append(incDEX);
            ret.append(" ");
        }
        if (incINT > 0 && incLUK <= 0 && incDEX <= 0 && incSTR <= 0) {
            ret.append("INT +");
            ret.append(incINT);
            ret.append(" ");
        }
        if (incLUK > 0 && incSTR <= 0 && incDEX <= 0 && incINT <= 0) {
            ret.append("LUK +");
            ret.append(incLUK);
            ret.append(" ");
        }
        if (incACC > 0) {
            ret.append("ACC +");
            ret.append(incACC);
            ret.append(" ");
        }
        if (incEVA > 0) {
            ret.append("Gives EVA: ");
            ret.append(incEVA);
            ret.append(" ");
        }
        if (incSpeed > 0) {
            ret.append("Speed +");
            ret.append(incSpeed);
            ret.append(" ");
        }
        if (incJump > 0) {
            ret.append("Jump +");
            ret.append(incJump);
            ret.append(" ");
        }
        if (incPAD > 0) {
            ret.append("ATT +");
            ret.append(incPAD);
            ret.append(" ");
        }
        if (incMAD > 0) {
            ret.append("Magic ATT +");
            ret.append(incMAD);
            ret.append(" ");
        }
        if (incPDD > 0 && incPDDr < 0) {
            ret.append("DEF +");
            ret.append(incPDD);
            ret.append(" ");
        }
        if (incMDD > 0) {
            ret.append("Magic DEF +");
            ret.append(incMDD);
            ret.append(" ");
        }
        if (incSTRr > 0 && incLUKr <= 0 && incDEXr <= 0 && incINTr <= 0) {
            ret.append("STR ");
            ret.append(incSTRr);
            ret.append("%");
            ret.append(" ");
        }
        if (incDEXr > 0 && incLUKr <= 0 && incSTRr <= 0 && incINTr <= 0) {
            ret.append("DEX ");
            ret.append(incDEXr);
            ret.append("%");
            ret.append(" ");
        }
        if (incINTr > 0 && incLUKr <= 0 && incDEXr <= 0 && incSTRr <= 0) {
            ret.append("INT ");
            ret.append(incINTr);
            ret.append("%");
            ret.append(" ");
        }
        if (incLUKr > 0 && incSTRr <= 0 && incDEXr <= 0 && incINTr <= 0) {
            ret.append("LUK ");
            ret.append(incLUKr);
            ret.append("%");
            ret.append(" ");
        }
        if (incACCr > 0) {
            ret.append("ACC ");
            ret.append(incACCr);
            ret.append("%");
            ret.append(" ");
        }
        if (incEVAr > 0) {
            ret.append("Gives EVA %: ");
            ret.append(incEVAr);
            ret.append(" ");
        }
        if (incPADr > 0) {
            ret.append("ATT ");
            ret.append(incPADr);
            ret.append("%");
            ret.append(" ");
        }
        if (incMADr > 0) {
            ret.append("Magic ATT ");
            ret.append(incMADr);
            ret.append("%");
            ret.append(" ");
        }
        if (incPDDr > 0) {
            ret.append("DEF ");
            ret.append(incPDDr);
            ret.append("%");
            ret.append(" ");
        }
        if (incMDDr > 0) {
            ret.append("Magic DEF ");
            ret.append(incMDDr);
            ret.append("%");
            ret.append(" ");
        }
        if (incCr > 0) {
            ret.append("Critical ");
            ret.append(incCr);
            ret.append("%");
            ret.append(" ");
        }
        if (incDAMr > 0) {
            ret.append("Total Damage ");
            ret.append(incDAMr);
            ret.append("%");
            ret.append(" ");
        }
        if (level > 0) {
            ret.append("Level: ");
            ret.append(level);
            ret.append(" ");
        }
        if(incSTRr > 0 && incINTr > 0 && incLUKr > 0 && incDEXr > 0)
        {
            isAllStat = true;
            ret.append("All Stat ");
            ret.append(incSTRr);
            ret.append("%");
            ret.append(" ");
        }
        if(incSTR > 0 && incLUK > 0 && incDEX > 0 && incINT > 0)
        {
            ret.append("All Stat +");
            ret.append(incSTR);
            ret.append(" ");
        }
        if(incCriticaldamageMin > 0 || incCriticaldamageMax > 0)
        {
            ret.append("Critical Damage ");
            ret.append(incCriticaldamageMin);
            ret.append("%");
            ret.append(" ");
        }
        return ret.toString();
    }
}
