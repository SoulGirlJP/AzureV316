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
	  qm.sendNext("Your basic attacks no longer make me want to cry. It is time to teach you something more advanced!");
	} else if (status == 1) {
      qm.sendNextPrevS("Hey, everyone. You having fun with your training, #h #?\r\nKimu, Kizan, can I speak to you for a moment?", 1,0,1102100);
	} else if (status == 2) {
      qm.sendNextPrev("#h #! Take a break! That's an order!");	
	} else if (status == 3) {
	  qm.sendNextPrevS("(Kiku whispers to the others.)", 1,0,1102000);
	} else if (status == 4) {
      qm.sendNextPrev("!!!");	
    } else if (status == 5) {
	  qm.sendNextPrevS("...", 1,0,1102004);
	} else if (status == 6) {
      qm.sendNextPrev("#h #! Do not move from that spot! I will return shortly.");
	} else if (status == 7)  {
	  qm.spawnNpcForPlayer(1102113, -824, -88);
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
	  qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}