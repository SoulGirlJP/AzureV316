


/*

	퓨어 온라인 소스 스크립트 입니다.

	포탈이 있는 맵 : 골렘사원4

	포탈 설명 : 미니던전 입장


*/

var map = 106021500;
var exit = 106021400;

function enter(pi) {
    if (pi.getPlayer().getMapId() == map) {
        var eim = pi.getEventInstance();
        if (eim == null) {
            pi.warp(exit);
            return true;
        }
        eim.removePlayer(pi.getPlayer());
        pi.warp(exit);
        pi.getPlayer().message(5, "미니던전 인스턴스에서 퇴장했습니다.");
        return true;
    }
            
        
}
