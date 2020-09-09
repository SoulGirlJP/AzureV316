// Zero Upgrade Weapon
// Script : 9900001

var status = -1;

function start() {
	status = -1;
	action (1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		status--;
		cm.dispose();
	}
	
	if (status == 0) {
		if (mode == 0) { 
			cm.dispose();
		} else {
			cm.sendSimple("Hello, I am the zero weapon upgrader, would you like to upgrade your weapon? \r\n #L0# Yes, please. #l \r\n #L1# No, thank you. #l");
		}
	} else if (status == 1) {
		if (selection == 0) {
			cm.zeroWeaponUpgrade();
		}
		cm.dispose();
	}
}