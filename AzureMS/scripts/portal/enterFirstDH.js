function enter(pi) {
    if (pi.getQuestStatus(20701) == 1) {
	pi.warp(913000000,0);
    } else if (pi.getQuestStatus(20702) == 1) {
	pi.warp(913001000,0);
    } else if (pi.getQuestStatus(20703) == 1) {
	pi.warp(913010000,0);
    }
}