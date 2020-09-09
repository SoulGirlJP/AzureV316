/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 설희

	엔피시가 있는 맵 : 설희의 방

	엔피시 설명 : 1.5차전직퀘스트


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
            qm.sendNext("이도류를 사용하기 위해서는 2가지 조건을 충족시켜야 해요. 첫째,레벨20이상이어야하고, 둘째, 자격을 갖춘 주인만이 얻을수 있다고 전해지는 전설의 보물 #b혜안#k을 가져와야 해요.");
        } else if (status == 1) {
            qm.sendNextPrev("혜안은 통찰력을 갖추게 해준다는 보물로 주인을 선택해서 나타나는 물건이지요. #b비화원의 심처, 구슬의방#k으로 보내 주죠. 구슬의 방에 있는 #b구슬들을 타격#k해서 떨어뜨리면 탁하게 변할 거에요.");
        } else if (status == 2) {
            qm.askAcceptDecline("#b탁해진 구슬을 더블클릭해서 깨트리면 혜안#k을 얻을 수 있답니다. 물론 당신에게 혜안의 주인이 될 자격이 있다면 말이죠. 쉽게 얻을 수 있는 물건이 아니므로 노력하셔야 해요. 아시겠어요? 그럼 수락하면 당신을 구슬의 방으로 보내죠.");
        } else if (status == 3) {
	    qm.forceStartQuest();
	    qm.warp(910350000, 0);
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
            qm.sendYesNo("훌륭하군요. 혜안이 당신을 선택했네요. 지금 바로 이도류의 진정한 스킬을 배워보겠어요? 허락한다면 당신을 세미 듀어러로 각성 시키죠.");
        } else if (status == 1) {
	    qm.gainItem(4032616, -1);
            qm.forceCompleteQuest();
            qm.getPlayer().changeJob(430);
            qm.gainItem(1342000, 1);
	    qm.sendNext("이제부터 당신은 #b세미듀어러#k에요. 항상 자부심을 갖고 생활하세요.");
	    qm.dispose();
        }
    }
}