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
        qm.sendOk("가상 세계 적응을 위해 워밍업 프로그램을 진행하실 것을 추천합니다.");
        qm.dispose();
    } else {
         status++;
         if (status == 0) {
	if (qm.getQuestStatus(1806) == 0) {
	     qm.sendOk("아래 보이는 #b몬스터 생성기를 작동#k시키면, 몬스터가 나타나기 시작합니다..\r\n가상 세계에 적응할 겸, #e#b#o9306000# 10마리#k#n 정도만 처치하십시오");
	     qm.forceStartQuest();
	     qm.dispose();
	} else {
	      qm.sendNext("#o9306000# 10마리를 모두 처치하셨습니까?");
	}
         } else if (status == 1) {
	
	qm.sendNextPrev("워밍업 프로그램을 성공적으로 마치셨습니다. 이런 방식으로 각 링크로 이동, 그 링크에서 진행 가능한 프로그램을 완료하시면 됩니다.");
         } else if (status == 2) {
	qm.sendNextPrev("몬스터 스킨 변경 코어 1개가 지급되었습니다. 이볼빙 시스템을 종료하신 후, 코어 장착 후 재접속 하시면, 변화를 체험하실 수 있습니다.");
	//퀘스트완료
	qm.dispose();
         }
    }
}