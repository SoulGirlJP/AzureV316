var status;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    if (!cm.canHold(2430143)) {
                cm.sendOk("Out of inventory space.");
		cm.dispose();
		return;
	    }
            cm.gainItem(2430143, -1);
		cm.getPlayer().addFame(10);
                cm.fakeRelog();
                cm.updateChar();
	    cm.getPlayer().dropMessage(1, "Someone "+cm.getPlayer().getName()+"Popularity thanks to the love letter you wrote for.");
	    cm.dispose();
            
        } else { 
            cm.dispose();
        }
    }
}