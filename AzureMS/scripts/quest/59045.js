/* Return to Masteria
    Den Expedition 1
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendOk("Psssh. Chicken. Unlike me, you must be TERRIFIED of kobolds.");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("Remember that kobold hideout I mentioned last time? I've got a favor to ask. I lost something there... I think one of them must've picked it up and stashed it in their pouch.");
	} else if (status == 1) {
	    qm.sendYesNo("(Can you get 30 #t4034003# items from #o9390914# monsters in #m866000130#?\r\n(You'll be moved there if you accept.)");
	} else if (status == 2) {
	    qm.sendNext("Thanks! I promise I'll tout great tales of your marvelous deeds if you find the thing I'm looking for!");	
	} else if (status == 3) {
	    qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.forceStartQuest(59046);
		qm.forceCompleteQuest(59046);
		
	    qm.dispose();	
	}
}
