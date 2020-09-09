/* RED 1st impact
    First Season of RED Achievements
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendNext("Well, think again.");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDeclineNoESC("Hello, #b#h0##k! Now that RED is here, it's time to challenge yourself with some exciting achievements. Go the extra mile to earn your goodies! What do you say?");
	} else if (status == 1) {
	    qm.sendNext("Click on the #e#b#fEffect/BasicEff.img/MainNotice/Achieve/Default/0#Trophy Icon#k#n on the left side of your screen to check your Achievements!");
	} else if (status == 2) {
        qm.forceStartQuest();
		qm.forceCompleteQuest();
        qm.dispose();
    }
}