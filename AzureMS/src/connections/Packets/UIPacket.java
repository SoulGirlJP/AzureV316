package connections.Packets;

import client.Character.MapleCharacter;
import constants.GameConstants;
import constants.ServerConstants;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import tools.HexTool;
import tools.Pair;

public class UIPacket {

    public static byte[] showInfo(String msg) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.TOP_MSG.getValue());
        packet.writeMapleAsciiString(msg);

        return packet.getPacket();
    }

    public static byte[] BattleUserRespawnUI() {
        WritingPacket p = new WritingPacket();
        p.writeShort(205);
        p.writeMapleAsciiString("Resurrect automatically after 5 seconds.");
        p.writeInt(5);
        return p.getPacket();
    }

    public static byte[] greenShowInfo(String msg) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.GREEN_SHOW_INFO.getValue());
        packet.write(0);
        packet.writeMapleAsciiString(msg);
        packet.write(1); // 0 = Lock 1 = Clear

        return packet.getPacket();
    }

    public static byte[] detailShowInfo(String msg, boolean RuneSystem, int color) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.DETAIL_SHOW_INFO.getValue());
        packet.writeInt(color); // color
        packet.writeInt(RuneSystem ? 0x11 : 0x14); // width
        packet.writeInt(RuneSystem ? 0 : 0x0F); // heigh
        packet.writeInt(0); // Unk
        packet.writeMapleAsciiString(msg);

        return packet.getPacket();
    }

    public static byte[] detailShowInfo(String msg, boolean RuneSystem) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.DETAIL_SHOW_INFO.getValue());
        packet.writeInt(3); // color
        packet.writeInt(RuneSystem ? 0x11 : 0x14); // width
        packet.writeInt(RuneSystem ? 0 : 0x0F); // heigh
        packet.writeInt(0); // Unk
        packet.writeMapleAsciiString(msg);

        return packet.getPacket();
    }

    public static byte[] getItemTopMsg(int itemid, String msg) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MID_MSG.getValue());
        packet.writeInt(itemid);
        packet.writeMapleAsciiString(msg);

        return packet.getPacket();
    }

    public static byte[] enforceMSG(String a, int id, int delay) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ENFORCE_MSG.getValue());
        packet.writeMapleAsciiString(a);
        packet.writeInt(id);
        packet.writeInt(delay);
        packet.write(1);

        return packet.getPacket();
    }

    public static byte[] enforceMSGY(String a, int id, int delay, int y) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ENFORCE_MSG.getValue() + 1);
        packet.writeMapleAsciiString(a);
        packet.writeInt(id);
        packet.writeInt(delay);
        packet.writeInt(y);

        return packet.getPacket();
    }

    public static byte[] clearMidMsg() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.CLEAR_MID_MSG.getValue());

        return mplew.getPacket();
    }

    public static byte[] getStatusMsg(int itemid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        packet.write(0x8); // 1.2.250+ (+1)
        packet.writeInt(itemid);

        return packet.getPacket();
    }

    public static byte[] getSPMsg(byte sp, short job) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        packet.write(0x5); // 1.2.250+ (+1)
        packet.writeShort(job);
        packet.write(sp);

        return packet.getPacket();
    }

    public static byte[] getGPMsg(int itemid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        packet.write(0x8); // 1.2.250+ (+1)
        packet.writeInt(itemid);

        return packet.getPacket();
    }

    public static final byte[] MapNameDisplay(final int mapid) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.BOSS_ENV.getValue());
        packet.write(0x4);
        packet.writeMapleAsciiString("maplemap/enter/" + mapid);

        return packet.getPacket();
    }

    public static final byte[] showWZEffect(final String data, int value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        packet.write(29);
        packet.writeMapleAsciiString(data);
        packet.writeInt(value);

        return packet.getPacket();
    }

    public static byte[] Mulung_DojoUp2() {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        pw.write(10);

        return pw.getPacket();
    }

    public static final byte[] broadcastWZEffect(final int cid, final String data, int value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SHOW_FOREIGN_EFFECT.getValue());
        packet.writeInt(cid);
        packet.write(29);
        packet.writeMapleAsciiString(data);
        packet.writeInt(value);

        return packet.getPacket();
    }

    public static final byte[] showWZEffect(final String data) {
        return showWZEffect(data, 0);
    }

    public static byte[] summonHelper(boolean summon) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.SUMMON_HINT.getValue());
        packet.write(summon ? 1 : 0);

        return packet.getPacket();
    }

    public static byte[] summonMessage(int type) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.SUMMON_HINT_MSG.getValue());
        packet.write(1);
        packet.writeInt(type);
        packet.writeInt(7000); // probably the delay

        return packet.getPacket();
    }

    public static byte[] summonMessage(String message) {
        WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.SUMMON_HINT_MSG.getValue());
        packet.write(0);
        packet.writeMapleAsciiString(message);
        packet.writeInt(200); // IDK
        packet.writeShort(0);
        packet.writeInt(10000); // Probably delay

        return packet.getPacket();
    }

    public static final byte[] AchievementRatio(int value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ACHIVEMENT_RATIO.getValue());
        packet.writeInt(value);

        return packet.getPacket();
    }

    public static final byte[] MapleChat() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MAPLE_CHAT.getValue());
        packet.write(0x07);
        for (MapleCharacter chr : ServerConstants.mChat_char) {
            packet.write(1);
            packet.writeInt(chr.getId());
            packet.writeInt(chr.getFame());
            packet.writeLong(System.currentTimeMillis());
            packet.writeMapleAsciiString(chr.getName());
            PacketProvider.addPlayerLooks(packet, chr, true, chr.getGender() == 1);
        }
        packet.write(0);
        packet.write(0);
        return packet.getPacket();
    }

    public static final byte[] OpenUI(int value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.OPEN_WINDOW.getValue());
        packet.writeInt(value);

        return packet.getPacket();
    }

    public static final byte[] SetDead(byte value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SetDead.getValue());
        packet.write(value);
        packet.writeInt(0);
	System.out.println("SetDead" + packet.toString());
        return packet.getPacket();
    }

    public static final byte[] OpenUIOnDead() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.DEADED_WINDOW.getValue());
        packet.writeInt(1);
        packet.write(0);
        packet.writeInt(3);
        packet.writeInt(1);
        System.out.println("OpenUIOnDead" + packet.toString());
        return packet.getPacket();
    }

    public static final byte[] showSpecialMapEffect(int type, int action, String music, String back) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SPECIAL_MAP_EFFECT.getValue());
        packet.writeInt(type);
        packet.writeInt(action);
        packet.writeMapleAsciiString(music);
        if (back != null) {
            packet.writeMapleAsciiString(back);
        }
        return packet.getPacket();
    }

    public static final byte[] cancelSpecialMapEffect() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SPECIAL_MAP_EFFECT.getValue());
        packet.writeLong(0);

        return packet.getPacket();
    }

    public static final byte[] playSpecialMapSound(String sound) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SPECIAL_MAP_SOUND.getValue());
        packet.writeMapleAsciiString(sound);

        return packet.getPacket();
    }

    public static final byte[] eliteBossNotice(int type, int mapid, int mobid) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.ELITE_BOSS_NOTICE.getValue());
        packet.write(type);
        if (type == 1) {
            packet.writeInt(mapid);
        } else if (type == 2) {
            packet.writeInt(mapid);
            packet.writeInt(mobid);
            packet.write(HexTool.getByteArrayFromHexString("20 75 1A 00"));
        }
        return packet.getPacket();
    }

    public static byte[] showPopupMessage(final String msg) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.POPUP_MSG.getValue());
        packet.writeMapleAsciiString(msg);
        packet.write(1);

        return packet.getPacket();
    }

    public static byte[] openUI(int type) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.OPEN_WINDOW.getValue());
        packet.writeInt(type);
        return packet.getPacket();
    }

    public static byte[] IntroLock(boolean enable) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.INTRO_LOCK.getValue());
        pw.write(enable ? 1 : 0);
        pw.writeInt(0);

        return pw.getPacket();
    }

    public static byte[] IntroEnableUI(int enable) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.INTRO_ENABLE_UI.getValue());
        pw.write(enable > 0 ? 1 : 0);
        if (enable > 0) {
            pw.write(enable);
            pw.writeShort(0);
        }
        pw.write(0);
        return pw.getPacket();
    }

    public static byte[] IntroDisableUI(boolean enable) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.INTRO_DISABLE_UI.getValue());
        pw.write(enable ? 1 : 0);

        return pw.getPacket();
    }

    public static byte[] OnCloseUI(int ui) {
        WritingPacket pw = new WritingPacket();
        pw.writeShort(SendPacketOpcode.OPEN_WINDOW.getValue() + 1);
        pw.writeInt(ui);
        return pw.getPacket();
    }

    public static byte[] openUIOption(int type, int npc) {
        WritingPacket pw = new WritingPacket(10);
        pw.writeShort(SendPacketOpcode.OPEN_WINDOW.getValue() + 2);
        pw.writeInt(type);
        pw.writeInt(npc);
        pw.writeInt(0);
        return pw.getPacket();
    }

    public static byte[] getDirectionInfo(int type, int value) {
        WritingPacket pw = new WritingPacket();
        pw.writeShort(SendPacketOpcode.DIRECTION_INFO.getValue());
        pw.write((byte) type);
        pw.writeInt(value);
        return pw.getPacket();
    }

    public static byte[] getDirectionInfo(String data, int value, int x, int y, int a, int b) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.DIRECTION_INFO.getValue());
        pw.write(2);
        pw.writeMapleAsciiString(data);
        pw.writeInt(value);
        pw.writeInt(x);
        pw.writeInt(y);
        pw.write(a);
        if (a > 0) {
            pw.writeInt(0);
        }
        pw.write(b);
        if (b > 1) {
            pw.writeInt(0);
            pw.write(a);
            pw.write(b);
        }

        return pw.getPacket();
    }

    public static byte[] getDirectionEffect(String data, int value, int x, int y) {
        return getDirectionEffect(data, value, x, y, 0);
    }

    public static byte[] getDirectionStatus(boolean enable) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(429);
        pw.write(enable ? 1 : 0);

        return pw.getPacket();
    }

    public static byte[] getT() {
        WritingPacket pw = new WritingPacket();
        pw.writeShort(1274);
        pw.writeInt(0);
        return pw.getPacket();
    }

    public static byte[] getDirectionEffect(String data, int value, int x, int y, int npc) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.DIRECTION_INFO.getValue());
        pw.write(2);
        pw.writeMapleAsciiString(data);
        pw.writeInt(value);
        pw.writeInt(x);
        pw.writeInt(y);
        pw.write(1);
        pw.writeInt(0);
        pw.write(1);
        pw.writeInt(npc);
        pw.write(1);
        pw.write(0);

        return pw.getPacket();
    }

    public static byte[] getDirectionInfoNew(byte x, int value) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.DIRECTION_INFO.getValue());
        pw.write(5);
        pw.write(x);
        pw.writeInt(value);
        if (x == 0) {
            pw.writeInt(value);
            pw.writeInt(value);
        }

        return pw.getPacket();
    }

    public static byte[] getDirectionInfoNew(byte x, int value, int value2, int value3) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(SendPacketOpcode.DIRECTION_INFO.getValue());
        pw.write(5);
        pw.write(x);
        pw.writeInt(value);
        if (x == 0) {
            pw.writeInt(value2);
            pw.writeInt(value3);
        }

        return pw.getPacket();
    }

    public static byte[] reissueMedal(int itemId, int type) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(633);
        pw.write(type);
        pw.writeInt(itemId);

        return pw.getPacket();
    }

    public static byte[] playMovie(String data, boolean show) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(685);
        pw.writeMapleAsciiString(data);
        pw.write(show ? 1 : 0);

        return pw.getPacket();
    }

    public static byte[] sendRepairWindow(int npc) {
        WritingPacket pw = new WritingPacket(10);

        pw.writeShort(601);
        pw.writeInt(33);
        pw.writeInt(npc);
        pw.writeInt(0);

        return pw.getPacket();
    }

    public static byte[] sendJewelCraftWindow(int npc) {
        WritingPacket pw = new WritingPacket(10);

        pw.writeShort(601);
        pw.writeInt(104);
        pw.writeInt(npc);
        pw.writeInt(0);

        return pw.getPacket();
    }

    public static byte[] directionFacialExpression(int expression, int duration) {
        WritingPacket pw = new WritingPacket();

        pw.writeShort(633);
        pw.writeInt(expression);
        pw.writeInt(duration);
        pw.write(0);
        return pw.getPacket();
    }

    public static byte[] OnSetMirrorDungeonInfo(boolean clear, boolean iscashshop) {
        WritingPacket p = new WritingPacket();
        //Dungeon 512 true
        //443 = Fast moving?
        //p.writeShort(443); // Fast moving
        p.writeInt(clear ? 0 : GameConstants.dimensionalMirrorList.size());
        for (Pair<String, String> d : GameConstants.dimensionalMirrorList) {
            p.writeMapleAsciiString(d.left);
            p.writeInt(0);
            p.writeMapleAsciiString(d.right);
        }
        return p.getPacket();
    }
	
	public static byte[] NewOnSetMirrorDungeonInfo(boolean clear) {
        WritingPacket p = new WritingPacket();
        //Dungeon 512hit
        //443 =Fast moving?
        p.writeShort(512); //Cash shop
        p.writeInt(clear ? 0 : GameConstants.dimensionalMirrorList.size());
        for (Pair<String, String> d : GameConstants.dimensionalMirrorList) {
            p.writeMapleAsciiString(d.left);
            p.writeInt(0);
            p.writeMapleAsciiString(d.right);
        }
        return p.getPacket();
    }
    
    /*
    public static byte[] testsadas(boolean clear) {
        WritingPacket p = new WritingPacket();
        p.writeShort(501);
        p.writeInt(clear ? 0 : GameConstants.dList.size());
        for (Pair<String, String> d : GameConstants.dList) {
            p.writeMapleAsciiString(d.left);
            p.writeInt(0);
            p.writeMapleAsciiString(d.right);
        }
        System.out.println("´Þ¼º·ü?" +p.toString());
        return p.getPacket();
    }
    */

    public static byte[] OnUpdateUIEventListInfo() {
        WritingPacket p = new WritingPacket();
        p.writeShort(192);
        p.writeShort(0);
        return p.getPacket();
    }

    public static byte[] OnRequestEventList() {
        WritingPacket p = new WritingPacket();
        p.writeShort(SendPacketOpcode.EVENT_LIST.getValue());
        p.writeInt(1);
        p.write(0);
        p.writeInt(1);
        PacketProvider.LIVE_EVENT_DECODE(p);
        return p.getPacket();
    }

    public static void DAILYGIFTDATA_DECODE(final WritingPacket p) {
        p.writeLong(PacketProvider.getTime(System.currentTimeMillis()));
        p.writeLong(PacketProvider.getTime(-2));
        int v4 = 28;
        p.writeInt(v4);
        p.writeInt(2);
        p.writeInt(v4);
        for (int i = 1; i <= v4; i++) {
            p.writeInt(i);
            p.writeInt(GameConstants.dailyGifts[i]);
            p.writeInt(GameConstants.dailyCounts[i]);
            p.writeInt(1);
            p.writeInt(0);
            p.write(0); // Added a cache icon below
            p.writeInt(0);
            p.writeInt(0);
        }

        p.writeInt(10);
        int v6 = 1;
        p.writeInt(v6);
        for (int i = 0; i < v6; i++) {
            p.writeInt(1000);
            p.writeInt(1);
        }
        int v8 = 0;
        p.writeInt(v8);
        for (int i = 0; i < v8; i++) {
            p.writeInt(0);
            p.writeInt(0);
            p.writeInt(0);
            p.writeInt(0);
        }
    }

    public static byte[] OnDailyGift(byte type, int nRet, int nItemID) {
        WritingPacket p = new WritingPacket();
        p.writeShort(SendPacketOpcode.DAILY_GIFT.getValue());
        p.write(type);
        if (type == 1) {
            p.writeInt(nRet);
            p.writeInt(nItemID);
        } else {
            boolean v1 = true;
            p.write(v1);
            if (v1) {
                DAILYGIFTDATA_DECODE(p);
            }
            int v10 = 0;
            p.writeInt(0);
            for (int i = 0; i < v10; i++) {
                DAILYGIFTDATA_DECODE(p);
            }
        }
        return p.getPacket();
    }

    public static byte[] OnCollectionRecordMessage(int collectionIndex, String data) {
        WritingPacket p = new WritingPacket();
        p.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        p.write(42);
        p.writeInt(collectionIndex);
        p.writeMapleAsciiString(data);
        return p.getPacket();
    }

    public static byte[] OnMonsterCollectionResult(int type) {
        return OnMonsterCollectionResult(type, 0, 0);
    }

    public static byte[] OnMonsterCollectionResult(int type, int ResultValue1, int v4) {
        WritingPacket p = new WritingPacket();
        p.writeShort(353);
        p.writeInt(type);

        p.writeInt(1);

        p.writeInt(ResultValue1);
        p.writeInt(v4);
        return p.getPacket();
    }

    public static Object detailShowInfo(String string, int i, int i0, long l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
