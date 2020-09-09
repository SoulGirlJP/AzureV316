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
	    cm.gainItem(5190000, 1);
	    cm.gainItem(5190001, 1);
	    cm.gainItem(5190002, 1);
	    cm.gainItem(5190003, 1);
	    cm.gainItem(5190004, 1);
	    cm.gainItem(5190005, 1);
	    cm.gainItem(5190006, 1);
	    cm.gainItem(5190010, 1);
	    cm.gainItem(2433018,-1);
	    cm.dispose();
        } else { 
            cm.dispose();
        }
    }
}