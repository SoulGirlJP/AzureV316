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
            if (qm.getQuestStatus(2642) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(2642) == 1) {
	    	qm.forceCompleteQuest();
	    	qm.getPlayer().changeJob(432);
		qm.sendNext("이제부터... 당신은 듀얼 마스터입니다.");
	    }
        } else if (status == 1) {
            qm.sendNext("예전에 비해 몰라보게 강해졌군요. 홍아가 당신에 대해 말하던 게 생각나네요... 당신이라면 분명 강해질 거라고. 재능이 있다고... 다크로드와 싸울 수 있을 거라고... 그렇게 말했죠.");
        } else if (status == 2) {
            qm.sendNextPrev("당신은 이렇게 강해졌는데 정작 비화원의 수장이라는 내가 약해져만 가는 것 같아요. 우리의 싸움이 항상 정당하다고만 믿었는데... 그것이 아니라면 어떻게 해야 할까요?");
        } else if (status == 3) {
            qm.sendNextPrev("아버지... 당신이라면 어떻게 하셨을까요? 도적들을 이끌던 당신이라면...");
        } else if (status == 4) {
            qm.sendPrev("미안해요. 상념이 길었군요... 아직 좀 더 생각을 정리할 시간이 필요해요.");
	    qm.dispose();
	}
    }
}