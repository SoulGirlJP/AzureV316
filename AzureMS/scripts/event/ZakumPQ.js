//ZPQ maps, center area then 1-1 through 16-6 increasing gradually
var mapList = new Array(280010000, 280010010, 280010011, 280010020, 280010030, 280010031, 280010040, 280010041, 280010050, 280010060,
    280010070, 280010071, 280010080, 280010081, 280010090, 280010091, 280010100, 280010101, 280010110, 280010120, 280010130, 280010140,
    280010150, 280011000, 280011001, 280011002, 280011003, 280011004, 280011005, 280011006);

function init() {
    em.setProperty("state", "0");
}

function monsterValue(eim, mobId) {
    return 1;
}

function setup() {
    em.setProperty("state", "1");

    var eim = em.newInstance("ZakumPQ");

    for (var i = 0; i < mapList.length; i++) {
	eim.setInstanceMap(mapList[i]).resetFully();
    }
    eim.getMapFactory().getMap(280010000).shuffleReactors();

    eim.startEventTimer(1800000);
	
    return eim;
}

function playerEntry(eim, player) {
    var map = em.getMapFactory().getMap(280010000);
    player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {
}

function playerDead(eim, player) {
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow( 100, 280090000);
    em.setProperty("state", "0");
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
	case 280010000:
	case 280010010:
	case 280010011:
	case 280010020:
	case 280010030:
	case 280010031:
	case 280010040:
	case 280010041:
	case 280010050:
	case 280010060:
	case 280010070:
	case 280010071:
	case 280010080:
	case 280010081:
	case 280010090:
	case 280010091:
	case 280010100:
	case 280010101:
	case 280010110:
	case 280010120:
	case 280010130:
	case 280010140:
	case 280010150:
	case 280011000:
	case 280011001:
	case 280011002:
	case 280011003:
	case 280011004:
	case 280011005:
	case 280011006:
	    return;
    }
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
    	em.setProperty("state", "0");
    }
}

function playerDisconnected(eim, player) {
    return -4;
}

function leftParty(eim, player) {
    playerExit(eim, player);
}

function disbandParty(eim) {
    //boot whole party and end
    eim.disposeIfPlayerBelow(100, 280090000);
    em.setProperty("state", "0");
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    var map = eim.getMapFactory().getMap(280090000);
    player.changeMap(map, map.getPortal(0));

    if (eim.disposeIfPlayerBelow(0, 0)) {
    	em.setProperty("state", "0");
    }
}

//for offline players
function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
    if (eim.disposeIfPlayerBelow(0, 0)) {
    	em.setProperty("state", "0");
    }
}

function clearPQ(eim) {
    //ZPQ does nothing special with winners
    eim.disposeIfPlayerBelow(100, 280090000);
    em.setProperty("state", "0");
}

function allMonstersDead(eim) {}
function cancelSchedule() {}