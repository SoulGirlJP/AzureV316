// Dorothy Boss
// Script 2540011


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
var enter = "\r\n";
var data, date, day;


/*	Boss Information Settings	*/

var bossid = 9309207; // Boss mob code
var secondboss = 8880150; // (Lucid, Swooman only, 2 Peb mob)
var startmap = 992050000; // Boss map
var secondmap = 450004250; // (Lucid, Swooman only, 2 pages map)
var exitmap = 410000060; // Open space (exit map)
var bossname = "Dorothy" // In Hangul
var limit = 3; // Number of admissions allowed per day
var time = 25; // Time limit
//1135 48
var x = 0, y = 185; // Boss coordinates
var x_2 = 718, y_2 = -490; // (Lucid, Suman only, 2 pe boss coordinates)
var deathcount = 10; // Death Count
/*------------------------------*/
function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
		var msg = "#fs11##d<#i2433840# :: #r Wyrm Dorothy of the Sid #k>#d"+enter;
                msg += "<Boss Reward : #i4001913#>"+enter;
		msg += "<Time limit : #r"+time+" Minutes#k #dDaily entries#k #d: #b"+cm.GetCount(bossname+"c", limit)+" Time#k / #r"+limit+" Times#k>#k"+enter;
                msg += "#d<DeathCount :#k #r10 Time#k>#k"+enter;
		msg += "#L1##rI'm moving to kill the boss.#k";
		cm.sendSimple(msg);
	} else if (status == 1) {
		if(!cm.CountCheck(bossname+"c", limit)) {
			cm.sendOk("Per day "+limit+"Only one time.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getParty() == null) {
			cm.sendOk("Have a party and challenge.");
			cm.dispose();
			return;
		}
		if (!isPartyLeader()) {
			cm.sendOk("If you are not the party leader, you cannot apply.");
			cm.dispose();
			return;
		}
            	if (!cm.allMembersHere()) {
                	cm.sendOk("All party members should be here.");
			cm.dispose();
                	return;
            	}
		if (cm.getPlayerCount(startmap) > 0) {
            		cm.sendOk("Someone is already challenging.\r\n#bPlease try another channel.#k");
            		cm.dispose();
			return;
        	}
            	if (cm.getPlayer().getEventInstance() != null) {
                	cm.getPlayer().getEventInstance().unregisterPlayer(cm.getPlayer());
			cm.sendOk("Event instance is duplicated.\r\nPlease try again.");
			cm.dispose();
			return;
		}
            	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
                	if (!CC(chr, bossname+"c", limit)) {
                    		countPass = false;
                    		break;
                	}
            	}
            	if (!countPass) {
                	cm.sendOk("There are some party members who have no dungeon entry.");
                	cm.dispose();
                	return;
            	} else {
            	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
			AC(chr, bossname+"c");
            	}
		}
		cm.resetMap(startmap);
		var em = cm.getEventManager("hboss");
		var eim = em.readyInstance();
		eim.setProperty("StartMap", startmap);
		eim.setProperty("SecondMap", secondmap);
		eim.setProperty("ExitMap", exitmap);
		eim.setProperty("BossName", bossname);
		eim.setProperty("Boss_ID", bossid);
		eim.setProperty("Boss_Second", secondboss);
		eim.setProperty("Boss_x", x);
		eim.setProperty("Boss_y", y);
		eim.setProperty("Boss_x_2", x_2);
		eim.setProperty("Boss_y_2", y_2);
		eim.setProperty("KillCount", 0);
		eim.setProperty("Leader", cm.getPlayer().getParty().getLeader().getName());
		eim.setProperty("DeathCount", deathcount);
                eim.startEventTimer(60000 * time);
		eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
		cm.dispose();
	}
                if (time == 0) {
            
            eim.dispose();
            cm.warp(100000000);
            
        }
}

function AC(player, boss) {
	player.setDateKey(boss, Integer.parseInt(player.getDateKey(boss, false)) + 1, false);
}

function CC(player, boss, limit) {
    if (player.getDateKey(boss, false) == null)
      player.setDateKey(boss, "0", false);
    return Integer.parseInt(player.getDateKey(boss, false)) < limit;
}
function isPartyLeader() {
	if (cm.getPlayer().getParty().getLeader().getId() == cm.getPlayer().getId())
		return true;
	else
		return false;
}