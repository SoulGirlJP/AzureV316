/* RED Zero
    [Maple Bingo] Bingo Bonanza
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("Let me know if you change your mind. You're missing out!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDeclineS("Maple Mayhem Bingo is ready to play! Wanna give it a try now?",1);
	} else if (status == 1) {
	    qm.dispose();
	}
}