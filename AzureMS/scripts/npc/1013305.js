// Purple Orb Shop
// Script : 1013305

var enter = "\r\n";
var shop = [
	{'itemid' : 5041001, 'qty' : 1, 'price' : 100},
        {'itemid' : 2450001, 'qty' : 1, 'price' : 2000},
        {'itemid' : 5121036, 'qty' : 1, 'price' : 2500},
        {'itemid' : 5050100, 'qty' : 1, 'price' : 3000},
        {'itemid' : 5211002, 'qty' : 1, 'price' : 3000},
        {'itemid' : 5530068, 'qty' : 1, 'price' : 6000},
        {'itemid' : 5044006, 'qty' : 1, 'price' : 7500},
        {'itemid' : 2048716, 'qty' : 10, 'price' : 600},
        {'itemid' : 2048747, 'qty' : 10, 'price' : 1000}
        

]

var need = 4310185;

var seld = -1;

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
		var msg = "#fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole# #dGood Morning. Azure Upgrade shop.#k"+enter;
		msg += "#fs11##k#k#k#fs11##fUI/FarmUI.img/objectStatus/star/whole# Need #r#i"+need+"##z"+need+"##k to trade."+enter;
                msg += "\r\n#e#rFlames Coming soon.\r\n#r"
		for (i = 0; i < shop.length; i++) {
			msg += "#L"+i+"##r#i"+shop[i]['itemid']+"##z"+shop[i]['itemid']+"# "+shop[i]['qty']+"개#k"+enter;
			msg += "　　 Required number : #b#i4310185# Purple Orb #r"+shop[i]['price']+"개#k"+enter;
		}
		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel;
		var msg = "#fs11#Information of the item you want to purchase."+enter;
		msg += "*- Item : #r#i"+shop[seld]['itemid']+"##z"+shop[seld]['itemid']+"##k"+enter;
		msg += "*- Count　 : #r"+shop[seld]['qty']+"개 #k"+enter;
		msg += "*- Price　 : #i"+need+"##z"+need+"# #r"+shop[seld]['price']+"개#k"+enter+enter;
		msg += "*- #bIf you really want to buy please press 'Yes'.";
		cm.sendYesNo(msg);
	} else if (status == 2) {
		item = shop[seld]['itemid'];
		qty = shop[seld]['qty'];
		price = shop[seld]['price'];
		if (cm.haveItem(need, price)) {
			cm.gainItem(item, qty);
			cm.gainItem(need, -price);
			cm.sendOk("Your purchase is complete.");
			cm.dispose();
		} else {
			cm.sendOk("#fs11##b#i"+need+"##z"+need+"##kIt looks like you are running out of materials.");
			cm.dispose();
		}
	}
}