/* Return to Masteria
    Kobold Fragrance
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("Isn't saving the town worth smelling like a #o9390927# for a little bit? Let me know if you change your mind...");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendAcceptDecline("Here's the #b#t2432251##k. Go on. Take it.");
	} else if (status == 1) {
	   qm.sendNextS("Double-click the #b#t2432251##k to use it. Forfeit then restart the quest if you lose the perfume.",1);
	} else if (status == 2) {
	   qm.sendNext("Defeating #o9390915# won't be easy.");
	} else if (status == 3) {
	    qm.forceStartQuest();
		qm.gainItem(2432251,1);
	    qm.dispose();	
	}
}