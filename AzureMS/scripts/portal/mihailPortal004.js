function enter(pi) {
    for (i = 0; i < 20; i++) {
	if (pi.getPlayerCount(913070050 + i) == 0) {
	    pi.resetMap(913070050 + i);
	    pi.warp(913070050 + i,0);
	    return;	
	}
    }
}