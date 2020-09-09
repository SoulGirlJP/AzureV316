package server.Items;

import java.util.ArrayList;
import java.util.List;

import tools.Pair;
import tools.Triple;

public class MapleProfessionRecipeEntry {

    public List<Pair<Integer, Integer>> recipe = new ArrayList<Pair<Integer, Integer>>();
    public List<Triple<Integer, Integer, Integer>> target = new ArrayList<Triple<Integer, Integer, Integer>>();
    private int reqSkillLevel, reqSkillProficiency, incSkillProficiency, incFatigability, period, needOpenItem;

    public int getReqSkillLevel() {
        return reqSkillLevel;
    }

    public int getReqSkillProficiency() {
        return reqSkillProficiency;
    }

    public int getIncSkillProficiency(int playerLvl) {
        if (incSkillProficiency == 0) {
            incSkillProficiency = (((incFatigability * 20) - (reqSkillLevel - playerLvl) * 2));
        }
        return incSkillProficiency;
    }

    public int getIncFatigability() {
        return incFatigability;
    }

    public int getNeedOpenItem() {
        return needOpenItem;
    }

    public int getPeriod() {
        return period;
    }

    public List<Pair<Integer, Integer>> getRecipe() {
        return recipe;
    }

    public List<Triple<Integer, Integer, Integer>> getTargets() {
        return target;
    }

    public MapleProfessionRecipeEntry(int reqSkillLevel, int reqSkillProficiency, int incSkillProficiency, int incFatigability, int needOpenItem, int period) {
        this.reqSkillLevel = reqSkillLevel;
        this.reqSkillProficiency = reqSkillProficiency;
        this.incSkillProficiency = incSkillProficiency;
        this.incFatigability = incFatigability;
        this.needOpenItem = needOpenItem;
        this.period = period;
    }
}
