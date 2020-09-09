var status = -1;
function start(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		qm.dispose();
		return;
	}
	if (status == 0) {
		qm.sendYesNo("Do you want to go Kerning Square now?");
	} else if (status == 1) {
		qm.warp(103040000,0);
		qm.forceCompleteQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}