/*
이볼빙 시스템 링크7 퀘스트

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
	    qm.sendOk("#e[강화 프로그램] 상황 대처 능력#n\r\n\r\n링크7의 #b#e#o9306007# 30마리#n#k를 처치하세요.");
	}
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
	    if (qm.getQuestStatus(1827) == 0) {
	        qm.sendOk("링크7의 #b#o9306007# 30마리를 처치#k하세요.\r\n가끔 프로텍터들이 나타나 공격을 방해하게 될테니, 방해 요소를 제거 후, 시스템 몬스터를 처치하시면 됩니다.");
	        qm.forceStartQuest();
	        qm.dispose();
	    } else {
		qm.sendYesNo("상황 대처 능력 향상 프로그램을 모두 완료하셨습니까?");
	    }
	} else if (status == 1) {
	    qm.sendOk("상황 대처 능력 향상을 위한 강화 프로그램을 성공적으로 마쳤습니다.");
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
    }
}