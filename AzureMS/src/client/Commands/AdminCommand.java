package client.Commands;

import constants.ServerConstants;

public abstract class AdminCommand implements ICommand {
	public AdminCommand() {}
	
	public static char getPrefix() {
		return ServerConstants.ADMIN_COMMAND_PREFIX;
	}
}
