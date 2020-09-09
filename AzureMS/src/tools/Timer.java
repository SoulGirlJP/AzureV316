package tools;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import tools.RandomStream.Randomizer;

public abstract class Timer {

    public static class WorldTimer extends Timer {

        private static WorldTimer instance = new WorldTimer();

        private WorldTimer() {
            name = "Worldtimer";
        }

        public static WorldTimer getInstance() {
            return instance;
        }
    }

    public static class LogoutTimer extends Timer {

        private static LogoutTimer instance = new LogoutTimer();

        private LogoutTimer() {
            name = "LogoutTimer";
        }

        public static LogoutTimer getInstance() {
            return instance;
        }
    }

    public static class MapTimer extends Timer {

        private static MapTimer instance = new MapTimer();

        private MapTimer() {
            name = "Maptimer";
        }

        public static MapTimer getInstance() {
            return instance;
        }
    }

    public static class BuffTimer extends Timer {

        private static BuffTimer instance = new BuffTimer();

        private BuffTimer() {
            name = "Bufftimer";
        }

        public static BuffTimer getInstance() {
            return instance;
        }

        public void cancelBuffTimer(final long time, final ScheduledFuture<?> a) {
            EtcTimer tMan = EtcTimer.getInstance();
            tMan.schedule(new Runnable() {
                @Override
                public void run() {
                    a.cancel(true);
                }
            }, time);
        }
    }

    public static class EventTimer extends Timer {

        private static EventTimer instance = new EventTimer();

        private EventTimer() {
            name = "Eventtimer";
        }

        public static EventTimer getInstance() {
            return instance;
        }
    }

    public static class CloneTimer extends Timer {

        private static CloneTimer instance = new CloneTimer();

        private CloneTimer() {
            name = "Clonetimer";
        }

        public static CloneTimer getInstance() {
            return instance;
        }
    }

    public static class EtcTimer extends Timer {

        private static EtcTimer instance = new EtcTimer();

        private EtcTimer() {
            name = "Etctimer";
        }

        public static EtcTimer getInstance() {
            return instance;
        }
    }

    public static class CheatTimer extends Timer {

        private static CheatTimer instance = new CheatTimer();

        private CheatTimer() {
            name = "Cheattimer";
        }

        public static CheatTimer getInstance() {
            return instance;
        }
    }

    public static class ShowTimer extends Timer {

        private static ShowTimer instance = new ShowTimer();

        private ShowTimer() {
            name = "ShowTimer";
        }

        public static ShowTimer getInstance() {
            return instance;
        }
    }

    public static class PingTimer extends Timer {

        private static PingTimer instance = new PingTimer();

        private PingTimer() {
            name = "Pingtimer";
        }

        public static PingTimer getInstance() {
            return instance;
        }
    }

    private ScheduledThreadPoolExecutor ses;
    protected String file, name;
    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    public void start() {
        if (ses != null && !ses.isShutdown() && !ses.isTerminated()) {
            return;
        }
        ses = new ScheduledThreadPoolExecutor(5, new RejectedThreadFactory());
        ses.setKeepAliveTime(10, TimeUnit.MINUTES);
        ses.allowCoreThreadTimeOut(true);
        ses.setMaximumPoolSize(8);
        ses.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
    }

    public ScheduledThreadPoolExecutor getSES() {
        return ses;
    }

    public void stop() {
        if (ses != null) {
            ses.shutdown();
        }
    }

    public ScheduledFuture<?> register(Runnable r, long repeatTime, long delay) {
        if (ses == null) {
            return null;
        }
        return ses.scheduleAtFixedRate(new LoggingSaveRunnable(r, file), delay, repeatTime, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> register(Runnable r, long repeatTime) {
        if (ses == null) {
            return null;
        }
        return ses.scheduleAtFixedRate(new LoggingSaveRunnable(r, file), 0, repeatTime, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> schedule(Runnable r, long delay) {
        if (ses == null) {

            return null;

        }

        return ses.schedule(new LoggingSaveRunnable(r, file), delay, TimeUnit.MILLISECONDS);

    }

    public ScheduledFuture<?> scheduleAtTimestamp(Runnable r, long timestamp) {
        return schedule(r, timestamp - System.currentTimeMillis());
    }

    private static class LoggingSaveRunnable implements Runnable {

        Runnable r;
        String file;

        public LoggingSaveRunnable(final Runnable r, final String file) {
            this.r = r;
            this.file = file;
        }

        @Override
        public void run() {
            try {
                r.run();
            } catch (Throwable t) {
                // t.printStackTrace();
            }
        }
    }

    private class RejectedThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber2 = new AtomicInteger(1);
        private final String tname;

        public RejectedThreadFactory() {
            tname = name + Randomizer.nextInt();
        }

        @Override
        public Thread newThread(Runnable r) {
            final Thread t = new Thread(r);
            t.setName(tname + "-W-" + threadNumber.getAndIncrement() + "-" + threadNumber2.getAndIncrement());
            return t;
        }
    }
}
