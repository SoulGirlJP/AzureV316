function enter(pi) {
	try {
    if (pi.getMap().getReactorByName("jnr3_out1").getState() > 0) {
	pi.warp(926110201,0);
    } else {
	pi.playerMessage(5, "The portal has not opened yet.");
    }
 } catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}