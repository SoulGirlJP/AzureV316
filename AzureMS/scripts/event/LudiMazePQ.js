/**
	Ludibrium PQ (101st Eos Tower)
*/

var minPlayers = 2;

function init() {
    em.setProperty("state", "0");
}

function monsterValue(eim, mobId) {
    return 1;
}

function setup() {
    em.setProperty("state", "1");

    var eim = em.newInstance("LudiPQ");
    for (var i = 809050000; i < 809050015; i++) {
	var map = eim.setInstanceMap(i);
	map.resetFully();
	map.shuffleReactors();
    }

    eim.startEventTimer(15 * 60000);

    return eim;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 809050017);

    em.setProperty("state", "0");
}

function changedMap(eim, player, mapid) {
    if (mapid >= 809050000 && mapid <= 809050015) {
	    return;
    }
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
	em.setProperty("state", "0");
    }
}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
    player.tryPartyQuest(1202);
}

function playerRevive(eim, player) {
}

function playerDisconnected(eim, player) {
    return -3;
}

function leftParty(eim, player) {			
    // If only 2 players are left, uncompletable
    if (eim.disposeIfPlayerBelow(minPlayers, 809050017)) {
	em.setProperty("state", "0");
    } else {
	playerExit(eim, player);
    }
}

function disbandParty(eim) {
    // Boot whole party and end
    eim.disposeIfPlayerBelow(100, 809050017);

    em.setProperty("state", "0");
}

function playerExit(eim, player) {
    var map = em.getMapFactory().getMap(809050017);

    eim.unregisterPlayer(player);
    player.changeMap(map, map.getPortal(0));
}

// For offline players
function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
}

function clearPQ(eim) {
    eim.disposeIfPlayerBelow(100, 809050016);

    em.setProperty("state", "0");
}

function finish(eim) {
    eim.disposeIfPlayerBelow(100, 809050016);

    em.setProperty("state", "0");
}

function timeOut(eim) {
    eim.disposeIfPlayerBelow(100, 809050017);

    em.setProperty("state", "0");
}

function cancelSchedule() {}
function playerDead() {}
function allMonstersDead(eim) {}