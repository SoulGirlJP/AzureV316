package provider;

import java.util.List;

public interface MapleDataDirectoryEntry extends MapleDataEntry {

    public List<MapleDataDirectoryEntry> getSubdirectories();

    public List<MapleDataFileEntry> getFiles();

    public MapleDataEntry getEntry(String name);
}
