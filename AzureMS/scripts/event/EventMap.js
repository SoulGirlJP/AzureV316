
importPackage(Packages.client.items);

importPackage(Packages.server.items);
importPackage(Packages.tools);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);
importPackage(Packages.server);
importPackage(Packages.tools.packet);
importPackage(Packages.tools.RandomStream);
importPackage(Packages.server.life);
importPackage(Packages.packet.creators);

function init() {
}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("EventMap_" + a) != null) {
        a = Randomizer.nextInt();
    }
    var eim = em.newInstance("EventMap_" + a);
    return eim;
}



function changedMap(eim, player, mapid) {
	if (mapid != Integer.parseInt(eim.getProperty("StartMap"))) {
		player.getClient().send(MainPacketCreator.getClock(0));
    		eim.unregisterPlayer(player);
	}
}

// Player entry
function playerEntry(eim, player) {
	mapid = Integer.parseInt(eim.getProperty("StartMap"));
	time = Integer.parseInt(eim.getProperty("Time"));
    	player.changeMap(mapid, 0);
        	eim.startEventTimer(60000 * time);
}

// When the timer runs out
function scheduledTimeout(eim) {
    	var it = eim.getPlayers().iterator();
        	while (it.hasNext()) {
            		var chr = it.next();
		chr.Message("We're out of maps when time runs out.");
    		chr.changeMap(120043000, 0);
        	}
	eim.unregisterAll();
    	if (eim != null) {
        	eim.dispose();
    	}
}


function playerDead(eim, player) {
    	player.changeMap(120043000, 0);
    	eim.unregisterPlayer(player);
}


function monsterValue(eim, mobid) {
    return 0;
}


function onMapLoad(eim, player) {

}

function playerDisconnected(eim, player)
{
    	player.changeMap(120043000, 0);
    	eim.unregisterPlayer(player);
}

function removePlayer(eim, player) {
    eim.dispose();
}


function playerExit(eim, player) {

    	player.changeMap(120043000, 0);
    eim.unregisterPlayer(player);
    if (eim != null) {
        if (eim.getPlayerCount() <= 0) {
            eim.dispose();
        }
    }
}

// Boss kill
function monsterKilled(eim, player, mobid) {
}

// When a party member leaves.
function leftParty(eim, player) {
}

// When we broke up the party

function disbandParty(eim)
{
}