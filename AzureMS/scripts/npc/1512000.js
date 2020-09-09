


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	아룽 에 의해 만들어 졌습니다.

	엔피시아이디 : 1512000

	엔피시 이름 : 아기 펭귄

	엔피시가 있는 맵 : 메이플로드 : 단풍나무 언덕 (10000)

	엔피시 설명 : MISSINGNO


*/

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    cm.dispose(); // TODO: REMOVE => WOLLY ADDED THIS
    return; // TODO: REMOVE => WOLLY ADDED THIS
    
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
        cm.sendOk("");
    }
}

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}
/*
function action(mode, type, selection) {
    cm.dispose(); // TODO: FIX
    return;

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
        cm.sendOk("#fn바탕#마인드 온라인 기본 안내\r\n\r\n(TP포인트는 몬스터 1마리당 1~50포인트 사이로 랜덤으로 쌓입니다.)\r\n\r\nTP포인트가 400포인트가 쌓이면 자동으로 빨간단추1개로 변환됩니다.\r\n\r\n후원 2배 이벤트 중 카페 공지 필독.");
        cm.dispose();
        return;
    }
        cm.dispose();
        return;
}*/
