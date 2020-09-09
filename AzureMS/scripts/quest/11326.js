var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.sendNext("Well hello my friend, I'm #bBjorn!#k, I need some help could you mind to help me?");
    } else if (status == 1) {
	qm.sendAcceptDecline("Oh right!, Thank you so much, but are you sure you are ready?");
    } else if (status == 2) {
	qm.forceStartQuest();
	qm.dispose();
    }
}

