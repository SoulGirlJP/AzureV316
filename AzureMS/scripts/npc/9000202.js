

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
	       var chat = "#fs15##n#k #k#r              Meadow Title Quest!!#n";

	       chat += "\r\n\r\n#L1##b#i1142974# #eFishing King Title";
               
               chat += "\r\n\r\n#L3##b#i1142973# #eJump King title";

	       cm.sendSimple(chat);

	    }  if (selection >= 9999999) {
		    cm.dispose();
             } else if (selection == 1) {
		cm.dispose();
		cm.openNpc(2012012);
		return;
             } else if (selection == 3) {
		cm.dispose();
		cm.openNpc(9000397);
		return;
} else if (selection == 998) {
		cm.dispose();
		cm.openNpc(9072201);
		return;





		}
	}
}