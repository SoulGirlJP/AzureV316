function init() {
    // 0 = Not started, 1 = started, 2 = first head defeated, 3 = second head defeated
    em.setProperty("state", "0");
	em.setProperty("leader", "true");
}

function setup(eim, leaderid) {
    em.setProperty("state", "1");
    em.setProperty("preheadCheck", "0");
	em.setProperty("leader", "true");

    var eim = em.newInstance("HorntailBattle");

    eim.setInstanceMap(240060200).resetFully();

    eim.startEventTimer(4500000); //now changed to 1 hour 15 mins
    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapFactory().getMap(240060200);
    player.changeMap(map, map.getPortal(0));
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
	case 240060200:
	    return;
    }
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
	em.setProperty("state", "0");
		em.setProperty("leader", "true");
    }
}

function playerDisconnected(eim, player) {
    return 0;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 240050400);
    em.setProperty("state", "0");
		em.setProperty("leader", "true");
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
	em.setProperty("state", "0");
		em.setProperty("leader", "true");
    }
}

function monsterValue(eim, mobId) {
    return 1;
}

function allMonstersDead(eim) {
}

function playerRevive(eim, player) {
    return false;
}

function clearPQ(eim) {}
function leftParty (eim, player) {}
function disbandParty (eim) {}
function playerDead(eim, player) {}
function cancelSchedule() {}