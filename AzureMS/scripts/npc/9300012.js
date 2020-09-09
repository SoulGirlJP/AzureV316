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
            cm.sendYesNo("#e<카오스 핑크빈 을 처치해주세요!>#n#k#n#k\r\n\r\n#e#r이동하시겠습니까?\r\n[파티를구성하지 않으면 입장이 불가능합니다.]\r\n#e#b#n#k#n#k\r\n");
	} else if (status == 1) {
            if (cm.getPlayer().getParty() != null) {
                if (cm.getPlayerCount(270051100) > 0 || cm.getPlayerCount(350060180) > 1 || cm.getPlayerCount(350060200) > 2) {
                    cm.sendOk("현재 핑크빈 을 잡고있는 유저가 있습니다.");
                    cm.dispose();
                } else {
		    cm.resetMap(270051100);
                    cm.warpParty(270051100);
                    cm.spawnMob(9300798, cm.getPlayer().getPosition().x + 300, cm.getPlayer().getPosition().y);
                    cm.dispose();
                }
            } else {
		cm.sendOk("파티를 만들어 주시길 바랍니다.");
		cm.dispose();
	    }
        }
    }
}