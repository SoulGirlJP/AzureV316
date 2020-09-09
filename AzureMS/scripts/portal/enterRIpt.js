function enter(pi) {
    if (pi.getMapId() == 140020000) {
	pi.warp(140000000,0);
    } else {
	pi.warp(pi.getMapId() - 100);
    }
}