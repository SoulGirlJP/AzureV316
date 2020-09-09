importPackage(Packages.tools.RandomStream);
importPackage(java.lang);

var enter = "\r\n";
var seld = -1;

var need = 2431792, qty = 1; // Box code, count

var 추뎀최소 = 50000000;
var 추뎀최대 = 150000000;

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
		var msg = "#i"+need+"##z"+need+"#Get 50-150 million additional damage using!"+enter;
		msg += "#fs11##bPlease press '예' To really use.";
		cm.sendYesNo(msg);
	} else if (status == 1) {
		if (!cm.haveItem(need, qty)) {
			cm.sendOk("Not enough items needed요.");
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