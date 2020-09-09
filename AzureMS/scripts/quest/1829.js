/*
이볼빙 시스템 링크9 퀘스트

자쿰씨 제작
*/

importPackage(Packages.server.quest);

var status = -1;

function start(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
	    qm.sendOk("start");
	    qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1 || mode == 0) {
	if (mode == 0) {
	    qm.sendOk("#e[강화 프로그램] 전투력 강화#n\r\n\r\n강력한 힘과 체력을 가진 #e#b#o9306101# 20마리 처치#k#n해야 한다.");
	}
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
	    if (qm.getQuestStatus(1829) == 0) {
	        qm.sendOk("강력한 힘과 체력을 가진 #e#b#o9306101# 20마리#n를 처치#k하세요.");
	        qm.forceStartQuest();
	        qm.dispose();
	    } else {
		qm.sendYesNo("#o9306101# 20마리를 처치하셨습니까?");
	    }
	} else if (status == 1) {
	    qm.sendOk("전투력 강화 프로그램을 성공적으로 마쳤습니다.");
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
    }
}