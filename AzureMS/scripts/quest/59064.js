/* Return to Masteria
	BeastTamer Tutorial
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("Hey, stranger! Are you okay?");
	} else if (status == 1) {
	  qm.sendNextPrev("I can't believe you ran into Grosso Polpo! Your ship was half-destroyed. You're lucky we found you... Where were you going, anyway?");
	} else if  (status == 2)  {
	  qm.sendNextPrevS("I was on my way to #bVictoria Island#n to meet the heroes of Maple World!",14);
	} else if  (status == 3)  {
	  qm.sendNextPrev("The heroes of Maple World? Do you happen to know #bEvan#n, Freud's successor?");
	} else if  (status == 4)  {
	  qm.sendNextPrevS("Boy, DO I! He's the Dragon Master! I mean, I don't know him personally, but I've memorized all his trivia!",14);
	} else if  (status == 5)  {
	  qm.sendNextPrev("Heh, well, I hear Evan's in #bHenesys#n. Perhaps you should head there.");
	} else if  (status == 6)  {
	  qm.sendNextPrev("I truly believe that, with the right mix of determination, guts, and luck, anyone can become a hero. Someday, you'll have to tell me your story. But for now, farewell.");
	} else if  (status == 7)  {
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
	  qm.dispose();
	}
}