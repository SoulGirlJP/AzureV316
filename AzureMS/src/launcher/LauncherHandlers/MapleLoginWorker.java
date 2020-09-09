package launcher.LauncherHandlers;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import client.MapleClient;
import connections.Packets.LoginPacket;
import handlers.LoginHandler.CharLoginHandler;
import tools.Pair;
import tools.Timer.PingTimer;

public class MapleLoginWorker {

    private static Runnable persister;
    private static final List<Pair<Integer, String>> IPLog = new LinkedList<Pair<Integer, String>>();
    private static final Lock mutex = new ReentrantLock();

    protected MapleLoginWorker() {
        // WorldTimer.getInstance().register(persister, 1800000L);
    }

    public static void registerClient(final MapleClient c) {
        if (c.finishLogin() == 0) {
            c.getSession().writeAndFlush(LoginPacket.getAuthSuccessRequest(c));
            /* Display Channel Won Ilhwa. */
            CharLoginHandler.getDisplayChannel(true, c);
            c.setIdleTask(PingTimer.getInstance().schedule(new Runnable() {
                public void run() {
                    c.getSession().close();
                }
            }, 10 * 60 * 10000));
        } else {
            c.getSession().writeAndFlush(LoginPacket.getLoginFailed(7));
            return;
        }
        mutex.lock();
        try {
            IPLog.add(new Pair<Integer, String>(c.getAccID(), c.getIp()));
        } finally {
            mutex.unlock();
        }
    }
}
