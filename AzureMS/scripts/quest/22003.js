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
            qm.askAcceptDecline("농장에 일하러 가신 #b아빠#k가 그만 도시락을 깜빡 잊고 가셨지 뭐니. 네가 #b농장 중심지#k에 계신 아빠에게 #b도시락을 배달#k해주렴. 착하지?");
        } else if (status == 1) {
	    qm.gainItem(4032448,1);
            qm.forceStartQuest();
	    qm.dispose();
	}
    }
}