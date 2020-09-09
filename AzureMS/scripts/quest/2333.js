var status = -1;

function start(mode, type, selection) {
	qm.getMap().killAllMonsters(true);
	qm.spawnMonster(3300008,1);
	qm.sendNext("Please, eliminate the Prime Minister!!!");
	qm.forceCompleteQuest(2332);
	qm.forceStartQuest();
	qm.dispose();
}

function end(mode, type, selection) {
		qm.gainItem(4032386,1);
		qm.gainItem(4032387,1);
		qm.forceCompleteQuest();
		qm.dispose();
}
	