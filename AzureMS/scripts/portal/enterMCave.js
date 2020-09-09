function enter(pi) {
    if (pi.getQuestStatus(21201) == 1) {
	for (i = 0; i < 10; i++) {
	    if (pi.getPlayerCount(914021000 + i) == 0) {
		pi.resetMap(914021000 + i);
		pi.setTimer(140030000,914021000 + i,914021010 + i,60 * 15);
                pi.warp(914021000 + i,0);
		return;
	    }
	}
    } else if (pi.getQuestStatus(21302) == 1) {
	if (pi.getPlayerCount(914022100) == 0) {
	    pi.getPlayer().parkTimer(140030000,914022100,914022200,60 * 20);
            pi.warp(914022100,0);
	}
    }
}