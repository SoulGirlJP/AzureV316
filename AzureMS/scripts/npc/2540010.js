importPackage(Packages.constants);

var status = -1;

var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
var HOT = "#fUI/CashShop.img/CSEffect/hot/0#";
var NEW = "#fUI/CashShop.img/CSEffect/new/0#";


function start() {
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
/*
	if (!cm.getPlayer().isGM()) {
	cm.sendOk("공사중입니다");
	cm.dispose();
	return;
	}
*/
        var choose = "             #fnSharing Ghotic Extrabold##fs17##fUI/FarmUI.img/objectStatus/star/whole#AzureMS boss system#fUI/FarmUI.img/objectStatus/star/whole#\r\n#fs11##Cgray#             #dWelcome to the AzureMS Boss System.#k\r\n\r\n#fs11#";
        choose += "#L2##rAzureMS Bull#k #d( ★ ) [ HP 300조 ] #i4032101# 10#k\r\n";
        choose += "#L3##rAzureMS Centipede#k #d( ★ ) [ HP 660조 ] #i4032101# 20#k \r\n";
        choose += "#L4##rAzureMS Reaper#k #d( ★ ) [ HP 1020조 ] #i4032101# 30\r\n";
        choose += "#L5##rSea dragon#k #d( ★★ ) [ HP 1740조 ] #i4032101# 50\r\n";
        //choose += "#L6##rAzureMS Wolf #k #d( ★★ ) [ HP 2160조 ] #i4032101# 60\r\n";
        choose += "#L7##rFake will #k #d( ★★ ) [ HP 2700조 ] #i4032101# 70\r\n";
        choose += "#L8##rSpirit of Harmony #k #d( ★★ ) [ HP 3300조 ] #i4032101# 80\r\n";
        //choose += "#L9##rDamien #k #d( ★★★ ) [ HP 4800조 ] #i4032101# 100\r\n";
        cm.sendSimple(choose);

    } else if (selection == 2) {// AzureMS bull
        cm.dispose();
  	cm.openNpc(2133007);

    } else if (selection == 3) {// AzureMS Centipede
        cm.dispose();
  	cm.openNpc(1540211);

    } else if (selection == 4) {//Reaper
        cm.dispose();
  	cm.openNpc(1540102);

    } else if (selection == 5) {//Sea dragon
        cm.dispose();
  	cm.openNpc(2510037);

    } else if (selection == 6) {// Wolf
        cm.dispose();
  	cm.openNpc(1540110);

    } else if (selection == 7) {//Fake will
        cm.dispose();
  	cm.openNpc(9000078);
    } else if (selection == 8) {//Spirit of Harmony
	cm.dispose();
        cm.openNpc(9000074); 
    } else if (selection == 9) {// Damien
	cm.dispose();
        cm.openNpc(3003323); 
}
}