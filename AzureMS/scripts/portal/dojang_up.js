function enter(pi) {
    if (pi.getMap().getAllMonstersThreadsafe().size() != 0) {
	pi.playerMessage("아직 몬스터가 남아있습니다.");
    } else {
	if (pi.getPlayer().getMapId() == 925076300) {
		pi.warp(925020003, 1);
	} else {
		pi.dojowarp(pi.getPlayer().getMapId() + 100);
	}
    }
}