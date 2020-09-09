/* Dawnveil
    Survivalism Instructions
	Spiegameman
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendSimple("The #rSurvivalism Challenge#k is a test of skill, where up to five players compete for the top spot in four mini-games. I'll send you an invitation between #rQRQRQR#k and #rQRQRQR#k, every #b10#k, #b30#k, and #b50#k minutes on the hour.\r\n\r\n(You can play #b10#k more games today.)");
		qm.dispose();
	}
}

function end(mode, type, selection) {
	   qm.dispose();		
}