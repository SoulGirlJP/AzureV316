function enter(pi) {
    if (pi.haveItem(4032179,1)) {
	if (pi.getPlayerCount(913002100) == 0) {
	    pi.resetMap(913002100);
	    pi.warp(913002100,0);
	    pi.spawnNPCTemp(1104104,3133,88);
	}
    } else {
	pi.warp(130010020,0);
    }
}