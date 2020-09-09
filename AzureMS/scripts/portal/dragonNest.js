function enter(pi) {
    if (pi.haveItem(4001094)) {
	if (pi.getQuestStatus(3706) > 0) {
	    if (pi.getPlayerCount(240040611) == 0) {
		pi.removeNpc(240040611, 2081008);
		pi.resetMap(240040611);
		pi.playPortalSE();
		pi.warp(240040611, "sp");
	    } else {
		pi.playerMessage(5, "Someone else is already inside in an attempt to complete the quest. Please try again later.");
	    }
	} else {
	    pi.playerMessage(5, "You do not have the quest started. Please try again later.");
	}
    } else {
	pi.playerMessage(5, "In order to enter the premise, you'll need to have the Nine Spirit's Egg in possession.");
    }
}