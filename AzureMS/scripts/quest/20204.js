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
            if (qm.getQuestStatus(20204) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20204) == 1 && qm.haveItem(4032096,30)) {
		qm.askAcceptDecline("시험의 증표를 모두 가져왔군. 생각보다... 아니, 겨우 이런 일로 칭찬해 줄 필요는 없겠지. 너에게 정식기사가 될 자격이 있음을 확인했다. 이대로 정식기사가 되겠나?");
	    }
        } else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.gainItem(1142067,1);
	    qm.getPlayer().changeJob(1410);
	    qm.removeAll(4032096);
	    qm.showinfoMessage("<정식기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제 넌 더 이상 수련기사가 아니다. 시그너스 기사단의 정식기사다.");
        } else if (status == 2) {
            qm.sendNextPrev("너에게 #bSP#k를 좀 줬어. 별로 많진 않지만 새로 전수한 스킬을 찍어 볼 수는 있겠지. 나이트 워커의 스킬 중 정식기사에게 허락된 스킬들이 어떤지 다크니스와 함께 시험해 보라고.");
        } else if (status == 3) {
            qm.sendPrev("그럼 시그너스 기사단의 정식기사로서의 악에 물들지 않기를. 비록 어둠 속에 가려져 있다고 해도.");
	    qm.dispose();
	}
    }
}












