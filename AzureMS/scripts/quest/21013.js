var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
	if (mode == 0 && status == 0) {
	    qm.dispose();
	}
        if (mode == 0 && status == 1) {
            qm.sendNext("영웅님께도 분명 도움이 되는 물건일 거예요. 사양하지 말아주세요.");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendSimple("여, 영웅님... 저, 정말 뵙고 싶었어요.\r\n#b#L0#(수줍어하는 것 같다)#l");
        } else if (status == 1) {
            qm.askAcceptDecline("저어, 아주 예전부터 영웅님을 만나면 선물하고 싶은 게 있었는데... 마을로 가느라 바쁘신 건 알지만... 제 마음의 선물을 받아주시겠어요?");
        } else if (status == 2) {
            qm.forceStartQuest();
            qm.sendNext("선물의 재료는 이 주변에 있는 상자 안에 넣어 두었어요. 번거로우시겠지만 상자를 부순 후 그 안에서 재료인 #b대나무 한 단#k과 #b목재#k를 가져다 주세요. 그럼 바로 조립해서 드릴게요.");
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
            qm.sendNext("재료를 모두 가져오셨군요? 그럼 잠시만요, 이렇게 저렇게 조립을 하면... \r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i3010062# 1 #t3010062# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 95 exp");
        } else if (status == 1) {
            qm.gainItem(4032309,-1);
            qm.gainItem(4032310,-1);
            qm.gainItem(3010062, 1);
            qm.forceCompleteQuest();
            qm.gainExp(95);
            qm.sendNextPrev("자, 의자 완성이에요! 헤헤, 아무리 영웅이라도 피곤할 때가 있을 거라고 생각해서 예전부터 영웅님에게 의자를 선물로 드리고 싶었어요.");
        } else if (status == 2) {
            qm.sendNextPrev("영웅이라고 해서 언제나 강할 수 있는 건 아니라고 생각해요. 영웅도 분명 지치고 힘들 때도 있고, 약해질 때도 있을 거예요. 하지만 그것을 극복할 수 있기에 영웅이라는 거겠죠?")
            qm.dispose();
        }
    }
}