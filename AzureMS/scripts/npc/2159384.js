/* 경찰 전용 맵 엔피시 입니다. */
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);
importPackage(java.util);
importPackage(java.lang);

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0 && status == 0) {
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
	if(cm.getPlayer().getMapId() != 234567897) {
	cm.dispose(); return;
	}
	map = cm.getClient().getChannelServer().getMapFactory().getMap(234567899);
	names = map.names.split(",");
		if(cm.getPlayer().isDead) {
			cm.dispose(); return;
		}
			if(cm.getPlayer().isPoliceVote) {
				var text = "마피아 일것같은 사람을 지목해 주세요.\r\n";
					for(i=0;i< names.length; i++) {
					try {
						if(cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[i]).mapiajob != "경찰") {
						//text += cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[i]).mapiajob+ "";
							text += "#L"+i+"##b"+names[i]+"#k";
						}
						if(cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[i]).isDead) {
							text += "[사망]#l\r\n";
						} else {
							text += "#l\r\n";
						}
					    } catch(e) {
							text += "";
					    }
					}
				cm.sendSimple(text);
			} else {
				cm.sendOk("더이상 선택하실 수 없습니다.");
				cm.dispose(); return;
			}
    } else if(status == 1) {
	sel = selection;
	if(cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[sel]).isDead) {
		cm.sendOk("사망한 사람은 살릴 수 없습니다."); cm.dispose(); return;
	} else {
		cm.sendYesNo("정말 #b"+names[sel]+"#k님 을(를) 마피아로 지목하시겠습니까?");
	}
    } else if(status == 2) {
					for(a=0;a<names.length;a++) {
						if(cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[a]).getpolicevote == 1) {
						cm.sendOk("이미 팀원이 지목을하여 더이상 지목하실 수 없습니다."); cm.dispose(); return;
						}
					}
	if(cm.getPlayer().isPoliceVote) {
		cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[sel]).getpolicevote = 1;
		cm.getPlayer().isPoliceVote = false;
		cm.getClient().getChannelServer().getMapFactory().getMap(234567897).broadcastMessage(MainPacketCreator.serverNotice(5, cm.getPlayer().getName()+"님이 "+names[sel]+"님을 마피아로 선택셨습니다. 마피아 인지 아닌지는 아침에 발표됩니다."));
		//map.broadcastMessage(MainPacketCreator.serverNotice(5, cm.getPlayer().getName()+"님이 "+names[sel]+"님을 마피아로 선택셨습니다. 마피아 인지 아닌지는 아침에 발표됩니다."));
		cm.sendOk(names[sel]+"님을 마피아로 선택하셨습니다.");
		cm.dispose(); return;
	} else {
		cm.sendOk("더이상 선택하실 수 없습니다.");
		cm.dispose(); return;
	}
    }
}