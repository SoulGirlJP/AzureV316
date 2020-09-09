
/* 의사 전용 맵 엔피시 입니다. */
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
	if(cm.getPlayer().getMapId() != 234567898) {
	cm.dispose(); return;
	}
	map = cm.getClient().getChannelServer().getMapFactory().getMap(234567899);
	names = map.names.split(",");
		if(cm.getPlayer().isDead) {
			cm.dispose(); return;
		}
			if(cm.getPlayer().isDrVote) {
				var text = "살리시길 원하는 사람을 선택 해 주세요.\r\n";
					for(i=0;i< map.playern; i++) {
					try {
						if(cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[i]).isDead) {
							text += "#Cgray##L"+i+"##b"+names[i]+"#k";
							text += "[사망]#l\r\n";
						} else {
							text += "#k#L"+i+"##b"+names[i]+"#k#l\r\n";
						}
					   } catch(e) {
							text += "";
					    }
					}
			} else {
				cm.sendOk("더이상 살릴 수 없습니다.");
				cm.dispose(); return;
			}
			cm.sendSimple(text);
    } else if(status == 1) {
	sel = selection;
	if(cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[sel]).isDead) {
		cm.sendOk("사망한 사람은 살릴 수 없습니다."); cm.dispose(); return;
	} else {
		cm.sendYesNo("정말 #b"+names[sel]+"#k을(를) 살리시겠습니까?");
	}
    } else if(status == 2) {
	if(cm.getPlayer().isDrVote) {
		cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(names[sel]).getdrvote = 1;
		cm.getPlayer().isDrVote = false;
		map.broadcastMessage(MainPacketCreator.serverNotice(5, cm.getPlayer().getName()+"님이 "+names[sel]+"님을 살리셨습니다."));
		cm.sendOk(names[sel]+"님 을 살렸습니다..");
		cm.dispose(); return;
	} else {
		cm.sendOk("더이상 살릴 수 없습니다.");
		cm.dispose(); return;
	}
    }
}