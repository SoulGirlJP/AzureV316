function enter(pi) {
	try {
    //if (pi.getMap().getReactorByName("rnj31_out").getState() > 0) {
	pi.warp(926100200,0);
    //} else {
	//pi.playerMessage(5, "The portal is not open.");
    //}
 } catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}