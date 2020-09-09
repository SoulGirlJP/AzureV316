/**
 * @author: Eric
 * @portal: JQ Exit to the entrance of boss
 * @func: Romeo and Juliet GMS-like PQ
*/

function enter(pi) {
	try {
		pi.warpParty(926110400, 0); 
		pi.showEffect(true, "quest/party/clear"); // map
		pi.showEffect(false, "quest/party/clear"); // client
		pi.playSound(true, "Party1/Clear"); // map
		pi.playSound(false, "Party1/Clear"); // client
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}