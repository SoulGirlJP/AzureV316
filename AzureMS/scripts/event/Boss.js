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
            zeroAdd(cal.getMonth() + 1, 2) + '-' +
            zeroAdd(cal.getDate(), 2) + '-' +
            zeroAdd(cal.getHours(), 2) + '-' +
            zeroAdd(cal.getMinutes(), 2) + '-' +
            zeroAdd(cal.getSeconds(), 2)
}

function zeroAdd(n, digits)
{
    zero = "";
    n = n.toString();

    if (n.length < digits)
    {
        for (i = 0; i < digits - n.length; i++)
        {
            zero += '0';
        }
    }
    return zero + n;
}

function init()
{
}

function monsterKilled(eim, player, mobid) {
    getMobList(eim);
    if (mobid == Integer.parseInt(eim.getProperty("Global_mobId"))) {
        eim.restartEventTimer(6000);
        var mobcount = Integer.parseInt(eim.getProperty("Global_mobCount"));
        var cust = Integer.parseInt(eim.getProperty("Global_Customed"));
        var num = mobcount * (bns + cust) * 5;
        var totalp = Integer.parseInt(eim.getProperty("points")) + num;
        var getRatio = new Number(100 / mob.length);
        var getRatio = getRatio.toFixed(0);
        if (mobcount < mob.length)
        {
            eim.getMapInstance(0).broadcastMessage(UIPacket.AchievementRatio(getRatio * mobcount));
            eim.getMapInstance(0).broadcastMessage(UIPacket.enforceMSG("Five seconds later, the mock battle begins. All participants should be prepared.", 38, 5000));
            var end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
            var end = new Number(end / 1000);
            var end = end.toFixed(0);

            var it = eim.getPlayers().iterator();
            while (it.hasNext())
            {
                var chr = it.next();
                chr.setKeyValue2("BossPoint", Number(chr.getKeyValue2("BossPoint")) + Number(totalp));
                chr.Message(22, "( " + mobcount + " ) The simulated battle of the round has ended successfully. (Begins a mock battle [" + end + " Seconds] has passed.)");
                chr.Message(22, "In this round clear " + Comma(totalp) + " I got a point. Cumulative activity score [" + Comma(chr.getKeyValue2("BossPoint")) + " Dot] is.");

            }
        } else if (mobcount == mob.length)
        {
            eim.getMapInstance(0).broadcastMessage(UIPacket.AchievementRatio(100));
            var end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
            var end = new Number(end / 1000);
            var end = end.toFixed(0);

            eim.getMapInstance(0).broadcastMessage(MainPacketCreator.showEffect("monsterPark/clear"));
            eim.getMapInstance(0).broadcastMessage(MainPacketCreator.playSound("Party1/Clear"));
            eim.getMapInstance(0).broadcastMessage(UIPacket.enforceMSG("All rounds of mock battles have been completed successfully.", 38, 3000));
            var it = eim.getPlayers().iterator();
            eim.setProperty("Global_checkEnd", getNow());
            while (it.hasNext())
            {
                var chr = it.next();
                chr.setKeyValue2("BossPoint", Number(chr.getKeyValue2("BossPoint")) + Number(totalp));
                chr.Message(22, "( " + mobcount + " ) The simulated battle of the round has ended successfully. (Begins a mock battle [" + end + "Seconds] has passed.)");
                chr.Message(22, "In this round clear " + Comma(totalp) + " I got a point. Cumulative activity score [" + Comma(chr.getKeyValue2("BossPoint")) + "Dot].");

            }
        }
    }

}

function setup(eim)
{
    var a = Randomizer.nextInt();
    while (em.getInstance("PartyQuest_ID" + a) != null)
    {
        a = Randomizer.nextInt();
    }

    var eim = em.newInstance("PartyQuest_ID" + a);
    eim.setProperty("points", 0);
    eim.setProperty("Global_mobCount", 0);
    beginQuest(eim);
    return eim;
}

function beginQuest(eim, player)
{
    if (eim)
    {
        eim.startEventTimer(12000);
    }

}

function getMobList(eim)
{

    var diff = eim.getProperty("Global_Level");

    if (diff.equals("쉬움"))
    {
        mob = [2220000, 6130101, 6300005, 8220007, 3220000, 9300003, 6220000, 3300005, 3300006, 3300007];
        bns = 1;
    } else if (diff.equals("보통"))
    {
        mob = [3401011, 4220000, 5220003, 7220000, 8220000, 4300013, 9300488, 6160003];
        bns = 2;
    } else if (diff.equals("어려움"))
    {
        mob = [8180000, 8800002, 8220011, 8220012, 9300152, 8620012, 9300627, 9700037];
        bns = 4;
    } else
    {
        mob = [8820212, 8880000, 8900000, 8910000, 8920000, 8930000];
        bns = 8;
    }
}

function Comma(i)
{
    var reg = /(^[+-]?\d+)(\d{3})/;
    i += '';
    while (reg.test(i))
        i = i.replace(reg, '$1' + ',' + '$2');
    return i;
}


function spawnMob(eim)
{
    getMobList(eim);
    var mobId = mob[Integer.parseInt(eim.getProperty("Global_mobCount"))];
    var mobzz = em.getMonster(mobId);
    eim.setProperty("Global_mobId", mobId);
    eim.registerMonster(mobzz);
    diff = eim.getProperty("Global_Level");
    cust = Integer.parseInt(eim.getProperty("Global_Customed"));

    custom = em.newMonsterStats();
    custom.setOExp(mobzz.getMobExp() * cust * (Integer.parseInt(eim.getProperty("Global_mobCount")) + 1));
    custom.setOHp(mobzz.getMobMaxHp() * cust * (Integer.parseInt(eim.getProperty("Global_mobCount")) + 1));
    custom.setOMp(mobzz.getMobMaxMp() * cust * ((eim.getProperty("Global_mobCount")) + 1));
    mobzz.setOverrideStats(custom);

    var it = eim.getPlayers().iterator();
    var mobcount = Integer.parseInt(eim.getProperty("Global_mobCount"));

    while (it.hasNext())
    {
        var chr = it.next();
        eim.getMapInstance(0).broadcastMessage(UIPacket.enforceMSG("( " + Number(Number(mobcount) + Number(1)) + " / " + mob.length + " ) Start the mock battle. ", 38, 3000));
        chr.Message(22, "( " + Number(Number(mobcount) + Number(1)) + " ) Start your mock battle. The summoned monster's health is [" + Comma(mobzz.getMobMaxHp()) + "] is.");
    }

    eim.getMapInstance(0).spawnMonsterOnGroundBelow(mobzz, new java.awt.Point(3134, 577));
}

function scheduledTimeout(eim)
{
    var exit = em.getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();

    getMobList(eim);
    var num = Integer.parseInt(eim.getProperty("Global_mobCount"));
    if (num < mob.length)
    {
        spawnMob(eim);
        eim.setProperty("Global_mobCount", num + 1);

    } else
    {
        var inz = eim.getProperty("Global_checkEnd").split("-");

            eim.unregisterAll();
        while (it.hasNext())
        {
            var chr = it.next();
            var end = System.currentTimeMillis() - Long.parseLong(eim.getProperty("Global_Bosstime"));
            var end = new Number(end / 1000);
            var end = end.toFixed(0);
            updateRanking(chr.getParty().getLeader().getName(), eim.getProperty("Global_checkStr"), getNow(), eim.getProperty("Global_Level"), end, eim.getProperty("Global_Customed"), getMembers(eim), chr.getParty().getMembers().size())
            chr.Message(22, "You have successfully completed all rounds of mock battles. Total travel time [" + end + " Seconds].")
            chr.Message(8, "" + chr.getName() + " Mock battles in " + inz[1] + " Month " + inz[2] + " Day " + inz[3] + " Hour " + inz[4] + " Minutes cleared, record saved.");
            chr.changeMap(exit, exit.getPortal("sp"));
            eim.dispose();
        }
    }
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

function updateRanking(name, bgin, ends, diff, time, rate, pmem, size)
{
    con = MYSQL.getConnection();
    ps = con.prepareStatement("INSERT INTO bossraidtime(name,bgin,ends,diff,time,rate,pmem,size) VALUES(?,?,?,?,?,?,?,?)");
    ps.setString(1, name);
    ps.setString(2, bgin);
    ps.setString(3, ends);
    ps.setString(4, diff);
    ps.setInt(5, time);
    ps.setInt(6, rate);
    ps.setString(7, pmem);
    ps.setInt(8, size);
    ps.executeUpdate();
    ps.close();
    con.close();
}

function playerEntry(eim, player)
{
    player.changeMap(eim.getMapInstance(0), eim.getMapInstance(0).getPortal("sp"));
    player.send(UIPacket.AchievementRatio(0));
    eim.getMapInstance(0).broadcastMessage(UIPacket.enforceMSG("After 10 seconds, the mock battle begins. All participants should be prepared.", 38, 10000));
}

function changedMap(eim, player, mapid)
{
}

function allMonstersDead(eim)
{

}

function playerDead(eim, player)
{
    return 0;
}

function playerRevive(eim, player)
{
}

function playerDisconnected(eim, player)
{
    /* 0 : Keep instances until they are all gone
     * 1 to: Keeps an instance of who goes out if only a certain level is left
     * -1 or less: Maintains only a certain level of people, or deletes instances when the party leader leaves
     */
    if (eim.getProperty("Global_MinPerson") == null)
    {
        return -1;
    }
    return -Integer.parseInt(eim.getProperty("Global_MinPerson"));
}

function monsterValue(eim, mobid)
{
    return 1;
}

function leftParty(eim, player) {
        eim.unregisterAll();
    if (eim.getPlayerCount() < Integer.parseInt(eim.getProperty("Global_MinPerson")))
    {
        var exit = em.getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
        var it = eim.getPlayers().iterator();
        while (it.hasNext())
        {
            var chr = it.next();
            chr.changeMap(exit, exit.getPortal(0));
            chr.Message("The party member has left the party and can no longer proceed with the quest.");
        }
        if (eim != null)
        {
            eim.dispose();
        }
    }
}

function disbandParty(eim)
{
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    eim.unregisterAll();
    while (it.hasNext())
    {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("The party leader has left the party and can no longer proceed with the quest.");
    }
    if (eim != null)
    {
        eim.dispose();
    }
}

function clearPQ(eim)
{
    var exit = eim.getMapInstance(0);
    var it = eim.getPlayers().iterator();
    while (it.hasNext())
    {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.message(6, "[Notification] The PartyQuest instance has ended. Thank you :)");
    }
    eim.unregisterAll();
    if (eim != null)
    {
        eim.dispose();
    }

}

function playerExit(eim, player)
{
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    eim.unregisterAll();
    while (it.hasNext())
    {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("You can't proceed anymore because you've given up a party quest.");
    }
    if (eim != null)
    {
        eim.dispose();
    }
}

function onMapLoad(eim, player)
{
}

function cancelSchedule(a)
{
}