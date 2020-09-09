/**
 * @author: Eric
 * @script: ludi_s1Clear
 * @func: Handles the portal for stage 8 in Ludi PQ
*/

function enter(pi) {
	try {
		var eim = pi.getPlayer().getEventInstance();
		if (eim.getProperty("8stageclear") == null) {
			pi.playerMessage(5, "The portal is blocked.");
		} else {
			pi.warpParty(pi.getMapId() + 100, 0);
			if (pi.getPlayer().getMap().getAllMonstersThreadsafe().size() == 0) {
				pi.getPlayer().spawnCustomMonster(9300012, 55000 * pi.getPlayer().getAveragePartyLevel(), 55000 * pi.getPlayer().getAveragePartyLevel(), 1, 1096, 184, pi.getPlayer().getAveragePartyLevel());
			}
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}