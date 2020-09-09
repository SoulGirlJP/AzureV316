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
            if (qm.getQuestStatus(20320) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20320) == 1) {
		qm.askAcceptDecline("승급시험을 통과하신 것을 축하드립니다. 이제부터 정식기사입니다. 지금 바로 전직하시겠습니까?");
	    }
	} else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.getPlayer().changeJob(5111);
	    qm.gainItem(1142401,1);
	    qm.showinfoMessage("<정식 빛의 기사> 칭호를 획득 하셨습니다.");
	    qm.dispose();
	}
    }
}