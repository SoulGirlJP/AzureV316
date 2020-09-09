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
            if (qm.getQuestStatus(23033) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23033) == 1) {
                qm.sendYesNo("이 정도로 능력이 있다면 더 이상 망설일 것 없지. 너무 위험해서 좀 더 후에 전수할 생각이었지만... 더 강력한 배틀 메이지의 스킬을 너에게 전수해 주겠어! 너라면 그 정도는 감당할 수 있을 것 같군!");
	    }
        } else if (status == 1) {
	    qm.changeJob(3211);
	    qm.gainItem(1142244,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 상급생> 칭호를 획득 하셨습니다.");
	    qm.sendNext("널 전직시켰어. 이제 더 이상 예전의 네가 아니야. 광기에 가까운 위험천만한 스킬의 세계가 펼쳐질 거야. 컨트롤하기 쉽진 않겠지만... 후후, 그 위험한 임무도 해낸 너라면 어렵지 않게 다룰 수 있으리라 믿어.");
        } else if (status == 2) {
	    qm.sendPrev("그럼 다음 수업에 보자. 그때까지 레지스탕스로서 멋지게 활약하길 기대하지.");
            qm.dispose();
        }
    }
}