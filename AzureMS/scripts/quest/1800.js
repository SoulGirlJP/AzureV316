/* Dawnveil
    [Evolution System] Suspicious Movement on the Path
	 Claudine
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendAcceptDecline("Calling the Alliance. The Black Wings are operation deep within the mines below Edelestein. Something foul is afoot. Need help in #m310010000# immediately. Please accept.");
	} else if (status == 1) {
		qm.warp(310010000,0);
		qm.forceStartQuest();
		qm.forceCompleteQuest();
        qm.dispose();
	}
}

function end(mode, type, selection) {
      qm.dispose();		
}       
  
  