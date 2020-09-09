


/*

	퓨어 온라인 소스 스크립트 입니다.

	포탈이 있는 맵 : 불과 물의 전장

	포탈 설명 : 미니던전 입장


*/

var map = 240020501;
var exit = 240020500;

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
    } else {
        var em = pi.getEventManager("MiniDungeon");
        if (em == null) {
            pi.getPlayer().message(5, "미니던전 스크립트에 오류가 발생했습니다. GM에게 문의해 주세요.");
            return false;
        }
        if (pi.getPlayer().getParty() != null) {
            if (!pi.allMembersHere()) {
                pi.getPlayer().message(5, "파티원이 모두 모여있어야 입장할 수 있습니다.");
                return false;
            }
            if (!pi.isLeader()) {
                pi.getPlayer().message(5, "파티장이 입장할 수 있습니다.");
                return false;
            }
            em.setProperty("Leader_"+pi.getPlayer().getParty().getLeader().getId()+"_Exit", pi.getPlayer().getMapId()+"");
            em.setProperty("Leader_"+pi.getPlayer().getParty().getLeader().getId()+"_Map", map+"");
            em.startInstance(pi.getParty(), pi.getPlayer().getMap());
            pi.getPlayer().message(5, "미니던전 인스턴스에 입장되었습니다.");
            var eim = pi.getPlayer().getEventInstance();
            eim.startEventTimer(7200000);
            return true;
        } else {
            em.setProperty("Leader_"+pi.getPlayer().getId()+"_Exit", pi.getPlayer().getMapId()+"");
            em.setProperty("Leader_"+pi.getPlayer().getId()+"_Map", map+"");
            em.startInstance(pi.getPlayer());
            pi.getPlayer().message(5, "미니던전 인스턴스에 입장되었습니다.");
            var eim = pi.getPlayer().getEventInstance();
            eim.startEventTimer(7200000);
            return true;
        }
    }
            
        
}
