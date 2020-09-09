
var k = "#fUI/UIToolTip/Item/Equip/Star/Star#"

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
            cps = "                     #e<AzureMS, Cash Shop>#n\r\n";
            cps = " #fnSharing Gothic Extrabold##fs15##b#h ##k #fnSharing Gothic Extrabold##fs15#'S Info.#fnSharing Gothic Extrabold##fs12#\r\n Level : "+ cm.getPlayer().getLevel() +"¡¡Meso : " + cm.getPlayer().getMeso()+ " Won#n\r\n\r\n";
            cps += "\r\n#L1##fs 13##e#r  Hair Modification shop #d#k";
           cps += "\r\n#L2##fs 13##e#r  Search Cash#d#k";

            cm.sendSimple(cps);         
        } else if (selection == 1) {
            cm.dispose();
            cm.openNpc(2010001);
        } else if (selection == 2) {
            cm.dispose();
            cm.openNpc(1530141);
        } else if (selection == 3) {
            cm.dispose();
            cm.openNpc(2411025);
        } else if (selection == 4) {
            cm.dispose();
            cm.openNpc(3003154);         
        } else if (selection == 5) {
            cm.dispose();
            cm.openNpc(1530556);
        } else if (selection == 7) {
            cm.dispose();
            cm.openNpc(1540010);
        } else if (selection == 6) {
            cm.dispose();
            cm.openShop(9900000);
        } else if (selection == 100014) {
            cm.dispose();
            cm.openNpc(2450023);
        } else if (selection == 100015) {
            cm.dispose();
            cm.openNpc(1512003);
        } else if (selection == 100016) {
            cm.dispose();
            cm.openNpc(9001119);
        } else if (selection == 100017) {
            cm.dispose();
            cm.openNpc(9001119);
        } else if (selection >= 0) {
            cm.CollectivelyShop(selection, 1530429);
            cm.dispose();
        }
    }
}