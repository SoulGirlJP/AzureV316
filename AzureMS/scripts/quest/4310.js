var status = -1;
function start(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		qm.dispose();
		return;
	}
	if (status == 0) {
		qm.forceCompleteQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}