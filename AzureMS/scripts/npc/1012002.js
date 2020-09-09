// Pianus

var status;
function start() {
    status = -1;
	action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0)
        cm.dispose();
    else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendYesNo("#e<Keep Pianus {Bonus}!>#n\r\n\r\n#e#d #fUI/UIWindow2.img/MobGage/Mob/9300515# Pianus#n#k#n#k   Acquisition Item #i4310034# (30~40 QTY)\r\n\r\n#e#rDo you want to move?\r\n[You cannot enter without a party.]\r\n#e#b#n#k#n#k\r\n");
	} else if (status == 1) {
            if (cm.getPlayer().getParty() != null) {
                if (cm.getPlayerCount(923000100) > 0 ) {
                    cm.sendOk("There is a player who is challenging Pianus now.");
                    cm.dispose();
                } else {
		    cm.resetMap(923000100);
                    cm.warpParty(923000100);
                    cm.spawnMob(9300515, cm.getPlayer().getPosition().x + 400, cm.getPlayer().getPosition().y);
                    cm.dispose();
                }
            } else {
		cm.sendOk("Please make a party.");
		cm.dispose();
	    }
        }
    }
}