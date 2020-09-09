/* Cygnus revamp
	Noblesse tutorial
	Kiku
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Hey, you're looking pretty good. #h #. I think I'll promote you to Knight-in-Training.\r\nJust follow the arrows, and they'll lead you straight to the Empress.");
	} else if (status == 1) {
	    qm.sendNextPrev("Prove me right, #h #.");
	} else if (status == 2) {
        qm.forceStartQuest();	
		qm.gainExp(3348);
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}