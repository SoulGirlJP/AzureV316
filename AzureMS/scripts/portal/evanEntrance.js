function enter(pi) {
	if (pi.getPlayer().getJob() != 2001) {
    pi.warp(100030400, "east00");
    return true;
	} else {
	pi.dropMessage("튜토리얼을 완료해야 나갈 수 있습니다.");
	return false;
	}
}  