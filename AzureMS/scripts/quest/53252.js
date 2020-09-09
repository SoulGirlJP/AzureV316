var status = -1;
var t1 = new Date().getTime();
var t2 = new Date().getTime();
var time=0;

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.EnableUI(1);
            delay(2000);
            qm.sendDirectionInfo("Effect/DirectionNewPirate.img/effect/tuto/monAttack0");
            delay(2000);
            qm.sendDirectionInfo("Effect/DirectionNewPirate.img/effect/tuto/monAttack1");
            delay(5000);
            qm.sendNext("Hay ra khoi day ngay truoc khi qua muon!",9270092);
        } else if (status == 1) {
            qm.sendNext(qm.getPlayer().getName()+" di di...",9270092);
        } else if (status == 2) {
            qm.sendPlayerToNpc("Hay roi khoi day cung toi, Burke. Co gang len");
        } else if (status == 3) {
            qm.sendNext("Toi da sai lam. Toi da muon suc manh cua cau. Toi da muon giet cau! Chung...",9270092);
        } else if (status == 4) {
            qm.sendNext("Chung biet tat ca ve #bThe core#k. Toi da nham khi nghi rang toi co the co duoc suc manh tu chung! Xin loi "+qm.c.getPlayer().getName(),9270092);
        } else if (status == 5) {
            qm.sendPlayerToNpc("Vay tai sao anh de toi song, Burke?");
        } else if (status == 6) {
            qm.sendNext("Toi cam han suc manh do, khong phai anh!",9270092);
        } else if (status == 7) {
            qm.sendNext("Chay di, toi se o lai ngan chung lai, chung dinh hoi sinh...",9270092);
        } else if (status == 8) {
            qm.sendDirectionInfo("Effect/DirectionNewPirate.img/newPirate/balloonMsg2/25");
            delay(2000);
            qm.sendDirectionInfo("Effect/DirectionNewPirate.img/newPirate/balloonMsg2/23");
            qm.sendNext("#bThe core#k thuc su o #m240010300#, hay den do...",9270092);
        } else if (status == 9) {
            qm.sendDirectionInfo("Effect/DirectionNewPirate.img/newPirate/balloonMsg2/17");
            delay(3000);
            qm.removeNpc(552000074,9270092);
            qm.removeNpc(552000074,9270090);
            qm.forceCompleteQuest();
			cm.EnableUI(0);
            qm.warp(240000000);
            qm.dispose();
        } 
    }
}

function delay(time){
    t1 = new Date().getTime();
    t2 = new Date().getTime();
    t2+=time;
    while(true){
        if(t1<t2) t1= new Date().getTime();
        else return;
    }
}
