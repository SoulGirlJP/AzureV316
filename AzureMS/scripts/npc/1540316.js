var status = 0;
var time = "#fUI/UIToolTip/Item/Equip/Star/Star#"

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
		var jessica = "            #e#rAzureMS#k Welcome to the fishing world. #n\r\n";
		jessica += "#e#b#L0# "+time+" Fishing guide " +time+"#n#k\r\n";
		jessica += "#e#b#L1# "+time+" Buy fishing supplies " +time+"#n#k\r\n";
		jessica += "#e#b#L8# "+time+" Fishing bead shops " +time+"#n#k\r\n";
		jessica += "#e#b#L3# "+time+" Exchange system " +time+"#n#k\r\n";
		cm.sendSimple(jessica);

        } else if (status == 1) {
	if (selection == 0) {
		cm.sendOk("If you sit on a chair after buying bait, fishing rod and chair, you can get various items when bait is caught at random. It's a good idea to save the coins you got from fishing..\r\n\r\n#rCaution: Please be sure to empty your inventory when fishing!#k");
		cm.dispose();
	} else if (selection == 3) {
		cm.dispose();
		cm.openNpc(1104102);
	} else if (selection == 4) {
		cm.dispose();
		cm.openNpc(2020006);
	} else if (selection == 8) {
		cm.dispose();
		cm.openNpc(1540893);

	} else if (selection == 1) {
		var jessica2 = "Please note that chairs and baits are expensive.\r\n\r\n#b";
		jessica2 += "#L0##i3010432# "+time+" Chair purchase#k (100,000 Mesos)\r\n";
		jessica2 += "#L1##b#i2430996# "+time+" Bait purchase#k (Per 1000 baits, 10 million mesos)#n\r\n";
		jessica2 += "#L5555##b#i2430996# "+time+" Bait purchase#k (9.99 Million Mesos per 9999 baits)#n\r\n";
		cm.sendSimple(jessica2);
	} else if (selection == 2) {
		var jessica3 = "Choose an item you want to exchange.\r\n#Cgray#(10 coins per 1000 fish)#k\r\n";
                jessica3 += "#L196##bExchange your notes for 11th anniversary coins\r\n";
                jessica3 += "#L197##bExchange your bakchi into Win Winter Meet Coin\r\n";
                jessica3 += "#L198##bExchange your items for Justice Coin";
		cm.sendSimple(jessica3);
		}



        } else if (status == 2) {
if (selection == 0) {
		if (cm.getMeso() >= 100000) {
		cm.gainItem(3010432, 1);
		cm.gainMeso(-100000);
		cm.sendOk("Haha. Come back next time.");
		cm.dispose();
	} else {
		cm.sendOk("I think you don't have enough mesos?" );
		cm.dispose();
		}
	} else if (selection == 1) {
		if (cm.getMeso() >= 99999999) {
		cm.gainItem(2430996, 1000);
		cm.gainMeso(-99999999);
		cm.sendOk("Whenever the bait runs out, come to me!");
		cm.dispose();
	} else {
		cm.sendOk("I think you don't have enough mesos?" );
		cm.dispose();
		}

	} else if (selection == 5555) {
		if (cm.getMeso() >= 1000000) {
		cm.gainItem(2430996, 9999);
		cm.gainMeso(-1000000);
		cm.sendOk("Whenever the bait runs out, come to me!");
		cm.dispose();
	} else {
		cm.sendOk("I think you don't have enough mesos?" );
		cm.dispose();
		}
             } else if (selection == 50) {
		cm.dispose();
		cm.openNpc(2060002);
		return;
             } else if (selection == 38) {
		cm.dispose();
		cm.openNpc(9201036);
		return;
	} else if (selection == 51) {
		if (cm.haveItem(4001189, 1500)) {
		if (cm.haveItem(4001189, 1500)) {
		if (cm.haveItem(4001189, 1500)) {
		cm.gainItem(4001189, -1500);
		cm.gainItem(4001189, -1500);
		cm.gainItem(4001189, -1500);
		cm.name(1142126, 300,0,3);
		cm.sendOk("You may not like it.");
		cm.dispose();
	} else {
		cm.sendOk("this! Are you sure there's a fish?");
		cm.dispose();
		}




}	
}
}
}
}
}