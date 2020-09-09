function enter(pi) {
    pi.playPortalSE();
    pi.warp(200090000, 5);
    if (pi.getEventManager("Boats").getProperty("haveBalrog").equals("true")) {
	pi.changeMusic("Bgm04/ArabPirate");
    }
}
