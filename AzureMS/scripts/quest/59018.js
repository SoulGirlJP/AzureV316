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
	  qm.sendNextS("Tsk. You slept for three days. So irresponsible.",4,9390303);
	} else if (status == 1) {
	  qm.sendNextPrevS("You must be tired, Eka! You were hovering at #b#h0#'s#k side the entire time! Didn't even take a single nap!",4);
	} else if  (status == 2)  {
	  qm.sendNextPrevS("You have me confused with someone else, Arby.",4,9390303);
	} else if  (status == 3)  {
	  qm.sendNextPrevS("Anyway, #b#h0##k. Eka and I have to go for now. You're still too weak to handle our powers.",4);
	} else if  (status == 4)  {
	  qm.sendNextPrevS("We won't be far, though! Promise! Since we fist-bumped, we'll just be singing songs and dancing in the spirit realm until you're ready for us.",4);
	} else if  (status == 5)  {
	  qm.sendNextPrevS("Whenever you call one of us, it uses up your MP. Last time, you collapsed because you spent too much MP channeling my power. Hee hee. Not to toot my own whiskers.",4);
	} else if  (status == 6)  {
	  qm.sendNextPrevS("Lai and Fort will be roasting marshmallows in the spirit realm with me and Eka until you call them. Otherwise, you'd be out of MP all the time.",4);
	} else if  (status == 7)  {
	  qm.sendNextPrevS("Try not to miss me too much, squirt.",4,9390302);
	} else if  (status == 8)  {
	  qm.sendNextPrevS("You can summon me or Fort whenever you need help, which will probably be all the time, since there's a lot you can't do without me.",4,9390302);
	} else if  (status == 9)  {
	  qm.sendNextPrevS("When you need us, activate our guardian modes just like we taught you. Just watch your MP, little buddy.",4,9390302);
	} else if  (status == 10)  {
	  qm.sendNextPrevS("Oooo, summon me if you see any pretty flowers... or ice cream. I like lemon sorbet.",4,9390301);
	} else if  (status == 11)  {
	  qm.sendNextPrevS("You'll have to wait a bit to use me and Eka's powers.",4);
	} else if  (status == 12)  {
	  qm.sendNextPrevS("We cost too much MP. I don't want you collapsing again, though I did pick out a colorful yet tasteful arrangement for your funeral.",4);
	} else if  (status == 13)  {
	  qm.sendNextPrevS("So I won't see you for a while, Arby?",14);
	} else if  (status == 14)  {
	  qm.sendNextPrevS("Nope! But don't worry! I'll always be in your heart... And I'll pop out from time to time to say hi! Now get back to Beast Tamin'!",4);
	} else if  (status == 15)  {
	  qm.sendNextPrevS("Ciao, baby!",5);
	} else if  (status == 16)  {
	  qm.sendNextPrevS("See you later, squirt.",5,9390302);
	} else if  (status == 17)  {
	  qm.sendNextPrevS("Are we going somewhere?",5,9390301);
	} else if  (status == 18)  {
	  qm.sendNextPrevS("Finally, I get to be alone.",5,9390303);
	} else if  (status == 19)  {
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
      qm.warp(866138000);
	  qm.dispose();
	}
}