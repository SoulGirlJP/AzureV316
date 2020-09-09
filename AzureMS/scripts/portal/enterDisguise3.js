function enter(pi) {
    if (pi.haveItem(4032179,1)) {
	if (pi.getPlayerCount(913002200) == 0) {
	    pi.resetMap(913002200);
	    pi.warp(913002200,0);
	    pi.spawnNPCTemp(1104100,636,88);
	}
    } else {
	pi.warp(130010110,0);
    }
}