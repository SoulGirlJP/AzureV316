package server.Maps.MapReactor;

public class ReactorDropEntry {

    public ReactorDropEntry(int itemId, int chance, int questid) {
        this.itemId = itemId;
        this.chance = chance;
        this.questid = questid;
    }

    public int itemId, chance, questid;
    public int assignedRangeStart, assignedRangeLength;
}
