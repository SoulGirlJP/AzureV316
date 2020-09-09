
var status;
var select = -1;
var book  = new Array(1102481,1102482,1102483,1102484,1102485,1072743,1072744,1072745,1072746,1072747,1132174,1132175,1132176,1132177,1132178,1082543,1082544,1082545,1082546,1082547);

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
	    var text = "Choose the lentive armor you want to receive#l\r\n\r\n#b";
		for (var i = 0; i < book.length; i++) {
		    text+="#L"+i+"##i"+book[i]+"# #z"+book[i]+"##l\r\n";
		}
		cm.sendSimple(text);
	} else if (status == 1) {
		select = selection;
		cm.sendYesNo("The talented armor to get #b#z"+book[select]+"##k Right?");
	} else if (status == 2) {
	    if (cm.haveItem(2432069, 1)) {
		if (cm.canHold(1102481)) {
		    cm.sendOk("Check your inventory");
		    cm.gainItem(2432069, -1);
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






