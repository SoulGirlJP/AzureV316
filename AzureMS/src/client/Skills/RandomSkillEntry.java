/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Skills;

import java.util.List;
import tools.Pair;

/**
 *
 * @author ¶Ç±ø
 */
public class RandomSkillEntry {
    private int skillid, prob;
    private List<Pair<Integer, Integer>> skillList;
    
    public RandomSkillEntry(int skillid, int prob, List<Pair<Integer, Integer>> skillList) {
        this.skillid = skillid;
        this.prob = prob;
        this.skillList = skillList;
    }
    
    public int getSkillId() {
        return skillid;
    }
    
    public int getProb() {
        return prob;
    }
    
    public List<Pair<Integer, Integer>> getSkillList() {
        return skillList;        
    }
    
}
