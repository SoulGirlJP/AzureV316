function enter(pi) {
	try {
	pi.warpParty(926100400, 0); //, pi.getPlayer().getEventInstance().getPlayers());
	} catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}