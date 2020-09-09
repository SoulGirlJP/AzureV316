var status = 0;

var mySelection = 0;

var Stats = new Array(4); // 0 = 스우 HP, 1 = 스우 ATK, 2 = 데미안 HP, 3 = 데미안 ATK
var SpicalRage = 20;

var FinalHP1 = 0;
var FinalHP2 = 0;

var Victory = 0;
var justice = 0;

function start() {
	cm.sendSimple("시아스타일 #b스우#k vs #b데미안#k 시스템 입니다.\r\n#Cgray#ㄴ승리할 것 같은 보스를 선택 해주세요.\r\n\r\n#b#L01#스우#l\r\n#L02#데미안#l");
}

function action(mode, type, selection) {
	if (mode == 1) status++;
	else {
		cm.dispose();
		return;
	}

	if (status == 1) {
		mySelection = selection;
		cm.sendGetText("#b#z4310038# #k를 얼마나 배팅 하실 건가요 ?\r\n");
	} else if (status == 2) {
		justice = cm.getText();

		if (isNaN(justice) || justice < 100) {
			cm.sendOk("최소 100개 부터 배팅이 가능합니다.");
			cm.dispose();
			return;
		} else if (justice > 500) {
			cm.sendOk("최대 500개 까지만 배팅이 가능합니다.");
			cm.dispose();
			return;
		} else {
			if (!cm.haveItem(4310038, justice)) {
				cm.sendOk("입력한 만큼의 #v4310038##b#z4310038# #k아이템이 없습니다.");
				cm.dispose();
				return;
			}
		}

		for(i=0; i<Stats.length; i++) {
			if(i % 2 == 0) {
				// hp
				Stats[i] = Math.floor(Math.random() * 30000) + 80000;
				if (i == 0) { finalHP1 = Stats[i] } else { finalHP2 = Stats[i] }
			} else {
				// atk
				Stats[i] = Math.floor(Math.random() * 10000) + 10000;
			}
		}
		cm.gainItem(4310038, -justice);
		cm.sendNext("                    설정된 보스의 스탯 수치입니다\r\n\r\n" + "스우 #rHP #k: #b" + Stats[0] + "\r\n#k스우 #rATK #k: #b" + Stats[1] + "#k\r\n\\r\n데미안 #rHP #k: #b" + Stats[2] + "\r\n#k데미안 #rATK #k: #b" + Stats[3]);
	} else if (status == 3) {
		var Text = "";
		var SpicalRanVal = 0;
		var firstAttack = Math.floor(Math.random() * 100);

		var Damage = 0;
		// 1 = 스우 , 2 = 데미안
		if (firstAttack > 45) {
			firstAttack = mySelection;
		} else {
			if (mySelection == 1) { firstAttack = 2 } else { firstAttack = 1 }
		}


		if (firstAttack == 1) {
			Text += "                     선 공격자는 #b스우#k 입니다!\r\n\r\n";
		} else {
			Text += "                     선 공격자는 #b데미안#k 입니다!\r\n\r\n";
		}

		while(true) {
			if (firstAttack == 1) {
				SpicalRanVal = Math.floor(Math.random() * 100);
				if (SpicalRanVal < SpicalRage) {
					Damage = (Stats[1] * 2);
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d스우의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Text += "#e스우의 공격 : #r" + Damage + "#k [치명타]#n\r\n";
					}
				} else {
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d스우의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Damage = Stats[1];
						Text += "#d스우의 공격 : #b" + Damage + "#k [평타]\r\n";
					}
				}

				Stats[2] = Stats[2] - Damage;
				

				Text += "#Cgray#데미안의 남은 HP : #B" + Stats[2] / finalHP2 * 100 + "##k\r\n";
				Text += "───────────────────────────\r\n";

				if (Stats[2] < 0) {
					Text += "#b스우#k의 승리!\r\n";
					Victory = 1;
					break;
				}


				SpicalRanVal = Math.floor(Math.random() * 100);
				if (SpicalRanVal < SpicalRage) {
					Damage = (Stats[3] * 2);
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d데미안의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Text += "#e데미안의 공격 : #r" + Damage + "#k [치명타]#n\r\n";
					}
				} else {
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d데미안의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Damage = Stats[3];
						Text += "#d데미안의 공격 : #b" + Damage + "#k [평타]\r\n";
					}
				}

				Stats[0] = Stats[0] - Damage;
				

				Text += "#Cgray#스우의 남은 HP : #B" + Stats[0] / finalHP1 * 100 + "##k\r\n";
				Text += "───────────────────────────\r\n";

				if (Stats[0] < 0) {
					Text += "#b데미안#k의 승리!\r\n";
					Victory = 2;
					break;
				}
			} else {
				SpicalRanVal = Math.floor(Math.random() * 100);
				if (SpicalRanVal < SpicalRage) {
					Damage = (Stats[3] * 2);
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d데미안의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Text += "#e데미안의 공격 : #r" + Damage + "#k [치명타]#n\r\n";
					}
				} else {
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d데미안의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Damage = Stats[3];
						Text += "#d데미안의 공격 : #b" + Damage + "#k [평타]\r\n";
					}
				}

				Stats[0] = Stats[0] - Damage;
				

				Text += "#Cgray#스우의 남은 HP : #B" + Stats[0] / finalHP1 * 100 + "##k\r\n";
				Text += "───────────────────────────\r\n";

				if (Stats[0] < 0) {
					Text += "#b데미안#k의 승리!\r\n";
					Victory = 2;
					break;
				}


				SpicalRanVal = Math.floor(Math.random() * 100);
				if (SpicalRanVal < SpicalRage) {
					Damage = (Stats[1] * 2);
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d스우의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Text += "#e스우의 공격 : #r" + Damage + "#k [치명타]#n\r\n";
					}
				} else {
					SpicalRanVal = Math.floor(Math.random() * 100);
					if (SpicalRanVal < SpicalRage) {
						Damage = 0;
						Text += "#d스우의 공격 : #Cgray#" + Damage + "#k [회피]\r\n";
					} else {
						Damage = Stats[1];
						Text += "#d스우의 공격 : #b" + Damage + "#k [평타]\r\n";
					}
				}

				Stats[2] = Stats[2] - Damage;
				

				Text += "#Cgray#데미안의 남은 HP : #B" + Stats[2] / finalHP2 * 100 + "##k\r\n";
				Text += "───────────────────────────\r\n";

				if (Stats[2] < 0) {
					Text += "#b스우#k의 승리!\r\n";
					Victory = 1;
					break;
				}
			}
		}

		cm.sendOk(Text);
	} else if (status == 4) {
		if (mySelection == Victory) {
			cm.gainItem(4310038, justice * 2);
			cm.sendOk("당신이 건 쪽이 승리 하였어요 ! 축하드려요 !\r\n\r\n#b#v4310038##z4310038# " + justice * 2 + "#k 획득 !");
			cm.dispose();
			return;
		} else {
			cm.sendOk("아쉽지만 당신이 건 쪽이 패배 하셨어요.. 다음에 다시 도전 해주세요 !");
			cm.dispose();
			return;
		}
	}
}