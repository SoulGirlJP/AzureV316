// Jackell Orb

importPackage(Packages.tools.RandomStream);
importPackage(Packages.constants);

var enter = "\r\n";
var seld = -1, seld2 = -1;

var xitemcode = 4001910, xqty = 1;

var list = [
//{'item' : 4310156, 'chance' : 9000},
//{'item' : 4310199, 'chance' : 9000},
//{'item' : 2439614, 'chance' : 300},
{'item' : 2591314, 'chance' : 500},
//{'item' : 1182285, 'chance' : 500},
//{'item' : 2046991, 'chance' : 500},
//{'item' : 2046992, 'chance' : 500},
//{'item' : 2047814, 'chance' : 500}
]

// chance = chance, 100 is 1 fur, 10000 is 100 fur

var 꽝위로템 = 4310156;

var 최종아이템 = [];

var 최소올스탯 = 1, 최대올스탯 = 1000;
var 최소공마 = 1, 최대공마 = 1000;

var 최종올스탯 = -1, 최종공마 = -1;

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

		var msg = "#fs11##dHi #rFragment of Fate#k #dExchange system.\r\nBoss #rFragment of Fate#k#dYou can get items with.\r\nBoss Ornament Options Information - #rBall, hemp 1 to 1000 / all stat 1 to 1000#k#fs11#"+enter;
		msg += "#d필요한 파편 : #r#i"+xitemcode+"##z"+xitemcode+"# "+xqty+"개#b"+enter+enter;
		for (i = 0; i < list.length; i++) {
			per = list[i]['chance'] / 100;
			msg += "#i"+list[i]['item']+"##z"+list[i]['item']+"# ("+per+"%)"+enter;
		}
		if (xqty == 1) {
			msg += enter+"#fs11##rPlease press 'Yes' to really win.";
			cm.sendYesNo(msg);
		} else if (xqty > 1) {
			sss = true;
			cm.sendGetNumber(msg+enter+"#rHow many fragments of fate do you use.", 1, 1, xqty);
		} else if (xqty < 1) {
			msg += enter+"#fs11##rThere's not enough fragments of fate.";
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
			if (Randomizer.rand(1, 10000) <= list[it1]['chance']) 최종아이템.push(list[it1]['item']);

			if (Randomizer.rand(1, 10000) <= list[it2]['chance']) 최종아이템.push(list[it2]['item']);

			if (최종아이템.length == 0) 최종아이템.push(꽝위로템);

			for (a = 0; a < 최종아이템.length; a++) {
				if (GameConstants.isEquip(최종아이템[a])) {
					최종올스탯 = Randomizer.rand(최소올스탯, 최대올스탯);
					최종공마 = Randomizer.rand(최소공마, 최대공마);
					cm.gainItemAllStat(최종아이템[a], 1, 최종올스탯, 최종공마, 5);
					msg += "#i"+최종아이템[a]+"##z"+최종아이템[a]+"# ATK : "+최종공마+" / All Stats : "+최종올스탯+enter;
				} else {
					cm.gainItem(최종아이템[a], 1);
					msg += "#i"+최종아이템[a]+"##z"+최종아이템[a]+"#"+enter;
				}
				//얻은아이템.push(최종아이템[a]);
				최종아이템 = [];
			}
			cm.gainItem(xitemcode, -1);
		}
                Packages.tools.LoggerChatting.writeLog("오브깐새끼.txt", "[ "+cm.getItemName(xitemcode)+" "+xqty+"개 ] Nickname : "+cm.getPlayer().getName()+" /  AccountID : "+cm.getPlayer().getAccountID());
		cm.sendOk(msg);
		cm.dispose();
	}
}

function isEquip(id) {
	// 1272016
	return id / 1000000 == 1;
}