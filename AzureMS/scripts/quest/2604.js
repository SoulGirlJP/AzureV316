/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 주크블랙)

	엔피시아이디 : 
	
	엔피시 이름 : 홍아

	엔피시가 있는 맵 : 초보 수련장 입구

	엔피시 설명 : 퀘스트


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
	    qm.sendOk("아직 볼일이 남은거야? 빨리하라구. 난 그렇게 시간이 많은 사람이 아니거든.");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("크크크... 이거 이번에 흥미로운 신입이 들어왔는걸? 시바가 느리다고 타박해도 눈 하나 깜빡 안 하다니, 기분 안 나빴어? 사실 시바는 그냥 널 시험해 보려고 한 말이야. 너에게 이도류의 길을 갈 재능은 충분해.");
        } else if (status == 1) {
            qm.sendNextPrev("이도류의 재능은 물론이고 또 다른 재능도 보이는 것 같은데... 이거 평범한 수련을 시켜서는 안 되겠는걸? #b특별한 재능을 가진 사람에게는 특별한 임무를 준다#k! 이게 듀얼 블레이드의 방식이거든.");
        } else if (status == 2) {
            qm.sendNextPrev("어떤 임무냐고? 그건... 아직 설명해 줄 수 없어. #b설희#k님께 널 정식으로 소개하지. 설희님의 마음에 들면 특별한 임무를 받는 거고, 아니면 뭐... 그냥 평범하게 수련하는 거고. 설희님의 마음에 들도록 노력해 보라고.");
        } else if (status == 3) {
            qm.askAcceptDecline("그럼 수락하면 바로 설희님께 보내 주도록 하지.");
        } else if (status == 4) {
	    qm.forceStartQuest();
	    qm.warp(103050101,0);
            qm.dispose();
        }
    }
}
