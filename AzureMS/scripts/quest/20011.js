/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 키잔

	엔피시가 있는 맵 : 시작의 숲3

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
	    qm.sendOk("강해지고 싶다면 열심히 수련하는 수밖에 없어.");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("사냥하는 방법에는 여러 가지가 있지만, 가장 기초적인 건 #b일반공격#k으로 공격하는 거야. 손에 무기만 들고 있다면 언제든지 할 수 있어. 몬스터 앞에 서서 휘두르기만 하면 되거든.");
        } else if (status == 1) {
	    qm.sendNextPrev("#bCtrl 키#k를 누르면 일반공격을 할  수 있어. 보통은 #b키보드 맨 왼쪽 아래#k에 있는데... 혹시 위치를 모르지는 않겠지? 자, 한 번 확인해봐.");
        } else if (status == 2) {            
            qm.askAcceptDecline("확인했으면 바로 시험을 해봐야겠지? 이 주변에는 에레브에서 가장 약한 티노가 많이 있으니 한 번 사냥해 보도록 해. #r1마리#k 사냥 한 후에 돌아오면 보상을 주도록 할게.");        
        } else if (status == 3) {
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
            qm.sendNext("아, 티노는 무사히 잡은 모양이네? 어때? 생각보다 간단하지? 일반공격은 간단하지만 그만큼 약해. 보통은 더 강력한 스킬을 쓰는데... 그건 키누가 알려줄 거야. 그보다 퀘스트 보상부터 줄게.");
        } else if (status == 1) {
	    qm.sendNext("노블레스용 장비야. 입고 있는 옷보다는 성능이 조금 더 좋을 거야. 보기에도 멋지고. 화살표를 따라 왼쪽으로 더 가면 동생인 #b키누#k가 있는데, 그 애를 만나기 전에 옷을 갈아입는 게 어떨까?\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i1002869# 노블레스 우아한 모자 1개\r\n#i1052177# 노블레스 화려한 로브 1개\r\n#fUI/UIWindow.img/QuestIcon/8/0# 30 exp");
        } else if (status == 2) {          
	    qm.forceCompleteQuest();
	    qm.gainItem(1002869, 1);
	    qm.gainItem(1052177, 1);
            qm.gainExp(30);
	    qm.dispose();
        }
    }
}