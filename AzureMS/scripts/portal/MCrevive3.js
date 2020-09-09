/**
 * @author: Eric
 * @script: MCrevive<(mapid%1000)/100>
 * @func: warps players back to arena and fixes their score back to normal
*/
// Development v117.2 - Monster Carnival Reviving Field 3
function enter(pi) {
	var portal = 0;
	switch (pi.getPlayer().getCarnivalParty().getTeam()) {
		case 0:
			portal = 4;
			break;
		case 1:
			portal = 3;
			break;
	}
	pi.warp(980000301, portal);
	pi.getPlayer().CPUpdate(false, pi.getPlayer().getAvailableCP(), pi.getPlayer().getTotalCP(), 0); // this will update for the player who killed the mob
	pi.getPlayer().CPUpdate(true, pi.getPlayer().getAvailableCP(), pi.getPlayer().getTotalCP(), 0); // this will update for the player's carnival team (their party)
	return true;
}