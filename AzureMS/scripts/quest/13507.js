/* Return to Masteria
    [Collection] Enabling Cassandra
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("Why you no ever want to help me?");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDecline("You came at JUST the right time, #b#h0##k. I'm just getting into this new prophesying app and I need somebody to try it out on! You have to let me test it on you, okay? Plleeeaaaassse?");
	} else if (status == 1) {
	    qm.sendNextS("I need tons of pebbles for this project. I already used all the good ones around here. That's why I want you to go get the pebbles the monsters stole from me! 30 would be a good start. You can handle that, right?",1);
	} else if (status == 2) {
	   	qm.sendNextPrevS("Aren't you gonna go, like, smush monsters or something? I need #r30#k of those #r#i3994718##t3994718##k items. But don't try to get them from #bmonsters that are 11 or more levels lower, or 21 or more levels higher than you#k. I'm gonna be watching Mapleflix until you get back!",1);
	} else if (status == 3) {
        qm.forceStartQuest();
		qm.forceCompleteQuest();
        qm.dispose();
    }
}