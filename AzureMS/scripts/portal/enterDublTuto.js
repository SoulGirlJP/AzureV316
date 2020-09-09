


/*

	오딘 KMS 팀 소스의 스크립트 입니다. (제작 : 주크블랙)

	포탈이 있는 맵 : 103050100

	포탈 설명 : 비화원윗층 (2605)


*/


function enter(pi) {
    if (pi.getQuestStatus(2605) >= 1 && pi.getQuestStatus(2609) <= 1) {
	pi.warp(103050500, 0);
	pi.playPortalSE();
	return true;
    } else {
	pi.getPlayer().dropMessage(5, "문이 잠겨있어 접근할 수 없습니다.");
	return false;
    }
    
}
