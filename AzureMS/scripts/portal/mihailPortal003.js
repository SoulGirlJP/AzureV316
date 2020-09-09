function enter(pi) {
    if (pi.getQuestStatus(20033) == 1) {
	for (i = 0; i < 20; i++) {
	    if (pi.getPlayerCount(913070020 + i) == 0) {
	        pi.resetMap(913070020 + i);
	        pi.setTimer(913070003,913070020 + i,60 * 5);
                pi.warp(913070020 + i,0);
                pi.spawnMob(9001051,166,65);
	        return;
	    }
	}
    }
}