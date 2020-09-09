package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class ChangeEquipSpecialAwesome implements LifeMovementFragment {

    private int type, wui;

    public ChangeEquipSpecialAwesome(int type, int wui) {
        this.type = type;
        this.wui = wui;
    }

    
    public void serialize(WritingPacket packet) {
        packet.write(type);
        packet.write(wui);
    }

    
    public Point getPosition() {
        return new Point(0, 0);
    }
}
