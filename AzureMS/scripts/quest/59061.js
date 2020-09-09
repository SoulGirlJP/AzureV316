/* Return to Masteria
    The Story of Heroes
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNext("Looking at you, I'm reminded of those brave heroes who stood up to the Black Mage ages ago...");
	} else if (status == 1) {
	    qm.sendNextPrevS("Sweet! Granny told me allllll about them. I hope I get to meet them someday!",14);
	} else if (status == 2) {
	   qm.sendNextPrev("I imagine someone on Victoria Island would know how to get in touch with them.");
	} else if (status == 3) {
	   qm.sendNextPrev("Why don't you head to the harbor outside town? There should be a boat headed for Victoria Island there.");
	} else if (status == 4) {
	   qm.sendNextPrev("Best of luck on your travels, young one. I know you'll be a great hero someday.");
	} else if (status == 5) {
	    qm.forceStartQuest();
		qm.gainExp(2758);
	    qm.dispose();	
	}
}