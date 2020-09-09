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
	    qm.sendNext(qm.getPlayer().getName()+", cau co ve da manh len rat nhieu!");
	} else if (status == 1) {
	    qm.sendNextPrev("Lan nay hi vong cau se danh bai han, Black Mage. Toi tin vao cau...");
	} else if (status == 2) {
	    qm.sendNext("...Mercedes...");
	} else if (status == 3) {
            qm.forceStartQuest();
            qm.changeJob(2312);
            qm.gainItem(1142339,1);
            qm.dispose();
	}
    }
}

function end(mode, type, selection) {
}