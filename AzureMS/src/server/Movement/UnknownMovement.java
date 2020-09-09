package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class UnknownMovement extends AbstractLifeMovement {

    private Point pixelsPerSecond;
    private int unk, fh;
    private byte ForcedStop;

    public UnknownMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public Point getPixelsPerSecond() {
        return pixelsPerSecond;
    }

    public void setPixelsPerSecond(Point wobble) {
        this.pixelsPerSecond = wobble;
    }

    public int getUnk() {
        return unk;
    }

    public void setUnk(int unk) {
        this.unk = unk;
    }

    public void setFh(short fh) {
        this.fh = fh;
    }

    public void setForcedStop(byte ForcedStop) {
        this.ForcedStop = ForcedStop;
    }

   
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writePos(getPosition());
        packet.writePos(pixelsPerSecond);
        packet.writeShort(unk);
        packet.write(getNewstate());
        packet.writeShort(getDuration());
        packet.write(ForcedStop);
    }
}
