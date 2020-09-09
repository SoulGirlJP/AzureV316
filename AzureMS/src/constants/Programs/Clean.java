package constants.Programs;

import java.util.concurrent.ScheduledFuture;

import launcher.Utility.WorldBroadcasting;
import connections.Packets.MainPacketCreator;
import tools.CPUSampler;
import tools.DeadLockDetector;

public class Clean {

    private static transient ScheduledFuture<?> start;
    private static int timeo = 0;

    public static void main(String[] args) {
        if (start == null) {
            start = tools.Timer.WorldTimer.getInstance().register(new Runnable() {
                public void run() {
                    if (timeo == 0) {
                        CPUSampler.getInstance().start();
                        System.out.println("CPUSampler Thread Start!!");
                        DeadLockDetector clean = new DeadLockDetector(10, (byte) 1);
                        clean.run();
                        timeo++;
                    } else if (timeo == -1) {
                        timeo = 0;
                    } else if (timeo != 0) {
                        CPUSampler.getInstance().start();
                        System.out.println("CPUSampler Thread Start!!");
                        DeadLockDetector clean = new DeadLockDetector(10, (byte) 1);
                        clean.run();
                        WorldBroadcasting.broadcast(
                                MainPacketCreator.serverNotice(1, "Server optimization is in progress. Please note that it may cause some lag."));
                        timeo++;
                    }
                }
            }, 3600000 * 3);
        }
    }
}
