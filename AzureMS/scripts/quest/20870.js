/* RED Zero
    End of the Knight-in-Training
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("I see. It's good to know your limits, but I think you're ready to move on...");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDecline("#h0#, you have done surprisingly well. Do you wish to take the #bKnighthood Exam#k? If you pass, you will become a full-fledged knight.");
	} else if (status == 1) {
	    qm.sendOk("Come to Ereve when you are ready for the exam. Your Chief Knight will test your abilities.");
	    qm.forceStartQuest();
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
}