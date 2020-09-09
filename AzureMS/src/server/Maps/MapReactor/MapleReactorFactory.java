package server.Maps.MapReactor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import provider.WzXML.MapleDataType;
import tools.Pair;
import tools.StringUtil;

public class MapleReactorFactory {

    private static final MapleDataProvider data = MapleDataProviderFactory.getDataProvider(new File("wz/Reactor.wz"));
    private static Map<Integer, MapleReactorStats> reactorStats = new HashMap<Integer, MapleReactorStats>();

    public static final MapleReactorStats getReactor(int rid) {
        MapleReactorStats stats = reactorStats.get(Integer.valueOf(rid));
        if (stats == null) {
            int infoId = rid;
            MapleData reactorData = data
                    .getData(StringUtil.getLeftPaddedStr(Integer.toString(infoId) + ".img", '0', 11));
            MapleData link = reactorData.getChildByPath("info/link");
            if (link != null) {
                infoId = MapleDataTool.getIntConvert("info/link", reactorData);
                stats = reactorStats.get(Integer.valueOf(infoId));
            }
            if (stats == null) {
                reactorData = data.getData(StringUtil.getLeftPaddedStr(Integer.toString(infoId) + ".img", '0', 11));
                MapleData reactorInfoData = reactorData.getChildByPath("0/event");
                stats = new MapleReactorStats();

                if (reactorInfoData != null) {
                    boolean areaSet = false;
                    int i = 0;
                    while (reactorInfoData != null) {
                        for (MapleData rdata : reactorInfoData.getChildren()) {
                            if (rdata.getType() != MapleDataType.PROPERTY) { // timeout and etc..
                                continue;
                            }
                            Pair<Integer, Integer> reactItem = null;
                            int type = MapleDataTool.getIntConvert("type", rdata);
                            if (type == 100) { // reactor waits for item
                                reactItem = new Pair<Integer, Integer>(MapleDataTool.getIntConvert("0", rdata),
                                        MapleDataTool.getIntConvert("1", rdata));
                                if (!areaSet) { // only set area of effect for item-triggered reactors once
                                    stats.setTL(MapleDataTool.getPoint("lt", rdata));
                                    stats.setBR(MapleDataTool.getPoint("rb", rdata));
                                    areaSet = true;
                                }
                            }
                            stats.addState((byte) i, Byte.parseByte(rdata.getName()), type, reactItem,
                                    (byte) MapleDataTool.getIntConvert("state", rdata));
                        }
                        i++;
                        reactorInfoData = reactorData.getChildByPath(i + "/event");
                    }
                } else { // sit there and look pretty; likely a reactor such as Zakum/Papulatus doors
                    // that shows if player can enter
                    stats.addState((byte) 0, (byte) 0, 999, null, (byte) 0);
                }
                reactorStats.put(Integer.valueOf(infoId), stats);
                if (rid != infoId) {
                    reactorStats.put(Integer.valueOf(rid), stats);
                }
            } else { // stats exist at infoId but not rid; add to map
                reactorStats.put(Integer.valueOf(rid), stats);
            }
        }
        return stats;
    }
}
