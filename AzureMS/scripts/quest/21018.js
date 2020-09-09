var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0) {
            qm.sendNext("어라, 혹시 5마리로는 부족하신 건가요? 혹시 수련을 위해 더 퇴치하고 싶으시다면 하셔도 상관은 없어요. 영웅님을 위해 이번만은 마음이 아프지만 좀 더 사냥하더라도 눈 감아 드릴...");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("자, 그럼 이때까지 기초 체력을 한 번 테스트 해보도록 할게요. 방법은 간단해요. 이 섬에서 가장 강하고 흉폭한 몬스터, 무루쿤을 퇴치하면 돼요! 한 #r50마리#k퇴치해 주시면 좋겠지만...");
        } else if (status == 1) {
            qm.askAcceptDecline("몇 마리 없는 무루쿤을 다 퇴치해 버리는 건 생태계에 좋지않은 것 같으니 5마리만 퇴치하도록 할게요. 자연과 환경을생각하는 단련! 아, 아름답기도 해라...");
        } else if (status == 2) {
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
            qm.sendNext("무루쿤 5마리를 모두 퇴치하고 오셨군요. 좋아요. 순조롭게 기초 체력이 쌓였네요. 얼음 속에서 굳어 있던 몸이 딱 수련하기 좋은 정도로 풀리셨어요. 그럼 이제부터 수련을 시작... 어라?\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 1200 exp");
        } else if (status == 1) {
            qm.forceCompleteQuest();
            qm.gainExp(1200);
            qm.sendNext("아, 그러고 보니... 지금까지 영웅님의 성함도 모르고 있었네요. 다섯 명의 영웅들 중 한 명인 건 확실한데, 누구세요? ... 아참, 기억이 없으시지...");
        } else if (status == 2) {
            qm.sendNextPrev("이제부터 검은 마법사와 싸우기 위해 본격적인 수련을 시작해야 하는데, 당신이 어떤 영웅인지 알아야 그에 맞는 수련을 할 거 아니겠어요? 당신에게 맞지 않는 무기로 맞지 않는 스킬을 연마해 봤자, 검은 마법사와 싸우기엔 역부족일 테니까요.");
        } else if (status == 3) {
            qm.sendNext("그런데... 영웅님은 대체 누구시죠?");
            qm.dispose();
        }
    }
}