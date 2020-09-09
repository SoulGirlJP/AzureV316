/**
 * @author: Eric
 * @portal: Yulete's Lab
 * @func: Romeo and Juliet GMS-like PQ
*/

function enter(pi) {
	try {
		var em = pi.getEventManager("Juliet");
		if (em != null && em.getProperty("stage4").equals("2")) {
			pi.warp(926110203, 0);
			pi.getPlayer().dropMessage(6, "A mysterious scientist hurriedly left the lab, but not before summoning some monsters.");
			pi.getPlayer().dropNPC(2112010, "Who is it that's barging into my lab without permission!! My lab report is not for any one of you!!!");
		} else {
			pi.playerMessage(5, "The portal has not opened yet.");
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}