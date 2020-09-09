
importPackage(Packages.client.items);

importPackage(Packages.server.items);
importPackage(Packages.tools);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);
importPackage(Packages.server);
importPackage(Packages.server.items);
importPackage(Packages.server.life);
importPackage(Packages.tools.RandomStream);
var enter = "\r\n";
var seld = -1;
var 환포 = -1;
var ftype = "", fitem = -1, fqty = -1, fprice = -1;

// Rebirth Shop
var shop = [
	{'type' : "Additional Damage", 'itemid' : 0, 'min' : 150000, 'max' : 200000, 'price' : 2000, 'qty' : 1},
	{'type' : "Item", 'itemid' : 5680159, 'qty' : 1, 'price' : 2000, 'allstat' : 0, 'atk' : 0},
        {'type' : "Item", 'itemid' : 1022232, 'qty' : 1, 'price' : 15000, 'allstat' : 500, 'atk' : 500},
        {'type' : "Item", 'itemid' : 1122254, 'qty' : 1, 'price' : 15000, 'allstat' : 500, 'atk' : 500},
        {'type' : "Item", 'itemid' : 1662002, 'qty' : 1, 'price' : 3000, 'allstat' : 15, 'atk' : 15},
        {'type' : "Item", 'itemid' : 1662003, 'qty' : 1, 'price' : 3000, 'allstat' : 15, 'atk' : 15},
        {'type' : "Item", 'itemid' : 1672018, 'qty' : 1, 'price' : 3000, 'allstat' : 15, 'atk' : 15}
      
]

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
		var en4 = cm.getQuestRecord(201804);
		환포 = cm.parseInt(en4.getCustomData());

		var msg = "#fs11#Greetings. I am Azure's #rRebirth Shop#k.#k"+enter;
		msg += "#fs11#What would you like to purchase.#k"+enter;
		msg += "Current #b#h ##k, rebirth point : #b"+환포+"P#k"+enter+enter;
		var itemlist = "#fs14##e#rUseful Goods#n#fs10# (ITEMS)#k#fs11#"+enter;
		var addmg = "#fs14##e#rAdditional DPS#n#fs10# (DAMAGE)#k#fs11#"+enter;

		for (i = 0; i < shop.length; i++) {
			if (shop[i]['type'] == "Additional Damage") {
				addmg += "#L"+i+"#"+shop[i]['min']+"~"+shop[i]['max']+" Additional DPS"+enter;
				addmg += "　└ Price : #b"+shop[i]['price']+"P#k#l"+enter;
			} else if (shop[i]['type'] == "Item") {
				itemlist += "#L"+i+"##b#i"+shop[i]['itemid']+"##z"+shop[i]['itemid']+"##k"+enter;
				itemlist += "　└ Price : #b"+shop[i]['price']+"P#k All Stats : #b"+shop[i]['allstat']+"#k Att/Matt : #b"+shop[i]['atk']+"#k#l"+enter;
			}
		}
		msg += itemlist+enter;
		msg += addmg;
		cm.sendSimple(msg);

	} else if (status == 1) {
		seld = sel;
		ftype = shop[seld]['type'];
		fitem = shop[seld]['itemid'];
		fqty = shop[seld]['qty'];
		fprice = shop[seld]['price'];

		fffitem = ftype == "Item" ? "#b#i"+fitem+"##z"+fitem+"##k" : ftype == "AdditionalDamage" ? "#bAdditionalDamage#k" : "";

		var msg = "Information of the item you want to purchase.#fs11#"+enter;
		msg += " ◎ Item : "+fffitem+enter;
		if (ftype == "Item") {
			msg += " ◎ Number : #b"+fqty+"개#k"+enter;
		} else {
			msg += " ◎ Number : #b"+shop[seld]['min']+"~"+shop[seld]['max']+"#k"+enter;
		}
		msg += " ◎ Price : #bSponsor Point "+fprice+"P#k"+enter;
		if (ftype == "Item") {
			msg += " ◎ All Stats : #b"+shop[seld]['allstat']+"#k"+enter;
			msg += " ◎ ATK : #b"+shop[seld]['atk']+"#k"+enter;
		}
		msg += " ◎ If you really want to buy please click 'Yes'.";
		cm.sendYesNo(msg);
	} else if (status == 2) {
		if (환포 < fprice) {
			cm.sendOk("Please double check that you don't have enough rebirth points.");
			cm.dispose();
			return;
		}
                if (!cm.canHold(1662002,1)) {
		cm.sendOk("#fs11#Please free up more inventory space!");
		cm.dispose();
		return;
                }
		if (ftype == "아이템") {
                	ItemInfo = ItemInformation.getInstance().getEquipById(fitem);
		ItemInfo.setStr(shop[seld]['allstat']);
                	ItemInfo.setDex(shop[seld]['allstat']);
                	ItemInfo.setInt(shop[seld]['allstat']);
                	ItemInfo.setLuk(shop[seld]['allstat']);
                	ItemInfo.setWatk(shop[seld]['atk']);
                	ItemInfo.setMatk(shop[seld]['atk']);

                	InventoryManipulator.addFromDrop(cm.getClient(), ItemInfo, false);
			cm.getQuestRecord(201804).setCustomData((환포 - fprice) + "");
			cm.sendOk("The transaction has been completed.");
			cm.dispose();
		} else if (ftype == "AdditionalDamage") {
			qqq = Randomizer.rand(shop[seld]['min'], shop[seld]['max']);
			if (cm.getPlayer().getKeyValue("rcDamage") == null) cm.getPlayer().setKeyValue("rcDamage", "0");
			cm.getPlayer().setKeyValue("rcDamage", ""+(Long.parseLong(cm.getPlayer().getKeyValue("rcDamage")) + qqq));
			cm.getQuestRecord(201804).setCustomData((환포 - fprice) + "");
			cm.sendOk("The transaction has been completed."+enter+"#fs11##bNow #h #Additional damage from : "+Long.parseLong(cm.getPlayer().getKeyValue("rcDamage"))+"(+"+qqq+")");
			cm.dispose();
			//Chudem Payment
		}
	}
}