package tools;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadLockDetector extends Thread {

    public static final byte NOTHING = 0;

    public static final byte RESTART = 1;

    private final int sleepTime;

    private final ThreadMXBean tmx;

    private final byte doWhenDL;

    public DeadLockDetector(int sleepTime, byte doWhenDL) {
        super("DeadLockDetector");
        System.out.println("DeadLockSearch Start!!");
        this.sleepTime = sleepTime * 1000;
        this.tmx = ManagementFactory.getThreadMXBean();
        this.doWhenDL = doWhenDL;
    }

    @Override
    public final void run() {
        boolean deadlock = false;
        while (!deadlock) {
            try {
                long[] ids = tmx.findDeadlockedThreads();

                if (ids != null) {

                    deadlock = true;
                    ThreadInfo[] tis = tmx.getThreadInfo(ids, true, true);
                    String info = "DeadLock Found!\n";
                    for (ThreadInfo ti : tis) {
                        info += ti.toString();
                    }

                    for (ThreadInfo ti : tis) {
                        LockInfo[] locks = ti.getLockedSynchronizers();
                        MonitorInfo[] monitors = ti.getLockedMonitors();
                        if (locks.length == 0 && monitors.length == 0) {

                            continue;
                        }

                        ThreadInfo dl = ti;
                        info += "Java-level deadlock:\n";
                        info += "\t" + dl.getThreadName() + " is waiting to lock " + dl.getLockInfo().toString()
                                + " which is held by " + dl.getLockOwnerName() + "\n";

                        while ((dl = tmx.getThreadInfo(new long[]{dl.getLockOwnerId()}, true, true)[0])
                                .getThreadId() != ti.getThreadId()) {
                            info += "\t" + dl.getThreadName() + " is waiting to lock " + dl.getLockInfo().toString()
                                    + " which is held by " + dl.getLockOwnerName() + "\n";
                        }
                    }
                }
                Thread.sleep(sleepTime);
            } catch (Exception e) {
            }
        }
    }
}
