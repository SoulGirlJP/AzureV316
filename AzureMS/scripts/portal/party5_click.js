/**
 * @author: Eric
 * @script: party5_click
 * @func: Unknown | Used within Romeo and Juliet PQ, might be handled to load the coordinates of the NPC that activates portal
*/

function enter(pi) {
	try {
		switch(pi.getMapId()) {
			case 926110000:
				if (pi.getPlayer().isGM())
					pi.getPlayer().dropMessage(5, "[Debug]: Portal Entered on MapID: " + pi.getMapId());
				break;
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}