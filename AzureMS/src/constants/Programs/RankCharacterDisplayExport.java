package constants.Programs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import constants.GameConstants;
import connections.Database.MYSQL;
import launcher.LauncherHandlers.MapleCacheData;
import server.Items.ItemInformation;

public class RankCharacterDisplayExport {

    public static List<Integer> getExpList(Connection con, int level) throws SQLException {
        PreparedStatement ps = con
                .prepareStatement("SELECT * FROM characters WHERE level = " + level + " ORDER BY exp ");
        ResultSet rs = ps.executeQuery();
        List<Integer> exps = new ArrayList<>();
        while (rs.next()) {
            exps.add(rs.getInt("id"));
        }
        rs.close();
        ps.close();
        return exps;
    }

    public static List<String> listRunningProcesses() {
        List<String> processes = new ArrayList();
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    processes.add(line.substring(0, line.indexOf(" ")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processes;
    }

    public static void main(String args[]) throws SQLException, InterruptedException {
        String LuacherTop3Info = "";
        File f = new File("C:\\Users\\gurumotoa\\Desktop\\hOney" + "\\rankDisplay\\");
        for (File s : f.listFiles()) {
            s.delete();
        }
        List<Integer> id = new ArrayList<>();
        List<Integer> level = new ArrayList<>();
        MYSQL.init();
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con
                .prepareStatement("SELECT * FROM characters WHERE gm = 0 ORDER BY reborns DESC");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            /*
			 * if (!level.contains(rs.getInt("level"))) { List<Integer> exps =
			 * getExpList(con, rs.getInt("level")); for (Integer i : exps) { if (id.size()
			 * == 3) { break; }
             */
            id.add(rs.getInt("id"));
            /*
			 * } level.add(rs.getInt("level")); } if (id.size() == 3) { break; }
             */
        }
        rs.close();
        ps.close();
        con.close();
        MapleCacheData mc = new MapleCacheData();
        mc.startCacheData();
        int rank = 1;
        for (Integer i : id) {
            MapleCharacter chr = MapleCharacter.loadCharFromDB(i, new MapleClient(null, null, null), false);
            final Map<Short, Integer> myEquip = new LinkedHashMap<>();
            final Map<Short, Integer> maskedEquip = new LinkedHashMap<>();
            MapleInventory equip = chr.getInventory(MapleInventoryType.EQUIPPED);
            if (chr.getGender() == 0) {
                if (equip.getItem((short) -5) == null) {
                    myEquip.put((short) 5, 1040036);
                }
                if (equip.getItem((short) -6) == null) {
                    if (equip.getItem((short) -5) != null && (equip.getItem((short) -5).getItemId() / 10000) != 105) {
                        myEquip.put((short) 6, 1060026);
                    } else if (equip.getItem((short) -5) == null) {
                        myEquip.put((short) 6, 1060026);
                    }
                }
            } else if (chr.getGender() == 1) {
                if (equip.getItem((short) -5) == null) {
                    myEquip.put((short) 5, 1041046);
                }
                if (equip.getItem((short) -6) == null) {
                    if (equip.getItem((short) -5) != null && (equip.getItem((short) -5).getItemId() / 10000) != 105) {
                        myEquip.put((short) 6, 1061039);
                    } else if (equip.getItem((short) -5) == null) {
                        myEquip.put((short) 6, 1061039);
                    }
                }
            }
            for (final IItem item : equip.list()) {
                short pos_ = 0;
                short posTemp = item.getPosition();
                if (posTemp == -11 || posTemp == -10) {
                    continue;
                }
                IEquip item_ = (IEquip) item;
                short pos = pos_ == 0 ? (short) (posTemp * -1) : pos_;
                if (pos < 100 && myEquip.get(pos) == null) {
                    String lol = ((Integer) item.getItemId()).toString();
                    String ss = lol.substring(0, 3);
                    int moru = Integer.parseInt(ss + ((Integer) item_.getPotential7()).toString());
                    myEquip.put(pos, item_.getPotential7() != 0 ? moru : item.getItemId());
                } else if (pos > 100 && pos != 111) {
                    pos -= 100;
                    if (pos == -11 || pos == -10) {
                        continue;
                    }
                    if (myEquip.get(pos) != null) {
                        maskedEquip.put(pos, myEquip.get(pos));
                    }
                    String lol = ((Integer) item.getItemId()).toString();
                    String ss = lol.substring(0, 3);
                    int moru = Integer.parseInt(ss + ((Integer) item_.getPotential7()).toString());
                    myEquip.put(pos, item_.getPotential7() != 0 ? moru : item.getItemId());
                } else if (myEquip.get(pos) != null) {
                    maskedEquip.put(pos, item.getItemId());
                }
            }
            String cody = "";
            cody = (2000 + chr.getSkinColor() + "," + chr.getFace() + "," + chr.getHair() + ",");
            for (Entry<Short, Integer> e : myEquip.entrySet()) {
                if (!GameConstants.isWeapon(e.getValue())) {
                    cody += (e.getValue() + ",");
                }
            }
            if (GameConstants.isZero(chr.getJob())) {
                // cody += equip.getItem((short) -11).getItemId() + ",";
            } else {
                IItem cWeapon = equip.getItem((byte) -111);
                IItem weapon = equip.getItem((byte) -11);
                if (cWeapon != null) {
                    // cody += cWeapon.getItemId() + ",";
                } else if (weapon != null) {
                    // cody += weapon.getItemId();
                }
            }
            if (chr.getSecondFace() > 0 && !GameConstants.isZero(chr.getJob())) {
                cody += (chr.getSecondFace());
            }
            if (!LuacherTop3Info.equals("")) {
                LuacherTop3Info += ":";
            }
            LuacherTop3Info += rank + "." + chr.getLevel() + "." + chr.getName() + ".png";
            cody += (" " + rank + "." + chr.getLevel() + "." + chr.getName());
            int weap = equip.getItem((short) -11) != null ? equip.getItem((short) -11).getItemId() : 0;
            String walk = "stand1";
            if (weap != 0) {
                if (ItemInformation.getInstance().isWalk2(weap)) {
                    walk = "stand2";
                }
            }
            cody += " " + walk;
            cody += " C:\\Users\\gurumotoa\\Desktop\\hOney\\rankDisplay\\";
            Runtime rt = Runtime.getRuntime();
            String exeFile = "CharacterDisplay\\WzComparerR2.exe " + cody;
            Process p;
            try {
                p = rt.exec(exeFile);
                p.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
            boolean checkW2 = true;
            while (checkW2) {
                List<String> list = listRunningProcesses();
                checkW2 = false;
                for (String pr : list) {
                    if (pr.contains("WzComparerR2")) {
                        checkW2 = true;
                    }
                }
            }
            rank++;
        }

        System.out.println("¿Ï·á");
    }

    public static long get() {
        return 11L;
    }

    public static long get_() {
        return 15000001875000L;
    }
}
