/*
 * 퓨어온라인 소스 스크립트 입니다.
 * 
 * 포탈위치 : 생명의동굴 : 미로방
 * 포탈설명 : 첫번째 미로방 입장
 * 
 * 제작 : 주크블랙
 * 
 */

function enter(pi) {
    if (pi.haveItem(4001087, 1)) {
        pi.warp(240050101);
        pi.getPlayer().message(5, "첫 번째 미로방의 수정의 힘에 의해 어딘가로 이동됩니다.");
        pi.gainItem(4001087, -1);
        return true;
    } else {
        pi.getPlayer().message(5, "미로방에 들어가는데 필요한 열쇠가 없습니다.");
        return false;
    }
}