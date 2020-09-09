/* Return to Masteria
    Evan's Whereabouts 5
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNext("The ground is still quaking! So... dizzy...");
	} else if (status == 1) {
	    qm.sendAcceptDecline("Why don't you take care of some Mixed Golems for me, so I can get my thoughts about Evan straight?\r\n(You will be moved to the map if you accept.)");
	} else if (status == 2) {
	    qm.sendNext("Thanks! Smashing 70 Mixed Golems should do the trick!");
	} else if (status == 3) {
	    qm.forceStartQuest();
		qm.warp(100040400,0);
        qm.dispose();
	}
}