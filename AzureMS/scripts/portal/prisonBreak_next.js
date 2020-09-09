function enter(pi) {
    if (pi.getMonsterCount(pi.getMapId()) == 0 && pi.getMapId() != 921160350) {
        if (pi.getMapId() == 921160350) {
	    pi.warp(pi.getMapId() + 50);
    	} else {
	    pi.warp(pi.getMapId() + 100);
	}
    } else {
	//
    }
}