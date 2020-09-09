/**
 * @author: Eric
 * @portal: Boss Entrance
 * @func: Romeo and Juliet GMS-like PQ (Warps you to the boss)
*/

function enter(pi) {
    if ((pi.getMap().getCharactersSize() == 4 || pi.getPlayer().isGM()) && pi.getMap(926110401).getCharactersSize() == 0) {
		pi.warpParty(926110401,0);
    } else {
		pi.playerMessage(5, "Not everyone is here.");
    }
}