package connections.Packets;

import client.Character.MapleCharacter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import client.MapleClient;
import constants.GameConstants;
import constants.ServerConstants;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.ServerPortInitialize.LoginServer;
import tools.HexTool;

public class LoginPacket {

    public static final byte[] initializeConnection(final short mapleVersion, final byte[] sendIv, final byte[] recvIv,
            final boolean ingame) {
        final WritingPacket w = new WritingPacket();

        int ret = 0;
        ret ^= (mapleVersion & 0x7FFF);
        ret ^= (ServerConstants.check << 15);
        ret ^= ((ServerConstants.subVersion & 0xFF) << 16);
        String version = String.valueOf(ret);

        int packetsize = 2 + 4 + 4 + 4 + 1
                + (ingame ? 0 : (4 + 4 + 4 + 1 + 1 + 2 + 2 + 4 + 4 + 1 + 1 + version.length()));

        w.writeShort(packetsize);

        if (!ingame) {
            w.writeShort(291);
            w.writeMapleAsciiString(version);
            w.write(recvIv);
            w.write(sendIv);
            w.write(1); // locale
            w.write(0); // single thread loading
        }

        w.writeShort(291);
        w.writeInt(mapleVersion);
        w.write(recvIv);
        w.write(sendIv);
        w.write(1); // locale

        if (!ingame) {
            w.writeInt(ServerConstants.subVersion);
            w.writeInt(ServerConstants.subVersion); // next subversion
            w.writeInt(0); // unknown
            w.write(false);
            w.write(false);
        }
        return w.getPacket();
    }

    public static final byte[] getHotfix() {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.HOTFIX.getValue());
        w.write(0);

        return w.getPacket();
    }

    public static byte[] getWorldSelect(byte type, int world) {
        final WritingPacket w = new WritingPacket();

        w.writeShort(SendPacketOpcode.WORLD_SELECT.getValue());
        w.write(type);
        w.writeInt(world);
        
        return w.getPacket();
    }

    public static final byte[] getSessionResponse(int pResponse) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.SESSION_CHECK.getValue());
        w.writeInt(pResponse);

        return w.getPacket();
    }

    public static final byte[] getKeyGuardResponse(String Key) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.KEYGUARD_CHECK.getValue());
        w.writeMapleAsciiString(Key);

        return w.getPacket();
    }

    public static byte[] getRelogResponse() {
        WritingPacket packet = new WritingPacket(3);
        packet.writeShort(SendPacketOpcode.RELOG_RESPONSE.getValue());
        packet.write(1);

        return packet.getPacket();
    }

    public static final byte[] getPing() {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.PING.getValue());
        w.write(HexTool.getByteArrayFromHexString("70 D4 AC 00 30 07 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00"));
        return w.getPacket();
    }

    /*
	 * 로그인 메세지 코드
	 * 
	 * 0 : 성공 3 : 지워지거나 접속 중지된 아이디 입니다. 4 : 비밀번호가 일치하지 않습니다. 5 : 등록되지 않은 아이디 입니다. 6
	 * : 시스템 오류로 접속할 수 없습니다. 7 : 현재 접속중인 아이디 입니다. 8 : 시스템 오류로 접속할 수 없습니다. 9 : 시스템
	 * 오류로 접속할 수 없습니다. 10 : 현재 서버에 접속요청이 많아 처리하지 못했습니다. 11 : 20세 이상만 접속할 수 있습니다. 다른
	 * 서버에 선택해 주세요. 17 : U-OTP 번호를 입력해주세요. 18 : OTP 번호가 틀립니다.
     */
    public static final byte[] getLoginFailed(final int reason) {
        final WritingPacket w = new WritingPacket(16);
        w.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        w.writeInt(reason);
        w.writeShort(0);

        return w.getPacket();
    }

    public static final byte[] getPermBan(final byte reason) {
        final WritingPacket w = new WritingPacket(16);

        w.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        w.writeShort(2); // Account is banned
        w.write(0);
        w.write(reason);
        w.write(HexTool.getByteArrayFromHexString("01 01 01 01 00"));

        return w.getPacket();
    }

    public static final byte[] getTempBan(final long timestampTill, final byte reason) {
        final WritingPacket w = new WritingPacket(17);

        w.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        w.write(2);
        w.write(HexTool.getByteArrayFromHexString("00 00 00 00 00"));
        w.write(reason);
        w.writeLong(timestampTill); // Tempban date is handled as a 64-bit long, number of 100NS intervals since
        // 1/1/1601. Lulz.

        return w.getPacket();
    }

    public static final byte[] getAuthSuccessRequest(final MapleClient client) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        w.write(0);
        w.writeMapleAsciiString(client.getAccountName()); // sID
        w.writeInt(client.getAccID());// dwAccountID
        w.writeInt(0);
        w.writeInt(0);
        w.write(0);
        w.writeInt(0);
        w.writeInt(0);
        w.writeInt(0x13);
        w.write(0);
        w.write(0);
        w.writeLong(0);
        if (true) { // NexonLogin
            w.write(1);
        } else {
            w.write(0);
            w.writeMapleAsciiString(client.getAccountName());
        }
        w.write(0); // is naver account?
        w.writeMapleAsciiString("");

        if (false) {
            w.write(0);
        } else {
            w.write(1);

            w.write(0x1E); // 1.2.250+
            /*
            for (int i = 0; i < 23; i++) {
                w.write(i == 20 ? 0 : 1);
                w.writeShort(i == 20 ? 0 : 1);
            }
            */  // 일리움 ?기?
            for (int i = 0; i < 23; i++) {
                w.write(i == 25 ? 0 : 1);
                w.writeShort(i == 25 ? 0 : 1);
            }
        }
        w.write(0);
        w.writeInt(0);
        return w.getPacket();
    }

    public static final byte[] getCharEndRequest(final MapleClient client, String Acc, String Pwd, boolean Charlist) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.CHAR_END_REQUEST.getValue());
        w.write(0);
        w.writeInt(client.getAccID());
        w.write(client.getGender());
        w.write(client.isGm() ? 1 : 0); // Admin byte
        w.write0(21);
        w.writeMapleAsciiString(Pwd); // password.
        w.writeMapleAsciiString(Acc); // Game ID.
        w.writeShort(0);
        w.write(1);

        w.write(0x1E); // 1.2.250+
        for (int i = 0; i < 23; i++) {
            w.write(i == 25 ? 0 : 1);
            w.writeShort(i == 25 ? 0 : 1);
        }
        w.write(0);
        w.writeInt(-1);
        w.write(0);
        w.write(Charlist ? 1 : 0);

        return w.getPacket();
    }

    public static final byte[] deleteCharResponse(final int cid, final int state) {
        final WritingPacket w = new WritingPacket();

        w.writeShort(SendPacketOpcode.DELETE_CHAR_RESPONSE.getValue());
        w.writeInt(cid);
        w.write(state);
        w.writeShort(0);
        return w.getPacket();
    }

    public static final byte[] secondPwError(final byte mode) {
        final WritingPacket w = new WritingPacket(3);

        /*
		 * 14 - Invalid password 15 - Second password is incorrect
         */
        w.writeShort(SendPacketOpcode.SECONDPW_ERROR.getValue());
        w.write(mode);

        return w.getPacket();
    }

    public static final byte[] getServerList(final int serverId, final Map<Integer, Integer> channelLoad) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.WORLD_INFORMATION.getValue());
        w.write(serverId);
        w.writeMapleAsciiString(LoginServer.getInstance().getServerName());
        w.write(LoginServer.getInstance().getFlag());
        String msg = "";
        if (GameConstants.isServerReady()) {
            /* If the server is fully loaded */
            msg = LoginServer.getInstance().getEventMessage();
        } else {
            /* If the server is not fully loaded */
            msg = "Server not ready.\r\n\r\nAll the data you need \r\nWait a moment \r\nplease wait.";
        }
        w.writeMapleAsciiString(msg);

        int lastChannel = ServerConstants.ChannelCount;
        Set<Integer> channels = channelLoad.keySet();
        for (int i = 30; i > 0; i--) {
            if (channels.contains(i)) {
                lastChannel = i;
                break;
            }
        }
        w.write(lastChannel);

        int load;
        for (int i = 0; i < lastChannel; i++) {
            if (channels.contains(i)) {
                load = (ChannelServer.getInstance(i).getPlayerStorage().getConnectedClients());
            } else {
                load = 50;
            }
            w.writeMapleAsciiString(
                    LoginServer.getInstance().getServerName() + "-" + (i == 1 ? i : (i == 2 ? ("20세이상") : (i))));
            w.writeInt(load == 0 ? 1 : load >= 50 ? 50 : load);
            w.write(serverId);
            w.write(i);
            w.write(0); // adult channel
        }
        w.writeShort(0); // {x, y, message}
        w.writeInt(0); // boom up event notice
        w.write(0); // star planet

        return w.getPacket();
    }

    public static byte[] getLastWorld() {
        WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.LAST_WORLD.getValue());
        w.writeInt(0);

        return w.getPacket();
    }

    public static byte[] getSelectedWorld() {
        WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.SELECTED_WORLD.getValue());
        w.writeInt(0);

        return w.getPacket();
    }

    public static byte[] recommendWorld() {
        boolean message = ServerConstants.recommendMessage.length() > 0;
        WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.RECOMMEND_WORLD.getValue());
        w.write(message ? 1 : 0); // amount
        if (message) {
            w.writeInt(0); // world id
            w.writeMapleAsciiString(ServerConstants.recommendMessage);
        }
        return w.getPacket();
    }

    public static byte[] getSecondPasswordConfirm(boolean success) {
        WritingPacket w = new WritingPacket();
        w.writeShort(0x2E);
        w.write(success ? 0x14 : 0);
        return w.getPacket();
    }

    public static byte[] getSecondPasswordResult(boolean success) {
        WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.AUTH_STATUS_WITH_SPW_RESULT.getValue());
        w.write(success ? 0 : 0x14);

        return w.getPacket();
    }

    public static byte[] getSecondPasswordCheck(boolean enable, boolean picwrong, boolean success) {
        WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.SECONDPW_RESULT.getValue());
        w.write(1);
        w.write(6);

        return w.getPacket();
    }

    public static final byte[] getEndOfServerList() {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.WORLD_INFORMATION.getValue());
        w.write(0xFF);
        /* 1.2.240 Add ad */
        int advertisement = 0; // 1.2.250(2) standard.
        w.write(advertisement);
        for (int i = 0; i < advertisement; i++) {
            w.writeMapleAsciiString(""); // Advertising photos.

            w.writeMapleAsciiString(""); // Address.
            w.writeInt(0); // time
            w.writeInt(0); // width
            /* Fixed ad width and weight */
            w.writeInt(0); // height
            w.writeInt(0); // x
            w.writeInt(0); // y
        }
        /* 1.2.240 Add ad */
        w.write(0); // NotActiveAccountDlgFocus
        w.write(0); // Attempt to access locked account?

        return w.getPacket();
    }

    public static final byte[] setBurningEffect(final int chrid) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.SET_BURNING_CHAR.getValue());
        w.write(1);
        w.writeInt(chrid);

        return w.getPacket();
    }

    public static final byte[] getChannelBackImg(final boolean first_login, final int status) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.CHANNEL_BACK_IMG.getValue());
        w.write(!first_login ? 2 : 0);
        if (!first_login) {
            w.writeMapleAsciiString("main");
            w.write(1);
            w.writeMapleAsciiString("sub");
            w.write(status);
        }
        return w.getPacket();
    }

    /*
	 * 0 - Normal 1 - Highly populated 2 - Full
     */
    public static final byte[] getServerStatus(final int status) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.SERVERSTATUS.getValue());
        w.writeShort(status);

        return w.getPacket();
    }

    public static final byte[] charlist(final MapleClient c, final boolean secondpw, final List<MapleCharacter> chars) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.CHARLIST.getValue());
        w.write(0);
        w.writeMapleAsciiString("");
        w.writeMapleAsciiString("");
        w.writeInt(0);
        w.write(0);// 버닝
        w.writeInt(0);
        w.writeLong(PacketProvider.getKoreanTimestamp(System.currentTimeMillis()));
        w.write(0); // 1.2.238+
        w.writeInt(chars.size()); // 1.2.238+
        for (final MapleCharacter chr : chars) { // 1.2.238+
            w.writeInt(chr.getId()); // TODO : Character position.
        }
        w.write(chars.size()); // 1.2.238+
        for (final MapleCharacter chr : chars) {
            addPlayerEntry(w, chr);
        }
        w.write(c.isUsing2ndPassword() ? 1 : 2);
        w.write(0);
        w.write(1);
        w.writeInt(40);
        w.writeInt(0);
        w.writeInt(0xFF);
        w.writeReversedLong(PacketProvider.getKoreanTimestamp(System.currentTimeMillis()));
        int value = c.getNameChangeValue();
        w.write(1);
        w.write0(11);
        return w.getPacket();
    }

    public static final byte[] addNewCharacterEntry(final MapleCharacter chr, final boolean worked) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.ADD_NEW_CHAR_ENTRY.getValue());
        w.write(worked ? 0 : 1);
        addPlayerEntry(w, chr);
        w.writeInt(0);
        w.write(0);
        return w.getPacket();
    }

    public static final byte[] charNameResponse(final String charname, final boolean nameUsed) {
        final WritingPacket w = new WritingPacket();
        w.writeShort(SendPacketOpcode.CHAR_NAME_RESPONSE.getValue());
        w.writeMapleAsciiString(charname);
        w.write(nameUsed ? 1 : 0);

        return w.getPacket();
    }

    private static final void addPlayerEntry(final WritingPacket w, final MapleCharacter chr) {
        PacketProvider.addPlayerStats(w, chr, false);
        w.writeInt(0);
        w.writeLong(0);
        PacketProvider.addPlayerLooks(w, chr, true, chr.getGender() == 1);
        if (GameConstants.isZero(chr.getJob())) {
            PacketProvider.addPlayerLooks(w, chr, true, chr.getGender() == 0);
        }
        w.write(0);
    }

    public static byte[] sp2() {
        WritingPacket p = new WritingPacket();
        p.writeShort(36);
        p.write(1);
        return p.getPacket();
    }

    public static byte[] sp3() {
        WritingPacket p = new WritingPacket();
        p.writeShort(33);
        p.writeShort(1);
        return p.getPacket();
    }
    
    public static byte[] sucessSP() {
        WritingPacket p = new WritingPacket();
        p.writeShort(32);
        p.write(0);
        return p.getPacket();
    }

    public static byte[] registerSP() {
        WritingPacket p = new WritingPacket();
        p.writeShort(58);
        p.write(0x13);
        return p.getPacket();
    }

    public static byte[] createCharMenu() {
        // Ilium Carrier Restriction Off(20 => 25)
        WritingPacket p = new WritingPacket();
        p.writeShort(1692);
        p.write(0);
        p.write(1);
        p.write(0x1E);
        for (int i = 0; i < 23; i++) {
            p.write(i == 25 ? 0 : 1);
            p.writeShort(i == 25 ? 0 : 1);
        }
        return p.getPacket();
    }

    public static byte[] faildSp() {
        WritingPacket p = new WritingPacket();
        p.writeShort(58);
        p.write(0x14);
        return p.getPacket();
    }
}
