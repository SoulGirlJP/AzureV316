/* Cygnus revamp
	Noblesse tutorial
	Kiku
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	status++;
	if (status == 0) {
	  qm.sendYesNo("I don't know if they told you during the orientation, but we're here to fight the Black Mage. Right now,you're not fit to fight a black mop. I'm gonna fix that.\r\nYou ready for some action? ");
	} else if (status == 1) {
	  qm.forceStartQuest();
	  qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/1");
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
	    qm.sendNextPrev("Did you meet up with Kiku? He seems tough, but he's total softly.");
	} else if (status == 1) {
	    qm.sendNextPrev("The orientation's almost over. You wanna go ahead and get started on your training?");
	} else if (status == 2) {
	    qm.forceCompleteQuest();
		qm.warp(130030101,0);
	    qm.dispose();
	}
  }
}