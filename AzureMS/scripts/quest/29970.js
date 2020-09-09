
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
                qm.sendNext("Phantom! La nguoi sao!");
            } else if (status == 1) {
                qm.sendNextPrev("Trong nguoi co ve kha manh day.");
            } else if (status == 2) {
                qm.sendNextPrev("Nhung muon vao trong hay buoc qua xac ta truoc da");
            } else if (status == 3) {
                qm.sendSimple("Come here baby. hahahahaha");
            } else if (status == 4) {
                qm.forceStartQuest();
                qm.dispose();
            } 
        }
}

function end(mode, type, selection) {
}