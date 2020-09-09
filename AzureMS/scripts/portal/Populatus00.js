function enter(pi) {
    if (!pi.checkTimeValue("Papulatus_BattleStartTime", 3600 * 4)) {
        pi.getPlayer().message(5, "시계탑의 근원에는 4시간마다 한번씩만 입장할 수 있습니다.");
        return false;
    }
    if (!pi.getPlayer().getMap().isExpiredMapTimer()) {
	pi.playerMessage(5, "지금은 시계탑의 근원에 입장할 수 없습니다.");
	return false;
    }
    if (!pi.haveItem(4031179)) {
	pi.playerMessage(5, "차원 균열의 조각을 소지하고 있지 않아 파풀라투스를 만날 수 없습니다.");
	return false;
    }
    pi.setTimeValueCurrent("Papulatus_BattleStartTime");
    pi.playPortalSE();
    pi.warp(pi.getPlayer().getMapId() + 1, "sp");
    return true;
}