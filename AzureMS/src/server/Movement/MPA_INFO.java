package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class MPA_INFO implements LifeMovementFragment {

    private int mpa, p1, p2, p3, p4, p5, p6;

    public MPA_INFO(int nMPA, int nParam1, int nParam2, int nParam3, int nParam4, int nParam5, int nParam6) {
        mpa = nMPA;
        p1 = nParam1;
        p2 = nParam2;
        p3 = nParam3;
        p4 = nParam4;
        p5 = nParam5;
        p6 = nParam6;
    }

    
    public void serialize(WritingPacket packet) {
        packet.writeShort(mpa);
        packet.writeShort(p1);
        packet.writeShort(p2);
        packet.writeShort(p3);
        packet.writeShort(p4);
        packet.writeShort(p5);
        packet.writeShort(p6);
    }

   
    public Point getPosition() {
        return new Point(0, 0);
    }

}
