function enter(pi) {
	try {
    //if (pi.getMap().getReactorByName("jnr32_out").getState() > 0) {
	pi.warp(926110200,0);
    //} else {
	//pi.playerMessage(5, "The portal is not open.");
    //}
	} catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}