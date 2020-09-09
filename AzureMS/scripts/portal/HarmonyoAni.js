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
//    pi.playPortalSE();
//    pi.warp(211060300, 1);
//    return true;
    pi.getPlayer().message(5, "이곳은 현재 입장할 수 없습니다.");
    return false;
}