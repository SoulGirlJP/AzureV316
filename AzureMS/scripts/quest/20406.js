var status = -1;

function start(mode, type, selection) {
	qm.sendNext("Maybe you should go back to the cave to see if anyone is there...");
	qm.forceStartQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.forceStartQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}