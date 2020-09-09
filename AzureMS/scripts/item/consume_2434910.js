var status = -1;
var sel = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
            cm.getC().removeScriptEngine("consume_2434910.js");
        }
        status = -1;
        cm.dispose();
        cm.getC().removeScriptEngine("consume_2434910.js");
    }
    if (status == 0) {
        cm.sendGetText("\r\n#ePlease enter the name of the cash item to be acquired.#n\r\n#Cgray#- We can't refund items that were purchased inadvertently.#k\r\n\r\n#b Obtain a Cache Item granted with [All Stats 500]..#k\r\n\r\n#r¡Ø If you don't know the exact name, just enter the word included.");
    } else if (status == 1) {
        if (cm.getText().equals("") || cm.getText().equals(" ")) {
            cm.sendOk("You mistyped.");
            cm.dispose();
            return;
        }
        var t = cm.searchCashItem(cm.getText());
        if (t.equals("")) {
            cm.sendOk("No Results Found.");
            cm.dispose();
            return;
        }
        cm.sendSimple("Entered [#b" + cm.getText() + "#k] Search results for.\r\n\r\n" + t);
    } else if (status == 2) {
        sel = selection;
        cm.sendYesNo("Really chosen #i" + sel + "##b#t" + sel + "##k Would you like to be paid?");
    } else if (status == 3) {
        if (!cm.canHold(sel)) {
            cm.sendOk("Please create at least 1 space in the Inventory Equipment tab and try again.");
            cm.dispose();
            return;
        }
        cm.gainItemAllStat(sel, 1, 500,100);
        cm.gainItem(2434910,-1);
        cm.sendOk("We have paid the selected item.");
        cm.dispose();
        cm.getC().removeScriptEngine("consume_2434910.js");
    }
}