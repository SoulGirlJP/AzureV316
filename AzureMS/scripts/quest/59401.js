/* Return to Masteria
    Arby's Pet Tutorial
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 2) {
		    qm.sendNext("Let me know when you to meet my buddy!");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	    qm.sendNext("I'm here to introduce a new friend!");
	} else if (status == 1) {
	    qm.sendNextPrev("They're called #bpets#k! They can #bpick up items#k and even use #bHP or MP potions#k, depending on their skills!");
	} else if (status == 2) {
	    qm.sendYesNo("I'd like to introduce you to my friend the #bRune Snail#k. Ready to meet the little guy?");	
	} else if (status == 3) {
	    qm.sendNext("Click my friend from your #bCash Tab#k to summon him!");		
	} else if (status == 4) {
	    //qm.gainItem(5000054,1);//crashes when hovering over it....
	    qm.sendNext("Your new slimy friend will stick around for #b5 hours#k. After that, you'll have to part ways.");
	} else if (status == 5) {
	    qm.sendNextPrev("Oh! And remember, your pet may hide if its #bFullness#k drops too much. No one likes to be hungry. Feed your pet with items like #i2120000;# from time to time to get that Fullness back up!");
	} else if (status == 6) {
	    qm.sendNextPrev("I know you two'll get along great!");
	} else if (status == 7) {
	    qm.forceStartQuest();
		qm.forceCompleteQuest();
	    qm.dispose();	
	}
}