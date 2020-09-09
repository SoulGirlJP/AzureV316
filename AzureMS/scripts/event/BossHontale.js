importPackage(Packages.tools.RandomStream);
importPackage(Packages.packet.creators);
importPackage(Packages.launch.world);
importPackage(Packages.server.life);
importPackage(Packages.main.world);
importPackage(Packages.database);
importPackage(java.lang);
importPackage(java.awt);

function getNow()
{
	cal = new Date();
	return  zeroAdd(cal.getFullYear(), 4) + '-' +
		zeroAdd(cal.getMonth()+1, 2) + '-' +
		zeroAdd(cal.getDate(), 2) + '-' +
		zeroAdd(cal.getHours(), 2) + '-' +
		zeroAdd(cal.getMinutes(), 2) + '-' +
		zeroAdd(cal.getSeconds(), 2)
}

function zeroAdd(n, digits)
{
	zero = "";
	n = n.toString();

	if(n.length < digits)
	{
		for(i = 0; i < digits - n.length; i++)
		{
			zero += '0';
		}
	}
	return zero + n;
}


function init() {
}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("Hontale"+a) != null) {
        a = Randomizer.nextInt();
    }
    var eim = em.newInstance("Hontale"+a);
    return eim;
}

function playerEntry(eim, player) {
    var map = Integer.parseInt(eim.getProperty("Global_StartMap"));
    player.changeMap(eim.getMapFactory().getMap(map), eim.getMapFactory().getMap(map).getPortal("sp"));
    eim.startEventTimer(75 * 60 * 1000);
}

function monsterKilled(eim, player)
{
}


function changedMap(eim, player, mapid)
{
	switch(mapid)
	{
		case 240060000:
		case 240060001:
		case 240060002:
		eim.setProperty("posX", "870");
		eim.setProperty("posY", "225");
		break;

		default:
		eim.setProperty("posX", "-339");
		eim.setProperty("posY", "251");
		break;
	}

	switch(mapid)
	{

		case 240060000:
		eim.setProperty("honTale", "8810024");
		break;

		case 240060001:
		eim.setProperty("honTale", "8810128");
		break;

		case 240060002:
		eim.setProperty("honTale", "8810212");
		break;

		case 240060100:
		eim.setProperty("honTale", "8810025");
		break;

		case 240060101:
		eim.setProperty("honTale", "8810129");
		break;

		case 240060102:
		eim.setProperty("honTale", "8810213");
		break;

	}

	if(mapid%240060000 < 200)
	{
		eim.getMapFactory().getMap(mapid).spawnMonsterOnGroundBelow(em.getMonster(eim.getProperty("honTale")), new java.awt.Point(eim.getProperty("posX"), eim.getProperty("posY")));
	}
	return mapid;
}

function scheduledTimeout(eim) {
    
    var exit = em.getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        if (chr == null) {
            System.out.println("Character NULL!");
        }
        if (exit == null) {
            System.out.println("EXIT Map is NULL!");
        }
        if (exit.getPortal("sp") == null) {
            System.out.println("EXIT Map portal NULL!");
        }
        chr.changeMap(exit, exit.getPortal("sp"));
        chr.Message(11, "It was timed out and the altar of Jakum was closed.");
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
}

function allMonstersDead(eim)
{
	if(eim.getProperty("Global_Status") == 3)
	{
		eim.getMapFactory().getMap(240060300).spawnMonsterOnGroundBelow(em.getMonster(eim.getProperty("honTale")), new java.awt.Point(71, 260));
	}
}

function playerDead(eim, player) {
	player.setHp(player.getMaxHp());
	player.changeMap(player.getMapId(), 0);
    return 0;
}

function playerRevive(eim, player) {
    
}

function playerDisconnected(eim, player) {
    /* 0 : Keep instances until they are all gone
     * 1 to: Keeps an instance of who goes out if only a certain level is left
     * -1 or less: Maintains only a certain level of people, or deletes instances when the party leader leaves
     */
    if (eim.getProperty("Global_MinPerson") == null) {
        return -1;
    }
    return -Integer.parseInt(eim.getProperty("Global_MinPerson"));
}


function getMembers(eim)
{
	var it = eim.getPlayers().iterator();
	while (it.hasNext())
	{
		var chr = it.next();
		var size = chr.getParty().getMembers().size();
		var mem1 = chr.getParty().getLeader().getName() + ":";
		var mem2 = size > 1 ? chr.getParty().getMemberByIndex(1).getName() + ":" : ":";
		var mem3 = size > 2 ? chr.getParty().getMemberByIndex(2).getName() + ":" : ":";
		var mem4 = size > 3 ? chr.getParty().getMemberByIndex(3).getName() + ":" : ":";
		var mem5 = size > 4 ? chr.getParty().getMemberByIndex(4).getName() + ":" : ":";
		var mem6 = size > 5 ? chr.getParty().getMemberByIndex(5).getName() + ":" : ":";
	}
	return mem1 + mem2 + mem3 + mem4 + mem5 + mem6
}

function updateRanking(name, boss, bgin, ends, time, pmem, size)
{

}

function monsterValue(eim, mobid)
{
	if(mobid == 8810000 || mobid == 8810100 || mobid == 8810200)
	{
		eim.setProperty("Global_Status", "2");
	}

	if(mobid == 8810001 || mobid == 8810101 || mobid == 8810201)
	{
		eim.setProperty("Global_Status", "3");
	}

	if(mobid == 8810018)
	{
        	var it = eim.getPlayers().iterator();
		eim.setProperty("Global_checkEnd", getNow());
		end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
		end = end/1000;
		var inz = eim.getProperty("Global_checkEnd").split("-");
        	while (it.hasNext())
		{
			var chr = it.next();
			chr.Message(8, ""+chr.getName()+" The Horntailer from) "+inz[1]+" Month "+inz[2]+" Day "+inz[3]+" Hour "+inz[4]+" Minutes cleared, record saved.");
			updateRanking(chr.getParty().getLeader().getName(), 8810018, eim.getProperty("Global_checkStr"), getNow(), end, getMembers(eim), chr.getParty().getMembers().size());
		}
	}

	else if(mobid == 8810122)
	{
        	var it = eim.getPlayers().iterator();
		eim.setProperty("Global_checkEnd", getNow());
		end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
		end = end/1000;
		var inz = eim.getProperty("Global_checkEnd").split("-");
        	while (it.hasNext())
		{
			var chr = it.next();
		chr.Message(8, ""+chr.getName()+" Chaos Horntaila with "+inz[1]+" Month "+inz[2]+" Day "+inz[3]+" Hour "+inz[4]+" Minutes cleared, record saved.");
		updateRanking(chr.getParty().getLeader().getName(), 8810122, eim.getProperty("Global_checkStr"), getNow(), end, getMembers(eim), chr.getParty().getMembers().size());

		}
	}

	else if(mobid == 8810214)
	{
        	var it = eim.getPlayers().iterator();
		eim.setProperty("Global_checkEnd", getNow());
		end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
		end = end/1000;
		var inz = eim.getProperty("Global_checkEnd").split("-");
        	while (it.hasNext())
		{
			var chr = it.next();
		chr.Message(8, ""+chr.getName()+" Easy Horntailers from "+inz[1]+" Month "+inz[2]+" Day "+inz[3]+" Hour "+inz[4]+" Minutes cleared, record saved.");
		updateRanking(chr.getParty().getLeader().getName(), 8810214, eim.getProperty("Global_checkStr"), getNow(), end, getMembers(eim), chr.getParty().getMembers().size());

		}
	}
	return mobid;
}

function leftParty(eim, player) {
    if (eim.getPlayerCount() < Integer.parseInt(eim.getProperty("Global_MinPerson"))) {
        var exit = em.getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
        var it = eim.getPlayers().iterator();
        while (it.hasNext()) {
            var chr = it.next();
            chr.changeMap(exit, exit.getPortal(0));
            chr.Message("The party member has left the party and can no longer proceed with the quest.");
        }
        eim.unregisterAll();
        if (eim != null) {
            eim.dispose();
        }
    }
    
}

function disbandParty(eim) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("The party leader has left the party and can no longer proceed with the quest.");
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
}

function clearPQ(eim) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_RewardMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
    
}

function playerExit(eim, player) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("You can't proceed anymore because you've given up a party quest.");
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
}

function onMapLoad(eim, player) {
}

function cancelSchedule(a) {
    
}