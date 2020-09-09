var status = 0;
var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
별빨 = "#fUI/GuildMark.img/Mark/Pattern/00004001/1#"
별파 = "#fUI/GuildMark.img/Mark/Pattern/00004001/11#"
별보 = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"
별회 = "#fUI/GuildMark.img/Mark/Pattern/00004001/15#"
importPackage(Packages.constants);

function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) { if (mode == -1) { cm.dispose(); } else { if (mode == 0) { cm.dispose(); return; } if (mode == 1) status++; else status--;

    if (status == 0) {
		
		var chat = "              #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 환생시스템 "+별+"\r\n#fs10##Cgray#                                "+ServerConstants.serverName+"의 환생시스템입니다.#k\r\n\r\n#fs12##b▶ #h # 님의 환생횟수는 "+cm.Comma(cm.getReborns())+" 회입니다.#k\r\n#r▶ #h #님의 레벨은 "+cm.getPlayer().getLevel()+" 레벨 입니다.#k\r\n\r\n";
	 	chat += "\r\n#L1##fs 13##s9001005##e#r  환생 시스템#d ( 환생,변생 이용하기 )\r\n";
	 	chat += "\r\n#L6##fs 13##s80001082##e#r 각성 시스템#d ( 환생하면 할수있다! )\r\n";
	 	chat += "\r\n#L2##fs 13##s9001005##e#r  환포 추뎀#d ( 환포추뎀 교환 )\r\n";
	 	chat += "\r\n#L3##fs 13##s80001081##e#r  환코 검색캐시#d ( 환포추뎀 교환 )\r\n";
		chat += "\r\n#L4##fs 13##s80001079##e#r  환생 상점이용#d ( 환생포인트 상점 )";
		chat += "\r\n#L5##fs 13##s80001080##e#r  환코 후포교환#d ( 환코교환 상점 )";
		cm.sendSimple(chat);	

	} else if (status == 1) {

	if (selection == 1) {
		cm.dispose();
		cm.openNpc(2491006);

	} else if (selection == 2) {
		cm.dispose();
		cm.openNpc(9000192);

	} else if (selection == 3) {
		cm.dispose();
		cm.openNpc(3003261);

	} else if (selection == 4) {
		cm.dispose();
		cm.openNpc(2007);

	} else if (selection == 5) {
		cm.dispose();
		cm.openNpc(1012116);


	} else if (selection == 6) {
		cm.dispose();
		cm.openNpc(1540942);
			}
		}
	}
}