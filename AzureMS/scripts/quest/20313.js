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
	    qm.askAcceptDecline("그 일을 막은 당신의 공로를 높이 사서 여제께서 당신께 새로 직위를 수여하셨습니다. 직위를 받으시겠습니까?");
        } else if (status == 1) {
	    qm.forceStartQuest();
	    qm.forceCompleteQuest(20311);
	    qm.gainItem(4032179,-1);
	    qm.gainItem(4032103,-1);
	    qm.gainItem(1142068,1);
	    qm.getPlayer().changeJob(1311);
	    qm.showinfoMessage("<상급기사의 훈장> 칭호를 획득 하셨습니다.");
            qm.expandInventory(4,4);
            qm.sendNext("#h #. 당신을 상급기사로 임명합니다. 지금 이 시간부터 당신은 시그너스 기사단이 상급기사로서 더 많은 책임을 짊어지게 됩니다. 항상 자유로운 눈으로 세계를 바라보되, 당신이 가진 의무를 잊지 마시길...");
	    qm.dispose();
	}
    }
}