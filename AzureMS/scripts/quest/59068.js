/* Return to Masteria
    Evan's Whereabouts 4
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNext("You're looking for Evan? I did happen to see him. But would you mind doing me a favor first?");
	} else if (status == 1) {
	    qm.sendAcceptDecline("I'm so dizzy... The Dark Stone Golems are making the ground quake! Can you do something about them?\r\n(You will be moved to the map if you accept.)");
	} else if (status == 2) {
	    qm.sendNext("Great! Smash 60 Dark Stone Golems to cut down the ruckus!");
	} else if (status == 3) {
	    qm.forceStartQuest();
		qm.warp(100040100,0);
        qm.dispose();
	}
}

function end(mode, type, selection) {
if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
		qm.forceCompleteQuest();
	    qm.dispose();		
	}
  }
}