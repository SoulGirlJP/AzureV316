importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.tools);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);
importPackage(Packages.server);
importPackage(Packages.tools.packet);
importPackage(Packages.server.life);
importPackage(Packages.tools.RandomStream);
importPackage(Packages.packet.creators);

function init() {
}

function playerRevive(eim, player) {
	return true;
}

function spawnBoss(bossid, point, mapid, eim) {
    	var mobzz = em.getMonster(bossid);
    	eim.registerMonster(mobzz);
    	eim.getMapFactory().getMap(mapid).spawnMonsterOnGroundBelow(mobzz, point);
}

function setup(eim) {
	var a = Randomizer.nextInt();
	while (em.getInstance("Boss_" + a) != null) {
		a = Randomizer.nextInt();
	}
	var eim = em.newInstance("Boss_" + a);
	return eim;
}

function isLeader(player) {
	return player.getParty().getLeader().getId() == player.getId();
}

// Player entry
function playerEntry(eim, player) {
	mobid = Integer.parseInt(eim.getProperty("Boss_ID"));
	//mobid2 = Integer.parseInt(eim.getProperty("Boss_Second"));
	x = Integer.parseInt(eim.getProperty("Boss_x"));
	y = Integer.parseInt(eim.getProperty("Boss_y"));
	x2 = Integer.parseInt(eim.getProperty("Boss_x_2"));
	y2 = Integer.parseInt(eim.getProperty("Boss_y_2"));
	mapid = Integer.parseInt(eim.getProperty("StartMap"));
    	player.changeMap(mapid, 0);
    	if (isLeader(player) && mobid != null) {
		eim.setProperty("LLLeader", player.getName());
		spawnBoss(mobid, new Point(x, y), mapid, eim);
		//spawnBoss(mobid2, new Point(x2, y2), mapid, eim);
		if (mobid == 8880140) {
			spawnBoss(8880166, new Point(x, y), mapid, eim);
		}
	}
}

function spawnz(eim) {
    	var mobzz = em.getMonster(9460026);
	mobzz.setFh(90);
    	eim.registerMonster(mobzz);
    	eim.getMapFactory().getMap(814031200).spawnMonsterOnGroundBelow(mobzz, new Point(96, -2377));
}

function playerDead(eim, player) {
}

function monsterValue(eim, mobid) {
	return 0;
}

function onMapLoad(eim, player) {
}

function playerDisconnected(eim, player) {
}


function WriteLog(boss, leader) {
}

function changedMap(eim, player, mapid) {
	if (mapid == 100000000) {
    		eim.unregisterPlayer(player);
	}
}

function removePlayer(eim, player) {
	eim.dispose();
}


function playerExit(eim, player) {
	eim.unregisterPlayer(player);
	if (eim != null) {
		if (eim.getPlayerCount() <= 0) {
			eim.dispose();
		}
	}
}


function allMonstersDead(eim) {
	kc = Integer.parseInt(eim.getProperty("KillCount"));
	bn = eim.getProperty("BossName");
	bi = eim.getProperty("Boss_ID");
	if (bn != "스우" && bn != "카오스퀸" &&bn != "카오스 피에르" && bi != 8881000 && bi != 8880301 && bi != 8880140 && bi != 8880502) {
        		eim.setProperty("IsClear", "1");
		var mapasd1 = 0;
        		var it = eim.getPlayers().iterator();
        		while (it.hasNext()) {
            			var chr = it.next();
			chr.Message("Congratulations on your clear! Just exit with the `@boss` command.");
			chr.Message(bi);
			mapasd1 = chr.getMapId();
        		}
		eim.getMapFactory().getMap(mapasd1).resetFully();
	}
}

function monsterKilled(eim, player) {
}

// When a party member leaves.
function leftParty(eim, player) {
	var it = eim.getPlayers().iterator();
	while (it.hasNext()) {
           		var chr = it.next();
		chr.changeMap(100000000, 0);
           		chr.Message("The expedition is disbanded because the party member quits the party.");
	}
	eim.unregisterAll();
	if (eim != null) {
		eim.dispose();
	}
}

// When we broke up the party
function disbandParty(eim) {
	var it = eim.getPlayers().iterator();
	while (it.hasNext()) {
		var chr = it.next();
		chr.changeMap(100000000, 0);
		chr.Message("The expedition is disbanded because the party leader quits the party.");
	}
	eim.unregisterAll();
	if (eim != null) {
		eim.dispose();
	}
}