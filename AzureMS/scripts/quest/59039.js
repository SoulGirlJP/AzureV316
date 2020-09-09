/* Return to Masteria
    The Kobold Threat 2
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendNext("I knew it! You've been talking to Bluffing Tom, haven't you?");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("These claws you got me will be the perfect finishing touch for my living room decor.");
	} else if (status == 1) {
	    qm.sendYesNo("Can you get me some more?");
	} else if (status == 2) {
	    qm.sendNext("I'm counting on you!");	
	} else if (status == 3) {
	    qm.sendNextPrevS("(Things still haven't been cleared up. Better get 15 more #b#t4034001##k.)",14);	
	} else if (status == 4) {
	    qm.forceStartQuest();
	    qm.dispose();	
	}
}