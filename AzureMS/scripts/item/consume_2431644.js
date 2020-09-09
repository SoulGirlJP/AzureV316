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
	    cm.gainItem(2049704, 20);
	    cm.gainItem(4001832, 1000);
	    cm.gainItem(2430218, 1);
	    cm.gainItem(2430026, 1);
	    
	    
            cm.gainMeso(10000000);
	    cm.gainItem(2431644,-1);
	    cm.dispose();
        } else { 
            cm.dispose();
        }
    }
}