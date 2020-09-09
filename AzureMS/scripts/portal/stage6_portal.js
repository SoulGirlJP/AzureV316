/**
 * @author: Eric
 * @script: ludi_s1Clear
 * @func: Handles the portal for stage 6 in Ludi PQ
*/

function enter(pi) {
	try {
		// var eim = pi.getPlayer().getEventInstance();
		pi.warpParty(pi.getMapId() + 100, 0);
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}