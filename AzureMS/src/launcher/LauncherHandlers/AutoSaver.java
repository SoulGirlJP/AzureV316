package launcher.LauncherHandlers;

import client.Character.MapleCharacter;
import constants.EventConstants.MedalRanking;
import launcher.ServerPortInitialize.ChannelServer;

public class AutoSaver implements Runnable {

    @Override
    public void run() {
        int characterCount = 0;
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters().values()) {
                chr.saveToDB(false, false);
                characterCount++;
            }
            cs.saveAllMerchant();
        }
        System.out.println("[Notice] Automatically saved: " + characterCount + " characters and all merchants.");
        MedalRanking.getInstance().save();
        
    }
}
