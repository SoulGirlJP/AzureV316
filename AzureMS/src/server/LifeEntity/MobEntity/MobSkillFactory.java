package server.LifeEntity.MobEntity;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;

public class MobSkillFactory {

    private static Map<Pair<Integer, Integer>, MobSkill> mobSkills = new HashMap<Pair<Integer, Integer>, MobSkill>();
    private static MapleDataProvider dataSource = MapleDataProviderFactory.getDataProvider(new File("wz/Skill.wz/MobSkill"));

    public static MobSkill getMobSkill(int skillId, int level) {
        MobSkill ret = mobSkills.get(new Pair<Integer, Integer>(Integer.valueOf(skillId), Integer.valueOf(level)));
        if (ret != null) {
            return ret;
        }

        final MapleData skillData = dataSource.getData(skillId + ".img").getChildByPath("level/" + level);
        if (skillData != null) {
            List<Integer> toSummon = new ArrayList<Integer>();
            for (int i = 0; i > -1; i++) {
                if (skillData.getChildByPath(String.valueOf(i)) == null) {
                    break;
                }
                toSummon.add(Integer.valueOf(MapleDataTool.getInt(skillData.getChildByPath(String.valueOf(i)), 0)));
            }
            final MapleData ltd = skillData.getChildByPath("lt");
            Point lt = null;
            Point rb = null;
            if (ltd != null) {
                lt = (Point) ltd.getData();
                rb = (Point) skillData.getChildByPath("rb").getData();
            }
            ret = new MobSkill(skillId, level);
            ret.addSummons(toSummon);
            ret.setCoolTime(MapleDataTool.getInt("interval", skillData, 0) * 1000);
            ret.setDuration(MapleDataTool.getInt("time", skillData, 0) * 1000);
            ret.setHp(MapleDataTool.getInt("hp", skillData, 100));
            ret.setMpCon(MapleDataTool.getInt(skillData.getChildByPath("mpCon"), 0));
            ret.setSpawnEffect(MapleDataTool.getInt("summonEffect", skillData, 0));
            ret.setX(MapleDataTool.getInt("x", skillData, 1));
            ret.setY(MapleDataTool.getInt("y", skillData, 1));
            ret.setProp(MapleDataTool.getInt("prop", skillData, 100) / 100);
            ret.setLimit((short) MapleDataTool.getInt("limit", skillData, 0));
            ret.setLtRb(lt, rb);
            ret.setSkillAfter(MapleDataTool.getInt("skillAfter", skillData, 0));
            if (skillId == 201) {

                int i = 0;
                int mId = -1;
                while ((mId = MapleDataTool.getInt(String.valueOf(i), skillData, -1)) != -1) {
                    ret.addMonster(mId);
                    i++;
                }
            }
            mobSkills.put(new Pair<Integer, Integer>(Integer.valueOf(skillId), Integer.valueOf(level)), ret);
        }
        return ret;
    }
}
