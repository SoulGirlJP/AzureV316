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
            if (qm.getQuestStatus(20202) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20202) == 1 && qm.haveItem(4032096,30)) {
		qm.askAcceptDecline("시험의 증표를 모두 가져오셨군요! 정말 대단해요! 당신께는 정식기사가 될 자격이 충분해요! 정식기사의 길을 가시겠어요?");
	    }
        } else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.gainItem(1142067,1);
	    qm.getPlayer().changeJob(1210);
	    qm.removeAll(4032096);
	    qm.showinfoMessage("<정식기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제 당신은 더 이상 수련기사가 아니예요. 시그너스 기사단의 정식기사예요!");
        } else if (status == 2) {
            qm.sendNextPrev("당신께 #bSP#k를 드렸어요. 플레임 위자드의 스킬 중에 정식기사에게만 허락된 스킬들도 전수해 드렸으니, 플레임과 함께 더 강해지세요!");
        } else if (status == 3) {
            qm.sendPrev("자, 그럼 시그너스 기사단의 정식기사로서 더욱 열정을 갖고 노력해 주세요!");
	    qm.dispose();
	}
    }
}