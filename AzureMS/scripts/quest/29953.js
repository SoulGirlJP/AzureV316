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
	    qm.sendNext("Cac gia lang van dang chim sau trong giac ngu," + qm.getPlayer().getName()+"!");
	} else if (status == 1) {
	    qm.sendNextPrev("Hay tap luyen them nua, 1 ngay nao do hay cuu ho...");
	} else if (status == 2) {
	    qm.sendNext("Mercedes...");
	} else if (status == 3) {
            qm.forceStartQuest();
            qm.changeJob(2310);
            qm.gainItem(1142337,1);
            qm.dispose();
	}
    }
}

function end(mode, type, selection) {
}