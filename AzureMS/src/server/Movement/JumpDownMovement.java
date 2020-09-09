package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class JumpDownMovement extends AbstractLifeMovement {

    private Point pixelsPerSecond;
    private Point offset;
    private int unk;
    private int fh;

    public JumpDownMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public Point getPixelsPerSecond() {
        return pixelsPerSecond;
    }

    public void setPixelsPerSecond(Point wobble) {
        this.pixelsPerSecond = wobble;
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point wobble) {
        this.offset = wobble;
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
        packet.writePos(pixelsPerSecond);
        packet.writeShort(unk);
        packet.writeShort(fh);
        packet.writePos(offset);
        packet.write(getNewstate());
        packet.writeShort(getDuration());
        packet.write(0);
    }
}
