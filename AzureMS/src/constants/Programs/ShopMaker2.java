package constants.Programs;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.Items.ItemInformation;
import tools.Triple;

public class ShopMaker2 {

    private static int MIN_LEVEL = 0, MAX_LEVEL = 100;
    private static List<Integer> EXCPET_ITEMS = Arrays.asList(1003142, 1402037);
    private static List<String> EXCPET_ITEM_NAMES = Arrays.asList("À§Á¬");
    private static ItemInformation nameSource = ItemInformation.getInstance();
    private static int MESO_RATE = -1;
    private static int NPC = -1;
    private static int COMMON = 10000;
    private static int WARRIOR = 10100;
    private static int MAGE = 10200;
    private static int BOWMAN = 10300;
    private static int THIEF = 10400;
    private static int PIRATE = 10500;
    private static int CASH = 10600;

    private static Map<Integer, Integer> itemMap = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        Console c = System.console();
        while (MESO_RATE == -1) { // Sale price setting
            try {
                MESO_RATE = Integer.parseInt(c.readLine("How many times do you want to resell the item's price? : "));
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number.");
            } catch (Exception e) {
                System.out.println("Unknown error. : " + e);
            }
        }
        System.out.println();

        while (NPC == -1) { // Sale price setting
            try {
                NPC = Integer.parseInt(c.readLine("Please enter the NPC code to use for sale. : "));
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number.");
            } catch (Exception e) {
                System.out.println("Unknown error. : " + e);
            }
        }
        System.out.println();

        System.out.println("Start creating all item store files.");
        long start = System.currentTimeMillis();
        boolean isLast;
        try {
            PrintWriter out = getCreateFile("AShopItems.sql");
            out.println("DELETE FROM `shopitems` WHERE `shopid` >= 10000 AND `shopid` <= 11000;");
            out.println("INSERT INTO `shopitems` (`shopid`,`itemid`,`price`,`position`) VALUES");

            List<Triple<Integer, Integer, Integer>> all = getAllItems();
            for (Triple<Integer, Integer, Integer> item : all) {
                int i = 0;
                if (!itemMap.containsKey(item.getSecond())) {
                    itemMap.put(item.getSecond(), 1);
                    i = 1;
                } else {
                    i = itemMap.get(item.getSecond());
                    i++;
                    itemMap.remove(item.getSecond());
                    itemMap.put(item.getSecond(), i);
                }
                isLast = all.indexOf(item) == all.size() - 1;
                StringBuilder shopitems = new StringBuilder();
                shopitems.append("(").append(item.getSecond()).append(",").append(item.getFirst()).append(",")
                        .append(item.getThird() * MESO_RATE).append(",").append(i).append(")")
                        .append(isLast ? ";" : ",");
                out.println(shopitems.toString());
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Failed to load item. : " + e);
            return;
        }
        List<Integer> common = new ArrayList<Integer>();
        List<Integer> warrior = new ArrayList<Integer>();
        List<Integer> mage = new ArrayList<Integer>();
        List<Integer> thief = new ArrayList<Integer>();
        List<Integer> pirate = new ArrayList<Integer>();
        List<Integer> archer = new ArrayList<Integer>();
        List<Integer> cash = new ArrayList<Integer>();
        try {
            PrintWriter sql = getCreateFile("AShop.sql");
            sql.println("DELETE FROM `shops` WHERE `shopid` >= 10000 AND `shopid` <= 11000;");
            sql.println("INSERT INTO `shops` (`shopid`, `npcid`) VALUES ");
            Iterator<Integer> items = itemMap.keySet().iterator();
            while (items.hasNext()) {
                int itemEntry = items.next();
                StringBuilder shops = new StringBuilder();
                isLast = !items.hasNext();
                shops.append("(").append(itemEntry).append(", ").append(NPC).append(")").append(isLast ? ";" : ",");
                sql.println(shops.toString());

                switch ((itemEntry % 1000) / 100) {
                    case 0:
                        common.add(itemEntry);
                        break;
                    case 1:
                        warrior.add(itemEntry);
                        break;
                    case 2:
                        mage.add(itemEntry);
                        break;
                    case 3:
                        archer.add(itemEntry);
                        break;
                    case 4:
                        thief.add(itemEntry);
                        break;
                    case 5:
                        pirate.add(itemEntry);
                        break;
                    case 6:
                        cash.add(itemEntry);
                        break;
                }
            }
            sql.close();
            PrintWriter name = getCreateFile("desc.txt");
            name.println("Etc Shop :");
            dropInfo(common, name);
            name.println("Warrior shop :");
            dropInfo(warrior, name);
            name.println("Wizard shop :");
            dropInfo(mage, name);
            name.println("Archer shop :");
            dropInfo(archer, name);
            name.println("Rogue shop :");
            dropInfo(thief, name);
            name.println("Pirate shop :");
            dropInfo(pirate, name);
            name.println("Cash shop :");
            dropInfo(cash, name);
            name.println("\r\n");
            name.println("Excluded item code :");
            for (int id : EXCPET_ITEMS) {
                name.print(id + ", ");
            }
            name.println();
            name.println("Excluded Item Name :");
            for (String conname : EXCPET_ITEM_NAMES) {
                name.print(conname + ", ");
            }
            name.close();
        } catch (Exception e) {
            System.out.println("Unknown error.");
        }
        long end = System.currentTimeMillis();
        System.out.println("Completed.");
        System.out.println("AShopItems.sql, AShop.sql, desc.txt Please check the file.");
        System.exit(0);
        return;
    }

    private static PrintWriter getCreateFile(String filename) throws IOException {
        File cf = new File(filename);
        if (!cf.exists()) {
            cf.createNewFile();
        }
        FileWriter of = new FileWriter(cf.getAbsolutePath());
        PrintWriter sql = new PrintWriter(of);
        return sql;
    }

    private static void dropInfo(List<Integer> list, PrintWriter name) {
        if (list.isEmpty()) {
            return;
        }
        Collections.sort(list);
        for (Integer Int : list) {
            int value = Int.intValue() % 100;
            EquipType et = EquipType.getEquipTypeByID(value);
            String shopname;
            if (et == null) {
                shopname = MapleWeaponType.getMapleWeaponTypeById(value - EquipType.¹«±â.shopIdAlter()).name();
            } else {
                shopname = et.name();
            }
            String a = "    ";
            a += shopname;
            a += " shop : " + Int.intValue();
            name.println(a);
        }
        name.println();
    }

    private static List<Triple<Integer, Integer, Integer>> getAllItems() { // Item, shop, price
        File source = new File(System.getProperty("wzpath") + "Character.wz");
        MapleDataProvider sourceData = MapleDataProviderFactory.getDataProvider(source);
        List<Triple<Integer, Integer, Integer>> list = new ArrayList<Triple<Integer, Integer, Integer>>();
        String[] category = {"Accessory", "Cap", "Cape", "Coat", "Glove", "Longcoat", "Pants", "Shield", "Shoes",
            "Weapon"};
        for (String cat : category) {
            File path = new File(source.getAbsolutePath() + "/" + cat);
            sourceData = MapleDataProviderFactory.getDataProvider(path);
            filefor:
            for (File file : path.listFiles()) {
                int level = 0;
                int job = 0;
                boolean isCash = false;
                String islot = "";
                int price = 0;

                MapleData data = sourceData.getData(file.getName().replace(".xml", ""));
                MapleData info = data.getChildByPath("info");
                String filename = file.getName().replace(".img.xml", "");
                int id = Integer.parseInt(filename);
                level = MapleDataTool.getInt("reqLevel", info, 0); // level
                if (level < MIN_LEVEL || level > MAX_LEVEL) {
                    continue;
                }
                if (EXCPET_ITEMS.contains(id)) {
                    continue; // TODO Create Excluded Item Store..?
                }
                String name = nameSource.getName(id);
                if (name == null || name.equals("") || name.equals("NO-NAME")) {
                    continue;
                }
                if (EXCPET_ITEM_NAMES.contains(name)) {
                    continue;
                }
                for (String contain : EXCPET_ITEM_NAMES) {
                    if (name.contains(contain)) {
                        continue filefor;
                    }
                }

                islot = MapleDataTool.getString("islot", info, "gbg");
                job = MapleDataTool.getInt("reqJob", info, 0); // Job classification
                isCash = MapleDataTool.getInt("cash", info, 0) == 1; // Check for Cached Items
                if ((price = MapleDataTool.getInt("price", info, isCash ? 10 : 0)) <= 1) {
                    continue; // Check price
                }
                if (MapleDataTool.getInt("epicItem", info, 0) == 1
                        || MapleDataTool.getInt("equipTradeBlock", info, 0) == 1
                        || MapleDataTool.getInt("tradeBlock", info, 0) == 1
                        || MapleDataTool.getInt("quest", info, 0) == 1 || MapleDataTool.getInt("notSale", info, 0) == 1
                        || MapleDataTool.getInt("only", info, 0) == 1
                        || MapleDataTool.getInt("expireOnLogout", info, 0) == 1
                        || (nameSource.getDesc(id) != null && !isCash)) {
                    continue; // Not sold condition
                }
                EquipType type = EquipType.getEquipType(islot);
                if (type == null) {
                    System.out.println("It is an unknown item.");
                    continue;
                }

                try {
                    int[] temp = {0, WARRIOR, MAGE, 0, BOWMAN, 0, 0, 0, THIEF, 0, 0, 0, 0, 0, 0, 0, PIRATE};
                    int shopId = COMMON;
                    if (!isCash) {
                        try {
                            int temped = temp[job];
                            if (temped == 0) {
                                shopId = COMMON;
                            } else {
                                shopId = temped;
                            }
                        } catch (Exception e) {
                            shopId = COMMON;
                        }
                        shopId += type.shopIdAlter;
                        if (isWeapon(id)) {
                            shopId += MapleWeaponType.getWeaponType(id).getShopIdAlter();
                        }
                        list.add(new Triple<Integer, Integer, Integer>(id, shopId, price));
                    } else {
                        shopId = CASH;
                        shopId += type.shopIdAlter;
                        if (isWeapon(id)) {
                            shopId += MapleWeaponType.getWeaponType(id).getShopIdAlter();
                        }
                        list.add(new Triple<Integer, Integer, Integer>(id, shopId, price));
                    }
                } catch (NullPointerException e) {
                    System.out.println("No pointer.");
                }
            }
        }
        return list;
    }

    private static boolean isWeapon(int itemId) {
        return !MapleWeaponType.getWeaponType(itemId).equals(MapleWeaponType.¹«±â¾Æ´Ô);
    }

    private static enum EquipType {
        ¸ðÀÚ("cp", 1), °¡¹ß("hrcp", 1), ¾ó±¼Àå½Ä("af", 2), ´«Àå½Ä("ay", 3), ±Í°í¸®("ae", 4), ½Å¹ß("so", 5), ¸ÁÅä("sr", 6), »óÀÇ("ma",
                7), Àå°©("gv", 8), ÇÑ¹ú¿Ê("mapn", 9), ÇÏÀÇ("pn", 10), ¹ÝÁö("ri", 11), ¹æÆÐ("si", 12), Ææ´øÆ®("pe", 13), Çã¸®¶ì("be",
                13), ¾î±úÀå½Ä("sh", 13), ÈÆÀå("me", 14), ¹«±â("wp", 15), º¸Á¶¹«±â("wpsi", 15), ¾²·¹±â("gbg", 0),;

        private String tag;
        private int shopIdAlter;

        private EquipType(String tag, int alter) {
            this.tag = tag;
            shopIdAlter = alter;
        }

        private int shopIdAlter() {
            return shopIdAlter;
        }

        private static EquipType getEquipType(String st) {
            for (EquipType et : EquipType.values()) {
                if (st.equalsIgnoreCase(et.tag)) {
                    return et;
                }
            }
            return null;
        }

        private static EquipType getEquipTypeByID(int id) {
            for (EquipType et : EquipType.values()) {
                if (id == et.shopIdAlter()) {
                    return et;
                }
            }
            return null;
        }
    }

    private enum MapleWeaponType {
        ¹«±â¾Æ´Ô(0), ÇÑ¼Õµµ³¢(1), µÎ¼Õµµ³¢(2), ÇÑ¼ÕµÐ±â(3), µÎ¼ÕµÐ±â(4), È°(5), ¾Æ´ë(6), ¼®±Ã(7), ´Ü°Ë(8), ÃÑ(9), ³ÊÅ¬(10), Æú¾Ï(11), Ã¢(12), ½ºÅÂÇÁ(
                13), ÇÑ¼Õ°Ë(14), µÎ¼Õ°Ë(15), ¿Ïµå(16), »þÀÌ´×·Îµå(17), ¼Ò¿ï½´ÅÍ(18), ºí·¹ÀÌµå(19), º¸Á¶¹«±â(20), ÄÉÀÎ(21), µà¾óº¸¿ì°Ç(22),;

        private int shopIdAlter;

        private MapleWeaponType(int shopIdAlter) {
            this.shopIdAlter = shopIdAlter;
        }

        public int getShopIdAlter() {
            return shopIdAlter;
        }

        private static MapleWeaponType getWeaponType(int itemId) {
            int cat = (itemId / 10000) % 100;
            MapleWeaponType[] type = {¹«±â¾Æ´Ô, »þÀÌ´×·Îµå, ¼Ò¿ï½´ÅÍ, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, ÇÑ¼Õ°Ë, ÇÑ¼Õµµ³¢, ÇÑ¼ÕµÐ±â, ´Ü°Ë,
                ºí·¹ÀÌµå, º¸Á¶¹«±â, ÄÉÀÎ, ¿Ïµå, ½ºÅÂÇÁ, ¹«±â¾Æ´Ô, µÎ¼Õ°Ë, µÎ¼Õµµ³¢, µÎ¼ÕµÐ±â, Ã¢, Æú¾Ï, È°, ¼®±Ã, ¾Æ´ë, ³ÊÅ¬, ÃÑ, ¹«±â¾Æ´Ô, ¹«±â¾Æ´Ô, µà¾óº¸¿ì°Ç};
            if (0 > cat - 20 || type.length <= cat - 20) {
                return ¹«±â¾Æ´Ô;
            }
            return type[cat - 20];
        }

        public static MapleWeaponType getMapleWeaponTypeById(int id) {
            for (MapleWeaponType mwt : MapleWeaponType.values()) {
                if (id == mwt.getShopIdAlter()) {
                    return mwt;
                }
            }
            return null;
        }
    }
}
