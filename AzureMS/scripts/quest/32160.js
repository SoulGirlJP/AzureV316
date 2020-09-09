/* RED 1st impact
	[Riena Strait] Get it Strait
	Lilin
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 2) {
		    qm.sendNext("Are you too busy?");
            qm.dispose();
        status--;
    }
    if (status == 0) {
		qm.sendNext("Something's wrong with Rien. The glaciers are melting!");
	} else if (status == 1) {
        qm.sendNextPrev("...");
    } else if (status == 2) {	  
	    qm.sendAcceptDecline("I need your help! Come visit me!\r\n\r\n#b#e(Press Accept to move to Rien.)#n#k");
    } else if (status == 3) {	 
        qm.sendNext("I will meet you in Rien.");    
    } else if (status == 4) {	 	   	
		qm.warp(140000000,0);
		qm.forceStartQuest();
		qm.dispose();
	}
}