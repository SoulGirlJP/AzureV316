// Chaos Horntail
// Script : 9000325


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
var status = -1;
var cost = 226800000; // 소환가격
var bossname = "카오스혼테일";//체크될 몹 이름
var bossname2 = "혼테일";//체크될 몹 이름
var startmap = 240060200;//맵 코드
var startmap2 = 240060201;//맵 코드
var x = 71;//x좌표
var y = 260;//y좌표
var hp = "378 억";
var hp1 = "315 억 (총 X5)";
var hp2 = "63 억";
var limit1 = 5;
var limit2 = 5;
var enter = "\r\n";
importPackage(Packages.constants);

function start() {
 action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

 	if (status == 0) {
		var msg = "#fs11##d<#i2041200# :: #r Horntail #k>#d"+enter;
                msg += "<Boss Reward : #i4001897#>"+enter;
                msg += "#d<Death Count :#k #r15 Times#k>#k"+enter;
		msg += "#L1#<Chaos> I'm moving to kill the boss. #b<"+cm.GetCount(bossname+"c", limit1)+"#k/#r"+limit1+"#k>#k\r\n";
		//msg += "#L2#<노말> 보스를 처치하러 이동하겠습니다. #b<"+cm.GetCount(bossname2+"c", limit2)+"#k/#r"+limit2+"#k>#k";
		cm.sendSimple(msg);
	} else if (status == 1) {
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
		if (selection == 1) {
		if(!cm.CountCheck(bossname+"c", limit1)) {
			cm.sendOk("Per day "+limit1+"Only one time.");
			cm.dispose();
			return;
		}
		if (cm.getPlayerCount(startmap2) > 0) {
            		cm.sendOk("Someone is already challenging.\r\n#bPlease try another channel.#k");
            		cm.dispose();
			return;
        	}
            	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
                	if (!CC(chr, bossname+"c", limit1)) {
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
			cm.resetMap(startmap2);
			cm.allPartyWarp(startmap2,true);
 			cm.spawnMob(8810122,x,y);
 			cm.spawnMob(8810102,x,y);
 			cm.spawnMob(8810103,x,y);
 			cm.spawnMob(8810104,x,y);
 			cm.spawnMob(8810105,x,y);
 			cm.spawnMob(8810106,x,y);
 			cm.spawnMob(8810107,x,y);
 			cm.spawnMob(8810108,x,y);
			cm.spawnMob(8810109,x,y);
		} else {
		if(!cm.CountCheck(bossname2+"c", limit2)) {
			cm.sendOk("Per day "+limit2+" Only one time.");
			cm.dispose();
			return;
		}
		if (cm.getPlayerCount(startmap) > 0) {
            		cm.sendOk("Someone is already challenging.\r\n#bPlease try another channel.#k");
            		cm.dispose();
			return;
        	}
            	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
                	if (!CC(chr, bossname2+"c", limit2)) {
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
			AC(chr, bossname2+"c");
            	}
		}
			cm.resetMap(startmap);
			cm.allPartyWarp(startmap,true);
 			cm.spawnMob(8810122,x,y); // was 8810118
 			cm.spawnMob(8810002,x,y);
			cm.spawnMob(8810003,x,y);
 			cm.spawnMob(8810004,x,y);
 			cm.spawnMob(8810005,x,y);
 			cm.spawnMob(8810006,x,y);
 			cm.spawnMob(8810007,x,y);
 			cm.spawnMob(8810008,x,y);
			cm.spawnMob(8810009,x,y);
		}
 		cm.changeMusic("Bgm14/HonTale");
 		cm.dispose();
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