/* Return to Masteria
	BeastTamer Tutorial
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("#b#h0##k, are you responsible for this? I know it's fun to pick on #b#p9390305##k, but it's really not very nice.");
	} else if (status == 1) {
      qm.sendNextPrev("I'm ashamed of you. Go apologize to #b#p9390305##k!");
	} else if  (status == 2)  {
	  qm.sendAcceptDecline("A true hero is never afraid to apologize!");
	} else if  (status == 3)  {
	  qm.sendNextS("I'm going to give the most epic apology ever!",15); 
	} else if  (status == 4)  {
	  qm.forceStartQuest();
	  qm.sendQuestWindow();
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
		qm.sendNextS("Hilga, I am truly, deeply, totally, completely sorry.",14); 
	} else if (status == 1) {
	    qm.sendNextPrevS("You pulled this prank on me, #h0#?",0,9390305); 
	} else if (status == 2) {
	    qm.sendNextPrevS("I thought everyone would find it entertaining, but I was wrong. I'm epically, heroically, massively sorry. Will you forgive me?",14); 
	} else if (status == 3) {
	    qm.sendNextPrevS("Sigh. You're still young, so I suppose I have to let it slide. Don't do it again, okay?",0,9390305); 
	} else if (status == 4) {
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
  }
}