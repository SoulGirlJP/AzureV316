var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == -1) {
            qm.sendNext("앗, 안 할 거야? 안 할 거야? 아... 아쉽다.");
            qm.dispose();
        }
        if (status == 0) {
            qm.sendNext("자네가 바로 마이가 추천한 그 사람이로군. 전사가 되고 싶어한다고 들었네만... 맞는가? 내가 바로 전사 전직관 주먹펴고 일어서라네. 전사로서 모험하길 원하는 자들을 도와주고 있지."); 
        } else if (status == 1) {
	    qm.sendNextPrev("전사에 대해서는 어느 정도 알고 있는가? 전사는 강한 힘과 체력을 바탕으로 근거리 무기를 휘둘러 적을 쓰러뜨리는 직업이지. 적 가장 가까이에서 싸우는 두려움 없는 자들. 매력적이지 않는가?");
        } else if (status == 2) {
	    qm.sendAcceptDecline("자네는 자질이 충분해 보이는군. 자네 같은 사람이 전사가 되겠다면 환영이지. 전사가 되겠는가? 수락한다면 전직관의 특권을 사용해 당장 자네를 #b페리온, 전사의 성전#k으로 초대하겠네 #r하지만 거절한다고 해서 길이 없는 건 아니야. 거절하면 다른 직업의 길을 안내하자.");
        } else if (status == 3) {
	    qm.forceStartQuest();
	    qm.dispose();
        } else {
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
        if (status == -1) {
            qm.sendNext("앗, 안 할 거야? 안 할 거야? 아... 아쉽다.");
            qm.dispose();
        }
        if (status == 0) {
            qm.sendYesNo("여기서 만나니 더욱 반갑군... 그럼 자네를 전사로 만들어 주지. 마음의 준비는 물론 되었겠지? 물러서는 자는 전사가 아니야."); 
        } else if (status == 1) {
	    if (qm.getJobId() == 0) {
                qm.changeJobById(100);
                qm.gainItem(1302077, 1);
		var lv = qm.getPlayer().getLevel();
	        qm.resetStats(35, 4, 4, 4, lv > 10 ? (lv - 10) * 5 + 23 : 23, lv > 10 ? (lv - 10) * 3 + 1 : 1);
		qm.forceCompleteQuest();
	        qm.gainItem(1142107, 1);
		qm.forceCompleteQuest(29900);
	    }
	    qm.sendNext("전사가 된 자네는 한층 강해졌다네. 전사로서 사용할 수 있는 새로운 스킬을 생겼으니 어서 새로운 힘을 시험해 보게나.");
        } else {
            qm.dispose();
        }
    }
}