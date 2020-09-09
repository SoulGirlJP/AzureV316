importPackage(Packages.client.items);
var status = -1;
var sel = 0;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
	var list = cm.getCashEquipList();
	if (list == "") {
		cm.sendOk("There is no cache device in place.");
		cm.dispose();
		return;
	}
	var chat = "Please select a cache device to set and reset the potential\r\n#b";
	chat += list;
	cm.sendSimple(chat);
    } else if (status == 1) {
	sel = selection;
	cm.sendYesNo("Really selected item #b#t" + cm.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(sel).getItemId() + "##kDo you want to set and reset the potential on?");
    } else if (status == 2) {
	cm.renewCashPotential(sel);
	cm.gainItem(2431578,-1);
	cm.sendOk("Potential setting and resetting completed. Please confirm an item");
	cm.dispose();
    }
}
