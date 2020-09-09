/*

제작자 : 타임 (time_amd@nate.com)

*/

 

var status = -1;
var time = "#fUI/UIToolTip/Item/Equip/Star/Star#"
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
        Store selection from 0 to 99
        Move selection is from 100 to 199
        Content selection is from 200 to 299
        Operator selection is from 300 to 399
        */
          var choose = "";
         choose += "　#L2##fnSharing Ghotic Extrabold##fs18##i2434910##k　#bJustice Search Cash  #k#l#r\r\n";

         choose += "　#L3##fnSharing Ghotic Extrabold##fs18##i2436214##k　#bJustice Soul Random  #k#l#r\r\n";

        cm.sendSimple(choose);

    } else if (status == 1) {
        var s = selection;
        cm.dispose();
        if (s == 0) {
            cm.openNpc (1022107);

 } else if (s == 2) {
  cm.openNpc (9001193);

 } else if (s == 3) {
  cm.openNpc (1540894);
}else if (s == 155) {
	    cm.dispose();
	    cm.openNpc(1540893);

        }
    }
}
