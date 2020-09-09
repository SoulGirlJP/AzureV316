/*
이볼빙 시스템 링크8 퀘스트

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
	    qm.sendOk("#e[강화 프로그램] 분별력 향상#n\r\n\r\n#b#e#o9306200# 2마리#n#k를 찾아내고, 그들이 사라지기 전에 처치해야 한다.");
	}
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
	    if (qm.getQuestStatus(1828) == 0) {
	        qm.sendOk("#o9306200#을 찾아내서, 사라지기 전에 처치하시면 됩니다. #o9306200#은 #o9306003# 무리에 숨어 있을테니, 잘 구분해서 처치하세요.");
	        qm.forceStartQuest();
	        qm.dispose();
	    } else {
		qm.sendYesNo("#o9306200#을 찾아서 처치하셨습니까?");
	    }
	} else if (status == 1) {
	    qm.sendOk("분별력 향상을 위한 강화 프로그램을 성공적으로 마쳤습니다.");
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
    }
}