/* Return to Masteria
    Kobolds... For Real?
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 2) {
		    qm.sendNext("I feel like I can trust you now. Please help us.");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("Ah, there you are. I've been thinking...");
	} else if (status == 1) {
	    qm.sendNextPrev("The fact that both you and Bluffing Tom have been going on and on about kobolds is making me uneasy...");
	} else if (status == 2) {
	    qm.sendYesNo("Would you mind checking on the kobolds in the forests outside town?");	
	} else if (status == 3) {
	    qm.sendNextS("FINALLY the truth is getting through your thick skull! I'll go right away!",14);	
	} else if (status == 4) {
	    qm.sendNextPrevS("(Dun, dun, dun! A hero rushes to the forest!)",14);	
	} else if (status == 5) {
	    qm.forceStartQuest();
		qm.forceCompleteQuest();
	    qm.dispose();	
	}
}