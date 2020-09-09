importPackage(Packages.tools.RandomStream);
importPackage(Packages.packet.creators);
importPackage(Packages.launch.world);
importPackage(java.lang);
importPackage(java.awt);

var returnmap = 450003600;
var rewardmap = 450004300;
var mapid = new Array(450004150,450004250);
var monster = new Array(8880140,8880150);
var point = new Array(new Point(1019, 50), new Point(798, -192));

function init() {

}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("Lucid" + a) != null) {
        a = Randomizer.nextInt();
    }
    var eim = em.newInstance("Lucid" + a);
    return eim;
}

function playerEntry(eim, player) {
    player.changeMap(mapid[Integer.parseInt(eim.getProperty("Stage"))], "sp");
	player.getClient().sendPacket(BossPacket.showSpineScreen(true, true, true, "Map/Effect3.img/BossLucid/Lucid/lusi", "animation", 9000));
    if (eim.isLeader(player)) {

    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(1439, 48));
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(1439, 48));
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880164), new Point(766, 48));
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880164), new Point(766, 48));
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(587, 48));
  	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(587, 48));
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880165), point[Integer.parseInt(eim.getProperty("Stage"))]);
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880168), point[Integer.parseInt(eim.getProperty("Stage"))]);
    	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880169), point[Integer.parseInt(eim.getProperty("Stage"))]);
        eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(monster[Integer.parseInt(eim.getProperty("Stage"))]), point[Integer.parseInt(eim.getProperty("Stage"))]);
        if (Integer.parseInt(eim.getProperty("Stage")) == 0) {
             eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(8880166), point[Integer.parseInt(eim.getProperty("Stage"))]);
        }
    }
}

function changedMap(eim, player, mapid) {
	if (mapid == 123456788) {
		player.getClient().send(MainPacketCreator.getClock(0));
    		eim.unregisterPlayer(player);
	}
}

function scheduledTimeout(eim) {
    	var it = eim.getPlayers().iterator();
    	while (it.hasNext()) {
		var chr = it.next();
		chr.changeMap(123456788, "sp");
            	eim.unregisterAll();
            	eim.dispose();
	}
}

function monsterKilled(eim, player, mobid) {
    if (mobid == monster[Integer.parseInt(eim.getProperty("Stage"))]) {
	Stage = Integer.parseInt(eim.getProperty("Stage")) + 1;
    	var it = eim.getPlayers().iterator();
	if (Stage == 1) {
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(297, -125));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(786, -194));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880164), new Point(964, -331));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880164), new Point(148, -550));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(763, -490));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880165), new Point(1085, -619));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880168), new Point(525, -685));
    	eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(8880171), new Point(329, -855));
        eim.getMapFactory().getMap(450004250).spawnMonsterOnGroundBelow(em.getMonster(monster[Stage]), point[Stage]);
        while (it.hasNext()) {
            	var chr = it.next();
		chr.dropMessage(5, "Lucid was angry.");
            	chr.changeMap(mapid[Stage], "sp");
       	}
	}
    }
}


function playerDead(eim, player) {
	player.changeMap(player.getMap().getId(), 0);
	player.addHP(player.getMaxHp());
}


function monsterValue(eim, mobid) {
    return 0;
}

function leftParty(eim, player) {
    eim.unregisterPlayer(player);
    var map = em.getMapFactory().getMap(returnmap);
    player.changeMap(map, map.getPortal(0));
}

function disbandParty(eim) {

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

function onMapLoad(eim, player) {

}

function playerDisconnected(eim, player)
{
    if (eim.getProperty("Global_MinPerson") == null)
    {
        return -1;
    }
    return -Integer.parseInt(eim.getProperty("Global_MinPerson"));
}

function leftParty(eim, player) {
    if (eim.getPlayerCount() < Integer.parseInt(eim.getProperty("Global_MinPerson")))
    {
        var exit = em.getChannelServer().getMapFactory().getMap(450003600);
        var it = eim.getPlayers().iterator();
        while (it.hasNext())
        {
            var chr = it.next();
            chr.changeMap(exit, exit.getPortal(0));
            chr.Message("The party member has left the party and can no longer proceed with the quest.");
        }
        eim.unregisterAll();
        if (eim != null)
        {
            eim.dispose();
        }
    }
}

function disbandParty(eim)
{
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(450003600);
    var it = eim.getPlayers().iterator();
    while (it.hasNext())
    {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("The party leader has left the party and can no longer proceed with the quest.");
    }
    eim.unregisterAll();
    if (eim != null)
    {
        eim.dispose();
    }
}

function cancelSchedule() {
}