
/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	포탈이 있는 맵 : 10000

	포탈 설명 : 미니맵 도움말 표시


*/
function enter(pi) {
    if (pi.getPlayer().getKeyValue("tuto_infoMinimap") == null) {
        pi.showWZEffect("UI/tutorial.img/25", 1);
        pi.getPlayer().setKeyValue("tuto_infoMinimap", "1");
    }
    return false;
}