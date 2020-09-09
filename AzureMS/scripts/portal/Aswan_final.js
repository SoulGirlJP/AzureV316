/*
 * 퓨어온라인 소스 스크립트 입니다.
 * 
 * 포탈위치 : 
 * 포탈설명 : 
 * 
 * 제작 : 주크블랙
 * 
 */
importPackage(java.lang);
importPackage(Packages.packet.creators);

function enter(pi) {
    var eim = pi.getPlayer().getEventInstance();
    if (eim == null) {
        pi.warp(262000000);
        return true;
    }
    if (eim.getProperty("CurrentStage").equals("4")) {
        pi.getPlayer().gainExp(30000, true, true, true);
        eim.unregisterPlayer(pi.getPlayer());
        pi.getPlayer().addInnerExp(100 * pi.getPlayer().getInnerLevel());
        pi.getPlayer().message(5, 100 * pi.getPlayer().getInnerLevel()+"의 명성치를 얻었습니다.");
        pi.getPlayer().makeNewAswanShop();
        pi.warp(262000000);
        return true;
    } else {
        pi.getPlayer().send(UIPacket.showInfo("던전 내의 몬스터를 모두 잡아야 다음 스테이지로 이동할 수 있습니다."));
        pi.getPlayer().message(5, "몬스터를 모두 잡으신 후 다음 포탈로 이동해 주세요.");
        return false;
    }
}