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
            if (qm.getQuestStatus(20205) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20205) == 1 && qm.haveItem(4032096,30)) {
		qm.askAcceptDecline("시험의 증표를 모두 가져왔네? 아하하! 너라면 잘 할 줄 알았어. 너에게 정식기사가 될 자격이 있음을 확인하였노라! 하하. 이대로 정식기사가 되겠어?");
	    }
        } else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.gainItem(1142067,1);
	    qm.getPlayer().changeJob(1510);
	    qm.removeAll(4032096);
	    qm.showinfoMessage("<정식기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제 넌 더 이상 수련기사가 아니야. 시그너스 기사단의 정식기사지.");
        } else if (status == 2) {
            qm.sendNextPrev("너한테 #bSP#k를 줬어. 스트라이커의 정식기사에게만 허락된 스킬들을 찍어볼 수 있을 거야. 라이트닝과 결합된 더 강한 스킬들, 잘 올려봐.");
        } else if (status == 3) {
            qm.sendPrev("뭐, 시그너스 기사단의 정식기사라고 너무 심각하게 생각하지는 말았으면 좋겠어. 아무리 어려운 일이라도 즐거움을 찾자고.");
	    qm.dispose();
	}
    }
}