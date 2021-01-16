package constants;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import client.MapleClient;
import client.BingoGame;
import client.Character.MapleCharacter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import tools.Triple;

public class ServerConstants {

    /* 서버 설정 */
    public static String Host = "175.207.0.33";
    public static String ADMIN1;
    public static String ADMIN2;
    public static String ADMIN3;
    public static String ADMIN4;
    public static String ADMIN5;
    public static int startMap;
    public static byte defaultFlag;
    public static int ChannelCount;
    public static int LoginPort;
    public static int ChannelPort;
    public static int CashShopPort;
    public static int BuddyChatPort;

    public static boolean isLocal = false;
    public static int AHT_VERSION = 0;
    public static long SKILL_FILE_CRC = 1471966654; // skill.wz crc32

    public static byte[] testPacket = null;
    public static boolean already = false;
    public static int defaultExpRate;
    public static int defaultMesoRate;
    public static int defaultDropRate;
    public static int defaultCashRate;
    public static int defaultBossCashRate;
    public static int maxDrop;
    public static int bossMaxDrop;

    public static String mrank1 = null;
    public static String prank1 = null;
    public static String crank1 = null;

    public static int defaultMaxChannelLoad = 50;
    public static int cshopNpc = 0;
    public static int chatlimit = 0;

    /* DB 설정 */
    public static int dbPort;
    public static String dbHost;
    public static String dbUser;
    public static String dbPassword;

    /* Message 설정 및 이벤트 설정 */
    public static String recommendMessage = "";
    public static String serverName = "";
    public static String serverMessage = "";
    public static String serverWelcome = "";
    public static String eventMessage = "";
    public static String serverHint = "원석님 바보";
    public static String beginner = "";
    public static String serverNotice = "";
    public static String serverNotititle = "";
    public static String serverNotification = "";
    public static String events = "";
    public static String real_face_hair = "";
    public static String serverCheckMessage = "현재 " + serverName + " 서버 점검 중입니다.\r\n 자세한 내용은 홈페이지를 참고하여 주십시오.\r\n [사유 : 시스템 안정화]";

    /* 개발 설정 */
    public static boolean serverCheck;

    public static boolean isDev = false;

    public static int shopSale = 0;

    /* 기타 설정 */
    public static boolean UnlockMaxDamage = true;
    public static boolean feverTime = false;
    public static boolean useMaxDrop;
    public static boolean useBossMaxDrop;
    
    public static boolean showPackets = true;
    public static boolean DEBUG_RECEIVE = true;
    public static boolean DEBUG_SEND = true;
    /* 패킷테스트 
    public static boolean showPackets = false;
    public static boolean sendPacketShow = false;
    public static boolean recvPacketShow = false;
    */
    public static boolean realese = false;
    public static String path = "";
    public static String windowsDumpPath = "";

    /* 버전 설정 */
    public static short MAPLE_VERSION;
    public static byte subVersion;
    public static final byte check = 0;

    /* 기타 설정 2 */
    public static String hp_skillid_dummy = "";
    public static String hp_skillid_real[];

    //public static int loginPointAid = -1;

    public static MapleCharacter chr;

    public static List<MapleCharacter> mChat_char = new ArrayList<>();

    public static boolean isShutdown = false;

    public static boolean AutoHotTimeSystem;
    public static boolean AutoHotTimeSystemtemchacks = false; 
    
    
    public static BingoGame BingoGame = null;    

    /* Hot TIme Settings */
    public static int AutoHotTimeSystemHour = 0;
    public static int AutoHotTimeSystemMinute = 0;
    public static int AutoHotTimeSystemSecond = 0;

    public static List<Integer> AutoHotTimeSundayItemCode = new ArrayList<Integer>();
    public static List<Integer> AutoHotTimeMondayItemCode = new ArrayList<Integer>();
    public static List<Integer> AutoHotTimeTuesdayItemCode = new ArrayList<Integer>();
    public static List<Integer> AutoHotTimeWednesdayItemCode = new ArrayList<Integer>();
    public static List<Integer> AutoHotTimeThursdayItemCode = new ArrayList<Integer>();
    public static List<Integer> AutoHotTimeFridayItemCode = new ArrayList<Integer>();
    public static List<Integer> AutoHotTimeSaturdayItemCode = new ArrayList<Integer>();
    
    /* Hot Time Settings End */    
    
    /*커넥터 설정*/
    public static final Map<String, Triple<String, String, String>> authlist = new ConcurrentHashMap<>();//모두포함
    public static final Map<String, Triple<String, String, String>> authlist2 = new ConcurrentHashMap<>();//모두포함
    public static boolean ConnectorSetting;
    public static boolean ConnecterLog = false;    
    
    public static final char PLAYER_COMMAND_PREFIX = '@';
    public static final char ADMIN_COMMAND_PREFIX = '!';

    public static final boolean AUTO_REGISTER = false;
    public static final int GENSALT_ITERATIONS = 10;
    
    static {
        try {
            FileInputStream setting = new FileInputStream("property/ServerSettings.properties");
            Properties setting_ = new Properties();
            setting_.load(setting);
            setting.close();
            defaultFlag = Byte.parseByte(setting_.getProperty(toUni("Flag")));
            String[] temp = setting_.getProperty(toUni("커스텀퀘스트")).split(",");
            for (String v1 : temp) {
                GameConstants.questReader.add(Integer.parseInt(v1));
            }
            ChannelCount = Integer.parseInt(setting_.getProperty("ChannelCount"));
            LoginPort = Integer.parseInt(setting_.getProperty("LoginPort"));
            ChannelPort = Integer.parseInt(setting_.getProperty("ChannelPort"));
            CashShopPort = Integer.parseInt(setting_.getProperty("CSPort"));
            BuddyChatPort = Integer.parseInt(setting_.getProperty("BuddyChatPort"));

            defaultExpRate = Integer.parseInt(setting_.getProperty("EXPRate"));
            defaultDropRate = Integer.parseInt(setting_.getProperty("DropRate"));
            defaultMesoRate = Integer.parseInt(setting_.getProperty("MesoRate"));
            defaultCashRate = Integer.parseInt(setting_.getProperty("CashRate"));
            defaultBossCashRate = Integer.parseInt(setting_.getProperty("BossCashRate"));

            cshopNpc = Integer.parseInt(setting_.getProperty("CSNpc"));

            serverName = new String(setting_.getProperty(toUni("서버이름")).getBytes("ISO-8859-1"), "euc-kr");
            serverMessage = new String(setting_.getProperty(toUni("서버메세지")).getBytes("ISO-8859-1"), "euc-kr");
            serverWelcome = new String(setting_.getProperty(toUni("서버환영메세지")).getBytes("ISO-8859-1"), "euc-kr");
            eventMessage = new String(setting_.getProperty(toUni("이벤트메세지")).getBytes("ISO-8859-1"), "euc-kr");
            beginner = new String(setting_.getProperty(toUni("처음시작공지")).getBytes("ISO-8859-1"), "euc-kr");
            serverNotititle = new String(setting_.getProperty(toUni("서버공지제목")).getBytes("ISO-8859-1"), "euc-kr");
            serverNotification = new String(setting_.getProperty(toUni("서버공지내용")).getBytes("ISO-8859-1"), "euc-kr");
            recommendMessage = new String(setting_.getProperty(toUni("추천메세지")).getBytes("ISO-8859-1"), "euc-kr");
            serverHint = new String(setting_.getProperty(toUni("서버힌트")).getBytes("ISO-8859-1"), "euc-kr");

            dbHost = new String(setting_.getProperty(toUni("Arc.dbHost")).getBytes("ISO-8859-1"), "euc-kr");
            dbPort = Integer.parseInt(setting_.getProperty(toUni("Arc.dbPort")));
            dbUser = new String(setting_.getProperty(toUni("Arc.dbUser")).getBytes("ISO-8859-1"), "euc-kr");
            dbPassword = new String(setting_.getProperty(toUni("Arc.dbPassword")).getBytes("ISO-8859-1"), "euc-kr");

            events = new String(setting_.getProperty("Events"));

            startMap = Integer.parseInt(setting_.getProperty("StartMap"));
            serverHint = new String(setting_.getProperty(toUni("서버힌트")).getBytes("ISO-8859-1"), "euc-kr");

            MAPLE_VERSION = Short.parseShort(setting_.getProperty("GameVersion"));
            subVersion = Byte.parseByte(setting_.getProperty("GameSubVersion"));

            path = new String(setting_.getProperty("Path"));
            windowsDumpPath = new String(setting_.getProperty("WindowsDumpPath"));

            serverCheck = Boolean.parseBoolean(setting_.getProperty(toUni("서버점검")));
            showPackets = Boolean.parseBoolean(setting_.getProperty(toUni("패킷출력")));
            useMaxDrop = Boolean.parseBoolean(setting_.getProperty(toUni("최대드랍사용")));
            useBossMaxDrop = Boolean.parseBoolean(setting_.getProperty(toUni("최대보스드랍사용")));
            bossMaxDrop = Integer.parseInt(setting_.getProperty("BossMaxDrop"));
            maxDrop = Integer.parseInt(setting_.getProperty("MaxDrop"));

            AutoHotTimeSystem = Boolean.parseBoolean(setting_.getProperty("IsAutoHotTime"));
            AutoHotTimeSystemHour = Integer.parseInt(setting_.getProperty("AutoHotTimeHour"));
            AutoHotTimeSystemMinute = Integer.parseInt(setting_.getProperty("AutoHotTimeMinute"));
            AutoHotTimeSystemSecond = Integer.parseInt(setting_.getProperty("AutoHotTimeSeconds"));
            
            String AutoHotTimeSundayItemCodes = setting_.getProperty("AutoHotTimeSundayItemCode");
            if (!AutoHotTimeSundayItemCodes.isEmpty()) {
                String AutoHotTimeSundayItemCodess[] = AutoHotTimeSundayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeSundayItemCodess.length; i++) {
                    AutoHotTimeSundayItemCode.add(Integer.parseInt(AutoHotTimeSundayItemCodess[i]));
                }
            }
            String AutoHotTimeMondayItemCodes = setting_.getProperty("AutoHotTimeMondayItemCode");
            if (!AutoHotTimeMondayItemCodes.isEmpty()) {
                String AutoHotTimeMondayItemCodess[] = AutoHotTimeMondayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeMondayItemCodess.length; i++) {
                    AutoHotTimeMondayItemCode.add(Integer.parseInt(AutoHotTimeMondayItemCodess[i]));
                }
            }
            String AutoHotTimeTuesdayItemCodes = setting_.getProperty("AutoHotTimeTuesdayItemCode");
            if (!AutoHotTimeTuesdayItemCodes.isEmpty()) {
                String AutoHotTimeTuesdayItemCodess[] = AutoHotTimeTuesdayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeTuesdayItemCodess.length; i++) {
                    AutoHotTimeTuesdayItemCode.add(Integer.parseInt(AutoHotTimeTuesdayItemCodess[i]));
                }
            }
            String AutoHotTimeWednesdayItemCodes = setting_.getProperty("AutoHotTimeWednesDayItemCode");
            if (!AutoHotTimeWednesdayItemCodes.isEmpty()) {
                String AutoHotTimeWednesdayItemCodess[] = AutoHotTimeWednesdayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeWednesdayItemCodess.length; i++) {
                    AutoHotTimeWednesdayItemCode.add(Integer.parseInt(AutoHotTimeWednesdayItemCodess[i]));
                }
            }
            String AutoHotTimeThursdayItemCodes = setting_.getProperty("AutoHotTimeThursdayItemCode");
            if (!AutoHotTimeThursdayItemCodes.isEmpty()) {
                String AutoHotTimeThursdayItemCodess[] = AutoHotTimeThursdayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeThursdayItemCodess.length; i++) {
                    AutoHotTimeThursdayItemCode.add(Integer.parseInt(AutoHotTimeThursdayItemCodess[i]));
                }
            }

            String AutoHotTimeFridayItemCodes = setting_.getProperty("AutoHotTimeFridayItemCode");
            if (!AutoHotTimeFridayItemCodes.isEmpty()) {
                String AutoHotTimeFridayItemCodess[] = AutoHotTimeFridayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeFridayItemCodess.length; i++) {
                    AutoHotTimeFridayItemCode.add(Integer.parseInt(AutoHotTimeFridayItemCodess[i]));
                }
            }
            String AutoHotTimeSaturdayItemCodes = setting_.getProperty("AutoHotTimeSaturdayItemCode");
            if (!AutoHotTimeSaturdayItemCodes.isEmpty()) {
                String AutoHotTimeSaturdayItemCodess[] = AutoHotTimeSaturdayItemCodes.split(",");
                for (int i = 0; i < AutoHotTimeSaturdayItemCodess.length; i++) {
                    AutoHotTimeSaturdayItemCode.add(Integer.parseInt(AutoHotTimeSaturdayItemCodess[i]));
                }
            }
            
            ConnectorSetting = Boolean.parseBoolean(setting_.getProperty("IsConnectorSetting"));
        } catch (Exception e) {
            System.err.println("[오류] 서버 세팅파일을 불러오는데 실패하였습니다.");
            if (!realese) {
                e.printStackTrace();
            }
        }
    }

    public static int basePorts = (isLocal ? 100 : 0) + (ChannelPort);

    protected static String toUni(String kor) throws UnsupportedEncodingException {
        return new String(kor.getBytes("KSC5601"), "8859_1");
    }

    public static boolean isAdminIp(String ip) {
        ip = ip.replaceAll("/", "");
        return ip.equals(ServerConstants.ADMIN1) || ip.equals(ServerConstants.ADMIN2) || ip.equals(ServerConstants.ADMIN3) || ip.equals(ServerConstants.ADMIN4) || ip.equals(ServerConstants.ADMIN5);
    }

    public static String getServerHost(MapleClient ha) {
        try {
            return InetAddress.getByName(ServerConstants.Host).getHostAddress().replace("/", "");
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        return ServerConstants.Host;
    }
}
