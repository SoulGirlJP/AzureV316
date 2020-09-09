/**
 * @author: Eric
 * @portal: Juliet PQ Jump Quest portals
 * @func: Yulete's Office spawns for Romeo and Juliet PQ
*/

function enter(pi) {
	try {
		var em = pi.getEventManager("Juliet");
		if (em != null && em.getProperty("stage5").equals("0")) {
			/* Original Exp: 
			 *	9300142 - 317.8
			 *	9300143 - 317.8
			 *	9300144 - 355.2
			 *	9300145 - 336.6
			 *	9300146 - 392.6
			*/
			pi.spawnMonster(9300142, 15600 * pi.getPlayer().getAveragePartyLevel(), 1800 * pi.getPlayer().getAveragePartyLevel(), 10, 1589 * pi.getPlayer().getAveragePartyLevel());
			pi.spawnMonster(9300143, 15600 * pi.getPlayer().getAveragePartyLevel(), 1800 * pi.getPlayer().getAveragePartyLevel(), 10, 1589 * pi.getPlayer().getAveragePartyLevel());
			pi.spawnMonster(9300144, 18000 * pi.getPlayer().getAveragePartyLevel(), 1800 * pi.getPlayer().getAveragePartyLevel(), 10, 1776 * pi.getPlayer().getAveragePartyLevel());
			pi.spawnMonster(9300145, 16800 * pi.getPlayer().getAveragePartyLevel(), 1800 * pi.getPlayer().getAveragePartyLevel(), 10, 1683 * pi.getPlayer().getAveragePartyLevel());
			pi.spawnMonster(9300146, 20400 * pi.getPlayer().getAveragePartyLevel(), 2000 * pi.getPlayer().getAveragePartyLevel(), 10, 1963 * pi.getPlayer().getAveragePartyLevel());
			em.setProperty("stage5", "1");
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}