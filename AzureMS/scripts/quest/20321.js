var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
	    qm.sendYesNo("시험준비는 모두 끝나셨나요? 지금 바로 시험장으로 입장하셔도 괜찮겠습니까?");
        } else if (status == 1) {
	    for (i = 0; i < 10; i ++) {
	    	if (qm.getPlayerCount(913070200 + i) == 0) {
		    qm.resetMap(913070200 + i);
		    qm.setTimer(130000000,913070200 + i,60 * 5);
	            qm.warp(913070200 + i,0);
                    qm.dispose();
		    qm.forceStartQuest();
		    return;
		}
	    }
	}
    }
}