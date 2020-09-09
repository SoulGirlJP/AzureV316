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
            cm.sendYesNo("#e<Defeat Dorothy's Illusion!>#n\r\n\r\n#e#d #fUI/UIWindow2.img/MobGage/Mob/9309208# Dorothy's Illusion#n#k#n#k   Acquisition Item #i4310034# (12~18 QTY)\r\n                                                #i4000985#(15~40개)\r\n\r\n#e#r이동하시겠습니까?\r\n[파티를구성하지 않으면 입장이 불가능합니다.]\r\n#e#b#n#k#n#k\r\n");
	} else if (status == 1) {
            if (cm.getPlayer().getParty() != null) {
                if (cm.getPlayerCount(992050000) > 0 || cm.getPlayerCount(350060180) > 1 || cm.getPlayerCount(350060200) > 2) {
                    cm.sendOk("There's a player now challenging Dorothy's illusion.");
                    cm.dispose();
                } else {
		    cm.resetMap(992050000);
                    cm.warpParty(992050000);
                    cm.spawnMob(9309208, cm.getPlayer().getPosition().x -200, cm.getPlayer().getPosition().y);
                    cm.dispose();
                }
            } else {
		cm.sendOk("Please make a party.");
		cm.dispose();
	    }
        }
    }
}