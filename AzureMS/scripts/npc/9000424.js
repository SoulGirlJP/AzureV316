importPackage(Packages.constants);

var status = -1;

Star = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"

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
        var chat = "          #fnSharing Ghotic Extrabold##fs13# AzureMS Damage Upgrade shop!#n#k\r\n#fs11##Cgray#\r\n";
        chat += " #fs12#You can exchange your coins/box for special Damage!#fs12#\r\n\r\n";
        chat += "    #i4001623#   #i4310034#   #i4032101#   #i4031102#\r\n";
       // chat += "#r▶ #h0#'S support points are "+cm.getPlayer().getRC()+" Won.#k\r\n";
        //chat += "#b▶ #h0#'S #z4310070# The number of "+cm.itemQuantity(4310070)+" QTY.#k\r\n";
        //chat += "#L1#"+별보+"#d [HOT] Additional Damage\r\n";
        //chat += "#L4#"+별보+"#d [HOT] Additional Percent Damage\r\n";
        //chat += "#L5#"+별보+"#d [HOT] Support Skill\r\n";
        //chat += "#L2#"+별보+"#d [HOT] Enhance Donations [All Stat + 8 ATT + 15]\r\n";
       // chat += "#L10#"+별보+"#d [HOT] Donations Search Cash[All Stat 70 ATT 30]\r\n";
       chat += "#L1#" + Star  + " #d[#k#gExchange#k#d]#k #gEmerald Coins\r\n";
       chat += "#L2#" + Star  + " #d[#k#gExchange#k#d]#k #gJustice Coins\r\n";
       chat += "#L3#" + Star  + " #d[#k#gExchange#k#d]#k #gTreasure Boxes\r\n";
       chat += "#L4#" + Star  + " #d[#k#gExchange#k#d]#k #gMemory Cores\r\n";
        cm.sendSimple(chat);

    } else if (status == 1) {
        
     if (selection == 1) {
       cm.dispose();
       cm.openNpc(1501013);

    } else if (selection == 2) {
        cm.dispose();
        cm.openNpc(3003205);

    } else if (selection == 3) {
        cm.dispose();
        cm.openNpc(3003203);

    }   else if (selection == 4) {
        cm.dispose();
        cm.openNpc(1530619);

    }
        
        
    /*if (selection == 1) {
       cm.dispose();
       cm.openNpc(9050006);

    } else if (selection == 2) {
        cm.dispose();
        cm.openNpc(9010060);

    } else if (selection == 4) {
        cm.dispose();
        cm.openNpc(1104208);

    } else if (selection == 5) {
        cm.dispose();
        cm.openNpc(3003351);

    } else if (selection == 6) {
        cm.dispose();
        cm.openNpc(2400010);

    } else if (selection == 7) {
        cm.dispose();
        cm.openNpc(1032100);

    } else if (selection == 10) {
        cm.dispose();
        cm.openNpc(1540729);

		}*/
	}
}