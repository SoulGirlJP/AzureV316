/**
 * @author: Eric
 * @portal: Juliet PQ Jump Quest portals
 * @func: Romeo and Juliet GMS-like PQ
*/

function enter(pi) {
	try {
		var em = pi.getEventManager("Juliet");
		if (em != null && em.getProperty("stage6_2").equals("0")) {
			pi.environmentChange(true, "r3");
			pi.warp(926110303, 0);
			em.setProperty("stage6_2", "1");
		} else {
			pi.playerMessage(5, "Someone has already gone in this portal.");
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}