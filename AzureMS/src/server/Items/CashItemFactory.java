package server.Items;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;

public class CashItemFactory {

    private static CashItemFactory instance = new CashItemFactory();
    public Map<Integer, CashItemInfo> itemStats = new HashMap<Integer, CashItemInfo>();
    public MapleDataProvider petData = MapleDataProviderFactory.getDataProvider(new File("wz/Item.wz/Pet"));
    public MapleDataProvider data = MapleDataProviderFactory.getDataProvider(new File("wz/Etc.wz"));

    public static CashItemFactory getInstance() {
        return instance;
    }

    protected CashItemFactory() {
        for (MapleData field : data.getData("Commodity.img").getChildren()) {
            int itemId = MapleDataTool.getIntConvert("ItemId", field, 0);
            int sn = MapleDataTool.getIntConvert("SN", field, 0);
            boolean onSale = itemId > 0;
            int period = 0;
            if (itemId >= 5000000 && itemId <= 5002000) { // Æê
                period = MapleDataTool.getIntConvert("life", petData.getData(itemId + ".img").getChildByPath("info"));
            } else {
                period = MapleDataTool.getIntConvert("Period", field, 0);
            }
            if (onSale) {
                final CashItemInfo stats = new CashItemInfo(MapleDataTool.getIntConvert("ItemId", field),
                        MapleDataTool.getIntConvert("Count", field, 1), MapleDataTool.getIntConvert("Price", field, 0),
                        period, MapleDataTool.getIntConvert("OnSale", field, 0) == 1);
                itemStats.put(MapleDataTool.getIntConvert("SN", field, 0), stats);
            }
        }
    }

    public CashItemInfo getItemInfoFromItemId(int id) {
        for (CashItemInfo cii : itemStats.values()) {
            if (cii.getId() == id) {
                return cii;
            }
        }
        return null;
    }

    public CashItemInfo getItem(int sn) {
        CashItemInfo stats = itemStats.get(sn);
        if (stats == null) {
            return null;
        }
        return stats;
    }

    public List<Pair<Integer, CashItemInfo>> getPackages(int itemId) {
        List<Pair<Integer, CashItemInfo>> ret = new ArrayList<Pair<Integer, CashItemInfo>>();
        MapleData b = data.getData("CashPackage.img").getChildByPath(Integer.toString(itemId)).getChildByPath("SN");
        for (MapleData c : b.getChildren()) {
            int sn = MapleDataTool.getIntConvert(c.getName(), b);
            ret.add(new Pair(sn, getItem(sn)));
        }

        return ret;
    }
}
