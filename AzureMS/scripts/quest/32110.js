/* Dawnveil
    [Ellinel Fairy Academy] Combing the Academy 1
	Cootie
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Isn't this place amazing, #h #. Let's have a look around.");
    } else if (status == 1) {	   
        qm.sendNextPrevS("What should we do first?",6);	 
    } else if (status == 2) {
        qm.sendNextPrevS("You know what kids love the most? Secrets! I remember trading potion recipes with my friends behind the teacher's backs, hiding away my alchemy research in the nooks around school...",4);	
	} else if (status == 3) {	
        qm.sendNextPrev("I bet these kids have hidden notes all around the school. But how would we find them?");	
	} else if (status == 4) {
	    qm.sendNextPrevS("#bThey must be nearby, we should look around.",6);	
	} else if (status == 5) {
	    qm.sendNextPrev("Yeah, we'll find them if we search hard! I just now it.");	
	} else if (status == 6) {
	    qm.sendAcceptDecline("I bet those #r#o3501004##k things got some of the notes... I saw you fighting a moment ago, and you were relatively good at it. Maybe you can get one or two of those #bSchoolboys' Note#k notes back?");	
	} else if (status == 7) { 
	    qm.sendNext("Not all of these notes are going to be useful, but you'll have to read every single one to find the clues!\r\n\r\n(Defeat #r#o3501004##k monsters, gather #bSchoolboys' Note#k notes, and read them to find clues.) ");	
	} else if (status == 8) {
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.gainExp(4000);
		qm.dispose();
	}
}

function end(mode, type, selection) { 
        qm.dispose(); 
 }
