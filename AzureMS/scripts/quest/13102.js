/* Dawnveil
    [Maple Castle] The Grand Invitation 
	Cassandra + Cygnus
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
		qm.dispose();
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
	    qm.sendNext("Oh, hello there, #b#e#h ##k#n! Didn't think I'd run into you. I've been busy cleaning up #bMaple Castle#k for the past few days. It's not great for my skin, to be honest...");
	} else if (status == 1) {
	    qm.sendNextPrev("To tell you the truth, it's REALLY hard to renovate an entire castle with a few people. I was hoping you might swing by and help us out.");
	} else if (status == 2) {
	    qm.sendPrev("#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i2431132##bHalloween Mask Fragment x1#k\r\n#i3994650##bBoo Buddy Candy x1#k\r\n\r\nThis is your Maple Castle welcoming gift. Enjoy!");
	    qm.forceCompleteQuest();
	    qm.gainItem(2431132, 1);
		qm.gainItem(3994650, 1);
	    qm.dispose();		
	}
  }
}