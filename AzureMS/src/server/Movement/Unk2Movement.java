package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class Unk2Movement extends AbstractLifeMovement {

    private short unk;
    private byte ForcedStop;

    public Unk2Movement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public void setUnk(short unk) {
        this.unk = unk;
    }

    public void setForcedStop(byte ForceStop) {
        this.ForcedStop = ForceStop;
    }

    
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writeShort(unk);
        packet.write(getNewstate());
        packet.writeShort(getDuration());
        packet.write(ForcedStop);
    }
}
