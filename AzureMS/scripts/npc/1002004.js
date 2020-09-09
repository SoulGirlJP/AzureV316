importPackage(Packages.server.named);

var status = 0;
var sel = -1;
var mesos = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
	var chat = cm.getPlayer().getName() + "Nice to meet you. Named holes, even dealers, scissors, rocks, assistants.\r\n";
	chat += "#b#eNamed hole, even " + Named.nextTime + "Until turn result " + Named.namedTime + "#k#n\r\n";
	if (cm.isNamedTimeOver()) {
		chat += "#L3##bNamed hole, I want to bet.(#rTimeout#b)#l\r\n";
	} else {
		chat += "#L0##bNamed hole, I want to bet on.#l\r\n";
	}
	chat += "#L2#Named Holes, I want to get an even bet.#l\r\n";
	chat += "#L1#Named Hall, I want to hear about.#l";
	cm.sendSimple(chat);
    } else if (status == 1) {
	sel = selection;
	if (selection == 0) {
		if (Named.isCheck(cm.getPlayer().getId(), Named.nextDate + "-" + Named.nextTime)) {
			cm.sendOk(cm.getPlayer().getName() + "Is " + Named.nextTime + "You have already bet on the round.");
			cm.dispose();
			return;
		}
		if (Named.isCheck(cm.getPlayer().getId())) {
			cm.sendOk("You have already bet on the round.");
			cm.dispose();
			return;
		}
		cm.sendSimple("Which bet will you choose?\r\n#b#L0#I'll bet the meso.#l");
	} else if (selection == 1) {
		cm.sendOk("It is a way to use named ladder results. You can bet on mesos and other items.\r\n\r\n#e#bOdd and even odds#n#k\r\nOdd / Pair 1.8x\r\nLeft to right 4 lines 3 lines / 1.6 times\r\nLeft 3 Right 3 Left 4 Right 4 /2.5\r\n\r\n#e#r<Named ladder term>#n#k\r\nLeft / left\r\nRight off / depart from right\r\n3 rows / 3 ladders\r\n4 rows / 4 ladders\r\n3 ladders starting from left 3 / left\r\n3 right / 3 ladders starting from the right\r\n4 ladders left from left 4 / left\r\n4 right / 4 ladders starting from the right\r\n\r\nHit rate 100%! Wish 100%!!\r\n#rOdd and even results can be found at http://named.com.");
		cm.dispose();
	} else if (selection == 2) {
		cm.sendYesNo("If no result is yet, the bet record will be removed. \r\n#e#rex)When you want to be rewarded when the first round is not over after betting on the first round#n#k Do you really want to be rewarded?");
	} else if (selection ==3 ){
		cm.dispose();
		return;
	}
    } else if (status == 2) {
	if (sel == 2) {
		if (!Named.isCheck(cm.getPlayer().getId())) {
			cm.sendOk("Sorry, no rewards.");
			cm.dispose();
			return;
		} else {
			cm.sendSimple("Please select a bet to win.\r\n#b" + Named.getText(cm.getPlayer().getId()));
		}
	} else if (sel == 0 ) {
		cm.sendGetNumber("Please enter a method to bet",1,1,cm.getMeso() > 200000000 ? 200000000 : cm.getMeso());
	}
    } else if (status == 3) {
	if (sel == 2) {
		if (Named.giveItemorMeso(selection,cm.getPlayer())) {
			cm.sendOk("Did you get the bet reward? I will appreciate it again next time.");
			cm.dispose();
			return;
		} else {
			cm.sendOk("Please free up space in your inventory and try again.");
			cm.dispose();	
			return;
		}
	} else if (sel == 0) {
		mesos = selection;
		cm.sendYesNo("Really " + cm.Comma(mesos) + " Do you want to bet on mesos?");
	}
    } else if (status == 4) {
	if (mesos > 30000000) {
		cm.sendOk("The maximum bet is 30 million mesos.");
		cm.dispose();
		return;
	}
	cm.sendSimple("Select the side you want to bet on.\r\n#b#L0#hall #r(1.8x)#b#l\r\n#L1#¦ #r(1.8x)#b#l\r\n#L2#Left #r(1.6x)#b#l\r\n#L3#outflow #r(1.6x)#b#l\r\n#L4#3line #r(1.6x)#b#l\r\n#L5#4line #r(1.6x)#b#l\r\n#L6#Left3 #r(2.5x)#b#l\r\n#L7#Ooh3 #r(2.5x)#b#l\r\n#L8#Left4 #r(2.5x)#b#l\r\n#L9#Ooh4 #r(2.5x)#b#l");
    } else if (status == 5) {
	cm.installNamed(mesos,selection);
    }
}
