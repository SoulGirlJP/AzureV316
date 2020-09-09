function enter(pi) {
	try {
    if (java.lang.Math.random() < 0.1) {
	pi.warp(930000300,"16st");
    } else {
	pi.warp(930000300, 6);
    }
 } catch (e) {
    pi.getPlayer().dropMessage(5, "Error: " + e);
 }
}