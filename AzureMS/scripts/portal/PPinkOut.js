/*
 * 퓨어온라인 소스 스크립트 입니다.
 * 
 * 포탈위치 : 황혼과 여명 사이
 * 포탈설명 : 핑크빈 원정대 출구
 * 
 * 제작 : 주크블랙
 * 
 */

function enter(pi) {
    if (pi.getPlayer().getParty() == null) {
        pi.warp(270050000);
        return true;
    }
    if (pi.getPlayer().getParty().getExpedition() == null) {
        pi.warp(270050000);
        return true;
    }
    if (pi.getPlayer().getParty().getExpedition().getLeader() != pi.getPlayer().getId()) {
        pi.warp(270050000);
        pi.getParty().getExpedition().addDeadChar(pi.getPlayer().getId());
    } else {
        pi.allExpeditionWarp(270050000, true);
    }
    return true;
}