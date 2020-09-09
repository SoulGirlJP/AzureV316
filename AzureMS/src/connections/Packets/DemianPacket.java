package connections.Packets;

import java.util.List;

import connections.Packets.PacketUtility.WritingPacket;
import handlers.GlobalHandler.BossEventHandler.Damien.DemianPattern;
import handlers.GlobalHandler.BossEventHandler.Damien.DemianSword;
import tools.HexTool;

public class DemianPacket {

    public static byte[] Demian_OnFlyingSwordCreat(boolean isVisible, DemianSword ds) {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(1122);
        wp.write(isVisible);
        wp.writeInt(ds.getObjectId());
        wp.write(0);
        wp.write(ds.getAttackIdx());
        wp.writeInt(ds.getMobId());
        wp.writeInt(ds.getPosition().x);
        wp.writeInt(ds.getPosition().y);

        return wp.getPacket();
    }

    public static byte[] Demian_OnFlyingSwordNode(DemianSword ds, List<DemianPattern> pattern) {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(1125);
        wp.writeInt(ds.getObjectId());
        wp.writeInt(0);
        wp.write(0);
        if (pattern.size() == 0) {
            wp.writeInt(1);
            wp.write(1);
            wp.writeShort(0);
            wp.writeShort(0);
            wp.writeShort(30);
            wp.writeInt(0);
            wp.writeInt(0);
            wp.writeInt(0);
            wp.write(0);
            wp.write(0);
            wp.writeInt(ds.getPosition().x);
            wp.writeInt(ds.getPosition().y);
        } else {
            wp.writeInt(pattern.size());
            for (DemianPattern pa : pattern) {
                wp.write(pa.v1);
                wp.writeShort(pa.v2);
                wp.writeShort(pa.v3);
                wp.writeShort(pa.v4);
                wp.writeInt(pa.v5);
                wp.writeInt(pa.v6);
                wp.writeInt(pa.v7);
                wp.write(pa.v8);
                wp.write(pa.v9);
                wp.writeInt(pa.v10);
                wp.writeInt(pa.v11);
            }
        }
        return wp.getPacket();
    }

    public static byte[] Demian_OnFlyingSwordTarget(DemianSword ds) {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(1126);
        wp.writeInt(ds.getObjectId());
        wp.writeInt(ds.getCid());
        return wp.getPacket();
    }

    public static byte[] Demian_OnCorruptionChange() {
        WritingPacket p = new WritingPacket();
        p.writeShort(1127);
        p.write(0);
        p.writeInt(0);
        return p.getPacket();
    }

    public static byte[] OnStigmaObjectDelete() {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(1120);
        wp.writeInt(1);
        return wp.getPacket();
    }

    public static byte[] OnStigmaObjectCreate() {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(1120);
        wp.write(HexTool.getByteArrayFromHexString(
                "00 00 00 00 DE 02 00 00 10 00 00 00 C4 09 00 00 01 00 00 00 23 00 4D 61 70 2F 4F 62 6A 2F 42 6F 73 73 44 65 6D 69 61 6E 2E 69 6D 67 2F 64 65 6D 69 61 6E 2F 61 6C 74 61 72 00"));
        return wp.getPacket();
    }

}
