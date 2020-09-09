/**
 * @author: Eric
 * @func: El Nath PQ : Stage Clear Portal
*/

function enter(pi) {
	try {
		if (pi.haveMonster(9300093)) { // they shouldn't be here in the first place if he died
			var em = pi.getEventManager("ProtectTylus");
			if (em == null) { // how are you here on a null em?
				pi.warp(211000001, 0);
			} else {
				if (em.getProperty("finished").equals("1")) {
					pi.warp(921100301, 0);
				} else {
					pi.playerMessage("Please protect Tylus from kidnappers!");
					return;
				}
			}
		} else {
			pi.warp(211000001, 0);
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}