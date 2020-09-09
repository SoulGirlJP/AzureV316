var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 0 && status == 4) {
	    qm.dispose();
	    return;
	}
	if (mode > 0)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNext("흘흘... 이런 외진 곳에 젊은이가 어쩐 일인가?");
	} else if (status == 1) {
	    qm.sendNextPrevS("최고의 폴암을 갖고 싶습니다!",3);
	} else if (status == 2) {
	    qm.sendNextPrev("최고의 폴암? 그런 건 저기 어디 마을에서 팔 텐데...");
	} else if (status == 3) {
	    qm.sendNextPrevS("당신이 메이플 월드 최고의 대장장이라고 알고 있습니다! 당신의 무기를 받고 싶습니다!",3);    
	} else if (status == 4) {
	    qm.askAcceptDecline("이 늙은이는 이제 늙어서 좋은 무기 같은 것은 못 만든다네. 하지만 예전에 만든 것 중에 괜찮은 폴암이 있긴 하지... 하지만 넘겨줄 수는 없어. 이 녀석은 워낙에 날카로워서 주인마저도 다치게 할 녀석이거든. 그래도 꼭 갖고 싶은가?");
	} else if (status == 5) {
	    qm.forceStartQuest();
	    qm.sendNext("흘흘... 그렇게까지 말한다면야 어쩔 수 없지. 이 늙은이가 간단한 시험을 하나 하겠네. 옆에 있는 #b수련장#k에 수현하는 #r흉터곰#k 녀석들을 쓰러뜨리고 #b자격의 증표#k를 #b30개#k 가져오게. 그럼 내 거대한 폴암을 내주도록 하지.");
	} else {
	    qm.dispose();
	}
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 0 && status == 2) {
	    qm.sendNext("최고의 폴암을 얻기 싫은 겐가?");
	    qm.dispose();
	    return;
	}
	if (mode > 0)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNext("호오~ 자격의 증표를 모두 구해온 건가? 자네... 생각보다 강한 사람이었구만. 하지만 그보다 마음에 드는 건, 자신을 찌를지도 모르고 위험한 무기를 두려워하지 않고 선뜻 가져가겠다는 패기 넘치는 태도로고... 좋네. 거대한 폴암을 자네에게 주겠네.");
	} else if (status == 1) {
	    qm.sendNextPrev("#b(한참 후, 대장옹은 천에 감싸인 거대한 폴암을 내밀었다.)#k");
	} else if (status == 2) {
	    qm.sendYesNo("이게 바로 자네를 위해 만든 폴암. 마하라네... 잘 부탁하네.");
	} else if (status == 3) {
	    qm.forceCompleteQuest();
	    qm.removeAll(4032311);
	    qm.dispose();
	}
    }
}