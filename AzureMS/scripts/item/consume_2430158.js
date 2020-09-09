var status;
var select;
function start() {
    status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1)
        cm.dispose();
    else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
		cm.sendSimple("Um ... Is there anything you want to do??\r\n\r\n#b#L0#Lion King's Medallion and 50 Purification Totems#l\r\n#L1#Exchange Lion King's Medallion and Purification Totem for 100#l");
	} else if (status == 1) {
		select = selection;
		if (selection == 0) {
			cm.sendYesNo("50 Purification Totem and 1 Lion King Medallion #bNoble Lion King Medal#k I'll exchange it for Do you really want to exchange?");
		} else if (selection == 1) {
			cm.sendYesNo("100 Purification Totem and 1 Lion King Medallion #bRoyal Lion King's Medallion#k I'll exchange it for Do you really want to exchange?");
		}
	} else if (status == 2) {
		if (select == 0) {
			if (cm.haveItem(4000630,50) && cm.haveItem(2430158,1) && cm.canHold(4310009)) {
				cm.gainItem(4000630,-50);
				cm.gainItem(2430158,-1);
				cm.gainItem(4310009,1);
				cm.sendOk("I exchanged it. Now you can buy Van Leon Set Armor from Murt.");
				cm.dispose();
			} else {
				cm.sendOk("Are you sure you have the items you need? Or check if there is not enough inventory.");
				cm.dispose();
			}
		} else if (select == 1) {
			if (cm.haveItem(4000630,100) && cm.haveItem(2430158,1) && cm.canHold(4310009)) {
				cm.gainItem(4000630,-100);
				cm.gainItem(2430158,-1);
				cm.gainItem(4310010,1);
				cm.sendOk("I exchanged it. Now you can buy Van Leon Set Weapons from Mert.");
				cm.dispose();
			} else {
				cm.sendOk("Are you sure you have the items you need? Or check if there is not enough inventory.");
				cm.dispose();
			}
		}
	}


else { 
		cm.dispose();
	}
    }
}