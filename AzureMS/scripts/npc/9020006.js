importPackage(Packages.packet.creators);
importPackage(Packages.client);
importPackage(Packages.constants);
importPackage(Packages.launch.world);

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
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
	if(cm.getPlayer().getMapId() == 921160100) {
	if(status == 0) {
	cm.sendSimple("여기까지올라오시다니 대단합니다.\r\n바로 다음장소로 이동하겠습니까?\r\n#L1#다음 장소로 이동한다.#l");
	cm.showEffect(true,"quest/party/clear");
	} else if (status == 1) {
	if(selection == 0) {
		} else if (selection == 1) {
	cm.resetMap(921160200);
	cm.allPartyWarp(921160200,true);
	cm.showEffect(true,"Gstar/start");
	cm.getPlayer().getMap().respawn(true);
	cm.spawnMob(9300451,-576,-90);
	cm.spawnMob(9300451,116,-22);
	cm.spawnMob(9300451,1225,8);
	cm.spawnMob(9300451,660,4);
	cm.spawnMob(9300451,-37,-275);
	cm.spawnMob(9300451,574,-276);
	cm.spawnMob(9300450,-242,-219);
	cm.spawnMob(9300450,310,-270);
	cm.spawnMob(9300450,924,-264);
	cm.spawnMob(9300450,956,19);
	cm.spawnMob(9300450,319,21);
	cm.spawnMob(9300450,1167,-269);
	cm.dispose();
			}
		}
	} else if (cm.getPlayer().getMapId() == 921160200) {
	if(status == 0) {
	cm.sendSimple("바로 다음장소로 이동하겠습니까?\r\n#L0#다음 장소로 이동한다.#l");
	//cm.showEffect(true,"quest/party/clear");
	} else if (status == 1) {
        if (cm.getMonsterCount(921160200) > 0) {
  	cm.sendOk("모든 몬스터를 전멸시켜야 합니다.");
  	cm.dispose();
	} else {
	cm.resetMap(921160300);
	cm.allPartyWarp(921160300,true);
	cm.getPlayer().getMap().respawn(true);
	cm.spawnMob(9300449,-179,-138);
	cm.spawnMob(9300449,159,-144);
	cm.spawnMob(9300449,206,-411);
	cm.spawnMob(9300449,-205,-415);
	cm.dispose();
}
}
	} else if(cm.getPlayer().getMapId() == 921160350) {
	if(status == 0) {
	cm.sendSimple("바로 다음장소로 이동하겠습니까?\r\n#L0#다음 장소로 이동한다.#l");
	cm.showEffect(true,"quest/party/clear");
	} else if (status == 1) {
	if(selection == 0) {
	cm.resetMap(921160400);
	cm.allPartyWarp(921160400,true);
	cm.getPlayer().getMap().respawn(true);
	cm.spawnMob(9300452,2822,-139);
	cm.spawnMob(9300452,1724,-139);
	cm.spawnMob(9300452,2254,-139);
	cm.spawnMob(9300452,2060,-139);
	cm.spawnMob(9300452,1933,-139);
	cm.spawnMob(9300450,1906,-139);
	cm.spawnMob(9300450,1731,-139);
	cm.spawnMob(9300450,1661,-139);
	cm.spawnMob(9300450,2643,-139);
	cm.spawnMob(9300450,2459,-139);
	cm.spawnMob(9300450,2207,-139);
	cm.spawnMob(9300452,2482,-139);
	cm.spawnMob(9300452,1365,-139);
	cm.spawnMob(9300452,1508,-139);
	cm.spawnMob(9300452,1613,-139);
	cm.spawnMob(9300452,1841,-139);
	cm.dispose();
			}
		}
	} else if (cm.getPlayer().getMapId() == 921160400) {
	if(status == 0) {
	cm.sendSimple("바로 다음장소로 이동하겠습니까?\r\n#L0#다음 장소로 이동한다.#l");
	//cm.showEffect(true,"quest/party/clear");
	} else if (status == 1) {
        if (cm.getMonsterCount(921160400) > 0) {
  	cm.sendOk("모든 몬스터를 전멸시켜야 합니다.");
  	cm.dispose();
	} else {
	cm.allPartyWarp(921160500,true);
	cm.dispose();
			}
		}
	} else if (cm.getPlayer().getMapId() == 921160600) {
	if(status == 0) {
	cm.sendSimple("바로 다음장소로 이동하겠습니까?\r\n#L1#다음 장소로 이동한다.#l");
	//cm.showEffect(true,"quest/party/clear");
	} else if (status == 1) {
	if (cm.haveItem(4001528, 6)) {
	cm.gainItem(4001528 , -6);
	cm.resetMap(921160700);
	cm.allPartyWarp(921160700,true);
	cm.removeNpc(9020006);
	cm.spawnMob(9300454,-840,-181);
	cm.dispose();
	} else {
  	cm.sendOk("#i4001528# #z4001528#가 부족 합니다. 어서빨리 6개의열쇠를 모아주십쇼.");
	cm.dispose();
			}
		}
	} else if (cm.getPlayer().getMapId() == 921160700) {
	if(status == 0) {
	cm.sendSimple("도와주셔서 정말 감사드립니다. 덕분에 이곳을 벗어날 수 있게 되었습니다..\r\n#L1#이곳에서 나가시겠습니까?#l");
	//cm.showEffect(true,"quest/party/clear");
	} else if (status == 1) {
	if (cm.haveItem(4001528, 0)) {
	cm.gainItem(4001534 , 1);
	cm.warp(921160000,0);
	cm.dispose();
	} else {
  	cm.sendOk("#i4001528# #z4001528#가 부족 합니다. 어서빨리 열쇠를 모아주십쇼.");
	cm.dispose();
			}
		}
	} else if (cm.getPlayer().getMapId() == 921160500) {
	if(status == 0) {
	cm.sendSimple("바로 다음장소로 이동하겠습니까?\r\n#L1#다음 장소로 이동한다.#l");
	cm.showEffect(true,"quest/party/clear");
		} else if(status == 1) {
	cm.allPartyWarp(921160600,true);
	cm.dispose();
		}
	}
}