function enter(pi) {
    pi.playPortalSE();
    pi.warp(200090000, 4);
    if (pi.getClient().getChannelServer().getEventSM().getEventManager("Boats").getProperty("haveBalrog").equals("true")) {
	pi.changeMusic("Bgm04/ArabPirate");
    }
}
