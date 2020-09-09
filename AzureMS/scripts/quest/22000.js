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
            qm.sendNext("잘 잤니, 에반?");
	} else if (status == 1) {
            qm.sendNextPrevS("#b네... 엄마도 잘 주무셨어요?#k",3);
        } else if (status == 2) {
            qm.sendNextPrev("그래... 그런데 넌 어째 잠을 제대로 자지 못한 얼굴이구나. 어젯밤에 천둥하고 번개가 엄청나게 쳤지. 그래서 그런가?");
        } else if (status == 3) {
            qm.sendNextPrevS("#b아뇨. 그게 아니라 간밤에 이상한 꿈을 꿔서요.#k",3);
        } else if (status == 4) {
	    qm.sendNextPrev("이상한 꿈? 무슨 꿈인데 그러니?");
        } else if (status == 5) {
            qm.sendNextPrevS("#b그러니까...#k",3);
        } else if (status == 6) {
            qm.sendNextPrevS("#b(안개 속에서 드래곤을 만나는 꿈을 꿨다고 설명했다.)#k",3);
        } else if (status == 7) {
            qm.askAcceptDecline("호호호호, 드래곤이라고? 그거 굉장한데? 잡아 먹히지 않아서 다행이구나. 재미있는 꿈이니 유타에게도 말해주렴. 분명 좋아할 거야.");
        } else if (status == 8) {
            qm.forceStartQuest();
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("어, 일어났냐. 에반? 아침부터 눈 밑이 왜 그렇게 퀭해? 밤에 못잤어? 뭐? 이상한 꿈을 꿨다고? 무슨 꿈인데? 에엥? 드래곤이 나오는 꿈을 꿨단 말이야?");
        } else if (status == 1) {
	    qm.gainExp(20);
            qm.forceCompleteQuest();
            qm.sendNextPrev("푸하하하하~ 드래곤이라고? 그거 굉장한데? 용꿈이잖아! 근데 혹시 그 꿈에 개는 한 마리 안나왔나? 하하하하~\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 20 exp");
        } else if (status == 2) {
            qm.sendNextPrev("아침부터 한참 웃어네. 하하하. 자, 이상한 소리는 그만하고 불독한테 아침밥이나 좀 줘.");
        } else if (status == 3) {
            qm.sendNextPrevS("#b엥? 그건 유타가 할 일이지낳아?#k",3);
        } else if (status == 4) {
            qm.askAcceptDecline("이 녀석! 형이라고 부르라니까! 불독이 나를 얼마나 싫어하는지는 너도 잘 알잖아. 다가가면 분명히 물리고 말거야. 불독이 넌 좋아하니까 네가 가져다 줘.");
        } else if (status == 5) {
	    qm.gainItem(4032447,1);
            qm.forceStartQuest(22001);
            qm.dispose();
        }
    }
}