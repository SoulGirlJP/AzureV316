function enter(pi) {
	try {
		pi.openNpc(2144018);
	} catch (e) {
		pi.playerMessage(5, "Error: " + e);
	}
}