
var status;
var select = -1;
var book  = new Array(1003175,1102278,1082298,1052317,1072488,1003173,1102276,1082296,1052315,1072486,1003174,1102277,1082297,1052316,1072487,1003172,1102275,1082295,1052314,1072485,1003176,1102279,1082299,1052318,1072489);

function start() {    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode <= 0) {
        cm.dispose();
    	return;
    } else {
        if (mode == 1)
            status++;
        if (status == 0) {
	    var text = "Please select the empress armor item you want to receive #r.#l\r\n\r\n#b";
		for (var i = 0; i < book.length; i++) {
		    text+="#L"+i+"##i"+book[i]+"# #z"+book[i]+"##l\r\n";
		}
		cm.sendSimple(text);
	} else if (status == 1) {
		select = selection;
		cm.sendYesNo("Received Armor Item #b#z"+book[select]+"##k Right?");
	} else if (status == 2) {
	    if (cm.haveItem(2431945, 1)) {
		if (cm.canHold(1003175)) {
		    cm.sendOk("Check your inventory");
		    cm.gainItem(2431945, -1);
		    cm.gainItem(book[select], 1);
		    cm.dispose();
		} else {
		    cm.sendOk("There is no empty space in the compartment.");
		    cm.dispose();
		}
            } else {
		cm.sendOk("Lack.");
		cm.dispose();

}
	}
    }
}






