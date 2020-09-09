function enter(pi) {
	var eim = pi.getDisconnected("Dragonica");
	if (eim != null && pi.getPlayer().getParty() != null) { //only skip if not null
		eim.registerPlayer(pi.getPlayer());
		return true;
	}
    if (pi.getPlayer().getParty() == null || !pi.isLeader()) {
	pi.playerMessage(5, "The leader of the party must be here.");
	return false;
    }
	var party = pi.getPlayer().getParty().getMembers();
	var next = true;
	var size = 0;
	var it = party.iterator();
	while (it.hasNext()) {
		var cPlayer = it.next();
		var ccPlayer = pi.getPlayer().getMap().getCharacterById(cPlayer.getId());
		if (ccPlayer == null || ccPlayer.getLevel() < 120 || (ccPlayer.getSkillLevel(ccPlayer.getStat().getSkillByJob(1026, ccPlayer.getJob())) <= 0)) {
			next = false;
			break;
		} else if (ccPlayer.isGM()) {
			size += 4;
		} else {
			size++;
		}
	}
	if (next && size >= 2) {
		var em = pi.getEventManager("Dragonica");
		if (em == null) {
			pi.playerMessage(5, "This event is currently not available.");
		} else {
			var prop = em.getProperty("state");
			if (prop == null || prop.equals("0")) {
				em.startInstance(pi.getParty(), pi.getMap(), 200);
			} else {
				pi.playerMessage(5, "Someone is already attempting this boss.");
			}
		}
	} else {
		pi.playerMessage(5, "Make sure all 2+ party members are in this map and level 120+ and have Soaring skill.");
		return false;
	}
        return true;
}