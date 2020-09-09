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
	    if (!cm.canHold(2290285)) {
                cm.sendOk("Out of inventory space.");
		cm.dispose();
		return;
	    }
            cm.gainItem(2430144, -1);
	    cm.gainItem(2290285, 1);
	    cm.getPlayer().dropMessage(6, "It was exchanged for mystery mastery book.");
	    cm.dispose();
            
        } else { 
            cm.dispose();
        }
    }
}