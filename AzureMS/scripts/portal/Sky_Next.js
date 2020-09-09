function enter(pi) {
    if (pi.getMonsterCount(pi.getMapId()) == 0) {
	pi.warpParty(pi.getMapId() + 100);
    } else {
	pi.showinfoMessage("몬스터가 " + pi.getMonsterCount(pi.getMapId()) + "마리 남았습니다.");
    }
}