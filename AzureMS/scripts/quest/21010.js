var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && (status == 0 || status == 3)) {
            qm.sendNext("어이쿠, 사양하실 것 없습니다! 영웅님께 포션 하나 드리는게 뭐 어려운 일이라고. 마음이 바뀌면 다시 말씀해 주십시오.");
            qm.dispose();     
	    return;       
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.askAcceptDecline("음? 이 섬에 왠 사람이지...? 어라, 리린님 아니십니까? 리린님이 여기까지 어쩐 일로... 아, 혹시 이 분은 리린님께서 아시는 분입니까? 네? 이 분이 영웅이라고요?");
        } else if (status == 1) {
            qm.sendNextPrev("이 분이 바로 리린님의 일족이 수백 년간 기다려온 바로 그 영웅이시군요! 오오, 어쩐지 척 보기에도 평범한 분은 아닌 것 같더라니...");
        } else if (status == 2) {
            qm.askAcceptDecline("그런데 검은 마법사의 저주로 얼음 속에서 수백 년을 잠들어 계셔서 그런지 영웅님께서 체력이 매우 약해지신 것 같군요. #b여기 체력 회복용 포션 하나 드릴 테니 어서 드십시오.#k");
        } else if (status == 3) {
            qm.gainItem(2000022,1);
            qm.forceStartQuest();
            qm.sendNext("일단 쭈욱 들이키신 후에 다시 이야기 하죠~");
        } else if (status == 4) {
            qm.sendNextPrevS("#b(포션은 어떻게 마시는 거지? ...기억이 나지 않는데...)#k",3);
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
            qm.sendNext("오랫동안 영웅의 흔적을 찾아 얼음 속을 발굴했지만, 정말 영웅님께서 나타나실 줄이야! 역시 예언이 틀리지 않았군요! 리린님께서는 옳은 선택을 하신 겁니다! 영웅님께서 귀환하셨으니 이제 검은 마법사는 두려워할 필요 없겠죠!");
        } else if (status == 1) {
            qm.sendNextPrev("아, 이런. 제가 너무 영웅님을 오래 잡아두고 있었군요. 반가운 마음에 그만... 아마 다른 펭귄들도 다들 그럴 겁니다. 바쁘신 것은 알지만 마을로 가는 길에 #b다른 펭귄 녀석들에게도 말을 걸어 주세요.#k 영웅님께서 말을 걸어 주시면 다들 놀랄 겁니다!\r\n\r\n #fUI/UIWindow.img/QuestIcon/4/0# \r\n#i2000022# 5 #t2000022#\r\n#i2000023# 5 #t2000023#\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 16 exp");
        } else if (status == 2) {
            qm.gainItem(2000022,5);
            qm.gainItem(2000023,5);
            qm.forceCompleteQuest();
            qm.gainExp(16);
            qm.sendNextPrev("레벨업을 하셨군요? 혹시 스킬 포인트도 획득하셨을지 모르겠습니다. 메이플 월드에서는 레벨 1이 올라갈 때마다 스킬 포인트 3을 얻을 수 있거든요. #bK키#k를 눌러 스킬창을 열면 확인하실 수 있을 겁니다.");
        } else if (status == 3) {
            qm.sendNextPrevS("#b(이렇게까지 친절한데 기억나는 것이 아무것도 없어. 난 정말 영웅인 걸까. 일단 스킬을 확인해 보자... 근데 어떻게 확인하지?)#k",3);
            qm.dispose();
        }
    }
}