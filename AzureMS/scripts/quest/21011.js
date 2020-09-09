var status = -1;

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && status == 4) {
            qm.sendNext("그, 그러시군요. 역시 영웅님은 바쁘시군요... 훌쩍. 혹시 생각이 바뀌시면 다시 말씀해 주세요.");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("리, 리린님과 함께 계신 당신은 설마... 설마... 역시 영웅이신가요? 리린님! 귀찮다는 듯이 고개만 끄덕이지 말고 확실히 말해 주세요! 이분이 바로 영웅?!");
        } else if (status == 1) {
            qm.sendNextPrev("...죄송합니다. 감동해서 그만 소리를 지르고 말았네요. 훌쩍. 하지만 정말 감동해서... 아아, 눈물이 나려고해... 리린님께서도 정말 기쁘시겠군요.");
        } else if (status == 2) {
            qm.sendNextPrev("그런데... 영웅님께서는 무기를 안 들고 계시는군요. 영웅들은 자신만의 무기가 있던 걸로 아는데... 아, 검은 마법사와 싸우는 동안 잃어버리신 모양이군요.");
        } else if (status == 3) {
            qm.sendYesNo("대신이라고 하긴 너무 초라하지만, #b일단 이 검이라도 들고 다녀 주세요.#k 영웅님께 드리는 선물이에요. 영웅이 빈 손으로다니는 건 너무 이상하니까요. \r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i1302000# 1 #t1302000# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 35 exp");
        } else if (status == 4) {
            qm.gainItem(1302000,1);
            qm.forceCompleteQuest();
            qm.gainExp(35);
            qm.sendNextS("#b(스킬도 전혀 영웅답지 않았는데... 검마저 매우 낯설다. 과거의 난 검을 사용해 본 적이 있기는 한 걸까? 검은 어떻게 착용하는 거지?)#k",3);
            qm.dispose();
        }
    }
}