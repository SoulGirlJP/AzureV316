
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
            cps = "                     #e<AzureMS Story All-round Store>#n\r\n";
            cps = " #fnSharing Ghotic Extrabold##fs15##b#h ##k #fnSharing Ghotic Extrabold##fs15#'S Info.#fnSharing Ghotic Extrabold##fs12#\r\n Level : "+ cm.getPlayer().getLevel() +"¡¡Meso's : " + cm.getPlayer().getMeso();
            cps += "\r\n#L1##fs 13##e#r  Job shop#d ( Equipment Shops by Job )#k";
           cps += "\r\n#L2##fs 13##e#b  Infinity store#d ( Special Equips )#k";
           cps += "\r\n#L3##fs 13##e#b  Consume shop#d (Potions, Spell trace, etc)#k";
           //cps += "\r\n#L4##fs 13##e#r  Special shop#d (Accessory, ring, android, etc)#k";
           cps += "\r\n#L6##fs 13##e#r  Secondary weapon#d (Secondary weapon shop)#k";
            cm.sendSimple(cps);         
        } else if (selection == 1) {
            cm.dispose();
            cm.openNpc(3003155);
        } else if (selection == 2) {
            cm.dispose();
            cm.openNpc(3003153);
        } else if (selection == 3) {
            cm.dispose();
            cm.openNpc(2411025);
        } else if (selection == 4) {
            cm.dispose();
            cm.openNpc(3003154);         
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