function init() {
    em.setProperty("state", "0");
}

function monsterValue(eim, mobId) {
    return 1;
}

function setup(level, leaderid) {
    em.setProperty("state", "1");

    var eim = em.newInstance("KerningPQ" + leaderid);
    eim.setInstanceMap(910340100).resetPQ(level);
    eim.setInstanceMap(910340200).resetPQ(level);
    eim.setInstanceMap(910340300).resetPQ(level);
    eim.setInstanceMap(910340400).resetPQ(level);
    var map = eim.setInstanceMap(910340500);
	map.resetPQ(level);
		var mob = em.getMonster(9300003);
		eim.registerMonster(mob);
		mob.changeLevel(level);

		map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(0, -430));
		eim.setInstanceMap(910340600).resetPQ(level);
    
    eim.startEventTimer(1200000);

    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
    player.tryPartyQuest(1201);
}

function playerDead(eim, player) {
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
	case 910340100: // 1st Stage
	case 910340200: // 2nd Stage
	case 910340300: // 3rd Stage
	case 910340400: // 4th Stage
	case 910340500: // 5th Stage
	case 910340600: // Bonus stage
	    return; // Everything is fine
    }
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(2, 910340000)) {
	em.setProperty("state", "0");
    }
}

function playerRevive(eim, player) {
}

function playerDisconnected(eim, player) {
    return -2;
}

function leftParty(eim, player) {			
    // If only 2 players are left, uncompletable
    if (eim.disposeIfPlayerBelow(2, 910340000)) {
	em.setProperty("state", "0");
    } else {
	playerExit(eim, player);
    }
}

function disbandParty(eim) {
    // Boot whole party and end
    eim.disposeIfPlayerBelow(100, 910340000);

    em.setProperty("state", "0");
}


function scheduledTimeout(eim) {
    clearPQ(eim);
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    var exit = eim.getMapFactory().getMap(910340000);
    player.changeMap(exit, exit.getPortal(0));
    if (eim.disposeIfPlayerBelow(2, 910340000)) {
	em.setProperty("state", "0");
    }
}

function clearPQ(eim) {
    // KPQ does nothing special with winners
    eim.disposeIfPlayerBelow(100, 910340000);

    em.setProperty("state", "0");
}

function allMonstersDead(eim) {
}

function cancelSchedule() {
}