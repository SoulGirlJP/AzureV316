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
    var startmap = Integer.parseInt(eim.getProperty("Global_StartMap"));
    var curstage = Integer.parseInt(eim.getProperty("CurrentStage"));
    var curmap = (startmap + ((curstage - 1) * 100));
    if (eim.getProperty(curmap+"Prepared") == null) {
        pi.prepareAswanMob(curmap, eim);
        eim.setProperty(curmap+"Prepared", "1");
    }
    if(curmap == pi.getPlayer().getMapId()) {
        pi.getPlayer().send(UIPacket.showInfo("던전 내의 몬스터를 모두 잡아야 다음 스테이지로 이동할 수 있습니다."));
        pi.getPlayer().message(5, "몬스터를 모두 잡으신 후 다음 포탈로 이동해 주세요.");
        return false;
    } else {
        pi.warp(curmap);
        return true;
    }
}