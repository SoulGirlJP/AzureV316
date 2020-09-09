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
	    qm.sendNext("끼이이이익! 끽! 끽!");
        } else if (status == 1) {
            qm.sendNextPrevS("배는 찼는데 정신은 여전히 없네... 이 상황은 대체 뭐지? 눈을 뜨자마자 보이는 건 원숭이에, 여기는 어디인지도 모르겠고... 배는 어떻게 된 거지? 넌 어떻게 된 건지 알고 있니?",3);
        } else if (status == 2) {
	    qm.askAcceptDecline("끼익, 끼익! (원숭이가 고개를 끄덕인다. 정말 이 녀석이 상황을 아는 건가? 원숭이에게 자세히 물어보자!)");
	} else if (status == 3) {
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}