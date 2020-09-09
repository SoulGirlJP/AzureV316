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
           if (cm.getPlayer().getMapId() == 326090310) {
		var jessica = "#fn나눔고딕 Extrabold##r따보#k 의 부탁을 포기하고 #b퀘스트의 전당#k 으로 가겠나..?\r\n";
		jessica += "#L0##r[포기]#k #b퀘스트의 전당 으로 이동할게요.#k\r\n";
		cm.sendSimple(jessica);
           } else {
	   cm.sendOk("#fn나눔고딕 Extrabold#이거.. 참 고민이 많군..");
	   cm.dispose();
           }
	} else if (status == 1) {
	if (selection == 0) {
	cm.killAllMob();
        cm.removeAll(4032335);
        cm.removeAll(4033012);
        cm.warp(100030301,0);
	cm.dispose();
}
}
}
}