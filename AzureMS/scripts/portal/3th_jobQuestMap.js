function enter(pi) {
    if (pi.getQuestStatus(1431) == 1 || pi.getQuestStatus(1432) == 1  || pi.getQuestStatus(1433) == 1) {
	for (i = 0; i < 10; i++) {
	    if (pi.getPlayerCount(910540100 + i) == 0) {
		pi.setTimer(211040401,910540100 + i,20 * 60);
                pi.warp(910540100 + i,0);
		pi.getPlayer().spawnAll();
		return;
	    }
	}
    } else if (pi.getQuestStatus(1435) == 1 || pi.getQuestStatus(1436) == 1  || pi.getQuestStatus(1437) == 1) {
	for (i = 0; i < 10; i++) {
	    if (pi.getPlayerCount(910540200 + i) == 0) {
		pi.setTimer(211040401,910540200 + i,20 * 60);
                pi.warp(910540200 + i,0);
		pi.getPlayer().spawnAll();
		return;
	    }
	}
    } else if (pi.getQuestStatus(1439) == 1 || pi.getQuestStatus(1440) == 1) {
	for (i = 0; i < 10; i++) {
	    if (pi.getPlayerCount(910540300 + i) == 0) {
		pi.setTimer(211040401,910540300 + i,20 * 60);
                pi.warp(910540300 + i,0);
		pi.getPlayer().spawnAll();
		return;
	    }
	}
    } else if (pi.getQuestStatus(1442) == 1 || pi.getQuestStatus(1443) == 1 || pi.getQuestStatus(1447) == 1) {
	for (i = 0; i < 10; i++) {
	    if (pi.getPlayerCount(910540400 + i) == 0) {
		pi.setTimer(211040401,910540400 + i,20 * 60);
                pi.warp(910540400 + i,0);
		pi.getPlayer().spawnAll();
		return;
	    }
        }
    } else if (pi.getQuestStatus(1445) == 1 || pi.getQuestStatus(1446) == 1 || pi.getQuestStatus(1448) == 1) {
	for (i = 0; i < 10; i++) {
	    if (pi.getPlayerCount(910540500 + i) == 0) {
		pi.setTimer(211040401,910540500 + i,20 * 60);
                pi.warp(910540500 + i,0);
		pi.getPlayer().spawnAll();
		return;
	    }
	}
    }
}