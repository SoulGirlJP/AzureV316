


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	포탈이 있는 맵 : 103050370

	포탈 설명 : 수련장3 나가는 포탈


*/

importPackage(java.lang)

function enter(pi) {
    if (pi.haveItem(4031013, 20)) {
        pi.getPlayer().setKeyValue("2ndJobTrialComplete", "1");
        pi.removeAll(4031013);
        pi.warp(100000000);
        var startTime = pi.getPlayer().getKeyValue("2ndTrialStartTime");
        pi.getPlayer().setKeyValue2("2ndJobTrialCompleteTime2", ((System.currentTimeMillis() / 1000) - startTime)+"");
        return true;
    } else {
        pi.getPlayer().message(5, "검은 구슬을 아직 다 모으지 못해 나갈 수 없다.");
        return false;
    }
}
