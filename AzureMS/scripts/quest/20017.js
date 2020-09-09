/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 키쿠

	엔피시가 있는 맵 : 수련의 숲1

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
	    qm.sendOk("기사라면 누구나 수련의 숲에서 수련할 수 있다네.");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("응? 나인하트가 보냈다고? 아하! 이야, 이번에 새로 들어온 신입인가? 반갑군. 반가워. 내 이름은 키쿠. 자네 같은 노블레스들을 가르치는 수련교관이지. 물론 인간은 아냐.");
        } else if (status == 1) {
	    qm.sendNextPrev("우리는 피요라고 불리는 종족이야. 어린 여제 곁에 있는 신수는 본적이 있지? 피요는 신수와 같은 종족이야. 계열은 좀 다르지만...비슷하지. 에레브에서만 살고 있으니 낯설겠지만 금방 익숙해질거야.");
        } else if (status == 2) {
	    qm.sendNextPrev("아, 혹시 알고 있어? 이 에레브에는 몬스터는 전혀 없어. 사악한 힘을 가진 존재는 에레브에 발도 들일 수 없거든. 그래도 걱정하진 마. 수련은 신수가 만들어낸 환상생물 티티를 대상으로 할 거니까.");
        } else if (status == 3) {            
            qm.askAcceptDecline("기합이 제대로 들어갔군! 그럼... 자네의 수준을 보아하니, 티티들중에서도 조금 상급의 티티를 잡아도 되겠군. #b수련의 숲2#k에 있는 #r티무 15마리#k 정도면 충분하겠는걸? 왼쪽의 포탈을 타고 #b수련의 숲2#k로 가서 사냥하도록 하게.");        
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
            qm.sendOK("오호, 티무는 다 퇴치한 건가? 생각보다 빠른걸? 좋아. 그럼바로 다음 단계로 넘어가도 되겠군.\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i2000020# 노블레스의 빨간 포션 10개\r\n#i2000021# 노블레스의 파란 포션 10개\r\n#fUI/UIWindow.img/QuestIcon/8/0# 430 exp");
        } else if (status == 1) {          
	    qm.forceCompleteQuest();
	    qm.gainItem(2000020, 10);
	    qm.gainItem(2000021, 10);
            qm.gainExp(430);
	    qm.dispose();
        }
    }
}