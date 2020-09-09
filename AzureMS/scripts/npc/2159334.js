/*

	천외천온라인 에 의해 만들어 졌습니다.

	엔피시아이디 : 1033110

	엔피시 이름 : 오르골

	엔피시가 있는 맵 : 요정의 숲 : 에우렐 (101050000)

	엔피시 설명 : MISSINGNO


*/

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1 || mode == 0) {
        cm.dispose();
    } else {
        status++;
        if (status == 0) {
	   if (cm.getQuestStatus(3507) == 1 && cm.getQuestStatus(3544) == 0 && cm.getPlayer().getJob() == 3112) {
             cm.sendNext("주군, 혹시 무슨 고민이라도 있으신가요?");
	} else {
		cm.sendOk("너는 누구야? 우리편이야? 적이야? 난 우리편이 아닌 자에겐 관심 없어.")
		cm.dispose();
		}
	} else if (status == 1) {
           cm.sendNextPrevS("아니에요. 그냥 마스테마님이 보고 싶었을 뿐이라 할까요? 정말 오랜만에 뵈는 것 같네요. 추억이라..추억....." ,2);
	} else if (status == 2) {
           cm.sendNextPrevS("추억이라니... 대체 언제적 추억을 말하는 거지? 검은 마법사를 따르기로 한 그 때? 아니면 검은 마법사에게 대항하던 그 때? 그것도 아니면 최근 광산에서 깨어났을 때를 말하는 것인가?" ,2);
	} else if (status == 3) {
           cm.sendNextPrevS("하지만 추억이라면 보통 따뜻한 것을 말하는 것이겠지. 나에게 따뜻한 추억이 있을까? 그래... 검은 마법사를 만나기 전에 가족들과 같이 지냈던 시기가 가장 따뜻한 나날들이었던 것 같다. 어머니... 그리고 데미안." ,2);
	} else if (status == 4) {
           cm.sendNextPrevS("그러고보면 마스테마와 수련을 했던 때도 꽤 즐거웠지. 나에게 다시 그런 날들이 돌아올까?." ,2);
	} else if (status == 5) {
           cm.sendNextPrevS("(행복하고 즐거웠던 추억을 회상하니 왠지 마음이 따뜻해진다.)\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 1249000 exp\r\n#fUI/UIWindow.img/QuestIcon/11/0# 감성 40" ,2);
	} else if (status == 6) {
	    cm.playSound(false, "profession/levelup");
	    cm.forceCompleteQuest(3544);
	    cm.forceCompleteQuest(3507);
	    cm.gainExp(1249000);
	    cm.dispose();
        }
    }
}