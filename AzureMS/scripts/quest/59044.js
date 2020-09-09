/* Return to Masteria
    Discovering the Den
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendOk("Let me know if you want to hear any more of my heroic tales!");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("So there I was, one man against ELEVEN #o9390927#s! Bam! Boom! POW! I made 'em all scramble for the hills all by myself! They even whimpered the location of their hideout while they ran!");
	} else if (status == 1) {
	    qm.sendYesNoS("(Tom is so full of squirrel poop. Anyway, guess I should tell Woodrock about the #m866000130#.)",16);
	} else if (status == 2) {
	    qm.sendNext("You're not gonna tell Woodrock again, are you?");	
	} else if (status == 3) {
	    qm.forceStartQuest();
	    qm.dispose();	
	}
}