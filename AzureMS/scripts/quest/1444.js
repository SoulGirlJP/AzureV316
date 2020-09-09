/* 
	NPC Name: 		Arec
	Map(s): 		El Nath : Chief's Residence
	Description: 	Quest - [Job Advancement]Blade Lord
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;

	if (status == 0) {
	    if (qm.getPlayer().getJob() == 520) {
		qm.sendNext("All you have to do is go where I tell you, beat an enemy, and bring back the spoils... If you don't think you can handle that, go back to Victoria Island now. Ossyria is no place for weaklings.");
	} else if (status == 1) {
	    qm.sendNextPrev("There is an obelisk at Sharp Cliff I near El Nath. Behind it, there's a path to the Holy Ground at the Snowfield. Touch the Holy Stone there, and you'll be warped to another dimension. Your enemy is waiting for you there.");
	} else if (status == 2) {
	    qm.sendNextPrev("Bring me proof of your victory, and we'll see if you're ready.");
	} else if (status == 3) {
	    qm.forceStartQuest();
	    qm.dispose();
	}
    }
}
}
function end(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    if (!qm.haveItem(4031059)) {
		qm.sendOk("Very good. Now come back to me when you have received #i4031059");
		qm.forceStartQuest();
		qm.dispose();
	    } else {
		qm.getPlayer().changeJob(521);
		qm.gainSp(3);
	    qm.sendOk("You have advanced to Outlaw. Good luck, see you again at level 120.");
		qm.forceCompleteQuest();
		qm.dispose();
		 }
    }
}
}