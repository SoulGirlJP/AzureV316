importPackage(java.mysql);
importPackage(java.lang);
importPackage(Packages.database);
var ���� = [
	{'itemid' : 2439291, 'qty' : 1, 'price' : 2250},
        
	//{'itemid' : 5121060, 'qty' : 1, 'price' : 5000},
	{'itemid' : 5062010, 'qty' : 10, 'price' : 5000},
	{'itemid' : 5062500, 'qty' : 10, 'price' : 8000},
	{'itemid' : 2049360, 'qty' : 1, 'price' : 13500}
	//{'itemid' : 4310156, 'qty' : 1, 'price' : 5000},
	//{'itemid' : 2470003, 'qty' : 1, 'price' : 5000},
	//{'itemid' : 4310235, 'qty' : 100, 'price' : 5000}
]

var enter = "\r\n";
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
		var msg = "#fs11##dGood Morning! #dMy name is Toben Mage, and I manage a Vote Points store.#b#fs11#" +enter+enter;
		msg += "#h # Vote Points : "+bp+enter;
		for (i = 0; i < ����.length; i++) msg += "#L"+i+"##b#i"+����[i]['itemid']+"##z"+����[i]['itemid']+"# "+����[i]['qty']+" QTY#k"+enter+" ��Price : #b"+����[i]['price']+"P#k"+enter;

		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel;
		var msg = "#fs11#Information about the item you want to purchase."+enter;
		if (sel != 999) {
			msg += " Item : #b#i"+����[seld]['itemid']+"##z"+����[seld]['itemid']+"##k"+enter;
			msg += " Count : #b"+����[seld]['qty']+" QTY #k"+enter;
			msg += " Price : "+����[seld]['price']+"P#k"+enter;
		} else {
			msg += " Item : #b"+�ߵ�+"Additional Damage#k"+enter;
			msg += " Price : #b"+�ߵ�����+"P#k"+enter;
		}
		msg += " Please press '��' to really buy.";
		cm.sendYesNo(msg);
	} else if (status == 2) {
		item = ����[seld]['itemid'];
		qty = ����[seld]['qty'];
		price = ����[seld]['price'];
		if (bp >= price) {
			cm.gainItem(item, qty);
			cm.getPlayer().gainVPoints(-price);
			cm.sendOk("Your purchase is complete.");
			cm.dispose();
		} else {
			cm.sendOk("#bVote Points#k seems to be lacking.");
			cm.dispose();
		}
	}
}