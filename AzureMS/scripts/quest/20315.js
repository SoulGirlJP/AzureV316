var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
	    qm.askAcceptDecline("그래서 말이야. 이 일을 해결한 너에게 여제께서 새로운 직위를 내리셨어. 직위를 받겠어?");
        } else if (status == 1) {
	    qm.forceStartQuest();
	    qm.forceCompleteQuest(20311);
	    qm.gainItem(4032179,-1);
	    qm.gainItem(4032105,-1);
	    qm.gainItem(1142068,1);
	    qm.getPlayer().changeJob(1411);
	    qm.showinfoMessage("<상급기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("#h #. 그대를 상급기사로 임명하노라! 헤헷. 지금 이 시간부터 넌 시그너스 기사단의 상급기사야. 지위가 높아진 만큼 더 어려운 임무가 주어지겠지. 하지만 뭐 어때? 그것까지 모두 즐거워! 즐기지 않으면 너무 어려워지잖아!?");
	    qm.dispose();
	}
    }
}