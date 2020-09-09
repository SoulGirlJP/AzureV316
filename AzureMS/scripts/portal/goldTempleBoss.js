function enter(pi) {
    if (pi.getQuestStatus(3863) == 1) {
	if (pi.getPlayerCount(925120100) == 0) {
	    pi.warp(925120100,0);
	    pi.spawnMob(9100025,823,513);
	} else {
	    pi.playerMassage("이미 라바나와의 싸움이 시작되었습니다.");
	}
    } else {
        var channel = pi.getChannelNumber();
    	if (channel == 6 || channel == 7 || channel == 8) {
	    if (pi.getParty() != null) {
	    	if (pi.getParty().getMembers().size() >= 2) {
		    if (pi.getPlayerCount(252030100) == 0) {
			pi.resetMap(252030100);
			pi.bossTimer(252030000,252030100,60 * 30);
		    	pi.warpParty(252030100);
			pi.spawnMob(8800200,823,513);
		    } else {
		    	pi.playerMessage("이미 라바나와의 싸움이 시작되었습니다.");
		    }
	    	} else {
		    pi.playerMessage("2인 이상 6인 이하 파티를 구성해서 입장해 주세요.");
	    	}
	    } else {
	    	pi.showinfoMessage("파티장을 통해 이동해 주세요.");
	    }
    	} else {
	    pi.showinfoMessage("이 채널에서는 도전할 수 없습니다. 6, 7, 8채널 중 한 곳으로 이동하세요.");
	}
    }
}