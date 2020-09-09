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
	    qm.sendNext("안녕하신가? 여행하기 참 좋은 날씨라고 생각하지 않는가? 새로운 여행을 떠나는 모험가인가 보군.");
        } else if (status == 1) {
            qm.askAcceptDecline("지금은 출발 준비를 하고 있으니 조금만 기다려 주게.");
        } else if (status == 2) {
            qm.sendNext("오~! 기다리는 사이에 출발 준비가 모두 완료되었네. 바로 출발할테니 어서 탑승하게나. 그럼 바로 출발 하도록 하지.");
        } else if (status == 3) {
            qm.forceCompleteQuest();
	    qm.warp(3000000,0);
            qm.dispose();
	}
    }
}