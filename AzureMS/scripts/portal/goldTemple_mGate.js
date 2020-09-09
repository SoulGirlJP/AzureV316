function enter(pi) {
    if (pi.getQuestStatus(3870) == 1) {
        pi.warp(925120000,0);
        pi.spawnMob(9100024,-915,162);
    } else {
	pi.openNpc(9000075);
    }
}