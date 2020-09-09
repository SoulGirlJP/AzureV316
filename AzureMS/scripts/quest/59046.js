/* Return to Masteria
    Den Expedition 2
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.warp(866000140,0);
		qm.dispose();
	}
}