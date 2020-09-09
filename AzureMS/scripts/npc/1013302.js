// Donation Shop

importPackage(java.mysql);
importPackage(java.lang);
importPackage(Packages.database);
var shop = [
	{'itemid' : 2439291, 'qty' : 1, 'price' : 2250},
        
	//{'itemid' : 5121060, 'qty' : 1, 'price' : 5000},
	{'itemid' : 5062010, 'qty' : 10, 'price' : 5000},
	{'itemid' : 5062500, 'qty' : 10, 'price' : 8000},
	{'itemid' : 2049360, 'qty' : 1, 'price' : 13500}
	//{'itemid' : 4310156, 'qty' : 1, 'price' : 5000},
	//{'itemid' : 2470003, 'qty' : 1, 'price' : 5000},
	//{'itemid' : 4310235, 'qty' : 100, 'price' : 5000}
]

var enter = "#k\r\n";
var seld = -1;
var bp = -1;

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
		bp = cm.getPlayer().getVPoints();
		var msg = "#fs11##dGood Morning! I am the manager for the Item Shop.\r\n#fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole1#  What Can I get for ya?  #fUI/FarmUI.img/objectStatus/star/whole1##b#fs11#" +enter+enter;
		msg += "#fs11##k#k#k#fs11##fUI/FarmUI.img/objectStatus/star/whole# Current DP : #r["+cm.getPlayer().getRC()+"P] #kAvailable."+enter;
		for (i = 0; i < shop.length; i++) msg += "#fUI/FarmUI.img/objectStatus/star/whole20#"+"#L"+i+"##b#i"+shop[i]['itemid']+"##z"+shop[i]['itemid']+"# "+shop[i]['qty']+" QTY#k"+enter+" ��Price : #b"+shop[i]['price']+"P#k"+enter;

		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel;
		var msg = "#fs11#Information about the item you want to purchase."+enter;
		if (sel != 999) {
			msg += " Item : #b#i"+shop[seld]['itemid']+"##z"+shop[seld]['itemid']+"##k"+enter;
			msg += " Count : #b"+shop[seld]['qty']+" QTY #k"+enter;
			msg += " Price : "+shop[seld]['price']+"P#k"+enter;
		}
		msg += " Please press '��' to really buy.";
		cm.sendYesNo(msg);
	} else if (status == 2) {
		item = shop[seld]['itemid'];
		qty = shop[seld]['qty'];
		price = shop[seld]['price'];
		if (cm.getPlayer().getRC() >= price) {
			cm.gainItem(item, qty);
			cm.getPlayer().gainRC(-price);
			cm.sendOk("Your purchase is complete.");
			cm.dispose();
		} else {
			cm.sendOk("#bDonation Points#k seems to be lacking.");
			cm.dispose();
		}
	}
}