function enter(pi) {
    if (pi.getQuestStatus(23033) == 1 || pi.getQuestStatus(23034) == 1 || pi.getQuestStatus(23035) == 1) {
        if (pi.getPlayerCount(931000200) == 0) {
	    pi.setTimer(310050100,931000200,60 * 15);
	    pi.warp(931000200,0);
	    pi.getPlayer().spawnAll();
	} else {
	    pi.playerMessage("이미 다른 누군가가 퀘스트를 진행하고 있습니다.");
    	}
    } else {
	pi.playerMessage("퀘스트를 진행 중 일때만 입장이 가능합니다.");
    }
}