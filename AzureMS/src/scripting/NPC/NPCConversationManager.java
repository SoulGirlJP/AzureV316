package scripting.NPC;

import client.BingoGame;
import client.Character.MapleCharacter;
import client.Community.MapleGuild.MapleGuild;
import client.Community.MapleGuild.MapleGuildRanking;
import client.Community.MapleGuild.MapleSquadLegacy;
import client.Community.MapleParty.MaplePartyCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.Item;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Invocable;

import client.MapleCarnivalParty;
import client.MapleClient;
import client.MapleKeyBinding;
import client.ItemInventory.PetsMounts.MaplePet;
import client.ItemInventory.StructPotentialItem;
import client.MapleQuestStatus;
import client.Skills.ISkill;
import client.Skills.InnerAbillity;
import client.Skills.InnerSkillValueHolder;
import client.Skills.SkillEntry;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import client.Stats.PlayerStatList;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.WritingPacket;
import connections.Packets.SLFCGPacket;
import connections.Packets.SkillPackets.ZeroSkill;
import connections.Packets.UIPacket;
import constants.GameConstants;
import handlers.GlobalHandler.InterServerHandler;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;
import static handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler.potential;
import provider.MapleData;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.MapleSlideMenu;
import server.MapleSlideMenu.SlideMenu0;
import server.MapleSlideMenu.SlideMenu1;
import server.MapleSlideMenu.SlideMenu2;
import server.MapleSlideMenu.SlideMenu3;
import server.MapleSlideMenu.SlideMenu4;
import server.MapleSlideMenu.SlideMenu5;
import provider.Named;
import tools.HexTool;
import tools.Pair;
import tools.StringUtil;
import tools.Timer;
import tools.Timer.EtcTimer;
import tools.Timer.MapTimer;
import tools.Triple;
import tools.RandomStream.Randomizer;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.WideObjectHolder;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;
import static launcher.Utility.WorldCommunity.사피;
import launcher.Utility.WorldConnected;
import scripting.AbstractPlayerInteraction;
import scripting.EventManager.EventInstanceManager;
import server.Events.MaplePyramidSubway;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.Items.MapleItemIdenfier;
import server.Items.MapleRing;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonsterProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterDropEntry;
import server.LifeEntity.MobEntity.MonsterEntity.OverrideMonsterStats;
import server.LifeEntity.NpcEntity.MapleNPC;
import server.LifeEntity.NpcEntity.MapleNPCStats;
import server.Maps.MapleCarnivalChallenge;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleWorldMap.MapleWorldMapProvider;
import server.Quests.MapleQuest;
import server.Shops.MapleShopFactory;
import tools.CurrentTime;

public class NPCConversationManager extends AbstractPlayerInteraction {

    private String getText;
    private final byte type; // -1 = NPC, 0 = start quest, 1 = end quest
    private byte lastMsg = -1;
    public boolean pendingDisposal = false;
    private final Invocable iv;

    public NPCConversationManager(MapleClient c, int npc, int questid, String npcscript, byte type, Invocable iv) {
        super(c, npc, questid, npcscript);
        this.type = type;
        this.iv = iv;
    }

    public boolean is사피() {
        return 사피;
    }

    public void 사피() {
        WorldCommunity.사피();
    }

    public void 사피끝() {
        WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(11, "여러분 아쉽지만 이벤트가 끝나 버렸어요... 다음에 또 만나요~"));
        사피 = false;
    }

	public Invocable getIv()
	{
		return iv;
	}

	public int getNpc()
	{
		return id;
	}

	public int getQuest()
	{
		return id2;
	}

	public String getScript()
	{
		return script;
	}

	public byte getType()
	{
		return type;
	}

	public void safeDispose()
	{
		pendingDisposal = true;
	}

	public void sendSlideMenu(final int type, final String sel)
	{
		if (lastMsg > -1)
		{
			return;
		}
		int lasticon = 0;
		// if (type == 0 && sel.contains("#")) {
		// String splitted[] = sel.split("#");
		// lasticon = Integer.parseInt(splitted[splitted.length - 2]);
		// if (lasticon < 0) {
		// lasticon = 0;
		// }
		// }
		c.getSession().writeAndFlush(MainPacketCreator.getSlideMenu(id, type, lasticon, sel));
		lastMsg = 0x12;// was12
	}

	public String getDimensionalMirror(MapleCharacter character)
	{
		return MapleSlideMenu.SlideMenu0.getSelectionInfo(character, id);
	}
        
        public void ResetInnerPot()
	{
		// int itemid = slea.readInt();
		// short slot = (short) slea.readInt();
		// Item item = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);
		List<InnerSkillValueHolder> newValues = new LinkedList<InnerSkillValueHolder>();

		for (InnerSkillValueHolder isvh : c.getPlayer().getInnerSkills())
		{
			newValues.add(InnerAbillity.getInstance().renewSkill(isvh.getRank(), 2702000, true));
		}

		c.getPlayer().getInnerSkills().clear();
		for (InnerSkillValueHolder isvh : newValues)
		{
			c.getPlayer().getInnerSkills().add(isvh);
		}

		// c.getPlayer().getInventory(MapleInventoryType.USE).removeItem(slot, (short)
		// 1, false);
		int j = 0;
		for (InnerSkillValueHolder i : newValues)
		{
			j++;
			c.sendPacket(MainPacketCreator.updateInnerAbility(i, j, j == 3));
		}
                
		c.getPlayer().dropMessage(5, "Inner Potential has been reconfigured.");
	}

	public String getSlideMenuSelection(int type)
	{
		switch (type)
		{
		case 0:
			return SlideMenu0.getSelectionInfo(getPlayer(), id);
		case 1:
			return SlideMenu1.getSelectionInfo(getPlayer(), id);
		case 2:
			return SlideMenu2.getSelectionInfo(getPlayer(), id);
		case 3:
			return SlideMenu3.getSelectionInfo(getPlayer(), id);
		case 4:
			return SlideMenu4.getSelectionInfo(getPlayer(), id);
		case 5:
			return SlideMenu5.getSelectionInfo(getPlayer(), id);
		default:
			return SlideMenu0.getSelectionInfo(getPlayer(), id);
		}
	}

	public int getSlideMenuDataInteger(int type)
	{
		switch (type)
		{
		case 0:
			return SlideMenu0.getDataInteger(type);
		case 1:
			return SlideMenu1.getDataInteger(type);
		case 2:
			return SlideMenu2.getDataInteger(type);
		case 3:
			return SlideMenu3.getDataInteger(type);
		case 4:
			return SlideMenu4.getDataInteger(type);
		case 5:
			return SlideMenu5.getDataInteger(type);
		default:
			return SlideMenu0.getDataInteger(type);
		}
	}

	// public String getSlideMenuSelection(int type) {
	// try {
	// Class<?> slideMenu = (Class<?>)
	// MapleSlideMenu.getSlideMenu(type).newInstance();
	// try {
	// return (String) slideMenu.getClass().getMethod("getSelectionInfo",
	// MapleCharacter.class, int.class).invoke(slideMenu, c.getPlayer(), id);
	// } catch (NoSuchMethodException | SecurityException |
	// IllegalAccessException | IllegalArgumentException |
	// InvocationTargetException ex) {
	// return "";
	// }
	// } catch (InstantiationException | IllegalAccessException ex) {
	// return "";
	// }
	// }
	//
	// public int getSlideMenuDataInteger(int type) {
	// try {
	// Class<?> slideMenu = (Class<?>)
	// MapleSlideMenu.getSlideMenu(type).newInstance();
	// try {
	// return (int) slideMenu.getClass().getMethod("getDataInteger",
	// int.class).invoke(slideMenu, type);
	// } catch (NoSuchMethodException | SecurityException |
	// IllegalAccessException | IllegalArgumentException |
	// InvocationTargetException ex) {
	// return 0;
	// }
	// } catch (InstantiationException | IllegalAccessException ex) {
	// return 0;
	// }
	// }
	public void sendNext(String text)
	{
		sendNext(text, id);
	}

	public void sendNext(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // sendNext will dc otherwise!
			sendSimple(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setText(text);
		t.setNext(true);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void fieldEffect(int delay)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				getClient().sendPacket(MainPacketCreator.fieldEffect("12 02 F4 01 00 00 01 00 30 00"));
				getClient().sendPacket(MainPacketCreator.fieldEffect("12 02 F4 01 00 00 01 00 31 00"));
				getClient().sendPacket(MainPacketCreator.fieldEffect("12 02 F4 01 00 00 01 00 32 00"));
				getClient().sendPacket(MainPacketCreator.fieldEffect("10 01 FF 00 00 00 00 00 00 00 00 00 00 00"));
				getClient().sendPacket(MainPacketCreator.fieldEffect("10 00 00 00 00 00 00 00 00 00 E8 03 00 00"));
			}
		}, delay);
	}

	public void sendPlayerToNpc(int delay, String text)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				sendPlayerToNpc(text);
			}
		}, delay);
	}
        
        public void BlockGameTimeMove(final int destination, final int movemap, final int time) {
            getPlayer().changeMap(movemap, 0);
            getClient().send(MainPacketCreator.getClock(time));
            Timer.CloneTimer tMan = Timer.CloneTimer.getInstance();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (getPlayer() != null) {
                        if (getMapId() == movemap) {
                            ExitBlockGame(destination);
                        }
                    }
                }
            };
            tMan.schedule(r, time * 1000);
        }

	public void StartBlockGame()
	{
		MapleClient c = getClient();
		c.getSession().writeAndFlush(SLFCGPacket.CharReLocationPacket(0xFFFF, 0));
		c.getSession().writeAndFlush(UIPacket.IntroDisableUI(true));
		c.getSession().writeAndFlush(UIPacket.IntroLock(true));
                // 여기까지 UI잠금 
		long startTime = System.currentTimeMillis();
		for (int count = 0;; count++)
		{
			long now = System.currentTimeMillis();
			if (now - startTime >= 2000)
			{
				break;
			}
		}
                // 여기까지 뭔지 모르겠음 의미없어보임
		c.getSession().writeAndFlush(SLFCGPacket.BlockGameCommandPacket(1));
		c.getSession().writeAndFlush(SLFCGPacket.BlockGameCommandPacket(2));
		c.getSession().writeAndFlush(SLFCGPacket.BlockGameControlPacket(100, 10));
	}
        
        public void ExitBlockGame(final int mapid) {
            getC().getSession().writeAndFlush(SLFCGPacket.BlockGameCommandPacket(3));
            getPlayer().setBlockCount(0);
            long startTime = System.currentTimeMillis();
            for (int count = 0;; count++) {
                long now = System.currentTimeMillis();
                if (now - startTime >= 3500) {
                    break;
                }
            }
            getC().getSession().writeAndFlush(UIPacket.IntroDisableUI(false));
            getC().getSession().writeAndFlush(UIPacket.IntroLock(false));
            ChannelServer cserv = getC().getChannelServer();
            MapleMap target = cserv.getMapFactory().getMap(mapid);
            getPlayer().changeMap(target, target.getPortal(0));
            getPlayer().gainItem(4310085, getPlayer().getBlockCoin());
            getPlayer().setBlockCoin(0);
        }

	public void sendPlayerToNpc(String text)
	{
		sendNextS(text, (byte) 3, id);
	}

	public void sendNextNoESC(String text)
	{
		sendNextS(text, (byte) 1, id);
	}

	public void sendNextNoESC(String text, int id)
	{
		sendNextS(text, (byte) 1, id);
	}

	public void sendNextS(String text, byte type)
	{
		sendNextS(text, type, id);
	}

	public void sendNextS(String text, byte param, int idd)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimpleS(text, param);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, (param == 1 && idd != id) ? idd : id, (byte) 0);
		t.setParam(param);
		t.setNpcIDD(idd);
		t.setText(text);
		t.setNext(true);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalk(id, (byte) 0, text, "00
		// 01", type, idd));
		lastMsg = 0;
	}

	public void sendPrev(String text)
	{
		sendPrev(text, id);
	}

	public void sendPrev(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setText(text);
		t.setPrev(true);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void sendPrevS(String text, byte type)
	{
		sendPrevS(text, type, id);
	}

	public void sendPrevS(String text, byte param, int idd)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimpleS(text, param);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setParam(param);
		t.setNpcIDD(idd);
		t.setText(text);
		t.setPrev(true);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void sendNextPrev(String text)
	{
		sendNextPrev(text, id);
	}

	public void sendNextPrev(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setText(text);
		t.setPrev(true);
		t.setNext(true);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void PlayerToNpc(String text)
	{
		sendNextPrevS(text, (byte) 3);
	}

	public void sendNextPrevS(String text)
	{
		sendNextPrevS(text, (byte) 3);
	}

	public void sendNextPrevS(String text, byte type)
	{
		sendNextPrevS(text, type, id);
	}

	public void sendNextPrevS(String text, byte type, int idd)
	{
		sendNextPrevS(text, type, idd, id);
	}

	public void sendNextPrevS(String text, byte param, int idd, int npcid)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimpleS(text, param);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setParam(param);
		t.setNpcIDD(idd);
		t.setText(text);
		t.setPrev(true);
		t.setNext(true);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void sendOk(String text)
	{
		sendOk(text, id);
	}

	public void sendOk(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}

		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setText(text);
                c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void sendOkS(String text, byte type)
	{
		sendOkS(text, type, id);
	}

	public void sendOkS(String text, byte param, int idd)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimpleS(text, param);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 0);
		t.setParam(param);
		t.setNpcIDD(idd);
		t.setText(text);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 0;
	}

	public void sendSelfTalk(String text)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimpleS(text, type);
			return;
		}
		c.getSession().writeAndFlush(MainPacketCreator.getSelfTalkText(text));
		lastMsg = 0;
	}

	public void sendYesNo(String text)
	{
		sendYesNo(text, id);
	}

	public void sendYesNo(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 3);
		t.setText(text);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 3;
	}

	public void sendYesNoS(String text, byte type)
	{
		sendYesNoS(text, type, id);
	}

	public void sendYesNoS(String text, byte param, int idd)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimpleS(text, param);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 3);
		t.setParam(param);
		t.setNpcIDD(idd);
		t.setText(text);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 3;
	}

	public void askMapSelection(final String sel)
	{
		if (lastMsg > -1)
		{
			return;
		}
		c.getSession().writeAndFlush(MainPacketCreator.getMapSelection(id, sel));
		lastMsg = ((byte) 0x13);
	}

	public void sendAcceptDecline(String text)
	{
		askAcceptDecline(text);
	}

	public void sendAcceptDeclineNoESC(String text)
	{
		askAcceptDeclineNoESC(text);
	}

	public void askAcceptDecline(String text)
	{
		askAcceptDecline(text, id);
	}

	public void askAcceptDecline(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		lastMsg = ((byte) 16);

		NPCTalk t = new NPCTalk((byte) 4, id, lastMsg);
		t.setText(text);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalk(id, (byte) lastMsg, text,
		// "", (byte) 0));
	}

	public void askAcceptDeclineNoESC(String text)
	{
		askAcceptDeclineNoESC(text, id);
	}

	public void askAcceptDeclineNoESC(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		lastMsg = (byte) 16;

		NPCTalk t = new NPCTalk((byte) 4, id, lastMsg);
		t.setText(text);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalk(id, (byte) lastMsg, text,
		// "", (byte) 1));
	}

	public void askAngelicBusterAvatar()
	{
		if (lastMsg > -1)
		{
			return;
		}
		lastMsg = 24;

		NPCTalk t = new NPCTalk((byte) 4, id, lastMsg);
		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
	}

	public int parseInt(double d)
	{
		return (int) d;
	}

	public int parseInt(String d)
	{
		return Integer.parseInt(d);
	}

	public void askAvatar(String text, Object... args)
	{
		if (lastMsg > -1)
		{
			return;
		}
		lastMsg = 10;
		NPCTalk t = new NPCTalk((byte) 4, id, lastMsg);
		t.setText(text);
		t.setArgs(args);
		if (GameConstants.isZero(c.getPlayer().getJob()))
		{
			t.setZeroBeta(c.getPlayer().getGender() == 1);
		}
		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalkStyle(id, text, args,
		// false));
	}

	public void sendSimple(String text)
	{
		sendSimple(text, id);
	}

	public void sendSimple(String text, int id)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (!text.contains("#L"))
		{ // sendSimple will dc otherwise!
			sendNext(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 6);
		getPlayer().lastNpcTalk = text;
		t.setText(text);
		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 6;
	}

	public void sendSimpleS(String text, byte type)
	{
		sendSimpleS(text, type, id);
	}

	public void sendSimpleS(String text, byte type, int idd)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (!text.contains("#L"))
		{ // sendSimple will dc otherwise!
			sendNextS(text, type);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, (type == 1 && idd != id) ? idd : id, (byte) 6);
		t.setNpcIDD(idd);
		t.setParam(type);
		t.setText(text);
		getPlayer().lastNpcTalk = text;

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 6;
	}

	public void sendStyle(String text, Object args[])
	{
		if (lastMsg > -1)
		{
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 10);
		t.setText(text);
		t.setArgs(args);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalkStyle(id, text, styles,
		// false));
		lastMsg = 10;
	}

	public void sendSecondStyle(String text, Object args[])
	{
		if (lastMsg > -1)
		{
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 10);
		t.setText(text);
		t.setArgs(args);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalkStyle(id, text, styles,
		// true));
		lastMsg = 10;
	}

	public void sendGetNumber(String text, int amount, int min, int max)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 5);
		t.setText(text);
		t.setAmount(amount);
		t.setMin(min);
		t.setMax(max);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		// c.getSession().writeAndFlush(NPCPacket.getNPCTalkNum(id, text, def, min,
		// max));
		lastMsg = 5;
	}

	public void sendGetText(String text)
	{
		sendGetText(id, text, "", 0, 0);
	}

	public void sendGetText(String text, String def, int min, int max)
	{
		sendGetText(id, text, def, min, max);
	}

	public void sendGetText(int id, String text, String def, int min, int max)
	{
		if (lastMsg > -1)
		{
			return;
		}
		if (text.contains("#L"))
		{ // will dc otherwise!
			sendSimple(text);
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 4);
		t.setText(text);
		t.setDef(def);
		t.setMin(min);
		t.setMax(max);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 4;
	}

	public void askCustomMixHairAndProb(String text)
	{
		c.getSession().writeAndFlush(
				MainPacketCreator.getNPCTalkMixStyle(id, text, GameConstants.isAngelicBuster(c.getPlayer().getJob())));
		lastMsg = 44;
	}

	public void setGetText(String text)
	{
		this.getText = text;
	}

	public String getText()
	{
		return getText;
	}

	public void setHair(int hair)
	{
		// if (hairExists(hair)) {
		getPlayer().setHair(hair);
		getPlayer().updateSingleStat(PlayerStatList.HAIR, hair);
		getPlayer().equipChanged();
		// }
	}

	public void setSecondHair(int hair)
	{
		// if (hairExists(hair)) {
		getPlayer().setSecondHair(hair);
		getPlayer().updateSingleStat(PlayerStatList.HAIR, hair);
		getPlayer().equipChanged();
		// }
	}

	public void setFace(int face)
	{
		// =if (faceExists(face)) {
		getPlayer().setFace(face);
		getPlayer().updateSingleStat(PlayerStatList.FACE, face);
		getPlayer().equipChanged();
		// }
	}

	public void setSecondFace(int face)
	{
		// if (faceExists(face)) {
		getPlayer().setSecondFace(face);
		getPlayer().updateSingleStat(PlayerStatList.FACE, face);
		getPlayer().equipChanged();
		// }
	}

	public void setSkin(int color)
	{
		getPlayer().setSkinColor((byte) color);
		getPlayer().updateSingleStat(PlayerStatList.SKIN, color);
		getPlayer().equipChanged();
	}

	public static boolean itemExists(int itemId)
	{
		ItemInformation ii = ItemInformation.getInstance();
		for (Pair<Integer, String> item : ii.getAllItems())
		{
			if (item.getLeft() == itemId)
			{
				return true;
			}
		}
		return false;
	}

	public int setRandomAvatar(int ticket, int... args_all)
	{
		if (!haveItem(ticket))
		{
			return -1;
		}
		gainItem(ticket, (short) -1);

		int args = args_all[Randomizer.nextInt(args_all.length)];
		if (args < 100)
		{
			c.getPlayer().setSkinColor((byte) args);
			c.getPlayer().updateSingleStat(PlayerStatList.SKIN, args);
		}
		else if (args < 30000)
		{
			c.getPlayer().setFace(args);
			c.getPlayer().updateSingleStat(PlayerStatList.FACE, args);
		}
		else
		{
			c.getPlayer().setHair(args);
			c.getPlayer().updateSingleStat(PlayerStatList.HAIR, args);
		}
		c.getPlayer().equipChanged();

		return 1;
	}

	public int setAvatar(int ticket, int args)
	{
		if (!haveItem(ticket))
		{
			return -1;
		}
		gainItem(ticket, (short) -1);

		if (args < 100)
		{
			c.getPlayer().setSkinColor((byte) args);
			c.getPlayer().updateSingleStat(PlayerStatList.SKIN, args);
		}
		else if (args < 30000)
		{
			c.getPlayer().setFace(args);
			c.getPlayer().updateSingleStat(PlayerStatList.FACE, args);
		}
		else
		{
			c.getPlayer().setHair(args);
			c.getPlayer().updateSingleStat(PlayerStatList.HAIR, args);
		}
		c.getPlayer().equipChanged();

		return 1;
	}

	public void sendStorage()
	{
		c.getPlayer().setConversation(4);
		// c.getPlayer().getStorage().sendStorage(c);
		c.getPlayer().getStorage().send2ndPWChecking(c, id, false);
	}

	public void openShop(int id)
	{
		MapleShopFactory.getInstance().getShop(id).sendShop(c);
	}

	public void changeJob(short job)
	{
		c.getPlayer().changeJob(job);
	}

	public void startQuest(int idd)
	{
		MapleQuest.getInstance(idd).start(getPlayer(), id);
	}

	public void completeQuest(int idd)
	{
		MapleQuest.getInstance(idd).complete(getPlayer(), id);
	}

	public void forfeitQuest(int idd)
	{
		MapleQuest.getInstance(idd).forfeit(getPlayer());
	}

	public void forceStartQuest()
	{
		MapleQuest.getInstance(id2).forceStart(getPlayer(), getNpc(), null);
	}

	@Override
	public void forceStartQuest(int idd)
	{
		MapleQuest.getInstance(idd).forceStart(getPlayer(), getNpc(), null);
	}

	public void forceStartQuest(String customData)
	{
		MapleQuest.getInstance(id2).forceStart(getPlayer(), getNpc(), customData);
	}

	public void forceCompleteQuest()
	{
		MapleQuest.getInstance(id2).forceComplete(getPlayer(), getNpc());
	}

	@Override
	public void forceCompleteQuest(final int idd)
	{
		MapleQuest.getInstance(idd).forceComplete(getPlayer(), getNpc());
	}

	public String getQuestCustomData()
	{
		return c.getPlayer().getQuestNAdd(MapleQuest.getInstance(id2)).getCustomData();
	}

	public String getQuestCustomData(int quest)
	{
		return c.getPlayer().getQuestNAdd(MapleQuest.getInstance(quest)).getCustomData();
	}

	public void setQuestCustomData(String customData)
	{
		getPlayer().getQuestNAdd(MapleQuest.getInstance(id2)).setCustomData(customData);
	}

	public long getMeso()
	{
		return getPlayer().getMeso();
	}

	public void gainAp(final int amount)
	{
		c.getPlayer().gainAp((short) amount);
	}

	public void expandInventory(byte type, int amt)
	{
		c.getPlayer().expandInventory(type, amt);
	}

	public void unequipEverything()
	{
		MapleInventory equipped = getPlayer().getInventory(MapleInventoryType.EQUIPPED);
		MapleInventory equip = getPlayer().getInventory(MapleInventoryType.EQUIP);
		List<Short> ids = new LinkedList<>();
		for (IItem item : equipped.newList())
		{
			ids.add(item.getPosition());
		}
		for (short itemid : ids)
		{
			InventoryManipulator.unequip(getC(), itemid, equip.getNextFreeSlot());
		}
	}

	public final void clearSkills()
	{
		final Map<ISkill, SkillEntry> skills = new HashMap<>(getPlayer().getSkills());
		final Map<ISkill, SkillEntry> newList = new HashMap<>();
		for (Entry<ISkill, SkillEntry> skill : skills.entrySet())
		{
			newList.put(skill.getKey(), new SkillEntry((byte) 0, (byte) 0, -1));
		}
		getPlayer().changeSkillsLevel(newList);
		newList.clear();
		skills.clear();
	}
	
    public int getRC() {
        return getPlayer().getRC();
    }
            
   public int getCSPoints() {
        return getPlayer().getCSPoints();
    }

    public void setRC(int rc) {
        getPlayer().gainRC(rc - getRC());
    }
    
    public void loseRC(int rc) {
        getPlayer().loseRC(rc);
    }
    
    public void gainRC(int rc) {
        if (rc < 0) {
            c.getPlayer().loseRC(rc);
        } else {
            c.getPlayer().gainRC(rc);
        }
        }  

	public boolean hasSkill(int skillid)
	{
		ISkill theSkill = SkillFactory.getSkill(skillid);
		if (theSkill != null)
		{
			return c.getPlayer().getSkillLevel(theSkill) > 0;
		}
		return false;
	}

	public void showEffect(boolean broadcast, String effect)
	{
		if (broadcast)
		{
			c.getPlayer().getMap().broadcastMessage(MainPacketCreator.showMapEffect(effect));
		}
		else
		{
			c.getSession().writeAndFlush(MainPacketCreator.showMapEffect(effect));
		}
	}

	public void playSound(boolean broadcast, String sound)
	{
		if (broadcast)
		{
			c.getPlayer().getMap().broadcastMessage(MainPacketCreator.playSound(sound));
		}
		else
		{
			c.getSession().writeAndFlush(MainPacketCreator.playSound(sound));
		}
	}

	public void environmentChange(boolean broadcast, String env)
	{
		if (broadcast)
		{
			c.getPlayer().getMap().broadcastMessage(MainPacketCreator.environmentChange(env, 2));
		}
		else
		{
			c.getSession().writeAndFlush(MainPacketCreator.environmentChange(env, 2));
		}
	}

	public void updateBuddyCapacity(int capacity)
	{
		c.getPlayer().setBuddyCapacity((byte) capacity);
	}

	public int getBuddyCapacity()
	{
		return c.getPlayer().getBuddyCapacity();
	}

	public int partyMembersInMap()
	{
		int inMap = 0;
		if (getPlayer().getParty() == null)
		{
			return inMap;
		}
		for (MapleCharacter char2 : getPlayer().getMap().getCharacters())
		{
			if (char2.getParty() != null && char2.getParty().getId() == getPlayer().getParty().getId())
			{
				inMap++;
			}
		}
		return inMap;
	}

	public List<MapleCharacter> getPartyMembers()
	{
		if (getPlayer().getParty() == null)
		{
			return null;
		}
		List<MapleCharacter> chars = new LinkedList<>(); // creates an empty
		// array full of
		// shit..
		for (MaplePartyCharacter chr : getPlayer().getParty().getMembers())
		{
			for (ChannelServer channel : ChannelServer.getAllInstances())
			{
				MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
				if (ch != null)
				{ // double check <3
					chars.add(ch);
				}
			}
		}
		return chars;
	}

    public int getFrozenMobCount() {
        return getPlayer().getFrozenMobCount();
    }

    public void addFrozenMobCount(int a1) {
        int val = (getFrozenMobCount() + a1) > 9999 ? 9999 : getFrozenMobCount() + a1;
        getPlayer().setFrozenMobCount(val);
        getClient().getSession().writeAndFlush(MainPacketCreator.OnAddPopupSay(1052230, 3500, "#face1#프로즌 링크 몬스터수를 충전했어!", ""));
    }     

	public int findFrozenMap()
	{
		int mapid = 993014200;
		MapleWorldMapProvider mapFactory = ChannelServer.getInstance(getClient().getChannel()).getMapFactory();
		while (mapid != 993014301)
		{
			if (mapFactory.getMap(mapid).getAllPlayer().size() == 0)
			{
				return mapid;
			}
                        else {
			mapid++;
                        }
		}
		return 0;
	}

	public void warpClock(int mapid, int mapid2, int portal, int time)
	{
		warp(mapid);
		getClient().sendPacket(MainPacketCreator.getClock(time));
		MapTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				if (getPlayer().getMapId() == mapid)
				{
					getPlayer().changeMap(mapid2, portal);
				}
			}
		}, time * 1000);
	}

	public void warpPartyWithExp(int mapId, int exp)
	{
		if (getPlayer().getParty() == null)
		{
			warp(mapId, 0);
			gainExp(exp);
			return;
		}
		MapleMap target = getMap(mapId);
		for (MaplePartyCharacter chr : getPlayer().getParty().getMembers())
		{
			MapleCharacter curChar = c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
			if ((curChar.getEventInstance() == null && getPlayer().getEventInstance() == null)
					|| curChar.getEventInstance() == getPlayer().getEventInstance())
			{
				curChar.changeMap(target, target.getPortal(0));
				curChar.gainExp(exp, true, false, true);
			}
		}
	}

	public void warpPartyWithExpMeso(int mapId, int exp, int meso)
	{
		if (getPlayer().getParty() == null)
		{
			warp(mapId, 0);
			gainExp(exp);
			gainMeso(meso);
			return;
		}
		MapleMap target = getMap(mapId);
		for (MaplePartyCharacter chr : getPlayer().getParty().getMembers())
		{
			MapleCharacter curChar = c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
			if ((curChar.getEventInstance() == null && getPlayer().getEventInstance() == null)
					|| curChar.getEventInstance() == getPlayer().getEventInstance())
			{
				curChar.changeMap(target, target.getPortal(0));
				curChar.gainExp(exp, true, false, true);
				curChar.gainMeso(meso, true);
			}
		}
	}

	public MapleSquadLegacy getSquad(String type)
	{
		return c.getChannelServer().getMapleSquad(type);
	}

	public int getSquadAvailability(String type)
	{
		final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
		if (squad == null)
		{
			return -1;
		}
		return squad.getStatus();
	}

	public boolean registerExpedition(String type, int minutes, String startText)
	{
		if (c.getChannelServer().getMapleSquad(type) == null)
		{
			final MapleSquadLegacy squad = new MapleSquadLegacy(c.getChannel(), type, c.getPlayer(),
					minutes * 60 * 1000);
			final boolean ret = c.getChannelServer().addMapleSquad(squad, type);
			if (ret)
			{
				final MapleMap map = c.getPlayer().getMap();
				map.broadcastMessage(MainPacketCreator.getClock(minutes * 60));
				map.broadcastMessage(MainPacketCreator.serverNotice(-6, startText));
			}
			return ret;
		}
		return false;
	}

	public boolean registerSquad(String type, int minutes, String startText)
	{
		if (c.getChannelServer().getMapleSquad(type) == null)
		{
			final MapleSquadLegacy squad = new MapleSquadLegacy(c.getChannel(), type, c.getPlayer(),
					minutes * 60 * 1000);
			final boolean ret = c.getChannelServer().addMapleSquad(squad, type);
			if (ret)
			{
				final MapleMap map = c.getPlayer().getMap();
				map.broadcastMessage(MainPacketCreator.getClock(minutes * 60));
				map.broadcastMessage(MainPacketCreator.serverNotice(6, c.getPlayer().getName() + startText));
			}
			return ret;
		}
		return false;
	}

	public boolean getSquadList(String type, byte type_)
	{
		try
		{
			final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
			if (squad == null)
			{
				return false;
			}
			if (type_ == 0 || type_ == 3)
			{ // Normal viewing
				sendNext(squad.getSquadMemberString(type_));
			}
			else if (type_ == 1)
			{ // Squad Leader banning, Check out banned
				// participant
				sendSimple(squad.getSquadMemberString(type_));
			}
			else if (type_ == 2)
			{
				if (squad.getBannedMemberSize() > 0)
				{
					sendSimple(squad.getSquadMemberString(type_));
				}
				else
				{
					sendNext(squad.getSquadMemberString(type_));
				}
			}
			return true;
		}
		catch (NullPointerException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	// public void teachSkill(int id, int skillevel, byte masterlevel, long
	// expiration) {
	// getPlayer().changeSkillLevelAll(SkillFactory.getSkill(id), skillevel,
	// masterlevel, expiration);
	// }

	public byte isSquadLeader(String type)
	{
		final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
		if (squad == null)
		{
			return -1;
		}
		else
		{
			if (squad.getLeader() != null && squad.getLeader().getId() == c.getPlayer().getId())
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}

	public boolean reAdd(String eim, String squad)
	{
		EventInstanceManager eimz = getDisconnected(eim);
		MapleSquadLegacy squadz = getSquad(squad);
		if (eimz != null && squadz != null)
		{
			squadz.reAddMember(getPlayer());
			eimz.registerPlayer(getPlayer());
			return true;
		}
		return false;
	}

	public void banMember(String type, int pos)
	{
		final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
		if (squad != null)
		{
			squad.banMember(pos);
		}
	}

	public void acceptMember(String type, int pos)
	{
		final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
		if (squad != null)
		{
			squad.acceptMember(pos);
		}
	}

	public int addMember(String type, boolean join)
	{
		try
		{
			final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
			if (squad != null)
			{
				return squad.addMember(c.getPlayer(), join);
			}
			return -1;
		}
		catch (NullPointerException ex)
		{
			ex.printStackTrace();
			return -1;
		}
	}

	public byte isSquadMember(String type)
	{
		final MapleSquadLegacy squad = c.getChannelServer().getMapleSquad(type);
		if (squad == null)
		{
			return -1;
		}
		else
		{
			if (squad.getMembers().contains(c.getPlayer()))
			{
				return 1;
			}
			else if (squad.isBanned(c.getPlayer()))
			{
				return 2;
			}
			else
			{
				return 0;
			}
		}
	}

	public void resetReactors()
	{
		getPlayer().getMap().resetReactors(getClient());
	}

	public void genericGuildMessage(int code)
	{
		c.getSession().writeAndFlush(MainPacketCreator.genericGuildMessage((byte) code));
	}

	public void disbandGuild()
	{
		final int gid = c.getPlayer().getGuildId();
		if (gid <= 0 || c.getPlayer().getGuildRank() != 1)
		{
			return;
		}
		WideObjectHolder.getInstance().disbandGuild(gid);
	}

    public void increaseGuildCapacity() { //길드 인원 늘리기
        if (c.getPlayer().getMeso() < 10000000) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "1000만 메소가 충분하지 않습니다."));
            return;
        }
        final int gid = c.getPlayer().getGuildId();
        if (gid <= 0) {
            return;
        }
        ChannelServer.increaseGuildCapacity(gid);
        c.getPlayer().gainMeso(-10000000, true, false, true);
    }

	public void displayGuildRanks()
	{
		c.getSession().writeAndFlush(MainPacketCreator.showGuildRanks(id, MapleGuildRanking.getInstance().getRank()));
	}

	public boolean removePlayerFromInstance()
	{
		if (c.getPlayer().getEventInstance() != null)
		{
			c.getPlayer().getEventInstance().removePlayer(c.getPlayer());
			return true;
		}
		return false;
	}

	public boolean isPlayerInstance()
	{
		return c.getPlayer().getEventInstance() != null;
	}

	public void makeTaintedEquip(byte slot)
	{
		Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(slot);
		sel.setStr((short) 69);
		sel.setDex((short) 69);
		sel.setInt((short) 69);
		sel.setLuk((short) 69);
		sel.setHp((short) 69);
		sel.setMp((short) 69);
		sel.setWatk((short) 69);
		sel.setMatk((short) 69);
		sel.setWdef((short) 69);
		sel.setMdef((short) 69);
		sel.setAcc((short) 69);
		sel.setAvoid((short) 69);
		sel.setHands((short) 69);
		sel.setSpeed((short) 69);
		sel.setJump((short) 69);
		sel.setUpgradeSlots((byte) 69);
		sel.setViciousHammer((byte) 69);
		sel.setEnhance((byte) 69);
		c.getPlayer().equipChanged();
		c.getPlayer().fakeRelog();
	}

	public void changeStat(byte slot, int type, int amount)
	{
		Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(slot);
		switch (type)
		{
		case 0:
			sel.setStr((short) amount);
			break;
		case 1:
			sel.setDex((short) amount);
			break;
		case 2:
			sel.setInt((short) amount);
			break;
		case 3:
			sel.setLuk((short) amount);
			break;
		case 4:
			sel.setHp((short) amount);
			break;
		case 5:
			sel.setMp((short) amount);
			break;
		case 6:
			sel.setWatk((short) amount);
			break;
		case 7:
			sel.setMatk((short) amount);
			break;
		case 8:
			sel.setWdef((short) amount);
			break;
		case 9:
			sel.setMdef((short) amount);
			break;
		case 10:
			sel.setAcc((short) amount);
			break;
		case 11:
			sel.setAvoid((short) amount);
			break;
		case 12:
			sel.setHands((short) amount);
			break;
		case 13:
			sel.setSpeed((short) amount);
			break;
		case 14:
			sel.setJump((short) amount);
			break;
		case 15:
			sel.setUpgradeSlots((byte) amount);
			break;
		case 16:
			sel.setViciousHammer((byte) amount);
			break;
		case 17:
			sel.setLevel((byte) amount);
			break;
		case 18:
			sel.setEnhance((byte) amount);
			break;
		case 19:
			sel.setPotential1(amount);
			break;
		case 20:
			sel.setPotential2(amount);
			break;
		case 21:
			sel.setPotential3(amount);
			break;
		case 22:
			sel.setPotential4(amount);
			break;
		case 23:
			sel.setPotential5(amount);
			break;
		case 24:
			sel.setOwner(getText());
			break;
		default:
			break;
		}
		c.getPlayer().equipChanged();
		c.getPlayer().fakeRelog();
	}

	public void openPackageDeliverer()
	{
		// c.getPlayer().setConversation(2);
		// c.getSession().writeAndFlush(CField.sendPackageMSG((byte) 9, null));
	}

	public void openMerchantItemStore()
	{
		// c.getPlayer().setConversation(3);
		// HiredMerchantHandler.displayMerch(c);
		// c.getSession().writeAndFlush(CWvsContext.enableActions());
	}

	public void sendPVPWindow()
	{
		c.getSession().writeAndFlush(UIPacket.openUI(0x32));
		// c.getSession().writeAndFlush(CField.sendPVPMaps());
	}

	public void sendAzwanWindow()
	{
		c.getSession().writeAndFlush(UIPacket.openUI(0x46));
	}

	public void sendOpenJobChangeUI()
	{
		c.getSession().writeAndFlush(UIPacket.openUI(0xA4)); // job selections change
		// depending on ur job
	}

	public void sendTimeGateWindow()
	{
		c.getSession().writeAndFlush(UIPacket.openUI(0xA8));
	}

	public void SendEvolution()
	{
		c.getSession().writeAndFlush(UIPacket.openUI(100));
	}

	public void sendRepairWindow()
	{
		c.getSession().writeAndFlush(UIPacket.sendRepairWindow(id));
	}

	public void sendJewelCraftWindow()
	{
		c.getSession().writeAndFlush(UIPacket.sendJewelCraftWindow(id));
	}

	public void sendRedLeaf(boolean viewonly, boolean autocheck)
	{
		/*
		 * if (autocheck) { viewonly = c.getPlayer().getFriendShipToAdd() == 0; }
		 * c.getSession().writeAndFlush(UIPacket.sendRedLeaf(viewonly ? 0 :
		 * c.getPlayer().getFriendShipToAdd(), viewonly));
		 */
	}

	public int GetCount(String boss, int limit)
	{
		if (getPlayer().getDateKey(boss, false) == null)
		{
			return limit;
		}
		return limit - Integer.parseInt(this.c.getPlayer().getDateKey(boss, false));
	}

	public final boolean CountCheck(String paramString, int paramInt)
	{
		if (getPlayer().getDateKey(paramString, false) == null)
		{
			getPlayer().setDateKey(paramString, "0", false);
		}
		return Integer.parseInt(getPlayer().getDateKey(paramString, false)) < paramInt;
	}

	public void CountAdd(String paramString, int paramInt)
	{
		if (getPlayer().getDateKey(paramString, false) == null)
		{
			getPlayer().setDateKey(paramString, "0", false);
		}
		getPlayer().setDateKey(paramString, "" + (Integer.parseInt(getPlayer().getDateKey(paramString, false)) + 1),
				false);
		// return Integer.parseInt(getPlayer().getDateKey(paramString, false)) <
		// paramInt;
	}

	public void sendProfessionWindow()
	{
		c.getSession().writeAndFlush(UIPacket.openUI(42));
	}

	public void OpenUI(int ui)
	{
		c.getPlayer().getMap().broadcastMessage(UIPacket.openUI(ui));
	}

	public void openUI(int ui)
	{
		c.getPlayer().getMap().broadcastMessage(UIPacket.openUI(ui));
	}

	public void getMulungRanking()
	{
		// c.getSession().writeAndFlush(CWvsContext.getMulungRanking());
	}

	public final int getDojoPoints()
	{
		return dojo_getPts();
	}

	public final int getDojoRecord()
	{
		return c.getPlayer().getIntNoRecord(GameConstants.DOJO_RECORD);
	}

	public void setDojoRecord(final boolean reset, final boolean take, int amount)
	{
		if (reset)
		{
			c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO_RECORD)).setCustomData("0");
			c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO)).setCustomData("0");
		}
		else if (take)
		{
			c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO_RECORD))
					.setCustomData(String.valueOf(c.getPlayer().getIntRecord(GameConstants.DOJO_RECORD) - amount));
			c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO))
					.setCustomData(String.valueOf(c.getPlayer().getIntRecord(GameConstants.DOJO_RECORD) - amount));
		}
		else
		{
			c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO_RECORD))
					.setCustomData(String.valueOf(c.getPlayer().getIntRecord(GameConstants.DOJO_RECORD) + 1));
		}
	}

/*
	public boolean start_DojoAgent(final boolean dojo, final boolean party, final int mapid)
	{
		if (dojo)
		{
			return MapleDojoAgent.warpStartDojo(c.getPlayer(), party, getMap(mapid));
		}
		return MapleDojoAgent.warpStartAgent(c.getPlayer(), party);
	}
*/

	public boolean start_PyramidSubway(final int pyramid)
	{
		if (pyramid >= 0)
		{
			return MaplePyramidSubway.warpStartPyramid(c.getPlayer(), pyramid);
		}
		return MaplePyramidSubway.warpStartSubway(c.getPlayer());
	}

	public boolean bonus_PyramidSubway(final int pyramid)
	{
		if (pyramid >= 0)
		{
			return MaplePyramidSubway.warpBonusPyramid(c.getPlayer(), pyramid);
		}
		return MaplePyramidSubway.warpBonusSubway(c.getPlayer());
	}

	public final short getKegs()
	{
		// return c.getChannelServer().getFireWorks().getKegsPercentage();
		return -1;
	}

	public void giveKegs(final int kegs)
	{
		// c.getChannelServer().getFireWorks().giveKegs(c.getPlayer(), kegs);
	}

	public final short getSunshines()
	{
		// return c.getChannelServer().getFireWorks().getSunsPercentage();
		return -1;
	}

	public void addSunshines(final int kegs)
	{
		// c.getChannelServer().getFireWorks().giveSuns(c.getPlayer(), kegs);
	}

	public final short getDecorations()
	{
		// return c.getChannelServer().getFireWorks().getDecsPercentage();
		return -1;
	}

	public void addDecorations(final int kegs)
	{
		/*
		 * try { c.getChannelServer().getFireWorks().giveDecs(c.getPlayer(), kegs); }
		 * catch (Exception e) { }
		 */
	}

	public final MapleCarnivalParty getCarnivalParty()
	{
		return c.getPlayer().getCarnivalParty();
	}

	public final MapleCarnivalChallenge getNextCarnivalRequest()
	{
		return null;
	}

	public final MapleCarnivalChallenge getCarnivalChallenge(MapleCharacter chr)
	{
		return new MapleCarnivalChallenge(chr);
	}

	public int setAndroid(int args)
	{
		if (args < 30000)
		{
			c.getPlayer().getAndroid().setFace(args);
			c.getPlayer().getAndroid().saveToDb();
		}
		else
		{
			c.getPlayer().getAndroid().setHair(args);
			c.getPlayer().getAndroid().saveToDb();
		}
		c.getPlayer().setAndroid(c.getPlayer().getAndroid()); // Respawn it
		c.getPlayer().equipChanged();
		return 1;
	}

	public void sendAndroidStyle(String text, int styles[])
	{
		if (lastMsg > -1)
		{
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 11);
		t.setText(text);
		t.setArgs(styles);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 11;
	}

	public void setAndroidHair(int hair)
	{
		getPlayer().getAndroid().setHair(hair);
		getPlayer().getAndroid().saveToDb();
		c.getPlayer().setAndroid(c.getPlayer().getAndroid());
	}

	public void setAndroidFace(int face)
	{
		getPlayer().getAndroid().setFace(face);
		getPlayer().getAndroid().saveToDb();
		c.getPlayer().setAndroid(c.getPlayer().getAndroid());
	}

	public int getAndroidStat(final String type)
	{
		switch (type)
		{
		case "HAIR":
			return c.getPlayer().getAndroid().getHair();
		case "FACE":
			return c.getPlayer().getAndroid().getFace();
		case "GENDER":
			int itemid = c.getPlayer().getAndroid().getItemId();
			if (itemid == 1662000 || itemid == 1662002)
			{
				return 0;
			}
			else
			{
				return 1;
			}
		}
		return -1;
	}

        public void reloadChar() {
        getPlayer().reloadChar();
    }


	public void askAndroid(String text, int... args)
	{
		if (lastMsg > -1)
		{
			return;
		}
		NPCTalk t = new NPCTalk((byte) 4, id, (byte) 11);
		t.setText(text);
		t.setArgs(args);

		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		lastMsg = 11;
	}

	@Override
	public MapleCharacter getChar()
	{
		return getPlayer();
	}
            public MapleCharacter getChar(int id) {
        MapleCharacter chr = null;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            chr = cs.getPlayerStorage().getCharacterById(id);
            if (chr != null) {
                return chr;
            }
        }
        return null;
    }

    public MapleCharacter getChar(String name) {
        return getPlayer().getMap().getCharacterByName_InMap(name);
    }
    
        public void makeRing(int itemid, MapleCharacter chr) {
        try {
            IItem item = ItemInformation.getInstance().getEquipById(itemid);
            IItem item1 = ItemInformation.getInstance().getEquipById(itemid);
            item.setCash(true);
            item.setUniqueId(MapleItemIdenfier.getInstance().getNewUniqueId());
            item1.setCash(true);
            item1.setUniqueId(MapleItemIdenfier.getInstance().getNewUniqueId());
            MapleRing.makeRing(itemid, chr, item.getUniqueId(), item1.getUniqueId()); //내꺼
            MapleRing.makeRing(itemid, getPlayer(), item1.getUniqueId(), item.getUniqueId());//파트너꺼
            InventoryManipulator.addbyItem(getClient(), item);
            InventoryManipulator.addbyItem(chr.getClient(), item1);
            chr.reloadChar();
            reloadChar();
            sendOk("선택하신 반지를 제작 완료 하였습니다. 인벤토리를 확인해 봐주시길 바랍니다.");
            chr.dropMessage(5, getPlayer().getName() + "님으로 부터 반지가 도착 하였습니다. 인벤토리를 확인해 주시길 바랍니다.");
        } catch (Exception ex) {
            sendOk("반지를 제작하는데 오류가 발생 하였습니다.");
        }
    }

	public void equipSecondaryByID(final int shieldID)
	{
		if (shieldID > 0)
		{
			// c.getPlayer().setShield(shieldID);
		}
		else
		{
			System.out.println("Please insert an item-id to equip.");
		}

	}

	public static int editEquipById(MapleCharacter chr, int max, int itemid, String stat, int newval)
	{
		return editEquipById(chr, max, itemid, stat, (short) newval);
	}

	public static int editEquipById(MapleCharacter chr, int max, int itemid, String stat, short newval)
	{
		// Is it an equip?
		if (!GameConstants.isEquip(itemid))
		{
			return -1;
		}

		// Get List
		List<IItem> equips = chr.getInventory(MapleInventoryType.EQUIP).listById(itemid);
		List<IItem> equipped = chr.getInventory(MapleInventoryType.EQUIPPED).listById(itemid);

		// Do you have any?
		if (equips.isEmpty() && equipped.isEmpty())
		{
			return 0;
		}

		int edited = 0;

		// edit items
		for (IItem itm : equips)
		{
			Equip e = (Equip) itm;
			if (edited >= max)
			{
				break;
			}
			edited++;
			switch (stat)
			{
			case "str":
				e.setStr(newval);
				break;
			case "dex":
				e.setDex(newval);
				break;
			case "int":
				e.setInt(newval);
				break;
			case "luk":
				e.setLuk(newval);
				break;
			case "watk":
				e.setWatk(newval);
				break;
			case "matk":
				e.setMatk(newval);
				break;
			default:
				return -2;
			}
		}
		for (IItem itm : equipped)
		{
			Equip e = (Equip) itm;
			if (edited >= max)
			{
				break;
			}
			edited++;
			switch (stat)
			{
			case "str":
				e.setStr(newval);
				break;
			case "dex":
				e.setDex(newval);
				break;
			case "int":
				e.setInt(newval);
				break;
			case "luk":
				e.setLuk(newval);
				break;
			case "watk":
				e.setWatk(newval);
				break;
			case "matk":
				e.setMatk(newval);
				break;
			default:
				return -2;
			}
		}

		// Return items edited
		return (edited);
	}

	public int getReborns()
	{ // tjat
		return getPlayer().getReborns();
	}

	public boolean getSR(Triple<String, Map<Integer, String>, Long> ma, int sel)
	{
		if (ma.second.get(sel) == null || ma.second.get(sel).length() <= 0)
		{
			dispose();
			return false;
		}
		sendOk(ma.second.get(sel));
		return true;
	}

	public Equip getEquip(int itemid)
	{
		return (Equip) ItemInformation.getInstance().getEquipById(itemid);
	}

	public void setExpiration(Object statsSel, long expire)
	{
		if (statsSel instanceof Equip)
		{
			((Equip) statsSel).setExpiration(System.currentTimeMillis() + (expire * 24 * 60 * 60 * 1000));
		}
	}

	public void setLock(Object statsSel)
	{
		if (statsSel instanceof Equip)
		{
			Equip eq = (Equip) statsSel;
			if (eq.getExpiration() == -1)
			{
				eq.setFlag((byte) (eq.getFlag() | ItemFlag.LOCK.getValue()));
			}
		}
	}

	public boolean addFromDrop(Object statsSel)
	{
		if (statsSel instanceof IItem)
		{
			final IItem it = (IItem) statsSel;
			if (InventoryManipulator.checkSpace(getClient(), it.getItemId(), it.getQuantity(), it.getOwner()))
			{
				InventoryManipulator.addFromDrop(getClient(), it, false);
				return true;
			}
		}
		return false;
	}

	public int getVPoints()
	{
		return getPlayer().getVPoints();
	}

	public int getStarterQuestID()
	{
		return getPlayer().getStarterQuestID();
	}

	public int getStarterQuestStatus()
	{
		return getPlayer().getStarterQuest();
	}

	public void setStarterQuestID(int id)
	{
		getPlayer().setStarterQuestID(id);
	}

	public void setStarterQuestStatus(int id)
	{
		getPlayer().setStarterQuest(id);
	}

	public int getDPoints()
	{
		return getPlayer().getVPoints();
	}

	public void setDPoints(int dpoints)
	{
		// getPlayer().setDPoints(getPlayer().getDPoints() + dpoints);
	}

	public int getEPoints()
	{
		return 0;
	}

	public void setEPoints(int epoints)
	{
		// getPlayer().setEPoints(getPlayer().getEPoints() + epoints);
	}

	public void setAndroidSkinColor(int color)
	{
		getPlayer().getAndroid().setSkinColor(color);
		getPlayer().getAndroid().saveToDb();
		c.getPlayer().setAndroid(c.getPlayer().getAndroid());
	}

	public void setSecondSkin(int color)
	{
		getPlayer().setSecondSkinColor((byte) color);
		getPlayer().updateSingleStat(PlayerStatList.SKIN, color);
		getPlayer().equipChanged();
	}

	public boolean replaceItem(int slot, int invType, Object statsSel, int offset, String type)
	{
		return replaceItem(slot, invType, statsSel, offset, type, false);
	}

	public boolean replaceItem(int slot, int invType, Object statsSel, int offset, String type, boolean takeSlot)
	{
		MapleInventoryType inv = MapleInventoryType.getByType((byte) invType);
		if (inv == null)
		{
			return false;
		}
		IItem item = getPlayer().getInventory(inv).getItem((byte) slot);
		if (item == null || statsSel instanceof IItem)
		{
			item = (IItem) statsSel;
		}
		if (offset > 0)
		{
			if (inv != MapleInventoryType.EQUIP)
			{
				return false;
			}
			Equip eq = (Equip) item;
			if (takeSlot)
			{
				if (eq.getUpgradeSlots() < 1)
				{
					return false;
				}
				else
				{
					eq.setUpgradeSlots((byte) (eq.getUpgradeSlots() - 1));
				}
				if (eq.getExpiration() == -1)
				{
					eq.setFlag((byte) (eq.getFlag() | ItemFlag.LOCK.getValue()));
				}
			}
			if (type.equalsIgnoreCase("Slots"))
			{
				eq.setUpgradeSlots((byte) (eq.getUpgradeSlots() + offset));
				eq.setViciousHammer((byte) (eq.getViciousHammer() + offset));
			}
			else if (type.equalsIgnoreCase("Level"))
			{
				eq.setLevel((byte) (eq.getLevel() + offset));
			}
			else if (type.equalsIgnoreCase("Hammer"))
			{
				eq.setViciousHammer((byte) (eq.getViciousHammer() + offset));
			}
			else if (type.equalsIgnoreCase("STR"))
			{
				eq.setStr((short) (eq.getStr() + offset));
			}
			else if (type.equalsIgnoreCase("DEX"))
			{
				eq.setDex((short) (eq.getDex() + offset));
			}
			else if (type.equalsIgnoreCase("INT"))
			{
				eq.setInt((short) (eq.getInt() + offset));
			}
			else if (type.equalsIgnoreCase("LUK"))
			{
				eq.setLuk((short) (eq.getLuk() + offset));
			}
			else if (type.equalsIgnoreCase("HP"))
			{
				eq.setHp((short) (eq.getHp() + offset));
			}
			else if (type.equalsIgnoreCase("MP"))
			{
				eq.setMp((short) (eq.getMp() + offset));
			}
			else if (type.equalsIgnoreCase("PAD"))
			{
				eq.setWatk((short) (eq.getWatk() + offset));
			}
			else if (type.equalsIgnoreCase("MAD"))
			{
				eq.setMatk((short) (eq.getMatk() + offset));
			}
			else if (type.equalsIgnoreCase("PDD"))
			{
				eq.setWdef((short) (eq.getWdef() + offset));
			}
			else if (type.equalsIgnoreCase("MDD"))
			{
				eq.setMdef((short) (eq.getMdef() + offset));
			}
			else if (type.equalsIgnoreCase("ACC"))
			{
				eq.setAcc((short) (eq.getAcc() + offset));
			}
			else if (type.equalsIgnoreCase("Avoid"))
			{
				eq.setAvoid((short) (eq.getAvoid() + offset));
			}
			else if (type.equalsIgnoreCase("Hands"))
			{
				eq.setHands((short) (eq.getHands() + offset));
			}
			else if (type.equalsIgnoreCase("Speed"))
			{
				eq.setSpeed((short) (eq.getSpeed() + offset));
			}
			else if (type.equalsIgnoreCase("Jump"))
			{
				eq.setJump((short) (eq.getJump() + offset));
			}
			else if (type.equalsIgnoreCase("ItemEXP"))
			{
				eq.setItemEXP(eq.getItemEXP() + offset);
			}
			else if (type.equalsIgnoreCase("Expiration"))
			{
				eq.setExpiration((long) (eq.getExpiration() + offset));
			}
			else if (type.equalsIgnoreCase("Flag"))
			{
				eq.setFlag((byte) (eq.getFlag() + offset));
			}
			item = eq.copy();
		}
		InventoryManipulator.removeFromSlot(getClient(), inv, (short) slot, item.getQuantity(), false);
		InventoryManipulator.addFromDrop(getClient(), item, false);
		return true;
	}

	public boolean replaceItem(int slot, int invType, Object statsSel, int upgradeSlots)
	{
		return replaceItem(slot, invType, statsSel, upgradeSlots, "Slots");
	}

	public boolean isCash(final int itemId)
	{
		return ItemInformation.getInstance().isCash(itemId);
	}

	public int getTotalStat(final int itemId)
	{
		return ItemInformation.getInstance().getTotalStat((Equip) ItemInformation.getInstance().getEquipById(itemId));
	}

	public int getReqLevel(final int itemId)
	{
		return ItemInformation.getInstance().getReqLevel(itemId);
	}

	public SkillStatEffect getEffect(int buff)
	{
		return ItemInformation.getInstance().getItemEffect(buff);
	}

	public void buffGuild(final int buff, final int duration, final String msg)
	{
		ItemInformation ii = ItemInformation.getInstance();
		if (ii.getItemEffect(buff) != null && getPlayer().getGuildId() > 0)
		{
			final SkillStatEffect mse = ii.getItemEffect(buff);
			for (ChannelServer cserv : ChannelServer.getAllInstances())
			{
				for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters().values())
				{
					if (chr.getGuildId() == getPlayer().getGuildId())
					{
						mse.applyTo(chr, null, true);
						chr.dropMessage(5, "Your guild has gotten a " + msg + " buff.");
					}
				}
			}
		}
	}

	/* public boolean createAlliance(String alliancename)
	{
		MapleParty pt = c.getPlayer().getParty();
		MapleCharacter otherChar = c.getChannelServer().getPlayerStorage()
				.getCharacterById(pt.getMemberByIndex(1).getId());
		if (otherChar == null || otherChar.getId() == c.getPlayer().getId())
		{
			return false;
		}
		try
		{
			return WideObjectHolder.getInstance().createAlliance(alliancename, c.getPlayer().getId(), otherChar.getId(),
					c.getPlayer().getGuildId(), otherChar.getGuildId());
		}
		catch (Exception re)
		{
			return false;
		}
	} */

	public boolean addCapacityToAlliance()
	{
		try
		{
			final MapleGuild gs = WideObjectHolder.getInstance().getGuild(c.getPlayer().getGuildId());
			if (gs != null && c.getPlayer().getGuildRank() == 1 && c.getPlayer().getAllianceRank() == 1)
			{
				if (WideObjectHolder.getInstance().getAllianceLeader(gs.getAllianceId()) == c.getPlayer().getId()
						&& WideObjectHolder.getInstance().changeAllianceCapacity(gs.getAllianceId()))
				{
					gainMeso(-10000000);
					return true;
				}
			}
		}
		catch (Exception re)
		{
		}
		return false;
	}

	public boolean disbandAlliance()
	{
		try
		{
			final MapleGuild gs = WideObjectHolder.getInstance().getGuild(c.getPlayer().getGuildId());
			if (gs != null && c.getPlayer().getGuildRank() == 1 && c.getPlayer().getAllianceRank() == 1)
			{
				if (WideObjectHolder.getInstance().getAllianceLeader(gs.getAllianceId()) == c.getPlayer().getId()
						&& WideObjectHolder.getInstance().disbandAlliance(gs.getAllianceId()))
				{
					return true;
				}
			}
		}
		catch (Exception re)
		{
		}
		return false;
	}

	public byte getLastMsg()
	{
		return lastMsg;
	}

	public final void setLastMsg(final byte last)
	{
		this.lastMsg = last;
	}

	public final void maxAllSkills()
	{
		HashMap<ISkill, SkillEntry> sa = new HashMap<>();
		for (ISkill skil : SkillFactory.getAllSkills())
		{
			if (GameConstants.isApplicableSkill(skil.getId()) && skil.getId() < 90000000)
			{ // no
				// db/additionals/resistance
				// skills
				sa.put(skil, new SkillEntry((byte) skil.getMaxLevel(), (byte) skil.getMaxLevel(),
						SkillFactory.getDefaultSExpiry(skil)));
			}
		}
		getPlayer().changeSkillsLevel(sa);
	}

	public final void maxSkillsByJob()
	{
		HashMap<ISkill, SkillEntry> sa = new HashMap<>();
		for (ISkill skil : SkillFactory.getAllSkills())
		{
			if (GameConstants.isApplicableSkill(skil.getId()) && skil.canBeLearnedBy(getPlayer().getJob())
					&& !skil.isInvisible())
			{ // no db/additionals/resistance
				// skills
				sa.put(skil, new SkillEntry((byte) skil.getMaxLevel(), (byte) skil.getMaxLevel(),
						SkillFactory.getDefaultSExpiry(skil)));
			}
		}
		getPlayer().changeSkillsLevel(sa);
	}

	public final void removeSkillsByJob()
	{
		HashMap<ISkill, SkillEntry> sa = new HashMap<>();
		for (ISkill skil : SkillFactory.getAllSkills())
		{
			if (GameConstants.isApplicableSkill(skil.getId()) && skil.canBeLearnedBy(getPlayer().getJob()))
			{ // no
				// db/additionals/resistance
				// skills
				sa.put(skil,
						new SkillEntry((byte) -1, (byte) skil.getMaxLevel(), SkillFactory.getDefaultSExpiry(skil)));
			}
		}
		getPlayer().changeSkillsLevel(sa);
	}

	public final void maxSkillsByJobId(int jobid)
	{
		HashMap<ISkill, SkillEntry> sa = new HashMap<>();
		for (ISkill skil : SkillFactory.getAllSkills())
		{
			if (GameConstants.isApplicableSkill(skil.getId()) && skil.canBeLearnedBy(getPlayer().getJob())
					&& skil.getId() >= jobid * 1000000 && skil.getId() < (jobid + 1) * 1000000 && !skil.isInvisible())
			{
				sa.put(skil, new SkillEntry((byte) skil.getMaxLevel(), (byte) skil.getMaxLevel(),
						SkillFactory.getDefaultSExpiry(skil)));
			}
		}
		getPlayer().changeSkillsLevel(sa);
	}

	public final void resetStats(int str, int dex, int z, int luk)
	{
		c.getPlayer().resetStats(str, dex, z, luk);
	}

	public void killAllMonsters(int mapid)
	{
		MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
		map.killAllMonsters(false); // No drop.
	}

	public void cleardrops()
	{
		MapleMonsterProvider.getInstance().clearDrops();
	}

	public final boolean dropItem(int slot, int invType, int quantity)
	{
		MapleInventoryType inv = MapleInventoryType.getByType((byte) invType);
		if (inv == null)
		{
			return false;
		}
		InventoryManipulator.drop(c, inv, (short) slot, (short) quantity);
		return true;
	}

	public final List<Integer> getAllPotentialInfo()
	{
		List<Integer> list = new ArrayList<>(ItemInformation.getInstance().getAllPotentialInfo().keySet());
		Collections.sort(list);
		return list;
	}

	public final List<Integer> getAllPotentialInfoSearch(String content)
	{
		List<Integer> list = new ArrayList<>();
		for (Entry<Integer, List<StructPotentialItem>> i : ItemInformation.getInstance().getAllPotentialInfo()
				.entrySet())
		{
			for (StructPotentialItem ii : i.getValue())
			{
				if (ii.toString().contains(content))
				{
					list.add(i.getKey());
				}
			}
		}
		Collections.sort(list);
		return list;
	}

	public void MakeGMItem(byte slot, MapleCharacter player)
	{
		MapleInventory equip = player.getInventory(MapleInventoryType.EQUIP);
		Equip eu = (Equip) equip.getItem(slot);
		int item = equip.getItem(slot).getItemId();
		short hand = eu.getHands();
		byte level = eu.getLevel();
		Equip nItem = new Equip(item, slot, (byte) 0);
		nItem.setStr((short) 32767); // STR
		nItem.setDex((short) 32767); // DEX
		nItem.setInt((short) 32767); // INT
		nItem.setLuk((short) 32767); // LUK
		nItem.setUpgradeSlots((byte) 0);
		nItem.setHands(hand);
		nItem.setLevel(level);
		player.getInventory(MapleInventoryType.EQUIP).removeItem(slot);
		player.getInventory(MapleInventoryType.EQUIP).addFromDB(nItem);
	}

	public final void sendRPS()
	{
		// c.getSession().writeAndFlush(MainPacketCreator.getRPSMode((byte) 8, -1, -1,
		// -1));
	}

	public final void setQuestRecord(Object ch, final int questid, final String data)
	{
		((MapleCharacter) ch).getQuestNAdd(MapleQuest.getInstance(questid)).setCustomData(data);
	}

	public final void doWeddingEffect(final Object ch)
	{
		/*
		 * final MapleCharacter chr = (MapleCharacter) ch; final MapleCharacter player =
		 * getPlayer();
		 * getMap().broadcastMessage(CWvsContext.yellowChat(player.getName() +
		 * ", do you take " + chr.getName() +
		 * " as your wife and promise to stay beside her through all downtimes, crashes, and lags?"
		 * )); CloneTimer.getInstance().schedule(new Runnable() {
		 * 
		 * @Override public void run() { if (chr == null || player == null) {
		 * warpMap(680000500, 0); } else {
		 * chr.getMap().broadcastMessage(CWvsContext.yellowChat(chr.getName() +
		 * ", do you take " + player.getName() +
		 * " as your husband and promise to stay beside him through all downtimes, crashes, and lags?"
		 * )); } } }, 10000); CloneTimer.getInstance().schedule(new Runnable() {
		 * 
		 * @Override public void run() { if (chr == null || player == null) { if (player
		 * != null) { setQuestRecord(player, 160001, "3"); setQuestRecord(player,
		 * 160002, "0"); } else if (chr != null) { setQuestRecord(chr, 160001, "3");
		 * setQuestRecord(chr, 160002, "0"); } warpMap(680000500, 0); } else {
		 * setQuestRecord(player, 160001, "2"); setQuestRecord(chr, 160001, "2");
		 * sendNPCText( player.getName() + " and " + chr.getName() +
		 * ", I wish you two all the best on your " +
		 * chr.getClient().getChannelServer().getServerName() + " journey together!",
		 * 9201002); chr.getMap().startExtendedMapEffect("You may now kiss the bride, "
		 * + player.getName() + "!", 5120006); if (chr.getGuildId() > 0) {
		 * World.Guild.guildPacket(chr.getGuildId(), CWvsContext.sendMarriage(false,
		 * chr.getName())); } if (player.getGuildId() > 0) {
		 * World.Guild.guildPacket(player.getGuildId(), CWvsContext.sendMarriage(false,
		 * player.getName())); } } } }, 20000); // 10 sec 10 sec
		 */

	}

	public void putKey(int key, int type, int action)
	{
		getPlayer().changeKeybinding(key, new MapleKeyBinding((byte) type, action));
		getClient().getSession().writeAndFlush(MainPacketCreator.getKeymap(getPlayer().getKeyLayout()));
	}

	public void doRing(final String name, final int itemid)
	{
		// PlayersHandler.DoRing(getClient(), name, itemid);
	}

	public int getNaturalStats(final int itemid, final String it)
	{
		Map<String, Integer> eqStats = ItemInformation.getInstance().getEquipStats(itemid);
		if (eqStats != null && eqStats.containsKey(it))
		{
			return eqStats.get(it);
		}
		return 0;
	}

	public String checkDrop(MapleCharacter chr, int mobId)
	{
		final List<MonsterDropEntry> ranks = MapleMonsterProvider.getInstance().retrieveDrop(mobId);
		if (ranks != null && ranks.size() > 0)
		{
			int num = 0;
			int itemId;
			int ch;
			MonsterDropEntry de;
			StringBuilder name = new StringBuilder();
			for (int i = 0; i < ranks.size(); i++)
			{
				de = ranks.get(i);
				if (de.chance > 0 && (de.questid <= 0
						|| (de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0)))
				{
					itemId = de.itemId;
					if (num == 0)
					{
						name.append("Drops for #o").append(mobId).append("#\r\n");
						name.append("--------------------------------------\r\n");
					}
					String namez = "#z" + itemId + "#";
					if (itemId == 0)
					{ // meso
						itemId = 4031041; // display sack of cash
						namez = (de.Minimum * getClient().getChannelServer().getMesoRate()) + " to "
								+ (de.Maximum * getClient().getChannelServer().getMesoRate()) + " meso";
					}
					ch = de.chance * getClient().getChannelServer().getDropRate();
					name.append(num + 1).append(") #v").append(itemId).append("#").append(namez).append(" - ")
							.append(Integer.valueOf(ch >= 999999 ? 1000000 : ch).doubleValue() / 10000.0)
							.append("% chance. ")
							.append(de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0
									? ("Requires quest " + MapleQuest.getInstance(de.questid).getName()
											+ " to be started.")
									: "")
							.append("\r\n");
					num++;
				}
			}
			if (name.length() > 0)
			{
				return name.toString();
			}

		}
		return "No drops was returned.";
	}

	public String getLeftPadded(final String in, final char padchar, final int length)
	{
		return StringUtil.getLeftPaddedStr(in, padchar, length);
	}

	public void handleDivorce()
	{
		/*
		 * if (getPlayer().getMarriageId() <= 0) {
		 * sendNext("Please make sure you have a marriage."); return; } final int chz =
		 * World.Find.findChannel(getPlayer().getMarriageId()); if (chz == -1) { // sql
		 * queries try { Connection con = DatabaseConnection.getConnection();
		 * PreparedStatement ps = con.prepareStatement(
		 * "UPDATE queststatus SET customData = ? WHERE characterid = ? AND (quest = ? OR quest = ?)"
		 * ); ps.setString(1, "0"); ps.setInt(2, getPlayer().getMarriageId());
		 * ps.setInt(3, 160001); ps.setInt(4, 160002); ps.executeUpdate(); ps.close();
		 * 
		 * ps =
		 * con.prepareStatement("UPDATE characters SET marriageid = ? WHERE id = ?");
		 * ps.setInt(1, 0); ps.setInt(2, getPlayer().getMarriageId());
		 * ps.executeUpdate(); ps.close(); } catch (SQLException e) {
		 * e.printStackTrace(); return; } setQuestRecord(getPlayer(), 160001, "0");
		 * setQuestRecord(getPlayer(), 160002, "0"); getPlayer().setMarriageId(0);
		 * sendNext("You have been successfully divorced..."); return; } else if (chz <
		 * -1) { sendNext("Please make sure your partner is logged on."); return; }
		 * MapleCharacter cPlayer = ChannelServer.getInstance(chz).getPlayerStorage()
		 * .getCharacterById(getPlayer().getMarriageId()); if (cPlayer != null) {
		 * cPlayer.dropMessage(1, "Your partner has divorced you.");
		 * cPlayer.setMarriageId(0); setQuestRecord(cPlayer, 160001, "0");
		 * setQuestRecord(getPlayer(), 160001, "0"); setQuestRecord(cPlayer, 160002,
		 * "0"); setQuestRecord(getPlayer(), 160002, "0"); getPlayer().setMarriageId(0);
		 * sendNext("You have been successfully divorced..."); } else {
		 * sendNext("An error occurred..."); }
		 */
	}

	/*
	 * Start of Custom Features
	 */
	public void gainAPS(int gain)
	{
		getPlayer().gainAp(gain);
	}

	/*
	 * End of Custom Features
	 */
	public void changeJobById(short job)
	{
		c.getPlayer().changeJob(job);
	}

	public int getJobId()
	{
		return getPlayer().getJob();
	}

	public int getLevel()
	{
		return getPlayer().getLevel();
	}

	public int getEquipId(byte slot)
	{
		MapleInventory equip = getPlayer().getInventory(MapleInventoryType.EQUIP);
		return equip.getItem(slot).getItemId();
	}

	public int getUseId(byte slot)
	{
		MapleInventory use = getPlayer().getInventory(MapleInventoryType.USE);
		return use.getItem(slot).getItemId();
	}

	public int getSetupId(byte slot)
	{
		MapleInventory setup = getPlayer().getInventory(MapleInventoryType.SETUP);
		return setup.getItem(slot).getItemId();
	}

	public int getCashId(byte slot)
	{
		MapleInventory cash = getPlayer().getInventory(MapleInventoryType.CASH);
		return cash.getItem(slot).getItemId();
	}

	public int getETCId(byte slot)
	{
		MapleInventory etc = getPlayer().getInventory(MapleInventoryType.ETC);
		return etc.getItem(slot).getItemId();
	}

	public String EquipList(MapleClient c)
	{
		StringBuilder str = new StringBuilder();
		MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
		List<String> stra = new LinkedList<>();
		for (IItem item : equip.list())
		{
			stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
		}
		for (String strb : stra)
		{
			str.append(strb);
		}
		return str.toString();
	}

	public String UseList(MapleClient c)
	{
		StringBuilder str = new StringBuilder();
		MapleInventory use = c.getPlayer().getInventory(MapleInventoryType.USE);
		List<String> stra = new LinkedList<>();
		for (IItem item : use.list())
		{
			stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
		}
		for (String strb : stra)
		{
			str.append(strb);
		}
		return str.toString();
	}

	public String CashList(MapleClient c)
	{
		StringBuilder str = new StringBuilder();
		MapleInventory cash = c.getPlayer().getInventory(MapleInventoryType.CASH);
		List<String> stra = new LinkedList<>();
		for (IItem item : cash.list())
		{
			stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
		}
		for (String strb : stra)
		{
			str.append(strb);
		}
		return str.toString();
	}

	public String ETCList(MapleClient c)
	{
		StringBuilder str = new StringBuilder();
		MapleInventory etc = c.getPlayer().getInventory(MapleInventoryType.ETC);
		List<String> stra = new LinkedList<>();
		for (IItem item : etc.list())
		{
			stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
		}
		for (String strb : stra)
		{
			str.append(strb);
		}
		return str.toString();
	}

	public String SetupList(MapleClient c)
	{
		StringBuilder str = new StringBuilder();
		MapleInventory setup = c.getPlayer().getInventory(MapleInventoryType.SETUP);
		List<String> stra = new LinkedList<>();
		for (IItem item : setup.list())
		{
			stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
		}
		for (String strb : stra)
		{
			str.append(strb);
		}
		return str.toString();
	}

	public void wearEquip(int itemid, byte slot)
	{
		final ItemInformation li = ItemInformation.getInstance();
		final MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
		IItem item = li.getEquipById(itemid);
		item.setPosition(slot);
		equip.addFromDB(item);
	}

	public void write(Object o)
	{
		c.getSession().writeAndFlush(o);
	}

	public void showHilla()
	{
		try
		{
			c.getSession().writeAndFlush(MainPacketCreator.showMapEffect("phantom/hillah"));
			MapleNPC hilla = new MapleNPC(1402400, new MapleNPCStats("Hilla"));
			hilla.setPosition(new Point(-131, -2));
			hilla.setCy(-7);
			hilla.setF(1);
			hilla.setFh(12);
			hilla.setRx0(-181);
			hilla.setRx1(-81);
			MapleNPC guard1 = new MapleNPC(1402401, new MapleNPCStats("Hilla's Guard"));
			guard1.setPosition(new Point(-209, -2));
			guard1.setCy(-7);
			guard1.setF(1);
			guard1.setFh(12);
			guard1.setRx0(-259);
			guard1.setRx1(-159);
			MapleNPC guard2 = new MapleNPC(1402401, new MapleNPCStats("Hilla's Guard"));
			guard2.setPosition(new Point(-282, -2));
			guard2.setCy(-7);
			guard2.setF(1);
			guard2.setFh(12);
			guard2.setRx0(-332);
			guard2.setRx1(-232);
			MapleNPC guard3 = new MapleNPC(1402401, new MapleNPCStats("Hilla's Guard"));
			guard3.setPosition(new Point(-59, -2));
			guard3.setCy(-7);
			guard3.setF(1);
			guard3.setFh(12);
			guard3.setRx0(-109);
			guard3.setRx1(-9);
			c.getSession().writeAndFlush(MainPacketCreator.spawnNPC(hilla, true));
			c.getSession().writeAndFlush(MainPacketCreator.spawnNPC(guard1, true));
			c.getSession().writeAndFlush(MainPacketCreator.spawnNPC(guard2, true));
			c.getSession().writeAndFlush(MainPacketCreator.spawnNPC(guard3, true));
			Thread.sleep(6000);
		}
		catch (InterruptedException e)
		{
		}
		NPCScriptManager.getInstance().start(c.getPlayer().getClient(), 1104201, "PTtutor500_2");
	}

	public void showSkaia()
	{
		try
		{
			c.getSession().writeAndFlush(MainPacketCreator.showMapEffect("phantom/skaia"));
			Thread.sleep(8000);
		}
		catch (InterruptedException e)
		{
		}
		NPCScriptManager.getInstance().start(c.getPlayer().getClient(), 1104201, "PTtutor500_3");
	}

	public void showPhantomWait()
	{
		try
		{
			c.getSession().writeAndFlush(MainPacketCreator.showMapEffect("phantom/phantom"));
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
		}
		NPCScriptManager.getInstance().start(c.getPlayer().getClient(), 1104201, "PTtutor500_4");
	}
            public void openCS() {
        InterServerHandler.EnterCS(c, c.getPlayer(), false);
    }

	public void movePhantom()
	{
		try
		{
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 3, 2));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 2000));
			Thread.sleep(2000);
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 3, 0));
		}
		catch (InterruptedException e)
		{
		}
		NPCScriptManager.getInstance().start(c.getPlayer().getClient(), 1104201, "PTtutor500_1");
	}

	public void showPhantomMovie()
	{
		warp(150000000);
		try
		{
			c.getSession().writeAndFlush(UIPacket.playMovie("phantom.avi", true));
			Thread.sleep(4 * 60 * 1000); // 4 minutes
		}
		catch (InterruptedException e)
		{
		}
		MapleQuest.getInstance(25000).forceComplete(c.getPlayer(), 1402000);
		c.getSession().writeAndFlush(UIPacket.getDirectionStatus(false));
		c.getSession().writeAndFlush(UIPacket.IntroEnableUI(0));
	}

	public void sendNextNoESC(int delay, String text)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				sendNextNoESC(text);
			}
		}, delay);
	}

	public void sendNextS(int deay, String text, int type)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				sendNextS(text, (byte) type);
			}
		}, deay);
	}

	public void sendNextPrevS(int deay, String text, int type)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				sendNextPrevS(text, (byte) type);
			}
		}, deay);
	}

	public void sendNextNoESC(int delay, String text, int npcid)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				sendNextNoESC(text, npcid);
			}
		}, delay);
	}

	public void warp(int delay, int mapid, int portal)
	{
		EtcTimer.getInstance().schedule(new Runnable()
		{
			@Override
			public void run()
			{
				warp(mapid, portal);
			}
		}, delay);
	}

	public void mihileNeinheartDisappear()
	{
		try
		{
			c.getSession()
					.write(UIPacket.getDirectionInfo("Effect/Direction7.img/effect/tuto/step0/4", 2000, 0, -100, 1, 0));
			c.getSession().writeAndFlush(UIPacket.directionFacialExpression(6, 2000));
			c.getPlayer().getClient().getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 2000));
			Thread.sleep(2000);
			NPCScriptManager.getInstance().start(c, 1106000, "tuto002");
		}
		catch (InterruptedException e)
		{
		}
	}

	public void mihileMove913070001()
	{
		try
		{
			c.getPlayer().getClient().getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 3, 2));
			c.getPlayer().getClient().getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 800));
			Thread.sleep(800);
		}
		catch (InterruptedException e)
		{
		}
		c.getSession().writeAndFlush(UIPacket.IntroEnableUI(0));
		c.getSession().writeAndFlush(UIPacket.IntroDisableUI(false));
		while (c.getPlayer().getLevel() < 2)
		{
			c.getPlayer().levelUp();
		}
		c.getPlayer().setExp(0);
		warp(913070001, 0);
		c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
	}

	public void mihileSoul()
	{
		try
		{
			c.getSession()
					.write(UIPacket.getDirectionInfo("Effect/Direction7.img/effect/tuto/soul/0", 4000, 0, -100, 1, 0));
			Thread.sleep(4000);
		}
		catch (InterruptedException e)
		{
		}
		NPCScriptManager.getInstance().start(c, 1106000, "tuto003");
	}

	public void mihileMove913070050()
	{
		try
		{
			c.getPlayer().getClient().getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 3, 2));
			c.getPlayer().getClient().getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 6000));
			Thread.sleep(5000);
			c.getPlayer().getClient().getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 3, 0));
			NPCScriptManager.getInstance().start(c, 1106001, "tuto005");
		}
		catch (InterruptedException e)
		{
		}
	}

	public void mihileAssailantSummon()
	{
		for (int i = 0; i < 10; i++)
		{
			c.getPlayer().getMap().spawnMonster_sSack(MapleLifeProvider.getMonster(9001050), new Point(240, 65), 0);
		}
		c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
	}

	public List<Triple<Short, String, Integer>> rankList(short[] ranks, String[] names, int[] values)
	{
		List<Triple<Short, String, Integer>> list = new LinkedList<Triple<Short, String, Integer>>();
		if (ranks.length != names.length || names.length != values.length || values.length != ranks.length)
		{
			return null;
		}
		for (int i = 0; i < ranks.length; i++)
		{
			list.add(new Triple<>(ranks[i], names[i], values[i]));
		}
		return list;
	}

	public boolean partyHaveItem(int itemid, short quantity)
	{
		if (getPlayer().getParty() == null)
		{
			return false;
		}
		for (MaplePartyCharacter chr : getPlayer().getParty().getMembers())
		{
			for (ChannelServer channel : ChannelServer.getAllInstances())
			{
				MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
				if (ch != null)
				{
					if (!ch.haveItem(itemid, quantity))
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	public final boolean scrollItem(final short scroll, final short item)
	{
		return InventoryHandler.UseUpgradeScroll((byte) scroll, (byte) item, getClient(), getPlayer());
	}

	public long currentTimeMillis()
	{
		return System.currentTimeMillis();
	}

	/*
	 * public final int WEAPON_RENTAL = 57463816; public int weaponRentalState() {
	 * if (c.getPlayer().getIntNoRecord(WEAPON_RENTAL) == 0) { return 0; } return
	 * (System.currentTimeMillis() / (60 * 1000) -
	 * c.getPlayer().getIntNoRecord(WEAPON_RENTAL)) >= 15 ? 1 : 2; } public void
	 * setWeaponRentalUnavailable() {
	 * c.getPlayer().getQuestNAdd(MapleQuest.getInstance(WEAPON_RENTAL)).
	 * setCustomData("" + System.currentTimeMillis() / (60 * 1000)); }
	 */
	public MapleQuest getQuestById(int questId)
	{
		return MapleQuest.getInstance(questId);
	}

	public int getEquipLevelById(int itemId)
	{
		ItemInformation ii = ItemInformation.getInstance();
		return ii.getEquipStats(itemId).get("reqLevel").intValue();
	}

	public void sendGMBoard(String url)
	{
		// c.getSession().writeAndFlush(MainPacketCreator.gmBoard(1, url));
	}

	public void addPendantSlot(int days)
	{
		c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.PENDANT_SLOT))
				.setCustomData(String.valueOf(System.currentTimeMillis() + ((long) days * 24 * 60 * 60 * 1000)));
	}

	public long getCustomMeso()
	{
		return c.getPlayer().getLongNoRecord(GameConstants.CUSTOM_BANK);
	}

	public void setCustomMeso(long meso)
	{
		c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.CUSTOM_BANK)).setCustomData(meso + "");
	}

	public void enter_931060110()
	{
		try
		{
			c.getSession().writeAndFlush(
					UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/menuUI", 6000, 285, 186, 1, 0));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 900));
			Thread.sleep(900);
			c.getSession().writeAndFlush(UIPacket.showInfo("First, click MENU at the bottom of the screen."));
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseMoveToMenu", 1740, -114, -14, 1, 3));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1680));
			Thread.sleep(1680);
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseClick",
					1440, 246, 196, 1, 3));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1440));
			Thread.sleep(1440);
			c.getSession().writeAndFlush(UIPacket.showInfo("Now, select Go to Farm."));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 600));
			Thread.sleep(600);
			c.getSession().writeAndFlush(
					UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/menuOpen", 50000, 285, 186, 1, 2));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 600));
			Thread.sleep(600);
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseMoveToMyfarm", 750, 246, 196, 1, 2));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 720));
			Thread.sleep(720);
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/menuMouseOver", 50000, 285, 186, 1, 2));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseClick",
					50000, 246, 166, 1, 3));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1440));
			Thread.sleep(1440);
		}
		catch (InterruptedException ex)
		{
		}
	}

	public void enter_931060120()
	{
		try
		{
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/character",
					120000, -200, 0, 1, 1));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1200));
			Thread.sleep(1200);
			c.getSession().writeAndFlush(UIPacket.showInfo("Hover over any other character..."));
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseMoveToChar", 1680, -400, -210, 1, 3));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1650));
			Thread.sleep(1650);
			c.getSession().writeAndFlush(
					UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseUp", 600, -190, -30, 1, 3));
			c.getSession().writeAndFlush(UIPacket.showInfo("Then right-click."));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 540));
			Thread.sleep(540);
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseClick",
					1200, -190, -30, 1, 3));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1200));
			Thread.sleep(1200);
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/characterMenu", 50000, -200, 0, 1, 2));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 900));
			Thread.sleep(900);
			c.getSession().writeAndFlush(UIPacket.showInfo("When the Character Menu appears, click Go to Farm."));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo(
					"Effect/CharacterEff.img/farmEnterTuto/mouseMoveToOtherfarm", 1440, -190, -30, 1, 5));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1380));
			Thread.sleep(1380);
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/menuMouseOver", 50000, -200, 0, 1, 4));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo("Effect/CharacterEff.img/farmEnterTuto/mouseClick",
					60000, -130, 150, 1, 6));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo((byte) 1, 1200));
			Thread.sleep(1200);
		}
		catch (InterruptedException ex)
		{
		}
	}

	public void np_tuto_0_2()
	{
		try
		{
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo(3, 2));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo(1, 10));
			Thread.sleep(10);
			c.getSession().writeAndFlush(UIPacket.directionFacialExpression(5, 3000));
			c.getSession().writeAndFlush(UIPacket
					.getDirectionInfo("Effect/DirectionNewPirate.img/newPirate/balloonMsg1/1", 2000, 0, -80, 0, 0));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo(1, 1000));
			Thread.sleep(1000);
		}
		catch (InterruptedException ex)
		{
		}
		NPCScriptManager.getInstance().dispose(c);
		c.removeClickedNPC();
		NPCScriptManager.getInstance().start(c, 9270083, "np_tuto_0_3");
	}

	public static String getMobImg(int mob)
	{
		MapleMonster monster = MapleLifeProvider.getMonster(mob);
		if (monster.getStats().getLink() != 0)
		{
			mob = monster.getStats().getLink();
		}
		String mobStr = String.valueOf(mob);
		while (mobStr.length() < 7)
		{
			String newStr = "0" + mobStr;
			mobStr = newStr;
		}
		return "#fMob/" + mobStr + ".img/stand/0#";
	}
        public void SearchItem(String gomgo) {
        NPCConversationManager cm = this;
        if (gomgo.getBytes().length < 4) {
            cm.sendOk("검색어는 2글자 이상이어야 합니다.");
            cm.dispose();
            return;
        }
        c.send(UIPacket.greenShowInfo(gomgo + "(으)로 검색중입니다. 잠시만 기다려주세요."));
        String chat = "";
        ItemInformation ii = ItemInformation.getInstance();
        int g = 0;
        List<String> retItems = new ArrayList<String>();
        for (Pair<Integer, String> itemPair : ItemInformation.getInstance().getAllEquips()) {
            if (itemPair.getRight().toLowerCase().contains(gomgo.toLowerCase())) {
                if (ii.isCash(itemPair.getLeft())) {
                    if (itemPair.getLeft() / 100000 >= 1) {
                        chat += "#L" + itemPair.getLeft() + "# #i" + itemPair.getLeft() + "# #fs14##b" + itemPair.getRight() + "#fs12##k#l\r\n";
                        g++;
                    }
                }
            }
        }
        if (g != 0) {
            cm.sendSimple(chat);
        } else {
            chat = "발견된 아이템이 없습니다.";
            cm.sendOk(chat);
            cm.dispose();
        }
    }

	public void showAdvanturerBoatScene()
	{
		try
		{
			c.getSession().writeAndFlush(UIPacket.getDirectionStatus(true));
			c.getSession().writeAndFlush(UIPacket.IntroEnableUI(1));
			c.getSession().writeAndFlush(MainPacketCreator.playSound("advStory/whistle"));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo(1, 208));
			Thread.sleep(208);
			c.getSession().writeAndFlush(UIPacket.showWZEffect("Effect/Direction3.img/adventureStory/Scene2"));
			Thread.sleep(3000);
		}
		catch (InterruptedException ex)
		{
		}
		NPCScriptManager.getInstance().dispose(c);
		c.removeClickedNPC();
		NPCScriptManager.getInstance().start(c, 10306, "ExplorerTut07");
	}

	public void showMapleLeafScene()
	{
		try
		{
			c.getSession().writeAndFlush(UIPacket.getDirectionStatus(true));
			c.getSession().writeAndFlush(UIPacket.IntroEnableUI(1));
			c.getSession().writeAndFlush(MainPacketCreator.showEnterEffect("adventureStory/mapleLeaf/0"));
			c.getSession().writeAndFlush(UIPacket.getDirectionInfo(1, 1800));
			Thread.sleep(1800);
		}
		catch (InterruptedException ex)
		{
		}
		c.getSession().writeAndFlush(UIPacket.IntroEnableUI(0));
		NPCScriptManager.getInstance().dispose(c);
		c.removeClickedNPC();
		NPCScriptManager.getInstance().start(c, 10306, "ExplorerTut08");
	}

	public final void UnlockHonor()
	{
		// c.getPlayer().HonorUnlock();
		c.getPlayer().dropMessage(5, "Slot 1 Inner potential opened.");
	}

	public final void UnlockHonor2()
	{
		// c.getPlayer().HonorUnlock2();
		c.getPlayer().dropMessage(5, "Slot 2 Inner potential opened.");
	}

	public final void UnlockHonor3()
	{
		// c.getPlayer().HonorUnlock3();
		c.getPlayer().dropMessage(5, "Slot 3 Inner potential opened.");
	}

	public int AverageLevel(MapleCharacter chr)
	{
		int a = 0;
		for (final MapleCharacter partymem : chr.getClient().getChannelServer().getPartyMembers(chr.getParty()))
		{
			a += partymem.getLevel();
		}
		return (a / chr.getParty().getMembers().size());
	}

	public void startTime()
	{
		getPlayer().time = System.currentTimeMillis();
	}

	public int getTime()
	{
		return (int) ((System.currentTimeMillis() - getPlayer().time) / 1000);
	}

	public void openAuction()
	{
		c.getSession().writeAndFlush(UIPacket.OpenUI((byte) 0xA1));
	}

	public void HyperSkillMax()
	{
		MapleData data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz"))
				.getData(StringUtil.getLeftPaddedStr("" + c.getPlayer().getJob(), '0', 3) + ".img");
		final ISkill skills = null;
		byte maxLevel = 0;
		for (MapleData skill : data)
		{
			if (skill != null)
			{
				for (MapleData skillId : skill.getChildren())
				{
					if (!skillId.getName().equals("icon"))
					{
						maxLevel = (byte) MapleDataTool.getIntConvert("maxLevel", skillId.getChildByPath("common"), 0);
						if (MapleDataTool.getIntConvert("invisible", skillId, 0) == 0
								&& MapleDataTool.getIntConvert("reqLev", skillId, 0) > 0)
						{ // 스킬창에 안보이는 스킬은 올리지않음
							if (c.getPlayer().getLevel() >= MapleDataTool.getIntConvert("reqLev", skillId, 0))
							{
								c.getPlayer().changeSkillLevel(
										SkillFactory.getSkill(Integer.parseInt(skillId.getName())), maxLevel,
										SkillFactory.getSkill(Integer.parseInt(skillId.getName())).isFourthJob()
												? maxLevel
												: 0);
							}
						}
					}
				}
			}
		}
	}

    public void startDamageMeter() {
        resetMap(120000102);
        final OverrideMonsterStats overrideStats = new OverrideMonsterStats(Long.MAX_VALUE, 1, 0, false);
        MapleMonster mob = MapleLifeProvider.getMonster(1210102);
        mob.setHp(Long.MAX_VALUE);
        mob.setOverrideStats(overrideStats);
        mob.setScale(300);
        c.getChannelServer().getMapFactory().getMap(120000102).spawnMonsterOnGroundBelow(mob, new Point(6, 150));
        warp(120000102);
        c.getPlayer().setDamageMeter(0);
        c.getSession().writeAndFlush(MainPacketCreator.getClock(35));
        final int cRand = Randomizer.nextInt();
        c.getPlayer().setWarpRand(cRand);

        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (cRand == c.getPlayer().getWarpRand()) {
                    int id = getDamageMeterRankerId();
                    initDamageMeter(c.getPlayer().getId(), c.getPlayer().getName(), c.getPlayer().getDamageMeter());
                    c.getPlayer().dropMessage(5, "누적 데미지 : " + c.getPlayer().getDamageMeter() + " 기록이 저장 되었습니다.");
                    c.getPlayer().setDamageMeter(0);
                    c.getPlayer().setWarpRand(-1);
                    warp(100000000);
                    if (isDamageMeterRanker(c.getPlayer().getId())) {
                        if (id != -1) {
                            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                                for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters().values()) {
                                    if (chr.getId() == id) {
                                        //chr.removeAllEquip(1142187, false);
                                        //chr.dropMessage(5, "ZI존 딜량1등 자격이 박탈되어, 칭호가 회수되었습니다.");
                                    }
                                }
                            }
                        }
                        WorldBroadcasting.broadcastMessage(MainPacketCreator.OnAddPopupSay(9000036, 3000, "#b" + c.getPlayer().getName() + "#k님이 새로운 #e딜량 미터기 1위#k가 되었습니다.", ""));
                        //gainItemAllStat(1142187, (short) 1, (short) 100, (short) 30);
                    }
                }
            }
		}, 35 * 1000);
    }

    void initDamageMeter(int cid, String name, long damage) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM damagerank WHERE cid = ?");
            ps.setInt(1, cid);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("INSERT INTO damagerank(cid, name, damage) VALUES (?, ?, ?)");
            ps.setInt(1, cid);
            ps.setString(2, name);
            ps.setLong(3, damage);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String DamageMeterRank() {
        String text = "#b";
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM damagerank ORDER BY damage DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                text += "#r#e" + (i != 10 ? "0" : "") + i + "#n#b위 #r닉네임#b " + rs.getString("name") + " #r누적 데미지#b " + Comma(rs.getLong("damage")) + "\r\n";
                i++;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (text.equals("#b")) {
            text = "#r아직까지 딜량 미터기를 갱신한 유저가 없습니다.";
        }
        return text;
    }

    public boolean isDamageMeterRanker(int cid) {
        boolean value = false;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM damagerank ORDER BY damage DESC LIMIT 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cid") == cid) {
                    value = true;
                }
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

    public int getDamageMeterRankerId() {
        int value = -1;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM damagerank ORDER BY damage DESC LIMIT 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getInt("cid");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

	public void gainItemAllStat(int itemid, short quantity, short allstat)
	{
		gainItemAllStat(itemid, quantity, allstat, (short) -1);
	}

	public void gainItemAllStat(int itemid, short quantity, short allstat, short wmtk)
	{
		Equip equip = new Equip(itemid, quantity, (byte) 0);
		equip.setStr(allstat);
		equip.setDex(allstat);
		equip.setInt(allstat);
		equip.setLuk(allstat);
		if (wmtk != -1)
		{
			equip.setWatk(wmtk);
			equip.setMatk(wmtk);
		}
		InventoryManipulator.addFromDrop(c, equip, true);
	}

	public void gainItemAllStat(int itemid, short quantity, short allstat, short wmtk, int up)
	{
		Equip equip = new Equip(itemid, quantity, (byte) 0);
		equip.setStr(allstat);
		equip.setDex(allstat);
		equip.setInt(allstat);
		equip.setLuk(allstat);
		equip.setUpgradeSlots((byte) up);
		if (wmtk != -1)
		{
			equip.setWatk(wmtk);
			equip.setMatk(wmtk);
		}
		InventoryManipulator.addFromDrop(c, equip, true);
	}

	public void gainPCTime(long t)
	{
		getPlayer().gainPT(System.currentTimeMillis() + (t * 1000));
	}

	public void gainItemAllStat(int itemid, short quantity, short allstat, short wmtk, byte ups, byte level)
	{
		Equip equip = new Equip(itemid, quantity, (byte) 0);
		equip.setStr(allstat);
		equip.setDex(allstat);
		equip.setInt(allstat);
		equip.setLuk(allstat);
		if (ups != -1)
		{
			equip.setUpgradeSlots(ups);
		}
		if (wmtk != -1)
		{
			equip.setWatk(wmtk);
			equip.setMatk(wmtk);
		}
		if (level != -1)
		{
			equip.setItemLevel((byte) (equip.getItemLevel() - level));
		}
		InventoryManipulator.addFromDrop(c, equip, true);
	}

	public void gainItemAllStat(int itemid, short quantity, short allstat, short wmtk, byte ups, byte level, byte ign,
			byte boss)
	{
		Equip equip = new Equip(itemid, quantity, (byte) 0);
		equip.setStr(allstat);
		equip.setDex(allstat);
		equip.setInt(allstat);
		equip.setLuk(allstat);
		if (ign != -1)
		{
			equip.setIgnoreWdef(ign);
		}
		if (boss != -1)
		{
			equip.setBossDamage(boss);
		}
		if (ups != -1)
		{
			equip.setUpgradeSlots(ups);
		}
		if (wmtk != -1)
		{
			equip.setWatk(wmtk);
			equip.setMatk(wmtk);
		}
		if (level != -1)
		{
			equip.setItemLevel((byte) (equip.getItemLevel() - level));
		}
		InventoryManipulator.addFromDrop(c, equip, true);
	}

	public boolean isNamedTimeOver()
	{
		String[] ret = Named.namedTime.replaceAll("분", "").replaceAll("초", "").split(" ");
		int v1 = Integer.parseInt(ret[0]) * (1000 * 60);
		int v2 = Integer.parseInt(ret[1]) * 1000;
		return (v1 + v2) <= 40000;
	}

    public void installNamed(int RC, int seletion)
    {
        if (isNamedTimeOver())
        {
            sendOk("이번 회차에 배팅 할 수 있는 시간을 초과 하였습니다.");
            dispose();
            return;
        }
        if (getPlayer().getMapId() != 10000 && RC < 10000)
        {
            sendOk("10000이하 후원포인트는 배팅이 불가능합니다.");
            dispose();
            return;
        }
        Named.install_named_char(getPlayer().getId(), 0, 0, (int) (RC * (seletion == 0 || seletion == 1 ? 1.9 : seletion == 2 || seletion == 3 || seletion == 4 || seletion == 5 ? 1.9 : seletion == 6 || seletion == 7 || seletion == 8 || seletion == 9 ? 3.6 : 0)), seletion, Named.nextDate + "-" + Named.nextTime);
        sendOk("배팅을 완료 하였습니다. 좋은 결과가 나오면 좋겠네요");
        loseRC(RC);
        dispose();
    }

	public String Comma(long r)
	{
		if (true)
		{
			return String.valueOf(r);
		}
		String re = "";
		for (int i = String.valueOf(r).length(); i >= 1; i--)
		{
			if (i != 1 && i % 3 == 1)
			{
				re += ",";
			}
			re += String.valueOf(r).charAt(i - 1);

		}
		return new StringBuilder().append(re).reverse().toString();
	}

	public void setJagure(int mobid)
	{
		getPlayer().setKeyValue2("CapturedJaguar", mobid);
		getPlayer().send(MainPacketCreator.updateJaguar(getPlayer()));
	}

	public int getConnect()
	{
		return WorldConnected.getConnectedi();
	}

	public void snedPacket(short header, String hex)
	{
		WritingPacket p = new WritingPacket();
		p.writeShort(header);
		p.write(HexTool.getByteArrayFromHexString(hex));
		c.getSession().writeAndFlush(p.getPacket());
	}

	public String getCashEquipList()
	{
		String text = "";
		for (IItem item : getPlayer().getInventory(MapleInventoryType.EQUIPPED).list())
		{
			if (ItemInformation.getInstance().getEquipStats(item.getItemId()).get("cash") > 0)
			{
				text += "#L" + item.getPosition() + "# #i" + item.getItemId() + "# #t" + item.getItemId() + "##l\r\n";
			}
		}
		return text;
	}

	public void renewCashPotential(short pos)
	{
		Equip item = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(pos);
		if (item != null)
		{
			if (item.getState() != 0)
			{
				item.renewPotential();
			}
			else
			{
				item.setState((byte) 1);
			}
			if (item.getState() == 1)
			{ // 레어
				int rand = Randomizer.nextInt(100);
				if (rand < 5)
				{ // 에픽
					item.setState((byte) 18);
				}
				else if (rand < 3)
				{ // 유니크
					item.setState((byte) 19);
				}
				else
				{
					item.setState((byte) 17);
				}
			}
			else
			{
				item.setState((byte) (item.getState() + 16));
			}
			int level = item.getState() - 16;
			int temp = level;
			int a = 0;
			while (temp > 1)
			{
				if (temp > 1)
				{
					--temp;
					++a;
				}
			}
			item.setPotential1(potential(level, true, item.getItemId()));
			item.setPotential2(potential(level, false, item.getItemId()));
			getPlayer().getMap().broadcastMessage(MainPacketCreator.showCubeEffect(getPlayer().getId(), 2430091));
			getPlayer().forceReAddItem(item, MapleInventoryType.EQUIPPED);
		}
	}

	public String getHairName(int code)
	{
		return ItemInformation.getInstance().getName(code);
	}

	public int getManneHair(int slot)
	{
		String qd = c.getPlayer().getInfoQuest(26544);
		int hair = Integer.parseInt(qd.split("h" + slot + "=")[1].split(";")[0]);
		return hair;
	}

	public void updateManne(int slot, boolean update)
	{
		String qd = c.getPlayer().getInfoQuest(26544);
		String data = "h" + slot + "=" + c.getPlayer().getHair();
		int hair = Integer.parseInt(qd.split("h" + slot + "=")[1].split(";")[0]);
		if (update)
		{
			qd = qd.replaceAll("h" + slot + "=" + hair, data);
		}
		final MapleQuestStatus oldStatus = c.getPlayer().getQuest(MapleQuest.getInstance(26544));
		final MapleQuestStatus newStatus = new MapleQuestStatus(MapleQuest.getInstance(26544), (byte) 1, 0);
		newStatus.setCompletionTime(oldStatus.getCompletionTime());
		newStatus.setForfeited(oldStatus.getForfeited());
		c.getPlayer().updateQuest(newStatus);
		c.getPlayer().updateInfoQuest(26544, qd);
		c.sendPacket(MainPacketCreator.OnMannequinResult(slot, update));
		c.sendPacket(MainPacketCreator.SkillUseResult((byte) 0));
		c.sendPacket(MainPacketCreator.resetActions(c.getPlayer()));
	}

	public void resetMannequine()
	{
		c.sendPacket(MainPacketCreator.OnMannequinResult(c.getPlayer().selMannequineSlot, false));
	}

	public void sendTalk(String text, byte type, byte lastMsg, byte p, int npcidd, boolean prev, boolean next,
			int seconds)
	{

		if (this.lastMsg > -1)
		{
			return;
		}
		NPCTalk t = new NPCTalk(type, id, lastMsg);
		t.setText(text);
		t.setParam(p);
		t.setNpcIDD(npcidd);
		t.setPrev(prev);
		t.setNext(next);
		t.setSeconds(seconds);
		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		this.lastMsg = lastMsg;
	}

	public void sendTalk(String text, byte type, byte lastMsg, byte p, byte color, int npcidd, boolean prev,
			boolean next, int seconds)
	{

		if (this.lastMsg > -1)
		{
			return;
		}
		NPCTalk t = new NPCTalk(type, id, lastMsg, p, color);
		t.setText(text);
		t.setNpcIDD(npcidd);
		t.setPrev(prev);
		t.setNext(next);
		t.setSeconds(seconds);
		c.getSession().writeAndFlush(MainPacketCreator.getNPCTalk(t));
		this.lastMsg = lastMsg;
	}

	public void zeroWeaponUpgrade()
	{
		IEquip beta = (IEquip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
		IEquip alpha;
		int betatype = -10;
		int alphatype = -11;
		alpha = (IEquip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);
		Equip nalphatype = (Equip) alpha;
		Equip nbetatype = (Equip) beta;
		nbetatype.setItemId(nbetatype.getItemId() + 1);
		nalphatype.setItemId(nalphatype.getItemId() + 1);
		if (nbetatype.getItemId() == 1562001)
		{
			nalphatype.setWatk((short) 100);

			nbetatype.setWatk((short) 102);
			nbetatype.setWdef((short) 80);
			nbetatype.setMdef((short) 35);
			nbetatype.addUpgradeSlots((byte) 7);
		}
		else if (nbetatype.getItemId() == 1562002)
		{
			nalphatype.addWatk((short) 3); // 103

			nbetatype.addWatk((short) 3); // 105
			nbetatype.addWdef((short) 10); // 90
			nbetatype.addMdef((short) 5); // 40
		}
		else if (nbetatype.getItemId() == 1562003)
		{
			nalphatype.addWatk((short) 2); // 105

			nbetatype.addWatk((short) 2); // 107
			nbetatype.addWdef((short) 10); // 100
			nbetatype.addMdef((short) 5); // 45
		}
		else if (nbetatype.getItemId() == 1562004)
		{
			nalphatype.addWatk((short) 7); // 112

			nbetatype.addWatk((short) 7); // 114
			nbetatype.addWdef((short) 10); // 110
			nbetatype.addMdef((short) 5); // 50
		}
		else if (nbetatype.getItemId() == 1562005)
		{
			nalphatype.addStr((short) 8);
			nalphatype.addDex((short) 4);
			nalphatype.addWatk((short) 5); // 117
			nalphatype.addAcc((short) 50); // 50
			nalphatype.addUpgradeSlots((byte) 1);

			nbetatype.addStr((short) 8);
			nbetatype.addDex((short) 4);
			nbetatype.addWatk((short) 7); // 121
			nbetatype.addWdef((short) 10); // 120
			nbetatype.addMdef((short) 5); // 55
			nbetatype.addAcc((short) 50); // 50
			nbetatype.addUpgradeSlots((byte) 1);
		}
		else if (nbetatype.getItemId() == 1562006)
		{
			nalphatype.addStr((short) 27); // 35
			nalphatype.addDex((short) 16); // 20
			nalphatype.addWatk((short) 18); // 135
			nalphatype.addAcc((short) 50); // 100

			nbetatype.addStr((short) 27); // 35
			nbetatype.addDex((short) 16); // 20
			nbetatype.addWatk((short) 18); // 139
			nbetatype.addWdef((short) 10); // 130
			nbetatype.addMdef((short) 5); // 60
			nbetatype.addAcc((short) 50); // 100
		}
		else if (nbetatype.getItemId() == 1562007)
		{
			nalphatype.addStr((short) 5); // 40
			nalphatype.addDex((short) 20); // 40
			nalphatype.addWatk((short) 34); // 169
			nalphatype.addAcc((short) 20); // 120
			nalphatype.addBossDamage((byte) 30); // 30
			nalphatype.addIgnoreWdef((short) 10); // 10

			nbetatype.addStr((short) 5); // 40
			nbetatype.addDex((short) 20); // 40
			nbetatype.addWatk((short) 34); // 174
			nbetatype.addWdef((short) 20); // 150
			nbetatype.addMdef((short) 10); // 70
			nbetatype.addAcc((short) 20); // 120
			nbetatype.addBossDamage((byte) 30); // 30
			nbetatype.addIgnoreWdef((short) 10); // 10
		}
		c.send(ZeroSkill.WeaponLevelUp());
		c.send(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, nalphatype));
		c.send(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, nbetatype));
	}

	public void setInnerAbility(int level)
	{
		if (level >= 30)
		{
			InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(0, -1);
			c.getPlayer().getInnerSkills().add(isvh);
			c.getPlayer().changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(),
					isvh.getSkillLevel());
		}
		else if (level >= 60)
		{
			InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(Randomizer.rand(0, 2), -1);
			c.getPlayer().getInnerSkills().add(isvh);
			c.getPlayer().changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(),
					isvh.getSkillLevel());
		}
		else if (level >= 100)
		{
			InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(Randomizer.rand(1, 3), -1);
			c.getPlayer().getInnerSkills().add(isvh);
			c.getPlayer().changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(),
					isvh.getSkillLevel());
		}
	}

	public void setInnerStats()
	{
		c.getPlayer().innerLevelUp();
	}
        
	public String searchCashItem(String t)
	{
		String ret = "";
		for (Pair<Integer, String> item : ItemInformation.getInstance().getAllEquips())
		{
			if (item.right.contains(t))
			{
				if (ItemInformation.getInstance().isCash(item.left))
				{
					ret += "#b#L" + item.left + "# #i" + item.left + "##t" + item.left + "##l\r\n";
				}
			}
		}
		return ret;
	}

	public void setDeathCount(int i)
	{
		c.getPlayer().bossDeathCount = i;
		c.sendPacket(MainPacketCreator.getDeathCount(c.getPlayer().bossDeathCount));
	}

	public long parseLong(String t)
	{
		return t == null ? -1 : Long.parseLong(t);
	}

	public String updateEquipStat(byte pos, int nextE)
	{
		Equip eq = (Equip) getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(pos);
		String t = "초월 강화로 변화된 스탯 입니다.\r\n#i" + eq.getItemId() + "##b#t" + eq.getItemId() + "##k (#b" + nextE
				+ "강#k)\r\n\r\n";
		if (eq.getStr() > 0)
		{
			t += "STR : #e" + eq.getStr() + "#n -> ";
			eq.setStr((short) (eq.getStr() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getStr() + "#n#k\r\n";
		}
		if (eq.getDex() > 0)
		{
			t += "DEX : #e" + eq.getDex() + "#n -> ";
			eq.setDex((short) (eq.getDex() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getDex() + "#n#k\r\n";
		}
		if (eq.getInt() > 0)
		{
			t += "INT : #e" + eq.getInt() + "#n -> ";
			eq.setInt((short) (eq.getInt() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getInt() + "#n#k\r\n";
		}
		if (eq.getLuk() > 0)
		{
			t += "LUK : #e" + eq.getLuk() + "#n -> ";
			eq.setLuk((short) (eq.getLuk() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getLuk() + "#n#k\r\n";
		}
		if (eq.getWatk() > 0)
		{
			t += "공격력 : #e" + eq.getWatk() + "#n -> ";
			eq.setWatk((short) (eq.getWatk() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getWatk() + "#n#k\r\n";
		}
		if (eq.getMatk() > 0)
		{
			t += "마력 : #e" + eq.getMatk() + "#n -> ";
			eq.setMatk((short) (eq.getMatk() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getMatk() + "#n#k\r\n";
		}
		if (eq.getHp() > 0)
		{
			t += "HP : #e" + eq.getHp() + "#n -> ";
			eq.setHp((short) (eq.getHp() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getHp() + "#n#k\r\n";
		}
		if (eq.getMp() > 0)
		{
			t += "MP : #e" + eq.getMp() + "#n -> ";
			eq.setMp((short) (eq.getMp() + Randomizer.rand(10, 20)));
			t += "#r#e" + eq.getMp() + "#n#k\r\n";
		}
		eq.setOwner(nextE + "강");
		getPlayer().forceReAddItem(eq, MapleInventoryType.EQUIP);
		return t;
	}
    public void moneyBuff(int skillid) {
        String value[] = {
            "rc_shot",
            "stance",
            "holysymbol",
            "SharpEyes",
            "PartyBooster",
            "ShadowPartner",
            "TrifleWorm",
            "MagicArrow",
            "noir",
            "bling",
            "kinesis"
        };

        int moneybuff[][] = {
            {5321054, 70000},
            {80001140, 10000},
            {2311003, 30000},
            {3121002, 30000},
            {5121009, 30000},
            {4331002, 30000},
            {13101022, 20000},
            {3100010, 20000},
            {24120002, 20000},
            {24100003, 20000},
            {142110011, 20000}};
        if (getPlayer().getRC() < moneybuff[skillid][1]) {
            getClient().getSession().writeAndFlush(UIPacket.greenShowInfo("선택하신 [" + SkillFactory.getSkillName(moneybuff[skillid][0]) + "] 버프는 후원포인트가 부족하여 구매 하실 수 없습니다..."));
            dispose();
            return;
        }
        if (getPlayer().getKeyValue(value[skillid]) != null) {
            getClient().getSession().writeAndFlush(UIPacket.greenShowInfo("선택하신 [" + SkillFactory.getSkillName(moneybuff[skillid][0]) + "] 버프는 이미 구매하여 구매 하실 수 없습니다..."));
            dispose();
            return;
        }
        getPlayer().setKeyValue(value[skillid], "ture");
        getPlayer().gainRC(moneybuff[skillid][1]);
        getPlayer().unlockMaxDamage();
        getClient().getSession().writeAndFlush(UIPacket.greenShowInfo("선택하신 [" + SkillFactory.getSkillName(moneybuff[skillid][0]) + "] 버프를 구매 완료 하였습니다.."));
        dispose();
    }  

	public String getEnhance(String t)
	{
		return !t.contains("강") ? "0강" : t;
	}

	public int getEnhanceI(String t)
	{
		return Integer.parseInt(getEnhance(t).replaceAll("강", ""));
	}

	public void BuyPET(int Petitem)
	{
		int uniqueid = Petitem;
		Item itemr = new Item(Petitem, (short) 1, (short) 1, (short) 0);
		itemr.setExpiration(2475606994921L);
		final MaplePet pet = MaplePet.createPet(Petitem, itemr.getExpiration());
		itemr.setPet(pet);
		itemr.setUniqueId(pet.getUniqueId());
		InventoryManipulator.addbyItem(c, itemr);
		InventoryManipulator.addFromDrop(getClient(), itemr, false);
	}

	public int getAndroidGender()
	{
		int itemid = c.getPlayer().getAndroid().getItemId();
		return ItemInformation.getInstance().getAndroidBasicSettings(ItemInformation.getInstance().getAndroid(itemid))
				.getGender();
	}

	public int getConnectedPlayers()
	{
		int n = 0;
		for (ChannelServer cserv : ChannelServer.getAllInstances())
		{
			for (MapleCharacter name : cserv.getPlayerStorage().getAllCharacters().values())
			{
				n++;
			}
		}
		return n;
    }
            public void CollectivelyShop(int shopid, int npcid) {
        int shoplist[] = {
            // 모든 직업 상점 시작

            10021, // 전사 - 모자 상점
            10018, // 전사 - 상의 상점
            10017, // 전사 - 하의 상점
            10016, // 전사 - 신발 상점
            10019, // 전사 - 전신 상점
            10020, // 전사 - 장갑 상점
            10022, // 전사 - 방패 상점

            3000136, // 전사 - 데스페라도 상점
            10023, // 전사 - 한손 도끼 상점
            10024, // 전사 - 두손 도끼 상점
            10025, // 전사 - 한손 둔기 상점
            10026, // 전사 - 두손 둔기 상점
            10027, // 전사 - 한손검 상점
            10028, // 전사 - 두손검 상점
            10029, // 전사 - 창
            10030, // 전사 - 폴암

            10003, // 법사 - 모자 상점
            10001, // 법사 - 전신 상점 
            10123, // 법사 - 상의 상점
            12124, // 법사 - 하의 상점
            10000, // 법사 - 신발 상점
            10002, // 법사 - 장갑 상점
            10004, // 법사 - 방패 상점

            3000137, // 법사 - 샤이닝 로드 상점
            10125, // 법사 - 드래곤 장비 상점
            10005, // 법사 - 완드
            10006, // 법사 - 스태프

            10034, // 궁수 - 모자 상점
            10121, // 궁수 - 상의 상점
            10122, // 궁수 - 하의 상점
            10032, // 궁수 - 전신 상점
            10031, // 궁수 - 신발 상점
            10033, // 궁수 - 장갑 상점

            10035, // 궁수 - 활 상점
            10036, // 궁수 - 석궁 상점
            10108, // 궁수 - 듀얼궁 상점
            10110, // 궁수 - 마법화살 상점
            10037, // 궁수 - 화살 상점

            10012, // 도적 - 모자 상점
            10008, // 도적 - 상의 상점
            10009, // 도적 - 하의 상점
            10010, // 도적 - 전신 상점
            10007, // 도적 - 신발 상점
            10011, // 도적 - 장갑 상점
            10013, // 도적 - 방패 상점

            3000135, // 도적 - 에너지소드 상점
            10014, // 도적 - 단검 상점
            10015, // 도적 - 아대 상점
            10106, // 도적 - 블레이드 상점
            10109, // 도적 - 케인 상점
            2003, // 도적 - 카드 상점
            10038, // 도적 - 표창 상점

            10090, // 해적 - 모자상점
            10091, // 해적 - 전신상점
            10093, // 해적 - 신발상점
            10092, // 해적 - 장갑상점

            10087, // 해적 - 너클상점
            10088, // 해적 - 총상점
            10107, // 해적 - 핸드캐논상점
            11100, // 해적 - 소울슈터상점
            10089, // 해적 - 볼릿상점

            // 모든 직업 상점 종료

            // 세트 아이템 상점 시작

            1401004, // 템페스트 상점
            9250028, // 여제 상점
            2040000, // 타일런트 상점
            9250027, // 루타비스 상점
            1201002, // 앱솔랩스 상점

            9999999, // 아케인 무기 상점
            9999998, // 아케인 방어구 상점

            // 세트 아이템 상점 종료

            // 공용 상점 시작

            2232100, // 소비 상점
            8000, // 악세 상점 - 얼굴 장식
            8001, // 악세 상점 - 눈장식
            8002, // 악세 상점 - 펜던트
            8003, // 악세 상점 - 견장
            8004, // 악세 상점 - 반지
            8005, // 악세 상점 - 뱃지
            9110005, // 의자 상점

            444446, // 블래스터 무기상점
        };
        openShop(shoplist[shopid]);
        dispose();
    }
        
    public void StartBingoGame() {
        if (c.getPlayer().getMapId() != 922290000) {
            c.getPlayer().changeMap(c.getChannelServer().getMapFactory().getMap(922290000), c.getChannelServer().getMapFactory().getMap(922290000).getPortal(0));
            return;
        }
        if (c.getPlayer().getMap().getCharacters().size() < 5) {
            c.getSession().writeAndFlush(MainPacketCreator.OnAddPopupSay(9000267, 3000, "#face1#빙고 이용시 최소 5명이 필요하다구YO!", ""));
            return;
        }
        c.getPlayer().getMap().broadcastMessage(MainPacketCreator.getClock(30));
        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                for (MapleCharacter chr : c.getPlayer().getMap().getCharacters()) {
                    if (chr != null) {
                        chr.changeMap(922290100, 0);
                    }
                }
            }
        }, 30 * 1000);
        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (c.getPlayer().getMapId() == 922290100) {
                    BingoGame bingo = new BingoGame(c.getPlayer().getMap().getCharacters());
                    for (MapleCharacter chr : c.getPlayer().getMap().getCharacters()) {
                        if (chr != null) {
                            chr.setBingoGame(bingo);
                            chr.getClient().getSession().writeAndFlush(MainPacketCreator.musicChange("BgmEvent/dolphin_night"));
                            chr.getClient().getSession().writeAndFlush(MainPacketCreator.playSE("multiBingo/start"));
                            chr.getClient().getSession().writeAndFlush(MainPacketCreator.showMapEffect("Gstar/start"));
                        }
                    }
                }
            }
        }, 40 * 1000);
        MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                c.getPlayer().getBingoGame().StartGame();
            }
        }, 42 * 1000);
    }
    
    
    public void BuyCashItemNPC(int itemid, short quantity, String GM_LOG)
    {
	Equip equip = new Equip(itemid, quantity, (byte) 0);
	equip.setGMLog(CurrentTime.getAllCurrentTime() + "에 " + GM_LOG + "에서 호출된 gainItem 스크립트로 얻은 아이템.");
        InventoryManipulator.addFromDrop(c, equip, true);
    }
    
    public void BuySponsorItem(int itemid, short quantity, short allstat, short wmtk, String GM_LOG)
	{
	Equip equip = new Equip(itemid, quantity, (byte) 0);
	equip.setStr(allstat);
	equip.setDex(allstat);
	equip.setInt(allstat);
	equip.setLuk(allstat);
	if (wmtk != -1)
	{
            equip.setWatk(wmtk);
            equip.setMatk(wmtk);
	}
        equip.setGMLog(CurrentTime.getAllCurrentTime() + "에 " + GM_LOG + "에서 호출된 gainItem 스크립트로 얻은 아이템.");
	InventoryManipulator.addFromDrop(c, equip, true);
    }
}
