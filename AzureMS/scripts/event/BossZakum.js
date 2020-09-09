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
	em.setProperty("Global_Status", "0");
	em.setProperty("Global_Battle", "0");    
}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("Zakum"+a) != null) {
        a = Randomizer.nextInt();
    }
    em.setProperty("Global_Status", "1");
    var eim = em.newInstance("Zakum"+a);
    return eim;
}

function playerEntry(eim, player) {
    var map = Integer.parseInt(eim.getProperty("Global_StartMap"));
    player.changeMap(eim.getMapFactory().getMap(map), eim.getMapFactory().getMap(map).getPortal("sp"));
    player.message(5, "If you don't summon Jakum within 15 minutes, Zakum's altar will close.");
    eim.startEventTimer(15 * 60 * 1000);
}



function changedMap(eim, player, mapid) {
    
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
            System.out.println("EXIT Map Portal NULL!");
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
}

function playerDead(eim, player) {
    return 0;
}

function playerRevive(eim, player) {
    
}

function playerDisconnected(eim, player) {
    /* 0: keep the instance until all
     * 1 to: Keeps an instance of who goes out if only a certain level is left
     * -1 or less: Only people above a certain level remain면 유지이나, 파티장이 나가면 인스턴스 삭제
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

function monsterKilled(eim, player)
{
}

function monsterValue(eim, mobid)
{
	if(mobid == 8800002)
	{
        	var it = eim.getPlayers().iterator();
		eim.setProperty("Global_checkEnd", getNow());
		end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
		end = end/1000;
		var inz = eim.getProperty("Global_checkEnd").split("-");
        	while (it.hasNext())
		{
			var chr = it.next();
			chr.Message(8, ""+chr.getName()+" Zakum participated in "+inz[1]+" Month "+inz[2]+" Day "+inz[3]+" Hour "+inz[4]+" Minutes cleared, record saved.");
			updateRanking(chr.getParty().getLeader().getName(), 8800002, eim.getProperty("Global_checkStr"), getNow(), end, getMembers(eim), chr.getParty().getMembers().size());
		}
	}

	else if(mobid == 8800022)
	{
        	var it = eim.getPlayers().iterator();
		eim.setProperty("Global_checkEnd", getNow());
		end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
		end = end/1000;
		var inz = eim.getProperty("Global_checkEnd").split("-");
        	while (it.hasNext())
		{
			var chr = it.next();
		chr.Message(8, ""+chr.getName()+" Weakened Zakum participated in "+inz[1]+" Month "+inz[2]+" Day "+inz[3]+" Hour "+inz[4]+" Minutes cleared, record saved.");
		updateRanking(chr.getParty().getLeader().getName(), 8800022, eim.getProperty("Global_checkStr"), getNow(), end, getMembers(eim), chr.getParty().getMembers().size());

		}
	}

	else if(mobid == 8800102)
	{
        	var it = eim.getPlayers().iterator();
		eim.setProperty("Global_checkEnd", getNow());
		end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
		end = end/1000;
		var inz = eim.getProperty("Global_checkEnd").split("-");
        	while (it.hasNext())
		{
			var chr = it.next();
		chr.Message(8, ""+chr.getName()+" Chaos Zakum participated in "+inz[1]+" Month "+inz[2]+" Day "+inz[3]+" Hour "+inz[4]+" Minutes cleared, record saved.");
		updateRanking(chr.getParty().getLeader().getName(), 8800102, eim.getProperty("Global_checkStr"), getNow(), end, getMembers(eim), chr.getParty().getMembers().size());

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