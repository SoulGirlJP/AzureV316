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
	    qm.sendNext("Oh, chang phai" + qm.getPlayer().getName()+" day sao!");
	} else if (status == 1) {
	    qm.sendNextPrev("Tot lam, ban da chung to duoc suc manh cua minh.");
	} else if (status == 2) {
	    qm.sendNext("Hay den gap #p1101000# va nhan phan thuong cho huyen thoai!");
	} else if (status == 3) {
            qm.forceStartQuest();
            qm.gainItem(1142336,1);
            qm.dispose();
	}
    }
}

function end(mode, type, selection) {
}