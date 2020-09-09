package server.Quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import provider.MapleData;
import provider.MapleDataEntity;
import provider.WzXML.MapleDataType;

public class MapleCustomQuestData implements MapleData {

    private List<MapleCustomQuestData> children = new LinkedList<MapleCustomQuestData>();
    private String name;
    private Object data;
    private MapleDataEntity parent;

    public MapleCustomQuestData(String name, Object data, MapleDataEntity parent) {
        this.name = name;
        this.data = data;
        this.parent = parent;
    }

    public void addChild(MapleData child) {
        children.add((MapleCustomQuestData) child);
    }

    public String getName() {
        return name;
    }

    public MapleDataType getType() {
        return MapleDataType.UNKNOWN_TYPE;
    }

    public List<MapleData> getChildren() {
        MapleData[] ret = new MapleData[children.size()];
        ret = children.toArray(ret);
        return new ArrayList<MapleData>(Arrays.asList(ret));
    }

    public MapleData getChildByPath(String name) {
        if (name.equals(this.name)) {
            return this;
        }
        String lookup, nextName;
        if (name.indexOf("/") == -1) {
            lookup = name;
            nextName = name;
        } else {
            lookup = name.substring(0, name.indexOf("/"));
            nextName = name.substring(name.indexOf("/") + 1);
        }
        for (MapleData child : children) {
            if (child.getName().equals(lookup)) {
                return child.getChildByPath(nextName);
            }
        }
        return null;
    }

    public Object getData() {
        return data;
    }

    public Iterator<MapleData> iterator() {
        return getChildren().iterator();
    }

    public MapleDataEntity getParent() {
        return parent;
    }
}
