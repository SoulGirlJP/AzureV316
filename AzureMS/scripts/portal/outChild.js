function enter(pi) {
    if (pi.getQuestStatus(21001) != 1) {
	pi.warp(914000220,0);
    } else {
	pi.warp(914000400,0);
    }
}