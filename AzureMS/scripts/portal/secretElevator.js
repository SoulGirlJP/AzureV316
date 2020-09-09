function enter(pi) {
    if (pi.getPlayer().getLevel() < 70) {
	pi.getPlayer().dropMessage(5, "무언가 위험해 보이는 문이다.");
	return false;
    } else if (pi.getPlayerCount(310030210) == 0) {
        pi.TimeMoveMap(310030210, 310060120, 18);
        return true;
    } else {
	pi.getPlayer().dropMessage(5, "이미 엘리베이터에 누군가가 있는 것 같다.");
	return false;
    }
}