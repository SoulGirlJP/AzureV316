package handlers.ChatHandler;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ChatCodeFactory implements ProtocolCodecFactory {

    private final ProtocolEncoder encoder = new ChatPacketEncoder();
    private final ProtocolDecoder decoder = new ChatPacketDecoder();

    @Override
    public ProtocolEncoder getEncoder() throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder() throws Exception {
        return decoder;
    }
}
