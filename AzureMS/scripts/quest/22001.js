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
            qm.sendNextPrev("아침부터 한참 웃어네. 하하하. 자, 이상한 소리는 그만하고 불독한테 아침밥이나 좀 줘.");
        } else if (status == 1) {
            qm.sendNextPrevS("#b엥? 그건 유타가 할 일이지낳아?#k",3);
        } else if (status == 2) {
            qm.askAcceptDecline("이 녀석! 형이라고 부르라니까! 불독이 나를 얼마나 싫어하는지는 너도 잘 알잖아. 다가가면 분명히 물리고 말거야. 불독이 넌 좋아하니까 네가 가져다 줘.");
        } else if (status == 3) {
	    qm.gainItem(4032447,1);
            qm.forceStartQuest();
	    qm.dispose();
	}
    }
}