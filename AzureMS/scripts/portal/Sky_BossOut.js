function enter(pi) {
	if (pi.getPlayer().getParty() != null && pi.getMap().getAllMonstersThreadsafe().size() == 0 && pi.isLeader()) {
		var chars = pi.getMap().getCharactersThreadsafe();
		for (var i = 0; i < chars.size(); i++) {
			var item = ((chars.get(i).getJob() % 1000) / 100 + 2022651) | 0;
			if (item == 2022651) {
				item = 2022652;
			} else if (item == 2022654) {
				item = 2022655;
			} else if (item == 2022655) {
				item = 2022654;
			}
			pi.gainItem(item, 1, false, 0, 0, "", chars.get(i).getClient());
		}
		pi.addTrait("will", 40);
		pi.addTrait("charisma", 10);
		pi.gainExp_PQ(200, 1.5);
		pi.warpParty(240080050);
		pi.playPortalSE();
	} else {
		pi.playerMessage(5,"This portal is not available.");
	}
}