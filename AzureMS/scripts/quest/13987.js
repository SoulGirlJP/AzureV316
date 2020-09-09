/* RED Zero
    Second Season of RED Achievements
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("Well, think again.");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDecline("RED just keeps on rolling, and we've got even more achievements to celebrate! Are you up to the challenge?");
	} else if (status == 1) {
	    qm.sendOk("Click on the #e#b#fEffect/BasicEff.img/MainNotice/Achieve/Default/0#Trophy Icon#k#n on the left side of your screen to check your Achievements!");
        qm.forceStartQuest();
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
}