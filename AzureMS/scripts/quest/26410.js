/* RED 1st impact
    [Smart Mount] Lend a Hand
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendNext("Psh, FINE. I'll be here whenever you find the time.");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendYesNo("Finally! I need help! Can you come meet me?\r\n#r(Click Yes to teleport to the Victoria Tree Platform where Instructor Irvin is.)#k");
	} else if (status == 1) {
        qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.warp(104020100,0);
        qm.dispose();
    }
}