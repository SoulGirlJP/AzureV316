function enter(pi) {
	if (pi.getMap().getAllMonstersThreadsafe().size() == 0) {
		pi.warp(502029000,0);
	} else {
		pi.playerMessage("The Ultimate Visitor blocks your way out.");
	}
}