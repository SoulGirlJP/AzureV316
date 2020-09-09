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
	    qm.askAcceptDecline("이 일을 해결한 네 공로를 높이 사서 여제께서 네게 새로운 직위를 내리셨지. 지위를 받겠어?");
        } else if (status == 1) {
	    qm.forceStartQuest();
	    qm.forceCompleteQuest(20311);
	    qm.gainItem(4032179,-1);
	    qm.gainItem(4032104,-1);
	    qm.gainItem(1142068,1);
	    qm.getPlayer().changeJob(1411);
	    qm.showinfoMessage("<상급기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("#h #. 널 상급기사로 임명한다. 지금 이 시간부터 넌 시그너스 기사단의 상급기사로서 더 큰 신념을 관찰해 나가게 된다. 어둠 속에 있는 수많은 유혹들이 널 흔들겠지만 네 신념만을 향해 나아가도록.");
	    qm.dispose();
	}
    }
}