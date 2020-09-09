/* Cygnus revamp
	Noblesse tutorial
	Kimu
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	status++;
	if (status == 0) {
		qm.sendNext("This is the ceremony where we welcome all the newbie knights. We need to find Training Instructor Kiku. He's gotta be around here somewhere...");
	} else if (status == 1) {
      qm.sendNext("Having a hard time finding Kiku? You should use that NPC button next to your map! Just click on Kiku and you'll see an arrow!\r\nHurry up and go say hi before he gets grumpy!");
	} else if (status == 2) {
	  qm.forceStartQuest();
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
	    qm.sendNext("I wish they'd start sending over some decent sized fighters, but I guess you'll work.");
		qm.dispose();
	}
  }
}