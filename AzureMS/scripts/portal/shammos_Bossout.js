/**
 * @author: Eric
 * @script: shammos_Bossout
 * @func: Exit Rex's boss map
*/

function enter(pi) {
    if (pi.getPlayer().getParty() != null && pi.getMap().getAllMonstersThreadsafe().size() == 0) {
		pi.warp(921120001);
        pi.playPortalSE();
    } else {
        pi.playerMessage(5, "Energy from the ice is preventing you from entering.");
    }
}