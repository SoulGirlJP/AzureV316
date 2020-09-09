var bp = -1;

function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
		if (cm.getPlayer().getKeyValue("pocket") == null) {
			cm.sendYesNo("#fs11#Do you want to open your pocket slots using Blue orbs, Purple Orbs, Horntail Orb, Hilla Orb, Arkarium Orb and Cygnus Orb??\r\n\ #i4310184# 10 000\n\ #i4310185# 10 000\r\n #i4001897# 30\n  #i4001906# 15\r\n #i4001907# 10\n  #i4001908# 5")
                                     
		} else {
			cm.sendOk("#fs11#You already opened your pocket slot.");
			cm.dispose();
		}
	} else if (status == 1) {
		if (!cm.getPlayer().haveItem(4310184, 10000) // Blue Orb
                        && !cm.getPlayer().haveItem(4310185, 10000) // Purple Orb
                        && !cm.getPlayer().haveItem(4001897, 30) // Horntail Orb
                        && !cm.getPlayer().haveItem(4001906, 15) // Hilla Orb
                        && !cm.getPlayer().haveItem(4001907, 10) // Arkarium Orb
                        && !cm.getPlayer().haveItem(4001908, 5)) { // Cygnus Orb
			cm.sendOk("#fs11#You don't have enough Blue orbs, Purple Orbs, Horntail Orb, Hilla Orb, Arkarium Orb and/or Cygnus Orb.");
			cm.dispose();
			return;
                    }
		cm.getPlayer().setKeyValue("pocket", "ok");
		cm.forceCompleteQuest(6500);
                cm.gainItem(4310184, -10000);
                cm.gainItem(4310185, -10000);
                cm.gainItem(4001897, -30);
                cm.gainItem(4001906, -15);
                cm.gainItem(4001907, -10);
                cm.gainItem(4001908, -5);
		cm.sendOk("#fs11#Opened pocket slots using only Donation Points.");
		cm.dispose();
            
	}

}