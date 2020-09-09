function enter(pi) {
	var to_spawn = 9500390;
	if (pi.getPlayer().getMapId() == 809061000) {
		to_spawn = 9409018; //mirror mode
	} else if (pi.getPlayer().getLevel() > 120) {
		to_spawn = 9500392;
	} else if (pi.getPlayer().getLevel() > 90) {
		to_spawn = 9500391;
	} else if (pi.getPlayer().getLevel() < 50) {
		pi.playerMessage(5, "You must be atleast level 50.");
		return false;
	}
	var rav = "EASY";
	if (to_spawn == 9500391) {
		rav = "MED";
	} else if (to_spawn == 9500392) {
		rav = "HARD";
	} else if (to_spawn == 9409018) {
		rav = "HELL";
	}
	var eim = pi.getDisconnected("Ravana_" + rav);
	if (eim != null && pi.getPlayer().getParty() != null) { //only skip if not null
		eim.registerPlayer(pi.getPlayer());
		return true;
	}
    if (pi.getPlayer().getParty() == null || !pi.isLeader()) {
	pi.playerMessage(5, "The leader of the party must be here.");
	return false;
}
	//9500390 = level 50-90, 9500391 = level 90-120, 9500392 = level 120+

	var party = pi.getPlayer().getParty().getMembers();
	var mapId = pi.getPlayer().getMapId();
	var next = true;
	var it = party.iterator();
	while (it.hasNext()) {
		var cPlayer = it.next();
		var ccPlayer = pi.getPlayer().getMap().getCharacterById(cPlayer.getId());
		if (ccPlayer == null || !ccPlayer.haveItem(4001433,30,true,true)) {
			next = false;
			break;
		} else {
			if (to_spawn == 9409018 && ccPlayer.getLevel() <= 140) {
				next = false;
				break;
			} else if (to_spawn == 9500392 && ccPlayer.getLevel() <= 120) {
				next = false;
				break;
			} else if (to_spawn == 9500391 && (ccPlayer.getLevel() > 120 || ccPlayer.getLevel() <= 90)) {
				next = false;
				break;
			} else if (to_spawn == 9500390 && (ccPlayer.getLevel() < 50 || ccPlayer.getLevel() > 90)) {
				next = false;
				break;
			}
		}	
	}
	if (next) {
		var em1 = pi.getEventManager("Ravana_EASY");
		var em2 = pi.getEventManager("Ravana_MED");
		var em3 = pi.getEventManager("Ravana_HARD");
		var em = pi.getEventManager("Ravana_" + rav);
		if (em == null || em1 == null || em2 == null || em3 == null) {
			pi.playerMessage(5, "This event is currently not available.");
		} else {
			var prop1 = em1.getProperty("state");
			var prop2 = em2.getProperty("state");
			var prop3 = em3.getProperty("state");
			var prop = em.getProperty("state");
			if (prop1 != null && !prop1.equals("0")) {
				pi.playerMessage(5, "Someone is already attempting this boss.");
			} else if (prop2 != null && !prop2.equals("0")) {
				pi.playerMessage(5, "Someone is already attempting this boss.");
			} else if (prop3 != null && !prop3.equals("0")) {
				pi.playerMessage(5, "Someone is already attempting this boss.");
			} else if (prop != null && !prop.equals("0")) {
				pi.playerMessage(5, "Someone is already attempting this boss.");
			} else {
				em.startInstance(pi.getParty(), pi.getMap());
			}
		}
	} else {
		pi.playerMessage(5, "All of your party members require 30 Sunbursts! Come back to me when you have met my requirements. They also have be in the same level range as you (level ranges: 50-90, 91-120, 121+, 141+)");
		return false;
	}
    return true;
}