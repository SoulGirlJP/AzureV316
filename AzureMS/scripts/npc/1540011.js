importPackage(Packages.packet.creators);
importPackage(Packages.handler.channel);
importPackage(Packages.constants);

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
   cm.dispose();
   return;
    }
    if (status == 0) {
   var chat = "#k#d#fs11##fUI/GuildMark.img/Mark/Pattern/00004001/14# Hi. #h #sir! #rAzureMS Cash Shop#k #dis.#k#fs11#\r\n";
        chat += "#k#d#fs11##L5##fUI/GuildMark.img/Mark/Pattern/00004001/11# #rCody Shop#k#dI will enter.#k\r\n";
       // chat += "#k#d#fs11##L0##fUI/GuildMark.img/Mark/Pattern/00004001/13# #rCash shop#k#dI will enter.#k\r\n";
        //chat += "#k#d#fs11##L1##fUI/GuildMark.img/Mark/Pattern/00004001/12# #rSearch cash#k#dI will use.#k#k";
          
      
   cm.sendSimple(chat);
    } else if (status == 1) {
        select = selection;
        if (select == 0) {
           cm.dispose();
           InterServerHandler.EnterCS(cm.getClient(), cm.getPlayer(), false);
         }
        if (select == 1) {
        cm.dispose();
        cm.getClient().setClickedNPC(1000);
        cm.openNpc(1540011, "itemSearch");
       }
	if (select == 5) {
        		cm.dispose();
        		cm.openNpc(9010100);
	}
    } else if (status == 2) {
        if (select == 1) {
            cm.dispose();
          }
    }
}

