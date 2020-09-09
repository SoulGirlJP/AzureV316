function enter(pi) {

    if (pi.getQuestStatus(2601) == 2) {

	pi.playPortalSE();

	pi.warp(103050920, 1);

    } else {

	pi.playerMessage(5, "You must accept the quest before proceeding to the next map.");
    }

}