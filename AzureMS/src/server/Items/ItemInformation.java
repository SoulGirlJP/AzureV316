package server.Items;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import server.Systems.StarForceSystem;
import client.Character.MapleCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventoryType;
import client.ItemInventory.MapleWeaponType;
import client.ItemInventory.StructPotentialItem;
import client.MapleClient;
import client.Skills.SkillStatEffect;
import constants.GameConstants;
import connections.Packets.UIPacket;
import launcher.Utility.WorldBroadcasting;
import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.Items.StructSetItem.SetItem;
import tools.Pair;
import tools.StringUtil;
import tools.RandomStream.Randomizer;

public class ItemInformation {

    private final static ItemInformation instance = new ItemInformation();
    public final MapleDataProvider etcData = MapleDataProviderFactory.getDataProvider(new File("wz/Etc.wz"));
    public final MapleDataProvider itemData = MapleDataProviderFactory.getDataProvider(new File("wz/Item.wz"));
    protected final MapleDataProvider equipData = MapleDataProviderFactory.getDataProvider(new File("wz/Character.wz"));
    protected final MapleDataProvider stringData = MapleDataProviderFactory.getDataProvider(new File("wz/String.wz"));
    protected final MapleData cashStringData = stringData.getData("Cash.img");
    protected final MapleData consumeStringData = stringData.getData("Consume.img");
    protected final MapleData eqpStringData = stringData.getData("Eqp.img");
    protected final MapleData etcStringData = stringData.getData("Etc.img");
    protected final MapleData insStringData = stringData.getData("Ins.img");
    protected final MapleData petStringData = stringData.getData("Pet.img");
    protected final Map<Integer, Short> slotMaxCache = new HashMap<Integer, Short>();
    protected final Map<Integer, SkillStatEffect> itemEffects = new HashMap<Integer, SkillStatEffect>();
    protected final Map<Integer, Map<String, Integer>> equipStatsCache = new HashMap<Integer, Map<String, Integer>>();
    protected final Map<Integer, Map<String, Byte>> itemMakeStatsCache = new HashMap<Integer, Map<String, Byte>>();
    protected final Map<Integer, Map<Integer, StructEquipLevel>> equipLevelCache = new HashMap<Integer, Map<Integer, StructEquipLevel>>();
    protected final Map<Integer, Integer> equipLevelMaxLevel = new HashMap<Integer, Integer>();
    protected final Map<Integer, Short> itemMakeLevel = new HashMap<Integer, Short>();
    protected final Map<Integer, Equip> equipCache = new HashMap<Integer, Equip>();
    protected final Map<Integer, Double> priceCache = new HashMap<Integer, Double>();
    protected final Map<Integer, Integer> successCache = new HashMap<Integer, Integer>();
    protected final Map<Integer, Integer> cursedCache = new HashMap<Integer, Integer>();
    protected final Map<Integer, Integer> wholePriceCache = new HashMap<Integer, Integer>();
    protected final Map<Integer, Integer> projectileWatkCache = new HashMap<Integer, Integer>();
    protected final Map<Integer, Integer> monsterBookID = new HashMap<Integer, Integer>();
    protected final Map<Integer, String> nameCache = new HashMap<Integer, String>();
    protected final Map<Integer, String> descCache = new HashMap<Integer, String>();
    protected final Map<Integer, String> msgCache = new HashMap<Integer, String>();
    protected final Map<Integer, Map<String, Integer>> SkillStatsCache = new HashMap<Integer, Map<String, Integer>>();
    protected final Map<Integer, Byte> consumeOnPickupCache = new HashMap<Integer, Byte>();
    protected final Map<Integer, Byte> runOnPickupCache = new HashMap<Integer, Byte>();
    protected final Map<Integer, Boolean> dropRestrictionCache = new HashMap<Integer, Boolean>();
    protected final Map<Integer, Boolean> expirationOnLogOutCache = new HashMap<Integer, Boolean>();
    protected final Map<Integer, Boolean> pickupRestrictionCache = new HashMap<Integer, Boolean>();
    protected final Map<Integer, Integer> stateChangeCache = new HashMap<Integer, Integer>(40);
    protected final Map<Integer, Integer> karmaEnabledCache = new HashMap<Integer, Integer>();
    protected final Map<Integer, Integer> scrollUpgradeSlotUse = new HashMap<Integer, Integer>();
    protected final Map<Integer, Boolean> isQuestItemCache = new HashMap<Integer, Boolean>();
    protected final Map<Integer, List<Pair<Integer, Integer>>> summonMobCache = new HashMap<Integer, List<Pair<Integer, Integer>>>();
    protected final List<Pair<Integer, String>> itemNameCache = new ArrayList<Pair<Integer, String>>();
    protected final Map<Integer, Boolean> accCache = new HashMap<Integer, Boolean>();
    public final Map<Integer, StructSetItem> setItems = new HashMap();
    public final Map<Integer, List<StructPotentialItem>> potentialCache = new HashMap<Integer, List<StructPotentialItem>>();
    protected final Map<Integer, Pair<Integer, List<StructRewardItem>>> RewardItem = new HashMap<Integer, Pair<Integer, List<StructRewardItem>>>();
    protected Map<Integer, Integer> scriptedItemCache = new HashMap<Integer, Integer>();
    protected Map<Integer, Integer> androidCache = new HashMap<Integer, Integer>();
    protected Map<Integer, Integer> bagTypeCache = new HashMap<Integer, Integer>();
    protected List<Integer> nonLevelEquip = new LinkedList<Integer>();
    protected List<Integer> incSkill = null;
    protected Map<Integer, BasicAndroid> androidBasicCache = new HashMap<Integer, BasicAndroid>();
    protected Map<Integer, String> scriptedItemScriptCache = new HashMap<Integer, String>();
    public final List<Pair<Integer, Integer>> potentialOpCache = new LinkedList<>();
    protected Map<Integer, MapleInventoryType> inventoryTypeCache = new HashMap<Integer, MapleInventoryType>();
    protected Map<Integer, Integer> mipCache = new HashMap<Integer, Integer>();
    private short slotMax;
    protected Map<Integer, Map<String, Integer>> equipStats = null;

    protected ItemInformation() {
        cache();
    }

    public static ItemInformation getInstance() {
        return instance;
    }
 
    private void cache() {
        cachePotentialItems();
        cachePotentialOption();
        cacheSetItems();
    }

    private void cacheItemsFromList(List<Pair<Integer, String>> pair) {
        for (Pair<Integer, String> p : pair) {
            getItemData(p.getLeft());
        }
    }

    /*
* type-0: Total 10: Weapon 11: Weapon Excluded 20: Shield 40: Accessories 51: Helm 52: Tops, Suit 53:
* Bottom 54: Gloves 55: Shoes
     */
    public void cachePotentialOption() {
        final MapleData potsData = itemData.getData("ItemOption.img");
        for (MapleData data : potsData) {
            int potentialID = Integer.parseInt(data.getName());
            int type = MapleDataTool.getIntConvert("info/optionType", data, 0);
            int level = 0;
            Map<Integer, List<Integer>> option = new HashMap<Integer, List<Integer>>();
            List<Integer> id = new ArrayList<Integer>(100);
            switch (potentialID) {
                case 31001:
                case 31002:
                case 31003:
                case 31004:
                case 60002:
                    continue;
            }
            if (potentialID > 0 && potentialID < 906) { // Tier 1 Options
                level = 1;
            } else if ((potentialID > 10000 && potentialID < 10292) || (potentialID > 20000 && potentialID < 20015)
                    || (potentialID > 30000 && potentialID < 30015) || (potentialID > 40000 && potentialID < 40015)) { // Tier 2 Options
                level = 2;
            } else if (potentialID > 20040 && potentialID < 20407) { // Tier 3 Options
                level = 3;
            } else if (potentialID > 30040 && potentialID < 31005) { // Tier 4 Options
                level = 4;
            } else if (potentialID > 40040 && potentialID < 60004) { // Tier 5 Options
                level = 5;
            }
            potentialOpCache.add(new Pair<Integer, Integer>(potentialID, type));
            option.put(type, id);
        }
    }

    public void cachePotentialItems() {
        final MapleData potsData = itemData.getData("ItemOption.img");
        StructPotentialItem item;
        List<StructPotentialItem> items;
        for (MapleData data : potsData) {
            items = new LinkedList<StructPotentialItem>();
            for (MapleData level : data.getChildByPath("level")) {
                item = new StructPotentialItem();
                item.optionType = MapleDataTool.getIntConvert("info/optionType", data, 0);
                item.reqLevel = MapleDataTool.getIntConvert("info/reqLevel", data, 0);
                item.weight = MapleDataTool.getIntConvert("info/weight", data, 0);
                item.string = MapleDataTool.getString("info/string", level, "");
                item.face = MapleDataTool.getString("face", level, "");
                item.boss = MapleDataTool.getIntConvert("boss", level, 0) > 0;
                item.potentialID = Integer.parseInt(data.getName());
                item.attackType = (short) MapleDataTool.getIntConvert("attackType", level, 0);
                item.incMHP = (short) MapleDataTool.getIntConvert("incMHP", level, 0);
                item.incMMP = (short) MapleDataTool.getIntConvert("incMMP", level, 0);
                item.incSTR = (byte) MapleDataTool.getIntConvert("incSTR", level, 0);
                item.incDEX = (byte) MapleDataTool.getIntConvert("incDEX", level, 0);
                item.incINT = (byte) MapleDataTool.getIntConvert("incINT", level, 0);
                item.incLUK = (byte) MapleDataTool.getIntConvert("incLUK", level, 0);
                item.incACC = (byte) MapleDataTool.getIntConvert("incACC", level, 0);
                item.incEVA = (byte) MapleDataTool.getIntConvert("incEVA", level, 0);
                item.incSpeed = (byte) MapleDataTool.getIntConvert("incSpeed", level, 0);
                item.incJump = (byte) MapleDataTool.getIntConvert("incJump", level, 0);
                item.incPAD = (byte) MapleDataTool.getIntConvert("incPAD", level, 0);
                item.incMAD = (byte) MapleDataTool.getIntConvert("incMAD", level, 0);
                item.incPDD = (byte) MapleDataTool.getIntConvert("incPDD", level, 0);
                item.incMDD = (byte) MapleDataTool.getIntConvert("incMDD", level, 0);
                item.prop = (byte) MapleDataTool.getIntConvert("prop", level, 0);
                item.time = (byte) MapleDataTool.getIntConvert("time", level, 0);
                item.incSTRr = (byte) MapleDataTool.getIntConvert("incSTRr", level, 0);
                item.incDEXr = (byte) MapleDataTool.getIntConvert("incDEXr", level, 0);
                item.incINTr = (byte) MapleDataTool.getIntConvert("incINTr", level, 0);
                item.incLUKr = (byte) MapleDataTool.getIntConvert("incLUKr", level, 0);
                item.incMHPr = (byte) MapleDataTool.getIntConvert("incMHPr", level, 0);
                item.incMMPr = (byte) MapleDataTool.getIntConvert("incMMPr", level, 0);
                item.incACCr = (byte) MapleDataTool.getIntConvert("incACCr", level, 0);
                item.incEVAr = (byte) MapleDataTool.getIntConvert("incEVAr", level, 0);
                item.incPADr = (byte) MapleDataTool.getIntConvert("incPADr", level, 0);
                item.incMADr = (byte) MapleDataTool.getIntConvert("incMADr", level, 0);
                item.incPDDr = (byte) MapleDataTool.getIntConvert("incPDDr", level, 0);
                item.incMDDr = (byte) MapleDataTool.getIntConvert("incMDDr", level, 0);
                item.incCr = (byte) MapleDataTool.getIntConvert("incCr", level, 0);
                item.incDAMr = (byte) MapleDataTool.getIntConvert("incDAMr", level, 0);
                item.RecoveryHP = (byte) MapleDataTool.getIntConvert("RecoveryHP", level, 0);
                item.RecoveryMP = (byte) MapleDataTool.getIntConvert("RecoveryMP", level, 0);
                item.HP = (byte) MapleDataTool.getIntConvert("HP", level, 0);
                item.MP = (byte) MapleDataTool.getIntConvert("MP", level, 0);
                item.level = (byte) MapleDataTool.getIntConvert("level", level, 0);
                item.ignoreTargetDEF = (byte) MapleDataTool.getIntConvert("ignoreTargetDEF", level, 0);
                item.ignoreDAM = (byte) MapleDataTool.getIntConvert("ignoreDAM", level, 0);
                item.DAMreflect = (byte) MapleDataTool.getIntConvert("DAMreflect", level, 0);
                item.mpconReduce = (byte) MapleDataTool.getIntConvert("mpconReduce", level, 0);
                item.mpRestore = (byte) MapleDataTool.getIntConvert("mpRestore", level, 0);
                item.incMesoProp = (byte) MapleDataTool.getIntConvert("incMesoProp", level, 0);
                item.incRewardProp = (byte) MapleDataTool.getIntConvert("incRewardProp", level, 0);
                item.incAllskill = (byte) MapleDataTool.getIntConvert("incAllskill", level, 0);
                item.ignoreDAMr = (byte) MapleDataTool.getIntConvert("ignoreDAMr", level, 0);
                item.RecoveryUP = (byte) MapleDataTool.getIntConvert("RecoveryUP", level, 0);
                switch (item.potentialID) {
                    case 31001:
                    case 31002:
                    case 31003:
                    case 31004:
                        // item.skillID = (short) (item.potentialID - 23001);
                        item.skillID = 0;
                        break;
                    default:
                        item.skillID = 0;
                        break;
                }
                items.add(item);
            }
            potentialCache.put(Integer.parseInt(data.getName()), items);
        }
    }

    public final Integer getSetItemID(final int itemId) {
        if (getEquipStats(itemId) == null || !getEquipStats(itemId).containsKey("setItemID")) {
            return 0;
        }
        return getEquipStats(itemId).get("setItemID");
    }

    public final List<Integer> getEquipSkills(final int itemId) {
        if (getEquipStats(itemId) == null) {
            return null;
        }
        if (incSkill == null) {
            incSkill = new ArrayList<Integer>();
            final MapleData iz = getItemData(itemId);
            final MapleData dat = iz.getChildByPath("info/level/case");
            if (dat != null) {
                for (MapleData info : dat) {
                    for (MapleData data : info) {
                        if (data.getName().length() == 1 && data.getChildByPath("Skill") != null) {
                            for (MapleData skil : data.getChildByPath("Skill")) {
                                int incSkillz = MapleDataTool.getIntConvert("id", skil, 0);
                                if (incSkillz != 0) {
                                    incSkill.add(incSkillz);
                                }
                            }
                        }
                    }
                }
            }
        }
        return incSkill;
    }

    public final Map<Integer, Map<String, Integer>> getEquipIncrements(final int itemId) {
        if (getEquipStats(itemId) == null) {
            return null;
        }
        if (equipStats == null) {
            equipStats = new HashMap<Integer, Map<String, Integer>>();
            equipStats.put(-1, new HashMap<String, Integer>());
            final MapleData iz = getItemData(itemId);
            final MapleData dat = iz.getChildByPath("mob");
            if (dat != null) {
                for (MapleData child : dat) {
                    equipStats.get(-1).put("mob" + MapleDataTool.getIntConvert("id", child, 0),
                            MapleDataTool.getIntConvert("prob", child, 0));
                }
            }
        }
        return equipStats;
    }

    public void cacheSetItems() {
        final MapleData setsData = etcData.getData("SetItemInfo.img");
        StructSetItem itemz;
        SetItem itez;
        for (MapleData dat : setsData) {
            itemz = new StructSetItem();
            itemz.setItemID = (short) Short.parseShort(dat.getName());
            itemz.completeCount = (byte) MapleDataTool.getIntConvert("completeCount", dat, 0);
            for (MapleData level : dat.getChildByPath("Effect")) {
                itez = new SetItem();
                itez.incPDD = MapleDataTool.getIntConvert("incPDD", level, 0);
                itez.incMDD = MapleDataTool.getIntConvert("incMDD", level, 0);
                itez.incSTR = MapleDataTool.getIntConvert("incSTR", level, 0);
                itez.incDEX = MapleDataTool.getIntConvert("incDEX", level, 0);
                itez.incINT = MapleDataTool.getIntConvert("incINT", level, 0);
                itez.incLUK = MapleDataTool.getIntConvert("incLUK", level, 0);
                itez.incACC = MapleDataTool.getIntConvert("incACC", level, 0);
                itez.incPAD = MapleDataTool.getIntConvert("incPAD", level, 0);
                itez.incMAD = MapleDataTool.getIntConvert("incMAD", level, 0);
                itez.incSpeed = MapleDataTool.getIntConvert("incSpeed", level, 0);
                itez.incMHP = MapleDataTool.getIntConvert("incMHP", level, 0);
                itez.incMMP = MapleDataTool.getIntConvert("incMMP", level, 0);
                itez.incMHPr = MapleDataTool.getIntConvert("incMHPr", level, 0);
                itez.incMMPr = MapleDataTool.getIntConvert("incMMPr", level, 0);
                itez.incAllStat = MapleDataTool.getIntConvert("incAllStat", level, 0);
                itez.option1 = MapleDataTool.getIntConvert("Option/1/option", level, 0);
                itez.option2 = MapleDataTool.getIntConvert("Option/2/option", level, 0);
                itez.option1Level = MapleDataTool.getIntConvert("Option/1/level", level, 0);
                itez.option2Level = MapleDataTool.getIntConvert("Option/2/level", level, 0);
                itemz.items.put(Integer.parseInt(level.getName()), itez);
            }
            setItems.put(itemz.setItemID, itemz);
        }
    }

    public final Integer getSetItemId(int itemId) {
        if (getEquipStats(itemId) == null || !getEquipStats(itemId).containsKey("setItemID")) {
            return 0;
        }
        return getEquipStats(itemId).get("setItemID");
    }

    public final StructSetItem getSetItem(final int setItemId) {
        return setItems.get(setItemId);
    }

    public final List<Pair<Integer, String>> getAllItems() {
        if (!itemNameCache.isEmpty()) {
            return itemNameCache;
        }
        final List<Pair<Integer, String>> itemPairs = new ArrayList<Pair<Integer, String>>();
        MapleData itemsData;

        itemsData = stringData.getData("Cash.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                    MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }

        itemsData = stringData.getData("Consume.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                    MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }

        itemsData = stringData.getData("Eqp.img").getChildByPath("Eqp");
        for (final MapleData eqpType : itemsData.getChildren()) {
            for (final MapleData itemFolder : eqpType.getChildren()) {
                itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                        MapleDataTool.getString("name", itemFolder, "NO-NAME")));
            }
        }

        itemsData = stringData.getData("Etc.img").getChildByPath("Etc");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                    MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }

        itemsData = stringData.getData("Ins.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                    MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }

        itemsData = stringData.getData("Pet.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                    MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        return itemPairs;
    }

    public final List<Pair<Integer, String>> getAllEquips() {
        if (!itemNameCache.isEmpty()) {
            return itemNameCache;
        }
        final List<Pair<Integer, String>> itemPairs = new ArrayList<Pair<Integer, String>>();
        MapleData itemsData;

        itemsData = stringData.getData("Eqp.img").getChildByPath("Eqp");
        for (final MapleData eqpType : itemsData.getChildren()) {
            for (final MapleData itemFolder : eqpType.getChildren()) {
                itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()),
                        MapleDataTool.getString("name", itemFolder, "NO-NAME")));
            }
        }
        return itemPairs;
    }

    protected final MapleData getStringData(final int itemId) {
        String cat = null;
        MapleData data;

        if (itemId >= 5010000) {
            data = cashStringData;
        } else if (itemId >= 2000000 && itemId < 3000000) {
            data = consumeStringData;
        } else if (itemId >= 1142000 && itemId < 1143000 || itemId >= 1010000 && itemId < 1040000
                || itemId >= 1122000 && itemId < 1123000) {
            data = eqpStringData;
            cat = "Accessory";
        } else if (itemId >= 1000000 && itemId < 1010000) {
            data = eqpStringData;
            cat = "Cap";
        } else if (itemId >= 1102000 && itemId < 1103000) {
            data = eqpStringData;
            cat = "Cape";
        } else if (itemId >= 1040000 && itemId < 1050000) {
            data = eqpStringData;
            cat = "Coat";
        } else if (itemId >= 20000 && itemId < 29999) {
            data = eqpStringData;
            cat = "Face";
        } else if (itemId >= 1080000 && itemId < 1090000) {
            data = eqpStringData;
            cat = "Glove";
        } else if (itemId >= 30000 && itemId < 49999) {
            data = eqpStringData;
            cat = "Hair";
        } else if (itemId >= 1050000 && itemId < 1060000) {
            data = eqpStringData;
            cat = "Longcoat";
        } else if (itemId >= 1060000 && itemId < 1070000) {
            data = eqpStringData;
            cat = "Pants";
        } else if (itemId >= 1802000 && itemId < 1810000) {
            data = eqpStringData;
            cat = "PetEquip";
        } else if (itemId >= 1112000 && itemId < 1120000) {
            data = eqpStringData;
            cat = "Ring";
        } else if (itemId >= 1092000 && itemId < 1100000) {
            data = eqpStringData;
            cat = "Shield";
        } else if (itemId >= 1070000 && itemId < 1080000) {
            data = eqpStringData;
            cat = "Shoes";
        } else if (itemId >= 1900000 && itemId < 2000000) {
            data = eqpStringData;
            cat = "Taming";
        } else if (itemId >= 1200000 && itemId < 1800000) { // Base : 1300000
            data = eqpStringData;
            cat = "Weapon";
        } else if (itemId >= 4000000 && itemId < 5000000) {
            cat = "Etc";
            data = etcStringData;
        } else if (itemId >= 3000000 && itemId < 4000000) {
            data = insStringData;
        } else if (itemId >= 5000000 && itemId < 5010000) {
            data = petStringData;
        } else {
            return null;
        }
        if (cat == null) {
            return data.getChildByPath(String.valueOf(itemId));
        } else {
            if (cat.equals("Etc")) {
                return data.getChildByPath("Etc/" + itemId);
            } else {
                return data.getChildByPath("Eqp/" + cat + "/" + itemId);
            }
        }
    }

    public final MapleData getItemData(final int itemId) {
        MapleData ret = null;
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            // we should have .img files here beginning with the first 4 IID
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = itemData.getData(topDir.getName() + "/" + iFile.getName());
                    if (ret == null) {
                        return null;
                    }
                    ret = ret.getChildByPath(idStr);
                    return ret;
                } else if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    return itemData.getData(topDir.getName() + "/" + iFile.getName());
                }
            }
        }
        root = equipData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    return equipData.getData(topDir.getName() + "/" + iFile.getName());
                }
            }
        }
        return ret;
    }

    /**
     * returns the maximum of items in one slot
     */
    public final boolean isWalk2(final int itemId) {
        final MapleData item = getItemData(itemId);
        MapleData md = item.getChildByPath("walk2");
        if (md == null) {
            return false;
        }
        return true;
    }

    public final short getSlotMax(final MapleClient c, final int itemId) {
        if (slotMaxCache.containsKey(itemId)) {
            return slotMaxCache.get(itemId);
        }
        short ret = 0;
        final MapleData item = getItemData(itemId);
        if (item != null) {
            final MapleData smEntry = item.getChildByPath("info/slotMax");
            if (smEntry == null) {
                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    ret = 1;
                } else {
                    ret = 100;
                }
            } else {
                ret = (short) MapleDataTool.getInt(smEntry);
            }
        }
        if (ret == 0) {
            ret = 1;
        }
        if (itemId == 4000313) {
            ret = 1000;
        }
        if (GameConstants.isEtc(itemId) || GameConstants.isConsume(itemId)) {
            ret = 5000;
        }
        if (itemId >= 3994000 && itemId <= 3994011) {
            ret = 5000;
        }
        slotMaxCache.put(itemId, ret);
        return ret;
    }

    public final List<StructPotentialItem> getPotentialInfo(final int potId) {
        return potentialCache.get(potId);
    }

    public final Map<Integer, List<StructPotentialItem>> getAllPotentialInfo() {
        return potentialCache;
    }

    public int getScriptedItemNpc(int itemId) {
        if (scriptedItemCache.containsKey(itemId)) {
            return scriptedItemCache.get(itemId);
        }
        int npcId = MapleDataTool.getInt("spec/npc", getItemData(itemId), 0);
        scriptedItemCache.put(itemId, npcId);
        return scriptedItemCache.get(itemId);
    }

    public String getScriptedItemScript(int itemId) {
        if (scriptedItemScriptCache.containsKey(itemId)) {
            return scriptedItemScriptCache.get(itemId);
        }
        String script = MapleDataTool.getString("spec/script", getItemData(itemId));
        scriptedItemScriptCache.put(itemId, script);
        return scriptedItemScriptCache.get(itemId);
    }

    public final int getUpgradeScrollUseSlot(int itemid) {
        if (scrollUpgradeSlotUse.containsKey(itemid)) {
            return scrollUpgradeSlotUse.get(itemid);
        }
        int useslot = MapleDataTool.getIntConvert("info/tuc", getItemData(itemid), 1);
        scrollUpgradeSlotUse.put(itemid, useslot);
        return scrollUpgradeSlotUse.get(itemid);
    }

    public final int getWholePrice(final int itemId) {
        if (wholePriceCache.containsKey(itemId)) {
            return wholePriceCache.get(itemId);
        }
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return -1;
        }
        int pEntry = 0;
        final MapleData pData = item.getChildByPath("info/price");
        if (pData == null) {
            return -1;
        }
        pEntry = MapleDataTool.getInt(pData);

        wholePriceCache.put(itemId, pEntry);
        return pEntry;
    }

    public final double getPrice(final int itemId) {
        if (priceCache.containsKey(itemId)) {
            return priceCache.get(itemId);
        }
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return -1;
        }
        double pEntry = 0.0;
        MapleData pData = item.getChildByPath("info/unitPrice");
        if (pData != null) {
            try {
                pEntry = MapleDataTool.getDouble(pData);
            } catch (Exception e) {
                pEntry = (double) MapleDataTool.getInt(pData);
            }
        } else {
            pData = item.getChildByPath("info/price");
            if (pData == null) {
                MapleData lv = item.getChildByPath("info/lv");
                return MapleDataTool.getInt(lv) * 2;
            }
            pEntry = (double) MapleDataTool.getInt(pData);
        }
        if (itemId == 2070019 || itemId == 2330007) {
            pEntry = 1.0;
        }
        priceCache.put(itemId, pEntry);
        return pEntry;
    }

    public final int getSuccess(final int itemId, final MapleCharacter player, IItem equip) {
        if (player.getGMLevel() > 0) {
            return 100;
        }
        if (player.getInventory(MapleInventoryType.ETC).countById(4001136) >= 1) {
            player.gainItem(4001136, (short) -1, false, -1L, "The jewel of legend");
            player.message(5, "Piece of Order Scrolls Burning!");
            return 100;
        }
        if (player.getInventory(MapleInventoryType.ETC).countById(4031034) >= 1) {
            player.gainItem(4031034, (short) -1, false, -1L, "The jewel of legend");
            player.message(5, "Did you get 70% chance to unfold the magic scroll? Shouldn't you? Check it out!");
            return 70;
        }
        Equip t = (Equip) equip.copy();
        if (itemId / 100 == 20493) {
            int success = 0;
            Equip lev = (Equip) equip.copy();
            byte leve = lev.getEnhance();
            switch (itemId) {
                case 2049300:
                case 2049303:
                case 2049306:
                case 2049323: {
                    if (leve == 0) {
                        success = 100;
                    } else if (leve == 1) {
                        success = 90;
                    } else if (leve == 2) {
                        success = 80;
                    } else if (leve == 3) {
                        success = 70;
                    } else if (leve == 4) {
                        success = 60;
                    } else if (leve == 5) {
                        success = 50;
                    } else if (leve == 6) {
                        success = 40;
                    } else if (leve == 7) {
                        success = 30;
                    } else if (leve == 8) {
                        success = 20;
                    } else if (leve == 9) {
                        success = 10;
                    } else if (leve >= 10) {
                        success = 5;
                    }
                    return success;
                }
                case 2049301:
                case 2049307: {
                    if (leve == 0) {
                        success = 80;
                    } else if (leve == 1) {
                        success = 70;
                    } else if (leve == 2) {
                        success = 60;
                    } else if (leve == 3) {
                        success = 50;
                    } else if (leve == 4) {
                        success = 40;
                    } else if (leve == 5) {
                        success = 30;
                    } else if (leve == 6) {
                        success = 20;
                    } else if (leve == 7) {
                        success = 10;
                    } else if (leve >= 8) {
                        success = 5;
                    }
                    return success;
                }
            }
        }
        switch (itemId) {
            case 2046841:
            case 2046842:
            case 2046967:
            case 2046971:
            case 2047803:
            case 2047917: {
                return 20;
            }
        }
        if (equip == null) {
            System.err.println("[Error] A null value was entered for the Equipment Item value while trying to obtain the Probability of Success." + itemId);
            player.message(5, "[Error] Failed to get Probability for Current Order.");
            player.gainItem(itemId, (short) 1, false, -1, "Order book obtained from Failure to Obtain Probability");
            player.ea();
            return 0;
        }
        if (successCache.containsKey(itemId)) {
            return successCache.get(itemId);
        }

        final MapleData item = getItemData(itemId);
        if (item == null) {
            System.err.println("[ERROR] Null value was entered in order data value while trying to find the probability of success of the order." + itemId);
            player.message(5, "[ERROR] Failed to get Probability for Current Order.");
            player.gainItem(itemId, (short) 1, false, -1, "Order book obtained from Failure to Obtain Probability");
            player.ea();
            return 0;
        }
        int success = 0;
        if (item.getChildByPath("info/successRates") != null) {
            success = MapleDataTool.getIntConvert(t.getLevel() + "", item.getChildByPath("info/successRates"), 20);
        } else {
            success = MapleDataTool.getIntConvert("info/success", item, 100);
        }
        if (!GameConstants.isEpicScroll(itemId) && !GameConstants.isPotentialScroll(itemId)
                && !GameConstants.isEquipScroll(itemId)) {
            if (ItemFlag.LUKCYDAY.check(t.getFlag())) {
                success += 10;
            }
        }
        successCache.put(itemId, success);
        return success;
    }

    public final int getCursed(final int itemId, final MapleCharacter player) {
        return getCursed(itemId, player, null);
    }

    public final int getCursed(final int itemId, final MapleCharacter player, IItem equip) {
        // if (player.getGMLevel() > 0) {
        // return -1;
        // }
        if (cursedCache.containsKey(itemId)) {
            return cursedCache.get(itemId);
        }
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return -1;
        }
        int success = 0;
        success = MapleDataTool.getIntConvert("info/cursed", item, -1);
        cursedCache.put(itemId, success);
        return success;
    }

    public final boolean isAccountShared(final int itemId) {
        if (accCache.containsKey(itemId)) {
            return accCache.get(itemId);
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/accountSharable", getItemData(itemId), 0) == 1;

        accCache.put(itemId, bRestricted);
        return bRestricted;
    }

    public final int getStateChangeItem(final int itemId) {
        if (stateChangeCache.containsKey(itemId)) {
            return stateChangeCache.get(itemId);
        }
        final int triggerItem = MapleDataTool.getIntConvert("info/stateChangeItem", getItemData(itemId), 0);
        stateChangeCache.put(itemId, triggerItem);
        return triggerItem;
    }

    public final int getAndroid(final int itemId) {
        if (androidCache.containsKey(itemId)) {
            return androidCache.get(itemId);
        }
        final int i = MapleDataTool.getIntConvert("info/android", getItemData(itemId), 0);

        androidCache.put(itemId, i);
        return i;
    }

    public final BasicAndroid getAndroidBasicSettings(final int android) {
        if (androidBasicCache.containsKey(android)) {
            return androidBasicCache.get(android);
        }
        List<Integer> hairs = new LinkedList<Integer>();
        List<Integer> faces = new LinkedList<Integer>();
        String name = StringUtil.getLeftPaddedStr(android + "", '0', 4) + ".img";
        MapleData root = etcData.getData("Android/" + name);
        if (root == null) {
            System.err.println("[ERROR] Android default settings saved in Etc.wz on server " + name + " File not found.");
            return null;
        }
        MapleData costume = root.getChildByPath("costume");
        for (MapleData d : costume.getChildByPath("hair")) {
            hairs.add(MapleDataTool.getIntConvert(d));
        }
        for (MapleData d : costume.getChildByPath("face")) {
            faces.add(MapleDataTool.getIntConvert(d));
        }
        final BasicAndroid basicAnd = new BasicAndroid(hairs, faces,
                MapleDataTool.getIntConvert(root.getChildByPath("info/gender")));
        androidBasicCache.put(android, basicAnd);
        return basicAnd;
    }

    public final int getBagType(final int itemid) {
        if (bagTypeCache.containsKey(itemid)) {
            return bagTypeCache.get(itemid);
        }
        int prefix = itemid / 10000;
        String name = StringUtil.getLeftPaddedStr(prefix + "", '0', 4) + ".img";
        MapleData d = itemData.getData("Etc/" + name);
        if (d == null) {
            return 0;
        }
        name = StringUtil.getLeftPaddedStr(itemid + "", '0', 8);
        d = d.getChildByPath(name + "/info/bagType");
        int ret = MapleDataTool.getInt(d, 0);
        bagTypeCache.put(itemid, ret);
        return ret;
    }

    public final boolean isKarmaEnabled(final int itemId) {
        if (karmaEnabledCache.containsKey(itemId)) {
            return karmaEnabledCache.get(itemId) == 1;
        }
        final int iRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", getItemData(itemId), 0);

        karmaEnabledCache.put(itemId, iRestricted);
        return iRestricted == 1;
    }

    public final boolean isPKarmaEnabled(final int itemId) {
        if (karmaEnabledCache.containsKey(itemId)) {
            return karmaEnabledCache.get(itemId) == 2;
        }
        final int iRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", getItemData(itemId), 0);

        karmaEnabledCache.put(itemId, iRestricted);
        return iRestricted == 2;
    }

    public final void cacheEquipLevelStat(int itemid) {
        final MapleData item = getItemData(itemid);
        if (item == null) {
            return;
        }
        final MapleData info = item.getChildByPath("info/level");
        if (info == null) {
            nonLevelEquip.add(itemid);
            return;
        }
        final Map<Integer, StructEquipLevel> el = new HashMap<Integer, StructEquipLevel>();
        StructEquipLevel sel;
        for (final MapleData data : info.getChildByPath("info").getChildren()) {
            sel = new StructEquipLevel();
            sel.incSTRMax = (byte) MapleDataTool.getInt("incSTRMax", data, 0);
            sel.incSTRMin = (byte) MapleDataTool.getInt("incSTRMin", data, 0);

            sel.incDEXMax = (byte) MapleDataTool.getInt("incDEXMax", data, 0);
            sel.incDEXMin = (byte) MapleDataTool.getInt("incDEXMin", data, 0);

            sel.incLUKMax = (byte) MapleDataTool.getInt("incLUKMax", data, 0);
            sel.incLUKMin = (byte) MapleDataTool.getInt("incLUKMin", data, 0);

            sel.incINTMax = (byte) MapleDataTool.getInt("incINTMax", data, 0);
            sel.incINTMin = (byte) MapleDataTool.getInt("incINTMin", data, 0);

            sel.incPADMax = (byte) MapleDataTool.getInt("incPADMax", data, 0);
            sel.incPADMin = (byte) MapleDataTool.getInt("incPADMin", data, 0);

            sel.incMADMax = (byte) MapleDataTool.getInt("incMADMax", data, 0);
            sel.incMADMin = (byte) MapleDataTool.getInt("incMADMin", data, 0);

            sel.incMHPMax = (byte) MapleDataTool.getInt("incMHPMax", data, 0);
            sel.incMHPMin = (byte) MapleDataTool.getInt("incMHPMin", data, 0);

            el.put(Integer.parseInt(data.getName()), sel);
        }
        equipLevelMaxLevel.put(itemid, el.size());
        equipLevelCache.put(itemid, el);
    }

    public final StructEquipLevel getEquipLevelStat(final int itemid, final int level) {
        if (equipLevelCache.containsKey(itemid)) {
            if (equipLevelCache.get(itemid).containsKey(level)) {
                return equipLevelCache.get(itemid).get(level);
            }
        }
        cacheEquipLevelStat(itemid);
        return equipLevelCache.get(itemid).get(level);
    }

    public final int getMaxLevelEquip(int itemid) {
        if (nonLevelEquip.contains(itemid)) {
            return 0;
        }
        if (!equipLevelMaxLevel.containsKey(itemid)) {
            cacheEquipLevelStat(itemid);
        }
        if (equipLevelMaxLevel.containsKey(itemid)) {
            return equipLevelMaxLevel.get(itemid);
        }
        return 0;
    }

    public final Map<String, Byte> getItemMakeStats(final int itemId) {
        if (itemMakeStatsCache.containsKey(itemId)) {
            return itemMakeStatsCache.get(itemId);
        }
        if (itemId / 10000 != 425) {
            return null;
        }
        final Map<String, Byte> ret = new LinkedHashMap<String, Byte>();
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        ret.put("incPAD", (byte) MapleDataTool.getInt("incPAD", info, 0)); // WATK
        ret.put("incMAD", (byte) MapleDataTool.getInt("incMAD", info, 0)); // MATK
        ret.put("incACC", (byte) MapleDataTool.getInt("incACC", info, 0)); // ACC
        ret.put("incEVA", (byte) MapleDataTool.getInt("incEVA", info, 0)); // AVOID
        ret.put("incSpeed", (byte) MapleDataTool.getInt("incSpeed", info, 0)); // SPEED
        ret.put("incJump", (byte) MapleDataTool.getInt("incJump", info, 0)); // JUMP
        ret.put("incMaxHP", (byte) MapleDataTool.getInt("incMaxHP", info, 0)); // HP
        ret.put("incMaxMP", (byte) MapleDataTool.getInt("incMaxMP", info, 0)); // MP
        ret.put("incSTR", (byte) MapleDataTool.getInt("incSTR", info, 0)); // STR
        ret.put("incINT", (byte) MapleDataTool.getInt("incINT", info, 0)); // INT
        ret.put("incLUK", (byte) MapleDataTool.getInt("incLUK", info, 0)); // LUK
        ret.put("incDEX", (byte) MapleDataTool.getInt("incDEX", info, 0)); // DEX
        ret.put("randOption", (byte) MapleDataTool.getInt("randOption", info, 0)); // Black Crystal Wa/MA
        ret.put("randStat", (byte) MapleDataTool.getInt("randStat", info, 0)); // Dark Crystal - Str/Dex/int/Luk

        itemMakeStatsCache.put(itemId, ret);
        return ret;
    }

    public final Map<String, Integer> getEquipStats(final int itemId) {
        if (equipStatsCache.containsKey(itemId)) {
            return equipStatsCache.get(itemId);
        }
        final Map<String, Integer> ret = new LinkedHashMap<String, Integer>();
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        for (final MapleData data : info.getChildren()) {
            if (data.getName().startsWith("inc")) {
                ret.put(data.getName().substring(3), (int) MapleDataTool.getIntConvert(data));
            }
        }
        ret.put("tuc", MapleDataTool.getInt("tuc", info, 0));
        ret.put("reqLevel", MapleDataTool.getInt("reqLevel", info, 0));
        ret.put("reqJob", MapleDataTool.getInt("reqJob", info, 0));
        ret.put("reqSTR", MapleDataTool.getInt("reqSTR", info, 0));
        ret.put("reqDEX", MapleDataTool.getInt("reqDEX", info, 0));
        ret.put("reqINT", MapleDataTool.getInt("reqINT", info, 0));
        ret.put("reqLUK", MapleDataTool.getInt("reqLUK", info, 0));
        ret.put("reqPOP", MapleDataTool.getInt("reqPOP", info, 0));
        ret.put("cash", MapleDataTool.getInt("cash", info, 0));
        ret.put("canLevel", info.getChildByPath("level") == null ? 0 : 1);
        ret.put("cursed", MapleDataTool.getInt("cursed", info, 0));
        ret.put("success", MapleDataTool.getInt("success", info, 0));
        ret.put("equipTradeBlock", MapleDataTool.getInt("equipTradeBlock", info, 0));
        ret.put("setItemID", MapleDataTool.getInt("setItemID", info, 0));
        ret.put("gatherTool", MapleDataTool.getInt("gatherTool", info, 0));
        ret.put("expireOnLogout", MapleDataTool.getInt("expireOnLogout", info, 0));
        ret.put("charmEXP", MapleDataTool.getInt("charmEXP", info, 0));
        ret.put("willEXP", MapleDataTool.getInt("willEXP", info, 0));
        ret.put("charismaEXP", MapleDataTool.getInt("charismaEXP", info, 0));
        ret.put("forceUpgrade", MapleDataTool.getInt("forceUpgrade", info, 1));
        ret.put("bdR", MapleDataTool.getInt("bdR", info, 0));
        ret.put("imdR", MapleDataTool.getInt("imdR", info, 0));
        if (GameConstants.isMagicWeapon(itemId)) {
            ret.put("elemDefault", MapleDataTool.getInt("elemDefault", info, 100));
            ret.put("incRMAS", MapleDataTool.getInt("incRMAS", info, 100)); // Poison
            ret.put("incRMAF", MapleDataTool.getInt("incRMAF", info, 100)); // Fire
            ret.put("incRMAL", MapleDataTool.getInt("incRMAL", info, 100)); // Lightning
            ret.put("incRMAI", MapleDataTool.getInt("incRMAI", info, 100)); // Ice
        }

        equipStatsCache.put(itemId, ret);
        return ret;
    }

    public final boolean canEquip(final Map<String, Integer> stats, final int itemid, final int level, final int job,
            final int fame, final int str, final int dex, final int luk, final int int_) {
        if (level >= stats.get("reqLevel") && str >= stats.get("reqSTR") && dex >= stats.get("reqDEX")
                && luk >= stats.get("reqLUK") && int_ >= stats.get("reqINT")) {
            final int fameReq = stats.get("reqPOP");
            if (fameReq != 0 && fame < fameReq) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int getReqLevel(final int itemId) {
        try {
                
             return getEquipStats(itemId).get("reqLevel");
            
            
        } catch (Exception ex) {
            return 0;
        }
    }

    public final List<Integer> getScrollReqs(final int itemId) {
        final List<Integer> ret = new ArrayList<Integer>();
        final MapleData data = getItemData(itemId).getChildByPath("req");

        if (data == null) {
            return ret;
        }
        for (final MapleData req : data.getChildren()) {
            ret.add(MapleDataTool.getInt(req));
        }
        return ret;
    }

    public final IItem scrollEquipWithId(final IItem equip, final IItem scrollId, final boolean ws,
            final MapleCharacter chr) {
        if (equip.getType() == 1) {
            Equip nEquip = (Equip) equip;
            IEquip zeroEquip = null;
            if (GameConstants.isAlphaWeapon(nEquip.getItemId())) {
                zeroEquip = (IEquip) chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
            }
            Equip nZero = (Equip) zeroEquip;
            final Map<String, Integer> stats = getEquipStats(scrollId.getItemId());
            final Map<String, Integer> eqstats = getEquipStats(equip.getItemId());
            boolean failed = false;
            switch (scrollId.getItemId()) {
                case 2049000: // White Order 1%
                case 2049001: // White Order 3%
                case 2049002: // White Order 5%
                case 2049004: // White Order 10%
                case 2049005: // White Order 20%
                case 2049009:
                case 2049010:
                case 2049011:
                case 2049012:
                case 2049013:
                case 2049014:
                case 2049015:
                case 2049016:
                case 2049017:
                case 2049018:
                case 2049019:
                case 2049020:
                case 2049021:
                case 2049022:
                case 2049023:
                case 2049024:
                case 2049025:
                case 2049026:
                case 2049027:
                case 2049028:
                case 2049029:
                case 2049030:
                case 2049031:
                case 2049032:
                case 2049033:
                case 2049034:
                case 2049035:
                case 2049036:
                case 2049037:
                case 2049038:
                case 2049039:
                case 2049040: {
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip))) {
                        if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                            if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                nEquip.setAmazing(false);
                            } else {
                                return null; // Æã
                            }
                        }
                        failed = true;
                    } else if (nEquip.getLevel()
                            + nEquip.getUpgradeSlots() < (eqstats.get("tuc") + (nEquip.getViciousHammer() > 0 ? 1 : 0))) {
                        nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 1));
                    } else {
                        chr.dropMessage(5, "The order did not work because no order failure was found.");
                    }
                    break;
                }
                case 2049006: // Order of the Cursed White 1%
                case 2049007: // Order of the Cursed White 3%
                case 2049008: // Order of the Cursed White 5%
                {
                    if (nEquip.getLevel() + nEquip.getUpgradeSlots() < eqstats.get("tuc")) {
                        nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 2));
                    }
                    break;
                }
                case 2040727: // Shoe Spikes Scroll 10%-Added non-slip option to shoes. Success rate: 10%, no impact on upgrades
                {
                    short flag = nEquip.getFlag();
                    flag |= ItemFlag.SPIKES.getValue();
                    nEquip.setFlag(flag);
                    break;
                }
                case 2041058: // Cloak Winter Scroll 10%-Added a cold protection option to your cloak
                {
                    short flag = nEquip.getFlag();
                    flag |= ItemFlag.COLD.getValue();
                    nEquip.setFlag(flag);
                    break;
                }
                /* 8 äÅ Order, Justice Order */
                case 2046025:
                case 2046026:
                case 2046340:
                case 2046119:
                case 2046341:
                case 2046120:
                case 2046251:
                case 2046054:
                case 2046055:
                case 2046056:
                case 2046057:
                case 2046058:
                case 2046059:
                case 2046138:
                case 2046139:
                case 2046140:
                case 2046374: //Fragment of Twisted Time
                case 2046127: //Legendary Two-Handed Weapon Special Order
                case 2046032: //Legendary One-Handed Weapon Special Order
                case 2046889: //Limited Access Boost Order
                case 2047972: //Super Armor Amplification Scroll
                case 2046094:
                case 2046095:
                case 2046162:
                case 2046163:
                case 2046564:
                case 2046856:
                case 2046857:
                case 2047930:
                case 2047931:
                case 2048094:
                case 2048095:
                case 2046991:
                case 2046992:
                case 2046814:
                case 2047814:
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip))) {
                        if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                            if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                nEquip.setAmazing(false);
                            } else {
                                return null; // Æã
                            }
                        }
                        failed = true;
                    } else {

                        int as = Randomizer.rand(10, 30);
                        switch (scrollId.getItemId()) {
                            case 2046025: // 8 äÅ One-Hand Attack Damage Scroll 20%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(7, 8)));
                                break;
                            case 2046026: // 8 äÅ One-Hand Weapon Spell 20%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.rand(7, 8)));
                                break;
                            case 2046340: // 8 äÅ Accessory Spell 20%
                                nEquip.setWatk((short) (nEquip.getWatk() + 1));
                                break;
                            case 2046341: // 8 äÅ Accessory Magic Spell 20%
                                nEquip.setMatk((short) (nEquip.getMatk() + 1));
                                break;
                            case 2046119: // 8 äÅ Two-Handed Weapon Attack Scroll 20%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(7, 8)));
                                break;
                            case 2046120: // 8 äÅ Two-Handed Magic Power Order 20%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.rand(7, 8)));
                                break;
                            case 2046251: // 8 äÅ Armor Boost 20%
                                nEquip.setStr((short) (nEquip.getStr() + 3));
                                nEquip.setInt((short) (nEquip.getInt() + 3));
                                nEquip.setDex((short) (nEquip.getDex() + 3));
                                nEquip.setLuk((short) (nEquip.getLuk() + 3));
                                break;
                            case 2046054: // Justice One-Handed Weapon Attack Scroll 20%
                            case 2046055: // Justice One-Hand Weapon Spell 20%
                            case 2046056: // Justice One-Handed Weapon Attack Scroll 40%
                            case 2046057: // Justice One-Handed Weapon Spell 40%
                            case 2046138: // Justice Two-Handed Weapon Attack Scroll 20%
                            case 2046139: // Justice Two-Handed Weapon Attack Scroll 40%
                                if (scrollId.getItemId() == 2046055 || scrollId.getItemId() == 2046057) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + 5));
                                } else {
                                    nEquip.setWatk((short) (nEquip.getWatk() + 5));
                                }
                                nEquip.setStr((short) (nEquip.getStr() + 3));
                                nEquip.setDex((short) (nEquip.getDex() + 3));
                                nEquip.setInt((short) (nEquip.getInt() + 3));
                                nEquip.setLuk((short) (nEquip.getLuk() + 3));
                                nEquip.setAcc((short) (nEquip.getAcc() + 15));
                                break;
                            case 2046058: // Justice One-Handed Weapon Attack Scroll 70%
                            case 2046059: // Justice One-Hand Weapon Spell 70%
                            case 2046140: // Justice Two-Handed Weapon Attack Scroll 70%
                                if (scrollId.getItemId() == 2046059) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + 2));
                                } else {
                                    nEquip.setWatk((short) (nEquip.getWatk() + 2));
                                }
                                nEquip.setStr((short) (nEquip.getStr() + 1));
                                nEquip.setDex((short) (nEquip.getDex() + 1));
                                nEquip.setInt((short) (nEquip.getInt() + 1));
                                nEquip.setLuk((short) (nEquip.getLuk() + 1));
                                nEquip.setAcc((short) (nEquip.getAcc() + 5));
                                break;
                            case 2046127: //Legendary Two-Handed Weapon Special Order
                                nEquip.setAllStatP((byte) 10); // All Stats
                                break;
                            case 2046032: // Legendary One-Handed Weapon Special Order
                                nEquip.setAllStatP((byte) 10); // All Stats
                                break;
                            case 2047972: // Super Armor Amplification Scroll
                                nEquip.setAllStatP((byte) 10); // All Stats
                                break;
                            case 2046889: // Limited Access Boost Order
                                nEquip.setAllStatP((byte) 10); // All Stats
                                break;
                            case 2046374: //  Fragment of Twisted Time
                                nEquip.setWatk((short) (nEquip.getWatk() + 20));
                                nEquip.setMatk((short) (nEquip.getMatk() + 20));
                                nEquip.setWdef((short) (nEquip.getWdef() + 25));
                                nEquip.setMdef((short) (nEquip.getMdef() + 25));
                                nEquip.setStr((short) (nEquip.getStr() + 30));
                                nEquip.setDex((short) (nEquip.getDex() + 30));
                                nEquip.setInt((short) (nEquip.getInt() + 30));
                                nEquip.setLuk((short) (nEquip.getLuk() + 30));
                                nEquip.setAvoid((short) (nEquip.getAvoid() + 30));
                                nEquip.setAcc((short) (nEquip.getAcc() + 30));
                                nEquip.setSpeed((short) (nEquip.getSpeed() + 3));
                                nEquip.setJump((short) (nEquip.getJump() + 2));
                                nEquip.setMp((short) (nEquip.getMp() + 1000));
                                nEquip.setHp((short) (nEquip.getHp() + 1000));
                                break;
                            case 2046094: // 9th Anniversary One-Hand Damage Order Scroll 10%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.ScrollRand(7, 9)));
                                break;
                            case 2046095: // 9th Anniversary One-Hand Weapon Spell 10%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.ScrollRand(7, 9)));
                                break;
                            case 2047930:
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(1, 15)));
                                break;
                            case 2047931:
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.rand(1, 15)));
                                break;
                            case 2046991:

                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(15, 40)));
                                nEquip.setStr((short) (nEquip.getStr() + as));
                                nEquip.setDex((short) (nEquip.getDex() + as));
                                nEquip.setInt((short) (nEquip.getInt() + as));
                                nEquip.setLuk((short) (nEquip.getLuk() + as));
                                break;
                            case 2046992:
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.rand(15, 40)));
                                nEquip.setStr((short) (nEquip.getStr() + as));
                                nEquip.setDex((short) (nEquip.getDex() + as));
                                nEquip.setInt((short) (nEquip.getInt() + as));
                                nEquip.setLuk((short) (nEquip.getLuk() + as));
                                break;
                            case 2047814:

                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(15, 40)));
                                nEquip.setStr((short) (nEquip.getStr() + as));
                                nEquip.setDex((short) (nEquip.getDex() + as));
                                nEquip.setInt((short) (nEquip.getInt() + as));
                                nEquip.setLuk((short) (nEquip.getLuk() + as));
                                break;
                            case 2046162: // 9th Anniversary Two-Handed Weapon Damage Scroll 10%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.ScrollRand(7, 9)));
                                break;
                            case 2046163: // 9th Anniversary Two-Handed Weapon Power Scroll 10%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.ScrollRand(7, 9)));
                                break;
                            case 5530336: // Accessories Damage Scroll 100%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.ScrollRand(2, 4)));
                                break;
                            case 5530337: // Accessories Magic Scroll 100%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.ScrollRand(2, 4)));
                                break;
                            case 5530338: // Pet Equipment Attack Scroll 100%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.ScrollRand(2, 4)));
                                break;
                            case 5530339: // Pet Equipment Scroll 100%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.ScrollRand(2, 4)));
                                break;
                            case 2048094: // Premium Pet Equipment Attack Scroll 100%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(1, 15)));
                                break;
                            case 2046856: // Premium Accessories Attack Scroll 100%
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.rand(10, 15)));
                                break;
                            case 2048095: // Premium Pet Equipment Power Order 100%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.rand(1, 15)));
                                break;
                            case 2046857: // Premium Accessory Magic Scroll 100%
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.rand(10, 15)));
                                break;
                            case 2046564: // 9th Anniversary Armor Spell 10%
                                nEquip.setStr((short) (nEquip.getStr() + 5));
                                nEquip.setInt((short) (nEquip.getInt() + 5));
                                nEquip.setDex((short) (nEquip.getDex() + 5));
                                nEquip.setLuk((short) (nEquip.getLuk() + 5));
                                break;
                        }
                    }
                    break;
                case 2046841:
                case 2046842:
                case 2046967:
                case 2046971:
                case 2047803:
                case 2047917:
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip))) {
                        if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                            if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                nEquip.setAmazing(false);
                            } else {
                                return null; // pop
                            }
                        }
                        failed = true;
                    } else {
                        switch (scrollId.getItemId()) {
                            case 2046841:
                                nEquip.setWatk((short) (nEquip.getWatk() + 1));
                                break;
                            case 2046842:
                                nEquip.setMatk((short) (nEquip.getMatk() + 1));
                                break;
                            case 2046967:
                                nEquip.setWatk((short) (nEquip.getWatk() + 9));
                                nEquip.setStr((short) (nEquip.getStr() + 3));
                                nEquip.setInt((short) (nEquip.getInt() + 3));
                                nEquip.setDex((short) (nEquip.getDex() + 3));
                                nEquip.setLuk((short) (nEquip.getLuk() + 3));
                                break;
                            case 2046971:
                                nEquip.setMatk((short) (nEquip.getMatk() + 9));
                                nEquip.setStr((short) (nEquip.getStr() + 3));
                                nEquip.setInt((short) (nEquip.getInt() + 3));
                                nEquip.setDex((short) (nEquip.getDex() + 3));
                                nEquip.setLuk((short) (nEquip.getLuk() + 3));
                                break;
                            case 2047803:
                                nEquip.setWatk((short) (nEquip.getWatk() + 9));
                                nEquip.setStr((short) (nEquip.getStr() + 3));
                                nEquip.setInt((short) (nEquip.getInt() + 3));
                                nEquip.setDex((short) (nEquip.getDex() + 3));
                                nEquip.setLuk((short) (nEquip.getLuk() + 3));
                                break;
                            case 2047917:
                                nEquip.setStr((short) (nEquip.getStr() + 9));
                                nEquip.setInt((short) (nEquip.getInt() + 9));
                                nEquip.setDex((short) (nEquip.getDex() + 9));
                                nEquip.setLuk((short) (nEquip.getLuk() + 9));
                                break;
                        }
                    }
                    break;
                case 2049701:
                case 2049702:
                case 2049703:
                case 2049700:
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                        if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                            if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                nEquip.setAmazing(false);
                            } else {
                                return null; // Æã
                            }
                        }
                        failed = true;
                    } else if (nEquip.getState() <= 17) {
                        nEquip.setState((byte) 2);
                        if (Randomizer.nextInt(100) < 30) {
                            nEquip.setLines((byte) 3);
                        } else {
                            nEquip.setLines((byte) 2);
                        }
                    }
                    break;
                case 2049750:
                case 2049751:
                case 2049752:
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                        if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                            if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                nEquip.setAmazing(false);
                            } else {
                                return null; // pop
                            }
                        }
                        failed = true;
                    } else if (nEquip.getState() <= 19) {
                        nEquip.setState((byte) 3);
                        if (Randomizer.nextInt(100) < 30) {
                            nEquip.setLines((byte) 3);
                        } else {
                            nEquip.setLines((byte) 2);
                        }
                    }
                    break;
                case 2531000: // Protect
                {
                    short flag = nEquip.getFlag();
                    flag |= ItemFlag.PROTECT.getValue();
                    nEquip.setFlag(flag);
                    break;
                }
                case 2532000: // Safety
                {
                    short flag = nEquip.getFlag();
                    flag |= ItemFlag.SAFETY.getValue();
                    nEquip.setFlag(flag);
                    break;
                }

                case 5064300: // Recovery
                {
                    short flag = nEquip.getFlag();
                    flag |= ItemFlag.RECOVERY.getValue();
                    nEquip.setFlag(flag);
                    break;
                }
                case 5063000:
                case 2049704: {// Legend Potential Order
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                        failed = true;
                    } else if (nEquip.getState() <= 20) {
                        nEquip.setState((byte) 4);
                        if (Randomizer.nextInt(100) < 30) {
                            nEquip.setLines((byte) 3);
                        } else {
                            nEquip.setLines((byte) 2);
                        }
                    }
                    break;
                }
                case 2049401: {// Legend Potential Order
                    if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                        failed = true;
                    } else if (nEquip.getState() <= 10) {
                        nEquip.setState((byte) 4);
                        if (Randomizer.nextInt(100) < 15) {
                            nEquip.setLines((byte) 3);
                        } else {
                            nEquip.setLines((byte) 2);
                        }
                    }
                    break;
                }
                case 2530000:
                case 2530001:
                case 2530002: // Lucky Day
                {
                    short flag = nEquip.getFlag();
                    flag |= ItemFlag.LUKCYDAY.getValue();
                    nEquip.setFlag(flag);
                    break;
                }

                case 5064200: {
                    Equip origin = (Equip) ItemInformation.getInstance().getEquipById(nEquip.getItemId());
                    nEquip.setAcc(origin.getAcc());
                    nEquip.setAvoid(origin.getAvoid());
                    nEquip.setDex(origin.getDex());
                    nEquip.setHands(origin.getHands());
                    nEquip.setHp(origin.getHp());
                    nEquip.setHpR(origin.getHpR());
                    nEquip.setInt(origin.getInt());
                    nEquip.setJump(origin.getJump());
                    nEquip.setLevel(origin.getLevel());
                    nEquip.setLuk(origin.getLuk());
                    nEquip.setMatk(origin.getMatk());
                    nEquip.setMdef(origin.getMdef());
                    nEquip.setMp(origin.getMp());
                    nEquip.setMpR(origin.getMpR());
                    nEquip.setSpeed(origin.getSpeed());
                    nEquip.setStr(origin.getStr());
                    nEquip.setUpgradeSlots(origin.getUpgradeSlots());
                    nEquip.setWatk(origin.getWatk());
                    nEquip.setWdef(origin.getWdef());
                    nEquip.setEnhance((byte) 0);
                    nEquip.setViciousHammer((byte) 0);
                    nEquip.setFireStat(new int[16]);
                    nEquip.setAllDamageP(origin.getAllDamageP());
                    nEquip.setIgnoreWdef(origin.getIgnoreWdef());
                    nEquip.setBossDamage(origin.getBossDamage());
                    nEquip.setAllStatP(origin.getAllStatP());
                    nEquip.setDownLevel(origin.getDownLevel());
                    break;
                }
                default: {
                    if (GameConstants.isChaosScroll(scrollId.getItemId())) {
                        if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                            if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr, nEquip))) {
                                if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                    chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                    nEquip.setAmazing(false);
                                } else {
                                    return null; // Æã
                                }
                            }
                            failed = true;
                        } else {
                            final int z = GameConstants.getChaosNumber(scrollId.getItemId());
                            final boolean a = scrollId.getItemId() == 2049137; // General Positive Chaos Order
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short) (nEquip.getStr() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short) (nEquip.getDex() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short) (nEquip.getInt() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short) (nEquip.getHp() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short) (nEquip.getMp() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                            }
                        }
                        final int z = GameConstants.getChaosNumber1(scrollId.getItemId());
                        final boolean a = scrollId.getItemId() == 2049153; //Fine Positive Chaos Spellbook
                        if (nEquip.getStr() > 0) {
                            nEquip.setStr((short) (nEquip.getStr() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getDex() > 0) {
                            nEquip.setDex((short) (nEquip.getDex() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getInt() > 0) {
                            nEquip.setInt((short) (nEquip.getInt() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getLuk() > 0) {
                            nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getWatk() > 0) {
                            nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getMatk() > 0) {
                            nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getHp() > 0) {
                            nEquip.setHp((short) (nEquip.getHp() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        if (nEquip.getMp() > 0) {
                            nEquip.setMp((short) (nEquip.getMp() + Randomizer.nextInt(z) * (a ? 1 : Randomizer.nextBoolean() ? 1 : 1)));
                        }
                        break;
                    } else if (scrollId.getItemId() == 2049370) {
                        for (int i = 0; i < 12; i++) {
                            int at = 0;
                            int aps = 0;
                            boolean weapon = GameConstants.isWeapon(nEquip.getItemId());
                            switch (nEquip.getEnhance()) {// Cathedral Reinforcement Chance
                                case 0:
                                    at = 19;
                                    aps = weapon ? 6 : 0;
                                    break;
                                case 1:
                                    at = 21;
                                    aps = weapon ? 6 : 0;
                                    break;
                                case 2:
                                    at = 23;
                                    aps = weapon ? 7 : 0;
                                    break;
                                case 3:
                                    at = 25;
                                    aps = weapon ? 7 : 0;
                                    break;
                                case 4:
                                    at = 27;
                                    aps = weapon ? 8 : 0;
                                    break;
                                case 5:
                                    aps = weapon ? 11 : 8;
                                    break;
                                case 6:
                                    aps = weapon ? 15 : 9;
                                    break;
                                case 7:
                                    aps = weapon ? 17 : 10;
                                    break;
                                case 8:
                                    aps = weapon ? 18 : 11;
                                    break;
                                case 9:
                                    aps = weapon ? 19 : 12;
                                    break;
                                case 10:
                                    aps = weapon ? 22 : 13;
                                    break;
                                case 11:
                                    aps = weapon ? 23 : 14;
                                    break;
                                case 12:
                                    aps = weapon ? 25 : 15;
                                    break;
                                case 13:
                                    aps = weapon ? 26 : 16;
                                    break;
                                case 14:
                                    aps = weapon ? 27 : 17;
                                    break;
                                default: // 13 stars or more
                                    break;
                            }
                            if (ItemInformation.getInstance().getReqLevel(nEquip.getItemId()) < 150) {
                                if (at > 0) {
                                    at -= ((150 - ItemInformation.getInstance().getReqLevel(nEquip.getItemId())) / 10) * 2;
                                }
                                if (aps > 0) {
                                    aps -= ((150 - ItemInformation.getInstance().getReqLevel(nEquip.getItemId())) / 10);
                                }
                            }
                            nEquip.setStr((short) (nEquip.getStr() + at));
                            nEquip.setDex((short) (nEquip.getDex() + at));
                            nEquip.setInt((short) (nEquip.getInt() + at));
                            nEquip.setLuk((short) (nEquip.getLuk() + at));
                            nEquip.setWatk((short) (nEquip.getWatk() + aps));
                            nEquip.setMatk((short) (nEquip.getMatk() + aps));
                            nEquip.setEnhance((byte) (nEquip.getEnhance() + 1));
                        }
                    } else if (scrollId.getItemId() == 2049371) {
                        int fo = scrollId.getItemId() == 2049371 ? 17 : 12;
                        for (int i = 0; i < fo; i++) {
                            StarForceSystem.StarForceStat(chr.getClient(), nEquip, null, i);
                            StarForceSystem.StarSystemUpgrade(nEquip, 1, chr);
                        }
                    } else if (GameConstants.isEquipScroll(scrollId.getItemId())) {
                        if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                            if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing() || nEquip.isAmazing()) {
                                chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                nEquip.setAmazing(false);
                            } else if (scrollId.getItemId() != 2049323) {
                                return null; // Æã
                            }
                            failed = true;
                        } else {
                            for (int i = 1; i <= MapleDataTool.getIntConvert("info/forceUpgrade",
                                    getItemData(scrollId.getItemId()), 1); i++) {
                                if (GameConstants.isSuperior(nEquip.getItemId())) {
                                    int slevel = getReqLevel(nEquip.getItemId());
                                    int senhance = nEquip.getEnhance();
                                    if (senhance < 1) { // 0 -> 1
                                        nEquip.setStr((short) (nEquip.getStr() + (slevel > 140 ? 19 : slevel > 100 ? 9 : slevel > 70 ? 2 : 1)));
                                        nEquip.setDex((short) (nEquip.getDex() + (slevel > 140 ? 19 : slevel > 100 ? 9 : slevel > 70 ? 2 : 1)));
                                        nEquip.setInt((short) (nEquip.getInt() + (slevel > 140 ? 19 : slevel > 100 ? 9 : slevel > 70 ? 2 : 1)));
                                        nEquip.setLuk((short) (nEquip.getLuk() + (slevel > 140 ? 19 : slevel > 100 ? 9 : slevel > 70 ? 2 : 1)));
                                        nEquip.setEnhance((byte) 1);
                                    } else if (senhance == 1) { // 1 -> 2
                                        nEquip.setStr((short) (nEquip.getStr() + (slevel > 140 ? 20 : slevel > 100 ? 10 : slevel > 70 ? 3 : 2)));
                                        nEquip.setDex((short) (nEquip.getDex() + (slevel > 140 ? 20 : slevel > 100 ? 10 : slevel > 70 ? 3 : 2)));
                                        nEquip.setInt((short) (nEquip.getInt() + (slevel > 140 ? 20 : slevel > 100 ? 10 : slevel > 70 ? 3 : 2)));
                                        nEquip.setLuk((short) (nEquip.getLuk() + (slevel > 140 ? 20 : slevel > 100 ? 10 : slevel > 70 ? 3 : 2)));
                                        nEquip.setEnhance((byte) 2);
                                    } else if (senhance == 2) { // 2 -> 3
                                        nEquip.setStr((short) (nEquip.getStr() + (slevel > 140 ? 22 : slevel > 100 ? 12 : slevel > 70 ? 5 : 4)));
                                        nEquip.setDex((short) (nEquip.getDex() + (slevel > 140 ? 22 : slevel > 100 ? 12 : slevel > 70 ? 5 : 4)));
                                        nEquip.setInt((short) (nEquip.getInt() + (slevel > 140 ? 22 : slevel > 100 ? 12 : slevel > 70 ? 5 : 4)));
                                        nEquip.setLuk((short) (nEquip.getLuk() + (slevel > 140 ? 22 : slevel > 100 ? 12 : slevel > 70 ? 5 : 4)));
                                        nEquip.setEnhance((byte) 3);
                                    } else if (senhance == 3) { // 3 -> 4
                                        nEquip.setStr((short) (nEquip.getStr() + (slevel > 140 ? 25 : slevel > 100 ? 15 : slevel > 70 ? 8 : 7)));
                                        nEquip.setDex((short) (nEquip.getDex() + (slevel > 140 ? 25 : slevel > 100 ? 15 : slevel > 70 ? 8 : 7)));
                                        nEquip.setInt((short) (nEquip.getInt() + (slevel > 140 ? 25 : slevel > 100 ? 15 : slevel > 70 ? 8 : 7)));
                                        nEquip.setLuk((short) (nEquip.getLuk() + (slevel > 140 ? 25 : slevel > 100 ? 15 : slevel > 70 ? 8 : 7)));
                                        nEquip.setEnhance((byte) 4);
                                    } else if (senhance == 4) { // 4 -> 5
                                        nEquip.setStr((short) (nEquip.getStr() + (slevel > 140 ? 29 : slevel > 100 ? 19 : slevel > 70 ? 12 : 11)));
                                        nEquip.setDex((short) (nEquip.getDex() + (slevel > 140 ? 29 : slevel > 100 ? 19 : slevel > 70 ? 12 : 11)));
                                        nEquip.setInt((short) (nEquip.getInt() + (slevel > 140 ? 29 : slevel > 100 ? 19 : slevel > 70 ? 12 : 11)));
                                        nEquip.setLuk((short) (nEquip.getLuk() + (slevel > 140 ? 29 : slevel > 100 ? 19 : slevel > 70 ? 12 : 11)));
                                        nEquip.setEnhance((byte) 5);
                                    } else if (senhance == 5) { // 5 -> 6
                                        nEquip.setWatk((short) (nEquip.getWatk() + (slevel > 140 ? 9 : slevel > 100 ? 5 : slevel > 70 ? 2 : 2)));
                                        nEquip.setMatk((short) (nEquip.getMatk() + (slevel > 140 ? 9 : slevel > 100 ? 5 : slevel > 70 ? 2 : 2)));
                                        nEquip.setEnhance((byte) 6);
                                    } else if (senhance == 6) { // 6 -> 7
                                        nEquip.setWatk((short) (nEquip.getWatk() + (slevel > 140 ? 10 : slevel > 100 ? 6 : slevel > 70 ? 3 : 3)));
                                        nEquip.setMatk((short) (nEquip.getMatk() + (slevel > 140 ? 10 : slevel > 100 ? 6 : slevel > 70 ? 3 : 3)));
                                        nEquip.setEnhance((byte) 7);
                                    } else if (senhance == 7) { // 7 -> 8
                                        nEquip.setWatk((short) (nEquip.getWatk() + (slevel > 140 ? 11 : slevel > 100 ? 7 : slevel > 70 ? 4 : 5)));
                                        nEquip.setMatk((short) (nEquip.getMatk() + (slevel > 140 ? 11 : slevel > 100 ? 7 : slevel > 70 ? 4 : 5)));
                                        nEquip.setEnhance((byte) 8);
                                    } else if (senhance == 8) { // 8 -> 9
                                        nEquip.setWatk((short) (nEquip.getWatk() + (slevel > 140 ? 12 : slevel > 100 ? 8 : slevel > 70 ? 5 : 8)));
                                        nEquip.setMatk((short) (nEquip.getMatk() + (slevel > 140 ? 12 : slevel > 100 ? 8 : slevel > 70 ? 5 : 8)));
                                        nEquip.setEnhance((byte) 9);
                                    } else if (senhance == 9) { // 9 -> 10
                                        nEquip.setWatk((short) (nEquip.getWatk() + (slevel > 140 ? 13 : slevel > 100 ? 9 : slevel > 70 ? 6 : 12)));
                                        nEquip.setMatk((short) (nEquip.getMatk() + (slevel > 140 ? 13 : slevel > 100 ? 9 : slevel > 70 ? 6 : 12)));
                                        nEquip.setEnhance((byte) 10);
                                    } else {
                                        nEquip.setStr((short) (nEquip.getStr() + (slevel > 140 ? 30 : slevel > 100 ? 20 : slevel > 70 ? 15 : 10)));
                                        nEquip.setDex((short) (nEquip.getDex() + (slevel > 140 ? 30 : slevel > 100 ? 20 : slevel > 70 ? 15 : 10)));
                                        nEquip.setInt((short) (nEquip.getInt() + (slevel > 140 ? 30 : slevel > 100 ? 20 : slevel > 70 ? 15 : 10)));
                                        nEquip.setLuk((short) (nEquip.getLuk() + (slevel > 140 ? 30 : slevel > 100 ? 20 : slevel > 70 ? 15 : 10)));
                                        nEquip.setEnhance((byte) (nEquip.getEnhance() + 1));
                                    }
                                } else {
                                    if (nEquip.getStr() > 0) {
                                        nEquip.setStr((short) (nEquip.getStr() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(0, 1))));
                                    }
                                    if (nEquip.getDex() > 0) {
                                        nEquip.setDex((short) (nEquip.getDex() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(0, 1))));
                                    }
                                    if (nEquip.getInt() > 0) {
                                        nEquip.setInt((short) (nEquip.getInt() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(0, 1))));
                                    }
                                    if (nEquip.getLuk() > 0) {
                                        nEquip.setLuk((short) (nEquip.getLuk() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(0, 1))));
                                    }
                                    if (nEquip.getWatk() > 0) {
                                        nEquip.setWatk((short) (nEquip.getWatk() + (GameConstants.isWeapon(nEquip.getItemId()) ? ¹«±â¸¶°ø°ø½Ä(nEquip.getWatk()) : ¹æ¾î±¸¸¶°ø°ø½Ä(nEquip.getWatk()))));
                                    }
                                    if (nEquip.getWdef() > 0) {
                                        nEquip.setWdef((short) (nEquip.getWdef() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(1, 2))));
                                    }
                                    if (nEquip.getMatk() > 0) {
                                        nEquip.setMatk((short) (nEquip.getMatk() + (GameConstants.isWeapon(nEquip.getItemId()) ? ¹«±â¸¶°ø°ø½Ä(nEquip.getMatk()) : ¹æ¾î±¸¸¶°ø°ø½Ä(nEquip.getMatk()))));
                                    }
                                    if (nEquip.getMdef() > 0) {
                                        nEquip.setMdef((short) (nEquip.getMdef() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(1, 2))));
                                    }
                                    if (nEquip.getAcc() > 0) {
                                        nEquip.setAcc((short) (nEquip.getAcc() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(1, 2))));
                                    }
                                    if (nEquip.getAvoid() > 0) {
                                        nEquip.setAvoid((short) (nEquip.getAvoid() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(1, 2))));
                                    }
                                    if (nEquip.getHp() > 0) {
                                        nEquip.setMp((short) (nEquip.getHp() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(1, 2))));
                                    }
                                    if (nEquip.getMp() > 0) {
                                        nEquip.setMp((short) (nEquip.getMp() + getEquipLevel(getReqLevel(nEquip.getItemId()) + Randomizer.rand(1, 2))));
                                    }
                                    nEquip.setEnhance((byte) (nEquip.getEnhance() + 1)); // This is the part where it attaches
                                    if (nEquip.getEnhance() >= 8) {
                                        WorldBroadcasting.broadcastMessage(UIPacket.enforceMSG(chr.getName() + "'S " + ItemInformation.getInstance().getName(nEquip.getItemId()) + " Is(end) " + nEquip.getEnhance()  + "Successfully strengthened the castle.", 48, 5000));
                                    }
                                }
                            }
                        }
                        break;
                    } else if ((scrollId.getItemId() == 2049360) || (scrollId.getItemId() == 2049361)) {// item
                        int at = 0;
                        int aps = 0;
                        int suc = 10;
                        boolean weapon = GameConstants.isWeapon(nEquip.getItemId());
                        switch (nEquip.getEnhance()) {// Cathedral Reinforcement Chance
                            case 0:
                                at = 19;
                                aps = weapon ? 6 : 0;
                                suc = 60;
                                break;
                            case 1:
                                at = 21;
                                aps = weapon ? 6 : 0;
                                suc = 55;
                                break;
                            case 2:
                                at = 23;
                                aps = weapon ? 7 : 0;
                                suc = 50;
                                break;
                            case 3:
                                at = 25;
                                aps = weapon ? 7 : 0;
                                suc = 40;
                                break;
                            case 4:
                                at = 27;
                                aps = weapon ? 8 : 0;
                                suc = 30;
                                break;
                            case 5:
                                aps = weapon ? 11 : 8;
                                suc = 20;
                                break;
                            case 6:
                                aps = weapon ? 15 : 9;
                                suc = 19;
                                break;
                            case 7:
                                aps = weapon ? 17 : 10;
                                suc = 18;
                                break;
                            case 8:
                                aps = weapon ? 18 : 11;
                                suc = 17;
                                break;
                            case 9:
                                aps = weapon ? 19 : 12;
                                suc = 16;
                                break;
                            case 10:
                                aps = weapon ? 22 : 13;
                                suc = 14;
                                break;
                            case 11:
                                aps = weapon ? 23 : 14;
                                suc = 12;
                                break;
                            case 12:
                                aps = weapon ? 25 : 15;
                                break;
                            case 13:
                                aps = weapon ? 26 : 16;
                                break;
                            case 14:
                                aps = weapon ? 27 : 17;
                                break;
                            default: // 13 stars or more
                                break;
                        }
                        if (chr.isGM()) {
                            suc = 100;
                        }
                        if (!Randomizer.isSuccess(suc)) {
                            if (Randomizer.isSuccess(100)) {
                                if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                    chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                    nEquip.setAmazing(false);
                                } else {
                                    return null; // Æã
                                }
                            }
                            failed = true;
                        } else {
                            if (ItemInformation.getInstance().getReqLevel(nEquip.getItemId()) < 150) {
                                if (at > 0) {
                                    at -= ((150 - ItemInformation.getInstance().getReqLevel(nEquip.getItemId())) / 10) * 2;
                                }
                                if (aps > 0) {
                                    aps -= ((150 - ItemInformation.getInstance().getReqLevel(nEquip.getItemId())) / 10);
                                }
                            }
                            nEquip.setStr((short) (nEquip.getStr() + at));
                            nEquip.setDex((short) (nEquip.getDex() + at));
                            nEquip.setInt((short) (nEquip.getInt() + at));
                            nEquip.setLuk((short) (nEquip.getLuk() + at));
                            nEquip.setWatk((short) (nEquip.getWatk() + aps));
                            nEquip.setMatk((short) (nEquip.getMatk() + aps));
                            nEquip.setEnhance((byte) (nEquip.getEnhance() + 1));
                        }
                        break;
                    } else if (GameConstants.isPotentialScroll(scrollId.getItemId())) {
                        if (nEquip.getState() == 0) {
                            if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip))) {
                                if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                                    if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                        chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                        nEquip.setAmazing(false);
                                    } else {
                                        return null; // Æã
                                    }
                                }
                                failed = true;
                            } else {
                                nEquip.setState((byte) 1);
                            }
                        }
                        break;
                    } else if (GameConstants.isRebirhFireScroll(scrollId.getItemId())) {
                        if (nEquip.getFire() == -1) {
                            if (Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip))) {
                                nEquip.setFire((byte) 5);
                                if (nZero != null) {
                                    nZero.setFire((byte) 5);
                                }
                                nEquip = randomizeStatsFire((Equip) nEquip, true);
                                if (nZero != null) {
                                    nEquip = randomizeStatsFire((Equip) nEquip, true);
                                }
                            }
                        } else if (Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip))) {
                            nEquip = randomizeStatsFire((Equip) nEquip, false);
                        }
                        break;
                    } else {
                        if (!Randomizer.isSuccess(getSuccess(scrollId.getItemId(), chr, nEquip), chr)) {
                            if (Randomizer.isSuccess(getCursed(scrollId.getItemId(), chr))) {
                                if (ItemFlag.PROTECT.check(nEquip.getFlag()) || nEquip.isAmazing()) {
                                    chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                                    nEquip.setAmazing(false);
                                } else {
                                    return null; // Æã
                                }
                            }
                            failed = true;
                        } else {
                            for (Entry<String, Integer> stat : stats.entrySet()) {
                                final String key = stat.getKey();

                                if (key.equals("STR")) {
                                    nEquip.setStr((short) (nEquip.getStr() + stat.getValue().intValue()));
                                } else if (key.equals("DEX")) {
                                    nEquip.setDex((short) (nEquip.getDex() + stat.getValue().intValue()));
                                } else if (key.equals("INT")) {
                                    nEquip.setInt((short) (nEquip.getInt() + stat.getValue().intValue()));
                                } else if (key.equals("LUK")) {
                                    nEquip.setLuk((short) (nEquip.getLuk() + stat.getValue().intValue()));
                                } else if (key.equals("PAD")) {
                                    nEquip.setWatk((short) (nEquip.getWatk() + stat.getValue().intValue()));
                                } else if (key.equals("PDD")) {
                                    nEquip.setWdef((short) (nEquip.getWdef() + stat.getValue().intValue()));
                                } else if (key.equals("MAD")) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + stat.getValue().intValue()));
                                } else if (key.equals("MDD")) {
                                    nEquip.setMdef((short) (nEquip.getMdef() + stat.getValue().intValue()));
                                } else if (key.equals("ACC")) {
                                    nEquip.setAcc((short) (nEquip.getAcc() + stat.getValue().intValue()));
                                } else if (key.equals("EVA")) {
                                    nEquip.setAvoid((short) (nEquip.getAvoid() + stat.getValue().intValue()));
                                } else if (key.equals("Speed")) {
                                    nEquip.setSpeed((short) (nEquip.getSpeed() + stat.getValue().intValue()));
                                } else if (key.equals("Jump")) {
                                    nEquip.setJump((short) (nEquip.getJump() + stat.getValue().intValue()));
                                } else if (key.equals("MHP")) {
                                    nEquip.setHp((short) (nEquip.getHp() + stat.getValue().intValue()));
                                } else if (key.equals("MMP")) {
                                    nEquip.setMp((short) (nEquip.getMp() + stat.getValue().intValue()));
                                } else if (key.equals("MHPr")) {
                                    nEquip.setHpR((short) (nEquip.getHpR() + stat.getValue().intValue()));
                                } else if (key.equals("MMPr")) {
                                    nEquip.setMpR((short) (nEquip.getMpR() + stat.getValue().intValue()));
                                }
                            }
                        }
                        break;
                    }
                }
            }
            if (!GameConstants.isCleanSlate(scrollId.getItemId())
                    && !GameConstants.isSpecialScroll(scrollId.getItemId())
                    && !GameConstants.isEquipScroll(scrollId.getItemId())
                    && !GameConstants.isPotentialScroll(scrollId.getItemId())
                    && !GameConstants.isRebirhFireScroll(scrollId.getItemId())
                    && !GameConstants.isEpicScroll(scrollId.getItemId()) && scrollId.getItemId() != 2049360
                    && scrollId.getItemId() != 2049361) {
                if (ItemFlag.SAFETY.check(nEquip.getFlag()) && failed) {
                    chr.dropMessage(5, "The effects of the spell did not destroy the item.");
                } else {
                    nEquip.setUpgradeSlots(
                            (byte) (nEquip.getUpgradeSlots() - getUpgradeScrollUseSlot(scrollId.getItemId())));
                }
                if (!failed) {
                    nEquip.setLevel((byte) (nEquip.getLevel() + 1));
                }
            }
        }
        return equip;
    }

    private static int getEquipLevel(int level) {
        int stat = 0;
        if (level >= 0 && level <= 50) {
            stat = 1;
        } else if (level >= 51 && level <= 100) {
            stat = 2;
        } else {
            stat = 3;
        }
        return stat;
    }

    public final IItem getEquipById(final int equipId) {
        return getEquipById(equipId, -1);
    }

    public final IItem getEquipById(final int equipId, final int ringId) {
        final Equip nEquip = new Equip(equipId, (byte) 0, (byte) 0);
        nEquip.setQuantity((short) 1);
        final Map<String, Integer> stats = getEquipStats(equipId);
        if (stats != null) {
            for (Entry<String, Integer> stat : stats.entrySet()) {
                final String key = stat.getKey();

                if (key.equals("STR")) {
                    nEquip.setStr((short) stat.getValue().intValue());
                } else if (key.equals("DEX")) {
                    nEquip.setDex((short) stat.getValue().intValue());
                } else if (key.equals("INT")) {
                    nEquip.setInt((short) stat.getValue().intValue());
                } else if (key.equals("LUK")) {
                    nEquip.setLuk((short) stat.getValue().intValue());
                } else if (key.equals("PAD")) {
                    nEquip.setWatk((short) stat.getValue().intValue());
                } else if (key.equals("PDD")) {
                    nEquip.setWdef((short) stat.getValue().intValue());
                } else if (key.equals("MAD")) {
                    nEquip.setMatk((short) stat.getValue().intValue());
                } else if (key.equals("MDD")) {
                    nEquip.setMdef((short) stat.getValue().intValue());
                } else if (key.equals("ACC")) {
                    nEquip.setAcc((short) stat.getValue().intValue());
                } else if (key.equals("EVA")) {
                    nEquip.setAvoid((short) stat.getValue().intValue());
                } else if (key.equals("Speed")) {
                    nEquip.setSpeed((short) stat.getValue().intValue());
                } else if (key.equals("Jump")) {
                    nEquip.setJump((short) stat.getValue().intValue());
                } else if (key.equals("MHP")) {
                    nEquip.setHp((short) stat.getValue().intValue());
                } else if (key.equals("MMP")) {
                    nEquip.setMp((short) stat.getValue().intValue());
                } else if (key.equals("MHPr")) {
                    nEquip.setHpR((short) stat.getValue().intValue());
                } else if (key.equals("MMPr")) {
                    nEquip.setMpR((short) stat.getValue().intValue());
                } else if (key.equals("tuc")) {
                    nEquip.setUpgradeSlots((byte) stat.getValue().intValue());
                } else if (key.equals("Craft")) {
                    nEquip.setHands((short) stat.getValue().intValue());
                } else if (key.equals("durability")) {
                    nEquip.setDurability(stat.getValue().intValue());
                } else if (key.equals("imdR")) {
                    nEquip.setIgnoreWdef(stat.getValue().shortValue());
                } else if (key.equals("bdR")) {
                    nEquip.setBossDamage(stat.getValue().byteValue());
                }
            }
        }
        equipCache.put(equipId, nEquip);
        return nEquip.copy();
    }

    private final int ¹æ¾î±¸¸¶°ø°ø½Ä(int i) {
        if (i >= 25) {
            return 6;
        } else if (i >= 20) {
            return 5;
        } else if (i >= 15) {
            return 4;
        } else if (i >= 10) {
            return 3;
        } else if (i >= 1) {
            return 2;
        } else {
            return 0;
        }
    }

    private final int ¹«±â¸¶°ø°ø½Ä(int i) {
        if (i >= 250) {
            return 6;
        } else if (i >= 200) {
            return 5;
        } else if (i >= 150) {
            return 4;
        } else if (i >= 100) {
            return 3;
        } else if (i >= 50) {
            return 2;
        } else {
            return 0;
        }
    }

    private final short getRandStat(final short defaultValue, final int maxRange) {
        if (defaultValue == 0) {
            return 0;
        }
        // vary no more than ceil of 10% of stat
        final int lMaxRange = (int) Math.min(Math.ceil(defaultValue * 0.1), maxRange);
        return (short) ((defaultValue - lMaxRange) + Math.floor(Math.random() * (lMaxRange * 2 + 1)));
    }

    protected final short getRandStatFusion(final short defaultValue, final int value1, final int value2) {
        if (defaultValue == 0) {
            return 0;
        }
        final int range = ((value1 + value2) / 2) - defaultValue;
        final int rand = Randomizer.nextInt(Math.abs(range) + 1);
        return (short) (defaultValue + (range < 0 ? -rand : rand));
    }

    protected final short getRandStatAbove(final short defaultValue, final int maxRange) {
        if (defaultValue <= 0) {
            return 0;
        }
        final int lMaxRange = (int) Math.min(Math.ceil(defaultValue * 0.1), maxRange);

        return (short) ((defaultValue) + Randomizer.nextInt(lMaxRange + 1));
    }

    public final Equip randomizeStats(final Equip equip, final boolean potential) {

        equip.setStr(getRandStat(equip.getStr(), 5));
        equip.setDex(getRandStat(equip.getDex(), 5));
        equip.setInt(getRandStat(equip.getInt(), 5));
        equip.setLuk(getRandStat(equip.getLuk(), 5));
        equip.setMatk(getRandStat(equip.getMatk(), 5));
        equip.setWatk(getRandStat(equip.getWatk(), 5));
        equip.setAcc(getRandStat(equip.getAcc(), 5));
        equip.setAvoid(getRandStat(equip.getAvoid(), 5));
        equip.setJump(getRandStat(equip.getJump(), 5));
        equip.setHands(getRandStat(equip.getHands(), 5));
        equip.setSpeed(getRandStat(equip.getSpeed(), 5));
        equip.setWdef(getRandStat(equip.getWdef(), 10));
        equip.setMdef(getRandStat(equip.getMdef(), 10));
        equip.setHp(getRandStat(equip.getHp(), 10));
        equip.setMp(getRandStat(equip.getMp(), 10));
        // if (equip.getItemId() / 10000 != 166 && equip.getItemId() / 10000 != 167) {
        // // ANDROID
        // if (Randomizer.nextInt(1000) < 333 && potential) {
        // equip.setState((byte) 1);
        // }
        // }

        return equip;
    }

    public final SkillStatEffect getItemEffect(final int itemId) {
        SkillStatEffect ret = itemEffects.get(Integer.valueOf(itemId));
        if (ret == null) {
            final MapleData item = getItemData(itemId);
            if (item == null) {
                return null;
            }
            ret = SkillStatEffect.loadItemEffectFromData(item.getChildByPath("spec"), itemId);
            itemEffects.put(Integer.valueOf(itemId), ret);
        }
        return ret;
    }

    public final List<Pair<Integer, Integer>> getSummonMobs(final int itemId) {
        if (summonMobCache.containsKey(Integer.valueOf(itemId))) {
            return summonMobCache.get(itemId);
        }
        if (!GameConstants.isSummonSack(itemId)) {
            return null;
        }
        final MapleData data = getItemData(itemId).getChildByPath("mob");
        if (data == null) {
            return null;
        }
        final List<Pair<Integer, Integer>> mobPairs = new ArrayList<Pair<Integer, Integer>>();

        for (final MapleData child : data.getChildren()) {
            mobPairs.add(
                    new Pair(MapleDataTool.getIntConvert("id", child), MapleDataTool.getIntConvert("prob", child)));
        }
        summonMobCache.put(itemId, mobPairs);
        return mobPairs;
    }

    public final int getCardMobId(final int id) {
        if (id == 0) {
            return 0;
        }
        if (monsterBookID.containsKey(id)) {
            return monsterBookID.get(id);
        }
        final MapleData data = getItemData(id);
        final int monsterid = MapleDataTool.getIntConvert("info/mob", data, 0);

        if (monsterid == 0) { // Hack.
            return 0;
        }
        monsterBookID.put(id, monsterid);
        return monsterBookID.get(id);
    }

    public final int getWatkForProjectile(final int itemId) {
        Integer atk = projectileWatkCache.get(itemId);
        if (atk != null) {
            return atk.intValue();
        }
        final MapleData data = getItemData(itemId);
        atk = Integer.valueOf(MapleDataTool.getInt("info/incPAD", data, 0));
        projectileWatkCache.put(itemId, atk);
        return atk.intValue();
    }

    public final boolean canScroll(final int scrollid, final int itemid) {
        return (scrollid / 100) % 100 == (itemid / 10000) % 100;
    }

    public final boolean notReturn(final int scrollid) {
        switch (scrollid / 1000) {
            case 1352:
            case 167:
            case 1099:
            case 1098:
            case 2046:
            case 2047:
                return true;
        }
        return false;
    }

    public final String getName(final int itemId) {
        if (nameCache.containsKey(itemId)) {
            return nameCache.get(itemId);
        }
        final MapleData strings = getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("name", strings, null);
        nameCache.put(itemId, ret);
        return ret;
    }

    public final String getDesc(final int itemId) {
        if (descCache.containsKey(itemId)) {
            return descCache.get(itemId);
        }
        final MapleData strings = getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("desc", strings, null);
        descCache.put(itemId, ret);
        return ret;
    }

    public final String getMsg(final int itemId) {
        if (msgCache.containsKey(itemId)) {
            return msgCache.get(itemId);
        }
        final MapleData strings = getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("msg", strings, null);
        msgCache.put(itemId, ret);
        return ret;
    }

    public final short getItemMakeLevel(final int itemId) {
        if (itemMakeLevel.containsKey(itemId)) {
            return itemMakeLevel.get(itemId);
        }
        if (itemId / 10000 != 400) {
            return 0;
        }
        final short lvl = (short) MapleDataTool.getIntConvert("info/lv", getItemData(itemId), 0);
        itemMakeLevel.put(itemId, lvl);
        return lvl;
    }

    public final byte isConsumeOnPickup(final int itemId) {
        // 0 = not, 1 = consume on pickup, 2 = consume + party
        if (consumeOnPickupCache.containsKey(itemId)) {
            return consumeOnPickupCache.get(itemId);
        }
        final MapleData data = getItemData(itemId);
        byte consume = (byte) MapleDataTool.getIntConvert("spec/consumeOnPickup", data, 0);
        if (consume == 0) {
            consume = (byte) MapleDataTool.getIntConvert("specEx/consumeOnPickup", data, 0);
        }
        if (consume == 1) {
            if (MapleDataTool.getIntConvert("spec/party", getItemData(itemId), 0) > 0) {
                consume = 2;
            }
        }
        consumeOnPickupCache.put(itemId, consume);
        return consume;
    }

    public final byte isRunOnPickup(final int itemid) {
        if (runOnPickupCache.containsKey(itemid)) {
            return runOnPickupCache.get(itemid);
        }
        final MapleData data = getItemData(itemid);
        byte run = (byte) MapleDataTool.getIntConvert("spec/runOnPickup", data, 0);
        runOnPickupCache.put(itemid, run);
        return run;
    }

    public final boolean isDropRestricted(final int itemId) {
        if (dropRestrictionCache.containsKey(itemId)) {
            return dropRestrictionCache.get(itemId);
        }
        final MapleData data = getItemData(itemId);

        boolean trade = false;
        if (MapleDataTool.getIntConvert("info/tradeBlock", data, 0) == 1
                || MapleDataTool.getIntConvert("info/quest", data, 0) == 1
                || MapleDataTool.getIntConvert("info/accountSharable", data, 0) == 1) {
            trade = true;
        }
        dropRestrictionCache.put(itemId, trade);
        return trade;
    }

    public final boolean isTradeBlock(final int itemId) {
        final MapleData data = getItemData(itemId);
        boolean tradeblock = false;
        if (MapleDataTool.getIntConvert("info/tradeBlock", data, 0) == 1
                || MapleDataTool.getIntConvert("info/quest", data, 0) == 1
                || MapleDataTool.getIntConvert("info/accountSharable", data, 0) == 1) {
            tradeblock = true;
        }
        return tradeblock;
    }

    public final boolean isOnlyTradeBlock(final int itemId) {
        final MapleData data = getItemData(itemId);
        boolean tradeblock = false;
        if (MapleDataTool.getIntConvert("info/tradeBlock", data, 0) == 1) {
            tradeblock = true;
        }
        return tradeblock;
    }

    public final boolean isExpireOnLogout(final int itemId) {
        if (expirationOnLogOutCache.containsKey(itemId)) {
            return expirationOnLogOutCache.get(itemId);
        }
        final MapleData data = getItemData(itemId);

        boolean expire = false;
        if (MapleDataTool.getIntConvert("info/expireOnLogout", data, 0) == 1) {
            expire = true;
        }
        expirationOnLogOutCache.put(itemId, expire);
        return expire;
    }

    public final boolean isPickupRestricted(final int itemId) {
        if (pickupRestrictionCache.containsKey(itemId)) {
            return pickupRestrictionCache.get(itemId);
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/only", getItemData(itemId), 0) == 1;

        pickupRestrictionCache.put(itemId, bRestricted);
        return bRestricted;
    }

    public final Pair<Integer, List<StructRewardItem>> getRewardItem(final int itemid) {
        if (RewardItem.containsKey(itemid)) {
            return RewardItem.get(itemid);
        }
        final MapleData data = getItemData(itemid);
        if (data == null) {
            return null;
        }
        final MapleData rewards = data.getChildByPath("reward");
        if (rewards == null) {
            return null;
        }
        int totalprob = 0; // As there are some rewards with prob above 2000, we can't assume it's always
        // 100
        List<StructRewardItem> all = new ArrayList();

        for (final MapleData reward : rewards) {
            StructRewardItem struct = new StructRewardItem();

            struct.itemid = MapleDataTool.getInt("item", reward, 0);
            struct.prob = (byte) MapleDataTool.getInt("prob", reward, 0);
            struct.quantity = (short) MapleDataTool.getInt("count", reward, 0);
            struct.statGrade = MapleDataTool.getInt("statGrade", reward, 0);
            struct.effect = MapleDataTool.getString("Effect", reward, "");
            struct.worldmsg = MapleDataTool.getString("worldMsg", reward, null);
            struct.period = MapleDataTool.getInt("period", reward, -1);

            totalprob += struct.prob;

            all.add(struct);
        }
        Pair<Integer, List<StructRewardItem>> toreturn = new Pair(totalprob, all);
        RewardItem.put(itemid, toreturn);
        return toreturn;
    }

    public final Map<String, Integer> getSkillStats(final int itemId) {
        if (SkillStatsCache.containsKey(itemId)) {
            return SkillStatsCache.get(itemId);
        }
        if (!(itemId / 10000 == 228 || itemId / 10000 == 229)) { // Skillbook and mastery book
            return null;
        }
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        final Map<String, Integer> ret = new LinkedHashMap<String, Integer>();
        for (final MapleData data : info.getChildren()) {
            if (data.getName().startsWith("inc")) {
                ret.put(data.getName().substring(3), MapleDataTool.getIntConvert(data));
            }
        }
        ret.put("masterLevel", MapleDataTool.getInt("masterLevel", info, 0));
        ret.put("reqSkillLevel", MapleDataTool.getInt("reqSkillLevel", info, 0));
        ret.put("success", MapleDataTool.getInt("success", info, 0));

        final MapleData skill = info.getChildByPath("skill");

        for (int i = 0; i < skill.getChildren().size(); i++) { // List of allowed skillIds
            ret.put("skillid" + i, MapleDataTool.getInt(Integer.toString(i), skill, 0));
        }
        SkillStatsCache.put(itemId, ret);
        return ret;
    }

    public final List<Integer> petsCanConsume(final int itemId) {
        final List<Integer> ret = new ArrayList<Integer>();
        final MapleData data = getItemData(itemId);
        int curPetId = 0;
        int size = data.getChildren().size();
        int count = 0;
        while (true) {
            curPetId = MapleDataTool.getInt("spec/" + Integer.toString(count), data, 0);
            if (curPetId == 0) {
                break;
            }
            ret.add(Integer.valueOf(curPetId));
            count++;
        }
        return ret;
    }

    public final int getTameness(final int itemId) {
        return MapleDataTool.getInt("spec/incTameness", getItemData(itemId), 0);
    }

    public final int getRepleteness(final int itemId) {
        return MapleDataTool.getInt("spec/incRepleteness", getItemData(itemId), 0);
    }

    public final boolean isQuestItem(final int itemId) {
        if (isQuestItemCache.containsKey(itemId)) {
            return isQuestItemCache.get(itemId);
        }
        final boolean questItem = MapleDataTool.getIntConvert("info/quest", getItemData(itemId), 0) == 1;
        isQuestItemCache.put(itemId, questItem);
        return questItem;
    }

    public final boolean itemExists(final int itemId) {
        if (GameConstants.getInventoryType(itemId) == MapleInventoryType.UNDEFINED) {
            return false;
        }
        return getItemData(itemId) != null;
    }

    public final boolean isCash(final int itemid) {
        if (getEquipStats(itemid) == null) {
            return GameConstants.getInventoryType(itemid) == MapleInventoryType.CASH;
        }
        return GameConstants.getInventoryType(itemid) == MapleInventoryType.CASH
                || getEquipStats(itemid).get("cash") > 0;
    }

    public final boolean pickupItem(final int itemid) {
        if (getItemData(itemid) == null) {
            return false;
        }
        return MapleDataTool.getIntConvert("info/pickupItem", getItemData(itemid), 0) == 1;
    }

    public final Equip fuse(final Equip equip1, final Equip equip2) {
        if (equip1.getItemId() != equip2.getItemId()) {
            return equip1;
        }
        final Equip equip = (Equip) getEquipById(equip1.getItemId());
        equip.setStr(getRandStatFusion(equip.getStr(), equip1.getStr(), equip2.getStr()));
        equip.setDex(getRandStatFusion(equip.getDex(), equip1.getDex(), equip2.getDex()));
        equip.setInt(getRandStatFusion(equip.getInt(), equip1.getInt(), equip2.getInt()));
        equip.setLuk(getRandStatFusion(equip.getLuk(), equip1.getLuk(), equip2.getLuk()));
        equip.setMatk(getRandStatFusion(equip.getMatk(), equip1.getMatk(), equip2.getMatk()));
        equip.setWatk(getRandStatFusion(equip.getWatk(), equip1.getWatk(), equip2.getWatk()));
        equip.setAcc(getRandStatFusion(equip.getAcc(), equip1.getAcc(), equip2.getAcc()));
        equip.setAvoid(getRandStatFusion(equip.getAvoid(), equip1.getAvoid(), equip2.getAvoid()));
        equip.setJump(getRandStatFusion(equip.getJump(), equip1.getJump(), equip2.getJump()));
        equip.setHands(getRandStatFusion(equip.getHands(), equip1.getHands(), equip2.getHands()));
        equip.setSpeed(getRandStatFusion(equip.getSpeed(), equip1.getSpeed(), equip2.getSpeed()));
        equip.setWdef(getRandStatFusion(equip.getWdef(), equip1.getWdef(), equip2.getWdef()));
        equip.setMdef(getRandStatFusion(equip.getMdef(), equip1.getMdef(), equip2.getMdef()));
        equip.setHp(getRandStatFusion(equip.getHp(), equip1.getHp(), equip2.getHp()));
        equip.setMp(getRandStatFusion(equip.getMp(), equip1.getMp(), equip2.getMp()));
        return equip;
    }

    public final int getTotalStat(final Equip equip) { // i get COOL when my defense is higher on gms...
        return equip.getStr() + equip.getDex() + equip.getInt() + equip.getLuk() + equip.getMatk() + equip.getWatk()
                + equip.getAcc() + equip.getAvoid() + equip.getJump() + equip.getHands() + equip.getSpeed()
                + equip.getHp() + equip.getMp() + equip.getWdef() + equip.getMdef();
    }

    public final Equip levelUpItem(Equip e) {
        e.setItemLevel((byte) (e.getItemLevel() + 1));
        e.setItemEXP(0);
        StructEquipLevel stats = getEquipLevelStat(e.getItemId(), e.getItemLevel());
        if (stats.incSTRMin > 0) {
            e.setStr((short) (e.getStr() + Randomizer.rand(stats.incSTRMin, stats.incSTRMax)));
        }
        if (stats.incDEXMin > 0) {
            e.setDex((short) (e.getDex() + Randomizer.rand(stats.incDEXMin, stats.incDEXMax)));
        }
        if (stats.incINTMin > 0) {
            e.setInt((short) (e.getInt() + Randomizer.rand(stats.incINTMin, stats.incINTMax)));
        }
        if (stats.incLUKMin > 0) {
            e.setLuk((short) (e.getLuk() + Randomizer.rand(stats.incLUKMin, stats.incLUKMax)));
        }
        if (stats.incMHPMin > 0) {
            e.setHp((short) (e.getHp() + Randomizer.rand(stats.incMHPMin, stats.incMHPMax)));
        }
        return e;
    }

    public final int getCashCharmExp(Equip equip) {
        if (!equip.isCash()) {
            return 0;
        }
        int id = equip.getItemId() / 10000;
        if (GameConstants.isWeapon(equip.getItemId())) {
            return 60; // weapon
        } else if (id == 109 || id == 110 || id == 113) {
            return 10; // Shield & Cloak & Belt
        } else if (GameConstants.isAccessory(equip.getItemId())) {
            return 40; // Accessories
        } else if (id == 100) {
            return 50; // Shoulders //Pitching
        } else if (id == 104 || id == 106) {
            return 30; // Tops and bottoms
        } else if (id == 105) {
            return 60; // Suit
        } else if (id == 108) {
            return 40; // Gloves
        } else if (id == 107) {
            return 40; // shoes
        }
        return 0;
    }

    public final short getSlotMax(final int itemId) {
        final MapleData item = getItemData(itemId);
        if (item == null) {
            return 0;
        }
        if (itemId == 4000313) {
            return 1000;
        }
        return slotMax;
    }

    public MapleInventoryType getInventoryType(int itemId) {
        if (inventoryTypeCache.containsKey(itemId)) {
            return inventoryTypeCache.get(itemId);
        }
        MapleInventoryType ret;
        String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = itemData.getRoot();
        for (MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = MapleInventoryType.getByWZName(topDir.getName());
                    inventoryTypeCache.put(itemId, ret);
                    return ret;
                } else if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    ret = MapleInventoryType.getByWZName(topDir.getName());
                    inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
            }
        }
        root = equipData.getRoot();
        for (MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    ret = MapleInventoryType.EQUIP;
                    inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
            }
        }
        ret = MapleInventoryType.UNDEFINED;
        inventoryTypeCache.put(itemId, ret);
        return ret;
    }

    public final Equip randomizeStats(final Equip equip) {
        equip.setStr(getRandStat(equip.getStr(), 5));
        equip.setDex(getRandStat(equip.getDex(), 5));
        equip.setInt(getRandStat(equip.getInt(), 5));
        equip.setLuk(getRandStat(equip.getLuk(), 5));
        equip.setMatk(getRandStat(equip.getMatk(), 5));
        equip.setWatk(getRandStat(equip.getWatk(), 5));
        equip.setAcc(getRandStat(equip.getAcc(), 5));
        equip.setAvoid(getRandStat(equip.getAvoid(), 5));
        equip.setJump(getRandStat(equip.getJump(), 5));
        equip.setHands(getRandStat(equip.getHands(), 5));
        equip.setSpeed(getRandStat(equip.getSpeed(), 5));
        equip.setWdef(getRandStat(equip.getWdef(), 10));
        equip.setMdef(getRandStat(equip.getMdef(), 10));
        equip.setHp(getRandStat(equip.getHp(), 10));
        equip.setMp(getRandStat(equip.getMp(), 10));
        if (Randomizer.nextInt(100) > 90) { // 9% chance
            equip.setState((byte) 1);
            // equip.setPotential((byte) 1);
        }
        return equip;
    }

    public double getFireWMtk(int level) {
        int r = Randomizer.rand(1, 5);
        boolean ol = level >= 160;
        switch (r) {
            case 1:
                return ol ? 15.44 : 12.35;
            case 2:
                return ol ? 22.31 : 17.85;
            case 3:
                return ol ? 30.70 : 24.56;
            case 4:
                return ol ? 40.3 : 32.24;
            case 5:
                return ol ? 51.69 : 41.35;
            default:
                return 0;
        }
    }

    public Equip randomizeStatsFire(final Equip equip, final boolean isFirst) { //Flame of Eternal Reincarnation
        int[] efs = equip.getFireStat();
        int[] nefs = new int[16];
        Equip def = (Equip) ItemInformation.getInstance().getEquipById(equip.getItemId());
        nefs[0] = getRandStatFire(60);
        equip.setStr((short) (equip.getStr() - efs[0] + nefs[0]));
        nefs[1] = getRandStatFire(60);
        equip.setDex((short) (equip.getDex() - efs[1] + nefs[1]));
        nefs[2] = getRandStatFire(60);
        equip.setInt((short) (equip.getInt() - efs[2] + nefs[2]));
        nefs[3] = getRandStatFire(60);
        equip.setLuk((short) (equip.getLuk() - efs[3] + nefs[3]));
        int wmtk = 15;
        if (GameConstants.isWeapon(equip.getItemId())) {
            wmtk = (int) (def.getWatk() * (getFireWMtk(getReqLevel(equip.getItemId())) * 0.01D));
        }
        nefs[4] = getRandStatFire(wmtk);
        equip.setMatk((short) (equip.getMatk() - efs[4] + nefs[4]));
        nefs[5] = getRandStatFire(wmtk);
        equip.setWatk((short) (equip.getWatk() - efs[5] + nefs[5]));
        nefs[6] = getRandStatFire(200);
        equip.setAcc((short) (equip.getAcc() - efs[6] + nefs[6]));
        nefs[7] = getRandStatFire(200);
        equip.setAvoid((short) (equip.getAvoid() - efs[7] + nefs[7]));
        nefs[8] = getRandStatFire(200);
        equip.setJump((short) (equip.getJump() - efs[8] + nefs[8]));
        nefs[10] = getRandStatFire(40);
        equip.setHands((short) (equip.getHands() - efs[10] + nefs[10]));
        nefs[11] = getRandStatFire(20);
        equip.setSpeed((short) (equip.getSpeed() - efs[11] + nefs[11]));
        nefs[12] = getRandStatFire(40);
        equip.setWdef((short) (equip.getWdef() - efs[12] + nefs[12]));
        nefs[13] = getRandStatFire(40);
        equip.setMdef((short) (equip.getMdef() - efs[13] + nefs[13]));
        nefs[14] = getRandStatFire(2500);
        equip.setHp((short) (equip.getHp() - efs[14] + nefs[14]));
        nefs[15] = getRandStatFire(2500);
        equip.setMp((short) (equip.getMp() - efs[15] + nefs[15]));

        if (!GameConstants.isWeapon(equip.getItemId())) {
            if (Randomizer.rand(1, 2) == 1) {
                equip.setAllStatP((byte) def.getAllStatP());
            } else {
                equip.setAllStatP((byte) (def.getAllStatP() + (Randomizer.nextInt(10) + 1))); //All Stats%
            }
        }
        if (Randomizer.rand(1, 2) == 1) {
            byte downlevel = (byte) (Randomizer.nextInt(10) + 1); //Reduced wear level
            if (downlevel > ItemInformation.getInstance().getReqLevel(equip.getItemId())) {
                equip.setDownLevel((byte) (ItemInformation.getInstance().getReqLevel(equip.getItemId()) - 1));
            } else {
                equip.setDownLevel((byte) downlevel);
            }
        } else {
            equip.setDownLevel((byte) 0);
        }
        if (GameConstants.isWeapon(equip.getItemId())) {
            if (Randomizer.rand(1, 2) == 1) {
                equip.setBossDamage((byte) (def.getBossDamage() + Randomizer.nextInt(4)));
            } else {
                equip.setBossDamage((byte) def.getBossDamage());
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setAllDamageP((byte) (Randomizer.nextInt(4) + def.getAllDamageP()));
            } else {
                equip.setAllDamageP((byte) def.getAllDamageP());
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setIgnoreWdef((short) (def.getIgnoreWdef() + Randomizer.nextInt(4)));
            } else {
                equip.setIgnoreWdef((short) def.getIgnoreWdef());
            }
        }
        short flag = def.getFlag();
        flag |= ItemFlag.UNTRADEABLE.getValue();
        equip.setFlag(flag);
        equip.setFireStat(nefs);
        return equip;
    }

    private short getRandStatFire(final int maxRange) {
        int rate = Randomizer.rand(1, 10);
        short value = 0;
        if (rate >= 7) {
            value = (short) Randomizer.rand(0, maxRange);
        }
        return (short) value;
    }

    public Equip addCrystalEffect(Equip equip, int crystalId) {
        switch (crystalId) {
            case 4250400:
            case 4250401:
            case 4250402:
                equip.setSpeed((short) (equip.getSpeed() + (crystalId == 4250402 ? 5 : crystalId - 4250400 + 2)));
                break;
            case 4250500:
            case 4250501:
            case 4250502:
                equip.setJump((short) (equip.getJump() + crystalId - 4250500 + 1));
                break;
            case 4251100:
            case 4251101:
            case 4251102:
                equip.setDex((short) (equip.getDex() + (crystalId == 4251102 ? 5 : crystalId - 4251100 + 2)));
                break;
            case 4250000:
            case 4250001:
            case 4250002:
                equip.setWatk((short) (equip.getWatk() + crystalId - 4250000 + 1));
                break;
            case 4250700:
            case 4250701:
            case 4250702:
                equip.setMp((short) (equip.getMp() + ((crystalId - 4250702 + 1) * 10)));
                break;
            case 4250200:
            case 4250201:
            case 4250202:
                equip.setAcc((short) (equip.getAcc() + (crystalId == 4250202 ? 5 : crystalId - 4250200 + 2)));
                break;
            case 4251000:
            case 4251001:
            case 4251002:
                equip.setLuk((short) (equip.getLuk() + (crystalId == 4251002 ? 5 : crystalId - 4251000 + 2)));
                break;
            case 4250300:
            case 4250301:
            case 4250302:
                equip.setAvoid((short) (equip.getAvoid() + (crystalId == 4250302 ? 5 : crystalId - 4250302 + 2)));
                break;
            case 4250800:
            case 4250801:
            case 4250802:
                equip.setStr((short) (equip.getStr() + (crystalId == 4250802 ? 5 : crystalId - 4250802 + 2)));
                break;
            case 4250100:
            case 4250101:
            case 4250102:
                equip.setMatk((short) (equip.getMatk() + crystalId - 4250100 + 1));
                break;
            case 4250600:
            case 4250601:
            case 4250602:
                equip.setHp((short) (equip.getHp() + ((crystalId - 4250600 + 1) * 10)));
                break;
            case 4250900:
            case 4250901:
            case 4250902:
                equip.setInt((short) (equip.getInt() + (crystalId == 4250902 ? 5 : crystalId - 4250900 + 2)));
                break;
            case 4251300:
            case 4251301:
            case 4251302:
                equip.setWatk(getRandStat(equip.getWatk(), crystalId - 4251300 + 1));
                equip.setMatk(getRandStat(equip.getMatk(), crystalId - 4251300 + 1));
                equip.setJump(getRandStat(equip.getJump(), crystalId - 4251300 + 1));
                equip.setSpeed(getRandStat(equip.getSpeed(), crystalId - 4251300 + 1));
                break;
            case 4251400:
            case 4251401:
            case 4251402:
                equip.setStr(getRandStat(equip.getStr(), (crystalId == 4251402 ? 5 : crystalId - 4251400 + 2)));
                equip.setDex(getRandStat(equip.getDex(), (crystalId == 4251402 ? 5 : crystalId - 4251400 + 2)));
                equip.setInt(getRandStat(equip.getInt(), (crystalId == 4251402 ? 5 : crystalId - 4251400 + 2)));
                equip.setLuk(getRandStat(equip.getLuk(), (crystalId == 4251402 ? 5 : crystalId - 4251400 + 2)));
                break;
        }
        return equip;
    }

    public int getETCMonsLvl(int itemid) {
        if (getItemData(itemid) != null) {
            final MapleData lvl = getItemData(itemid).getChildByPath("info/lv");
            if (lvl == null) {
                return -1;
            }
            return MapleDataTool.getInt(lvl);
        }
        return -1;
    }

    public MapleWeaponType getWeaponType(int itemId) {
        int cat = (itemId / 10000) % 100;
        MapleWeaponType[] type = {MapleWeaponType.SWORD1H, MapleWeaponType.AXE1H, MapleWeaponType.BLUNT1H,
            MapleWeaponType.DAGGER, MapleWeaponType.NOT_A_WEAPON, MapleWeaponType.NOT_A_WEAPON,
            MapleWeaponType.NOT_A_WEAPON, MapleWeaponType.WAND, MapleWeaponType.STAFF, MapleWeaponType.NOT_A_WEAPON,
            MapleWeaponType.SWORD2H, MapleWeaponType.AXE2H, MapleWeaponType.BLUNT2H, MapleWeaponType.SPEAR,
            MapleWeaponType.POLE_ARM, MapleWeaponType.BOW, MapleWeaponType.CROSSBOW, MapleWeaponType.CLAW,
            MapleWeaponType.KNUCKLE, MapleWeaponType.GUN, MapleWeaponType.DESPERADO};
        if (cat < 30 || cat > 49) {
            return MapleWeaponType.NOT_A_WEAPON;
        }
        return type[cat - 30];
    }

    public int getImpLifeid(int itemId) {
        if (mipCache.containsKey(itemId)) {
            return mipCache.get(itemId);
        }
        MapleData item = getItemData(itemId);
        if (item == null) {
            return -1;
        }
        int pEntry = 0;
        MapleData pData = item.getChildByPath("spec/lifeId");
        if (pData == null) {
            return -1;
        }
        pEntry = MapleDataTool.getInt(pData);
        mipCache.put(itemId, pEntry);
        return pEntry;
    }

    public final Equip randomizeStatspig(Equip equip, boolean potential) /*      */ {
        if ((equip.getLevel() >= 0) && (equip.getLevel() <= 79)) {
            if (Randomizer.rand(1, 2) == 1) {
                equip.setStr(getRandStatAbove(equip.getStr(), 10));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setDex(getRandStatAbove(equip.getDex(), 10));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setInt(getRandStatAbove(equip.getInt(), 10));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setLuk(getRandStatAbove(equip.getLuk(), 10));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setMatk(getRandStatAbove(equip.getMatk(), GameConstants.isWeapon(equip.getItemId()) ? 5 : 1));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setWatk(getRandStatAbove(equip.getWatk(), GameConstants.isWeapon(equip.getItemId()) ? 5 : 1));
            }
        } else if ((equip.getLevel() >= 80) && (equip.getLevel() <= 119)) {
            if (Randomizer.rand(1, 2) == 1) {
                equip.setStr(getRandStatAbove(equip.getStr(), 15));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setDex(getRandStatAbove(equip.getDex(), 15));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setInt(getRandStatAbove(equip.getInt(), 15));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setLuk(getRandStatAbove(equip.getLuk(), 15));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setMatk(getRandStatAbove(equip.getMatk(), GameConstants.isWeapon(equip.getItemId()) ? 8 : 2));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setWatk(getRandStatAbove(equip.getWatk(), GameConstants.isWeapon(equip.getItemId()) ? 8 : 2));
            }
        } else if ((equip.getLevel() >= 120) && (equip.getLevel() <= 200)) {
            if (Randomizer.rand(1, 2) == 1) {
                equip.setStr(getRandStatAbove(equip.getStr(), 20));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setDex(getRandStatAbove(equip.getDex(), 20));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setInt(getRandStatAbove(equip.getInt(), 20));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setLuk(getRandStatAbove(equip.getLuk(), 20));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setMatk(getRandStatAbove(equip.getMatk(), GameConstants.isWeapon(equip.getItemId()) ? 12 : 3));
            }
            if (Randomizer.rand(1, 2) == 1) {
                equip.setWatk(getRandStatAbove(equip.getWatk(), GameConstants.isWeapon(equip.getItemId()) ? 12 : 3));
            }
        }
        return equip;
    }

    public final Pair<Integer, List<Integer>> questItemInfo(final int itemId) {
        MapleData d = this.getItemData(itemId);
        if (d == null) {
            return null;
        }
        int questid = MapleDataTool.getIntConvert("info/questId", d, 0);
        List<Integer> list = new ArrayList<>();
        for (MapleData dd : d.getChildByPath("info/consumeItem")) {
            list.add(MapleDataTool.getIntConvert(dd));
        }
        return new Pair<>(questid, list);
    }
    
    public final int getPotentialOptionID(int level, boolean editional, int itemtype) {
        int count = 1000;
        while (true) {
//            for (Pair<Integer, Integer> a : potentialOpCache) {
//                System.out.println(a.getLeft() + " " + a.getRight());
//            }
            Pair<Integer, Integer> data = potentialOpCache.get(Randomizer.nextInt(potentialOpCache.size()));
            if (level == (data.getLeft() / 10000) && !PotentialType(data.getLeft(), data.getRight(), itemtype, editional)) {
                return data.getLeft();
            }
            if (count-- < 0) {
                return 0;
            }
        }
    }

    private boolean PotentialType(int potential, int potype, int itemtype, boolean editional) {  //Latent deletion
        if (!editional && potential != 30551 && potential != 40041 && potential != 40043 && potential != 30042 && potential != 30044 && ( isEditionalWeaponOption(potential) || isEditionalArmorOption(potential))) {
            return true;
        }
        int itemtype_ = itemtype;
        itemtype /= 10;
        if (!editional && itemtype == 119 && (potential % 100000 == 30011 || potential % 100000 == 30003 || potential % 100000 == 40656 || potential % 100000 == 42046 || 
                potential % 100000 == 42501 || potential % 100000 == 30001 || potential % 100000 == 32046 || potential % 100000 == 30601 || 
                potential % 100000 == 40002 || potential % 100000 == 42045 || potential % 100000 == 30602 || potential % 100000 == 32047 || 
                potential % 100000 == 32048 || potential % 100000 == 40602 || potential % 100000 == 32093 || potential % 100000 == 32094 || 
                potential % 100000 == 40650 || potential % 100000 == 32045 || potential % 100000 == 30006 || potential % 100000 == 40501 || 
                potential % 100000 == 30004 || potential % 100000 == 40003 || potential % 100000 == 40601 || potential % 100000 == 42048 ||
                potential % 100000 == 42047 || potential % 100000 == 30007 || potential % 100000 == 32092 || potential % 100000 == 40081 ||
                potential % 100000 == 42091 || potential % 100000 == 30012 || potential % 100000 == 30002 || potential % 100000 == 40005 || 
                potential % 100000 == 40012 || potential % 100000 == 32091 || potential % 100000 == 40603 || potential % 100000 == 40502 || 
                potential % 100000 == 42094 || potential % 100000 == 42093 || potential % 100000 == 40006 || potential % 100000 == 30005 || 
                potential % 100000 == 40008 || potential % 100000 == 40011 || potential % 100000 == 42092 || potential % 100000 == 40004 || 
                potential % 100000 == 42601 || potential % 100000 == 42054 || potential % 100000 == 40356 || potential % 100000 == 40045 || 
                potential % 100000 == 40053 || potential % 100000 == 40551 || potential % 100000 == 42009 || potential % 100000 == 32010 || 
                potential % 100000 == 42058 || potential % 100000 == 42071 || potential % 100000 == 30357 || potential % 100000 == 42010 || 
                potential % 100000 == 30046 || potential % 100000 == 40014 || potential % 100000 == 40046 || potential % 100000 == 30014 || 
                potential % 100000 == 42044 || potential % 100000 == 42052 || potential % 100000 == 30013 || potential % 100000 == 32052 || 
                potential % 100000 == 40357 || potential % 100000 == 32054 || potential % 100000 == 30356 || potential % 100000 == 32058 || 
                potential % 100000 == 42060 || potential % 100000 == 32071 || potential % 100000 == 32009 || potential % 100000 == 30054 || 
                potential % 100000 == 42062 || potential % 100000 == 42291 || potential % 100000 == 40013 || potential % 100000 == 30551 || 
                potential % 100000 == 30053 || potential % 100000 == 30045 || potential % 100000 == 40054 || potential % 100000 == 40007 || 
                potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 40656)) { // Emblem
            return true;
        }
        if (!editional && itemtype == 167 && (potential % 100000 == 42091 || potential % 100000 == 32057 || potential % 100000 == 32094 || potential % 100000 == 30008 || 
                potential % 100000 == 40006 || potential % 100000 == 40051 || potential % 100000 == 30051 || potential % 100000 == 30006 || 
                potential % 100000 == 30003 || potential % 100000 == 30004 || potential % 100000 == 40092 || potential % 100000 == 30007 || 
                potential % 100000 == 30055 || potential % 100000 == 42093 || potential % 100000 == 42057 || potential % 100000 == 30002 || 
                potential % 100000 == 30012 || potential % 100000 == 40070 || potential % 100000 == 30001 || potential % 100000 == 40011 || 
                potential % 100000 == 40602 || potential % 100000 == 30070 || potential % 100000 == 32092 || potential % 100000 == 30005 || 
                potential % 100000 == 32093 || potential % 100000 == 30601 || potential % 100000 == 30291 || potential % 100000 == 40603 || 
                potential % 100000 == 42092 || potential % 100000 == 40004 || potential % 100000 == 30052 || potential % 100000 == 30602 || 
                potential % 100000 == 40052 || potential % 100000 == 40055 || potential % 100000 == 30011 || potential % 100000 == 40003 || 
                potential % 100000 == 40601 || potential % 100000 == 42094 || potential % 100000 == 40007 || potential % 100000 == 40091 || 
                potential % 100000 == 40012 || potential % 100000 == 40008 || potential % 100000 == 40292 || potential % 100000 == 30003 || 
                potential % 100000 == 40005 || potential % 100000 == 40002 || potential % 100000 == 40291 || potential % 100000 == 40081 || 
                potential % 100000 == 42291 || potential % 100000 == 40014 || potential % 100000 == 40013 || potential % 100000 == 30013 || 
                potential % 100000 == 42009 || potential % 100000 == 42058 || potential % 100000 == 42010 || potential % 100000 == 42071 || 
                potential % 100000 == 32071 || potential % 100000 == 40357 || potential % 100000 == 32009 || potential % 100000 == 42062 || 
                potential % 100000 == 42052 || potential % 100000 == 42044 || potential % 100000 == 30014 || potential % 100000 == 40356 || 
                potential % 100000 == 32052 || potential % 100000 == 32058 || potential % 100000 == 30356 || potential % 100000 == 42601 || 
                potential % 100000 == 32010 || potential % 100000 == 42054 || potential % 100000 == 32054 || potential % 100000 == 42060 || 
                potential % 100000 == 30357 || potential % 100000 == 40001 || potential % 100000 == 32091))  {  //Android
            return true;
        }
        if ((itemtype < 101 || itemtype >= 120) && potype == 40) { // Not accessories, latent accessories
            return true; 
        }
        if (!editional && itemtype == 135 && (potential % 100000 == 32047 || potential % 100000 == 32092 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
                potential % 100000 == 32046 || potential % 100000 == 30004 || potential % 100000 == 32045 || potential % 100000 == 40008 || 
                potential % 100000 == 30006 || potential % 100000 == 30007 || potential % 100000 == 42045 ||  potential % 100000 == 30002 ||
                potential % 100000 == 40006 ||  potential % 100000 == 42092 || potential % 100000 == 30011 ||  potential % 100000 == 40003 || 
                potential % 100000 == 30008 || potential % 100000 == 42093 || potential % 100000 == 30012 || potential % 100000 == 40081 ||
                potential % 100000 == 30001 || potential % 100000 == 42048 || potential % 100000 == 42091 || potential % 100000 == 30005 ||
                potential % 100000 == 32091 || potential % 100000 == 40001 || potential % 100000 == 32048 || potential % 100000 == 42094 || 
                potential % 100000 == 30003 || potential % 100000 == 40002 || potential % 100000 == 40012  || potential % 100000 == 42047 || 
                potential % 100000 == 40011 || potential % 100000 == 42046 || potential % 100000 == 40007 || potential % 100000 == 40004 || 
                potential % 100000 == 40551 || potential % 100000 == 40013 || potential % 100000 == 42054 || potential % 100000 == 42058 || 
                potential % 100000 == 30014 || potential % 100000 == 42044 || potential % 100000 == 42601 || potential % 100000 == 40014 || 
                potential % 100000 == 32054 || potential % 100000 == 30053 || potential % 100000 == 42062 || potential % 100000 == 32071 || 
                potential % 100000 == 30046 || potential % 100000 == 40053 || potential % 100000 == 42052 || potential % 100000 == 30045 || 
                potential % 100000 == 42060 || potential % 100000 == 42010 || potential % 100000 == 32010 || potential % 100000 == 40045 || 
                potential % 100000 == 32009 || potential % 100000 == 32058 || potential % 100000 == 42291 || potential % 100000 == 30357 || 
                potential % 100000 == 40054 || potential % 100000 == 32052 || potential % 100000 == 30551 || potential % 100000 == 30054 || 
                potential % 100000 == 42071 || potential % 100000 == 42009 || potential % 100000 == 30013 || potential % 100000 == 40005)) {   //Secondary weapon
            return true;
        }
                if (!editional && itemtype == 109 && (potential % 100000 == 32047 || potential % 100000 == 32092 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
                potential % 100000 == 32046 || potential % 100000 == 30004 || potential % 100000 == 32045 || potential % 100000 == 40008 || 
                potential % 100000 == 30006 || potential % 100000 == 30007 || potential % 100000 == 42045 ||  potential % 100000 == 30002 ||
                potential % 100000 == 40006 ||  potential % 100000 == 42092 || potential % 100000 == 30011 ||  potential % 100000 == 40003 || 
                potential % 100000 == 30008 || potential % 100000 == 42093 || potential % 100000 == 30012 || potential % 100000 == 40081 ||
                potential % 100000 == 30001 || potential % 100000 == 42048 || potential % 100000 == 42091 || potential % 100000 == 30005 ||
                potential % 100000 == 32091 || potential % 100000 == 40001 || potential % 100000 == 32048 || potential % 100000 == 42094 || 
                potential % 100000 == 30003 || potential % 100000 == 40002 || potential % 100000 == 40012  || potential % 100000 == 42047 || 
                potential % 100000 == 40011 || potential % 100000 == 42046 || potential % 100000 == 40007 || potential % 100000 == 40004 || 
                potential % 100000 == 40551 || potential % 100000 == 40013 || potential % 100000 == 42054 || potential % 100000 == 42058 || 
                potential % 100000 == 30014 || potential % 100000 == 42044 || potential % 100000 == 42601 || potential % 100000 == 40014 || 
                potential % 100000 == 32054 || potential % 100000 == 30053 || potential % 100000 == 42062 || potential % 100000 == 32071 || 
                potential % 100000 == 30046 || potential % 100000 == 40053 || potential % 100000 == 42052 || potential % 100000 == 30045 || 
                potential % 100000 == 42060 || potential % 100000 == 42010 || potential % 100000 == 32010 || potential % 100000 == 40045 || 
                potential % 100000 == 32009 || potential % 100000 == 32058 || potential % 100000 == 42291 || potential % 100000 == 30357 || 
                potential % 100000 == 40054 || potential % 100000 == 32052 || potential % 100000 == 30551 || potential % 100000 == 30054 || 
                potential % 100000 == 42071 || potential % 100000 == 42009 || potential % 100000 == 30013 || potential % 100000 == 40005)) {   //Mikhail , µ¥¸ó º¸Á¶¹«±â
            return true;
        }
        if (!editional && itemtype == 110 && (potential % 100000 == 40004 || potential % 100000 == 40007 || potential % 100000 == 40002 || 
                potential % 100000 == 40650 || potential % 100000 == 42092 || potential % 100000 == 30001 || potential % 100000 == 30005 ||
                potential % 100000 == 42091 || potential % 100000 == 32091 || potential % 100000 == 32094 || potential % 100000 == 40656 ||
                potential % 100000 == 30007 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32092 ||
                potential % 100000 == 30002 || potential % 100000 == 32093 || potential % 100000 == 30004 || potential % 100000 == 40001 || 
                potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 30003 || potential % 100000 == 30008 ||
                potential % 100000 == 30006 || potential % 100000 == 40006 || potential % 100000 == 42501 || potential % 100000 == 40008 || 
                potential % 100000 == 42052 || potential % 100000 == 42009 || potential % 100000 == 32009 ||potential % 100000 == 42010 ||
                potential % 100000 == 32010 || potential % 100000 == 32058 || potential % 100000 == 40501 || potential % 100000 == 40502 || 
                potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 40014 || potential % 100000 == 30013 || 
                potential % 100000 == 42058 || potential % 100000 == 32054 || potential % 100000 == 42054 ||
                potential % 100000 == 30014 || potential % 100000 == 42060 || potential % 100000 == 42062 || potential % 100000 == 32071 || 
                potential % 100000 == 40013 || potential % 100000 == 40003 ))  {  
                    //Cloak
            return true;
                }
                
        if (!(itemtype_ == 1098 || itemtype_ == 1099) && (itemtype < 119 || itemtype >= 172) && potype == 10) { // When weapons are latent, not weapons
            return true;
        }
        if (itemtype != 100 && potype == 51) { // Not a hat, but a hat-only potential 
            return true;
        }
         if (!editional && itemtype == 100 && (potential % 100000 == 32661 || potential % 100000 == 42091 || potential % 100000 == 30001 || 
                potential % 100000 == 30005 || potential % 100000 == 30003 || potential % 100000 == 30001 || potential % 100000 == 40081 || 
                potential % 100000 == 32092 || potential % 100000 == 40005 || potential % 100000 == 30106 || potential % 100000 == 30006 ||
                potential % 100000 == 32091 || potential % 100000 == 40107 || potential % 100000 == 30008 || potential % 100000 == 30002 || 
                potential % 100000 == 30107 || potential % 100000 == 42092 || potential % 100000 == 32094 || potential % 100000 == 42094 ||
                potential % 100000 == 40003 || potential % 100000 == 30107 || potential % 100000 == 42093 || potential % 100000 == 30004 || 
                potential % 100000 == 42106 || potential % 100000 == 41006 || potential % 100000 == 42661 || potential % 100000 == 30007 || 
                potential % 100000 == 40106 || potential % 100000 == 40002 || potential % 100000 == 40007 || potential % 100000 == 40004 ||
                potential % 100000 == 32058 || potential % 100000 == 40008 || potential % 100000 == 40001 || potential % 100000 == 40006 || 
                potential % 100000 == 42009 || potential % 100000 == 32071 || potential % 100000 == 42058 || potential % 100000 == 42071 || 
                potential % 100000 == 32010 || potential % 100000 == 42062 || potential % 100000 == 32052 || potential % 100000 == 32054 || 
                potential % 100000 == 42060 || potential % 100000 == 42052 || potential % 100000 == 32009 || potential % 100000 == 40014 ||
                potential % 100000 == 30013 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 30014 || 
                potential % 100000 == 40013 || potential % 100000 == 32093)) { //hat
            return true;
         }
        if ((itemtype != 104 && itemtype != 105) && potype == 52) { // When it's not the top, but the potential
            return true;
        }
        if (!editional && itemtype == 104 && (potential % 100000 == 32094 || potential % 100000 == 30003 || potential % 100000 == 30007 || potential % 100000 == 40501 || 
            potential % 100000 == 32091 || potential % 100000 == 30002 || potential % 100000 == 30001 || potential % 100000 == 40081 || 
            potential % 100000 == 30004 || potential % 100000 == 42094 || potential % 100000 == 40005 || potential % 100000 == 40502 || 
            potential % 100000 == 40008 || potential % 100000 == 40656 || potential % 100000 == 30005 || potential % 100000 == 32092 || 
            potential % 100000 == 30006 || potential % 100000 == 42091 || potential % 100000 == 32093 || potential % 100000 == 40004 || 
            potential % 100000 == 40501 || potential % 100000 == 42092 || potential % 100000 == 42501 || potential % 100000 == 40007 || 
            potential % 100000 == 40001 || potential % 100000 == 42093 || potential % 100000 == 40003 || potential % 100000 == 40650 || 
            potential % 100000 == 42054 || potential % 100000 == 42010 || potential % 100000 == 40013 || potential % 100000 == 40014 || 
            potential % 100000 == 42058 || potential % 100000 == 42062 || potential % 100000 == 42071 || potential % 100000 == 32054 || 
            potential % 100000 == 32010 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 42009 || potential % 100000 == 42060 || potential % 100000 == 32071 || potential % 100000 == 30014 || 
            potential % 100000 == 30013 || potential % 100000 == 32009 || potential % 100000 == 42052 || potential % 100000 == 40006 || 
            potential % 100000 == 40002 || potential % 100000 == 30008)) { //top
            return true;
        }
        
        if (itemtype != 106 && potype == 53) { // Not bottom, but bottom potential
            return true;
        }
        if (!editional && itemtype == 106 && (potential % 100000 == 40502 || potential % 100000 == 30002 || potential % 100000 == 30003 || potential % 100000 == 30004 ||
            potential % 100000 == 40650 || potential % 100000 == 42091 || potential % 100000 == 32092 || 
            potential % 100000 == 30008 || potential % 100000 == 30006 || potential % 100000 == 32091 ||
            potential % 100000 == 40501 || potential % 100000 == 40007 || potential % 100000 == 40656 || potential % 100000 == 30007 ||
            potential % 100000 == 42501 || potential % 100000 == 42092 || potential % 100000 == 42093 || potential % 100000 == 30005 || 
            potential % 100000 == 40005 || potential % 100000 == 32094 || potential % 100000 == 40003 || potential % 100000 == 42094 || 
            potential % 100000 == 40001 || potential % 100000 == 40004 || potential % 100000 == 40002 || potential % 100000 == 30001 || 
            potential % 100000 == 40013 || potential % 100000 == 30014 || potential % 100000 == 40014 || potential % 100000 == 42062 || 
            potential % 100000 == 42058 || potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 42009 ||
            potential % 100000 == 42071 || potential % 100000 == 32054 || potential % 100000 == 32010 || potential % 100000 == 42054 ||
            potential % 100000 == 32071 || potential % 100000 == 42060 || potential % 100000 == 32058 || 
            potential % 100000 == 32052 || potential % 100000 == 42010 || potential % 100000 == 32009 || potential % 100000 == 40006 || 
            potential % 100000 == 40008 || potential % 100000 == 40081 || potential % 100000 == 32093 )) {
        //pants
            return true;
        }
        
        if (!editional && itemtype == 105 && (potential % 100000 == 32091 || potential % 100000 == 40004 || potential % 100000 == 30004 || potential % 100000 == 30003 || 
            potential % 100000 == 42092 || potential % 100000 == 32094 || potential % 100000 == 32092 || potential % 100000 == 42091 || 
            potential % 100000 == 30005 || potential % 100000 == 40650 || potential % 100000 == 30008 || potential % 100000 == 40005 || 
            potential % 100000 == 42093 || potential % 100000 == 40081 || potential % 100000 == 42501 || potential % 100000 == 32093 || 
            potential % 100000 == 40501 || potential % 100000 == 40502 || potential % 100000 == 42094 || potential % 100000 == 40008 || 
            potential % 100000 == 40007 || potential % 100000 == 30007 || potential % 100000 == 30001 || potential % 100000 == 40002 || 
            potential % 100000 == 40001 || potential % 100000 == 40656 || potential % 100000 == 30002 || potential % 100000 == 40006 || 
            potential % 100000 == 42058 || potential % 100000 == 40014 || potential % 100000 == 42010 || potential % 100000 == 42054 || 
            potential % 100000 == 40013 || potential % 100000 == 42071 || potential % 100000 == 32009 || potential % 100000 == 32054 || 
            potential % 100000 == 30014 || potential % 100000 == 30013 || potential % 100000 == 42060 || 
            potential % 100000 == 42062 || potential % 100000 == 42052 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 32071 || potential % 100000 == 42009 || potential % 100000 == 32010 || potential % 100000 == 40003 || 
            potential % 100000 == 30006)) { //Suit
            return true;
        }
        
        if (!editional && itemtype == 111 && (potential % 100000 == 30004 || potential % 100000 == 40006 || potential % 100000 == 30002 || potential % 100000 == 30005 || 
            potential % 100000 == 30001 || potential % 100000 == 42093 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
            potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32091 || potential % 100000 == 30007 || 
            potential % 100000 == 40002 || potential % 100000 == 40003 || potential % 100000 == 40001 || potential % 100000 == 30008 || 
            potential % 100000 == 42092 || potential % 100000 == 40008 || potential % 100000 == 40005 || potential % 100000 == 30006 || 
            potential % 100000 == 40007 || potential % 100000 == 30003 || potential % 100000 == 42091 || potential % 100000 == 40004 || 
            potential % 100000 == 42060 || potential % 100000 == 40356 || potential % 100000 == 42054 || potential % 100000 == 32010 || 
            potential % 100000 == 42062 || potential % 100000 == 40014 || potential % 100000 == 42009 || potential % 100000 == 32071 || 
            potential % 100000 == 30356 || potential % 100000 == 30013 || potential % 100000 == 30357 || 
            potential % 100000 == 42052 || potential % 100000 == 42071 || potential % 100000 == 42058 || potential % 100000 == 32009 || 
            potential % 100000 == 32052 || potential % 100000 == 40357 || potential % 100000 == 32054 || potential % 100000 == 42010 || 
            potential % 100000 == 40013 || potential % 100000 == 30014 || potential % 100000 == 32058 || potential % 100000 == 32092)) { //ring
            return true;
        }
        
        if (!editional && itemtype == 103 && (potential % 100000 == 30004 || potential % 100000 == 40006 || potential % 100000 == 30002 || potential % 100000 == 30005 || 
            potential % 100000 == 30001 || potential % 100000 == 42093 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
            potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32091 || potential % 100000 == 30007 || 
            potential % 100000 == 40002 || potential % 100000 == 40003 || potential % 100000 == 40001 || potential % 100000 == 30008 || 
            potential % 100000 == 42092 || potential % 100000 == 40008 || potential % 100000 == 40005 || potential % 100000 == 30006 || 
            potential % 100000 == 42062 || potential % 100000 == 40007 || potential % 100000 == 30003 || potential % 100000 == 42091 || potential % 100000 == 40004 || 
            potential % 100000 == 40014 || potential % 100000 == 32052 || potential % 100000 == 32071 || potential % 100000 == 42058 || potential % 100000 == 32010 || 
            potential % 100000 == 42071 || potential % 100000 == 32058 || potential % 100000 == 30013 || potential % 100000 == 32054 || potential % 100000 == 42060 || 
            potential % 100000 == 30014 || potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 42052 || 
            potential % 100000 == 40013 || potential % 100000 == 40356 || potential % 100000 == 30356 || potential % 100000 == 40357 || potential % 100000 == 30357 || 
            potential % 100000 == 32009 || potential % 100000 == 32092)) { //Earrings
            return true;
        }
        
        if (!editional && itemtype == 112 && (potential % 100000 == 30004 || potential % 100000 == 40006 || potential % 100000 == 30002 || potential % 100000 == 30005 || 
            potential % 100000 == 30001 || potential % 100000 == 42093 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
            potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32091 || potential % 100000 == 30007 || 
            potential % 100000 == 40002 || potential % 100000 == 40003 || potential % 100000 == 40001 || potential % 100000 == 30008 || 
            potential % 100000 == 42092 || potential % 100000 == 40008 || potential % 100000 == 40005 || potential % 100000 == 30006 || 
            potential % 100000 == 42062 || potential % 100000 == 40007 || potential % 100000 == 30003 || potential % 100000 == 42091 || potential % 100000 == 40004 || 
            potential % 100000 == 40014 || potential % 100000 == 32052 || potential % 100000 == 32071 || potential % 100000 == 42058 || potential % 100000 == 32010 || 
            potential % 100000 == 42071 || potential % 100000 == 32058 || potential % 100000 == 30013 || potential % 100000 == 32054 || potential % 100000 == 42060 || 
            potential % 100000 == 30014 || potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 42052 || 
            potential % 100000 == 40013 || potential % 100000 == 40356 || potential % 100000 == 30356 || potential % 100000 == 40357 || potential % 100000 == 30357 || 
            potential % 100000 == 32009 || potential % 100000 == 32092)) { //pendant
            return true;
        }
      
        if (!editional && itemtype == 113 && (potential % 100000 == 32094 || potential % 100000 == 40650 || potential % 100000 == 32092 || potential % 100000 == 40502 || 
            potential % 100000 == 42501 || potential % 100000 == 32093 || potential % 100000 == 30002 || potential % 100000 == 32091 || 
            potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 40008 || potential % 100000 == 40007 || 
            potential % 100000 == 30004 || potential % 100000 == 30001 || potential % 100000 == 40656 || potential % 100000 == 30006 || 
            potential % 100000 == 40005 || potential % 100000 == 30003 || potential % 100000 == 40004 || potential % 100000 == 40006 || 
            potential % 100000 == 30007 || potential % 100000 == 42091 || potential % 100000 == 30005 || potential % 100000 == 40081 || 
            potential % 100000 == 40501 || potential % 100000 == 40002 || potential % 100000 == 42092 || potential % 100000 == 42094 || 
            potential % 100000 == 32010 || potential % 100000 == 42071 || potential % 100000 == 42010 || potential % 100000 == 42009 || 
            potential % 100000 == 30013 || potential % 100000 == 32058 || potential % 100000 == 40014 || potential % 100000 == 40013 || 
            potential % 100000 == 32009 || potential % 100000 == 42058 || potential % 100000 == 32054 || potential % 100000 == 32071 || 
            potential % 100000 == 42054 || potential % 100000 == 42052 || potential % 100000 == 32052 || 
            potential % 100000 == 42060 || potential % 100000 == 30014 || potential % 100000 == 42062 || potential % 100000 == 40003 || 
            potential % 100000 == 42093)) {//belt
            return true;
        }
            
        if (itemtype != 108 && potype == 54) { // Gloves
            if (!editional)
                return true;
        }
        if (!editional && itemtype == 108 && (potential % 100000 == 30006 || potential % 100000 == 40001 || potential % 100000 == 30091 || 
            potential % 100000 == 40003 || potential % 100000 == 30005 || potential % 100000 == 30001 || potential % 100000 == 40701 || 
            potential % 100000 == 32092 || potential % 100000 == 42093 || potential % 100000 == 30002 || potential % 100000 == 30004 || 
            potential % 100000 == 42061 || potential % 100000 == 42059 || potential % 100000 == 30003 || potential % 100000 == 30702 || 
            potential % 100000 == 40650 || potential % 100000 == 30008 || potential % 100000 == 40007 || potential % 100000 == 32091 || 
            potential % 100000 == 32094 || potential % 100000 == 30093 || potential % 100000 == 30092 || potential % 100000 == 40656 || 
            potential % 100000 == 30007 || potential % 100000 == 40703 || potential % 100000 == 30701 || potential % 100000 == 40502 || 
            potential % 100000 == 40502 || potential % 100000 == 32093 || potential % 100000 == 40501 || potential % 100000 == 42092 || 
            potential % 100000 == 42094 || potential % 100000 == 41007 || potential % 100000 == 42091 || potential % 100000 == 40081 || 
            potential % 100000 == 40005 || potential % 100000 == 40006 || potential % 100000 == 40002 || potential % 100000 == 40702 || 
            potential % 100000 == 40013 || potential % 100000 == 42062 || potential % 100000 == 42009 || potential % 100000 == 42054 || 
            potential % 100000 == 42058 || potential % 100000 == 32058 || potential % 100000 == 40014 || 
            potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 42010 || potential % 100000 == 30013 || 
            potential % 100000 == 32054 || potential % 100000 == 42052 || potential % 100000 == 42060 || potential % 100000 == 30014 || 
            potential % 100000 == 32071 || potential % 100000 == 32010 || potential % 100000 == 32009 || potential % 100000 == 42501 || 
            potential % 100000 == 40004 || potential % 100000 == 40008 || potential % 100000 == 30094)) {//Gloves
            return true;
        }    
            
        if (!editional && itemtype == 115 && (potential % 100000 == 40004 || potential % 100000 == 40007 || potential % 100000 == 40002 || 
                potential % 100000 == 40650 || potential % 100000 == 42092 || potential % 100000 == 30001 || potential % 100000 == 30005 ||
                potential % 100000 == 42091 || potential % 100000 == 32091 || potential % 100000 == 32094 || potential % 100000 == 40656 ||
                potential % 100000 == 30007 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32092 ||
                potential % 100000 == 30002 || potential % 100000 == 32093 || potential % 100000 == 30004 || potential % 100000 == 40001 || 
                potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 30003 || potential % 100000 == 30008 ||
                potential % 100000 == 30006 || potential % 100000 == 40006 || potential % 100000 == 42501 || potential % 100000 == 40008 || 
                potential % 100000 == 42052 || potential % 100000 == 42009 || potential % 100000 == 32009 ||potential % 100000 == 42010 ||
                potential % 100000 == 32010 || potential % 100000 == 32058 || potential % 100000 == 40501 || potential % 100000 == 40502 || 
                potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 40014 || potential % 100000 == 30013 || 
                potential % 100000 == 42058 || potential % 100000 == 32054 || potential % 100000 == 42054 ||
                potential % 100000 == 30014 || potential % 100000 == 42060 || potential % 100000 == 42062 || potential % 100000 == 32071 || 
                potential % 100000 == 40013 || potential % 100000 == 40003 ))  {  
                    //Shoulder
            return true;
                }
        
        if (itemtype != 107 && potype == 55) { // shoes
            return true;
        }       
        
        if (!editional && itemtype == 107 && (potential % 100000 == 30004 || potential % 100000 == 40010 || potential % 100000 == 30007 || potential % 100000 == 30009 || 
            potential % 100000 == 42501 || potential % 100000 == 30002 || potential % 100000 == 40001 || potential % 100000 == 32092 || 
            potential % 100000 == 32091 || potential % 100000 == 40007 || potential % 100000 == 40650 || potential % 100000 == 32094 || 
            potential % 100000 == 30010 || potential % 100000 == 41005 || potential % 100000 == 42091 || potential % 100000 == 40002 || 
            potential % 100000 == 40003 || potential % 100000 == 40656 || potential % 100000 == 30003 || potential % 100000 == 40502 || 
            potential % 100000 == 30001 || potential % 100000 == 30006 || potential % 100000 == 30008 || potential % 100000 == 42092 || 
            potential % 100000 == 40081 || potential % 100000 == 40006 || potential % 100000 == 42094 || potential % 100000 == 40501 || 
            potential % 100000 == 40004 || potential % 100000 == 40008 || potential % 100000 == 30005 || potential % 100000 == 40009 || 
            potential % 100000 == 42062 || potential % 100000 == 42060 || potential % 100000 == 30014 || potential % 100000 == 40013 || 
            potential % 100000 == 42058 || potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 40014 || 
            potential % 100000 == 42054 || potential % 100000 == 32071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42071 || potential % 100000 == 42052 || potential % 100000 == 32054 || 
            potential % 100000 == 32010 || potential % 100000 == 32009 || potential % 100000 == 42093 || 
            potential % 100000 == 40005 || potential % 100000 == 32093)) {//shoes
            return true;
        } 
        
        if (!editional && itemtype == 101 && (potential % 100000 == 30004 || potential % 100000 == 40006 || potential % 100000 == 30002 || potential % 100000 == 30005 || 
            potential % 100000 == 30001 || potential % 100000 == 42093 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
            potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32091 || potential % 100000 == 30007 || 
            potential % 100000 == 40002 || potential % 100000 == 40003 || potential % 100000 == 40001 || potential % 100000 == 30008 || 
            potential % 100000 == 42092 || potential % 100000 == 40008 || potential % 100000 == 40005 || potential % 100000 == 30006 || 
            potential % 100000 == 42062 || potential % 100000 == 40007 || potential % 100000 == 30003 || potential % 100000 == 42091 || potential % 100000 == 40004 || 
            potential % 100000 == 40014 || potential % 100000 == 32052 || potential % 100000 == 32071 || potential % 100000 == 42058 || potential % 100000 == 32010 || 
            potential % 100000 == 42071 || potential % 100000 == 32058 || potential % 100000 == 30013 || potential % 100000 == 32054 || potential % 100000 == 42060 || 
            potential % 100000 == 30014 || potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 42052 || 
            potential % 100000 == 40013 || potential % 100000 == 40356 || potential % 100000 == 30356 || potential % 100000 == 40357 || potential % 100000 == 30357 || 
            potential % 100000 == 32009 || potential % 100000 == 32092)) { //Face decoration
            return true;
        }
        
        if (!editional && itemtype == 102 && (potential % 100000 == 30004 || potential % 100000 == 40006 || potential % 100000 == 30002 || potential % 100000 == 30005 || 
            potential % 100000 == 30001 || potential % 100000 == 42093 || potential % 100000 == 32094 || potential % 100000 == 32093 || 
            potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 32091 || potential % 100000 == 30007 || 
            potential % 100000 == 40002 || potential % 100000 == 40003 || potential % 100000 == 40001 || potential % 100000 == 30008 || 
            potential % 100000 == 42092 || potential % 100000 == 40008 || potential % 100000 == 40005 || potential % 100000 == 30006 || 
            potential % 100000 == 42062 || potential % 100000 == 40007 || potential % 100000 == 30003 || potential % 100000 == 42091 || potential % 100000 == 40004 || 
            potential % 100000 == 40014 || potential % 100000 == 32052 || potential % 100000 == 32071 || potential % 100000 == 42058 || potential % 100000 == 32010 || 
            potential % 100000 == 42071 || potential % 100000 == 32058 || potential % 100000 == 30013 || potential % 100000 == 32054 || potential % 100000 == 42060 || 
            potential % 100000 == 30014 || potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 42052 || 
            potential % 100000 == 40013 || potential % 100000 == 40356 || potential % 100000 == 30356 || potential % 100000 == 40357 || potential % 100000 == 30357 || 
            potential % 100000 == 32009 || potential % 100000 == 32092)) { //Eye decoration
            return true;
        }
        
        /*
       Weapon potentials
        */  
        if (!editional && itemtype == 123 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Desperado
            return true;
        }
        if (!editional && itemtype == 131 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //One-Handed Ax
            return true;
        }
        if (!editional && itemtype == 141 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Two-Handed Ax
            return true;
        }
        if (!editional && itemtype == 132 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //One-handed blunt
            return true;
        }
        if (!editional && itemtype == 142 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Two-handed blunt
            return true;
        }
        if (!editional && itemtype == 130 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //One-handed sword
            return true;
        }
        if (!editional && itemtype == 140 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Two-handed Sword
            return true;
        }
        if (!editional && itemtype == 144 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Pole arm
            return true;
        }
        if (!editional && itemtype == 143 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //window
            return true;
        }
        if (!editional && itemtype == 158 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Gauntlet Revolver
            return true;
        }
        if (!editional && itemtype == 145 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //bow
            return true;
        }
        if (!editional && itemtype == 146 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //crossbow
            return true;
        }
        if (!editional && itemtype == 152 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Dual Bow Gun
            return true;
        }
        if (!editional && itemtype == 121 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Shining Rod
            return true;
        }
        if (!editional && itemtype == 137 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Wand
            return true;
        }
        if (!editional && itemtype == 138 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //staff
            return true;
        }
        if (!editional && itemtype == 126 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //ESP limiter
            return true;
        }
        if (!editional && itemtype == 124 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Energy sword
            return true;
        }
        if (!editional && itemtype == 133 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //dagger
            return true;
        }
        if (!editional && itemtype == 147 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Ah
            return true;
        }
        if (!editional && itemtype == 134 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //blade
            return true;
        }
        if (!editional && itemtype == 127 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //chain
            return true;
        }
        if (!editional && itemtype == 136 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Cane
            return true;
        }
                if (!editional && itemtype == 148 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Knuckles
            return true;
        }
        if (!editional && itemtype == 149 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //key
            return true;
        }
        if (!editional && itemtype == 153 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Hand cannon
            return true;
        }
        if (!editional && itemtype == 122 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Soul Shooter
            return true;
        }
        if (!editional && itemtype == 128 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Magic Gauntlets
            return true;
        }
        if (!editional && itemtype == 159 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Ancient Bow
            return true;
        }
        if (!editional && itemtype == 156 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Greatsword zero
            return true;
        }
        if (!editional && itemtype == 157 && (potential % 100000 == 32094 || potential % 100000 == 42048 || potential % 100000 == 32045 || potential % 100000 == 30002 || 
            potential % 100000 == 40005 || potential % 100000 == 42093 || potential % 100000 == 42094 || potential % 100000 == 40081 || potential % 100000 == 40002 || 
            potential % 100000 == 30004 || potential % 100000 == 32093 || potential % 100000 == 42092 || potential % 100000 == 32092 || potential % 100000 == 32047 || 
            potential % 100000 == 40003 || potential % 100000 == 32091 || potential % 100000 == 30011 || potential % 100000 == 40004 || potential % 100000 == 32046 || 
            potential % 100000 == 40006 || potential % 100000 == 42046 || potential % 100000 == 30001 || potential % 100000 == 30007 || potential % 100000 == 42091 || 
            potential % 100000 == 40008 || potential % 100000 == 30006 || potential % 100000 == 40011 || potential % 100000 == 42045 || potential % 100000 == 42047 || 
            potential % 100000 == 40012 || potential % 100000 == 40007 || potential % 100000 == 30012 || potential % 100000 == 32048 || potential % 100000 == 30005 || 
            potential % 100000 == 30046 || potential % 100000 == 42062 || potential % 100000 == 30054 || potential % 100000 == 40357 || potential % 100000 == 42058 || 
            potential % 100000 == 42009 || potential % 100000 == 42010 || potential % 100000 == 42054 || potential % 100000 == 40014 || potential % 100000 == 30014 || 
            potential % 100000 == 40053 || potential % 100000 == 32071 || potential % 100000 == 30356 || potential % 100000 == 40054 || potential % 100000 == 32010 || 
            potential % 100000 == 42601 || potential % 100000 == 30551 || potential % 100000 == 40013 || potential % 100000 == 40045 || potential % 100000 == 40008 || 
            potential % 100000 == 30053 || potential % 100000 == 32009 || potential % 100000 == 42071 || potential % 100000 == 32052 || potential % 100000 == 32058 || 
            potential % 100000 == 30013 || potential % 100000 == 42052 || potential % 100000 == 30045 || potential % 100000 == 30357 || potential % 100000 == 32054 || 
            potential % 100000 == 42044 || potential % 100000 == 42060 || potential % 100000 == 40046 || potential % 100000 == 42291 || potential % 100000 == 40551 || 
            potential % 100000 == 40356 || potential % 100000 == 40001 || potential % 100000 == 30008 || potential % 100000 == 30003 )) {
        //Attitude zero
            return true;
        }
        if (editional) {
            
            if ((itemtype == 100 || itemtype == 135) && (potential == 42060)) {
                return false;
            }
                
            if (itemtype == 100 && (potential == 40106 || potential == 40556)) {
                return false;
            }
            if (itemtype == 103 && (potential == 40047)) {
                return true;
            }
            if (itemtype == 113 && (potential == 42501)) {
                return true; // Purl
            }
            if (itemtype == 104 && (potential == 42501)) {
                return true; // Purse
            }
            if (itemtype == 115 && (potential == 42501)) {
                return true; // Shoulder purging
            }
            if (itemtype == 106 && (potential == 42501)) {
                return true; // Bottoming
            }
            if (itemtype == 105 && (potential == 42501)) {
                return true; // Purse
            }
            if (itemtype == 108 && (potential == 42501 || potential % 100000 == 40057 || potential % 100000 == 40056)) {
                return true; // Glove purging
            }
            if (itemtype == 119 && (potential == 42602 || potential % 100000 == 32601)) {
                return true; // Emblem purging
            }
            if (itemtype == 108 && (potential == 42060)) {
                return false; //Knit Knitwear
            }
            if (itemtype == 167 && (potential == 32007 || potential % 100000 == 42007 || potential % 100000 == 32008 || potential % 100000 == 42008)) {
                return false; //Knit Heart
            }
            if (itemtype == 113 && (potential == 42060)) {
                return false; //Knit
            }
            if (itemtype == 115 && (potential == 42060)) {
                return false; //Shoulder-knit
            }
            if (itemtype == 135 && (potential == 42060)) {
                return false; //Kneading Auxiliary Weapon
            }
            
            if (itemtype == 167 && (potential == 42095 || potential == 42096 || potential == 32051 || potential == 42051 || potential == 32087 || potential == 42087 || 
                    potential % 1000 == 291 || potential == 42292 || (potential >= 30041 && potential <= 30044) || (potential >= 40041 && potential <= 40044) || 
                    potential == 32601 || potential == 32070 || potential == 42070 || potential == 32057 || potential == 32801 || potential == 42057 || 
                    potential == 32061 || potential == 42053 || potential == 32053 || potential == 32202 || potential == 32201 || 
                    potential == 32116  || potential == 42008 || potential == 42005 || potential == 32006 || potential == 32007 || 
                    potential == 42116 || potential == 42007 || potential == 42006 || potential == 42801 || potential == 42602 || 
                    potential == 32008 || potential == 32005 || potential == 32206)) {
                return true;
            }
            
            if (potype == 0 || potype == 10 || potype == 11 || potype == 20 || potype == 40 || potype == 54) { 
                
                if (isEditionalWeaponOption(potential)) {
                    if ((itemtype_ == 1098 || itemtype_ == 1099) || (itemtype >= 119 && itemtype < 172 && itemtype != 167)) { //Eddie weapons option, if the item is a weapon
                        return false;
                    } else {
                        if (isEditionalArmorOption(potential)) { 
                            return false;
                        }
                    }
                } else {
                    if ((itemtype_ == 1098 || itemtype_ == 1099) || (itemtype >= 119 && itemtype < 172 && itemtype != 167)) { //Eddie weapons option, if the item is a weapon
                        return true;
                    }
                    
                    if (isEditionalGloveOption(potential)) {
                        if (itemtype == 108) { // Gloves
                            return false;
                        } 
                        return true;
                    } else {
                        if (isEditionalArmorOption(potential)) {
                            return false;
                        }
                    }
                    return true;
                }
            } else {
                return true;
            }
            return true;
        }
        return false;
    }
    
    private boolean isEditionalWeaponOption(int potentialID) {
        switch (potentialID) {
            case 306:
            case 307:
            case 2011:
            case 2012:
            case 12001:
            case 12002:
            case 12003:
            case 12004:
            case 12011:
            case 12012:
            case 12041:
            case 12042:
            case 12043:
            case 12044:
            case 12051:
            case 12052:
            case 12055:
            case 12070:
            case 12082:
            case 12801:
            case 12081:
            case 22001:
            case 22002:
            case 22003:
            case 22004:
            case 22011:
            case 22012:
            case 22041:
            case 22042:
            case 22043:
            case 22044:
            case 22051:
            case 22052:
            case 22056:
            case 22070:
            case 22087:
            case 22201:
            case 22206:
            case 22801:
            case 22802:
            case 22291:
            case 30041:
            case 30042:
            case 30043:
            case 30044:
            case 30091:
            case 30092:
            case 30093:
            case 30094:
//            case 32001:
//            case 32002:
//            case 32003:
//            case 32004:
//            case 32011:
//            case 32012:
//            case 32041:
//            case 32042: // dex 5
//            case 32043: // int 5
//            case 32044:
            case 32047:
            case 32048:
            case 32051:
            case 32053:
            case 32057:
//            case 32058: 
            case 32061:
            case 32070:
            case 32087:
            case 32116:
            case 32291:
            case 32601:
            case 32801:
            case 32201:
            case 32206:
            case 40041: // 12 force
            case 40042:
            case 40043: // Phosphorus 12 fur
            case 40044:
//            case 42001:
//            case 42002:
//            case 42003:
//            case 42004:
//            case 42011:
//            case 42012:
//            case 42041: Force 7
//            case 42042:
//            case 42043:
//            case 42044:
            case 42047:
            case 42048:
            case 42051:
            case 42053:
            case 42057:
//            case 42058:
            case 42070:
            case 42087:
            case 42091:
            case 42092:
            case 42093:
            case 42094:  
            case 42116:
//            case 42291:
            case 42292:
//            case 42601:
            case 42602:
            case 42801:
            case 42095:
            case 42096:
                return true;
        }
        return false;
    }
    
    private boolean isEditionalArmorOption(int potentialID) {
        switch (potentialID) {
            case 13:
            case 14:
            case 2011:
            case 2012:
            case 10013:
            case 10014:
            case 10045:
            case 10046:
            case 10053:
            case 10054:
            case 12001:
            case 12002:
            case 12003:
            case 12004:
            case 12011:
            case 12012:
            case 12041:
            case 12042:
            case 12043:
            case 12044:
            case 12053:
            case 12054:
            case 12081:
            case 20013:
            case 20014:
            case 20053:
            case 20054:
            case 22001:
            case 22002:
            case 22003:
            case 22004:
            case 22005:
            case 22006:
            case 22007:
            case 22008:
            //case 22009: // Movement Speed ??8
            //case 22010: //Jump power 8
            case 22011:
            case 22012:
            case 22013:
            case 22014:
            case 22041:
            case 22042:
            case 22043:
            case 22044:
            case 22053:
            case 22054:
//            case 22056: //Chance rate 2%
            case 22086:
            case 22802:
//            case 30013:
//            case 30014:
//            case 30045:
//            case 30046:
//            case 30053:
//            case 30054:
            case 30551:
            case 30091:
            case 30092:
            case 30093:
            case 30094:
            case 32001:
            case 32002:
            case 32003:
            case 32004:
            case 32005:
            case 32006:
            case 32007:
            case 32008:
//            case 32009:
//            case 32010:
            case 32011:
            case 32012:
            case 32013:
            case 32014:
            case 32041:
            case 32042:
            case 32043:
            case 32044:
            case 32045:
            case 32046:
            case 32047:
            case 32048:
//            case 32052:
            case 32055:
            case 32056:
//            case 32058: 
//            case 32071:
            case 32086:
            case 32091:
            case 32092:
            case 32093:
            case 32094:
            case 32111:
            case 32802:
            case 32551: 
//            case 40013:
//            case 40014:
//            case 40045:
//            case 40046:
//            case 40053:
//            case 40054:
//            case 40551:
            case 42001:
            case 42002:
            case 42003:
            case 42004:
            case 42005:
            case 42006:
            case 42007:
            case 42008:
//            case 42009:
//            case 42010:
            case 42011:
            case 42012:
            case 42013:
            case 42014:
            case 42041:
            case 42042:
            case 42043: 
            case 42044:
            case 42045:
            case 42046:
            case 42047:
            case 42048:
//            case 42052:
            case 42055:
            case 42056:
//            case 42058:
//            case 42071:
            case 42086:
            case 42091:
            case 42092:
            case 42093:
            case 42094:
            case 42111:
            case 42802:
            case 42501:
            case 42551:
            case 42650:
            case 42656:
            case 60064:
            case 60066:
            case 60072:
                return true;
        }
        return false;
    }
    
    private boolean isEditionalGloveOption(int potentialID) {
        switch (potentialID) {
            case 40056:
            case 40057:
            case 42059:
                return true;
        }
        return false;
    }
}
