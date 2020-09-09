/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 키무

	엔피시가 있는 맵 : 시작의 숲2

	엔피시 설명 : 시그너스 튜토리얼


*/

importPackage(Packages.server.quest);

var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else if (mode == 0 && status < 3)
            status--;
	else if (mode == 0 && status == 3) {
	    qm.sendOk("메이플 월드에 오신 것을 환영합니다! 여기는 메이플 월드에서도 가장 평화롭고 안전한 곳, 여제 시그너스님이 다스리는땅, 에레브입니다.");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("에레브에 오신 것을 환영합니다! 당신의 이름이... 아, #h #?님이시군요? 반가워요! 기다리고 있었어요. 시그너스 기사단이 되려고 오신 것 맞죠? 제이름은 키무. 여제 시그너스의 명을 받아, 당신과 같은 노블레스를 안내하고 있답니다.");
        } else if (status == 1) {
	    qm.sendNextPrev("정식으로 시그너스 기사단이 되려면 먼저 여제를 뵈야 하는데요, 여제꼐서는 이 섬에서도 가장 중심이 되는 곳에 시눗와 함께 계세요. 거기까지 가기 전에 메이플 월드에서 필요한 기초상식들을 제형제들이 알려드릴까 하는데, 괜찮으세요?");
        } else if (status == 2) {
	    qm.sendNextPrev("아참, 미리 말씀드리지만 이건 퀘스트랍니다. 메이플 월드의 주민들은 종종 여러분께 이런저런 부탁을 하는데, 그걸 #r퀘스트#k라고 불러요. 수락한 퀘스트를 완료하면 경험치나 아이템 등의 보상을 얻을 수 있으니 성실하게 수행하면 도움이 될 거예요.");
        } else if (status == 3) {            
            qm.askAcceptDecline("그럼 당신께 사냥에 대해 알려드릴 #r키잔#k을 만나러 가시겠어요? 키잔은 화살표를 따라 왼쪽으로 가면 만나실 수 있답니다.");        
        } else if (status == 4) {
	    qm.forceStartQuest();
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
        if (status == 0) {
            qm.sendNext("키무 형이 보낸 노블레스가 당신이야? 반가워! 난 키잔. 키무 형이 당신에게 전해 달라고 한 보상을 전해줄게. #b I 키#k를 눌러 인벤토리를 열면 확인할 수 있어. 빨간색은 HP를, 파란색은 MP를 회복시켜 줘. 미리미리 사용법을 알아두는 게 좋아.\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i2000020# 노블레스의 빨간 포션 5개\r\n#i2000021# 노블레스의 파란 포션 5개\r\n#fUI/UIWindow.img/QuestIcon/8/0# 15 exp");
        } else if (status == 1) {          
	    qm.forceCompleteQuest();
	    qm.gainItem(2000020, 5);
	    qm.gainItem(2000021, 5);
            qm.gainExp(15);
	    qm.dispose();
        }
    }
}