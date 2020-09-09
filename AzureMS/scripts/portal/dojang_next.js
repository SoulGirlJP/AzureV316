function enter(pi) {
    if (pi.haveMonster()) {
	pi.playerMessage("아직 몬스터가 남아있습니다.");
    } else {
	pi.dojowarp(pi.getPlayer().getMapId() + 100);
    }
}