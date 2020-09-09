package client.ItemInventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import client.MapleClient;
import connections.Packets.MainPacketCreator;

public class EquipWorthCalculator {

    private int seed = new Random().nextInt();

    public static EquipWorthCalculator ewc() {
        return new EquipWorthCalculator();
    }

    public static int worth(Equip eqp) {
        int k = 0;
        k += eqp.getStr() * 3; // 45
        k += eqp.getDex() * 3; // 45
        k += eqp.getInt() * 3; // 45
        k += eqp.getLuk() * 3; // 45
        k += eqp.getWatk() * 10; // 200
        k += eqp.getMatk() * 10; // 200
        k += eqp.getWdef(); // 150
        k += eqp.getMdef(); // 150
        k += eqp.getAvoid() + eqp.getAcc(); // 300
        return k; // 1190 (About 1200 is average)
    }

    public String listByClient(MapleClient c) {
        StringBuilder sb = new StringBuilder("#b#e[Equipment Item List]#n#k");
        for (IItem item : c.getPlayer().getInventory(MapleInventoryType.EQUIP).list()) {
            sb.append("\r\n#L").append(item.getPosition()).append("##i").append(item.getItemId()).append("##t")
                    .append(item.getItemId()).append("#");
        }
        return sb.toString();
    }

    public boolean grind(MapleClient c, int slot) {
        Equip eqp = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) slot);
        if (eqp.getLines() > 0) {
            final int modify; // The higher the value, the wider the cube will be.
            int w = worth(eqp);
            if (w >= 0 && w < 200) { // Fuck bang trying to disassemble rotten items?; It's a sword
                c.getPlayer().dropMessage(5, "Dark light begins to drift on the equipment.");// Why is the thread slip? ?
                modify = -1;
            } else if (w >= 200 && w < 3600) { // The equipment is awesome. It's trash.
                c.getPlayer().dropMessage(5, "The device disassembles with a slight flash.");//Why is the thread slip? ?
                modify = 0;
            } else if (w >= 3600 && w < 8100) { // You're drunk.
                c.getPlayer().dropMessage(5, "The equipment disassembles with a little flash.");// Why is the thread slip? ?
                modify = 1;
            } else if (w >= 8100 && w < 14400) { // Heat
                c.getPlayer().dropMessage(5, "Equipment disassembles with normal flash.");// Why is the thread slip? ?
                modify = 2;
            } else if (w >= 14400 && w < 30000) { // ¤µ¤¼¤º
                c.getPlayer().dropMessage(5, "Equipment disassembles with bright flashes.");// Why is the thread slip? ?
                modify = 3;
            } else { // Hull this is the item, you're the operator
                c.getPlayer().dropMessage(5, "Equipment disassembles with a dazzling flash.");// Why is the thread slip? ?
                modify = 4;
            }
            c.getPlayer().getInventory(MapleInventoryType.EQUIP).removeItem((short) slot);
            c.getPlayer().getClient().getSession().writeAndFlush(MainPacketCreator.getPlayerInfo(c.getPlayer()));
            c.getPlayer().getMap().removePlayer(c.getPlayer());
            c.getPlayer().getMap().addPlayer(c.getPlayer());
            if (modify >= 0) {
                if (Math.random() * (10 - modify) != 0) {
                    short q = makeQuantity(modify);
                    if (q > 0) {
                        int r = reward(modify);
                        c.getPlayer().gainItem(r, makeQuantity(modify), false, -1, "Miracle cube obtained by disassembling equipment");
                        c.getPlayer().dropMessage(1, "Disassembled the equipment successfully.");
                    } else {
                        c.getPlayer().dropMessage(1, "Failed to disassemble the equipment.");
                    }
                } else {
                    c.getPlayer().dropMessage(1, "Failed to disassemble the equipment.");
                }
            } else {
                c.getPlayer().dropMessage(1, "Failed to disassemble the equipment.");
            }
            return true;
        }
        c.getPlayer().dropMessage(1, "Items without potential cannot be broken down into cubes.");
        return false;
    }

    public int reward(int m) {
        List<Integer> l = Arrays.asList(5062000, 5062002, 5062005, 5062500, 5062006, 5062090);
        Collections.shuffle(l);
        return l.get(Math.max(5, ((int) (Math.random() * m)) + RewardRandomizeIndexCode())); // Math randomThis even more free
        // Renderable
    }

    public short makeQuantity(int m) {
        String arr[] = {"0~5", "0~8", "0~10", "0~20", "0~25"}; // 0~4
        int x = Short.parseShort(arr[m].split("~")[0]);
        int y = Short.parseShort(arr[m].split("~")[1]);
        int z = y - x;
        return (short) (x + z + RewardRandomizeIndexCode()); // 0-3 bonuses
    }

    private int RewardRandomizeIndexCode() {
        switch (seed % 10) {
            case 0:
            case 1:
            case 2:
            case 3:
                return 0;
            case 4:
            case 5:
            case 6:
                return 1;
            case 7:
            case 8:
                return 2;
            case 9:
                return 3;
        }
        return -1;
    }
}
