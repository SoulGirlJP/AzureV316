var status = -1;

function start(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("This is an important decision to make.");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendNext("Hmm... hello my friend I'm in a big trouble I'm looking for someone to help me out, are you the right person I'm looking for?");
    } else if (status == 1) {
	qm.sendPlayerToNpc("#bI can help you for sure.");
    } else if (status == 2) {
	qm.sendNext("You are saving my life sir, let's hope you won't fall down!");
    } else if (status == 3) {
	qm.forceStartQuest();
	qm.dispose();
    }
}
