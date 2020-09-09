/* RED Zero
    [New Years] The Kiterunner
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    qm.sendOk("Figures. Young 'uns! Well, let me know if you change your mind. *grumble grumble*");
	    qm.dispose();
	}
	status--
    }
    if (status == 0) {
		qm.sendAcceptDeclineS("*grumble grumble* Stupid crows! They've stolen all the food people made for the town festival. Young Mapler, will you help get the food back?\r\n#b(Press Yes to get on the waiting list)",1);
    } else if (status == 1) {	   
		qm.forceStartQuest();
		qm.dispose();
	}
}
