package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class FlashMovement extends AbstractLifeMovement {

    private Point pixelsPerSecond;

    public FlashMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public Point getPixelsPerSecond() {
        return pixelsPerSecond;
    }

    public void setPixelsPerSecond(Point ye) {
        this.pixelsPerSecond = ye;
    }

    
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writePos(getPosition());
        packet.writePos(pixelsPerSecond);
        packet.write(getNewstate());
        packet.writeShort(getDuration());
    }
}
