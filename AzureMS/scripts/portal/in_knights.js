/*
 * 퓨어온라인 소스 스크립트 입니다.
 * 
 * 포탈위치 : 
 * 포탈설명 : 
 * 
 * 제작 : 주크블랙
 * 
 */

function enter(pi) {
    if (pi.getPlayer().getLevel() < 160) {
        pi.getPlayer().message("레벨 160 이상만 입장 가능합니다.");
        return false;
    }
    pi.playPortalSE();
    pi.warp(271030100);
    return true;
}