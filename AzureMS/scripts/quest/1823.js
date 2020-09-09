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
	    qm.sendOk("날아다니는 #o9306002#를 잘 따라다니며 50마리를 처치하면 됩니다.\r\n링크3 내의 유도장치가 작동하면, #o9306002#들은 유도장치 주변으로 몰려들게 되는데, 그때를 놓치지 않고 이용하시면 좀 더 수월하게 프로그램을 진행하실 수 있습니다.");
	    qm.forceCompleteQuest();
	    qm.dispose();
        }
    }
}