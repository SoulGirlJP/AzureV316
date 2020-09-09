package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class AranMovement extends AbstractLifeMovement {

    private byte ForcedStop;
    private int unk;
    
    public AranMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public void setForcedStop(byte ForceStop) {
        this.ForcedStop = ForceStop;
    }
    
    public void setUnk(int unk) {
        this.unk = unk;
    }

    
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        if (getType() == 28) {
            packet.writeInt(unk);
        }
        packet.write(getNewstate());
        packet.writeShort(getDuration());
        packet.write(ForcedStop);
    }
}
