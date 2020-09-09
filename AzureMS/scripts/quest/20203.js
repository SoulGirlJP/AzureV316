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
            if (qm.getQuestStatus(20203) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20203) == 1 && qm.haveItem(4032096,30)) {
		qm.askAcceptDecline("시험의 증표... 모두 확인했습니다. 당신에게 정식기사가 될 자격이 있음을 확인했습니다. 정식기사의 길을 가시겠습니까?");
	    }
        } else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.gainItem(1142067,1);
	    qm.getPlayer().changeJob(1310);
	    qm.removeAll(4032096);
	    qm.showinfoMessage("<정식기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제 당신은 더 이상 수련기사가 아닙니다. 시그너스 기사단의 정식기사입니다.");
        } else if (status == 2) {
            qm.sendNextPrev("당신께 #bSP#k를 드렸습니다. 윈드 브레이커의 스킬 중에 정식기사에게만 허락된 스킬들도 전수해 드렸으니, 스톰과 함께 연마하시길.");
        } else if (status == 3) {
            qm.sendPrev("그럼 시그너스 기사단의 정식기사로서 항상 이성적인 사고를 잊지 마시길...");
	    qm.dispose();
	}
    }
}