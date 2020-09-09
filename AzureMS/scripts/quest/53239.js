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
            qm.sendNext("La cau do sao "+qm.getPlayer().getName()+", may vien pha le cau dua toi dung tot lam'.");
        } else if (status == 1) {
            qm.sendPlayerToNpc("Toi can 1 khau sung moi. Toi da manh len rat nhieu.");
        } else if (status == 2) {
            qm.sendNext("Cau kha that day. Duoc thoi, hay tim cho toi #z4033252##i4033252# va toi se giup cau.",9270091);
        } else if (status == 3) {
            qm.sendNext("Hay den #m552000073# noi nhung than thoai duoc viet len...",9270091);
        } else if (status == 4) {
            qm.sendPlayerToNpc("(Oh men, lan nay khong biet lai gi nua day. Chep, danh vay)");
        } else if (status == 5) {
            qm.forceStartQuest();
            qm.warp(552000073,1);
            //qm.gainItem(4033252,30);
            qm.dispose();
        } 
    }
}
function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            qm.sendNext("Cau da ve sao. Dua toi nhung vien pha le nao.");
        } else if (status == 1) {
            qm.gainItem(4033252,-30);
            qm.sendPlayerToNpc("Ong lam xong sung cho toi roi chu?");
        } else if (status == 2) {
            qm.sendNext("Dung voi vang cau be, cung sap xong roi. Doi chut di!",9270091);
        } else if (status == 3) {
            qm.sendPlayerToNpc("...");
        } else if (status == 4) {
            qm.sendNext("No day roi #z1492142##i1492142#, that tuyet phai khong haha. Day, toi tang cau day.");
        } else if (status == 5) {
            qm.forceCompleteQuest();
            qm.changeJob(571);
            qm.gainItem(1492142,1);
            qm.dispose();
        } 
    }
}
