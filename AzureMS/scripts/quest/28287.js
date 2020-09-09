/* Dawnveil
    [Halloween] Witch Malady's Secret Mission
	Witch Malady
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendAcceptDecline("My goodness! My goodness! Always so busy. If you help me, out of the graciousness of your big heart, I may be able to give you something that you'll find useful--in the name of the Witch Malady!");
    } else if (status == 1) {	   
        qm.sendNext("Kekeke! I knew from the moment you spoke that you would be lending me a hand.");	
	} else if (status == 2) {	   
	    qm.sendPrevOk("Talk to me if you want to know more about the things you can do for me.");	
	} else if (status == 3) {	   
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}