function enter(pi) {
    if (pi.getPlayer().getLevel() < 30) {
	pi.getPlayer().message(5, "레벨 30이상만 입장 가능합니다.");
	return false;
    }
    pi.playPortalSE();
    pi.setSavedMapId(pi.getMapId());
    pi.warp(910001000, 0);
    return true;
}
