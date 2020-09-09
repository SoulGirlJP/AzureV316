

/*
    CelphisMs NPC 파일
*/


var status = 0;
var selected = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
 cm.dispose();
 return;
    }
    if (mode == 1)
 status++;
    else
 status--;
	if (status == 0) {
		if (cm.getPlayer().getMapId() != 109090000) {
			cm.warp(109090000);
			cm.dispose();
	//	} else if (cm.getPlayer().hasGmLevel(6)) {
                } else {
		cm.sendSimple("#b#L0#술래잡기 하기#l\r\n");
		}
   	} else if (status == 1) {
		selected = selection;
		if (selection == 0) {
			cm.sendYesNo("술래잡기는 최소 12명이상이 필요하며 술래는 랜덤으로 정해집니다. 잡는 방법은 도망자를 한 대 치기만 하면 됩니다. 술래잡기맵에서는 모든 스킬이 사용불가능합니다. 입장하시겠습니까?");
     		} else if (selection == 1) {
			cm.sendSimple("#r() 안에 있는 숫자는 트로피 갯수입니다.#k\r\n#b#L0#황금 단풍잎 20개 #r(10개)#k#l\r\n#L1#황금 단풍잎 250개 #r(100개)#k#l\r\n#L2#황금 단풍잎 500개 #r(200개)#k#l\r\n#L3#러블링 파티베어 3개 #r(20개)#k#l\r\n" + 
			"#L4#미라클 큐브 1개 #r(20개)#k#l\r\n#L5#부화기 한 세트 #r(20개)#k#l\r\n#L6#호신 부적 5개 #r(20개)#k#l\r\n#L7#운명의 수레바퀴 5개 #r(20개)#k#l\r\n#L8#황금 망치 #r(50개)#k#l\r\n#L9#혼돈의 주문서 #r(150개)#k#l\r\n#L10#연금술사의 반지 #r(300개)#k#l\r\n#L11#타임리스/리버스중 한 종류 랜덤 #r(300개)#k#l\r\n#L12#정령의 팬던트 1일권 #r(500개)#k#l");
		}
	} else if (status == 2) {
		if (selected == 0) {
			if (cm.getMap().getCharactersSize() >= 11) {
				if (!cm.getMap().getCharactersSize() > 0) {
					cm.sendOk("술래잡기가 끝날 때 까지 기다려주세요.");
				} else {
					cm.startCatch();
				}
			} else {
				cm.sendOk("12명이상이 되야 합니다.");
			}
		} else if (selected == 1) {
			switch(selection) {
				case 0: {
					if (cm.haveItem(4000038,10)) {
						cm.gainItem(4000038, -10);
						cm.gainItem(4000313, 20);
					}
				}
				break;
				case 1: {
					if (cm.haveItem(4000038,100)) {
						cm.gainItem(4000038, -100);
						cm.gainItem(4000313, 250);
					}
				}
				break;
				case 2: {
					if (cm.haveItem(4000038,200)) {
						cm.gainItem(4000038, -200);
						cm.gainItem(4000313, 500);
					}
				}
				break;
				case 3: {
					if (cm.haveItem(4000038,10)) {
						cm.gainItem(4000038, -10);
						cm.gainItem(5122000, 3);
					}
				}
				break;
				case 4: {
					if (cm.haveItem(4000038,20)) {
						cm.gainItem(4000038, -20);
						cm.gainItem(5062000, 1);
					}
				}
				break;
				case 5: {
					if (cm.haveItem(4000038,20)) {
						cm.gainItem(4000038, -20);
						cm.gainItem(5060002,1);
						cm.gainItem(4170000,1);
					}
				}
				break;
				case 6: {
					if (cm.haveItem(4000038,20)) {
						cm.gainItem(4000038, -20);
						cm.gainItem(5130000, 5);
					}
				}
				break;
				case 7: {
					if (cm.haveItem(4000038,20)) {
						cm.gainItem(4000038, -20);
						cm.gainItem(5510000, 5);
					}
				}
				break;
				case 8: {
					if (cm.haveItem(4000038,50)) {
						cm.gainItem(4000038, -50);
						cm.gainItem(5570000, 1);
					}
				}
				break;
				case 9: {
					if (cm.haveItem(4000038,150)) {
						cm.gainItem(4000038, -150);
						cm.gainItem(2049118, 1);
					}
				}
				break;
				case 10: {
					if (cm.haveItem(4000038,300)) {
						cm.gainItem(4000038, -300);
						cm.gainItem(1112400, 1);
					}
				}
				break;
				case 11: {
					var isReverseTimeless = Array(1432047, 1382057, 1492023, 1472068, 1452057, 1332075, 1372044, 1462050, 1302081, 1402046, 1412033, 1422037, 1312037, 1482023, 1442063, 1322060, 1432049, 1322061, 1382059, 1472071, 1492025, 1372045, 1452059, 1332076, 1462051, 1402047, 1302086, 1412034, 1422038, 1312038, 1482024, 1442067, 1002776, 1002777, 1002778, 1002779, 1002780, 1002790, 1002791, 1002792, 1002793, 1002794, 1082234, 1082235, 1082236, 1082237, 1082238, 1082239, 1082240, 1082241, 1082242, 1082243, 1092058, 1092059, 1092057, 1052155, 1052156, 1052157, 1052158, 1052159, 1052160, 1052161, 1052162, 1052163, 1052164, 1072355, 1072356, 1072357, 1072358, 1072359, 1072361, 1072362, 1072363, 1072364, 1072365);
					if (cm.haveItem(40000380,300)) {
						cm.gainItem(4000038, -300);
						cm.gainItem(isReverseTimeless(Math.floor(Math.random() * isReverseTimeless.length)), 1);
					}
				}
				break;
				case 12: {
					if (cm.haveItem(4000038,500)) {
						cm.gainItem(4000038, -500);
						cm.gainItem(1122155, 1, 24 * 60 * 60 * 1000);
					}
				}
				break;
			}
			cm.sendOk("정상적으로 지급이 완료되었습니다.");
		}
		cm.dispose();
	}
 }
