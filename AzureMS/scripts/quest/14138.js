/* Grand Athenaeum
    [Revamp Celebration] Lv. 10 Equipment Gift
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendNext("This gift is no longer available after #bLv. 14#k, so come see me before then!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	  qm.sendYesNo("11#k?! You already got your level that high?\r\nWell, I'm giving away equipment to help out you Pirate-type people! Do you want yours now?");
	} else if (status == 1) {
      qm.gainItem(2430443,1);
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
	  qm.dispose();
	}
}