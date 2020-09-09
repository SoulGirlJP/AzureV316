


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	스피릿매니저 에 의해 만들어 졌습니다.

	엔피시아이디 : 1012007

	엔피시 이름 : 프로드

	엔피시가 있는 맵 : 헤네시스 : 펫산책로 (100000202)

	엔피시 설명 : 조련사


*/

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        cm.warp(100000000,0);
        //cm.gainItem(4310218,1); Arcane coins
        //cm.gainItem(2431289,1); // Box not working
        cm.gainItem(4310241,1); // Chair Coin
        cm.gainItem(2450001,1); // exp coupon
        cm.gainItem(4310185, 500) // Purple orb
        cm.dispose();
        return;
    }
}
