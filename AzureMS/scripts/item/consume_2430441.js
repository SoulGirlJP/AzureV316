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
            cm.getPlayer().modifyCSPoints(1, 600000, true);
	    cm.gainItem(2430441,-1);
	    cm.dispose();
        } else { 
            cm.dispose();
        }
    }
}