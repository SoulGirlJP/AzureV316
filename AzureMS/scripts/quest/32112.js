/* Dawnveil
    [Ellinel Fairy Academy] Clue Number One
	Hidey Hole
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendAcceptDecline("There's something weird over here. Should we check it out?");	
	} else if (status == 1) { 
		qm.forceStartQuest();
		qm.gainItem(4033828,1);
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
	    qm.sendNext("You found a script? Let me take a look at that.\r\n\r\n...Well, there are some obvious problems in the the first act, and the All-You-Can-Eat Sundae Bar scene seems a little tacked on, but this is a fine example of fairy entertainment. Why did the kids have this?");
	} else if (status == 1) {
	    qm.sendNextPrev("Let's investigate the third floor! Maybe we'll find something else.\r\n\r\n(#bTalk to #e#p1500000##k#n on the 3rd floor of Ellinel Fairy Academy.)");
	} else if (status == 2) {
		qm.removeAll(4033828);
		qm.forceCompleteQuest(32111);
		qm.forceCompleteQuest();
		qm.forceStartQuest(32113);
		qm.forceCompleteQuest(32113);
	    qm.dispose();		
	}
  }
}