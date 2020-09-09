/*
이볼빙 시스템 링크3 퀘스트

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
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
	    if (qm.getQuestStatus(1825) == 0) {
	        qm.sendOk("시스템 장애로 인해 가상 몬스터가 변형되었습니다. 변조된 #e#b#o9306005#, #o9306100#를 처치#k#n해서 오류를 바로 잡으면 됩니다.\r\n#o9306005#는 #o9306004#를 처치할 경우 나타나고, #o9306100#은 #o9306005#를 모두 제거하면 나타납니다.");
	        qm.forceStartQuest();
	        qm.dispose();
	    } else {
		qm.sendOk("링크5의 오류를 수정, 교정 기술 향상 훈련을 성공적으로 마쳤습니다.");
		qm.forceCompleteQuest();
		qm.dispose();
	    }
	}
    }
}