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
	    cm.gainItem(2591163, 1);
	    cm.gainItem(2590008, 1);
            cm.sendOk("The following items have been paid!\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n\r\n#i2590008# #b#z2590008##k\r\n#i2591163# #b#z2591163##k");
	    cm.gainItem(2431110,-1);
	    cm.dispose();
        } else { 
            cm.dispose();
        }
    }
}