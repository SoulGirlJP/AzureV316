function enter(pi) {
	try {
    if (java.lang.Math.random() < 0.1) {
	pi.warp(930000300, 0);
    } else {
	pi.warp(930000300, 7);
    }
 } catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}