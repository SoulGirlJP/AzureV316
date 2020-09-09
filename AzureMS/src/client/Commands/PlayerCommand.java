package client.Commands;

import constants.ServerConstants;

public abstract class PlayerCommand implements ICommand {
	public PlayerCommand() {}
	
	public static char getPrefix() {
		return ServerConstants.PLAYER_COMMAND_PREFIX;
	}
}
