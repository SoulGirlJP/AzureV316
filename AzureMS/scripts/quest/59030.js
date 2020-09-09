/* Return to Masteria
    Eka's Power
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNextS("To be honest, I never thought I'd see the day you'd be strong enough to handle my power. I suppose I have to let you summon me now...",5,9390303);
	} else if (status == 1) {
	    qm.sendNextPrevS("Press #b[up]#k when you change modes to activate me.",5,9390303);
	} else if (status == 2) {
	    qm.sendNextPrevS("I'm sure you already know how to-- Ugh. Why do you always look so lost?",5,9390303);
	} else if (status == 3) {
	    qm.sendNextPrevS("Fine, fine. I'll explain. You can only use my skills when you activate my mode, so remember to #bhotkey my hawk skills#k for #bmy mode#k.",5,9390303);
	} else if (status == 4) {
	    qm.sendNextPrevS("Take this. Remember, this does NOT mean I like you.\r\n#i1142674:##b#t1142674:##k",5,9390303);
	} else if (status == 5) {
	    qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.gainItem(1142674,1)
		qm.teachSkill(110001503,1,1);
        qm.dispose();
	}
}