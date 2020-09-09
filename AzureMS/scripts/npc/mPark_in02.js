


/*

	퓨어 온라인 소스 팩의 스크립트 입니다.

        제작 : 주크블랙

	엔피시아이디 : 9071004
	
	엔피시 이름 : 게이트

	엔피시가 있는 맵 : 몬스터파크

	엔피시 설명 : in02 포탈 게이트


*/


var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
	if (cm.getPlayer().getDateKey3("mP_00") == -1) cm.getPlayer().setDateKey3("mP_00", 0);
	if (cm.getPlayer().getDateKey3("mP_00") >= 100) {
		cm.sendOk("하루에 3번 이상 이용하실 수 없습니다.");
		cm.dispose();
		return;
	}
        var text = "어느 곳으로 입장하시겠습니까?\r\n#r(레벨 160이상 유저 이용 가능)\r\n#b";
        	text += "#fs11##b#h #님의 몬스터파크 이용 가능 횟수 : "+(3 - cm.getPlayer().getDateKey3("mP_00"))+"#k\r\n";
		text += "#L1#폐허가 된 도시(Lv.160~169)#l\r\n" +
		"#L2#죽은 나무의 숲(Lv.170~179)#l\r\n" +
		"#L3#감시의 탑(Lv.175~184)#l\r\n" +
		"#L4#용의 둥지(Lv.180~189)#l\r\n" +
		"#L5#망각의 신전(Lv.185~194)#l\r\n" +
		"#L6#기사단의 요새(Lv.190~199)#l\r\n" +
		"#L7#원혼의 협곡(Lv.200~209)#l\r\n" +
		"#L8#소멸의 여로(Lv.200~209)\r\n" +
		"#L9#츄츄 아일랜드(Lv.210~219)\r\n"
        cm.sendSimple(text);
    } else if (status == 1) {
        var map = 100000000;
        switch (selection) {
            case 1:
                map = 954000000;
                break;
            case 2:
                map = 954010000;
                break;
            case 3:
                map = 954020000;
                break;
            case 4:
                map = 954030000;
                break;
            case 5:
                map = 954040000;
                break;
            case 6:
                map = 954050000;
                break;
            case 7:
                map = 954060000;
                break;
			case 8:
                map = 954070000;
                break;
			case 9:
                map = 954080000;
                break;
        }
        
        
			for (var i = 0; i < 6; i ++) {
				if (cm.getPlayerCount(map + (i * 100)) > 0) {
					cm.sendOk("이미 다른 파티가 몬스터 파크를 이용 중 입니다.");
					cm.dispose();
					return;
				}
			}
        var party = cm.getPlayer().getParty();
        if (party == null) {
            if (!checkLevel(cm.getPlayer().getLevel(), 160, 275)) {
                cm.sendOk("파티원 중 레벨이 맞지 않는 파티원이 있습니다.\r\n#r160레벨 이상#k의 파티원만 입장할 수 있습니다.");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4001522, 1)) {
                cm.sendOk("파티원 중 입장권을 소지하지 않은 파티원이 있습니다.\r\n#r#i4001522# #t4001522##k이 있어야 입장할 수 있습니다.");
                cm.dispose();
                return;
            }
            
            
            cm.gainItem(4001522, -1);
            var em = cm.getEventManager("MonsterPark");
            var eim = em.readyInstance();
            eim.setProperty("Global_StartMap", map);
            eim.setProperty("Global_ExitMap", "951000000");
            eim.setProperty("Global_MinPerson", "1");
            eim.setProperty("Global_RewardMap", "951000000");
            eim.setProperty("CurrentStage", "1");
            eim.startEventTimer(1200000);
            eim.registerPlayer(cm.getPlayer());
		cm.getPlayer().setDateKey3("mP_00", cm.getPlayer().getDateKey3("mP_00") + 1);
            cm.dispose();
        } else {
            if (!cm.isLeader()) {
                cm.sendOk("파티장이 입장신청을 할 수 있습니다.");
                cm.dispose();
                return;
            }
            if (!cm.allMembersHere()) {
                cm.sendOk("파티원이 전원 이곳에 모여있어야 합니다.");
                cm.dispose();
                return;
            }
            var it = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            var ticketPass = true;
            var levelPass = true;
            while (it.hasNext()) {
                var chr = it.next();
                if (!chr.haveItem(4001522, 1, false, true)) {
                    ticketPass = false;
                    break;
                }
                if (!checkLevel(chr.getLevel(), 160, 250)) {
                    levelPass = false;
                    break;
                }
            }
            if (!ticketPass) {
                cm.sendOk("파티원 중 입장권을 소지하지 않은 파티원이 있습니다.\r\n#r#i4001522# #t4001522##k이 있어야 입장할 수 있습니다.");
                cm.dispose();
                return;
            }
            if (!levelPass) {
                cm.sendOk("파티원 중 레벨이 맞지 않는 파티원이 있습니다.\r\n#r165레벨 이상#k의 파티원만 입장할 수 있습니다.");
                cm.dispose();
                return;
            }
            var toRem = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
            while (toRem.hasNext()) {
                var chr = toRem.next();
                cm.gainItemTarget(chr, 4001522, -1);            }
            var em = cm.getEventManager("MonsterPark");
            var eim = em.readyInstance();
            eim.setProperty("Global_StartMap", map);
            eim.setProperty("Global_ExitMap", "951000000");
            eim.setProperty("Global_MinPerson", "1");
            eim.setProperty("Global_RewardMap", "951000000");
            eim.setProperty("CurrentStage", "1");
            eim.startEventTimer(1200000);
            eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
		cm.getPlayer().setDateKey3("mP_00", cm.getPlayer().getDateKey3("mP_00") + 1);
            cm.dispose();
        }
    }
}


function checkLevel(cur, min, max) {
    return (cur >= min && cur <= max);
}