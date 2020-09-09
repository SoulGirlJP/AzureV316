/* Return to Masteria
    Bluffing Tom's Mama
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.forceStartQuest();
		qm.dispose();
	}
}

function end(mode, type, selection) {
if (mode == -1) {
	qm.dispose();
    } else {
    if (mode == 1) {
        status++;
    } else 
        if (status == 3) {
		    qm.sendOk("I'm scared to go alone. Won't you come with me? Tom would...");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNextS("Oh, about time! You must be here to save me!",0,9390316);
	} else if (status == 1) {
	    qm.sendNextPrevS("Bluffing Tom said his mom should be around here. That you?",14);
	} else if (status == 2) {
	    qm.sendNextPrevS("What? My precious Tommy-boy is the one who rescued me? Oh, dear, sweet, precious, perfect boy!",0,9390316);
	} else if (status == 3) {
		qm.sendYesNoS("Come on. Let's get out of here. (You will be moved to a different map if you accept.)",0,9390313);
	} else if (status == 4) {
	    qm.forceCompleteQuest();
		qm.gainExp(764);
		qm.warp(866000000);
	    qm.dispose();		
	}
  }
}