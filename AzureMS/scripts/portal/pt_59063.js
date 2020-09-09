/* Return to Masteria
    Arboren Ferry
    Made by Daenerys
*/
function enter(pi) {
    if (pi.getQuestStatus(59063) == 1) {
	pi.playPortalSE();
	pi.warp(866000230);
    } else {
    pi.getPlayer().dropMessage(5, "The harbor is undergoing construction. Find another way...");
	pi.topMsg("The harbor is undergoing construction. Find another way...");
	}

}
