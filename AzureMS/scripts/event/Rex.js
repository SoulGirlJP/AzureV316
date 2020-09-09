/**
 * @author: Eric
 * @script: Rex
 * @func: Resurrection of the Hoblin King
*/
var minPlayers = 2;

function init() {
	em.setProperty("state", "0");
	em.setProperty("leader", "true");
}

function setup(level, leaderid) {
	em.setProperty("state", "1");
	em.setProperty("leader", "true");
    var eim = em.newInstance("Rex" + leaderid);
    eim.setInstanceMap(921120005).resetPQ(level);
    eim.setInstanceMap(921120100).resetPQ(level);
    eim.setInstanceMap(921120200).resetPQ(level);
    eim.setInstanceMap(921120300).resetPQ(level);
	eim.startEventTimer(1800000); 
    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {
    eim.unregisterPlayer(player);
	if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("state", "0");
		em.setProperty("leader", "true");
	}
    return true;
}

function scheduledTimeout(eim) {
    end(eim);
}

function changedMap(eim, player, mapid) {
    if (mapid < 921120005 || mapid > 921120400) {
        eim.unregisterPlayer(player);
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
	player.changeMap(em.getChannelServer().getMapFactory().getMap(211000002), em.getChannelServer().getMapFactory().getMap(211000002).getPortal(0));
	if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("state", "0");
		em.setProperty("leader", "true");
	}
}

function end(eim) {
    eim.disposeIfPlayerBelow(100, 211000002);
	em.setProperty("state", "0");
	em.setProperty("leader", "true");
}

function clearPQ(eim) {
    end(eim);
}

function leftParty(eim, player) {
	end(eim);
}
function disbandParty(eim) {
	end(eim);
}

function playerDead(eim, player) {}
function cancelSchedule() {}
function allMonstersDead(eim) {}