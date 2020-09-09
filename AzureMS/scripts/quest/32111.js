/* Dawnveil
    [Ellinel Fairy Academy] Combing the Academy 2
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
	    qm.sendAcceptDecline("If this note is accurate, the secret thing they were working on will be around the dormitories. The boys' hall stretches across most of the second floor. Let's try there.");	
	} else if (status == 1) { 
	    qm.sendNext("I, uh, I'm going to hang out here until you're done. I need to look around...\r\n#b(Check the dormitories on both ends of the 2nd floor.)");	
	} else if (status == 2) {
		qm.forceStartQuest();
		qm.gainExp(4000);
		qm.dispose();
	}
}

function end(mode, type, selection) { 
        qm.dispose(); 
 }
