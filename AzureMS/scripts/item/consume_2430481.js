
/*

    퓨어 소스 팩의 스크립트 입니다. (제작 : 엑시즈)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 큐브조각 교환


*/
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
            var text = "#bMaster Miracle Cube Pieces #r#c2430481# QTY #kHave Silver Seal #r10 QTY#k, Advanced Equipment Enhancement Order Form #r20 QTY#k, 80% Epic Potential Order Book #r30 QTY#k Can be exchanged using.\r\n\r\n";
                text += "#b#L0##i2049501# #z2049501##l\r\n";
		text += "#b#L1##i2049300# #z2049300##l\r\n";
		text += "#b#L2##i2049701# #z2049701##l";
            cm.sendSimple(text);
        } else if(status == 1) {
            if (selection == 0) {
                if (cm.haveItem(2430481, 10)) {
		    cm.sendYesNo("Really #b#i2049501# #z2049501##kDo you want to replace it with?");
		    select = 0;
                } else {
                    cm.sendNext("I'm sorry #b#z2430481##kI don't think this is enough.");
		    cm.dispose();
                }
            } else if (selection == 1) {
                if (cm.haveItem(2430481, 20)) {
		    cm.sendYesNo("Really #b#i2049300# #z2049300##kDo you want to replace it with?");
		    select = 1;
		} else {
                    cm.sendNext("I'm sorry #b#z2430481##kI don't think this is enough.");
		    cm.dispose();
		}
	    } else if (selection == 2) {
                if (cm.haveItem(2430481, 30)) {
		    cm.sendYesNo("Really #b#i2049701# #z20497010##kDo you want to replace it with?");
		    select = 2;
		} else {
                    cm.sendNext("I'm sorry #b#z2430481##kI don't think this is enough.");
		    cm.dispose();
		}        
            } else {
                cm.dispose();
            }
	} else if (status == 2) {
            if (select == 0) {
                if (cm.canHold(2049501)) {
                    cm.gainItem(2430481, -10);
                    cm.gainItem(2049501, 1);
		    cm.sendNext("Exchange completed.");
                } else {
                    cm.sendNext("Sorry, but you don't have enough inventory space. #b Consumption#k Please free up inventory space on the tab.");
                }
                cm.dispose();
            } else if (select == 1) {
		if (cm.canHold(2049300)) {
                    cm.gainItem(2430481, -20);
                    cm.gainItem(2049300, 1);
                } else {
                    cm.sendNext("Sorry, but you don't have enough inventory space. #bConsumption#kPlease free up inventory space on the tab.");
                }
                cm.dispose();
	    } else if (select == 2) {
		if (cm.canHold(2049701)) {
                    cm.gainItem(2430481, -30);
                    cm.gainItem(2049701, 1);
                } else {
                    cm.sendNext("Sorry, but you don't have enough inventory space. #bConsumption#k Please free up inventory space on the tab.");
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