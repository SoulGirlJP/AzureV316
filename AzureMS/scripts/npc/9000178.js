

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
	       var chat = "#fs15##n#k #k#r       What will you exchange?#n";
	       chat += "#fs15##n#k\r\n\r\n#r #e#b#h0##k'S#k#e#r Additional Damage#n#k #e#d"+cm.getPlayer().getAddDamage()+"#fs14#"
     
               chat += "\r\n\r\n#L3##b#i4310034# #eJerko Shop";

               chat += "\r\n\r\n#L4##b#i4032101# #eTreasure Shop";

	       cm.sendSimple(chat);

	    }  if (selection >= 9999999) {
		    cm.dispose();
             } else if (selection == 3) {
		cm.dispose();
		cm.openNpc(3003502);
		return;

             } else if (selection == 4) {
		cm.dispose();
		cm.openNpc(3003203);
		return;
		}
	}
}