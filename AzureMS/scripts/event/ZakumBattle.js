function init() {
em.setProperty("state", "0");
	em.setProperty("leader", "true");
}

function setup(eim, leaderid) {
	em.setProperty("state", "1");
	em.setProperty("leader", "true");
    var eim = em.newInstance("ZakumBattle" + leaderid);
    
    eim.setProperty("zakSummoned", "0");
    eim.setInstanceMap(280030000).resetFully();
//    eim.schedule("checkStart", 1200000); // 20 min
    eim.startEventTimer(3000000); //50 minutes
    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {
    return false;
}

function changedMap(eim, player, mapid) {
    if (mapid != 280030000) {
	eim.unregisterPlayer(player);

	if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("leader", "true");
		em.setProperty("state", "0");
	}
    }
}

function playerDisconnected(eim, player) {
    return 0;
}

function scheduledTimeout(eim) {
    end(eim);
}

function monsterValue(eim, mobId) {
    return 1;
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    	if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("leader", "true");
		em.setProperty("state", "0");
	}
}

function end(eim) {
    eim.disposeIfPlayerBelow(100, 211042300);
	em.setProperty("state", "0");
		em.setProperty("leader", "true");
    em.setProperty("zakSummoned", "0");
}

function clearPQ(eim) {
    end(eim);
}

function allMonstersDead(eim) {
	if (em.getProperty("state").equals("1")) {
		em.setProperty("state", "2");
	} else if (em.getProperty("state").equals("2")) {
		em.setProperty("state", "3");
	}
}

function leftParty (eim, player) {}
function disbandParty (eim) {}
function playerDead(eim, player) {}
function cancelSchedule() {}