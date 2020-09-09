


/*

	퓨어 온라인 소스 스크립트 입니다.

	포탈이 있는 맵 : 불과 어둠의 전장

	포탈 설명 : 그리프의 숲


*/

var map = 240020101;

function enter(pi) {
    if (!pi.checkTimeValue("Gryphius_BattleStartTime", 3600 * 6)) {
        pi.getPlayer().message(5, "그리프의 숲에는 6시간마다 한번씩만 입장할 수 있습니다.");
        return false;
    }
    if (!pi.getPlayer().getMap().isExpiredMapTimer()) {
        pi.getPlayer().message(5, "지금은 그리프의 숲에 입장할 수 없습니다.");
        return;
    }
    pi.setTimeValueCurrent("Gryphius_BattleStartTime");
    pi.scheduleTimeMoveMap(pi.getPlayer().getMapId(), map, 10000, true);
    pi.playPortalSE();
    
    pi.warp(map, "sp");
    return true;
}