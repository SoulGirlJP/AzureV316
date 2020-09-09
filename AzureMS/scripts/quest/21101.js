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
	    qm.askAcceptDecline("#b(나는 스스로를 거대한 폴암을 사용하던 영웅이라고 확신하고 있는가? 확신한다면 힘을 주어 거대한 폴암을 잡자. 분명 뭔가 반응이 올 것이다.)#k");
        } else if (status == 1) {
	    qm.getPlayer().changeJob(2100);
            qm.forceCompleteQuest();
            qm.gainItem(2000013,50);
            qm.gainItem(2000014,50);
	    qm.resetStats(35,4,4,4);
	    qm.gainItem(1142129,1);
	    qm.showinfoMessage("<깨어난 아란> 칭호를 획득 하셨습니다.");
            qm.sendNext("#b(뭔가 기억이 떠오르는 것 같다...)#k");
        } else if (status == 2) {
	    qm.warp(914090100);
            qm.dispose();
	}
    }
}