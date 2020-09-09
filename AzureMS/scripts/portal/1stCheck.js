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
    if (pi.getPlayer().getKeyValue("1stJobTrialStatus") == null) {
	pi.getPlayer().message("'유리' 에게 먼저 말을 걸어주세요.");
        return false;
    } else {
        pi.warp(219000000, "in03");
        return true;
    }
}