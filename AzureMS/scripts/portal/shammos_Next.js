/**
 * @author: Eric
 * @script: Shammos portal to continue
*/

function enter(pi) {
	try {
		if (pi.getPlayer().getParty() != null && pi.getMap().getMonsterById(9300275) == null && pi.isLeader()) {
			pi.warpParty(((pi.getPlayer().getMapId() / 100) + 1) * 100 - (pi.getPlayer().getMapId() % 100)); // this is actually JUST (pi.getPlayer().getMapId() + 100), but we'll stick to this.
			pi.playPortalSE();
		} else {
			pi.playerMessage(5, "You cannot go to the next map because Shammos has not arrived. Shammos must arrive before you can be transported.");
		}
	} catch (e) {
		pi.playerMessage(5, "Error: " + e);
	}
}