var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (status == 0 && mode == 0) {
            qm.sendNext("안 돼요, 아란... 아이를 포기하고 우리들만 살아남아봤자... 그건 아무런 의미도 없어요! 당신께 부담이 되는 건 알지만... 다시 생각해 주세요!");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.askAcceptDecline("이런! 아이 한 명이 숲에 남겨진 모양이에요! 아이를 두고 우리들만 도망칠 수는 없어요! 아란... 미안하지만 아이를 구하러 가 주세요! 부상까지 입은 당신께 이런 말을 해서는 안 되는 걸 알고 있지만... 당신밖에 없어요!");
        } else if (status == 1) {
            qm.forceStartQuest();
            qm.sendNext("#b아이는 아마도 숲 깊은 곳에 있을 거예요!#k 검은 마법사가 이곳을 알아내기 전에 방주를 출발시켜야 하니 서둘러서 아이를 데려와 주셔야 해요!");
        } else if (status == 2) {
            qm.sendNextPrev("가장 중요한 건 당황하지 않는 거예요, 아란. 혹시 퀘스트 진행 상황을 확인하고 싶으면 #bQ키#k를 눌러 퀘스트창을 확인하세요.");
        } else if (status == 3) {
            qm.sendNextPrev("부탁해요, 아란! 아이를 구해 주세요! 더 이상 검은 마법사에게 누구도 희생시키고 싶지 않아요!");
            qm.dispose();
        }
    }
}