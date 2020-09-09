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
            cm.sendYesNo("#e<하드 매그너스를 처치해주세요!>#n\r\n\r\n#e#d #fUI/UIWindow2.img/MobGage/Mob/8880000# 하드 매그너스#n#k#n#k   획득아이템 #i4310058# (3~3개)\r\n\r\n#e#r이동하시겠습니까?\r\n[파티를구성하지 않으면 입장이 불가능합니다.]\r\n#e#b#n#k#n#k\r\n");
	} else if (status == 1) {
            if (cm.getPlayer().getParty() != null) {
                if (cm.getPlayerCount(401060200) > 0 || cm.getPlayerCount(350060180) > 1 || cm.getPlayerCount(350060200) > 2) {
                    cm.sendOk("지금 하드 매그너스에 도전 중인 플레이어가 있습니다.");
                    cm.dispose();
                } else {
		    cm.resetMap(401060200);
                    cm.warpParty(401060200);
                    cm.spawnMob(8880002, cm.getPlayer().getPosition().x + 2000, cm.getPlayer().getPosition().y);
                    cm.dispose();
                }
            } else {
		cm.sendOk("파티를 만들어 주시길 바랍니다.");
		cm.dispose();
	    }
        }
    }
}