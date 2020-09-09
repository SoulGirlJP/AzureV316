function enter(pi) {
    if (pi.getQuestStatus(1041) == 1) {
	pi.warp(1010100,0);
    } else if (pi.getQuestStatus(1042) == 1) {
	pi.warp(1010200,0);
    } else if (pi.getQuestStatus(1043) == 1) {
	pi.warp(1010300,0);
    } else if (pi.getQuestStatus(1044) == 1) {
	pi.warp(1010400,0);
    }
}