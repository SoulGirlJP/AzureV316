
var status;
var select = -1;
var book  = new Array(2591194,2591195,2591196,2591197,2591198,2591199,2591200,2591201,2591202);

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
	    if(!cm.haveItem(2431753, 10)) { 
		cm.sendOk("#eSoul sculpture#nI don't think there are 10 of these.");
		cm.dispose();
		} else {
	    var text = "#b#h ##kYou already #eSoul sculpture#n You collected 10!\r\nCongratulations, choose the soul you want to receive.\r\n\r\n#b";
		for (var i = 0; i < book.length; i++) {
		    text+="#L"+i+"##i"+book[i]+"# #z"+book[i]+"##l\r\n";
		}
				cm.sendSimple(text);
		}
	} else if (status == 1) {
		select = selection;
		cm.sendYesNo("Soul to receive #b#t"+book[select]+"##k Is this correct?");
	} else if (status == 2) {
		cm.gainItem(book[select], 1);
		cm.gainItem(2431753, -10);
		cm.dispose();
			}
    		}
	}
