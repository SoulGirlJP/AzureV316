package launcher.ServerPortInitialize;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import constants.ServerConstants;
import handlers.ChatHandler.ChatCodeFactory;
import handlers.Chathandler.ChatServerHandler;

public class BuddyChatServer {

    private static int PORT = ServerConstants.BuddyChatPort;
    private static BuddyChatServer Instance = new BuddyChatServer();
    private InetSocketAddress InetSocketadd;
    private IoAcceptor acceptor;

    public static BuddyChatServer getInstance() {
        return Instance;
    }

    public final void run_startup_configurations() {
        try {
            /* Start Socket Configuration */
            ByteBuffer.setUseDirectBuffers(false);
            ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

            acceptor = new SocketAcceptor();
            final SocketAcceptorConfig cfg = new SocketAcceptorConfig();
            cfg.getSessionConfig().setTcpNoDelay(true);
            cfg.setDisconnectOnUnbind(true);
            cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ChatCodeFactory()));
            InetSocketadd = new InetSocketAddress(PORT);
            acceptor.bind(InetSocketadd, new ChatServerHandler(), cfg);
            /* Exit socket setup */
            System.out.println("[Notification] Friend Chat Server " + PORT + " Successfully opened port.");
        } catch (IOException e) {
            System.err.println("[Error] Friend Chat Server " + PORT + " Failed to open port.");
            e.printStackTrace();
        }
    }

    public final void shutdown() {

    }
}
