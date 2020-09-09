package provider;

import java.io.File;

import provider.WzXML.XMLWZFile;

public class MapleDataProviderFactory {

    private final static String wzPath = "WZ/";

    private static MapleDataProvider getWZ(Object in, boolean provideImages) {
        if (in instanceof File) {
            File fileIn = (File) in;

            return new XMLWZFile(fileIn);
        }
        throw new IllegalArgumentException("Can't create data provider for input " + in);
    }

    public static MapleDataProvider getDataProvider(Object in) {
        return getWZ(in, false);
    }

    public static MapleDataProvider getImageProvidingDataProvider(Object in) {
        return getWZ(in, true);
    }

    public static File fileInWZPath(String filename) {
        return new File(wzPath, filename);
    }
}
