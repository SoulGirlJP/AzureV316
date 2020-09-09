/*
 * This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation. You may not use, modify
    or distribute this program under any other version of the
    GNU Affero General Public License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
-- Odin JavaScript --------------------------------------------------------------------------------
	Ludibirum Maze PQ
-- By ---------------------------------------------------------------------------------------------
	Stereo
-- Version Info -----------------------------------------------------------------------------------
	1.1 - fixed minor problems
	1.0 - First Version by Stereo
---------------------------------------------------------------------------------------------------
**/

/*
INSERT monsterdrops (monsterid,itemid,chance) VALUES (9300001,4001007,5);
INSERT monsterdrops (monsterid,itemid,chance) VALUES (9300000,4001008,1);
INSERT monsterdrops (monsterid,itemid,chance) VALUES (9300002,4001008,1);
INSERT monsterdrops (monsterid,itemid,chance) VALUES (9300003,4001008,1);
*/

importPackage(Packages.world);
importPackage(Packages.server.life);
importPackage(Packages.server);
importPackage(Packages.server.maps);
importPackage(Packages.tools);
importPackage(Packages.client);

var exitMap;
var allowMapChange = true;
var minPlayers = 3;

var bossid = Array(6130101, 6300005, 8220007, 9300039, 9500175, 9300012, 8130100, 8520000, 8180001, 8180000, 9500357, 9400014, 7220001, 9400594, 8500001);  //8800002 for zakum
var bossname = Array("Mushmom", "Zombie Mushmom", "Blue Mushmom", "Papa Pixie", "Angry Lord Pirate", "Alishar", "Jr. Balrog", "Pianus", "Griffey", "Manon", "Snow Yeti", "Black Crow", "Nine-tailed Fox", "Master Guardian", "Papulatus");
var bossx = Array(37, 399, -337, -368, 110, 453, -178, -244, -102, -102, -216, -202, 273, -178, -32);
var bossy = Array(1918, 1918, 1918, 1497, 1022, 643, 423, -312, -817, -817, -1820, -2164, -2201, -2894, -4212);
var bosshp = Array(-1, 275000, 300000, 350000, 700000, 775000, 1000000, 3500000, -1, -1, 6000000, 6000000, 7500000, 10000000, 20000000);  //-1 means original hp; mushmom is hardcoded into NPC as the NPC starts chain, so configure there
var mapId = 221000200;
var monsterdelay = 5000; //5sec

var stage = 0;

function init() { // Initial loading.
    exitMap = em.getChannelServer().getMapFactory().getMap(221000000);
    em.setProperty("OmPQOpen", "true"); // allows entrance.
    em.setProperty("shuffleReactors", "true");
    instanceId = 1;
}

function monsterValue(eim, mobId) { // Killed monster.

    if((!contains(bossid, mobId)) && (mobId != 8500002))  //boss summons and gm summons needn't count & we add an exception for papulatus cause he's cool
       return;
    
    if(mobId == 8500001)     //papulatus clock
       return;               // they've gotta kill pap yet ;D

    var map = getMap(eim);
    map.broadcastMessage(MaplePacketCreator.serverNotice(6, "[Event] " + "Congratulations on killing " + bossname[stage] + "."));
    stage++;
    if (stage > bossid.length - 1)   //arrays start at 0, length starts at 1
       finishEvent(eim);
    else
    {
         map.broadcastMessage(MaplePacketCreator.serverNotice(6, "[Event] " + "Next up: " + bossname[stage] + ", spawning in " + monsterdelay / 1000 + " seconds."));
         //spawnNextBoss(eim);
         eim.schedule("spawnNextBoss", monsterdelay);
    }
    return 1;
}

function setup() {
    stage = 0;
    var eim = em.newInstance("OmegaPQ");
    var eventTime = 30 * (1000 * 60);
    var p;
    var map = getMap(eim);
    map.toggleDrops();

    spawnNPC(eim, 2043000, -155, 1779);
    spawnNPC(eim, 1052015, 344, -4717);

    var iter = map.getPortals().iterator();    //kills the portals
    	while (iter.hasNext()) {
    		var p = iter.next();
                p.setScriptName("omPQ");
	}

    eim.schedule("timeOut", eventTime); // invokes "timeOut" in how ever many seconds.
    eim.startEventTimer(eventTime); // Sends a clock packet and tags a timer to the players.
    eim.setProperty("pqFinished", "false");
    eim.setProperty("enforceParty", "true"); // means basically if you're less than the party number, should you be all warped out?
    return eim;
}

function playerEntry(eim, player) {

    player.changeMap(getMap(eim), getMap(eim).getPortal(0));
    player.setallowedMapChange(false);
}

function playerDead(eim, player) {
}

function playerRevive(eim, player) { // player presses ok on the death pop up.
    player.setallowedMapChange(true);
    if (((eim.isLeader(player)) || (party.size() <= minPlayers)) && (eim.getProperty("enforceParty").equals("true"))) { // Check for party leader
        warpPartyOut(eim);
        dispose(eim);
    } else
        playerExit(eim, player);
}

function playerDisconnected(eim, player) {
    var party = eim.getPlayers();
    if (((eim.isLeader(player)) || (party.size() < minPlayers)) && (eim.getProperty("enforceParty").equals("true"))) {
        var party = eim.getPlayers();
        for (var i = 0; i < party.size(); i++)
            if (party.get(i).equals(player))
                removePlayer(eim, player);
            else
                playerExit(eim, party.get(i));
        dispose(eim);
    } else
        removePlayer(eim, player);
}

function leftParty(eim, player) {
    var party = eim.getPlayers();
    if ((party.size() < minPlayers) && (eim.getProperty("enforceParty").equals("true"))) {
        warpPartyOut(eim);
        dispose(eim);
    } else
        playerExit(eim, player);
}

function disbandParty(eim) {
    warpPartyOut(eim);
    dispose(eim);
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    player.setallowedMapChange(true);
    player.changeMap(exitMap, exitMap.getPortal(0));
    
    if(eim.getPlayerCount() == 0)
        dispose(eim);
}

function removePlayer(eim, player) {  //for disconnected peeps / peeps who have left
    player.setallowedMapChange(true);
    player.getMap().removePlayer(player);
    player.setMap(exitMap);
    eim.unregisterPlayer(player);
    
    if(eim.getPlayerCount() == 0)
        dispose(eim);
}

function clearPQ(eim) {
    warpPartyOut(eim);
    dispose(eim);
}

function allMonstersDead(eim) {
}

function cancelSchedule() {
}

function playerMapChange(eim, player) {
         return player.allowedMapChange();
}

function dispose(eim) {
    eim.schedule("OpenOmPQ", 5000); // 5 seconds ?
}

function OpenOmPQ(eim) {
    em.setProperty("OmPQOpen", "true");
    eim.dispose();
}

function timeOut(eim) {
    if (eim != null) {
        if (eim.getPlayerCount() > 0) {
            var pIter = eim.getPlayers().iterator();
            while (pIter.hasNext())
                playerExit(eim, pIter.next());
        }
        stage = 0;
        dispose(eim);
    }
}

function spawnNextBoss(eim) {
         var mob = MapleLifeFactory.getMonster(bossid[stage]);
         var overrideStats = new MapleMonsterStats();
         if (bosshp[stage] == -1) {
            bosshp[stage] = mob.getHp();
         }
         overrideStats.setExp(mob.getExp() * 0.8 * (bosshp[stage] / mob.getHp()));  // exp directly proportional to ratio of orig & nerfed HPs
	 overrideStats.setHp(bosshp[stage]);
	 overrideStats.setMp(mob.getMaxMp());
         mob.setOverrideStats(overrideStats);
	 eim.registerMonster(mob);
         var map = eim.getMapInstance(221000200, true);
         map.spawnMonsterOnGroudBelow(mob, new java.awt.Point(bossx[stage], bossy[stage]));
    }

  function finishEvent(eim) {
    eim.setProperty("enforceParty", "false");
    eim.setProperty("pqFinished", "true"); // the NPC will take care of the rest
  }
  
  function contains(a, obj) {
  var i = a.length;
  while (i--) {
    if (a[i] === obj) {
      return true;
    }
  }
  return false;
}

function getMap(eim)
{
      return eim.getMapInstance(221000200, true);
}

function spawnNPC(eim, npcId, x, y)
{
   var point = new java.awt.Point(x, y);
   var map = getMap(eim);
   var npc = MapleLifeFactory.getNPC(npcId);
            if (npc != null) {
                npc.setPosition(point);
                npc.setCy(y);
                npc.setRx0(x);
                npc.setRx1(x);
                npc.setFh(map.getFootholds().findBelow(point).getId());
                map.addMapObject(npc);
                map.broadcastMessage(MaplePacketCreator.spawnNPC(npc));
            }
}

function warpPartyOut(eim)
{
    var party = eim.getPlayers();
    for (var i = 0; i < party.size(); i++) {
        playerExit(eim, party.get(i));
        }
}