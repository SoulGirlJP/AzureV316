package launcher.AdminGUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;


/**
 *
 * @author SoulGirlJP#7859
 * 
 **/

public class AdminTool {
    private static int PORT = 9700;
    private static InetSocketAddress InetSocketadd;
    private static IoAcceptor acceptor;
    public static List<IoSession> session = new ArrayList<>();
    
    public static final void run_startup_configurations() {
        try {
            /* Start Socket Configuration */
            ByteBuffer.setUseDirectBuffers(false);
            ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

            acceptor = new SocketAcceptor();
            final SocketAcceptorConfig config = new SocketAcceptorConfig();
            config.getSessionConfig().setTcpNoDelay(true);
            config.setDisconnectOnUnbind(true);
            InetSocketadd = new InetSocketAddress(PORT);
            acceptor.bind(InetSocketadd, new AdminToolHandler(), config);
            /* Exit socket setup */
            System.out.println("[Notice] Server Manager " + PORT + " Port Successful Opened. \r\n");
            } catch (IOException e) {
            System.err.println("[Error] Manager Server " + PORT + " Port Failed To Open. \r\n");
            e.printStackTrace();
            }
    }

    public static final void broadcastMessage(ByteBuffer buff) {
        for (IoSession se : session) {
            se.write(buff);
        }
    }
}
