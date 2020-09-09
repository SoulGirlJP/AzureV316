


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	루돌이 에 의해 만들어 졌습니다.

	엔피시아이디 : 2042003

	엔피시 이름 : 조수 레드

	엔피시가 있는 맵 : 몬스터 카니발 : 카니발 필드1&lt;대기실> (980000100)

	엔피시 설명 : 몬스터 카니발


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
        cm.sendOk("준비중");
        cm.dispose();
        return;
    }
}
