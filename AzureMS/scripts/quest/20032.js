var status = -1;

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
            if (qm.getQuestStatus(20032) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20032) == 1) {
            	qm.forceCompleteQuest();
		qm.sendNext("왜 이렇게 오래 걸렸어? 굼뜨기는~ 청소는 다 끝낸거야? 이제 아주 조금 봐줄만하군. 뭘봐? 청소끝났으면 물건 정리하는걸 도와야지!");
	    }
	} else if (status == 1) {
	    qm.gainExp(40);
	    qm.gainExp(92);
	    qm.warp(913070003,0);
	    qm.dispose();
	}
    }
}