/*
	토토로(mst_totoro@naver.com) 제작

*/
importPackage(Packages.client.items);

var item = [4031456]; // Quest material code, if you want to increase the item type [4000000, 40000001]
var cost = [300]; // Number of quest materials, same as above
var gain = 1142974; // Quest reward code

// From here
var icon_start = "#fUI/UIWindow2/UtilDlgEx/list1#"
var icon_doing = "#fUI/UIWindow2/UtilDlgEx/list0#"
var icon_complete = "#fUI/UIWindow2/UtilDlgEx/list3#"
var icon_etc = "#fUI/UIWindow2/UtilDlgEx/list2#"
var icon_getitem = "#fUI/UIWindow2/QuestIcon/4/0#"
// Quest icon, don't touch this

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0 && status == 2) {
       cm.sendNext("Happy #dAzureMS#k Have"); // Message to decline when asked to accept quest
       cm.dispose();
       return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }
   // From here
   quest = cm.getQuestRecord(19021500);
   if (quest.getCustomData() == null) {
	quest.setStatus(0);
	quest.setCustomData("0");
   }
   // Quest Declaration so far, don't touch

    if (status == 0) {
	qstatus = quest.getStatus(); // Quest progress
	for (i = 0; i < item.length; i++) {
		itemcheck = (cm.haveItem(item[i], cost[i]) && cm.itemQuantity(item[i]) >= cost[i]); // Quest Material Check
		cm.getPlayer().dropMessage(5, "" + itemcheck);
	}
	var ms = ""
	ms += "Oh, how can I really do this? I need fishing beads..\r\n\r\n" // Quest message
	if (qstatus != 2) {
		if (qstatus == 0) {
			ms += icon_start + "\r\n"
		} else if (qstatus == 1 && !itemcheck) {
			ms += icon_doing + "\r\n"
		} else if (qstatus == 1 && itemcheck) {
			ms += icon_complete + "\r\n"
		}
		ms += "#b#L0#Let's become Kang Tae Gong!.#l\r\n" // Quest Title Name
		if (qstatus != 0) {
			ms += "\r\n\r\n"
			ms += icon_etc + "\r\n"
			ms += "#b#L1#I'll give up the quest.#l\r\n" // Quest abandon name
		}
		cm.sendNext(ms);
	} else {
		cm.sendNext("You have already completed this quest."); // Quest Message if you have already completed
		cm.dispose();
	}
    } else if (status == 1) {
	sel = selection;
	if (sel == 0) {
		if (qstatus == 0) {
			cm.sendNext("I'm going to make a pretty necklace with fishing beads!"); // Message on Accepting Quest First
		} else if (qstatus == 1 && !itemcheck) {
			var ms = ""
			ms += "You haven't collected all the items you need yet?\r\n\r\n"
			ms += "#r#e< Necessary items >#k#n\r\n\r\n"
			for (i = 0; i < item.length; i++) {
				ms += "- #i" + item[i] + "# #b#z" + item[i] + "# " + cost[i] + " QTY#k\r\n"
			}
			cm.sendNext(ms); // Quest message in progress
			cm.dispose();
		} else if (qstatus == 1 && itemcheck) {
			cm.sendNext("You've collected all the items you need!\r\n"); // Quest complete message, first
		}
	} else if (sel == 1) {
		quest.setCustomData("0");
		quest.setStatus(0);
		cm.sendNext("I'm sorry. Please come back next time.\r\n"); // Quest message when giving up
		cm.dispose();
	}
    } else if (status == 2) {
	if (qstatus == 0) {
		cm.sendYesNo("You can collect the fishing beads and bring them to me?"); // Message to ask if you want to accept the quest
	} else if (qstatus == 1 && itemcheck) {
		equipslot = cm.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot(); // Equipment slot check
		if (equipslot >= 1) {
			var ms = ""
			ms += "Quest complete message\r\n\r\n" // Quest complete message, second
			ms += icon_getitem + "\r\n"
			ms += "#i" + gain + "# #b#z" + gain + "# 1 QTY#k\r\n"
			quest.setStatus(2);
			quest.setCustomData("2");
			for (i = 0; i < item.length; i++) {
				cm.gainItem(item[i], -cost[i]);
			}
			cm.gainItem(gain, 1);
			cm.sendNext(ms);
			cm.dispose();
		} else {
			cm.sendNext("Please empty at least 1 slot of inventory equipment slot."); // Message when there is no equipment
			cm.dispose();
		}
	}
    } else if (status == 3) {
	if (qstatus == 0) {
		quest.setStatus(1);
		quest.setCustomData("1");
		cm.sendNext("Thanks ! Please collect it and bring it !! "); // Thank you when you accept quest
		cm.dispose();
	}
    }
}
