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
	    qm.sendNext("흐엉~ 티티티 슬프다. 티티티 화난다. 티티티 운다... 흐어어엉~");
        } else if (status == 1) {
            qm.sendNextPrevS("무, 무슨 일이냐.",3);
        } else if (status == 2) {
            qm.sendNextPrev("티티티 보석 만들었다. #b사과처럼 빨간 보석#k이다. 그런데 #r도둑#k이 보석 훔쳐갔다. 티티티 보석 없다. 티티티 슬프다...");
    	} else if (status == 3) {
            qm.sendNextPrevS("빨간 보석을 도둑이 훔쳐갔다고?",3);
    	} else if (status == 4) {
            qm.askAcceptDecline("그렇다. 티티티 보석 되찾고 싶다. 티티티 보석 찾아주면 사례한다. 도둑 잡아주면 사례한다.");
    	} else if (status == 5) {
	    qm.sendNext("도둑은 저쪽으로 갔다. 저쪽이... 밥 먹는 손이 오른손, 밥 안 먹는 손이 왼손... #b왼쪽#k이다! 왼쪽으로 가면 도둑 잡을 수 있을 거다.");
	    qm.forceStartQuest();
	    qm.dispose();
	}
    }
}