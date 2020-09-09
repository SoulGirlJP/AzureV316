importPackage(Packages.tools.RandomStream);
importPackage(Packages.packet.creators);
importPackage(Packages.launch.world);
importPackage(java.lang);
importPackage(java.awt);

var returnmap = 350060300;
var mapid = new Array(350060160, 350060180, 350060200);
var monster = new Array(8240097, 8240098, 8240099);
var point = new Array(new Point(5, -14), new Point(-48, -14), new Point(32, -14));

function init() {

}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("Swoo" + a) != null) {
        a = Randomizer.nextInt();
    }
    var eim = em.newInstance("Swoo" + a);
    return eim;
}

function playerEntry(eim, player) {
    player.changeMap(mapid[Integer.parseInt(eim.getProperty("Stage"))], "sp");
    if (eim.isLeader(player)) {
        eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).spawnMonsterOnGroundBelow(em.getMonster(monster[Integer.parseInt(eim.getProperty("Stage"))]), point[Integer.parseInt(eim.getProperty("Stage"))]);
    }
    if (Integer.parseInt(eim.getProperty("Stage")) == 0) {
        	eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).broadcastMessage(MobPacket.applySwooLaser(eim.getMapFactory().getMap(mapid[Integer.parseInt(eim.getProperty("Stage"))]).getMonsterById(monster[Integer.parseInt(eim.getProperty("Stage"))]).getObjectId()));
    }     
}

function changedMap(eim, player, mapid) {

}

function scheduledTimeout(eim) {
    var Stage = Integer.parseInt(eim.getProperty("Stage")) + 1;
    var nextWarp = eim.getProperty("nextWarp");
    var it = eim.getPlayers().iterator();
    if (nextWarp) {
        eim.setProperty("Stage", Stage);
        eim.getMapFactory().getMap(mapid[Stage]).spawnMonsterOnGroundBelow(em.getMonster(monster[Stage]), point[Stage]);
        while (it.hasNext()) {
            var chr = it.next();
            chr.changeMap(mapid[Stage], "sp");
        }
        eim.setProperty("nextWarp", "false");
    }
}

function monsterKilled(eim, player, mobid) {
    if (mobid == monster[Integer.parseInt(eim.getProperty("Stage"))] && mobid != 8240099) {
        eim.restartEventTimer(5000);
        eim.setProperty("nextWarp", "true");
    } else if (mobid == 8240099) {
       WorldBroadcasting.broadcast(MainPacketCreator.serverNotice(6, "Expedition to defeat Black Heaven's swoo! You are the real hero!"));
        var it = eim.getPlayers().iterator();
        while (it.hasNext()) {
            var chr = it.next();
            eim.unregisterAll();
            eim.dispose();

        }
    }
}



function playerDead(eim, player) {

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
        var exit = em.getChannelServer().getMapFactory().getMap(350060300);
        var it = eim.getPlayers().iterator();
        while (it.hasNext())
        {
            var chr = it.next();
            chr.changeMap(exit, exit.getPortal(0));
            chr.Message("파티원이 파티를 그만둬서 더이상 퀘스트를 진행할 수 없습니다.");
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
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(350060300);
    var it = eim.getPlayers().iterator();
    while (it.hasNext())
    {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("파티장이 파티를 그만둬서 더이상 퀘스트를 진행할 수 없습니다.");
    }
    eim.unregisterAll();
    if (eim != null)
    {
        eim.dispose();
    }
}

function cancelSchedule() {
}