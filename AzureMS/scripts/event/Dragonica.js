var minPlayers = 3;

function init() {
em.setProperty("state", "0");
	em.setProperty("leader", "true");
}

function setup(level, leaderid) {
em.setProperty("state", "1");
	em.setProperty("leader", "true");
    var eim = em.newInstance("Dragonica" + leaderid);

        eim.setInstanceMap(240080600).resetPQ(level);
	eim.setInstanceMap(240080700);
	var map3 = eim.setInstanceMap(240080800);
	map3.resetPQ(level);
    var mob3 = em.getMonster(8300007);
	mob3.changeLevel(level);
    eim.registerMonster(mob3);
    map3.spawnMonsterOnGroundBelow(mob3, new java.awt.Point(700, -10));

    eim.startEventTimer(1200000); //20 min
    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {
    var map = eim.getMapInstance(eim.getMapInstance(0).getAllMonstersThreadsafe().size() == 0 ? 2 : 0);
    player.addHP(50);
    player.changeMap(map, map.getPortal(0));
    return true;
}

function scheduledTimeout(eim) {
    end(eim);
}

function changedMap(eim, player, mapid) {
    if (mapid != 240080600 && mapid != 240080700 && mapid != 240080800 && mapid != 240080040 && mapid != 240080050) {
	eim.unregisterPlayer(player);
	var map = em.getChannelServer().getMapFactory().getMap(240080050);
	player.changeMap(map, map.getPortal(0));

	if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("state", "0");
		em.setProperty("leader", "true");
	}
    }
}

function playerDisconnected(eim, player) {
    return 0;
}

function monsterValue(eim, mobId) {
    return 1;
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
	em.setProperty("state", "0");
		em.setProperty("leader", "true");
	}
}

function end(eim) {
    eim.disposeIfPlayerBelow(100, 240080050);
	em.setProperty("state", "0");
		em.setProperty("leader", "true");
}

function clearPQ(eim) {
    end(eim);
}

function allMonstersDead(eim) {
	if (eim.getMapInstance(2).getAllMonstersThreadsafe().size() == 0) {
		eim.getMapInstance(2).spawnNpc(2085003, new java.awt.Point(700, -10));
		eim.broadcastPlayerMsg(6, "Dragonica has been beaten! Have the leader go in the Portal to finish!");
	}
}

function leftParty (eim, player) {
    // If only 2 players are left, uncompletable:
	end(eim);
}
function disbandParty (eim) {
	end(eim);
}
function playerDead(eim, player) {}
function cancelSchedule() {}