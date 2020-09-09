var status;
var select;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var text = "#bAmazing Miracle Cube Pieces #r#c2430759# QTY#kHave Golden Stamp Seal #r10 QTY#k, 4-Star Equipment Enhancement Order Form #r20 QTY#k, The five amazing Miracle cubes #r30 QTY#kCan be exchanged using.\r\n\r\n";
                text += "#b#L0##i2049501# #z2049501##l\r\n";
		text += "#b#L1##i2049300# #z2049300##l\r\n";
		text += "#b#L2##i2049701# #z2049701##l";
            cm.sendSimple(text);
        } else if(status == 1) {
            if (selection == 0) {
                if (cm.haveItem(2430759, 10)) {
		    cm.sendYesNo("Really #b#i2049500# #z2049500##kDo you want to replace it with?");
		    select = 0;
                } else {
                    cm.sendNext("I'm sorry #b#z2430759##kI don't think this is enough.");
		    cm.dispose();
                }
            } else if (selection == 1) {
                if (cm.haveItem(2430759, 20)) {
		    cm.sendYesNo("Really #b#i2049305# #z2049305##kDo you want to replace it with?");
		    select = 1;
		} else {
                    cm.sendNext("I'm sorry #b#z2430759##kI don't think this is enough.");
		    cm.dispose();
		}
	    } else if (selection == 2) {
                if (cm.haveItem(2430759, 30)) {
		    cm.sendYesNo("Really #b#i5062005# #z5062005##kDo you want to replace it with?");
		    select = 2;
		} else {
                    cm.sendNext("I'm sorry #b#z2430759##kI don't think this is enough.");
		    cm.dispose();
		}        
            } else {
                cm.dispose();
            }
	} else if (status == 2) {
            if (select == 0) {
                if (cm.canHold(2049501)) {
                    cm.gainItem(2430759, -10);
                    cm.gainItem(2049500, 1);
		    cm.sendNext("Exchange completed.");
                } else {
                    cm.sendNext("Sorry, but you don't have enough inventory space. #bConsumption #kPlease free up inventory space on the tab.");
                }
                cm.dispose();
            } else if (select == 1) {
		if (cm.canHold(2049300)) {
                    cm.gainItem(2430759, -20);
                    cm.gainItem(2049305, 1);
                } else {
                    cm.sendNext("Sorry, but you don't have enough inventory space. #bConsumption #kPlease free up inventory space on the tab.");
                }
                cm.dispose();
	    } else if (select == 2) {
		if (cm.canHold(2049701)) {
                    cm.gainItem(2430759, -30);
                    cm.gainItem(5062005, 5);
                } else {
                    cm.sendNext("Sorry, but you don't have enough inventory space. #bCash #kPlease free up inventory space on the tab.");
                }
                cm.dispose();        
            } else {
                cm.dispose();
            }
        } else { 
            cm.dispose();
        }
    }
}