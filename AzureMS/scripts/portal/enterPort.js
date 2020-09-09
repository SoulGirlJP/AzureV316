function enter(pi) {
    if (pi.getQuestStatus(21301) == 1) {
	if (pi.getPlayerCount(914022000) == 0) {
	    pi.resetMap(914022000);
	    pi.warp(914022000,0);
	    pi.spawnMob(9001013,2266,3);
	}
    } else {
    	pi.warp(140020300,0);
    }
}