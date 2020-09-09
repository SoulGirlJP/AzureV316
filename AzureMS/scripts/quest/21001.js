var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (status == 0 && mode == 0) {
            qm.sendNext("으아앙! 아란님이 거절했어!");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.askAcceptDecline("으으... 무서워서 혼났어요... 훌쩍. 어서 헬레나님께 데려가 주세요!");
        } else if (status == 1) {
            qm.forceStartQuest();
            qm.gainItem(4001271,1);
            qm.warp(914000300);
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (status == 0 && mode == 0) {
            qm.sendNext("한시가 급해요! 빨리 아이를 데려와주세요!");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendYesNo("아아, 무사하셨군요? 아이는? 아이는 데려오셨나요?");
        } else if (status == 1) {
            if (!qm.forceCompleteQuest(21001)) {
                qm.gainItem(4001271,-1);
                qm.forceCompleteQuest();
            }
            qm.sendNext("다행이에요... 정말 다행이다...");
        } else if (status == 2) {
            qm.sendNextPrevS("어서 비행선에 타! 더 이상 시간이 없다!",3);
        } else if (status == 3) {
            qm.sendNextPrev("마, 맞아요. 이럴 때가 아니죠. 검은 마법사의 기운이 점점 가까워지고 있어요! 아무래도 방주의 위치를 알아낸 모양이에요! 당장 출발하지 않으면 당해버리고 말 거예요!");
        } else if (status == 4) {
            qm.sendNextPrevS("당장 출발해!",3);
        } else if (status == 5) {
            qm.sendNextPrev("아란! 당신도 방주에 타세요! 싸우고 싶은 당신의 마음은 알지만... 이미 늦었어요! 싸움은 동료들에게 맡기고 함께 빅토리아 아일랜드로 가요!");
        } else if (status == 6) {
            qm.sendNextPrevS("그럴 순 없어!",3);
        } else if (status == 7) {
            qm.sendNextPrevS("헬레나, 당신은 먼저 빅토리아 아일랜드로 떠나, 절대 죽지않고 나중에 꼭 합류할게. 난 동료들과 검은 마법사와 싸우겠어!",3);
        } else if (status == 8) {
            qm.warp(914090010);
            qm.dispose();
        }
    }
}