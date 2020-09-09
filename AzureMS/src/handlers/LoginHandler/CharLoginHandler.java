package handlers.LoginHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import client.Character.MapleCharacter;
import client.Character.MapleCharacterUtil;
import client.MapleClient;
import client.ItemInventory.IItem;
import client.ItemInventory.Item;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import client.ItemInventory.MapleItempotMain;
import client.Skills.SkillFactory;
import constants.GameConstants;
import constants.ServerConstants;
import connections.Database.MYSQL;
import launcher.ServerPortInitialize.LoginServer;
import launcher.Start;
import launcher.LauncherHandlers.MapleLoginHelper;
import launcher.LauncherHandlers.MapleLoginWorker;
import launcher.LauncherHandlers.MapleNewCharJobType.JobType;
import launcher.Utility.WorldConnected;
import connections.Packets.LoginPacket;
import connections.Packets.MainPacketCreator;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.ReadingMaple;
import server.Items.ItemInformation;
import tools.KoreanDateUtil;

public class CharLoginHandler {

    public static int canjoin = 1;

    private static boolean loginFailCount(MapleClient c) {
        c.loginAttempt++;
        if (c.loginAttempt > 5) {
            return true;
        }
        return false;
    }

    public static void checkLoginAuthInfo(ReadingMaple rh, MapleClient c) throws Exception {
        rh.skip(22);
        String login = rh.readMapleAsciiString();
        String pwd = rh.readMapleAsciiString();

        c.setAccountName(login);
        c.setAccID(c.getAccID(login));
        //전용접속기
        /*boolean B = SakuraLogin(login);
        if(!B)
        {
            c.send(MainPacketCreator.serverNotice(1,"전용 접속기로만 접속 가능 합니다."));
            c.send(LoginPacket.getLoginFailed(20));
            return;
        }*/
        boolean ipBan = c.hasBannedIP();
        boolean macBan = false;
        int checkId = AutoRegister.checkAccount(c, login, pwd);
        if (!GameConstants.isServerReady()) {
            c.send(MainPacketCreator.serverNotice(1, "The server data is being loaded. Please wait."));
            c.send(LoginPacket.getLoginFailed(20));
            return;
        }
        switch (checkId) {
                case 0:
                    //생성 가능한 아이디일때
                    if (canjoin == 1) {
                        //AutoRegister.registerAccount(c, login, pwd);
                        c.send(MainPacketCreator.serverNotice(1, ServerConstants.serverName + " Successful account creation !\r\nPlease log in again."));
                        c.send(LoginPacket.getLoginFailed(20));
                        return;
                    } else {
                        c.send(MainPacketCreator.serverNotice(1, "Rebooting server, please try again later."));
                        c.send(LoginPacket.getLoginFailed(20));
                    }
                    break;
            case 1:
                // Account Failed
                c.send(MainPacketCreator.serverNotice(1, "No account found.\r\n" + ServerConstants.serverName + "\r\nPlease register an account at\r\nAzureMS.online"));
                c.send(LoginPacket.getLoginFailed(20));
                return;
            case 2:
                // php error
                c.send(MainPacketCreator.serverNotice(1, "There was a page error, please try again later."));
                c.send(LoginPacket.getLoginFailed(20));
                return;
            case 3:
                // Level
                c.send(MainPacketCreator.serverNotice(1, "The site is not at the correct level. Please use your account after it has been upgraded."));
                c.send(LoginPacket.getLoginFailed(20));
                return;
            case 6:
                // Exceeded
                c.send(MainPacketCreator.serverNotice(1, "You have exceeded the maximum number of account creations per IP.."));
                c.send(LoginPacket.getLoginFailed(20));
                return;
            case 5:
                break;
            default:
                return;
        }

        int loginok = c.login(login, pwd, ipBan || macBan);
        Calendar tempbannedTill = c.getTempBanCalendar();
        if (loginok == 0 && (ipBan || macBan)) {
            loginok = 3;
            if (ipBan || macBan) {
                MapleCharacter.ban(c.getIp().split(":")[0], "Enforcing account ban, account " + login, false);
            }
        }
        if (loginok != 0) {
            c.updateLoginState(login);
            if (!loginFailCount(c)) {
                c.getSession().writeAndFlush(LoginPacket.getLoginFailed(loginok));
            }
        } else if (tempbannedTill.getTimeInMillis() != 0) {
            if (!loginFailCount(c)) {
                c.getSession().writeAndFlush(LoginPacket.getTempBan(
                        KoreanDateUtil.getTempBanTimestamp(tempbannedTill.getTimeInMillis()), c.getBanReason()));
            }
        } else {
            c.loginAttempt = 0;
            MapleLoginWorker.registerClient(c);
        }
    }


    public static void CharlistRequest(ReadingMaple rh, MapleClient c) {
        if (!GameConstants.isServerReady()) {
            c.send(MainPacketCreator.serverNotice(1, "[" + ServerConstants.serverName + "] The server is not ready at this time.\r\n\r\nCan't connect to the server yet because it is loading the required data..\r\n\r\nPlease reconnect after a while."));
            return;
        }
        rh.skip(1);
        int server = rh.readByte();
        int channel = rh.readByte();
        final boolean isFirstLogin = rh.readByte() == 0;
        if (!isFirstLogin) { // 1.2.239+ Game end correspondence.
            rh.skip(1);
            final String account = rh.readMapleAsciiString();
            final String login = account.split(",")[0];
            final String pwd = account.split(",")[1];
            int loginok = c.login(login, pwd, c.hasBannedIP());
            if (loginok != 0) {
                c.getSession().close();
                return;
            }
            rh.skip(21);
            c.getSession().writeAndFlush(LoginPacket.getCharEndRequest(c, login, pwd, true));
            c.getSession().writeAndFlush(LoginPacket.getSelectedWorld());
        }
        
        c.setWorld(server);
        c.setChannel(channel);
        System.out.println("[Notice] " + c.getSessionIPAddress().toString() + " in " + c.getAccountName() + " Into account " + (channel == 0 ? 1 : channel == 1 ? "20세이상" : channel) + " Attempting to connect to channel.");
        LoginServer.getInstance().ip.add(c.getSessionIPAddress());
        try {
            List<MapleCharacter> chars = c.loadCharacters();
            c.getSession().writeAndFlush(LoginPacket.charlist(c, c.isUsing2ndPassword(), chars));
            chars = null;
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public static void onlyRegisterSecondPassword(ReadingMaple rh, MapleClient c) {
        String secondpw = rh.readMapleAsciiString();
        c.setSecondPassword(secondpw);
        c.updateSecondPassword();
        c.send(LoginPacket.getSecondPasswordResult(true));
    }

    public static void registerSecondPassword(ReadingMaple rh, MapleClient c) {
        String originalPassword = rh.readMapleAsciiString();
        String changePassword = rh.readMapleAsciiString();

        if (!originalPassword.equals(c.getSecondPassword())) {
            c.send(LoginPacket.getSecondPasswordResult(false));
        } else {
            c.setSecondPassword(changePassword);
            c.updateSecondPassword();
            c.send(LoginPacket.getSecondPasswordResult(true));
        }
    }

    public static void getSPCheck(ReadingMaple rh, MapleClient c) {
        if (c.getSecondPassword() != null) {
            c.getSession().writeAndFlush(LoginPacket.getSecondPasswordCheck(true, true, true));
        } else {
            c.getSession().writeAndFlush(LoginPacket.getSecondPasswordCheck(false, false, false));
        }
    }

    public static void getWebStartLogin(ReadingMaple rh, MapleClient c) {

    }

    public static byte[] digest(String a_szAlgorithm, byte[] a_input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(a_szAlgorithm);
        return md.digest(a_input);
    }

    public static String getCryptoMD5String(String a_szSource) throws Exception {
        if (a_szSource == null) {
            throw new Exception("Can't conver to Message Digest String value!!");
        }
        byte[] bip = digest("MD5", a_szSource.getBytes());
        String eip;
        String result = "";
        int nSize = bip.length;
        for (int i = 0; i < nSize; i++) {
            eip = "" + Integer.toHexString((int) bip[i] & 0x000000ff);
            if (eip.length() < 2) {
                eip = "0" + eip;
            }
            result = result + eip;
        }
        return result;

    }
    //전용접속기
   /* public static boolean SakuraLogin(String LoginID)
    {
    try {
            Connection con = MYSQL.getConnection();
            long time = System.currentTimeMillis() / 1000;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SakuraLogin WHERE time > "+ ( time - 30 ) + " AND id = ?");
            ps.setString(1, LoginID);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }*/
    public static boolean isHomePageAccount(String login, String pwd) {
        boolean isHoamPageAccount = false;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("SELECT * FROM test.xe_member WHERE email_address = ? AND password = ?");
            ps.setString(1, login);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isHoamPageAccount = true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isHoamPageAccount;
    }


    public static void getLoginRequest(ReadingMaple rh, MapleClient c) {
        /* 로그인 시작 */
        c.getSession().writeAndFlush(MainPacketCreator.serverMessage(""));
        short webStart = rh.readShort();
        if (webStart == 1) {
            String token = rh.readMapleAsciiString();
            String[] sp = token.split(",");
            final String login = sp[0];
            final String pwd = sp[1];
            c.updateLoginState(login);
            int loginok = c.login(login, pwd, c.hasBannedIP());
            if (loginok != 0) {
                c.getSession().close();
                return;
            }
            if (c.finishLogin() == 0) {
                c.setAccountName(login);
                c.getSession().writeAndFlush(LoginPacket.getRelogResponse());
                c.getSession().writeAndFlush(LoginPacket.getAuthSuccessRequest(c));
                c.getSession().writeAndFlush(LoginPacket.getCharEndRequest(c, login, pwd, false));
            } else {
                c.getSession().writeAndFlush(LoginPacket.getLoginFailed(20));
            }
        } else {
            final String account = rh.readMapleAsciiString();
            final String login = account.split(",")[0];
            String pwd = account.split(",")[1];
            c.updateLoginState(login);
            int loginok = c.login(login, pwd, c.hasBannedIP());
            if (loginok != 0) {
                c.getSession().close();
                return;
            }
            if (c.finishLogin() == 0) {
                c.setAccountName(login);
                c.getSession().writeAndFlush(LoginPacket.getRelogResponse());
                c.getSession().writeAndFlush(LoginPacket.getCharEndRequest(c, login, pwd, false));
            } else {
                c.getSession().writeAndFlush(LoginPacket.getLoginFailed(20));
            }
        }
    }

    public static void Setup(ReadingMaple r, MapleClient c) {
    }
    
    public static void worldSelect(final ReadingMaple r, final MapleClient c) {
        byte tp = r.readByte();
        int world = r.readInt();
        byte unk = r.readByte();
        c.getSession().writeAndFlush(LoginPacket.getWorldSelect(tp, world));
    }

    public static void checkHotfix(ReadingMaple rh, MapleClient c) {
        c.getSession().writeAndFlush(LoginPacket.getHotfix());
    }

    public static void getIPRequest(ReadingMaple rh, MapleClient c) {
        if (!c.isLoggedIn()) {
            return;
        }
        c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, c.getSessionIPAddress());
        c.getSession().writeAndFlush(MainPacketCreator.getServerIP(c, ServerConstants.basePorts + c.getChannel(),
                ServerConstants.BuddyChatPort, rh.readInt()));
    }

    public static void getDisplayChannel(final boolean first_login, MapleClient c) {
        c.getSession().writeAndFlush(LoginPacket.getServerList(0, WorldConnected.getConnected()));
        c.getSession().writeAndFlush(LoginPacket.getEndOfServerList());
    }

    public static void getSessionCheck(ReadingMaple rh, MapleClient c) {
        int pRequest = rh.readInt();
        int pResponse = pRequest ^ SendPacketOpcode.SESSION_CHECK.getValue();
        c.getSession().writeAndFlush(LoginPacket.getSessionResponse(pResponse));
    }

    public static void setBurningCharacter(ReadingMaple rh, MapleClient c) {
        rh.skip(1);
        int accountId = rh.readInt();
        int charId = rh.readInt();
        if (!c.isLoggedIn() || c.getAccID() != accountId) {
            return;
        }
        if (!c.setBurningCharacter(accountId, charId)) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Invalid request."));
            return;
        }
        c.send(LoginPacket.setBurningEffect(charId));
    }

    public static void secondpasword(ReadingMaple r, MapleClient c) {
        if (c.isUsing2ndPassword()) {
            c.sendPacket(LoginPacket.sp2());
            c.sendPacket(LoginPacket.sp3());
        } else {
            c.sendPacket(MainPacketCreator.serverNotice(1, "Please set the secondary password in the upper left corner."));
            c.sendPacket(LoginPacket.registerSP());
        }
    }

    public static void checkSecondPassword(ReadingMaple rh, MapleClient c) {
        String code = rh.readMapleAsciiString();
        if (code.equals(c.getSecondPassword())) {
            c.send(LoginPacket.createCharMenu());
        } else {
            c.send(LoginPacket.faildSp());
        }
    }

    public static void newConnection(MapleClient c) {
        Connection con = null;
        if (ServerConstants.Host.equals(c.getSessionIPAddress().replace("/", "")) || ServerConstants.showPackets) {
            c.allowLoggin = true;
            return;
        }
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM acceptip where ip = ?");
            ps.setString(1, c.getIp().split(":")[0]);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c.allowLoggin = true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void CheckCharName(String name, MapleClient c) {
        c.getSession().writeAndFlush(LoginPacket.charNameResponse(name,
                !MapleCharacterUtil.canCreateChar(name) || MapleLoginHelper.getInstance().isForbiddenName(name)));
    }

    @SuppressWarnings("unchecked")
    public static void CreateChar(ReadingMaple lea, MapleClient c) { //Modulation
        String name;
        byte gender, skin;
        short subcategory;
        int face, hair, hairColor = -1, hat = -1, top, bottom = -1, shoes, weapon, cape = -1, faceMark = -1, shield = -1;
        name = lea.readMapleAsciiString();

        if (!MapleCharacterUtil.canCreateChar(name)) {
            System.out.println("char name hack: " + name);
            return;
        }
        lea.skip(4);
        lea.skip(4);
        int job_type = lea.readInt();
        JobType job = JobType.getByType(job_type);

        if (job == null) {
            System.out.println("New job type found: " + job_type);
            return;
        }
        subcategory = lea.readShort();
        gender = lea.readByte();
        skin = lea.readByte();
        lea.skip(1);
        face = lea.readInt();
        hair = lea.readInt();

        if (job.faceMark) {
            faceMark = lea.readInt();
        }
        if (job.hat) {
            hat = lea.readInt();
        }
        top = lea.readInt();

        if (job.bottom) {
            bottom = lea.readInt();
        }

        if (job.cape) {
            cape = lea.readInt();
        }
        shoes = lea.readInt();
        weapon = lea.readInt();

        if (lea.available() >= 4) {
            shield = lea.readInt();
        }
        MapleCharacter newchar = MapleCharacter.getDefault(c);
        newchar.setFace(face);
        newchar.setSecondFace(face);
        newchar.setHair(hair);
        newchar.setGender(gender);
        newchar.setName(name);
        newchar.setSkinColor(skin);

        if (faceMark < 0) {
            faceMark = 0;
        }
        newchar.setSecondFace(faceMark);
        final ItemInformation ii = ItemInformation.getInstance();
        final MapleInventory equip = newchar.getInventory(MapleInventoryType.EQUIPPED);
        IItem item;
        int[][] equips = new int[][]{{hat, -1}, {top, -5}, {bottom, -6}, {cape, -9}, {shoes, -7}, {shield, -10}};
        if (job == JobType.PathFinder) {
            for (int[] i : equips) {
                if (i[0] > 0) {
                    item = ii.getEquipById(i[0]);
                    item.setPosition((byte) i[1]);
                    item.setGMLog("Character Creation Initiator");
                    equip.addFromDB(item);
                }
            }
        }
        else
        {
            int[][] equips2 = new int[][]{{hat, -1}, {top, -5}, {bottom, -6}, {cape, -9}, {shoes, -7}, {weapon, -11}, {shield, -10}};
            for (int[] i : equips2) {
                if (i[0] > 0) {
                    item = ii.getEquipById(i[0]);
                    item.setPosition((byte) i[1]);
                    item.setGMLog("Character Creation Initiator");
                    equip.addFromDB(item);
                }
            }
        }  
        if (job == JobType.Zero) 
        {
            System.out.println("Zero paid out (test)");
            //Sharpness shadow
            item = ii.getEquipById(1572000);
            item.setPosition((byte) -11);
            item.setGMLog("Character Creation Initial System");
            equip.addFromDB(item);
	}
        /*
		if (job == JobType.Kaiser) {
		    System.out.println("카이저 지급 완료 ");
		    item = ii.getEquipById(1352500);
		    item.setPosition((byte) -10);
		    item.setGMLog("Character Creation");
		    equip.addFromDB(item);
		}
         */
        newchar.setJob((short) job.id);
        int[][] guidebooks = new int[][]{{4161001, 0}, {4161047, 1}, {4161048, 2000}, {4161052, 2001}, {4161054, 3}, {4161079, 2002}};
        int guidebook = 0;
        for (int[] i : guidebooks) {
            if (newchar.getJob() == i[1]) {
                guidebook = i[0];
            } else if (newchar.getJob() / 1000 == i[1]) {
                guidebook = i[0];
            }
        }

        if (guidebook > 0) {
            newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(guidebook, (byte) 0, (short) 1, (byte) 0));
        }

        switch (job) {
            case Resistance: //resistance  
                newchar.changeSkillLevel(SkillFactory.getSkill(30001061), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(30001068), (byte) 1, (byte) 1);
                break;
            case Cygnus: //Cygnus
                newchar.changeSkillLevel(SkillFactory.getSkill(10001003), (byte) 1, (byte) 1); // 장인의 혼
                newchar.changeSkillLevel(SkillFactory.getSkill(10001244), (byte) 1, (byte) 1); // 엘리멘탈 슬래시
                newchar.changeSkillLevel(SkillFactory.getSkill(10001245), (byte) 1, (byte) 1); // 져니 홈
                newchar.changeSkillLevel(SkillFactory.getSkill(10000246), (byte) 1, (byte) 1); // 엘리멘탈 하모니
                newchar.changeSkillLevel(SkillFactory.getSkill(10000252), (byte) 1, (byte) 1); // 엘리멘탈 쉬프트
                break;
            case Aran: //아란
                newchar.changeSkillLevel(SkillFactory.getSkill(20001295), (byte) 1, (byte) 1); //컴뱃 스탭
                break;
            case Mercedes: //메르세데스
                newchar.changeSkillLevel(SkillFactory.getSkill(20020109), (byte) 1, (byte) 1); // 엘프의 회복
                newchar.changeSkillLevel(SkillFactory.getSkill(20021110), (byte) 1, (byte) 1); // 엘프의 축복
                newchar.changeSkillLevel(SkillFactory.getSkill(20020111), (byte) 1, (byte) 1); // 스타일리쉬 무브
                newchar.changeSkillLevel(SkillFactory.getSkill(20020112), (byte) 1, (byte) 1); // 왕의 자격
                break;
            case Demon: //데몬
                newchar.changeSkillLevel(SkillFactory.getSkill(30011109), (byte) 1, (byte) 1); // 데빌 윙즈
                newchar.changeSkillLevel(SkillFactory.getSkill(30010110), (byte) 1, (byte) 1); // 데몬 점프
                break;
            case Xenon: //제논
                newchar.changeSkillLevel(SkillFactory.getSkill(30020232), (byte) 1, (byte) 1); // 서플러스 서플라이
                newchar.changeSkillLevel(SkillFactory.getSkill(30020233), (byte) 1, (byte) 1); // 하이브리드 로직
                newchar.changeSkillLevel(SkillFactory.getSkill(30020234), (byte) 1, (byte) 1); // 멀티래터럴 I
                newchar.changeSkillLevel(SkillFactory.getSkill(30021235), (byte) 1, (byte) 1); // 프로멧사 어썰트
                newchar.changeSkillLevel(SkillFactory.getSkill(30021236), (byte) 1, (byte) 1); // 멀티 모드 링커
                newchar.changeSkillLevel(SkillFactory.getSkill(30021237), (byte) 1, (byte) 1); // 에비에이션 리버티
                newchar.changeSkillLevel(SkillFactory.getSkill(30020240), (byte) 1, (byte) 1); // 카모플라쥬
                break;
            case Phantom: // 팬텀
                newchar.changeSkillLevel(SkillFactory.getSkill(20031203), (byte) 1, (byte) 1); // 리턴 오브 팬텀
                newchar.changeSkillLevel(SkillFactory.getSkill(20030204), (byte) 1, (byte) 1); // 데들리 인스팅트
                newchar.changeSkillLevel(SkillFactory.getSkill(20031205), (byte) 1, (byte) 1); // 팬텀 슈라우드
                newchar.changeSkillLevel(SkillFactory.getSkill(20030206), (byte) 1, (byte) 1); // 하이 덱스터러티
                newchar.changeSkillLevel(SkillFactory.getSkill(20031207), (byte) 1, (byte) 1); // 스틸 스킬
                newchar.changeSkillLevel(SkillFactory.getSkill(20031208), (byte) 1, (byte) 1); // 스킬 매니지먼트
                newchar.changeSkillLevel(SkillFactory.getSkill(20031209), (byte) 1, (byte) 1); // 저지먼트
                newchar.changeSkillLevel(SkillFactory.getSkill(20031260), (byte) 1, (byte) 1); // 저지먼트 AUTO / MANUAL
                break;
            case Mihile: // 미하일
                newchar.changeSkillLevel(SkillFactory.getSkill(50001214), (byte) 1, (byte) 1); // 빛의 수호
                break;
            case Luminous: // 루미너스
                newchar.changeSkillLevel(SkillFactory.getSkill(20040219), (byte) 1, (byte) 1); // 이퀄리브리엄
                newchar.changeSkillLevel(SkillFactory.getSkill(20040216), (byte) 1, (byte) 1); // 선파이어
                newchar.changeSkillLevel(SkillFactory.getSkill(20040217), (byte) 1, (byte) 1); // 이클립스
                newchar.changeSkillLevel(SkillFactory.getSkill(20040218), (byte) 1, (byte) 1); // 퍼미에이트
                newchar.changeSkillLevel(SkillFactory.getSkill(20040221), (byte) 1, (byte) 1); // 파워오브라이트
                newchar.changeSkillLevel(SkillFactory.getSkill(20041222), (byte) 1, (byte) 1); // 라이트 블링크
                break;
            case Kaiser: // 카이저
                newchar.changeSkillLevel(SkillFactory.getSkill(60001216), (byte) 1, (byte) 1); // 리셔플 스위치 : 방어모드
                newchar.changeSkillLevel(SkillFactory.getSkill(60001217), (byte) 1, (byte) 1); // 리셔플 스위치 : 공격모드
                newchar.changeSkillLevel(SkillFactory.getSkill(60001218), (byte) 1, (byte) 1); // 바티컬커넥트
                newchar.changeSkillLevel(SkillFactory.getSkill(60001219), (byte) 1, (byte) 1); // 아이언 윌
                newchar.changeSkillLevel(SkillFactory.getSkill(60001220), (byte) 1, (byte) 1); // 트랜스피규레이션
                newchar.changeSkillLevel(SkillFactory.getSkill(60001225), (byte) 1, (byte) 1); // 커맨드
                break;
            case AngelicBuster: //엔젤릭버스터
                newchar.setSecondFace(21173);
                newchar.setSecondHair(37141);
                newchar.changeSkillLevel(SkillFactory.getSkill(60011216), (byte) 1, (byte) 1); // 석세서
                newchar.changeSkillLevel(SkillFactory.getSkill(60011218), (byte) 1, (byte) 1); // 매지컬 리프트
                newchar.changeSkillLevel(SkillFactory.getSkill(60011219), (byte) 1, (byte) 1); // 소울 컨트랙트
                newchar.changeSkillLevel(SkillFactory.getSkill(60011220), (byte) 1, (byte) 1); // 데이드림
                newchar.changeSkillLevel(SkillFactory.getSkill(60011221), (byte) 1, (byte) 1); // 코디네이트
                newchar.changeSkillLevel(SkillFactory.getSkill(60011222), (byte) 1, (byte) 1); // 드레스 업
                break;
            case Zero: //제로
                newchar.changeSkillLevel(SkillFactory.getSkill(100001262), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100000282), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100001263), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100001264), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100001265), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100001266), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100000267), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100001268), (byte) 1, (byte) 1);
                newchar.changeSkillLevel(SkillFactory.getSkill(100000279), (byte) 5, (byte) 5);
                newchar.setGender((byte) 1);
                newchar.setSecondGender((byte) 0);
                newchar.setSecondSkinColor(skin);
                newchar.setSecondFace(21290);
                newchar.setSecondHair(37623);
                newchar.setLevel((short) 100);
                newchar.getStat().str = 518;
                newchar.getStat().maxhp = 6910;
                newchar.getStat().hp = 6910;
                newchar.getStat().maxmp = 100;
                newchar.getStat().mp = 100;
                newchar.setRemainingSp(3, 0);
                newchar.setRemainingSp(3, 1);
                break;
            case Kadena: //카데나
                newchar.changeSkillLevel(SkillFactory.getSkill(60020218), (byte) 1, (byte) 1);
                break;
            default:
                break;
        }
        newchar.setSubcategory(subcategory);
        if (MapleCharacterUtil.canCreateChar(name) && !MapleLoginHelper.getInstance().isForbiddenName(name)) {
            for (MapleInventory inv : newchar.getInventorys()) {
                for (IItem t : inv.list()) {
                    if (!Start.createChar.contains(String.valueOf(t.getItemId()))) {
                        if (t.getItemId() != 1352500) {
                            c.getSession().close();
                            return;
                        }
                    }
                }
            }
            MapleCharacter.saveNewCharToDB(newchar);
            MapleItempotMain.getInstance().newCharDB(newchar.getId());
            c.getSession().writeAndFlush(LoginPacket.addNewCharacterEntry(newchar, true));
            c.createdChar(newchar.getId());
        } else {
            c.getSession().writeAndFlush(LoginPacket.addNewCharacterEntry(newchar, false));
        }
    }

    public static void DeleteChar(ReadingMaple rh, MapleClient c) {
        String Secondpw_Client = rh.readMapleAsciiString();
        int Character_ID = rh.readInt();
        if (!c.login_Auth(Character_ID)) {
            c.getSession().close();
            return; // Attempting to delete other character
        }
        byte state = 0;
        if (Secondpw_Client == null) { // Client's hacking
            c.getSession().close();
            return;
        } else if (!c.CheckSecondPassword(Secondpw_Client)) { // Wrong Password
            state = 0x14;
        }
        if (state == 0) {
            if (!c.deleteCharacter(Character_ID)) {
                state = 1; // actually something else would be good o.o
            }
        }
        c.getSession().writeAndFlush(LoginPacket.deleteCharResponse(Character_ID, state));
    }

    public static void Character_WithSecondPassword(ReadingMaple rh, MapleClient c) {
        int key = rh.readInt();
        int len = rh.readShort();
        int cal = 5 - key;
        char[] arr = new char[len];
        byte value;
        for (int i = 0; i < len; i++) {
            value = (byte) rh.readByte();
            int tmp = (int) value & 0xFF;
            if (cal > 0)
                value = (byte) (((tmp << (cal % 8)) & 0xFF) | ((tmp << (cal % 8)) >> 8));
            else if (cal < 0)
                value = (byte) ((((tmp << 8) >>> (-cal % 8)) & 0xFF) | (((tmp << 8) >>> (-cal % 8)) >>> 8));
            if (value < 0)
                value += 256;
            arr[i] = (char) value;
        }
        
        String password = String.valueOf(arr);
        int charId = rh.readInt();

        if (loginFailCount(c) || c.getSecondPassword() == null || !c.login_Auth(charId)) { // This should not happen unless player is hacking
            c.getSession().close();
            return;
        }
        if (c.CheckSecondPassword(password)) {
            if (c.getIdleTask() != null) {
                c.getIdleTask().cancel(true);
            }
            c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, c.getSessionIPAddress());
            c.getSession().writeAndFlush(MainPacketCreator.getServerIP(c, ServerConstants.ChannelPort + c.getChannel(),
                    ServerConstants.BuddyChatPort, charId));
        } else {
            c.getSession().writeAndFlush(LoginPacket.faildSp());
        }
    }

    public static void updateCharCard(ReadingMaple rh, MapleClient c) {
        if (!c.isLoggedIn()) {
            c.getSession().close();
            return;
        }
        Map<Integer, Integer> cid = new LinkedHashMap<Integer, Integer>();

        for (int i = 1; i <= 6; i++) {
            int charid = rh.readInt();
            cid.put(i, charid);
        }
    }

    public static void changeSeccondPassword(ReadingMaple rh, MapleClient c) {
        String before = rh.readMapleAsciiString();
        String after = rh.readMapleAsciiString();
        if (!c.CheckSecondPassword(before)) {
            c.sendPacket(LoginPacket.faildSp());
        } else {
            c.setSecondPassword(after);
            c.sendPacket(LoginPacket.sucessSP());
        }
    }

    public static void registerSP(ReadingMaple rh, MapleClient c) {
        String before = rh.readMapleAsciiString();
        c.setSecondPassword(before);
        c.sendPacket(LoginPacket.sucessSP());
    }
}
