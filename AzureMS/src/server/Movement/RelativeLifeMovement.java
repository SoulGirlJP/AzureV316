package server.Movement;

import java.awt.Point;

import connections.Packets.PacketUtility.WritingPacket;

public class RelativeLifeMovement extends AbstractLifeMovement {

    private short fh;
    private int unk1, unk2;
    private byte ForcedStop;

    public RelativeLifeMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public short getFh() {
        return fh;
    }

    public void setFh(short fh) {
        this.fh = fh;
    }
    
    public void setUnk1(int unk1) {
        this.unk1 = unk1;
    }
    
    public void setUnk2(int unk2) {
        this.unk2 = unk2;
    }

    public void setForcedStop(byte ForcedStop) {
        this.ForcedStop = ForcedStop;
    }

   
    public void serialize(WritingPacket packet) {
        packet.write(getType());
        packet.writeShort(getPosition().x);
        packet.writeShort(getPosition().y);
        if (getType() == 21 || getType() == 22) {
            packet.writeShort(fh);
        }
        packet.write(getNewstate());
        packet.writeShort(getDuration());
        packet.write(ForcedStop);
        if (getType() == 59) {
            packet.writeShort(unk1);
            packet.writeShort(unk2);
        }
    }
}
