/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 키누

	엔피시가 있는 맵 : 시작의 숲4

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
	    qm.sendOk("일반공격도 물론 필요하지만 사냥의 기본은 역시 스킬이지요.");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("기다리고 있었습니다. #h # 씨. 제 이름은 키누. 형제 중 셋째입니다. 일반공격에 대해 배우셨다고요? 그럼 다음 단계는 당연히 짐작하고 계시겠죠? 바로 #b스킬#k입니다. 메이플 월드에서는 스킬공격이 아주 중요하답니다.");
        } else if (status == 1) {
	    qm.sendNextPrev("레벨업을 하면 스킬 포인트를 얻을 수 있습니다. 당신에게도 어느정도 스킬 포인트가 있을 텐데요, #bK키#k를 눌러 스킬창에서 원하는 스킬에 적당히 스킬 포인트를 투자하여 스킬을 올려 보세요. 이렇게 올린 #b스킬을 퀵슬롯에 올려 놓으면 훨씬 사용하기 편합니다.#k");
        } else if (status == 2) {
	    qm.askAcceptDecline("자, 그럼 까먹기 전에 바로 실전연습입니다. 이 주변에는 티브들이 많이 있는데요, #b달팽이 세마리 스킬을 사용해 #r티브 3마리#k를 사냥한 후 그 증거로 #b티브의 깃털#k을 1개 가져와 주세요. 그럼 기다리겠습니다.");        
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
            qm.sendNext("티브를 퇴치하고 티브의 깃털을 가져오셨군요. 훌륭합니다. #b정식으로 기사가 되면 레벨업을 할 때마다 스킬포인트를 3씩#k 얻으실 수 있습니다. 그럼, 화살표를 따라 왼쪽으로 쭈욱 가면 다음 단계를 담당하고 있는 #b키아#k를 만나실 수 있을 겁니다.\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#fUI/UIWindow.img/QuestIcon/8/0# 40 exp");
        } else if (status == 1) {
	    qm.gainItem(4000483, -1);          
	    qm.forceCompleteQuest();
            qm.gainExp(40);
	    qm.dispose();
        }
    }
}