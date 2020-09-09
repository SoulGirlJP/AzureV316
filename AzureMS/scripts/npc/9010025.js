importPackage(java.lang);

var coinui = "#fItem/Etc/0431/04310034/info/iconShop#"
var star = "#fUI/FarmUI.img/objectStatus/star/whole#";

/* Here is the boss setting */
var bossname = ["Faust", "King Cran", "Debbie Zone", "Dragonnica", "Dragon Rider", "Tin Hunter", "Dorothy", "Aufheaven"];
var bosscoin = [10, 20, 30, 50, 60, 80, 110, 130];
var bosshp = ["2000 Billion", "4000 Billion", "7000 Billion", "1조2000 Billion", "1조4000 Billion", "2조", "3조4000 Billion", "4조 6000 Billion"];
var bosscode = [5220002, 5220000, 9300119, 8300006, 8300007, 9309205, 9309208, 8220011];
var bossxy = [[797, 19], [797, 19], [797, 19], [797, 19], [797, 19], [797, 19], [797, 19], [797, 19]];
// bossxy means [x, y]. You can set the coordinates in the order of boss mobs. An easy way is to use the same coordinates as long as the bossmap below is the same.
var bossmap = [500000001, 500000001, 500000001, 500000001, 500000001, 500000002, 500000002, 500000002];
var in_limited = 30; // No entry

function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (cm.getPlayer().getDateKey("저코보스", false) == null) {
		cm.getPlayer().setDateKey("저코보스", "0", false);
		cm.getPlayer().dropMessage(5, "enter");
	}
	if (status == 0) {
		bossin = parseInt(cm.getPlayer().getDateKey("저코보스", false));
		lastlimited = in_limited - bossin;
		var ms = "#fnSharing Gothic##fs13#"
		ms += "		   " + star + "#r[ AzureMS boss system ]#n#k" + star + "\r\n\r\n"
		ms += "		       My remaining boss entry : " + lastlimited + "\r\n"
		for (i = 0; i < bossname.length; i++) {
			ui = "#fUI/UIWindow2.img/MobGage/Mob/" + bosscode[i] + "#"
			ms += "#b#L" + i + "#" + ui + " " + bossname[i] + " [ HP : " + bosshp[i] + " ] " + coinui + " " + bosscoin[i] + "#l\r\n"
		}
		cm.sendNext(ms);
	} else if (status == 1) {
		sel = selection;
		injoy = true;
		// From here
               if(!cm.getPlayer().isGM()){
		if(!cm.CountCheck("저코보스", in_limited)) {
			cm.sendOk("Per day " + in_limited + " You can only enter once.");
			cm.dispose();
			return;
		}
            }
		if (cm.getPlayer().getParty() == null) {
			cm.sendOk("Please organize your party first.");
			cm.dispose();
			return;
		}
		if (!isPartyLeader()) {
			cm.sendOk("Only the party leader can speak to me and enter.");
			cm.dispose();
			return;
		}
            		if (!cm.allMembersHere()) {
                		cm.sendOk("All party members should be on the same map as the party leader.");
			cm.dispose();
                		return;
            		}
		for (i = 0; i < bossmap.length; i++) {
			if (cm.getPlayerCount(bossmap[i]) > 0 || cm.getPlayerCount(350060200) > 0) {
            			injoy = false;
        			}
		}
		// Limit check to here
		// From here
            		var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            		var countPass = true;
            		while (it.hasNext()) {
                		var chr = it.next();
                		if (!CC(chr, "저코보스", in_limited)) {
                    			countPass = false;
                    			break;
                		}
            		}
		if (!injoy) {
			cm.sendOk("Someone is already challenging.\r\n#bPlease try another channel.#k");
            		cm.dispose();
			return;
		}
		// Party member entrance check up to here
            		if (!countPass) { // If you get stuck at a party member check
                		cm.sendOk("There are some party members who have no dungeon entry.");
                		cm.dispose();
                		return;
            		} else { // When we did not check party member entrance check
		// From here
            			var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            			var countPass = true;
            			while (it.hasNext()) {
                			var chr = it.next();
				AC(chr, "저코보스");
            			}
		}
		// Deduction of entrance number to here
		cm.resetMap(bossmap[sel]);
		cm.resetMap(350060200); // Exception Swoo 2 Phase Map
		var em = cm.getEventManager("hboss"); // Event script name for boss system
		var eim = em.readyInstance();
		eim.setProperty("StartMap", bossmap[sel]); // Boss Event Start Map Settings
		eim.setProperty("BossName", bossname[sel]); // Boss Event Set Boss Name
		eim.setProperty("Boss_ID", bosscode[sel]); // Boss Event Boss Code Settings
		eim.setProperty("Boss_x", bossxy[sel][0]); // Boss Event Boss X Coordinate Settings
		eim.setProperty("Boss_y", bossxy[sel][1]); // Boss event boss Y coordinate setting
		eim.setProperty("KillCount", 0); // I don't know what
		eim.setProperty("Leader", cm.getPlayer().getParty().getLeader().getName()); // Set Boss Event Party Leader Name
		eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap()); // Go to the map set above
		cm.dispose();
	}
}


function isPartyLeader() {
	if (cm.getPlayer().getParty().getLeader().getId() == cm.getPlayer().getId()) {
		return true;
	} else {
		return false;
	}
}

function AC(player, boss) {
	player.setDateKey(boss, Integer.parseInt(player.getDateKey(boss, false)) + 1, false);
}

function CC(player, boss, limit) {
	if (player.getDateKey(boss, false) == null) {
		player.setDateKey(boss, "0", false);
	}
	return Integer.parseInt(player.getDateKey(boss, false)) < limit;
}