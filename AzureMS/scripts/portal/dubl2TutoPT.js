


/*

	오딘 KMS 팀 소스의 스크립트 입니다. (제작 : 주크블랙)

	포탈이 있는 맵 : 103050900

	포탈 설명 : 시작하는곳 퀘스트 체크 (2600)


*/


function enter(pi) {
    if (pi.getQuestStatus(2600) == 1) {
	pi.warp(103050910, 0);
	return true;
    } else {
	pi.getPlayer().dropMessage(5, "홍아에게 퀘스트를 받아야 진행할 수 있습니다.");
	return false;
    }
    
}
