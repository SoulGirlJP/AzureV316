/* Cygnus revamp
	Noblesse tutorial
	Kinu
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	status++;
	if (status == 0) {
	  qm.sendYesNo("I am Kinu. I will get you up to speed on Ereve's history. Now, go on and sit! I teach best when my students are shorter than me!");
	} else if (status == 1) {
      qm.sendNext("Press X in front of any chair to sit down. If you own one, its the same deal. X marks the butt.");
	} else if (status == 2) {
	  qm.forceStartQuest();
	  qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/10");
	  qm.dispose();
	} else if (status == 3) {
	  qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}