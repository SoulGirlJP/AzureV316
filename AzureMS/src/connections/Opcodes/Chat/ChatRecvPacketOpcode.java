package connections.Opcodes.Chat;

import java.util.HashMap;
import java.util.Map;

public enum ChatRecvPacketOpcode {
    LoginResult(1),
    GuildChatMessage(19),
    FriendChatMessage(20);

    private short value;
    private final static Map<Short, ChatRecvPacketOpcode> RecvOpcodes = new HashMap<Short, ChatRecvPacketOpcode>();

    public static void initalized() {
        if (!RecvOpcodes.isEmpty()) {
            RecvOpcodes.clear();
        }
        for (ChatRecvPacketOpcode recv : ChatRecvPacketOpcode.values()) {
            RecvOpcodes.put(recv.getValue(), recv);
        }
    }

    public static Map<Short, ChatRecvPacketOpcode> getRecvOpcodes() {
        return RecvOpcodes;
    }

    private ChatRecvPacketOpcode(int value) {
        setValue((short) value);
    }

    public void setValue(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public static String getOpcodeName(int value) {

        for (ChatRecvPacketOpcode opcode : values()) {
            if (opcode.getValue() == value) {
                return opcode.name();
            }
        }
        return "UNKNOWN";
    }
}
