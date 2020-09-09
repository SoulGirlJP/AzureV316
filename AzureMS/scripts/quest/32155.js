/* Dawnveil
    [Theme Dungeon] Ellinel Fairy Academy
	Professor Dreamboat
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendAcceptDecline("You seem to be in good condition. How about another mission? I've received an urgent request from the #bEllinel Fairy Academy");
    } else if (status == 1) {	   
        qm.sendNext("A young human entered the #bEllinel Fairy Academy#k and it's caused quite a disturbance.");
    } else if (status == 2) {
        qm.sendNextPrev("I don't know all the details, but I know our relationship with the fairies is strained enough as it is. Will you go to the North Forest near Ellinia and meet with #bFanzy#k?");	
	} else if (status == 3) {	
	    qm.sendYesNo("Fanzy will take you into the land of the fairies. I can send you to him directly, if you'd like.'");	
	} else if (status == 4) {
		qm.warp(101030000,0);
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
	    qm.sendNext("Are you the one I invited to help with the ruckus at the Ellinel Fairy Academy?");
    } else if (status == 1) {
	    qm.sendNextPrevS("Um, of course?",15);
	} else if (status == 2) {	
	    qm.sendNextPrev("You don't look as strong as I'd hoped. But, you're famous, so i'll leave it to you.");
	} else if (status == 3) {
	    qm.forceCompleteQuest();
		qm.dispose(); 
  } 
 }