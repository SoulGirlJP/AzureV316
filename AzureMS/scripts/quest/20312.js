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
	    qm.askAcceptDecline("그런 일을 막은 당신의 공로를 높이 사, 여제께서 당신께 새로운 직위를 수여하셨어요. 직위를 받으시겠어요?");
        } else if (status == 1) {
	    qm.forceStartQuest();
	    qm.forceCompleteQuest(20311);
	    qm.gainItem(4032179,-1);
	    qm.gainItem(4032102,-1);
	    qm.gainItem(1142068,1);
	    qm.getPlayer().changeJob(1211);
	    qm.showinfoMessage("<상급기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("#h #. 당신을 상급기사로 임명합니다. 지금 이 시간부터 당신은 시그너스 기사단이 상급기사로서 더 열정적으로 자신을 단련하길 바래요. 그 열정이 당신에게 두려움이 아닌 용기를 줄 거예요.");
	    qm.dispose();
	}
    }
}