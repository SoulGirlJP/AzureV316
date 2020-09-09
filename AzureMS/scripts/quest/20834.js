/* Cygnus revamp
	Noblesse tutorial
	Cygnus
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Ah, you must be one of my new recruits.");
	} else if (status == 1) {
	    qm.sendNextPrevS("My name's #h #. I love Ereve. It's so pretty here.");
	} else if (status == 2) {
      qm.sendNextPrev("Oh, yes. Ereve is lovely, and so peaceful. Has your training been difficult?");
	} else if (status == 3) {
	    qm.sendNextPrevS("Nothing I can't handle. Maple World better get ready, because I'm about to save it so hard.");
	} else if (status == 4) {
        qm.sendNextPrev("(She smiles.) Your enthusiasm is reassuring. The suffering of so many weighs heavily upon me... I hope you can help me ease their pain.");
	} else if (status == 5) {
	    qm.sendNextPrevS("Yeah, um, I'm sorry. Who are you again? You don't look like much of a knight...");
	} else if (status == 6) {
        qm.sendNextPrev("My name is...");
	} else if (status == 7) {	
        qm.forceStartQuest();    
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}