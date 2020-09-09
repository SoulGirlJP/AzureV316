package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import client.Character.MapleCharacter;
import client.ItemInventory.IItem;
import client.Community.MapleUserTrade;
import constants.ServerConstants;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LoggerChatting {

    public static String chatLog = "ChatLog.txt";
    public static String givercLog = "후원포인트지급.txt";
    public static String givehbLog = "홍보포인트지급.txt";
    public static String giveadLog = "추가데미지지급.txt";
    public static String Leon = "반레온토벌.txt";
    public static String Kaung = "카웅토벌.txt";
    public static String Papulatus = "파풀라투스토벌.txt";
    public static String Archive = "아카이럼토벌.txt";
    public static String Signus = "시그너스토벌.txt";
    public static String demLog = "데미안토벌.txt";
    public static String Heila = "힐라토벌.txt";
    public static String Belum = "벨룸토벌.txt";   
    public static String Half = "반반토벌.txt";   
    public static String BloodyQueen = "블러디퀸토벌.txt";   
    public static String Pierre = "피에르토벌.txt";   
    public static String Pinkbin = "핑크빈토벌.txt";   
    public static String Zacum = "자쿰토벌.txt";
    public static String Magnus = "매그너스토벌.txt";
    public static String swooLog = "스우토벌.txt";
    public static String lucLog = "루시드토벌.txt";
    public static String wLog = "윌토벌.txt";
    public static String crLog = "크로스토벌.txt";
    public static String dorosiLog = "도로시토벌.txt";
    public static String dcLog = "캐릭터삭제.txt";
    public static String commandLog = "GM명령어로그.txt";
    public static String commandLog2 = "유저명령어로그.txt";
    public static String tradeLog = "교환로그.txt";

    public static void writeLog(String log, String text) {
        try {
            Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("KST"), Locale.KOREAN);
            File file = new File("property/Logs/" + log);

            FileOutputStream fos = new FileOutputStream(file, true);

            fos.write((currentTime.getTime().toString() + " " + text + "" + System.getProperty("line.separator"))
                    .getBytes());
            fos.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public static void writeTradeLog(String log, String text, String character) {
        try {
            Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("KST"), Locale.KOREAN);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            File file = new File(
                    "property/Logs/" + sdf.format(timestamp) + " " + character + " " + log);

            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write((currentTime.getTime().toString() + " " + text + "" + System.getProperty("line.separator") + "\r\n")
                    .getBytes());
            fos.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public static String getTradeLogType(MapleUserTrade local, MapleUserTrade partner) {
        StringBuilder sb = new StringBuilder();
        sb.append(local.getChr().getName() + "이(가) 받은 아이템, 메소 \r\n");
        sb.append(local.exchangeMeso + " 메소\r\n");
        for (final IItem item : local.exchangeItems) {
            sb.append(item.getItemId() + " " + item.getQuantity() + "개\r\n");
        }
        sb.append(partner.getChr().getName() + "==================================================\r\n");
        sb.append(partner.getChr().getName() + "이(가) 받은 아이템, 메소\r\n");
        sb.append(partner.exchangeMeso + " 메소\r\n");
        for (final IItem item : partner.exchangeItems) {
            sb.append(item.getItemId() + " " + item.getQuantity() + "개\r\n");
        }
        return sb.toString();
    }

    public static String getChatLogType(String type, MapleCharacter chr, String chattext) {
        return "[" + type + "] " + chr.getName() + " : " + chattext + " 현재맵 : " + chr.getMap().getStreetName() + "-"
                + chr.getMap().getMapName() + " (" + chr.getMap().getId() + ")";
    }

    public static String getCommandLogType(String type, MapleCharacter chr, String chattext) {
        return "[" + type + "] 캐릭터 이름 : " + chr.getName() + "사용한 명령어 : " + chattext + " 현재맵 : "
                + chr.getMap().getStreetName() + "-" + chr.getMap().getMapName() + " (" + chr.getMap().getId() + ")";
    }

    public static String getRcgive(String type, MapleCharacter chr, MapleCharacter victim, long qty) {
        return "[" + type + "] 지급자 : " + chr.getName() + " / 피지급자 : " + victim.getName() + " / 지급량 : " + qty;
    }

    public static String getBossLog(String type, MapleCharacter chr) {
        return "[" + type + "] 닉네임 : " + chr.getName() + " /  계정ID : " + chr.getAccountID();
    }

    public static String getDeleteLog(String type, String chr, String ac) {
        return "[" + type + "] 닉네임 : " + chr + " /  계정ID : " + ac;
    }
}
