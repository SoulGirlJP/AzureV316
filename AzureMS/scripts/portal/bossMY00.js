function enter(pi) {
    if (!pi.haveItem(4032246)) {
	pi.playerMessage(5, "You do not have the Spirit of Fantasy Theme park.");
    } else {
	pi.openNpc(9270047);
    }
}