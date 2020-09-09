var status = -1;

function start(mode, type, selection) {
	qm.sendNext("I don't know.. maybe you can go #bHunt the Zombies#k for a clue. Maybe an item or something will lead you to it.");
	qm.forceStartQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.forceStartQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}