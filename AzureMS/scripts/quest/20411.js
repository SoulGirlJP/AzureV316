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
            if (qm.getQuestStatus(20411) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20411) == 1) {
                qm.sendNext("큰 활약을 해주셔서 감사합니다. 미하일님 덕분에 시그너스님은 무사하십니다. 현혹되었던 기사들도 모두 정상으로 돌아왔고요. 기사들 모두 당신에게 큰 신뢰와 감사의 마음을 가지고 있습니다. 그리고 당신을 자신들의 기사단장으로써 인정했습니다. 이제 원치 않으셔도 저의 제안을 받아들이셔야 할 것 같습니다.");
	    }
        } else if (status == 1) {
	    qm.changeJob(5112);
            qm.forceCompleteQuest();
	    qm.gainItem(1142402,1);
	    qm.showinfoMessage("<빛의 기사단장> 칭호를 획득 하셨습니다.");
            qm.dispose();
        }
    }
}