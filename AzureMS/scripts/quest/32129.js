/* Dawnveil
    [Ellinel Fairy Academy] Professor Peace
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
	    qm.sendAcceptDecline("Not bad, #h #. You really thought like a fairy out there. Let's return to the Headmistress in Ellinel.\r\n#b(You will be moved to Ellinel if you accept)");	
	} else if (status == 1) { 
	    qm.sendNext("Great. All the kids must be back by now, right?");
	} else if (status == 2) { 
		qm.warp(101072000,0);
		qm.gainExp(10190);
		qm.forceStartQuest();
		qm.dispose();
	}
}