package client.Commands;

import java.util.LinkedList;
import java.util.List;

import client.Character.MapleCharacter;
import client.MapleClient;
import constants.ServerConstants;
import tools.Pair;
import tools.StringUtil;
import tools.Timer.WorldTimer;

public class CommandProcessor {

	private final List<Pair<String, String>> gmlog = new LinkedList<Pair<String, String>>();
	//private final Map<String, DefinitionCommandPair> commands = new LinkedHashMap<String, DefinitionCommandPair>();
	private static CommandProcessor instance = new CommandProcessor();
	private static Runnable persister;

	public static CommandProcessor getInstance() {
		return instance;
	}

	private CommandProcessor() {
		instance = this;
		WorldTimer.getInstance().register(persister, 1800000); // 30 min once
	}

	public static String joinAfterString(String splitted[], String str) {
		for (int i = 1; i < splitted.length; i++) {
			if (splitted[i].equalsIgnoreCase(str) && i + 1 < splitted.length) {
				return StringUtil.joinStringFrom(splitted, i + 1);
			}
		}
		return null;
	}

	public static int getOptionalIntArg(String splitted[], int position, int def) {
		if (splitted.length > position) {
			try {
				return Integer.parseInt(splitted[position]);
			} catch (NumberFormatException nfe) {
				return def;
			}
		}
		return def;
	}

	public static String getNamedArg(String splitted[], int startpos, String name) {
		for (int i = startpos; i < splitted.length; i++) {
			if (splitted[i].equalsIgnoreCase(name) && i + 1 < splitted.length) {
				return splitted[i + 1];
			}
		}
		return null;
	}

	public static Integer getNamedIntArg(String splitted[], int startpos, String name) {
		String arg = getNamedArg(splitted, startpos, name);
		if (arg != null) {
			try {
				return Integer.parseInt(arg);
			} catch (NumberFormatException nfe) {
				// swallow - we don't really care
			}
		}
		return null;
	}

	public static Long getNamedLongArg(String splitted[], int startpos, String name) {
		String arg = getNamedArg(splitted, startpos, name);
		if (arg != null) {
			try {
				return Long.parseLong(arg);
			} catch (NumberFormatException nfe) {
				// swallow - we don't really care
			}
		}
		return null;
	}

	public static int getNamedIntArg(String splitted[], int startpos, String name, int def) {
		Integer ret = getNamedIntArg(splitted, startpos, name);
		if (ret == null) {
			return def;
		}
		return ret.intValue();
	}

	public static Double getNamedDoubleArg(String splitted[], int startpos, String name) {
		String arg = getNamedArg(splitted, startpos, name);
		if (arg != null) {
			try {
				return Double.parseDouble(arg);
			} catch (NumberFormatException nfe) {
				// swallow - we don't really care
			}
		}
		return null;
	}
	
	public static void forcePersisting() {
		persister.run();
	}

	/*public void dropHelp(MapleCharacter chr, int page) {
		List<DefinitionCommandPair> allCommands = new ArrayList<DefinitionCommandPair>(commands.values());
		int startEntry = (page - 1) * 20;
		chr.dropMessage(6, "Command help: --------" + page + "---------");
		for (int i = startEntry; i < startEntry + 20 && i < allCommands.size(); i++) {
                   CommandDefinition commandDefinition = allCommands.get(i).getDefinition();
			if (chr.hasGmLevel((byte) commandDefinition.getRequiredLevel())) {
				chr.dropMessage(6, commandDefinition.getCommand() + " " + commandDefinition.getParameterDescription()
						+ ": " + commandDefinition.getHelp());
			}
		}
	}*/

	public static String GetCommandList() {
		Class<?>[] commands = PlayerCommands.class.getClasses();
		ICommand iCommand = null;
		String s = "Player Commands =>\n";
		
		for (Class<?> cmd : commands) {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		s += "\nAdminCommands => \n";
		commands = AdminCommands.class.getClasses();
		
		for (Class<?> cmd :commands) {
			Command command = (Command) cmd.getAnnotation(Command.class);
			try {
				iCommand = (AdminCommand) cmd.getConstructor().newInstance();
				
				s += "\t- ";
				for (int i = 0; i < command.names().length; i++) {
					String name = command.names()[i];
					s += "!";
					if (i == command.names().length - 1) {
						s += name;
					} else {
						s += name + ", ";
					}
				}
				s += " " + command.parameters() + " => " + cmd.getDeclaredMethod("getDescription").invoke(iCommand) + "\n";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return s;
	}
	
	public void processCommand(MapleClient c, String line) {
		String msg = line.split(" ")[0].replace(String.valueOf(line.charAt(0)), "");
		Class<?>[] commands = null;
		
		boolean isPlayerCommand = (line.startsWith(String.valueOf(ServerConstants.PLAYER_COMMAND_PREFIX)));
		boolean isAdminCommand = (line.startsWith(String.valueOf(ServerConstants.ADMIN_COMMAND_PREFIX)));
		
		if (isPlayerCommand) {
			commands = PlayerCommands.class.getClasses();
		} else if (isAdminCommand) {
			commands = AdminCommands.class.getClasses();
		}
		
		if (commands == null) {
			System.out.println("[CommandProcessor] No valid command class found!");
			return;
		}
		
		for (Class<?> cmd : commands) {
			Command command = (Command) cmd.getAnnotation(Command.class);
			if (command == null) {
				System.out.println("[CommandProcessor] Missing @Command annotation on: " + cmd.getName());
				continue;
			}
                        
			for (String name : command.names()) {
				if (!name.toLowerCase().equalsIgnoreCase(msg.toLowerCase())) {
					continue;
				}
				if (c.getPlayer().getGMLevel() < command.requiredType().ordinal()) {
					continue;
				}
				try {
                                    ICommand iCommand = null;
                                    switch (line.charAt(0)) {
                                        case ServerConstants.PLAYER_COMMAND_PREFIX:
                                                iCommand = (PlayerCommand) cmd.getConstructor().newInstance();
                                                break;
                                        case ServerConstants.ADMIN_COMMAND_PREFIX:
                                                iCommand = (AdminCommand) cmd.getConstructor().newInstance();
                                                break;
                                    }
                                    
                                    // System.out.println("User: " + c.getPlayer().getName() + " with gm: " + c.getPlayer().getGMLevel() + " used: " + msg + " which requires: " + command.requiredType().ordinal());
                                    
                                    cmd.getDeclaredMethod("execute", MapleCharacter.class, String[].class).invoke(iCommand, c.getPlayer(), line.split(" "));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
		c.getPlayer().dropMessage(6, "\'" + msg + "\'" + " does not exist or you do not have the permissions to use it.");
	}
}
