/* Cygnus revamp
	Noblesse tutorial
	Kimu
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	status++;
	if (status == 0) {
	  qm.sendNext("I like to give all the new recruits a little gift when they come to Ereve. It's important that the recruits look up to snuff, you know? Hit the#e#b I key#k#n to open up your inventory when we're done talking. Double click on that hat I gave you!");
	} else if (status == 1) {
	  qm.forceStartQuest();
	  qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/5");
	  qm.gainItem(1003769, 1);
	  qm.dispose();
	} else if  (status == 2)  {
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
		qm.sendOk("I found Kinu in a pile of books. He'll tell you what you need to know, or possibly just put you to sleep. Or both.");
	    qm.dispose();
	} else if (status == 1) {
	    qm.dispose();
	}
  }
}