function enter(pi) {
    var mapid = 0;
    var portal = 0;
    switch (pi.getPlayer().getMapId()) {
        case 931050410:
            mapid = 102040600;
            portal = 1;
            break;
    }
    if (mapid != 0) {
        pi.warp(mapid, portal);
    }
    return true;
}