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
            if (qm.getQuestStatus(23013) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23013) == 1) {
                qm.sendYesNo("메카닉이 되기로 결심하신 겁니까? 선택을 번복할 기회는 있습니다. 대화를 멈추고 퀘스트를 포기한 후 다른 분께 말을 걸면 됩니다. 잘 생각해 보십시오. 정말 메카닉을 택하시겠습니까? 이 직업이 당신의 레지스탕스의 길에 어울린다고 생각합니까?");
	    }
        } else if (status == 1) {
	    qm.changeJob(3500);
	    qm.gainItem(1492014,1);
	    qm.gainItem(1142242,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 신입생> 칭호를 획득 하셨습니다.");
            qm.sendNext("정식 레지스탕스가 된 걸 환영합니다. 지금부터 당신은 메카닉입니다. 기계를 다루는 자로서 당신이 할 수 있는 모든 방법을 동원해 눈 앞에 있는 적을 처단하십시오.");
        } else if (status == 2) {
	    qm.sendPrev("자칫 블랙윙에게 우리의 정체가 발각되기라도 하면 곤란합니다. 그러니 지금부터 절 담임 선생님이라고 부르십시오. 당신은 방과후 특별수업을 받으러 교실에 온 학생인 겁니다. 이 특별수업에서 당신을 강력한 메카닉으로 가르치겠습니다.");
            qm.dispose();
        }
    }
}