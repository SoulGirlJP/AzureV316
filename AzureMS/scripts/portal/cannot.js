


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	포탈이 있는 맵 : 109040004

	포탈 설명 : 시작맵 포탈차단


*/


function enter(pi) {
    pi.getPlayer().message(5, "포탈을 통해서는 나가실 수 없습니다.");
    return false;
}
