/* Return to Masteria
    Mesoranger! Rules and Regulations
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendSimple("Alien robots are invading! Protect the world! Participate in the Mesoranger event by clicking on the invitation that comes every #r20 minutes#k from 6am until midnight.#l\r\n#b#L0#[Mesoranger] Game Info#l\r\n#L1#Check Daily Limit");		
    } else if (status == 1) {
        sel = selection;
	  if (selection == 0) {		
	    qm.sendSimple("#r[Mesoranger!]#k is a rhythm game where you groove to a beat to defeat alien robots with your allies. Want more details?r\r\n#b#L0#Selecting Difficulty\r\n#b#L1#Normal Attacks\r\n#b#L2#[Special Attack 1] Final Beam\r\n#b#L3#[Special Attack 2] Special Missile\r\n#b#L4#Scoring");	
		qm.dispose();
     } else if (selection == 1) {
		qm.sendOk("You can participate #b5#k more time(s) today.");
		}
	    qm.dispose();
    }
}