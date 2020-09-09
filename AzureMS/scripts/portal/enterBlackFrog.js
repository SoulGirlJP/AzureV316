function enter(pi) {
    try {
	if (pi.isQuestActive(22596)) {
		pi.forceCompleteQuest(22596);
		pi.getPlayer().gainAp(5);
		pi.playerMessage(5, "Ibech has ran away! Gained 5 AP!");
	}
	pi.warp(922030000,0);
    } catch (e) {
	pi.playerMessage(5, "Error, please report on forums: " + e);
    }
}