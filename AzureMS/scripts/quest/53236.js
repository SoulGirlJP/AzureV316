var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            qm.sendNext("Thoi gian kho luyen the nao "+qm.getPlayer().getName()+", thu vi chu?",1072008);
        } else if (status == 1) {
            qm.sendPlayerToNpc("Cam on Kyrin, toi thay minh cung manh len kha kha. Toi can 1 suc manh lon hon!");
        } else if (status == 2) {
            qm.sendNext("Toi nhin thay dieu do trong mat cau. Hay tim gap #p9270091#, anh ta se giup cau. Anh ay da sua giup toi khau sung, that tot bung ^^!",1072008);
        } else if (status == 3) {
            qm.sendNext("Toi se dua cau di gap anh ta. The nao, di luon nhe!",1072008);
        } else if (status == 4) {
            qm.sendPlayerToNpc("Oh, vay thi tot qua. Cam on co Kyrin.");
        } else if (status == 5) {
            qm.forceStartQuest();
            qm.warp(552000071,1);
            qm.dispose();
        } 
    }
}

function end(mode, type, selection) {
	qm.forceCompleteQuest();
	qm.dispose();
}