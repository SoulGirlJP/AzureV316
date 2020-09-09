package provider.WzXML;

import provider.MapleDataEntity;
import provider.MapleDataEntry;

public class WZEntry implements MapleDataEntry {

    private String name;
    private int size;
    private int checksum;
    private int offset;
    private MapleDataEntity parent;

    public WZEntry(String name, int size, int checksum, MapleDataEntity parent) {
        super();
        this.name = name;
        this.size = size;
        this.checksum = checksum;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getChecksum() {
        return checksum;
    }

    public int getOffset() {
        return offset;
    }

    public MapleDataEntity getParent() {
        return parent;
    }
}
