var status = -1;

var itemID = [5062010, 5062009, 5062500,2435719,5062010,5062009,5062500,2435719,5062010,5062009,5062500,2435719]; // 구매 아이템 코드
var itemNum = [10, 10, 10,1,50,50,50,5,200,200,200,20]; // 구매 아이템 개수

var costID = 2435458; // 소비 재료 아이템 코드
var costNum = [20, 10, 10,10,100,50,50,50,400,200,200,200]; // 소비 재료 아이템 개수

function start(){
    status = -1;
    action(1,0,0);
}

function action(mode,type,selection){
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
		var txt = ""
		txt += "#z" + costID + "# " + cm.itemQuantity(costID) + "QTY Had.\r\n"
		txt += "Please select an item to exchange.\r\n"
		for (i = 0; i < itemID.length; i++) {
			txt += "#d#L" + i + "##i" + itemID[i] + "# #z" + itemID[i] + "# " + itemNum[i] + " QTY #b[ " + costNum[i] + " QTY Consumption ]\r\n"
		}
		cm.sendSimple(txt);
	} else if (status == 1) {
		sel = selection;
		var txt = ""
		txt += "The item you selected #d#z" + itemID[sel] + "# " + itemNum[sel] + " QTY #kIs required #b#z" + costID + "# Count #kIs #b" + costNum[sel] + " QTY #kis. Do you really want to exchange?\r\n"
		cm.sendYesNo(txt);
	} else if (status == 2) {
		if (cm.haveItem(costID, costNum[sel]) && cm.itemQuantity(costID) >= costNum[sel]) {
			if (cm.canHold(itemID[sel])) {
				var txt = ""
				txt += "Congratulations. Successful exchange completed.\r\n"
				cm.gainItem(costID, -costNum[sel]);
				cm.gainItem(itemID[sel], itemNum[sel]);
				cm.sendOk(txt);
				cm.dispose();
			} else {
				cm.sendOk("Out of inventory space.");
				cm.dispose();
			}
		} else {
			cm.sendOk("#b#z" + costID + "##kIs not enough.");
			cm.dispose();
		}
	}
}