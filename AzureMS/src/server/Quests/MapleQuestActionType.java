package server.Quests;

public enum MapleQuestActionType {

    UNDEFINED(-1), exp(0), item(1), nextQuest(2), money(3), QUEST(4), skill(5), pop(6), buffItemID(7), infoNumber(
            8), insightEXP(9), senseEXP(10), charismaEXP(11), charmEXP(12), craftEXP(13), willEXP(14);
    final byte type;

    private MapleQuestActionType(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

    public static MapleQuestActionType getByType(byte type) {
        for (MapleQuestActionType l : MapleQuestActionType.values()) {
            if (l.getType() == type) {
                return l;
            }
        }
        return null;
    }

    public static MapleQuestActionType getByWZName(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException ex) {
            return UNDEFINED;
        }
    }
}
