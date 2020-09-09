// Pink Bean Boss
// Script : 2141000

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
var cost = 226800000; // Summon Price
var bossname = "Chaos Pink Bean";//Mob name to be checked
var startmap = 270051100;//Map code
var x = 5;//x-coordinate
var y = -42;//y-coordinate
var hp = "378 billion";
var hp1 = "31.5 billion (total X5)";
var hp2 = "63 billion";
var limit = 5;
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
		if (cm.getPlayer().getMapId() != startmap) {
			var msg = "#fs11##d<#i2591087# :: #r Twilight Pink Bean of the Gods #k>#d"+enter;
               		msg += "<Boss reward : #i4001903#>"+enter;
			msg += "<#dDaily entries#k #d: #b"+cm.GetCount(bossname+"c", limit)+" Time#k / #r"+limit+" Times#k>#k"+enter;
                	msg += "#d<Death count :#k #r15 Times#k>#k"+enter;
			msg += "#L1##rI'm moving to kill the boss.#k";
		cm.sendSimple(msg);
		} else {
			cm.sendOk("Cuckoo...");
			cm.dispose();
		}
	} else if (status == 1) {
		if(!cm.CountCheck(bossname+"c", limit)) {
			cm.sendOk("Per day "+limit+" Only one time.");
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
		cm.allPartyWarp(startmap,true);
 		cm.dispose();
		/*cm.spawnMobOnMap(8820115, 1, x, y, startmap);
		cm.spawnMobOnMap(8820116, 1, x, y, startmap);
		cm.spawnMobOnMap(8820117, 1, x, y, startmap);
		cm.spawnMobOnMap(8820118, 1, x, y, startmap);
		cm.spawnMobOnMap(8820102, 1, x, y, startmap);*/
		cm.spawnMobOnMap(8820210, 1, x, y, startmap); // Chaos Pink Bean
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