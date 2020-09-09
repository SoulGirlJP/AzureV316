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
	  qm.sendNext("Also, I saw #b#p9390306##k crying... Do you know anything about that?");
	} else if (status == 1) {
      qm.sendNextPrevS("Well...",14);
	} else if  (status == 2)  {
	  qm.sendAcceptDecline("#b#h0##k! You have to treat your friends better! Apologize to #b#p9390306##k!");
	} else if  (status == 3)  {
	  qm.sendNextS("You got it! A true hero is excellent at delivering apologies, as you just experienced!",15);
	} else if  (status == 4)  {
	  qm.forceStartQuest();
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
		qm.sendNextS("Hi, Bran... I'm really sorry...",14); 
	} else if (status == 1) {
	    qm.sendNextPrevS("I knew it was you...",0,9390306); 
	} else if (status == 2) {
	    qm.sendNextPrevS("I'm super sorry. I solemnly swear never to pull another prank on you, upon my honor as an aspiring hero.",14); 
	} else if (status == 3) {
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
  }
}