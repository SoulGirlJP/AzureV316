/*
이볼빙 시스템 링크1 퀘스트 - 워밍업

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
        qm.sendOk("처음부터 시스템에 과부하를 주게 되면 예상치 못한 일이 일어날 수도 있습니다. 워밍업을 하실 것을 추천합니다.");
        qm.dispose();
    } else {
         status++;
         if (status == 0) {
	if (qm.getQuestStatus(1806) == 0) {
	     qm.forceStartQuest();
	     qm.sendOk("#b몬스터 생성기를 작동#k시키면, 몬스터가 나타나기 시작합니다..\r\n간단히 몸을 풀 겸 #e#b20마리#k#n 정도만 처치하십시오.");
	     //qm.dispose();
	} else {
	      qm.sendNext("#o9306000# 20마리를 처치하셨습니까?"); //획들 물음표 성향 손재주5
	}
         } else if (status == 1) {
	
	qm.sendOk("기초 프로그램이 종료되었습니다.");
         } else if (status == 2) {
	qm.sendNextPrev("몬스터 스킨 변경 코어 1개가 지급되었습니다. 이볼빙 시스템을 종료하신 후, 코어 장착 후 재접속 하시면, 변화를 체험하실 수 있습니다.");
	//퀘스트완료
	qm.dispose();
         }
    }
}