package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public interface LifeMovementFragment {

    void serialize(WritingPacket packet);

    Point getPosition();
}
