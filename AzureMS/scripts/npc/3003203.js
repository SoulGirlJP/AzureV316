importPackage(Packages.tools);

var coin = 4032101;
var cost = 1;

var icon = "#fItem/Etc/0403/04032101/info/icon#"

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
		return;
	}
	if (mode == 0) {
		status--;
	}
	if (mode == 1) {
		status++;
	}

	switch (status) {
		case 0:
			A = parseInt(cm.itemQuantity(coin) / 1); // coin = material code, 1 = number of materials
			B = Math.min(cm.itemQuantity(coin), A); // Don't touch this material with coins
			var a = ""
			a += "I am " + icon + " #d#z" + coin + "##kWill be exchanged for 2 Million\r\nAdditional damage per piece. "
			a += "\r\nHow much do you want to exchange?\r\n\r\n"
			a += "Now #h # Additional damage : #b" + cm.getPlayer().getAddDamage() + "#k\r\n\r\n"
			a += "Maximum exchangeable frequency : " + B + " Coins\r\n"
			cm.sendGetNumber(a, B, 1, A);
		break;
		case 1:
			sel = selection; // Here sel puts the selected value from the previous status
			// The reason for the sel is that there is no place to enter or select the selection value in this status, so it is stored in sel so that it can be used for the next status
			if (sel > 32767) {
				cm.sendNext("The maximum value that can be entered is 32767 or less.");
				cm.dispose();
			} else {
				var a = ""
				a += "The number of exchanges currently entered is " + sel + "Times. Do you really want to exchange it like this?\r\n"
				cm.sendAcceptDecline(a);
			}
		break;
		case 2:
			if (sel == 0 || sel < 1 || sel > A || sel > 32767) { // When input value is 0, when input value is negative, when input value exceeds maximum input
				cm.worldMessage(2, "[Notice] : " + cm.getPlayer().getName() + "Has attempted a copy. Please report all.");
				cm.dispose();
			} else {
				if (cm.haveItem(coin, cost * sel) && cm.itemQuantity(coin) >= (cost * sel)) {
					var a = ""
					a += "Congratulations. The exchange was successful."
					cm.gainItem(coin, -cost * sel);
					cm.getPlayer().gainAddDamage(2000000 * sel);
					cm.sendNext(a);
					cm.dispose();
				} else {
					cm.worldMessage(2, "[Notice] : " + cm.getPlayer().getName() + "Has manipulated a selection value. Please report all.");
					cm.dispose();
				}
			}
		break;
	}
}