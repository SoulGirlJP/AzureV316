


/*

	퓨어 온라인 소스 스크립트 입니다.

	포탈이 있는 맵 : 위험한 동굴

	포탈 설명 : 피아누스의 동굴


*/

var map = 230040420;

function enter(pi) {
    if (!pi.checkTimeValue("Pianus_BattleStartTime", 3600 * 4)) {
        pi.getPlayer().message(5, "피아누스는 4시간마다 한번씩만 도전할 수 있습니다.");
        return false;
    }
    pi.setTimeValueCurrent("Pianus_BattleStartTime");
    pi.playPortalSE();
    pi.warp(map);
    return true;
}
