
/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	포탈이 있는 맵 : 30000

	포탈 설명 : 공격 도움말 표시


*/
function enter(pi) {
    if (pi.getPlayer().getKeyValue("tuto_infoAttack") == null) {
        pi.showWZEffect("UI/tutorial.img/20", 1);
        pi.getPlayer().setKeyValue("tuto_infoAttack", "1");
    }
    return false;
}