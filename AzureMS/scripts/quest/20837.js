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
		qm.sendNext("I supposed you're about ready to become a Knight-in-Training. I'll send you to the Test Site, and remember, no slouching!");
	} else if (status == 1) {	
        qm.forceStartQuest();	
        qm.forceCompleteQuest();
        qm.warp(130030106);		
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}