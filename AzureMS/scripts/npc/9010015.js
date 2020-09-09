/*

히트맨온라인 쥬얼

*/

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
		var jessica = "#fn나눔고딕 Extrabold##d쥬얼크래프트#k 를 이용 해보시겠어요?\r\n";
		jessica += "#L0##b쥬얼크래프트 상점#k\r\n";
                jessica += "#L1##r쥬얼크래프트 합성#k";
		cm.sendSimple(jessica);

	} else if (status == 1) {
	if (selection == 0) {
	cm.dispose();
        cm.openNpc(9000131);

	} else if (selection == 1) {
	cm.dispose();
        cm.openNpc(9000132);
}
}
}
}