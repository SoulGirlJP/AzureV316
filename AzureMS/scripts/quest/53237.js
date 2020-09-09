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
            qm.sendNext("Cau da den roi day ak "+qm.getPlayer().getName()+", Kyrin co noi voi toi ve cau.",9270091);
        } else if (status == 1) {
            qm.sendPlayerToNpc("Kyrin noi rang ong la mot nguoi lam sung rat gioi, va chuyen loi cam on ve khau sung voi ong.");
        } else if (status == 2) {
            qm.sendNext("Oh khong co gi. Toi la tho lam sung gioi nhat o day. Neu cau muon 1 khau sung tot, toi se lam giup cau. Cau hay tim cho toi 1 vai thu truoc nhe?",9270091);
        } else if (status == 3) {
            qm.sendNext("Nguoi ta noi rang muon tim vien pha le mau xanh thi phai den #m552000072# ma toi lai khong co thoi gian de den 1 noi toi tam am thap nhu the. ",9270091);
        } else if (status == 4) {
            qm.sendPlayerToNpc("Khong van de gi. Hay dua toi den do");
        } else if (status == 5) {
            qm.forceStartQuest();
            qm.warp(552000072,1);
            //qm.gainItem(4033251,30);
            qm.dispose();
        } 
    }
}
function end(mode, type, selection) {
	qm.forceCompleteQuest();
	qm.dispose();
}
