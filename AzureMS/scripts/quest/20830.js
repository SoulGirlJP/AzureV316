/* Cygnus revamp
	Noblesse tutorial
	Kimu
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	status++;
	if (status == 0) {
		qm.sendYesNo("Okay! You've earned a 30 second re-hydration break! Drink this, and don't faint on me!");
	} else if (status == 1) {
	    qm.forceStartQuest();
		qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/2");
		qm.gainItem(2001555, 1);
	    qm.dispose();
	}
  }

function end(mode, type, selection) {
	 qm.dispose();
	}