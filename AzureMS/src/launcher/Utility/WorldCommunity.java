/*
 * 테스피아 Project
 * ==================================
 * 팬더 spirit_m@nate.com
 * 백호 softwarewithcreative@nate.com
 * ==================================
 * 
 */

package launcher.Utility;

import client.Character.MapleCharacter;
import client.Community.MapleBuddy.BuddyList;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import client.Community.MapleParty.MaplePartyOperation;
import static client.Community.MapleParty.MaplePartyOperation.CHANGE_LEADER;
import static client.Community.MapleParty.MaplePartyOperation.DISBAND;
import static client.Community.MapleParty.MaplePartyOperation.JOIN;
import static client.Community.MapleParty.MaplePartyOperation.LEAVE;
import static client.Community.MapleParty.MaplePartyOperation.LOG_ONOFF;
import static client.Community.MapleParty.MaplePartyOperation.SILENT_UPDATE;
import connections.Packets.MainPacketCreator;
import handlers.ChatHandler.MapleMultiChat;
import handlers.ChatHandler.MapleMultiChatCharacter;
import java.util.ArrayList;
import java.util.List;
import launcher.LauncherHandlers.MaplePlayerIdChannelPair;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.MaplePlayerHolder;
import launcher.Utility.MapleHolders.WideObjectHolder;
import tools.Timer;

/** 서버 전체에 커뮤니티 (메신저, 친구, 파티 등) 기능을 제공하는 함수들.
 *
 * @author 티썬
 *
 * since 2012. 2. 24
 * @since Revision 33
 */
public class WorldCommunity {
    
    /** 메신저 방 만들기 - 월드 홀더에 메신저를 하나 생성함.
     * 
     * @param <mapleMultiChatCharacter> 메신저캐릭터
     * @return 메신저클래스
     * @since Revision 33
     */
    public static boolean 사피 = false;
    private static java.util.Timer 사피타임 = null;
    public static boolean isFreeze = false;
    
    public static MapleMultiChat createMessenger(MapleMultiChatCharacter chrfor) {
	return WideObjectHolder.getInstance().createMessenger(chrfor);
    }
    
    /** 메신저 방 얻어냄 - 월드 홀더에서 메신저를 얻어냄.
     * 
     * @param <int> 메신저ID
     * @return 메신저클래스
     * @since Revision 33
     */

    public static MapleMultiChat getMessenger(int messengerid) {
	return WideObjectHolder.getInstance().getMessenger(messengerid);
    }


    /** 메신저 방 떠남 - 월드 홀더에서 메신저를 얻어내서 메신저캐릭터 삭제.
     * 
     * @param <int> 메신저ID
     * @param <mapleMultiChatCharacter> 메신저캐릭터
     * @since Revision 33
     */
    public static void leaveMessenger(int messengerid, MapleMultiChatCharacter target) {
	final MapleMultiChat messenger = WideObjectHolder.getInstance().getMessenger(messengerid);
	if (messenger == null) {
	    throw new IllegalArgumentException("No messenger with the specified messengerid exists");
	}
	final int position = messenger.getPositionByName(target.getName());
	messenger.removeMember(target);
	for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleMultiChatCharacter messengerchar : messenger.getMembers()) {
                    final MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(messengerchar.getName());
                    if (chr != null) {
                        chr.getClient().getSession().writeAndFlush(MainPacketCreator.removeMessengerPlayer(position));
                    }
            }
	}
    }

    /** 메신저 방에 참여 - 기존 메신저 참여자들에게 패킷도 방송함.
     * 
     * @param <int> 메신저 ID
     * @param <mapleMultiChatCharacter> 메신저캐릭터
     * @param <String> 참여자 이름
     * @param <int> 참여자 채널
     * @since Revision 33
     */
    public static void joinMessenger(int messengerid, MapleMultiChatCharacter newchar, String newcharname, int newcharchannel) {
	final MapleMultiChat messenger = WideObjectHolder.getInstance().getMessenger(messengerid);
	if (messenger == null) {
	    throw new IllegalArgumentException("No messenger with the specified messengerid exists");
	}
	
	    for (MapleMultiChatCharacter oldmessengerchar : messenger.getMembers()) {
                if (!(oldmessengerchar.getName().equals(newcharname))) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        final MapleCharacter oldplayer = cserv.getPlayerStorage().getCharacterByName(oldmessengerchar.getName());
                        if (oldplayer != null) {
                            final MapleCharacter newplayer = ChannelServer.getInstance(newcharchannel).getPlayerStorage().getCharacterByName(newcharname);
                            oldplayer.getClient().getSession().writeAndFlush(MainPacketCreator.addMessengerPlayer(newcharname, newplayer, newchar.getPosition(), newcharchannel));
                            newplayer.getClient().getSession().writeAndFlush(MainPacketCreator.addMessengerPlayer(oldplayer.getName(), oldplayer, oldmessengerchar.getPosition(), oldmessengerchar.getChannel()));
                        }
                    }
                } else {
                    ChannelServer.getInstance(newcharchannel).getPlayerStorage().getCharacterByName(newcharname).getClient().send(MainPacketCreator.joinMessenger(newchar.getPosition()));
                }
            }
            
            
    }
	
    

    /** 메신저 채팅 - 패킷방송
     * 
     * @param <int> 메신저 ID
     * @param <String> 채팅대화
     * @param <String> 송신자 이름
     * @since Revision 33
     */
    public static void messengerChat(int messengerid, String chattext, String namefrom) {
	final MapleMultiChat messenger = WideObjectHolder.getInstance().getMessenger(messengerid);
	if (messenger == null) {
	    throw new IllegalArgumentException("No messenger with the specified messengerid exists");
	}
	for (ChannelServer server : ChannelServer.getAllInstances()) {
	    for (MapleMultiChatCharacter messengerchar : messenger.getMembers()) {
                if (messengerchar.getChannel() == server.getChannel() && !(messengerchar.getName().equals(namefrom))) {
                    final MapleCharacter chr = server.getPlayerStorage().getCharacterByName(messengerchar.getName());
                    if (chr != null) {
                        chr.getClient().getSession().writeAndFlush(MainPacketCreator.messengerChat(namefrom, chattext));
                    }
                }
            }
	}
    }

    /** 메신저 초대 거부
     * 
     * @param <String> 초대했던사람
     * @param <String> 거부한사람
     * @since Revision 33
     */
    public static void declineChat(String target, String namefrom) {
	    if (WorldConnected.isConnected(target)) {
                final MapleCharacter chr = ChannelServer.getInstance(WorldConnected.find(target)).getPlayerStorage().getCharacterByName(target);
                final MapleMultiChat messenger = chr.getMessenger();
                if (messenger != null) {
                    chr.getClient().getSession().writeAndFlush(MainPacketCreator.messengerNote(namefrom, 5, 0));
                }
            }
	
    }
 
    /** 메신저 업데이트 (룩 등이 바뀌었을때)
     * 
     * @param <int> 메신저ID
     * @param <String> 업데이트된 사람의 이름
     * @param <int> 업데이트된 사람의 현재 채널 
     * @since Revision 33
     */
    public static void updateMessenger(int messengerid, String namefrom, int fromchannel) {
	final MapleMultiChat messenger = WideObjectHolder.getInstance().getMessenger(messengerid);
	final int position = messenger.getPositionByName(namefrom);

	for (ChannelServer server : ChannelServer.getAllInstances()) {
	    for (MapleMultiChatCharacter messengerchar : messenger.getMembers()) {
                if (messengerchar.getChannel() == server.getChannel() && !(messengerchar.getName().equals(namefrom))) {
                    final MapleCharacter chr = server.getPlayerStorage().getCharacterByName(messengerchar.getName());
                    if (chr != null) {
                        MapleCharacter from = ChannelServer.getInstance(fromchannel).getPlayerStorage().getCharacterByName(namefrom);
                        chr.getClient().getSession().writeAndFlush(MainPacketCreator.updateMessengerPlayer(namefrom, from, position, fromchannel));
                    }
                }
            }
	}
    }

    /** 패킷을 방송하지 않고 메신저에서 플레이어 삭제
     * 
     * @param <int> 메신저ID
     * @param <mapleMultiChatCharacter> 메신저캐릭터
     * @since Revision 33
     * 
     */
    public static void silentLeaveMessenger(int messengerid, MapleMultiChatCharacter target) {
	final MapleMultiChat messenger = WideObjectHolder.getInstance().getMessenger(messengerid);
	if (messenger == null) {
	    throw new IllegalArgumentException("No messenger with the specified messengerid exists");
	}
	messenger.silentRemoveMember(target);
    }

    
    /** 패킷을 방송하지 않고 메신저에 플레이어 참여
     * 
     * @param <int> 메신저ID
     * @param <mapleMUltiChatCharacter> 참여할 플레이어
     * @param <int> 참여되는 위치 (슬롯)
     * @since Revision 33
     */
    public static void silentJoinMessenger(int messengerid, MapleMultiChatCharacter target, int position) {
	final MapleMultiChat messenger = WideObjectHolder.getInstance().getMessenger(messengerid);
	if (messenger == null) {
	    throw new IllegalArgumentException("No messenger with the specified messengerid exists");
	}
	messenger.silentAddMember(target, position);
    }


    /** 친구 채팅 패킷방송
     * 
     * @param <int[]> 수신되는 캐릭터 목록
     * @param <int> 송신자 캐릭터 ID
     * @param <String> 송신자 캐릭터명
     * @param <String> 채팅 데이터
     * @since Revision 33
     */
    public static void buddyChat(int[] recipientCharacterIds, int cidFrom, String nameFrom, String chattext) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            final MaplePlayerHolder playerStorage = cserv.getPlayerStorage();
            for (int characterId : recipientCharacterIds) {
                final MapleCharacter chr = playerStorage.getCharacterById(characterId);
                if (chr != null) {
                    if (chr.getBuddylist().containsVisible(cidFrom)) {
                        chr.getClient().getSession().writeAndFlush(MainPacketCreator.multiChat(nameFrom, chattext, 0));
                    }
                }
            }
        }
    }

    /** 접속중인 친구를 얻어옴.
     * 
     * @param <int> 요청자ID
     * @param <String> 대상자ID
     * @return 플레이어아이디, 채널 페어 클래스 배열
     * @since Revision 33
     */
    public static MaplePlayerIdChannelPair[] multiBuddyFind(int charIdFrom, int[] characterIds) {
	List<MaplePlayerIdChannelPair> foundsChars = new ArrayList<MaplePlayerIdChannelPair>(characterIds.length);
	for (ChannelServer cserv : ChannelServer.getAllInstances()) {
	    for (int charid : cserv.multiBuddyFindMain(charIdFrom, characterIds)) {
		foundsChars.add(new MaplePlayerIdChannelPair(charid, cserv.getChannel()));
	    }
	}
	return foundsChars.toArray(new MaplePlayerIdChannelPair[foundsChars.size()]);
    }

    /** 친구 추가 요청
     * 
     * @param <String> 추가이름
     * @param <int> 요청자 채널
     * @param <int> 요청자 ID
     * @param <String> 요청자 이름
     * @param <int> 요청자 레벨
     * @param <int> 요청자 직업
     * @return 친구 추가 결과 BuddyList.BuddyAddResult Enum값
     * @since Revision 33
     */
    public static BuddyList.BuddyAddResult requestBuddyAdd(String addName, int channelFrom, int cidFrom, String nameFrom, int levelFrom, int jobFrom, String groupName) {
        for (ChannelServer server : ChannelServer.getAllInstances()) {
            final MapleCharacter addChar = server.getPlayerStorage().getCharacterByName(addName);
            if (addChar != null) {
                final BuddyList buddylist = addChar.getBuddylist();
                if (buddylist.isFull()) {
                    return BuddyList.BuddyAddResult.BUDDYLIST_FULL;
                }
                if (!buddylist.contains(cidFrom)) {
                    buddylist.addBuddyRequest(addChar.getClient(), cidFrom, nameFrom, channelFrom, levelFrom, jobFrom, groupName);
                } else {
                    if (buddylist.containsVisible(cidFrom)) {
                        return BuddyList.BuddyAddResult.ALREADY_ON_LIST;
                    }
                }
            }
        }
	return BuddyList.BuddyAddResult.OK;
    }
    
    /** 파티 얻어냄 - 월드 홀더에서 해당파티ID의 파티를 얻어냄.
     * 
     * @param <int> 얻어낼 파티 ID
     * @return 월드홀더에서 얻어낸 파티
     * @since Revision 33
     */
    public static MapleParty getParty(int partyid) {
        return WideObjectHolder.getInstance().getParty(partyid);
    }
    
    /** 파티가 업데이트 될때 패킷 송출
     * 
     * @param <int> 파티ID
     * @param <maplePartyOperation> 파티업데이트 작업
     * @param <maplePartyCharacter> 업데이트되는 대상
     */
    //TODO only notify channels where partymembers are?
    public static void updateParty(int partyid, MaplePartyOperation operation, MaplePartyCharacter target) {
	final MapleParty party = WideObjectHolder.getInstance().getParty(partyid);
	if (party == null) {
	    throw new IllegalArgumentException("no party with the specified partyid exists");
	}
	switch (operation) {
	    case JOIN:
		party.addMember(target);
		break;
	    case EXPEL:
	    case LEAVE:
		party.removeMember(target);
		break;
	    case DISBAND:
		WideObjectHolder.getInstance().disbandParty(partyid);
		break;
	    case SILENT_UPDATE:
	    case LOG_ONOFF:
		party.updateMember(target);
		break;
	    case CHANGE_LEADER:
		party.setLeader(target);
		break;
	    default:
		throw new RuntimeException("Unhandeled updateParty operation " + operation.name());
	}
	WideObjectHolder.getInstance().updateParty(party, operation, target);
    }

    /** 파티 생성 - 월드홀더에 새 파티를 기억시킴.
     * 
     * @param <maplePartyCharacter> 파티장 파티캐릭터
     * @return 파티클래스
     * @since Revision 33
     */
    public static MapleParty createParty(MaplePartyCharacter chrfor) {
	return WideObjectHolder.getInstance().createParty(chrfor);
    }

    /** 연합 채팅
     * 
     * @param <int> 길드아이디
     * @param <String> 송출자 이름
     * @param <int> 송출자 캐릭터ID
     * @param <String> 채팅 데이터
     * @since Revision 33
     */
    public static void allianceChat(int gid, String name, int cid, String msg) {
	WideObjectHolder.getInstance().allianceChat(gid, name, cid, msg);
    }

    /** 파티 채팅
     * 
     * @param <mapleParty> 채팅을 송출할 파티
     * @param <String> 채팅 데이터
     * @param <String> 송출자 캐릭터명
     * @since Revision 33
     */
    public static void partyChat(MapleParty party, String chattext, String namefrom) {
        for (ChannelServer server : ChannelServer.getAllInstances())
	for (MaplePartyCharacter partychar : party.getMembers()) {
	    if (partychar.getChannel() == server.getChannel() && !(partychar.getName().equals(namefrom))) {
		final MapleCharacter chr = server.getPlayerStorage().getCharacterByName(partychar.getName());
		if (chr != null) {
		    chr.getClient().getSession().writeAndFlush(MainPacketCreator.multiChat(namefrom, chattext, 1));
		}
	    }
	}
    }
    
    public static boolean 사피제한 = false;

    public static void 사피() {
        사피 = true;
        사피제한 = true;
        사피캔슬();
        WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(11, "Experience event started! If you don't go hunting fast, you'll regret it.?"));
    }

    public static void 사피캔슬() {
        Timer.EtcTimer tMan1 = Timer.EtcTimer.getInstance();
        tMan1.schedule(new Runnable() {
            @Override
            public void run() {
                WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(11, "I'm sorry, but the event is over ... See you next time.~"));
                사피제한();
                사피 = false;
            }
        }, 1800000);
    }

    public static void 사피제한() {
        Timer.EtcTimer tMan1 = Timer.EtcTimer.getInstance();
        tMan1.schedule(new Runnable() {
            @Override
            public void run() {
                사피제한 = false;
            }
        }, 1000 * 30 * 15);
    }
}
