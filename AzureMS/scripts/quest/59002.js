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
	  qm.sendNext("What is it with you and that hero thing? Do you really think you're cut out to be a hero?");
	} else if (status == 1) {
      qm.sendNextPrevS("Sure, I'm going to be a legend, just like the five Maple Heroes!",14);
	} else if  (status == 2)  {
	  qm.sendNextPrev("Yeah. *snort* Sure.");
	} else if  (status == 3)  {
	  qm.sendNextPrevS("You don't believe me? That's okay. What can I do to prove it to you?",14);
	} else if  (status == 4)  {
	  qm.sendAcceptDecline("You know #bWolf Forest#k to the east? The place even grown-ups are afraid of? Go there... ALONE. Then maybe I'll believe you'll be a hero someday.");
	} else if  (status == 5)  {
	  qm.sendNextS("You got it! Be back in a jiffy!",14);
	} else if  (status == 6)  {
	  qm.sendNextPrev("W-wait! Really? Are you sure?");
	} else if  (status == 7)  {
	  qm.sendNextPrevS("Bah, a hero like me ain't scared of no forest!",14);
	} else if  (status == 8)  {
	  qm.forceStartQuest();
	  qm.dispose();
	}
}