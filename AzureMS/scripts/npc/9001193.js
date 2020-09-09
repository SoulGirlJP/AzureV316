/*
	
*/
importPackage(Packages.tools.RandomStream);
importPackage(java.lang);
importPackage(java.sql);
importPackage(java.util);
importPackage(java.io);
importPackage(Packages.constants)
importPackage(Packages.constants.Data)
importPackage(Packages.client.Character);
importPackage(Packages.connections.Packets);
importPackage(Packages.connections.Packets.PacketUtility);
importPackage(Packages.client.ItemInventory);
importPackage(Packages.server.Items);
importPackage(Packages.server.Systems);
importPackage(Packages.handlers.GlobalHandler);
importPackage(Packages.handlers);
importPackage(Packages.handlers.GlobalHandler.ItemInventoryHandler);
importPackage(Packages.launcher.LauncherHandlers);
importPackage(Packages.launcher.Utility);
importPackage(Packages.launcher.ServerPortInitialize);
importPackage(Packages.launcher.Utility.MapleHolders);
importPackage(Packages.launcher.Utility.netty);
importPackage(Packages.connections.Database);
importPackage(Packages.client);
importPackage(Packages.client.Stats);
importPackage(Packages.scripting.NPC);
importPackage(Packages.server.LifeEntity.NpcEntity);
importPackage(Packages.server.Shops);
importPackage(Packages.connections.Packets.MainPacketCreator);
var status = -1;

function start() {
	status = -1;
	action (1, 0, 0);
}

function action(mode, type, selection) {
	list = [1703992,1903993,1703994,1703995,1100005,1100006,1100007]
	if (mode == 1) {
		status++;
	} else {
		status--;
		cm.dispose();
	}
	if (status == 0) {
		if(mode == 0)
			cm.dispose();
		else
			cm.sendGetText("\r\n#ePlease enter the name of the cash item to be acquired                              #dJustice [400]#n\r\n#Cgray#- We can't refund items that were purchased inadvertently..#k\r\n\r\n#b [All Stats 650 ATT 200] Obtain a Cash item granted the attack.#k\r\n\r\n#r�� If you don't know the exact name, just enter the word included.");
	} else if (status == 1) {
		var itemid = cm.getText();
		cm.SearchItem(itemid);
	} else if (status == 2) {
	    for(i=0; i<list.length; i++) {
	         if(selection == list[i]) {
	             cm.sendOk("This item is not available for purchase.");
	             cm.dispose();
	             return;
	         }
	    } 
       if (cm.haveItem(4310034, 400)) {
            cm.gainItem(4310034, -400);
            cm.sendOk("#i"+selection+"# #fs14##e#b#t"+selection+"##n#k#fs12#Obtained.");
            var allstat = 650;
            var attk = 200;
            	cm.gainItemAllStat(selection, 1, allstat, attk, 0);
                cm.dispose();
    } else {
        	cm.sendOk("#fnSharing Ghotic Extrabold##fs13#You need 400 Justice Coin to purchase.");
            cm.dispose();
        }
	}
}