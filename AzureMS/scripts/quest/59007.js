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
	  qm.sendNext("Now that that's take care of, let me introduce myself one more time. I'm #bArby#k, a proud member of the Critter Champs!");
	} else if (status == 1) {
	  qm.sendNextPrevS("I'm #b#h0##k. Never heard of the Critter Champs, except the five billion times you've mentioned them already.",14);
	} else if  (status == 2)  {
	  qm.sendNextPrev("We're the bravest of the brave! The champiest of the champs! We're #bfour stupendous critters#k on a crazy adventure, destined to become great heroes!");
	} else if  (status == 3)  {
	  qm.sendNextPrevS("You guys and me both! I'm going to be great, just like the five Maple Heroes!",14);
	} else if  (status == 4)  {
	  qm.sendNextPrev("Ohmigosh, I'm the five Maple Heroes biggest fan-cat! HEY! Brain bulb! #bWhy don't you join us?#k Then there'll be five Critter Champs, just like the five Maple Heroes!");
	} else if  (status == 5)  {
	  qm.sendNextPrevS("But I'm not a critter!",14);
	} else if  (status == 6)  {
	  qm.sendNextPrev("You aren't? You look like a critter, just with a human face and body.");
	} else if  (status == 7)  {
	  qm.sendNextPrevS("I'm a human! With animal ears and an animal tail. Huge difference!",14);
	} else if  (status == 8)  {
	  qm.sendAcceptDecline("You keep telling yourself that. Anyway, wanna join us or what?");
	} else if  (status == 9)  {
	  qm.sendNextS("Um... Sure! Aspiring heroes oughtta stick together!",14);
	} else if  (status == 10)  {
	  qm.sendNext("Kitty dance! Woohoo! Let me know when you're ready!");
	} else if  (status == 11)  {
	  qm.forceStartQuest();
	  qm.dispose();
	}
}