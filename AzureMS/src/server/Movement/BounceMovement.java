package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class BounceMovement extends AbstractLifeMovement {

    private int unk;
    private int fh;

    public BounceMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public int getUnk() {
        return unk;
    }

    public void setUnk(int unk) {
        this.unk = unk;
    }

    public int getFH() {
        return fh;
    }

    public void setFH(int fh) {
        this.fh = fh;
    }

   
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writePos(getPosition());
        packet.writeShort(getUnk());
        packet.writeShort(getFH());
        packet.write(getNewstate());
        packet.writeShort(getDuration());
    }
}
