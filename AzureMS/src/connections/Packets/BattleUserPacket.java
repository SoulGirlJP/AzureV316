package connections.Packets;

import connections.Packets.PacketUtility.WritingPacket;

public class BattleUserPacket {

    public static byte[] OnRegisterBattleUser() {
        WritingPacket wp = new WritingPacket();
        wp.writeShort(1091);

        return wp.getPacket();
    }

    public static void DecodeUserData(WritingPacket wp) {
        wp.writeInt(0);// dwReciveBattleUserKey
        wp.writeInt(0);// dwCharactgerID
        wp.writeInt(0);// nExp
        wp.writeInt(0);// nPoint
        wp.writeInt(0);// nSpecialPoint
        wp.writeInt(0);// nSkillSet
        wp.writeInt(0);// nKillCount
        wp.writeInt(0);// nDeathCount

    }
}
