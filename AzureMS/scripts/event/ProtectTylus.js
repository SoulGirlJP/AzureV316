/**
 * @author: Eric
 * @script: ProtectTylus
 * @func: El Nath PQ
*/

function init() {
    em.setProperty("state", "0");
	em.setProperty("summoned", "0");
	em.setProperty("finished", "0");
}

function setup(level, leaderid) {
	em.setProperty("state", "1");
    var eim = em.newInstance("ProtectTylus" + leaderid);
	var map = eim.setInstanceMap(921100300);
    map.resetPQ(level);
	map.killAllMonsters(false);
	eim.startEventTimer(600000);
    return eim;
}

function playerEntry(eim, player) {
	var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
	if (em.getProperty("summoned").equals("0")) {
		var mob = em.getMonster(9300093);
		var stats = new Packages.server.life.OverrideMonsterStats();
		stats.setOHp(player.getAveragePartyLevel() * 100);
		stats.setOMp(120); // doesn't even att so fuck if i care
		mob.changeLevel(player.getAveragePartyLevel());
		mob.setOverrideStats(stats);
		eim.registerMonster(mob);
		map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(-358, -86));
		em.setProperty("summoned", "1");
		eim.schedule("finish", 1 * 60 * 1000); // 7 minutes (meaning 3 minutes remaining)
	}
}

function finish(eim) {
	eim.broadcastPlayerMsg(6, "You have almost reached the destination. Please exit by the portal within the time left.");
	if (em.getProperty("finished").equals("0")) {
		em.setProperty("finished", "1");
	}
}

function changedMap(eim, player, mapid) {
    if (mapid != 921100300) {
		playerExit(eim, player);
    }
}

function playerDisconnected(eim, player) {
    return 0;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 921100301);
    em.setProperty("state", "0");
	em.setProperty("summoned", "0");
	em.setProperty("finished", "0");
}

function monsterValue(eim, mobId) {
    if (mobId == 9300093) {
		allMonstersDead(eim);
    }
    return 1;
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("state", "0");
		em.setProperty("summoned", "0");
		em.setProperty("finished", "0");
    }
}

function allMonstersDead(eim) {
    eim.disposeIfPlayerBelow(100, 211000001);
    em.setProperty("state", "0");
	em.setProperty("summoned", "0");
	em.setProperty("finished", "0");
}

function playerRevive(eim, player) {
    return false;
}

function clearPQ(eim) {}
function leftParty (eim, player) {}
function disbandParty (eim) {}
function playerDead(eim, player) {}
function cancelSchedule() {}