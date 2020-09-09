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
	 cm.gainItem(2430275,-1);
               cm.teachSkill(80001033,1,1);
               cm.getPlayer().Message(6, "I got a ride (Spiegel's own hot air balloon)");
	 cm.dispose();
    }
}
}