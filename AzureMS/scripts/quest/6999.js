/* RED 1st impact
	[Zakum] Statue of Dread
    [Pirate] 3rd job instructor
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 4) {
		    qm.sendNext("I see... Come back when you're strong enough!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
		qm.sendNext("I'd heard you were improving. You look well.");
	} else if (status == 1) {
        qm.sendNextPrev("I was waiting to see if you would put your your name on the line to challenge Zakum as a Pirate.");
    } else if (status == 2) {	  
	    qm.sendNextPrev("I don't think you're quite ready for the big beast. Why don't you fight something a little more your level?");
    } else if (status == 3) {	 
        qm.sendNextPrev("You'll get the experience AND deal a blow to the real Zakum. You'll have to pass a test to challenge the real deal, though");    
    } else if (status == 4) {	 
        qm.sendAcceptDecline("Are you ready to face Crumbling Zakum?\r\n#r<<Click Yes to teleport to the #bChief's Residence in El Nath#r.>>");   	
    } else if (status == 5) {	
        qm.sendNext("I'll take to you the Chief's Residence. Be brave.");	
    } else if (status == 6) {	   	
		qm.warp(211000001,0);
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
	    qm.sendNext("Have you come to try your strength against Crumbling Zakum?");
	} else if (status == 1) {
	    qm.sendNextPrev("It's a simple enough thing. Place an #b#t4001796##k on the altar, and Crumbling Zakum will arise.");
	} else if (status == 2) {
	    qm.sendNextPrev("Crumbling Zakum is not quite as strong as the normal monster, but it will not be an easy task to defeat it. The experience should prepare you for the real thing.");	
	} else if (status == 3) {
	    qm.sendNextPrev("Let me know when you want to challenge Crumbling Zakum. I'll give you an #r#t4001796##b and #bAll Cure Potions#k for the fight. I'll also teleport you to the altar.");
	} else if (status == 4) {
	    qm.sendNextPrev("Now you can challenge Crumbling Zakum whenever you'd like. Just take this, and let me know when you want to fight.");
	} else if (status == 5) {
	  qm.forceCompleteQuest();
	  qm.dispose();		
	}
  }
}