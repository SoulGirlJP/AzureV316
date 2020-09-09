/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	엔피시아이디 : 
	
	엔피시 이름 : 로저

	엔피시가 있는 맵 : 달팽이동산

	엔피시 설명 : 퀘스트


*/

var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
		qm.forceCompleteQuest(7269);
		qm.dispose();
        }
    }
}