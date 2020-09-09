
var status = -1;

function start(mode, type, selection) {
	qm.forceStartQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	if (qm.canHold(1142375,1)) {
		qm.gainItem(1142375,1);
		qm.forceStartQuest();
		qm.forceCompleteQuest();
	}
	qm.dispose();
}