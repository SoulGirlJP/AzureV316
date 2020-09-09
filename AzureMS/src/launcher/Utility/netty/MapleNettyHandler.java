package launcher.Utility.netty;

import static constants.Data.ServerType.AUCTION;
import static constants.Data.ServerType.CASHSHOP;
import static constants.Data.ServerType.CHANNEL;

import client.MapleClient;
import constants.ServerConstants;
import constants.Data.ServerType;
import handlers.MapleServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import connections.Packets.LoginPacket;
import connections.Crypto.MapleCrypto;
import connections.Opcodes.RecvPacketOpcode;
import connections.Packets.PacketUtility.ReadingMaple;
import tools.RandomStream.Randomizer;

public class MapleNettyHandler extends SimpleChannelInboundHandler<ReadingMaple> {

    private final ServerType serverType;
    private final int channel;

    public MapleNettyHandler(ServerType serverType, int channel) {
        this.serverType = serverType;
        this.channel = channel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	final String address = ctx.channel().remoteAddress().toString().split(":")[0];
    	System.out.println("[Network] Attempting connection from: " + address + " on " + serverType + " server.");
    	
        final byte serverRecv[] = {(byte) 0x22, (byte) 0x3F, (byte) 0x37, (byte) Randomizer.nextInt(255)};
        final byte serverSend[] = {(byte) 0xC9, (byte) 0x3A, (byte) 0x27, (byte) Randomizer.nextInt(255)};
        final byte ivRecv[] = serverRecv;
        final byte ivSend[] = serverSend;
        final MapleClient client = new MapleClient(ctx.channel(), new MapleCrypto(ivSend, (short) (0xFFFF - ServerConstants.MAPLE_VERSION), serverType == CHANNEL || serverType == CASHSHOP || serverType == AUCTION, true), new MapleCrypto(ivRecv, ServerConstants.MAPLE_VERSION, serverType == CHANNEL || serverType == CASHSHOP || serverType == AUCTION));

        client.setChannel(channel);
        ctx.writeAndFlush(LoginPacket.initializeConnection(ServerConstants.MAPLE_VERSION, ivSend, ivRecv, !serverType.equals(ServerType.LOGIN)));
        ctx.channel().attr(MapleClient.CLIENTKEY).set(client);
        //client.sendPing();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        MapleClient client = ctx.channel().attr(MapleClient.CLIENTKEY).get();

        if (client != null) {
            System.out.println(client.getIp() + " disconnected.");
            client.disconnect(true, false);
        }
        ctx.channel().attr(MapleClient.CLIENTKEY).set(null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof ReadTimeoutException) 
        {
            ctx.close();
            System.out.println(cause);
        }
        if (cause instanceof WriteTimeoutException) 
        {
            ctx.close();
            System.out.println(cause);
        }

    }

     @Override
     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         if (evt instanceof IdleStateEvent) {
             IdleStateEvent e = (IdleStateEvent) evt;
             if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
             } else if (e.state() == IdleState.WRITER_IDLE) {
                 ctx.close();
             }
         }
     }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReadingMaple slea) throws Exception {
        final MapleClient c = (MapleClient) ctx.channel().attr(MapleClient.CLIENTKEY).get();
        final int header_num = slea.readShort();
        
        if (ServerConstants.DEBUG_RECEIVE
				&& ServerConstants.showPackets == true
                && header_num != RecvPacketOpcode.MOVE_LIFE.getValue()
                && header_num != RecvPacketOpcode.MOVE_PLAYER.getValue()
                && header_num != RecvPacketOpcode.QUEST_ACTION.getValue()
                && header_num != RecvPacketOpcode.NPC_ACTION.getValue()
                && header_num != RecvPacketOpcode.AUTO_AGGRO.getValue() 
                /*&& header_num != RecvPacketOpcode.MOVE_PET.getValue()*/
        ) {
//            final StringBuilder sb = new StringBuilder("[RECEIVE] " + RecvPacketOpcode.getOpcodeName(header_num) + " :\n");
//            sb.append(HexTool.toString(slea.getByteArray())).append("\n").append(HexTool.toStringFromAscii(slea.getByteArray()) + "\n");
            System.out.println("["+ RecvPacketOpcode.getOpcodeName(header_num) +"] "+ header_num + " " + slea.toString());
//            System.out.println(sb.toString());
        }
        
        if (!ServerConstants.DEBUG_RECEIVE && RecvPacketOpcode.getOpcodeName(header_num).equals("UNKNOWN")) {
           final StringBuilder sb = new StringBuilder("[RECEIVE] " + RecvPacketOpcode.getOpcodeName(header_num) + " :\n");
            //sb.append(HexTool.toString(slea.getByteArray())).append("\n").append(HexTool.toStringFromAscii(slea.getByteArray()) + "\n");
            System.out.println(sb.toString());
       }

        for (final RecvPacketOpcode recv : RecvPacketOpcode.values()) {
            if (recv.getValue() == header_num) {
                try {
                    MapleServerHandler.handlePacket(recv, slea, c, serverType);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }
    }
}
