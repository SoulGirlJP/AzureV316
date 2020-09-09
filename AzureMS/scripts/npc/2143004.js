var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        if (cm.getPlayer().getMapId() == 271040000) {
            cm.sendSimple("#e<시그너스 원정>#n\r\n" + "타락한 시그너스에게 맞설 준비는 되셨습니까?\r\n\r\n" + "#L0##b 시그너스 입장을 신청한다.");
        } else {
            cm.sendYesNo("시그너스 원정을 그만하고 나가시겠습니까?");
        }
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 271040000) {
            if (cm.getPlayer().getParty() == null) {
                cm.sendOk("파티를 맺고 입장해 주세요.");
                cm.dispose();
            } else if (!cm.isLeader()) {
                cm.sendOk("파티장만 입장신청이 가능합니다");
                cm.dispose();
            } else if (cm.getPlayerCount(271040100) > 0) {
		cm.sendOk("이미 다른 파티가 시그너스를 처치중 입니다.");
		cm.dispose();
	    } else {
		cm.resetMap(271040100);
                cm.warpParty(271040100);
                cm.spawnMob(8850011, -280, 117);
                cm.getPlayer().getMap().startMapEffect("이곳을 찾아 온 사람을 보는 것은 정말 오랜만이에요. 하지만 무사히 돌아간 분도 없었답니다.", 5120043);
                cm.dispose();
            }
        } else {
            cm.warp(271040000, 1);
            cm.dispose();
        }
    }
}