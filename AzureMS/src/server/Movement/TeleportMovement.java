package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class TeleportMovement extends AbstractLifeMovement {

    private Point pixelsPerSecond;

    public TeleportMovement(int type, Point position, int newstate) {
        super(type, position, 0, newstate);
    }

    public Point getPixelsPerSecond() {
        return pixelsPerSecond;
    }

    public void setPixelsPerSecond(Point wobble) {
        this.pixelsPerSecond = wobble;
    }

   
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writeShort(getPosition().x);
        packet.writeShort(getPosition().y);
        packet.writeShort(getPixelsPerSecond().x);
        packet.writeShort(getPixelsPerSecond().y);
        packet.write(getNewstate());
    }
}
