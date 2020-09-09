var status = -1;

function start(mode, type, selection) {
	qm.sendNext("Go back to Erev to report about the situation.");
	qm.forceStartQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.forceStartQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}