/**
 * @author: Eric
 * @event: Juliet PQ (We aren't going to support Romeo*)
 * @func: Romeo and Juliet GMS-like PQ
*/
var minPlayers = 4;

function init() {
	em.setProperty("state", "0");
	em.setProperty("leader", "true");
	em.setProperty("summoned", "0");
}

function setup(level, leaderid) {
	em.getProperties().clear();
	em.setProperty("state", "1");
	em.setProperty("summoned", "0");
	em.setProperty("leader", "true");
    var eim = em.newInstance("Juliet" + leaderid);
	em.setProperty("stage", "0");
	em.setProperty("stage1", "0");
	em.setProperty("stage3", "0");
	em.setProperty("stage4", "0");
	em.setProperty("stage5", "0");
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
	var y;
	for (y = 0; y < 4; y++) { //stage number
		em.setProperty("stage6_" + y, "0");
		for (var b = 0; b < 10; b++) {
			for (var c = 0; c < 4; c++) {
				//em.broadcastYellowMsg("stage6_" + y + "_" + b + "_" + c + " = 0");
				em.setProperty("stage6_ " + y + "_" + b + "_" + c + "", "0");
			}
		}
	}
	var i;
	for (y = 0; y < 4; y++) { //stage number
		for (i = 0; i < 10; i++) {		
			var found = false;
			while (!found) {
				for (var x = 0; x < 4; x++) {
					if (!found) {
						var founded = false;
						for (var z = 0; z < 4; z++) { //check if any other stages have this value set already.
							//em.broadcastYellowMsg("stage6_" + z + "_" + i + "_" + x + " check");
							if (em.getProperty("stage6_" + z + "_" + i + "_" + x + "") == null) {
								em.setProperty("stage6_" + z + "_" + i + "_" + x + "", "0");
							} else if (em.getProperty("stage6_" + z + "_" + i + "_" + x + "").equals("1")) {
								founded = true;
								break;
							}
						}
						if (!founded && java.lang.Math.random() < 0.25) {
							//em.broadcastYellowMsg("stage6_" + z + "_" + i + "_" + x + " = 1");
							em.setProperty("stage6_" + y + "_" + i + "_" + x + "", "1");
							found = true;
							break;
						}
					}
				}
			}
			//BUT, stage6_0_0_0 set, then stage6_1_0_0 also not set!
		}
	}
	em.setProperty("stage7", "0"); //whether they were killed or not.
    eim.setInstanceMap(926110000).resetPQ(level);
    eim.setInstanceMap(926110001).resetPQ(level);
	eim.setInstanceMap(926110100).resetPQ(level);
	eim.setInstanceMap(926110200).resetPQ(level);
    eim.setInstanceMap(926110201).resetPQ(level);
    eim.setInstanceMap(926110202).resetPQ(level);
	eim.setInstanceMap(926110203).resetPQ(level);
	eim.setInstanceMap(926110300).resetPQ(level);
	eim.setInstanceMap(926110301).resetPQ(level);
	eim.setInstanceMap(926110302).resetPQ(level);
	eim.setInstanceMap(926110303).resetPQ(level);
	eim.setInstanceMap(926110304).resetPQ(level);
	eim.setInstanceMap(926110400).resetPQ(level);
	var fgt = eim.setInstanceMap(926110401);
	fgt.resetPQ(level);
	fgt.killMonster(9300138);
	var mob = em.getMonster(9300138);
	var stats = new Packages.server.life.OverrideMonsterStats();
	stats.setOHp(level * 3); // closest to average :x
	stats.setOMp(120);
	mob.changeLevel(level);
	mob.setOverrideStats(stats);
	eim.registerMonster(mob);
	eim.getMapInstance(13).spawnMonsterOnGroundBelow(mob, new java.awt.Point(-412, -130));
	
	
	var map = eim.setInstanceMap(926110500);
	map.resetPQ(level);
	map.spawnNpc(2112011, new java.awt.Point(232, 150));
	eim.setInstanceMap(926110600).resetPQ(level);
    eim.startEventTimer(1200000);
    return eim;
}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
    player.tryPartyQuest(1205);
}

function playerRevive(eim, player) {
}

function scheduledTimeout(eim) {
    end(eim);
}

function changedMap(eim, player, mapid) {
    if (mapid < 926110000 || mapid > 926110600) {
		eim.unregisterPlayer(player);
		if (eim.disposeIfPlayerBelow(0, 0)) {
			em.setProperty("state", "0");
			em.setProperty("leader", "true");
			em.setProperty("summoned", "0");
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
    if (mapid == 926110401 && (em.getProperty("summoned") == null || em.getProperty("summoned").equals("0"))) {
		eim.getMapInstance(mapid).setSpawns(false);
		eim.getMapInstance(mapid).killAllMonsters(true); 
		eim.getMapInstance(mapid).spawnNpc(2112010, new java.awt.Point(232, 150));
	}
}

function playerDisconnected(eim, player) {
    return 0;
}

function monsterValue(eim, mobId) {
    if (mobId == 9300137 || mobId == 9300138) {
		em.setProperty("stage7", "1");
		eim.broadcastPlayerMsg(5, "Romeo has been killed!"); // TODO: gms-like message xD
		//end(eim);
    } else if (mobId == 9300139 || mobId == 9300140) {
			eim.getMapInstance(13).spawnNpc(2112003, new java.awt.Point(76, 150));
			eim.getMapInstance(13).spawnNpc(2112004, new java.awt.Point(46, 150));
		if (em.getProperty("stage7").equals("0")) {
			eim.getMapInstance(15).spawnNpc(2112003, new java.awt.Point(111, 128));
		} else { // TODO: code the NPC's if you failed
			eim.getMapInstance(15).spawnNpc(2112009, new java.awt.Point(111, 128));
			eim.getMapInstance(15).spawnNpc(2112008, new java.awt.Point(211, 128));
		}
    }
    return 1;
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    if (eim.disposeIfPlayerBelow(0, 0)) {
		em.setProperty("state", "0");
		em.setProperty("leader", "true");
		em.setProperty("summoned", "0");
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

function end(eim) {
    eim.disposeIfPlayerBelow(100, 926110700);
	em.setProperty("state", "0");
	em.setProperty("leader", "true");
	em.setProperty("summoned", "0");
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

function clearPQ(eim) {
    end(eim);
}

function leftParty(eim, player) {
	end(eim);
}
function disbandParty(eim) {
	end(eim);
}

function allMonstersDead(eim) {}
function playerDead(eim, player) {}
function cancelSchedule() {}