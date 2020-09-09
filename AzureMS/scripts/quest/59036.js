/* Return to Masteria
    The Boy Who Cried Kobold
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
	    qm.sendNextS("Did you know...",0,9390313);
	} else if (status == 1) {
	    qm.sendNextPrevS("There are kobolds in the nearby forests?",0,9390313);
	} else if (status == 2) {
	    qm.sendNextPrevS("I've met a kobold or two... Vile beasts.",14);	
	} else if (status == 3) {
		qm.sendNextPrevS("Really? And here I thought kobolds were just a legend. Never met anyone's that seen one in person before.",0,9390313);
	} else if (status == 4) {
	    qm.forceCompleteQuest();
		qm.gainExp(246);
	    qm.dispose();		
	}
  }
}