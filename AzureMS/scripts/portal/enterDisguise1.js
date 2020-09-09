function enter(pi) {
    if (pi.haveItem(4032179,1)) {
	if (pi.getPlayerCount(913002000) == 0) {
	    pi.resetMap(913002000);
	    pi.warp(913002000,0);
	    pi.spawnNPCTemp(1104102,2663,88);
	}
    } else {
	pi.warp(130010010,0);
    }
}