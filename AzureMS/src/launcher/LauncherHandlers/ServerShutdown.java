package launcher.LauncherHandlers;

import launcher.ServerPortInitialize.ChannelServer;


public class ServerShutdown implements Runnable {

    private int channel;

    public ServerShutdown(int channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            ChannelServer.getInstance(channel).shutdown();
        } catch (Throwable t) {
            System.err.println("SHUTDOWN ERROR" + t);
        }

        System.out.println("[Exit] channel " + channel + " The server closes the port.");

        boolean error = true;
        while (error) {
            try {
                error = false;
            } catch (Exception e) {
                error = true;
            }
        }

        System.out.println("[Exit] channel " + channel + " Shutting down server.");

        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            while (!cserv.hasFinishedShutdown()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("ERROR" + e);
                }
            }
        }
        System.out.println("[Exit] channel " + channel + " The server has been shut down.");
    }
}
