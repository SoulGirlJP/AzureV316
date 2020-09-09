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
	    qm.sendNext("끼익~ 끽, 끽! 끼기긱! 끼익~! 끽! 끽?!");
        } else if (status == 1) {
            qm.sendNextPrevS("난 분명 모험가가 되기 위해 메이플 월드로 가고 있었는데... 어떻게 된 거지?",3);
        } else if (status == 2) {
	    qm.sendNextPrev("끼익끼익~ 끽! 끼긱! 끼이이이익!");
        } else if (status == 3) {
            qm.sendNextPrevS("선장님하고 얘기를 하고 주변을 둘러보다가 분명... 맞아! 발록! 발록이 나타났어! 그리고 난... 그대로 배에서 떨어진 건가? 그럼 어떻게 무사한 거지? 분명 #b수영 하는 법은 알지만#k 그 와중에 정신을 차리고 수영이 가능했을 리가 없는데... 혹시 난 의외로 수영 신동이었던 건가?!",3);
        } else if (status == 4) {
	    qm.sendNextPrev("끼이이이익! 끽! 끽! (조그마한 원숭이가 뭔가 불만이 많은 것처럼 발을 동동 구르고 있다. 아직 새끼처럼 보인다. 그러고 보니 눈을 뜬 후 맨 처음 본 얼굴이 이 얼굴이었던 것 같은데...)");
        } else if (status == 5) {
            qm.sendNextPrevS("엥...? 그러고보니 아까부터 넌 뭐가 그리 하고 싶은 말이 많은 거야? 엥? 그 손에 들린 건... (원숭이가 품 안에서 사과를 하나 꺼냈다. 맛있어 보이는 잘 익은 사과다. 그런데... 이 사과를 어쩌라고?)\r\n\r\n#i2010000#",3);
        } else if (status == 6) {
	    qm.askAcceptDecline("끼리리리릭~ 꿀꺽! (원숭이는 답답하다는 얼굴로 사과를 내밀며 입을 움직여 먹는 시늉을 해보인다. 설마... 내 체력이 떨어진 걸 알고 먹으라는 건가! 이 녀석, 정말 좋은 녀석이잖아!)");
	} else if (status == 7) {
	    qm.gainItem(2010000,1);
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}