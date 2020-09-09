
var status;
var select = -1;
var book  = new Array(2591010,2591011,2591012,2591013,2591014,2591015,2591016,2591089,2591090,2591091,2591092,2591093,2591094,2591095);

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
	    if(!cm.haveItem(2431655, 10)) { 
		cm.sendOk("#eSoul sculpture#n I don't think there are 10 of these.");
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
		cm.sendYesNo("Soul to receive #b#t"+book[select]+"##kIs this correct?");
	} else if (status == 2) {
		cm.gainItem(book[select], 1);
		cm.gainItem(2431655, -10);
		cm.dispose();
			}
    		}
	}
