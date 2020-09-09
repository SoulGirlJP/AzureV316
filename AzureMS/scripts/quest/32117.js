/* Dawnveil
    [Ellinel Fairy Academy] Graduate Search
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
	    qm.sendAcceptDecline("Do you know Arwen or Rowen from Ellinia? They are former Ellinel Fairy Academy graduates. They might know of some places we teachers do not.\r\n\r\n #b#e(You will be moved to Ellinia if you accept.)");	
	} else if (status == 1) { 
	    qm.sendNext("Please meet Arwen the Fairy in Ellinia");
	} else if (status == 2) { 
		qm.warp(101000000,0);
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
	    qm.sendNext("What do you want? I'm busy...");
	} else if (status == 1) {
	    qm.sendNextPrevS("(You tell Arwen what's going on.)",2);
	} else if (status == 2) {
	    qm.sendNextPrev("Missing students? That sounds dangerous... Ellinel isn't the safest place to go missing.");
	} else if (status == 3) {
	    qm.forceCompleteQuest();
		qm.gainExp(1900);
	    qm.dispose();		
	}
  }
}