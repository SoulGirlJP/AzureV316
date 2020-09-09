// Lotus Boss
// Script : 1404005



importPackage(Packages.client.items);

importPackage(Packages.server.items);
importPackage(Packages.tools);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);
importPackage(Packages.server);
importPackage(Packages.tools.packet);
importPackage(Packages.tools.RandomStream);
importPackage(Packages.server.life);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);

var enter = "\r\n";
var data, date, day;
var boxmsg = "#fs11#Party play reward.#b"+enter;
var seld = -1;

/*	Reward	*/

var reward = 4001914; // Party Personal Reward

var rewardlist = [
{'item' : 4001879, 'min' : 1, 'max' : 2, 'chance' : 10000},
{'item' : 4310218, 'min' : 1, 'max' : 2, 'chance' : 10000}
]

/*	Boss Information Settings	*/

var bossid = 9801028; // Boss mob code
var secondboss = 9801029; // (Lucid, Swooman only, 2 Pb mob)
var startmap = 350060180; // Boss map
var secondmap = 350060200; // (Lucid, Swooman only, 2 pages map)
var exitmap = 410000060; //Open space (exit map)
var bossname = "Swoo" // In Hangul
var limit = 3; // The number of times you can enter Solfle
var time = 25; // Time limit
//1135 48
var x = -10, y = -16; // Boss coordinates
var x_2 = 226, y_2 = -16; // (Lucid, Suman only, 2 pe boss coordinates)
var deathcount = 15; // Death Count

/*------------------------------*/

var bossname2 = "Patisseu";
var limit2 = 3;
var 파티보스피1 = 500000000000000;
var 파티보스피2 = 1000000000000000;
var reward = 4001914; // Particle Reward


var 솔로보상 = 4001900, 개수 = 2;

function start() {
	status = -1;
	action(1, 0, 0);
}
/*function unboxing() {
	var ab = 0, qa = 0;
	for (i = 0; i < rewardlist.length; i++) {
		qb = Randomizer.rand(rewardlist[i]['min'], rewardlist[i]['max']);
		tchance = Randomizer.rand(1, 10000);
		if (tchance <= rewardlist[i]['chance']) {
			cm.gainItem(rewardlist[i]['item'], qb);
			ab++;
			boxmsg += "#b#i"+rewardlist[i]['item']+"##z"+rewardlist[i]['item']+"# "+qb+"개#k"+enter; 
		} // I finally caught a giant
	}
	boxmsg += "Gun "+ab+"Rewards!";
}*/
function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
		if (cm.getPlayer().getReborns() < 100 && !cm.getPlayer().isGM()) {
			cm.sendOk("A boss who can challenge only one character over 100 rebirths.");
			cm.dispose();
			return;
		}
/*
		if (!cm.getPlayer().isGM()) {
			cm.sendOk("패치중");
			cm.dispose();
			return;
		}
*/
		var msg = "#fs11##d<#i2591427# :: #r Heart of Swoo Black Haven #k>#d"+enter;
                msg += "Boss compensation : #i4001914#>"+enter;
		msg += "<Time limit : #r"+time+"time#k>"+enter;
                msg += "#d<Death count :#k #r15 Minute#k>#k"+enter;
		msg += "#L1#[Party] I'm moving to kill the boss. <#b"+cm.GetCount(bossname2+"c", limit2)+"time / #r"+limit2+"time#k>"+enter;
		msg += "#L3#I would like to receive a party play reward.";
		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel;
		if (sel == 3) {
			if (cm.haveItem(reward, 1)) {
				var msg = "#fs11#Would you like to receive a reward?"+enter;
				msg += "#b* All rewards are paid randomly."+enter;
				msg += "* Please press 'Yes' to receive.";
				cm.sendYesNo(msg);
			} else {
				var msg = "#fs11#Have a good time in AzureMS."+enter;
				msg += "#b* Swoo's kill reward comes from catching Swoo #i"+reward+"##z"+reward+"#You will be paid when you import."+enter;
				cm.sendOk(msg);
				cm.dispose();
				
			}
		} else {

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
		if (cm.getPlayerCount(startmap) > 0 || cm.getPlayerCount(secondmap) > 0) {
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

		bossc = sel == 1 ? bossname2 : bossname;
		lm = sel == 1 ? limit2 : limit;
            	var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
		var dd = 0;
            	var countPass = true;
            	while (it.hasNext()) {
                	var chr = it.next();
			dd++;
                	if (!CC(chr, bossc+"c", lm)) {
                    		countPass = false;
                    		break;
                	}
            	}

            	if (!countPass) {
                	cm.sendOk("There are some party members who have no dungeon entry.");
                	cm.dispose();
                	return;
            	} else {
			if (dd != 1 && sel == 2) {
                		cm.sendOk("Not suitable for solo mode conditions."+dd);
                		cm.dispose();
                		return;
			}
            		var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            		var countPass = true;
            		while (it.hasNext()) {
                		var chr = it.next();
				AC(chr, bossc+"c");
            		}


		}
		if (sel == 1) {
            		var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            		var countPass = true;
            		while (it.hasNext()) {
                		var chr = it.next();
				chr.setKeyValue("Patisseu reward", reward+"");
				chr.setKeyValue("Patisseu", "1");
				chr.setKeyValue("Patisseu 2", 파티보스피2+"");
            		}
		} else {
            		var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            		var countPass = true;
            		while (it.hasNext()) {
                		var chr = it.next();
				chr.setKeyValue("Patisseu", "0");
				chr.setKeyValue("Soloth reward", 솔로보상);
				chr.setKeyValue("Solos", 개수);
            		}
		}
		cm.resetMap(startmap);
		cm.resetMap(secondmap);
			var msg = (cm.getClient().getChannel()+1)+"In the channel "+cm.getPlayer().getName()+"'S party will challenge Su.";
			WorldBroadcasting.broadcastMessage(MainPacketCreator.serverNotice(6, msg));
		var em = cm.getEventManager("hboss");
		var eim = em.readyInstance();
		eim.setProperty("StartMap", startmap);
		eim.setProperty("SecondMap", secondmap);
		eim.setProperty("ExitMap", exitmap);
		eim.setProperty("BossName", bossc);
                eim.setProperty("PHp1", 파티보스피1);
                eim.setProperty("PHp2", 파티보스피2);
		eim.setProperty("Boss_ID", bossid);
		eim.setProperty("Boss_Second", secondboss);
		eim.setProperty("Boss_x", x);
		eim.setProperty("Boss_y", y);
		eim.setProperty("Boss_x_2", x_2);
		eim.setProperty("Boss_y_2", y_2);
		eim.setProperty("KillCount", 0);
		eim.setProperty("Reward", reward);
		eim.setProperty("Leader", cm.getPlayer().getParty().getLeader().getName());
		eim.setProperty("DeathCount", deathcount);
                eim.startEventTimer(60000 * time);
		eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
                cm.warpParty(startmap);
		cm.dispose();

		}
                
                        if (time == 0) {
            
            eim.dispose();
            cm.warpParty(100000000);
            
        }
	} else if (status == 2) {
		if (seld == 3) {
			cm.gainItem(reward, -1);
			unboxing();
			cm.sendOk(boxmsg);
			cm.dispose();
		}
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
