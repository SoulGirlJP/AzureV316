
var status = -1;

function start(mode, type, selection) {
    if (qm.getQuestStatus(25111) == 2 && qm.getJob() == 2410){
        if (mode == -1) {
            qm.dispose();
        } else {
            if (mode == 1)
                status++;
            else
                status--;

            if (status == 0) {
                qm.sendNext("Lai la "+qm.getPlayer().getName()+" do ak?");
            } else if (status == 1) {
                qm.sendNextPrev("Ban da trai qua 1 doan duong dai de den day. Sao, thu vi chu?");
            } else if (status == 2) {
                qm.sendNextPrev("Toi biet ban den day vi suc manh. No nam ben trong ban, gio toi se danh thuc no len 1 tam cao moi...");
            } else if (status == 3) {
                qm.sendSimple("Ban thuc su manh me. Hay su dung suc manh de giup do ban be...va hay cuu co ay...");
            } else if (status == 4) {
                qm.forceStartQuest();
                //qm.changeJob(2411);
                qm.dispose();
            } 
        }
    }
}

function end(mode, type, selection) {
}