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
            if (qm.getQuestStatus(22007) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(22007) == 1) {
		qm.sendNext("오, 달걀은 가져온 거야? 그럼 달걀을 건네줘. 그럼 부화기를 줄게.");
	    }
	} else if (status == 1) {
	    qm.gainExp(360);
	    qm.forceCompleteQuest();
	    qm.sendNext("자, 받으라고. 도대체 어디에 쓰려는 건지는 모르겠지만...\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 360 exp");
	    qm.dispose();
	}
    }
}