// Portal script for Custom MV PQ
// Removed in ver. 1.9 and replaced with the official GMS portal in 2.0 :o
function enter(pi) {
	try {
	if (pi.isLeader() && pi.haveItem(4032248, 17)) {
	if (pi.getPlayer().getParty() != null) {
		pi.warpParty(674030200, 0);
		pi.mapMessage(5, "Hammer the rock and defeat MV!");
		pi.removeAll(4032248);
	} else {
		pi.changeMap(674030200, 0);
		pi.playerMessage(5, "Hammer the rock and defeat MV!");
		pi.removeAll(4032248);
	}
	} else {
		pi.playerMessage(5, "Make sure you're the party leader and have 17 Maps to MV's Lair.");
	}
	} catch (e) {
		pi.playerMessage("Error: " + e);
	}
}