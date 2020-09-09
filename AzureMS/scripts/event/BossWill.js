
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
var rucif = 8880166;
var map1 = 450008250;
var map2 = 450008350;

var boss1 = 8880301;
var boss1x = 33, boss1y = 215;

var boss2 = 8880302;
var boss2x = -4, boss2y = 281;

var selfboom1 = 8880315;
var selfboom2 = 8880316;
var trap = 8880306;

function init() {
}


function playerRevive(eim, player) {
/*
	if (Integer.parseInt(eim.getProperty("Stage")) == 0) {
		chr.changeMap(map1, 0);
	} else if (Integer.parseInt(eim.getProperty("Stage")) == 1) {
		chr.changeMap(map2, 0);
	}
	player.addHP(player.getMaxHp());
*/
	return true;
}

function spawnBoss(bossid, point, mapid, eim) {
    	var mobzz = em.getMonster(bossid);
    	eim.registerMonster(mobzz);

    	eim.getMapFactory().getMap(mapid).spawnMonsterOnGroundBelow(mobzz, point);
}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("WillBoss_" + a) != null) {
        a = Randomizer.nextInt();
    }
    var eim = em.newInstance("WillBoss_" + a);
    return eim;
}

function isLeader(player) {
	return player.getParty().getLeader().getId() == player.getId();
}
// Player entry
function playerEntry(eim, player) {

	mobid = boss1;
	x = boss1x;
	y = boss1y;
	mapid = map1;
    	player.changeMap(mapid, 0);
	eim.setProperty("Stage", 0);
    	if (isLeader(player) && mobid != null) {
		eim.setProperty("LLLeader", player.getName());
		spawnBoss(mobid, new Point(x, y), mapid, eim);
		
	}

}

// When the timer runs out
function scheduledTimeout(eim) {
    var isClear = eim.getProperty("IsClear");
    	var it = eim.getPlayers().iterator();
	if (isClear == "1") {
        	while (it.hasNext()) {
            		var chr = it.next();
			chr.changeMap(123456788, 0);
			chr.Message("Moved.");
			chr.getClient().send(MainPacketCreator.getClock(0));
        	}
	} else {
        	while (it.hasNext()) {
            		var chr = it.next();
			
			chr.changeMap(123456788, 0);
			chr.Message("Over time, the party breaks up.");
			chr.getClient().send(MainPacketCreator.getClock(0));
        	}
	}
	eim.unregisterAll();
    	if (eim != null) {
        	eim.dispose();
    	}
}


function playerDead(eim, player) {
			player.changeMap(player.getMap().getId(), 0);
			player.addHP(player.getMaxHp());
}


function monsterValue(eim, mobid) {
    return 0;
}


function onMapLoad(eim, player) {

}

function playerDisconnected(eim, player)
{
}


function WriteLog(boss, leader) {
/*
        a = new Date();
	temp = Randomizer.rand(0,9999999);
	fFile1 = new File("Log/boss/"+boss+"/"+temp+"_"+leader+".log");
        if (!fFile1.exists()) fFile1.createNewFile();
        out1 = new FileOutputStream("Log/boss/"+boss+"/"+temp+"_"+leader+".log",false);
	var msg = "'"+boss+"'보스가 격파되었습니다.\r\n";
	msg += "해당 원정대 파티장 : "+leader+"\r\n";
	msg += "격파 시각 : "+a.getFullYear()+"년 "+Number(a.getMonth() + 1)+" 월 "+a.getDate()+"일 "+a.getHours()+"시 "+a.getMinutes()+"분 "+a.getSeconds()+"초\r\n";
        out1.write(msg.getBytes());
        out1.close();
*/
}

function changedMap(eim, player, mapid) {
	if (mapid == 123456788) {
		if (isLeader(player)) {
			player.doneWillMob();
		}
		player.getClient().send(MainPacketCreator.getClock(0));
    		eim.unregisterPlayer(player);
	}
}

function removePlayer(eim, player) {
    eim.dispose();
}


function playerExit(eim, player) {
	if (isLeader(player)) {
		player.doneWillMob();
	}
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}


function allMonstersDead(eim) {
}
// Boss kill
function monsterKilled(eim, player, mobid) {
	if (mobid == boss1) {
		eim.setProperty("Stage", 1);
		mobid = boss2;
		x = boss2x;
		y = boss2y;
		mapid = map2;
        	var it = eim.getPlayers().iterator();
        	while (it.hasNext())
        	{
           		var chr = it.next();
			chr.changeMap(mapid, 0);
           		chr.Message("Will was angry.");
    			if (isLeader(chr) && mobid != null) {
				spawnBoss(mobid, new Point(x, y), mapid, eim);
				chr.doneWillMob();
				chr.spawnWillMob1(20, true);
				chr.spawnWillMob2(40, true);
		
			}
       		}
	} else if (mobid == boss2) {
        	var it = eim.getPlayers().iterator();
        	eim.restartEventTimer(30000);
        	eim.setProperty("IsClear", "1");
        	while (it.hasNext())
        	{
    			if (isLeader(chr)) {
				chr.doneWillMob();
			}
           		var chr = it.next();
			chr.Message("Congratulations on your clear! After a while you will be automatically removed.");
			chr.Message("1000 Boss Points were awarded for Boss Extermination.");
			ret1 = Integer.parseInt(chr.getDateKey("BossP", false));
			fin = ret1 + 1000;

			chr.setDateKey("BossP", ""+fin, false);
       		}
	}
}

// When a party member leaves.
function leftParty(eim, player) {
        var it = eim.getPlayers().iterator();
        while (it.hasNext())
        {
    		if (isLeader(chr)) {
			chr.doneWillMob();
		}
           	var chr = it.next();
		chr.changeMap(123456788, 0);
           	chr.Message("The expedition is disbanded because the party member quits the party.");
        }
	eim.unregisterAll();
        if (eim != null)
        {
            eim.dispose();
        }
}

// When we broke up the party

function disbandParty(eim) {
    var it = eim.getPlayers().iterator();
    while (it.hasNext())
    {
    			if (isLeader(chr)) {
				chr.doneWillMob();
			}
        var chr = it.next();
	//var tese = chr.getWarpMap(410000060);
	chr.changeMap(123456788, 0);
        chr.Message("The expedition is disbanded because the party leader quits the party.");
    }
	eim.unregisterAll();
    if (eim != null)
    {
        eim.dispose();
    }
}