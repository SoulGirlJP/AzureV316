
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


/*	보스 정보 설정란	*/

var bossid = 8880301; // 보스 몹 코드
var secondboss = 9999992;// (루시드, 스우만 적용. 2페 몹)
var startmap = 450008250; // 보스 맵
var secondmap = 450008350; // (루시드, 스우만 적용. 2페 맵)
var exitmap = 970060000; // 광장(퇴장 맵)
var bossname = "will"
var limit = 2; // 시간
//1135 48
var x = 500, y = 50; // 보스 좌표
var x_2 = 718, y_2 = -490; // (루시드, 스우만 적용. 2페 보스 좌표)
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
	    var msg = "#fs11##d<#fUI/UIWindow2.img/MobGage/Mob/8880301# :: #rWill#k>#d"+enter;
                msg += "< Extremely Difficult >"+enter;
                msg += "<Boss Reward : #i4001919# >"+enter;
		msg += "< [Time limit :#k #r60 Minutes#k] >#k"+enter;
                msg += "#d<Death count :#k #r15 Times#k>#k"+enter;
		msg += "#L1##rI'm moving to kill the boss.#k";
		cm.sendSimple(msg);
	} else if (status == 1) {
		if(cm.getPlayer().checkBossClearDB(limit, bossname) != null) {
			cm.sendOk(cm.getPlayer().checkBossClearDB(limit, bossname) + " Left.");
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
                             if (cm.getPlayerCount(450008250) > 0 || cm.getPlayerCount(450008350) > 0) { 
            		cm.sendOk("Someone is already challenging.\r\n#bPlease try another channel.#k");
            		cm.dispose();
			return;
        	}
            	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
                	if (chr.checkBossClearDB(limit, bossname) != null) {
                    		countPass = false;
                    		break;
                	}
            	}
            	if (!countPass) {
                	cm.sendOk("There are some party members who have no dungeon entry.");
                	cm.dispose();
                	return;
            	} else {
            /*	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
			AC(chr, bossname+"c");
            	}*/
		}
            		cm.resetMap(450008250);
            		cm.resetMap(450008350);
		var em = cm.getEventManager("Raidboss");
		var eim = em.readyInstance();
		eim.setProperty("StartMap", startmap);
		eim.setProperty("SecondMap", secondmap);
		eim.setProperty("BossName", bossname);
		eim.setProperty("Boss_ID", bossid);
		eim.setProperty("Boss_Second", secondboss);
		eim.setProperty("Boss_x", x);
		eim.setProperty("Boss_y", y);
		eim.setProperty("Boss_x_2", x_2);
		eim.setProperty("Boss_y_2", y_2);
		eim.setProperty("KillCount", 0);
		eim.setProperty("Leader", cm.getPlayer().getParty().getLeader().getName());
		//cm.CountAdd(bossname+"c");
		eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
		cm.dispose();
	}
}


function isPartyLeader() {
	if (cm.getPlayer().getParty().getLeader().getId() == cm.getPlayer().getId())
		return true;
	else
		return false;
}

function AC(player, boss) {
	player.setDateKey(boss, Integer.parseInt(player.getDateKey(boss, false)) + 1, false);
}

function CC(player, boss, limit) {
    if (player.getDateKey(boss, false) == null)
      player.setDateKey(boss, "0", false);
    return Integer.parseInt(player.getDateKey(boss, false)) < limit;
}