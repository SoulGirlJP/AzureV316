/**
 * @author: Eric
 * @script: Shammos portal to continue
*/

function enter(pi) {
    if (pi.getPlayer().getParty() != null && pi.getMap().getAllMonstersThreadsafe().size() == 0 && pi.isLeader()) {
		pi.warpParty(921120100);
        pi.playPortalSE();
    } else {
        pi.playerMessage(5, "Energy from the ice is preventing you from entering.");
    }
}