/* Cygnus revamp
	Noblesse tutorial
	Kinu + Hawkeye (1102006+1101006)
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("Your first lesson will be about Ereve. Ereve is a floating island, held aloft by the Empress's powers. It has remained stationary for a number of years but once floated about Maple World like a ship.");
	} else if (status == 1) {
      qm.sendNextPrev("Right now, we're focused on gathering up information on the Black Mage and preparing our forces to face him. It's some serious business, lemme tell you.");
	} else if (status == 2) {
      qm.sendNextPrev("The knights themselves are gathered into 5 groups, based around the Spirits of Light, Fire, Wind, Lightning, and Darkness. Each group is led by a Chief Knight and...\r\nOh, here is one now. Hello Hawkeye.");
	} else if (status == 3) {
	  qm.sendNextPrevS("Ahoy! I wanted to welcome the new knight in person. I must give ye my apologies, for I left the muffins I baked ye on my ship.", 1,0,1101007);
	} else if (status == 4) {
	  qm.sendNextPrev("Hawkeye, this is highly unorthodox!");
	} else if (status == 5) {
	  qm.sendNextPrevS("Yar, have a heart, Kinu. Don't ye remember yer first day with the knights? Wasn't it a mite overwhelming without a friend?", 1,0,1101007);
	} else if (status == 6) {
	  qm.sendNextPrev("I suppose I could bend the rules this once. #h #, meet Hawkeye, the Chief Knight of Lightning !");
	} else if (status == 7) {
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
	    qm.sendNext("Tis a pleasure to welcome ye to the knights.");
	} else if (status == 1) {
	    qm.sendNextPrevS("How kind! Thank you!", 3);
	} else if (status == 2) {
	    qm.sendNextPrev("Anything ye need, ye come straight to me, ol' Hawkeye, captain of the Thunder Breakers. I beg ye pardon, but what was ye name again? I plumb forgot already.");	
	} else if (status == 3) {
	    qm.sendNextPrevS("Hawkeye, the new recruit's name is #h #! Now, shoo!", 1,0,1102006);
	} else if (status == 4) {
	    qm.sendNextPrev("I promise, I won't forget yer name the next time we meet.");
	} else if (status == 5) {
	    qm.sendNextPrevS("Finally! Where was I? Oh yes, once your initial training is over, you will choose your knightly path and be promoted to\r\nKinght-in-Training! The paths you can pick from are Light, Fire, Wind, Lightning, and Darkness.", 1,0,1102006);
	} else if (status == 6) {
	    qm.sendNextPrevS("As a Cygnus Knight, your duty will be to protect the Empress, defeat the evil Black Mage, spy on his henchmen, and keep peace in Maple World. Simple, yes?", 1,0,1102006);	
	} else if (status == 7) {
	    qm.sendNextPrevS("I've given you enough to ponder. Go speak with Kimu for your next lesson.", 1,0,1102006);	
    } else if (status == 8) {
	  qm.forceCompleteQuest();
	  qm.dispose();		
	}
  }
}