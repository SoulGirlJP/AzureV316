
var status = -1;

function start(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
            qm.forceCompleteQuest(3181);
            qm.forceCompleteQuest(3182);
	    qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        qm.dispose();
    } else {
        status++;
        if (status == 0) {
            qm.forceCompleteQuest(3181);
            qm.forceCompleteQuest(3182);
	    qm.dispose();
        }
    }
}