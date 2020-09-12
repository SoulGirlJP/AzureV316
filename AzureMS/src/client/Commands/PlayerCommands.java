package client.Commands;

import client.Character.MapleCharacter;

import client.ItemInventory.IEquip;
import client.ItemInventory.MapleInventoryType;
import client.Stats.PlayerStatList;
import client.Stats.PlayerStats;
import connections.Packets.MainPacketCreator;
import constants.Data.AccountType;
import constants.GameConstants;
import constants.ServerConstants;
import scripting.NPC.NPCScriptManager;
import server.Items.InventoryManipulator;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleMapHandling.MaplePortal;

public class PlayerCommands {
    @Command(names = {"help"}, parameters = "", requiredType = AccountType.PLAYER) 
    public static class Help extends PlayerCommand {

        @Override
        public int execute(MapleCharacter chr, String[] args) {
            Class<?>[] commands = PlayerCommands.class.getClasses();
            ICommand iCommand = null;
            
            chr.dropMessage(6, "Player Commands: ");
            for (Class<?> cmd : commands) {
                String s = "";
                Command command = (Command) cmd.getAnnotation(Command.class);
                try {
                    iCommand = (PlayerCommand) cmd.getConstructor().newInstance();

                    s += "\t- ";
                    for (int i = 0; i < command.names().length; i++) {
                        String name = command.names()[i];
                        s += "@";
                        if (i == command.names().length - 1) {
                                s += name;
                        } else {
                                s += name + ", ";
                        }
                    }
                    s += " " + command.parameters() + " => " + cmd.getDeclaredMethod("getDescription").invoke(iCommand) + "\n";
                    chr.dropMessage(6, s);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            return 1;
        }

        @Override
        public String getDescription() {
            return "Retrieves a list of all player commands.";
        }
    }
    
        /* 
	@Command(names = {"check"}, parameters = "", requiredType = AccountType.PLAYER) 
	public static class Check extends PlayerCommand { // TODO: properly set-up
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			final PlayerStats stats = c.getStat();
			final StringBuilder sb = new StringBuilder();
			
			sb.append(c.getName() + "'s stats => ");
			sb.append(" || Str: ").append(stats.getLocalStr());
			sb.append(" || Dex: ").append(stats.getLocalDex());
			sb.append(" || Luk: ").append(stats.getLocalLuk());
			sb.append(" || Int: ").append(stats.getLocalInt());
			sb.append(" || Watk %: ").append(stats.getPercentWatk());
			sb.append(" || Matk %: ").append(stats.getPercentMatk());
			sb.append(" || BD %: ").append(stats.bossdam_r);
			sb.append(" || Damage %: ").append(stats.dam_r);
			sb.append(" || IED %: ").append(stats.ignoreTargetDEF);
			sb.append(" || Att Speed: ").append(GetAttackSpeedToString(c));
			sb.append(" || Ping: ").append(c.getClient().getLatency());
			
			c.dropMessage(6, sb.toString());
			return 1;
		}
		
		private static String GetAttackSpeedToString(MapleCharacter pChr) {
			Equip eq = (Equip) pChr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);
			int weaponSpeed = (eq == null) ? -1 : eq.getSpeed();
			if (weaponSpeed > -1) {
				Integer indieBooster = pChr.getBuffedValue(BuffStats.IndieBooster);
				weaponSpeed = (indieBooster == null) ? weaponSpeed : (weaponSpeed - indieBooster.intValue());
			}
			String attkSpeedOutput = (weaponSpeed == -1) ? "N/A" : String.valueOf(weaponSpeed);
			return attkSpeedOutput;
		}

		@Override
		public String getDescription() {
			return "Displays relevant character information.";
		}
	}
	*/
	
	@Command(names = {"str"}, parameters = "<amount>", requiredType = AccountType.PLAYER)
	public static class Str extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int str = Integer.parseInt(splitted[1]);
			final PlayerStats stat = c.getStat();

			if (stat.getStr() + str > c.getMaxStats() || c.getRemainingAp() < str || c.getRemainingAp() < 0 || str < 0) {
				c.dropMessage(5, "An error occurred.");
				return 0;
			}

			stat.setStr(stat.getStr() + str);
			c.setRemainingAp(c.getRemainingAp() - str);
			c.updateSingleStat(PlayerStatList.AVAILABLEAP, c.getRemainingAp());
			c.updateSingleStat(PlayerStatList.STR, stat.getStr());

			return 1;
		}

		@Override
		public String getDescription() {
			return "Increases your STR by the specified amount based on your available remaining ap.";
		}
	}

	@Command(names = {"int"}, parameters = "<amount>", requiredType = AccountType.PLAYER)
	public static class Int extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int int_ = Integer.parseInt(splitted[1]);
			final PlayerStats stat = c.getStat();

			if (stat.getInt() + int_ > c.getMaxStats() || c.getRemainingAp() < int_ || c.getRemainingAp() < 0 || int_ < 0) {
				c.dropMessage(5, "An error occurred.");
				return 0;
			}

			stat.setInt(stat.getInt() + int_);
			c.setRemainingAp(c.getRemainingAp() - int_);
			c.updateSingleStat(PlayerStatList.AVAILABLEAP, c.getRemainingAp());
			c.updateSingleStat(PlayerStatList.INT, stat.getInt());

			return 1;
		}

		@Override
		public String getDescription() {
			return "Increases your INT by the specified amount based on your available remaining ap.";
		}
	}

	@Command(names = {"dex"}, parameters = "<amount>", requiredType = AccountType.PLAYER)
	public static class Dex extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int dex = Integer.parseInt(splitted[1]);
			final PlayerStats stat = c.getStat();

			if (stat.getDex() + dex > c.getMaxStats() || c.getRemainingAp() < dex
					|| c.getRemainingAp() < 0 || dex < 0) {
				c.dropMessage(5, "An error occurred.");
				return 0;
			}

			stat.setDex(stat.getDex() + dex);
			c.setRemainingAp(c.getRemainingAp() - dex);
			c.updateSingleStat(PlayerStatList.AVAILABLEAP, c.getRemainingAp());
			c.updateSingleStat(PlayerStatList.DEX, stat.getDex());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Increases your DEX by the specified amount based on your available remaining ap.";
		}
	}

	@Command(names = {"luk"}, parameters = "<amount>", requiredType = AccountType.PLAYER)
	public static class Luk extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int luk = Integer.parseInt(splitted[1]);
			final PlayerStats stat = c.getStat();

			if (stat.getLuk() + luk > c.getMaxStats() || c.getRemainingAp() < luk
					|| c.getRemainingAp() < 0 || luk < 0) {
				c.dropMessage(5, "An error occurred.");
				return 0;
			}

			stat.setLuk(stat.getLuk() + luk);
			c.setRemainingAp(c.getRemainingAp() - luk);
			c.updateSingleStat(PlayerStatList.AVAILABLEAP, c.getRemainingAp());
			c.updateSingleStat(PlayerStatList.LUK, stat.getLuk());
			return 1;
		}

		@Override
		public String getDescription() {
			return "Increases your LUK by the specified amount based on your available remaining ap.";
		}
	}
	
	@Command(names = {"hp"}, parameters = "<amount>", requiredType = AccountType.PLAYER)
	public static class HP extends PlayerCommand {
		@Override
		public int execute(MapleCharacter chr, String[] splitted) {
			int hp = Integer.parseInt(splitted[1]);
			final PlayerStats stat = chr.getStat();
			long MaxHP = stat.getMaxHp();
			if (chr.getRemainingAp() < hp
					|| chr.getRemainingAp() < 0 || hp < 0) {
				chr.dropMessage(5, "An error occurred.");
				return 0;
			} else {
				for (int i = 0; i < hp; i++) {
					ISkill improvingMaxHP = null;
					int improvingMaxHPLevel = 0;
					if (chr.getJob() == 0) {
						MaxHP += Randomizer.rand(8, 12);
					} else if (chr.getJob() >= 100 && chr.getJob() <= 132) {
						improvingMaxHP = SkillFactory.getSkill(1000001);
						improvingMaxHPLevel = chr.getSkillLevel(improvingMaxHP);
						MaxHP += Randomizer.rand(20, 24);
						if (improvingMaxHPLevel >= 1) {
							MaxHP += improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
						}
					} else if (chr.getJob() >= 200 && chr.getJob() <= 232) {
						MaxHP += Randomizer.rand(6, 10);
					} else if (chr.getJob() >= 300 && chr.getJob() <= 322) {
						MaxHP += Randomizer.rand(16, 20);
					} else if (chr.getJob() >= 400 && chr.getJob() <= 422) {
						MaxHP += Randomizer.rand(20, 24);
					} else if (chr.getJob() >= 500 && chr.getJob() <= 522) {
						improvingMaxHP = SkillFactory.getSkill(5100000);
						improvingMaxHPLevel = chr.getSkillLevel(improvingMaxHP);
						MaxHP += Randomizer.rand(16, 20);
						if (improvingMaxHPLevel >= 1) {
							MaxHP += improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
						}
					} else if (chr.getJob() >= 1100 && chr.getJob() <= 1111) {
						improvingMaxHP = SkillFactory.getSkill(11000000);
						improvingMaxHPLevel = chr.getSkillLevel(improvingMaxHP);
						MaxHP += Randomizer.rand(36, 42);
						if (improvingMaxHPLevel >= 1) {
							MaxHP += improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
						}
					} else if (chr.getJob() >= 1200 && chr.getJob() <= 1211) {
						MaxHP += Randomizer.rand(15, 21);
					} else if ((chr.getJob() >= 1300 && chr.getJob() <= 1311) || (chr.getJob() >= 1400 && chr.getJob() <= 1411)) {
						MaxHP += Randomizer.rand(30, 36);
					} else if (chr.getJob() == 3101 || (chr.getJob() >= 3120 && chr.getJob() <= 3122)) {
						MaxHP += Randomizer.rand(30, 36);
					} else {
						MaxHP += Randomizer.rand(50, 100);
					}
					// MaxHP = Math.min(500000, MaxHP);
					stat.setMaxHp((int) MaxHP);
					chr.setRemainingAp(chr.getRemainingAp() - 1);
					chr.updateSingleStat(PlayerStatList.AVAILABLEAP, chr.getRemainingAp());
					chr.updateSingleStat(PlayerStatList.MAXHP, stat.getMaxHp());
				}
			}
			return 1;
		}

		@Override
		public String getDescription() {
			return "Increases your HP by the specified amount based on your available remaining ap.";
		}
	}

	@Command(names = {"referrer"}, parameters = "", requiredType = AccountType.PLAYER)
	public static class Referrer extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			NPCScriptManager.getInstance().dispose(c.getClient());
			NPCScriptManager.getInstance().start(c.getClient(), 9010031, null);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Opens the referrer npc.";
		}
	}

	@Command(names = {"secondaryremove"}, parameters = "", requiredType = AccountType.PLAYER)
	public static class SecondaryRemove extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			IEquip equip = null;
			equip = (IEquip) c.getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -10);

			if (equip == null) {
				c.Message(1, "You don't have a secondary equipped");
				c.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(c));
				return 0;
			}

			c.getInventory(MapleInventoryType.EQUIPPED).removeSlot((byte) -10);
			c.equipChanged();

			InventoryManipulator.addFromDrop(c.getClient(), equip, false);

			c.getStat().recalcLocalStats(c);
			c.send(MainPacketCreator.getPlayerInfo(c));

			MapleMap currentMap = c.getMap();
			currentMap.removePlayer(c);
			currentMap.addPlayer(c);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Removes your currently equipped secondary.";
		}
	}

	@Command(names = {"dispose"}, parameters = "", requiredType = AccountType.PLAYER)
	public static class Dispose extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.getClient().sendPacket(MainPacketCreator.SkillUseResult((byte) 1));
			c.getClient().sendPacket(MainPacketCreator.resetActions(c));
			c.dropMessage(5, "Lag has been removed.");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Disposes your character and allows you to perform actions.";
		}
	}

	@Command(names = {"save"}, parameters = "", requiredType = AccountType.PLAYER)
	public static class Save extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			c.dropMessage(6, "[SAVE] Start saving... You should never exit the game before the save is complete.");
			c.saveToDB(false, false);
			c.dropMessage(5, "[SAVE] Save completed.");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Saves your character data to the database.";
		}
	}

	@Command(names = {"maxskills"}, parameters = "",requiredType = AccountType.PLAYER)
	public static class MaxSkills extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			if (c.getLevel() < 10) {
				c.dropMessage(1, "Available from level 10 or higher.");
				return 0;
			}

			for (int i = 0; i < (c.getJob() % 10) + 1; i++) {
				c.maxskill(((i + 1) == ((c.getJob() % 10) + 1)) ? c.getJob() - (c.getJob() % 100) : c.getJob() - (i + 1));
			}

			c.updateSingleStat(PlayerStatList.CHARM, 32767);
			c.maxskill(c.getJob());

			if (GameConstants.isDemonAvenger(c.getJob())) {
				c.maxskill(3101);
			}

			c.dropMessage(5, "Completed current skill skill master");
			return 1;
		}

		@Override
		public String getDescription() {
			return "Maxes all available skills of your character.";
		}
	}

	@Command(names = {"lobby", "town", "plaza"}, parameters = "(portal_id)", requiredType = AccountType.PLAYER)
	public static class Lobby extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int jobid = c.getJob();
			if (jobid == 0 || jobid == 1000 || jobid == 2000 || jobid == 2001 || jobid == 2002 || jobid == 2003
					|| jobid == 2004 || jobid == 3000 || jobid == 3001 || jobid == 5000 || jobid == 6000
					|| jobid == 6001 || jobid == 6002
					|| (jobid == 10112 && c.getMapId() == ServerConstants.startMap)) {
				c.dropMessage(5, "[System] novice can not go to the square.");
				return 0;
			}

			if (c.getDojoStartTime() > 0) {
				c.dropMessage(5, "[System] Mureung Dojang is in progress. Please go out through NPC");
				return 0;
			}
			MapleMap target = c.getClient().getChannelServer().getMapFactory().getMap(100000000);
			MaplePortal targetPortal = null;
			if (splitted.length > 1) {
				try {
					targetPortal = target.getPortal(Integer.parseInt(splitted[1]));
				} catch (IndexOutOfBoundsException e) {
					c.dropMessage(5, "There is a value of the missing portal.");
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
			return "Warps you to the lobby and optionally spawns you near the specified portal id.";
		}
	}

	@Command(names = {"boss"}, parameters = "(portal_id)", requiredType = AccountType.PLAYER)
	public static class Boss extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int jobid = c.getJob();
			if (jobid == 0 || jobid == 1000 || jobid == 2000 || jobid == 2001 || jobid == 2002 || jobid == 2003
					|| jobid == 2004 || jobid == 3000 || jobid == 3001 || jobid == 5000 || jobid == 6000
					|| jobid == 6001 || jobid == 6002
					|| (jobid == 10112 && c.getMapId() == ServerConstants.startMap)) {
				c.dropMessage(5, "[System] beginners can not move to the boss room.");
				return 0;
			}
			if (c.getDojoStartTime() > 0) {
				c.dropMessage(5, "[System] Mureung Dojang is in progress. Please go out through NPC");
				return 0;
			}
			MapleMap target = c.getClient().getChannelServer().getMapFactory().getMap(970060000);
			MaplePortal targetPortal = null;
			if (splitted.length > 1) {
				try {
					targetPortal = target.getPortal(Integer.parseInt(splitted[1]));
				} catch (IndexOutOfBoundsException e) {
					c.dropMessage(5, "There is a value of the missing portal.");
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
			return "Warps you to the boss lobby and optionally spawns you near the specified portal id.";
		}
	}

	@Command(names = {"fishing"}, parameters = "(portal_id)", requiredType = AccountType.PLAYER)
	public static class Fishing extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int jobid = c.getJob();
			if (jobid == 0 || jobid == 1000 || jobid == 2000 || jobid == 2001 || jobid == 2002 || jobid == 2003
					|| jobid == 2004 || jobid == 3000 || jobid == 3001 || jobid == 5000 || jobid == 6000
					|| jobid == 6001 || jobid == 6002
					|| (jobid == 10112 && c.getMapId() == ServerConstants.startMap)) {
				c.dropMessage(5, "Beginners can not go to the fishing spot.");
				return 0;
			}
			int mapcode[] = { 925060100, 925060200, 925060300, 925060400, 925060500, 925060700, 925060800, 925060900,
					925061000, 925061100, 925061300, 925061400, 925061500, 925061600, 925061700, 925061900, 925062000,
					925062100, 925062200, 925062300, 925062500, 925062600, 925062700, 925062800, 925062900, 925063100,
					925063200, 925063300, 925063400, 925063500, 925063700, 925063800, 925063900, 925064000, 925064100,
					925064300, 925064400, 925064500, 925064600, 925064700 };

			for (int i = 0; i < mapcode.length; i++) {
				if (c.getMapId() == mapcode[i]) {
					c.dropMessage(5, "[System] Can't move in Mureungdowon.");
					return 0;
				}
				if (c.getMapId() == 100000055) {
					c.dropMessage(5, "[System] You can't move on the fishing map.");
					return 0;
				}
				if (c.getDojoStartTime() > 0) {
					c.dropMessage(5, "[System] Mureung Dojang is in progress. Please go out through NPC");
					return 0;
				}
			}
			MapleMap target = c.getClient().getChannelServer().getMapFactory().getMap(100000055);
			MaplePortal targetPortal = null;
			if (splitted.length > 1) {
				try {
					targetPortal = target.getPortal(Integer.parseInt(splitted[1]));
				} catch (IndexOutOfBoundsException e) {
					c.dropMessage(5, "There is a value of the missing portal.");
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
			return "Warps you to the fishing map and optionally spawns you near the specified portal id.";
		}
	}

	@Command(names = {"gambling"}, parameters = "(portal_id)", requiredType = AccountType.PLAYER)
	public static class Gambling extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int jobid = c.getJob();
			if (jobid == 0 || jobid == 1000 || jobid == 2000 || jobid == 2001 || jobid == 2002 || jobid == 2003
					|| jobid == 2004 || jobid == 3000 || jobid == 3001 || jobid == 5000 || jobid == 6000
					|| jobid == 6001 || jobid == 6002
					|| (jobid == 10112 && c.getMapId() == ServerConstants.startMap)) {
				c.dropMessage(5, "[System] beginners can not go to the gambling house.");
				return 0;
			}
			MapleMap target = c.getClient().getChannelServer().getMapFactory().getMap(323000101);
			MaplePortal targetPortal = null;
			if (splitted.length > 1) {
				try {
					targetPortal = target.getPortal(Integer.parseInt(splitted[1]));
				} catch (IndexOutOfBoundsException e) {
					c.dropMessage(5, "There is a value of the missing portal.");
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
			return "Warps you to the gambling map and optionally spawns you near the specified portal id.";
		}
	}

	@Command(names = {"composition"}, parameters = "(portal_id)", requiredType = AccountType.PLAYER)
	public static class Composition extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			int jobid = c.getJob();
			if (jobid == 0 || jobid == 1000 || jobid == 2000 || jobid == 2001 || jobid == 2002 || jobid == 2003
					|| jobid == 2004 || jobid == 3000 || jobid == 3001 || jobid == 5000 || jobid == 6000
					|| jobid == 6001 || jobid == 6002
					|| (jobid == 10112 && c.getMapId() == ServerConstants.startMap)) {
				c.dropMessage(5, "[System] beginners can not go to work.");
				return 0;
			}
			if (c.getDojoStartTime() > 0) {
				c.dropMessage(5, "[System] Mureung Dojang is in progress. Please go out through NPC");
				return 0;
			}
			MapleMap target = c.getClient().getChannelServer().getMapFactory().getMap(100000001);
			MaplePortal targetPortal = null;
			if (splitted.length > 1) {
				try {
					targetPortal = target.getPortal(Integer.parseInt(splitted[1]));
				} catch (IndexOutOfBoundsException e) {
					c.dropMessage(5, "There is a value of the missing portal.");
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
			return "Warps you to the composition map and optionally spawns you near the specified portal id.";
		}
	}

	@Command(names = {"rebirth", "rb"}, parameters = "", requiredType = AccountType.PLAYER)
	public static class Rebirth extends PlayerCommand {
		@Override
		public int execute(MapleCharacter c, String[] splitted) {
			NPCScriptManager.getInstance().dispose(c.getClient());
			NPCScriptManager.getInstance().start(c.getClient(), 2510022, null);
			return 1;
		}

		@Override
		public String getDescription() {
			return "Opens the rebirth npc.";
		}
	}
}
