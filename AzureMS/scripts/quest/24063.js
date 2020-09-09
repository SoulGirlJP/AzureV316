var status = -1;

function start(mode, type, selection) {
	qm.sendNext("I will NEVER give up!");
	qm.gainExp(5000);
	qm.forceCompleteQuest();
	qm.dispose();
}
function end(mode, type, selection) {
	qm.dispose();
}
