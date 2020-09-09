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
            qm.sendNext(qm.getPlayer().getName()+", toi can giup do. Sap den han cuoi phai hoan thanh, khach hang cua toi dang thuc giuc!.");
        } else if (status == 1) {
            qm.sendPlayerToNpc("Binh tinh nao #p2111007#, anh dang lam gi the?.");
        } else if (status == 2) {
            qm.sendNext("Khong co gi lon lam dau. Nhung thuc su quanh day chang co ai ca. Cau se giup toi chu?.");
        } else if (status == 3) {
            qm.sendPlayerToNpc("Doi toi uong chut cafe va toi se suy nghi ve dieu nay...");
        } else if (status == 4) {
            qm.sendAcceptDecline("Neu cau giup toi. Toi se ke cho cau moi chuyen toi biet. Cau se giup toi chu?");
        } else if (status == 5) {
            qm.sendNext("Toi thay rang cau se khong the cuong lai su bat ngo nay dau.");
        } else if (status == 6) {
            qm.sendNext("2 ngay truoc, toi nhan duoc 1 yeu cau tu 1 to chuc bi mat.");
        } else if (status == 7) {
            qm.sendPlayerToNpc("Va anh khong muon ai biet ve dieu do?");
        } else if (status == 8) {
            qm.sendNext("Toi muon giu bi mat cho khach hang cua minh. Ho muon toi lam #z4033250##i4033250#. Va ho tra tien rat hau hinh =p~");
        } else if (status == 9) {
            qm.sendPlayerToNpc("(#z4033250##i4033250#, nghe cai ten mon do nay lam minh cam thay co gi do nguy hiem...)");
        } else if (status == 10) {
            qm.sendPlayerToNpc("Anh biet khach hang la ai khong?");
        } else if (status == 11) {
            qm.sendNext("Toi khong biet ho la ai va cung chang can biet dieu do lam gi. Ho giuc toi lam nhanh va se den trong vai ngay toi. Nguyen lieu can lam la 50 #z4000357##i4000357#, 50 #z4000358##i4000358# va 50 #z4000364##i4000364#.");
        } else if (status == 12) {
            qm.sendPlayerToNpc("(Vi khach nay co dieu gi do dac biet. minh se hoi anh ta sau khi hoan thanh moi duoc!)");
        } else if (status == 13) {
            qm.forceStartQuest();
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
            qm.sendNext("Cau da ve sao. Nao, hay nhanh dua chung cho toi!.");
        } else if (status == 1) {
//            qm.gainItem(4000357,-50);
//            qm.gainItem(4000358,-50);
//            qm.gainItem(4000364,-50);
            qm.sendPlayerToNpc("...??!");
        } else if (status == 2) {
            qm.forceCompleteQuest();
            qm.dispose();
        } 
    }
}
