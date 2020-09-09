/* Cygnus revamp
	Noblesse tutorial
	Tiny Bird
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("(*chirp, chirp*)");
	 } else if (status == 1) {
	  qm.sendNextPrevS("Look! It's a bird! Is it talking to me?");
	 } else if (status == 2) {
	 qm.sendNext("*chirp, chirp, chirp*");
	 } else if (status == 3) {
	  qm.sendNextPrevS("OMGOODNESS! I can understand birds! I must be some sort of superhero. It... wants me to follow it. I'm sure Kizan won't mind.");
	 } else if (status == 4) {
	  qm.forceStartQuest();
	  qm.removeNpc(130030105,1102113);
	  qm.warp(130030104);
	  qm.dispose();
	} else if  (status == 5)  {
	  qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}