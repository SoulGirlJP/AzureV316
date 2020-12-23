package client.Commands;


import java.awt.Point;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.BingoGame;
import client.Character.MapleCharacter;
import static client.Commands.CommandProcessor.getNamedIntArg;
import static client.Commands.CommandProcessor.getNamedLongArg;
import static client.Commands.CommandProcessor.getOptionalIntArg;
import client.ItemInventory.Equip;
import client.ItemInventory.IItem;
import client.ItemInventory.Item;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventoryType;
import client.MapleClient;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Stats.PlayerStatList;
import connections.Database.MYSQL;
import connections.Opcodes.RecvPacketOpcode;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.DemianPacket;
import connections.Packets.MainPacketCreator;
import connections.Packets.RunePacket;
import connections.Packets.SLFCGPacket;
import constants.Data.AccountType;
import constants.EventConstants.MedalRanking;
import constants.GameConstants;
import constants.ServerConstants;
import launcher.ServerPortInitialize.CashShopServer;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.MapleLocalisation;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldConnected;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import scripting.NPC.NPCScriptManager;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.OverrideMonsterStats;
import server.LifeEntity.NpcEntity.MapleNPC;
import server.LifeEntity.NpcEntity.MapleNPCStats;
import server.LifeEntity.NpcEntity.MaplePlayerNPC;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapReactor.MapleReactor;
import server.Maps.MapReactor.MapleReactorFactory;
import server.Maps.MapReactor.MapleReactorStats;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleMapHandling.MaplePortal;
import server.Maps.MapleMapHandling.MapleRune;
import server.Quests.MapleQuest;
import server.Shops.MapleShopFactory;
import tools.ArrayMap;
import tools.CurrentTime;
import tools.LoggerChatting;
import tools.Pair;
import tools.StringUtil;
import tools.Timer.MapTimer;

public class AdminCommands {

	@Command(names = {"fsay"}, parameters = "<name> <message>", requiredType = AccountType.ADMIN)
	public static class ForceSay extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleCharacter target = c.getClient().getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
			
			if (target == null) {
				c.dropMessage(6, "Character: " + splitted[1] + " not found");
				return 0;
			}
			
			String msg = "";
			for(int i = 2; i < splitted.length; i++) {
				if (i < splitted.length - 1) {
					msg += splitted[i] + " ";
				} else {
					msg += splitted[i];
				}
			}
			target.getMap().broadcastMessage(MainPacketCreator.getChatText(target.getId(), msg, target.isGM(), 3), target.getPosition());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Forces the specified user in your channel to say the specified message.";
		}
	}

    @Command(names = {"botcheck"}, parameters = "<name>", requiredType = AccountType.LOWGM)
    public static class BotCheck extends AdminCommand {
        @Override
        public int execute (MapleCharacter chr, String[] args) {

            if(args.length <= 1) {
                chr.dropMessage(1, "!botcheck <name>");
            }
            else {
                String playerName = args[1];
                MapleCharacter targetPlayer = chr.getClient().getChannelServer().getPlayerStorage().getCharacterByName(playerName);
                if(targetPlayer == null) {
                    chr.dropMessage(5, playerName + " is not online.");
                }
                else {
                    targetPlayer.dispose();
                    // Dispose all their actions before forcing them to open an NPC
                    NPCScriptManager.getInstance().start(targetPlayer.getClient(), 9000344); // bot check NPC
                }
            }
            return 1;
        }
        @Override
        public String getDescription(){return "Sends a player an NPC dialogue bot check.";}
    }
	
	@Command(names = {"charinfo"}, parameters = "<name>", requiredType = AccountType.LOWGM)
	public static class CharInfo extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			final StringBuilder builder = new StringBuilder();
			MapleCharacter other = c.getClient().getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);

			builder.append(MapleClient.getLogMessage(other, ""));
			builder.append(" Is ").append(other.getPosition().x);
			builder.append(" /").append(other.getPosition().y);
			builder.append(" In position");
			builder.append(" || HP: ").append(other.getStat().getHp()).append(" /").append(other.getStat().getCurrentMaxHp());
			builder.append(" || MP: ").append(other.getStat().getMp()).append(" /").append(other.getStat().getCurrentMaxMp());
			builder.append(" || EXP: ").append(other.getExp());
			builder.append(" || Party: ").append(other.getParty() != null);
			builder.append(" || Trading: ").append(other.getTrade() != null);
			builder.append("] || cash: [").append(other.getClient().getPlayer().getNX());
			builder.append("] || meso: [").append(other.getClient().getPlayer().getMeso());
			builder.append("] || IP address: ").append(other.getClient().getIp());
			builder.append(" || Str: ").append(other.getClient().getPlayer().getStat().getLocalStr()).append(" (")
					.append(other.getClient().getPlayer().getStat().getStr()).append(" + ")
					.append(other.getClient().getPlayer().getStat().getLocalStr()
							- other.getClient().getPlayer().getStat().getStr())
					.append(")");
			builder.append(" || Dex: ").append(other.getClient().getPlayer().getStat().getLocalDex()).append(" (")
					.append(other.getClient().getPlayer().getStat().getDex()).append(" + ")
					.append(other.getClient().getPlayer().getStat().getLocalDex()
							- other.getClient().getPlayer().getStat().getDex())
					.append(")");
			builder.append(" || Int: ").append(other.getClient().getPlayer().getStat().getLocalInt()).append(" (")
					.append(other.getClient().getPlayer().getStat().getInt()).append(" + ")
					.append(other.getClient().getPlayer().getStat().getLocalInt()
							- other.getClient().getPlayer().getStat().getInt())
					.append(")");
			builder.append(" || Luk: ")
					.append(other.getClient().getPlayer().getStat().getLocalLuk()).append(" (")
					.append(other.getClient().getPlayer().getStat().getLuk()).append(" + ")
					.append(other.getClient().getPlayer().getStat().getLocalLuk() 
							- other.getClient().getPlayer().getStat().getLuk())
					.append(")");
			builder.append(" || ATT: ").append(other.getClient().getPlayer().getStat().getWAtk());
			builder.append(" || M.ATT: ").append(other.getClient().getPlayer().getStat().getMAtk());
			builder.append(" || Physics Mastery: ").append(other.getClient().getPlayer().getStat().getWMastery());
			builder.append(" || Magic Mastery: ").append(other.getClient().getPlayer().getStat().getMMastery());
			builder.append(" || Physical Attack Increase%: ").append(other.getClient().getPlayer().getStat().getPercentWatk());
			builder.append(" || Magic Attack Power Increase%: ").append(other.getClient().getPlayer().getStat().getPercentMatk());
			builder.append(" || Str increase%: ").append(other.getClient().getPlayer().getStat().getPercentStr());
			builder.append(" || Dex increase%: ").append(other.getClient().getPlayer().getStat().getPercentDex());
			builder.append(" || Int increase%: ").append(other.getClient().getPlayer().getStat().getPercentInt());
			builder.append(" || Luk increase%: ").append(other.getClient().getPlayer().getStat().getPercentLuk());
			builder.append(" || Stat Attack: ").append(other.getClient().getPlayer().getStat().getMinAttack()).append(" ~ ").append(other.getClient().getPlayer().getStat().getMaxAttack());
			builder.append(" || Additional damage: ").append(other.getAddDamage());
			builder.append(" || Latency: ").append(other.getClient().getLatency());

			c.dropMessage(6, builder.toString());
			other = null;
			return 1;
		}
		
		@Override
		public String getDescription() {
			return "Retrieves specific character information of the specified user in your channel.";
		}
	}
	
	@Command(names = {"ban"}, parameters = "<name> <reason>", requiredType = AccountType.GM)
	public static class Ban extends AdminCommand {
		@Override
		public int execute(MapleCharacter chr, String[] args) {
			if (args.length < 3) {
				return 0;
			}

			final StringBuilder sb = new StringBuilder(chr.getName());
			sb.append(" banned ").append(args[1]).append(": ").append(StringUtil.joinStringFrom(args, 2));

			ChannelServer cserv = chr.getClient().getChannelServer();
			final MapleCharacter target = cserv.getPlayerStorage().getCharacterByName(args[1]);

			if (target != null) {
				sb.append(" (IP: ").append(target.getClient().getIp().split(":")[0]).append(")");
				if (target.ban(sb.toString(), true, false)) {
					chr.dropMessage(6, "You have been banned.");
					if (chr.getKeyValue("Banned_Today") == null) {
						chr.setKeyValue("Banned_Today", "0");
					}
					chr.setKeyValue("Banned_Today", (Integer.parseInt(chr.getKeyValue("Banned_Today")) + 1) + "");
				} else {
					chr.dropMessage(6, "Ban failed.");
				}
			} else {
				if (MapleCharacter.ban(args[1], sb.toString(), false)) {
					chr.dropMessage(6, args[1] + " Offline ban Success.");
				} else {
					chr.dropMessage(6, args[1] + " Failed to ban.");
				}
			}
			return 1;
		}
		
		@Override
		public String getDescription() {
			return "Permanently bans the specified user in your channel.";
		}
	}

	@Command(names = {"tempban"}, parameters = "<name> <reason> <days>", requiredType = AccountType.LOWGM)
	public static class TempBan extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer cserv = c.getClient().getChannelServer();

			final MapleCharacter victim = cserv.getPlayerStorage().getCharacterByName(splitted[1]);
			final int reason = Integer.parseInt(splitted[2]);
			final int numDay = Integer.parseInt(splitted[3]);

			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, numDay);
			final DateFormat df = DateFormat.getInstance();

			if (victim == null) {
				c.dropMessage(6, "Can't find that character.");
				return 0;
			}
			victim.tempban("TempBan : " + c.getName() + "", cal, reason, true);
			c.dropMessage(6,
					"" + splitted[1] + " Character " + df.format(cal.getTime()) + " Has been successfully banned.");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Temporarily bans the specified user in your channel.";
		}
	}

	@Command(names = {"unban"}, parameters = "<name>", requiredType = AccountType.GM)
	public static class UnBan extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length < 1) {
				c.dropMessage(6, "!unban <character name>");
			} else {
				final byte result = c.getClient().unban(splitted[1]);
				if (result == -1) {
					c.dropMessage(6, "I didn't find that character.");
				} else if (result == -2) {
					c.dropMessage(6, "There was an error releasing the character's ban.");
				} else {
					c.dropMessage(6, "The character has been successfully released by the ban.");
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Unbans the specified user.";
		}
	}

	@Command(names = {"dc", "disconnect"}, parameters = "(-f) <name>", requiredType = AccountType.LOWGM)
	public static class Disconnect extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer cserv = c.getClient().getChannelServer();

			int level = 0;
			MapleCharacter victim;
			if (splitted[1].charAt(0) == '-') {
				level = StringUtil.countCharacters(splitted[1], 'f');
				victim = cserv.getPlayerStorage().getCharacterByName(splitted[2]);
			} else {
				victim = cserv.getPlayerStorage().getCharacterByName(splitted[1]);
			}
			try {
				Connection con = MYSQL.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT accountid WHERE id = ?");
				ps.setInt(1, victim.getId());
				con.close();
			} catch (Exception e) {

			}
			if (level < 2) {
				victim.getClient().getSession().close();
				if (level >= 1) {
					victim.getClient().disconnect(true, false);
				}
			} else {
				c.dropMessage(6, "Please use dc -f instead.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Disconnects the specified user in your channel";
		}
	}

	@Command(names = {"online", "connected"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class Connected extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			Map<Integer, Integer> connected = WorldConnected.getConnected(c.getClient().getWorld());
			StringBuilder conStr = new StringBuilder("Number of people currently online: ");
			boolean first = true;
			for (int i : connected.keySet()) {
				if (!first) {
					conStr.append(", ");
				} else {
					first = false;
				}
				if (i == 0) {
					conStr.append("run: ");
					conStr.append(connected.get(i));
				} else {
					conStr.append("channel");
					conStr.append(i);
					conStr.append(": ");
					conStr.append(connected.get(i));
				}
			}
			c.dropMessage(6, conStr.toString());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves a list of the number of people currently online.";
		}
	}

	@Command(names = {"questforfeit"}, parameters = "<quest_id>", requiredType = AccountType.GM)
	public static class QuestForfeit extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleQuest.getInstance(Integer.parseInt(splitted[1])).forfeit(c);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Forfeits the specified quest.";
		}
	}

	@Command(names = {"questcomplete"}, parameters = "<quest_id>", requiredType = AccountType.GM)
	public static class QuestComplete extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleQuest.getInstance(Integer.parseInt(splitted[1])).forceComplete(c, 0);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Force completes the specified quest.";
		}
	}

	@Command(names = {"queststart"}, parameters = "<quest_id>", requiredType = AccountType.GM)
	public static class QuestStart extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleQuest.getInstance(Integer.parseInt(splitted[1])).forceStart(c, 0, null);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Force starts the specified quest.";
		}
	}

	@Command(names = {"pathtracking"}, parameters = "<thread_number>", requiredType = AccountType.ADMIN)
	public static class PathTracking extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length < 1) {
				return 0;
			}
			Thread[] threads = new Thread[Thread.activeCount()];
			Thread.enumerate(threads);
			Thread t = threads[Integer.parseInt(splitted[1])];
			c.dropMessage(6, t.toString() + ":");
			for (StackTraceElement elem : t.getStackTrace()) {
				c.dropMessage(6, elem.toString());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Returns the stacktrace for the specified thread.";
		}
	}

	@Command(names = {"toggledrops"}, parameters = "", requiredType = AccountType.GM)
	public static class DropToggle extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getMap().toggleDrops();
			return 1;
		}

		@Override
		public String getDescription() {
			return "Toggles the drops for the monsters in your current map.";
		}
	}

	@Command(names = {"togglemega", "togglesmega"}, parameters = "", requiredType = AccountType.GM)
	public static class LoudSpeakerToggle extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer.allToggleMegaphoneMuteState();
			c.dropMessage(6, "Loudspeaker state: " + (c.getClient().getChannelServer().getMegaphoneMuteState() ? "disabled" : "enabled"));
			return 1;
		}

		@Override
		public String getDescription() {
			return "Toggles the ability to use megaphones in all channels.";
		}
	}

	@Command(names = {"spawnreactor"}, parameters = "<reactor_id>", requiredType = AccountType.ADMIN)
	public static class ReactorRecall extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleReactorStats reactorSt = MapleReactorFactory.getReactor(Integer.parseInt(splitted[1]));
			MapleReactor reactor = new MapleReactor(reactorSt, Integer.parseInt(splitted[1]));
			reactor.setDelay(-1);
			reactor.setPosition(c.getPosition());
			c.getMap().spawnReactor(reactor);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns a reactor of the specified id at your current location.";
		}
	}

	@Command(names = {"hitreactor"}, parameters = "<reactor_id>", requiredType = AccountType.ADMIN)
	public static class Reactor extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getMap().getReactorByOid(Integer.parseInt(splitted[1])).hitReactor(c.getClient());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Activates the specified reactor.";
		}
	}

	@Command(names = {"listreactor"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ReactorList extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			List<MapleMapObject> reactors = map.getMapObjectsInRange(c.getPosition(),
					Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.REACTOR));
			for (MapleMapObject reactorL : reactors) {
				MapleReactor reactor2l = (MapleReactor) reactorL;
				c.dropMessage(6,
						"Reactor: objectid: " + reactor2l.getObjectId() + " Reactor ID: " + reactor2l.getReactorId()
								+ " location: " + reactor2l.getPosition().toString() + " state: "
								+ reactor2l.getState());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves a list of available reactors in your current map.";
		}
	}

	@Command(names = {"deletereactor"}, parameters = "<reactor_id OR all>", requiredType = AccountType.ADMIN)
	public static class ReactorDelete extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			List<MapleMapObject> reactors = map.getMapObjectsInRange(c.getPosition(),
					Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.REACTOR));
			if (splitted[1].equals("all")) {
				for (MapleMapObject reactorL : reactors) {
					MapleReactor reactor2l = (MapleReactor) reactorL;
					c.getMap().destroyReactor(reactor2l.getObjectId());
				}
			} else {
				c.getMap().destroyReactor(Integer.parseInt(splitted[1]));
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Deletes the specified reactor (or all reactors when using the keyword 'all') in your current map.";
		}
	}

	@Command(names = {"resetreactor"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ReactorReset extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getMap().resetReactors(c.getClient());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Resets all reactors in your current map.";
		}
	}

	@Command(names = {"setreactor"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ReactorSetting extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getMap().setReactorState(c.getClient());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Enables all reactors in your current map.";
		}
	}

	@Command(names = {"removedrops"}, parameters = "", requiredType = AccountType.GM)
	public static class DropRemove extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			List<MapleMapObject> items = c.getMap().getMapObjectsInRange(c.getPosition(),
					Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.ITEM));
			for (MapleMapObject i : items) {
				c.getMap().removeMapObject(i);
				c.getMap().broadcastMessage(MainPacketCreator.removeItemFromMap(i.getObjectId(), 0, 0), i.getPosition());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Removes all drops in your current map.";
		}
	}

	@Command(names = {"setexp", "exprate"}, parameters = "<rate>", requiredType = AccountType.GM)
	public static class ExpRate extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length > 1) {
				final int rate = Integer.parseInt(splitted[1]);
				for (ChannelServer cs : ChannelServer.getAllInstances()) {
					cs.setExpRate(rate);
				}
				c.dropMessage(6, "The exp rate has been modified to: " + rate);
			} else {
				c.dropMessage(6, "Usage: !setexp/exprate <number>");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets the exp of all channels to the specified rate.";
		}
	}

	@Command(names = {"setdrop", "droprate"}, parameters = "<rate>", requiredType = AccountType.GM)
	public static class DropRate extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length > 1) {
				final int rate = Integer.parseInt(splitted[1]);
				for (ChannelServer cs : ChannelServer.getAllInstances()) {
					cs.setDropRate(rate);
				}
				c.dropMessage(6, "The drop rate has been modified to: " + rate);
			} else {
				c.dropMessage(6, "Usage: !setdrop/droprate <number>");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets the drop rate of all channels to the specified rate.";
		}
	}

	@Command(names = {"dcall", "disconnectall"}, parameters = "", requiredType = AccountType.GM)
	public static class DisconnectAll extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ServerConstants.isShutdown = true;
			for (ChannelServer cs : ChannelServer.getAllInstances()) {
				cs.getPlayerStorage().disconnectAll();
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Disconnects everyone in the server.";
		}
	}

	@Command(names = {"mapnpcs"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class CMNpc extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			c.Message("Current map : " + map.getId() + " - " + map.getStreetName() + " : " + map.getMapName());
			for (MapleMapObject mo : map.getAllNPC()) {
				c.Message(7, ((MapleNPC) mo).getId() + " : " + ((MapleNPC) mo).getName());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves information about your current map and all of the npcs inside.";
		}
	}

	@Command(names = {"mapportals"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class CMPortal extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			c.Message("Current map : " + map.getId() + " - " + map.getStreetName() + " : " + map.getMapName());
			for (MaplePortal mp : map.getPortals()) {
				c.Message(7, mp.getId() + " : " + mp.getName() + " script : " + mp.getScriptName()
						+ " destination : " + mp.getTarget());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves information about your current map and all of the portals inside.";
		}
	}

	@Command(names = {"mapplayers"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class CMPlayer extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			c.Message("Current map : " + map.getId() + " - " + map.getStreetName() + " : " + map.getMapName());
			for (MapleMapObject pr : map.getAllPlayer()) {
				c.Message(7, ((MapleCharacter) pr).getName());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves information about your current map and all of the players inside.";
		}
	}

	@Command(names = {"addhonor"}, parameters = "<amount>", requiredType = AccountType.LOWGM)
	public static class Honor extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int d = Integer.parseInt(splitted[1]);
			c.addInnerExp(d);
			c.message(5, "Your honor rose by: " + d + "");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Increases your honor exp by the specified amount.";
		}
	}

	@Command(names = {"killmob"}, parameters = "(drops 0/1)", requiredType = AccountType.GM)
	public static class MobKill extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			boolean withDrops = CommandProcessor.getNamedIntArg(splitted, 1, "drops") == 1;
			
			MapleMonster mob;
			for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MONSTER))) {
				mob = (MapleMonster) monstermo;
				map.killMonster(mob, c, withDrops, false, (byte) 1);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Kills all monsters in your current map and based on the optional parameter 'drops' will either spawn the drops or not. Example: !killmob drops 1";
		}
	}

	@Command(names = {"mobdata"}, parameters = "<range_amount>", requiredType = AccountType.LOWGM)
	public static class MobPrint extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleMap map = c.getMap();
			double range = Double.POSITIVE_INFINITY;

			if (splitted.length > 0) {
				int givenRange = Integer.parseInt(splitted[1]);
				range = givenRange * givenRange;
			}

			MapleMonster mob;
			for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
				mob = (MapleMonster) monstermo;
				c.dropMessage(6, "monster " + mob.toString());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves a list of all monsters in your map or specified by the range amount.";
		}
	}

	@Command(names = {"notice"}, parameters = "(map/channel/server) <message>", requiredType = AccountType.LOWGM)
	public static class Notice extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int joinmod = 1;
			int range = -1;

			if (splitted[1].equals("map")) {
				range = 0;
			} else if (splitted[1].equals("channel")) {
				range = 1;
			} else if (splitted[1].equals("server")) {
				range = 2;
			}

			int tfrom = 2;
			if (range == -1) {
				range = 2;
				tfrom = 1;
			}

			int type = getNoticeType(splitted[tfrom]);
			if (type == -1) {
				type = 0;
				joinmod = 0;
			}

			StringBuilder sb = new StringBuilder();
			if (splitted[tfrom].equals("noticed")) {
				sb.append("[notice]");
			} else {
				sb.append("");
			}

			joinmod += tfrom;
			sb.append(StringUtil.joinStringFrom(splitted, joinmod));

			byte[] packet = MainPacketCreator.serverNotice(type, sb.toString());
			if (range == 0) {
				c.getMap().broadcastMessage(packet);
			} else if (range == 1) {
				ChannelServer.getInstance(c.getClient().getChannel()).broadcastPacket(packet);
			} else if (range == 2) {
				WorldBroadcasting.broadcastMessage(packet);
			}
			return 1;
		}

		private int getNoticeType(String typestring) {
			if (typestring.equals("notice")) {
				return 0;
			} else if (typestring.equals("pop-up")) {
				return 1;
			} else if (typestring.equals("lightblue")) {
				return 2;
			} else if (typestring.equals("pink")) {
				return 5;
			} else if (typestring.equals("noticed")) {
				return 5;
			} else if (typestring.equals("blue")) {
				return 6;
			}
			return -1;
		}

		@Override
		public String getDescription() {
			return "Sends a notice to the entire server unless specified in the optional parameters.";
		}
	}

	@Command(names = {"spawnnpc"}, parameters = "<npc_id>", requiredType = AccountType.GM)
	public static class NPC extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int npcId = Integer.parseInt(splitted[1]);
			MapleNPC npc = MapleLifeProvider.getNPC(npcId);
			if (npc != null && !npc.getName().equals("MISSINGNO")) {
				npc.setPosition(c.getPosition());
				npc.setCy(c.getPosition().y);
				npc.setRx0(c.getPosition().x + 50);
				npc.setRx1(c.getPosition().x - 50);
				npc.setFh(c.getMap().getFootholds().findMaple(c.getPosition()).getId());
				npc.setCustom(true);
				c.getMap().addMapObject(npc);
				c.dropMessage(5, npc.getObjectId() + "");
				c.getMap().broadcastMessage(MainPacketCreator.spawnNPC(npc, true));
			} else {
				c.dropMessage(6, "You entered an NPC that does not exist in WZ.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns an npc with the specified id at your current location.";
		}
	}

	@Command(names = {"deletenpc"}, parameters = "(npc_id)", requiredType = AccountType.GM)
	public static class NPCDelete extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			for (MapleMapObject npcss : c.getMap().getAllNPC()) {
				MapleNPC npc = (MapleNPC) npcss;
				if (splitted[1] != null) {
					if (npc.getId() == Integer.parseInt(splitted[1])) {
						c.getMap().broadcastMessage(MainPacketCreator.removeNPC(npc.getObjectId()));
						c.getMap().broadcastMessage(MainPacketCreator.removeNPCController(npc.getObjectId()));
						c.getMap().removeMapObject(npc);
					}
				} else {
					c.getMap().broadcastMessage(MainPacketCreator.removeNPC(npc.getObjectId()));
					c.getMap().broadcastMessage(MainPacketCreator.removeNPCController(npc.getObjectId()));
					c.getMap().removeMapObject(npc);
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Deletes either all npcs (unless an npc id is specified, then it will only delete the specified one) in your current map.";
		}
	}

	@Command(names = {"spawnpnpcperm"}, parameters = "<npc_id>", requiredType = AccountType.GM)
	public static class PlayerAndFish extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int npcId = Integer.parseInt(splitted[1]);
			MaplePlayerNPC npc = new MaplePlayerNPC(npcId, new MapleNPCStats(""));
			npc.setPosition(c.getPosition());
			npc.setName(c.getName());
			npc.setCy(c.getPosition().y);
			npc.setRx0(c.getPosition().x + 50);
			npc.setRx1(c.getPosition().x - 50);
			npc.setFace(c.getFace());
			npc.setHair(c.getHair());
			npc.setSkin(c.getSkinColor());
			npc.setDirection((byte) (c.isFacingLeft() ? 0 : 1));
			npc.setFh(c.getMap().getFootholds().findMaple(c.getPosition()).getId());
			Map<Byte, Integer> equips = new LinkedHashMap<Byte, Integer>();
			for (IItem equip : c.getInventory(MapleInventoryType.EQUIPPED).list()) {
				equips.put((byte) equip.getPosition(), equip.getItemId());
			}
			npc.setEquips(equips);
			c.getMap().addMapObject(npc);
			npc.broadcastPacket(c.getMap());
			try {
				Connection con = MYSQL.getConnection();
				String sql = "INSERT INTO `playernpcs`(`id`, `name`, `hair`, `face`, `skin`, `dir`, `x`, `y`, `map`) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, npcId);
				ps.setString(2, c.getName());
				ps.setInt(3, c.getHair());
				ps.setInt(4, c.getFace());
				ps.setInt(5, c.getSkinColor());
				ps.setInt(6, c.isFacingLeft() ? 0 : 1);
				ps.setInt(7, c.getPosition().x);
				ps.setInt(8, c.getPosition().y);
				ps.setInt(9, c.getMapId());
				ps.executeUpdate();
				ps.close();
				ps = con.prepareStatement("INSERT INTO `playernpcs_equip`(`npcid`, `equipid`, `equippos`) VALUES (? ,? ,?)");
				ps.setInt(1, npcId);
				for (IItem equip : c.getInventory(MapleInventoryType.EQUIPPED).list()) {
					ps.setInt(2, equip.getItemId());
					ps.setByte(3, (byte) equip.getPosition());
					ps.executeUpdate();
				}
				ps.close();
				con.close();
				c.dropMessage(6, "Player NPC successfully registered in DB.");
			} catch (Exception e) {
				c.dropMessage(1, "Player NPC failed to register in DB.");
				System.err.println("[ERROR] An error occurred while building player nfish.");
				if (!ServerConstants.realese) {
					e.printStackTrace();
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Permanently spawns a player npc with the specified npc id at your current location. Note: This command isn't finished yet, please refrain from using!";
		}
	}

	@Command(names = {"spawnnpcperm"}, parameters = "<npc_id>", requiredType = AccountType.GM)
	public static class NPCFix extends AdminCommand {

		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int npcId = Integer.parseInt(splitted[1]);
			MapleNPC npc = MapleLifeProvider.getNPC(npcId);
			if (npc != null && !npc.getName().equals("MISSINGNO")) {
				npc.setPosition(c.getPosition());
				npc.setCy(c.getPosition().y + 2);
				npc.setRx0(c.getPosition().x + 50);
				npc.setRx1(c.getPosition().x - 50);
				npc.setFh(c.getMap().getFootholds().findMaple(c.getPosition()).getId());
				c.getMap().addMapObject(npc);
				c.getMap().broadcastMessage(MainPacketCreator.spawnNPC(npc, true));
			} else {
				c.dropMessage(6, "You entered an NPC that does not exist in WZ.");
				return 0;
			}
			try {
				Connection con = MYSQL.getConnection();
				String sql = "INSERT INTO `spawn`(`lifeid`, `rx0`, `rx1`, `cy`, `fh`, `type`, `dir`, `mapid`, `mobTime`) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, npcId);
				ps.setInt(2, c.getPosition().x + 50);
				ps.setInt(3, c.getPosition().x - 50);
				ps.setInt(4, c.getPosition().y + 2);
				ps.setInt(5, c.getMap().getFootholds().findMaple(c.getPosition()).getId());
				ps.setString(6, "n");
				ps.setInt(7, c.getFacingDirection() == 1 ? 0 : 1);
				ps.setInt(8, c.getMapId());
				ps.setInt(9, 0);
				ps.executeUpdate();
				ps.close();
				con.close();
			} catch (Exception e) {
				System.err.println("[Error] Failed to register nfish.");
				if (!ServerConstants.realese) {
					e.printStackTrace();
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Permanently spawns an npc with the specified npc id at your current location.";
		}
	}

	@Command(names = {"spawnmobtemp"}, parameters = "<mob_id>", requiredType = AccountType.GM)
	public static class MobFix extends AdminCommand {

		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (c.getMap().getId() == 100000000) {
                c.dropMessage(6, "Let's not make the same mistake again shall we?");
                return 0;
            }
			int mobId = Integer.parseInt(splitted[1]);
			MapleMonster mob = MapleLifeProvider.getMonster(mobId);
			c.getMap().spawnMonsterOnGroundBelow(mob, c.getPosition());
			return 1;
		}
		
		@Override
		public String getDescription() {
			return "Permanently spawns a mob at your current location.";
		}
	}

	@Command(names = {"reloadopcodes"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class OpcodeReset extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			SendPacketOpcode.loadOpcode();
			RecvPacketOpcode.loadOpcode();
			c.dropShowInfo("[System] Reloading opcodes complete.");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Reloads all available opcodes.";
		}
	}

	@Command(names = {"debugopcode"}, parameters = "<opcode_id>", requiredType = AccountType.ADMIN)
	public static class OpcodeDebug extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			for (SendPacketOpcode send : SendPacketOpcode.values()) {
				if (send.name().equals(splitted[1])) {
					send.setValue(Short.parseShort(splitted[2]));
					c.dropMessage(5, "[Debug Opcode] " + send.name() + " : " + send.getValue());
					break;
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Prints information about the send opcode with the specified id.";
		}
	}

	@Command(names = {"reloadevents"}, parameters = "", requiredType = AccountType.GM)
	public static class EventReset extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			for (ChannelServer instance : ChannelServer.getAllInstances()) {
				instance.reloadEvents();
			}
			c.dropShowInfo("[System] Event Reset Completed.");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Reloads the event instances in all channels.";
		}
	}

	@Command(names = {"search"}, parameters = "<Npc/Map/Mob/REACTOR/Item/Skill> <value_to_search>", requiredType = AccountType.GM)
	public static class Search extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			String type = splitted[1];
			String search = StringUtil.joinStringFrom(splitted, 2);
			MapleData data = null;
			MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File("wz/String.wz"));
			c.dropMessage(6, "<<type: " + type + " | search word: " + search + ">>");

			if (type.equalsIgnoreCase("Npc")) {
				List<String> retNpcs = new ArrayList<String>();
				data = dataProvider.getData("Npc.img");
				List<Pair<Integer, String>> npcPairList = new LinkedList<Pair<Integer, String>>();
				for (MapleData npcIdData : data.getChildren()) {
					npcPairList.add(new Pair<Integer, String>(Integer.parseInt(npcIdData.getName()),
							MapleDataTool.getString(npcIdData.getChildByPath("name"), "NO-NAME")));
				}
				for (Pair<Integer, String> npcPair : npcPairList) {
					if (npcPair.getRight().toLowerCase().contains(search.toLowerCase())) {
						retNpcs.add(npcPair.getLeft() + " - " + npcPair.getRight());
					}
				}
				if (retNpcs != null && retNpcs.size() > 0) {
					for (String singleRetNpc : retNpcs) {
						c.dropMessage(6, singleRetNpc);
					}
				} else {
					c.dropMessage(6, "No npc found.");
				}

			} else if (type.equalsIgnoreCase("Map")) {
				List<String> retMaps = new ArrayList<String>();
				data = dataProvider.getData("Map.img");
				List<Pair<Integer, String>> mapPairList = new LinkedList<Pair<Integer, String>>();
				for (MapleData mapAreaData : data.getChildren()) {
					for (MapleData mapIdData : mapAreaData.getChildren()) {
						mapPairList.add(new Pair<Integer, String>(Integer.parseInt(mapIdData.getName()),
								MapleDataTool.getString(mapIdData.getChildByPath("streetName"), "NO-NAME") + " - "
										+ MapleDataTool.getString(mapIdData.getChildByPath("mapName"), "NO-NAME")));
					}
				}
				for (Pair<Integer, String> mapPair : mapPairList) {
					if (mapPair.getRight().toLowerCase().contains(search.toLowerCase())) {
						retMaps.add(mapPair.getLeft() + " - " + mapPair.getRight());
					}
				}
				if (retMaps != null && retMaps.size() > 0) {
					for (String singleRetMap : retMaps) {
						c.dropMessage(6, singleRetMap);
					}
				} else {
					c.dropMessage(6, "No map found.");
				}
			} else if (type.equalsIgnoreCase("Mob")) {
				List<String> retMobs = new ArrayList<String>();
				data = dataProvider.getData("Mob.img");
				List<Pair<Integer, String>> mobPairList = new LinkedList<Pair<Integer, String>>();
				for (MapleData mobIdData : data.getChildren()) {
					mobPairList.add(new Pair<Integer, String>(Integer.parseInt(mobIdData.getName()),
							MapleDataTool.getString(mobIdData.getChildByPath("name"), "NO-NAME")));
				}
				for (Pair<Integer, String> mobPair : mobPairList) {
					if (mobPair.getRight().toLowerCase().contains(search.toLowerCase())) {
						retMobs.add(mobPair.getLeft() + " - " + mobPair.getRight());
					}
				}
				if (retMobs != null && retMobs.size() > 0) {
					for (String singleRetMob : retMobs) {
						c.dropMessage(6, singleRetMob);
					}
				} else {
					c.dropMessage(6, "No monsters found.");
				}

			} else if (type.equalsIgnoreCase("REACTOR")) {
				c.dropMessage(6, "NOT ADDED YET");

			} else if (type.equalsIgnoreCase("Item")) {
				List<String> retItems = new ArrayList<String>();
				for (Pair<Integer, String> itemPair : ItemInformation.getInstance().getAllItems()) {
					if (itemPair.getRight().toLowerCase().contains(search.toLowerCase())) {
						retItems.add(itemPair.getLeft() + " - " + itemPair.getRight());
					}
				}
				if (retItems != null && retItems.size() > 0) {
					for (String singleRetItem : retItems) {
						c.dropMessage(6, singleRetItem);
					}
				} else {
					c.dropMessage(6, "No items found.");
				}

			} else if (type.equalsIgnoreCase("Skill")) {
				List<String> retSkills = new ArrayList<String>();
				data = dataProvider.getData("Skill.img");
				List<Pair<Integer, String>> skillPairList = new LinkedList<Pair<Integer, String>>();
				for (MapleData skillIdData : data.getChildren()) {
					skillPairList.add(new Pair<Integer, String>(Integer.parseInt(skillIdData.getName()),
							MapleDataTool.getString(skillIdData.getChildByPath("name"), "NO-NAME")));
				}
				for (Pair<Integer, String> skillPair : skillPairList) {
					if (skillPair.getRight().toLowerCase().contains(search.toLowerCase())) {
						retSkills.add(skillPair.getLeft() + " - " + skillPair.getRight());
					}
				}
				if (retSkills != null && retSkills.size() > 0) {
					for (String singleRetSkill : retSkills) {
						c.dropMessage(6, singleRetSkill);
					}
				} else {
					c.dropMessage(6, "No skills found.");
				}
			} else {
				c.dropMessage(6, "This search cannot be processed.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Tries to find the specified value to search in one of the specified wz's.";
		}

	}

	@Command(names = {"servernotice"}, parameters = "<message>", requiredType = AccountType.LOWGM)
	public static class ServerNotice extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			Collection<ChannelServer> cservs = ChannelServer.getAllInstances();
			String outputMessage = StringUtil.joinStringFrom(splitted, 1);
			for (ChannelServer cserv : cservs) {
				cserv.setServerMessage(outputMessage);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sends a server notice (scrolling message at the top) with the specified message. Note: if the message is empty it'll remove the message at the top altogether.";
		}

	}

	@Command(names = {"shutdownchannels"}, parameters = "<amount_in_seconds>", requiredType = AccountType.ADMIN)
	public static class ServerShutdown extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int time = 60000;
			if (splitted.length > 1) {
				time = Integer.parseInt(splitted[1]) * 1000;
			}
			
			c.dropMessage(6, "Shutting down all channel servers in " + (time) + " seconds");
			
			CommandProcessor.forcePersisting();
			ChannelServer.shutdown(time);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Shuts down all channel servers in the specified time (or 60 secs if no time was specified).";
		}
	}

	@Command(names = {"shutdowncs"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class CSShutdown extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(6, "Shutting down the cashshop server.");
			CashShopServer.getInstance().shutdown();
			return 1;
		}

		@Override
		public String getDescription() {
			return "Shuts down the cash shop server immediately.";
		}
	}

	@Command(names = {"shutdownlogin"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ServerShutdownLogin extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(6, "Shutting down logins for all channels..");
			for (ChannelServer cs : ChannelServer.getAllInstances()) {
				cs.shutdownLogin();
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Shuts down logins for all channels.";
		}
	}

	@Command(names = {"shutdownmerchants"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ServerShutdownMerchant extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(6, "Shutting down all merchants..");
			
			c.getClient().getChannelServer().closeAllMerchant();
			return 1;
		}

		@Override
		public String getDescription() {
			return "Shuts down all merchants in your current channel.";
		}
	}

	@Command(names = {"spawn"}, parameters = "<monster_id> (amount) (hp number) (exp number)", requiredType = AccountType.GM)
	public static class Spawn extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			final int mid = Integer.parseInt(splitted[1]);
			final int num = Math.min(getOptionalIntArg(splitted, 2, 1), 300);

			Long hp = getNamedLongArg(splitted, 1, "hp");
			Integer exp = getNamedIntArg(splitted, 1, "exp");

			final MapleMonster onemob = MapleLifeProvider.getMonster(mid);

			if (onemob == null) {
				c.dropMessage(6, "Monster with id: " + mid + " not found.");
				return 0;
			}
			
			long newhp = 0;
			int newexp = 0;

			final double oldExpRatio = ((double) onemob.getHp() / onemob.getMobExp());

			if (hp != null) {
				newhp = hp;
			} else {
				newhp = onemob.getMobMaxHp();
			}
			
			if (exp != null) {
				newexp = exp.intValue();
			} else {
				newexp = onemob.getMobExp();
			}

			if (newhp < 1) {
				newhp = 1;
			}
			final double newExpRatio = ((double) newhp / newexp);

			c.dropMessage(6, "EXP and HP ratio: (After adjustment-" + newExpRatio + " : Before adjustment-" + oldExpRatio + ")");

			if (c.getGMLevel() <= 5) {
				if (mid == 8810018 || mid == 8810118 || mid == 5100001 || mid == 5130106 || mid == 8190001
						|| mid == 9001009 || mid == 9300256 || mid == 9300257 || mid == 9300280 || mid == 9300281
						|| mid == 9300282 || mid == 9300283 || mid == 9300284) {
					c.dropMessage(6, "This monster is blocked.");
					return 0;
				}
			}
			final OverrideMonsterStats overrideStats = new OverrideMonsterStats();
			overrideStats.setOHp(newhp);
			overrideStats.setOExp(newexp);
			overrideStats.setOMp(onemob.getMobMaxMp());

			for (int i = 0; i < num; i++) {
				MapleMonster mob = MapleLifeProvider.getMonster(mid);
				mob.setOverrideStats(overrideStats);
				c.getMap().spawnMonsterOnGroundBelow(mob, c.getPosition());
				c.dropMessage(6, "" + mob.getObjectId());
				for (Pair<Integer, Integer> ms : mob.getSkills()) {
					c.dropMessage(6, ms.getLeft() + ":" + ms.getRight());
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns a monster with the specified mob id and optionally the amount of monsters to spawn. Note: the hp/exp can be changed using the named parameters. Example: !spawn 1210102 2 hp 1000 exp 1000";
		}
	}

	@Command(names = {"rune"}, parameters = "<type_id>", requiredType = AccountType.GM)
	public static class Rune extends AdminCommand {

		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			Point pos = c.getPosition();
			int type = Integer.parseInt(splitted[1]);
			if (type >= 0 && type <= 7) {
				MapleRune rune = new MapleRune(Integer.parseInt(splitted[1]), pos.x - 50, pos.y,
						c.getMap());
				List<MapleMapObject> runes = c.getMap().getAllRune();
				for (int i = 0; i < runes.size(); i++) {
					c.getMap().removeMapObject(runes.get(i));
				}
				c.getMap().addMapObject(rune);
				c.getMap().broadcastMessage(RunePacket.spawnRune(rune, false));
				c.getMap().broadcastMessage(RunePacket.spawnRune(rune, true));
			} else {
				c.message("Only 0 ~ 7 can be input.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns a rune of the specified type (0-7) at your current location.";
		}
	}

	@Command(names = {"timer"}, parameters = "(amount)", requiredType = AccountType.LOWGM)
	public static class Clock extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getMap().broadcastMessage(MainPacketCreator.getClock(getOptionalIntArg(splitted, 1, 60)));
			return 1;
		}

		@Override
		public String getDescription() {
			return "Creates a timer at the top of the screen for either 60 seconds or the specified amount of time.";
		}
	}

	@Command(names = {"packet"}, parameters = "<hex_data>", requiredType = AccountType.ADMIN)
	public static class Packet extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length > 1) {
				c.getClient().getSession().writeAndFlush(
						MainPacketCreator.getPacketFromHexString(StringUtil.joinStringFrom(splitted, 1)));
			} else {
				c.dropMessage(6, "Please enter packet data.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sends a packet with the specified hex code to yourself.";
		}
	}

	@Command(names = {"warp"}, parameters = "<name>", requiredType = AccountType.LOWGM)
	public static class Warp extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer cserv = c.getClient().getChannelServer();
			MapleCharacter victim = cserv.getPlayerStorage().getCharacterByName(splitted[1]);
			if (victim != null) {
				c.changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getPosition()));
				victim.getClient().getSession().writeAndFlush(SLFCGPacket.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
			} else {
				MapleCharacter chr = null;
				for (ChannelServer cserv_ : ChannelServer.getAllInstances()) {
					chr = cserv_.getPlayerStorage().getCharacterByName(splitted[1]);
					if (chr != null) {
						break;
					}
				}
				if (chr != null) {
					MapleLocalisation loc = WorldConnected.getLocation(splitted[1]);
					c.dropMessage(6, "Warp by changing the channel. Please wait.");
					MapleCharacter.crossChannelWarp(c.getClient(), loc.map, loc.channel);
					victim.getClient().getSession().writeAndFlush(SLFCGPacket.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
				} else {
					c.dropMessage(6, "No target player found.");
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Warps you to the specific player, if they're not on your channel it will change channel for you.";
		}

	}

	@Command(names = {"summons"}, parameters = "<name>", requiredType = AccountType.LOWGM)
	public static class Summons extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer cserv = c.getClient().getChannelServer();
			MapleCharacter victim = cserv.getPlayerStorage().getCharacterByName(splitted[1]);
			if (victim != null) {
				if (victim.getMapId() == c.getMapId()) {
					victim.getClient().getSession().writeAndFlush(SLFCGPacket
							.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
				} else {
					victim.changeMap(c.getMap(),
							c.getMap().findClosestSpawnpoint(c.getPosition()));
					victim.getClient().getSession().writeAndFlush(SLFCGPacket
							.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
				}
			} else {
				MapleCharacter chr = null;
				for (ChannelServer cserv_ : ChannelServer.getAllInstances()) {
					chr = cserv_.getPlayerStorage().getCharacterByName(splitted[1]);
					if (chr != null) {
						break;
					}
				}
				if (chr != null) {
					MapleLocalisation loc = WorldConnected.getLocation(c.getName());
					chr.dropMessage(6, "Will be summoned by changing channel.");
					MapleCharacter.crossChannelWarp(chr.getClient(), loc.map, loc.channel);
					chr.getClient().getSession().writeAndFlush(SLFCGPacket
							.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
				} else {
					c.dropMessage(6, "No target player found.");
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Summons another player to your location, if they're not on your channel it will change channel for them.";
		}
	}

	@Command(names = {"summonsall"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class SummonsAll extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer cserv = c.getClient().getChannelServer();
			for (MapleCharacter victim : cserv.getPlayerStorage().getAllCharacters().values()) {
				if (victim.getMapId() != c.getMapId()) {
					victim.changeMap(c.getMap(), c.getPosition());
					victim.getClient().getSession().writeAndFlush(SLFCGPacket
							.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
				} else {
					MapleCharacter chr = null;
					for (ChannelServer cserv_ : ChannelServer.getAllInstances()) {
						chr = cserv_.getPlayerStorage().getAllCharacters()
								.get(cserv_.getPlayerStorage().getAllCharacters().get(chr));
						if (chr != null) {
							break;
						}
					}
					if (chr != null) {
						MapleLocalisation loc = WorldConnected.getLocation(c.getName());
						chr.dropMessage(6, "Will be summoned by changing channel.");
						MapleCharacter.crossChannelWarp(chr.getClient(), loc.map, loc.channel);
						chr.getClient().getSession().writeAndFlush(SLFCGPacket
								.CharReLocationPacket(c.getPosition().x, c.getPosition().y));
					}
					chr.dropMessage(6, "All summoned.");
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Summons every player on the server to your location.";
		}
	}

	@Command(names = {"map"}, parameters = "<map_id> (portal_id)", requiredType = AccountType.LOWGM)
	public static class MapTo extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ChannelServer cserv = c.getClient().getChannelServer();
			MapleMap target = null;
			if (c.getEventInstance() != null) {
				target = c.getEventInstance().getMapFactory().getMap(Integer.parseInt(splitted[1]));
			} else {
				target = cserv.getMapFactory().getMap(Integer.parseInt(splitted[1]));
			}

			MaplePortal targetPortal = null;
			if (splitted.length > 2) {
				try {
					targetPortal = target.getPortal(Integer.parseInt(splitted[2]));
				} catch (IndexOutOfBoundsException e) {
					c.dropMessage(5, "Invalid portal selected.");
				} catch (NumberFormatException a) {
				}
			}
			if (targetPortal == null) {
				targetPortal = target.getPortal(0);
			}
			c.changeMap(target, targetPortal);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Warps you to a map with the specified id and optionally spawns your near the portal based on the optional parameter portal id.";
		}

	}

	@Command(names = {"clearinv"}, parameters = "<all/equipped/equip/use/setup/etc/cash>", requiredType = AccountType.GM)
	public static class Clear extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			Map<Pair<Short, Short>, MapleInventoryType> eqs = new ArrayMap<Pair<Short, Short>, MapleInventoryType>();
			if (splitted[1].equals("all")) {
				for (MapleInventoryType type : MapleInventoryType.values()) {
					for (IItem item : c.getInventory(type)) {
						eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), type);
					}
				}
			} else if (splitted[1].equals("equipped")) {
				for (IItem item : c.getInventory(MapleInventoryType.EQUIPPED)) {
					eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()),
							MapleInventoryType.EQUIPPED);
				}
			} else if (splitted[1].equals("equip")) {
				for (IItem item : c.getInventory(MapleInventoryType.EQUIP)) {
					eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.EQUIP);
				}
			} else if (splitted[1].equals("use")) {
				for (IItem item : c.getInventory(MapleInventoryType.USE)) {
					eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.USE);
				}
			} else if (splitted[1].equals("setup")) {
				for (IItem item : c.getInventory(MapleInventoryType.SETUP)) {
					eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.SETUP);
				}
			} else if (splitted[1].equals("etc")) {
				for (IItem item : c.getInventory(MapleInventoryType.ETC)) {
					eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.ETC);
				}
			} else if (splitted[1].equals("cash")) {
				for (IItem item : c.getInventory(MapleInventoryType.CASH)) {
					eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), MapleInventoryType.CASH);
				}
			} else {
				c.dropMessage(6, "[all/equipped/equip/use/setup/etc/cash]");
			}
			for (Entry<Pair<Short, Short>, MapleInventoryType> eq : eqs.entrySet()) {
				InventoryManipulator.removeFromSlot(c.getClient(), eq.getValue(), eq.getKey().left, eq.getKey().right, false, false);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Clears everything in your inventory based on the specified bag.";
		}
	}

	@Command(names = {"skill"}, parameters = "<skill_id> (current_level) (max_level)", requiredType = AccountType.LOWGM)
	public static class Skill extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			ISkill skill = SkillFactory.getSkill(Integer.parseInt(splitted[1]));
			byte level = (byte) getOptionalIntArg(splitted, 2, 1);
			byte masterlevel = (byte) getOptionalIntArg(splitted, 3, 1);
			if (level > skill.getMaxLevel()) {
				level = skill.getMaxLevel();
			}
			c.changeSkillLevel(skill, level, masterlevel);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Changes the skill level based on the specified skill id and optionally the current level amount and max level amount.";
		}
	}

	@Command(names = {"ap"}, parameters = "<amount>", requiredType = AccountType.LOWGM)
	public static class AP extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.setRemainingAp(Integer.parseInt(splitted[1]));
			c.updateSingleStat(PlayerStatList.AVAILABLEAP, c.getRemainingAp());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets your remaining ap to the specified value.";
		}
	}

	@Command(names = {"job"}, parameters = "<job_id>", requiredType = AccountType.LOWGM)
	public static class Job extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.changeJob(Integer.parseInt(splitted[1]));
			return 1;
		}

		@Override
		public String getDescription() {
			return "Changes your job to that of the specified id.";
		}
	}

	@Command(names = {"mapid"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class CM extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(5, "You are currently in the following map: " + c.getMap().getId());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Prints the id of the map you are currently located in.";
		}
	}

	@Command(names = {"shop"}, parameters = "<shop_id>", requiredType = AccountType.LOWGM)
	public static class Shop extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleShopFactory shop = MapleShopFactory.getInstance();
			int shopId = Integer.parseInt(splitted[1]);
			if (shop.getShop(shopId) != null) {
				shop.getShop(shopId).sendShop(c.getClient());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Opens a shop window of the specific id.";
		}
	}

	@Command(names = {"meso"}, parameters = "<amount>", requiredType = AccountType.LOWGM)
	public static class Meso extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length == 1) {
				c.gainMeso(9999999999L - c.getMeso(), true);
			} else {
				c.setMeso(Long.parseLong(splitted[1]));
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets your mesos to the specified value, if no value is specified it will set it to 10b.";
		}
	}

	@Command(names = {"item"}, parameters = "<item_id> (amount)", requiredType = AccountType.GM)
	public static class GainItem extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			final int itemId = Integer.parseInt(splitted[1]);
			final short quantity = (short) getOptionalIntArg(splitted, 2, 1);

			if (c.getGMLevel() < 6) {
				for (int i : GameConstants.itemBlock) {
					if (itemId == i) {
						c.dropMessage(5, "This item cannot be created at your GM level.");
					}
				}
			}
			ItemInformation ii = ItemInformation.getInstance();
			if (GameConstants.isPet(itemId)) {
				c.dropMessage(5, "Please use the cash shop for pets.");
			} else if (!ii.itemExists(itemId)) {
				c.dropMessage(5, itemId + " Item does not exist.");
			} else {
				IItem item;
				if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
					item = ii.randomizeStats((Equip) ii.getEquipById(itemId), true);
				} else {
					item = new Item(itemId, (byte) 0, quantity, (byte) 0);
				}
				item.setOwner(c.getName());
				item.setGMLog(CurrentTime.getAllCurrentTime() + "on " + c.getName() + "Item obtained by command of.");
				InventoryManipulator.addbyItem(c.getClient(), item);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns an item of the specified id and optionally sets the amount.";
		}
	}
	
	@Command(names = {"proitem"}, parameters = "<id> <stats> <attack> <mainpot_id> <bpot_id>", requiredType = AccountType.ADMIN)
	public static class ProItem extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length < 6) {
				c.dropMessage(5, "Error, usage: proitem <id> <stats> <attack> <mainpot> <bpot>");
				return 0;
			}
			
			final int itemId = Integer.parseInt(splitted[1]);
			ItemInformation ii = ItemInformation.getInstance();
			if (itemId >= 2000000) {
				c.dropMessage(5, "You can only get equips.");
				return 0;
			} else if (!ii.itemExists(itemId)) {
				c.dropMessage(5, itemId + " does not exist");
				return 0;
			} else {
				Equip equip = (Equip) ii.getEquipById(itemId);
				
				short mainStats = Short.parseShort(splitted[2]);
				equip.setStr(mainStats);
				equip.setDex(mainStats);
				equip.setInt(mainStats);
				equip.setLuk(mainStats);
				
				short attack = Short.parseShort(splitted[3]);
				equip.setWatk(attack);
				equip.setMatk(attack);
				
				int mainpot = Integer.parseInt(splitted[4]);
				int bpot = Integer.parseInt(splitted[5]);
				
				int tier = Integer.valueOf(String.valueOf(mainpot).charAt(0));
				tier = Math.max(Math.min(tier, 1), 4);
				
				equip.setState((byte) 20); // legendary on both bpot and mpot.. I think
				
				equip.setPotential1(mainpot);
				equip.setPotential2(mainpot);
				equip.setPotential3(mainpot);
				
				equip.setPotential4(bpot);
				equip.setPotential5(bpot);
				equip.setPotential6(bpot);
				
				short flag = equip.getFlag();
		        flag |= ItemFlag.UNTRADEABLE.getValue();
		        
		        equip.setFlag(flag);
				equip.setOwner(c.getName());
				equip.setGMLog(CurrentTime.getAllCurrentTime() + "on " + c.getName() + " Item obtained by command of.");
				
				InventoryManipulator.addbyItem(c.getClient(), equip);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns an equipment of the specified id with the specified stats, attack, main potential and bonus potential.";
		}
	}

	@Command(names = {"drop"}, parameters = "<item_id> (quantity)", requiredType = AccountType.GM)
	public static class Drop extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			final int itemId = Integer.parseInt(splitted[1]);
			final short quantity = (short) getOptionalIntArg(splitted, 2, 1);
			if (itemId == 2100106 || itemId == 2100107) {
				c.dropMessage(5, "Item is blocked.");
				return 0;
			}
			if (GameConstants.isPet(itemId)) {
				c.dropMessage(5, "Please buy the pet at the cash shop.");
			} else {
				IItem toDrop;
				if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
					ItemInformation ii = ItemInformation.getInstance();
					toDrop = ii.randomizeStats((Equip) ii.getEquipById(itemId), true);
				} else {
					toDrop = new Item(itemId, (byte) 0, quantity, (byte) 0);
				}
				toDrop.setGMLog(c.getName() + "Item created by this drop command");

				c.getMap().spawnItemDrop(c, c, toDrop, c.getPosition(), true, true);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Drops an item of the specified id to the floor and optionally sets the amount.";
		}
	}

	@Command(names = {"levelup"}, parameters = "<amount>", requiredType = AccountType.LOWGM)
	public static class LevelUp extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			short level = Short.parseShort(splitted[1]);
			int levelgain = level - c.getLevel();
			for (int i = 0; i < levelgain; i++) {
				c.levelUp();
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Levels your character up by the specified amount. (Note: you need to be a lower level than the level specified)";
		}
	}

	@Command(names = {"level"}, parameters = "<amount>", requiredType = AccountType.LOWGM)
	public static class Level extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int level = Integer.parseInt(splitted[1]);
			c.updateLevel(level);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets your character level to the specified amount.";
		}
	}

	@Command(names = {"onlinenow"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class Online extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(6, "The users currently connected to the channel are:. :");
			String names = "";
			for (MapleCharacter name : c.getClient().getChannelServer().getPlayerStorage().getAllCharacters().values()) {
				names += name.getName();
				names += ", ";
			}
			if (names.equals("")) {
				names = "There are no users currently connected to the channel.";
			}
			c.dropMessage(6, names);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves a list of all people currently online in your current channel.";
		}
	}

	@Command(names = {"saveall"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class SaveAll extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(6, "Start saving...");
			for (ChannelServer cserv : ChannelServer.getAllInstances()) {
				cserv.saveAllMerchant();
				for (MapleCharacter hp : cserv.getPlayerStorage().getAllCharacters().values()) {
					if (hp != null) {
						hp.saveToDB(false, false);
					}
				}
			}
			MedalRanking.getInstance().save();
			c.dropMessage(6, "[System] Saved.");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Saves all merchants and players currently online on the server to the database.";
		}
	}

	@Command(names = {"yell"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class Word extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length > 2) {
				StringBuilder sb = new StringBuilder();
				sb.append(StringUtil.joinStringFrom(splitted, 2));
				if (!c.hasGmLevel((byte) 5)) {
					if (Integer.parseInt(splitted[1]) >= 12) {
						c.dropMessage(6, "Color code not available. Available Code: 0 ~ 11");
						return 0;
					}
				}
				byte[] packet = MainPacketCreator.getGMText(Integer.parseInt(splitted[1]), sb.toString());
				WorldBroadcasting.broadcastMessage(packet);
			} else {
				c.dropMessage(6, "Usage: !yell <Color code> <say>");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sends a yell message to the entire server using the specified color id (0-11).";
		}
	}

	@Command(names = {"vac"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class Bag extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (!c.isHidden()) {
				c.dropMessage(6, "Bag is only available in Hide Mode.");
			} else {
				for (final MapleMapObject mmo : c.getMap().getAllMonster()) {
					final MapleMonster monster = (MapleMonster) mmo;
					monster.setPosition(c.getPosition());
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Moves all monsters in your current map to your current character location.";
		}
	}

	@Command(names = {"onlineall"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class OnlineAll extends AdminCommand {

		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			for (ChannelServer cserv : ChannelServer.getAllInstances()) {
				String names = "channel "
						+ (cserv.getChannel() == 0 ? 1 : cserv.getChannel() == 1 ? "20ºº¿ÃªÛ" : cserv.getChannel()) + " ("
						+ cserv.getPlayerStorage().getAllCharacters().size() + " persons) : ";

				for (MapleCharacter name : cserv.getPlayerStorage().getAllCharacters().values()) {
					names += name.getName();
					names += ", ";
				}
				c.dropMessage(6, names);
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Retrieves a list of all players who are currently online on the entire server, sorted by channel.";
		}
	}

	@Command(names = {"bgm"}, parameters = "<song_name>", requiredType = AccountType.LOWGM)
	public static class BGM extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getMap().broadcastMessage(MainPacketCreator.musicChange(splitted[1]));
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets the current background music to the specified song.";
		}
	}

	@Command(names = {"maxstats"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class MaxStats extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getStat().setAmbition(Short.MAX_VALUE);
			c.getStat().setCharm(Short.MAX_VALUE);
			c.getStat().setWillPower(Short.MAX_VALUE);
			c.getStat().setDiligence(Short.MAX_VALUE);
			c.getStat().setEmpathy(Short.MAX_VALUE);
			c.getStat().setInsight(Short.MAX_VALUE);
			c.getStat().setDex(c.getMaxStats());
			c.getStat().setInt(c.getMaxStats());
			c.getStat().setLuk(c.getMaxStats());
			c.getStat().setStr(c.getMaxStats());
			c.getStat().setMaxHp(500000);
			c.getStat().setHp(500000, c);
			if (!GameConstants.isZero(c.getJob())) {
				c.getStat().setMaxMp(500000);
				c.getStat().setMp(500000);
			}
			c.updateSingleStat(PlayerStatList.STR, c.getMaxStats());
			c.updateSingleStat(PlayerStatList.DEX, c.getMaxStats());
			c.updateSingleStat(PlayerStatList.INT, c.getMaxStats());
			c.updateSingleStat(PlayerStatList.LUK, c.getMaxStats());
			c.updateSingleStat(PlayerStatList.CHARISMA, Short.MAX_VALUE);
			c.updateSingleStat(PlayerStatList.WILLPOWER, Short.MAX_VALUE);
			c.updateSingleStat(PlayerStatList.INSIGHT, Short.MAX_VALUE);
			c.updateSingleStat(PlayerStatList.CHARM, Short.MAX_VALUE);
			c.updateSingleStat(PlayerStatList.CRAFT, Short.MAX_VALUE);
			c.updateSingleStat(PlayerStatList.SENSE, Short.MAX_VALUE);
			c.updateSingleStat(PlayerStatList.MAXHP, 500000);
			if (!GameConstants.isZero(c.getJob())) {
				c.updateSingleStat(PlayerStatList.MAXMP, 500000);
				c.updateSingleStat(PlayerStatList.MP, 500000);
			}
			c.updateSingleStat(PlayerStatList.HP, 500000);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Maxes your stats, traits, hp and mp on your character.";
		}
	}

	@Command(names = {"resetstats"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class StatReset extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getStat().setAmbition(0);
			c.getStat().setCharm(0);
			c.getStat().setWillPower(0);
			c.getStat().setDiligence(0);
			c.getStat().setEmpathy(0);
			c.getStat().setInsight(0);
			c.getStat().setStr(4);
			c.getStat().setDex(4);
			c.getStat().setInt(4);
			c.getStat().setLuk(4);
			c.getStat().setMaxHp(1000);
			c.getStat().setMaxMp(1000);
			c.getStat().setHp(1000, c);
			c.getStat().setMp(1000);
			c.updateSingleStat(PlayerStatList.STR, 4);
			c.updateSingleStat(PlayerStatList.DEX, 4);
			c.updateSingleStat(PlayerStatList.INT, 4);
			c.updateSingleStat(PlayerStatList.LUK, 4);
			c.updateSingleStat(PlayerStatList.CHARISMA, 0);
			c.updateSingleStat(PlayerStatList.WILLPOWER, 0);
			c.updateSingleStat(PlayerStatList.INSIGHT, 0);
			c.updateSingleStat(PlayerStatList.CHARM, 0);
			c.updateSingleStat(PlayerStatList.CRAFT, 0);
			c.updateSingleStat(PlayerStatList.SENSE, 0);
			c.updateSingleStat(PlayerStatList.MAXHP, 1000);
			c.updateSingleStat(PlayerStatList.MAXMP, 1000);
			c.updateSingleStat(PlayerStatList.MP, 1000);
			c.updateSingleStat(PlayerStatList.HP, 1000);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Resets your stats, traits, mp and hp to the lowest possible number.";
		}
	}

	@Command(names = {"hide"}, parameters = "", requiredType = AccountType.LOWGM)
	public static class Hide extends AdminCommand {

		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			boolean hided = c.isHidden();
			if (hided) {
				c.message(6, "Hide has been released.");
			} else {
				c.message(6, "Hide status applied.");
			}
			c.setHide(!hided);
			return 1;
		}

		@Override
		public String getDescription() {
			return "(Un)hides your character from all regular players.";
		}
	}

	@Command(names = {"vp"}, parameters = "<name> <amount>", requiredType = AccountType.LOWGM)
	public static class VP extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleCharacter who = c.getClient().getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
			final int vp = Integer.parseInt(splitted[2]);
			if (who != null) {
				if (vp > 0) {
					LoggerChatting.writeLog(LoggerChatting.givercLog, LoggerChatting.getRcgive("vote points", c, who, vp));
					who.gainVPoints(vp);
					who.dropMessage(6, "You have received: " + vp + " vote points from: " + c.getName());
					c.dropMessage(5, "You have given: " + vp + " vote points to: " + who.getName());
				} else {
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getGMText(20, "You have less than 0 points."));
				}
			} else {
				c.dropMessage(5, "No target player found.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Gives the specified amount of vote points to the specified user.";
		}
	}

	@Command(names = {"dp"}, parameters = "<name> <amount>", requiredType = AccountType.LOWGM)
	public static class DonationPoint extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleCharacter who = c.getClient().getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
			final int dp = Integer.parseInt(splitted[2]);
			if (who != null) {
				if (dp > 0) {
					LoggerChatting.writeLog(LoggerChatting.givercLog,LoggerChatting.getRcgive("donation points", c, who, dp));
					who.gainRC(dp);
					who.dropMessage(6, "You have received: " + dp + " donation points from: " + c.getName());
					c.dropMessage(5, "You have given: " + dp + " donation points to: " + who.getName());
				} else {
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getGMText(20, "You have less than 0 points."));
				}
			} else {
				c.dropMessage(5, "No target player found.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Gives the specified amount of donation points to the specified user.";
		}
	}

	@Command(names = {"pp"}, parameters = "<name> <amount>", requiredType = AccountType.LOWGM)
	public static class PromotionPoint extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleCharacter who = c.getClient().getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
			final int pp = Integer.parseInt(splitted[2]);
			if (who != null) {
				if (pp > 0) {
					LoggerChatting.writeLog(LoggerChatting.givehbLog, LoggerChatting.getRcgive("promotion points", c, who, pp));
					who.gainHbPoint(pp);
					who.dropMessage(6, "You have received: " + pp + " promotion points from: " + c.getName());
					c.dropMessage(5, "You have given: " + pp + " promotion points to: " + who.getName());
				} else {
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getGMText(20, "You have less than 0 points."));
				}
			} else {
				c.dropMessage(5, "No target player found.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return null;
		}
	}

	@Command(names = {"setadddamage"}, parameters = "<name> <amount>", requiredType = AccountType.GM)
	public static class SetAddDamage extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			String Cname = splitted[1];
			for (ChannelServer ch : ChannelServer.getAllInstances()) {
				MapleCharacter other = ch.getPlayerStorage().getCharacterByName(Cname);
				if (other != null) {
					long amount = Long.parseLong(splitted[2]);
					other.setAddDamage(amount);
					other.dropMessage(5, "Your additional damge has been set to: " + other.getAddDamage() + ".");
					c.dropMessage(5, "You have set: " + other.getName() + "'s additional damage to: " + other.getAddDamage() + ". ");
					return 1;
				}
			}
			return 0;
		}

		@Override
		public String getDescription() {
			return "Sets the specified user's additional damage to the specified amount.";
		}
	}
	
	@Command(names = {"adddamage"}, parameters = "<name> <amount>", requiredType = AccountType.GM)
	public static class AddDamage extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			String Cname = splitted[1];
			for (ChannelServer ch : ChannelServer.getAllInstances()) {
				MapleCharacter other = ch.getPlayerStorage().getCharacterByName(Cname);
				if (other != null) {
					long amount = Long.parseLong(splitted[2]);
					other.setAddDamage(other.getAddDamage() + amount);
					other.dropMessage(5, "Your additional damge has been set to: " + other.getAddDamage() + ".");
					c.dropMessage(5, "You have set: " + other.getName() + "'s additional damage to: " + other.getAddDamage() + ". ");
					return 1;
				}
			}
			return 0;
		}

		@Override
		public String getDescription() {
			return "Adds the specified amount of additional damage to the specified user.";
		}
	}

	@Command(names = {"modem"}, parameters = "<name> <amount>", requiredType = AccountType.GM)
	public static class Modem extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			String Cname = splitted[1];
			for (ChannelServer ch : ChannelServer.getAllInstances()) {
				MapleCharacter chr = ch.getPlayerStorage().getCharacterByName(Cname);
				if (chr != null) {
					int amount = Integer.parseInt(splitted[2]);
					
					chr.setDamageHit(chr.getDamageHit() + amount);
					chr.dropMessage(5, "Your damage hit has been set to: " + chr.getDamageHit() + ".");
					c.dropMessage(5, "You have set: " + chr.getName() + "'s damage hit to: " + chr.getDamageHit() + ". ");
					return 1;
				} else {
					chr.dropMessage(5, chr.getName() + " The player is not connected.");
				}
			}
			return 0;
		}

		@Override
		public String getDescription() {
			return "Sets the specified user's damage hit to the specified amount.";
		}
	}

	@Command(names = {"killplayer"}, parameters = "<name>", requiredType = AccountType.LOWGM)
	public static class Kill extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			MapleCharacter who = c.getClient().getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
			if (who != null) {
				who.addMPHP(-who.getStat().getCurrentMaxHp(), -who.getStat().getCurrentMaxMp());
				c.dropMessage(5, "You have killed: " + splitted[1]);
				return 1;
			} else {
				c.dropMessage(5, "Target player not found on this channel.");
				return 0;
			}
		}

		@Override
		public String getDescription() {
			return "Kills the specified user, if they are in your channel.";
		}
	}

	@Command(names = {"togglefever"}, parameters = "", requiredType = AccountType.GM)
	public static class Fever extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (ServerConstants.feverTime) {
				ServerConstants.feverTime = false;
				c.dropMessage(5, "[GM] Fever time is off.");
			} else {
				ServerConstants.feverTime = true;
				c.dropMessage(5, "[GM] Fever time is on.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Toggles star forcing fever time";
		}
	}

	@Command(names = {"toggleshowpacket"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ToggleShowPacket extends AdminCommand {

		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (ServerConstants.showPackets) {
				ServerConstants.showPackets = false;
				c.dropMessage(5, "[GM] Packet output turned off.");
			} else {
				ServerConstants.showPackets = true;
				c.dropMessage(5, "[GM] Packet output turned on.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Toggles the ability to display packets.";
		}
	}

	@Command(names = {"toggleservercheck"}, parameters = "", requiredType = AccountType.ADMIN)
	public static class ServerCheck extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (ServerConstants.serverCheck) {
				ServerConstants.serverCheck = false;
				c.dropMessage(5, "[GM notice] Server check is off.");
			} else {
				ServerConstants.serverCheck = true;
				c.dropMessage(5, "[GM notice] Server check is on.");
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Toggles server checking.";
		}
	}

	@Command(names = {"cash"}, parameters = "<amount>", requiredType = AccountType.LOWGM)
	public static class Cash extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.modifyCSPoints(1, Integer.parseInt(splitted[1]), true);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Sets your cash points to the specified amount.";
		}
	}

	@Command(names = {"maxskill"}, parameters = "<skill_id>", requiredType = AccountType.LOWGM)
	public static class MaxSkillChar extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.maxskill(Integer.parseInt(splitted[1]));
			return 1;
		}

		@Override
		public String getDescription() {
			return "Maxes the specified skill.";
		}
	}

	@Command(names = {"damian"}, parameters = "", requiredType = AccountType.GM)
	public static class Damian extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			for (MapleMapObject ds : c.getMap().getAllDemianSword()) {
				c.getClient().getSession().writeAndFlush(DemianPacket.Demian_OnCorruptionChange());
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Spawns damian's swords.";
		}
	}

	@Command(names = {"bingo"}, parameters = "<start/stop>", requiredType = AccountType.GM)
	public static class Bingo extends AdminCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (splitted.length > 1) {
				switch (splitted[1]) {
				case "start": {
					if (c.getMapId() != 922290000) {
						return 0;
					}
					if (c.getMap().getCharacters().size() < 5) {
						c.getClient().getSession().writeAndFlush(MainPacketCreator.OnAddPopupSay(1052230, 3000,
								"#face1#5 bingo minimum enrollment", ""));
						return 0;
					}
					c.getMap().broadcastMessage(MainPacketCreator.getClock(30));
					MapTimer.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							for (MapleCharacter chr : c.getMap().getCharacters()) {
								if (chr != null) {
									chr.changeMap(922290100, 0);
								}
							}
						}
					}, 30 * 1000);
					MapTimer.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							if (c.getMapId() == 922290100) {
								BingoGame bingo = new BingoGame(c.getMap().getCharacters());
								for (MapleCharacter chr : c.getMap().getCharacters()) {
									if (chr != null) {
										chr.setBingoGame(bingo);
										chr.getClient().getSession()
												.writeAndFlush(MainPacketCreator.musicChange("BgmEvent/dolphin_night"));
										chr.getClient().getSession()
												.writeAndFlush(MainPacketCreator.playSE("multiBingo/start"));
										chr.getClient().getSession()
												.writeAndFlush(MainPacketCreator.showMapEffect("Gstar/start"));
									}
								}
							}
						}
					}, 40 * 1000);
					MapTimer.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							c.getBingoGame().StartGame();
						}
					}, 42 * 1000);
					break;
				}
				case "Stop": {
					c.getBingoGame().StopBingo();
					break;
				}
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Starts/Stops a game of bingo.";
		}
	}
}
