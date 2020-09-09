function enter(pi) {
    for(var i = 0; i < 6; i++) {
	if (pi.getPlayerCount(100020100 + i) == 0) {
	    pi.setTimer(100020000,100020100 + i,60 * 60);
	    if (pi.getParty()) {
		pi.warpParty(100020100 + i);
	    } else {
		pi.warp(100020100 + i,0);
	    }
	    return;
	} else {
	    pi.playerMessage("현재 모든 미니 던전이 사용되고 있습니다.");
	}
    }
}