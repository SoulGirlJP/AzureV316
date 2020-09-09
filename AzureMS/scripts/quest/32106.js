/* Dawnveil
    [Ellinel Fairy Academy] Ivana's Misunderstanding
	Headmistress Ivana
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNextS("You're still here. Is there more to discuss?",4);
    } else if (status == 1) {	   
        qm.sendNextPrevS("You cannot trust this outsider, Headmistress! The human will only feed us lies and lame excuses.",4,1500002);
    } else if (status == 2) {
        qm.sendNextPrevS("#bI thought you were a wise and rational people. We should analyze the facts before we come to any kind of judgment.",6);	
	} else if (status == 3) {	
        qm.sendNextPrevS("Five children vanished into thin air at once! What other facts do you need? This one kidnapped them, end of story!",4,1500002);	
	} else if (status == 4) {
	    qm.sendNextPrevS("#bSo, you have proof that Cootie was the culprit?",6);	
	} else if (status == 5) {
	    qm.sendNextPrevS("The one you call Cootie has been chased off of these grounds a number of times, but he continues to return and defy our wishes. He has been conducting secret experiments in our forest!",4,1500002);	
	} else if (status == 5) {
	    qm.sendNextPrevS("He's been planning this! It's the perfect crime. He comes to scout the area for weeks before he finally steals the children from underneath our very noses! He knew we had a number of staffers going out on vacation, and I caught him lotering around the scene of the crime afterward. He MUST be guilty!",4,1500002);	
	} else if (status == 6) { 
	    qm.sendNextPrevS("#b(Could Cootie really have planned the kidnapping of five children? He's so small!)",6);	
	} else if (status == 7) {
	    qm.sendNextPrevS("Your desire is to find the most rational explanation. I present to you that our prime subject IS the most rational explanation. We must interrogate him.",4);
	} else if (status == 8) {
		qm.sendNextPrevS("#b(They're way too upset to see anybody except Cootie as a suspect. Better talk to him...)",6);
	} else if (status == 9) {
		qm.forceStartQuest();
		qm.dispose();
	}
}

function end(mode, type, selection) { 
    if (mode == 0 && type == 0) { 
        status--; 
    } else if (mode == -1) { 
        qm.dispose(); 
        return; 
    } else { 
        status++; 
    } 
    if (status == 0) {
	    qm.sendNext("Are you here to chastise me?");
    } else if (status == 1) {
	    qm.sendNextPrev("Look, hear me out, please? Why would I ever kidnap a fairy? SURE, they're THE most evolved and amazing species on the planet, but...");
	} else if (status == 2) {	
	    qm.forceCompleteQuest();
	    qm.gainExp(1600);
		qm.gainItem(4033826,1)
		qm.gainItem(4033827,20)
		qm.dispose(); 
  } 
 }
