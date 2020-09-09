/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 시그너스

	엔피시가 있는 맵 : 에레브

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
	    qm.sendOk("에레브에 오신 것을 환영합니다...");
	    qm.dispose();
	    return;
	}	
        if (status == 0) {
            qm.sendNext("알고 계신가요? 지금의 메이플 월드는 평화로워 보이지만 보이지 않는 곳에 어둠이 도사리고 있어요. 메이플 월드를 집어 삼키려는 검은 마법사와, 그를 부활시키려는 자들...");
        } else if (status == 1) {
            qm.sendNextPrev("사악하고 교활한 적들은 점차 힘을 얻어가는데, 우리는 그저 멍하니 바라볼 수밖에 업성요. 아니, 때로는 미지에의 두려움이 더 큰 적이 되어 우리를 괴롭힐 때도 있지요...");
        } else if (status == 2) {
            qm.askAcceptDecline("그렇지만 걱정은 하지 않을게요. 당신이라면 그 모든 것을 이겨내고 메이플 월드를 지켜주실 테니까요... 그렇죠? 기사가 되겠다고 말씀하신 당신이라면...\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i1142065# 노블레스의 훈장 1개");
        } else if (status == 3) {
	    qm.forceStartQuest();
	    qm.gainItem(1142065, 1);
	    qm.sendNext("후훗... 그렇게 대답해 주실 줄 알았어요. 물론 아직은 조금 더 강해 지셔야 하겠지만요");        
	    qm.forceCompleteQuest();
	    qm.dispose();
        }
    }
}