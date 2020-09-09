


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	포탈이 있는 맵 : 전문기술마을

	포탈 설명 : 전문기술마을 나가기


*/


function enter(pi) {
    pi.playPortalSE();
    try {
        pi.warp(pi.getSavedMapId(), "profession");
    } catch (e) {
        pi.warp(pi.getSavedMapId());
    }
    return true;
}
