/* Return to Masteria
    Evan's Whereabouts 2
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNext("So, here's the deal. I heard that Evan's on a mission for the Maple Alliance. That's all I know.");
	} else if (status == 1) {
        qm.sendNextPrev("But I can point you toward someone who DOES know more... if you help me with a little something.");
 	} else if (status == 2) {
	    qm.sendYesNo("I need you to hunt 50 Stone Golems. They've been interfering with my research. Then I'll tell you who to talk to. What do you say? (You will be moved if you accept.)");
	} else if (status == 3) {
	    qm.sendNext("Of course, you can hunt more than that if you like!");
	} else if (status == 4) {
	    qm.forceStartQuest();
		qm.warp(100040000);
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
	    qm.sendNext("You thinned them out! Wonderful! Today, you are the archeology's hero! Be proud!");
	} else if (status == 1) {
	    qm.sendNextPrev("Little #bPia#k on the left side of #bHenesys#k saw Evan recently. Talk to #bPia#k for more info. (Accept to complete the quest and teleport to Henesys)");
	} else if (status == 2) {
		qm.forceCompleteQuest();
		qm.forceStartQuest(59079);
		qm.gainExp(4431);
		qm.warp(100000000,0);
	    qm.dispose();		
	}
  }
}