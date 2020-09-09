package constants.Programs;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import server.Items.ItemInformation;
import tools.Triple;

public class CheckItemExist {

    private static HashMap<Integer, Integer> itemList = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        Console c = System.console();
        RewardIncubator();
        boolean isLast;
        try {
            PrintWriter sql = getCreateFile("incubatordata.sql");
            sql.println("INSERT INTO `incubatordata` (`itemid`, `amount`) VALUES ");
            Iterator<Integer> items = itemList.keySet().iterator();
            while (items.hasNext()) {
                int itemEntry = items.next();
                StringBuilder shops = new StringBuilder();
                isLast = !items.hasNext();
                shops.append("(").append(itemEntry).append(", ").append("1").append(")").append(isLast ? ";" : ",");
                sql.println(shops.toString());

            }
            sql.close();
        } catch (Exception e) {
            System.out.println("Unknown error.");
        }

        System.out.println("Completed.");
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

    private static void checkItem(int itemid, int type) { // Item, shop, price
        boolean isExist = false;
        if (type == 1) {
            File source = new File("wz/Character.wz");
            List<Triple<Integer, Integer, Integer>> list = new ArrayList<Triple<Integer, Integer, Integer>>();
            String[] category = {"Accessory", "Cap", "Cape", "Coat", "Glove", "Longcoat", "Pants", "Shield", "Shoes",
                "Weapon"};
            for (String cat : category) {
                File path = new File(source.getAbsolutePath() + "/" + cat + "/" + "0" + itemid + ".img.xml");
                if (path.exists()) {
                    isExist = true;
                }
            }
        } else if (type == 2 || type == 3 || type == 4) {
            try {
                File source = new File("wz/Item.wz");
                MapleDataProvider sourceData;
                List<Triple<Integer, Integer, Integer>> list = new ArrayList<Triple<Integer, Integer, Integer>>();
                String[] category = {"", "", "Consume", "Install", "Etc", "Cash", "Pet", "Special"};
                String cat = category[type];

                File path = new File(source.getAbsolutePath() + "/" + cat);
                sourceData = MapleDataProviderFactory.getDataProvider(path);
                MapleData dd = sourceData.getData("0" + itemid / 10000 + ".img");
                if (dd.getChildByPath("0" + itemid) != null) {
                    isExist = true;
                }
            } catch (Exception ex) {
            }
        }
        if (!isExist) {
            System.out.println(itemid);
        }
    }

    public static void RewardIncubator() {
        System.out.println("RewardIncubator Called.:::");
        try {
            FileReader fl = new FileReader("rewardincubator.celphis");
            BufferedReader br = new BufferedReader(fl);
            String readLine = null;
            final ItemInformation ii = ItemInformation.getInstance();
            int i = 0;
            while ((readLine = br.readLine()) != null) {

                checkItem(Integer.parseInt(readLine), Integer.parseInt(readLine) / 1000000);
                i++;
            }
            fl.close();
            br.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
