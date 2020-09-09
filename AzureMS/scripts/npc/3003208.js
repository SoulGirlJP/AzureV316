importPackage(Packages.tools);
importPackage(Packages.packet.creators);
importPackage(java.util);
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
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);


var status = 0;
var enter = "\r\n";

var time = 25; // 제한시간
var deathcount = 20; // 데스카운트
var bossname = "루시드" // 한글로
var limit = 3; // 하루 입장 가능 회수
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
		var 말 = "#fs11##d<#i2591590# :: #rLucid#k>#d"+enter;
                말 += "<Boss Reward : #i4001915# >"+enter;
		말 += "<Time Limit : #r"+time+" Mins#k #dDaily entries#k #d: #b"+cm.GetCount(bossname+"c", limit)+" Time#k / #r"+limit+" Times#k>#k"+enter;
                말 += "#d<Death Count :#k #r10 Timnes#k>#k"+enter;
		말 += "#L0##rI'm moving to kill the boss.#k";
        cm.sendSimple(말);
    } else if (status == 1) {
        if (cm.getPlayerCount(450004150) > 0 || cm.getPlayerCount(450004250) > 0) {
            cm.sendOk("Someone is already challenging Lucid.\r\nPlease try another channel.");
            cm.dispose();
        } else if (cm.getPlayer().getParty() == null) {
            cm.sendOk("This place is dangerous.\r\nPlease try to create a party.");
            cm.dispose();
        } else if (!cm.isLeader()) {
            cm.sendOk("Only the party leader can apply for admission.");
            cm.dispose();
        } else if (!cm.allMembersHere()) {
            cm.sendOk("All party members must gather here to enter.");
            cm.dispose();
        } else {
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
			var msg = (cm.getClient().getChannel()+1)+" In the channel "+cm.getPlayer().getName()+"'S party challenges Lucid.";
			WorldBroadcasting.broadcastMessage(MainPacketCreator.serverNotice(6, msg));
            		cm.resetMap(450004150);
            		cm.resetMap(450004250);
                    em = cm.getEventManager("Lucid");
                    eim = em.readyInstance();
                    eim.setProperty("Stage", "0");
			eim.setProperty("DeathCount", deathcount);
                    eim.setProperty("nextWarp", "false");
                    eim.setProperty("Global_MinPerson", cm.getParty().getMembers().size());
                	eim.startEventTimer(60000 * time);
                    eim.registerParty(cm.getParty(), cm.getMap());
                    cm.warpParty(startmap);
                    
        }
            if (time == 0) {
            
            eim.dispose();
            cm.warpParty(100000000);
            
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

                