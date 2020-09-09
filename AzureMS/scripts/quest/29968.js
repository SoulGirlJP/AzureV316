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
			qm.sendNextPrev("Tot lam, ban da chung to duoc suc manh cua minh. Tuy nhien quang duong van con dai");
		} else if (status == 2) {
			qm.sendNextPrev("Hay chuan bi hoan thanh nhiem vu cua minh!");
		} else if (status == 3) {
			qm.sendNext("Doi toi mot chut nhe...");
		} else if (status == 4) {
				qm.changeJob(2410);
				qm.forceStartQuest();
				qm.gainItem(1142376,1);
				qm.dispose();
		}
    }
}

function end(mode, type, selection) {
}