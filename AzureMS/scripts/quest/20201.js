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
            if (qm.getQuestStatus(20201) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20201) == 1 && qm.haveItem(4032096,30)) {
		qm.askAcceptDecline("시험의 증표를 모두 가져왔군... 좋다. 그대에게는 정식기사가 될 자격이 충분하다. 정식기사의 길을 가겠는가?");
	    }
        } else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.gainItem(1142067,1);
	    qm.getPlayer().changeJob(1110);
	    qm.removeAll(4032096);
	    qm.showinfoMessage("<정식기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제 수련기사로서의 그대는 없다. 그대는 시그너스 기사단의 정식기사다.");
        } else if (status == 2) {
            qm.sendNextPrev("그대에게 #bSP#k를 주었다. 소울 마스터의 스킬 중, 정식기사에게만 허락된 스킬을 몇 가지 전수했으니 소울과 함께 더욱 강해지도록.");
        } else if (status == 3) {
            qm.sendPrev("시그너스 기사단의 정식기사로서 부끄럽지 않은 몸가짐을 갖춰 여제의 명예를 높이도록.");
	    qm.dispose();
	}
    }
}