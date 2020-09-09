package server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.MonsterCollectionRegion.ClearQuest.ClearQuestSub;
import server.MonsterCollectionRegion.CollectionField;
import server.MonsterCollectionRegion.CollectionField.CollectionGroup;
import server.MonsterCollectionRegion.CollectionField.CollectionGroup.CollectionMob;

public class MonsterCollection {

    private static final MapleDataProvider etcDataWZ = MapleDataProviderFactory.getDataProvider(new File("wz/Etc.wz"));
    private static final MapleData mobCollectionData = etcDataWZ.getData("mobCollection.img");
    private static MonsterCollection _instance = new MonsterCollection();

    public static MonsterCollection getInstance() {
        return _instance;
    }

    private Map<Integer, MonsterCollectionRegion> _mobCollectionData = new HashMap<>();
    private Map<Integer, CollectionMob> _mobCollectionInfo = new HashMap<>();

    public MonsterCollection() {
        int i = 0;

        for (MapleData regionData : mobCollectionData.getChildren()) {
            if ("ExplorationRewardIcon".equals(regionData.getName())
                    || "completeCollectSoundPath".equals(regionData.getName())) {
                continue;
            }

            MonsterCollectionRegion r = new MonsterCollectionRegion();

            r.regionID = Integer.parseInt(regionData.getName());
            r.name = MapleDataTool.getString("info/name", regionData);
            r.recordID = MapleDataTool.getInt("info/recordID", regionData);
            r.rewardID = MapleDataTool.getInt("info/rewardID", regionData);
            r.clearQuest.decl = MapleDataTool.getString("info/clearQuest/decl", regionData, "");

            i = 0;
            if (regionData.getChildByPath("info/clearQuest") != null) {
                MapleData subItemData = null;
                while ((subItemData = regionData.getChildByPath("info/clearQuest")
                        .getChildByPath(Integer.toString(i))) != null) {
                    ClearQuestSub subItem = new ClearQuestSub();

                    subItem.clearCount = MapleDataTool.getInt("clearCount", subItemData);
                    subItem.recordID = MapleDataTool.getInt("recordID", subItemData);
                    subItem.rewardID = MapleDataTool.getInt("rewardID", subItemData);

                    r.clearQuest.clearQuestSub.add(subItem);

                    i++;
                }
            }

            i = 0;
            MapleData fieldData = null;
            while ((fieldData = regionData.getChildByPath(Integer.toString(i))) != null) {
                CollectionField subItem = new CollectionField();

                subItem.sessionID = Integer.parseInt(fieldData.getName());
                subItem.name = MapleDataTool.getString("info/name", fieldData);
                subItem.recordID = MapleDataTool.getInt("info/recordID", fieldData);
                subItem.rewardID = MapleDataTool.getInt("info/rewardID", fieldData);
                subItem.period = MapleDataTool.getInt("info/period", fieldData, -1);

                int idx = 0;
                MapleData groupData = null;
                while ((groupData = fieldData.getChildByPath("group").getChildByPath(Integer.toString(idx))) != null) {
                    CollectionField.CollectionGroup group = new CollectionField.CollectionGroup();

                    group.groupID = Integer.parseInt(groupData.getName());
                    group.name = MapleDataTool.getString("name", groupData);
                    group.recordID = MapleDataTool.getInt("recordID", groupData);
                    group.rewardID = MapleDataTool.getInt("rewardID", groupData);
                    group.rewardCount = MapleDataTool.getInt("rewardCount", groupData, 1);
                    group.count = MapleDataTool.getInt("count", groupData, 1);
                    group.explorationCycle = MapleDataTool.getInt("exploraionCycle", groupData, -1);
                    group.explorationReward = MapleDataTool.getInt("exploraionReward", groupData, -1);

                    if (groupData.getChildByPath("mob") != null) {
                        for (MapleData mobData : groupData.getChildByPath("mob").getChildren()) {
                            CollectionField.CollectionGroup.CollectionMob mob = new CollectionField.CollectionGroup.CollectionMob();

                            mob.regionId = Integer.parseInt(regionData.getName());
                            mob.sessionId = Integer.parseInt(fieldData.getName());
                            mob.groupId = Integer.parseInt(groupData.getName());
                            mob.index = Integer.parseInt(mobData.getName());
                            mob.type = MapleDataTool.getInt("type", mobData);
                            mob.id = MapleDataTool.getInt("id", mobData);
                            mob.starRank = MapleDataTool.getInt("starRank", mobData, 0);
                            mob.tooltip = MapleDataTool.getString("tooltip", mobData, "");
                            mob.eliteName = MapleDataTool.getString("eliteName", mobData, "");
                            mob.recordID = MapleDataTool.getInt("recordID", mobData, -1);
                            mob.rewardID = MapleDataTool.getInt("rewardID", mobData, -1);
                            mob.rewardCount = MapleDataTool.getInt("rewardCount", mobData, 1);

                            if (mobData.getChildByPath("special") != null) {

                                int idx_special = 0;
                                MapleData specialData = null;
                                while ((specialData = groupData.getChildByPath(Integer.toString(idx_special))) != null) {
                                    mob.special.add(MapleDataTool.getInt(specialData));

                                    idx_special++;
                                }

                            }

                            group.mobs.add(mob);

                            if (_mobCollectionInfo.containsKey(mob.id) && mob.id != 9100049) { // 9100049 : 몬스터 컬렉션 더미 몬스터
                                //	System.out.println("MonsterCollection Error: Already Exists!");
                            }

                            _mobCollectionInfo.put(mob.id, mob);
                        }
                    }

                    subItem.groups.add(group);

                    idx++;
                }

                r.fields.add(subItem);

                i++;
            }

            _mobCollectionData.put(Integer.parseInt(regionData.getName()), r);
        }
    }

    public CollectionMob getMobCollectionInfo(int mobId) {
        return _mobCollectionInfo.get(mobId);
    }

    public MonsterCollectionRegion getCollectionRegion(int regionId) {
        return _mobCollectionData.get(regionId);
    }

    public CollectionField getCollectionSession(int regionId, int sessionId) {
        MonsterCollectionRegion region = _mobCollectionData.get(regionId);

        if (region == null) {
            return null;
        }

        for (CollectionField field : region.fields) {
            if (field.sessionID == sessionId) {
                return field;
            }
        }

        return null;
    }

    public CollectionGroup getCollectionGroup(int regionId, int sessionId, int groupId) {
        MonsterCollectionRegion region = _mobCollectionData.get(regionId);

        if (region == null) {
            return null;
        }

        CollectionField field = getCollectionSession(regionId, sessionId);

        if (field == null) {
            return null;
        }

        for (CollectionGroup g : field.groups) {
            if (g.groupID == groupId) {
                return g;
            }
        }

        return null;
    }
}
