package server;

import java.util.LinkedList;
import java.util.List;

public class MonsterCollectionRegion {

    public static class ClearQuest {

        public static class ClearQuestSub {

            public int clearCount;
            public int recordID;
            public int rewardID;
        }

        public String decl;
        public List<ClearQuestSub> clearQuestSub = new LinkedList<>();
    }

    public static class CollectionField {

        public static class CollectionGroup {

            public static class CollectionMob {

                public int regionId;
                public int sessionId;
                public int groupId;
                public int index;
                public int type;
                public int id;
                public int starRank;
                public String tooltip;
                public String eliteName;
                public int recordID;
                public int rewardID;
                public int rewardCount;
                public List<Integer> special = new LinkedList<>();

                public int getCollectionId() {
                    return sessionId + 100 * (regionId + 1000);
                }

                public boolean isSkip() {
                    return type == 7 || type == 8; // event or empty
                }

                public int convertMobFlag() {
                    switch (index) {
                        case 0:
                            return 2; // 2, 3
                        case 1:
                            return 0; // 0, 7
                        case 2:
                            return 4; // 4, 5
                        case 3:
                            return 9; // 9, 10
                        case 4:
                            return 14; // 14, 15
                        case 5:
                            return 12; // 12, 19
                        case 6:
                            return 16; // 16, 17
                        case 7:
                            return 21; // 21, 22
                        case 8:
                            return 26; // 26, 27
                        case 9:
                            return 24; // 24, 31
                        case 10:
                            return 28; // 28, 29
                        case 11:
                            return 33; // 33, 34
                        case 12:
                            return 38; // 38, 39
                        case 13:
                            return 36; // 36, 43
                        case 14:
                            return 40; // 40, 41
                        case 15:
                            return 45; // 45, 46
                        case 16:
                            return 50; // 50, 51
                        case 17:
                            return 48; // 48, 55
                        case 18:
                            return 52; // 52, 53
                        case 19:
                            return 57; // 57, 58
                        case 20:
                            return 62; // 62, 63
                        case 21:
                            return 60; // 60, 67
                        case 22:
                            return 64; // 64, 65
                        case 23:
                            return 69; // 69, 70
                        case 24:
                            return 74; // 74, 75

                        default:
                            return -1;
                    }
                }
            }

            public int groupID;
            public String name;
            public int recordID;
            public int rewardID;
            public int rewardCount;
            public int count;
            public int explorationCycle;
            public int explorationReward;
            public List<CollectionMob> mobs = new LinkedList<>();
        }

        public int sessionID;
        public String name;
        public int recordID;
        public int rewardID;
        public int period;
        public List<CollectionGroup> groups = new LinkedList<>();
    }

    public int regionID;
    public int recordID;
    public String name;
    public int rewardID;
    public ClearQuest clearQuest = new ClearQuest();
    public List<CollectionField> fields = new LinkedList<>();
}
