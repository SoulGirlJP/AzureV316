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
            qm.sendNext("Troi dat. Nhin nhung vien pha le nay. Chung that dep va lap anh",9270091);
        } else if (status == 1) {
            qm.sendPlayerToNpc("Co gi dau. Chuyen don gian y ma. Day, hay cam lay chung.");
        } else if (status == 2) {
            qm.gainItem(4033251,-30);
            qm.sendNext("Cam on cau, toi da lam xong sung cho cau roi",9270091);
        } else if (status == 3) {
            qm.sendNext("Nhin nay, khau sung dac biet toi da lam cho cau #z1492140##i1492140#, hi vong cau se thich no!",9270091);
        } else if (status == 4) {
            qm.sendPlayerToNpc("No rat hop voi toi. Cam on ong, tam biet!");
        } else if (status == 5) {
            qm.forceStartQuest();
            qm.gainItem(1492140,1);
            qm.changeJob(570);
            qm.dispose();
        } 
    }
}
function end(mode, type, selection) {
	qm.forceCompleteQuest();
	qm.dispose();
}
