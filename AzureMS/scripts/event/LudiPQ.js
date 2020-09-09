/**
 * @author : Eric
 * @script: LudiPQ
 * @func: Ludibrium PQ (101st Eos Tower)
*/

var stg6_combo = Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
var minPlayers = 3;

function init() {
    em.setProperty("state", "0");
}

function setup(level, leaderid) {
    em.setProperty("state", "1");
	em.setProperty("portal1", "1");
	em.setProperty("portal2", "1");
	em.setProperty("portal3", "1");
	em.setProperty("portal4", "1");
	em.setProperty("portal5", "1");
	em.setProperty("portal6", "1");
	em.setProperty("portal7", "1");
	em.setProperty("portal8", "1");
	em.setProperty("portal9", "1");
	em.setProperty("portal10", "1");
    var eim = em.newInstance("LudiPQ");
    eim.setProperty("stage2", "0");
    var map = eim.setInstanceMap(922010100);
    map.resetPQ(level);
    map = eim.setInstanceMap(922010400);
    map.resetPQ(level);
    eim.setInstanceMap(922010401).resetPQ(level);
    eim.setInstanceMap(922010402).resetPQ(level);
    eim.setInstanceMap(922010403).resetPQ(level);
    eim.setInstanceMap(922010404).resetPQ(level);
    eim.setInstanceMap(922010405).resetPQ(level);
    map = eim.setInstanceMap(922010700);
	map.resetPQ(level);
	map.getPortal("next00").setScriptName("lpq7");
	eim.setInstanceMap(922010800).getPortal("next00").setScriptName("lpq8");
    eim.setInstanceMap(922010900).resetPQ(level);
	// TODO: Remastered portal calculations.
	for (var b = 0; b < stg6_combo.length; b++) { //stage6_001
		for (var y = 0; y < 3; y++) { //stage number
			em.setProperty("stage6_" + stg6_combo[b] + "" + y + "", "0");
		}
	}
	for (var b = 0; b < stg6_combo.length; b++) { //stage6_001	
		var found = false;
		while (!found) {
			for (var x = 0; x < 3; x++) {
				if (!found) {
					var founded = false;
					for (var z = 0; z < 3; z++) { //check if any other stages have this value set already.
						if (em.getProperty("stage6_" + stg6_combo[b] + "" + (z) + "") == null) {
							em.setProperty("stage6_" + stg6_combo[b] + "" + (z) + "", "0");
						} else if (em.getProperty("stage6_" + stg6_combo[b] + "" + (z) + "").equals("1")) {
							founded = true;
							break;
						}
					}
					if (!founded && java.lang.Math.random() < 0.33) {
						em.setProperty("stage6_" + stg6_combo[b] + "" + (x) + "", "1");
						found = true;
						break;
					}
				}
			}
		}
	}
    eim.startEventTimer(1200000); //20 mins
    return eim;
}

function monsterValue(eim, mobId) {
    return 1;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 922010000);
    em.setProperty("state", "0");
	em.setProperty("portal1", "1");
	em.setProperty("portal2", "1");
	em.setProperty("portal3", "1");
	em.setProperty("portal4", "1");
	em.setProperty("portal5", "1");
	em.setProperty("portal6", "1");
	em.setProperty("portal7", "1");
	em.setProperty("portal8", "1");
	em.setProperty("portal9", "1");
	em.setProperty("portal10", "1");
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
		case 922010100: // Stage 1
		case 922010200: // Stage 2
		case 922010201: // Stage 2 - Tower's Trap'
		case 922010300: // Stage 3
		case 922010400: //stage 4
		case 922010401: //darkness in stage 4
		case 922010402: //darkness in stage 4
		case 922010403: //darkness in stage 4
		case 922010404: //darkness in stage 4
		case 922010405: //darkness in stage 4
		case 922010500: //stage 5
		case 922010501: //tower's maze in stage 5
		case 922010502: //tower's maze in stage 5
		case 922010503: //tower's maze in stage 5
		case 922010504: //tower's maze in stage 5
		case 922010505: //tower's maze in stage 5
		case 922010506: //tower's maze in stage 5
		case 922010600:
		case 922010700: //stage 7
		case 922010800:
		case 922010900: //crack on the wall
		case 922011000: //bonus
			return;
    }
    eim.unregisterPlayer(player);
    if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("state", "0");
		em.setProperty("portal1", "1");
		em.setProperty("portal2", "1");
		em.setProperty("portal3", "1");
		em.setProperty("portal4", "1");
		em.setProperty("portal5", "1");
		em.setProperty("portal6", "1");
		em.setProperty("portal7", "1");
		em.setProperty("portal8", "1");
		em.setProperty("portal9", "1");
		em.setProperty("portal10", "1");
    }
}

function playerEntry(eim, player) {
    var map = em.getMapFactory().getMap(922010100);
    player.changeMap(map, map.getPortal(0));
    player.tryPartyQuest(1202);
}

function playerDisconnected(eim, player) {
	return -3;
}

function leftParty(eim, player) {			
    if (eim.disposeIfPlayerBelow(minPlayers, 922010000)) {
		em.setProperty("state", "0");
		em.setProperty("portal1", "1");
		em.setProperty("portal2", "1");
		em.setProperty("portal3", "1");
		em.setProperty("portal4", "1");
		em.setProperty("portal5", "1");
		em.setProperty("portal6", "1");
		em.setProperty("portal7", "1");
		em.setProperty("portal8", "1");
		em.setProperty("portal9", "1");
		em.setProperty("portal10", "1");
    } else {
		playerExit(eim, player);
    }
}

function disbandParty(eim) {
    eim.disposeIfPlayerBelow(100, 922010000);
    em.setProperty("state", "0");
	em.setProperty("portal1", "1");
	em.setProperty("portal2", "1");
	em.setProperty("portal3", "1");
	em.setProperty("portal4", "1");
	em.setProperty("portal5", "1");
	em.setProperty("portal6", "1");
	em.setProperty("portal7", "1");
	em.setProperty("portal8", "1");
	em.setProperty("portal9", "1");
	em.setProperty("portal10", "1");
}

function playerExit(eim, player) {
    var map = em.getMapFactory().getMap(922010000);
    eim.unregisterPlayer(player);
    player.changeMap(map, map.getPortal(0));
}

function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
}

function clearPQ(eim) {
    eim.disposeIfPlayerBelow(100, 922010000);
    em.setProperty("state", "0");
	em.setProperty("portal1", "1");
	em.setProperty("portal2", "1");
	em.setProperty("portal3", "1");
	em.setProperty("portal4", "1");
	em.setProperty("portal5", "1");
	em.setProperty("portal6", "1");
	em.setProperty("portal7", "1");
	em.setProperty("portal8", "1");
	em.setProperty("portal9", "1");
	em.setProperty("portal10", "1");
}

function finish(eim) {
    eim.disposeIfPlayerBelow(100, 922010000);
    em.setProperty("state", "0");
	em.setProperty("portal1", "1");
	em.setProperty("portal2", "1");
	em.setProperty("portal3", "1");
	em.setProperty("portal4", "1");
	em.setProperty("portal5", "1");
	em.setProperty("portal6", "1");
	em.setProperty("portal7", "1");
	em.setProperty("portal8", "1");
	em.setProperty("portal9", "1");
	em.setProperty("portal10", "1");
}

function timeOut(eim) {
    eim.disposeIfPlayerBelow(100, 922010000);
    em.setProperty("state", "0");
	em.setProperty("portal1", "1");
	em.setProperty("portal2", "1");
	em.setProperty("portal3", "1");
	em.setProperty("portal4", "1");
	em.setProperty("portal5", "1");
	em.setProperty("portal6", "1");
	em.setProperty("portal7", "1");
	em.setProperty("portal8", "1");
	em.setProperty("portal9", "1");
	em.setProperty("portal10", "1");
}

function cancelSchedule() {}
function playerDead() {}
function allMonstersDead(eim) {}
function playerRevive(eim, player) {}