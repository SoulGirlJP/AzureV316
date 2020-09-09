/* Return to Masteria
    Finding King Kobold
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendOk("Our town's in danger. If only a great hero would help us...");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("There's only one thing to do: hunt #o9390915#!");
	} else if (status == 1) {
	   qm.sendYesNo("The others will scatter if their leader is taken out. Can you do it?\r\n(You'll be moved to a nearby map if you accept.)");
	} else if (status == 2) {
	   qm.sendNext("Defeating #o9390915# won't be easy.");
	} else if (status == 3) {
	    qm.forceStartQuest();
		qm.warp(866000150,0);
	    qm.dispose();	
	}
}