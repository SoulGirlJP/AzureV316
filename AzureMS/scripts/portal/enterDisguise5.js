function enter(pi) {
    if (pi.haveItem(4032179,1)) {
	if (pi.getPlayerCount(913002400) == 0) {
	    pi.resetMap(913002400);
	    pi.warp(913002400,0);
	    pi.spawnNPCTemp(1104101,693,88);
	}
    } else {
	pi.warp(130020000,0);
    }
}