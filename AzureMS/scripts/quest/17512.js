/* Dawnveil
	[Tynerum]So close, So Barrier
	??????
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("Who are you?");
	} else if (status == 1) {
      qm.sendNextPrev("This place looks ancient... who knows how long it's been since anyone set foot here?");
    } else if (status == 2) {	 
	  qm.sendYesNo("Who are you?");
    } else if (status == 3) {		
	  qm.sendOk("This is no place for you. Get lost.");
	} else if (status == 4) {	
		qm.forceStartQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}