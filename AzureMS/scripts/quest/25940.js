/* Dawnveil
    The love flows with Maple Chat!
	Eileen
    Made by Daenerys
*/

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {   	
		qm.forceCompleteQuest();
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
	    qm.gainitem(3700135, 1);
		qm.forceCompleteQuest();
	    qm.dispose();		
	}
  }
}