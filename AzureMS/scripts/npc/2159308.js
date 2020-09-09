//  Arkarium
/*
        Arkarium Boss
        
	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	페리 에 의해 만들어 졌습니다.

	엔피시아이디 : 2159110

	엔피시 이름 : 헨리테

	엔피시가 있는 맵 : 히든스트리트 : 함정! 실험실 감옥 (931000310)

	엔피시 설명 : MISSINGNO


*/


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

var bossid = 8860000; // Boss mob code
var secondboss = 8880150; // (Lucid, Swooman only, 2 Peb mob)
var startmap = 272020200; // Boss map
var secondmap = 450004250; // (Lucid, Swooman only, 2 pages map)
var exitmap = 410000060; // Open space (exit map)
var bossname = "Arkarium" // In Hangul
var limit = 5; // Daily Admissions
var time = 15; // Time limit
//1135 48
var x = -1, y = -181; // Boss coordinates
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
		var msg = "#fs11##d<#i2591202# :: #rArkarium#k>#d"+enter;
                msg += "<Boss Reward : #i4001907#>"+enter;
		msg += "<Time limit : #r"+time+" Minute#k #dDaily entries#k #d: #b"+cm.GetCount(bossname+"c", limit)+" Time#k / #r"+limit+" Times#k>#k"+enter;
                msg += "#d<Death Count :#k #r10 Times#k>#k"+enter;
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
			cm.sendOk("You cannot apply if you are not the party leader.");
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