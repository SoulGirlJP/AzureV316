/* Return to Masteria
    Pia's Letter
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNextS("Here's the letter. I'll even read it to you.",1);
	} else if (status == 1) {
	    qm.sendNextPrevS("Dear Animal Ears,\r\n\r\nAfter an excruciating search, I'm pleased to say that I've located Evan for you! Come to the Golem Temple at once. #b#p1012102##k will lead the way.\r\nI think you'll be... surprised... when you get here, heh heh.\r\n\r\n- Hugs and kisses, Vicky",1);
	} else if (status == 2) {
	    qm.sendYesNo("Thanks! Smashing 70 Mixed Golems should do the trick!");
	} else if (status == 3) {
	    qm.sendNextS("What? I have to help again? Sigh, whatever. Do you want to go now?\r\n#b(You'll be teleported there if you accept.)#k",1);
	} else if (status == 4) {
	    qm.sendNextPrevS("I don't know when I'll see you again, but I hope you find Evan.",1)
	} else if (status == 5) {
	    qm.forceStartQuest();
		qm.warp(866000901,0);
        qm.dispose();
	}
}