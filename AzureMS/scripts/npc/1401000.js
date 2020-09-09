/*
  제작자 우비
*/

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
            
	       var chat = "";
	       chat += "#L1#[모든직업]스킬학습";	       
               chat += "\r\n#L402#[시그너스]스킬학습";

	       cm.sendSimple(chat);

	     } else if (selection == 1) {
		cm.dispose();
		cm.openNpc(1002006);
	     } else if (selection == 400) {
		cm.dispose();
		cm.openShop(1010100);
	     } else if (selection == 401) {
		cm.dispose();
		cm.openShop(9020004);
	     } else if (selection == 402) {
		cm.dispose();
		cm.openNpc(1402301);	
	     } else if (selection == 403) {
		cm.dispose();
		cm.openNpc(1033200);	
	     } else if (selection == 404) {
		cm.dispose();
		cm.openShop(10009);		
	     } else if (selection == 405) {
		cm.dispose();
		cm.openShop(2400004);	
			}
    	}
}





