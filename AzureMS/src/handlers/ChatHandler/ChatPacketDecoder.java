package handlers.ChatHandler;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import connections.Crypto.MapleCrypto;

public class ChatPacketDecoder extends CumulativeProtocolDecoder {

    public static final String DECODER_STATE_KEY = ChatPacketDecoder.class.getName() + ".STATE";

    public static class DecoderState {

        public int packetlength = -1;
    }

    @Override
    protected boolean doDecode(IoSession session, ByteBuffer in, ProtocolDecoderOutput out) throws Exception {
        final DecoderState decoderState = (DecoderState) session.getAttribute(DECODER_STATE_KEY);
        final MapleChatClient client = (MapleChatClient) session.getAttribute(MapleChatClient.MCCKEY);

        if (decoderState.packetlength == -1) {
            if (in.remaining() >= 4) {
                final int packetHeader = in.getInt();
                if (!client.getReceiveCrypto().checkPacket(packetHeader)) {
                    session.close();
                    return false;
                }
                decoderState.packetlength = MapleCrypto.getPacketLength(packetHeader);
            } else {
                return false;
            }
        }
        if (in.remaining() >= decoderState.packetlength) {
            final byte decryptedPacket[] = new byte[decoderState.packetlength];
            in.get(decryptedPacket, 0, decoderState.packetlength);
            decoderState.packetlength = -1;
            out.write(client.getReceiveCrypto().crypt(decryptedPacket));
            return true;
        }
        return false;
    }
}
