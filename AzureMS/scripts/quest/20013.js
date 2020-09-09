/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 키아

	엔피시가 있는 맵 : 시작의 숲5

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
	    qm.sendOk("몬스터를 사냥해서 전리품을 얻는 것이 아이템을 구하는 기본이자 정석인 법이지~");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("#b(뚝딱뚝딱...)#k");
        } else if (status == 1) {
	    qm.sendNextPrev("...앗! 깜짝이야! 정신이 없어서 누가 온 줄도 모르고 있었네. 네가 키누가 말하던 그 노블레스야? 반가워! 나는 키아라고 해. 특기는 #b의자#k를 만드는 거지. 에레브에 온 기념으로 너에게도 의자를 하나 만들어 줄 생각이야.");
        } else if (status == 2) {
	    qm.sendNextPrev("상자 여는 법은 알아? 상자는 몬스터를 공격할 때처럼 무기를 휘둘러 공격하면 부숴서 열 수 있어. 단, 몬스터는 스킬로 공격이 가능하지만 #b상자는 일반공격으로만 공격이 된다#k는 걸 명심해야 해.");
        } else if (status == 3) {            
            qm.askAcceptDecline("상자를 열어서 #b석재#k와 #b휘장#k을 하나씩만 구해줘! 재료만 모이면 바로 멋진 의자를 만들어 줄게. 그럼 기다릴게!");        
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
            qm.sendNext("석재와 휘장은 구해온 거야? 어디 한 번 볼까? ...맞아! 이게 바로 의자의 재료가 되는 석재와 휘장이야! 그럼 바로 의자로 만들어 줄게.");
        } else if (status == 1) {
	    qm.sendNext("자, 노블레스 의자야. 멋지지? #b피로할 때 의자에 앉으면 HP가 빨리 회복#k 돼. 인벤토리 #b설치창#k에 들어가 있을 테니 확인해 본 후에 #b키샤#k에게 가줘. 화살표를 따라 왼쪽으로 가면 만날 수 있어.\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i3010060# 노블레스 의자 1개\r\n#fUI/UIWindow.img/QuestIcon/8/0# 95 exp");
        } else if (status == 2) {          
	    qm.forceCompleteQuest();
	    qm.gainItem(4032267, -1);
	    qm.gainItem(4032268, -1);
	    qm.gainItem(3010060, 1);
            qm.gainExp(95);
	    qm.dispose();
        }
    }
}