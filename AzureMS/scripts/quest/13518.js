/* RED Zero
    [New Years] Yut Wars
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    qm.sendOk("Eh, I can't force you. You're missing out on some fantastic rewards, though!");
	    qm.dispose();
	}
	status--
    }
    if (status == 0) {
		qm.sendAcceptDeclineS("You're not alone! Now you can play the Game of Yut with your friends. You want to give it a shot now?",1);
    } else if (status == 1) {	   
		qm.forceStartQuest();
		qm.dispose();
	}
}
