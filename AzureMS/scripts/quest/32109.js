/* Dawnveil
    [Ellinel Fairy Academy] Cootie's Suggestion
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
		qm.forceStartQuest();
		qm.dispose();
	}
}

function end(mode, type, selection) {
if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNext("You think YOU can find the missing children? How do you propose to do that?");
	} else if (status == 1) {
	    qm.sendNextPrevS("#bI'd like to look through the childrens' rooms.",6);	
    } else if (status == 2) {	 
	    qm.sendNext("Do you think you can find some clues in their things? That might actually work...");
    } else if (status == 3) {
	    qm.sendNextPrevS("You're just looking for a way to steal from us! Headmistress Ivana, we must not listen to these cunning strangers!",4,1500002);	
    } else if (status == 4) {
	    qm.sendNextPrev("I have not given them my full trust, but the safety of those students must be our top priority. We have no choice but to allow them some leeway.");
    } else if (status == 5) {
	    qm.sendNextPrev("#b#h ##k. I will grant you permission to search the academy. You shall be restricted to the dormitories on the second and third floors. And do be careful. The academy was constructed with a number of defenses to ward off intruders and I do not feel the need to have them deactivated, considering the situation.");
	} else if (status == 6) {
	    qm.sendNextPrevS("I've got my eye on you, outsider.",4,1500002);	
 	} else if (status == 7) {
	    qm.sendNextPrevS("We'll find those precious children, no matter what it takes! I'll meet you on the second floor.\r\n#b(Head to the 2nd floor of Ellinel Fairy Academy and meet Cootie the Really Small.)",4,1500000);	
	} else if (status == 8) {
		qm.forceCompleteQuest();
		qm.gainExp(1600);
	    qm.dispose();		
	}
  }
}