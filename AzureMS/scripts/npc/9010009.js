importPackage(java.util);
importPackage(Packages.client.ItemInventory);
importPackage(Packages.client);
importPackage(Packages.server.Items);
importPackage(Packages.constants);
importPackage(Packages.LauncherHandlers);
importPackage(Packages.connections.Packets);
importPackage(Packages.tools);


var status = 0;
var operation = -1;
var select = -1;
var type;
var ty;
var gc = GameConstants;
var dd = true;
var yes= 1;
var invs = Array(1, 5);
var invv;
var selected;
var slot_1 = Array();
var slot_2 = Array();
var statsSel;
var sel;
var name;

var blockitems = [4310218,4310014,4310061,1142477,1142512,1142111,1142143,1142188,2431289,2436214,4001899,4001897,4001905,4001906,4001909,4001907,4001908,4001901,4001911,4001903,4001917,4001921,4001896,4001922,4001914,4001912,4001915,4001913,4001916,4001904,4001919,4001902,4001892,4310184,4310185,4310199,4001843,4001893,4033929,4001879,4001878,2433453,2434584,2434585,2434586,2434587,2433418,2430226,2435713,2436423,2435328,4310034,4032101,4031102,4001623];

function start() {
	status = -1;
	action(1, 0, 0);
}

// Gifting System

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			var ask = "Which type of item would you like to present?\r\n";
			ask +="#L1##b[Equip]#kI present an item.\r\n";
			ask +="#L2##b[Use]#kI present an item.\r\n";
			ask +="#L4##b[Etc]#kI present an item.\r\n";
			ask +="#L3##b[Set-up]#kI present an item.\r\n";
			ask +="#L5##b[Cash]#kI present an item.\r\n";
			ask +="#L6##bI want to hear an explanation.#k\r\n"
                        ask +="#L7##bI want to see the list of Blocked Items."
			cm.sendSimple(ask);
		} else if (status == 1) {
			operation = selection;
			if (operation == 1) {
				type = MapleInventoryType.EQUIP;
				yes = 1;
			} else if (operation == 2) {
				type = MapleInventoryType.USE;
				yes = 2;
			} else if (operation == 4) {
				type = MapleInventoryType.SETUP;
				yes = 4;
			} else if (operation == 3) {
				type = MapleInventoryType.ETC;
				yes = 3;
			} else if (operation == 5) {
				type = MapleInventoryType.CASH;
				yes = 5;
			}
			if (selection >= 1 && selection <=5) {
				cm.sendGetText("Please enter the nickname of the person receiving the gift.");
			} else if (selection == 6) {
				cm.sendOk("Gift system is a system that can be given to anyone        regardless of item type or option.\r\nYou need 5 million mesos and #i4310185# 1000 Purple orbs\r\nto make a gift and you must be connected to the same    channel.\r\nProblems caused by misuse are not the responsibility of  the operator.");
				cm.dispose();
			} else if (selection == 7) {
                                var items = "";
                                for (i = 0; i < blockitems.length; i++)
                                items += "#i"+blockitems[i]+"# \n";
				cm.sendOk(items);
				cm.dispose();
			}
		} else if (status == 2) {
			if (operation == 1) {
				type = MapleInventoryType.EQUIP;
			} else if (operation == 2) {
				type = MapleInventoryType.USE;
			} else if (operation == 3) {
				type = MapleInventoryType.SETUP;
			} else if (operation == 4) {
				type = MapleInventoryType.ETC;
			} else if (operation == 5) {
				type = MapleInventoryType.CASH;
			}
				var item = cm.getChar().getInventory(type);
		var text = cm.getText();
		var conn = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(text);
		if (conn == null){
		cm.sendOk("You are not currently connected or the channel is different. Or it may be an ID that doesn't exist.");
		cm.dispose();
		}else{
		var ok = false;
		var selStr = "#b"+conn.getName()+"#kWhich item would you like to gift to?\r\n";
		for (var x = 1; x < 2; x++) {
			var inv = cm.getInventory(yes);
			for (var i = 0; i <= cm.getInventory(yes).getSlotLimit(); i++) {
				if (x == 0) {
					slot_1.push(i);
				} else {
					slot_2.push(i);
				}
				var it = inv.getItem(i);
				if (it == null) {
					continue;
				}
				var itemid = it.getItemId();
				ok = true;
				selStr += "#L" + (yes * 1000 + i) + "##v" + itemid + "##t" + itemid + "##l\r\n";
			}
		}
		if (!ok) {
			cm.sendOk("There are no items to gift.");
			cm.dispose();
			return;
		}
		cm.sendSimple(selStr + "#k");
		}
		} else if (status == 3) {
		sel = selection;

			if (operation == 1) {
				type = MapleInventoryType.EQUIP;
			} else if (operation == 2) {
				type = MapleInventoryType.USE;
			} else if (operation == 3) {
				type = MapleInventoryType.SETUP;
			} else if (operation == 4) {
				type = MapleInventoryType.ETC;
			} else if (operation == 5) {
				type = MapleInventoryType.CASH;
			}
			var item = cm.getChar().getInventory(type).getItem(selection%1000).copy();
			var text = cm.getText();
			invv = selection / 1000;
			var inzz = cm.getInventory(invv);
			selected = selection % 1000;
				if (invv == invs[0]) {
					statsSel = inzz.getItem(slot_1[selected]);
				} else {
					statsSel = inzz.getItem(slot_2[selected]);
				}
				if (statsSel == null) {
					cm.sendOk("Error. Please report to the moderator.");
					cm.dispose();
					return;
				}
			var text = cm.getText();
			var con = cm.getClient().getChannelServer().isMyChannelConnected(text);
			var conn = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(text);
	if (!cm.getPlayer().isGM())
	for (i = 0; i < blockitems.length; i++) {
		if (item.getItemId() == blockitems[i]) {
			cm.sendOk("You cannot send this item by Mail.");
			cm.dispose();
			return;
		}
	}

	if (item.getQuantity() == 1){
		if (cm.getMeso()>=5000000 && cm.haveItem(4310185, 1000)){
			if (GameConstants.isPet(item.getItemId()) == false) {
				if (cm.getPlayer().getName() != text) {
			InventoryManipulator.removeFromSlot(cm.getC(), type, selection%1000, item.getQuantity(), true);
			InventoryManipulator.addFromDrop(conn.getClient(), item, true);
			cm.sendOk("#b"+text + "#k To #i"+item.getItemId()+"##b(#t"+item.getItemId()+"#)#kSent.");
			cm.gainMeso(-5000000)
                        cm.gainItem(4310185, -1000)
			cm.dispose();

			var itemName = ItemInformation.getInstance().getName(item.getItemId());
			FileoutputUtil.log(FileoutputUtil.택배로그, "Caller : " + cm.getPlayer().getName() + " / Receiver : " + text + " [ " + itemName + " 1개 ] Send");
			}else {
				cm.sendOk("You can't give it to yourself.");
				cm.dispose();
			}
			}else {
				cm.sendOk("Pets cannot be Gifted.");
				cm.dispose();
			}
			}else{
			cm.sendOk("There is not enough Mesos or Purple orbs.");
			cm.dispose();
			}
			}else {
				cm.sendGetText("How many would you like to give?\r\nCurrently in possession #i"+item.getItemId()+"# #b(#t"+item.getItemId()+"#)#k Amount : #b"+item.getQuantity()+"#k");
			}
			name = text;
		}else if (status==4){

		var sele = selection%1000;
		var quan = cm.getText();
			if (operation == 1) {
				type = MapleInventoryType.EQUIP;
			} else if (operation == 2) {
				type = MapleInventoryType.USE;
			} else if (operation == 3) {
				type = MapleInventoryType.SETUP;
			} else if (operation == 4) {
				type = MapleInventoryType.ETC;
			} else if (operation == 5) {
				type = MapleInventoryType.CASH;
			}
			var item = cm.getChar().getInventory(type).getItem(sel%1000).copy();
			var text = cm.getText();
			invv = sel / 1000;
			var inzz = cm.getInventory(invv);
			selected = sel % 1000;
				if (invv == invs[0]) {
					statsSel = inzz.getItem(slot_1[selected]);
				} else {
					statsSel = inzz.getItem(slot_2[selected]);
				}
				if (statsSel == null) {
					cm.sendOk("Error. Please report to the moderator.");
					cm.dispose();
					return;
				}
			var text = cm.getText();
			var con = cm.getClient().getChannelServer().isMyChannelConnected(name);
	        		var conn = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(name);
	if (item.getQuantity() >= text && cm.getText()>0) {
		if (cm.getMeso()>=5000000 && cm.haveItem(4310185, 1000)){
			if (cm.getPlayer().getName() != name) {
			item.setQuantity(text);
			InventoryManipulator.removeFromSlot(cm.getC(), type, sel%1000, item.getQuantity(), true);
			InventoryManipulator.addFromDrop(conn.getClient(), item, true);
			cm.sendOk("#b"+name + "#k To #i"+item.getItemId()+"##b(#t"+item.getItemId()+"#)#kSent.");
			cm.gainMeso(-1000000)
                        cm.gainItem(4310185, -1000)
			cm.dispose();

			var itemName = ItemInformation.getInstance().getName(item.getItemId());
			FileoutputUtil.log(FileoutputUtil.택배로그, "Caller : " + cm.getPlayer().getName() + " / Receiver : " + name + " [ " + itemName + " " + item.getQuantity() + "개 ] send");
			}else {
				cm.sendOk("You can't give it to yourself.");
				cm.dispose();
			}
			}else {
				cm.sendOk("There is not enough Mesos or Purple orbs.");
				cm.dispose();
			}
			}else {
				cm.sendOk("More than you have.");
				cm.dispose();
			}
		}
	}
}	
