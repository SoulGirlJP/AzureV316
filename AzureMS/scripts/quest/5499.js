/* Return to Masteria
    The Curbrock in the Grass
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNext("Have you come to hear the story of Curbrock?");
	} else if (status == 1) {
        qm.sendNextPrev("Come close, then, and I will tell you my tale");
 	} else if (status == 2) {
	    qm.sendNextPrev("Long ago, there was a snake that tried to become a man by eating helpless human beings.");
	} else if (status == 3) {
	    qm.sendNextPrev("The snake pretended to be like us, even taking on a human name--Curbrock! However, when rumors of his evil spread, he vanished.");
	} else if (status == 4) {
	    qm.sendNextPrev("But I've heard rumors recently. People have been vanishing from the SleepyWood. They say that Curbrock has returned.");
	} else if (status == 5) {
	    qm.sendYesNo("Would you go there for yourself and see if these rumors are true? I can send you there now if you want.\r\n\r\n#b#e(You'll be teleported to the Curbrock's Hideout if you accept. Forfeit and reaccept the quest to start again.)#n#k");
	} else if (status == 6) {
	    qm.sendNext("Remember, Curbrock is deadly. You must run away if you meet him!");
	} else if (status == 7) {
	    qm.forceStartQuest();
		qm.warp(600050000);
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
	    qm.sendNext("You don't look well, my friend.");
	} else if (status == 1) {
	    qm.sendNextPrev("Then the rumors are true. We must stop him before he eats anyone else.\r\nBut you are still too weak. Return to me when you are stronger.\r\nI will give you #i1182054:# #b#t1182054##k as a gift.");
	} else if (status == 2) {
	    qm.gainItem(1182054,1)
		qm.forceCompleteQuest();
	    qm.dispose();		
	}
  }
}