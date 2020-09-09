importPackage(Packages.constants);

var status = 0;
var invs = Array(1, 5);
var invv;
var selected;
var slot_1 = Array();
var slot_2 = Array();
var statsSel;
var º° = "#fUI/FarmUI.img/objectStatus/star/whole#";

function start() {
	action(1,0,0);
}

function action(mode, type, selection) {
	if (mode != 1) {
		cm.dispose();
		return;
	}
	status++;
	if (status == 1) {
		var txt = "#fnSharing Ghotic Extrabold##fs17#"+º°+" AzureMS Disposal "+º°+"\r\n#fs10##Cgray# Please select your desired inventory window.#k\r\n#fs12#";
		txt += "\r\n#d* Drop requires 100,000 mesos.#k\r\n";
		txt += "#L1##rCash items#k Let's drop.#k\r\n";
		txt += "#L2##bEquipment items#k Let's drop.#k\r\n";
		cm.sendSimple(txt);
	} else if (status == 2) {
		var ok = false;
		var selStr = "#fnSharing Ghotic Extrabold##dPlease select the item you would like to drop.#k\r\n";
		for (var x = 0; x < invs.length; x++) {
			var inv = cm.getInventory(invs[x]);
			for (var i = 0; i <= inv.getSlotLimit(); i++) {
				if (x == 0) {
					slot_1.push(i);
				} else {
					slot_2.push(i);
				}
				var it = inv.getItem(i);
				if (it == null) {
					continue;
				}
				if (selection == 1){
				var itemid = it.getItemId();
				}else if (selection == 2){
				if (cm.isCash(it.getItemId())){
				var itemid = 0;
				}else{
				var itemid = it.getItemId();
				}
				}

				if (selection == 1){
				if (!cm.isCash(itemid)) {
					continue;
				}
				}else if (selection == 2){
				if (!GameConstants.isEquip(itemid)) {
					continue;
				}
				}
				ok = true;
				selStr += "#L" + (invs[x] * 1000 + i) + "##v" + itemid + "##t" + itemid + "##l\r\n";
			}
		}
		if (!ok) {
			cm.sendOk("#fnSharing Ghotic Extrabold##rI don't think there are any items in the inventory window..#k");
			cm.dispose();
			return;
		}
		cm.sendSimple(selStr + "#k");
	} else if (status == 3) {
		invv = selection / 1000;
		selected = selection % 1000;
		var inzz = cm.getInventory(invv);
		if (invv == invs[0]) {
			statsSel = inzz.getItem(slot_1[selected]);
		} else {
			statsSel = inzz.getItem(slot_2[selected]);
		}
		if (statsSel == null) {
			cm.sendOk("#fnSharing Ghotic Extrabold##rError Guidance\r\n\r\nPlease try again.#k");
			cm.dispose();
			return;
		}
		cm.sendGetNumber("#fnSharing Ghotic Extrabold##v" + statsSel.getItemId() + "##t" + statsSel.getItemId() + "#\r\n\r\n#dPlease write the number of drop.#k", 1, 1, statsSel.getQuantity());
	} else if (status == 4) {
	if (cm.getMeso()>=100000){
		if (statsSel.getItemId() !== 1143032 && statsSel.getItemId() !== 1142373 && statsSel.getItemId() !== 1112750 && statsSel.getItemId() !== 1182058 && statsSel.getItemId() !== 1142551 && statsSel.getItemId() !== 1182062 && statsSel.getItemId() !== 1182063 && statsSel.getItemId() !== 1182064 && statsSel.getItemId() !== 1182192){
		cm.gainMeso(-100000);
		if (!cm.dropItem(selected, invv, selection)) {
			cm.sendOk("#fnSharing Ghotic Extrabold##r[Error Guidance]#k\r\n\r\nPlease try again.");
		}
                } else {
				cm.sendOk("#fnSharing Ghotic Extrabold##rThis item cannot be dropped.#k");
				cm.dispose();
                }
	} else {
	cm.sendOk("#fnSharing Ghotic Extrabold##rNot enough meso for drop.#k");
	cm.dispose();
	}
		cm.dispose();
	}
}
