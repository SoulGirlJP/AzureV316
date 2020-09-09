/* Dawnveil
    [Maple Castle] Welcome to the Party
	Lania
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendAcceptDecline("#bMaple Castle#k is open for the Halloween season! Anyone wearing a #rHalloween Costume#k can enter.");
    } else if (status == 1) {	   
        qm.sendNext("Wow, your outfit is FANTASTIC. You're definitely invited to the #bMaple Castle#k Halloween party.");	
    } else if (status == 2) {		
		qm.warp(910028300,0);
		qm.dispose();
	}
}

function end(mode, type, selection) {
	   qm.dispose();		
}