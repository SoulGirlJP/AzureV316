var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            qm.sendNext("아직 무루피아를 사냥할 준비가 안 되신 건가요? 준비할 수 있는 건 모두 준비한 후에 사냥하는 게 좋아요. 괜히 대충 잡다가 비석 세우면 억울해지니까요.");
            qm.dispose();
	    return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.askAcceptDecline("그럼 계속해서 기초 체력 단련을 할까요? 준비는 되셨어요? 검은 제대로 장비 하셨는지 스킬과 물약은 퀵슬롯에 올려 놓았는지 다시 한 번 확인하신 후 수락해 주세요.");
        } else if (status == 1) {
            qm.forceStartQuest();
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("무루피아를 모두 퇴치하고 돌아오셨군요. 확실히 전보다는 체력이 좀 생기신 것 같네요. 그러니까... 펭귄이 파닥파닥 하는 정도?\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i2000022# 20 #t2000022# \r\n#i2000023# 20 #t2000023# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 450 exp");
        } else if (status == 1) {
            qm.gainItem(2000022,20);
            qm.gainItem(2000023,20);
            qm.forceCompleteQuest();
            qm.gainExp(450);
            qm.sendNext("좀 더 체력을 쌓으면 펭귄도 들어올릴 수 있으실 거예요. 그럼 다음 단계 단련을 하실 거면 말씀해 주세요.");
            qm.dispose();
        }
    }
}