function enter(pi) {
    var eim = pi.getEventManager("LudiPQ").getInstance("LudiPQ");
    
    // only let people through if the eim is ready
    if (eim.getProperty("stage1status") == null) { // do nothing; send message to player
	pi.playerMessage(5, "The portal is blocked.");
    } else {
	pi.warp(pi.getMapId() + 100, "st00");
    }
}