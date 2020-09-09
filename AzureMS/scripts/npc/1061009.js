var status = -1;
var chr;
var itemlist = Array(Array(1142009,1000),Array(2048304,300), Array(2049300,100), Array(2049400,100));
var sel = 0;
var info = "";
var sel2 = 0;

function start(infot, chrs) {
    cm.dispose(); // TODO: REMOVE => WOLLY ADDED THIS
    return; // TODO: REMOVE => WOLLY ADDED THIS
    
    status = -1;
	
    if (infot != null) 
	info = infot;

    if (info != "")
	action1 (1, 0, 0, chrs);
    else
    	action (1, 0, 0);
}

function action1(mode, type, selection, chrs) {
	if (chrs != null)
		chr = chrs;

	if (mode == 1) {
		status ++;
	} else {
		cm.dispose();
		return;
	}
	if (status == 0) {
		cm.sendYesNo(info + "\r\n님으로 부터 대전신청이 들어 왔습니다. 승낙할시 바로 PVP가 시작됩니다.");
	} else {
		if (cm.getChar(chr.getName()) == null) {
			cm.sendOk(chr.getName() + "님이 현재 이 맵에 존재하지 않아요.");
			cm.dispose();
			return;
		}
		cm.timeMoveMap(100000000,960040002,600);
		chr.timeMoveMap(100000000,960040002,600);
		cm.getPlayer().dropMessage(1,"제한시간 10분안에 상대방을 먼저 처치 하는쪽이 승리 합니다.");
		chr.dropMessage(1,"제한시간 10분안에 상대방을 먼저 처치 하는쪽이 승리 합니다.");
		cm.dispose();
	}
}

function action(mode, type, selection) {

    if (mode == 1) {
        status++;
    } else {
	cm.dispose();
	return;
    }
    if (info != "") {
	action1 (1, type, selection, null);
	return;
    }
    if (status == 0) {
        var chat = "진정한 현ㅍ.. 가 아니고!! 대결을 원하신다면 PVP 한판!! 어떠세요?";
	chat += "\r\n#b#L0##e1 vs 1 PVP를 이용 하겠습니다.#l";
	//chat += "\r\n#L2#배틀 포인트로 아이템을 교환 하겠습니다.#l";
	//chat += "\r\n#L3#배틀 포인트를 초기화 하겠습니다.#l";
	cm.sendSimple(chat);
    } else if (status == 1) {
	sel = selection;
	if (selection == 0) {
		if (cm.getPlayerCount(960040002) > 0) {
			cm.sendOk("이미 다른 플레이어들이 PVP를 이용하고 있으니 다른채널을 이용해주시면 감사하겠어요");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getRankPoint() < 0) {
			cm.sendOk("PVP를 이용하기 위해선 배틀 포인트가 0이상은 필요 합니다. #Cgray#배틀 포인트 초기화 기능을 이용해 주세요");
			cm.dispose();
			return;
		}
		cm.sendGetText("대결을 원하는 상대방의 닉네임을 입력해 주세요.");
	} else if (selection == 2) {
		var chat = "교환 하고싶은 아이템을 선택해보세요.";
		chat += "\r\n\r\n";
		chat += "　◆ #b#h #님의 보유 배틀포인트 : #e#r"+cm.getPlayer().getRankPoint() +"#n#k\r\n";
		for (var i = 0; i < itemlist.length; i ++) {
			chat += "#L" + i + "# #b#i" + itemlist[i][0] + "# #z" + itemlist[i][0] + "# #k#e: " + itemlist[i][1] + "포인트#n#l\r\n";
		}
		cm.sendSimple(chat);
	} else if (selection == 3) {
		cm.sendYesNo("배틀 포인트를 초기화를 하는데 필요한 비용은 #e#b100,000,000 메소#n#k가 필요 하며 배틀 포인트가 500 포인트로 설정 됩니다.");
	}
    } else if (status == 2) {
	if (sel == 0) {
		if (cm.getPlayer().getName() == cm.getText()) {
			cm.sendOk("본인과의 싸움은 말이 안되잖아요!!");
			cm.dispose();
			return;
		}
		chr = cm.getChar(cm.getText());
		if (chr != null) {
			cm.sendYesNo("#Cgray##e닉네임 : " + chr.getName() + "\r\n#r레벨 : " + chr.getLevel() + "#n#k\r\n님에게 정말로 대전 신청을 보내시겠어요?");
		} else {
			cm.sendOk("입력하신 분은 현재 이맵에 계시지 않아요");
			cm.dispose();
		}
	} else if (sel == 2) {
		sel2 = selection;
		cm.sendYesNo("#i" + itemlist[sel2][0] + "# #Cgray##e" + itemlist[sel2][0] + "#r\r\n" + itemlist[sel2][1] + "배틀 포인트\r\n을 정말로 교환 하시겠어요?");
	} else if (sel == 3) {
		if (cm.getPlayer().getMeso() >= 100000000) {
			cm.getPlayer().setRankPoint(500);
			cm.sendOk("배틀 포인트 초기화를 완료 하였습니다.");
			cm.dispose();
		} else {
			cm.sendOk("배틀 포인트를 초기화 하기 위해선 #e#b100,000,000 메소#n#k가 필요 합니다.");
			cm.dispose();
		}
	}
    } else if (status == 3) {
	if (sel == 0) {
		if (chr.getLevel() >= cm.getPlayer().getLevel()) {
			if ((chr.getLevel() - cm.getPlayer().getLevel()) > 50) {
				cm.sendOk("PVP는 상대와 레벨 50 이상,이하 차이날시 이용이 불가능 합니다.");
				cm.dispose();
				return;
			}
		} else if (chr.getLevel() <= cm.getPlayer().getLevel()) {
			if ((cm.getPlayer().getLevel() - chr.getLevel()) > 50) {
				cm.sendOk("PVP는 상대와 레벨 50 이상,이하 차이날시 이용이 불가능 합니다.");
				cm.dispose();
				return;
			}
		}
		cm.sendOk("#Cgray##e닉네임 : " + chr.getName() + "\r\n#r레벨 : " + chr.getLevel() + "#n#k\r\n님에게 대전 신청을 보냈습니다. 상대방이 대전 신청을 승낙 할시 바로 PVP가 시작 됩니다.");
		cm.sendPVP("#Cgray##e닉네임 : " + cm.getPlayer().getName() + "\r\n#r레벨 : " + cm.getPlayer().getLevel() + "#n#k", cm.getPlayer(), chr);
		cm.dispose();
	} else if (sel == 2) {
		if (cm.getPlayer().getRankPoint() >= itemlist[sel2][1]) {
			if (!cm.canHold(itemlist[sel2][0])) {
				cm.sendOk("선택하신 아이템을 교환 하기에는 인벤토리에 공간이 부족합니다.");
				cm.dispose();
				return;
			}
			cm.gainItem(itemlist[sel2][0],1);
			cm.getPlayer().addRankPoint(-itemlist[sel2][1]);
			cm.sendOk("선택하신 아이템을 지급 해드렸으니 인벤토리를 확인해 보세요.");
			cm.dispose();
		} else {
			cm.sendOk("선택하신 아이템을 교환하기에는 배틀 포인트가 부족해요");
			cm.dispose();
		}
	}
    }
}