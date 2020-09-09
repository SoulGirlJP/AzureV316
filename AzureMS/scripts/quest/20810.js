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
            if (qm.getQuestStatus(20810) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20810) == 1) {
                qm.sendYesNo("미하일, 수습기사가 되기 위한 모든 시험에 통과한 것을 축하해요. 언제라도 수습기사로 임명이 가능하답니다. 지금 바로 수습기사의 절차를 밟겠어요?");
	    }
        } else if (status == 1) {
	    qm.changeJob(5110);
            qm.forceCompleteQuest();
	    qm.gainItem(1142400,1);
	    qm.showinfoMessage("<수습 빛의 기사> 칭호를 획득 하셨습니다.");
            qm.dispose();
        }
    }
}