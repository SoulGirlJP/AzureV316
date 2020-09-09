var 주흔 = 4001832;
var 소비 = 1;

var 메소 = 25000;

function start() {
	status = -1;
	action (1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	}
	if (mode == 0) {
		if (status == 2) {
			cm.sendOk("Please try again later.");
			cm.dispose();
			return;
		} else {
			status--;
		}
	}
	
	switch (status) {
		case 0:
			var m = ""
			m += "#Cgray##e[ AzureMS Methodist NPC : #p" + cm.getNpc() + "# ]#n#k\r\n\r\n"
			m += " I am #z" + 주흔 + "# To exchange for Meso #p" + cm.getNpc() + "#is. Do you want to exchange? (25,000 meso each)\r\n"
			m += "#b#L0#Yeah, #z" + 주흔 + "# I would like to exchange.#l\r\n"
			cm.sendSimple(m);
		break;
		case 1:
			개수 = cm.itemQuantity(주흔);
			회수 = parseInt(개수 / 소비);
			최대 = Math.min(개수, 회수);

			var m = ""
			m += "\r\n#Cgray##e[ AzureMS Present #h #'S data ]#n#k\r\n\r\n"
			m += "- Item Count : " + 개수 + " QTY\r\n"
			m += "- Exchangeable : " + 최대 + " time\r\n"
			m += "- Max meso available : " + (최대 * 메소) + " Mesos\r\n\r\n"
			m += "#h # Is currently #z" + 주흔 + "# Total " + 개수 + " You have " + 최대 + " Times.\r\n"
			cm.sendGetNumber(m, 최대, 1, 회수);
		break;
		case 2:
			S = selection;

			if (S > 32767) {
				cm.sendOk("You can only exchange up to 32767 times. Please note that if you are uncomfortable.");
				cm.dispose();
				return;
			}

			var m = ""
			m += "#h #The number of times you entered " + S + "Burned, total #z" + 주흔 + "# " + (소비 * S) + " QTY is consumed. Do you really want to exchange?\r\n"
			cm.sendYesNo(m);
		break;
		case 3:
			if (개수 < (S * 소비) && S <= 0) {
				cm.sendOk("Bug bug.");
				cm.dispose();
				return;
			} else {
				if (cm.haveItem(주흔, S * 소비) && 개수 >= (S * 소비)) {
					var m = ""
					m += "Total consumption : #r" + (S * 소비) + " QTY#k\r\n"
					m += "Total Acquisition Meso : #b" + (S * 메소) + " Mesos#k\r\n\r\n"
					m += "The exchange was successful.\r\n"
					cm.gainItem(주흔, -S * 소비);
					cm.gainMeso(메소 * S);
					cm.sendOk(m);
					cm.dispose();
				} else {
					cm.sendOk("Bug bug.");
					cm.dispose();
					return;
				}
			}
		break;
	}		
}