/* Cygnus revamp
	Noblesse tutorial
	Kimu
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Welcome to Ereve! This is the safest and most peaceful place in all of Maple World. Empress Cygnus keeps it nice all the time!   You're#b #h ##k, right? Here to joint the #p1064023# Knights. I'm your guide, #p1102004#. All the Noblesses in town come to me first!");
	} else if (status == 1) {
      qm.sendNextPrev("You need to get over to the Knight's Orientation right away. They're getting started already. Follow me, okay?.");
    } else if (status == 2) {	    
		qm.warp(130030100);
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}