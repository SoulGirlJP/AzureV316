function enter(pi) {
	try {
    if (pi.getMap().getAllMonstersThreadsafe().size() == 0) {
	pi.warp(926110100,0);
    } else {
	pi.playerMessage(5, "The portal has not opened yet.");
    }
 } catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}