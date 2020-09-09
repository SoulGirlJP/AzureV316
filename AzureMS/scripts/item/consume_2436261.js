importPackage(Packages.tools.RandomStream);
importPackage(java.lang);

var enter = "\r\n";
var seld = -1;

var need = 2436261, qty = 1; // Box code, count

var 추뎀최소 = 100000000;
var 추뎀최대 = 500000000;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
		var msg = "#i"+need+"##z"+need+"#Get 100-500 million additional damage using!"+enter;
		msg += "#fs11##bPlease press '예' to really use it.";
		cm.sendYesNo(msg);
	} else if (status == 1) {
		if (!cm.haveItem(need, qty)) {
			cm.sendOk("I don't have enough items.");
			cm.dispose();
			return;
		}
		cm.gainItem(need, -qty);
		rc = Randomizer.rand(추뎀최소, 추뎀최대);
                        cm.getPlayer().gainAddDamage(rc);
		cm.getPlayer().Message(rc+" Obtained additional damage.");
		cm.sendOk(rc+" Additional damage is paid.");
		cm.dispose();
	}
}