package launcher.Utility.netty;

import java.util.List;

import client.MapleClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import connections.Crypto.MapleCrypto;
import connections.Packets.PacketUtility.ByteStream;
import connections.Packets.PacketUtility.ReadingMaple;

public class MapleNettyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {
        MapleClient client = ctx.channel().attr(MapleClient.CLIENTKEY).get();

        if (client == null) {
            return;
        }

        if (buffer.readableBytes() < 4) {
            return;
        }
        final int packetHeader = buffer.readInt();
        int packetlength = MapleCrypto.getPacketLength(packetHeader);
        if (!client.getReceiveCrypto().checkPacket(packetHeader)) {
            // System.out.println("@MapleNettyDecoder::decode | checking packet failed! " +
            // client.getAccountName() + "," + (client.getAccID() != 1 ?
            // client.getLoginState() : 0));
            return;
        }

        if (buffer.readableBytes() < packetlength) {
            buffer.resetReaderIndex();
            return;
        }

        buffer.markReaderIndex();

        // Convert the received data into a new BigInteger.
        byte[] decoded = new byte[packetlength];
        buffer.readBytes(decoded);
        buffer.markReaderIndex();

        list.add(new ReadingMaple(new ByteStream(client.getReceiveCrypto().crypt(decoded))));
    }
}
