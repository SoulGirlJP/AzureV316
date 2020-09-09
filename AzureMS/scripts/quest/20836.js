/* Cygnus revamp
	Noblesse tutorial
	Kizan
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("THERE YOU ARE! I told you not to move! You're going to pay for that. Maybe not today, maybe not tomorrow, but one day, when you're on a particularly annoying mission, know that I've secretely arranged it. Now get back to the Drill Hall!");
	} else if (status == 1) {	
        qm.forceStartQuest();   
        qm.warp(130030105);		
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
	    qm.sendNext("You haven't forgotten my last lesson, have you?! Pretty Ctrl to perform a regular attack!\r\nTime for the next lessons! Ready?!");
	} else if (status == 1) {
	    qm.sendNextPrev("Skill attacks! They dish out the pain! Open your Skill windows by pressing the K key to access your skills.\r\nYou'll get more skills once you're more experienced, so never stop training!");
	} else if (status == 2) {
	    qm.forceCompleteQuest();
	    qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/6");	
	    qm.dispose();		
	}
  }
}