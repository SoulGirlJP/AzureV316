
var status;
var select = -1;
var book  = new Array(1003797,1003798,1003799,1003800,1003801,1042254,1042255,1042256,1042257,1042258,1062165,1062166,1062167,1062168,1062169);

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
	    var text = "Choose the 150 armor you want to receive#l\r\n\r\n#b";
		for (var i = 0; i < book.length; i++) {
		    text+="#L"+i+"##i"+book[i]+"# #z"+book[i]+"##l\r\n";
		}
		cm.sendSimple(text);
	} else if (status == 1) {
		select = selection;
		cm.sendYesNo("The 150 armor that you will receive #b#z"+book[select]+"##k Right?");
	} else if (status == 2) {
	    if (cm.haveItem(2432341, 1)) {
		if (cm.canHold(1003797)) {
		    cm.sendOk("Check your inventory");
		    cm.gainItem(2432341, -1);
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







