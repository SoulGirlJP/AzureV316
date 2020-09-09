var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && status == 2) {
            qm.sendNext("음... 이 방법으로는 기억이 돌아오지 않을까요? 그래도 혹시 모르니 다시 한 번 생각해 주십시오.");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("영웅님! 안녕하세요! 네? 당신이 영웅인 줄 어떻게 알았냐고요? 그야 앞에서 세 명이 그 큰 목소리로 떠들어댔으니 당연히 알지요. 이미 섬에 영웅님이 돌아오셨다는 소문이 전부 퍼졌을 겁니다.");
        } else if (status == 1) {
            qm.sendNextPrev("그나저나, 어쩐지 영웅님의 표정이 안 좋으신데요? 무슨 문제라도 있으신가요? 네? 본인이 정말 영웅인지 모르겠다고요? 영웅님께서 기억을 잃으신 겁니까?! 그럴 수가... 수백 년이나 얼음 속에 갇혀 있던 부작용인 모양이군요.");
        } else if (status == 2) {
            qm.askAcceptDecline("아, 영웅이시니 검을 휘둘러 보면 뭔가 생각나실 수도 있지\r\n않을까요? #b몬스터 사냥#k을 해보시면 어떻습니까?");
        } else if (status == 3) {
            qm.forceStartQuest();
            qm.sendNext("마침 이 주변에 #r튜토리얼 무루#k들이 많이 사는데, 한 #r3마리#k만 퇴치해 보십시오. 뭔가 떠오를지도 모릅니다.");
        } else if (status == 4) {
            qm.sendNextPrev("아, 혹시 스킬 사용법도 잊어버리신 건 아닙니까? #b스킬을 퀵 슬롯에 올려 놓으면 편하게 사용할 수 있습니다.#k 스킬뿐만 아니라 소비 아이템도 올릴 수 있으니 잘 활용하십시오.", 1);
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (status == 0 && mode == 0) {
            qm.sendNext("어라...? 포션이 싫으신 건가요?");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendYesNo("음... 표정을 보니 별로 떠오르는 건 없으신 모양이네요... 하지만 걱정 마십시오. 어떻게든 잘 될 겁니다. 자, 여기 포션을 드릴 테니 힘내세요!\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i2000022# 10 #t2000022# \r\n#i2000023# 10 #t2000023# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 57 exp");
        } else if (status == 1) {
            qm.gainItem(2000022,10);
            qm.gainItem(2000023,10);
            qm.forceCompleteQuest();
            qm.gainExp(57);
            qm.sendNextS("#b(내가 진짜 영웅이라고 해도... 아무런 능력도 없는 영웅이 과연 쓸모가 있을까?)#k",3);
            qm.dispose();
        }
    }
}

