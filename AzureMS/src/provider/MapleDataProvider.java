package provider;

public interface MapleDataProvider {

    MapleData getData(String path);

    MapleDataDirectoryEntry getRoot();
}
