package handlers.GlobalHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.Character.MapleCharacter;
import client.Community.MapleBuddy.BuddylistEntry;
import client.Community.MapleGuild.MapleGuild;
import client.Community.MapleUserTrade;
import client.ItemInventory.MapleInventoryType;
import client.MapleClient;
import client.MapleQuestStatus;
import client.Skills.SkillFactory;
import connections.Database.MYSQL;
import connections.Packets.CashPacket;
import connections.Packets.LoginPacket;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.SkillPackets.ZeroSkill;
import connections.Packets.SoulWeaponPacket;
import connections.Packets.UIPacket;
import constants.Data.QuickMoveEntry;
import constants.GameConstants;
import constants.ServerConstants;
import handlers.ChatHandler.MapleMultiChatCharacter;
import handlers.DueyHandler.DueyHandler;
import java.util.Calendar;
import launcher.AdminGUI.AdminTool;
import launcher.AdminGUI.AdminToolPacket;
import launcher.AdminGUI.AdminToolStart;
import launcher.LauncherHandlers.ChracterTransfer;
import launcher.LauncherHandlers.MapleItemHolder;
import launcher.LauncherHandlers.MaplePlayerIdChannelPair;
import launcher.ServerPortInitialize.AuctionServer;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.ServerPortInitialize.LoginServer;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;
import scripting.NPC.NPCScriptManager;
import server.Items.ItemInformation;
import server.Maps.MapField.FieldLimitType;
import server.Maps.MapleMapHandling.MapleMap;
import server.Quests.MapleQuest;
import server.Shops.IMapleCharacterShop;

public class InterServerHandler {

    public static final void EnterMTS(final MapleClient c) {
        final MapleMap map = c.getChannelServer().getMapFactory().getMap(910000000);
        c.getPlayer().changeMap(map, map.getPortal(0));
    }

    public static final void EnterAuction(MapleClient c) {
        final MapleCharacter chr = c.getPlayer();

        if (!chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }

        final ChannelServer ch = ChannelServer.getInstance(c.getChannel());

        String ip = ServerConstants.getServerHost(c);

        if (ip == null) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Cash Shop is currently unavailable."));
            return;
        }

        if (chr.getTrade() != null) {
            MapleUserTrade.cancelTrade(chr.getTrade());
        }

        final IMapleCharacterShop shop = chr.getPlayerShop();
        if (shop != null) {
            shop.removeVisitor(chr);
            if (shop.isOwner(chr)) {
                shop.setOpen(true);
            }
        }

        if (chr.getMessenger() != null) {
            MapleMultiChatCharacter messengerplayer = new MapleMultiChatCharacter(chr);
            WorldCommunity.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
        }
        try {
            chr.cancelAllBuffs();
        } catch (Exception ex) {

        }
        ChannelServer.addCooldownsToStorage(chr.getId(), chr.getAllCooldowns());
        ChannelServer.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
        ChannelServer.ChannelChange_Data(new ChracterTransfer(chr), chr.getId(), -20);
        ch.removePlayer(chr);
        c.updateLoginState(MapleClient.CHANGE_CHANNEL, c.getSessionIPAddress());
        c.getSession().writeAndFlush(MainPacketCreator.serverMessage(""));
        c.getSession().writeAndFlush(MainPacketCreator.getChannelChange(AuctionServer.PORT, ServerConstants.getServerHost(c)));
        chr.saveToDB(false, false);
        chr.getMap().removePlayer(chr);
        c.setPlayer(null);
    }

    public static final void EnterCS(final MapleClient c, final MapleCharacter chr, final boolean ScriptEnter) {
        if (!chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        if (ScriptEnter && ServerConstants.cshopNpc != 0) {
            if (c.getPlayer().getConversation() != 0) {
                NPCScriptManager.getInstance().dispose(c);
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            }
            NPCScriptManager.getInstance().start(c, ServerConstants.cshopNpc, null);
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final ChannelServer ch = ChannelServer.getInstance(c.getChannel());

        String ip = ServerConstants.getServerHost(c);

        if (ip == null) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Cash Shop is currently unavailable."));
            return;
        }

        if (chr.getTrade() != null) {
            MapleUserTrade.cancelTrade(chr.getTrade());
        }

        final IMapleCharacterShop shop = chr.getPlayerShop();
        if (shop != null) {
            shop.removeVisitor(chr);
            if (shop.isOwner(chr)) {
                shop.setOpen(true);
            }
        }

        if (chr.getMessenger() != null) {
            MapleMultiChatCharacter messengerplayer = new MapleMultiChatCharacter(chr);
            WorldCommunity.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
        }
        try {
            chr.cancelAllBuffs();
        } catch (Exception ex) {

        }
        ChannelServer.addCooldownsToStorage(chr.getId(), chr.getAllCooldowns());
        ChannelServer.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
        ChannelServer.ChannelChange_Data(new ChracterTransfer(chr), chr.getId(), -10);
        ch.removePlayer(chr);
        c.updateLoginState(MapleClient.CHANGE_CHANNEL, c.getSessionIPAddress());
        c.getSession().writeAndFlush(MainPacketCreator.serverMessage(""));
        c.getSession().writeAndFlush(MainPacketCreator.getChannelChange(ServerConstants.CashShopPort, ServerConstants.getServerHost(c))); // default
        chr.saveToDB(false, false);
        chr.getMap().removePlayer(chr);
        c.setPlayer(null);
    }

    public static void Loggedin(final int playerid, final MapleClient c) {
        final ChannelServer channelServer = c.getChannelServer();
        MapleCharacter player;
        final ChracterTransfer transfer = channelServer.getPlayerStorage().getPendingCharacter(playerid);
        boolean checkFromDB = false;
        if (transfer == null) {
            player = MapleCharacter.loadCharFromDB(playerid, c, true);
            checkFromDB = true;
        } else {
            player = MapleCharacter.ReconstructChr(transfer, c, true);
        }
        if (player == null) {
            System.out.println("ERROR!!!!!! CANNOT LOAD CHARACTER FROM DB!!");
            return;
        }
        c.setPlayer(player);
        c.setAccID(player.getAccountID());
        c.loadAuthData();
        c.getPlayer().setMorphGage(0);
        final int state = c.getLoginState();
        boolean allowLogin = false;
        if (state == MapleClient.LOGIN_SERVER_TRANSITION || state == MapleClient.CHANGE_CHANNEL) {
            if (!ChannelServer.isCharacterListConnected(c.loadCharacterNames(), true)) {
                allowLogin = true;
            }
        }
        if (!allowLogin) {
            c.setPlayer(null);
            c.getSession().close();
            if (!ServerConstants.realese) {
            }
            return;
        }
        if (!AdminToolStart.현재접속자.contains(player.getName())) {
            AdminToolStart.동접(player.getName());
            AdminToolStart.접속자수.setText(String.valueOf((int) (Integer.parseInt(AdminToolStart.접속자수.getText()) + 1)));
        }
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters().values()) {
                if (chr.getName().equals(player.getName())) {
                    c.setPlayer(null);
                    c.getSession().close();
                    if (!ServerConstants.realese) {
                    }
                    return;
                }
            }
        }

        if (!LoginServer.getInstance().ip.contains(c.getSessionIPAddress())) {
            c.setPlayer(null);
            c.getSession().close();
            System.out.println("3not allow login - " + c.getAccountName() + " from " + c.getSessionIPAddress() + " / state : " + state);
            return;
        }
        c.updateLoginState(MapleClient.LOGIN_LOGGEDIN, c.getSessionIPAddress());
        
        final ChannelServer cserv = ChannelServer.getInstance(c.getChannel());
        cserv.addPlayer(player);
        c.getSession().writeAndFlush(MainPacketCreator.HeadTitle(player.HeadTitle()));
        c.getSession().writeAndFlush(MainPacketCreator.getPlayerInfo(player));
        player.getMap().addPlayer(player);
        // Daily Gift Enable
       Calendar cal = Calendar.getInstance(); 
       int Month = cal.get(Calendar.MONTH) + 1;
       if (c.getPlayer().getDailyGift().checkDailyGift(c.getAccID(c.getAccountName()))) {
           c.getPlayer().getDailyGift().loadDailyGift(c.getAccID(c.getAccountName()));
       } else {
            c.getPlayer().getDailyGift().InsertDailyData(c.getAccID(c.getAccountName()), 0, 0);
            c.getPlayer().getDailyGift().loadDailyGift(c.getAccID(c.getAccountName()));
        }
       // Daily Gift End
        c.getPlayer().giveCoolDowns(ChannelServer.getCooldownsFromStorage(player.getId()));
        try {
            player.expirationTask();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }

        if (player.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -27) != null && player.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -28) != null) {
            if (player.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -27).getAndroid() != null) {
                player.setAndroid(player.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -27).getAndroid());
            }
        }
        try {
            player.expirationTask();
            try {
                c.getPlayer().giveSilentDebuff(ChannelServer.getDiseaseFromStorage(player.getId()));
            } catch (NullPointerException ex) {

            }
            final int buddyIds[] = player.getBuddylist().getBuddyIds();
            WorldBroadcasting.loggedOn(player.getName(), player.getId(), c.getChannel(), buddyIds);
            final MaplePlayerIdChannelPair[] onlineBuddies = WorldCommunity.multiBuddyFind(player.getId(), buddyIds);
            for (MaplePlayerIdChannelPair onlineBuddy : onlineBuddies) {
                final BuddylistEntry ble = player.getBuddylist().get(onlineBuddy.getCharacterId());
                ble.setChannel(onlineBuddy.getChannel());
                player.getBuddylist().put(ble);
            }
            c.getSession().writeAndFlush(MainPacketCreator.updateBuddylist(player.getBuddylist().getBuddies(), 10, 0));
            if (player.getGuildId() > 0) {
                ChannelServer.setGuildMemberOnline(player.getMGC(), true, c.getChannel());
                c.getSession().writeAndFlush(MainPacketCreator.showGuildInfo(player));
                final MapleGuild gs = ChannelServer.getGuild(player.getGuildId());
                if (gs != null) {
                    final List<byte[]> packetList = ChannelServer.getAllianceInfo(gs.getAllianceId(), true);
                    if (packetList != null) {
                        for (byte[] pack : packetList) {
                            if (pack != null) {
                                c.getSession().writeAndFlush(pack);
                            }
                        }
                    }
                } else {
                    player.setGuildId(0);
                    player.setGuildRank((byte) 5);
                    player.setAllianceRank((byte) 5);
                    player.saveGuildStatus();
                }
            }
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        player.showNote();
        player.updatePartyMemberHP();

        if (c.getPlayer().matrixSkills.size() > 0) {
            c.sendPacket(MainPacketCreator.updateSkill(c.getPlayer().matrixSkills));
        }

        if (GameConstants.isPhantom(player.getJob())) {
            c.getSession().writeAndFlush(MainPacketCreator.cardAmount(c.getPlayer().getCardStack()));
        }

        player.completeQuest(34120, 0);
        if (GameConstants.isMercedes(player.getJob()))
        player.updateInfoQuest(7784, "sw=1");
        
        for (MapleQuestStatus status : player.getStartedQuests()) {
            if (status.hasMobKills()) {
                c.getSession().writeAndFlush(MainPacketCreator.updateQuestMobKills(status));
            }
        }
        
        int[] bossquests = new int[]{33565,
                31851,
                31833,
                3496,
                3470,
                30007,
                3170,
                31179,
                3521,
                31152,
                34015,
                33294,
                34330,
                34585,
                35632,
                35731,
                35815,
                34478,
                34331, // ??
                34478,
                100114, // Adventure Island
                16013}; // Union
            for (int questid : bossquests) {
                if (player.getQuestStatus(questid) != 2) {
                    MapleQuest.getInstance(questid).forceComplete(player, 0);
                }
            }
        
        String quick = player.getQuestNAdd(MapleQuest.getInstance(GameConstants.QUICK_SLOT)).getCustomData();
        if (quick != null) {
            List<Integer> quickSlot = new ArrayList<>();
            String[] sp = quick.split(",");
            for (String s : sp) {
                quickSlot.add(Integer.parseInt(s));
            }
            c.send(MainPacketCreator.getQuickSlot(quickSlot));
        }

        if (DueyHandler.DueyItemSize(player.getName()) > 0) {
            player.send(DueyHandler.DueyMessage(28));
        }

        /* Zero Gender Overlay Initialization */
        if ((player.getGender() == 0) && (player.getSecondGender() == 0) && (GameConstants.isZero(player.getJob()) || (player.getGender() == 1) && (player.getSecondGender() == 1) && (GameConstants.isZero(player.getJob())))) {
            player.setGender((byte) 1);
            player.setSecondGender((byte) 0);
        }

   /*     if (player.haveItem(1142009, 1, true, true)) { //Dilmeter
            if (!isDamageMeterRanker(player.getId())) {
                player.removeAllEquip(1142009, false);
                player.send(MainPacketCreator.OnAddPopupSay(9000036, 3000, ItemInformation.getInstance().getName(1142009) + " Disqualified, medal was recovered.", ""));
            }
        }

        if (ServerConstants.loginPointAid != -1) { //Login point ranking
            if (player.haveItem(1142077, 1, true, true)) {
                if (player.getAccountID() != ServerConstants.loginPointAid) {
                    player.removeAllEquip(1142077, false);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142077) + " Disqualified, medal was recovered.");
                }
            }
        }

        if (ServerConstants.loginPointAid != -1) { //Login point ranking
            if (player.getAccountID() == ServerConstants.loginPointAid) {
                if (!player.haveItem(1142077, 1, true, true)) {
                    player.gainItem(1142077, 1);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142077) + " Medal was paid.");
                }
            }
        } 

/*        if (ServerConstants.chr != null) { //Electronic board
            if (player.haveItem(1142144, 1, true, true)) {
                if (!player.getName().equals(ServerConstants.chr.getName())) {
                    player.removeAllEquip(1142144, false);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142144) + " Disqualified, medal was recovered.");
                }
            }
        }

        if (ServerConstants.chr != null) { //Electronic board
            if (player.getName().equals(ServerConstants.chr.getName())) {
                if (!player.haveItem(1142144, 1, true, true)) {
                    player.gainItem(1142144, 1);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142144) + " Medal was paid.");
                }
            }
        }

        if (ServerConstants.prank1 != null) { //Popularity ranking
            if (player.haveItem(1142011, 1, true, true)) {
                if (!player.getName().equals(ServerConstants.prank1)) {
                    player.removeAllEquip(1142011, false);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142011) + " Disqualified, medal was recovered.");
                }
            }
        }

        if (ServerConstants.prank1 != null) { //Popularity ranking
            if (player.getName().equals(ServerConstants.prank1)) {
                if (!player.haveItem(1142011, 1, true, true)) {
                    player.gainItem(1142011, 1);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142011) + " Medal was paid.");
                }
            }
        } 

        if (ServerConstants.mrank1 != null) { //Meso ranking
            if (player.haveItem(1142010, 1, true, true)) {
                if (!player.getName().equals(ServerConstants.mrank1)) {
                    player.removeAllEquip(1142010, false);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142010) + " Disqualified, medal was recovered.");
                }
            }
        }

        if (ServerConstants.mrank1 != null) { //Meso ranking
            if (player.getName().equals(ServerConstants.mrank1)) {
                if (!player.haveItem(1142010, 1, true, true)) {
                    player.gainItem(1142010, 1);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142010) + " Medal was paid.");
                }
            }
        } */

        if (ServerConstants.crank1 != null) { //Referrer ranking
            if (player.haveItem(1142140, 1, true, true)) {
                if (!player.getName().equals(ServerConstants.crank1)) {
                    player.removeAllEquip(1142140, false);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142140) + " Disqualified, medal was recovered.");
                }
            }
        }

        if (ServerConstants.crank1 != null) { //Referrer ranking
            if (player.getName().equals(ServerConstants.crank1)) {
                if (!player.haveItem(1142140, 1, true, true)) {
                    player.gainItem(1142140, 1);
                    player.dropMessage(5, ItemInformation.getInstance().getName(1142140) + " Medal was paid.");
                }
            }
        } 

        /* Disable Gender When Nonzero */
        if (!GameConstants.isZero(c.getPlayer().getJob())) {
            c.getPlayer().setSecondGender((byte) -1);
        } else {
            c.send(ZeroSkill.Clothes(player.getBetaClothes()));
        }

        /* Soul Enchanter */
        if ((player.isEquippedSoulWeapon()) && (transfer == null)) {
            player.setSoulCount(0);
            c.send(SoulWeaponPacket.giveSoulGauge(player.getSoulCount(), player.getEquippedSoulSkill()));
        }

        /* Elemental Pendant */
        if (player.getInventory(MapleInventoryType.EQUIPPED).findById(1122017) != null || player.getInventory(MapleInventoryType.EQUIPPED).findById(1122155) != null) {
            player.equipPendantOfSpirit();
        }

        /* Opengate */
        c.getPlayer().setKeyValue("opengate", null);
        c.getPlayer().setKeyValue("count", null);

        /* Pendant slot */
        if (!c.getPlayer().getStat().getJC()) {
            String[] text = {ServerConstants.serverName+" Welcome to the server.", ServerConstants.serverName+" Welcome to the server!"};
            String text3 = player.getName() + "," + text[(int) java.lang.Math.floor(java.lang.Math.random() * text.length)];
            WorldBroadcasting.broadcastMessage(UIPacket.detailShowInfo(text3 + " Players Online: " + ChannelServer.getOnlineConnections() + "", false));
            c.getSession().write(MainPacketCreator.serverMessage(ServerConstants.serverMessage));
            c.getPlayer().getStat().setJC(true);
        }     

        c.getSession().writeAndFlush(MainPacketCreator.serverMessage(ServerConstants.serverMessage));

        if (ServerConstants.serverHint != null) {
            if (ServerConstants.serverHint.length() > 0) {
                c.getSession().writeAndFlush(MainPacketCreator.OnAddPopupSay(9062000, 6000, ServerConstants.serverHint, ""));
                c.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, ServerConstants.serverHint));
            }
        }

        if (GameConstants.isXenon(player.getJob())) {
            player.startSurPlus();
        }

        if (GameConstants.isBlaster(player.getJob())) {
            player.giveBulletGauge(0, false);
        }

        if (GameConstants.isDemonAvenger(player.getJob())) {
            c.send(MainPacketCreator.giveDemonWatk(c.getPlayer().getStat().getHp()));
        }
        c.getSession().writeAndFlush(CashPacket.pendantSlot(false));
        player.sendMacros();

        if (GameConstants.isKaiser(c.getPlayer().getJob())) {
            c.getPlayer().changeKaiserTransformKey();
        } else {
            c.getSession().writeAndFlush(MainPacketCreator.getKeymap(player.getKeyLayout()));
        }
        if (GameConstants.isKOC(player.getJob()) && player.getLevel() >= 100) {
            if (player.getSkillLevel(Integer.parseInt(String.valueOf(player.getJob() + "1000"))) <= 0) {
                player.teachSkill(Integer.parseInt(String.valueOf(player.getJob() + "1000")), (byte) 0, SkillFactory.getSkill(Integer.parseInt(String.valueOf(player.getJob() + "1000"))).getMaxLevel());
            }
        }

        if (!player.getInfoQuest(31389).equals("ex=0") && !player.getInfoQuest(31389).equals("")) {
            c.sendPacket(UIPacket.OpenUI(1151));
        }
        player.send(MainPacketCreator.resetModifyInventoryItem());
        player.send(MainPacketCreator.showMaplePoint(player.getNX()));
        player.send(MainPacketCreator.getQuickMove(new ArrayList<QuickMoveEntry>()));
        //c.getSession().writeAndFlush(MainPacketCreator.OnChatLetClientConnect());       

        // Daily Gift ??
//        if (!c.getPlayer().getDailyGift().getDailyData().equals(cal.get(Calendar.YEAR) + (Month == 10 || Month == 11 || Month == 12 ? "" : "0") + Month + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE))) {
//            c.getPlayer().getDailyGift().setDailyCount(0);
//            c.getPlayer().getDailyGift().saveDailyGift(c.getAccID(c.getAccountName()), c.getPlayer().getDailyGift().getDailyDay(), c.getPlayer().getDailyGift().getDailyCount(), c.getPlayer().getDailyGift().getDailyData());
//        }
//        c.getSession().writeAndFlush(MainPacketCreator.getDailyGiftRecord("count=" + c.getPlayer().getDailyGift().getDailyCount() + ";day=" + c.getPlayer().getDailyGift().getDailyDay() + ";date=" + GameConstants.getCurrentDate_NoTime()));
//        c.getSession().writeAndFlush(UIPacket.OnDailyGift((byte) 0, 2, 0));
//        if (c.getPlayer().cores.size() == 0) {
//            for (int b = 0; b < 28; b++) {
//                if (getValue(c, "core" + b) != -1) {
//                    c.getPlayer().setKeyValue("core" + b, "-1");
//                }
//            }
//            for (final Map.Entry<ISkill, SkillEntry> s : c.getPlayer().matrixSkills.entrySet()) {
//                c.getPlayer().changeSkillLevel(s.getKey(), (byte) 0, (byte) 0);
//            }
//        }

         for (MapleCharacter ch : player.getMap().getCharacters()) {
            if (player != ch) {
                player.send(MainPacketCreator.spawnPlayerMapobject(ch));
                AdminTool.broadcastMessage(AdminToolPacket.Info());
            }
        }
    }
	public boolean isDamageMeterRanker(int cid, boolean no)
	{
		boolean value = false;
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
		try
		{
		 con = MYSQL.getConnection();
		 ps = null;
                        if (!no)
                            ps = con.prepareStatement("SELECT * FROM damagerank ORDER BY damage DESC LIMIT 1");
                        else
                            ps = con.prepareStatement("SELECT * FROM damagerank2 ORDER BY damage DESC LIMIT 1");
                        rs = ps.executeQuery();
			if (rs.next())
			{
				if (rs.getInt("cid") == cid)
				{
					value = true;
				}
			}
			rs.close();
			ps.close();
			con.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
                finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		return value;
	}

    public static final void ChangeChannel(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        if (!chr.isAlive()) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final int channel = rh.readByte();

        if (c.getPlayer().getStat().ChDelay > System.currentTimeMillis()) {
            c.getPlayer().dropMessage(1, "Channel change is possible once every 3 seconds.");
            c.getPlayer().send(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        c.getPlayer().getStat().ChDelay = System.currentTimeMillis() + 3000;
        final ChannelServer toch = ChannelServer.getInstance(channel);

        if (FieldLimitType.ChannelSwitch.check(chr.getMap().getFieldLimit()) || channel == c.getChannel()) {
            c.getSession().close();
            return;
        } else if (toch == null || toch.isShutdown()) {
            c.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Not currently accessible."));
            return;
        }
        if (chr.getTrade() != null) {
            MapleUserTrade.cancelTrade(chr.getTrade());
        }

        final IMapleCharacterShop shop = chr.getPlayerShop();
        if (shop != null) {
            shop.removeVisitor(chr);
            if (shop.isOwner(chr)) {
                shop.setOpen(true);
            }
        }

        final ChannelServer ch = ChannelServer.getInstance(c.getChannel());
        if (chr.getMessenger() != null) {
            WorldCommunity.silentLeaveMessenger(chr.getMessenger().getId(), new MapleMultiChatCharacter(chr));
        }
        try {
            //chr.cancelAllBuffs(); // Temporary processing (buffer preset when moving channel)
        } catch (Exception ex) {
        }
        ChannelServer.addCooldownsToStorage(chr.getId(), chr.getAllCooldowns());
        ChannelServer.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
        ChannelServer.ChannelChange_Data(new ChracterTransfer(chr), chr.getId(), channel);
        MapleItemHolder.registerInv(chr.getId(), chr.getInventorys());
        ch.removePlayer(chr);
        c.updateLoginState(MapleClient.CHANGE_CHANNEL, c.getSessionIPAddress());
        c.getSession().writeAndFlush(MainPacketCreator.getChannelChange(ServerConstants.basePorts + (channel), ServerConstants.getServerHost(c)));
        chr.saveToDB(false, false);
        chr.getMap().removePlayer(chr);
        c.setPlayer(null);
    }

    /*
    public static void getGameQuitRequest(ReadingMaple rh, MapleClient c) { //Login to exit the game
        String account = rh.readMapleAsciiString();
        c.getSession().writeAndFlush(MainPacketCreator.GameEnd());
    }
}
     */
    public static void getGameQuitRequest(ReadingMaple rh, MapleClient c) { //When the game ends, select the character selection window
        String account = rh.readMapleAsciiString();
        if (account.equals("")) {
            account = c.getAccountName();
        }
        if (!c.isLoggedIn() && !c.getAccountName().equals(account)) {
            c.getSession().close();
            return;
        }
        c.getSession().writeAndFlush(MainPacketCreator.serverNotice(4, ""));
        c.getSession().writeAndFlush(LoginPacket.getKeyGuardResponse((account) + "," + (c.getPassword(account))));
    }
}
