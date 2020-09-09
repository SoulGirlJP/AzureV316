/* Return to Masteria
    Kobold Knowledge
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 3) {
		    qm.sendOk("Oh, is that all you wanted to ask about? Well, come back anytime you want to chat, especially if it's about my dear, precious Tommy-boy.");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("Yes?");
	} else if (status == 1) {
	    qm.sendNextPrevS("What can you tell me about the #b#o9390927##k at the #b#m866000150##k?",14);
	} else if (status == 2) {
	   qm.sendNextPrev("Nothing... I saw their #b#o9390915##k at the end of the #b#m866000150##k, but I'm sure that's not useful at all.");
	} else if (status == 3) {
	   qm.sendYesNoS("You're crazy! That's a HUGE help! If we get rid of their king, they'll be leaderless! Thanks, lady! (Woodrock'll definitely want to know about this!)",16);
	} else if (status == 4) {
	   qm.sendNext("See you later!");
	} else if (status == 5) {
	    qm.forceStartQuest();
	    qm.dispose();	
	}
}