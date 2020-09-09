




/*

	히나 온라인 소스 팩의 스크립트 입니다.

        제작 : 티썬

	엔피시아이디 : 
	
	엔피시 이름 :

	엔피시가 있는 맵 : 

	엔피시 설명 : 


*/

importPackage(Packages.client.items);
importPackage(java.lang);
importPackage(Packages.tools.RandomStream);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);
var status = 0;
var ran = 300;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        var leftslot = cm.getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot();
        if (leftslot < 1) {
            cm.sendOk("Please empty the window.");
            cm.dispose();
            return;
        }
	ran = Randomizer.rand(300,300);
        if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431655# #b#z2431655##k Came out.");
            cm.gainItem(2431655, 1);
        } else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431656# #b#z2431656##k Came out.");
            cm.gainItem(2431656, 1);
        } else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431657# #b#z2431657##k Came out.");
            cm.gainItem(2431657, 1);
        } else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431658# #b#z2431658##k Came out.");
            cm.gainItem(2431658, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {  
            cm.sendOk("#i2431659# #b#z2431659##k Came out.");
            cm.gainItem(2431659, 1);
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431660# #b#z2431660##k Came out.");
            cm.gainItem(2431660, 1);
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431661# #b#z2431661##k Came out");
            cm.gainItem(2431661, 1);
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431662# #b#z2431662##k v."); 
            cm.gainItem(2431662, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431709# #b#z2431709##k Came out.");
            cm.gainItem(2431709, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431710# #b#z2431710##k Came out.");
            cm.gainItem(2431710, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431711# #b#z2431711##k Came out.");
            cm.gainItem(2431711, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
	    cm.sendOk("#i2431752# #b#z2431752##k Came out.");
            cm.gainItem(2431752, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
	    cm.sendOk("#i2431753# #b#z2431753##k Came out.");
            cm.gainItem(2431753, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431895# #b#z2431895##k Came out.");
	    cm.gainItem(2431895, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431896# #b#z2431896##k Came out."); 
	    cm.gainItem(2431896, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431963# #b#z2431963##k Came out.");
	    cm.gainItem(2431963, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2431964# #b#z2431964##k Came out.");
	    cm.gainItem(2431964, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2432138# #b#z2432138##k Came out.");
	    cm.gainItem(2432138, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2432575# #b#z2432575##k Came out.");
	    cm.gainItem(2432575, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2432576# #b#z2432576##k Came out.");
	    cm.gainItem(2432576, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2432577# #b#z2432577##k Came out.");
	    cm.gainItem(2432577, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2432578# #b#z2432578##k Came out.");
	    cm.gainItem(2432578, 1); 
	} else if (Randomizer.nextInt(300) <= 20) {
            cm.sendOk("#i2432579# #b#z2432579##k Came out.");
	    cm.gainItem(2432579, 1); 
	} else {
	    cm.sendOk("Unfortunately #rWhack#k ! next time....");
}
	cm.gainItem(2431183, -1);
        cm.dispose();
    }
}

