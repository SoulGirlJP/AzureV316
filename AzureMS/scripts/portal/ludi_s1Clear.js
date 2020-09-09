/**
 * @author: Eric
 * @script: ludi_s1Clear
 * @func: Handles the portal for stage 1 in Ludi PQ
*/

function enter(pi) {
	try {
		var eim = pi.getPlayer().getEventInstance();
		if (eim.getProperty("stage1status") == null) {
			pi.playerMessage(5, "The portal is blocked.");
		} else {
			pi.warpParty(pi.getMapId() + 300, 0);
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}