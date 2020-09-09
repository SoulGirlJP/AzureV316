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
	  qm.sendNext("I lost my bell up that tree!\r\nSmack the tree with #b[Ctrl]#k and pick it up with #b[Z]#k when it drops.");
	} else if (status == 1) {
	  qm.sendAcceptDecline("Hurry up, please! I feel naked! Plus, I hid some kitty snacks in that bell...");
	} else if  (status == 2)  {
	  qm.sendNextS("Just like before, press #e#b[Ctrl]#k#n to attack the tree and #e#b[Z]#k#n to pick up the bell.",14);
	} else if  (status == 3)  {
	  qm.forceStartQuest();
	  qm.gainItem(4034005,1);
	  qm.killAllMobs();
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
	  qm.sendNextS("Here you go, kitty cat!",14);
	} else if (status == 1) {
	  qm.sendNextPrev("Turn around. I can't let you see where I hid my snack...");
	} else if  (status == 2)  {
	  qm.sendNextPrevS("Done yet?",14);
	} else if  (status == 3)  {
	  qm.sendNextPrev("Just a second... *scarf, scarf, scarf, burp*");
	} else if  (status == 4)  {
	  qm.forceCompleteQuest();
	  qm.gainItem(4034005, -1);
	  qm.warp(866107000,0);
	  //qm.showBeastTamerTutScene2();
	  qm.dispose();
	}
  }
}