/* Return to Masteria
	BeastTamer Tutorial
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendOkS("You just let me know when you and your little animal pals are ready, hehe!",4);
            qm.dispose();
        status--;
    }
	if (status == 0) {
	  qm.sendNextS("Hey, kid. You need to get to Henesys? Yeah, I was eavesdropping.",4);
	} else if (status == 1) {
	  qm.sendYesNoS("Fort's going with you, right? In that case, I know all about the pirates-- I mean, the geography of the area. I can send you to #bHenesys#k right now. Like, this exact second. Interested?\r\n(You'll be moved once you accept.)",4);
	} else if  (status == 2)  {
	  qm.sendNextS("Leave it to MOI, haha! That's Vicky for 'me'.",4);
	} else if  (status == 3)  {
	  qm.sendNextPrevS("Keep an eye on that Fort for me, hehe.",4);
	} else if  (status == 4)  {
	  qm.forceStartQuest();
	  qm.warp(100000000,1);
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
	    qm.sendNextS("Very good. We'll proceed with our wicked plot-- er, our wonderful plan to help get more information on Evan!",4);
	} else if (status == 1) {
	    qm.sendNextPrevS("I'll let you know the moment we learn anything about #bEvan#k! Until than, toodles.",4);
	} else if (status == 2) {
	    qm.forceCompleteQuest();
		qm.gainExp(3309);
	    qm.dispose();		
	}
  }
}