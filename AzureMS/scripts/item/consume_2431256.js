
var status;
var select = -1;
var book  = new Array(3010690,3010734,3010851,3010878,3014000,3014001,3014002,3014004,3014003,3010844,3010815,3010798,3010700,3010720,3010705,3010702,3010695,3010694,3010693,3010692,3010691,3010685,3010683,3010682,3010679,3010677,3010674,3010673,3010672,3010659,3010656,3010655,3010654,3010653,3010652,3010651,3010644,3010643,3010642,3010641,3010640,3010637,3010636,3010624,3010623,3010622,3012017,3010613,3010612,3010611,3010601,3010600,3010598,3010597,3010596,3010593,3010592,3010590,3010589);

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
	    var text = "Please select the rare chair item you would like to receive #r.#l\r\n\r\n#b";
		for (var i = 0; i < book.length; i++) {
		    text+="#L"+i+"##i"+book[i]+"# #z"+book[i]+"##l\r\n";
		}
		cm.sendSimple(text);
	} else if (status == 1) {
		select = selection;
		cm.sendYesNo("Receive rare chair items #b#z"+book[select]+"##k Right?");
	} else if (status == 2) {
	    if (cm.haveItem(2431256, 1)) {
		if (cm.canHold(3010705)) {
		    cm.sendOk("Check your inventory");
		    cm.gainItem(2431256, -1);
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






