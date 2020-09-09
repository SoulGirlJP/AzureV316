function enter(pi) {
    if (pi.getMapId() == 921160300) {
        var mapid = [921160300,921160310,921160320,921160330,921160340,921160350];
        var rands = Math.floor(Math.random() * 6);
        pi.setSavedMapId(pi.getMapId());
        pi.warp(mapid[rands],0);
    } else {
	pi.warp(pi.getSavedMapId(),0);
    }
}