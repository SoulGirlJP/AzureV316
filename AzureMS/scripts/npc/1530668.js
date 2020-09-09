// Black Mage Orb


importPackage(Packages.tools.RandomStream);
importPackage(Packages.constants);

var enter = "\r\n";
var seld = -1, seld2 = -1;

var xitemcode = 4001902, xqty = 1;

var list = [
//{'item' : 4001879, 'chance' : 7000},
//{'item' : 4310218, 'chance' : 7000},
//{'item' : 2439614, 'chance' : 200}, 
{'item' : 4033929, 'chance' : 1000},
{'item' : 2435328, 'chance' : 100}, // 04001895
{'item' : 1182285, 'chance' : 20},
{'item' : 2591676, 'chance' : 50},
//{'item' : 2046991, 'chance' : 500},
//{'item' : 2046992, 'chance' : 500},
//{'item' : 2047814, 'chance' : 500}
]

// chance = chance, 100 is 1 fur, 10000 is 100 fur

var mesoPouch = 4310184;

var finalItem = [];

var minAllStat = 900, maxAllStat = 2600;
var minAtt = 850, maxAtt = 2550;

var finalAllStat = -1, finalAtt = -1;

var 얻은아이템 = [];

var sss = false;

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
		xqty = cm.itemQuantity(xitemcode);

		var msg = "#fs11##dGood Morning, #rAzure Orbs#k #dExchange system.\r\nThe #rBlack Mage Orb#k #dYou can get items with it.\r\nBoss Ornament Options Informations :\r\n - #rAll Stats 900 ~ 2600 / ATT, M.ATT 850 ~ 2550#k#fs11#"+enter;
		msg += "#dNeed/Have : #r#i"+xitemcode+"##z"+xitemcode+"# "+xqty+"개#b"+enter+enter;
		for (i = 0; i < list.length; i++) {
			per = list[i]['chance'] / 100;
			msg += "#i"+list[i]['item']+"##z"+list[i]['item']+"# ("+per+"%)"+enter;
		}
		if (xqty == 1) {
			msg += enter+"#fs11##rPlease press 'Yes' to really win.";
			cm.sendYesNo(msg);
		} else if (xqty > 1) {
			sss = true;
			cm.sendGetNumber(msg+enter+"#rPlease write how many Orbs you wish to use.", 1, 1, xqty);
		} else if (xqty < 1) {
			msg += enter+"#fs11##rThere is a shortage of Orbs.";
			cm.sendOk(msg);
			cm.dispose();
		}
	} else if (status == 1) {
		if (sss)
			xqty = sel;
		if (!cm.haveItem(xitemcode, xqty)) {
			cm.sendOk("#fs11#Please check if the ingredients are not enough.");
			cm.dispose();
			return;
		}
		var msg = "#fs11# The following items came out! #fs11##b"+enter;
		for (i = 0; i < xqty; i++) {
			it1 = Randomizer.rand(1, list.length) - 1, it2 = Randomizer.rand(1, list.length) - 1;
			if (Randomizer.rand(1, 10000) <= list[it1]['chance']) finalItem.push(list[it1]['item']);

			if (Randomizer.rand(1, 10000) <= list[it2]['chance']) finalItem.push(list[it2]['item']);

			if (finalItem.length == 0) {
                            finalItem.push(mesoPouch);
                            
                        }
                        
			for (a = 0; a < finalItem.length; a++) {
				if (GameConstants.isEquip(finalItem[a])) {
					finalAllStat = Randomizer.rand(minAllStat, maxAllStat);
					finalAtt = Randomizer.rand(minAtt, maxAtt);
					cm.gainItemAllStat(finalItem[a], 1, finalAllStat, finalAtt, 5);
					msg += "#i"+finalItem[a]+"##z"+finalItem[a]+"# ATT/M.ATT : "+finalAtt+" / All Stats : "+finalAllStat+enter;
				} else {
					cm.gainItem(finalItem[a], 1);
					msg += "#i"+finalItem[a]+"##z"+finalItem[a]+"#"+enter;
				}
				//얻은아이템.push(최종아이템[a]);
				finalItem = [];
			}
			cm.gainItem(xitemcode, -1);
		}
                Packages.tools.LoggerChatting.writeLog("오브깐새끼.txt", "[ "+cm.getItemName(xitemcode)+" "+xqty+"개 ] NickName : "+cm.getPlayer().getName()+" /  AccountID : "+cm.getPlayer().getAccountID());
		cm.sendOk(msg);
		cm.dispose();
	}
}

function isEquip(id) {
	// 1272016
	return id / 1000000 == 1;
}
