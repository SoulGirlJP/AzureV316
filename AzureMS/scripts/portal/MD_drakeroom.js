function enter(pi) {
    for(var i = 0; i < 11; i++) {
	if (pi.getPlayerCount(105090320 + i) == 0) {
	    pi.setTimer(105090311,105090320 + i,60 * 60);
	    if (pi.getParty()) {
		pi.warpParty(105090320 + i);
	    } else {
		pi.warp(105090320 + i,0);
	    }
	    return;
	} else {
	    pi.playerMessage("현재 모든 미니 던전이 사용되고 있습니다.");
	}
    }
}