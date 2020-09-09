

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
	       var chat = "#fs15##n#k #k#r              Let's buy Chudem!#n";
	       chat += "#fs15##n#k\r\n\r\n#r #e#b#h0##k'S #k#e#rAdditional Damage#n#k #e#d"+cm.getPlayer().getAddDamage()+"#fs14#"

	       //chat += "\r\n\r\n#L1##b#i4310210# #eVCoin chudem Store";

               
               chat += "\r\n\r\n#L3##b#i4310034# #eJerko Chudem Store";

               chat += "\r\n\r\n#L4##b#i4032101# #eTreasure Chudem Shop";

	       cm.sendSimple(chat);

	    }  if (selection >= 9999999) {
		    cm.dispose();
             } else if (selection == 1) {
		cm.dispose();
		cm.openNpc(3003201);
		return;
             } else if (selection == 3) {
		cm.dispose();
		cm.openNpc(3003205);
		return;

             } else if (selection == 4) {
		cm.dispose();
		cm.openNpc(3003203);
		return;

             } else if (selection == 10001) {
		cm.dispose();
		cm.openNpc(1033221);
		return;

             } else if (selection == 10002) {
		cm.dispose();
		cm.openNpc(1033211);
		return;
             } else if (selection == 10003) {
		cm.dispose();
		cm.openNpc(1033206);
		return;
             } else if (selection == 10004) {
		cm.dispose();
		cm.openNpc(1033205);
		return;
             } else if (selection == 10009) {
		cm.dispose();
		cm.openNpc(1033111);
		return;
 } else if (selection == 20000) {
		cm.dispose();
		cm.openNpc(9072100);
		return;
             } else if (selection == 10006) {
		cm.dispose();
		cm.openNpc(1033202);
		return;
             } else if (selection == 10007) {
		cm.dispose();
		cm.openNpc(1033201);
		return;
            } else if (selection == 10008) {
		cm.dispose();
		cm.openNpc(1033200);
		return;
            } else if (selection == 10011) {
		cm.dispose();
		cm.openNpc(1033110);
		return;
  } else if (selection == 20001) {
		cm.dispose();
		cm.openNpc(1033105);
		return;
} else if (selection == 998) {
		cm.dispose();
		cm.openNpc(9072201);
		return;





		}
	}
}