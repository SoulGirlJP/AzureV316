var select = -1;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	
	var text = "Tag content. Please select a menu\r\n";
	text += "#L0#I'll enter.\r\n";
	text += "#L1#I'll listen to the explanation.\r\n";
	cm.sendSimple(text);

} else if (status == 1) {
	 sel = selection;
	   if(sel == 0) {
	if (getPlayerCount(109090300) < 7 ) {
		cm.warp(109090300);
		cm.dispose();
                return;
	} else {
		cm.sendOk("Already seven.");
		cm.dispose();
                return;
		}
		} 

	}
	
	
	}
}


