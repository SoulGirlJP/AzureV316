package client.Community.MapleGuild;

import connections.Packets.MainPacketCreator;

public enum MapleGuildResponse {
    NOT_IN_CHANNEL(0x38),
    ALREADY_IN_GUILD(0x41),
    NOT_IN_GUILD(0x44);

    private int value;

    private MapleGuildResponse(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public byte[] getPacket() {
        return MainPacketCreator.genericGuildMessage((byte) value);
    }
}
