package handlers.GlobalHandler.BossEventHandler.Damien;

import client.MapleClient;
import connections.Packets.DemianPacket;
import connections.Packets.PacketUtility.ReadingMaple;

public class DemianHandler {

    public static void CDemianFlyingSword_EncodeMakeEnterInfo(ReadingMaple rm, MapleClient c) {
        c.getPlayer().getMap().broadcastMessage(DemianPacket.Demian_OnFlyingSwordNode(
                c.getPlayer().getMap().getDemianSword(rm.readInt()), DemianPattern.patternList));
    }
}
