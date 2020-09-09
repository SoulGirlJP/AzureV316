package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class AngelicBusterMovement extends AbstractLifeMovement {

    private Point pixelsPerSecond, offset;
    private int unk, movetype;

    public AngelicBusterMovement(int type, Point position, int duration, int newstate) {
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

    public int getMoveType() {
        return movetype;
    }

    public void setMoveType(int movetype) {
        this.movetype = movetype;
    }

   
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writeInt(movetype);
        packet.writePos(getPosition());
        packet.writePos(pixelsPerSecond);
        packet.writeShort(unk);
        packet.writePos(offset);
        packet.write(getNewstate());
        packet.writeShort(getDuration());
    }
}
