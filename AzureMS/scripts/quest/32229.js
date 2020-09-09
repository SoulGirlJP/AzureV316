/* RED 1st impact
    Explorer Thief - Shadower
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendOk("If you don't want to hear about it, fine. You can always reconsider");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendNext("You chose the path of a Shadower. So, how much do you know about the Explorer's abilities?");
	} else if (status == 1) {
	    qm.sendYesNo("Do you want to learn more about the new Explorer basics? I've got time.\r\n#r(Click Yes to move to the tutorial.)#k");
	} else if (status == 2) {
        qm.forceStartQuest();
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
}