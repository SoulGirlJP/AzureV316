function enter(pi) {
    if (pi.getPlayer().getInventory(pi.getInvType(-1)).findById(1003036) == null) {
	pi.playerMessage(5, "The stench is too powerful.");
	pi.warp(105050400);
	return true;
	}
	return false;
}