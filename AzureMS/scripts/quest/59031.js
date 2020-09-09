/* Return to Masteria
    Summoning Arby
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNextS("I'm here! It's finally time to play with me!",5,9390463);
	} else if (status == 1) {
	    qm.sendNextPrevS("Press #b[down]#k when you change modes to activate me.",5,9390463);
	} else if (status == 2) {
	    qm.sendNextPrevS("Since I'm so adorable, you and your party are going to LOVE me!",5,9390463);
	} else if (status == 3) {
	    qm.sendNextPrevS("You can only use my skills when you activate my mode, so remember to #bhotkey my cat skills#k for #bmy mode!#k",5,9390463);
	} else if (status == 4) {
	    qm.sendNextPrevS("This is my last gift!\r\nTake this #i1142675:##b#t1142675:##k",5,9390463);
	} else if (status == 5) {
	    qm.sendNextPrevS("That's it for now! I'll see you around! Buh-bye!",5,9390463);
	} else if (status == 6) {
	    qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.gainItem(1142675,1)
		qm.teachSkill(110001504,1,1);
        qm.dispose();
	}
}