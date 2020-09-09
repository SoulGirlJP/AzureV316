/* Cygnus revamp
	Noblesse tutorial
	Kia
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("*#btap, tap, whirrr, bang, bang*");
	} else if (status == 1) {
	    qm.sendNextPrev("EEEEEEEK! Don't sneak up on me like that. I almost sliced my tail off. Anyway, I'm Kia! You ready to begin the test?");	
	} else if (status == 2) {
	    qm.sendNextPrev("Its easy, easy, easy! See those boxes? Break them! Then defeat the monsters that pop out! You'll get some Proof of Exam item if you do! Whee!");
	} else if (status == 3) {
	    qm.sendNextPrev("Just use #bregular attacks to break the boxes#k! Then use #bskills to defeat the monsters#k! I need 3 Proof of Exam items!");	
	} else if (status == 4) {	
        qm.forceStartQuest();
		qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/9");		
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
	    qm.sendNext("Do you have the Proof of Exam items?");
	} else if (status == 1) {
	    qm.sendNextPrev("Yay! I'm so happy! You're every bit as amazing as I knew you'd be! Here, take this chair. I made it for you! Sit on it when you're tired, and you'll get your HP back faster! I slipped it into your Set-up inventory!");
	} else if (status == 2) {
      qm.gainItem(3010060,1);
	  qm.removeAll(4033670);
	  qm.forceCompleteQuest();
	  qm.dispose();		
	}
  }
}