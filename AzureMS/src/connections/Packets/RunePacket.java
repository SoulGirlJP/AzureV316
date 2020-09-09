package connections.Packets;

import client.Character.MapleCharacter;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import server.Maps.MapleMapHandling.MapleRune;
import tools.HexTool;

public class RunePacket {

    public static byte[] spawnRune(MapleRune rune, boolean respawn) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(respawn ? SendPacketOpcode.RESPAWN_RUNE.getValue() : SendPacketOpcode.SPAWN_RUNE.getValue());
        packet.writeInt(1);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(rune.getRuneType());
        packet.writeInt(rune.getPositionX());
        packet.writeInt(rune.getPositionY());
        packet.write(0);
        packet.writeInt(0);

        return packet.getPacket();
    }

    public static byte[] removeRune(MapleRune rune, MapleCharacter chr) {
	WritingPacket packet = new WritingPacket();
	packet.writeShort(SendPacketOpcode.REMOVE_RUNE.getValue());
        packet.writeInt(0);
        packet.writeInt(chr.getId());
        packet.writeInt(100);
        packet.writeShort(0);  
	  
	return packet.getPacket(); 
    }
     
    public static byte[] RuneAction(int type, int time) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.RUNE_ACTION.getValue());
        packet.writeInt(type);
        if (type == 9)
            packet.write(HexTool.getByteArrayFromHexString("3B 43 6A 3C 18 00 00 00 9B 12 0A 45 26 90 F8 4F D3 91 F8 4F DC 91 F8 4F DD 91 F8 4F DC 91 F8 4F"));
        else {
            packet.writeInt(time);
            packet.writeInt(2);
            packet.writeInt(3);
            packet.writeInt(0);
        }
        return packet.getPacket();
    }

    public static byte[] showRuneEffect(int type) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.RUNE_EFFECT.getValue());
        packet.writeInt(type);
        packet.write(0);
        
        return packet.getPacket();
    }
}
