����ǥ = "#fUI/UIWindow2.img/Quest/icon/icon0#"
icon = "#i3801315#"

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

	
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
	var msg = "#e#fnSharing Gothic##fs20##e#r  #fUI/FarmUI.img/objectStatus/star/whole5#   AzureMS Utility Shop   #fUI/FarmUI.img/objectStatus/star/whole5#\r\n\r\n#fs13##fc0xFF6B66FF#";
        msg += "#L0##fUI/FarmUI.img/objectStatus/star/whole7# Blue Orb Shop\r\n";
        msg += "#L1##fUI/FarmUI.img/objectStatus/star/whole16# Purple Orb Shop\r\n";
        msg += "#L2##fUI/FarmUI.img/objectStatus/star/whole2# Meso Exchange Shop\r\n";
        msg += "#L3##fUI/FarmUI.img/objectStatus/star/whole3# Boss Orb Exchange Shop";
	cm.sendSimple(msg);
    } else if (status == 1) {
	if (selection == 0) {
            cm.dispose();
            cm.openNpc(3003352);
        }
        else if (selection == 1) {
            cm.dispose();
            cm.openNpc(1013305); 
        }
        else if (selection == 2) {
            cm.dispose();
            cm.openNpc(1012112); 
        }
        else if (selection == 3) {
            cm.dispose();
            cm.openNpc(1013312); 
        }
    }
}
