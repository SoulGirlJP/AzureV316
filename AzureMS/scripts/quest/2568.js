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
	    qm.askAcceptDecline("왔나? 자네가 일을 보고 오는 동안에 대포에 발화장치가 장착 되었다네. 자, 그럼 더 이상 지체할 필요 없겠지! 바로 출발하지!");
	} else if (status == 1) {
            qm.forceStartQuest();
	    qm.warp(120000202,0);
            qm.dispose();
	}
    }
}