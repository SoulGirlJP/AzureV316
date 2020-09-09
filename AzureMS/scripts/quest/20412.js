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
            qm.askAcceptDecline("준비는 모두 끝나셨나요? 지금 바로 출발하셔도 괜찮겠습니까?");
        } else if (status == 1) {
	    for (i = 0; i < 10; i++) {
		if (qm.getPlayerCount(913070100 + i) == 0) {
		    qm.resetMap(913070100 + i);
                    qm.setTimer(130000000,913070100 + i,60 * 5);
	            qm.warp(913070100 + i,0);
	            qm.showinfoMessage("제한시간 내에 사로잡힌 기사들을 제압하고 여제 시그너스를 호위하라!");
		    qm.forceStartQuest();
                    qm.dispose();
		    return;
		}
	    }
        }
    }
}