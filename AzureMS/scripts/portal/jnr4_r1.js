/**
 * @author: Eric
 * @portal: Juliet PQ Jump Quest portals
 * @func: Romeo and Juliet GMS-like PQ
*/

function enter(pi) {
	try {
		var em = pi.getEventManager("Juliet");
		if (em != null && em.getProperty("stage6_0").equals("0")) {
			pi.environmentChange(true, "r1");
			pi.warp(926110301, 0);
			em.setProperty("stage6_0", "1");
		} else {
			pi.playerMessage(5, "Someone has already gone in this portal.");
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}