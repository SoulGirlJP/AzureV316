/* Return to Masteria
	BeastTamer Tutorial
	All animals
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNextS("Time to go, squirt!",4,9390302);
	} else if (status == 1) {
      qm.sendNextPrevS("Macaroni! What? I just like that word.",4,9390300);
	} else if  (status == 2)  {
	  qm.sendNextPrevS("Me, too!",4,9390301);
	} else if  (status == 3)  {
	  qm.sendNextPrevS("Hmph.",4,9390303);
	} else if  (status == 4)  {
	  qm.sendNextPrevS("There's a town nearby! I love towns! Let's go check it out!",4,9390300);
    } else if  (status == 5)  {
	  qm.sendNextPrevS("I betcha don't know how to travel like a Critter Champ yet, #b#h0##k. Goodie, I get to teach you!.",4,9390300);
    } else if  (status == 6)  {
	  qm.sendNextPrevS("Press #bW#k to open your World Map!",4,9390300);
    } else if  (status == 7)  {	
	  qm.sendNextPrevS("Once you open your World Map, find #bStump Town#k, and click on it! CLICK CLICK CLICK! Just once, though. I only said it three times 'cause I'm excitable",4,9390300);
    } else if  (status == 8)  {	
	  qm.sendNextPrevS("Then click on the Enable Navigation button at the top right of the World Map! Again, just one click!",4,9390300);
	} else if  (status == 9)  {	
	  qm.sendNextPrevS("I suppose I should teach you the #q110001514# skill so you can return to #bStump Town#k. Try it sometime, if you want. I don't really care.\r\n#s110001514#",4,9390303);
	} else if  (status == 10)  {
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
		qm.sendNextS("Now, let's go, let's go, let's go!",4,9390300); 
	} else if (status == 1) {
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
  }
}