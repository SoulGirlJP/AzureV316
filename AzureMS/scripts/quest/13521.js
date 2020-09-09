/* RED Zero
    [New Years] Traditional Games
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendOk("You can play the Game of Yut #b10 more times today.#k\r\nYou can play the Kite Rider game #r10 more times today#k.\r\nCheck out these awesome New Year's games!");
		qm.dispose();
	}
}