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
            cm.gainSponserItem(1112943,'[time]',300,50,0);
            cm.gainSponserItem(3700148,'[time]',300,50,0);
	    cm.gainItem(4001832, 1000);
            cm.gainMeso(100000000);
	    cm.gainItem(2432669,-1);
	    cm.dispose();
        } else { 
            cm.dispose();
        }
    }
}