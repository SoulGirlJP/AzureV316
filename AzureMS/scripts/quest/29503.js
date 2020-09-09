/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 엘도라도)

	엔피시아이디 : 
	
	엔피시 이름 : 나인하트

	엔피시가 있는 맵 : 에레브

	엔피시 설명 : 시그너스 튜토리얼


*/

importPackage(Packages.server.quest);

var status = -1;

function start(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        qm.dispose();
	return;
    } else {
	status++;
	if (status == 0) {
	    qm.sendSimple("기부왕 훈장에 대해 궁금한가? 어떤것을 선택해보겠는가?#b\r\n\r\n#L0#현재 나의 랭킹을 보고 싶습니다.#l\r\n#L1#전체 기부왕 랭킹을 보고 싶습니다.#l"); 
	} else if (status == 1) {
            if (selection == 0) {
                var rank = Packages.MedalRanking.getCurrentRank(qm.getPlayer().getId(), qm.getPlayer().getMapId()+"_기부왕");
                if (rank == -1) {
                    qm.sendOk("아직 아무도 순위에 등록되지 않았다네.");
                    qm.dispose();
                    return;
                }
                qm.sendOk("현재 자네의 순위는 #b"+rank+"#k위 라네.");
                qm.dispose();
            } else {
		qm.dispose();
	    }
        }
    }
}