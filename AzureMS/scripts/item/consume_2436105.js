importPackage(Packages.tools.RandomStream);
importPackage(java.lang);

var enter = "\r\n";
var seld = -1;

var need = 2436105, qty = 1; // 상자 코드, 개수

var 후포최소 = 30000;
var 후포최대 = 100000;

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
		/*var msg = "#i"+need+"##z"+need+"#Get up to 30,000 hoops ~ 100,000 hoops using!"+enter;
		msg += "#fs11##bPlease press '예' to really use it.";
		cm.sendYesNo(msg);
	} else if (status == 1) {
		if (!cm.haveItem(need, qty)) {
			cm.sendOk("I don't have enough items.");
			cm.dispose();
			return;
		}
		cm.gainItem(need, -qty);
		rc = Randomizer.rand(후포최소, 후포최대);
                        cm.getPlayer().gainRC(rc);
		cm.getPlayer().Message(rc+" Earned a Sponsor Point.");
		cm.sendOk(rc+" Sponsor Point payment is completed.");
		cm.dispose();*/
	}
}