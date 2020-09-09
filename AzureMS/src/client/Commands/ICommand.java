package client.Commands;

import client.Character.MapleCharacter;
import constants.ServerConstants;

public interface ICommand {
	public int execute(MapleCharacter chr, String[] args);
	
	public abstract String getDescription();
	
	static char getPrefix() {
        return ServerConstants.PLAYER_COMMAND_PREFIX;
    }
}
