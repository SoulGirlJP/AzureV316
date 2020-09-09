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
            if (qm.getQuestStatus(20031) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20031) == 1) {
		qm.sendSimple("왜 이렇게 오래 걸렸어? 내가 안 보는 사이 농땡이친거 아니야? 가져오라는 물건은 가져왔어?#b\r\n#L0#네...여기...그리고 다락에서 이 편지를 줏었는데요. 아직 못 보신것 같아서요...크롬이라는 분이 보내신 것 같던데...");
	    }
        } else if (status == 1) {
	    qm.gainExp(12);
	    qm.gainExp(51);
	    qm.gainItem(4033194,-1);
	    qm.gainItem(4033195,-1);
            qm.forceCompleteQuest();
            qm.sendNext("뭐라고!! 이리내놔! 왜 남의 물건에 마음대로 손을 대는거야?");
        } else if (status == 2) {
	    qm.warp(913070002,0);
            qm.dispose();
	}
    }
}