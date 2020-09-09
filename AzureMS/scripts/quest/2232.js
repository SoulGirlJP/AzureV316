var status = -1;

function start(mode, type, selection) {
	if (qm.getPlayer().getJunior1() > 0) {
		qm.forceCompleteQuest();
		qm.gainExp(3000);
		qm.sendNext("Good job!");
	} else {
		qm.sendNext("Please, find a junior!");
	}
	qm.dispose();
}
function end(mode, type, selection) {
	if (qm.getPlayer().getJunior1() > 0) {
		qm.forceCompleteQuest();
		qm.gainExp(3000);
		qm.sendNext("Good job!");
	} else {
		qm.sendNext("Please, find a junior!");
	}
	qm.dispose();
}
