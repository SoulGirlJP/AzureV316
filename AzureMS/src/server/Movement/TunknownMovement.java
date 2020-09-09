package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class TunknownMovement extends AbstractLifeMovement {

    private Point offset;
    private byte force;

    public TunknownMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point wobble) {
        this.offset = wobble;
    }

    public void setForce(byte force) {
        this.force = force;
    }

    
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writePos(getPosition());
        packet.writePos(offset);
        packet.write(getNewstate());
        packet.writeShort(getDuration());
        packet.write(force);
    }
}
