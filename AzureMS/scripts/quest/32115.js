/* Dawnveil
    [Ellinel Fairy Academy] Clue Number Two
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
		qm.gainItem(4033829,1);
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
	    qm.sendNext("These costumes must be what the girls were working on in secret! I bet they were putting on that play we found! But how does this be into their disappearance?");
	} else if (status == 1) {
	    qm.sendNextPrev("Let's go back to the first floor and talk with the Headmistress.\r\n\r\n(#bGo to the 1st floor of Ellinel Fairy Academy.)");
	} else if (status == 2) {
		qm.removeAll(4033829);
		qm.forceCompleteQuest(32114);
		qm.forceCompleteQuest();
	    qm.dispose();		
	}
  }
}