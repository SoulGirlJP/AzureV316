/* RED Zero
    [Maple Bingo] Count to Bingo
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendOk("You can participate in Maple Bingo 10 times each day per account. That means you can play #b10 more times#k today, #h0#! Match it up!");
	    qm.dispose();
	}
}