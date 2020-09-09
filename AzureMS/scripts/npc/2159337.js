var status;

var 이지맵 = 209000001;
var 이지보스 = 9303154;

var 노말맵 = 209000002;
var 노말보스 = 9303153;

var 하드맵 = 209000003;
var 하드보스 = 8880120;

var 헬맵 = 209000004;
var 헬보스 = 9300864;

function start() {
	status = -1;
	action(1, 0, 0);
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
		var chat = "#e사냥을 원하시는 #r보스#k를 선택해 주십시오#n\r\n\r\n";
		chat += "#L0##d이지 레이드#l#k#n\r\n";
		chat += "#L1##b노말 레이드#l#k#n\r\n";
		chat += "#L2##r하드 레이드#l#k#n\r\n";
		chat += "#L3##e#r헬 레이드#l#k#n";
		cm.sendSimple(chat);
	} else if (status == 1) {
		if (cm.getParty() != null) {
			if (selection == 0) {
				cm.resetMap(이지맵);
				cm.warp(이지맵);
				cm.spawnMob(이지보스, cm.getPlayer().getPosition().getX(), cm.getPlayer().getPosition().getY());
				cm.sendOk("#e건투를 빕니다.#n");
				cm.dispose();
			} else if (selection == 1) {
				cm.resetMap(노말맵);
				cm.warp(노말맵);
				cm.spawnMob(노말보스, cm.getPlayer().getPosition().getX(), cm.getPlayer().getPosition().getY());
				cm.sendOk("#e건투를 빕니다.#n");
				cm.dispose();
			} else if (selection == 2) {
				cm.resetMap(하드맵);
				cm.warp(하드맵);
				cm.spawnMob(하드보스, cm.getPlayer().getPosition().getX(), cm.getPlayer().getPosition().getY());
				cm.sendOk("#e건투를 빕니다.#n");
				cm.dispose();
			} else if (selection == 3) {
				cm.resetMap(헬맵);
				cm.warp(헬맵);
				cm.spawnMob(헬보스, cm.getPlayer().getPosition().getX(), cm.getPlayer().getPosition().getY());
				cm.sendOk("#e건투를 빕니다.#n");
				cm.dispose();
			}
		} else {
			cm.sendOk("#e파티가 없습니다.#n");
			cm.dispose();
		}
	}
}
