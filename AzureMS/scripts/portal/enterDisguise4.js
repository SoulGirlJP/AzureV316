function enter(pi) {
    if (pi.haveItem(4032179,1)) {
	if (pi.getPlayerCount(913002300) == 0) {
	    pi.resetMap(913002300);
	    pi.warp(913002300,0);
	    pi.spawnNPCTemp(1104103,-2635,88);
	}
    } else {
	pi.warp(130010120,0);
    }
}