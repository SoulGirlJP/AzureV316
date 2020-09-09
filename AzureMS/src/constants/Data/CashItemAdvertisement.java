package constants.Data;

import java.util.LinkedList;
import java.util.List;

import launcher.Utility.WorldBroadcasting;
import connections.Packets.MainPacketCreator;
import tools.Pair;
import tools.Timer.WorldTimer;
import tools.RandomStream.Randomizer;

public class CashItemAdvertisement {

    private static List<Pair<Integer, String>> advertisementMessages = new LinkedList<Pair<Integer, String>>();

    public static void addMessage(int itemid, String message) {
        advertisementMessages.add(new Pair(itemid, message));
    }

    public static void startTasking() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Pair<Integer, String> randomed = advertisementMessages
                        .get(Randomizer.nextInt(advertisementMessages.size()));
                WorldBroadcasting
                        .broadcastMessage(MainPacketCreator.serverNotice(6, randomed.getRight(), randomed.getLeft()));
            }
        };
        WorldTimer.getInstance().register(r, 180000L);
    }
}
