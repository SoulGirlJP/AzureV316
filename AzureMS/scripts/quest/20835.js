/* Cygnus revamp
	Noblesse tutorial
	Neinheart + other chief knights
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Ah, #b#h #,#k isn't it? I am #p1102107#, strategist for our young Empress. You will be seeing much more of me in the future.");
	} else if (status == 1) {
	    qm.sendNextPrev("Shouldn't you be in training? What urgent matter brings you here, #h #? Did Kiku send you with clire news?");
	} else if (status == 2) {
      qm.sendNextPrevS("Nah. A bird told me to follow it, and I ended up here!");
	} else if (status == 3) {
	   qm.sendNextPrevS("#h #, are ye sure ye didn't follow me here to collect yer welcome muffins? I'm sorry, but someone ate them.", 1,0,1101007);
	} else if (status == 4) {
        qm.sendNextPrevS("And they were delicious. But enough chatter, we have work to do, Hawkeye. Let us go.", 1,0,1101006);
	} else if (status == 5) {
	    qm.sendNextPrevS("It was lovely to meet you, #h #. I sincerely hope that you become a knight of courage and wisdom.", 1,0,1404008);
	} else if (status == 6) {
        qm.sendNextPrev("I have my doubts. Not even promoted to knight-in-training and already nosing about the Empress's quarters for no good reason. Unacceptable!");
	} else if (status == 7) {	
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
	    qm.sendNext("Don't listen to ol' Neinheart, #h #. Ye may as well introduce yerself to the other Chief Knights while yer here.");
	} else if (status == 1) {
	    qm.sendNextPrevS("Oh, um, sure. My name's #h #. I'm here to become a knight, defeat the Black Mage, save Maple World, and, just recently, be a spokesperson for bird-kind.");
	} else if (status == 2) {
	    qm.sendNextPrevS("Big aspirations for such meager amounts of skill. May the shadows keep you safe until you learn some humility.", 1,0,1101006);	
	} else if (status == 3) {
	    qm.sendNextPrevS("Oh, give the kid a break! Welcome to Ereve! We'll meet again more formally soon.", 1,0,1102109);
	} else if (status == 4) {
	    qm.sendNextPrevS("Not if the kid doesn't finish training first... Becoming a knight takes a lot of hard work and dedication.", 1,0,1102110);
	} else if (status == 5) {
	    qm.sendNextPrevS("I hate to break up the party, but we have a meeting to get to. Sorry, kid. I'm sure we'll get a chance to talk more later.", 1,0,1101003);
	} else if (status == 6) {
	    qm.sendNextPrev("Yer right. Mihile. We've got to get goin', #h #. Keep up with yer trainin'.");	
	} else if (status == 7) {
	  qm.forceCompleteQuest();
	  qm.dispose();		
	}
  }
}