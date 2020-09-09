package server.Quests;

public enum MapleQuestRequirementType {

    UNDEFINED(-1), job(0), item(1), quest(2), lvmin(3), lvmax(4), end(5), mob(6), npc(7), fieldEnter(8), interval(9), startscript(10), endscript(10), pet(11), pettamenessmin(12), questComplete(14), pop(15), skill(16);

    public MapleQuestRequirementType getITEM() {
        return item;
    }
    final byte type;

    private MapleQuestRequirementType(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

    public static MapleQuestRequirementType getByType(byte type) {
        for (MapleQuestRequirementType l : MapleQuestRequirementType.values()) {
            if (l.getType() == type) {
                return l;
            }
        }
        return null;
    }

    public static MapleQuestRequirementType getByWZName(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException ex) {
            return UNDEFINED;
        }
    }
}
