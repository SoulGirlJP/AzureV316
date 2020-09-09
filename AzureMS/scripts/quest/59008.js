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
	  qm.sendNext("So, #b#h0##k. You from around here?");
	} else if (status == 1) {
	  qm.sendNextPrevS("Yeah, I live with my Granny. Just that way.",14);
	} else if  (status == 2)  {
	  qm.sendAcceptDecline("Great! You go back and wait there while #bI go gather up my friends#k. Sound good?");
	} else if  (status == 3)  {
	  qm.sendNextS("All right, little kitty. I'll warm a fish for you, too!",14);
	} else if  (status == 4)  {
	  qm.sendNextPrev("My name is #bArby#k, not kitty! And save the fish for #bFort#k. I'm a vegetarian.");
	} else if  (status == 5)  {
	  qm.sendNextPrevS("#bFort#k? Is he another one of the Critter Champs?",14);
	} else if  (status == 6)  {
	  qm.sendNextPrev("Yup! You'll like #bFort#k. He's really strong!");
	} else if  (status == 7)  {
	  qm.forceStartQuest();
	  qm.gainExp(58);
	  qm.gainAp(5);
	  qm.dispose();
	}
}