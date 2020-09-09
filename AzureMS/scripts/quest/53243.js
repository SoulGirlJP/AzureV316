var status = -1;
function start(mode, type, selection) {
    qm.forceStartQuest();
    qm.dispose();
}
function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (qm.getQuestStatus(53243)!=1){
            if (status == 0) {
                qm.sendNext("Hay mang nhung thu nay den cho #p2111008#. Anh ta o gan day thoi #m261010000#.");
            } else if (status == 1) {
                qm.sendPlayerToNpc("(Co ve day se la nguoi minh can tim...)");
            } else if (status == 2) {
                qm.forceStartQuest();
                qm.dispose();
            } 
        }else if (qm.getQuestStatus(53243)==1){
            if (status == 0) {
                qm.sendNext("Ai, ai do??!.");
            } else if (status == 1) {
                qm.sendPlayerToNpc("Binh tinh nao. #p2111007# da bao toi den day");
            } else if (status == 2) {
                qm.sendNext("Phu`...tu khi nhan viec nay toi khong the ngu noi!");
            } else if (status == 3) {
                qm.sendPlayerToNpc("Trong anh co ve rat met moi!");
            } else if (status == 4) {
                qm.sendNext("Phai cong nhan rang de lam ra no rat kho. Va luon suc manh la khong the tin noi. Toi bat dau thay hoi so...No co the pha huy 1 vien da khong o Maple Word!");
            } else if (status == 5) {
                qm.sendPlayerToNpc("(1 co may co the pha huy hon da...Nghe that la lung va minh khong thich no cho lam)");
            } else if (status == 6) {
                qm.sendPlayerToNpc("Day la nhung nguyen lieu anh can.");
            } else if (status == 7) {
                qm.gainItem(4000357,-50);
                qm.gainItem(4000358,-50);
                qm.gainItem(4000364,-50);
                qm.sendNext("Ak phai roi cam on cau. Doi toi 1 chut thoi, se xong ngay");
            } else if (status == 8) {
                qm.sendNext("Ka me zo ko banh do banh che tra da xoi ngo................");
            } else if (status == 9) {
                qm.sendNext("hm. No day roi.");
                qm.gainItem(4033250,1);
            } else if (status == 10) {
                qm.sendNext("Hay mang no den Ariant #m260020620#, khach hang dang doi o phia trong #m552000074#",2111007);
            } else if (status == 11) {
                qm.forceCompleteQuest();
                qm.forceCompleteQuest(53244);
                qm.dispose();
            } 
        }
    }
}
