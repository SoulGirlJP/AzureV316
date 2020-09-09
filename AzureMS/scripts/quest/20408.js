/*
 * Cygnus 2nd Job advancement - Proof of test
 * Soul
 */

var status = -1;

function start(mode, type, selection) {
	end(mode,type,selection); //idk lol
}

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("I guess you are not ready to tackle on the responsibilities of an official knight.");
	    qm.dispose();
	    return;
	} else if (status >= 2) {
	    status--;
	} else {
	    qm.dispose();
	    return;
	}
    } else {
	status++;
    }
    if (status == 0) {
		qm.sendYesNo("You've saved Erev. Do you want to become a Captain Knight?");
    } else if (status == 1) {
	if (!qm.canHold(1142069,1)) {
	    qm.sendOk("Please make space.");
	    qm.dispose();
	    return;
	}
	    qm.forceCompleteQuest();
	    if (qm.getJob() == 1111) {
		qm.changeJob(1112);
	    } else if (qm.getJob() == 1211) {
		qm.changeJob(1212);
	    } else if (qm.getJob() == 1311) {
		qm.changeJob(1312);
	    } else if (qm.getJob() == 1411) {
		qm.changeJob(1412);
	    } else if (qm.getJob() == 1511) {
		qm.changeJob(1512);
	    }
	    qm.teachSkill(10001005,1,0); //Echo
	    qm.gainItem(1142069,1);
	    qm.sendNext("You are now an official knight of the Knights of Cygnus.");
    } else if (status == 3) {
	qm.sendPrev("Now that you are officially a Knight of cygnus, act like one so you will keep Goodness's name up high.");
	qm.dispose();
    }
}